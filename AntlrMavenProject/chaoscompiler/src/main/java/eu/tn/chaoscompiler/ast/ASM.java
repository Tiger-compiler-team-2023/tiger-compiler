package eu.tn.chaoscompiler.ast;

public class ASM {
    static public String base_macros = """
/********** ********** **********
 **********   MACROS   **********
********** ********** **********/

.macro exit code
    // Exit le programme avec un code de retour
    // {x0, x8}
    mov     x0,     \\code
    mov     x8,     #93
    svc     #0
.endm

.macro push Xn
    // Empile reg
    // {}
    STR     \\Xn,   [SP, -16]!
.endm

.macro pop Xn
    // Depile reg
    // {Xn}
    LDR     \\Xn,   [SP], 16
.endm

.macro at
    // Xn <- *Xn
    // {x1}
    pop     x1
    LDR     x1,     [x1]
    push    x1
.endm

.macro push915
    // push tous les registres qui doivent être enregistrés par l'appelant
    // {}
    push    x9
    push    x10
    push    x11
    push    x12
    push    x13
    push    x14
    push    x15
.endm

.macro pop915
    // pop tous les registres qui doivent être enregistrés par l'appelant
    // {x9, x10, x11, x12, x13, x14, x15}
    pop     x15
    pop     x14
    pop     x13
    pop     x12
    pop     x11
    pop     x10
    pop     x9
.endm

.macro push1927
    // push tous les registres qui doivent être enregistrés par l'appelé
    // {}
    push    x19
    push    x20
    push    x21
    push    x22
    push    x23
    push    x24
    push    x25
    push    x26
    push    x27
.endm

.macro pop1927
    // pop tous les registres qui doivent être enregistrés par l'appelé
    // {x19, x20, x21, x22, x23, x24, x25, x26, x27}
    pop     x27
    pop     x26
    pop     x25
    pop     x24
    pop     x23
    pop     x22
    pop     x21
    pop     x20
    pop     x19
.endm

.macro mod Xa, Xb, Xc
    // Xa <- Xb % Xc
    // {x7}
    sdiv    x7,     \\Xb,    \\Xc
    mul     x7,     x7,     \\Xc
    sub     \\Xa,    \\Xb,    x7
.endm

.macro print
    // Ecrit sur la sortie standard
    // x1 -> addresse de la str
    // x2 -> taille de la str
    // {x0, x8}
    sub     x2,     x2,     #1              // La taille comprend EOF
    mov     x0,     #1
    mov     x8,     #64
    svc     #0
.endm

.macro input
    // Lit sur l'entree standard
    // x1 -> addresse de la str
    // x2 -> taille max de la str
    // {x0, x8}
    // x0 <- taille lue
    mov     x0,     #0
    mov     x8,     #63
    svc     #0
.endm

.macro err code
    // Quitte le programme avec une erreur
    // {x0, x1, x2, x7}
    mov     x0,     \\code
    push    x0
    b       error
.endm

            """;

