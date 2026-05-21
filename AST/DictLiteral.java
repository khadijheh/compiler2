//package AST;
//
//import java.util.Map;
//
//public class DictLiteral extends AstNode {
//    private final Map<AstNode, AstNode> entries;
//
//    public DictLiteral(Map<AstNode, AstNode> entries, int line) {
//        super("DictLiteral", line);
//        this.entries = entries;
//
//        for (var e : entries.entrySet()) {
//            addChild(e.getKey());
//            addChild(e.getValue());
//        }
//    }
//
//    public Map<AstNode, AstNode> getEntries() {
//        return entries;
//    }
//}
package AST;

import java.util.Map;

public class DictLiteral extends AstNode {

    public DictLiteral(Map<AstNode, AstNode> entries, int line) {
        super("DictLiteral", line);

        for (Map.Entry<AstNode, AstNode> e : entries.entrySet()) {
            AstNode pair = new AstNode("Entry", line) {};
            pair.addChild(e.getKey());
            pair.addChild(e.getValue());
            addChild(pair);
        }
    }

}
//context = {"id": 101, "name": "Laptop"}