package AST_H_C;

import java.util.List;

public abstract class JinjaBlock extends HtmlElement {

    protected List<HtmlElement> body;

    public JinjaBlock(String name, int numberOfLine, List<HtmlElement> body) {
        super(name, numberOfLine);
        this.body = body;

        if (body != null) {
            for (HtmlElement element : body) {
                addChild(element);
            }
        }
    }

    public List<HtmlElement> getBody() {
        return body;
    }

    public void setBody(List<HtmlElement> body) {
        this.body = body;
    }
}
