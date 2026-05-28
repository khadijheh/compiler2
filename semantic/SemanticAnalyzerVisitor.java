package semantic;

import AST.*;
import AST_H_C.Node;
import SymbolTable.WebSymbol;
import visitor.WebSymbolTableVisitor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SemanticAnalyzerVisitor — Full Semantic Analysis Pass
 * ======================================================
 * <p>
 * Checks performed:
 * 1  Undefined variables / undeclared function calls
 * 2  Duplicate declarations (variables and functions)
 * 3  Scope errors (local vs global)
 * 4  Type mismatch in assignments and binary expressions
 * 5  Function arity mismatch
 * 6  Function argument type mismatch
 * 7  Return type mismatch / return outside function
 * 8  Subscript on non-subscriptable type
 * 9  Attribute access on None / primitive types
 * 10  Non-iterable in for-loop
 * 11  Literal used as if-condition
 * 12  Unsafe type conversion from request.form input
 * 13  Template path error  (render_template file not found)
 * 14  Missing Flask variable (Jinja var not passed to template)
 * 15  Missing Flask import
 * 16  Route parameter mismatch  ← NEW (FIX 3)
 * 17  Duplicate route path
 * 18  Invalid HTTP method in @app.route
 */
public class SemanticAnalyzerVisitor {

    private final ScopeManager scopes;
    private final List<SemanticError> errors = new ArrayList<>();
    private final Set<String> reportedErrors = new HashSet<>();

    private int functionDepth = 0;
    private final Deque<String> currentFunctionName = new ArrayDeque<>();
    private final Map<String, Integer> functionParamCount = new HashMap<>();
    private final Map<String, String> functionParamTypes = new HashMap<>();
    private final Map<String, String> functionReturnType = new HashMap<>();

    private final Map<String, String> registeredRoutes = new LinkedHashMap<>();

    private final Map<String, List<String>> routeDynamicParams = new LinkedHashMap<>();

    private final String templateDirectory;

    private final Map<String, Integer> referencedTemplates = new LinkedHashMap<>();

    private final Map<String, Set<String>> templateJinjaVars = new LinkedHashMap<>();


    private final Map<String, Set<String>> templateLoopIterators = new LinkedHashMap<>();

    private final Map<String, List<Set<String>>> templatePassedVarsPerCall
            = new LinkedHashMap<>();
    private final Set<String> externalInputVars = new HashSet<>();

    public SemanticAnalyzerVisitor(ScopeManager scopes) {
        this(scopes, "Files");
    }

    public SemanticAnalyzerVisitor(ScopeManager scopes, String templateDirectory) {
        this.scopes = scopes;
        this.templateDirectory = templateDirectory;
    }

    public void analyse(AstNode root) {
        if (!containsImportStatement(root)) {
            scopes.suppressFlaskImportCheck();
        }

        visit(root);

        verifyTemplateFilesExist();
        verifyJinjaVariablesPassed();

        verifyRouteParameterMatch();

        printReport();
    }

    public void analyse(AstNode root, WebSymbolTableVisitor webSTV) {
        ingestWebSymbols(webSTV);
        analyse(root);
    }

    public List<SemanticError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    private void ingestWebSymbols(WebSymbolTableVisitor webSTV) {
        for (WebSymbol sym : WebSymbolTableVisitor.webSymbols) {
            String file = sym.fileName;   // template filename (may include path)

            try {
                file = java.nio.file.Paths.get(file).getFileName().toString();
            } catch (Exception ignored) {
            }
            String name = sym.name;
            String type = sym.type;

            if (isTemplateBuiltin(name)) continue;

            switch (type) {
                case "JINJA_LOOP_ITER":
                    templateLoopIterators
                            .computeIfAbsent(file, k -> new LinkedHashSet<>())
                            .add(name);
                    break;

                case "JINJA_VAR":
                case "JINJA_ITER":
                    Set<String> iters = templateLoopIterators
                            .getOrDefault(file, Collections.emptySet());
                    if (!iters.contains(name)) {
                        templateJinjaVars
                                .computeIfAbsent(file, k -> new LinkedHashSet<>())
                                .add(name);
                    }
                    break;

                default:
                    break;
            }
        }
    }


    private boolean containsImportStatement(AstNode node) {
        if (node == null) return false;
        if (node instanceof ImportStatement) return true;
        for (AstNode child : node.getChildren()) {
            if (containsImportStatement(child)) return true;
        }
        return false;
    }


    private void verifyTemplateFilesExist() {
        for (Map.Entry<String, Integer> entry : referencedTemplates.entrySet()) {
            String templateFile = entry.getKey();
            int line = entry.getValue();
            String fullPath = templateDirectory + File.separator + templateFile;

            if (!Files.exists(Paths.get(fullPath))) {
                reportError(
                        "Template path error: file '" + templateFile
                                + "' referenced in render_template() does not exist at '"
                                + fullPath + "'. "
                                + "Ensure the file is in the correct templates directory.",
                        line);
            } else if (!templateJinjaVars.containsKey(templateFile)) {
                scanTemplateViaAst(templateFile, fullPath);
            }
        }
    }


