package codegeneration;

import java.util.*;

public class GenerationContext {

    // ================================================================== //
    //  1. المتغيرات المستخرجة من Python AST                              //
    // ================================================================== //

    private final Map<String, Object> globalVariables = new LinkedHashMap<>();

    // ================================================================== //
    //  2. Routes                                                          //
    // ================================================================== //

    /**
     * routes
     * ──────
     * ربط كل route path بـ function name.
     *
     * مثال:
     *   "/" → "home"
     *   "/add" → "add_product"
     *   "/go-home" → "go_home"
     */
    private final Map<String, String> routes = new LinkedHashMap<>();

    // ================================================================== //
    //  routeToTemplate — ربط route path ← template filename             //
    // ================================================================== //

    /**
     * routeToTemplate
     * ────────────────
     * يربط كل route path بـ template filename المُولَّد.
     * يُملأ من ContextBuilder عند قراءة render_template() calls.
     *
     * مثال مستخرج من app.py:
     *   @app.route("/")
     *   def home():
     *       return render_template("index.html", ...)
     *   → routeToTemplate["/"] = "index.html"
     *
     *   @app.route("/add", methods=["GET","POST"])
     *   def add_product():
     *       return render_template("add_product.html")
     *   → routeToTemplate["/add"] = "add_product.html"
     *
     *   @app.route("/product/<int:product_id>")
     *   def product_detail(product_id):
     *       return render_template("product_detail.html", ...)
     *   → routeToTemplate["/product/<int:product_id>"] = "product_detail.html"
     *
     * يُستخدم في JinjaRenderer.resolveRoutePath() لتحويل:
     *   href="/add" → "add_product.html"
     *   href="/"    → "index.html"
     */
    private final Map<String, String> routeToTemplate = new LinkedHashMap<>();

    // ================================================================== //
    //  3. Template Variables                                              //
    // ================================================================== //

    /**
     * templateVariables
     * ─────────────────
     * المتغيرات المُمررة لكل template في render_template().
     *
     * مثال:
     *   "index.html" → {"products": "products", "now": "datetime.now()"}
     *   "add_product.html" → {}
     */
    private final Map<String, Map<String, String>> templateVariables =
            new LinkedHashMap<>();

    // ================================================================== //
    //  4. HTML/Jinja AST Trees                                           //
    // ================================================================== //

    /**
     * templates
     * ─────────
     * شجرة AST لكل template HTML/Jinja.
     * Key = اسم الملف، Value = root Node من HtmlVisitor.
     *
     * مثال:
     *   "index.html" → HtmlTag(html) → [head, body, ...]
     */
    private final Map<String, AST_H_C.Node> templates = new LinkedHashMap<>();

    // ================================================================== //
    //  5. Output HTML                                                     //
    // ================================================================== //

    /**
     * outputHtml
     * ──────────
     * HTML الناتج النهائي لكل صفحة.
     * يُملأ من JinjaRenderer بعد تقييم كل Jinja expressions.
     *
     * مثال:
     *   "index.html" → "<!DOCTYPE html><html>...<h3>Apple</h3>...</html>"
     */
    private final Map<String, String> outputHtml = new LinkedHashMap<>();

    // ================================================================== //
    //  6. Variable Scope Stack (لـ for loops)                            //
    // ================================================================== //

    /**
     * variableScopes
     * ──────────────
     * Stack من Maps. كل Map = scope واحد.
     *
     * كيف يعمل:
     *   {% for product in products %}
     *     ↓ pushScope("product", {id:1, name:"Apple",...}, 0)
     *     ↓ render:  {{ product.name }} → "Apple"
     *     ↓ popScope()
     *   {% endfor %}
     */
    private final Deque<Map<String, Object>> variableScopes = new ArrayDeque<>();

    /** يتتبع loop.index و loop.index0 */
    private final Deque<Integer> loopIndices = new ArrayDeque<>();

    // ================================================================== //
    //  7. Log و Warnings                                                  //
    // ================================================================== //

    private final List<String> logEntries = new ArrayList<>();
    private final List<String> warnings   = new ArrayList<>();

    // ================================================================== //
    //  resolveVariable — قلب GenerationContext                           //
    // ================================================================== //

