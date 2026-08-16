package codegeneration;

import AST.AstNode;
import AST_H_C.Node;
import semantic.SemanticError;

import java.util.*;

/**
 * Generator
 * =========
 * المنسّق الرئيسي لمرحلة Code Generation.
 */
public class Generator {

    // ================================================================== //
    //  Entry Point                                                        //
    // ================================================================== //

    /**
     * generate()
     * ──────────
     * نقطة الدخول الرئيسية.
     * يُستدعى من Main.java بعد انتهاء Semantic Analysis.
     *
     * @param htmlRoots      قائمة بشجر HTML/Jinja AST مع أسمائها
     * @param pythonRoot     شجرة Python AST من PythonVisitor
     * @param supportFiles   ملفات داعمة تُنسخ إلى output/
     * @param semanticPassed هل نجحت Semantic Analysis؟
     * @param semanticReport نص تقرير Semantic Analysis
     */
    /**
     * generateWithErrors()
     * ─────────────────────
     * نفس generate() ولكن مع تمرير قائمة الأخطاء semantic لكتابتها في التقرير.
     */
    public void generateWithErrors(Map<String, Node> htmlRoots,
                                   AstNode pythonRoot,
                                   List<String> supportFiles,
                                   boolean semanticPassed,
                                   String semanticReport,
                                   List<SemanticError> semanticErrors) {

        printHeader();

        // ── CHECK: حتى لو فشلت Semantic، نكتب التقارير ────────────── //
        if (!semanticPassed) {
            System.err.println("[Generator] ✗ Semantic Analysis FAILED");
            System.err.println("[Generator]   Reason: " + semanticReport);
            System.err.println("[Generator]   Writing reports to compiler_output/ for debugging...\n");

            GenerationContext ctx = new GenerationContext();
            ctx.addWarning("Semantic Analysis failed: " + semanticReport);

            for (SemanticError err : semanticErrors) {
                ctx.addLog("[SEMANTIC ERROR] " + err.getMessage());
            }

            for (Map.Entry<String, Node> entry : htmlRoots.entrySet()) {
                String templateName = entry.getKey();
                Node htmlRoot = entry.getValue();

                ctx.addLog("Processing template: " + templateName);
                ctx.addTemplate(templateName, htmlRoot);

                if (htmlRoot != null) {
                    try {
                        JinjaRenderer renderer = new JinjaRenderer(ctx);
                        String html = renderer.render(htmlRoot);
                        ctx.addOutput(templateName, html);
                        System.out.println("  ✓ Partial HTML generated for: " + templateName);
                    } catch (Exception e) {
                        ctx.addWarning("Could not render HTML for " + templateName + ": " + e.getMessage());
                    }
                }
            }

            ASTJsonSerializer serializer = new ASTJsonSerializer();
            String pythonJson = "{}";
            String jinjaJson = "{}";

            if (pythonRoot != null) {
                pythonJson = serializer.serializePython(pythonRoot);
            }
            if (!htmlRoots.isEmpty()) {
                Node firstHtml = htmlRoots.values().iterator().next();
                if (firstHtml != null) {
                    jinjaJson = serializer.serializeJinja(firstHtml);
                }
            }

//            String fullReport = buildFailureReportWithErrors(semanticReport, semanticErrors, pythonRoot, htmlRoots, ctx);

            OutputWriter writer = new OutputWriter(ctx);
            writer.writeAllWithErrors(pythonJson, jinjaJson, semanticReport, supportFiles, semanticErrors);

            System.out.println("\n" + "=".repeat(60));
            System.out.println("   GENERATION FAILED - Semantic Errors");
            System.out.println("   compiler_output/semantic_report.txt - Error details");
            System.out.println("   compiler_output/generation_log.txt  - Full log");
            System.out.println("   compiler_output/ast_python.json     - Python AST");
            System.out.println("   compiler_output/ast_jinja.json      - Jinja AST");
            System.out.println("=".repeat(60));
            return;
        }

        // ── Semantic نجحت ─────────────────────────────────────────── //
        System.out.println("[Generator] ✓ Semantic Analysis passed");
        System.out.println("[Generator] Starting code generation...\n");

        System.out.println("[Step 1/4] Building GenerationContext...");

        GenerationContext ctx = new GenerationContext();

        for (Map.Entry<String, Node> entry : htmlRoots.entrySet()) {
            String templateName = entry.getKey();
            Node htmlRoot = entry.getValue();

            ctx.addLog("Processing template: " + templateName);
            ctx.addTemplate(templateName, htmlRoot);

            if (pythonRoot != null) {
                ContextBuilder builder = new ContextBuilder();
                GenerationContext partialCtx = builder.build(pythonRoot, htmlRoot, templateName);
                mergeContexts(ctx, partialCtx);
            }
        }

        System.out.println("  ✓ Routes found      : " + ctx.getRoutes().size());
        System.out.println("  ✓ Templates found   : " + ctx.getTemplates().size());
        System.out.println("  ✓ Global variables  : " + ctx.getGlobalVariables().keySet());

        Object products = ctx.getGlobalVariables().get("products");
        if (products instanceof List) {
            System.out.println("  ✓ Products count    : " + ((List<?>) products).size());
        }

        System.out.println();

        System.out.println("[Step 2/4] Rendering templates (Jinja → HTML)...");

        JinjaRenderer renderer = new JinjaRenderer(ctx);

        if (ctx.getTemplates().isEmpty()) {
            ctx.addWarning("No templates to render");
            System.out.println("  ⚠ No templates found");
        } else {
            for (Map.Entry<String, Node> entry : ctx.getTemplates().entrySet()) {
                String templateName = entry.getKey();
                Node templateRoot = entry.getValue();

                Map<String, String> vars = ctx.getTemplateVariables().get(templateName);
                String perItemVar = null, perItemSource = null;

                if (vars != null) {
                    for (Map.Entry<String, String> v : vars.entrySet()) {
                        String jinjaName = v.getKey(), pythonName = v.getValue();
                        if (ctx.getGlobalVariables().get(pythonName) == null) {
                            Object candidateList = ctx.getGlobalVariables().get(pythonName + "s");
                            if (candidateList instanceof List) {
                                perItemVar = jinjaName;
                                perItemSource = pythonName + "s";
                                break;
                            }
                        }
                    }
                }

                if (perItemVar != null) {
                    List<?> items = (List<?>) ctx.getGlobalVariables().get(perItemSource);
                    String base = templateName.replace(".html", "");
                    for (Object item : items) {
                        ctx.addGlobalVariable(perItemVar, item);
                        String html = renderer.render(templateRoot);
                        Object idVal = (item instanceof Map) ? ((Map<?, ?>) item).get("id") : null;
                        String outKey = base + "_" + idVal + ".html";
                        ctx.addOutput(outKey, html);
                        System.out.println("  ✓ " + outKey + " → " + html.length() + " chars generated");
                    }
                    ctx.getGlobalVariables().remove(perItemVar);
                } else {
                    String html = renderer.render(templateRoot);
                    ctx.addOutput(templateName, html);
                    System.out.println("  ✓ " + templateName + " → " + html.length() + " chars generated");
                }
            }
        }

        System.out.println();

        System.out.println("[Step 3/4] Serializing ASTs to JSON...");

        ASTJsonSerializer serializer = new ASTJsonSerializer();

        String pythonJson = "{}";
        if (pythonRoot != null) {
            pythonJson = serializer.serializePython(pythonRoot);
            System.out.println("  ✓ Python AST serialized (" + pythonJson.length() + " chars)");
        } else {
            System.out.println("  ⚠ Python AST is null — skipped");
        }

        String jinjaJson = "{}";
        if (!htmlRoots.isEmpty()) {
            Node firstHtml = htmlRoots.values().iterator().next();
            if (firstHtml != null) {
                jinjaJson = serializer.serializeJinja(firstHtml);
                System.out.println("  ✓ Jinja AST serialized  (" + jinjaJson.length() + " chars)");
            }
        } else {
            System.out.println("  ⚠ Jinja AST is null — skipped");
        }

        System.out.println();

        System.out.println("[Step 4/4] Writing output files...");

        ctx.addLog("=== OutputWriter started ===");

        OutputWriter writer = new OutputWriter(ctx);
        writer.writeAll(pythonJson, jinjaJson, semanticReport, supportFiles);

        System.out.println("  ✓ output/            — HTML files written");
        System.out.println("  ✓ compiler_output/   — JSON + log files written");

        printSummary(ctx);
    }
    // ================================================================== //
    //  دالة مساعدة لدمج Contexts                                         //
    // ================================================================== //

