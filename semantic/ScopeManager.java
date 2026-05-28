package semantic;

import java.util.*;

public class ScopeManager {

    // ------------------------------------------------------------------ //
    //  Inner class: TypeInfo                                               //
    // ------------------------------------------------------------------ //
    public static class TypeInfo {
        public final String kind;
        // Variable | Function | Parameter | Import | FlaskAPI | Builtin
        public final String type;
        // Int | Float | String | Bool | None | List | Dict | Any | Function
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

    private static class Scope {
        final String name;
        final Scope parent;
        final Map<String, TypeInfo> table = new LinkedHashMap<>();

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

    private Scope current;


    private boolean suppressImportCheck = false;


    private final Set<String> importedFlaskNames = new HashSet<>();


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

    public void suppressFlaskImportCheck() {
        suppressImportCheck = true;
    }


    public void registerFlaskImports(List<String> importedNames, int line) {
        for (String name : importedNames) {
            if (FLASK_NAMES.contains(name)) {
                importedFlaskNames.add(name);
            }
            // Redefine so lookup() returns non-null with updated kind.
            define(name, "Import", "Any", line);
            System.out.println("  [Import] Registered '" + name + "'");
        }
    }

    /**
     * Returns true if import-checking is globally suppressed
     * (i.e. the file had no import statements at all).
     */
    public boolean isImportCheckSuppressed() {
        return suppressImportCheck;
    }

    /**
     * Returns true if the given Flask name was explicitly imported.
     * Used by visitIdentifier / visitFunctionCall to detect missing imports.
     */
    public boolean isFlaskNameImported(String name) {
        return importedFlaskNames.contains(name);
    }


    public boolean isFlaskAPIName(String name) {
        return FLASK_NAMES.contains(name);
    }

    private void preloadBuiltins() {
        final int B = 0;

        for (String fn : new String[]{
                "print", "len", "range", "str", "int", "float", "bool",
                "list", "dict", "tuple", "set", "type", "isinstance",
                "enumerate", "zip", "map", "filter", "sorted", "reversed",
                "open", "input", "abs", "max", "min", "sum", "round",
                "hasattr", "getattr", "setattr", "next", "iter", "id",
                "repr", "format", "vars", "dir", "callable", "staticmethod",
                "classmethod", "property", "super", "object", "Exception",
                "ValueError", "TypeError", "KeyError", "IndexError",
                "AttributeError", "RuntimeError", "StopIteration", "IOError"
        }) {
            define(fn, "Builtin", "Function", B);
        }

        for (String d : new String[]{
                "__name__", "__file__", "__doc__", "__package__",
                "__spec__", "__loader__", "__builtins__", "__all__",
                "__version__", "__author__", "__main__"
        }) {
            define(d, "Variable", "String", B);
        }


        for (String fn : FLASK_NAMES) {
            define(fn, "FlaskAPI", "Any", B);
        }
    }
}