grammar Chaos;
/*
@header{
package parser;
}*/

program
 : exp
 ;


exp
 : expNoBinOpNoId binOp
 | loop
 | ifThen
 | negation
 | id idFacto
 ;

idFacto
 : binOp
 | refOrCreate
 ;

expNoBinOp
 : expNoBinOpNoId
 | idRead
 ;

idRead
 : id idReadFacto
 ;

idReadFacto
 : '(' expCKleene ')'
 | '.' id lValue
 | '[' exp ']' lValue
 | /* mot vide */
 ;

expNoBinOpNoId
 : constante
 | letExp
 | '(' expNoBinOpNoIdPar
 ;

expNoBinOpNoIdPar
 : ')'
 | exp expNoBinOpNoIdParExp
 ;

expNoBinOpNoIdParExp
 : ';' expSCKleene ')'
 | ')'
 ;

binOp
 : addOp
 | multOp
 | orOp
 | andOp
 | compOp
 | /* mot vide */
 ;

orOp
 : '|' orOpTail
 ;

orOpTail
 : expNoBinOp orOpOpt
 ;

orOpOpt
 : orOp
 | andOpOpt
 ;

andOp
 : '&' andOpTail
 ;

andOpTail
 : expNoBinOp andOpOpt
 ;

andOpOpt
 : andOp
 | compOpOpt
 ;

compOp
 : '=' compOpTail
 | '<>' compOpTail
 | '<=' compOpTail
 | '>=' compOpTail
 | '<' compOpTail
 | '>' compOpTail
 ;

compOpTail
 : expNoBinOp compOpOpt
 ;

compOpOpt
 : compOp
 | addOpOpt
 ;

addOp
 : '+' addOpTail
 | '-' addOpTail
 ;

addOpTail
 : expNoBinOp addOpOpt
 ;

addOpOpt
 : addOp
 | multOpOpt
 ;

multOp
 : '*' multOpTail
 | '/' multOpTail
 ;

multOpTail
 : expNoBinOp multOpOpt
 ;

multOpOpt
 : multOp
 | /* mot vide */
 ;

constante
 : INT
 | STR
 | 'nil'
 | 'break'
 ;

refOrCreate
 : '(' expCKleene ')' binOp
 | '.' id lValue refOrCreateTail
 | ':=' exp
 | '{' fieldCreateCKleene '}'
 | '[' exp ']' refOrCreateCrochet
 ;

refOrCreateCrochet
 : lValue refOrCreateTail
 | 'of' exp
 ;

refOrCreateTail
 : ':=' exp
 | binOp
 ;

lValue
 : '[' exp ']' lValue
 | '.' id lValue
 | /* mot vide */
 ;

fieldCreate
 : id '=' exp
 ;

loop
 : whileExp
 | forExp
 ;

whileExp
 : 'while' exp 'do' exp
 ;

forExp
 : 'for' id ':=' exp 'to' exp 'do' exp
 ;

negation
 : '-' exp
 ;

ifThen
 : 'if' expNoBinOp binOp 'then' exp optElse
 ;

optElse
 : 'else' exp
 ;

letExp
 : 'let' decPositive 'in' expSCKleene 'end'
 ;

dec
 : tyDec
 | varDec
 | funDec
 ;

tyDec
 : 'type' id '=' ty
 ;

ty
 : id
 | arrTy
 | recTy
 ;

arrTy
 : 'array' 'of' id
 ;

recTy
 : '{' fieldDecCKleene '}'
 ;

fieldDec
 : id ':' id
 ;

funDec
 : 'function' id '(' fieldDecCKleene ')' optType '=' exp
 ;

varDec
 : 'var' id optType ':=' exp
 ;

optType
 : ':' id
 | /* mot vide */
 ;

/* ********** ********** ********** ********** *
 *    Etoile de Kleene (*) et positive (+)     *
 * ********** ********** ********** ********** */

expSCKleene
 : exp expSCKleeneFacto
 | /* mot vide */
 ;

expSCKleeneFacto
 : ';' exp expSCKleeneFacto
 | /* mot vide */
 ;

fieldDecCKleene
 : fieldDec fieldDecCKleeneFacto
 | /* mot vide */
 ;

fieldDecCKleeneFacto
 : ',' fieldDec fieldDecCKleeneFacto
 | /* mot vide */
 ;

expCKleene
 : exp expCKleeneFacto
 | /* mot vide */
 ;

expCKleeneFacto
 : ',' exp expCKleeneFacto
 | /* mot vide */
 ;

fieldCreateCKleene
 : fieldCreate fieldCreateCKleeneFacto
 | /* mot vide */
 ;

fieldCreateCKleeneFacto
 : ',' fieldCreate fieldCreateCKleeneFacto
 | /* mot vide */
 ;

decPositive
 : dec decKleene
 ;

decKleene
 : dec decKleene
 | /* mot vide */
 ;

id
 : ID
 ;

// Règles lexer
INT : ('0'..'9')+ ;
ID : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
STR : '"'('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'.'|' '|'!'|'?'|'-'|':'|';'|','|'\\n'|'\\t'|'\\r'|'('|')')*'"';
COMMENT : '/*' (.)*? '*/' -> skip ;
WS : [ \n\t\r] + -> skip ;
