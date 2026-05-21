package SymbolTable;

public class WebSymbol {
    public String name;
    public String type;    // HTML_ID, HTML_CLASS, JINJA_VAR, CSS_SELECTOR
    public int line;
    public String fileName;

    public WebSymbol(String name, String type, int line, String fileName) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.fileName = fileName;
    }
    @Override
    public String toString() {
        return String.format("%-25s | %-15s | %-6d | %-20s", name, type, line, fileName);
    }
}