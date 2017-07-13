
q = [0.6,0.6,2.4,0.6]
qsum = sum(q)
for i in range(len(q)):
    q[i] = q[i] / qsum 
    
print q

