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
    push x0 // var 1/6
    push x0 // var 2/6
    push x0 // var 3/6
    push x0 // var 4/6
    push x0 // var 5/6
    push x0 // var 6/6
    // BEGIN DeclarationList
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
    // END DeclarationList
    
    // BEGIN Sequence
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
                // BEGIN FunctionCall
                    // GESTION DU NOUVEAU SCOPE
                    push x28/*Ch. STAT*/
                    push x29/*Ch. DYN*/
                    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                    // BEGIN Id addition
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id addition
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #5
                            push x9
                        // END IntegerNode
                        
                        // BEGIN IntegerNode
                            MOV x9, #2
                            push x9
                        // END IntegerNode
                        
                    // END ParameterList
                    
                    bl function_6
                    // GESTION FIN DU NOUVEAU SCOPE
                    pop x7 // RES
                    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                    pop x29/*Ch. DYN*/ // Ch. DYN
                    pop x28/*Ch. STAT*/ // Ch. STAT
                    push x7 // RES
                // END FunctionCall
                
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
                // BEGIN FunctionCall
                    // GESTION DU NOUVEAU SCOPE
                    push x28/*Ch. STAT*/
                    push x29/*Ch. DYN*/
                    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                    // BEGIN Id soustraction
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id soustraction
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #500
                            push x9
                        // END IntegerNode
                        
                        // BEGIN IntegerNode
                            MOV x9, #20
                            push x9
                        // END IntegerNode
                        
                    // END ParameterList
                    
                    bl function_11
                    // GESTION FIN DU NOUVEAU SCOPE
                    pop x7 // RES
                    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                    pop x29/*Ch. DYN*/ // Ch. DYN
                    pop x28/*Ch. STAT*/ // Ch. STAT
                    push x7 // RES
                // END FunctionCall
                
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
                // BEGIN FunctionCall
                    // GESTION DU NOUVEAU SCOPE
                    push x28/*Ch. STAT*/
                    push x29/*Ch. DYN*/
                    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                    // BEGIN Id multiplication
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id multiplication
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #12
                            push x9
                        // END IntegerNode
                        
                        // BEGIN IntegerNode
                            MOV x9, #12
                            push x9
                        // END IntegerNode
                        
                    // END ParameterList
                    
                    bl function_16
                    // GESTION FIN DU NOUVEAU SCOPE
                    pop x7 // RES
                    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                    pop x29/*Ch. DYN*/ // Ch. DYN
                    pop x28/*Ch. STAT*/ // Ch. STAT
                    push x7 // RES
                // END FunctionCall
                
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
                // BEGIN FunctionCall
                    // GESTION DU NOUVEAU SCOPE
                    push x28/*Ch. STAT*/
                    push x29/*Ch. DYN*/
                    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                    // BEGIN Id modulo
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id modulo
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #2
                            push x9
                        // END IntegerNode
                        
                        // BEGIN IntegerNode
                            MOV x9, #5
                            push x9
                        // END IntegerNode
                        
                    // END ParameterList
                    
                    bl function_26
                    // GESTION FIN DU NOUVEAU SCOPE
                    pop x7 // RES
                    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                    pop x29/*Ch. DYN*/ // Ch. DYN
                    pop x28/*Ch. STAT*/ // Ch. STAT
                    push x7 // RES
                // END FunctionCall
                
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
                // BEGIN StringNode
                    ldr x0, =str_9
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN FunctionCall
                    // GESTION DU NOUVEAU SCOPE
                    push x28/*Ch. STAT*/
                    push x29/*Ch. DYN*/
                    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                    // BEGIN Id puissance
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id puissance
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #33
                            push x9
                        // END IntegerNode
                        
                        // BEGIN IntegerNode
                            MOV x9, #2
                            push x9
                        // END IntegerNode
                        
                    // END ParameterList
                    
                    bl function_31
                    // GESTION FIN DU NOUVEAU SCOPE
                    pop x7 // RES
                    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                    pop x29/*Ch. DYN*/ // Ch. DYN
                    pop x28/*Ch. STAT*/ // Ch. STAT
                    push x7 // RES
                // END FunctionCall
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_10
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
    // BEGIN Function addition
        function_6:
        push x30/*@retour*/ // @retour
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
            
            // BEGIN Id b
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
                at // i = *i
            // END Id b
            
            bl ari_int_add // [2] -> [1]
        // END Operateur binaire
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function addition
    
    // BEGIN Function soustraction
        function_11:
        push x30/*@retour*/ // @retour
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
            
            // BEGIN Id b
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
                at // i = *i
            // END Id b
            
            bl ari_int_sub // [2] -> [1]
        // END Operateur binaire
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function soustraction
    
    // BEGIN Function multiplication
        function_16:
        push x30/*@retour*/ // @retour
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
            
            // BEGIN Id b
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
                at // i = *i
            // END Id b
            
            bl ari_int_mul // [2] -> [1]
        // END Operateur binaire
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function multiplication
    
    // BEGIN Function division
        function_21:
        push x30/*@retour*/ // @retour
        // BEGIN IfThenElse2
            // BEGIN Operateur binaire
                // BEGIN Id b
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id b
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl ari_int_NE // [2] -> [1]
            // END Operateur binaire
            
            pop x1
            cmp x1,#0
            beq _else_2
            bne _then_2
            _else_2:
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
                
                // BEGIN Id b
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id b
                
                bl ari_int_div // [2] -> [1]
            // END Operateur binaire
            
            b _end_ifthenelse_2
            _then_2:
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
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
            // END Sequence
            
            b _end_ifthenelse_2
            _end_ifthenelse_2:
        // END IfThenElse2
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function division
    
    // BEGIN Function modulo
        function_26:
        push x30/*@retour*/ // @retour
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
            
            // BEGIN Operateur binaire
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
                    
                    // BEGIN Id b
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id b
                    
                    bl ari_int_div // [2] -> [1]
                // END Operateur binaire
                
                // BEGIN Id b
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id b
                
                bl ari_int_mul // [2] -> [1]
            // END Operateur binaire
            
            bl ari_int_sub // [2] -> [1]
        // END Operateur binaire
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function modulo
    
    // BEGIN Function puissance
        function_31:
        push x30/*@retour*/ // @retour
        // BEGIN Sequence
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
                    // BEGIN VariableDeclaration
                        // BEGIN Id a
                            push x28/*Ch. STAT*/
                            mov x0, #1 // depth
                            push x0
                            mov x0, #48 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                            at // i = *i
                        // END Id a
                        
                        // BEGIN Id b
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
                            push x0
                            mov x0, #32 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                        // END Id b
                        
                        pop x0 // adresse
                        pop x1 // val
                        STR x1, [x0]
                    // END VariableDeclaration
                    
                // END DeclarationList
                
                // BEGIN Sequence
                    // BEGIN IfThenElse3
                        // BEGIN Operateur binaire
                            // BEGIN Id n
                                push x28/*Ch. STAT*/
                                mov x0, #1 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                                at // i = *i
                            // END Id n
                            
                            // BEGIN IntegerNode
                                MOV x9, #0
                                push x9
                            // END IntegerNode
                            
                            bl ari_int_EQ // [2] -> [1]
                        // END Operateur binaire
                        
                        pop x1
                        cmp x1,#0
                        beq _else_3
                        bne _then_3
                        _else_3:
                        // BEGIN While4
                            b _loop_4
                            _loop_4:
                            // BEGIN Operateur binaire
                                // BEGIN Id n
                                    push x28/*Ch. STAT*/
                                    mov x0, #1 // depth
                                    push x0
                                    mov x0, #32 // depl
                                    push x0
                                    bl chainage_st // [3] -> [1]
                                    at // i = *i
                                // END Id n
                                
                                // BEGIN IntegerNode
                                    MOV x9, #1
                                    push x9
                                // END IntegerNode
                                
                                bl ari_int_GT // [2] -> [1]
                            // END Operateur binaire
                            
                            pop x1
                            cmp x1,#0
                            beq _end_loop_4
                            // BEGIN Sequence
                                // BEGIN Affect
                                    // BEGIN Operateur binaire
                                        // BEGIN Id a
                                            push x28/*Ch. STAT*/
                                            mov x0, #1 // depth
                                            push x0
                                            mov x0, #48 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                            at // i = *i
                                        // END Id a
                                        
                                        // BEGIN Id b
                                            push x28/*Ch. STAT*/
                                            mov x0, #0 // depth
                                            push x0
                                            mov x0, #32 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                            at // i = *i
                                        // END Id b
                                        
                                        bl ari_int_mul // [2] -> [1]
                                    // END Operateur binaire
                                    
                                    // BEGIN Id b
                                        push x28/*Ch. STAT*/
                                        mov x0, #0 // depth
                                        push x0
                                        mov x0, #32 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                    // END Id b
                                    
                                    pop x0 // adresse
                                    pop x1 // val
                                    STR x1, [x0]
                                // END Affect
                                
                                // BEGIN Affect
                                    // BEGIN Operateur binaire
                                        // BEGIN Id n
                                            push x28/*Ch. STAT*/
                                            mov x0, #1 // depth
                                            push x0
                                            mov x0, #32 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                            at // i = *i
                                        // END Id n
                                        
                                        // BEGIN IntegerNode
                                            MOV x9, #1
                                            push x9
                                        // END IntegerNode
                                        
                                        bl ari_int_sub // [2] -> [1]
                                    // END Operateur binaire
                                    
                                    // BEGIN Id n
                                        push x28/*Ch. STAT*/
                                        mov x0, #1 // depth
                                        push x0
                                        mov x0, #32 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                    // END Id n
                                    
                                    pop x0 // adresse
                                    pop x1 // val
                                    STR x1, [x0]
                                // END Affect
                                
                            // END Sequence
                            
                            b _loop_4
                            _end_loop_4:
                        // END While4
                        
                        b _end_ifthenelse_3
                        _then_3:
                        // BEGIN Affect
                            // BEGIN IntegerNode
                                MOV x9, #1
                                push x9
                            // END IntegerNode
                            
                            // BEGIN Id b
                                push x28/*Ch. STAT*/
                                mov x0, #0 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                            // END Id b
                            
                            pop x0 // adresse
                            pop x1 // val
                            STR x1, [x0]
                        // END Affect
                        
                        b _end_ifthenelse_3
                        _end_ifthenelse_3:
                    // END IfThenElse3
                    
                    // BEGIN Id b
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id b
                    
                // END Sequence
                
                // GESTION FIN DU NOUVEAU SCOPE
                pop x7 // RES
                mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                pop x29/*Ch. DYN*/ // Ch. DYN
                pop x28/*Ch. STAT*/ // Ch. STAT
                push x7 // RES
            // END Let
            
        // END Sequence
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function puissance
    