    private void scanTemplateViaAst(String templateFile, String fullPath) {
        try {
            String htmlCode = Files.readString(Paths.get(fullPath));

            grammers.htmlLexer lexer = new grammers.htmlLexer(
                    org.antlr.v4.runtime.CharStreams.fromString(htmlCode));
            grammers.htmlParser parser = new grammers.htmlParser(
                    new org.antlr.v4.runtime.CommonTokenStream(lexer));
            visitor.HtmlVisitor htmlVisitor = new visitor.HtmlVisitor();
            Node htmlAst = htmlVisitor.visitHtmlDocument(parser.htmlDocument());

            WebSymbolTableVisitor tmpSTV = new WebSymbolTableVisitor(templateFile);
            tmpSTV.build(htmlAst);
            ingestWebSymbols(tmpSTV);

            WebSymbolTableVisitor.webSymbols.clear();

        } catch (Exception e) {

        }
    }


    private void verifyJinjaVariablesPassed() {
        for (Map.Entry<String, Set<String>> entry : templateJinjaVars.entrySet()) {
            String templateFile = entry.getKey();
            Set<String> required = entry.getValue();
            Set<String> loopIters = templateLoopIterators
                    .getOrDefault(templateFile, Collections.emptySet());

            List<Set<String>> callSnapshots = templatePassedVarsPerCall
                    .getOrDefault(templateFile, Collections.emptyList());

            for (String var : required) {
                if (loopIters.contains(var)) continue; // loop var — skip

                if (callSnapshots.isEmpty()) {
                    int line = referencedTemplates.getOrDefault(templateFile, 0);
                    reportError(
                            "Missing Flask variable: '" + var
                                    + "' is used in template '" + templateFile
                                    + "' but render_template() was never called for this template.",
                            line);
                    continue;
                }

                for (int i = 0; i < callSnapshots.size(); i++) {
                    Set<String> snapshot = callSnapshots.get(i);


                    String root = var.split("\\|")[0].split("\\.")[0];

                    boolean satisfied = snapshot.contains(var)
                            || snapshot.contains(root)
                            || snapshot.contains(root + "s"); // simple plural heuristic

                    if (!satisfied) {
                        int line = referencedTemplates.getOrDefault(templateFile, 0);
                        reportError(
                                "Missing Flask variable: Jinja variable '" + var
                                        + "' is used in template '" + templateFile
                                        + "' but is not passed in render_template() call #"
                                        + (i + 1) + ". "
                                        + "Add '" + var + "=<value>' to that render_template() call.",
                                line);
                    }
                }
            }
        }
    }


    private static final Pattern ROUTE_PARAM_PATTERN =
            Pattern.compile("<(?:[a-zA-Z_][a-zA-Z0-9_]*:)?([a-zA-Z_][a-zA-Z0-9_]*)>");

    private void verifyRouteParameterMatch() {
        for (Map.Entry<String, List<String>> entry : routeDynamicParams.entrySet()) {
            String funcName = entry.getKey();
            List<String> routeParams = entry.getValue();

            if (routeParams.isEmpty()) continue;



            if (!functionParamCount.containsKey(funcName)) {
                int line = 0;
                reportError(
                        "Route parameter mismatch: @app.route with dynamic segment(s) "
                                + routeParams + " was found but the view function '"
                                + funcName + "' was never defined.",
                        line);
            }
        }
    }

    private void printReport() {
        System.out.println();
        final int w = 68;
        final String bar = "═".repeat(w);

        List<SemanticError> errorList = new ArrayList<>();
        List<SemanticError> warningList = new ArrayList<>();
        for (SemanticError e : errors) {
            if (e.isWarning()) warningList.add(e);
            else errorList.add(e);
        }

        if (errors.isEmpty()) {
            System.out.println("╔" + bar + "╗");
            System.out.printf("║  %-" + (w - 2) + "s║%n",
                    "✔  Semantic Analysis passed with 0 error(s)");
            System.out.println("╚" + bar + "╝");
            return;
        }
        if (!warningList.isEmpty()) {
            System.out.println("╔" + bar + "╗");
            System.out.printf("║  %-" + (w - 2) + "s║%n",
                    "⚠  Semantic Analysis found " + warningList.size() + " warning(s)");
            System.out.println("╠" + bar + "╣");
            printEntries(warningList, w);
            System.out.println("╚" + bar + "╝");
            System.out.println();
        }

        if (!errorList.isEmpty()) {
            System.out.println("╔" + bar + "╗");
            System.out.printf("║  %-" + (w - 2) + "s║%n",
                    "✘  Semantic Analysis found " + errorList.size() + " error(s)");
            System.out.println("╠" + bar + "╣");
            printEntries(errorList, w);
            System.out.println("╚" + bar + "╝");

            throw new SemanticError(
                    errorList.size() + " semantic error(s) — see report above.", 0);
        }
        System.out.println("[Semantic Analysis] "
                + warningList.size() + " warning(s) — compilation continues.");
    }

