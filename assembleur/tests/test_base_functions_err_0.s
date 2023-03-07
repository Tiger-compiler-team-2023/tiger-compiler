.include    "../base_macros.s"
// MACROS


// fin MACROS
.section    .text
.global     _start
_start:
// EXECUTION

    err     #0

// fin EXECUTION
	exit    #0
// FUNCTIONS


// fin FUNCTIONS
.include    "../arithmetic_functions.s"
.include    "../data_functions.s"
.include    "../base_functions.s"
// DATA


// fin DATA
