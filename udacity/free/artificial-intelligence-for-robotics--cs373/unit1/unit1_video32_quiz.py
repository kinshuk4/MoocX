
pC = 0.001
pNC = 1.0-pC

world = ['cancer', 'no_cancer']
p = [pC, pNC]

pHit = 0.8
pMiss = 0.1


def sense(p, Z):
    q=[]
    for i in range(len(p)):
        hit = (Z == world[i])
        q.append(p[i] * (hit * pHit + (1-hit) * pMiss))
    s = sum(q)
    for i in range(len(q)):
        q[i] = q[i] / s
    return q


print sense(p, 'cancer')
