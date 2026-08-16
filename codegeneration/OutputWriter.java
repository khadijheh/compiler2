package codegeneration;

import semantic.SemanticError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * OutputWriter
 * ============
 * يكتب كل ملفات الخرج على الـ disk.
 *
 * ينشئ مجلدين:
 * ─────────────────────────────────────────────────────────────────
 *
 * output/
 * ├── index.html          ← HTML مُولَّد من JinjaRenderer
 * ├── add_product.html    ← HTML مُولَّد (إذا كان template موجود)
 * ├── app.py              ← مَنسوخ كما هو (لا تعديل)
 * ├── style.css           ← مَنسوخ كما هو (لا تعديل)
 * └── script.js           ← مَنسوخ كما هو (لا تعديل) إذا وجد
 *
 * compiler_output/
 * ├── ast_python.json     ← Python AST كـ JSON
 * ├── ast_jinja.json      ← HTML/Jinja AST كـ JSON
 * ├── semantic_report.txt ← تقرير Semantic Analysis
 * └── generation_log.txt  ← سجل كامل لمرحلة التوليد
 *
 * ملاحظة مهمة:
 * ─────────────────────────────────────────────────────────────────
 * app.py وstyle.css وscript.js لا تدخل في التحليل والتوليد.
 * تُنسخ فقط لمجلد output/ لأنها ضرورية لتشغيل التطبيق.
 */
public class OutputWriter {

    // ================================================================== //
    //  Constants                                                          //
    // ================================================================== //

    /** مجلد HTML الناتج */
    private static final String OUTPUT_DIR          = "output";

    /** مجلد ملفات المترجم */
    private static final String COMPILER_OUTPUT_DIR = "compiler_output";

    // ================================================================== //
    //  State                                                              //
    // ================================================================== //

    private final GenerationContext context;

    // ================================================================== //
    //  Constructor                                                        //
    // ================================================================== //

    public OutputWriter(GenerationContext context) {
        this.context = context;
    }

    // ================================================================== //
    //  Entry Point                                                        //
    // ================================================================== //

    /**
     * writeAll()
     * ──────────
     * نقطة الدخول الرئيسية.
     * يكتب كل الملفات المطلوبة.
     *
     * @param pythonJson     محتوى ast_python.json
     * @param jinjaJson      محتوى ast_jinja.json
     * @param semanticReport محتوى semantic_report.txt
     * @param supportFiles   مسارات الملفات الداعمة [app.py, style.css, ...]
     */
    public void writeAll(String       pythonJson,
                         String       jinjaJson,
                         String       semanticReport,
                         List<String> supportFiles) {

        context.addLog("=== OutputWriter started ===");

        // ── خطوة 1: إنشاء المجلدات ───────────────────────────────────── //
        createDirectory(OUTPUT_DIR);
        createDirectory(COMPILER_OUTPUT_DIR);

        // ── خطوة 2: كتابة HTML الناتج ────────────────────────────────── //
        writeHtmlOutputs();

        // ── خطوة 3: نسخ الملفات الداعمة إلى output/ ─────────────────── //
        if (supportFiles != null) {
            for (String filePath : supportFiles) {
                copySupportFile(filePath);
            }
        }

        // ── خطوة 4: كتابة JSON files ─────────────────────────────────── //
        writeFile(
                COMPILER_OUTPUT_DIR + "/ast_python.json",
                pythonJson != null ? pythonJson : "{}",
                "Python AST JSON"
        );

        writeFile(
                COMPILER_OUTPUT_DIR + "/ast_jinja.json",
                jinjaJson != null ? jinjaJson : "{}",
                "Jinja AST JSON"
        );

        // ── خطوة 5: كتابة Semantic report ────────────────────────────── //
        writeFile(
                COMPILER_OUTPUT_DIR + "/semantic_report.txt",
                semanticReport != null ? semanticReport
                        : "Semantic Analysis: No report available.",
                "Semantic Report"
        );

        // ── خطوة 6: كتابة generation log (آخر خطوة دائماً) ──────────── //
        writeGenerationLog();

        context.addLog("=== OutputWriter finished ===");
    }
    public void writeAllWithErrors(String pythonJson,
                                   String jinjaJson,
                                   String semanticReport,
                                   List<String> supportFiles,
                                   List<SemanticError> semanticErrors) {

        context.addLog("=== OutputWriter started (with errors) ===");

        createDirectory(OUTPUT_DIR);
        createDirectory(COMPILER_OUTPUT_DIR);

        writeHtmlOutputs();

        if (supportFiles != null) {
            for (String filePath : supportFiles) {
                copySupportFile(filePath);
            }
        }

        writeFile(COMPILER_OUTPUT_DIR + "/ast_python.json",
                pythonJson != null ? pythonJson : "{}",
                "Python AST JSON");

        writeFile(COMPILER_OUTPUT_DIR + "/ast_jinja.json",
                jinjaJson != null ? jinjaJson : "{}",
                "Jinja AST JSON");

        // ✅ هذه الدالة تبني التقرير الكامل من semanticReport + semanticErrors
        writeSemanticReportWithErrors(semanticReport, semanticErrors);

        writeGenerationLog();

        context.addLog("=== OutputWriter finished ===");
    }
    // ================================================================== //
    //  كتابة HTML الناتج                                                 //
    // ================================================================== //