    private void printEntries(List<SemanticError> list, int w) {
        int chunk = w - 4;
        for (int i = 0; i < list.size(); i++) {
            String msg = String.format("#%-2d  %s", i + 1, list.get(i).getMessage());
            for (int pos = 0; pos < msg.length(); pos += chunk) {
                String part = msg.substring(pos, Math.min(pos + chunk, msg.length()));
                System.out.printf("║  %-" + (w - 2) + "s║%n", part);
            }
            if (i < list.size() - 1)
                System.out.println("╟" + "─".repeat(w) + "╢");
        }
    }


    private String visit(AstNode node) {
        if (node == null) return "Any";

        if (node instanceof Program) return visitProgram((Program) node);
        if (node instanceof FunctionDef) return visitFunctionDef((FunctionDef) node);
        if (node instanceof Assign) return visitAssign((Assign) node);
        if (node instanceof IfStatement) return visitIfStatement((IfStatement) node);
        if (node instanceof ForStatement) return visitForStatement((ForStatement) node);
        if (node instanceof ReturnStatement) return visitReturnStatement((ReturnStatement) node);
        if (node instanceof ImportStatement) return visitImportStatement((ImportStatement) node);
        if (node instanceof BinaryExpression) return visitBinaryExpression((BinaryExpression) node);
        if (node instanceof Identifier) return visitIdentifier((Identifier) node);
        if (node instanceof FunctionCall) return visitFunctionCall((FunctionCall) node);
        if (node instanceof AttributeAccess) return visitAttributeAccess((AttributeAccess) node);
        if (node instanceof Subscript) return visitSubscript((Subscript) node);

        if (node instanceof NumberLiteral) return inferNumberType((NumberLiteral) node);
        if (node instanceof StringLiteral) return "String";
        if (node instanceof BooleanLiteral) return "Bool";
        if (node instanceof NoneLiteral) return "None";
        if (node instanceof ListLiteral) return visitListLiteral((ListLiteral) node);
        if (node instanceof DictLiteral) return visitDictLiteral((DictLiteral) node);
        if (node instanceof KeywordArgument)
            return visit(((KeywordArgument) node).getValue());

        if (node instanceof Decorator) return visitDecorator((Decorator) node);

        for (AstNode child : node.getChildren()) visit(child);
        return "Any";
    }

    private String visitProgram(Program node) {
        for (AstNode child : node.getChildren()) visit(child);
        return "void";
    }

    private String visitFunctionDef(FunctionDef node) {
        String name = node.getName();
        int line = node.getLine();
        String returnType = node.getReturnType() != null ? node.getReturnType() : "Any";

        if (scopes.isDefinedLocally(name)) {
            reportError(
                    "Duplicate declaration: function '" + name
                            + "' is already declared in scope '"
                            + scopes.currentScopeName() + "'.",
                    line);
        }
        scopes.define(name, "Function", returnType, line);

        int paramCount = node.getParameters() != null ? node.getParameters().size() : 0;
        functionParamCount.put(name, paramCount);
        functionReturnType.put(name, returnType);

        currentFunctionName.push(name);

        for (AstNode child : node.getChildren()) {
            if (child instanceof Decorator) {
                visit(child);
            }
        }

        scopes.enterScope("Function(" + name + ")");
        functionDepth++;

        List<String> paramNames = new ArrayList<>();

        if (node.getParameters() != null) {
            for (int i = 0; i < node.getParameters().size(); i++) {
                String param = node.getParameters().get(i);
                String paramType = node.getParamType(i);

                paramNames.add(param);

                if (scopes.isDefinedLocally(param)) {
                    reportError(
                            "Duplicate parameter name '" + param
                                    + "' in function '" + name + "'.",
                            line);
                }

                scopes.define(param, "Parameter", paramType, line);
                functionParamTypes.put(name + "#" + i, paramType);

                if (!"Any".equals(paramType)) {
                    System.out.println("  [TypeAnnotation] param '" + param
                            + "' : " + paramType + " in " + name + "()");
                }
            }
        }

        if (!"Any".equals(returnType)) {
            System.out.println("  [TypeAnnotation] return type of '"
                    + name + "()' : " + returnType);
        }

        List<String> dynamicSegments = routeDynamicParams.getOrDefault(
                name, Collections.emptyList());
        for (String seg : dynamicSegments) {
            if (!paramNames.contains(seg)) {
                reportError(
                        "Route parameter mismatch: dynamic segment '<" + seg
                                + ">' appears in the @app.route path for function '"
                                + name + "' but '" + seg
                                + "' is not declared as a parameter of that function. "
                                + "Add '" + seg + "' to the function signature: "
                                + "def " + name + "(..., " + seg + ", ...).",
                        line);
            }
        }
        for (String param : paramNames) {
            if (!dynamicSegments.isEmpty() && !dynamicSegments.contains(param)) {
                reportWarning(
                        "Route parameter mismatch: parameter '" + param
                                + "' of function '" + name
                                + "' does not match any dynamic segment in its @app.route path "
                                + dynamicSegments + ". "
                                + "Remove the parameter or add the corresponding '<"
                                + param + ">' segment to the route.",
                        line);
            }
        }

        if (node.getBody() != null) {
            for (AstNode stmt : node.getBody()) visit(stmt);
        }
        functionDepth--;
        currentFunctionName.pop();
        scopes.exitScope();
        return "void";
    }


