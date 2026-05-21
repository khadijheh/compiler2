package visitor;

import AST_H_C.*;
import SymbolTable.WebSymbol;

import java.util.ArrayList;
import java.util.List;

public class WebSymbolTableVisitor {

    public static List<WebSymbol> webSymbols = new ArrayList<>();
    private String currentFile;

    public WebSymbolTableVisitor(String fileName) {
        this.currentFile = fileName;
    }

    public void build(Node node) {
        if (node == null) return;


        if (node instanceof HtmlTag) {
            HtmlTag tag = (HtmlTag) node;
            if (tag.getAttributes() != null) {
                for (HtmlAttribute attr : tag.getAttributes()) {
                    String attrName = attr.getAttributeName();
                    String attrValue = attr.getAttributeValue();

                    if (attrName.equalsIgnoreCase("id")) {
                        webSymbols.add(new WebSymbol(attrValue, "HTML_ID", attr.getNumberOfLine(), currentFile));
                    } else if (attrName.equalsIgnoreCase("class")) {

                        for (String cls : attrValue.split("\\s+")) {
                            if (!cls.isEmpty()) {
                                webSymbols.add(new WebSymbol(cls, "HTML_CLASS", attr.getNumberOfLine(), currentFile));
                            }
                        }
                    }
                }
            }
            if (tag.getChildren() != null) {
                for (HtmlElement child : tag.getChildren()) build(child);
            }
        }
        else if (node instanceof CSSRuleSet) {
            CSSRuleSet rule = (CSSRuleSet) node;
            webSymbols.add(new WebSymbol(rule.getSelector(), "CSS_SELECTOR", rule.getNumberOfLine(), currentFile));
        }
        else if (node instanceof JinjaExpression) {
            JinjaExpression jinja = (JinjaExpression) node;
            webSymbols.add(new WebSymbol(jinja.getExpression(), "JINJA_VAR", jinja.getNumberOfLine(), currentFile));
        }
        else if (node instanceof JinjaForBlock) {
            JinjaForBlock forBlock = (JinjaForBlock) node;
            if (forBlock.getBody() != null) {
                for (HtmlElement element : forBlock.getBody()) build(element);
            }
        }
        else if (node instanceof JinjaIfBlock) {
            JinjaIfBlock ifBlock = (JinjaIfBlock) node;
            if (ifBlock.getBody() != null) {
                for (HtmlElement element : ifBlock.getBody()) {
                    build(element);
                }
            }
        }

    }
    public static void printReport() {

        System.out.format("%-25s | %-15s | %-6s | %-20s\n", "Symbol Name", "Type", "Line", "Source File");
        System.out.println("-".repeat(80));
        for (WebSymbol s : webSymbols) {
            System.out.println(s);
        }

    }
}