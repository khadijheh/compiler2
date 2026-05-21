// Generated from C:/Users/techrepair/IdeaProjects/compiler2/compiler2/grammers/flaskParser.g4 by ANTLR 4.13.2
package grammers;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link flaskParser}.
 */
public interface flaskParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link flaskParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(flaskParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(flaskParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(flaskParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(flaskParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code importStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterImportStmtRule(flaskParser.ImportStmtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code importStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitImportStmtRule(flaskParser.ImportStmtRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmtRule(flaskParser.AssignStmtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmtRule(flaskParser.AssignStmtRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmtRule(flaskParser.ReturnStmtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmtRule(flaskParser.ReturnStmtRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStmtRule(flaskParser.ExpressionStmtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionStmtRule}
	 * labeled alternative in {@link flaskParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStmtRule(flaskParser.ExpressionStmtRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#stmt_end}.
	 * @param ctx the parse tree
	 */
	void enterStmt_end(flaskParser.Stmt_endContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#stmt_end}.
	 * @param ctx the parse tree
	 */
	void exitStmt_end(flaskParser.Stmt_endContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionDefRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefRule(flaskParser.FunctionDefRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionDefRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefRule(flaskParser.FunctionDefRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmtRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmtRule(flaskParser.IfStmtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmtRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmtRule(flaskParser.IfStmtRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmtRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmtRule(flaskParser.ForStmtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmtRule}
	 * labeled alternative in {@link flaskParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmtRule(flaskParser.ForStmtRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportStmt(flaskParser.ImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportStmt(flaskParser.ImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(flaskParser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(flaskParser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#importItem}.
	 * @param ctx the parse tree
	 */
	void enterImportItem(flaskParser.ImportItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#importItem}.
	 * @param ctx the parse tree
	 */
	void exitImportItem(flaskParser.ImportItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(flaskParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(flaskParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(flaskParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(flaskParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(flaskParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(flaskParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(flaskParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(flaskParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(flaskParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(flaskParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(flaskParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(flaskParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(flaskParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(flaskParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(flaskParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(flaskParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(flaskParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(flaskParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(flaskParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(flaskParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(flaskParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(flaskParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#arith_expr}.
	 * @param ctx the parse tree
	 */
	void enterArith_expr(flaskParser.Arith_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#arith_expr}.
	 * @param ctx the parse tree
	 */
	void exitArith_expr(flaskParser.Arith_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(flaskParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(flaskParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(flaskParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(flaskParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierPrimary(flaskParser.IdentifierPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierPrimary(flaskParser.IdentifierPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterNumberPrimary(flaskParser.NumberPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitNumberPrimary(flaskParser.NumberPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterStringPrimary(flaskParser.StringPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitStringPrimary(flaskParser.StringPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code truePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterTruePrimary(flaskParser.TruePrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code truePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitTruePrimary(flaskParser.TruePrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falsePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterFalsePrimary(flaskParser.FalsePrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falsePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitFalsePrimary(flaskParser.FalsePrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterNonePrimary(flaskParser.NonePrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonePrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitNonePrimary(flaskParser.NonePrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterParenPrimary(flaskParser.ParenPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitParenPrimary(flaskParser.ParenPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterListPrimary(flaskParser.ListPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitListPrimary(flaskParser.ListPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dictPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterDictPrimary(flaskParser.DictPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dictPrimary}
	 * labeled alternative in {@link flaskParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitDictPrimary(flaskParser.DictPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterTrailer(flaskParser.TrailerContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitTrailer(flaskParser.TrailerContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(flaskParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(flaskParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(flaskParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(flaskParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#ign}.
	 * @param ctx the parse tree
	 */
	void enterIgn(flaskParser.IgnContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#ign}.
	 * @param ctx the parse tree
	 */
	void exitIgn(flaskParser.IgnContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(flaskParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(flaskParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteral(flaskParser.DictLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteral(flaskParser.DictLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void enterDictEntry(flaskParser.DictEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void exitDictEntry(flaskParser.DictEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link flaskParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_name(flaskParser.Dotted_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link flaskParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_name(flaskParser.Dotted_nameContext ctx);
}