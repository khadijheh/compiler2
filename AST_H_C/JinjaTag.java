package AST_H_C;

import java.util.List;

public class JinjaTag extends HtmlElement {

    private String tagContent;
    private List<HtmlElement> body;

    public JinjaTag(String name, int numberOfLine, String tagContent, List<HtmlElement> body) {
        super("JinjaTag(" + tagContent + ")", numberOfLine);
        this.tagContent = tagContent;
        this.body = body;

        if (body != null) {
            for (HtmlElement element : body) {
                addChild(element);
            }
        }
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
        this.name = "JinjaTag(" + tagContent + ")";
    }

    public List<HtmlElement> getBody() {
        return body;
    }

    public void setBody(List<HtmlElement> body) {
        this.body = body;
    }
}
