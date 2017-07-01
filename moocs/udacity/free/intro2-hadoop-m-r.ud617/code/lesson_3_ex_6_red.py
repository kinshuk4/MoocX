#!/usr/bin/python
import sys

def reducer():
    max_count = 0
    this_count = 0
    current_max = ""
    old_key = None

    for line in sys.stdin:
        data = line.strip().split(" ")
        if len(data) != 1:
            continue
        this_key = data[0]
        if old_key and old_key != this_key:
            if this_count >= max_count:
                max_count = this_count
                current_max = old_key
	
            this_count = 0
            old_key = this_key
        this_count += 1
        old_key = this_key
    if this_count >= max_count:
        max_count = this_count
        current_max = old_key
    print "{0}\t{1}".format(current_max, max_count)

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
