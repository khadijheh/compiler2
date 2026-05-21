package AST;

import java.util.List;

//public class FunctionCall extends AstNode {
//    private AstNode callee; // اسم الدالة المستدعاة (عادة Identifier)
//    private List<AstNode> arguments; // قائمة المدخلات
//
//    public FunctionCall(AstNode callee, List<AstNode> arguments, int linenumber) {
//        super("FunctionCall", linenumber);
//        this.callee = callee;
//        this.arguments = arguments;
//    }
//
//    public AstNode getCallee() { return callee; }
//    public List<AstNode> getArguments() { return arguments; }
//
//    @Override
//    public String toString() {
//        return "Call: " + callee.toString() + " Args: " + arguments.size() + " (Line: " + linenumber + ")";
//    }
//}
public class FunctionCall extends AstNode {
    public FunctionCall(AstNode function, List<AstNode> args, int line) {
        super("FunctionCall", line);
        addChild(function);
        for (AstNode arg : args) {
            addChild(arg);
        }
    }
}
