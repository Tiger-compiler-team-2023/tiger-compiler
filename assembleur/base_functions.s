/*
    Ce fichier contient les implémentations de différentes fonctions utilisables en assembleur ARM64.
*/

/********** ********** **********
 ********** FONCTIONS  **********
 ********** ********** **********/

print_int16:
    // Affiche arg1/1 en hexadéciaml sur la sortie standard
    // {x0, x1, x2, x7}
    // [1] -> [0]
    
    // ARGS [1]
    pop     x0                              // arg1/1

    // STACK -- push
    push1928

    // Implements print_int16
    mov     x19,    x0
    ldr     x1,     =print_int16_buffer
    ldr     x20,    =hexa_digits
    mov     x21,     #2
    print_int16_loop:
    cmp     x21,    #22
    bge     print_int16_endloop
    /*  Gestions des espaces dans le buffer
        print_int_buffer -> "0x xxxx xxxx xxxx xxxx\n"
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
    mov     x2,     #23
    print

    // STACK -- pop
    pop1928
    
    // RES [0]
    ret

print_int10:
    // Affiche arg1/1 en décimal sur la sortie standard
    // {x0, x1, x2, x7}
    // [1] -> [0]
    
    // ARGS [1]
    pop     x0                              // arg1/1

    // STACK -- push
    push1928

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
    sub     x24,    x24,    #1
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
    mov     x2,     x25
    print

    // STACK -- pop
    pop1928
    
    // RES [0]
    ret

input_int10:
    // Lit un entier sur l'entrée standard (arrête la lecture au premier non-chiffre) et le push sur la pile
    // {x0, x1, x2}
    // [0] -> [1]
    
    // ARGS [0]

    // STACK -- push
    push1928

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
    pop1928

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
    exit    #0x20
    malloc_end:

    // RES [1]
    push    x2                              // Renvoie l'adresse du début de l'espace
    ret

/********** ********** **********
 **********    DATA    **********
 ********** ********** **********/

.section .data
hexa_digits:
    .asciz  "0123456789abcdef"
print_int16_buffer:
    .asciz  "0x xxxx xxxx xxxx xxxx\n"
print_int10_buffer:
    .asciz  "xx xxx xxx xxx xxx xxx xxx\n"
input_int10_buffer:
    .fill   32,     1,      0
