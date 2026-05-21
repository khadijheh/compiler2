//package AST;
//
//public class StringLiteral extends AstNode {
//    private String value;
//
//    public StringLiteral(String value, int linenumber) {
//        super("StringLiteral", linenumber);
//        // نقوم بإزالة علامات التنصيص من البداية والنهاية لتخزين النص الصافي
//        this.value = value.replace("\"", "").replace("'", "");
//    }
//
//    public String getValue() { return value; }
//
//    @Override
//    public String toString() {
//        return "String: \"" + value + "\" (Line: " + linenumber + ")";
//    }
//}

package AST;

public class StringLiteral extends AstNode {

    private final String value;

    public StringLiteral(String value, int line) {
        super("String(\"" + value + "\")", line);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
