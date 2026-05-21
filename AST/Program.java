package AST;

//public class Program extends AstNode{
//    List<AstNode> statements ;
//
//    public Program(int linenumber, List<AstNode> statements) {
//        super("program", linenumber);
//        this.statements = statements;
//    }
//
//
//
//    void addStmt (AstNode stmt){
//        statements.add(stmt);
//
//    }
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Node: ").append(nodename).append(" (Line: ").append(linenumber).append(")\n");
//        for (AstNode stmt : statements) {
//
//            sb.append("  |-- ").append(stmt.toString()).append("\n");
//        }
//        return sb.toString();
//    }
//}



import java.util.List;

public class Program extends AstNode {

    public Program(int line, List<AstNode> statements) {
        super("Program", line);

        for (AstNode stmt : statements) {
            addChild(stmt);
        }
    }
}



