.include "../base_macros.s"
// MACROS


// fin MACROS
.section .text
.global _start
_start:
// EXECUTION

mov x0, #1
mov x1, #1
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #1
mov x1, #6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #1
mov x1, #12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #1
mov x1, #-6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #1
mov x1, #-12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #6
mov x1, #1
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #6
mov x1, #6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #6
mov x1, #12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #6
mov x1, #-6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #6
mov x1, #-12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #12
mov x1, #1
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #12
mov x1, #6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #12
mov x1, #12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #12
mov x1, #-6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #12
mov x1, #-12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-6
mov x1, #1
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-6
mov x1, #6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-6
mov x1, #12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-6
mov x1, #-6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-6
mov x1, #-12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-12
mov x1, #1
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-12
mov x1, #6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-12
mov x1, #12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-12
mov x1, #-6
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #-12
mov x1, #-12
push x0
push x1
bl ari_int_add
bl print_int10
mov x0, #1
mov x1, #1
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #1
mov x1, #6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #1
mov x1, #12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #1
mov x1, #-6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #1
mov x1, #-12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #6
mov x1, #1
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #6
mov x1, #6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #6
mov x1, #12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #6
mov x1, #-6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #6
mov x1, #-12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #12
mov x1, #1
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #12
mov x1, #6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #12
mov x1, #12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #12
mov x1, #-6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #12
mov x1, #-12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-6
mov x1, #1
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-6
mov x1, #6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-6
mov x1, #12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-6
mov x1, #-6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-6
mov x1, #-12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-12
mov x1, #1
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-12
mov x1, #6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-12
mov x1, #12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-12
mov x1, #-6
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #-12
mov x1, #-12
push x0
push x1
bl ari_int_sub
bl print_int10
mov x0, #1
mov x1, #1
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #1
mov x1, #6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #1
mov x1, #12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #1
mov x1, #-6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #1
mov x1, #-12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #6
mov x1, #1
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #6
mov x1, #6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #6
mov x1, #12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #6
mov x1, #-6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #6
mov x1, #-12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #12
mov x1, #1
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #12
mov x1, #6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #12
mov x1, #12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #12
mov x1, #-6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #12
mov x1, #-12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-6
mov x1, #1
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-6
mov x1, #6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-6
mov x1, #12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-6
mov x1, #-6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-6
mov x1, #-12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-12
mov x1, #1
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-12
mov x1, #6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-12
mov x1, #12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-12
mov x1, #-6
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #-12
mov x1, #-12
push x0
push x1
bl ari_int_mul
bl print_int10
mov x0, #1
mov x1, #1
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #1
mov x1, #6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #1
mov x1, #12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #1
mov x1, #-6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #1
mov x1, #-12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #6
mov x1, #1
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #6
mov x1, #6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #6
mov x1, #12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #6
mov x1, #-6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #6
mov x1, #-12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #12
mov x1, #1
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #12
mov x1, #6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #12
mov x1, #12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #12
mov x1, #-6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #12
mov x1, #-12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-6
mov x1, #1
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-6
mov x1, #6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-6
mov x1, #12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-6
mov x1, #-6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-6
mov x1, #-12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-12
mov x1, #1
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-12
mov x1, #6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-12
mov x1, #12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-12
mov x1, #-6
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #-12
mov x1, #-12
push x0
push x1
bl ari_int_div
bl print_int10
mov x0, #1
push x0
bl ari_int_neg
bl print_int10
mov x0, #6
push x0
bl ari_int_neg
bl print_int10
mov x0, #12
push x0
bl ari_int_neg
bl print_int10
mov x0, #-6
push x0
bl ari_int_neg
bl print_int10
mov x0, #-12
push x0
bl ari_int_neg
bl print_int10

// fin EXECUTION
	exit #0
// FUNCTIONS


// fin FUNCTIONS
.include "../arithmetic_functions.s"
.include "../base_functions.s"
// DATA


// fin DATA
