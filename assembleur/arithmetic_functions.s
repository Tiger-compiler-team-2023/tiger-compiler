/*
    Ce fichier contient les implémentations de différentes fonctions arithmétiques utilisables en assembleur ARM64.
*/

/********** ********** **********
 ********** FONCTIONS  **********
 ********** ********** **********/

ari_int_neg:
    // Push (- arg1/1)
    // {x0, x7}
    // [1] -> [1]
    
    // ARGS [1]
    pop     x0                              // arg1/1

    // Implements ari_int_neg
    mov     x7,     #0
    sub     x7,     x7,     x0
    
    // RES [1]
    push    x7
    ret

ari_int_add:
    // Push (arg1/2 + arg2/2)
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_add
    add     x7,     x0,     x1
    
    // RES [1]
    push    x7
    ret

ari_int_sub:
    // Push (arg1/2 - arg2/2)
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_sub
    sub     x7,     x0,     x1
    
    // RES [1]
    push    x7
    ret

ari_int_mul:
    // Push (arg1/2 * arg2/2)
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_mul
    mul     x7,     x0,     x1
    
    // RES [1]
    push    x7
    ret

ari_int_div:
    // Push (arg1/2 / arg2/2)
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_div
    sdiv    x7,     x0,     x1
    
    // RES [1]
    push    x7
    ret

ari_log_and:
    // Push (arg1/2 & arg2/2)
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_log_and
    /*  Ce qui est vrai ou faux
        On considère que tout ce qui est différent de 0
        est vrai, attention au AND bit à bit qui risque
        de dire que 1&2=0 (01&10=00)                    */
    mov     x7,     #0
    cmp     x0,     #0
    beq     ari_log_and_false
    cmp     x1,     #0
    beq     ari_log_and_false
    mov     x7,     #1
    ari_log_and_false:
    
    // RES [1]
    push    x7
    ret

ari_log_or:
    // Push (arg1/2 | arg2/2)
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_log_or
    /*  Ce qui est vrai ou faux
        On considère que tout ce qui est différent de 0
        est vrai, on implémente OR comme AND pour plus
        de clarté                                       */
    mov     x7,     #1
    cmp     x0,     #0
    bne     ari_log_or_true
    cmp     x1,     #0
    bne     ari_log_or_true
    mov     x7,     #0
    ari_log_or_true:
    
    // RES [1]
    push    x7
    ret

ari_int_EQ:
    // Push (arg1/2 = arg2/2) où arg1/2 et arg2/2 sont des entiers
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_EQ
    mov     x7,     #1
    cmp     x0,     x1
    beq     ari_int_EQ_true
    mov     x7,     #0
    ari_int_EQ_true:
    
    // RES [1]
    push    x7
    ret

ari_int_NE:
    // Push (arg1/2 <> arg2/2) où arg1/2 et arg2/2 sont des entiers
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_NE
    mov     x7,     #1
    cmp     x0,     x1
    bne     ari_int_NE_true
    mov     x7,     #0
    ari_int_NE_true:
    
    // RES [1]
    push    x7
    ret

ari_int_GT:
    // Push (arg1/2 > arg2/2) où arg1/2 et arg2/2 sont des entiers
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_GT
    mov     x7,     #1
    cmp     x0,     x1
    bgt     ari_int_GT_true
    mov     x7,     #0
    ari_int_GT_true:
    
    // RES [1]
    push    x7
    ret

ari_int_LT:
    // Push (arg1/2 < arg2/2) où arg1/2 et arg2/2 sont des entiers
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_LT
    mov     x7,     #1
    cmp     x0,     x1
    blt     ari_int_LT_true
    mov     x7,     #0
    ari_int_LT_true:
    
    // RES [1]
    push    x7
    ret

ari_int_GE:
    // Push (arg1/2 >= arg2/2) où arg1/2 et arg2/2 sont des entiers
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_GE
    mov     x7,     #1
    cmp     x0,     x1
    bge     ari_int_GE_true
    mov     x7,     #0
    ari_int_GE_true:
    
    // RES [1]
    push    x7
    ret

