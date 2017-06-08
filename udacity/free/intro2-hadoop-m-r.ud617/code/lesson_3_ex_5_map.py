#!/usr/bin/python
import sys

def mapper():
    
    for line in sys.stdin:
        
        data = line.strip().split(" ")

        if len(data) != 10:
            continue

        ip, identity, username, time, time_2, req_1, req_2, req_3, status, size = data
        

        print "{0}".format(ip)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	mapper()
	

main()
