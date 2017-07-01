
# probabilites from q6 (the grader says incorrect for some reason)
p=[0.7142857142857143, 0.28571428571428575, 0.0, 0.0]
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