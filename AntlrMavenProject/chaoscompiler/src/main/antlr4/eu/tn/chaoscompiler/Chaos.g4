grammar Chaos;

program : exp? EOF;



// Expressions

exp
    : expNonValued                                          #LoopOrCondition
    | expValued affectOp                                    #ExpBin
    ;



// Expressions sans valeur (càd de valeur void)

expNonValued
    : 'while' expValued 'do' exp                            #While
    | 'for' ID ':=' expValued 'to' expValued 'do' exp       #For
    | 'if' expValued 'then' exp elseOpt                     #If
    ;

// // Else

elseOpt
    : 'else' exp                                            #Else
    | /* mot vide */                                        #NoElse
    ;

// Expressions avec (possiblement) operateur binaire

expValued
    : expNoOr orOp
    ;

expNoOr
    : expNoAnd andOp
    ;

expNoAnd
    : expNoComp compOp
    ;

expNoComp
    : expNoAdd addOp
    ;

expNoAdd
    : value multOp
    ;

//  // Operateur logique |

affectOp
    : ':=' expValued                #Affect
    | /* mot vide */                #NoAffect
    ;

orOp
    : '|' expNoOr orOp              #Or
    | /* mot vide */                #NoOr
    ;

//  // Operateur logique &

andOp
    : '&' expNoAnd andOp            #And
    | /* mot vide */                #NoAnd
    ;

//  // Operateurs de comparaison

compOp
    : '=' expNoComp                 #Equals
    | '<>' expNoComp                #NotEquals
    | '<=' expNoComp                #InfOrEquals
    | '>=' expNoComp                #SupOrEquals
    | '<' expNoComp                 #InfThan
    | '>' expNoComp                 #SupThan
    | /* mot vide */                #NoComp
    ;

//  // Operateurs + et -

addOp
    : '+' expNoAdd addOp            #Add
    | '-' expNoAdd addOp            #Minus
    | /* mot vide */                #NoAddMinus
    ;

//  // Operateurs * et /

multOp
    : '*' value multOp              #Mult
    | '/' value multOp              #Divide
    | /* mot vide */                #NoMultDiv
    ;

//  // Expressions prenant possiblement une valeur
//  // sur lesquelles il est possible de faire une operation

value
    : ID idRef                      #Reference
    | STR                           #String
    | INT                           #Integer
    | '-' negationTail              #Negation
    | letExp                        #Let
    | '(' expSeq ')'                #Sequence
    ;

//  //  // Ce qui peut suivre l'operateur unaire -

negationTail
    : ID idRef                      #NegReference
    | STR                           #NegString
    | INT                           #NegInteger
    | letExp                        #NegLet
    | '(' expSeq ')'                #NegSequence
    ;

//  //  // Ce qui peut suivre un ID

idRef
    : '(' expValuedOrIfListOpt ')'              #FunctionCall
    | '[' expValuedOrIf ']' arrayCreateOpt      #ArrayElement
    | '{' fieldCreateOpt '}'                #RecordCreate
    | '.' ID idRef                          #StructFieldAccess
    | /* mot vide */                        #NoIdTail
    ;

arrayCreateOpt
    : 'of' expValued                        #ArrayAssign
    |  idRef                                #ArrayAccess
    ;

fieldCreateOpt
    : fieldCreate                           #FieldCreateOptParent
    | /* mot vide */                        #NoIdFieldCreate
    ;

fieldCreate
    : ID '=' expValued fieldCreateTail
    ;

fieldCreateTail
    : ',' fieldCreate                       #FieldCreateTailAdd
    | /* mot vide */                        #NoFieldCreateTail
    ;


//  //  // Expression let in end

letExp
    : 'let' declarationListOpt 'in' expSeqOpt 'end'
    ;


//  //  //  Liste de valeurs en parametres d'un appel de fonction

expValuedOrIfListOpt
    : expValuedList                 #Parameter
    | /* mot vide */                #NoParameter
    ;

expValuedList
    : expValuedOrIf expValuedListTail
    ;

expValuedListTail
    : ',' expValuedList          #NextParameter
    | /*mot vide */              #EndParameters
    ;

expValuedOrIf
    : 'if' expValued 'then' exp elseOpt     #IfValued
    | expValued                             #ExpValuedBis
    ;


//  //  // Sequences d'expressions separees par des ;

expSeqOpt
    : expSeq                    #NonEmptySequence
    | /* mot vide */            #EmptySequence
    ;

expSeq
    : exp expSeqTail
    ;

expSeqTail
    : ';' expSeq                #NextSeqElement
    | /* mot vide */            #EndSequence
    ;

//  // Liste de declarations dans let

declarationListOpt
    : declarationList           #NonEmptyDeclarationList
    | /* mot vide */            #EmptyDeclarationList
    ;

declarationList
    : declaration decTail
    ;

decTail
    : declaration decTail       #NextDeclaration
    | /* mot vide */            #DeclarationEnd
    ;

declaration
    : 'var' ID optType ':=' expValued                       #DeclarationVariable
    | 'type' ID '=' ty                                      #DeclarationType
    | 'function' ID '(' fieldDecList ')' optType '=' exp    #DeclarationFunction
    ;


//  //  // Declaration de types

ty
    : ID                #RenameType
    | arrTy             #Array
    | recTy             #Record
    ;

arrTy
    : 'array of' ID
    ;

recTy
    : '{' fieldDecList '}'
    ;

//  //  // Declaration de fonctions

optType
    : ':' ID                #HasType
    | /* mot vide */        #VoidType
    ;

//  //  //  // Liste de parametres pour une declaration de fonction
//  //  //  // Ou une declaration de type record

fieldDecList
    : fieldDec fieldDecTail     #FieldDecElement
    | /* mot vide */            #EmptyFieldDecList
    ;

fieldDecTail
    : ',' fieldDec fieldDecTail #NextFieldDec
    | /* mot vide */            #EndFieldDecList
    ;

fieldDec
    : ID ':' ID
    ;



// Terminaux

INT : ('0'..'9')+ ;
ID : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
STR : '"'('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'.'|' '|'!'|'?'|'-'|':'|';'|','|'\\n'|'\\t'|'\\r'|'('|')')*'"';


// Skip

COMMENT : '/*' (.)*? '*/' -> skip ;
WS : [ \n\t\r] + -> skip ;