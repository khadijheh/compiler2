package codegeneration;

import AST.*;
import AST_H_C.*;
import java.util.*;

/**
 * ASTJsonSerializer
 * =================
 * يُحوّل شجرتي AST إلى JSON strings لحفظها في compiler_output/.
 *
 * ينتج ملفين:
 * ─────────────────────────────────────────────────────────────────
 * ast_python.json  ← شجرة Python AST كاملة
 * ast_jinja.json   ← شجرة HTML/Jinja AST كاملة
 *
 * لماذا JSON؟
 * ─────────────────────────────────────────────────────────────────
 * - يمكن فتحها في أي محرر نصي أو browser
 * - تُستخدم لـ debugging
 * - مطلوبة في compiler_output/ حسب المتطلبات
 *
 * مثال ast_python.json:
 * ─────────────────────────────────────────────────────────────────
 * {
 *   "type": "PythonAST",
 *   "root": {
 *     "nodeType": "Program",
 *     "line": 1,
 *     "children": [
 *       {
 *         "nodeType": "Assign",
 *         "line": 5,
 *         "children": [
 *           { "nodeType": "Identifier", "name": "app", "line": 5 },
 *           { "nodeType": "FunctionCall", "line": 5, "children": [...] }
 *         ]
 *       },
 *       ...
 *     ]
 *   }
 * }
 *
 * مثال ast_jinja.json:
 * ─────────────────────────────────────────────────────────────────
 * {
 *   "type": "JinjaAST",
 *   "root": {
 *     "nodeType": "HtmlTag",
 *     "tagName": "html",
 *     "line": 1,
 *     "children": [
 *       {
 *         "nodeType": "HtmlTag",
 *         "tagName": "head",
 *         "line": 3,
 *         "children": [...]
 *       },
 *       {
 *         "nodeType": "JinjaForBlock",
 *         "name": "JinjaForBlock",
 *         "line": 194,
 *         "children": [...]
 *       },
 *       ...
 *     ]
 *   }
 * }
 */
public class ASTJsonSerializer {

    // ================================================================== //
    //  Python AST → JSON                                                  //
    // ================================================================== //

