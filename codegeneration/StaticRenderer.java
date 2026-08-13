package codegeneration;

import AST_H_C.*;

/**
 * StaticRenderer — يُحوّل HTML/CSS nodes ثابتة إلى نص HTML.
 *
 * يُعالج:
 *   HtmlTag       → <tag attrs>children</tag>
 *   HtmlText      → نص عادي
 *   HtmlAttribute → attr="value"
 *   CSS_Style     → <style>...</style>
 *   CSSRuleSet    → selector { properties }
 *   CssProperty   → property: value;
 *
 * لا يُعالج Jinja nodes — هذه من مسؤولية JinjaRenderer.
 */
public class StaticRenderer {

    private final GenerationContext context;

    // Tags التي لا تحتاج closing tag
    private static final java.util.Set<String> VOID_TAGS = new java.util.HashSet<>(
        java.util.Arrays.asList(
            "area","base","br","col","embed","hr","img","input",
            "link","meta","param","source","track","wbr"
        )
    );

    public StaticRenderer(GenerationContext context) {
        this.context = context;
    }

    // ================================================================== //
    //  Entry point                                                         //
    // ================================================================== //

    /**
     * يُحوّل أي HTML/CSS node إلى String.
     * إذا كان Jinja node → يُرجع "" (JinjaRenderer يتولى هذا).
     */
    public String render(Node node) {
        if (node == null) return "";

        // ── CSS ──────────────────────────────────────────────────────── //
        if (node instanceof CSS_Style)   return renderCssStyle((CSS_Style) node);
        if (node instanceof CSSRuleSet)  return renderRuleSet((CSSRuleSet) node);
        if (node instanceof CssProperty) return renderProperty((CssProperty) node);

        // ── HTML ─────────────────────────────────────────────────────── //
        if (node instanceof HtmlTag)       return renderTag((HtmlTag) node);
        if (node instanceof HtmlText)      return renderText((HtmlText) node);
        if (node instanceof HtmlAttribute) return renderAttribute((HtmlAttribute) node);

        // ── Jinja nodes → caller (JinjaRenderer) يتعامل معها ─────────── //
        if (node instanceof JinjaExpression ||
            node instanceof JinjaForBlock   ||
            node instanceof JinjaIfBlock    ||
            node instanceof JinjaTag        ||
            node instanceof JinjaSingleTag) {
            return ""; // JinjaRenderer يتولى هذه
        }

        // ── Unknown ──────────────────────────────────────────────────── //
        context.addWarning("StaticRenderer: unknown node type '"
                + node.getClass().getSimpleName() + "' at line "
                + node.getNumberOfLine() + " — skipped");
        return "";
    }

    // ================================================================== //
    //  HTML Tag                                                            //
    // ================================================================== //

    private String renderTag(HtmlTag tag) {
        StringBuilder sb = new StringBuilder();
        String tagName = tag.getTagName();

        // opening tag
        sb.append("<").append(tagName);

        // attributes
        if (tag.getAttributes() != null) {
            for (HtmlAttribute attr : tag.getAttributes()) {
                sb.append(" ").append(renderAttribute(attr));
            }
        }

        if (VOID_TAGS.contains(tagName.toLowerCase())) {
            // void tag — لا closing
            sb.append(">");
        } else {
            sb.append(">");
            // children (recursion — JinjaRenderer يتعامل مع Jinja children)
            if (tag.getChildren() != null) {
                for (Node child : tag.getChildrenNodes()) {
                    sb.append(render(child));
                }
            }
            sb.append("</").append(tagName).append(">");
        }

        return sb.toString();
    }

    // ================================================================== //
    //  HTML Attribute                                                      //
    // ================================================================== //

    private String renderAttribute(HtmlAttribute attr) {
        String name  = attr.getAttributeName();
        String value = attr.getAttributeValue();
        if (value == null || value.isEmpty()) return name;
        return name + "=\"" + escapeAttr(value) + "\"";
    }

    // ================================================================== //
    //  HTML Text                                                           //
    // ================================================================== //

    private String renderText(HtmlText text) {
        String t = text.getText();
        return (t == null) ? "" : t;
    }

    // ================================================================== //
    //  CSS                                                                 //
    // ================================================================== //

    private String renderCssStyle(CSS_Style style) {
        StringBuilder sb = new StringBuilder();
        sb.append("<style>\n");
        if (style.getRuleSets() != null) {
            for (CSSRuleSet rule : style.getRuleSets()) {
                sb.append(renderRuleSet(rule));
            }
        }
        sb.append("</style>\n");
        return sb.toString();
    }

    private String renderRuleSet(CSSRuleSet rule) {
        StringBuilder sb = new StringBuilder();
        sb.append(rule.getSelector()).append(" {\n");
        if (rule.getProperties() != null) {
            for (CssProperty prop : rule.getProperties()) {
                sb.append("  ").append(renderProperty(prop)).append("\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    private String renderProperty(CssProperty prop) {
        return prop.getProperty() + ": " + prop.getValue() + ";";
    }

    // ================================================================== //
    //  Helpers                                                             //
    // ================================================================== //

    private String escapeAttr(String val) {
        return val.replace("&", "&amp;")
                  .replace("\"", "&quot;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;");
    }
}
