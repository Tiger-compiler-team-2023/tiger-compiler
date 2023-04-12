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
    push x0 // var 1/7
    push x0 // var 2/7
    push x0 // var 3/7
    push x0 // var 4/7
    push x0 // var 5/7
    push x0 // var 6/7
    push x0 // var 7/7
    // BEGIN DeclarationList
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #2
                push x9
            // END IntegerNode
            
            // BEGIN Id a1
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id a1
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #18
                push x9
            // END IntegerNode
            
            // BEGIN Id a2
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #48 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id a2
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #9
                push x9
            // END IntegerNode
            
            // BEGIN Id a3
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #64 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id a3
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #0
                push x9
            // END IntegerNode
            
            // BEGIN Id r1
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #80 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r1
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #0
                push x9
            // END IntegerNode
            
            // BEGIN Id r2
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #96 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r2
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #0
                push x9
            // END IntegerNode
            
            // BEGIN Id r3
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #112 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r3
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #0
                push x9
            // END IntegerNode
            
            // BEGIN Id r4
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #128 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r4
            
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
                // BEGIN Id a1
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a1
                
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
                // BEGIN Id a2
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a2
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
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
                // BEGIN Id a3
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #64 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a3
                
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
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_4
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN Affect
            // BEGIN Operateur binaire
                // BEGIN Id a1
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a1
                
                // BEGIN Operateur binaire
                    // BEGIN Id a2
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id a2
                    
                    // BEGIN Operateur binaire
                        // BEGIN Id a3
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #64 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                            at // i = *i
                        // END Id a3
                        
                        // BEGIN Id a1
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #32 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                            at // i = *i
                        // END Id a1
                        
                        bl ari_int_mul // [2] -> [1]
                    // END Operateur binaire
                    
                    bl ari_int_sub // [2] -> [1]
                // END Operateur binaire
                
                bl ari_int_add // [2] -> [1]
            // END Operateur binaire
            
            // BEGIN Id r1
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #80 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r1
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
        // BEGIN Affect
            // BEGIN Operateur binaire
                // BEGIN Id a2
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a2
                
                // BEGIN Id a3
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #64 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a3
                
                bl ari_int_div // [2] -> [1]
            // END Operateur binaire
            
            // BEGIN Id r2
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #96 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r2
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
        // BEGIN Affect
            // BEGIN IntegerNode
                MOV x9, #11
                push x9
            // END IntegerNode
            
            // BEGIN Id r3
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #112 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r3
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
        // BEGIN Affect
            // BEGIN IntegerNode
                MOV x9, #1
                push x9
            // END IntegerNode
            
            // BEGIN Id r4
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #128 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r4
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_5
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id r1
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #80 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r1
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_6
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id r2
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #96 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r2
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_7
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id r3
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #112 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r3
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_8
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id r4
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #128 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r4
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_9
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
    // BEGIN String lit Ln 16, Col 8
        str_0:
         .asciz "a1 a2 a3 \t"
        
    // END String lit Ln 16, Col 8
    
    // BEGIN String lit Ln 18, Col 8
        str_1:
         .asciz " "
        
    // END String lit Ln 18, Col 8
    
    // BEGIN String lit Ln 20, Col 8
        str_2:
         .asciz " "
        
    // END String lit Ln 20, Col 8
    
    // BEGIN String lit Ln 22, Col 8
        str_3:
         .asciz "\n"
        
    // END String lit Ln 22, Col 8
    
    // BEGIN String lit Ln 24, Col 8
        str_4:
         .asciz "Expressions arithemetiques \n"
        
    // END String lit Ln 24, Col 8
    
    // BEGIN String lit Ln 31, Col 8
        str_5:
         .asciz "r1 r2 r3 r4 \t"
        
    // END String lit Ln 31, Col 8
    
    // BEGIN String lit Ln 33, Col 8
        str_6:
         .asciz " "
        
    // END String lit Ln 33, Col 8
    
    // BEGIN String lit Ln 35, Col 8
        str_7:
         .asciz " "
        
    // END String lit Ln 35, Col 8
    
    // BEGIN String lit Ln 37, Col 8
        str_8:
         .asciz " "
        
    // END String lit Ln 37, Col 8
    
    // BEGIN String lit Ln 39, Col 8
        str_9:
         .asciz "\n"
        
    // END String lit Ln 39, Col 8
    
// END DATA

