.include "../base_macros.s"
// MACROS


// fin MACROS
.section .text
.global _start
_start:
// EXECUTION

    mov x0, #0
    mov x1, #0
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #0
    mov x1, #6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #0
    mov x1, #12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #0
    mov x1, #-6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #0
    mov x1, #-12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #6
    mov x1, #0
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #6
    mov x1, #6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #6
    mov x1, #12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #12
    mov x1, #0
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #12
    mov x1, #6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #12
    mov x1, #12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-6
    mov x1, #0
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-6
    mov x1, #6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-6
    mov x1, #12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-12
    mov x1, #0
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-12
    mov x1, #6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-12
    mov x1, #12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #-12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_EQ
    bl print_int10
    mov x0, #0
    mov x1, #0
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #0
    mov x1, #6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #0
    mov x1, #12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #0
    mov x1, #-6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #0
    mov x1, #-12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #6
    mov x1, #0
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #6
    mov x1, #6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #6
    mov x1, #12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #12
    mov x1, #0
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #12
    mov x1, #6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #12
    mov x1, #12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-6
    mov x1, #0
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-6
    mov x1, #6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-6
    mov x1, #12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-12
    mov x1, #0
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-12
    mov x1, #6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-12
    mov x1, #12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #-12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_NE
    bl print_int10
    mov x0, #0
    mov x1, #0
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #0
    mov x1, #6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #0
    mov x1, #12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #0
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #0
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #6
    mov x1, #0
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #6
    mov x1, #6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #6
    mov x1, #12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #12
    mov x1, #0
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #12
    mov x1, #6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #12
    mov x1, #12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-6
    mov x1, #0
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-6
    mov x1, #6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-6
    mov x1, #12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-12
    mov x1, #0
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-12
    mov x1, #6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-12
    mov x1, #12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #-12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GT
    bl print_int10
    mov x0, #0
    mov x1, #0
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #0
    mov x1, #6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #0
    mov x1, #12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #0
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #0
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #6
    mov x1, #0
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #6
    mov x1, #6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #6
    mov x1, #12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #12
    mov x1, #0
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #12
    mov x1, #6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #12
    mov x1, #12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-6
    mov x1, #0
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-6
    mov x1, #6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-6
    mov x1, #12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-12
    mov x1, #0
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-12
    mov x1, #6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-12
    mov x1, #12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #-12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LT
    bl print_int10
    mov x0, #0
    mov x1, #0
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #0
    mov x1, #6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #0
    mov x1, #12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #0
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #0
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #6
    mov x1, #0
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #6
    mov x1, #6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #6
    mov x1, #12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #12
    mov x1, #0
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #12
    mov x1, #6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #12
    mov x1, #12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-6
    mov x1, #0
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-6
    mov x1, #6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-6
    mov x1, #12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-12
    mov x1, #0
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-12
    mov x1, #6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-12
    mov x1, #12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #-12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_GE
    bl print_int10
    mov x0, #0
    mov x1, #0
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #0
    mov x1, #6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #0
    mov x1, #12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #0
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #0
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #6
    mov x1, #0
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #6
    mov x1, #6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #6
    mov x1, #12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #12
    mov x1, #0
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #12
    mov x1, #6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #12
    mov x1, #12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-6
    mov x1, #0
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-6
    mov x1, #6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-6
    mov x1, #12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-6
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-6
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-12
    mov x1, #0
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-12
    mov x1, #6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-12
    mov x1, #12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-12
    mov x1, #-6
    push x0
    push x1
    bl ari_int_LE
    bl print_int10
    mov x0, #-12
    mov x1, #-12
    push x0
    push x1
    bl ari_int_LE
    bl print_int10

// fin EXECUTION
	exit #0
// FUNCTIONS


// fin FUNCTIONS
.include "../arithmetic_functions.s"
.include "../base_functions.s"
// DATA


// fin DATA
