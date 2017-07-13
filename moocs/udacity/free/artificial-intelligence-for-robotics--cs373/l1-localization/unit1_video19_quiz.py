#Program a function that returns a new distribution 
#q, shifted to the right by U units. If U=0, q should 
#be the same as p.

p=[0.2, 0.2, 0.2, 0.2, 0.2]
world=['green', 'red', 'red', 'green', 'green']
measurements = ['red', 'green']
pHit = 0.6
pMiss = 0.2

def sense(p, Z):
    q=[]
    for i in range(len(p)):
        hit = (Z == world[i])
        q.append(p[i] * (hit * pHit + (1-hit) * pMiss))
    s = sum(q)
    for i in range(len(q)):
        q[i] = q[i] / s
    return q

def move(p, U):
    U = U % len(p) * -1
    return p[U:] + p[:U]

def sum_lists(p1, p2):
    q = []
    for i in range(len(p1)):
        q.append(p1[i] + p2[i])
    return q
    

i1 = [x * 0.1 for x in move(p, 1)]
i2 = [x * 0.8 for x in move(p, 2)]
i3 = [x * 0.1 for x in move(p, 3)]

print i1
print i2
print i3

q = sum_lists(sum_lists(i1,i2), i3)

print q
print sum(q)
