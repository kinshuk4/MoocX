#!/usr/bin/python
import sys
import csv

def zero_pad(str, x):
    while len(str) < x:
        str = '0' + str
    return str

def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t', lineterminator='\n', quoting=csv.QUOTE_NONE)
    next(reader, None)
    for line in reader:

        if len(line) != 19:
            continue
        

        node_id = line[0]
        status = line[5]
	parent_id = line[6]
        user = line[3]

        if 'question' in status:
            node_id = (zero_pad(node_id, 11) + '_a').replace("\"", "")
            
	else:
            node_id = (zero_pad(parent_id, 11) + '_b').replace("\"", "")
            
	   
        print '{0}\t{1}'.format(node_id, user)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	mapper()
	

main()
