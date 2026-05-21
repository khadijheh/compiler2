//package AST;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ForStatement extends AstNode {
//    private String iteratorId;
//    private AstNode iterable;
//    private List<AstNode> body;
//
//    public ForStatement(String iteratorId, AstNode iterable, List<AstNode> body, int linenumber) {
//        super("ForStatement", linenumber);
//        this.iteratorId = iteratorId;
//        this.iterable = iterable;
//        this.body = body;
//    }
//
//    public String getIteratorId() { return iteratorId; }
//    public AstNode getIterable() { return iterable; }
//    public List<AstNode> getBody() { return body; }
//
//    @Override
//    public String toString() {
//        // نستخدم iterable.toString() بدلاً من nodename
//        return "For (Line: " + linenumber + ") [Iter: " + iteratorId + " in " + iterable.toString() + "]";
//    }
//}
package AST;

import java.util.List;

public class ForStatement extends AstNode {

    private final String variable;
    private final AstNode iterable;
    private final List<AstNode> body;

    public ForStatement(String variable, AstNode iterable,
                        List<AstNode> body, int line) {
        super("For(" + variable + ")", line);
        this.variable = variable;
        this.iterable = iterable;
        this.body = body;

        addChild(new Identifier(variable, line));
        addChild(iterable);

        for (AstNode stmt : body) {
            addChild(stmt);
        }
    }

    public String getIteratorId() {
        return variable;
    }

    public List<AstNode> getBody() {
        return body;
    }
}
