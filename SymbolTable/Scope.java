package SymbolTable;

import java.util.*;

import java.util.HashMap;
import java.util.Map;



//class Scope{
//    private Map<String,String> identi;
//    public Scope()
//    {
//        identi=new HashMap<>();
//    }
//    public boolean contains (String name){
//        return identi.containsKey(name);
//    }
//    public void add (String name ,String kind){
//        identi.put(name,kind);
//    }
//    public String get(String nn)
//    {
//        return identi.get(nn);
//    }
//    public Map<String,String>getIdentis()
//    {
//        return identi;
//    }
//}

public class Scope {
    private Map<String, Symbol> symbols = new HashMap<>();
    public Scope parent;
    public String scopeName;

    public static List<Symbol> report = new ArrayList<>();

    public Scope(Scope parent, String scopeName) {
        this.parent = parent;
        this.scopeName = scopeName;
    }

    public void define(String name, String type, int line) {

        if (!report.contains(name)) {
            Symbol s = new Symbol(name, type, line, this.scopeName);
            symbols.put(name, s);
            report.add(s);
        }
    }

    public static void printFinalReport() {

        System.out.format("%-15s | %-12s | %-5s | %-20s\n", "Name", "Type", "Line", "Scope");
        System.out.println("-".repeat(60));
        for (Symbol s : report) System.out.println(s);

    }

}




