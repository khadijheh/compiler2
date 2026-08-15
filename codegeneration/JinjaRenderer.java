package codegeneration;

import AST_H_C.*;

import java.util.*;

/**
 * JinjaRenderer
 * =============
 * يُحوّل شجرة HTML/Jinja AST إلى HTML string نظيف.
 *
 * التعديلات المُضافة:
 * ─────────────────────────────────────────────────────────────────
 * 1. resolveRoutePath(href) — تابع مساعد
 *    يُحوّل route paths في الـ href attributes:
 *      /add          → add.html
 *      /             → index.html
 *      /go-home      → index.html  (redirect)
 *      /product/1    → product_details.html
 *
 * 2. renderAttribute() يستدعي resolveRoutePath()
 *    على كل href attribute تلقائياً.
 */
public class JinjaRenderer {

    private final GenerationContext context;

    private static final Set<String> VOID_TAGS = new HashSet<>(Arrays.asList(
            "area", "base", "br", "col", "embed", "hr", "img", "input",
            "link", "meta", "param", "source", "track", "wbr"
    ));

    public JinjaRenderer(GenerationContext context) {
        this.context = context;
    }

    public String render(Node root) {
        if (root == null) {
            context.addWarning("JinjaRenderer.render(): root node is null");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        renderNode(root, sb);
        return sb.toString();
    }

    // ================================================================== //
    //  Core Dispatcher                                                    //
    // ================================================================== //

    private void renderNode(Node node, StringBuilder sb) {
        if (node == null) return;

        if (node instanceof JinjaExpression) {
            renderExpression((JinjaExpression) node, sb);
            return;
        }
        if (node instanceof JinjaForBlock) {
            renderForBlock((JinjaForBlock) node, sb);
            return;
        }
        if (node instanceof JinjaIfBlock) {
            renderIfBlock((JinjaIfBlock) node, sb);
            return;
        }
        if (node instanceof HtmlTag) {
            renderHtmlTag((HtmlTag) node, sb);
            return;
        }
        if (node instanceof HtmlText) {
            renderHtmlText((HtmlText) node, sb);
            return;
        }
        if (node instanceof CSS_Style) {
            renderCssStyle((CSS_Style) node, sb);
            return;
        }
        if (node instanceof HtmlAttribute) return;

        if (node instanceof JinjaTag || node instanceof JinjaSingleTag) {
            for (Node child : node.getChildrenNodes()) {
                renderNode(child, sb);
            }
            return;
        }

        for (Node child : node.getChildrenNodes()) {
            renderNode(child, sb);
        }
    }

    // ================================================================== //
    //  1. {{ expression }}                                                //
    // ================================================================== //
    private void renderExpression(JinjaExpression node, StringBuilder sb) {
        String expr = node.getExpression();
        String root = expr.contains(".") ? expr.substring(0, expr.indexOf('.')) : expr;

        if (context.isDynamic(root)) {
            sb.append("{{ ").append(expr).append(" }}");   // يبقى Jinja صالح لِـ Flask وقت التشغيل
            return;
        }

        Object value = context.resolveVariable(expr);
        if (value == null) {
            context.addWarning("{{ " + expr + " }} — not found (line " + node.getNumberOfLine() + ") — rendered empty");
            return;
        }
        sb.append(escapeHtml(value.toString()));
    }


    // ================================================================== //
    //  2. {% for x in collection %}                                      //
    // ================================================================== //

    private void renderForBlock(JinjaForBlock node, StringBuilder sb) {
        String forExpr = extractForExpression(node);

        if (forExpr == null) {
            context.addWarning("JinjaForBlock at line " + node.getNumberOfLine()
                    + " — cannot extract expression — skipped");
            return;
        }

        context.addLog("For loop expression: '" + forExpr + "'");

        String[] parts = forExpr.split("\\s+in\\s+", 2);
        if (parts.length != 2) {
            context.addWarning("JinjaForBlock: invalid '" + forExpr + "' — skipped");
            return;
        }

        String varName  = parts[0].trim();
        String collName = parts[1].trim();

        Object collObj = context.resolveVariable(collName);
        if (collObj == null) {
            context.addWarning("For: '" + collName + "' not found — skipped");
            return;
        }
        if (!(collObj instanceof List)) {
            context.addWarning("For: '" + collName + "' is not a List — skipped");
            return;
        }

        List<?> collection = (List<?>) collObj;
        if (collection.isEmpty()) {
            context.addLog("For: '" + collName + "' is empty");
            return;
        }

        context.addLog("For: iterating " + collection.size() + " items");

        for (int i = 0; i < collection.size(); i++) {
            context.pushScope(varName, collection.get(i), i);

            for (Node child : node.getChildrenNodes()) {
                if (child instanceof JinjaTag || child instanceof JinjaSingleTag) {
                    for (Node inner : child.getChildrenNodes()) {
                        renderNode(inner, sb);
                    }
                } else {
                    renderNode(child, sb);
                }
            }

            context.popScope();
        }
    }

    // ================================================================== //
    //  3. {% if condition %}                                              //
    // ================================================================== //

    private void renderIfBlock(JinjaIfBlock node, StringBuilder sb) {
        String  condition = extractIfCondition(node);
        boolean result    = evaluateCondition(condition);

        context.addLog("If: '" + condition + "' = " + result);

        if (result) {
            for (Node child : node.getChildrenNodes()) {
                if (child instanceof JinjaTag || child instanceof JinjaSingleTag) {
                    for (Node inner : child.getChildrenNodes()) {
                        renderNode(inner, sb);
                    }
                } else {
                    renderNode(child, sb);
                }
            }
        } else {
            context.addLog("If: false → element NOT generated");
        }
    }

    // ================================================================== //
    //  evaluateCondition                                                  //
    // ================================================================== //

    public boolean evaluateCondition(String condition) {
        if (condition == null || condition.isBlank()) return true;
        condition = condition.trim();

        if (condition.startsWith("not ")) {
            return !evaluateCondition(condition.substring(4).trim());
        }
        if (condition.contains("==")) {
            String[] p = condition.split("==", 2);
            Object left = context.resolveVariable(p[0].trim());
            String right = p[1].trim();
            if (left == null) return "None".equals(right) || "null".equals(right);
            return left.toString().equals(right);
        }
        if (condition.contains("!=")) {
            String[] p = condition.split("!=", 2);
            Object left = context.resolveVariable(p[0].trim());
            if (left == null) return true;
            return !left.toString().equals(p[1].trim());
        }
        if (condition.contains(">=")) {
            String[] p = condition.split(">=", 2);
            Object left = context.resolveVariable(p[0].trim());
            return left != null && toDouble(left.toString()) >= toDouble(p[1].trim());
        }
        if (condition.contains(">")) {
            String[] p = condition.split(">", 2);
            Object left = context.resolveVariable(p[0].trim());
            return left != null && toDouble(left.toString()) > toDouble(p[1].trim());
        }
        if (condition.contains("<=")) {
            String[] p = condition.split("<=", 2);
            Object left = context.resolveVariable(p[0].trim());
            return left != null && toDouble(left.toString()) <= toDouble(p[1].trim());
        }
        if (condition.contains("<")) {
            String[] p = condition.split("<", 2);
            Object left = context.resolveVariable(p[0].trim());
            return left != null && toDouble(left.toString()) < toDouble(p[1].trim());
        }

        Object value = context.resolveVariable(condition);
        if (value == null)            return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof List)    return !((List<?>) value).isEmpty();
        if (value instanceof String)  return !((String) value).isEmpty();
        if (value instanceof Number)  return ((Number) value).doubleValue() != 0;
        return true;
    }

