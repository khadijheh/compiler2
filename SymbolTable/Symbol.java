package SymbolTable;

public class Symbol {
    public String name;
    public String type;    // Variable, Function, Parameter
    public int line;
    public String scope;   // Global, Local(function_name)

    public Symbol(String name, String type, int line, String scope) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.scope = scope;
    }

    @Override
    public String toString() {
        return String.format("%-15s | %-12s | %-5d | %-20s", name, type, line, scope);
    }
}