    /**
     * writeHtmlOutputs()
     * ──────────────────
     * يكتب كل HTML files الناتجة من JinjaRenderer.
     *
     * يمشي على outputHtml في GenerationContext:
     *   "index.html"       → output/index.html
     *   "add_product.html" → output/add_product.html
     *   ...
     *
     * لا أسماء ثابتة — يعمل مع أي template.
     */
    private void writeHtmlOutputs() {
        Map<String, String> outputs = context.getOutputHtml();

        if (outputs.isEmpty()) {
            context.addWarning("OutputWriter: no HTML output to write");
            return;
        }

        context.addLog("Writing " + outputs.size() + " HTML file(s)...");

        for (Map.Entry<String, String> entry : outputs.entrySet()) {
            String templateName = entry.getKey();  // "index.html"
            String html         = entry.getValue();

            // أضف DOCTYPE إذا لم يكن موجوداً
            if (!html.trim().toLowerCase().startsWith("<!doctype")) {
                html = "<!DOCTYPE html>\n" + html;
            }
            context.addOutput(templateName, html);

            String outPath = OUTPUT_DIR + "/" + templateName;
            writeFile(outPath, html, "HTML output: " + templateName);
        }
    }

    // ================================================================== //
    //  نسخ الملفات الداعمة                                              //
    // ================================================================== //

    /**
     * copySupportFile()
     * ──────────────────
     * ينسخ ملف داعم (app.py, style.css, script.js) إلى output/.
     *
     * هذه الملفات لا تُعالَج — تُنسخ كما هي.
     *
     * مثال:
     *   "Files/test.txt"  → output/app.py
     *   "Files/style.css" → output/style.css
     *
     * إذا لم يوجد الملف → Warning ولا انهيار.
     */
    private void copySupportFile(String sourcePath) {
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            context.addWarning(
                    "Support file not found: " + sourcePath + " — skipped"
            );
            return;
        }

        // تحديد اسم الملف في output/
        String fileName = sourceFile.getName();

        // test.txt → app.py (اسم خاص في مشروعنا)
        if (fileName.equals("test.txt")) {
            fileName = "app.py";
        }

        String destPath = OUTPUT_DIR + "/" + fileName;