    private String visitAssign(Assign node) {
        AstNode left = node.getLeft();
        AstNode right = node.getChildren().size() > 1
                ? node.getChildren().get(1) : null;

        String rhsType = (right != null) ? visit(right) : "Any";

        if (left instanceof Identifier) {
            Identifier id = (Identifier) left;
            String idName = id.getName();
            int idLine = id.getLine();

            ScopeManager.TypeInfo existing = scopes.lookup(idName);
            if (existing != null) {
                if (!typesCompatible(existing.type, rhsType)) {
                    reportError(
                            "Type mismatch: cannot assign '" + rhsType
                                    + "' to variable '" + idName
                                    + "' which holds type '" + existing.type
                                    + "' (first declared at line " + existing.line + ").",
                            idLine);
                }
                scopes.define(idName, existing.kind, rhsType, idLine);
            } else {
                scopes.define(idName, "Variable", rhsType, idLine);
            }

            if (right instanceof FunctionCall) {
                markExternalInput(idName, (FunctionCall) right);
            }
        } else {
            visit(left);
        }
        return "void";
    }

    private void markExternalInput(String varName, FunctionCall call) {
        if (call.getChildren().isEmpty()) return;
        AstNode callee = call.getChildren().get(0);

        if (callee instanceof AttributeAccess) {
            AttributeAccess attr = (AttributeAccess) callee;
            if ("get".equals(attr.getAttributeName())) {
                AstNode target = attr.getTarget();
                if (target instanceof AttributeAccess) {
                    AttributeAccess inner = (AttributeAccess) target;
                    if ("form".equals(inner.getAttributeName())) {
                        externalInputVars.add(varName);
                    }
                }
            }
        }
    }


    private String visitIfStatement(IfStatement node) {
        List<AstNode> allChildren = node.getChildren();
        if (allChildren.isEmpty()) return "void";

        AstNode condition = allChildren.get(0);

        if (condition != null) {
            if (condition instanceof NumberLiteral) {
                reportError(
                        "Condition type error: numeric literal '"
                                + ((NumberLiteral) condition).getValue()
                                + "' used directly as if-condition. "
                                + "Did you mean a comparison expression?",
                        condition.getLine());
            } else {
                String condType = visit(condition);
                if ("None".equals(condType)) {
                    reportError(
                            "Condition type error: condition evaluates to 'None', "
                                    + "which is always False. Check your logic.",
                            condition.getLine());
                }
            }
        }

        scopes.enterScope("If@L" + node.getLine());
        for (AstNode stmt : node.getIfBody()) visit(stmt);
        scopes.exitScope();

        for (AstNode child : allChildren) {
            if (node.getIfBody().contains(child) || child == condition) continue;
            scopes.enterScope(child.getNodeName() + "@L" + child.getLine());
            for (AstNode stmt : child.getChildren()) visit(stmt);
            scopes.exitScope();
        }
        return "void";
    }


    private String visitForStatement(ForStatement node) {
        AstNode iterableNode = node.getChildren().size() > 1
                ? node.getChildren().get(1) : null;

        String iterableType = visit(iterableNode);

        if (!isIterable(iterableType)) {
            reportError(
                    "Loop validation error: cannot iterate over type '"
                            + iterableType + "'. "
                            + "Expected List, Dict, or String — got a non-iterable type.",
                    node.getLine());
        }

        scopes.enterScope("For@L" + node.getLine());
        scopes.define(node.getIteratorId(), "Variable", "Any", node.getLine());
        for (AstNode stmt : node.getBody()) visit(stmt);
        scopes.exitScope();
        return "void";
    }

