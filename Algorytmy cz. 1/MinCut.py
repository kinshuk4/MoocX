'''graph'''
from random import *

def minCut(data):
    if len(data) == 2:
        for i in data.keys():
            return len(data[i])
    else:
        i1 = choice([i for i in data.keys()])
        i2 = choice(data[i1])
        data[i1] = data[i1] + data[i2]
        for i in data[i2]:
            for j in data[i].copy():
                if j == i2:
                    data[i].remove(j)
                    data[i].append(i1)
        for i in data[i1].copy():
            if i == i1:
                data[i1].remove(i)
        del data[i2]
        return minCut(data)

mini = 10000
for i in range(0, 100):   #here is the problem, because dictionaries are not 'deepcopyable'   
    seed(i)
    print(i)
    data = {}   #so every time I want to invoke minCut I have to read my data from file 
    try:
        file = open("E:\\coursera\\algo part1\\program\\kargerMinCut.txt")
        for line in file:
            temp = line.split()
            for i in range(len(temp[1:])):
                temp[i + 1] = int(temp[i + 1])
            data[int(temp[0])] = temp[1:]
    finally:
        file.close()
    temp = minCut(data)
    print(temp)
    if temp < mini:
        mini = temp
print(mini)

#TODO: better version

