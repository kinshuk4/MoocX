w = [0.6, 1.2, 2.4, 0.6, 1.2]
sw = sum(w)
W = [] # normalized weights
for i in range(len(w)):
    W.append(w[i]/sw) 

print W
print sum(W)
