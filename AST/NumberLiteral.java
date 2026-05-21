//package AST;
//
//public class NumberLiteral extends AstNode {
//    private String value; // نخزنه كنص للحفاظ على الدقة، أو يمكن تحويله لـ Double
//
//    public NumberLiteral(String value, int linenumber) {
//        super("NumberLiteral", linenumber);
//        this.value = value;
//    }
//
//    public String getValue() { return value; }
//
//    @Override
//    public String toString() {
//        return "Number: " + value + " (Line: " + linenumber + ")";
//    }
//}
package AST;

public class NumberLiteral extends AstNode {

    private final String value;

    public NumberLiteral(String value, int line) {
        super("Number(" + value + ")", line);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
