p = [0, .5, 0, .5, 0]
U = 2

def circular(size, i):
    return i if i < size else i - size

def inexact(p, U):
    q = [0, 0, 0, 0, 0]

    size = len(p)

    for i in range(len(p)):
        q[circular(size, i+U-1)] += .1 * p[i]
        q[circular(size, i+U)] += .8 * p[i]
        q[circular(size, i+U+1)] += .1 * p[i]
    return q

print inexact(p,U) 