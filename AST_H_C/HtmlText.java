package AST_H_C;

public class HtmlText extends HtmlElement {
    private String text;

    public HtmlText(String name, int numberOfLine, String text) {
        super(buildNodeName(text), numberOfLine);
        this.text = text;
    }

    private static String buildNodeName(String text) {
        String normalized = text == null ? "" : text.replace("\r", "\\r").replace("\n", "\\n").trim();
        if (normalized.isEmpty()) {
            normalized = " ";
        }
        return "HtmlText(\"" + normalized + "\")";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.name = buildNodeName(text);
    }
}
