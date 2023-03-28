as test_data_functions0.s -o test_data_functions0.o
ld test_data_functions0.o -o test_data_functions0
if [ ! "$(./test_data_functions0)" == "4" ];
    then
        echo "🔴 Test of data functions (no error) failed."
    else
        echo "✅ Test of data functions (no error) succeeded."
    fi
rm -f test_data_functions0.o test_data_functions0

as test_data_functions1.s -o test_data_functions1.o
ld test_data_functions1.o -o test_data_functions1
if [ ! "$(./test_data_functions1 2>&1)" == "$(cat err_35)" ];
    then
        echo "🔴 Test of data functions (index out of range) failed."
    else
        echo "✅ Test of data functions (index out of range) succeeded."
    fi
rm -f test_data_functions1.o test_data_functions1

as test_data_functions2.s -o test_data_functions2.o
ld test_data_functions2.o -o test_data_functions2
if [ ! "$(./test_data_functions2 2>&1)" == "$(cat err_36)" ];
    then
        echo "🔴 Test of data functions (negative size) failed."
    else
        echo "✅ Test of data functions (negative size) succeeded."
    fi
rm -f test_data_functions2.o test_data_functions2

as test_data_functions3.s -o test_data_functions3.o
ld test_data_functions3.o -o test_data_functions3
if [ ! "$(./test_data_functions3 2>&1)" == "$(cat err_36)" ];
    then
        echo "🔴 Test of data functions (negative index) failed."
    else
        echo "✅ Test of data functions (negative index) succeeded."
    fi
rm -f test_data_functions3.o test_data_functions3