#!/usr/bin/python
import sys
import csv
import re

def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t', lineterminator='\n', quoting=csv.QUOTE_NONE)
    next(reader, None)
    for line in reader:

        if len(line) != 19:
            continue
        

        status = line[5]
        if 'question' in status:
            continue
        
        tags = ' '.join(line[2].split()).replace('\"', "").split(" ")
	for tag in tags:
            
            if len(tag) > 1:
                print '{0}'.format(tag)
                x = 0
        
def main():
	import StringIO
        sys.stdin = sys.__stdin__
	mapper()
	

main()
