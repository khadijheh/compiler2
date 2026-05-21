package AST_H_C;

import java.util.List;

public class CSSRuleSet extends Node {
    private String selector;
    private List<CssProperty> properties;

    public CSSRuleSet(String name, int numberOfLine, String selector, List<CssProperty> properties) {
        super("CSSRuleSet(" + selector + ")", numberOfLine);
        this.selector = selector;
        this.properties = properties;

        if (properties != null) {
            for (CssProperty property : properties) {
                addChild(property);
            }
        }
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
        this.name = "CSSRuleSet(" + selector + ")";
    }

    public List<CssProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<CssProperty> properties) {
        this.properties = properties;
    }
}