    private String visitReturnStatement(ReturnStatement node) {
        int line = node.getLine();

        if (functionDepth == 0) {
            reportError(
                    "Control flow error: 'return' statement found outside "
                            + "of any function definition.",
                    line);
            return "void";
        }

        String returnedType = "None";
        if (!node.getChildren().isEmpty()) {
            returnedType = visit(node.getChildren().get(0));
        }

        if (!currentFunctionName.isEmpty()) {
            String enclosing = currentFunctionName.peek();
            String declaredType = functionReturnType.getOrDefault(enclosing, "Any");

            if (!"Any".equals(declaredType)
                    && !"Any".equals(returnedType)
                    && !typesCompatible(declaredType, returnedType)) {
                reportError(
                        "Return type mismatch in function '" + enclosing
                                + "': declared return type is '" + declaredType
                                + "' but the return expression has type '"
                                + returnedType + "'.",
                        line);
            }
        }
        return "void";
    }

    private String visitImportStatement(ImportStatement node) {
        List<String> importedNames = new ArrayList<>();
        for (AstNode child : node.getChildren()) {
            if (child instanceof Identifier) {
                importedNames.add(((Identifier) child).getName());
            }
        }
        scopes.registerFlaskImports(importedNames, node.getLine());
        return "void";
    }

    private String visitBinaryExpression(BinaryExpression node) {
        String operator = node.getOperator();
        AstNode leftChild = node.getLeft();
        AstNode rightChild = node.getRight();

        String leftType = visit(leftChild);
        String rightType = visit(rightChild);

        if (isComparisonOp(operator) || isLogicalOp(operator)) {
            if ("None".equals(leftType) || "None".equals(rightType)) {
                reportError(
                        "Type error: comparing with 'None' using '" + operator
                                + "' may lead to unexpected behavior. "
                                + "Use 'is None' or 'is not None' for None checks.",
                        node.getLine());
            }
            return "Bool";
        }

        if (operator.equals("+")) {
            boolean leftIsString = "String".equals(leftType);
            boolean rightIsString = "String".equals(rightType);
            boolean leftIsNumeric = "Int".equals(leftType) || "Float".equals(leftType);
            boolean rightIsNumeric = "Int".equals(rightType) || "Float".equals(rightType);

            if (leftIsString && rightIsString) return "String"; // OK: concat

            if ((leftIsNumeric && rightIsString) || (leftIsString && rightIsNumeric)) {
                String example = leftIsNumeric
                        ? leftType + " + \""
                        + (rightChild instanceof StringLiteral
                        ? ((StringLiteral) rightChild).getValue() : "String")
                        + "\""
                        : "\""
                        + (leftChild instanceof StringLiteral
                        ? ((StringLiteral) leftChild).getValue() : "String")
                        + "\" + " + rightType;

                reportError(
                        "Type mismatch: cannot use operator '+' with '"
                                + leftType + "' and '" + rightType
                                + "'. Mixed type addition is not allowed (e.g., "
                                + example + "). "
                                + "Use str() to convert the number to String for concatenation, "
                                + "or ensure both operands are the same type.",
                        node.getLine());
                return "Any";
            }
        }

        if (!isArithmeticOp(operator) && !typesCompatible(leftType, rightType)) {
            reportError(
                    "Type mismatch: incompatible types '" + leftType
                            + "' and '" + rightType
                            + "' for operator '" + operator + "'.",
                    node.getLine());
        }

        if (isArithmeticOp(operator)) {
            if ("Bool".equals(leftType) || "Bool".equals(rightType)) {
                reportError(
                        "Type error: 'Bool' cannot be used in arithmetic expression ("
                                + leftType + " " + operator + " " + rightType + "). "
                                + "Use a numeric type instead.",
                        node.getLine());
                return "Any";
            }
            if (!operator.equals("+")) {
                if ("String".equals(leftType) || "String".equals(rightType)) {
                    reportError(
                            "Type error: 'String' cannot be used with operator '"
                                    + operator + "' ("
                                    + leftType + " " + operator + " " + rightType + "). "
                                    + "Only '+' is valid for String concatenation.",
                            node.getLine());
                    return "Any";
                }
            }
            if ("None".equals(leftType) || "None".equals(rightType)) {
                reportError(
                        "Type error: 'None' cannot be used in arithmetic expression ("
                                + leftType + " " + operator + " " + rightType + "). "
                                + "Check that the variable has been assigned a value.",
                        node.getLine());
                return "Any";
            }
            if ("Float".equals(leftType) || "Float".equals(rightType)) return "Float";
            if ("Int".equals(leftType) && "Int".equals(rightType)) return "Int";
            return "Any";
        }

        return "Any";
    }


