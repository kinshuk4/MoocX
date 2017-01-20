#!/usr/bin/env python

import sys

oldKey = None

# Loop around the data
# It will be in the format key\tval
# Where key is the store name, val is the sale amount
#
# Find the monetary value for the highest individual sale for each separate # store.
#link : https://www.udacity.com/course/viewer#!/c-ud617/l-313947755/# #m-2465068684


mx = 0
for line in sys.stdin:
    data_mapped = line.strip().split("\t")
    if len(data_mapped) != 2:
        # Something has gone wrong. Skip this line.
        continue

    thisKey, thisSale = data_mapped

    if oldKey and oldKey != thisKey:
        print oldKey, "\t", mx
	mx = 0
        oldKey = thisKey;
    oldKey = thisKey

    if float(thisSale)>mx:
	mx = float(thisSale)

if oldKey != None:
    print oldKey, "\t", mx