.include "base_macros.s"
// MACROS


// fin MACROS
.section .text
.global _start
_start:
// EXECUTION

ldr     x1,     =hw
mov     x2,     #14
print

exit    #0

// fin EXECUTION
	exit #0
// FUNCTIONS


// fin FUNCTIONS
.include "arithmetic_functions.s"
.include "base_functions.s"
// DATA

hw:
    .asciz "Hello, world!\n"

// fin DATA
