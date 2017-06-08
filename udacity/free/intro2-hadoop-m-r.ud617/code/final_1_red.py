#!/usr/bin/python
import sys

def return_max(list_a):
    results = []
    max_element = max(list_a)
    for i in range(len(list_a)):
        if list_a[i] == max_element:
            results.append(i)
    return results


def reducer():
    old_key = None
    hours_list = [0]*24
    
    for line in sys.stdin:
        
        data = line.strip().split("\t")

        if len(data) != 2:
            continue

        this_key = data[0]
	date_added = data[1]

        if old_key and old_key != this_key:
            results = return_max(hours_list)
            print "Student: ", old_key,", hours: ", results
            old_key = this_key
            hours_list = [0]*24
        hours_list[int(date_added)] += 1 
	old_key = this_key
    print "Student: ", old_key,", hours: ", results

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
