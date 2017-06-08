#!/usr/bin/python
import sys

def reducer():

    old_key = None
    total = 0
    
    for line in sys.stdin:
        
        data = line.strip().split("\t")
        if len(data) != 2:
            continue
	this_key, sale = data
	if old_key and old_key != this_key:
	    print "{0}\t{1}".format(old_key, total)
            total = 0
            old_key = this_key
        total += float(sale)
        old_key = this_key
    print "{0}\t{1}".format(old_key, total)
def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
