// Generated from C:/Courses/compiler2/grammers/htmlParser.g4 by ANTLR 4.13.1
package grammers;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link htmlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface htmlParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link htmlParser#htmlDocument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlDocument(htmlParser.HtmlDocumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tag_html}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTag_html(htmlParser.Tag_htmlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cssSty}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssSty(htmlParser.CssStyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaExpr}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaExpr(htmlParser.JinjaExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinja_Tag}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinja_Tag(htmlParser.Jinja_TagContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaComm}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaComm(htmlParser.JinjaCommContext ctx);
	/**
	 * Visit a parse tree produced by the {@code text}
	 * labeled alternative in {@link htmlParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitText(htmlParser.TextContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#htmlTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlTag(htmlParser.HtmlTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#htmlAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlAttribute(htmlParser.HtmlAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#cssStyle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssStyle(htmlParser.CssStyleContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(htmlParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleset(htmlParser.RulesetContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#properties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperties(htmlParser.PropertiesContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(htmlParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaExpression(htmlParser.JinjaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaTag(htmlParser.JinjaTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaComment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaComment(htmlParser.JinjaCommentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powerExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpr(htmlParser.PowerExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(htmlParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInExpr(htmlParser.InExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(htmlParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subscriptionExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptionExpr(htmlParser.SubscriptionExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivModExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivModExpr(htmlParser.MulDivModExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(htmlParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterExpr(htmlParser.FilterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code attributeExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeExpr(htmlParser.AttributeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(htmlParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(htmlParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(htmlParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(htmlParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(htmlParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsExpr(htmlParser.IsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(htmlParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(htmlParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link htmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(htmlParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(htmlParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(htmlParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(htmlParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(htmlParser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(htmlParser.FalseLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code noneLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoneLiteral(htmlParser.NoneLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dictLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteral(htmlParser.DictLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listLiteral}
	 * labeled alternative in {@link htmlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(htmlParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaSingleTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaSingleTag(htmlParser.JinjaSingleTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#singleJinjaTagContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleJinjaTagContent(htmlParser.SingleJinjaTagContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#firstBlockJinjaTagContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFirstBlockJinjaTagContent(htmlParser.FirstBlockJinjaTagContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#endBlockJinjaTagContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndBlockJinjaTagContent(htmlParser.EndBlockJinjaTagContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaBlock(htmlParser.JinjaBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaForBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaForBlock(htmlParser.JinjaForBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link htmlParser#jinjaIfBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaIfBlock(htmlParser.JinjaIfBlockContext ctx);
}