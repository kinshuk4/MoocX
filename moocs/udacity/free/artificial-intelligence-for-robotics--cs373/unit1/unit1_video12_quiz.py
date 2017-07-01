#Try using your code with a measurement of 'green' and 
#make sure the resulting probability distribution is correct.


p=[0.2, 0.2, 0.2, 0.2, 0.2]
world=['green', 'red', 'red', 'green', 'green']
Z = 'green'
pHit = 0.6
pMiss = 0.2

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