    /**
     * serializePython()
     * ──────────────────
     * نقطة الدخول لتحويل Python AST إلى JSON.
     *
     * @param root شجرة Python AST من PythonVisitor
     * @return JSON string كامل
     */
    public String serializePython(AstNode root) {
        if (root == null) return "{ \"type\": \"PythonAST\", \"root\": null }";

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"type\": \"PythonAST\",\n");
        sb.append("  \"root\": ");
        serializePythonNode(root, sb, 1);
        sb.append("\n}");
        return sb.toString();
    }

    /**
     * serializePythonNode()
     * ──────────────────────
     * يُسلسل Python AST node واحد وكل children بشكل recursive.
     *
     * يكتب لكل node:
     *   - nodeType: اسم الـ class
     *   - line: رقم السطر
     *   - حقول إضافية حسب نوع الـ node
     *   - children: array من الـ children
     */
    private void serializePythonNode(AstNode node, StringBuilder sb, int indent) {
        if (node == null) { sb.append("null"); return; }

        String pad  = "  ".repeat(indent);       // مسافة للـ }
        String pad2 = "  ".repeat(indent + 1);   // مسافة للـ fields

        sb.append("{\n");

        // ── nodeType ──────────────────────────────────────────────────── //
        sb.append(pad2).append("\"nodeType\": \"")
                .append(esc(node.getClass().getSimpleName())).append("\",\n");

        // ── line ──────────────────────────────────────────────────────── //
        sb.append(pad2).append("\"line\": ")
                .append(node.getLine()).append(",\n");

        // ── حقول إضافية حسب نوع الـ node ────────────────────────────── //
        writePythonNodeFields(node, sb, pad2);

        // ── children ──────────────────────────────────────────────────── //
        List<AstNode> children = node.getChildren();
        sb.append(pad2).append("\"children\": ");

        if (children == null || children.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[\n");
            for (int i = 0; i < children.size(); i++) {
                sb.append(pad2).append("  ");
                serializePythonNode(children.get(i), sb, indent + 2);
                if (i < children.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append(pad2).append("]");
        }

        sb.append("\n").append(pad).append("}");
    }

    /**
     * writePythonNodeFields()
     * ────────────────────────
     * يكتب الحقول الخاصة بكل نوع Python AST node.
     *
     * مثال:
     *   Identifier     → "name": "products"
     *   StringLiteral  → "value": "index.html"
     *   NumberLiteral  → "value": "15.99"
     *   BooleanLiteral → "value": true
     *   FunctionDef    → "name": "home", "returnType": "Any"
     *   BinaryExpression → "operator": "+"
     */
    private void writePythonNodeFields(AstNode node,
                                       StringBuilder sb,
                                       String pad2) {
        if (node instanceof Identifier) {
            sb.append(pad2).append("\"name\": \"")
                    .append(esc(((Identifier) node).getName())).append("\",\n");
        }
        else if (node instanceof StringLiteral) {
            sb.append(pad2).append("\"value\": \"")
                    .append(esc(((StringLiteral) node).getValue())).append("\",\n");
        }
        else if (node instanceof NumberLiteral) {
            sb.append(pad2).append("\"value\": \"")
                    .append(esc(((NumberLiteral) node).getValue())).append("\",\n");
        }
        else if (node instanceof BooleanLiteral) {
            sb.append(pad2).append("\"value\": ")
                    .append(((BooleanLiteral) node).getValue()).append(",\n");
        }
        else if (node instanceof FunctionDef) {
            FunctionDef fd = (FunctionDef) node;
            sb.append(pad2).append("\"name\": \"")
                    .append(esc(fd.getName())).append("\",\n");
            sb.append(pad2).append("\"returnType\": \"")
                    .append(esc(fd.getReturnType())).append("\",\n");
            // params
            sb.append(pad2).append("\"params\": [");
            List<String> params = fd.getParameters();
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    sb.append("\"").append(esc(params.get(i))).append("\"");
                    if (i < params.size() - 1) sb.append(", ");
                }
            }
            sb.append("],\n");
        }
        else if (node instanceof BinaryExpression) {
            sb.append(pad2).append("\"operator\": \"")
                    .append(esc(((BinaryExpression) node).getOperator())).append("\",\n");
        }
        else if (node instanceof Decorator) {
            sb.append(pad2).append("\"name\": \"")
                    .append(esc(((Decorator) node).getName())).append("\",\n");
        }
        else if (node instanceof KeywordArgument) {
            sb.append(pad2).append("\"key\": \"")
                    .append(esc(((KeywordArgument) node).getKey())).append("\",\n");
        }
    }

    // ================================================================== //
    //  Jinja/HTML AST → JSON                                             //
    // ================================================================== //

    /**
     * serializeJinja()
     * ─────────────────
     * نقطة الدخول لتحويل HTML/Jinja AST إلى JSON.
     *
     * @param root شجرة HTML/Jinja AST من HtmlVisitor
     * @return JSON string كامل
     */
    public String serializeJinja(Node root) {
        if (root == null) return "{ \"type\": \"JinjaAST\", \"root\": null }";

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"type\": \"JinjaAST\",\n");
        sb.append("  \"root\": ");
        serializeHtmlNode(root, sb, 1);
        sb.append("\n}");
        return sb.toString();
    }

    /**
     * serializeHtmlNode()
     * ────────────────────
     * يُسلسل HTML/Jinja AST node واحد وكل children بشكل recursive.
     *
     * يكتب لكل node:
     *   - nodeType: اسم الـ class
     *   - name: اسم الـ node
     *   - line: رقم السطر
     *   - حقول إضافية حسب نوع الـ node
     *   - children: array من الـ children
     */
    private void serializeHtmlNode(Node node, StringBuilder sb, int indent) {
        if (node == null) { sb.append("null"); return; }

        String pad  = "  ".repeat(indent);
        String pad2 = "  ".repeat(indent + 1);

        sb.append("{\n");

        // ── nodeType ──────────────────────────────────────────────────── //
        sb.append(pad2).append("\"nodeType\": \"")
                .append(esc(node.getClass().getSimpleName())).append("\",\n");

        // ── name ──────────────────────────────────────────────────────── //
        sb.append(pad2).append("\"name\": \"")
                .append(esc(node.getName())).append("\",\n");

        // ── line ──────────────────────────────────────────────────────── //
        sb.append(pad2).append("\"line\": ")
                .append(node.getNumberOfLine()).append(",\n");

        // ── حقول إضافية حسب نوع الـ node ────────────────────────────── //
        writeHtmlNodeFields(node, sb, pad2);

        // ── children ──────────────────────────────────────────────────── //
        List<Node> children = node.getChildrenNodes();
        sb.append(pad2).append("\"children\": ");

        if (children == null || children.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[\n");
            for (int i = 0; i < children.size(); i++) {
                sb.append(pad2).append("  ");
                serializeHtmlNode(children.get(i), sb, indent + 2);
                if (i < children.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append(pad2).append("]");
        }

        sb.append("\n").append(pad).append("}");
    }

    /**
     * writeHtmlNodeFields()
     * ──────────────────────
     * يكتب الحقول الخاصة بكل نوع HTML/Jinja AST node.
     *
     * مثال:
     *   HtmlTag        → "tagName": "div"
     *   HtmlAttribute  → "attrName": "class", "attrValue": "products"
     *   HtmlText       → "text": "المتجر الإلكتروني"
     *   JinjaExpression→ "expression": "product.name"
     *   CSSRuleSet     → "selector": "body"
     *   CssProperty    → "property": "color", "value": "#333"
     */
    private void writeHtmlNodeFields(Node node,
                                     StringBuilder sb,
                                     String pad2) {
        if (node instanceof HtmlTag) {
            sb.append(pad2).append("\"tagName\": \"")
                    .append(esc(((HtmlTag) node).getTagName())).append("\",\n");

            // attributes كـ array
            List<HtmlAttribute> attrs = ((HtmlTag) node).getAttributes();
            sb.append(pad2).append("\"attributes\": [");
            if (attrs != null && !attrs.isEmpty()) {
                sb.append("\n");
                for (int i = 0; i < attrs.size(); i++) {
                    HtmlAttribute a = attrs.get(i);
                    sb.append(pad2).append("  { \"name\": \"")
                            .append(esc(a.getAttributeName()))
                            .append("\", \"value\": \"")
                            .append(esc(a.getAttributeValue()))
                            .append("\" }");
                    if (i < attrs.size() - 1) sb.append(",");
                    sb.append("\n");
                }
                sb.append(pad2);
            }
            sb.append("],\n");
        }
        else if (node instanceof HtmlText) {
            String text = ((HtmlText) node).getText();
            sb.append(pad2).append("\"text\": \"")
                    .append(esc(text != null ? text.trim() : "")).append("\",\n");
        }
        else if (node instanceof JinjaExpression) {
            sb.append(pad2).append("\"expression\": \"")
                    .append(esc(((JinjaExpression) node).getExpression())).append("\",\n");
        }
        else if (node instanceof CSSRuleSet) {
            sb.append(pad2).append("\"selector\": \"")
                    .append(esc(((CSSRuleSet) node).getSelector())).append("\",\n");
        }
        else if (node instanceof CssProperty) {
            CssProperty prop = (CssProperty) node;
            sb.append(pad2).append("\"property\": \"")
                    .append(esc(prop.getProperty())).append("\",\n");
            sb.append(pad2).append("\"value\": \"")
                    .append(esc(prop.getValue())).append("\",\n");
        }
        else if (node instanceof HtmlAttribute) {
            HtmlAttribute attr = (HtmlAttribute) node;
            sb.append(pad2).append("\"attrName\": \"")
                    .append(esc(attr.getAttributeName())).append("\",\n");
            sb.append(pad2).append("\"attrValue\": \"")
                    .append(esc(attr.getAttributeValue())).append("\",\n");
        }
    }

    // ================================================================== //
    //  Helper: esc                                                        //
    // ================================================================== //

    /**
     * esc()
     * ─────
     * يُهرّب أحرف خاصة لتكون صالحة داخل JSON string.
     *
     * مثال:
     *   "He said \"hello\""  → "He said \\\"hello\\\""
     *   "line1\nline2"       → "line1\\nline2"
     */
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\",  "\\\\")
                .replace("\"",  "\\\"")
                .replace("\n",  "\\n")
                .replace("\r",  "\\r")
                .replace("\t",  "\\t");
    }
}