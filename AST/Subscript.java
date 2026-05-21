//package AST;
//
//public class Subscript extends AstNode {
//    private AstNode object; // المصفوفة أو القاموس
//    private AstNode index;  // الفهرس أو المفتاح
//
//    public Subscript(AstNode object, AstNode index, int linenumber) {
//        super("Subscript", linenumber);
//        this.object = object;
//        this.index = index;
//    }
//
//    @Override
//    public String toString() {
//        return object.toString() + "[" + index.toString() + "]";
//    }
//}
package AST;

public class Subscript extends AstNode {

    private final AstNode target;
    private final AstNode index;

    public Subscript(AstNode target, AstNode index, int line) {
        super("Subscript", line);
        this.target = target;
        this.index = index;

        addChild(target);
        addChild(index);
    }
}
