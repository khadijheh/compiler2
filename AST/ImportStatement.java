//package AST;
//
//public class ImportStatement extends AstNode {
//    private String fromModule; // اسم المكتبة (مثل flask) - يمكن أن يكون null
//    private String importedName; // الاسم المستورد (مثل Flask)
//
//    public ImportStatement(String fromModule, String importedName, int linenumber) {
//        super("ImportStatement", linenumber);
//        this.fromModule = fromModule;
//        this.importedName = importedName;
//    }
//
//    @Override
//    public String toString() {
//        if (fromModule != null) {
//            return "Import: from " + fromModule + " import " + importedName;
//        }
//        return "Import: " + importedName;
//    }
//}
package AST;

import java.util.List;

public class ImportStatement extends AstNode {

    public ImportStatement(String module, List<String> names, int line) {
        super("Import(" + module + ")", line);

        for (String n : names) {
            addChild(new Identifier(n, line));
        }
    }
}
