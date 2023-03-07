.include    "../base_macros.s"
// MACROS


// fin MACROS
.section    .text
.global     _start
_start:
// EXECUTION

    mov     x0,     #5
    mov     x1,     #4
    push    x0
    push    x1
    bl      array_assign
    pop     x20     // Non volatile address of the array
    mov     x0,     #5
    push    x20
    push    x0
    bl      array_access
    pop     x0
    LDR     x0,     [x0]
    push    x0
    bl      print_int10

// fin EXECUTION
	exit    #0
// FUNCTIONS


// fin FUNCTIONS
.include    "../arithmetic_functions.s"
.include    "../data_functions.s"
.include    "../base_functions.s"
// DATA


// fin DATA
