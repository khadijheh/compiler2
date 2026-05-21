package AST_H_C;

public class CssProperty extends Node {

    private String property;
    private String value;

    public CssProperty(String name, int numberOfLine, String property, String value) {
        super("CssProperty(" + property + ": " + value + ")", numberOfLine);
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
        this.name = "CssProperty(" + property + ": " + value + ")";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.name = "CssProperty(" + property + ": " + value + ")";
    }
}