    /**
     * mergeContexts()
     * ────────────────
     * يدمج بيانات من Context جزئي إلى Context رئيسي.
     */
    private void mergeContexts(GenerationContext target, GenerationContext source) {
        // دمج المتغيرات العالمية
        for (Map.Entry<String, Object> entry : source.getGlobalVariables().entrySet()) {
            if (!target.getGlobalVariables().containsKey(entry.getKey())) {
                target.addGlobalVariable(entry.getKey(), entry.getValue());
            }
        }

        // دمج الـ routes
        for (Map.Entry<String, String> entry : source.getRoutes().entrySet()) {
            if (!target.getRoutes().containsKey(entry.getKey())) {
                target.addRoute(entry.getKey(), entry.getValue());
            }
        }

        // دمج routeToTemplate
        for (Map.Entry<String, String> entry : source.getRouteToTemplate().entrySet()) {
            if (!target.getRouteToTemplate().containsKey(entry.getKey())) {
                target.addRouteToTemplate(entry.getKey(), entry.getValue());
            }
        }

        // دمج templateVariables
        for (Map.Entry<String, Map<String, String>> entry : source.getTemplateVariables().entrySet()) {
            if (!target.getTemplateVariables().containsKey(entry.getKey())) {
                target.addTemplateVariables(entry.getKey(), entry.getValue());
            }
        }
    }

