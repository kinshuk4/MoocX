#!/usr/bin/env python

import sys

#Find the total number of hits to the page /assets/js/the-associates.js.
#link : https://www.udacity.com/course/viewer#!/c-ud617/l-313947755/# #m-2465068684
count = 0
for line in sys.stdin:
    data_mapped = line.strip().split()
    if len(data_mapped) != 1:
        # Something has gone wrong. Skip this line.
        continue
    if '/assets/js/the-associates.js' in data_mapped:
	count+=1
print count