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
    push x0 // var 1/3
    push x0 // var 2/3
    push x0 // var 3/3
    // BEGIN DeclarationList
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #0
                push x9
            // END IntegerNode
            
            // BEGIN Id input
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id input
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #3
                push x9
            // END IntegerNode
            
            // BEGIN Id a
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #48 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id a
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #1
                push x9
            // END IntegerNode
            
            // BEGIN Id r
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #64 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id r
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
    // END DeclarationList
    
    // BEGIN Sequence
        // BEGIN IfThenElse2
            // BEGIN Operateur binaire
                // BEGIN Id input
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id input
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl ari_int_EQ // [2] -> [1]
            // END Operateur binaire
            
            pop x1
            cmp x1,#0
            beq _else_2
            bne _then_2
            _else_2:
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
            
            b _end_ifthenelse_2
            _then_2:
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
            
            b _end_ifthenelse_2
            _end_ifthenelse_2:
        // END IfThenElse2
        
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
                        MOV x9, #0
                        push x9
                    // END IntegerNode
                    
                    // BEGIN Id i
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id i
                    
                    pop x0 // adresse
                    pop x1 // val
                    STR x1, [x0]
                // END VariableDeclaration
                
                // BEGIN VariableDeclaration
                    // BEGIN IntegerNode
                        MOV x9, #7
                        push x9
                    // END IntegerNode
                    
                    // BEGIN Id end_i
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id end_i
                    
                    pop x0 // adresse
                    pop x1 // val
                    STR x1, [x0]
                // END VariableDeclaration
                
            // END DeclarationList
            
            // BEGIN While2
                b _loop_2
                _loop_2:
                // BEGIN Operateur binaire
                    // BEGIN Id i
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id i
                    
                    // BEGIN Id end_i
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id end_i
                    
                    bl ari_int_LE // [2] -> [1]
                // END Operateur binaire
                
                pop x1
                cmp x1,#0
                beq _end_loop_2
                // BEGIN Sequence
                    // BEGIN Affect
                        // BEGIN Operateur binaire
                            // BEGIN Id input
                                push x28/*Ch. STAT*/
                                mov x0, #1 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                                at // i = *i
                            // END Id input
                            
                            // BEGIN Id i
                                push x28/*Ch. STAT*/
                                mov x0, #0 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                                at // i = *i
                            // END Id i
                            
                            bl ari_int_add // [2] -> [1]
                        // END Operateur binaire
                        
                        // BEGIN Id input
                            push x28/*Ch. STAT*/
                            mov x0, #1 // depth
                            push x0
                            mov x0, #32 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                        // END Id input
                        
                        pop x0 // adresse
                        pop x1 // val
                        STR x1, [x0]
                    // END Affect
                    
                    // BEGIN Affect
                        // BEGIN Operateur binaire
                            // BEGIN Id i
                                push x28/*Ch. STAT*/
                                mov x0, #0 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                                at // i = *i
                            // END Id i
                            
                            // BEGIN IntegerNode
                                MOV x9, #1
                                push x9
                            // END IntegerNode
                            
                            bl ari_int_add // [2] -> [1]
                        // END Operateur binaire
                        
                        // BEGIN Id i
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #32 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                        // END Id i
                        
                        pop x0 // adresse
                        pop x1 // val
                        STR x1, [x0]
                    // END Affect
                    
                // END Sequence
                
                b _loop_2
                _end_loop_2:
            // END While2
            
            // GESTION FIN DU NOUVEAU SCOPE
            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
            pop x29/*Ch. DYN*/ // Ch. DYN
            pop x28/*Ch. STAT*/ // Ch. STAT
        // END Let
        
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
                // BEGIN Id input
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id input
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
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
        
        // BEGIN While3
            b _loop_3
            _loop_3:
            // BEGIN Operateur binaire
                // BEGIN Id a
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id a
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl ari_int_NE // [2] -> [1]
            // END Operateur binaire
            
            pop x1
            cmp x1,#0
            beq _end_loop_3
            // BEGIN Sequence
                // BEGIN Affect
                    // BEGIN Operateur binaire
                        // BEGIN Id r
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #64 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                            at // i = *i
                        // END Id r
                        
                        // BEGIN Id a
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #48 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                            at // i = *i
                        // END Id a
                        
                        bl ari_int_mul // [2] -> [1]
                    // END Operateur binaire
                    
                    // BEGIN Id r
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #64 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id r
                    
                    pop x0 // adresse
                    pop x1 // val
                    STR x1, [x0]
                // END Affect
                
                // BEGIN Affect
                    // BEGIN Operateur binaire
                        // BEGIN Id a
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #48 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                            at // i = *i
                        // END Id a
                        
                        // BEGIN IntegerNode
                            MOV x9, #1
                            push x9
                        // END IntegerNode
                        
                        bl ari_int_sub // [2] -> [1]
                    // END Operateur binaire
                    
                    // BEGIN Id a
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id a
                    
                    pop x0 // adresse
                    pop x1 // val
                    STR x1, [x0]
                // END Affect
                
            // END Sequence
            
            b _loop_3
            _end_loop_3:
        // END While3
        
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
                // BEGIN Id r
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #64 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id r
                
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
    // BEGIN String lit Ln 10, Col 72
        str_0:
         .asciz "la valeur de l input n est pas nulle\n"
        
    // END String lit Ln 10, Col 72
    
    // BEGIN String lit Ln 10, Col 25
        str_1:
         .asciz "la valeur de l input est nulle\n"
        
    // END String lit Ln 10, Col 25
    
    // BEGIN String lit Ln 15, Col 8
        str_2:
         .asciz "u0 est 0 \n"
        
    // END String lit Ln 15, Col 8
    
    // BEGIN String lit Ln 15, Col 29
        str_3:
         .asciz "la somme arithmetique S7 tel que est "
        
    // END String lit Ln 15, Col 29
    
    // BEGIN String lit Ln 15, Col 90
        str_4:
         .asciz "\n"
        
    // END String lit Ln 15, Col 90
    
    // BEGIN String lit Ln 20, Col 8
        str_5:
         .asciz "factioriel de 3 est "
        
    // END String lit Ln 20, Col 8
    
    // BEGIN String lit Ln 20, Col 48
        str_6:
         .asciz "\n"
        
    // END String lit Ln 20, Col 48
    
// END DATA

