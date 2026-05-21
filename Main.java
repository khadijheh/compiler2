//import AST.AstNode;
//import AST_H_C.Node;
//import grammers.flaskLexer;
//import grammers.flaskParser;
//import grammers.htmlLexer;
//import grammers.htmlParser;
//import org.antlr.v4.runtime.CharStream;
//import org.antlr.v4.runtime.CharStreams;
//import org.antlr.v4.runtime.CommonTokenStream;
//import visitor.HtmlVisitor;
//import visitor.PythonVisitor;
//import visitor.SymbolTableVisitor;
//import visitor.WebSymbolTableVisitor;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class Main {
//    public static void main(String[] args) {
//        try {
//            String filePath = "Files/index.html";
//            // String filePath = "Files/add_product.html";
//            // String filePath = "Files/product_details.html";
//
//            String htmlCode = Files.readString(Paths.get(filePath));
//
//            htmlLexer lexer = new htmlLexer(CharStreams.fromString(htmlCode));
//            htmlParser parser = new htmlParser(new CommonTokenStream(lexer));
//            HtmlVisitor visitor = new HtmlVisitor();
//            Node ast = visitor.visitHtmlDocument(parser.htmlDocument());
//            System.out.println("HTML AST");
//            System.out.println(ast);
//
//            WebSymbolTableVisitor webST = new WebSymbolTableVisitor(filePath);
//            webST.build(ast);
//
//            CharStream input = CharStreams.fromFileName("Files/test.txt");
//            flaskLexer pyLexer = new flaskLexer(input);
//            CommonTokenStream tokens = new CommonTokenStream(pyLexer);
//            flaskParser pyParser = new flaskParser(tokens);
//            flaskParser.ProgramContext tree = pyParser.program();
//            if (pyParser.getNumberOfSyntaxErrors() > 0) {
//                System.out.println("(Syntax Errors)");
//                return;
//            }
//
//            PythonVisitor pyVisitor = new PythonVisitor();
//            AstNode root = pyVisitor.visit(tree);
//
//            SymbolTableVisitor pySTVisitor = new SymbolTableVisitor();
//            pySTVisitor.build(root);
//
//            System.out.println("\nPYTHON AST");
//            System.out.println(root);
//        } catch (Exception e) {
//            System.err.println("error " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}

import AST.AstNode;
import AST_H_C.Node;
import grammers.flaskLexer;
import grammers.flaskParser;
import grammers.htmlLexer;
import grammers.htmlParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import semantic.ScopeManager;
import semantic.SemanticAnalyzerVisitor;
import visitor.HtmlVisitor;
import visitor.PythonVisitor;
import visitor.SymbolTableVisitor;
import visitor.WebSymbolTableVisitor;
import SymbolTable.Scope;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // ================================================================ //
            //  PHASE 1 – HTML / CSS / Jinja parsing                            //
            // ================================================================ //
            String filePath = "Files/index.html";
            // String filePath = "Files/add_product.html";
            // String filePath = "Files/product_details.html";
            String htmlCode = Files.readString(Paths.get(filePath));

            htmlLexer lexer = new htmlLexer(CharStreams.fromString(htmlCode));
            htmlParser parser = new htmlParser(new CommonTokenStream(lexer));
            HtmlVisitor visitor = new HtmlVisitor();
            Node htmlAst = visitor.visitHtmlDocument(parser.htmlDocument());

            System.out.println("=== HTML AST ===");
            System.out.println(htmlAst);

            WebSymbolTableVisitor webST = new WebSymbolTableVisitor(filePath);
            webST.build(htmlAst);

            // ================================================================ //
            //  PHASE 2 – Flask / Python lexing & parsing                       //
            // ================================================================ //
            CharStream input = CharStreams.fromFileName("Files/test.txt");
            flaskLexer pyLexer = new flaskLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(pyLexer);
            flaskParser pyParser = new flaskParser(tokens);

            flaskParser.ProgramContext tree = pyParser.program();

            if (pyParser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("[Error] Syntax errors detected – aborting.");
                System.exit(1);
            }

            PythonVisitor pyVisitor = new PythonVisitor();
            AstNode root = pyVisitor.visit(tree);

            System.out.println("\n=== PYTHON AST ===");
            System.out.println(root);

            // ================================================================ //
            //  PHASE 3 – Symbol Table construction (existing pass)             //
            // ================================================================ //

            SymbolTableVisitor pySTVisitor = new SymbolTableVisitor();
            pySTVisitor.build(root);

            System.out.println("\n=== SYMBOL TABLE ===");
            Scope.printFinalReport();


            // ================================================================ //
            //  PHASE 4 – SEMANTIC ANALYSIS  ← NEW                             //
            // ================================================================ //
            System.out.println("\n=== SEMANTIC ANALYSIS ===");

            ScopeManager scopeManager = new ScopeManager();
            SemanticAnalyzerVisitor semanticAnalyser =
                    new SemanticAnalyzerVisitor(scopeManager);

            semanticAnalyser.analyse(root);

            System.out.println("[Semantic Analysis] No fatal errors – ready for code generation.");

            // ================================================================ //
            //  PHASE 5 – Code Generation (placeholder)                        //
            // ================================================================ //
            // TODO: add your code-generation phase here.
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}