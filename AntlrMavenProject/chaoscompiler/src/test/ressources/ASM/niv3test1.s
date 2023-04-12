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
    push x0 // var 1/4
    push x0 // var 2/4
    push x0 // var 3/4
    push x0 // var 4/4
    // BEGIN DeclarationList
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
                    ldr x0, =str_4
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
                    // BEGIN Id fibonacci
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id fibonacci
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #5
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
                    ldr x0, =str_5
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
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
                // BEGIN FunctionCall
                    // GESTION DU NOUVEAU SCOPE
                    push x28/*Ch. STAT*/
                    push x29/*Ch. DYN*/
                    mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                    // BEGIN Id factoriel
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #0 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                    // END Id factoriel
                    
                    add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                    // BEGIN ParameterList
                        // BEGIN IntegerNode
                            MOV x9, #13
                            push x9
                        // END IntegerNode
                        
                    // END ParameterList
                    
                    bl function_10
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
                    ldr x0, =str_7
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
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
            // GESTION DU NOUVEAU SCOPE
            push x28/*Ch. STAT*/
            push x29/*Ch. DYN*/
            mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
            // BEGIN Id bouclesimbriques
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #0 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id bouclesimbriques
            
            add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
            // BEGIN ParameterList
            // END ParameterList
            
            bl function_14
            // GESTION FIN DU NOUVEAU SCOPE
            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
            pop x29/*Ch. DYN*/ // Ch. DYN
            pop x28/*Ch. STAT*/ // Ch. STAT
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
                // BEGIN StringNode
                    ldr x0, =str_10
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN FunctionCall
            // GESTION DU NOUVEAU SCOPE
            push x28/*Ch. STAT*/
            push x29/*Ch. DYN*/
            mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
            // BEGIN Id whilefor
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #0 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id whilefor
            
            add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
            // BEGIN ParameterList
            // END ParameterList
            
            bl function_19
            // GESTION FIN DU NOUVEAU SCOPE
            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
            pop x29/*Ch. DYN*/ // Ch. DYN
            pop x28/*Ch. STAT*/ // Ch. STAT
        // END FunctionCall
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN StringNode
                    ldr x0, =str_11
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
    // BEGIN Function fibonacci
        function_6:
        push x30/*@retour*/ // @retour
        // BEGIN Sequence
            // BEGIN IfThenElse2
                // BEGIN Operateur binaire
                    // BEGIN Id n
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
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
                beq _else_2
                bne _then_2
                _else_2:
                // BEGIN IfThenElse3
                    // BEGIN Operateur binaire
                        // BEGIN Id n
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
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
                        
                        bl ari_int_EQ // [2] -> [1]
                    // END Operateur binaire
                    
                    pop x1
                    cmp x1,#0
                    beq _else_3
                    bne _then_3
                    _else_3:
                    // BEGIN Operateur binaire
                        // BEGIN FunctionCall
                            // GESTION DU NOUVEAU SCOPE
                            push x28/*Ch. STAT*/
                            push x29/*Ch. DYN*/
                            mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                            push x28/*Ch. STAT*/ // Appel récursif
                            add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                            // BEGIN ParameterList
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
                                
                            // END ParameterList
                            
                            bl function_6
                            // GESTION FIN DU NOUVEAU SCOPE
                            pop x7 // RES
                            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                            pop x29/*Ch. DYN*/ // Ch. DYN
                            pop x28/*Ch. STAT*/ // Ch. STAT
                            push x7 // RES
                        // END FunctionCall
                        
                        // BEGIN FunctionCall
                            // GESTION DU NOUVEAU SCOPE
                            push x28/*Ch. STAT*/
                            push x29/*Ch. DYN*/
                            mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                            push x28/*Ch. STAT*/ // Appel récursif
                            add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                            // BEGIN ParameterList
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
                                        MOV x9, #2
                                        push x9
                                    // END IntegerNode
                                    
                                    bl ari_int_sub // [2] -> [1]
                                // END Operateur binaire
                                
                            // END ParameterList
                            
                            bl function_6
                            // GESTION FIN DU NOUVEAU SCOPE
                            pop x7 // RES
                            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                            pop x29/*Ch. DYN*/ // Ch. DYN
                            pop x28/*Ch. STAT*/ // Ch. STAT
                            push x7 // RES
                        // END FunctionCall
                        
                        bl ari_int_add // [2] -> [1]
                    // END Operateur binaire
                    
                    b _end_ifthenelse_3
                    _then_3:
                    // BEGIN IntegerNode
                        MOV x9, #1
                        push x9
                    // END IntegerNode
                    
                    b _end_ifthenelse_3
                    _end_ifthenelse_3:
                // END IfThenElse3
                
                b _end_ifthenelse_2
                _then_2:
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                b _end_ifthenelse_2
                _end_ifthenelse_2:
            // END IfThenElse2
            
        // END Sequence
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function fibonacci
    
    // BEGIN Function factoriel
        function_10:
        push x30/*@retour*/ // @retour
        // BEGIN Sequence
            // BEGIN IfThenElse4
                // BEGIN Operateur binaire
                    // BEGIN Operateur binaire
                        // BEGIN Id n
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
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
                    
                    // BEGIN Operateur binaire
                        // BEGIN Id n
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
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
                        
                        bl ari_int_EQ // [2] -> [1]
                    // END Operateur binaire
                    
                    bl ari_log_or // [2] -> [1]
                // END Operateur binaire
                
                pop x1
                cmp x1,#0
                beq _else_4
                bne _then_4
                _else_4:
                // BEGIN Operateur binaire
                    // BEGIN Id n
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #32 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id n
                    
                    // BEGIN FunctionCall
                        // GESTION DU NOUVEAU SCOPE
                        push x28/*Ch. STAT*/
                        push x29/*Ch. DYN*/
                        mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
                        push x28/*Ch. STAT*/ // Appel récursif
                        add x28/*Ch. STAT*/, SP/*STACK*/, #16 // Ch. STAT
                        // BEGIN ParameterList
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
                            
                        // END ParameterList
                        
                        bl function_10
                        // GESTION FIN DU NOUVEAU SCOPE
                        pop x7 // RES
                        mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                        pop x29/*Ch. DYN*/ // Ch. DYN
                        pop x28/*Ch. STAT*/ // Ch. STAT
                        push x7 // RES
                    // END FunctionCall
                    
                    bl ari_int_mul // [2] -> [1]
                // END Operateur binaire
                
                b _end_ifthenelse_4
                _then_4:
                // BEGIN IntegerNode
                    MOV x9, #1
                    push x9
                // END IntegerNode
                
                b _end_ifthenelse_4
                _end_ifthenelse_4:
            // END IfThenElse4
            
        // END Sequence
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function factoriel
    
    // BEGIN Function bouclesimbriques
        function_14:
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
                            MOV x9, #10
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
                        // BEGIN Sequence
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
                                        
                                        // BEGIN Id j
                                            push x28/*Ch. STAT*/
                                            mov x0, #0 // depth
                                            push x0
                                            mov x0, #32 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                        // END Id j
                                        
                                        pop x0 // adresse
                                        pop x1 // val
                                        STR x1, [x0]
                                    // END VariableDeclaration
                                    
                                    // BEGIN VariableDeclaration
                                        // BEGIN IntegerNode
                                            MOV x9, #10
                                            push x9
                                        // END IntegerNode
                                        
                                        // BEGIN Id end_j
                                            push x28/*Ch. STAT*/
                                            mov x0, #0 // depth
                                            push x0
                                            mov x0, #48 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                        // END Id end_j
                                        
                                        pop x0 // adresse
                                        pop x1 // val
                                        STR x1, [x0]
                                    // END VariableDeclaration
                                    
                                // END DeclarationList
                                
                                // BEGIN While3
                                    b _loop_3
                                    _loop_3:
                                    // BEGIN Operateur binaire
                                        // BEGIN Id j
                                            push x28/*Ch. STAT*/
                                            mov x0, #0 // depth
                                            push x0
                                            mov x0, #32 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                            at // i = *i
                                        // END Id j
                                        
                                        // BEGIN Id end_j
                                            push x28/*Ch. STAT*/
                                            mov x0, #0 // depth
                                            push x0
                                            mov x0, #48 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                            at // i = *i
                                        // END Id end_j
                                        
                                        bl ari_int_LE // [2] -> [1]
                                    // END Operateur binaire
                                    
                                    pop x1
                                    cmp x1,#0
                                    beq _end_loop_3
                                    // BEGIN Sequence
                                        // BEGIN Sequence
                                            // BEGIN FunctionCall
                                                // BEGIN ParameterList
                                                    // BEGIN Operateur binaire
                                                        // BEGIN Id i
                                                            push x28/*Ch. STAT*/
                                                            mov x0, #1 // depth
                                                            push x0
                                                            mov x0, #32 // depl
                                                            push x0
                                                            bl chainage_st // [3] -> [1]
                                                            at // i = *i
                                                        // END Id i
                                                        
                                                        // BEGIN Id j
                                                            push x28/*Ch. STAT*/
                                                            mov x0, #0 // depth
                                                            push x0
                                                            mov x0, #32 // depl
                                                            push x0
                                                            bl chainage_st // [3] -> [1]
                                                            at // i = *i
                                                        // END Id j
                                                        
                                                        bl ari_int_mul // [2] -> [1]
                                                    // END Operateur binaire
                                                    
                                                // END ParameterList
                                                
                                                // Fonction de la stdlib (id=printi)
                                                bl print_int10 // [1] -> [0]
                                            // END FunctionCall
                                            
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
                                            
                                        // END Sequence
                                        
                                        // BEGIN Affect
                                            // BEGIN Operateur binaire
                                                // BEGIN Id j
                                                    push x28/*Ch. STAT*/
                                                    mov x0, #0 // depth
                                                    push x0
                                                    mov x0, #32 // depl
                                                    push x0
                                                    bl chainage_st // [3] -> [1]
                                                    at // i = *i
                                                // END Id j
                                                
                                                // BEGIN IntegerNode
                                                    MOV x9, #1
                                                    push x9
                                                // END IntegerNode
                                                
                                                bl ari_int_add // [2] -> [1]
                                            // END Operateur binaire
                                            
                                            // BEGIN Id j
                                                push x28/*Ch. STAT*/
                                                mov x0, #0 // depth
                                                push x0
                                                mov x0, #32 // depl
                                                push x0
                                                bl chainage_st // [3] -> [1]
                                            // END Id j
                                            
                                            pop x0 // adresse
                                            pop x1 // val
                                            STR x1, [x0]
                                        // END Affect
                                        
                                    // END Sequence
                                    
                                    b _loop_3
                                    _end_loop_3:
                                // END While3
                                
                                // GESTION FIN DU NOUVEAU SCOPE
                                mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                                pop x29/*Ch. DYN*/ // Ch. DYN
                                pop x28/*Ch. STAT*/ // Ch. STAT
                            // END Let
                            
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
                            
                        // END Sequence
                        
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
            
        // END Sequence
        
        pop x30/*@retour*/ // @retour
        ret
    // END Function bouclesimbriques
    
    // BEGIN Function whilefor
        function_19:
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
                    
                // END DeclarationList
                
                // BEGIN While4
                    b _loop_4
                    _loop_4:
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
                            MOV x9, #5
                            push x9
                        // END IntegerNode
                        
                        bl ari_int_LE // [2] -> [1]
                    // END Operateur binaire
                    
                    pop x1
                    cmp x1,#0
                    beq _end_loop_4
                    // BEGIN Sequence
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
                                        MOV x9, #1
                                        push x9
                                    // END IntegerNode
                                    
                                    // BEGIN Id j
                                        push x28/*Ch. STAT*/
                                        mov x0, #0 // depth
                                        push x0
                                        mov x0, #32 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                    // END Id j
                                    
                                    pop x0 // adresse
                                    pop x1 // val
                                    STR x1, [x0]
                                // END VariableDeclaration
                                
                                // BEGIN VariableDeclaration
                                    // BEGIN Id i
                                        push x28/*Ch. STAT*/
                                        mov x0, #1 // depth
                                        push x0
                                        mov x0, #32 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                        at // i = *i
                                    // END Id i
                                    
                                    // BEGIN Id end_j
                                        push x28/*Ch. STAT*/
                                        mov x0, #0 // depth
                                        push x0
                                        mov x0, #48 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                    // END Id end_j
                                    
                                    pop x0 // adresse
                                    pop x1 // val
                                    STR x1, [x0]
                                // END VariableDeclaration
                                
                            // END DeclarationList
                            
                            // BEGIN While5
                                b _loop_5
                                _loop_5:
                                // BEGIN Operateur binaire
                                    // BEGIN Id j
                                        push x28/*Ch. STAT*/
                                        mov x0, #0 // depth
                                        push x0
                                        mov x0, #32 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                        at // i = *i
                                    // END Id j
                                    
                                    // BEGIN Id end_j
                                        push x28/*Ch. STAT*/
                                        mov x0, #0 // depth
                                        push x0
                                        mov x0, #48 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                        at // i = *i
                                    // END Id end_j
                                    
                                    bl ari_int_LE // [2] -> [1]
                                // END Operateur binaire
                                
                                pop x1
                                cmp x1,#0
                                beq _end_loop_5
                                // BEGIN Sequence
                                    // BEGIN Sequence
                                        // BEGIN FunctionCall
                                            // BEGIN ParameterList
                                                // BEGIN Id i
                                                    push x28/*Ch. STAT*/
                                                    mov x0, #1 // depth
                                                    push x0
                                                    mov x0, #32 // depl
                                                    push x0
                                                    bl chainage_st // [3] -> [1]
                                                    at // i = *i
                                                // END Id i
                                                
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
                                        
                                    // END Sequence
                                    
                                    // BEGIN Affect
                                        // BEGIN Operateur binaire
                                            // BEGIN Id j
                                                push x28/*Ch. STAT*/
                                                mov x0, #0 // depth
                                                push x0
                                                mov x0, #32 // depl
                                                push x0
                                                bl chainage_st // [3] -> [1]
                                                at // i = *i
                                            // END Id j
                                            
                                            // BEGIN IntegerNode
                                                MOV x9, #1
                                                push x9
                                            // END IntegerNode
                                            
                                            bl ari_int_add // [2] -> [1]
                                        // END Operateur binaire
                                        
                                        // BEGIN Id j
                                            push x28/*Ch. STAT*/
                                            mov x0, #0 // depth
                                            push x0
                                            mov x0, #32 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                        // END Id j
                                        
                                        pop x0 // adresse
                                        pop x1 // val
                                        STR x1, [x0]
                                    // END Affect
                                    
                                // END Sequence
                                
                                b _loop_5
                                _end_loop_5:
                            // END While5
                            
                            // GESTION FIN DU NOUVEAU SCOPE
                            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                            pop x29/*Ch. DYN*/ // Ch. DYN
                            pop x28/*Ch. STAT*/ // Ch. STAT
                        // END Let
                        
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
                    
                    b _loop_4
                    _end_loop_4:
                // END While4
                
                // GESTION FIN DU NOUVEAU SCOPE
                mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                pop x29/*Ch. DYN*/ // Ch. DYN
                pop x28/*Ch. STAT*/ // Ch. STAT
            // END Let
            
        // END Sequence
        
        pop x30/*@retour*/ // @retour
        ret
    // END Function whilefor
    
