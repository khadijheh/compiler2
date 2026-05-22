package semantic;

import AST.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SemanticAnalyzerVisitor {

    private final ScopeManager scopes;
    private final List<SemanticError> errors = new ArrayList<>();

    private final Map<String, String> registeredRoutes = new HashMap<>();

    private int functionDepth = 0;

    private final Map<String, Integer> functionParamCount = new HashMap<>();

    private final Map<String, String> functionParamTypes = new HashMap<>();

    private final Map<String, String> functionReturnType = new HashMap<>();

    private final Deque<String> currentFunctionName = new ArrayDeque<>();

    private final String templateDirectory;

    private final Map<String, Integer> referencedTemplates = new LinkedHashMap<>();

    private final Map<String, Set<String>> templateJinjaVars = new LinkedHashMap<>();

    private final Set<String> templateLoopIterators = new HashSet<>();

    private final Map<String, Set<String>> templatePassedVars = new LinkedHashMap<>();

    private final Set<String> externalInputVars = new HashSet<>();


    private final Set<String> reportedErrors = new HashSet<>();

    public SemanticAnalyzerVisitor(ScopeManager scopes) {
        this(scopes, "Files");
    }

    public SemanticAnalyzerVisitor(ScopeManager scopes, String templateDirectory) {
        this.scopes = scopes;
        this.templateDirectory = templateDirectory;
    }


    public void analyse(AstNode root) {
        boolean hasImportNode = containsImportStatement(root);
        if (!hasImportNode) {
            scopes.assumeFlaskImported();
        }

        visit(root);

        verifyTemplateFilesExist();

        verifyJinjaVariablesPassed();

        printReport();
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
                        "Missing Flask variable: template file '" + templateFile
                                + "' referenced in render_template() does not exist at path '"
                                + fullPath + "'. "
                                + "Ensure the file is in the correct templates directory.",
                        line);
            } else {
                // Template exists — scan it for Jinja variables
                scanTemplateForJinjaVars(templateFile, fullPath);
            }
        }
    }


    private void scanTemplateForJinjaVars(String templateFile, String fullPath) {
        Set<String> vars = new LinkedHashSet<>();
        try {
            String content = Files.readString(Paths.get(fullPath));

            java.util.regex.Matcher forMatcher = java.util.regex.Pattern
                    .compile("\\{%[-\\s]*for\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s+in")
                    .matcher(content);
            while (forMatcher.find()) {
                String loopVar = forMatcher.group(1);
                templateLoopIterators.add(loopVar);
            }

            java.util.regex.Matcher exprMatcher = java.util.regex.Pattern
                    .compile("\\{\\{\\s*([a-zA-Z_][a-zA-Z0-9_]*)")
                    .matcher(content);
            while (exprMatcher.find()) {
                String varName = exprMatcher.group(1);
                if (!isTemplateBuiltin(varName) && !templateLoopIterators.contains(varName)) {
                    vars.add(varName);
                }
            }

            // Match {% if var %} patterns
            java.util.regex.Matcher ifMatcher = java.util.regex.Pattern
                    .compile("\\{%[-\\s]*if\\s+([a-zA-Z_][a-zA-Z0-9_]*)")
                    .matcher(content);
            while (ifMatcher.find()) {
                String varName = ifMatcher.group(1);
                // Skip builtins and loop iterators
                if (!isTemplateBuiltin(varName) && !templateLoopIterators.contains(varName)) {
                    vars.add(varName);
                }
            }

            // Also extract iterable names from {% for var in ITERABLE %}
            // The iterable must be passed from Flask
            java.util.regex.Matcher iterMatcher = java.util.regex.Pattern
                    .compile("\\{%[-\\s]*for\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s+in\\s+([a-zA-Z_][a-zA-Z0-9_]*)")
                    .matcher(content);
            while (iterMatcher.find()) {
                String iterName = iterMatcher.group(1);
                if (!isTemplateBuiltin(iterName)) {
                    vars.add(iterName);
                }
            }

            templateJinjaVars.put(templateFile, vars);
        } catch (Exception e) {
        }
    }


    private boolean isTemplateBuiltin(String name) {
        return Set.of("range", "lipsum", "dict", "cycler", "joiner", "namespace",
                "true", "false", "none", "True", "False", "None",
                "loop", "block", "endblock", "extends", "include",
                "set", "import", "from", "as", "do", "macro", "call",
                "filter", "endfilter", "raw", "endraw").contains(name);
    }

    private void verifyJinjaVariablesPassed() {
        for (Map.Entry<String, Set<String>> entry : templateJinjaVars.entrySet()) {
            String templateFile = entry.getKey();
            Set<String> requiredVars = entry.getValue();
            Set<String> passedVars = templatePassedVars.getOrDefault(
                    templateFile, Collections.emptySet());

            for (String var : requiredVars) {
                if (templateLoopIterators.contains(var)) {
                    continue;
                }

                if (!passedVars.contains(var)) {
                    int line = referencedTemplates.getOrDefault(templateFile, 0);
                    reportError(
                            "Missing Flask variable: Jinja variable '" + var
                                    + "' is used in template '" + templateFile
                                    + "' but is not passed in render_template() call. "
                                    + "Add '" + var + "=<value>' to the render_template() arguments.",
                            line);
                }
            }
        }
    }


    private void printReport() {
        System.out.println();
        int w = 64;
        String bar = "═".repeat(w);

        // Separate errors and warnings
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
        } else {
            // ── Print WARNINGS first (non-fatal) ─────────────────────── //
            if (!warningList.isEmpty()) {
                System.out.println("╔" + bar + "╗");
                System.out.printf("║  %-" + (w - 2) + "s║%n",
                        "⚠  Semantic Analysis found " + warningList.size() + " warning(s)");
                System.out.println("╠" + bar + "╣");

                for (int i = 0; i < warningList.size(); i++) {
                    String msg = String.format("#%-2d  %s", i + 1, warningList.get(i).getMessage());
                    int chunk = w - 4;
                    for (int pos = 0; pos < msg.length(); pos += chunk) {
                        String part = msg.substring(pos, Math.min(pos + chunk, msg.length()));
                        System.out.printf("║  %-" + (w - 2) + "s║%n", part);
                    }
                    if (i < warningList.size() - 1) {
                        System.out.println("╟" + "─".repeat(w) + "╢");
                    }
                }
                System.out.println("╚" + bar + "╝");
                System.out.println();
            }

            // ── Print ERRORS (fatal) ───────────────────────────────── //
            if (!errorList.isEmpty()) {
                System.out.println("╔" + bar + "╗");
                System.out.printf("║  %-" + (w - 2) + "s║%n",
                        "✘  Semantic Analysis found " + errorList.size() + " error(s)");
                System.out.println("╠" + bar + "╣");

                for (int i = 0; i < errorList.size(); i++) {
                    String msg = String.format("#%-2d  %s", i + 1, errorList.get(i).getMessage());
                    int chunk = w - 4;
                    for (int pos = 0; pos < msg.length(); pos += chunk) {
                        String part = msg.substring(pos, Math.min(pos + chunk, msg.length()));
                        System.out.printf("║  %-" + (w - 2) + "s║%n", part);
                    }
                    if (i < errorList.size() - 1) {
                        System.out.println("╟" + "─".repeat(w) + "╢");
                    }
                }
                System.out.println("╚" + bar + "╝");

                // Only ERROR severity halts compilation
                throw new SemanticError(
                        errorList.size() + " semantic error(s) — see report above.", 0);
            }

            // Only warnings — compilation continues
            System.out.println("[Semantic Analysis] " + warningList.size()
                    + " warning(s) detected — compilation continues.");
        }
    }

    private String visit(AstNode node) {
        if (node == null) return "Any";

        // ── Statements ───────────────────────────────────────────────── //
        if (node instanceof Program) return visitProgram((Program) node);
        if (node instanceof FunctionDef) return visitFunctionDef((FunctionDef) node);
        if (node instanceof Assign) return visitAssign((Assign) node);
        if (node instanceof IfStatement) return visitIfStatement((IfStatement) node);
        if (node instanceof ForStatement) return visitForStatement((ForStatement) node);
        if (node instanceof ReturnStatement) return visitReturnStatement((ReturnStatement) node);
        if (node instanceof ImportStatement) return visitImportStatement((ImportStatement) node);

        // ── Expressions ──────────────────────────────────────────────── //
        if (node instanceof BinaryExpression) return visitBinaryExpression((BinaryExpression) node);
        if (node instanceof Identifier) return visitIdentifier((Identifier) node);
        if (node instanceof FunctionCall) return visitFunctionCall((FunctionCall) node);
        if (node instanceof AttributeAccess) return visitAttributeAccess((AttributeAccess) node);
        if (node instanceof Subscript) return visitSubscript((Subscript) node);

        // ── Literals ─────────────────────────────────────────────────── //
        if (node instanceof NumberLiteral) return inferNumberType((NumberLiteral) node);
        if (node instanceof StringLiteral) return "String";
        if (node instanceof BooleanLiteral) return "Bool";
        if (node instanceof NoneLiteral) return "None";
        if (node instanceof ListLiteral) return visitListLiteral((ListLiteral) node);
        if (node instanceof DictLiteral) return visitDictLiteral((DictLiteral) node);
        if (node instanceof KeywordArgument) return visit(((KeywordArgument) node).getValue());

        if (node instanceof Decorator) return visitDecorator((Decorator) node);

        // ── Generic fallthrough ──────────────────────────────────────── //
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
        String returnType = node.getReturnType();

        // ── CHECK 2: Duplicate function declaration ───────────────────── //
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

        // ── CHECK 3: Enter function scope ────────────────────────────── //
        scopes.enterScope("Function(" + name + ")");
        functionDepth++;
        currentFunctionName.push(name);

        if (node.getParameters() != null) {
            for (int i = 0; i < node.getParameters().size(); i++) {
                String param = node.getParameters().get(i);
                String paramType = node.getParamType(i);

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

        if (node.getBody() != null) {
            for (AstNode stmt : node.getBody()) visit(stmt);
        }
        for (AstNode child : node.getChildren()) {
            if (child instanceof Decorator) {
                visit(child);
            }
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

            // ── Track external input variables ─────────────────────── //
            // If RHS is a call to request.form.get(), mark this var as
            // external input so we can warn about unsafe conversions later.
            if (right instanceof FunctionCall) {
                markExternalInput(idName, (FunctionCall) right);
            } else if (right instanceof AttributeAccess) {
                // e.g. request.form (the .get call is on the next trailer)
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
            if (attr.getAttributeName().equals("get")) {
                AstNode target = attr.getTarget();
                if (target instanceof AttributeAccess) {
                    AttributeAccess innerAttr = (AttributeAccess) target;
                    if (innerAttr.getAttributeName().equals("form")) {
                        externalInputVars.add(varName);
                    }
                }
            }
        }
    }

    private String visitIfStatement(IfStatement node) {
        List<AstNode> allChildren = node.getChildren();
        if (allChildren.isEmpty()) return "void";

        AstNode firstChild = allChildren.get(0);
        boolean firstIsBody = node.getIfBody().contains(firstChild);

        if (!firstIsBody) {
            if (firstChild instanceof NumberLiteral) {
                reportError(
                        "Condition type error: numeric literal '"
                                + ((NumberLiteral) firstChild).getValue()
                                + "' used directly as if-condition. "
                                + "Did you mean a comparison expression?",
                        firstChild.getLine());
            } else if (firstChild instanceof StringLiteral) {
                // Parser limitation — not flagged
            } else {
                String condType = visit(firstChild);
                if (condType.equals("None")) {
                    reportError(
                            "Condition type error: condition evaluates to 'None', "
                                    + "which is always False. Check your logic.",
                            firstChild.getLine());
                }
            }
        }

        scopes.enterScope("If@L" + node.getLine());
        for (AstNode stmt : node.getIfBody()) visit(stmt);
        scopes.exitScope();

        int startIdx = firstIsBody ? 0 : 1;
        for (int i = startIdx; i < allChildren.size(); i++) {
            AstNode child = allChildren.get(i);
            if (node.getIfBody().contains(child)) continue;

            scopes.enterScope(child.getNodeName() + "@L" + child.getLine());
            for (AstNode stmt : child.getChildren()) {
                visit(stmt);
            }
            scopes.exitScope();
        }
        return "void";
    }


    private String visitForStatement(ForStatement node) {
        AstNode iterableNode = node.getChildren().size() > 1
                ? node.getChildren().get(1) : null;

        String iterableType = visit(iterableNode);

        // ── CHECK 10: Loop / iterable validation ─────────────────────── //
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

            if (!declaredType.equals("Any")
                    && !returnedType.equals("Any")
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

            if (leftType.equals("None") || rightType.equals("None")) {
                reportError(
                        "Type error: comparing with 'None' using '" + operator
                                + "' may lead to unexpected behavior. "
                                + "Use 'is None' or 'is not None' for None checks.",
                        node.getLine());
            }
            return "Bool";
        }

        // ── ENHANCED: Check for Int + String or String + Int ────────── //
        // e.g. 22 + "kh" → Type Mismatch Error
        if (operator.equals("+")) {
            boolean leftIsString = leftType.equals("String");
            boolean rightIsString = rightType.equals("String");
            boolean leftIsNumeric = leftType.equals("Int") || leftType.equals("Float");
            boolean rightIsNumeric = rightType.equals("Int") || rightType.equals("Float");

            if (leftIsString && rightIsString) {
                // String + String = concatenation → OK
                return "String";
            }

            if ((leftIsString && rightIsNumeric) || (leftIsNumeric && leftIsString)) {
                // WAIT: we need to check the CORRECT sides
                // leftIsString && rightIsNumeric: "kh" + 22
                // leftIsNumeric && rightIsString: 22 + "kh"
            }

            // Correct check for Int/Float + String or String + Int/Float
            if ((leftIsNumeric && rightIsString) || (leftIsString && rightIsNumeric)) {
                // ── TYPE MISMATCH: 22 + "kh" ───────────────────────── //
                String example = leftIsNumeric
                        ? leftType + " + \"" + (rightChild instanceof StringLiteral
                        ? ((StringLiteral) rightChild).getValue() : "String") + "\""
                        : "\"" + (leftChild instanceof StringLiteral
                        ? ((StringLiteral) leftChild).getValue() : "String") + "\" + " + rightType;

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

        // ── Generic type compatibility check (non-arithmetic, non-comparison) ── //
        if (!isArithmeticOp(operator) && !typesCompatible(leftType, rightType)) {
            reportError(
                    "Type mismatch: incompatible types '" + leftType
                            + "' and '" + rightType + "' for operator '" + operator + "'.",
                    node.getLine());
        }

        if (isArithmeticOp(operator)) {

            // ── CHECK 4a: Bool in arithmetic ─────────────────────────── //
            if (leftType.equals("Bool") || rightType.equals("Bool")) {
                reportError(
                        "Type error: 'Bool' cannot be used in arithmetic expression ("
                                + leftType + " " + operator + " " + rightType + "). "
                                + "Use a numeric type instead.",
                        node.getLine());
                return "Any";
            }

            // ── CHECK 4b: String in non-concat arithmetic ─────────────── //
            // Only applies for operators other than +, since + is handled above
            if (!operator.equals("+")) {
                if (leftType.equals("String") || rightType.equals("String")) {
                    reportError(
                            "Type error: 'String' cannot be used with operator '"
                                    + operator + "' ("
                                    + leftType + " " + operator + " " + rightType + "). "
                                    + "Only '+' is valid for String concatenation.",
                            node.getLine());
                    return "Any";
                }
            }

            // ── NEW: None in arithmetic → error ────────────────────── //
            if (leftType.equals("None") || rightType.equals("None")) {
                reportError(
                        "Type error: 'None' cannot be used in arithmetic expression ("
                                + leftType + " " + operator + " " + rightType + "). "
                                + "Check that the variable has been assigned a value.",
                        node.getLine());
                return "Any";
            }

            // Float is contagious (Int + Float → Float).
            if (leftType.equals("Float") || rightType.equals("Float")) return "Float";
            if (leftType.equals("Int") && rightType.equals("Int")) return "Int";
            return "Any";
        }

        return "Any";
    }

    private String visitIdentifier(Identifier node) {
        String name = node.getName();
        ScopeManager.TypeInfo info = scopes.lookup(name);

        if (info == null) {
            // ── UNDEFINED ERROR ──────────────────────────────────────── //
            reportError(
                    "Undefined error: identifier '" + name
                            + "' is used before it is declared. "
                            + "Make sure the variable or function is defined before use.",
                    node.getLine());
            return "Any";
        }

        // ── CHECK: Flask API used without import ─────────────────────── //
        if (info.kind.equals("FlaskAPI") && !scopes.isFlaskImported()) {
            reportError(
                    "Missing Flask import: '" + name
                            + "' is a Flask API name but 'from flask import " + name
                            + "' was never written. Add the import at the top of the file.",
                    node.getLine());
        }

        // ── CHECK 12: Variable from external input used in type conversion ── //
        // This is checked at the FunctionCall level instead.

        return info.type;
    }


    private String visitFunctionCall(FunctionCall node) {
        if (node.getChildren().isEmpty()) return "Any";

        AstNode callee = node.getChildren().get(0);
        String calleeName = null;

        if (callee instanceof Identifier) {
            calleeName = ((Identifier) callee).getName();
            ScopeManager.TypeInfo info = scopes.lookup(calleeName);

            // ── CHECK 1: Undeclared function ─────────────────────────── //
            if (info == null) {
                reportError(
                        "Undefined error: call to undeclared function '"
                                + calleeName + "'. "
                                + "Make sure the function is defined or imported before calling it.",
                        node.getLine());
            }
        } else {
            visit(callee);
            // Try to extract callee name from AttributeAccess for render_template etc.
            calleeName = extractCalleeName(callee);
        }

        // Gather argument nodes (children 1 .. n).
        List<AstNode> argNodes = new ArrayList<>();
        for (int i = 1; i < node.getChildren().size(); i++) {
            argNodes.add(node.getChildren().get(i));
        }

        // ── CHECK 5: Argument count mismatch ─────────────────────────── //
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

        // ── CHECK 6: Argument type mismatch ──────────────────────────── //
        for (int i = 0; i < argNodes.size(); i++) {
            String argType = visit(argNodes.get(i));

            if (calleeName != null) {
                String paramType = functionParamTypes.get(calleeName + "#" + i);

                if (paramType != null
                        && !paramType.equals("Any")
                        && !argType.equals("Any")
                        && !typesCompatible(paramType, argType)) {
                    reportError(
                            "Argument type mismatch: argument " + (i + 1)
                                    + " of function '" + calleeName
                                    + "' expects type '" + paramType
                                    + "' but received '" + argType
                                    + "'. Check the call at this line.",
                            argNodes.get(i).getLine());
                }
            }
        }

        // ═══════════════════════════════════════════════════════════════ //
        //  CHECK 12: Type conversion validation                             //
        //  Detect unsafe calls like float(request.form.get("price"))       //
        // ═══════════════════════════════════════════════════════════════ //
        if (calleeName != null && isTypeConversionFunction(calleeName)) {
            checkTypeConversionSafety(calleeName, argNodes, node.getLine());
        }

        // ═══════════════════════════════════════════════════════════════ //
        //  CHECK 13 + 15: render_template validation                       //
        //  Verify template file exists and Jinja vars are passed           //
        // ═══════════════════════════════════════════════════════════════ //
        if (calleeName != null && calleeName.equals("render_template")) {
            checkRenderTemplateCall(argNodes, node.getLine());
        }

        return "Any";
    }

    private String extractCalleeName(AstNode callee) {
        if (callee instanceof Identifier) {
            return ((Identifier) callee).getName();
        }
        if (callee instanceof AttributeAccess) {
            return ((AttributeAccess) callee).getAttributeName();
        }
        return null;
    }


    private boolean isTypeConversionFunction(String name) {
        return name.equals("float") || name.equals("int")
                || name.equals("bool") || name.equals("str")
                || name.equals("list") || name.equals("dict");
    }

    private void checkTypeConversionSafety(String funcName, List<AstNode> args, int line) {
        if (args.isEmpty()) return;

        // str() is always safe — no warning needed
        if (funcName.equals("str")) return;

        AstNode firstArg = args.get(0);

        // Check if the argument is a call to request.form.get()
        // This is the MOST DANGEROUS pattern — report as ERROR
        if (firstArg instanceof FunctionCall) {
            FunctionCall innerCall = (FunctionCall) firstArg;
            if (!innerCall.getChildren().isEmpty()) {
                AstNode innerCallee = innerCall.getChildren().get(0);
                String innerName = extractCalleeName(innerCallee);
                if ("get".equals(innerName)) {
                    reportError(
                            "Type error: unsafe type conversion '" + funcName
                                    + "(request.form.get(...))'. "
                                    + "The form input may not be a valid " + funcName
                                    + " value. Add a try/except block or validation "
                                    + "before converting. E.g.: "
                                    + "try: price = " + funcName + "(value) except ValueError: ...",
                            line);
                    return;
                }
            }
        }

        // Check if the argument is a variable from external input
        // This is a LESS DANGEROUS pattern (developer used intermediate variable)
        // Report as WARNING — the developer may have added validation logic
        if (firstArg instanceof Identifier) {
            String varName = ((Identifier) firstArg).getName();
            if (externalInputVars.contains(varName)) {
                reportWarning(
                        "Type warning: potentially unsafe type conversion '" + funcName + "("
                                + varName + ")'. The variable '" + varName
                                + "' comes from external input (request.form) and may "
                                + "not be a valid " + funcName + " value. "
                                + "Consider adding validation before conversion, e.g.: "
                                + "if " + varName + " and " + varName + ".replace('.','').isdigit():",
                        line);
                return;
            }
        }

        // Check if converting from an incompatible type (static)
        String argType = inferType(firstArg);
        if (funcName.equals("int") && argType.equals("String")) {
            reportWarning(
                    "Type warning: cannot convert String to Int implicitly. "
                            + "The string value may not represent a valid integer. "
                            + "Use try/except to handle potential ValueError.",
                    line);
        } else if (funcName.equals("float") && argType.equals("String")) {
            reportWarning(
                    "Type warning: cannot safely convert String to Float. "
                            + "The string value may not represent a valid number. "
                            + "Use try/except to handle potential ValueError.",
                    line);
        }
    }


    private String inferType(AstNode node) {
        if (node instanceof NumberLiteral) return inferNumberType((NumberLiteral) node);
        if (node instanceof StringLiteral) return "String";
        if (node instanceof BooleanLiteral) return "Bool";
        if (node instanceof NoneLiteral) return "None";
        if (node instanceof ListLiteral) return "List";
        if (node instanceof DictLiteral) return "Dict";
        if (node instanceof Identifier) {
            ScopeManager.TypeInfo info = scopes.lookup(((Identifier) node).getName());
            return info != null ? info.type : "Any";
        }
        return "Any";
    }

    private void checkRenderTemplateCall(List<AstNode> args, int line) {
        if (args.isEmpty()) return;

        // First argument should be the template filename (StringLiteral)
        AstNode templateArg = args.get(0);
        String templateFile = null;

        if (templateArg instanceof StringLiteral) {
            templateFile = ((StringLiteral) templateArg).getValue();
        } else {
            // Dynamic template name — can't verify at compile time
            reportError(
                    "Missing Flask variable: render_template() called with "
                            + "a non-literal template name. Cannot verify template "
                            + "file existence at compile time. Use a string literal "
                            + "for the template name.",
                    line);
            return;
        }

        // Record the template reference for post-analysis verification
        referencedTemplates.put(templateFile, line);

        // Track variables passed to this template
        Set<String> passedVars = templatePassedVars.computeIfAbsent(
                templateFile, k -> new LinkedHashSet<>());

        for (int i = 1; i < args.size(); i++) {
            AstNode arg = args.get(i);
            if (arg instanceof KeywordArgument) {
                KeywordArgument kw = (KeywordArgument) arg;
                passedVars.add(kw.getKey());
            }
        }
    }


    private String visitAttributeAccess(AttributeAccess node) {
        AstNode target = node.getTarget();
        String targetType = visit(target);

        if (targetType.equals("None")) {
            reportError(
                    "Type error: attribute access '."
                            + node.getAttributeName()
                            + "' on a 'None' value will always raise an error at runtime.",
                    node.getLine());
        } else if (targetType.equals("Int")
                || targetType.equals("Float")
                || targetType.equals("Bool")) {
            reportError(
                    "Type error: attribute access '."
                            + node.getAttributeName()
                            + "' on type '" + targetType
                            + "' is not supported. "
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

        if (targetType.equals("List")
                && !indexType.equals("Int")
                && !indexType.equals("Any")) {
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
            if (child.getNodeName().equals("Entry") && child.getChildren().size() >= 2) {
                AstNode key = child.getChildren().get(0);
                AstNode value = child.getChildren().get(1);

                String keyType = visit(key);
                visit(value);


                if (keyType.equals("List") || keyType.equals("Dict")) {
                    reportError(
                            "Type error: unhashable type '" + keyType
                                    + "' used as dictionary key. "
                                    + "Only immutable types (String, Int, Float, Bool, None, Tuple) "
                                    + "can be used as dictionary keys.",
                            key.getLine());
                }
            } else {
                visit(child);
            }
        }
        return "Dict";
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

        // ── CHECK D1: Route path uniqueness ──────────────────────────── //
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
            }
        }

        // ── CHECK D2: HTTP method names ───────────────────────────────── //
        Set<String> validMethods = new HashSet<>(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"));
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


    private String inferNumberType(NumberLiteral node) {
        return node.getValue().contains(".") ? "Float" : "Int";
    }

    private boolean typesCompatible(String lhs, String rhs) {
        if (lhs.equals("Any") || rhs.equals("Any")) return true;
        if (lhs.equals(rhs)) return true;
        boolean lhsNum = lhs.equals("Int") || lhs.equals("Float");
        boolean rhsNum = rhs.equals("Int") || rhs.equals("Float");
        return lhsNum && rhsNum; // Int ↔ Float widening only.
    }

    private boolean isArithmeticOp(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*")
                || op.equals("/") || op.equals("%") || op.equals("//")
                || op.equals("**");
    }

    private boolean isComparisonOp(String op) {
        return op.equals("==") || op.equals("!=") || op.equals("<")
                || op.equals(">") || op.equals("<=") || op.equals(">=")
                || op.equals("in") || op.equals("not in") || op.equals("is");
    }

    private boolean isLogicalOp(String op) {
        return op.equals("and") || op.equals("or") || op.equals("not");
    }

    private boolean isSubscriptable(String type) {
        return type.equals("List") || type.equals("Dict")
                || type.equals("String") || type.equals("Any");
    }

    private boolean isIterable(String type) {
        return type.equals("List") || type.equals("Dict")
                || type.equals("String") || type.equals("Any");
    }


    private void reportError(String message, int line) {
        // Deduplicate errors to prevent double-reporting
        String errorKey = "ERROR:" + message + "@" + line;
        if (reportedErrors.contains(errorKey)) return;
        reportedErrors.add(errorKey);

        SemanticError err = new SemanticError(message, line, SemanticError.Severity.ERROR);
        errors.add(err);
        System.err.println("  ✗ " + err.getMessage());
    }


    private void reportWarning(String message, int line) {
        // Deduplicate warnings to prevent double-reporting
        String errorKey = "WARNING:" + message + "@" + line;
        if (reportedErrors.contains(errorKey)) return;
        reportedErrors.add(errorKey);

        SemanticError warn = new SemanticError(message, line, SemanticError.Severity.WARNING);
        errors.add(warn);
        System.err.println("  ⚠ " + warn.getMessage());
    }
}
