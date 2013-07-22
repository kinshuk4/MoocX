# Please fill out this stencil and submit using the provided submission script.





## Problem 1
def myFilter(L, num): return [_ for _ in L if _%num]



## Problem 2
def myLists(L): return [list(range(1, _+1)) for _ in L]



## Problem 3
def myFunctionComposition(f, g): return { fk:gv for (fk, fv) in f.items() for (gk, gv) in g.items() if fv == gk }


## Problem 4
# Please only enter your numerical solution.

complex_addition_a = 5 + 3j
complex_addition_b = 1j
complex_addition_c = -1 + .001j
complex_addition_d = .001 + 9j



## Problem 5
GF2_sum_1 = 1
GF2_sum_2 = 0
GF2_sum_3 = 0


## Problem 6
def mySum(L):
    result = 0
    for x in L:
        result += x

    return result



## Problem 7
def myProduct(L):
    result = 1
    for x in L:
        result *= x

    return result



## Problem 8
def myMin(L):
    result = L[0]
    for x in L:
        if x < result:
            result = x

    return result



## Problem 9
def myConcat(L):
    result = ''
    for x in L:
        result += x
    return result



## Problem 10
def myUnion(L):
    return { __ for _ in L for __ in _}

