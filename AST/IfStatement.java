package AST;

import java.util.List;

public class IfStatement extends AstNode {
    private final List<AstNode> body;

    public IfStatement(AstNode condition, List<AstNode> body, int line) {
        super("If", line);
        this.body = body;

        addChild(condition);
        for (AstNode stmt : body) {
            addChild(stmt);
        }
    }


    public List<AstNode> getIfBody() {
        return body;
    }
}