    // ================================================================== //
    //  4. HTML Tag                                                        //
    // ================================================================== //

    private void renderHtmlTag(HtmlTag tag, StringBuilder sb) {
        String tagName = tag.getTagName();
        if (tagName == null || tagName.isBlank()) return;

        // تجاوز الـ wrapper html node المزدوجة
        if ("html".equalsIgnoreCase(tagName)) {
            List<Node> children = tag.getChildrenNodes();
            if (children.size() == 1 && children.get(0) instanceof HtmlTag) {
                HtmlTag inner = (HtmlTag) children.get(0);
                if ("html".equalsIgnoreCase(inner.getTagName())) {
                    renderHtmlTag(inner, sb);
                    return;
                }
            }
        }

        sb.append("<").append(tagName);

        // attributes من getAttributes() — مع حل الـ href
        if (tag.getAttributes() != null) {
            for (HtmlAttribute attr : tag.getAttributes()) {
                renderAttribute(attr, sb);
            }
        }

        // attributes إضافية من Node.children
        if (tag.getChildrenNodes() != null) {
            for (Node child : tag.getChildrenNodes()) {
                if (child instanceof HtmlAttribute) {
                    HtmlAttribute attr = (HtmlAttribute) child;
                    if (!isAttributeAlreadyAdded(tag, attr)) {
                        renderAttribute(attr, sb);
                    }
                }
            }
        }

        if (VOID_TAGS.contains(tagName.toLowerCase())) {
            sb.append(">\n");
            return;
        }

        sb.append(">\n");

        // children (بدون HtmlAttribute)
        for (Node child : tag.getChildrenNodes()) {
            if (child instanceof HtmlAttribute) continue;
            renderNode(child, sb);
        }

        sb.append("</").append(tagName).append(">\n");
    }

