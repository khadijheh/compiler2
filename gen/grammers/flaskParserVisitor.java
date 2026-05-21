// Generated from C:/Users/techrepair/IdeaProjects/compiler2/compiler2/grammers/flaskParser.g4 by ANTLR 4.13.2
package grammers;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link flaskParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface flaskParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link flaskParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(flaskParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(flaskParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code importStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStmtRule(flaskParser.ImportStmtRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmtRule(flaskParser.AssignStmtRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmtRule(flaskParser.ReturnStmtRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStmtRule(flaskParser.ExpressionStmtRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#stmt_end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt_end(flaskParser.Stmt_endContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionDefRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefRule(flaskParser.FunctionDefRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmtRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmtRule(flaskParser.IfStmtRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmtRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmtRule(flaskParser.ForStmtRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#importStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStmt(flaskParser.ImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#importList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportList(flaskParser.ImportListContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#importItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportItem(flaskParser.ImportItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(flaskParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(flaskParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(flaskParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(flaskParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(flaskParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(flaskParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(flaskParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(flaskParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(flaskParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(flaskParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(flaskParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#arith_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_expr(flaskParser.Arith_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(flaskParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(flaskParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierPrimary(flaskParser.IdentifierPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberPrimary(flaskParser.NumberPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringPrimary(flaskParser.StringPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code truePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruePrimary(flaskParser.TruePrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falsePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalsePrimary(flaskParser.FalsePrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonePrimary(flaskParser.NonePrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenPrimary(flaskParser.ParenPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListPrimary(flaskParser.ListPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dictPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictPrimary(flaskParser.DictPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailer(flaskParser.TrailerContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(flaskParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(flaskParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#ign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIgn(flaskParser.IgnContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(flaskParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#dictLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteral(flaskParser.DictLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#dictEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictEntry(flaskParser.DictEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link flaskParser#dotted_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_name(flaskParser.Dotted_nameContext ctx);
}