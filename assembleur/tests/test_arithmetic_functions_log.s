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
bl ari_log_and
bl print_int10
mov x0, #1
mov x1, #0
push x0
push x1
bl ari_log_and
bl print_int10
mov x0, #0
mov x1, #1
push x0
push x1
bl ari_log_and
bl print_int10
mov x0, #0
mov x1, #0
push x0
push x1
bl ari_log_and
bl print_int10
mov x0, #1
mov x1, #1
push x0
push x1
bl ari_log_or
bl print_int10
mov x0, #1
mov x1, #0
push x0
push x1
bl ari_log_or
bl print_int10
mov x0, #0
mov x1, #1
push x0
push x1
bl ari_log_or
bl print_int10
mov x0, #0
mov x1, #0
push x0
push x1
bl ari_log_or
bl print_int10

// fin EXECUTION
	exit #0
// FUNCTIONS


// fin FUNCTIONS
.include "../arithmetic_functions.s"
.include "../base_functions.s"
// DATA


// fin DATA