ari_int_LE:
    // Push (arg1/2 <= arg2/2) où arg1/2 et arg2/2 sont des entiers
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements ari_int_LE
    mov     x7,     #1
    cmp     x0,     x1
    ble     ari_int_LE_true
    mov     x7,     #0
    ari_int_LE_true:
    
    // RES [1]
    push    x7
    ret

ari_str_EQ:
    // Push (*arg1/2 = *arg2/2) où arg1/2 et arg2/2 sont des adresses de chaines de caractères
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // STACK -- push
    push1928

    // Implements ari_str_EQ
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x23,    #0                      // Flag -> Le dernier charactère lu est un symbole de fin de str
    mov     x21,    #0                      // i <- 0
    ari_str_EQ_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\0', alors on passe le flag à 1   */
    cmp     w25,    #0
    bne     ari_str_EQ_notdone1
    mov     x23,    #1
    ari_str_EQ_notdone1:
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    add     x21,    x21,    #1              // i++
    /*  Gestions des fins de chaine
        Si arg2[i] = '\0', alors on regarde le flag
        Si le flag est à 1 on renvoie vrai
        Sinon les chaines sont de tailles différentes
        et on renvoie faux                              */
    cmp     w22,    #0
    bne     ari_str_EQ_notdone2
    cmp     x23,    #1
    beq     ari_str_EQ_return_true          // return true  (on a lu tous les caractères)
    b       ari_str_EQ_return_false         // return false (arg2 est plus courte)
    ari_str_EQ_notdone2:
    /*  Gestions des fins de chaine
        Si arg2[i] != '\0', alors on regarde le flag
        Si le flag est à 1 les chaines sont de tailles
        différentes et on renvoie faux
        Sinon on continue                               */
    cmp     x23,    #1
    beq     ari_str_EQ_return_false         // return false (arg1 est plus courte)
    /*  Comparaison caractère par caractère
        Si arg1[i] = arg2[i] alors on boucle
        Sinon on renvoie faux                           */
    cmp     w25,    w22
    bne     ari_str_EQ_return_false         // return false (arg1[i] != arg2[i])
    b       ari_str_EQ_loop
    ari_str_EQ_return_true:
    mov     x7,     #1
    b ari_str_EQ_end
    ari_str_EQ_return_false:
    mov     x7,     #0
    ari_str_EQ_end:
    // STACK -- pop
    pop1928
    
    // RES [1]
    push    x7
    ret

ari_str_NE:
    // Push (*arg1/2 = *arg2/2) où arg1/2 et arg2/2 sont des adresses de chaines de caractères
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // STACK -- push
    push1928

    // Implements ari_str_NE
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x23,    #0                      // Flag -> Le dernier charactère lu est un symbole de fin de str
    mov     x21,    #0                      // i <- 0
    ari_str_NE_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\0', alors on passe le flag à 1   */
    cmp     w25,    #0
    bne     ari_str_NE_notdone1
    mov     x23,    #1
    ari_str_NE_notdone1:
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    add     x21,    x21,    #1              // i++
    /*  Gestions des fins de chaine
        Si arg2[i] = '\0', alors on regarde le flag
        Si le flag est à 1 on renvoie vrai
        Sinon les chaines sont de tailles différentes
        et on renvoie faux                              */
    cmp     w22,    #0
    bne     ari_str_NE_notdone2
    cmp     x23,    #1
    beq     ari_str_NE_return_false         // return false (on a lu tous les caractères)
    b       ari_str_NE_return_true          // return true  (arg2 est plus courte)
    ari_str_NE_notdone2:
    /*  Gestions des fins de chaine
        Si arg2[i] != '\0', alors on regarde le flag
        Si le flag est à 1 les chaines sont de tailles
        différentes et on renvoie faux
        Sinon on continue                               */
    cmp     x23,    #1
    beq     ari_str_NE_return_true          // return true  (arg1 est plus courte)
    /*  Comparaison caractère par caractère
        Si arg1[i] = arg2[i] alors on boucle
        Sinon on renvoie faux                           */
    cmp     w25,    w22
    bne     ari_str_NE_return_true          // return true  (arg1[i] != arg2[i])
    b       ari_str_NE_loop
    ari_str_NE_return_true:
    mov     x7,     #1
    b ari_str_NE_end
    ari_str_NE_return_false:
    mov     x7,     #0
    ari_str_NE_end:
    // STACK -- pop
    pop1928
    
    // RES [1]
    push    x7
    ret

