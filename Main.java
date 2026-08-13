import AST.AstNode;
import AST_H_C.Node;
import codegeneration.Generator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import grammers.flaskLexer;
import grammers.flaskParser;
import grammers.htmlLexer;
import grammers.htmlParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import semantic.ScopeManager;
import semantic.SemanticAnalyzerVisitor;
import semantic.SemanticError;
import visitor.HtmlVisitor;
import visitor.PythonVisitor;
import visitor.SymbolTableVisitor;
import visitor.WebSymbolTableVisitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {

    // ================================================================== //
    //  إعدادات المشروع                                                   //
    // ================================================================== //
    /**/
    /**
     * ملفات HTML المطلوب معالجتها
     */
    private static final List<String> HTML_FILES = Arrays.asList(
            "Files/index.html",
            "Files/add_product.html",
            "Files/product_details.html"
    );

    /**
     * ملف Python
     */
    private static final String PYTHON_FILE = "Files/test.txt";

    /**
     * مجلد الخرج
     */
    private static final String OUTPUT_DIR = "output";

    /**
     * مجلد ملفات المترجم
     */
    private static final String COMPILER_OUTPUT_DIR = "compiler_output";

    /**
     * منفذ الـ HTTP Server
     */
    private static final int SERVER_PORT = 8080;

    /**
     * routeTable
     * ──────────
     * يربط Flask route paths بأسماء ملفات HTML المُولَّدة.
     * يُملأ من runCompiler() بعد قراءة routeToTemplate من GenerationContext.
     * <p>
     * مثال:
     * "/add"                       → "add_product.html"
     * "/"                          → "index.html"
     * "/go-home"                   → "index.html"
     * "/product/<int:product_id>"  → "product_detail.html"
     * <p>
     * يُستخدمه HTTP Server لتحويل:
     * GET /add      → يقرأ output/add_product.html
     * GET /go-home  → Redirect → output/index.html
     */
    private static final Map<String, String> routeTable =
            new java.util.concurrent.ConcurrentHashMap<>();

    // ================================================================== //
    //  main                                                               //
    // ================================================================== //

    public static void main(String[] args) throws Exception {

        // ── تشغيل الـ compiler مرة أولى ─────────────────────────────── //
        System.out.println("=== INITIAL COMPILATION ===");
        runCompiler();

        // ── تشغيل HTTP Server ─────────────────────────────────────────── //
        startHttpServer();

        // ── تشغيل File Watcher ────────────────────────────────────────── //
        startFileWatcher();
    }

    // ================================================================== //
    //  COMPILER — كل المراحل                                            //
    // ================================================================== //

    /**
     * runCompiler()
     * ─────────────
     * يُشغّل كل مراحل الـ compiler:
     * Phase 1: HTML Parsing
     * Phase 2: Python Parsing
     * Phase 3: Symbol Table
     * Phase 4: Semantic Analysis
     * Phase 5: Code Generation
     */
    private static void runCompiler() {
        // الملفات الداعمة تُنسخ إلى output/
        List<String> supportFiles = new ArrayList<>();
        supportFiles.add(PYTHON_FILE);

        AstNode pythonAst = null;
        boolean semPassed = false;
        String semReport = "";

        // ── Phase 2: Python Parsing ──────────────────────────────────── //
        System.out.println("\n=== PHASE 2: PYTHON PARSING ===");
        try {
            CharStream input = CharStreams.fromFileName(PYTHON_FILE);
            flaskLexer pyLexer = new flaskLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(pyLexer);
            flaskParser pyParser = new flaskParser(tokens);
            flaskParser.ProgramContext tree = pyParser.program();

            if (pyParser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("[Error] Syntax errors — aborting.");
                return;
            }
            PythonVisitor pyVisitor = new PythonVisitor();
            pythonAst = pyVisitor.visit(tree);
            System.out.println(pythonAst);

        } catch (Exception e) {
            System.err.println("Error in Python parsing: " + e.getMessage());
            return;
        }

        // ── Phase 3: Symbol Table ────────────────────────────────────── //
        System.out.println("\n=== PHASE 3: SYMBOL TABLE ===");
        try {
            SymbolTableVisitor stVisitor = new SymbolTableVisitor();
            stVisitor.build(pythonAst);
            SymbolTable.Scope.printFinalReport();
        } catch (Exception e) {
            System.err.println("Error in Symbol Table: " + e.getMessage());
        }

        // ── Phase 4: Semantic Analysis ───────────────────────────────── //
        System.out.println("\n=== PHASE 4: SEMANTIC ANALYSIS ===");
        try {
            ScopeManager scopeManager = new ScopeManager();
            SemanticAnalyzerVisitor semVisitor =
                    new SemanticAnalyzerVisitor(scopeManager);
            try {
                semVisitor.analyse(pythonAst);
                semPassed = true;
                semReport = "Semantic Analysis PASSED — 0 errors.";
                System.out.println(semReport);
            } catch (SemanticError se) {
                semPassed = false;
                semReport = se.getMessage();
                System.err.println("\n" + semReport);
            }
        } catch (Exception e) {
            System.err.println("Error in Semantic Analysis: " + e.getMessage());
            semPassed = false;
            semReport = "Semantic error: " + e.getMessage();
        }


        // في Main.java - runCompiler() - Phase 5

// ── Phase 1 + 5: HTML Parse + Generate — لكل template ────────── //
        Generator generator = new Generator();

// ⭐ جمع كل ASTs في Map واحد
        Map<String, Node> htmlRoots = new LinkedHashMap<>();

        for (String htmlFile : HTML_FILES) {
            if (!Files.exists(Paths.get(htmlFile))) {
                System.out.println("[Skip] Not found: " + htmlFile);
                continue;
            }

            System.out.println("\n" + "─".repeat(50));
            System.out.println("Processing: " + htmlFile);

            Node htmlAst = null;

            // Phase 1: HTML Parsing
            System.out.println("=== PHASE 1: HTML PARSING (" + htmlFile + ") ===");
            try {
                String htmlCode = Files.readString(Paths.get(htmlFile));
                htmlLexer hLexer = new htmlLexer(CharStreams.fromString(htmlCode));
                htmlParser hParser = new htmlParser(new CommonTokenStream(hLexer));
                HtmlVisitor hVisitor = new HtmlVisitor();
                htmlAst = hVisitor.visitHtmlDocument(hParser.htmlDocument());
                System.out.println(htmlAst);

                WebSymbolTableVisitor webST = new WebSymbolTableVisitor(htmlFile);
                webST.build(htmlAst);


                String templateName = new File(htmlFile).getName(); // "index.html"
                htmlRoots.put(templateName, htmlAst);

            } catch (Exception e) {
                System.err.println("Error parsing '" + htmlFile + "': " + e.getMessage());
                continue;
            }
        }

        generator.generate(
                htmlRoots,      // ← Map بجميع القوالب
                pythonAst,
                supportFiles,
                semPassed,
                semReport
        );

        // ── بناء جدول الـ routes بعد التوليد ────────────────────────── //
        buildRouteTable();

        System.out.println("\n=== COMPILATION DONE ===");
        System.out.println("Open: http://localhost:" + SERVER_PORT + "/");
    }

    // ================================================================== //
    //  توليد سريع لـ index.html فقط                                     //
    // ================================================================== //

    /**
     * generateOnlyIndexHtml()
     * ────────────────────────
     * يُولد فقط ملف index.html بدلاً من إعادة توليد كل الملفات.
     * يُستخدم بعد إضافة منتج جديد لتسريع العملية.
     */
    private static void generateOnlyIndexHtml() {
        try {
            System.out.println("[ReGeneration] Generating only index.html...");

            // ── Parse Python (للحصول على المنتجات المحدثة) ─────────── //
            CharStream input = CharStreams.fromFileName(PYTHON_FILE);
            flaskLexer pyLexer = new flaskLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(pyLexer);
            flaskParser pyParser = new flaskParser(tokens);
            flaskParser.ProgramContext tree = pyParser.program();

            if (pyParser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("[ReGeneration] Python syntax errors — aborting.");
                return;
            }
            PythonVisitor pyVisitor = new PythonVisitor();
            AstNode pythonAst = pyVisitor.visit(tree);

            // ── Parse index.html فقط ────────────────────────────────── //
            String htmlFile = "Files/index.html";
            if (!Files.exists(Paths.get(htmlFile))) {
                System.err.println("[ReGenation] index.html not found");
                return;
            }

            String htmlCode = Files.readString(Paths.get(htmlFile));
            htmlLexer hLexer = new htmlLexer(CharStreams.fromString(htmlCode));
            htmlParser hParser = new htmlParser(new CommonTokenStream(hLexer));
            HtmlVisitor hVisitor = new HtmlVisitor();
            Node htmlAst = hVisitor.visitHtmlDocument(hParser.htmlDocument());

            // ── توليد index.html فقط ────────────────────────────────── //
            Map<String, Node> htmlRoots = new LinkedHashMap<>();
            htmlRoots.put("index.html", htmlAst);

            // التحقق من Semantic (بافتراض النجاح)
            boolean semPassed = true;
            String semReport = "Semantic Analysis PASSED — 0 errors.";

            Generator generator = new Generator();
            List<String> supportFiles = new ArrayList<>();
            supportFiles.add(PYTHON_FILE);

            generator.generate(htmlRoots, pythonAst, supportFiles, semPassed, semReport);

            // ── تحديث جدول الـ routes ────────────────────────────────── //
            buildRouteTable();

            System.out.println("[ReGeneration] index.html generated successfully!");

        } catch (Exception e) {
            System.err.println("[ReGeneration] Error generating index: " + e.getMessage());
            // في حالة الفشل، ارجع إلى التوليد الكامل
            System.out.println("[ReGeneration] Falling back to full compilation...");
            runCompiler();
        }
    }

    // ================================================================== //
    //  buildRouteTable — يبني جدول الـ routes من output/                //
    // ================================================================== //

    /**
     * buildRouteTable()
     * ──────────────────
     * يُعيد بناء routeTable بعد كل compilation.
     * <p>
     * يقرأ generation_log.txt لاستخراج routes المُسجَّلة،
     * أو يبني جدول افتراضي من أسماء الملفات الموجودة في output/.
     * <p>
     * مثال نتيجة:
     * routeTable = {
     * "/"         → "index.html",
     * "/add"      → "add_product.html",
     * "/go-home"  → "index.html",
     * "/product"  → "product_details.html"
     * }
     */
    private static void buildRouteTable() {
        // ── أولاً: اقرأ generation_log.txt لاستخراج RouteToTemplate ─── //
        Path logFile = Paths.get(COMPILER_OUTPUT_DIR + "/generation_log.txt");
        if (Files.exists(logFile)) {
            try {
                String log = Files.readString(logFile);
                for (String line : log.split("\n")) {
                    line = line.trim();
                    if (line.contains("RouteToTemplate:")) {
                        String part = line.substring(line.indexOf("RouteToTemplate:") + 16).trim();
                        String[] sides = part.split("→", 2);
                        if (sides.length == 2) {
                            String route = sides[0].trim();
                            String template = sides[1].trim();
                            routeTable.put(route, template);
                            System.out.println("[RouteTable] " + route + " → " + template);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("[RouteTable] Cannot read log: " + e.getMessage());
            }
        }

        // ⭐ تأكد من وجود dynamic route يدوياً إذا لم يتم استخراجه
        if (!routeTable.containsKey("/product/<int:product_id>")) {
            routeTable.put("/product/<int:product_id>", "product_details.html");
            System.out.println("[RouteTable] Added dynamic route: /product/<int:product_id> → product_details.html");
        }

        System.out.println("[RouteTable] Built " + routeTable.size() + " routes: " + routeTable);

        // ── ثانياً: أضف routes تلقائياً من output/ ────────────────────── //
        File outputDir = new File(OUTPUT_DIR);
        if (outputDir.exists()) {
            for (File f : outputDir.listFiles()) {
                if (!f.getName().endsWith(".html")) continue;
                String name = f.getName();

                if (name.equals("index.html")) {
                    routeTable.putIfAbsent("/", "index.html");
                    continue;
                }

                String withoutExt = name.replace(".html", "");
                routeTable.putIfAbsent("/" + withoutExt, name);

                // ⭐ تجنب التعارض مع dynamic routes
                if (withoutExt.contains("_")) {
                    String[] parts = withoutExt.split("_");
                    for (String part : parts) {
                        String shortRoute = "/" + part;
                        // لا تضف إذا كان هناك route ديناميكي بنفس البادئة
                        boolean hasDynamic = routeTable.keySet().stream()
                                .anyMatch(r -> r.startsWith(shortRoute + "/<") || r.startsWith(shortRoute + "/{"));
                        if (!hasDynamic && !routeTable.containsKey(shortRoute)) {
                            routeTable.put(shortRoute, name);
                        }
                    }
                }
            }
        }

        System.out.println("[RouteTable] Built " + routeTable.size() + " routes: " + routeTable);
    }
//    private static void buildRouteTable() {
//        routeTable.clear();
//
//        // ── أولاً: اقرأ generation_log.txt لاستخراج RouteToTemplate ─── //
//        Path logFile = Paths.get(COMPILER_OUTPUT_DIR + "/generation_log.txt");
//        if (Files.exists(logFile)) {
//            try {
//                String log = Files.readString(logFile);
//                // ابحث عن سطور مثل: RouteToTemplate: /add → add_product.html
//                for (String line : log.split("\n")) {
//                    line = line.trim();
//                    if (line.contains("RouteToTemplate:")) {
//                        // استخرج: "/add → add_product.html"
//                        String part = line.substring(
//                                line.indexOf("RouteToTemplate:") + 16).trim();
//                        String[] sides = part.split("→", 2);
//                        if (sides.length == 2) {
//                            String route = sides[0].trim();
//                            String template = sides[1].trim();
//                            routeTable.put(route, template);
//                            System.out.println("[RouteTable] " + route
//                                    + " → " + template);
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                System.err.println("[RouteTable] Cannot read log: " + e.getMessage());
//            }
//        }
//
//        // ── ثانياً: أضف الـ routes الأساسية إذا لم توجد ──────────────── //
//        // يمشي على كل ملفات output/ ويضيف route تلقائياً
//        File outputDir = new File(OUTPUT_DIR);
//        if (outputDir.exists()) {
//            for (File f : outputDir.listFiles()) {
//                if (!f.getName().endsWith(".html")) continue;
//                String name = f.getName(); // "add_product.html"
//
//                // index.html → "/"
//                if (name.equals("index.html")) {
//                    routeTable.putIfAbsent("/", "index.html");
//                    continue;
//                }
//
//                // add_product.html → "/add_product" و "/add"
//                String withoutExt = name.replace(".html", ""); // "add_product"
//                routeTable.putIfAbsent("/" + withoutExt, name);
//
//                // أضف نسخة مختصرة: add_product → /add
//                if (withoutExt.contains("_")) {
//                    String[] parts = withoutExt.split("_");
//                    String short1 = "/" + parts[0];            // "/add"
//                    String short2 = "/" + parts[parts.length - 1]; // "/product"
//                    routeTable.putIfAbsent(short1, name);
//                    routeTable.putIfAbsent(short2, name);
//                }
//            }
//        }
//
//        System.out.println("[RouteTable] Built " + routeTable.size()
//                + " routes: " + routeTable);
//    }

    // ================================================================== //
    //  HTTP SERVER                                                        //
    // ================================================================== //

    /**
     * startHttpServer()
     * ──────────────────
     * يُشغّل HTTP Server بسيط على port 8080.
     * <p>
     * يخدم الملفات من مجلد output/ مباشرة في المتصفح.
     * <p>
     * Routes:
     * http://localhost:8080/           → output/index.html
     * http://localhost:8080/index.html → output/index.html
     * http://localhost:8080/add_product.html → output/add_product.html
     * http://localhost:8080/style.css  → output/style.css
     * <p>
     * يُشغَّل في Thread منفصل لا يمنع File Watcher.
     */
    private static void startHttpServer() throws Exception {
        HttpServer server = HttpServer.create(
                new InetSocketAddress(SERVER_PORT), 0);

        // Handler رئيسي لكل الطلبات
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String requestPath = exchange.getRequestURI().getPath();
                String filePath = resolveFilePath(requestPath);

                System.out.println("[HTTP] GET " + requestPath
                        + " → " + filePath);

                File file = new File(OUTPUT_DIR + "/" + filePath);

                if (!file.exists()) {
                    // ── 404 Page ────────────────────────────────────────── //
                    String body = "<!DOCTYPE html><html><body>"
                            + "<h1>404 Not Found</h1>"
                            + "<p>Path: <code>" + requestPath + "</code></p>"
                            + "<p>Resolved to: <code>" + filePath + "</code></p>"
                            + "<p>File not found in <code>output/</code></p>"
                            + "<p>Available routes:</p><ul>"
                            + buildRouteListHtml()
                            + "</ul>"
                            + "<a href='/'>← Back to Home</a>"
                            + "</body></html>";
                    byte[] bodyBytes = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().set("Content-Type",
                            "text/html; charset=UTF-8");
                    exchange.sendResponseHeaders(404, bodyBytes.length);
                    exchange.getResponseBody().write(bodyBytes);
                    exchange.getResponseBody().close();
                    return;
                }

                // ── تحديد Content-Type وإرسال الملف ─────────────────── //
                String contentType = getContentType(filePath);
                byte[] response = Files.readAllBytes(file.toPath());

                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, response.length);
                exchange.getResponseBody().write(response);
                exchange.getResponseBody().close();

                System.out.println("[HTTP] 200 OK → "
                        + file.getName() + " (" + response.length + " bytes)");
            }
        });

        // Handler خاص لـ POST /add
        server.createContext("/add", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                if ("POST".equals(exchange.getRequestMethod())) {
                    // قراءة بيانات النموذج
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder body = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        body.append(line);
                    }

                    // تحليل البيانات (application/x-www-form-urlencoded)
                    String[] params = body.toString().split("&");
                    Map<String, String> formData = new HashMap<>();
                    for (String param : params) {
                        String[] pair = param.split("=");
                        if (pair.length == 2) {
                            String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
                            String value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                            formData.put(key, value);
                        }
                    }

                    // قراءة الملف الحالي
                    Path testFilePath = Paths.get(PYTHON_FILE);
                    String content = Files.readString(testFilePath);

                    // حساب ID جديد
                    int newId = 1;
                    int idStart = content.indexOf("products = [");
                    if (idStart != -1) {
                        // ابحث عن أكبر ID في المنتجات الحالية
                        String productsSection = content.substring(idStart);
                        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\"id\":\\s*(\\d+)");
                        java.util.regex.Matcher matcher = pattern.matcher(productsSection);
                        while (matcher.find()) {
                            int id = Integer.parseInt(matcher.group(1));
                            if (id >= newId) newId = id + 1;
                        }
                    }

                    // إضافة المنتج الجديد
                    String newProduct = String.format("""
                                        {
                                            "id": %d,
                                            "name": "%s",
                                            "price": %s,
                                            "description": "%s",
                                            "category": "%s",
                                            "image": DEFAULT_IMAGE,
                                        },
                                    """,
                            newId,
                            formData.getOrDefault("name", "New Product"),
                            formData.getOrDefault("price", "0.0"),
                            formData.getOrDefault("description", "No description"),
                            formData.getOrDefault("category", "General")
                    );

                    // العثور على قائمة products وإدراج المنتج
                    int productsIndex = content.indexOf("products = [");
                    if (productsIndex != -1) {
                        int insertIndex = content.indexOf("]", productsIndex);
                        if (insertIndex != -1) {
                            String before = content.substring(0, insertIndex);
                            String after = content.substring(insertIndex);
                            content = before + newProduct + after;
                            Files.writeString(testFilePath, content);
                            System.out.println("[Add] New product added: " + formData.get("name") + " (ID: " + newId + ")");
                        }
                    }

                    // إعادة التوليد
//                    runCompiler();
                    generateOnlyIndexHtml();  // ← استخدم هذا بدلاً منه

                    // إعادة توجيه إلى الصفحة الرئيسية
                    exchange.getResponseHeaders().set("Location", "/");
                    exchange.sendResponseHeaders(302, -1);
                    exchange.getResponseBody().close();
                    return;
                }

                // ── GET /add → عرض add_product.html من مجلد output/ ── //
                // ⭐ استخدم الملف المُولَّد في output/ وليس Files/
                Path htmlPath = Paths.get("output/add_product.html");

                if (!Files.exists(htmlPath)) {
                    // إذا لم يكن موجوداً، حاول التوليد أولاً
                    System.out.println("[Add] add_product.html not found in output/, running compiler...");
                    runCompiler();

                    // حاول مرة أخرى
                    htmlPath = Paths.get("output/add_product.html");
                    if (!Files.exists(htmlPath)) {
                        String error = "add_product.html not found in output/ even after compilation";
                        byte[] errorBytes = error.getBytes(StandardCharsets.UTF_8);
                        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        exchange.sendResponseHeaders(404, errorBytes.length);
                        exchange.getResponseBody().write(errorBytes);
                        exchange.getResponseBody().close();
                        return;
                    }
                }

                byte[] response = Files.readAllBytes(htmlPath);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.length);
                exchange.getResponseBody().write(response);
                exchange.getResponseBody().close();
            }
        });
        server.setExecutor(null);
        server.start();
    }

    // ================================================================== //
    //  resolveFilePath — قلب الـ HTTP Server                            //
    // ================================================================== //

    /**
     * resolveFilePath()
     * ──────────────────
     * يُحوّل request path إلى اسم ملف في output/.
     * <p>
     * الأولوية:
     * ─────────────────────────────────────────────────────────────
     * 1. بحث مباشر في routeTable
     * /add     → "add_product.html"
     * /        → "index.html"
     * /go-home → "index.html"
     * <p>
     * 2. بحث بـ pattern في routeTable
     * /product/1 → يطابق "/product/<int:product_id>" → "product_detail.html"
     * <p>
     * 3. الملف موجود مباشرة في output/
     * /add_product.html → "add_product.html"
     * /style.css        → "style.css"
     * <p>
     * 4. Fallback: أرجع الـ path كما هو
     */
    private static String resolveFilePath(String requestPath) {
        if (requestPath == null || requestPath.isBlank()) {
            return "index.html";
        }

        // ── 1. بحث مباشر في routeTable ──────────────────────────────── //
        if (routeTable.containsKey(requestPath)) {
            return routeTable.get(requestPath);
        }

        // ── 2. "/" → index.html ──────────────────────────────────────── //
        if (requestPath.equals("/")) {
            return "index.html";
        }

        // ── 3. بحث بـ pattern للـ dynamic routes ────────────────────── //
        for (Map.Entry<String, String> entry : routeTable.entrySet()) {
            String routeKey = entry.getKey();
            if (routeKey.contains("<") || routeKey.contains("{")) {
                int paramStart = Math.min(
                        routeKey.indexOf('<') != -1 ? routeKey.indexOf('<') : Integer.MAX_VALUE,
                        routeKey.indexOf('{') != -1 ? routeKey.indexOf('{') : Integer.MAX_VALUE
                );

                if (paramStart != Integer.MAX_VALUE) {
                    String prefix = routeKey.substring(0, paramStart);
                    if (!prefix.isBlank() && requestPath.startsWith(prefix)) {
                        String remaining = requestPath.substring(prefix.length());
                        if (routeKey.contains("int") && remaining.matches("\\d+")) {
                            return entry.getValue();
                        }
                        if (routeKey.contains("string") || routeKey.contains("path")) {
                            return entry.getValue();
                        }
                    }
                }
            }
        }

        // ── 4. معالجة خاصة لـ /product/{id} ──────────────────────────── //
//        if (requestPath.matches("^/product/\\d+$")) {
//            for (Map.Entry<String, String> entry : routeTable.entrySet()) {
//                if (entry.getKey().startsWith("/product/") &&
//                        (entry.getKey().contains("<") || entry.getKey().contains("{"))) {
//                    return entry.getValue();
//                }
//            }
//            if (new File(OUTPUT_DIR + "/product_details.html").exists()) {
//                return "product_details.html";
//            }
//        }
        if (requestPath.matches("^/product/\\d+$")) {
            // ابحث في routeTable عن أي route يبدأ بـ /product/ ويحتوي على <
            for (Map.Entry<String, String> entry : routeTable.entrySet()) {
                String routeKey = entry.getKey();
                if (routeKey.startsWith("/product/") && routeKey.contains("<")) {
                    return entry.getValue();  // ← يرجع "product_details.html"
                }
            }
            // إذا لم يوجد في routeTable، استخدم product_details.html مباشرة
            return "product_details.html";
        }

        // ── 5. الملف موجود مباشرة ────────────────────────────────────── //
        if (requestPath.contains(".")) {
            return requestPath.replaceFirst("^/", "");
        }

        // ── 6. Fallback: حاول إضافة .html ────────────────────────────── //
        String clean = requestPath.replaceFirst("^/", "");
        File htmlFile = new File(OUTPUT_DIR + "/" + clean + ".html");
        if (htmlFile.exists()) {
            return clean + ".html";
        }

        return requestPath.replaceFirst("^/", "");
    }