    static public String arithmetic_functions = """
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
    /*  Gestion des erreurs
        On vérifie qu'on ne divise pas par 0            */
    cmp     x1,     #0
    bne     ari_int_div_notzero
    err     #33
    ari_int_div_notzero:
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
    push1927

    // Implements ari_str_EQ
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x23,    #0                      // Flag -> Le dernier charactère lu est un symbole de fin de str
    mov     x21,    #0                      // i <- 0
    ari_str_EQ_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\\0', alors on passe le flag à 1   */
    cmp     w25,    #0
    bne     ari_str_EQ_notdone1
    mov     x23,    #1
    ari_str_EQ_notdone1:
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    add     x21,    x21,    #1              // i++
    /*  Gestions des fins de chaine
        Si arg2[i] = '\\0', alors on regarde le flag
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
        Si arg2[i] != '\\0', alors on regarde le flag
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
    pop1927
    
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
    push1927

    // Implements ari_str_NE
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x23,    #0                      // Flag -> Le dernier charactère lu est un symbole de fin de str
    mov     x21,    #0                      // i <- 0
    ari_str_NE_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\\0', alors on passe le flag à 1   */
    cmp     w25,    #0
    bne     ari_str_NE_notdone1
    mov     x23,    #1
    ari_str_NE_notdone1:
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    add     x21,    x21,    #1              // i++
    /*  Gestions des fins de chaine
        Si arg2[i] = '\\0', alors on regarde le flag
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
        Si arg2[i] != '\\0', alors on regarde le flag
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
    pop1927
    
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
    push1927

    // Implements ari_str_GT
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x21,    #0                      // i <- 0
    ari_str_GT_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\\0', alors arg1 est plus court ou
        égal à arg2 donc on retourne faux               */
    cmp     w25,    #0
    beq     ari_str_GT_return_false         // return false
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    /*  Gestions des fins de chaine
        Si arg2[i] = '\\0', alors arg2 est plus court
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
    pop1927
    
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
    push1927

    // Implements ari_str_GE
    mov     x19,    x0                      // Adresse de arg1
    mov     x20,    x1                      // Adresse de arg2
    mov     x23,    #0                      // Flag -> Le dernier charactère lu est un symbole de fin de str
    mov     x21,    #0                      // i <- 0
    ari_str_GE_loop:
    ldrb    w25,    [x19, x21]              // Charge arg1[i]
    /*  Gestions des fins de chaine
        Si arg1[i] = '\\0', alors on passe le flag à 1   */
    cmp     w25,    #0
    bne     ari_str_GE_notdone1
    mov     x23,    #1
    ari_str_GE_notdone1:
    ldrb    w22,    [x20, x21]              // Charge arg2[i]
    add     x21,    x21,    #1              // i++
    /*  Gestions des fins de chaine
        Si arg2[i] = '\\0', alors on regarde le flag
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
        Si arg2[i] != '\\0', alors on regarde le flag
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
    pop1927
    
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

            """;;

    static public String data_functions = """
/********** ********** **********
 ********** FONCTIONS  **********
    ********** ********** **********/

array_assign:
    // Push l'adresse du tableau de taille arg1/2 dont tous les éléments sont arg2/2
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [1]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // STACK -- push
    push1927
    // RET-PC -- push
    push    x30

    // Implements array_assign
    mov     x24,    x0
    mov     x25,    x1
    /*  Vérification
        On vérifie que la taille est > 0.               */
    cmp     x24,    #0
    bge     array_assign_no_err0
    err     #36                             // negative size or index
    array_assign_no_err0:
    /*  Taille à allouer
        On commence notre structure par 8 octets pour
        stocker la taille de l'objet et prévoir les 
        index out ot range. Chaque élément de notre
        structure fait 64 bits soient 8 octets.         */
    mov     x19,    #8
    mul     x19,    x19,    x25
    add     x19,    x19,    #8
    /*  Allocation
        On alloue la mémoire pour la structure.
        On récupère l'adresse dans x26.                 */
    push    x19
    bl      malloc
    pop     x26
    /*  Ecriture de la taille
        La case d'indice 0 contient la taille de la
        structure (la place prise par la taille n'est
        pas comprise dans la taille).                   */
    str     x24,     [x26]
    /*  Remplissage de la structure
        On boucle pour remplir la structure.            */
    mov     x20,    #8
    array_assign_loop:
    cmp     x20,    x19
    bge     array_assign_end
    str     x25,     [x26,   x20]
    add     x20,    x20,    #8
    b       array_assign_loop
    array_assign_end:
    /*  Valeur de retour                                */
    mov     x7,     x26

    // RET-PC -- pop
    pop    x30
    // STACK -- pop
    pop1927

    // RES [1]
    push    x7
    ret

array_access:
    // Push l'adresse de la case du tableau d'adresse arg1/2 à l'indice arg2/2 après vérification de l'existence
    // {x0, x1, x7}
    // [2] -> [1]
    
    // ARGS [1]
    pop     x1                              // arg2/2
    pop     x0                              // arg1/2

    // Implements array_access
    /*  Vérification
        On vérifie que l'indice est dans [0, size[.     */
    cmp     x1,     #0
    bge     array_access_no_err0
    err     #36                             // negative size or index
    array_access_no_err0:
    LDR     x7,     [x0]
    cmp     x1,     x7
    blt     array_access_no_err1
    err     #35                             // index out of range
    array_access_no_err1:
    /*  Accès
        On récupère la valeur en mémoire.               */
    add     x0,     x0,     #8
    mov     x7,     #8
    mul     x1,     x1,     x7
    add     x7,     x0,     x1

    // RES [1]
    push    x7
    ret

chainage_st:
    // Application du chainage statique (arg1/3 @CS, arg2/3 #scopes, arg3/3 depl)
    // {x0, x1, x7}
    // [3] -> [1]
    
    // ARGS [1]
    pop     x0                              // arg3/3
    pop     x1                              // arg2/3
    pop     x7                              // arg1/3

    // Implements chainage_st
    chainage_st_loop:
    cmp     x1,     #0
    beq     chainage_st_loop_end
    LDR     x7,     [x7,    #-16]
    sub     x1,     x1,     #1
    b       chainage_st_loop
    chainage_st_loop_end:

    SUB     x0,     x7,     x0

    // RES [1]
    push    x0
    ret

str_len:
    // Push le nombre d'octet jusqu'à l'octet nul en partant de où arg1/1
    // {x0, x1, x7}
    // [1] -> [1]
    
    // ARGS [2]
    pop     x0                              // arg1/1

    // Implements str_len
    mov     x7,     #0
    str_len_loop:
    ldrb    w1,     [x0, x7]
    add     x7,     x7,     #1
    cmp     w1,     #0
    bne     str_len_loop
    
    // RES [1]
    push    x7
    ret
        
            """;;

