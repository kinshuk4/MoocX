#!/usr/bin/env python
#Find the total sales value across all the stores, and the total number of #sales. Assume there is only one reducer.
#link : https://www.udacity.com/course/viewer#!/c-ud617/l-313947755/# #m-2465068684
import sys

for line in sys.stdin:
    data = line.strip().split("\t")
    if len(data) == 6:
        date, time, store, item, cost, payment = data
        print cost