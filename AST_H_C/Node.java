package AST_H_C;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected String name;
    protected int numberOfLine;
    protected List<Node> children = new ArrayList<>();

    public Node(String name, int numberOfLine) {
        this.name = name;
        this.numberOfLine = numberOfLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public void setNumberOfLine(int numberOfLine) {
        this.numberOfLine = numberOfLine;
    }

    public void addChild(Node child) {
        if (child != null) {
            children.add(child);
        }
    }

    public List<Node> getChildrenNodes() {
        return children;
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

        sb.append(name)
                .append(" (line ")
                .append(numberOfLine)
                .append(")")
                .append("\n");

        for (int i = 0; i < children.size(); i++) {
            sb.append(children.get(i).toString(
                    prefix + (isLast ? "   " : "|  "),
                    i == children.size() - 1
            ));
        }

        return sb.toString();
    }
}