    static public String base_functions = """
/********** ********** **********
 ********** FONCTIONS  **********
 ********** ********** **********/
print_str:
    // Affiche arg1/1 sur la sortie standard
    // {x0, x1, x2, x7}
    // [1] -> [0]
    pop     x0

    // Implements print_str
    push    x30
    push    x0
    push    x0
    bl      str_len
    pop     x2
    pop     x1
    print
    pop     x30

    // RES [0]
    ret

print_int16:
    // Affiche arg1/1 en hexadéciaml sur la sortie standard
    // {x0, x1, x2, x7}
    // [1] -> [0]
    
    // ARGS [1]
    pop     x0                              // arg1/1

    // STACK -- push
    push1927

    // Implements print_int16
    mov     x19,    x0
    ldr     x1,     =print_int16_buffer
    ldr     x20,    =hexa_digits
    mov     x21,     #2
    print_int16_loop:
    cmp     x21,    #22
    bge     print_int16_endloop
    /*  Gestions des espaces dans le buffer
        print_int_buffer -> "0x xxxx xxxx xxxx xxxx\\n"
        espace si i%5=2 ->     ^~2  ^~7  ^~12 ^~17      */
    mov     x22,    #5
    mod     x22,    x21,    x22
    cmp     x22,    #2
    bne     print_int16_no_sep
    add     x21,    x21,    #1
    print_int16_no_sep:
    lsr     x23,     x19,    #60            // Ne garde que les 4 bits de gauche
    ldrb    w22,    [x20, x23]              // Charge le chiffre associé à la valeur
    strb    w22,    [x1, x21]               // Ecrit le chiffre dans le buffer
    add     x21,    x21,    #1              // Incrémente l'indice d'écriture dans le buffer
    lsl     x19,    x19,    #4              // Efface les 4 bits de gauche
    b       print_int16_loop
    print_int16_endloop:
    mov     x2,     #24
    print

    // STACK -- pop
    pop1927
    
    // RES [0]
    ret

print_int10:
    // Affiche arg1/1 en décimal sur la sortie standard
    // {x0, x1, x2, x7}
    // [1] -> [0]
    
    // ARGS [1]
    pop     x0                              // arg1/1

    // STACK -- push
    push1927

    // Implements print_int16
    mov     x19,    x0
    ldr     x24,    =print_int10_buffer
    add     x24,    x24,    #25
    mov     x25,    #1
    mov     x20,    #0
    /*  Gestions des nombres négatifs
        On utilise x20 comme flag et on remplace le
        nombre par son opposé                           */
    cmp     x19,    #0
    bge     print_int10_getz
    mov     x20,    #1
    mov     x21,    #0
    sub     x19,    x21,    x19
    print_int10_getz:
    /*  Gestions des nombres nulles
        On écrit 0 et on quitte                         */
    cmp     x19,    #0
    bne     print_int10_loop
    mov     w22,    #'0'
    strb    w22,    [x24],  #-1
    add     x25,    x25,    #1
    b       print_int10_endloop
    print_int10_loop:
    /*  Gestions des espaces dans le buffer
        On ajoute des espace tous les 3+1=4 caractères 
        en partant de la droite                         */
    mov     x21,    #4
    mod     x21,    x25,    x21
    cmp     x21,    #0
    bne     print_int10_nospace
    mov     w22, #' '
    strb    w22,    [x24],  #-1
    add     x25,    x25,    #1
    print_int10_nospace:
    mov     x21,    #10                     // Besoin d'un registre pour mod
    mod     x22,    x19,    x21             // Obtention de l'unité
    add     w22,    w22,    #'0'            // Conversion en char
    strb    w22,    [x24],  #-1             // Ecriture du char et decalage
    add     x25,    x25,    #1              // Incr taille du buffer à afficher
    mov     x21,    #10                     // Besoin d'un registre pour sdiv
    sdiv    x19,    x19,    x21             // Decr valeur a afficher
    cmp     x19,    #0
    bne     print_int10_loop
    print_int10_endloop:
    /*  Gestions des nombres négatifs
        On utilise x20 comme flag et on ajoute le
        symbole '-' à gauche                            */
    cmp     x20,    #1
    bne     print_int10_gtz
    mov     w22, #'-'
    strb    w22,    [x24],  #-1
    add     x25,    x25,    #1
    print_int10_gtz:
    
    add     x1,     x24,    #1
    add     x2,     x25,    #1
    print

    // STACK -- pop
    pop1927
    
    // RES [0]
    ret

input_int10:
    // Lit un entier sur l'entrée standard (arrête la lecture au premier non-chiffre) et le push sur la pile
    // {x0, x1, x2}
    // [0] -> [1]
    
    // ARGS [0]

    // STACK -- push
    push1927

    // Implements print_int16
    ldr     x1,     =input_int10_buffer
    mov     x2,     32
    input

    mov     x19,    x1
    mov     x24,    x0
    mov     x21,    #0
    mov     x23,    #0
    ldrb    w22,    [x19, x5]
    /*  Gestions des nombres négatifs
        On utilise x20 comme flag quand on trouve le
        symbole '-' à gauche                            */
    mov     x20,    #0
    cmp     w22,    #'-'
    bne     input_int10_loop
    mov     x20,    #1
    add     x23,    x23,    #1
    input_int10_loop:
    /*  Gestions des caractères non-chiffre
        On quitte                                       */
    cmp     x23, x24
    bge     input_int10_endloop
    ldrb    w22,    [x19, x23]
    cmp     w22,    #'0'
    blo     input_int10_endloop
    cmp     w22,    #'9'
    bhi     input_int10_endloop
    /*  Gestions des caractères chiffre                 */
    sub     w22,    w22,    #'0'            // On soustrait '0' au caractère pour avoir sa valeur
    mov     x25,    #10                     // Besoin d'un registre pour mul
    mul     x21,    x21,    x25             // On multiplie le nombre actuel par 10
    sxtw    x22,    w22                     // Conversion de la valeur sur 32 bits en valeur sur 64 bits
    add     x21,    x21,    x22             // Ajout du chiffre lu au nombre
    add     x23,    x23,    #1              // Incr indice de lecture du buffer
    b       input_int10_loop
    input_int10_endloop:
    /*  Gestions des nombres négatifs
        On utilise x20 comme flag et on remplace le
        nombre par son opposé                           */
    cmp     x20,    #1
    bne     input_int10_end
    mov     x24,    #0
    sub     x21,    x24,    x21
    input_int10_end:

    mov     x0,     x21

    // STACK -- pop
    pop1927

    // RES [1]
    push    x0
    ret

malloc:
    // Réserve un espace mémoire sur le tas, en cas d'erreur, exit le processus avec un statut 32 (0x20)
    // {x0, x1, x2, x3}
    // [1] -> [1]
    
    // ARGS [1]
    pop     x0                              // arg1/1

    // Implements malloc
    mov     x1,     x0                      // Copie la taille attendue dans x1
    /*  Premier appel
        On cherche l'adresse actuelle du tas            */
    mov     x0,     #0x0
    mov     x8,     #0xd6
    svc     #0x0
    mov     x2,     x0                      // Copie de l'adresse actuelle dans x2
    /*  Second appel
        On essaie de réserver la mémoire à partir de
        cette adresse                                   */
    add     x0,     x0,     x1              // Calcul de l'adresse suivante
    mov     x8,     #0xd6
    svc     #0x0
    /*  Verification
        On vérifie que la taille allouée est la bonne   */
    sub     x3,     x0,     x2
    cmp     x3,     x1
    beq     malloc_end
    err     #32
    malloc_end:

    // RES [1]
    push    x2                              // Renvoie l'adresse du début de l'espace
    ret

error:
    // Quitte le programme avec un statut d'erreur après avoir affiché un message d'erreur adapté
    // {x7}
    // [1] -> [0]
    
    // ARGS [1]
    pop     x7                              // arg1/1

    // Implements error
    cmp     x7,     #32                     // malloc
    bne     err_not_32
    ldr     x1,     =err_32
    mov     x2,     len_err_32
    b       err_end
    err_not_32:
    cmp     x7,     #33                     // 0 div
    bne     err_not_33
    ldr     x1,     =err_33
    mov     x2,     len_err_33
    b       err_end
    err_not_33:
    cmp     x7,     #34                     // arithmetic overflow
    bne     err_not_34
    ldr     x1,     =err_34
    mov     x2,     len_err_34
    b       err_end
    err_not_34:
    cmp     x7,     #35                     // index out of range
    bne     err_not_35
    ldr     x1,     =err_35
    mov     x2,     len_err_35
    b       err_end
    err_not_35:
    cmp     x7,     #36                     // negative size or index
    bne     err_not_36
    ldr     x1,     =err_36
    mov     x2,     len_err_36
    b       err_end
    err_not_36:
    cmp     x7,     #0                      // no error
    bne     err_not_0
    ldr     x1,     =err_0
    mov     x2,     len_err_0
    b       err_end
    err_not_0:
    /*  Erreur inconnue
        A ce point, l'erreur n'est pas connue           */
    ldr     x1,     =err_UK
    mov     x2,     len_err_UK
    err_end:
    sub     x2,     x2,     #1              // La taille comprend EOF
    mov     x0,     #2
    mov     x8,     #64
    svc     #0
    exit    x7

    // Le processus a été quitté à ce point

/********** ********** **********
 **********    DATA    **********
 ********** ********** **********/

.section .data
hexa_digits:
    .asciz  "0123456789abcdef"
print_int16_buffer:
    .asciz  "0x xxxx xxxx xxxx xxxx\\n"
print_int10_buffer:
    .asciz  "xx xxx xxx xxx xxx xxx xxx "
input_int10_buffer:
    .fill   32,     1,      0
err_0:
    .asciz  "\\033[1;31mError:\\033[0m Called `error` function with status 0.\\n"
len_err_0 = . - err_0
err_32:
    .asciz  "\\033[1;31mError:\\033[0m Cannot malloc.\\n"
len_err_32 = . - err_32
err_33:
    .asciz  "\\033[1;31mError:\\033[0m Zero division.\\n"
len_err_33 = . - err_33
err_34:
    .asciz  "\\033[1;31mError:\\033[0m Arithmetic overflow.\\n"
len_err_34 = . - err_34
err_35:
    .asciz  "\\033[1;31mError:\\033[0m Index out of range.\\n"
len_err_35 = . - err_35
err_36:
    .asciz  "\\033[1;31mError:\\033[0m Negative size or index.\\n"
len_err_36 = . - err_36
err_UK:
    .asciz  "\\033[1;31mError:\\033[0m Unknown error (use `echo $?` to see status).\\n"
len_err_UK = . - err_UK

            """;;

}
