package AST_H_C;

public class HtmlAttribute extends Node {

    private String attributeName;
    private String attributeValue;

    public HtmlAttribute(String name, int numberOfLine, String attributeName, String attributeValue) {
        super(buildNodeName(attributeName, attributeValue), numberOfLine);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    private static String buildNodeName(String attributeName, String attributeValue) {
        if (attributeValue == null || attributeValue.isEmpty()) {
            return "HtmlAttribute(" + attributeName + ")";
        }
        return "HtmlAttribute(" + attributeName + "=\"" + attributeValue + "\")";
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        this.name = buildNodeName(attributeName, attributeValue);
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        this.name = buildNodeName(attributeName, attributeValue);
    }
}
