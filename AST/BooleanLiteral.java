//package AST;
//
//public class BooleanLiteral extends AstNode {
//    private final boolean value;
//
//    public BooleanLiteral(boolean value, int line) {
//        super("BooleanLiteral", line);
//        this.value = value;
//    }
//
//    public boolean getValue() {
//        return value;
//    }
//}
package AST;

public class BooleanLiteral extends AstNode {

    private final boolean value;

    public BooleanLiteral(boolean value, int line) {
        super("Boolean(" + value + ")", line);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