    /**
     * resolveVariable(expr)
     * ──────────────────────
     * يُحلّ أي Jinja expression إلى قيمة حقيقية.
     *
     * الحالات التي يتعامل معها:
     *
     *  "products"           → List من globalVariables
     *  "product.name"       → يبحث عن "product" في scopes → يُرجع map["name"]
     *  "product.price"      → نفس المبدأ
     *  "products|length"    → يُرجع size() الـ list
     *  "loop.index0"        → رقم الـ iteration (يبدأ من 0)
     *  "loop.index"         → رقم الـ iteration (يبدأ من 1)
     *  null / ""            → يُرجع null
     */
    public Object resolveVariable(String expr) {
        if (expr == null || expr.isBlank()) return null;
        expr = expr.trim();

        // ── loop.index0 / loop.index ─────────────────────────────────── //
        if (expr.equals("loop.index0")) {
            return loopIndices.isEmpty() ? 0 : loopIndices.peek();
        }
        if (expr.equals("loop.index")) {
            return loopIndices.isEmpty() ? 1 : (loopIndices.peek() + 1);
        }

        // ── Jinja filter: products|length ────────────────────────────── //
        if (expr.contains("|")) {
            String[] parts  = expr.split("\\|", 2);
            Object   base   = resolveVariable(parts[0].trim());
            String   filter = parts[1].trim();
            if ("length".equals(filter) && base instanceof List) {
                return ((List<?>) base).size();
            }
            return base;
        }

        // ── dot notation: product.name / product.price ───────────────── //
        if (expr.contains(".")) {
            String[] parts  = expr.split("\\.", 2);
            String   root   = parts[0].trim();
            String   field  = parts[1].trim();
            Object   rootVal = resolveVariable(root);
            if (rootVal instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) rootVal;
                return map.get(field);
            }
            return null;
        }

        // ── بحث في loop scopes أولاً ─────────────────────────────────── //
        for (Map<String, Object> scope : variableScopes) {
            if (scope.containsKey(expr)) return scope.get(expr);
        }

        // ── بحث في globalVariables ───────────────────────────────────── //
        if (globalVariables.containsKey(expr)) {
            return globalVariables.get(expr);
        }

        // ── لم يجد ───────────────────────────────────────────────────── //
        return null;
    }

    // ================================================================== //
    //  Scope Management                                                   //
    // ================================================================== //

    /**
     * pushScope — يُستدعى عند دخول {% for x in list %}
     *
     * مثال:
     *   pushScope("product", {"id":1,"name":"Apple",...}, 0)
     *   → الآن {{ product.name }} يُرجع "Apple"
     */
    public void pushScope(String varName, Object value, int loopIndex) {
        Map<String, Object> scope = new LinkedHashMap<>();
        scope.put(varName, value);
        variableScopes.push(scope);
        loopIndices.push(loopIndex);
    }

    /**
     * popScope — يُستدعى عند الخروج من {% endfor %}
     */
    public void popScope() {
        if (!variableScopes.isEmpty()) variableScopes.pop();
        if (!loopIndices.isEmpty())    loopIndices.pop();
    }

    // ================================================================== //
    //  Setters                                                            //
    // ================================================================== //

    public void addGlobalVariable(String name, Object value) {
        globalVariables.put(name, value);
    }

    public void addRoute(String path, String functionName) {
        routes.put(path, functionName);
    }
    private final Set<String> dynamicVariables = new HashSet<>();

    public boolean isDynamic(String name) { return dynamicVariables.contains(name); }

    public void addRouteToTemplate(String routePath, String templateFilename) {
        routeToTemplate.put(routePath, templateFilename);
    }

    public void addTemplateVariables(String templateName,
                                     Map<String, String> vars) {
        templateVariables.put(templateName, vars);
    }

    public void addTemplate(String filename, AST_H_C.Node rootNode) {
        templates.put(filename, rootNode);
    }

    public void addOutput(String pageName, String html) {
        outputHtml.put(pageName, html);
    }

    public void addLog(String entry) {
        logEntries.add(entry);
    }

    public void addWarning(String warning) {
        warnings.add("[WARNING] " + warning);
        logEntries.add("[WARNING] " + warning);
    }

    // ================================================================== //
    //  Getters                                                            //
    // ================================================================== //

    public Map<String, Object>              getGlobalVariables()   { return globalVariables; }
    public Map<String, String>              getRoutes()            { return routes; }
    public Map<String, String>              getRouteToTemplate()   { return routeToTemplate; }
    public Map<String, Map<String, String>> getTemplateVariables() { return templateVariables; }
    public Map<String, AST_H_C.Node>        getTemplates()         { return templates; }
    public Map<String, String>              getOutputHtml()        { return outputHtml; }
    public List<String>                     getLogEntries()        { return logEntries; }
    public List<String>                     getWarnings()          { return warnings; }

    public AST_H_C.Node getTemplate(String name) {
        return templates.get(name);
    }

    public Map<String, String> getTemplateVars(String templateName) {
        return templateVariables.getOrDefault(templateName,
                Collections.emptyMap());
    }
}