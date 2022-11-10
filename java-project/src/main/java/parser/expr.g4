grammar expr;

@header{
package parser;
}

program : expr?EOF;

expr
  : stringConstant                                    # StringConstantExpr
  | stringConstant exprNRG                            # StringConstantExprNRG
  | integerConstant                                   # IntegerConstantExpr
  | integerConstant exprNRG                           # IntegerConstantExprNRG
  | 'nil'                                             # NilExpr
  | 'nil' exprNRG                                     # NilExprNRG
  | lvalue                                            # LValueExpr
  | lvalue exprNRG                                    # LValueExprNRG
  | ID '(' exprList? ')'                              # FunctionAcess
  | ID '(' exprList? ')' exprNRG                      # FunctionAcessNRG
  | typeId '{' fieldList? '}'                         # RecordDeclarationExpr
  | typeId '{' fieldList? '}' exprNRG                 # RecordDeclarationExprNRG
  | typeId '[' expr ']' 'of' expr                     # ArrayDeclarationExpr
  | typeId '[' expr ']' 'of' expr exprNRG             # ArrayDeclarationExprNRG
  | 'if' expr 'then' expr                             # IfThenExpr
  | 'if' expr 'then' expr exprNRG                     # IfThenExprNRG
  | 'if' expr 'then' expr 'else' expr                 # IfThenElseExpr
  | 'if' expr 'then' expr 'else' expr exprNRG         # IfThenElseExprNRG
  | 'while' expr 'do' expr                            # WhileExpr
  | 'while' expr 'do' expr exprNRG                    # WhileExprNRG
  | 'for' ID ':=' expr 'to' expr 'do' expr            # ForExpr
  | 'for' ID ':=' expr 'to' expr 'do' expr exprNRG    # ForExprNRG
  | 'let' declarationList 'in' exprSeq? 'end'         # LetExpr
  | 'let' declarationList 'in' exprSeq? 'end' exprNRG # LetExprNRG
  | 'break'                                           # BreakExpr
  | 'break' exprNRG                                   # BreakExprNRG
  | '-' expr                                          # MinusExpr
  | '-' expr exprNRG                                  # MinusExprNRG
  | '(' exprSeq? ')'                                  # ParenthesedExpr
  | '(' exprSeq? ')' exprNRG                          # ParenthesedExprNRG
  | lvalue ':=' expr                                  # AssignationExpr
  | lvalue ':=' expr exprNRG                          # AssignationExprNRG
  ;

exprNRG
  : ('*' | '/') expr                                  # MultiplicativeExpr
  | ('*' | '/') expr exprNRG                          # MultiplicativeExprNRG
  | ('+' | '-') expr                                  # AdditiveExpr
  | ('+' | '-') expr exprNRG                          # AdditiveExprNRG
  | ('=' | '<>' | '>' | '<' | '>=' | '<=') expr       # EqualityExpr
  | ('=' | '<>' | '>' | '<' | '>=' | '<=') expr exprNRG # EqualityExprNRG
  | '&' expr                                          # AndExpr
  | '&' expr exprNRG                                  # AndExprNRG
  | '|' expr                                          # OrExpr
  | '|' expr exprNRG                                  # OrExprNRG
  ;

exprSeq
  : expr (';' expr)*
  ;

exprList
  : expr (',' expr)*
  ;

fieldList
  : ID '=' expr (',' ID '=' expr)*
  ;

stringConstant // Ajout
  : STR
  ;

integerConstant // Ajout
  : INT
  | lvalue
  ;

lvalue
  : ID lvalueNRG
  | ID
  ;

lvalueNRG
  :
  | '.' ID lvalueNRG
  | '[' expr ']' lvalueNRG
  | '.' ID
  | '[' expr ']'
  ;

declarationList
  : declaration+
  ;

declaration
  : typeDeclaration
  | variableDeclaration
  | functionDeclaration
  ;

typeDeclaration
  : 'type' typeId '=' type
  ;

type
  : typeId                    // alias
  | '{' typeFields? '}'       // records
  | 'array' 'of' typeId       // array
  ;

typeFields // records
  : typeField (',' typeField)*
  ;

typeField
  : ID ':' typeId
  ;

typeId        // Ajout
  : ID        // ça peut paraitre bête de faire une règle juste pour ça mais je pense qu'on en aura besoin pour les contrôles sémantiques
  ;

variableDeclaration
  : 'var' ID ':=' expr
  | 'var' ID ':' typeId ':=' expr
  ;

functionDeclaration
  : 'function' ID '(' typeFields? ')' '=' expr
  | 'function' ID '(' typeFields? ')' ':' typeId '=' expr
  ;

// Règles lexer
INT  :  ('0'..'9')+ ;
ID   :  ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
//STR : '"'(~["\\\r\n] | '\\n'|'\\t'|'\\r')+'"';
STR : '"'('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'.'|' '|'!'|'?'|'-'|':'|';'|','|'\\n'|'\\t'|'\\r'|'('|')')*'"';
COMMENT : '/*' (.)*?  '*/' ->skip ;
WS  : [ \n\t\r] + -> skip ;
