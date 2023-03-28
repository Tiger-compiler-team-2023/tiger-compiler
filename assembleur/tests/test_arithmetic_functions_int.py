
int1 = 1
int2 = 6
int3 = 12
int4 = -6
int5 = -12

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
ints = [int1, int2, int3, int4, int5]

for f in functions :
    for s in ints :
        for s_ in ints :
            f(s, s_)