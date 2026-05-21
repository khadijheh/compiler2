package AST;

//public class Assign extends AstNode {
//    private AstNode left;  //  (factor)
//    private AstNode right; // (expression)
//
//    public Assign(AstNode left, AstNode right, int linenumber) {
//
//        super("Assignment", linenumber);
//        this.left = left;
//        this.right = right;
//    }
//
//    public AstNode getLeft() { return left; }
//    public AstNode getRight() { return right; }
//
//    @Override
//    public String toString() {
//        // نستخدم toString() بدلاً من nodename لنحصل على التفاصيل
//        return "Assign (Line: " + linenumber + ") [" +
//                (left != null ? left.toString() : "null") +
//                " = " +
//                (right != null ? right.toString() : "null") +
//                "]";
//    }
//}

public class Assign extends AstNode {
    private AstNode left;
    private AstNode right;

    public Assign(AstNode left, AstNode right, int line) {
        super("Assign", line);
        this.left = left;
        this.right = right;
        addChild(left);
        addChild(right);
    }

    public AstNode getLeft() {
        return left;
    }


}