// END FUNCTIONS
    .include "assembleur/arithmetic_functions.s"
    .include "assembleur/data_functions.s"
    .include "assembleur/base_functions.s"
// BEGIN DATA
    // BEGIN String lit Ln 10, Col 10
        str_0:
         .asciz "le second membre ne doit pas etre null"
        
    // END String lit Ln 10, Col 10
    
    // BEGIN String lit Ln 29, Col 8
        str_1:
         .asciz "resulat de l addition "
        
    // END String lit Ln 29, Col 8
    
    // BEGIN String lit Ln 29, Col 62
        str_2:
         .asciz "\n"
        
    // END String lit Ln 29, Col 62
    
    // BEGIN String lit Ln 30, Col 8
        str_3:
         .asciz "resulat de la soustraction "
        
    // END String lit Ln 30, Col 8
    
    // BEGIN String lit Ln 30, Col 74
        str_4:
         .asciz "\n"
        
    // END String lit Ln 30, Col 74
    
    // BEGIN String lit Ln 31, Col 8
        str_5:
         .asciz "resulat de le multiplication "
        
    // END String lit Ln 31, Col 8
    
    // BEGIN String lit Ln 31, Col 77
        str_6:
         .asciz "\n"
        
    // END String lit Ln 31, Col 77
    
    // BEGIN String lit Ln 32, Col 8
        str_7:
         .asciz "resulat de la division "
        
    // END String lit Ln 32, Col 8
    
    // BEGIN String lit Ln 32, Col 61
        str_8:
         .asciz "\n"
        
    // END String lit Ln 32, Col 61
    
    // BEGIN String lit Ln 33, Col 8
        str_9:
         .asciz "2 expo 33 :"
        
    // END String lit Ln 33, Col 8
    
    // BEGIN String lit Ln 33, Col 53
        str_10:
         .asciz "\n"
        
    // END String lit Ln 33, Col 53
    
// END DATA

