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
        // BEGIN ArrayTypeDeclaration
        // END ArrayTypeDeclaration
        
        // BEGIN ArrayTypeDeclaration
        // END ArrayTypeDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN ArrayAssign
                // BEGIN IntegerNode
                    MOV x9, #3
                    push x9
                // END IntegerNode
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl array_assign // [2] -> [1]
            // END ArrayAssign
            
            // BEGIN Id arr
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id arr
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN ArrayAssign
                // BEGIN IntegerNode
                    MOV x9, #3
                    push x9
                // END IntegerNode
                
                // BEGIN IntegerNode
                    MOV x9, #0
                    push x9
                // END IntegerNode
                
                bl array_assign // [2] -> [1]
            // END ArrayAssign
            
            // BEGIN Id rec
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #48 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id rec
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #0
                push x9
            // END IntegerNode
            
            // BEGIN Id s
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #64 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id s
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
    // END DeclarationList
    
    // BEGIN Sequence
        // BEGIN Affect
            // BEGIN IntegerNode
                MOV x9, #23
                push x9
            // END IntegerNode
            
            // BEGIN ArrayAccess
                // BEGIN Id rec
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id rec
                
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
            // BEGIN IntegerNode
                MOV x9, #45
                push x9
            // END IntegerNode
            
            // BEGIN ArrayAccess
                // BEGIN Id rec
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id rec
                
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
        
        // BEGIN Affect
            // BEGIN Id arr
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
                at // i = *i
            // END Id arr
            
            // BEGIN ArrayAccess
                // BEGIN Id rec
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #48 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id rec
                
                // BEGIN IntegerNode
                    MOV x9, #2
                    push x9
                // END IntegerNode
                
                bl array_access // [2] -> [1]
            // END ArrayAccess
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
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
                        MOV x9, #2
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
                                MOV x9, #2
                                push x9
                            // END IntegerNode
                            
                            bl ari_int_add // [2] -> [1]
                        // END Operateur binaire
                        
                        // BEGIN ArrayAccess
                            // BEGIN ArrayAccess
                                // BEGIN Id rec
                                    push x28/*Ch. STAT*/
                                    mov x0, #1 // depth
                                    push x0
                                    mov x0, #48 // depl
                                    push x0
                                    bl chainage_st // [3] -> [1]
                                    at // i = *i
                                // END Id rec
                                
                                // BEGIN IntegerNode
                                    MOV x9, #2
                                    push x9
                                // END IntegerNode
                                
                                bl array_access // [2] -> [1]
                                at // i = *i
                            // END ArrayAccess
                            
                            // BEGIN Id i
                                push x28/*Ch. STAT*/
                                mov x0, #0 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                                at // i = *i
                            // END Id i
                            
                            bl array_access // [2] -> [1]
                        // END ArrayAccess
                        
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
                // BEGIN ArrayAccess
                    // BEGIN Id rec
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id rec
                    
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
                // BEGIN ArrayAccess
                    // BEGIN Id rec
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id rec
                    
                    // BEGIN IntegerNode
                        MOV x9, #1
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
                // BEGIN StringNode
                    ldr x0, =str_1
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
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
                        MOV x9, #2
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
            
            // BEGIN While3
                b _loop_3
                _loop_3:
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
                beq _end_loop_3
                // BEGIN Sequence
                    // BEGIN Sequence
                        // BEGIN FunctionCall
                            // BEGIN ParameterList
                                // BEGIN ArrayAccess
                                    // BEGIN ArrayAccess
                                        // BEGIN Id rec
                                            push x28/*Ch. STAT*/
                                            mov x0, #1 // depth
                                            push x0
                                            mov x0, #48 // depl
                                            push x0
                                            bl chainage_st // [3] -> [1]
                                            at // i = *i
                                        // END Id rec
                                        
                                        // BEGIN IntegerNode
                                            MOV x9, #2
                                            push x9
                                        // END IntegerNode
                                        
                                        bl array_access // [2] -> [1]
                                        at // i = *i
                                    // END ArrayAccess
                                    
                                    // BEGIN Id i
                                        push x28/*Ch. STAT*/
                                        mov x0, #0 // depth
                                        push x0
                                        mov x0, #32 // depl
                                        push x0
                                        bl chainage_st // [3] -> [1]
                                        at // i = *i
                                    // END Id i
                                    
                                    bl array_access // [2] -> [1]
                                    at // i = *i
                                // END ArrayAccess
                                
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
                    ldr x0, =str_3
                    push x0
                // END StringNode
                
            // END ParameterList
            
            // Fonction de la stdlib (id=print)
            bl print_str // [1] -> [0]
        // END FunctionCall
        
        // BEGIN Affect
            // BEGIN Operateur binaire
                // BEGIN ArrayAccess
                    // BEGIN Id rec
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id rec
                    
                    // BEGIN IntegerNode
                        MOV x9, #0
                        push x9
                    // END IntegerNode
                    
                    bl array_access // [2] -> [1]
                    at // i = *i
                // END ArrayAccess
                
                // BEGIN ArrayAccess
                    // BEGIN Id rec
                        push x28/*Ch. STAT*/
                        mov x0, #0 // depth
                        push x0
                        mov x0, #48 // depl
                        push x0
                        bl chainage_st // [3] -> [1]
                        at // i = *i
                    // END Id rec
                    
                    // BEGIN IntegerNode
                        MOV x9, #1
                        push x9
                    // END IntegerNode
                    
                    bl array_access // [2] -> [1]
                    at // i = *i
                // END ArrayAccess
                
                bl ari_int_add // [2] -> [1]
            // END Operateur binaire
            
            // BEGIN Id s
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #64 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id s
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END Affect
        
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
                        MOV x9, #2
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
            
            // BEGIN While4
                b _loop_4
                _loop_4:
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
                beq _end_loop_4
                // BEGIN Sequence
                    // BEGIN Affect
                        // BEGIN ArrayAccess
                            // BEGIN ArrayAccess
                                // BEGIN Id rec
                                    push x28/*Ch. STAT*/
                                    mov x0, #1 // depth
                                    push x0
                                    mov x0, #48 // depl
                                    push x0
                                    bl chainage_st // [3] -> [1]
                                    at // i = *i
                                // END Id rec
                                
                                // BEGIN IntegerNode
                                    MOV x9, #2
                                    push x9
                                // END IntegerNode
                                
                                bl array_access // [2] -> [1]
                                at // i = *i
                            // END ArrayAccess
                            
                            // BEGIN Id j
                                push x28/*Ch. STAT*/
                                mov x0, #0 // depth
                                push x0
                                mov x0, #32 // depl
                                push x0
                                bl chainage_st // [3] -> [1]
                                at // i = *i
                            // END Id j
                            
                            bl array_access // [2] -> [1]
                            at // i = *i
                        // END ArrayAccess
                        
                        // BEGIN Id s
                            push x28/*Ch. STAT*/
                            mov x0, #1 // depth
                            push x0
                            mov x0, #64 // depl
                            push x0
                            bl chainage_st // [3] -> [1]
                        // END Id s
                        
                        pop x0 // adresse
                        pop x1 // val
                        STR x1, [x0]
                    // END Affect
                    
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
                
                b _loop_4
                _end_loop_4:
            // END While4
            
            // GESTION FIN DU NOUVEAU SCOPE
            mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
            pop x29/*Ch. DYN*/ // Ch. DYN
            pop x28/*Ch. STAT*/ // Ch. STAT
        // END Let
        
        // BEGIN FunctionCall
            // BEGIN ParameterList
                // BEGIN Id s
                    push x28/*Ch. STAT*/
                    mov x0, #0 // depth
                    push x0
                    mov x0, #64 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id s
                
            // END ParameterList
            
            // Fonction de la stdlib (id=printi)
            bl print_int10 // [1] -> [0]
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
    // BEGIN String lit Ln 18, Col 8
        str_0:
         .asciz "\n"
        
    // END String lit Ln 18, Col 8
    
    // BEGIN String lit Ln 20, Col 8
        str_1:
         .asciz "\n"
        
    // END String lit Ln 20, Col 8
    
    // BEGIN String lit Ln 23, Col 10
        str_2:
         .asciz "\n"
        
    // END String lit Ln 23, Col 10
    
    // BEGIN String lit Ln 25, Col 8
        str_3:
         .asciz "la somme des valeur des entiers du record"
        
    // END String lit Ln 25, Col 8
    
// END DATA

