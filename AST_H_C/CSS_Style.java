package AST_H_C;

import java.util.List;

public class CSS_Style extends HtmlElement {

    private List<CSSRuleSet> ruleSets;

    public CSS_Style(String name, int numberOfLine, List<CSSRuleSet> ruleSets) {
        super("CSS_Style", numberOfLine);
        this.ruleSets = ruleSets;

        if (ruleSets != null) {
            for (CSSRuleSet ruleSet : ruleSets) {
                addChild(ruleSet);
            }
        }
    }

    public List<CSSRuleSet> getRuleSets() {
        return ruleSets;
    }

    public void setRuleSets(List<CSSRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }
}