// END FUNCTIONS
    .include "assembleur/arithmetic_functions.s"
    .include "assembleur/data_functions.s"
    .include "assembleur/base_functions.s"
// BEGIN DATA
    // BEGIN String lit Ln 21, Col 26
        str_0:
         .asciz "\t"
        
    // END String lit Ln 21, Col 26
    
    // BEGIN String lit Ln 23, Col 12
        str_1:
         .asciz "\n"
        
    // END String lit Ln 23, Col 12
    
    // BEGIN String lit Ln 32, Col 24
        str_2:
         .asciz " "
        
    // END String lit Ln 32, Col 24
    
    // BEGIN String lit Ln 34, Col 12
        str_3:
         .asciz "\n"
        
    // END String lit Ln 34, Col 12
    
    // BEGIN String lit Ln 40, Col 8
        str_4:
         .asciz "fib de 5 "
        
    // END String lit Ln 40, Col 8
    
    // BEGIN String lit Ln 40, Col 48
        str_5:
         .asciz "\n"
        
    // END String lit Ln 40, Col 48
    
    // BEGIN String lit Ln 41, Col 8
        str_6:
         .asciz "factoriel de 13 "
        
    // END String lit Ln 41, Col 8
    
    // BEGIN String lit Ln 41, Col 56
        str_7:
         .asciz "\n"
        
    // END String lit Ln 41, Col 56
    
    // BEGIN String lit Ln 42, Col 8
        str_8:
         .asciz "boucles imbriques \n"
        
    // END String lit Ln 42, Col 8
    
    // BEGIN String lit Ln 42, Col 57
        str_9:
         .asciz "\n"
        
    // END String lit Ln 42, Col 57
    
    // BEGIN String lit Ln 43, Col 8
        str_10:
         .asciz "whilefor \n"
        
    // END String lit Ln 43, Col 8
    
    // BEGIN String lit Ln 43, Col 40
        str_11:
         .asciz "\n"
        
    // END String lit Ln 43, Col 40
    
// END DATA

