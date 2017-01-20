#!/usr/bin/python
import sys
def reducer():
        
    purchases = []
    oldKey = None
     
    for line in sys.stdin:
        data = line.strip().split("\t")
        if len(data) != 2:
            continue
     
        thisKey, thisSale = data
        if oldKey and oldKey != thisKey:
            print oldKey, "\t", max(purchases)
            oldKey = thisKey
            purchases = []
     
        oldKey = thisKey
        purchases.append(float(thisSale))
     
    if oldKey != None:
         print oldKey, "\t", max(purchases)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()