    private boolean isAttributeAlreadyAdded(HtmlTag tag, HtmlAttribute attr) {
        if (tag.getAttributes() == null) return false;
        for (HtmlAttribute existing : tag.getAttributes()) {
            if (existing.getAttributeName().equals(attr.getAttributeName())) {
                return true;
            }
        }
        return false;
    }

    // ================================================================== //
    //  5. HTML Attribute — مع resolveRoutePath                          //
    // ================================================================== //

    private void renderAttribute(HtmlAttribute attr, StringBuilder sb) {
        String name  = attr.getAttributeName();
        String value = attr.getAttributeValue();
        if (name == null || name.isBlank()) return;

        sb.append(" ").append(name);

        if (value != null && !value.isBlank()) {
            // ── تحويل href routes إلى أسماء ملفات HTML ─────────────── //
            String resolved = name.equalsIgnoreCase("href")
                    ? resolveRoutePath(value)
                    : resolveAttributeValue(value);

            sb.append("=\"").append(resolved).append("\"");
        }
    }

    // ================================================================== //
    //  ⭐ resolveRoutePath — يقرأ من routeToTemplate في context          //
    // ================================================================== //

    /**
     * resolveRoutePath()
     * ──────────────────
     * يُحوّل route path في الـ href إلى اسم ملف HTML مُولَّد.
     *
     * يعتمد على:
     *   context.getRouteToTemplate()  ← مُملوء من ContextBuilder
     *   يقرأ app.py ويستخرج:
     *     @app.route("/")         → render_template("index.html")
     *     @app.route("/add")      → render_template("add_product.html")
     *     @app.route("/go-home")  → redirect → يُحوَّل لـ index.html
     *     @app.route("/product/<int:product_id>") → render_template("product_detail.html")
     *
     * الخطوات:
     * ─────────────────────────────────────────────────────────────
     * 1. روابط خارجية / static / anchor → لا تغيير
     * 2. href يحتوي {{ }} → حلّ Jinja أولاً ثم أعد التحويل
     * 3. بحث مباشر في routeToTemplate
     *    مثال: "/add" → "add_product.html"
     * 4. بحث بـ pattern مع routes التي تحتوي <type:name>
     *    مثال: "/product/1" → يطابق "/product/<int:product_id>"
     *          → يجد templateName = "product_detail.html"
     * 5. Fallback: يولّد اسم ملف من الـ path مباشرة
     *    مثال: "/contact" → "contact.html"
     */
    private String resolveRoutePath(String href) {
        if (href == null || href.isBlank()) return href;

        // ── 1. روابط لا تُعدَّل ──────────────────────────────────────── //
        if (href.startsWith("http://") || href.startsWith("https://"))
            return href;
        if (href.startsWith("#"))
            return href;
        if (href.startsWith("/static/"))
            return href;

        // ── 2. يحتوي Jinja {{ }} → حلّه أولاً ──────────────────────── //
        if (href.contains("{{")) {
            String resolved = resolveAttributeValue(href);
            return resolveRoutePath(resolved); // recursive بعد الحل
        }

        context.addLog("resolveRoutePath: '" + href + "'");

        // ── 3. بحث مباشر في routeToTemplate ─────────────────────────── //
        // routeToTemplate مُملوء من ContextBuilder بقراءة app.py:
        //   route "/" → render_template("index.html")         → {"/": "index.html"}
        //   route "/add" → render_template("add_product.html") → {"/add": "add_product.html"}
        Map<String, String> routeToTemplate = context.getRouteToTemplate();

        if (routeToTemplate.containsKey(href)) {
            String templateFile = routeToTemplate.get(href);
            context.addLog("  found in routeToTemplate: " + templateFile);
            return templateFile;
        }

        // ── 4. بحث بـ pattern في routes التي تحتوي <type:name> ──────── //
        // مثال: href="/product/1"
        //        route="/product/<int:product_id>" → prefix="/product/"
        //        يطابق → templateFile="product_detail.html"
        for (Map.Entry<String, String> entry : routeToTemplate.entrySet()) {
            String routePattern  = entry.getKey();   // "/product/<int:product_id>"
            String templateFile  = entry.getValue(); // "product_detail.html"

            if (routePattern.contains("<")) {
                // استخرج الجزء الثابت قبل الـ <
                String prefix = routePattern.substring(0, routePattern.indexOf('<'));
                if (href.startsWith(prefix) && !prefix.isBlank()) {
                    context.addLog("  pattern match: " + routePattern
                            + " → " + templateFile);
                    return templateFile;
                }
            }
        }

        // ── 5. Fallback: هل الـ routes تحتوي الـ path؟ ───────────────── //
        // ابحث في routes (route→funcName) ثم استنتج اسم الملف من funcName
        Map<String, String> routes = context.getRoutes();
        if (routes.containsKey(href)) {
            String funcName = routes.get(href);
            // هذه الدالة لا تُرجع template — تُرجع redirect
            // نبحث في routeToTemplate عن الـ route الذي تُحيل إليه
            // مثال: go_home → redirect إلى "/" → index.html
            String redirectTarget = findRedirectTarget(funcName, routeToTemplate);
            if (redirectTarget != null) {
                context.addLog("  redirect: " + funcName
                        + " → " + redirectTarget);
                return redirectTarget;
            }
        }

        // ── 6. Fallback نهائي: توليد اسم ملف من الـ path ───────────── //
        // مثال: "/contact"      → "contact.html"
        //        "/about-us"    → "about_us.html"
        //        "/blog/post/1" → "blog_post.html"
        String fallback = pathToFilename(href);
        context.addWarning("resolveRoutePath: no route found for '"
                + href + "' — fallback to '" + fallback + "'");
        return fallback;
    }

