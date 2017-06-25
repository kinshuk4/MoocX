#Modify the move function to accommodate the added 
#probabilities of overshooting or undershooting 
#the intended destination.

p=[0, 1, 0, 0, 0]
world=['green', 'red', 'red', 'green', 'green']
measurements = ['red', 'green']
pHit = 0.6
pMiss = 0.2
pExact = 0.8
pOvershoot = 0.1
pUndershoot = 0.1

def sense(p, Z):
    q=[]
    for i in range(len(p)):
        hit = (Z == world[i])
        q.append(p[i] * (hit * pHit + (1-hit) * pMiss))
    s = sum(q)
    for i in range(len(q)):
        q[i] = q[i] / s
    return q

def move_exact(p, U):
    q = []
    for i in range(len(p)):
        q.append(p[(i-U)%len(p)])
    return q

def sum_lists(p1, p2):
    q = []
    for i in range(len(p1)):
        q.append(p1[i] + p2[i])
    return q


def move(p, U):
    pE = [x * pExact for x in move_exact(p, U)]
    pO = [x * pOvershoot for x in move_exact(p, U+1)]
    pU = [x * pUndershoot for x in move_exact(p, U-1)]
    q = sum_lists(sum_lists(pE, pU), pO)
    return q

for i in range(2):
    p = move(p, 1)

print p
print sum(p)