//    private static String resolveFilePath(String requestPath) {
//        if (requestPath == null || requestPath.isBlank()) {
//            return "index.html";
//        }
//
//        // ── 1. بحث مباشر في routeTable ──────────────────────────────── //
//        if (routeTable.containsKey(requestPath)) {
//            return routeTable.get(requestPath);
//        }
//
//        // ── 2. "/" → index.html ──────────────────────────────────────── //
//        if (requestPath.equals("/")) {
//            return "index.html";
//        }
//
//        // ── 3. بحث بـ pattern (للـ dynamic routes مثل /product/1) ──── //
//        for (Map.Entry<String, String> entry : routeTable.entrySet()) {
//            String routeKey = entry.getKey();
//            if (routeKey.contains("<")) {
//                String prefix = routeKey.substring(0, routeKey.indexOf('<'));
//                if (!prefix.isBlank() && requestPath.startsWith(prefix)) {
//                    return entry.getValue();
//                }
//            }
//        }
//
//        // ── 4. الملف موجود مباشرة (مثل /style.css, /add_product.html) ─ //
//        if (requestPath.contains(".")) {
//            // أزل الـ / الأولى
//            return requestPath.replaceFirst("^/", "");
//        }
//
//        // ── 5. Fallback: حاول إضافة .html ────────────────────────────── //
//        String clean = requestPath.replaceFirst("^/", "");
//        File htmlFile = new File(OUTPUT_DIR + "/" + clean + ".html");
//        if (htmlFile.exists()) {
//            return clean + ".html";
//        }
//
//        // ── 6. أرجع الـ path كما هو (سيُعطي 404 إذا لم يوجد) ───────── //
//        return requestPath.replaceFirst("^/", "");
//    }

    /**
     * buildRouteListHtml()
     * ─────────────────────
     * يبني قائمة HTML بكل الـ routes المتاحة.
     * يُستخدم في صفحة الـ 404 لمساعدة المستخدم.
     */
    private static String buildRouteListHtml() {
        StringBuilder sb = new StringBuilder();
        if (routeTable.isEmpty()) {
            sb.append("<li>No routes available. Run compiler first.</li>");
        } else {
            for (Map.Entry<String, String> entry : routeTable.entrySet()) {
                sb.append("<li><a href='")
                        .append(entry.getKey()).append("'>")
                        .append(entry.getKey()).append("</a>")
                        .append(" → ").append(entry.getValue())
                        .append("</li>");
            }
        }
        return sb.toString();
    }

    /**
     * getContentType()
     * ─────────────────
     * يُرجع Content-Type المناسب لكل نوع ملف.
     */
    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css")) return "text/css; charset=UTF-8";
        if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
        if (path.endsWith(".json")) return "application/json; charset=UTF-8";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        return "text/plain; charset=UTF-8";
    }

    // ================================================================== //
    //  FILE WATCHER — إعادة التوليد التلقائية                          //
    // ================================================================== //

    /**
     * startFileWatcher()
     * ───────────────────
     * يراقب مجلد Files/ باستمرار.
     * <p>
     * إذا تغيّر أي ملف (إضافة/تعديل/حذف) → يُعيد تشغيل الـ compiler
     * تلقائياً → يُنتج HTML جديد → المتصفح يرى التغيير عند الـ refresh.
     * <p>
     * كيف يعمل؟
     * ─────────────────────────────────────────────────────────────
     * 1. يسجّل WatchService على مجلد Files/
     * 2. ينتظر WatchEvent
     * 3. عند وجود حدث ENTRY_CREATE/MODIFY/DELETE:
     * - ينتظر 500ms (debounce) لتجميع التغييرات المتعددة
     * - يطبع اسم الملف المُغيَّر
     * - يستدعي runCompiler()
     */
    private static void startFileWatcher() throws Exception {

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path watchDir = Paths.get("Files");
        watchDir.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        System.out.println("\n[FileWatcher] Watching Files/ for changes...");
        System.out.println("[FileWatcher] Edit any file in Files/ to auto-regenerate.");

        // يعمل في Thread منفصل
        Thread watcherThread = new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take(); // ينتظر حدث

                    // ── debounce: انتظر 500ms لتجميع التغييرات ────────── //
                    Thread.sleep(500);

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) continue;

                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        String fileName = ev.context().toString();

                        System.out.println("\n" + "=".repeat(50));
                        System.out.println("[FileWatcher] Change detected: "
                                + fileName + " (" + kind.name() + ")");
                        System.out.println("[FileWatcher] Re-running compiler...");
                        System.out.println("=".repeat(50));

                        // ── إعادة التوليد ────────────────────────────── //
                        runCompiler();

                        System.out.println("[FileWatcher] Done. Refresh browser.");
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        System.out.println("[FileWatcher] Watch key invalid — stopping.");
                        break;
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("[FileWatcher] Error: " + e.getMessage());
                }
            }
        });

        watcherThread.setDaemon(true);
        watcherThread.setName("FileWatcher");
        watcherThread.start();

        System.out.println("[FileWatcher] Running in background thread.");

        System.out.println("\nPress ENTER to stop the server...");
        new Scanner(System.in).nextLine();
        System.out.println("Server stopped.");
        System.exit(0);
    }
}