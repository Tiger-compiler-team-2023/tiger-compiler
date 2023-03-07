/*
Ce fichier contient les implémentations de différentes fonctions de gestions d'objets en mémoire utilisables en assembleur ARM64.
*/

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
