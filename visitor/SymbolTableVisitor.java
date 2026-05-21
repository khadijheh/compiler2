package visitor;

import AST.*;
import SymbolTable.Scope;

public class SymbolTableVisitor {
    private Scope currentScope = new Scope(null, "Global");

    public void build(AstNode node) {
        if (node == null) return;
        if (node instanceof Program) {
            for (AstNode child : node.getChildren()) {
                build(child);
            }
        }

        else if (node instanceof ImportStatement) {

             for (AstNode child : node.getChildren()) {
                if (child instanceof Identifier) {
                    Identifier id = (Identifier) child;
                    currentScope.define(id.getName(), "Imported Name", id.getLine());
                }
            }
        }

        else if (node instanceof Assign) {
                Assign assignNode = (Assign) node;
                if (assignNode.getLeft() instanceof Identifier) {
                Identifier id = (Identifier) assignNode.getLeft();
                    currentScope.define(id.getName(), "Variable", id.getLine());
            }
        }


         else if (node instanceof FunctionDef) {
            FunctionDef func = (FunctionDef) node;
            currentScope.define(func.getName(), "Function", func.getLine());

            Scope previous = currentScope;
            currentScope = new Scope(previous, "Local (" + func.getName() + ")");

            for (String param : func.getParameters()) {
                currentScope.define(param, "Parameter", func.getLine());
            }

            for (AstNode stmt : func.getBody()) {
                build(stmt);
            }
            currentScope = previous;
        }

         else if (node instanceof ForStatement) {
            ForStatement forNode = (ForStatement) node;
            currentScope.define(forNode.getIteratorId(), "Variable (Loop)", forNode.getLine());
             for (AstNode stmt : forNode.getBody()) build(stmt);
        }
        else if (node instanceof IfStatement) {
            IfStatement ifNode = (IfStatement) node;
            for (AstNode stmt : ifNode.getIfBody()) {
                build(stmt);
            }
        }




    }
}