//package AST;
//
////public class FunctionDef extends AstNode {
////    private String functionName;      // اسم التابع
////    private List<String> parameters;  // قائمة أسماء البارامترات (IDs)
////    private List<AstNode> body;       // قائمة التعليمات داخل التابع
////    private List<Decorator> decorators = new ArrayList<>();
////    public void setDecorators(List<Decorator> decorators) {
////        this.decorators = decorators;
////    }
////    public FunctionDef(String functionName, List<String> parameters, List<AstNode> body, int linenumber) {
////
////        super("FunctionDefinition", linenumber);
////        this.functionName = functionName;
////        this.parameters = parameters;
////        this.body = body;
////    }
////
////    // Getters
////    public String getFunctionName() { return functionName; }
////    public List<String> getParameters() { return parameters; }
////    public List<AstNode> getBody() { return body; }
////
////    @Override
////    public String toString() {
////        StringBuilder sb = new StringBuilder();
////        sb.append("Function: ").append(functionName)
////                .append(" Params: ").append(parameters);
////
////        if (body != null && !body.isEmpty()) {
////            sb.append(" {\n");
////            for (AstNode node : body) {
////                // إضافة مسافة بادئة (Indentation) لترتيب الشكل
////                sb.append("      --> ").append(node.toString()).append("\n");
////            }
////            sb.append("   }");
////        }
////        return sb.toString();
////    }
////}
//import java.util.List;
//import java.util.Map;
//
//public class FunctionDef extends AstNode {
//    private String name;
//    private List<String> params;
//    private List<AstNode> body;
//    private Map<String, String> paramTypes;    // parameter name -> type
//    private String returnType;                  // return type annotation
//    private List<String> decorators;            // decorator names (@app.route, etc.)
//
//    public FunctionDef(String name, List<String> params, List<AstNode> body, int line) {
//        super("FunctionDef(" + name + ")", line);
//        this.name = name;
//        this.params = params;
//        this.body = body;
//        this.returnType = "Any";  // default type
//
//        if (params != null) {
//            for (String p : params) {
//                addChild(new Identifier(p, line));
//            }
//        }
//
//        if (body != null) {
//            for (AstNode stmt : body) {
//                addChild(stmt);
//            }
//        }
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public List<String> getParameters() {
//        return params;
//    }
//
//    public List<AstNode> getBody() {
//        return body;
//    }
//
//    // Type annotation support
//    public void setParameterTypes(Map<String, String> paramTypes) {
//        this.paramTypes = paramTypes;
//    }
//
//    public void setReturnType(String returnType) {
//        this.returnType = returnType;
//    }
//
//    public Map<String, String> getParameterTypes() {
//        return paramTypes;
//    }
//
//    public String getReturnType() {
//        return returnType;
//    }
//
//    public void setDecorators(List<String> decorators) {
//        this.decorators = decorators;
//    }
//
//    public List<String> getDecorators() {
//        return decorators;
//    }
//}
package AST;

import java.util.ArrayList;
import java.util.List;

/**
 * FunctionDef — updated in-place to add paramTypes and returnType
 * while keeping full backward compatibility with the existing constructor.
 */
public class FunctionDef extends AstNode {

    private String       name;
    private List<String> params;
    private List<AstNode> body;

    // ── NEW fields ───────────────────────────────────────────────────── //
    private List<String> paramTypes;   // parallel to params; "Any" if no annotation
    private String       returnType;   // "Any" if no annotation

    // ------------------------------------------------------------------ //
    //  Original constructor — kept exactly as-is for backward compat      //
    // ------------------------------------------------------------------ //
    public FunctionDef(String name, List<String> params,
                       List<AstNode> body, int line) {
        super("FunctionDef(" + name + ")", line);
        this.name       = name;
        this.params     = params;
        this.body       = body;
        // Default: no type annotations
        this.returnType = "Any";
        this.paramTypes = buildAnyList(params == null ? 0 : params.size());

        if (params != null)
            for (String p : params) addChild(new Identifier(p, line));
        if (body != null)
            for (AstNode stmt : body) addChild(stmt);
    }

    // ------------------------------------------------------------------ //
    //  NEW constructor — with type annotations                            //
    // ------------------------------------------------------------------ //
    public FunctionDef(String name, List<String> params,
                       List<String> paramTypes, String returnType,
                       List<AstNode> body, int line) {
        super("FunctionDef(" + name + ")", line);
        this.name       = name;
        this.params     = params;
        this.body       = body;
        this.returnType = (returnType != null) ? returnType : "Any";
        this.paramTypes = (paramTypes != null) ? paramTypes
                : buildAnyList(params == null ? 0 : params.size());

        if (params != null)
            for (String p : params) addChild(new Identifier(p, line));
        if (body != null)
            for (AstNode stmt : body) addChild(stmt);
    }

    // ------------------------------------------------------------------ //
    //  Original getters — unchanged                                       //
    // ------------------------------------------------------------------ //
    public String        getName()       { return name; }
    public List<String>  getParameters() { return params; }
    public List<AstNode> getBody()       { return body; }

    // ------------------------------------------------------------------ //
    //  NEW getters — added to fix "Cannot resolve method" errors          //
    // ------------------------------------------------------------------ //

    /** Returns the declared return type, or "Any" if no annotation. */
    public String getReturnType() { return returnType; }

    /**
     * Returns the declared type of parameter at position index.
     * Returns "Any" if the parameter has no type annotation.
     */
    public String getParamType(int index) {
        if (paramTypes == null || index >= paramTypes.size()) return "Any";
        return paramTypes.get(index);
    }

    /** Returns all parameter types (parallel list to getParameters()). */
    public List<String> getParamTypes() { return paramTypes; }

    // ------------------------------------------------------------------ //
    //  Helper                                                              //
    // ------------------------------------------------------------------ //
    private static List<String> buildAnyList(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) list.add("Any");
        return list;
    }
}