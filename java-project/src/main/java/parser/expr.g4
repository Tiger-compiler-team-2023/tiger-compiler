grammar expr;

@header{
package parser;
}

program : expr? EOF;

expr
  : stringConstant                                    # StringConstantExpr
  | integerConstant                                   # IntegerConstantExpr
  | 'nil'                                             # NilExpr
  | lvalue                                            # LValueExpr
  | ID '(' exprList? ')'                              # FunctionAcess
  | typeId '{' fieldList? '}'                         # RecordDeclarationExpr
  | typeId '[' expr ']' 'of' expr                     # ArrayDeclarationExpr
  | 'if' expr 'then' expr                             # IfThenExpr
  | 'if' expr 'then' expr 'else' expr                 # IfThenElseExpr
  | 'while' expr 'do' expr                            # WhileExpr
  | 'for' ID ':=' expr 'to' expr 'do' expr            # ForExpr
  | 'let' declarationList 'in' exprSeq? 'end'         # LetExpr
  | 'break'                                           # BreakExpr
  | '-' expr                                          # MinusExpr
  | expr ('*' | '/') expr                             # MultiplicativeExpr
  | expr ('+' | '-') expr                             # AdditiveExpr
  | expr ('=' | '<>' | '>' | '<' | '>=' | '<=') expr  # EqualityExpr
  | expr '&' expr                                     # AndExpr
  | expr '|' expr                                     # OrExpr
  | '(' exprSeq? ')'                                  # ParenthesedExpr
  | lvalue ':=' expr                                  # AssignationExpr
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
  : ID
  | lvalue '.' ID
  | lvalue '[' expr ']'
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
  : typeField (',' typeField)+
  ;

typeField
  : ID ':' typeId
  ;

typeId        // Ajout
  : ID        // ça peut paraitre bête de faire une règle juste pour ça mais je pense qu'on en aura besoin pour les contrôles sémantiques
  ;

variableDeclaration
  : 'var' ID ':=' expr
  ;

functionDeclaration
  : 'function' ID '(' typeFields? ')' '=' expr
  | 'function' ID '(' typeFields? ')' ':' typeId '=' expr
  ;

// Règles lexer
INT  :  ('0'..'9')+ ;
ID   :  ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
STR : '"'(~["\\\r\n] | '\\n'|'\\t'|'\\r')+'"';
COMMENT : '/*' (.)*?  '*/' ->skip ;
WS  : [ \n\t\r] + -> skip ;
