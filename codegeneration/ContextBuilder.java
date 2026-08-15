package codegeneration;

import AST.*;
import AST_H_C.Node;

import java.io.File;
import java.util.*;

/**
 * ContextBuilder
 * ==============
 * يقرأ Python AST ويملأ GenerationContext بالبيانات اللازمة للتوليد.
 */
public class ContextBuilder {

    public ContextBuilder() {
        // لا يحتاج إعداد مسبق
    }

    // ================================================================== //
    //  Entry Point                                                        //
    // ================================================================== //

    public GenerationContext build(AstNode pythonRoot,
                                   Node    htmlRoot,
                                   String  htmlFileName) {

        GenerationContext ctx = new GenerationContext();

        ctx.addLog("=== ContextBuilder started ===");

        // ── خطوة 1: استخرج بيانات Python AST ────────────────────────── //
        if (pythonRoot != null) {
            ctx.addLog("Reading Python AST...");
            extractPythonData(pythonRoot, ctx);
            ctx.addLog("Python AST reading complete.");
        } else {
            ctx.addWarning("Python AST is null — skipping Python data extraction");
        }

        // ── خطوة 2: سجّل HTML/Jinja template ────────────────────────── //
        if (htmlRoot != null && htmlFileName != null) {
            String name = new File(htmlFileName).getName(); // "index.html"
            ctx.addTemplate(name, htmlRoot);
            ctx.addLog("Registered template: " + name);
        } else {
            ctx.addWarning("HTML root is null — no template registered");
        }


        ctx.addLog("=== ContextBuilder finished ===");
        ctx.addLog("Global variables: " + ctx.getGlobalVariables().keySet());
        ctx.addLog("Routes: " + ctx.getRoutes());
        ctx.addLog("Templates: " + ctx.getTemplates().keySet());

        return ctx;
    }

    // ================================================================== //
    //  استخراج بيانات Python AST                                        //
    // ================================================================== //

    private void extractPythonData(AstNode root, GenerationContext ctx) {
        for (AstNode node : root.getChildren()) {
            if (node instanceof FunctionDef) {
                extractFromFunctionDef((FunctionDef) node, ctx);
            } else if (node instanceof Assign) {
                extractFromAssign((Assign) node, ctx);
            }
        }
    }

    // ================================================================== //
    //  معالجة Assign nodes                                               //
    // ================================================================== //

    private void extractFromAssign(Assign node, GenerationContext ctx) {
        AstNode left  = node.getLeft();
        AstNode right = node.getChildren().size() > 1
                ? node.getChildren().get(1) : null;

        if (!(left instanceof Identifier)) return;
        if (right == null) return;

        String varName = ((Identifier) left).getName();

        // ── String literal ────────────────────────────────────────────── //
        if (right instanceof StringLiteral) {
            String value = ((StringLiteral) right).getValue();
            ctx.addGlobalVariable(varName, value);
            ctx.addLog("Extracted String variable: " + varName + " = \"" + value + "\"");
        }

        // ── Number literal ────────────────────────────────────────────── //
        else if (right instanceof NumberLiteral) {
            String raw = ((NumberLiteral) right).getValue();
            ctx.addGlobalVariable(varName, parseNumber(raw));
            ctx.addLog("Extracted Number variable: " + varName + " = " + raw);
        }

        // ── List literal: products = [{...}, {...}] ───────────────────── //
        else if (right instanceof ListLiteral) {
            List<Map<String, Object>> list = extractListLiteral(
                    (ListLiteral) right, ctx);
            ctx.addGlobalVariable(varName, list);
            ctx.addLog("Extracted List variable: " + varName
                    + " (" + list.size() + " items)");

            for (int i = 0; i < list.size(); i++) {
                ctx.addLog("  [" + i + "] = " + list.get(i));
            }
        }

        // ── Boolean ───────────────────────────────────────────────────── //
        else if (right instanceof BooleanLiteral) {
            boolean value = ((BooleanLiteral) right).getValue();
            ctx.addGlobalVariable(varName, value);
            ctx.addLog("Extracted Boolean variable: " + varName + " = " + value);
        }

        // ── غير ذلك: تجاهل ───────────────────────────────────────────── //
        else {
            ctx.addLog("Skipped assignment: " + varName
                    + " (RHS type: " + right.getClass().getSimpleName() + ")");
        }
    }

    // ================================================================== //
    //  ⭐ استخراج ListLiteral — المُصحَّح للتعامل مع ListLiteral المتداخلة //
    // ================================================================== //

