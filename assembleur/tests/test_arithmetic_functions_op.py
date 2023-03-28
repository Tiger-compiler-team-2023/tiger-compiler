
int1 = 1
int2 = 6
int3 = 12
int4 = -6
int5 = -12

def add(a, b) :
    print(a + b)

def sub(a, b) :
    print(a - b)

def mul(a, b) :
    print(a * b)

def div(a, b) :
    print(int(a / b))
    # (-6)//12 = -1
    # int((-6)/12) = 0
    # Or en C (-6)/12 = 0 donc je m'appuie sur le sens informatique plutôt que le sens mathématique

def neg(a) :
    print(-a)

bi = [add, sub, mul, div]
un = [neg]
ints = [int1, int2, int3, int4, int5]

for f in bi :
    for s in ints :
        for s_ in ints :
            f(s, s_)

for f in un :
    for s in ints :
        f(s)