    private String visitIdentifier(Identifier node) {
        String name = node.getName();
        ScopeManager.TypeInfo info = scopes.lookup(name);

        if (info == null) {
            reportError(
                    "Undefined error: identifier '" + name
                            + "' is used before it is declared. "
                            + "Make sure the variable or function is defined before use.",
                    node.getLine());
            return "Any";
        }

        if ("FlaskAPI".equals(info.kind)
                && !scopes.isImportCheckSuppressed()
                && !scopes.isFlaskNameImported(name)) {
            reportError(
                    "Missing Flask import: '" + name
                            + "' is a Flask API name but 'from flask import " + name
                            + "' was never written. Add the import statement at the top of the file.",
                    node.getLine());
        }

        return info.type;
    }

    private String visitFunctionCall(FunctionCall node) {
        if (node.getChildren().isEmpty()) return "Any";

        AstNode callee = node.getChildren().get(0);
        String calleeName = null;

        if (callee instanceof Identifier) {
            calleeName = ((Identifier) callee).getName();
            ScopeManager.TypeInfo info = scopes.lookup(calleeName);

            if (info != null
                    && "FlaskAPI".equals(info.kind)
                    && !scopes.isImportCheckSuppressed()
                    && !scopes.isFlaskNameImported(calleeName)) {
                reportError(
                        "Missing Flask import: '" + calleeName
                                + "' is a Flask API function but 'from flask import "
                                + calleeName + "' was never written.",
                        node.getLine());
            }

            if (info == null) {
                reportError(
                        "Undefined error: call to undeclared function '"
                                + calleeName + "'. "
                                + "Make sure the function is defined or imported before calling it.",
                        node.getLine());
            }
        } else {
            visit(callee);
            calleeName = extractCalleeName(callee);
        }

        List<AstNode> argNodes = new ArrayList<>();
        for (int i = 1; i < node.getChildren().size(); i++) {
            argNodes.add(node.getChildren().get(i));
        }

        if (calleeName != null && functionParamCount.containsKey(calleeName)) {
            int expected = functionParamCount.get(calleeName);
            int actual = argNodes.size();
            if (actual != expected) {
                reportError(
                        "Arity error: function '" + calleeName
                                + "' expects " + expected + " argument(s) "
                                + "but " + actual + " were provided.",
                        node.getLine());
            }
        }

        for (int i = 0; i < argNodes.size(); i++) {
            String argType = visit(argNodes.get(i));
            if (calleeName != null) {
                String paramType = functionParamTypes.get(calleeName + "#" + i);
                if (paramType != null
                        && !"Any".equals(paramType)
                        && !"Any".equals(argType)
                        && !typesCompatible(paramType, argType)) {
                    reportError(
                            "Argument type mismatch: argument " + (i + 1)
                                    + " of function '" + calleeName
                                    + "' expects type '" + paramType
                                    + "' but received '" + argType + "'.",
                            argNodes.get(i).getLine());
                }
            }
        }

        if (calleeName != null && isTypeConversionFunction(calleeName)) {
            checkTypeConversionSafety(calleeName, argNodes, node.getLine());
        }

        if ("render_template".equals(calleeName)) {
            checkRenderTemplateCall(argNodes, node.getLine());
        }

        return "Any";
    }

    private String extractCalleeName(AstNode callee) {
        if (callee instanceof Identifier) return ((Identifier) callee).getName();
        if (callee instanceof AttributeAccess)
            return ((AttributeAccess) callee).getAttributeName();
        return null;
    }