    // ================================================================== //
    //  دالة مساعدة لبناء تقرير الفشل                                     //
    // ================================================================== //

    /**
     * buildFailureReport()
     * ─────────────────────
     * يبني تقريراً مفصلاً عند فشل Semantic Analysis.
     */
    private String buildFailureReport(String semanticReport,
                                      AstNode pythonRoot,
                                      Map<String, Node> htmlRoots,
                                      GenerationContext ctx) {
        StringBuilder sb = new StringBuilder();

        sb.append("=".repeat(60)).append("\n");
        sb.append(" SEMANTIC ANALYSIS FAILED\n");
        sb.append("=".repeat(60)).append("\n\n");

        // سبب الفشل
        sb.append("ERROR MESSAGE:\n");
        sb.append("-".repeat(60)).append("\n");
        sb.append(semanticReport).append("\n\n");

//        // Python AST
//        sb.append("=".repeat(60)).append("\n");
//        sb.append("PYTHON AST\n");
//        sb.append("=".repeat(60)).append("\n");
//        if (pythonRoot != null) {
//            sb.append(pythonRoot.toString()).append("\n");
//        } else {
//            sb.append("(null - Python AST not available)\n");
//        }
//        sb.append("\n");
//
//        // HTML/Jinja ASTs
//        sb.append("=".repeat(60)).append("\n");
//        sb.append("HTML/JINJA ASTS (").append(htmlRoots.size()).append(" templates)\n");
//        sb.append("=".repeat(60)).append("\n");
//        for (Map.Entry<String, Node> entry : htmlRoots.entrySet()) {
//            sb.append("--- ").append(entry.getKey()).append(" ---\n");
//            if (entry.getValue() != null) {
//                sb.append(entry.getValue().toString()).append("\n");
//            } else {
//                sb.append("(null)\n");
//            }
//            sb.append("\n");
//        }

        // التحذيرات
        sb.append("=".repeat(60)).append("\n");
        sb.append("WARNINGS\n");
        sb.append("=".repeat(60)).append("\n");
        List<String> warnings = ctx.getWarnings();
        if (warnings.isEmpty()) {
            sb.append("No warnings.\n");
        } else {
            for (String w : warnings) {
                sb.append("  ").append(w).append("\n");
            }
        }
        sb.append("\n");

        // الخطوات التالية
        sb.append("=".repeat(60)).append("\n");
        sb.append("NEXT STEPS\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append("1. Fix all semantic errors listed above\n");
        sb.append("2. Re-run the compiler\n");
        sb.append("3. Check output/ for generated HTML files\n");
        sb.append("=".repeat(60)).append("\n");

        return sb.toString();
    }

    // ================================================================== //
    //  طباعة Header                                                      //
    // ================================================================== //

    private void printHeader() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("       CODE GENERATION PHASE");
        System.out.println("=".repeat(60));
    }

