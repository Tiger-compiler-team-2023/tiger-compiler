as test_arithmetic_functions_op.s -o test_arithmetic_functions_op.o
ld test_arithmetic_functions_op.o -o test_arithmetic_functions_op
if [ ! "$(./test_arithmetic_functions_op)" == "$(python3 test_arithmetic_functions_op.py)"  ];
    then
        echo "🔴 Test of arithmetic functions (op) failed."
    else
        echo "✅ Test of arithmetic functions (op) succeeded."
    fi
rm -f test_arithmetic_functions_op.o test_arithmetic_functions_op

as test_arithmetic_functions_log.s -o test_arithmetic_functions_log.o
ld test_arithmetic_functions_log.o -o test_arithmetic_functions_log
if [ ! "$(./test_arithmetic_functions_log)" == "$(python3 test_arithmetic_functions_log.py)"  ];
    then
        echo "🔴 Test of arithmetic functions (log) failed."
    else
        echo "✅ Test of arithmetic functions (log) succeeded."
    fi
rm -f test_arithmetic_functions_log.o test_arithmetic_functions_log

as test_arithmetic_functions_int.s -o test_arithmetic_functions_int.o
ld test_arithmetic_functions_int.o -o test_arithmetic_functions_int
if [ ! "$(./test_arithmetic_functions_int)" == "$(python3 test_arithmetic_functions_int.py)"  ];
    then
        echo "🔴 Test of arithmetic functions (int) failed."
    else
        echo "✅ Test of arithmetic functions (int) succeeded."
    fi
rm -f test_arithmetic_functions_int.o test_arithmetic_functions_int

as test_arithmetic_functions_str.s -o test_arithmetic_functions_str.o
ld test_arithmetic_functions_str.o -o test_arithmetic_functions_str
if [ ! "$(./test_arithmetic_functions_str)" == "$(python3 test_arithmetic_functions_str.py)"  ];
    then
        echo "🔴 Test of arithmetic functions (str) failed."
    else
        echo "✅ Test of arithmetic functions (str) succeeded."
    fi
rm -f test_arithmetic_functions_str.o test_arithmetic_functions_str