        try {
            Files.copy(
                    Paths.get(sourcePath),
                    Paths.get(destPath),
                    StandardCopyOption.REPLACE_EXISTING
            );
            context.addLog("Copied support file: " + sourcePath
                    + " → " + destPath);
        } catch (IOException e) {
            context.addWarning(
                    "Failed to copy support file '" + sourcePath
                            + "': " + e.getMessage()
            );
        }
    }

    // ================================================================== //
    //  كتابة generation_log.txt                                         //
    // ================================================================== //

    /**
     * writeGenerationLog()
     * ─────────────────────
     * يكتب سجل كامل لمرحلة التوليد.
     *
     * يشمل:
     *   - تاريخ ووقت التوليد
     *   - قائمة الملفات الناتجة
     *   - كل log entries من GenerationContext
     *   - كل warnings
     *   - إحصائيات التوليد
     */
    private void writeGenerationLog() {
        StringBuilder log = new StringBuilder();

        String separator = "=".repeat(60) + "\n";
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        // ── Header ────────────────────────────────────────────────────── //
        log.append(separator);
        log.append("GENERATION LOG\n");
        log.append("Generated at: ").append(timestamp).append("\n");
        log.append(separator).append("\n");

        // ── Output Files ──────────────────────────────────────────────── //
        log.append("--- OUTPUT FILES ---\n");
        if (context.getOutputHtml().isEmpty()) {
            log.append("  (no HTML files generated)\n");
        } else {
            for (String name : context.getOutputHtml().keySet()) {
                String html   = context.getOutputHtml().get(name);
                long   chars  = html != null ? html.length() : 0;
                log.append("  output/").append(name)
                        .append("  (").append(chars).append(" chars)\n");
            }
        }
        log.append("\n");

        // ── Routes ────────────────────────────────────────────────────── //
        log.append("--- ROUTES ---\n");
        if (context.getRoutes().isEmpty()) {
            log.append("  (no routes found)\n");
        } else {
            for (Map.Entry<String, String> r : context.getRoutes().entrySet()) {
                log.append("  ").append(r.getKey())
                        .append("  →  ").append(r.getValue()).append("()\n");
            }
        }
        log.append("\n");

        // ── Global Variables ──────────────────────────────────────────── //
        log.append("--- GLOBAL VARIABLES ---\n");
        for (Map.Entry<String, Object> v : context.getGlobalVariables().entrySet()) {
            String key = v.getKey();
            Object val = v.getValue();

            // ⭐ استثناء خاص لـ product - لا نعرضه في السجل
            if ("product".equals(key)) {
                continue;  // تخطي product لأنه متغير مساعد
            }

            String display;
            if (val instanceof List) {
                // ⭐ عرض تفاصيل جميع المنتجات في القائمة
                List<?> list = (List<?>) val;
                display = "List[" + list.size() + " items]\n";

                // ⭐ عرض كل منتج على حدة
                for (int i = 0; i < list.size(); i++) {
                    Object item = list.get(i);
                    display += "    [" + i + "] = " + item.toString() + "\n";
                }
                display = display.trim();  // إزالة السطر الجديد الزائد
            } else if (val instanceof String) {
                display = "\"" + val + "\"";
            } else {
                display = String.valueOf(val);
            }

            log.append("  ").append(key).append(" = ").append(display).append("\n");
        }
        log.append("\n");
        // ── Log Entries ───────────────────────────────────────────────── //
        log.append("--- LOG ENTRIES (")
                .append(context.getLogEntries().size()).append(") ---\n");
        for (String entry : context.getLogEntries()) {
            log.append("  ").append(entry).append("\n");
        }
        log.append("\n");

        // ── Warnings ──────────────────────────────────────────────────── //
        List<String> warnings = context.getWarnings();
        log.append("--- WARNINGS (").append(warnings.size()).append(") ---\n");
        if (warnings.isEmpty()) {
            log.append("  No warnings.\n");
        } else {
            for (String w : warnings) {
                log.append("  ").append(w).append("\n");
            }
        }
        log.append("\n");

        // ── Summary ───────────────────────────────────────────────────── //
        log.append("--- SUMMARY ---\n");
        log.append("  HTML files generated : ")
                .append(context.getOutputHtml().size()).append("\n");
        log.append("  Routes found         : ")
                .append(context.getRoutes().size()).append("\n");
        log.append("  Warnings             : ")
                .append(warnings.size()).append("\n");
        log.append("  Log entries          : ")
                .append(context.getLogEntries().size()).append("\n");
        log.append(separator);

        writeFile(
                COMPILER_OUTPUT_DIR + "/generation_log.txt",
                log.toString(),
                "Generation Log"
        );
    }

    // ================================================================== //
    //  إنشاء المجلدات                                                    //
    // ================================================================== //

    /**
     * createDirectory()
     * ──────────────────
     * ينشئ مجلداً إذا لم يكن موجوداً.
     * إذا فشل → Warning ولا انهيار.
     */
    private void createDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                context.addLog("Created directory: " + dirPath);
            } else {
                context.addWarning(
                        "Failed to create directory: " + dirPath
                );
            }
        } else {
            context.addLog("Directory already exists: " + dirPath);
        }
    }

    // ================================================================== //
    //  كتابة ملف نصي                                                     //
    // ================================================================== //

    /**
     * writeFile()
     * ────────────
     * يكتب محتوى نصي إلى ملف بترميز UTF-8.
     *
     * @param path    مسار الملف
     * @param content المحتوى
     * @param label   وصف للـ log
     *
     * إذا فشل → Warning ولا انهيار
     * (الكومبايلر يجب أن يكمل حتى لو فشل ملف واحد)
     */
    private void writeFile(String path, String content, String label) {
        try {
            Files.writeString(
                    Paths.get(path),
                    content,
                    StandardCharsets.UTF_8
            );
            context.addLog(
                    "Written: " + path
                            + "  (" + content.length() + " chars)"
                            + "  [" + label + "]"
            );
        } catch (IOException e) {
            context.addWarning(
                    "Failed to write '" + path
                            + "': " + e.getMessage()
                            + "  [" + label + "]"
            );
        }
    }
    private void writeSemanticReportWithErrors(String semanticReport,
                                               List<SemanticError> semanticErrors) {
        StringBuilder sb = new StringBuilder();

        String separator = "=".repeat(60) + "\n";
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        sb.append(separator);
        sb.append("SEMANTIC ANALYSIS REPORT\n");
        sb.append("Generated at: ").append(timestamp).append("\n");
        sb.append(separator).append("\n");

        sb.append("SUMMARY:\n");
        sb.append("-".repeat(60)).append("\n");
        sb.append(semanticReport).append("\n\n");

        if (semanticErrors != null && !semanticErrors.isEmpty()) {
            sb.append(separator);
            sb.append("DETAILED ERRORS (").append(semanticErrors.size()).append(" errors)\n");
            sb.append(separator).append("\n");

            for (int i = 0; i < semanticErrors.size(); i++) {
                SemanticError err = semanticErrors.get(i);
                sb.append(String.format("  #%-3d ", i + 1));
                sb.append("Line ").append(err.getLine());
                sb.append(": ").append(err.getMessage());
                sb.append("\n");
                if (i < semanticErrors.size() - 1) {
                    sb.append("  ").append("-".repeat(55)).append("\n");
                }
            }
            sb.append("\n");
        }

        sb.append(separator);
        sb.append("STATISTICS\n");
        sb.append(separator).append("\n");
        sb.append("  Total errors  : ").append(semanticErrors != null ? semanticErrors.size() : 0).append("\n");
        sb.append("  Total warnings: ").append(context.getWarnings().size()).append("\n");
        sb.append(separator);

        writeFile(COMPILER_OUTPUT_DIR + "/semantic_report.txt",
                sb.toString(),
                "Semantic Report with Errors");
    }
}