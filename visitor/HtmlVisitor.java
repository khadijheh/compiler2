package visitor;

import AST_H_C.CSSRuleSet;
import AST_H_C.CSS_Style;
import AST_H_C.CssProperty;
import AST_H_C.HtmlAttribute;
import AST_H_C.HtmlElement;
import AST_H_C.HtmlTag;
import AST_H_C.HtmlText;
import AST_H_C.JinjaExpression;
import AST_H_C.JinjaForBlock;
import AST_H_C.JinjaIfBlock;
import AST_H_C.JinjaSingleTag;
import AST_H_C.Node;
import grammers.htmlParser;
import grammers.htmlParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class HtmlVisitor extends htmlParserBaseVisitor<Node> {

    @Override
    public Node visitHtmlDocument(htmlParser.HtmlDocumentContext ctx) {
        List<HtmlElement> children = extractHtmlElements(ctx.htmlElement());
        return new HtmlTag("HTML_DOCUMENT", getLineNumber(ctx), "html", new ArrayList<>(), children);
    }

    @Override
    public Node visitTag_html(htmlParser.Tag_htmlContext ctx) {
        return visit(ctx.htmlTag());
    }

    @Override
    public Node visitCssSty(htmlParser.CssStyContext ctx) {
        return visit(ctx.cssStyle());
    }

    @Override
    public Node visitJinjaExpr(htmlParser.JinjaExprContext ctx) {
        return visit(ctx.jinjaExpression());
    }

    @Override
    public Node visitJinja_Tag(htmlParser.Jinja_TagContext ctx) {
        return visit(ctx.jinjaTag());
    }

    @Override
    public Node visitJinjaComm(htmlParser.JinjaCommContext ctx) {
        return visit(ctx.jinjaComment());
    }

    @Override
    public Node visitText(htmlParser.TextContext ctx) {
        return new HtmlText("HTML_TEXT", getLineNumber(ctx), ctx.HTML_TEXT().getText());
    }

    @Override
    public Node visitHtmlTag(htmlParser.HtmlTagContext ctx) {
        String tagName = ctx.TAG_NAME().isEmpty() ? "" : ctx.TAG_NAME(0).getText();
        List<HtmlAttribute> attributes = extractHtmlAttributes(ctx.htmlAttribute());
        List<HtmlElement> children = extractHtmlElements(ctx.htmlElement());

        return new HtmlTag("HTML_TAG", getLineNumber(ctx), tagName, attributes, children);
    }

    @Override
    public Node visitCssStyle(htmlParser.CssStyleContext ctx) {
        return new CSS_Style("CSS_STYLE", getLineNumber(ctx), extractRuleSets(ctx.stylesheet()));
    }

    @Override
    public Node visitRuleset(htmlParser.RulesetContext ctx) {
        String selector = ctx.CSS_SELECTOR().getText();
        List<CssProperty> properties = extractProperties(ctx.properties());

        return new CSSRuleSet("CSS_RULESET", getLineNumber(ctx), selector, properties);
    }

    @Override
    public Node visitProperties(htmlParser.PropertiesContext ctx) {
        String propertyName = ctx.PROPERTY().getText();
        String value = buildValue(ctx.value());

        return new CssProperty("CSS_PROPERTY", getLineNumber(ctx), propertyName, value);
    }

    @Override
    public Node visitJinjaExpression(htmlParser.JinjaExpressionContext ctx) {
        return new JinjaExpression("JINJA_EXPRESSION", getLineNumber(ctx), ctx.expr().getText());
    }

    @Override
    public Node visitJinjaTag(htmlParser.JinjaTagContext ctx) {
        if (ctx.jinjaSingleTag() != null) {
            return visit(ctx.jinjaSingleTag());
        }

        if (ctx.jinjaBlock() != null) {
            return visit(ctx.jinjaBlock());
        }

        return null;
    }

    @Override
    public Node visitJinjaSingleTag(htmlParser.JinjaSingleTagContext ctx) {
        return new JinjaSingleTag("JINJA_SINGLE_TAG", getLineNumber(ctx));
    }

    @Override
    public Node visitJinjaBlock(htmlParser.JinjaBlockContext ctx) {
        if (ctx.jinjaForBlock() != null) {
            return visit(ctx.jinjaForBlock());
        }

        if (ctx.jinjaIfBlock() != null) {
            return visit(ctx.jinjaIfBlock());
        }

        return null;
    }

    @Override
    public Node visitJinjaForBlock(htmlParser.JinjaForBlockContext ctx) {
        List<HtmlElement> body = extractHtmlElements(ctx.htmlElement());
        return new JinjaForBlock("JINJA_FOR_BLOCK", getLineNumber(ctx), body);
    }

    @Override
    public Node visitJinjaIfBlock(htmlParser.JinjaIfBlockContext ctx) {
        List<HtmlElement> body = extractHtmlElements(ctx.htmlElement());
        return new JinjaIfBlock("JINJA_IF_BLOCK", getLineNumber(ctx), body);
    }

    @Override
    public Node visitJinjaComment(htmlParser.JinjaCommentContext ctx) {
        String commentText = ctx.COMMENT_TEXT() != null ? ctx.COMMENT_TEXT().getText() : "";
        return new HtmlText("JINJA_COMMENT", getLineNumber(ctx), "{{# " + commentText + " #}}");
    }

    private int getLineNumber(ParserRuleContext ctx) {
        return ctx.start != null ? ctx.start.getLine() : -1;
    }

    private String unquote(String value) {
        if (value != null && value.length() >= 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length() - 1);
            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }
        return value;
    }

    private List<HtmlElement> extractHtmlElements(List<htmlParser.HtmlElementContext> contexts) {
        List<HtmlElement> elements = new ArrayList<>();
        for (htmlParser.HtmlElementContext context : contexts) {
            Node node = visit(context);
            if (node instanceof HtmlElement) {
                elements.add((HtmlElement) node);
            }
        }
        return elements;
    }

    private List<HtmlAttribute> extractHtmlAttributes(List<htmlParser.HtmlAttributeContext> contexts) {
        List<HtmlAttribute> attributes = new ArrayList<>();
        for (htmlParser.HtmlAttributeContext ctx : contexts) {
            String value = ctx.ATTRIBUTE_VALUE() != null ? unquote(ctx.ATTRIBUTE_VALUE().getText()) : "";
            attributes.add(new HtmlAttribute("HTML_ATTRIBUTE", getLineNumber(ctx), ctx.ATTR_NAME().getText(), value));
        }
        return attributes;
    }

    private List<CSSRuleSet> extractRuleSets(htmlParser.StylesheetContext ctx) {
        List<CSSRuleSet> ruleSets = new ArrayList<>();
        if (ctx == null) {
            return ruleSets;
        }

        for (htmlParser.RulesetContext ruleCtx : ctx.ruleset()) {
            Node node = visit(ruleCtx);
            if (node instanceof CSSRuleSet) {
                ruleSets.add((CSSRuleSet) node);
            }
        }

        return ruleSets;
    }

    private List<CssProperty> extractProperties(List<htmlParser.PropertiesContext> contexts) {
        List<CssProperty> properties = new ArrayList<>();
        for (htmlParser.PropertiesContext ctx : contexts) {
            Node node = visit(ctx);
            if (node instanceof CssProperty) {
                properties.add((CssProperty) node);
            }
        }
        return properties;
    }

    private String buildValue(htmlParser.ValueContext ctx) {
        StringBuilder value = new StringBuilder();
        List<TerminalNode> parts = ctx != null ? ctx.VALUE() : List.of();

        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                value.append(", ");
            }
            value.append(parts.get(i).getText());
        }

        return value.toString();
    }
}
