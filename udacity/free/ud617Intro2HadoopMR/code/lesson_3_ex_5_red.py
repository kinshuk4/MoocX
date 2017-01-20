#!/usr/bin/python
import sys

def reducer():
    ip_count = 0
    old_key = None

    for line in sys.stdin:
        data = line.strip().split("\t")
        if len(data) != 1:
            continue
        this_key = data[0]
        if old_key and old_key != this_key:
	    print "{0}\t{1}".format(old_key, ip_count)
            ip_count = 0
            old_key = this_key
        ip_count += 1
        old_key = this_key

    print "{0}\t{1}".format(old_key, ip_count)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
