package AST_H_C;

import java.util.List;

public class JinjaForBlock extends JinjaBlock {

    public JinjaForBlock(String name, int numberOfLine, List<HtmlElement> body) {
        super("JinjaForBlock", numberOfLine, body);
    }
}
