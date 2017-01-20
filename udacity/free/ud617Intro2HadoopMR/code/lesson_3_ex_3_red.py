#!/usr/bin/python
import sys
def reducer(): 
    total_sales = 0
    total_count = 0
 
    for line in sys.stdin:
        data = line.strip().split("\t")
        if len(data) != 1:
            continue

        total_sales += float(data[0])
        total_count += 1
 
    print "Total sales: ", total_sales, "\tTotal count: ", total_count

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
