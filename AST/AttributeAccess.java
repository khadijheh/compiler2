//package AST;
//
//public class AttributeAccess extends AstNode {
//    private AstNode object; // الكائن (مثلاً request)
//    private String attribute; // الخاصية (مثلاً form)
//
//    public
//    (AstNode object, String attribute, int linenumber) {
//        super("AttributeAccess", linenumber);
//        this.object = object;
//        this.attribute = attribute;
//    }
//
//    @Override
//    public String toString() {
//        return object.toString() + "." + attribute;
//    }
//}
package AST;

public class AttributeAccess extends AstNode {
//target.attribute
    private final AstNode target;
    private final String attribute;

    public AttributeAccess(AstNode target, String attribute, int line) {

        super("AttributeAccess(" + attribute + ")", line);
        this.target = target;
        this.attribute = attribute;

        addChild(target);
    }


    public String getAttributeName() {
        return attribute;
    }

    public AstNode getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "AttributeAccess: " + attribute + " (Line: " + getLine() + ")";
    }
}