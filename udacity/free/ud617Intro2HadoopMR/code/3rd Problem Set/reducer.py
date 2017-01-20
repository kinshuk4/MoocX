#!/usr/bin/env python

import sys
#Find the total sales value across all the stores, and the total number of #sales. Assume there is only one reducer.
#link : https://www.udacity.com/course/viewer#!/c-ud617/l-313947755/# #m-2465068684
sumTotal = 0
count = 0
for line in sys.stdin:
    sum = line.strip()
    count+=1
    sumTotal+=float(sum)

print count,sumTotal 