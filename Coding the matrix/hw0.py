# Please fill out this stencil and submit using the provided submission script.





## Problem 1
def myFilter(L, num):
    return [i for i in L if i % num != 0]



## Problem 2
def myLists(L):
    return [[j+1 for j in range(i)] for i in L]



## Problem 3
def myFunctionComposition(f, g):
    return {i : g[f[i]] for i in f.keys()}


## Problem 4
# Please only enter your numerical solution.

complex_addition_a = (5 + 3j)
complex_addition_b = (0 + 1j)
complex_addition_c = (-1 + 0.001j)
complex_addition_d = (0.001 + 9j)



## Problem 5
GF2_sum_1 = 1
GF2_sum_2 = 0
GF2_sum_3 = 0


## Problem 6
def mySum(L):
    sumka = 0
    for i in L:
        sumka += i
    return sumka



## Problem 7
def myProduct(L):
    sumka = 1
    for i in L:
        sumka *= i
    return sumka



## Problem 8
def myMin(L):
    mini = L[0]
    for i in L:
        if i < mini:
            mini = i 
    return mini

## Problem 9
def myConcat(L):
    sumka = ""
    for i in L:
        sumka += i
    return sumka


## Problem 10
def myUnion(L):
    union = set()
    for i in L:
        union = union | i
    return union

