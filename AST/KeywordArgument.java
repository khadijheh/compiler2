//package AST;
//
//public class KeywordArgument extends AstNode {
//    private final String name;
//    private final AstNode value;
//
//    public KeywordArgument(String name, AstNode value, int line) {
//        super("KeywordArgument", line);
//        this.name = name;
//        this.value = value;
//        addChild(value);
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public AstNode getValue() {
//        return value;
//    }
//}

package AST;

public class KeywordArgument extends AstNode {

    private final String name;
    private final AstNode value;

    public KeywordArgument(String name, AstNode value, int line) {
        super("KeywordArg(" + name + ")", line);
        this.name = name;
        this.value = value;

        addChild(value);
    }

    public String getName() {
        return name;
    }

    public AstNode getValue() {
        return value;
    }
    public String getKey() { return name; }
}