ari_str_GT:
    // Push (*arg1/2 > *arg2/2) où arg1/2 et arg2/2 sont des adresses de chaines de caractères
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // STACK -- push
    push1928

    // Implements ari_str_GT
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x21,    #0                      // i <- 0
    ari_str_GT_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\0', alors arg1 est plus court ou
        égal à arg2 donc on retourne faux               */
    cmp     w25,    #0
    beq     ari_str_GT_return_false         // return false
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    /*  Gestions des fins de chaine
        Si arg2[i] = '\0', alors arg2 est plus court
        que arg1 donc on retourne vrai */
    cmp     w22,    #0
    beq     ari_str_GT_return_true          // return true
    /*  Comparaison caractère par caractère
        Si arg1[i] < arg2[i] alors on renvoie faux
        Si arg1[i] > arg2[i] alors on renvoie vrai
        Sinon on boucle                                 */
    cmp     w25,    w22
    blt     ari_str_GT_return_false         // return false
    bgt     ari_str_GT_return_true          // return true
    add     x21,    x21,    #1              // i++
    b     ari_str_GT_loop
    ari_str_GT_return_true:
    mov     x7,     #1
    b ari_str_GT_end
    ari_str_GT_return_false:
    mov     x7,     #0
    ari_str_GT_end:

    // STACK -- pop
    pop1928
    
    // RES [1]
    push    x7
    ret

ari_str_LT:
    // Push (*arg1/2 < *arg2/2) où arg1/2 et arg2/2 sont des adresses de chaines de caractères
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Uses ari_str_GT
    push    x1
    push    x0
    b       ari_str_GT
    
    ret

ari_str_GE:
    // Push (*arg1/2 >= *arg2/2) où arg1/2 et arg2/2 sont des adresses de chaines de caractères
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // STACK -- push
    push1928

    // Implements ari_str_GE
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x23,    #0                      // Flag -> Le dernier charactère lu est un symbole de fin de str
    mov     x21,    #0                      // i <- 0
    ari_str_GE_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\0', alors on passe le flag à 1   */
    cmp     w25,    #0
    bne     ari_str_GE_notdone1
    mov     x23,    #1
    ari_str_GE_notdone1:
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    add     x21,    x21,    #1              // i++
    /*  Gestions des fins de chaine
        Si arg2[i] = '\0', alors on regarde le flag
        Si le flag est à 1 on renvoie vrai
        Sinon les chaines sont de tailles différentes
        et on renvoie faux                              */
    cmp     w22,    #0
    bne     ari_str_GE_notdone2
    cmp     x23,    #1
    beq     ari_str_GE_return_true          // return true  (on a lu tous les caractères)
    b       ari_str_GE_return_true          // return true  (arg2 est plus courte)
    ari_str_GE_notdone2:
    /*  Gestions des fins de chaine
        Si arg2[i] != '\0', alors on regarde le flag
        Si le flag est à 1 les arg1 est plsu long et on
        renvoie vrai
        Sinon on continue                               */
    cmp     x23,    #1
    beq     ari_str_GE_return_false         // return false (arg1 est plus courte)
    /*  Comparaison caractère par caractère
        Si arg1[i] >= arg2[i] alors on boucle
        Sinon on renvoie faux                           */
    cmp     w25,    w22
    blt     ari_str_GE_return_false         // return false (arg1[i] < arg2[i])
    bgt     ari_str_GE_return_true          // return true  (arg1[i] > arg2[i])
    b       ari_str_GE_loop
    ari_str_GE_return_true:
    mov     x7,     #1
    b ari_str_GE_end
    ari_str_GE_return_false:
    mov     x7,     #0
    ari_str_GE_end:
    // STACK -- pop
    pop1928
    
    // RES [1]
    push    x7
    ret

ari_str_LE:
    // Push (*arg1/2 >= *arg2/2) où arg1/2 et arg2/2 sont des adresses de chaines de caractères
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [2]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Uses ari_str_GE
    push    x1
    push    x0
    b       ari_str_GE
    
    ret
