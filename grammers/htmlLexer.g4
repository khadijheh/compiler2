lexer grammar htmlLexer;




WS: [\t\n\r]+ ->skip;

COMMENT : '<!--' .*? '-->' -> skip ;

EXPR_START: '{{' -> pushMode(EXPR_MODE);
TAG_START: '{%' -> pushMode(TAG_MODE);
COMMENT_START: '{#' -> pushMode(COMMENT_MODE);

HTML_DOCUMENT : '<!DOCTYPE html>';


STYLE_OPEN: '<style>' -> pushMode(CSS_MODE);

OPEN_TAG : '<'->pushMode(TAG);
HTML_TEXT     : (~[<{])+ ;
TEXT          : '{' ~[{%#] ;




mode TAG;
TAG_WS: [ \t\r\n] -> skip;
CLOSE_TAG : '>'->popMode;
CLOSE_SLASH_TAG : '/>'->popMode;
TAG_SLASH :'/';
TAG_NAME : [a-zA-Z!][a-zA-Z0-9]*;
ATTR_NAME:' '[a-zA-Z][a-zA-Z\-]*;
TAG_EQUALS : '=' ->pushMode(ATTRIBUTEVAL);

mode ATTRIBUTEVAL;
ATTRIBUTE_VALUE: ATTRIBUTE ->popMode;
ATTRIBUTE : DOUBLE_QUOTE_STRING | SINGLE_QUOTE_STRING | ATTCHARS ;
fragment DOUBLE_QUOTE_STRING: '"' ~[<"]* '"';
fragment SINGLE_QUOTE_STRING: '\'' ~[<']* '\'';
fragment ATTCHARS: [a-zA-Z0-9\-_#:.]+;
ATTR_WS: [ \t] -> skip;
// ========================= CSS ============================================

mode CSS_MODE;

STYLE_CLOSE:'</style>'->popMode;
CSS_WS: [ \r\n]+ -> skip;
CSS_COMMENT : '/*' .*? '*/' -> skip;
OPENBRACE : '{'->pushMode(PROPERTYDECLERATON);


//ID_SELECTOR: '#'[a-zA-Z0-9][a-zA-Z0-9_/-]*;
//CLASS_SELECTOR:'.'[a-zA-Z0-9][a-zA-Z0-9_/-]*;
//TAG_SELECTOR:[a-zA-Z][a-zA-Z0-9]*;

CSS_SELECTOR
    : ~[<{]+
    ;

mode PROPERTYDECLERATON;
PROPERTY : [a-zA-Z-]+;
VALUE: ~[\t\r\n;:{}]+;

SEMICOLON_CSS : ';';
COLON_CSS : ':';
COMMA_CSS : ',';


CLOSEBRACE : '}'->popMode;
CSS_PROP_WS: [ \t\r\n]+ -> skip;



// =============================== JINJA 2 =============================================================








// ----- نمط التعبيرات (EXPR_MODE) -----
mode EXPR_MODE;

    EXPR_END: '}}' -> popMode;

    // محددات
    DOT: '.';
    COMMA: ',';
    COLON: ':';
    PIPE: '|';
    LPAREN: '(';
    RPAREN: ')';
    LBRACK: '[';
    RBRACK: ']';
    LBRACE: '{';
    RBRACE: '}';

    // معاملات رياضية
    PLUS: '+';
    MINUS: '-';
    MULT: '*';
    DIV: '/';
    MOD: '%';
    POW: '**';

    // معاملات مقارنة
    EQ: '==';
    NE: '!=';
    LT: '<';
    GT: '>';
    LE: '<=';
    GE: '>=';

    // معاملات منطقية
    AND: 'and' | '&&';
    OR: 'or' | '||';
    NOT: 'not' | '!';

    // كلمات محجوزة
    IN: 'in';
    IS: 'is';
    NONE: 'None' | 'none';
    TRUE: 'True' | 'true';
    FALSE: 'False' | 'false';

    // قيم
    ID: [a-zA-Z_][a-zA-Z0-9_]*;
    INT: [0-9]+;
    FLOAT: [0-9]+ '.' [0-9]* | '.' [0-9]+;
    STRING: '"' (~["\\] | '\\' .)* '"'
          | '\'' (~['\\] | '\\' .)* '\'';
    EXPR_WS: [ \t\r\n]+ -> skip;

    // مسافات


// ----- نمط العلامات (TAG_MODE) -----
    mode TAG_MODE;
    JINJA_TAG_WS: [ \t\r\n]+ -> skip;
    TAG_END: '%}' -> popMode;
//        TAG_CONTENT : ~[%}]+;

    TAG_DOT: '.' -> type(DOT);
     TAG_COMMA: ',' -> type(COMMA);
     TAG_COLON: ':' -> type(COLON);
     TAG_PIPE: '|' -> type(PIPE);
     TAG_LPAREN: '(' -> type(LPAREN);
     TAG_RPAREN: ')' -> type(RPAREN);
     TAG_LBRACK: '[' -> type(LBRACK);
     TAG_RBRACK: ']' -> type(RBRACK);
     TAG_LBRACE: '{' -> type(LBRACE);
     TAG_RBRACE: '}' -> type(RBRACE);

     // معاملات رياضية مشتركة
     TAG_PLUS: '+' -> type(PLUS);
     TAG_MINUS: '-' -> type(MINUS);
     TAG_MULT: '*' -> type(MULT);
     TAG_DIV: '/' -> type(DIV);
     TAG_MOD: '%' -> type(MOD);
     TAG_POW: '**' -> type(POW);

     // معاملات مقارنة مشتركة
     TAG_EQ: '==' -> type(EQ);
     TAG_NE: '!=' -> type(NE);
     TAG_LT: '<' -> type(LT);
     TAG_GT: '>' -> type(GT);
     TAG_LE: '<=' -> type(LE);
     TAG_GE: '>=' -> type(GE);

     // معاملات منطقية مشتركة
     TAG_AND: ('and' | '&&') -> type(AND);
     TAG_OR: ('or' | '||') -> type(OR);
     TAG_NOT: ('not' | '!') -> type(NOT);

     // كلمات محجوزة مشتركة
     TAG_IN: 'in' -> type(IN);
     TAG_IS: 'is' -> type(IS);
     TAG_NONE: ('None' | 'none') -> type(NONE);
     TAG_TRUE: ('True' | 'true') -> type(TRUE);
     TAG_FALSE: ('False' | 'false') -> type(FALSE);

     // قيم مشتركة
     TAG_INT: [0-9]+ -> type(INT);
     TAG_FLOAT: ([0-9]+ '.' [0-9]*  | '.' [0-9]+) -> type(FLOAT);
     TAG_STRING: ('"' (~["\\] | '\\' .)* '"'
               | '\'' (~['\\] | '\\' .)* '\'') -> type(STRING);


    IF: 'if';
    ELIF: 'elif';
    ELSE: 'else';
    ENDIF: 'endif';

    SET : 'set';
    INCLUDE : 'include';
    EXTENDS : 'extends';


    FOR: 'for';
    IN_TAG: 'in' -> type(IN);
    ENDFOR: 'endfor';


     TAG_ID: [a-zA-Z_][a-zA-Z0-9_]* -> type(ID);







// ----- نمط التعليقات (COMMENT_MODE) -----
mode COMMENT_MODE;
    COMMENT_END: '#}' -> popMode;
    COMMENT_TEXT: .*?;



