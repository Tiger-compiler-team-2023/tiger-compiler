
log1 = True
log2 = False

def print01(r) :
    if r :
        print(1)
    else :
        print(0)

def et(a, b) :
    print01(a and b)

def ou(a, b) :
    print01(a or b)

functions = [et, ou]
logs = [log1, log2]

for f in functions :
    for s in logs :
        for s_ in logs :
            f(s, s_)