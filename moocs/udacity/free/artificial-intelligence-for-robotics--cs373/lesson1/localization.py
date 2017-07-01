#Write a code that outputs p after multiplying each entry 
#by pHit or pMiss at the appropriate places. Remember that
#the red cells 1 and 2 are hits and the other green cells
#are misses


p=[0.2,0.2,0.2,0.2,0.2]
pHit = 0.6
pMiss = 0.2

#Enter code here
hits=[0,1,1,0,0]
for i in range(len(p)):
    if hits[i] == 1:
        p[i] = p[i] * pHit
    else:
        p[i] = p[i] * pMiss 

print p