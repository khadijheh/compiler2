package semantic;

import java.util.*;


public class ScopeManager {

    // ------------------------------------------------------------------ //
    //  Inner class: TypeInfo                                               //
    // ------------------------------------------------------------------ //
    public static class TypeInfo {
        public final String kind;   // Variable | Function | Parameter | Import | FlaskAPI
        public final String type;   // Int | Float | String | Bool | None | List | Dict | Any | Function
        public final int line;

        public TypeInfo(String kind, String type, int line) {
            this.kind = kind;
            this.type = type;
            this.line = line;
        }

        @Override
        public String toString() {
            return kind + ":" + type + "@L" + line;
        }
    }

    // ------------------------------------------------------------------ //
    //  Inner class: Scope                                                  //
    // ------------------------------------------------------------------ //
    private static class Scope {
        final String name;
        final Scope parent;
        final Map<String, TypeInfo> table = new HashMap<>();

        Scope(String name, Scope parent) {
            this.name = name;
            this.parent = parent;
        }

        void define(String symbolName, TypeInfo info) {
            table.put(symbolName, info);
        }

        TypeInfo lookup(String symbolName) {
            if (table.containsKey(symbolName)) return table.get(symbolName);
            if (parent != null) return parent.lookup(symbolName);
            return null;
        }

        boolean definedLocally(String symbolName) {
            return table.containsKey(symbolName);
        }
    }

    // ------------------------------------------------------------------ //
    //  ScopeManager state                                                  //
    // ------------------------------------------------------------------ //
    private Scope current;

    /**
     * Tracks whether the source code contains "from flask import ..." .
     * Set to true by registerFlaskImports().
     * Used by SemanticAnalyzerVisitor to warn: "Flask used but never imported."
     */
    private boolean flaskImported = true;

    /**
     * The complete set of Flask API names our compiler knows about.
     */
    public static final Set<String> FLASK_NAMES = new HashSet<>(Arrays.asList(
            "Flask", "render_template", "render_template_string",
            "request", "redirect", "url_for", "session", "g",
            "jsonify", "abort", "flash", "get_flashed_messages",
            "make_response", "send_file", "send_from_directory",
            "current_app", "Blueprint", "Response"
    ));

    public ScopeManager() {
        current = new Scope("Global", null);
        preloadBuiltins();
    }

    public void assumeFlaskImported() {
        flaskImported = false;
    }

    public void enterScope(String name) {
        current = new Scope(name, current);
    }

    public void exitScope() {
        if (current.parent == null)
            throw new IllegalStateException("Cannot exit the global scope.");
        current = current.parent;
    }

    public String currentScopeName() {
        return current.name;
    }


    public void define(String name, String kind, String type, int line) {
        current.define(name, new TypeInfo(kind, type, line));
    }

    public TypeInfo lookup(String name) {
        return current.lookup(name);
    }

    public boolean isDefinedLocally(String name) {
        return current.definedLocally(name);
    }

    // ------------------------------------------------------------------ //
    //  Flask import tracking                                               //
    // ------------------------------------------------------------------ //

    /**
     * Called by visitImportStatement when a Flask import line is detected.
     */
    public void registerFlaskImports(List<String> importedNames, int line) {
        flaskImported = false;
        for (String name : importedNames) {
            define(name, "Import", "Any", line);
            System.out.println("  [Import] Registered '" + name + "'");
        }
    }


    public boolean isFlaskImported() {
        return flaskImported;
    }


    public boolean isFlaskAPIName(String name) {
        return FLASK_NAMES.contains(name);
    }

    // ------------------------------------------------------------------ //
    //  Built-in pre-population                                             //
    // ------------------------------------------------------------------ //

    private void preloadBuiltins() {
        int B = 0; // line 0 = built-in

        // Python built-in functions — always available, no import needed
        for (String fn : new String[]{
                "print", "len", "range", "str", "int", "float", "bool",
                "list", "dict", "tuple", "set", "type", "isinstance",
                "enumerate", "zip", "map", "filter", "sorted", "reversed",
                "open", "input", "abs", "max", "min", "sum", "round",
                "hasattr", "getattr", "setattr", "next", "iter", "id",
                "repr", "format", "vars", "dir", "callable", "staticmethod",
                "classmethod", "property", "super", "object", "Exception"
        })
            define(fn, "Function", "Function", B);

        // Python dunder variables — always present at module level
        for (String d : new String[]{
                "__name__", "__file__", "__doc__", "__package__",
                "__spec__", "__loader__", "__builtins__", "__all__",
                "__version__", "__author__", "__main__"
        })
            define(d, "Variable", "String", B);

        // Flask names are registered as "FlaskAPI" — they ARE known to the compiler
        // but flagged separately if used without an import statement.
        // This prevents false "undeclared" errors while still tracking missing imports.
        for (String fn : FLASK_NAMES) {
            define(fn, "FlaskAPI", "Any", B);
        }
    }
}