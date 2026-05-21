// Generated from C:/Courses/compiler2/grammers/htmlParser.g4 by ANTLR 4.13.1
package grammers;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link htmlParser}.
 */
public interface htmlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link htmlParser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void enterHtmlDocument(htmlParser.HtmlDocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void exitHtmlDocument(htmlParser.HtmlDocumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tag_html}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterTag_html(htmlParser.Tag_htmlContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tag_html}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitTag_html(htmlParser.Tag_htmlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cssSty}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterCssSty(htmlParser.CssStyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cssSty}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitCssSty(htmlParser.CssStyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaExpr}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterJinjaExpr(htmlParser.JinjaExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaExpr}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitJinjaExpr(htmlParser.JinjaExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinja_Tag}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterJinja_Tag(htmlParser.Jinja_TagContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinja_Tag}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitJinja_Tag(htmlParser.Jinja_TagContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaComm}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterJinjaComm(htmlParser.JinjaCommContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaComm}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitJinjaComm(htmlParser.JinjaCommContext ctx);
	/**
	 * Enter a parse tree produced by the {@code text}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterText(htmlParser.TextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code text}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitText(htmlParser.TextContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#htmlTag}.
	 * @param ctx the parse tree
	 */
	void enterHtmlTag(htmlParser.HtmlTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#htmlTag}.
	 * @param ctx the parse tree
	 */
	void exitHtmlTag(htmlParser.HtmlTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#htmlAttribute}.
	 * @param ctx the parse tree
	 */
	void enterHtmlAttribute(htmlParser.HtmlAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#htmlAttribute}.
	 * @param ctx the parse tree
	 */
	void exitHtmlAttribute(htmlParser.HtmlAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#cssStyle}.
	 * @param ctx the parse tree
	 */
	void enterCssStyle(htmlParser.CssStyleContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#cssStyle}.
	 * @param ctx the parse tree
	 */
	void exitCssStyle(htmlParser.CssStyleContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(htmlParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(htmlParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void enterRuleset(htmlParser.RulesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void exitRuleset(htmlParser.RulesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#properties}.
	 * @param ctx the parse tree
	 */
	void enterProperties(htmlParser.PropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#properties}.
	 * @param ctx the parse tree
	 */
	void exitProperties(htmlParser.PropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(htmlParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(htmlParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaExpression}.
	 * @param ctx the parse tree
	 */
	void enterJinjaExpression(htmlParser.JinjaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaExpression}.
	 * @param ctx the parse tree
	 */
	void exitJinjaExpression(htmlParser.JinjaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaTag}.
	 * @param ctx the parse tree
	 */
	void enterJinjaTag(htmlParser.JinjaTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaTag}.
	 * @param ctx the parse tree
	 */
	void exitJinjaTag(htmlParser.JinjaTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaComment}.
	 * @param ctx the parse tree
	 */
	void enterJinjaComment(htmlParser.JinjaCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaComment}.
	 * @param ctx the parse tree
	 */
	void exitJinjaComment(htmlParser.JinjaCommentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powerExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpr(htmlParser.PowerExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powerExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpr(htmlParser.PowerExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(htmlParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(htmlParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInExpr(htmlParser.InExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInExpr(htmlParser.InExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(htmlParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(htmlParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptionExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptionExpr(htmlParser.SubscriptionExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptionExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptionExpr(htmlParser.SubscriptionExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDivModExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDivModExpr(htmlParser.MulDivModExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDivModExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDivModExpr(htmlParser.MulDivModExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(htmlParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(htmlParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFilterExpr(htmlParser.FilterExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFilterExpr(htmlParser.FilterExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attributeExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAttributeExpr(htmlParser.AttributeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attributeExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAttributeExpr(htmlParser.AttributeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(htmlParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(htmlParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(htmlParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(htmlParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(htmlParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(htmlParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(htmlParser.LiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpr(htmlParser.LiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(htmlParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(htmlParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIsExpr(htmlParser.IsExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIsExpr(htmlParser.IsExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(htmlParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(htmlParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(htmlParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(htmlParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(htmlParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(htmlParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(htmlParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(htmlParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(htmlParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(htmlParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(htmlParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(htmlParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(htmlParser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(htmlParser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(htmlParser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(htmlParser.FalseLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code noneLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNoneLiteral(htmlParser.NoneLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code noneLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitNoneLiteral(htmlParser.NoneLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dictLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteral(htmlParser.DictLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dictLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteral(htmlParser.DictLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(htmlParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(htmlParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaSingleTag}.
	 * @param ctx the parse tree
	 */
	void enterJinjaSingleTag(htmlParser.JinjaSingleTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaSingleTag}.
	 * @param ctx the parse tree
	 */
	void exitJinjaSingleTag(htmlParser.JinjaSingleTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#singleJinjaTagContent}.
	 * @param ctx the parse tree
	 */
	void enterSingleJinjaTagContent(htmlParser.SingleJinjaTagContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#singleJinjaTagContent}.
	 * @param ctx the parse tree
	 */
	void exitSingleJinjaTagContent(htmlParser.SingleJinjaTagContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#firstBlockJinjaTagContent}.
	 * @param ctx the parse tree
	 */
	void enterFirstBlockJinjaTagContent(htmlParser.FirstBlockJinjaTagContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#firstBlockJinjaTagContent}.
	 * @param ctx the parse tree
	 */
	void exitFirstBlockJinjaTagContent(htmlParser.FirstBlockJinjaTagContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#endBlockJinjaTagContent}.
	 * @param ctx the parse tree
	 */
	void enterEndBlockJinjaTagContent(htmlParser.EndBlockJinjaTagContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#endBlockJinjaTagContent}.
	 * @param ctx the parse tree
	 */
	void exitEndBlockJinjaTagContent(htmlParser.EndBlockJinjaTagContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaBlock}.
	 * @param ctx the parse tree
	 */
	void enterJinjaBlock(htmlParser.JinjaBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaBlock}.
	 * @param ctx the parse tree
	 */
	void exitJinjaBlock(htmlParser.JinjaBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaForBlock}.
	 * @param ctx the parse tree
	 */
	void enterJinjaForBlock(htmlParser.JinjaForBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaForBlock}.
	 * @param ctx the parse tree
	 */
	void exitJinjaForBlock(htmlParser.JinjaForBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link htmlParser#jinjaIfBlock}.
	 * @param ctx the parse tree
	 */
	void enterJinjaIfBlock(htmlParser.JinjaIfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link htmlParser#jinjaIfBlock}.
	 * @param ctx the parse tree
	 */
	void exitJinjaIfBlock(htmlParser.JinjaIfBlockContext ctx);
}