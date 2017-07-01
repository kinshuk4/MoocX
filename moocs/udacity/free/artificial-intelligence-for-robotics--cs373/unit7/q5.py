

p=[0.25, 0.25, 0.25, 0.25]
world=['green', 'green', 'red', 'green']
Z = 'red'
pMiss = 0.2
pHit = 1.0 - pMiss

def sense(p, Z):
    q=[]
    for i in range(len(p)):
        hit = (Z == world[i])
        q.append(p[i] * (hit * pHit + (1-hit) * pMiss))
    qsum = sum(q)
    for i in range(len(q)):
        q[i] = q[i] / qsum
    return q
print sense(p,Z)
print sum(sense(p,Z))