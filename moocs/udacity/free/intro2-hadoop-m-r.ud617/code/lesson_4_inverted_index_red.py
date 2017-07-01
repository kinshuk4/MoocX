#!/usr/bin/python
import sys

def reducer():
    old_key = None
    node_list = []
    for line in sys.stdin:
        
        data = line.strip().split("\t")
        if len(data) != 2:
            continue
        this_key, node = data
        if old_key and old_key != this_key:
            print old_key, ": ", sorted(node_list)
            node_list = []
            old_key = this_key
        node_list.append(node.replace("\"", ""))
        old_key = this_key
   
    print old_key, ": ", sorted(node_list)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
