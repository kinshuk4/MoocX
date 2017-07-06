'''
Consider an instance of the optimal binary search tree problem with 7 keys (say 1,2,3,4,5,6,7 in sorted order) and frequencies w1=.05,w2=.4,w3=.08,w4=.04,w5=.1,w6=.1,w7=.23. What is the minimum-possible average search time of a binary search tree with these keys?
'''

import numpy as np

# calculate the average search time of bianry search tree
def calOBS(w):
    n = len(w)
    m = np.zeros([n,n])
    for s in np.arange(n):
        for i in np.arange(n-s):
            temp = np.zeros(s+1)
            for j in np.arange(s+1):
                r = i+j
                up = min([n-1,i+s])
                temp[j] = sum(w[i:up+1])
                if i<=r-1:
                    temp[j] += m[i,r-1]
                if i+s<n and r+1<= i+s:
                    temp[j] += m[r+1,i+s]
            m[i,i+s]=min(temp)
    return m[0,-1]


if __name__ == "__main__":
    weight = [0.2,0.05,0.17,0.1,0.2,0.03,0.25]
    print calOBS(weight)