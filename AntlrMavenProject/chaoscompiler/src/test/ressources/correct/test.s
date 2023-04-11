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
        // BEGIN VariableDeclaration
            // BEGIN IntegerNode
                MOV x9, #5
                push x9
            // END IntegerNode
            
            // BEGIN Id N
                push x28/*Ch. STAT*/
                mov x0, #0 // depth
                push x0
                mov x0, #32 // depl
                push x0
                bl chainage_st // [3] -> [1]
            // END Id N
            
            pop x0 // adresse
            pop x1 // val
            STR x1, [x0]
        // END VariableDeclaration
        
    // END DeclarationList
    
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
                // BEGIN Id N
                    push x28/*Ch. STAT*/
                    mov x0, #1 // depth
                    push x0
                    mov x0, #32 // depl
                    push x0
                    bl chainage_st // [3] -> [1]
                    at // i = *i
                // END Id N
                
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
        
        // BEGIN While1
            b _loop_1
            _loop_1:
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
            beq _end_loop_1
            // BEGIN Sequence
                // BEGIN FunctionCall
                    // BEGIN ParameterList
                        // BEGIN Id i
                            push x28/*Ch. STAT*/
                            mov x0, #0 // depth
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
            
            b _loop_1
            _end_loop_1:
        // END While1
        
        // GESTION FIN DU NOUVEAU SCOPE
        mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
        pop x29/*Ch. DYN*/ // Ch. DYN
        pop x28/*Ch. STAT*/ // Ch. STAT
    // END Let
    
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
// END DATA

