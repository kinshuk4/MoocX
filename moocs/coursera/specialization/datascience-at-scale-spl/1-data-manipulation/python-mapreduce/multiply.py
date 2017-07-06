# -*- coding: utf-8 -*-
import MapReduce
import json 
import sys


max_dim = 5
mr = MapReduce.MapReduce()

def mapper(record):
    global max_dim
    key = str(record[1])+''+str(record[2]) # like  11,23 etc.. where 11,23 are matrix positions
    value = record[0]+''+str(record[3]) #like A34, B3 etc.. where 34,3 are values
    if record[0] == 'a':
        for i in range(max_dim):
            mr.emit_intermediate(str(record[1])+''+str(i), record)

    elif record[0] == 'b':
        for i in range(max_dim):
            mr.emit_intermediate(str(i)+''+str(record[2]),record)


def reducer(key, list_of_values):
    global max_dim
    lst = []
    #a x b
    res = 0
    temp = 0
    #print key," calculation:"
    #print key,list_of_values
    for term_1 in list_of_values:
        for term_2 in list_of_values:
            if term_1[0]=='a':
                if term_1[2]==term_2[1] and term_2[0]=='b':
                    #print "multiplying ",term_1[-1],term_2[-1]
                    temp = term_1[-1]*term_2[-1]
                    res += temp
                    #print "so far",res
    mr.emit((int(list(key)[0]),int(list(key)[1]),res))



inputdata = open(sys.argv[1])
#inputdata = open("matrix.json")

mr.execute(inputdata, mapper, reducer)


"""

A                   B
63 45 93 32 49      63 18 89 28 39
33 0  0  26 95      59 76 34 12 06
25 11 0  60 89      30 52 49 03 95
24 79 24 47 18      77 75 85 0  0 
07 98 96 27 0       0  46 33 69 88

no. of keys = no. of elements in the resultant matrix (if 3x3 then keys are 00,01,02.....30,31,33)
for each key, the value is a list of all the elements needed to obtain that result number
example:
if the above AxB, and if we are trying to position 2,3 in the resultant matrix, the for the key 23,
the values will be [[25],[11],[60],[89],[28],[12],[03],[69]]
so, the calculation is: 25*28 + 11*12 + 89*60 =  ans


A           B
11 12 13    11 12 13    
21 22 23    21 22 23
31 32 33    31 32 33


"""