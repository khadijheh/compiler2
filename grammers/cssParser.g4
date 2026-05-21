parser grammar cssParser;

options {
    tokenVocab = cssLexer;
}

stylesheet: ruleset* EOF ;

ruleset : selector? OPENBRACE properties* CLOSEBRACE;

selector : (ID_SELECTOR|CLASS_SELECTOR|TAG_SELECTOR);

properties : PROPERTY COLON_CSS value SEMICOLON_CSS;

value :STRING_CSS|NUMBER|COLOR;
