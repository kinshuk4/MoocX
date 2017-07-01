#Write a code that outputs p after multiplying each entry 
#by pHit or pMiss at the appropriate places. Remember that
#the red cells 1 and 2 are hits and the other green cells
#are misses


p=[0.2,0.2,0.2,0.2,0.2]
pHit = 0.6
pMiss = 0.2

sense = [pMiss, pHit, pHit, pMiss, pMiss]
post = []
for i in range(len(sense)):
    post.append(p[i] * sense[i])
p = post

print sum(p)
