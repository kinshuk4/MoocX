#Modify the empty list, p, so that it becomes a UNIFORM probability
#distribution over five grid cells, as expressed in a list of 
#five probabilities.

world_size = 5
p = []
for i in range(0,world_size):
    p.append(1.0/world_size)
print p
