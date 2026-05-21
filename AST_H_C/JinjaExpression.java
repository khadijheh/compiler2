package AST_H_C;

public class JinjaExpression extends HtmlElement {

    private String expression;

    public JinjaExpression(String name, int numberOfLine, String expression) {
        super("JinjaExpression(" + expression + ")", numberOfLine);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        this.name = "JinjaExpression(" + expression + ")";
    }
}
