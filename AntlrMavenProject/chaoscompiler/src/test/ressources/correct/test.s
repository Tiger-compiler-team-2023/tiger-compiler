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
    push x0 // var 1/1
    // BEGIN DeclarationList
        // BEGIN ArrayTypeDeclaration
        // END ArrayTypeDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN ArrayAssign
                // BEGIN IntegerNode
                    MOV x9, #2
                    push x9
                // END IntegerNode
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl array_assign // [2] -> [1]
            // END ArrayAssign
            
            // BEGIN Id r
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
    // END DeclarationList
    
    // BEGIN Sequence
        // BEGIN Affect
            // BEGIN IntegerNode
                MOV x9, #5
                push x9
            // END IntegerNode
            
            // BEGIN ArrayAccess
                // BEGIN Id r
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl array_access // [2] -> [1]
            // END ArrayAccess
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
        // BEGIN Affect
            // BEGIN StringNode
                ldr x0, =str_0
                push x0
            // END StringNode
            
            // BEGIN ArrayAccess
                // BEGIN Id r
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r
                
                // BEGIN IntegerNode
                    MOV x9, #1
                    push x9
                // END IntegerNode
                
                bl array_access // [2] -> [1]
            // END ArrayAccess
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN ArrayAccess
                    // BEGIN Id r
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id r
                    
                    // BEGIN IntegerNode
                        MOV x9, #0
                        push x9
                    // END IntegerNode
                    
                    bl array_access // [2] -> [1]
                    at // i = *i
                // END ArrayAccess
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN ArrayAccess
                    // BEGIN Id r
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id r
                    
                    // BEGIN IntegerNode
                        MOV x9, #1
                        push x9
                    // END IntegerNode
                    
                    bl array_access // [2] -> [1]
                    at // i = *i
                // END ArrayAccess
                
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
    // BEGIN String lit Ln 3, Col 23
        str_0:
         .asciz "coucou"
        
    // END String lit Ln 3, Col 23
    
// END DATA