    private List<Map<String, Object>> extractListLiteral(ListLiteral node,
                                                         GenerationContext ctx) {
        List<Map<String, Object>> result = new ArrayList<>();

        ctx.addLog("  extractListLiteral: processing node with "
                + node.getChildren().size() + " children");

        for (AstNode child : node.getChildren()) {
            if (child instanceof ListLiteral) {
                ctx.addLog("  extractListLiteral: nested ListLiteral found — recursing");
                result.addAll(extractListLiteral((ListLiteral) child, ctx));
            }
            else if (child instanceof DictLiteral) {
                Map<String, Object> map = extractDictLiteral(
                        (DictLiteral) child, ctx);
                if (!map.isEmpty()) {
                    // ⭐ أضف سجل لكل منتج مستخرج
                    ctx.addLog("  Extracted product: " + map);
                    result.add(map);
                }
            }
            else {
                ctx.addLog("  extractListLiteral: skipping child of type "
                        + child.getClass().getSimpleName());
            }
        }

        ctx.addLog("  extractListLiteral: extracted " + result.size() + " items");
        return result;
    }
    // ================================================================== //
    //  استخراج DictLiteral                                               //
    // ================================================================== //

    private Map<String, Object> extractDictLiteral(DictLiteral node,
                                                   GenerationContext ctx) {
        Map<String, Object> map = new LinkedHashMap<>();

        for (AstNode child : node.getChildren()) {
            // كل pair مخزون كـ "Entry" node مع childين
            if (child.getChildren().size() >= 2) {
                AstNode keyNode   = child.getChildren().get(0);
                AstNode valueNode = child.getChildren().get(1);

                String key = extractKeyAsString(keyNode);
                Object value = extractValue(valueNode, ctx);

                if (key != null) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    // ================================================================== //
    //  Helper: استخراج key من Dict                                       //
    // ================================================================== //

    private String extractKeyAsString(AstNode node) {
        if (node instanceof StringLiteral) {
            return ((StringLiteral) node).getValue();
        }
        if (node instanceof Identifier) {
            return ((Identifier) node).getName();
        }
        return null;
    }

    // ================================================================== //
    //  Helper: استخراج value من Dict                                     //
    // ================================================================== //

    private Object extractValue(AstNode node, GenerationContext ctx) {
        if (node instanceof StringLiteral) {
            return ((StringLiteral) node).getValue();
        }
        if (node instanceof NumberLiteral) {
            return parseNumber(((NumberLiteral) node).getValue());
        }
        if (node instanceof BooleanLiteral) {
            return ((BooleanLiteral) node).getValue();
        }
        if (node instanceof Identifier) {
            String idName = ((Identifier) node).getName();
            Object found  = ctx.getGlobalVariables().get(idName);
            if (found != null) return found;
            return idName;
        }
        if (node instanceof NoneLiteral) {
            return null;
        }
        return "";
    }

    // ================================================================== //
    //  معالجة FunctionDef nodes                                          //
    // ================================================================== //

    private void extractFromFunctionDef(FunctionDef func,
                                        GenerationContext ctx) {
        String funcName = func.getName();
        ctx.addLog("Processing function: " + funcName + "()");

        // ── خطوة 1: ابحث عن @app.route decorator ────────────────────── //
        String routePath = extractRouteFromDecorators(func, ctx);
        if (routePath != null) {
            ctx.addRoute(routePath, funcName);
            ctx.addLog("  Route: " + routePath + " → " + funcName + "()");
        }

        // ── خطوة 2: ابحث عن render_template() في الـ body ───────────── //
        // نحتاج routePath لربطه مع templateName لاحقاً
        extractRenderTemplateCalls(func, funcName, routePath, ctx);
    }

    // ================================================================== //
    //  استخراج Route من Decorator                                        //
    // ================================================================== //

    private String extractRouteFromDecorators(FunctionDef func,
                                              GenerationContext ctx) {
        for (AstNode child : func.getChildren()) {
            if (!(child instanceof Decorator)) continue;

            Decorator dec  = (Decorator) child;
            String    name = dec.getName();

            if (name == null || !name.contains("route")) continue;

            for (AstNode arg : dec.getChildren()) {
                if (arg instanceof StringLiteral) {
                    return ((StringLiteral) arg).getValue();
                }
            }
        }
        return null;
    }

    // ================================================================== //
    //  استخراج render_template() calls                                   //
    // ================================================================== //

//    private void extractRenderTemplateCalls(AstNode node,
//                                            String  funcName,
//                                            String  routePath,
//                                            GenerationContext ctx) {
//        for (AstNode child : node.getChildren()) {
//
//            if (child instanceof ReturnStatement) {
//                for (AstNode retChild : child.getChildren()) {
//                    if (retChild instanceof FunctionCall
//                            && isRenderTemplate((FunctionCall) retChild)) {
//                        parseRenderTemplateCall(
//                                (FunctionCall) retChild, funcName, routePath, ctx);
//                    }
//                }
//            }
//
//            extractRenderTemplateCalls(child, funcName, routePath, ctx);
//        }
//    }
private void extractRenderTemplateCalls(AstNode node,
                                        String funcName,
                                        String routePath,
                                        GenerationContext ctx) {
    for (AstNode child : node.getChildren()) {

        // ⭐ معالجة ReturnStatement
        if (child instanceof ReturnStatement) {
            for (AstNode retChild : child.getChildren()) {
                if (retChild instanceof FunctionCall && isRenderTemplate((FunctionCall) retChild)) {
                    parseRenderTemplateCall((FunctionCall) retChild, funcName, routePath, ctx);
                }
            }
        }

        // ⭐ معالجة If statement (داخل For loop)
        if (child instanceof IfStatement) {
            for (AstNode ifChild : child.getChildren()) {
                if (ifChild instanceof ReturnStatement) {
                    for (AstNode retChild : ifChild.getChildren()) {
                        if (retChild instanceof FunctionCall && isRenderTemplate((FunctionCall) retChild)) {
                            parseRenderTemplateCall((FunctionCall) retChild, funcName, routePath, ctx);
                        }
                    }
                }
            }
        }

        // ⭐ استمرار البحث في باقي الأطفال
        extractRenderTemplateCalls(child, funcName, routePath, ctx);
    }
}

    private boolean isRenderTemplate(FunctionCall call) {
        if (call.getChildren().isEmpty()) return false;
        AstNode callee = call.getChildren().get(0);
        return callee instanceof Identifier
                && "render_template".equals(((Identifier) callee).getName());
    }

    private void parseRenderTemplateCall(FunctionCall call,
                                         String       funcName,
                                         String       routePath,
                                         GenerationContext ctx) {
        List<AstNode> args = new ArrayList<>();
        for (int i = 1; i < call.getChildren().size(); i++) {
            args.add(call.getChildren().get(i));
        }

        if (args.isEmpty()) return;

        String templateName = null;
        if (args.get(0) instanceof StringLiteral) {
            templateName = ((StringLiteral) args.get(0)).getValue();
        }
        if (templateName == null) {
            ctx.addWarning("render_template() in " + funcName
                    + "() — could not extract template name");
            return;
        }

        ctx.addLog("  render_template(\"" + templateName + "\") found in "
                + funcName + "()");

        // ── ⭐ سجّل الربط المباشر: route → template ─────────────────── //
        // هذا ما يُستخدمه JinjaRenderer.resolveRoutePath() لاحقاً
        // مثال: routePath="/add" → templateName="add_product.html"
        if (routePath != null) {
            ctx.addRouteToTemplate(routePath, templateName);
            ctx.addLog("  RouteToTemplate: " + routePath + " → " + templateName);
        }

        Map<String, String> vars = new LinkedHashMap<>();
        for (int i = 1; i < args.size(); i++) {
            AstNode arg = args.get(i);
            if (arg instanceof KeywordArgument) {
                KeywordArgument kw = (KeywordArgument) arg;
                String kwName = kw.getKey();
                String kwSource = extractKeyAsString(kw.getValue());
                if (kwName != null && kwSource != null) {
                    vars.put(kwName, kwSource);
                    ctx.addLog("    var: " + kwName + " = " + kwSource);
                }
            }
        }

        ctx.addTemplateVariables(templateName, vars);

        // ⭐ المعالجة الأساسية: ربط المتغيرات
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            String jinjaName = entry.getKey();     // "product" أو "products"
            String pythonName = entry.getValue();  // "product" أو "products"

            if (pythonName != null) {
                Object pythonVal = ctx.getGlobalVariables().get(pythonName);


                if (pythonVal != null && !ctx.getGlobalVariables().containsKey(jinjaName)) {
                    ctx.addGlobalVariable(jinjaName, pythonVal);
                    ctx.addLog("    mapped: " + jinjaName + " → " + pythonName);
                }
            }
        }
    }
    // ================================================================== //
    //  Helper: parseNumber                                                //
    // ================================================================== //

    private Object parseNumber(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e1) {
            try {
                return Double.parseDouble(raw);
            } catch (NumberFormatException e2) {
                return raw;
            }
        }
    }

}