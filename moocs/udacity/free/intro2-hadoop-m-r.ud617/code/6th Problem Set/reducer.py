#!/usr/bin/env python

import sys

#Find the total number of hits to the page /assets/js/the-associates.js.
#link : https://www.udacity.com/course/viewer#!/c-ud617/l-313947755/# #m-2465068684
count = 0
x={}
for line in sys.stdin:
    path = line.strip().split()
    if len(path) != 1 or len(path[0])<2:
        # Something has gone wrong. Skip this line.
        continue
	
    if path[0] in x:
	x[path[0]]  = x[path[0]]+1
    else:
        x[path[0]] = 1
z= sorted(x.items(),key=lambda y:y[1],reverse=True)
print z[0]