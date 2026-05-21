// Generated from C:/Courses/compiler2/grammers/cssParser.g4 by ANTLR 4.13.1
package grammers;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link cssParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface cssParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link cssParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(cssParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link cssParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleset(cssParser.RulesetContext ctx);
	/**
	 * Visit a parse tree produced by {@link cssParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(cssParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link cssParser#properties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperties(cssParser.PropertiesContext ctx);
	/**
	 * Visit a parse tree produced by {@link cssParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(cssParser.ValueContext ctx);
}