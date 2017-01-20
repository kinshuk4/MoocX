#!/usr/bin/python

import sys
import csv
import re
import datetime

def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t', lineterminator='\n', quoting=csv.QUOTE_NONE)
    for line in reader:
        if len(line) != 6:
            continue
        day = line[0]
        sales = line[4]
	day = datetime.datetime.strptime(day, "%Y-%m-%d")
        day_number = datetime.datetime.weekday(day)
        print "{0}\t{1}".format(day_number, sales)
        
       
def main():
    import StringIO
    sys.stdin = sys.__stdin__
    mapper()
    

main()
