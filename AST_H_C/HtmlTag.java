package AST_H_C;

import java.util.List;

public class HtmlTag extends HtmlElement {

    private String tagName;
    private List<HtmlAttribute> attributes;
    private List<HtmlElement> children;

    public HtmlTag(String name, int numberOfLine, String tagName, List<HtmlAttribute> attributes, List<HtmlElement> children) {
        super(buildNodeName(tagName), numberOfLine);
        this.tagName = tagName;
        this.attributes = attributes;
        this.children = children;

        if (attributes != null) {
            for (HtmlAttribute attribute : attributes) {
                addChild(attribute);
            }
        }

        if (children != null) {
            for (HtmlElement child : children) {
                addChild(child);
            }
        }
    }

    private static String buildNodeName(String tagName) {
        return "HtmlTag(" + tagName + ")";
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
        this.name = buildNodeName(tagName);
    }

    public List<HtmlAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<HtmlAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<HtmlElement> getChildren() {
        return children;
    }

    public void setChildren(List<HtmlElement> children) {
        this.children = children;
    }
}
