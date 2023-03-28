as test_base_functions_err_0.s -o test_base_functions_err_0.o
ld test_base_functions_err_0.o -o test_base_functions_err_0
if [ ! "$(./test_base_functions_err_0 2>&1)" == "$(cat err_0)" ];
    then
        echo "🔴 Test of base functions (err 0) failed."
    else
        echo "✅ Test of base functions (err 0) succeeded."
    fi
rm -f test_base_functions_err_0.o test_base_functions_err_0

as test_base_functions_err_1.s -o test_base_functions_err_1.o
ld test_base_functions_err_1.o -o test_base_functions_err_1
if [ ! "$(./test_base_functions_err_1 2>&1)" == "$(cat err_1)" ];
    then
        echo "🔴 Test of base functions (err 1) failed."
    else
        echo "✅ Test of base functions (err 1) succeeded."
    fi
rm -f test_base_functions_err_1.o test_base_functions_err_1

as test_base_functions_err_32.s -o test_base_functions_err_32.o
ld test_base_functions_err_32.o -o test_base_functions_err_32
if [ ! "$(./test_base_functions_err_32 2>&1)" == "$(cat err_32)" ];
    then
        echo "🔴 Test of base functions (err 32) failed."
    else
        echo "✅ Test of base functions (err 32) succeeded."
    fi
rm -f test_base_functions_err_32.o test_base_functions_err_32

as test_base_functions_err_33.s -o test_base_functions_err_33.o
ld test_base_functions_err_33.o -o test_base_functions_err_33
if [ ! "$(./test_base_functions_err_33 2>&1)" == "$(cat err_33)" ];
    then
        echo "🔴 Test of base functions (err 33) failed."
    else
        echo "✅ Test of base functions (err 33) succeeded."
    fi
rm -f test_base_functions_err_33.o test_base_functions_err_33

as test_base_functions_err_34.s -o test_base_functions_err_34.o
ld test_base_functions_err_34.o -o test_base_functions_err_34
if [ ! "$(./test_base_functions_err_34 2>&1)" == "$(cat err_34)" ];
    then
        echo "🔴 Test of base functions (err 34) failed."
    else
        echo "✅ Test of base functions (err 34) succeeded."
    fi
rm -f test_base_functions_err_34.o test_base_functions_err_34

as test_base_functions_err_35.s -o test_base_functions_err_35.o
ld test_base_functions_err_35.o -o test_base_functions_err_35
if [ ! "$(./test_base_functions_err_35 2>&1)" == "$(cat err_35)" ];
    then
        echo "🔴 Test of base functions (err 35) failed."
    else
        echo "✅ Test of base functions (err 35) succeeded."
    fi
rm -f test_base_functions_err_35.o test_base_functions_err_35

as test_base_functions_err_36.s -o test_base_functions_err_36.o
ld test_base_functions_err_36.o -o test_base_functions_err_36
if [ ! "$(./test_base_functions_err_36 2>&1)" == "$(cat err_36)" ];
    then
        echo "🔴 Test of base functions (err 36) failed."
    else
        echo "✅ Test of base functions (err 36) succeeded."
    fi
rm -f test_base_functions_err_36.o test_base_functions_err_36