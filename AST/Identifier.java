//package AST;
//
//public class Identifier extends AstNode {
//    private String value;
//
//    public Identifier(String value, int linenumber) {
//        super("Identifier", linenumber);
//        this.value = value;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    @Override
//    public String toString() {
//        return "ID: " + value + " (Line: " + linenumber + ")";
//    }
//}
package AST;

public class Identifier extends AstNode {

    private final String name;

    public Identifier(String name, int line) {
        super("Identifier(" + name + ")", line);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
