#!/usr/bin/python
import sys

def reducer():
    total = 0
    count = 0
    for line in sys.stdin:
        
        data = line.strip().split("\t")
        if len(data) != 2:
            continue
        day, sale = data
        if int(day) == 6:
            total += float(sale)
            count += 1
        
    ave_sales = total/count
    print "Average Sunday sales: ", ave_sales

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
