
str1 = "abcd\0"
str2 = "abcde\0"
str3 = "abc\0"
str4 = "abce\0"
str5 = "abcc\0"

def print01(r) :
    if r :
        print(1)
    else :
        print(0)

def eq(a, b) :
    print01(a == b)

def ne(a, b) :
    print01(a != b)

def gt(a, b) :
    print01(a > b)

def lt(a, b) :
    print01(a < b)

def ge(a, b) :
    print01(a >= b)

def le(a, b) :
    print01(a <= b)

functions = [eq, ne, gt, lt, ge, le]
strs = [str1, str2, str3, str4, str5]

for f in functions :
    for s in strs :
        for s_ in strs :
            f(s, s_)