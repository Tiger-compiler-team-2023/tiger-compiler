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
        // BEGIN FunctionDeclaration
        // END FunctionDeclaration
        
    // END DeclarationList
    
    // BEGIN FunctionCall
        // GESTION DU NOUVEAU SCOPE
        push x28/*Ch. STAT*/
        push x29/*Ch. DYN*/
        mov x29/*Ch. DYN*/, SP/*STACK*/ // Ch. DYN
        // BEGIN Id f
            push x28/*Ch. STAT*/
            mov x0, #0 // depth
            push x0
            mov x0, #0 // depl
            push x0
            bl chainage_st // [3] -> [1]
        // END Id f
        
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
    
    // GESTION FIN DU NOUVEAU SCOPE
    pop x7 // RES
    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
    pop x29/*Ch. DYN*/ // Ch. DYN
    pop x28/*Ch. STAT*/ // Ch. STAT
    push x7 // RES
// END Let
// fin EXECUTION
    exit #0
// BEGIN FUNCTIONS
    // BEGIN Function f
        function_6:
        push x30/*@retour*/ // @retour
        // BEGIN IfThenElse1
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
            beq _else_1
            bne _then_1
            _else_1:
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
                    
                    bl function_6
                    // GESTION FIN DU NOUVEAU SCOPE
                    pop x7 // RES
                    mov SP/*STACK*/, x29/*Ch. DYN*/ // Ch. DYN
                    pop x29/*Ch. DYN*/ // Ch. DYN
                    pop x28/*Ch. STAT*/ // Ch. STAT
                    push x7 // RES
                // END FunctionCall
                
                bl ari_int_mul // [2] -> [1]
            // END Operateur binaire
            
            b _end_ifthenelse_1
            _then_1:
            // BEGIN IntegerNode
                MOV x9, #1
                push x9
            // END IntegerNode
            
            b _end_ifthenelse_1
            _end_ifthenelse_1:
        // END IfThenElse1
        
        pop x7 // RES
        pop x30/*@retour*/ // @retour
        push x7 // RES
        ret
    // END Function f
    
// END FUNCTIONS
    .include "assembleur/arithmetic_functions.s"
    .include "assembleur/data_functions.s"
    .include "assembleur/base_functions.s"
// BEGIN DATA
// END DATA

