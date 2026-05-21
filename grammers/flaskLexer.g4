//معالجة الـ Indentation
lexer grammar flaskLexer;

@header {
    import java.util.LinkedList;
    import java.util.Stack;
    import org.antlr.v4.runtime.CommonToken;
    import org.antlr.v4.runtime.Token;
    import org.antlr.v4.runtime.misc.Pair;
    import org.antlr.v4.runtime.CharStream;
    import org.antlr.v4.runtime.TokenSource;
}

@members {
    private Stack<Integer> indentLevels = new Stack<>();
    private LinkedList<Token> pendingTokens = new LinkedList<>();
    private int nestingLevel = 0;

    {
        indentLevels.push(0);
    }

    @Override
    public void reset() {
        indentLevels.clear();
        indentLevels.push(0);
        pendingTokens.clear();
        nestingLevel = 0;
        super.reset();
    }

    @Override
    public Token nextToken() {
        if (!pendingTokens.isEmpty())
            return pendingTokens.poll();

        Token t = super.nextToken();

        if (t.getType() == EOF) {
            if (!pendingTokens.isEmpty()) {

            } else {

                pendingTokens.add(createToken(NEWLINE, "\n"));
            }
            while (indentLevels.size() > 1) {
                indentLevels.pop();
                pendingTokens.add(createToken(DEDENT, ""));
            }

            pendingTokens.add(t);
            return pendingTokens.poll();
        }

        return t;
    }


    private CommonToken createToken(int type, String text) {
        int stop = getCharIndex() - 1;
        int start = text.isEmpty() ? stop : stop - text.length() + 1;
        Pair<TokenSource, CharStream> source = new Pair<>(this, getInputStream());
        CommonToken t = new CommonToken(source, type, Token.DEFAULT_CHANNEL, start, stop);
        t.setText(text);
        return t;
    }
}

tokens { INDENT, DEDENT }

LPAREN : '(' { nestingLevel++; };
RPAREN : ')' { if (nestingLevel > 0) nestingLevel--; };
LBRACE : '{' { nestingLevel++; };
RBRACE : '}' { if (nestingLevel > 0) nestingLevel--; };
LBRACK : '[' { nestingLevel++; };
RBRACK : ']' { if (nestingLevel > 0) nestingLevel--; };

fragment IND_WS : [ \t\u00A0\u2007\u202F\u2009]* ;

NEWLINE
    : ('\r'? '\n' | '\r') IND_WS ('#' ~[\r\n]*)?
      {
        if (nestingLevel > 0) {
            skip();
        } else {
            int la = _input.LA(1);
            if (la == '\r' || la == '\n' || la == EOF) {
                skip();
            } else {
                String txt = getText();
                String indentation = "";
                int last = Math.max(txt.lastIndexOf('\n'), txt.lastIndexOf('\r'));
                if (last >= 0 && last < txt.length() - 1)
                    indentation = txt.substring(last + 1);

                String normalized = indentation //tap=4
                        .replace("\t", "    ")
                        .replace("\u00A0", " ")
                        .replace("\u2007", " ")
                        .replace("\u202F", " ")
                        .replace("\u2009", " ");

                int indentLength = normalized.length();
                int lastIndent = indentLevels.peek();

                setText("\n");

                if (indentLength > lastIndent) {
                    indentLevels.push(indentLength);
                    pendingTokens.add(createToken(INDENT, indentation));
                } else {
                    while (indentLength < indentLevels.peek()) {
                        indentLevels.pop();
                        pendingTokens.add(createToken(DEDENT, ""));
                    }
                }
            }
        }
      }
    ;







DEF : 'def';
RETURN : 'return';
IF: 'if';
ELSE: 'else';
ELIF: 'elif';
FOR: 'for';
IN: 'in';
IMPORT: 'import';
FROM: 'from';
AS: 'as';

TRUE  : 'True';
FALSE : 'False';
NONE  : 'None';

PLUS: '+';
MINUS : '-';
MULT : '*';
DIV : '/';
EQ: '==';
ASSIGN : '=';
NEQ : '!=';
LT: '<';
GT: '>';
LE:'<=';
GE: '>=';
COMMA: ',';
COLON:':';
DOT: '.';
AT :'@';

NUMBER : [0-9]+ ('.' [0-9]+)? ;

STRING
    : '"' (~["\\] | '\\' .)* '"'
    | '\'' (~['\\] | '\\' .)* '\''
    ;

ID
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;


WS : [ \t]+ -> skip;
HASH_COMMENT : '#' ~[\r\n]* -> skip;
