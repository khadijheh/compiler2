//package visitor;
//
//import AST.*;
//import grammers.flaskParser;
//import grammers.flaskParserBaseVisitor;
//import org.antlr.v4.runtime.tree.TerminalNode;
//
//import java.util.*;
//
//public class PythonVisitor extends flaskParserBaseVisitor<AstNode> {
//
//    @Override
//    public AstNode visitProgram(flaskParser.ProgramContext ctx) {
//        List<AstNode> statements = new ArrayList<>();
//        for (var stmt : ctx.statement()) {
//            AstNode node = visit(stmt);
//            if (node != null) statements.add(node);
//        }
//        return new Program(ctx.start.getLine(), statements);
//    }
//
//
//    @Override
//    public AstNode visitImportStmtRule(flaskParser.ImportStmtRuleContext ctx) {
//        return visit(ctx.importStmt());
//    }
//
//
//    @Override
//    public AstNode visitAssignStmtRule(flaskParser.AssignStmtRuleContext ctx) {
//        return visit(ctx.assignStmt());
//    }
//
//
//    @Override
//    public AstNode visitReturnStmtRule(flaskParser.ReturnStmtRuleContext ctx) {
//        return visit(ctx.returnStmt());
//    }
//
//
//    @Override
//    public AstNode visitExpressionStmtRule(flaskParser.ExpressionStmtRuleContext ctx) {
//        return visit(ctx.exprStmt());
//    }
//
//
//    public AstNode visitFunctionDefRule(flaskParser.FunctionDefRuleContext ctx) {
//        return visit(ctx.functionDef());
//    }
//
//
//    public AstNode visitIfStmtRule(flaskParser.IfStmtRuleContext ctx) {
//        return visit(ctx.ifStmt());
//    }
//
//
//    public AstNode visitForStmtRule(flaskParser.ForStmtRuleContext ctx) {
//        return visit(ctx.forStmt());
//    }
//
//
//    @Override
//    public AstNode visitIdentifierPrimary(flaskParser.IdentifierPrimaryContext ctx) {
//        return new Identifier(ctx.ID().getText(), ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitNumberPrimary(flaskParser.NumberPrimaryContext ctx) {
//        return new NumberLiteral(ctx.NUMBER().getText(), ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitStringPrimary(flaskParser.StringPrimaryContext ctx) {
//        String raw = ctx.STRING().getText();
//        return new StringLiteral(raw.substring(1, raw.length() - 1), ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitTruePrimary(flaskParser.TruePrimaryContext ctx) {
//        return new BooleanLiteral(true, ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitFalsePrimary(flaskParser.FalsePrimaryContext ctx) {
//        return new BooleanLiteral(false, ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitNonePrimary(flaskParser.NonePrimaryContext ctx) {
//        return new NoneLiteral(ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitParenPrimary(flaskParser.ParenPrimaryContext ctx) {
//        return (ctx.expression() != null) ? visit(ctx.expression()) : null;
//    }
//
//    @Override
//    public AstNode visitListPrimary(flaskParser.ListPrimaryContext ctx) {
//        return visit(ctx.listLiteral());
//    }
//
//    @Override
//    public AstNode visitDictPrimary(flaskParser.DictPrimaryContext ctx) {
//        return visit(ctx.dictLiteral());
//    }
//
//    @Override
//    public AstNode visitAssignStmt(flaskParser.AssignStmtContext ctx) {
//        AstNode left = visit(ctx.expression(0));
//        AstNode right = visit(ctx.expression(1));
//        return new Assign(left, right, ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitFunctionDef(flaskParser.FunctionDefContext ctx) {
//        String name = ctx.ID().getText();
//        List<String> params = new ArrayList<>();
//        if (ctx.paramList() != null) {
//            for (TerminalNode id : ctx.paramList().ID()) {
//                params.add(id.getText());
//            }
//        }
//        List<AstNode> body = extractBlock(ctx.block());
//        return new FunctionDef(name, params, body, ctx.start.getLine());
//    }
//
//    @Override
//    public AstNode visitIfStmt(flaskParser.IfStmtContext ctx) {
//        AstNode cond = visit(ctx.expression(0));
//        List<AstNode> ifBlock = extractBlock(ctx.block(0));
//        IfStatement ifStmt = new IfStatement(cond, ifBlock, ctx.start.getLine());
//
//        // Elif logic
//        for (int i = 1; i < ctx.block().size(); i++) {
//            if (i >= ctx.expression().size()) {
//                List<AstNode> elseBlock = extractBlock(ctx.block(i));
//                ifStmt.addChild(new AstNode("ElseBody", ctx.start.getLine()) {{
//                    for (AstNode s : elseBlock) addChild(s);
//                }});
//            } else {
//                AstNode elifCond = visit(ctx.expression(i));
//                List<AstNode> elifBlock = extractBlock(ctx.block(i));
//                ifStmt.addChild(new BinaryExpression(elifCond, "elif", new Program(0, elifBlock), 0));
//            }
//        }
//        return ifStmt;
//    }
//
//    @Override
//    public AstNode visitFactor(flaskParser.FactorContext ctx) {
//        AstNode current = visit(ctx.primary());
//        for (var tr : ctx.trailer()) {
//            if (tr.LPAREN() != null) {
//                List<AstNode> args = new ArrayList<>();
//                if (tr.argumentList() != null) {
//                    for (var arg : tr.argumentList().argument()) args.add(visit(arg));
//                }
//                current = new FunctionCall(current, args, tr.start.getLine());
//            } else if (tr.LBRACK() != null) {
//                current = new Subscript(current, visit(tr.expression()), tr.start.getLine());
//            } else if (tr.DOT() != null) {
//                current = new AttributeAccess(current, tr.ID().getText(), tr.start.getLine());
//            }
//        }
//        return current;
//    }
//
//    private List<AstNode> extractBlock(flaskParser.BlockContext ctx) {
//        List<AstNode> list = new ArrayList<>();
//        for (var stmt : ctx.statement()) {
//            AstNode node = visit(stmt);
//            if (node != null) list.add(node);
//        }
//        return list;
//    }
//
//    @Override
//    public AstNode visitImportStmt(flaskParser.ImportStmtContext ctx) {
//        List<String> names = new ArrayList<>();
//        if (ctx.FROM() != null && ctx.importList() != null) {
//            for (var item : ctx.importList().importItem()) {
//                names.add(item.ID(0).getText());
//            }
//        } else if (ctx.IMPORT() != null) {
//            names.add(ctx.dotted_name().getText());
//        }
//        return new ImportStatement(ctx.dotted_name().getText(), names, ctx.start.getLine());
//    }
//}
//
//
////package visitor;
////
////import AST.*;
////import grammers.flaskParser;
////import grammers.flaskParserBaseVisitor;
////import org.antlr.v4.runtime.tree.TerminalNode;
////
////import java.util.*;
////
////
////public class PythonVisitor extends flaskParserBaseVisitor<AstNode> {
////
////    @Override
////    public AstNode visitProgram(flaskParser.ProgramContext ctx) {
////
////        List<AstNode> statements = new ArrayList<>();
////        for (var stmt : ctx.statement()) {
////            AstNode node = visit(stmt);
////            if (node != null) statements.add(node);
////        }
////        return new Program(ctx.start.getLine(), statements);
////    }
////
////    @Override
////    public AstNode visitImportStmt(flaskParser.ImportStmtContext ctx) {
////        if (ctx.FROM() != null) {
////            String module = ctx.dotted_name().getText();
////            List<String> names = new ArrayList<>();
////
////            for (var item : ctx.importList().importItem()) {
////                String name = item.ID(0).getText();
////                names.add(name);
////            }
////
////            return new ImportStatement(module, names, ctx.start.getLine());
////        }
////
////        if (ctx.IMPORT() != null) {
////            String module = ctx.dotted_name().getText();
////            return new ImportStatement(module, Collections.emptyList(), ctx.start.getLine());
////        }
////
////        return null;
////    }
////
////    @Override
////    public AstNode visitAssignStmt(flaskParser.AssignStmtContext ctx) {
////        AstNode left = visit(ctx.expression(0));
////        AstNode right = visit(ctx.expression(1));
////        return new Assign(left, right, ctx.start.getLine());
////    }
////    @Override
////    public AstNode visitFunctionDef(flaskParser.FunctionDefContext ctx) {
////        String name = ctx.ID().getText();
////
////        List<String> params = new ArrayList<>();
////        if (ctx.paramList() != null) {
////            for (TerminalNode id : ctx.paramList().ID()) {
////                params.add(id.getText());
////            }
////        }
////
////        List<AstNode> body = extractBlock(ctx.block());
////        FunctionDef func = new FunctionDef(name, params, body, ctx.start.getLine());
////
////        if (ctx.decorator() != null && !ctx.decorator().isEmpty()) {
////            List<Decorator> decorators = new ArrayList<>();
////            for (var dec : ctx.decorator()) {
////                decorators.add((Decorator) visit(dec));
////            }
////
////            AstNode decoNode = new AstNode("Decorators", ctx.start.getLine()) {};
////            for (Decorator d : decorators) {
////                decoNode.addChild(d);
////            }
////            func.addChild(decoNode);
////        }
////
////        return func;
////    }
////
////    @Override
////    public AstNode visitDecorator(flaskParser.DecoratorContext ctx) {
////        String name = ctx.dotted_name().getText();
////        List<AstNode> args = new ArrayList<>();
////
////        if (ctx.argumentList() != null) {
////            for (var arg : ctx.argumentList().argument()) {
////                args.add(visit(arg));
////            }
////        }
////
////        return new Decorator(name, args, ctx.start.getLine());
////    }
////    @Override
////    public AstNode visitIfStmt(flaskParser.IfStmtContext ctx) {
////        AstNode cond = visit(ctx.expression(0));
////        List<AstNode> ifBlock = extractBlock(ctx.block(0));
////        IfStatement ifStmt = new IfStatement(cond, ifBlock, ctx.start.getLine());
////
////        int exprCount = ctx.expression().size();
////        for (int i = 1; i < exprCount; i++) {
////            AstNode elifCond = visit(ctx.expression(i));
////            List<AstNode> elifBlock = extractBlock(ctx.block(i));
////
////            AstNode elifNode = new AstNode("Elif", ctx.start.getLine()) {};
////            elifNode.addChild(elifCond);
////
////            AstNode elifBody = new AstNode("Body", ctx.start.getLine()) {};
////            for (AstNode s : elifBlock) elifBody.addChild(s);
////
////            elifNode.addChild(elifBody);
////            ifStmt.addChild(elifNode);
////        }
////
////        if (ctx.ELSE() != null) {
////            List<AstNode> elseBlock = extractBlock(ctx.block(ctx.block().size() - 1));
////
////            AstNode elseNode = new AstNode("Else", ctx.start.getLine()) {};
////            AstNode elseBody = new AstNode("Body", ctx.start.getLine()) {};
////            for (AstNode s : elseBlock) elseBody.addChild(s);
////
////            elseNode.addChild(elseBody);
////            ifStmt.addChild(elseNode);
////        }
////
////        return ifStmt;
////    }
////    @Override
////    public AstNode visitForStmt(flaskParser.ForStmtContext ctx) {
////        String var = ctx.ID().getText();
////        AstNode iterable = visit(ctx.expression());
////        List<AstNode> body = extractBlock(ctx.block());
////        return new ForStatement(var, iterable, body, ctx.start.getLine());
////    }
////    @Override
////    public AstNode visitReturnStmt(flaskParser.ReturnStmtContext ctx) {
////        if (ctx.expression().isEmpty()) {
////            return new ReturnStatement(null, ctx.start.getLine());
////        }
////        if (ctx.expression().size() > 1) {
////            List<AstNode> items = new ArrayList<>();
////            for (var e : ctx.expression()) items.add(visit(e));
////            AstNode tupleLike = new ListLiteral(items, ctx.start.getLine());
////            return new ReturnStatement(tupleLike, ctx.start.getLine());
////        }
////
////        return new ReturnStatement(visit(ctx.expression(0)), ctx.start.getLine());
////    }
////
////    @Override
////    public AstNode visitExpression(flaskParser.ExpressionContext ctx) {
////        if (ctx.comparison().size() > 1) {
////            List<AstNode> items = new ArrayList<>();
////            for (var c : ctx.comparison()) items.add(visit(c));
////            return new ListLiteral(items, ctx.start.getLine());
////        }
////        return visit(ctx.comparison(0));
////    }
////
////    @Override
////    public AstNode visitComparison(flaskParser.ComparisonContext ctx) {
////        // comparison : arith_expr ((EQ|NEQ|LT|GT|LE|GE) arith_expr)*
////        AstNode left = visit(ctx.arith_expr(0));
////        if (ctx.arith_expr().size() == 1) return left;
////
////        int opsCount = ctx.getChildCount();
////        for (int i = 1; i < ctx.arith_expr().size(); i++) {
////            String op = ctx.getChild(2 * i - 1).getText();
////            AstNode right = visit(ctx.arith_expr(i));
////            left = new BinaryExpression(left, op, right, ctx.start.getLine());
////        }
////        return left;
////    }
////
////    @Override
////    public AstNode visitArith_expr(flaskParser.Arith_exprContext ctx) {
////        AstNode left = visit(ctx.term(0));
////        if (ctx.term().size() == 1) return left;
////
////        for (int i = 1; i < ctx.term().size(); i++) {
////            String op = ctx.getChild(2 * i - 1).getText(); // + أو -
////            AstNode right = visit(ctx.term(i));
////            left = new BinaryExpression(left, op, right, ctx.start.getLine());
////        }
////        return left;
////    }
////
////    @Override
////    public AstNode visitTerm(flaskParser.TermContext ctx) {
////        AstNode left = visit(ctx.factor(0));
////        if (ctx.factor().size() == 1) return left;
////
////        for (int i = 1; i < ctx.factor().size(); i++) {
////            String op = ctx.getChild(2 * i - 1).getText(); // * أو /
////            AstNode right = visit(ctx.factor(i));
////            left = new BinaryExpression(left, op, right, ctx.start.getLine());
////        }
////        return left;
////    }
////    @Override
////    public AstNode visitFactor(flaskParser.FactorContext ctx) {
////        AstNode current = visit(ctx.primary());
////
////        for (var tr : ctx.trailer()) {
////            if (tr.LPAREN() != null) {
////                List<AstNode> args = new ArrayList<>();
////                if (tr.argumentList() != null) {
////                    for (var arg : tr.argumentList().argument()) {
////                        args.add(visit(arg));
////                    }
////                }
////                current = new FunctionCall(current, args, tr.start.getLine());
////            } else if (tr.LBRACK() != null) {
////                AstNode index = visit(tr.expression());
////                current = new Subscript(current, index, tr.start.getLine());
////            } else if (tr.DOT() != null) {
////                current = new AttributeAccess(current, tr.ID().getText(), tr.start.getLine());
////            }
////        }
////
////        return current;
////    }
////    @Override
////    public AstNode visitPrimary(flaskParser.PrimaryContext ctx) {
////        if (ctx.NUMBER() != null)
////            return new NumberLiteral(ctx.NUMBER().getText(), ctx.start.getLine());
////
////        if (ctx.STRING() != null) {
////            String raw = ctx.STRING().getText();
////            return new StringLiteral(raw.substring(1, raw.length() - 1), ctx.start.getLine());
////        }
////
////        if (ctx.TRUE() != null)
////            return new BooleanLiteral(true, ctx.start.getLine());
////
////        if (ctx.FALSE() != null)
////            return new BooleanLiteral(false, ctx.start.getLine());
////
////        if (ctx.NONE() != null)
////            return new NoneLiteral(ctx.start.getLine());
////
////        if (ctx.ID() != null)
////            return new Identifier(ctx.ID().getText(), ctx.start.getLine());
////
////        if (ctx.expression() != null)
////            return visit(ctx.expression());
////
////        if (ctx.listLiteral() != null)
////            return visit(ctx.listLiteral());
////
////        if (ctx.dictLiteral() != null)
////            return visit(ctx.dictLiteral());
////
////        return null;
////    }
////    @Override
////    public AstNode visitListLiteral(flaskParser.ListLiteralContext ctx) {
////        List<AstNode> elements = new ArrayList<>();
////        for (var expr : ctx.expression()) {
////            elements.add(visit(expr));
////        }
////        return new ListLiteral(elements, ctx.start.getLine());
////    }
////
////    @Override
////    public AstNode visitDictLiteral(flaskParser.DictLiteralContext ctx) {
////        Map<AstNode, AstNode> map = new LinkedHashMap<>();
////
////        for (var entry : ctx.dictEntry()) {
////            AstNode key = visit(entry.expression(0));
////            AstNode value = visit(entry.expression(1));
////            map.put(key, value);
////        }
////
////        return new DictLiteral(map, ctx.start.getLine());
////    }
////
////    @Override
////    public AstNode visitArgument(flaskParser.ArgumentContext ctx) {
////        // argument : ID ASSIGN expression | expression
////        if (ctx.ID() != null && ctx.ASSIGN() != null) {
////            return new KeywordArgument(ctx.ID().getText(), visit(ctx.expression()), ctx.start.getLine());
////        }
////        return visit(ctx.expression());
////    }
////
////    private List<AstNode> extractBlock(flaskParser.BlockContext ctx) {
////        List<AstNode> list = new ArrayList<>();
////        for (var stmt : ctx.statement()) {
////            AstNode node = visit(stmt);
////            if (node != null) list.add(node);
////        }
////        return list;
////    }
////}
package visitor;

