#!/usr/bin/python
import sys

def mapper():

    group = []

    old_key = None
    
    for line in sys.stdin:
        
        data = line.strip().split("\t")

        if len(data) != 2:
            continue

        this_key = data[0][:-2]
        user = data[1]

        if old_key and old_key != this_key:
            group_string = ', '.join(group).replace('\"', "")
            print "Node: ", old_key,", Users: ", group_string
            old_key = this_key
            group = []
            
        group.append(user)
	old_key = this_key

    group_string = ', '.join(group).replace('\"', "")
    print "Node: ", old_key,", Users: ", group_string

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	mapper()
	

main()