    /**
     * findRedirectTarget()
     * ─────────────────────
     * يبحث عن الـ template التي تُرجعها دالة redirect.
     *
     * مثال:
     *   go_home → redirect(url_for("home"))
     *   → "home" لها route "/" → routeToTemplate["/"] = "index.html"
     *   → يُرجع "index.html"
     *
     * يبحث في routeToTemplate عن أي ملف اسمه يبدأ بـ "index"
     * لأن go_home عادةً تُعيد للصفحة الرئيسية.
     */
    private String findRedirectTarget(String funcName,
                                      Map<String, String> routeToTemplate) {
        // الدوال التي تُعيد للصفحة الرئيسية
        if (funcName != null && (funcName.contains("home")
                || funcName.contains("index"))) {
            // ابحث عن "/" في routeToTemplate
            if (routeToTemplate.containsKey("/")) {
                return routeToTemplate.get("/");
            }
            // أو أي ملف اسمه index
            for (String tmpl : routeToTemplate.values()) {
                if (tmpl.startsWith("index")) return tmpl;
            }
        }
        return null;
    }

    /**
     * pathToFilename()
     * ─────────────────
     * يُحوّل أي route path إلى اسم ملف HTML.
     *
     * القواعد:
     *   "/"           → "index.html"
     *   "/add"        → "add.html"
     *   "/go-home"    → "go_home.html"
     *   "/product/1"  → "product.html"
     *   "/a/b/c"      → "a_b.html" (يأخذ أول جزأين)
     */
    private String pathToFilename(String path) {
        if (path == null || path.equals("/")) return "index.html";

        // أزل الـ / الأولى
        String clean = path.replaceFirst("^/", "");

        // أزل الأجزاء الديناميكية (أرقام وما بعد /)
        String[] segments = clean.split("/");
        String base = segments[0]; // خذ الجزء الأول فقط

        // حوّل - إلى _
        base = base.replace("-", "_");

        return base.isEmpty() ? "index.html" : base + ".html";
    }

