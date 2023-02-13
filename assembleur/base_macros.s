/*
    Ce fichier contient les implémentations de différentes macros utilisables en assembleur ARM64.
*/

/********** ********** **********
 **********   MACROS   **********
 ********** ********** **********/

.macro exit code
    // Exit le programme avec un code de retour
    // {x0, x8}
    mov     x0,     \code
    mov     x8,     #93
    svc     #0
.endm

.macro push Xn
    // Empile reg
    // {}
    STR     \Xn,   [SP, #-16]!
.endm

.macro pop Xn
    // Depile reg
    // {Xn}
    LDR     \Xn,   [SP], 16
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

.macro push1928
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
    push    x28
.endm

.macro pop1928
    // pop tous les registres qui doivent être enregistrés par l'appelé
    // {x19, x20, x21, x22, x23, x24, x25, x26, x27, x28}
    pop     x28
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
    sdiv    x7,     \Xb,    \Xc
    mul     x7,     x7,     \Xc
    sub     \Xa,    \Xb,    x7
.endm

.macro print
    // Ecrit sur la sortie standard
    // x1 -> addresse de la str
    // x2 -> taille de la str
    // {x0, x8}
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
