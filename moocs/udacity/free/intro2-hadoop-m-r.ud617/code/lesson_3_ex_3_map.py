#!/usr/bin/python
import sys

def mapper():
    
    for line in sys.stdin:
        
        data = line.strip().split("\t")

        if len(data) != 6:
            continue

        date, time, store, item, cost, payment = data
        

        print "{0}".format(cost)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	mapper()
	

main()