    // ================================================================== //
    //  resolveAttributeValue                                             //
    // ================================================================== //

    private String resolveAttributeValue(String value) {
        if (!value.contains("{{")) return value;

        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < value.length()) {
            int start = value.indexOf("{{", i);
            if (start == -1) { result.append(value.substring(i)); break; }
            result.append(value, i, start);
            int end = value.indexOf("}}", start);
            if (end == -1) { result.append(value.substring(start)); break; }
            String expr     = value.substring(start + 2, end).trim();
            Object resolved = context.resolveVariable(expr);
            result.append(resolved != null ? resolved.toString() : "");
            i = end + 2;
        }
        return result.toString();
    }

    // ================================================================== //
    //  HtmlText                                                           //
    // ================================================================== //

    private void renderHtmlText(HtmlText text, StringBuilder sb) {
        String t = text.getText();
        if (t != null) sb.append(t);
    }

    // ================================================================== //
    //  CSS                                                                //
    // ================================================================== //

    private void renderCssStyle(CSS_Style style, StringBuilder sb) {
        sb.append("<style>\n");
        if (style.getRuleSets() != null) {
            for (CSSRuleSet rule : style.getRuleSets()) {
                renderCssRuleSet(rule, sb);
            }
        }
        for (Node child : style.getChildrenNodes()) {
            if (child instanceof CSSRuleSet) {
                renderCssRuleSet((CSSRuleSet) child, sb);
            }
        }
        sb.append("</style>\n");
    }

    private void renderCssRuleSet(CSSRuleSet rule, StringBuilder sb) {
        sb.append(rule.getSelector()).append(" {\n");
        if (rule.getProperties() != null) {
            for (CssProperty prop : rule.getProperties()) {
                sb.append("  ").append(prop.getProperty())
                        .append(": ").append(prop.getValue()).append(";\n");
            }
        }
        sb.append("}\n");
    }

    // ================================================================== //
    //  extractForExpression / extractIfCondition                         //
    // ================================================================== //

    private String extractForExpression(JinjaForBlock node) {
        for (Node child : node.getChildrenNodes()) {
            if (child instanceof JinjaTag) {
                String content = ((JinjaTag) child).getTagContent();
                if (content != null) {
                    String expr = content.trim()
                            .replace("{%","").replace("%}","").trim();
                    if (expr.toLowerCase().startsWith("for ")) {
                        return expr.substring(4).trim();
                    }
                }
            }
        }
        String nodeName = node.getName();
        if (nodeName != null && nodeName.toLowerCase().contains("for ")) {
            String clean = nodeName.replace("JinjaForBlock","").trim();
            if (!clean.isEmpty()) return clean;
        }
        context.addLog("extractForExpression: using default 'product in products'");
        return "product in products";
    }

    private String extractIfCondition(JinjaIfBlock node) {
        for (Node child : node.getChildrenNodes()) {
            if (child instanceof JinjaTag) {
                String content = ((JinjaTag) child).getTagContent();
                if (content != null) {
                    String expr = content.trim()
                            .replace("{%","").replace("%}","").trim();
                    if (expr.toLowerCase().startsWith("if ")) {
                        return expr.substring(3).trim();
                    }
                }
            }
        }
        String nodeName = node.getName();
        if (nodeName != null && nodeName.toLowerCase().contains("if ")) {
            String clean = nodeName.replace("JinjaIfBlock","").trim();
            if (!clean.isEmpty()) return clean;
        }
        context.addLog("extractIfCondition: using default 'products'");
        return "products";
    }

    // ================================================================== //
    //  Helpers                                                            //
    // ================================================================== //

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&","&amp;").replace("<","&lt;")
                .replace(">","&gt;").replace("\"","&quot;");
    }

    private double toDouble(String s) {
        try { return Double.parseDouble(s.trim()); }
        catch (NumberFormatException e) { return 0; }
    }
}