grammar expr;

@header{
package parser;
}

program : TODO;

TODO : 'A';

WS : [ \n\t\r] + -> skip ;
