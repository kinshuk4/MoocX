#Write a program that will find the initial number
#of coconuts. 

def f(n):
    return (n-1) / 5 * 4

def f6(n):
    for i in range(6):
        n = f(n)
    return n 

def is_int(n):
    return abs(n-int(n)) < 0.0000001

for i in range(10000,100000):
    left = f6(float(i))
    if is_int(left):
        n = i
        break

print n
