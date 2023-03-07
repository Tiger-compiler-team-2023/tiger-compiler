.include "base_macros.s"
// MACROS


// fin MACROS
.section .text
.global _start
_start:
// EXECUTION

ldr     x1,     =hw
mov     x2,     hw_
print

// fin EXECUTION
	exit #0
// FUNCTIONS


// fin FUNCTIONS
.include "arithmetic_functions.s"
.include "base_functions.s"
// DATA

hw:
    .asciz "Hello, world!\n"
hw_ = . - hw

// fin DATA
