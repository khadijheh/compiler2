//package AST;
//
//public class BinaryExpression extends AstNode {
//    private AstNode left;      // الطرف الأيسر (مثلاً: x)
//    private String operator;   // عامل العملية (مثلاً: "+")
//    private AstNode right;     // الطرف الأيمن (مثلاً: 5)
//
//    public BinaryExpression(AstNode left, String operator, AstNode right, int linenumber) {
//        // نمرر نوع العقدة "BinaryExpression" ورقم السطر للأب
//        super("BinaryExpression", linenumber);
//        this.left = left;
//        this.operator = operator;
//        this.right = right;
//    }
//
//    // Getters للوصول للبيانات أثناء التحليل الدلالي (Semantic)
//    public AstNode getLeft() { return left; }
//    public String getOperator() { return operator; }
//    public AstNode getRight() { return right; }
//
//    @Override
//    public String toString() {
//        return "(" +
//                (left != null ? left.toString() : "?") +
//                " " + operator + " " +
//                (right != null ? right.toString() : "?") +
//                ")";
//    }
//}
package AST;

public class BinaryExpression extends AstNode {

    private final String operator;
    private final AstNode left;
    private final AstNode right;

    public BinaryExpression(AstNode left, String operator,
                            AstNode right, int line) {
        super("BinaryOp(" + operator + ")", line);
        this.left = left;
        this.right = right;
        this.operator = operator;

        addChild(left);
        addChild(right);
    }
    public String  getOperator() { return operator; }
    public AstNode getLeft()     { return left; }
    public AstNode getRight()    { return right; }
}
