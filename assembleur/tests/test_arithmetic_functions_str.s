.include "../base_macros.s"
// MACROS


// fin MACROS
.section .text
.global _start
_start:
// EXECUTION

    ldr x0, =str1
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str1
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str1
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str1
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str1
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str2
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str2
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str2
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str2
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str2
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str3
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str3
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str3
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str3
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str3
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str4
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str4
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str4
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str4
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str4
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str5
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str5
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str5
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str5
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str5
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_EQ
    bl print_int10
    ldr x0, =str1
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_NE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str2
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str3
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str4
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str5
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LT
    bl print_int10
    ldr x0, =str1
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_GE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str1
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str2
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str3
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str4
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str1
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str2
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str3
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str4
    push x0
    push x1
    bl ari_str_LE
    bl print_int10
    ldr x0, =str5
    ldr x1, =str5
    push x0
    push x1
    bl ari_str_LE
    bl print_int10

// fin EXECUTION
	exit #0
// FUNCTIONS


// fin FUNCTIONS
.include "../arithmetic_functions.s"
.include "../base_functions.s"
// DATA
str1:
    .asciz "abcd\0"
str2:
    .asciz "abcde\0"
str3:
    .asciz "abc\0"
str4:
    .asciz "abce\0"
str5:
    .asciz "abcc\0"

// fin DATA