    // ================================================================== //
    //  طباعة Summary                                                     //
    // ================================================================== //

    /**
     * printSummary()
     * ───────────────
     * يطبع ملخص نهائي بعد انتهاء التوليد.
     */
    private void printSummary(GenerationContext ctx) {
        int htmlCount = ctx.getOutputHtml().size();
        int warnCount = ctx.getWarnings().size();
        int routeCount = ctx.getRoutes().size();

        System.out.println();
        System.out.println("=".repeat(60));

        if (warnCount == 0) {
            System.out.println("       GENERATION COMPLETE ✓");
        } else {
            System.out.println("       GENERATION COMPLETE  (with warnings)");
        }

        System.out.println("=".repeat(60));
        System.out.println("  HTML files generated : " + htmlCount);
        System.out.println("  Routes found         : " + routeCount);
        System.out.println("  Warnings             : " + warnCount);
        System.out.println();

        // ── قائمة output files ────────────────────────────────────────── //
        System.out.println("  output/");
        if (ctx.getOutputHtml().isEmpty()) {
            System.out.println("    (empty)");
        } else {
            for (Map.Entry<String, String> e : ctx.getOutputHtml().entrySet()) {
                long chars = e.getValue() != null ? e.getValue().length() : 0;
                System.out.printf("    %-30s (%d chars)%n",
                        e.getKey(), chars);
            }
        }
        System.out.println();

        // ── قائمة compiler_output files ──────────────────────────────── //
        System.out.println("compiler_output/");
        System.out.println("    ast_python.json");
        System.out.println("    ast_jinja.json");
        System.out.println("    semantic_report.txt");
        System.out.println("    generation_log.txt");
        System.out.println();

        // ── Warnings إذا وجدت ────────────────────────────────────────── //
        if (warnCount > 0) {
            System.out.println("  WARNINGS:");
            for (String w : ctx.getWarnings()) {
                System.out.println("    " + w);
            }
            System.out.println();
        }

        System.out.println("=".repeat(60));
    }
    private String buildFailureReportWithErrors(String semanticReport,
                                                List<SemanticError> semanticErrors,
                                                AstNode pythonRoot,
                                                Map<String, Node> htmlRoots,
                                                GenerationContext ctx) {
        StringBuilder sb = new StringBuilder();

        sb.append("=".repeat(60)).append("\n");
        sb.append(" SEMANTIC ANALYSIS FAILED\n");
        sb.append("=".repeat(60)).append("\n\n");

        sb.append("ERROR SUMMARY:\n");
        sb.append("-".repeat(60)).append("\n");
        sb.append(semanticReport).append("\n\n");

        sb.append("=".repeat(60)).append("\n");
        sb.append("DETAILED ERRORS (").append(semanticErrors.size()).append(" errors)\n");
        sb.append("=".repeat(60)).append("\n\n");

        if (semanticErrors.isEmpty()) {
            sb.append("  No detailed errors available.\n\n");
        } else {
            for (int i = 0; i < semanticErrors.size(); i++) {
                SemanticError err = semanticErrors.get(i);
                sb.append("  ").append(String.format("%-3d", i + 1));
                sb.append("Line ").append(err.getLine());
                sb.append(": ").append(err.getMessage());
                sb.append("\n");
                if (i < semanticErrors.size() - 1) {
                    sb.append("  ").append("-".repeat(55)).append("\n");
                }
            }
            sb.append("\n");
        }

        sb.append("=".repeat(60)).append("\n");
        sb.append("WARNINGS\n");
        sb.append("=".repeat(60)).append("\n");
        List<String> warnings = ctx.getWarnings();
        if (warnings.isEmpty()) {
            sb.append("  No warnings.\n");
        } else {
            for (String w : warnings) {
                sb.append("  ").append(w).append("\n");
            }
        }
        sb.append("\n");

        sb.append("=".repeat(60)).append("\n");
        sb.append("NEXT STEPS\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append("1. Fix all semantic errors listed above\n");
        sb.append("2. Re-run the compiler\n");
        sb.append("3. Check output/ for generated HTML files\n");
        sb.append("=".repeat(60)).append("\n");

        return sb.toString();
    }
}