    private String visitDecorator(Decorator node) {
        String name = node.getName();
        int line = node.getLine();
        if (name.contains(".")) {
            String obj = name.substring(0, name.indexOf('.'));
            if (scopes.lookup(obj) == null) {
                reportError(
                        "Declaration error: decorator object '" + obj
                                + "' is not declared. Did you forget 'app = Flask(__name__)'?",
                        line);
            }
        }

        String routePath = null;
        List<String> httpMethods = new ArrayList<>();

        for (AstNode child : node.getChildren()) {
            if (child instanceof StringLiteral && routePath == null) {
                routePath = ((StringLiteral) child).getValue();
            }
            if (child instanceof KeywordArgument) {
                KeywordArgument kw = (KeywordArgument) child;
                if ("methods".equals(kw.getKey())) {
                    AstNode val = kw.getValue();
                    if (val instanceof ListLiteral) {
                        for (AstNode item : val.getChildren()) {
                            if (item instanceof StringLiteral) {
                                httpMethods.add(((StringLiteral) item).getValue());
                            }
                        }
                    }
                }
            }
        }

        if (routePath != null) {
            String existing = registeredRoutes.get(routePath);
            if (existing != null) {
                reportError(
                        "Duplicate route: path '" + routePath
                                + "' is already registered by function '" + existing
                                + "'. Each route path must be unique.",
                        line);
            } else {
                String owner = currentFunctionName.isEmpty()
                        ? "<global>" : currentFunctionName.peek();
                registeredRoutes.put(routePath, owner);

                List<String> segments = extractDynamicSegments(routePath);
                if (!segments.isEmpty() && !currentFunctionName.isEmpty()) {
                    routeDynamicParams.put(currentFunctionName.peek(), segments);
                }
            }
        }

        Set<String> validMethods = new HashSet<>(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"));
        for (String method : httpMethods) {
            if (!validMethods.contains(method.toUpperCase())) {
                reportError(
                        "Invalid HTTP method '" + method
                                + "' in @" + name + " decorator. "
                                + "Valid methods: GET, POST, PUT, DELETE, PATCH.",
                        line);
            }
        }

        return "void";
    }


    private List<String> extractDynamicSegments(String routePath) {
        List<String> result = new ArrayList<>();
        Matcher m = ROUTE_PARAM_PATTERN.matcher(routePath);
        while (m.find()) {
            result.add(m.group(1));
        }
        return result;
    }


    private void checkRenderTemplateCall(List<AstNode> args, int line) {
        if (args.isEmpty()) return;

        AstNode templateArg = args.get(0);
        String templateFile;

        if (templateArg instanceof StringLiteral) {
            templateFile = ((StringLiteral) templateArg).getValue();
        } else {
            reportError(
                    "Template path error: render_template() called with a non-literal "
                            + "template name. Cannot verify file existence at compile time. "
                            + "Use a string literal for the template name.",
                    line);
            return;
        }

        // Record reference line (first call wins for error reporting)
        referencedTemplates.putIfAbsent(templateFile, line);

        // FIX 6 – build a snapshot of variables passed by THIS call site
        Set<String> snapshot = new LinkedHashSet<>();
        for (int i = 1; i < args.size(); i++) {
            AstNode arg = args.get(i);
            if (arg instanceof KeywordArgument) {
                snapshot.add(((KeywordArgument) arg).getKey());
            }
        }
        templatePassedVarsPerCall
                .computeIfAbsent(templateFile, k -> new ArrayList<>())
                .add(snapshot);
    }


    private boolean isTypeConversionFunction(String name) {
        return "float".equals(name) || "int".equals(name)
                || "bool".equals(name) || "str".equals(name)
                || "list".equals(name) || "dict".equals(name);
    }

    private void checkTypeConversionSafety(String funcName, List<AstNode> args, int line) {
        if (args.isEmpty() || "str".equals(funcName)) return;

        AstNode firstArg = args.get(0);

        if (firstArg instanceof FunctionCall) {
            FunctionCall inner = (FunctionCall) firstArg;
            if (!inner.getChildren().isEmpty()) {
                String innerName = extractCalleeName(inner.getChildren().get(0));
                if ("get".equals(innerName)) {
                    reportError(
                            "Type error: unsafe type conversion '"
                                    + funcName + "(request.form.get(...))'."
                                    + " The form input may not be a valid "
                                    + funcName + " value."
                                    + " Add a try/except block or validate first.",
                            line);
                    return;
                }
            }
        }

        if (firstArg instanceof Identifier) {
            String varName = ((Identifier) firstArg).getName();
            if (externalInputVars.contains(varName)) {
                reportWarning(
                        "Type warning: potentially unsafe conversion '"
                                + funcName + "(" + varName + ")'. The variable '"
                                + varName + "' comes from request.form and may not be "
                                + "a valid " + funcName + " value. Consider validating first.",
                        line);
                return;
            }
        }

        String argType = inferType(firstArg);
        if ("int".equals(funcName) && "String".equals(argType)) {
            reportWarning(
                    "Type warning: converting String to Int may raise ValueError. "
                            + "Use try/except to handle potential errors.",
                    line);
        } else if ("float".equals(funcName) && "String".equals(argType)) {
            reportWarning(
                    "Type warning: converting String to Float may raise ValueError. "
                            + "Use try/except to handle potential errors.",
                    line);
        }
    }


    private String visitAttributeAccess(AttributeAccess node) {
        AstNode target = node.getTarget();
        String targetType = visit(target);

        if ("None".equals(targetType)) {
            reportError(
                    "Type error: attribute access '."
                            + node.getAttributeName()
                            + "' on a 'None' value will always raise an error at runtime.",
                    node.getLine());
        } else if ("Int".equals(targetType)
                || "Float".equals(targetType)
                || "Bool".equals(targetType)) {
            reportError(
                    "Type error: attribute access '."
                            + node.getAttributeName()
                            + "' on type '" + targetType + "' is not supported. "
                            + "Did you mean to use a variable or object?",
                    node.getLine());
        }

        return "Any";
    }

    private String visitSubscript(Subscript node) {
        if (node.getChildren().size() < 2) return "Any";

        AstNode targetNode = node.getChildren().get(0);
        AstNode indexNode = node.getChildren().get(1);
        String targetType = visit(targetNode);
        String indexType = visit(indexNode);

        if (!isSubscriptable(targetType)) {
            reportError(
                    "Type error: type '" + targetType
                            + "' does not support subscript indexing (e.g. x[i]). "
                            + "Expected List, Dict, or String.",
                    node.getLine());
        }

        if ("List".equals(targetType)
                && !"Int".equals(indexType)
                && !"Any".equals(indexType)) {
            reportError(
                    "Type error: List index must be an integer (Int), "
                            + "but got '" + indexType + "'. "
                            + "Use an integer variable or literal to index a List.",
                    node.getLine());
        }

        return "Any";
    }


    private String visitListLiteral(ListLiteral node) {
        for (AstNode child : node.getChildren()) visit(child);
        return "List";
    }

    private String visitDictLiteral(DictLiteral node) {
        for (AstNode child : node.getChildren()) {
            if ("Entry".equals(child.getNodeName())
                    && child.getChildren().size() >= 2) {
                AstNode key = child.getChildren().get(0);
                AstNode value = child.getChildren().get(1);
                String keyType = visit(key);
                visit(value);
                if ("List".equals(keyType) || "Dict".equals(keyType)) {
                    reportError(
                            "Type error: unhashable type '" + keyType
                                    + "' used as dictionary key. "
                                    + "Only immutable types (String, Int, Float, Bool, None) "
                                    + "can be used as dictionary keys.",
                            key.getLine());
                }
            } else {
                visit(child);
            }
        }
        return "Dict";
    }

    private String inferNumberType(NumberLiteral node) {
        return node.getValue().contains(".") ? "Float" : "Int";
    }

    private String inferType(AstNode node) {
        if (node instanceof NumberLiteral) return inferNumberType((NumberLiteral) node);
        if (node instanceof StringLiteral) return "String";
        if (node instanceof BooleanLiteral) return "Bool";
        if (node instanceof NoneLiteral) return "None";
        if (node instanceof ListLiteral) return "List";
        if (node instanceof DictLiteral) return "Dict";
        if (node instanceof Identifier) {
            ScopeManager.TypeInfo info =
                    scopes.lookup(((Identifier) node).getName());
            return info != null ? info.type : "Any";
        }
        return "Any";
    }

    private boolean typesCompatible(String lhs, String rhs) {
        if ("Any".equals(lhs) || "Any".equals(rhs)) return true;
        if (lhs.equals(rhs)) return true;
        boolean lNum = "Int".equals(lhs) || "Float".equals(lhs);
        boolean rNum = "Int".equals(rhs) || "Float".equals(rhs);
        return lNum && rNum;
    }

    private boolean isArithmeticOp(String op) {
        return "+".equals(op) || "-".equals(op) || "*".equals(op)
                || "/".equals(op) || "%".equals(op) || "//".equals(op)
                || "**".equals(op);
    }

    private boolean isComparisonOp(String op) {
        return "==".equals(op) || "!=".equals(op) || "<".equals(op)
                || ">".equals(op) || "<=".equals(op) || ">=".equals(op)
                || "in".equals(op) || "not in".equals(op) || "is".equals(op);
    }

    private boolean isLogicalOp(String op) {
        return "and".equals(op) || "or".equals(op) || "not".equals(op);
    }

    private boolean isSubscriptable(String type) {
        return "List".equals(type) || "Dict".equals(type)
                || "String".equals(type) || "Any".equals(type);
    }

    private boolean isIterable(String type) {
        return "List".equals(type) || "Dict".equals(type)
                || "String".equals(type) || "Any".equals(type);
    }

    private boolean isTemplateBuiltin(String name) {
        return Set.of(
                "range", "lipsum", "dict", "cycler", "joiner", "namespace",
                "true", "false", "none", "True", "False", "None",
                "loop", "block", "endblock", "extends", "include",
                "set", "import", "from", "as", "do", "macro", "call",
                "filter", "endfilter", "raw", "endraw",
                "super", "caller", "varargs", "kwargs"
        ).contains(name);
    }

    private void reportError(String message, int line) {
        String key = "ERR:" + line + ":" + message;
        if (reportedErrors.contains(key)) return;
        reportedErrors.add(key);
        SemanticError err = new SemanticError(message, line, SemanticError.Severity.ERROR);
        errors.add(err);
        System.err.println("  ✗ " + err.getMessage());
    }

    private void reportWarning(String message, int line) {
        String key = "WARN:" + line + ":" + message;
        if (reportedErrors.contains(key)) return;
        reportedErrors.add(key);
        SemanticError warn = new SemanticError(message, line, SemanticError.Severity.WARNING);
        errors.add(warn);
        System.err.println("  ⚠ " + warn.getMessage());
    }
}