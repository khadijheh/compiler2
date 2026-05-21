package AST;

import java.util.ArrayList;
import java.util.List;

//public abstract class AstNode {
//    protected String nodename;
//    protected int linenumber;
//    protected List<AstNode> children;
//
//    public AstNode(String nodename, int linenumber) {
//        this.nodename = nodename;
//        this.linenumber = linenumber;
//        this.children = new ArrayList<>();
//    }
//
//    public void addChild(AstNode child) {
//        if (child != null) {
//            this.children.add(child);
//        }
//    }
//
//}
public abstract class AstNode {
    protected String nodeName;
    protected int line;
    protected List<AstNode> children = new ArrayList<>();

    public AstNode(String nodeName, int line) {
        this.nodeName = nodeName;
        this.line = line;
    }

    public void addChild(AstNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    @Override
    public String toString() {
        return toString("", true);
    }

    private String toString(String prefix, boolean isLast) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);

        if (!prefix.isEmpty()) {
            sb.append(isLast ? "-- " : "|- ");
        }
        sb.append(nodeName)
                .append(" (line ")
                .append(line)
                .append(")")
                .append("\n");

        for (int i = 0; i < children.size(); i++) {
            sb.append(children.get(i)
                    .toString(
                            prefix + (isLast ? "   " : "|  "),
                            i == children.size() - 1
                    ));
        }
        return sb.toString();
    }
    public List<AstNode> getChildren() { return children; }
    public int getLine() { return line; }
    public String getNodeName() { return nodeName; }
}

