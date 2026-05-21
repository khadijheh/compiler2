parser grammar flaskParser;

options { tokenVocab = flaskLexer; }


program
    : (NEWLINE | statement)* EOF
    ;


statement
    : simple_stmt
    | compound_stmt
    ;

simple_stmt
    : importStmt stmt_end      #importStmtRule
    | assignStmt stmt_end      #assignStmtRule
    | returnStmt stmt_end      #returnStmtRule
    | exprStmt stmt_end        #expressionStmtRule
    ;

stmt_end : NEWLINE+ | EOF ;


compound_stmt
    : functionDef              #functionDefRule
    | ifStmt                   #ifStmtRule
    | forStmt                  #forStmtRule
    ;

importStmt
    : FROM dotted_name IMPORT importList
    | IMPORT dotted_name
    ;

importList
    : importItem (COMMA importItem)*
    ;

importItem
    : ID (AS ID)?
    ;


functionDef
    : decorator* DEF ID LPAREN paramList? RPAREN COLON block
    ;

decorator
    : AT dotted_name (LPAREN argumentList? RPAREN)? NEWLINE
    ;

paramList
    : ID (COMMA ID)*
    ;


block
    : NEWLINE
      (
        INDENT (NEWLINE* statement)+ DEDENT
        | (NEWLINE* statement)+
      )
    ;


ifStmt
    : IF expression COLON block
      (ELIF expression COLON block)*
      (ELSE COLON block)?
    ;

forStmt
    : FOR ID IN expression COLON block
    ;


assignStmt
    : expression ASSIGN expression
    ;

returnStmt
    : RETURN expression (COMMA expression)* (COMMA)?
    | RETURN
    ;

exprStmt
    : expression
    ;

expression
    : comparison (COMMA comparison)* (COMMA)?
    ;

comparison
    : arith_expr ((EQ | NEQ | LT | GT | LE | GE | (ASSIGN ASSIGN)) arith_expr)*
    ;


arith_expr
    : term ((PLUS | MINUS) term)*
    ;

term
    : factor ((MULT | DIV) factor)*
    ;

factor
    : primary trailer*
    ;

primary
    : ID                       #identifierPrimary
    | NUMBER                   #numberPrimary
    | STRING                   #stringPrimary
    | TRUE                     #truePrimary
    | FALSE                    #falsePrimary
    | NONE                     #nonePrimary
    | LPAREN expression? RPAREN #parenPrimary
    | listLiteral              #listPrimary
    | dictLiteral              #dictPrimary
    ;

trailer
    : LPAREN ign argumentList? ign RPAREN
    | LBRACK ign expression ign RBRACK
    | DOT ID
    ;



argumentList
    : argument (ign COMMA ign argument)* (ign COMMA)?
    ;


argument
    : ID ASSIGN expression
    | expression
    ;
ign
    : (NEWLINE | INDENT | DEDENT)*
    ;


listLiteral
    : LBRACK ign
      (expression ign (COMMA ign expression ign)*)?
      (COMMA ign)?
      RBRACK
    ;


dictLiteral
    : LBRACE ign
      (dictEntry ign (COMMA ign dictEntry ign)*)?
      (COMMA ign)?
      RBRACE
    ;

dictEntry
    : expression ign COLON ign expression
    ;



dotted_name
    : ID (DOT ID)*
    ;
