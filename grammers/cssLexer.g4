lexer grammar cssLexer;



OPENBRACE : '{'->pushMode(PROPERTYDECLERATON);
CLOSEBRACE : '}';


ID_SELECTOR: '#'[a-zA-Z0-9][a-zA-Z0-9_/-]*;
CLASS_SELECTOR:'.'[a-zA-Z0-9][a-zA-Z0-9_/-]*;
TAG_SELECTOR:[a-zA-Z][a-zA-Z0-9]*;

mode PROPERTYDECLERATON;
PROPERTY : 'color' | 'background-color' | 'width' | 'height' |
               'margin' | 'padding' | 'font-size' | 'font-family' |
               'border' | 'display' | 'position' | 'float' |
               'text-align' | 'text-decoration' | 'background' |
               'border-radius' | 'box-shadow' | 'opacity';
STRING_CSS:'"' ~[<"]* '"';
NUMBER : [0-9]+;
COLOR : '#' [0-9a-f]*;
SEMICOLON_CSS : ';';
COLON_CSS : ':';
COMMA_CSS : ',';

WS: [ \t\r\n]+ -> skip;
COMMENT: '/*' .*? '*/' -> skip;

//كود اللون COLOR : '#' [0-9a-fA-F]{3} ([0-9a-fA-F]{3})? ;
//اغلاق ال mode