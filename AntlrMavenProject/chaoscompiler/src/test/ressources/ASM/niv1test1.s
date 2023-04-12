.include "assembleur/base_macros.s"
// MACROS
    // Pas de macro à ajouter
// fin MACROS
    .section .text
    .global _start
    _start:
    mov x29, sp
// EXECUTION
// BEGIN Let
    // GESTION DU NOUVEAU SCOPE
    push x28/*Ch. STAT*/
    push x29/*Ch. DYN*/
    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
    push x28/*Ch. STAT*/
    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
    mov x0, #0
    push x0 // var 1/2
    push x0 // var 2/2
    // BEGIN DeclarationList
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #4
                push x9
            // END IntegerNode
            
            // BEGIN Id a
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id a
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #-1
                push x9
            // END IntegerNode
            
            // BEGIN Id b
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #48 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id b
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
    // END DeclarationList
    
    // BEGIN Sequence
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_0
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id a
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_1
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_2
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id b
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id b
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_3
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
    // END Sequence
    
    // GESTION FIN DU NOUVEAU SCOPE
    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
    pop x29/*Ch. DYN*/ // Ch. DYN
    pop x28/*Ch. STAT*/ // Ch. STAT
// END Let
// fin EXECUTION
    exit #0
// BEGIN FUNCTIONS
// END FUNCTIONS
    .include "assembleur/arithmetic_functions.s"
    .include "assembleur/data_functions.s"
    .include "assembleur/base_functions.s"
// BEGIN DATA
    // BEGIN String lit Ln 8, Col 8
        str_0:
         .asciz "a "
        
    // END String lit Ln 8, Col 8
    
    // BEGIN String lit Ln 10, Col 8
        str_1:
         .asciz "\n"
        
    // END String lit Ln 10, Col 8
    
    // BEGIN String lit Ln 11, Col 8
        str_2:
         .asciz "b "
        
    // END String lit Ln 11, Col 8
    
    // BEGIN String lit Ln 13, Col 8
        str_3:
         .asciz "\n"
        
    // END String lit Ln 13, Col 8
    
// END DATA

