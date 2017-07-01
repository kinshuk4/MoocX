#!/usr/bin/python
import sys
import csv
def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t', lineterminator='\n', quoting=csv.QUOTE_NONE)
    next(reader, None)
    for line in reader:

        if len(line) != 19:
            continue
        

        user = line[3]
	date_added = line[8][11:14]
        

        print "{0}\t{1}".format(user, date_added)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	mapper()
	

main()
