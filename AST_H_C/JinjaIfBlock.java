package AST_H_C;

import java.util.List;

public class JinjaIfBlock extends JinjaBlock {

    public JinjaIfBlock(String name, int numberOfLine, List<HtmlElement> body) {
        super("JinjaIfBlock", numberOfLine, body);
    }
}
