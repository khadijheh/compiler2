//package AST;
//
//import java.util.List;
//
//public class ListLiteral extends AstNode {
//    private final List<AstNode> elements;
//
//    public ListLiteral(List<AstNode> elements, int line) {
//        super("ListLiteral", line);
//        this.elements = elements;
//
//        for (AstNode e : elements) {
//            addChild(e);
//        }
//    }
//
//    public List<AstNode> getElements() {
//        return elements;
//    }
//}
package AST;

import java.util.List;

public class ListLiteral extends AstNode {

    public ListLiteral(List<AstNode> elements, int line) {
        super("ListLiteral", line);

        for (AstNode el : elements) {
            addChild(el);
        }
    }
}
