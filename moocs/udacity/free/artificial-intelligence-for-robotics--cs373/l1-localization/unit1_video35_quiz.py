# Before asking neighbour our house (world) has two possible states and their probabilities
world = ['no_burn', 'burn']
p = [0.999, 0.001]

# Likelihood that our sensor is correct (ie that neighbour does not lie)
pHit = 0.9
pMiss = 0.1

# Updating probabilities based on response
def sense(p, Z):
    q=[]
    for i in range(len(p)):
        hit = (Z == world[i])
        q.append(p[i] * (hit * pHit + (1-hit) * pMiss))
    # Output not-yet-normalized probability
    print q
    # Normalize and return
    s = sum(q)
    for i in range(len(q)):
        q[i] = q[i] / s
    return q

# Ouput normalized probability based on response
print sense(p, 'burn')