import AST.*;
import grammers.flaskParser;
import grammers.flaskParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class PythonVisitor extends flaskParserBaseVisitor<AstNode> {

    // ================================================================== //
    //  Program                                                             //
    // ================================================================== //

    @Override
    public AstNode visitProgram(flaskParser.ProgramContext ctx) {
        List<AstNode> statements = new ArrayList<>();
        for (var stmt : ctx.statement()) {
            AstNode node = visit(stmt);
            if (node != null) statements.add(node);
        }
        return new Program(ctx.start.getLine(), statements);
    }

    // ================================================================== //
    //  Statement dispatch wrappers                                         //
    // ================================================================== //

    @Override
    public AstNode visitImportStmtRule(flaskParser.ImportStmtRuleContext ctx) {
        return visit(ctx.importStmt());
    }

    @Override
    public AstNode visitAssignStmtRule(flaskParser.AssignStmtRuleContext ctx) {
        return visit(ctx.assignStmt());
    }

    @Override
    public AstNode visitReturnStmtRule(flaskParser.ReturnStmtRuleContext ctx) {
        return visit(ctx.returnStmt());
    }

    @Override
    public AstNode visitExpressionStmtRule(flaskParser.ExpressionStmtRuleContext ctx) {
        return visit(ctx.exprStmt());
    }

    @Override
    public AstNode visitFunctionDefRule(flaskParser.FunctionDefRuleContext ctx) {
        return visit(ctx.functionDef());
    }

    @Override
    public AstNode visitIfStmtRule(flaskParser.IfStmtRuleContext ctx) {
        return visit(ctx.ifStmt());
    }

    @Override
    public AstNode visitForStmtRule(flaskParser.ForStmtRuleContext ctx) {
        return visit(ctx.forStmt());
    }

    // ================================================================== //
    //  Import                                                              //
    // ================================================================== //

    @Override
    public AstNode visitImportStmt(flaskParser.ImportStmtContext ctx) {
        List<String> names = new ArrayList<>();
        if (ctx.FROM() != null && ctx.importList() != null) {
            for (var item : ctx.importList().importItem()) {
                names.add(item.ID(0).getText());
            }
        } else if (ctx.IMPORT() != null) {
            names.add(ctx.dotted_name().getText());
        }
        return new ImportStatement(
                ctx.dotted_name().getText(), names, ctx.start.getLine());
    }

    // ================================================================== //
    //  FunctionDef                                                         //
    //                                                                      //
    //  NOTE: The grammar uses  paramList : ID (COMMA ID)*                 //
    //  So we read  ctx.paramList().ID()  — a list of TerminalNode.        //
    //  There are NO param() or typeAnnotation() methods in the            //
    //  generated ParamListContext / FunctionDefContext.                    //
    //  paramTypes defaults to "Any" for all params (no annotation yet).   //
    // ================================================================== //

    @Override
    public AstNode visitFunctionDef(flaskParser.FunctionDefContext ctx) {
        String name = ctx.ID().getText();

        // Read parameter NAMES — uses ID() as the grammar generates
        List<String> params     = new ArrayList<>();
        List<String> paramTypes = new ArrayList<>();  // all "Any" — no grammar annotation yet

        if (ctx.paramList() != null) {
            for (TerminalNode id : ctx.paramList().ID()) {
                params.add(id.getText());
                paramTypes.add("Any");   // no type annotation in current grammar
            }
        }

        List<AstNode> body = extractBlock(ctx.block());

        // Use the new 6-arg constructor that stores paramTypes + returnType
        FunctionDef funcNode = new FunctionDef(
                name, params, paramTypes, "Any", body, ctx.start.getLine());

        // Attach decorators (@app.route etc.) as children
        if (ctx.decorator() != null && !ctx.decorator().isEmpty()) {
            for (var dec : ctx.decorator()) {
                AstNode decNode = visit(dec);
                if (decNode != null) funcNode.addChild(decNode);
            }
        }

        return funcNode;
    }

    // ================================================================== //
    //  Decorator                                                           //
    // ================================================================== //

    @Override
    public AstNode visitDecorator(flaskParser.DecoratorContext ctx) {
        String name = ctx.dotted_name().getText();
        List<AstNode> args = new ArrayList<>();
        if (ctx.argumentList() != null) {
            for (var arg : ctx.argumentList().argument()) {
                args.add(visit(arg));
            }
        }
        return new Decorator(name, args, ctx.start.getLine());
    }

    // ================================================================== //
    //  Statements                                                          //
    // ================================================================== //

    @Override
    public AstNode visitAssignStmt(flaskParser.AssignStmtContext ctx) {
        AstNode left  = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        return new Assign(left, right, ctx.start.getLine());
    }

    @Override
    public AstNode visitReturnStmt(flaskParser.ReturnStmtContext ctx) {
        if (ctx.expression().isEmpty()) {
            return new ReturnStatement(null, ctx.start.getLine());
        }
        if (ctx.expression().size() > 1) {
            List<AstNode> items = new ArrayList<>();
            for (var e : ctx.expression()) items.add(visit(e));
            return new ReturnStatement(
                    new ListLiteral(items, ctx.start.getLine()),
                    ctx.start.getLine());
        }
        return new ReturnStatement(
                visit(ctx.expression(0)), ctx.start.getLine());
    }

    @Override
    public AstNode visitForStmt(flaskParser.ForStmtContext ctx) {
        String        var      = ctx.ID().getText();
        AstNode       iterable = visit(ctx.expression());
        List<AstNode> body     = extractBlock(ctx.block());
        return new ForStatement(var, iterable, body, ctx.start.getLine());
    }

    @Override
    public AstNode visitIfStmt(flaskParser.IfStmtContext ctx) {
        AstNode       cond    = visit(ctx.expression(0));
        List<AstNode> ifBlock = extractBlock(ctx.block(0));
        IfStatement   ifStmt  = new IfStatement(cond, ifBlock, ctx.start.getLine());

        int exprCount = ctx.expression().size();
        for (int i = 1; i < exprCount; i++) {
            AstNode       elifCond  = visit(ctx.expression(i));
            List<AstNode> elifBlock = extractBlock(ctx.block(i));
            AstNode elifNode = new AstNode("Elif", ctx.start.getLine()) {};
            elifNode.addChild(elifCond);
            AstNode elifBody = new AstNode("Body", ctx.start.getLine()) {};
            for (AstNode s : elifBlock) elifBody.addChild(s);
            elifNode.addChild(elifBody);
            ifStmt.addChild(elifNode);
        }

        if (ctx.ELSE() != null) {
            List<AstNode> elseBlock =
                    extractBlock(ctx.block(ctx.block().size() - 1));
            AstNode elseNode = new AstNode("Else", ctx.start.getLine()) {};
            AstNode elseBody = new AstNode("Body", ctx.start.getLine()) {};
            for (AstNode s : elseBlock) elseBody.addChild(s);
            elseNode.addChild(elseBody);
            ifStmt.addChild(elseNode);
        }

        return ifStmt;
    }

    // ================================================================== //
    //  Expressions                                                         //
    // ================================================================== //

    @Override
    public AstNode visitExpression(flaskParser.ExpressionContext ctx) {
        if (ctx.comparison().size() > 1) {
            List<AstNode> items = new ArrayList<>();
            for (var c : ctx.comparison()) items.add(visit(c));
            return new ListLiteral(items, ctx.start.getLine());
        }
        return visit(ctx.comparison(0));
    }

    @Override
    public AstNode visitComparison(flaskParser.ComparisonContext ctx) {
        AstNode left = visit(ctx.arith_expr(0));
        if (ctx.arith_expr().size() == 1) return left;
        for (int i = 1; i < ctx.arith_expr().size(); i++) {
            String  op    = ctx.getChild(2 * i - 1).getText();
            AstNode right = visit(ctx.arith_expr(i));
            left = new BinaryExpression(left, op, right, ctx.start.getLine());
        }
        return left;
    }

    @Override
    public AstNode visitArith_expr(flaskParser.Arith_exprContext ctx) {
        AstNode left = visit(ctx.term(0));
        if (ctx.term().size() == 1) return left;
        for (int i = 1; i < ctx.term().size(); i++) {
            String  op    = ctx.getChild(2 * i - 1).getText();
            AstNode right = visit(ctx.term(i));
            left = new BinaryExpression(left, op, right, ctx.start.getLine());
        }
        return left;
    }

    @Override
    public AstNode visitTerm(flaskParser.TermContext ctx) {
        AstNode left = visit(ctx.factor(0));
        if (ctx.factor().size() == 1) return left;
        for (int i = 1; i < ctx.factor().size(); i++) {
            String  op    = ctx.getChild(2 * i - 1).getText();
            AstNode right = visit(ctx.factor(i));
            left = new BinaryExpression(left, op, right, ctx.start.getLine());
        }
        return left;
    }

    @Override
    public AstNode visitFactor(flaskParser.FactorContext ctx) {
        AstNode current = visit(ctx.primary());
        for (var tr : ctx.trailer()) {
            if (tr.LPAREN() != null) {
                List<AstNode> args = new ArrayList<>();
                if (tr.argumentList() != null) {
                    for (var arg : tr.argumentList().argument())
                        args.add(visit(arg));
                }
                current = new FunctionCall(current, args, tr.start.getLine());
            } else if (tr.LBRACK() != null) {
                current = new Subscript(
                        current, visit(tr.expression()), tr.start.getLine());
            } else if (tr.DOT() != null) {
                current = new AttributeAccess(
                        current, tr.ID().getText(), tr.start.getLine());
            }
        }
        return current;
    }

    // ================================================================== //
    //  Literals                                                            //
    // ================================================================== //

    @Override
    public AstNode visitListLiteral(flaskParser.ListLiteralContext ctx) {
        List<AstNode> elements = new ArrayList<>();
        for (var expr : ctx.expression()) elements.add(visit(expr));
        return new ListLiteral(elements, ctx.start.getLine());
    }

    @Override
    public AstNode visitDictLiteral(flaskParser.DictLiteralContext ctx) {
        // DictLiteral constructor requires Map<AstNode, AstNode>
        Map<AstNode, AstNode> entries = new LinkedHashMap<>();
        for (var entry : ctx.dictEntry()) {
            AstNode key   = visit(entry.expression(0));
            AstNode value = visit(entry.expression(1));
            entries.put(key, value);
        }
        return new DictLiteral(entries, ctx.start.getLine());
    }

    @Override
    public AstNode visitArgument(flaskParser.ArgumentContext ctx) {
        if (ctx.ID() != null && ctx.ASSIGN() != null) {
            return new KeywordArgument(
                    ctx.ID().getText(), visit(ctx.expression()), ctx.start.getLine());
        }
        return visit(ctx.expression());
    }

    // ================================================================== //
    //  Primary atoms                                                       //
    // ================================================================== //

    @Override
    public AstNode visitIdentifierPrimary(flaskParser.IdentifierPrimaryContext ctx) {
        return new Identifier(ctx.ID().getText(), ctx.start.getLine());
    }

    @Override
    public AstNode visitNumberPrimary(flaskParser.NumberPrimaryContext ctx) {
        return new NumberLiteral(ctx.NUMBER().getText(), ctx.start.getLine());
    }

    @Override
    public AstNode visitStringPrimary(flaskParser.StringPrimaryContext ctx) {
        String raw = ctx.STRING().getText();
        return new StringLiteral(raw.substring(1, raw.length() - 1), ctx.start.getLine());
    }

    @Override
    public AstNode visitTruePrimary(flaskParser.TruePrimaryContext ctx) {
        return new BooleanLiteral(true, ctx.start.getLine());
    }

    @Override
    public AstNode visitFalsePrimary(flaskParser.FalsePrimaryContext ctx) {
        return new BooleanLiteral(false, ctx.start.getLine());
    }

    @Override
    public AstNode visitNonePrimary(flaskParser.NonePrimaryContext ctx) {
        return new NoneLiteral(ctx.start.getLine());
    }

    @Override
    public AstNode visitParenPrimary(flaskParser.ParenPrimaryContext ctx) {
        return (ctx.expression() != null) ? visit(ctx.expression()) : null;
    }

    @Override
    public AstNode visitListPrimary(flaskParser.ListPrimaryContext ctx) {
        return visit(ctx.listLiteral());
    }

    @Override
    public AstNode visitDictPrimary(flaskParser.DictPrimaryContext ctx) {
        return visit(ctx.dictLiteral());
    }

    // ================================================================== //
    //  Helpers                                                             //
    // ================================================================== //

    private List<AstNode> extractBlock(flaskParser.BlockContext ctx) {
        List<AstNode> list = new ArrayList<>();
        for (var stmt : ctx.statement()) {
            AstNode node = visit(stmt);
            if (node != null) list.add(node);
        }
        return list;
    }
}