//package AST;
//
//public class ReturnStatement extends AstNode {
//    private AstNode expression; // القيمة التي سيتم إرجاعها
//
//    public ReturnStatement(AstNode expression, int linenumber) {
//        super("ReturnStatement", linenumber);
//        this.expression = expression;
//    }
//
//    public AstNode getExpression() { return expression; }
//
//    @Override
//    public String toString() {
//        return "Return (Line: " + linenumber + ") -> [" + (expression != null ? expression.toString() : "None") + "]";
//    }
//}
package AST;

public class ReturnStatement extends AstNode {

    private final AstNode value;

    public ReturnStatement(AstNode value, int line) {
        super("Return", line);
        this.value = value;

        if (value != null) {
            addChild(value);
        }
    }
}
