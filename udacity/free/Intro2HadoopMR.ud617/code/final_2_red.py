#!/usr/bin/python
import sys

def reducer():

    question_length = "NULL"
    answer_length = 0
    answer_count = 0

    old_key = None
    
    for line in sys.stdin:
        
        data = line.strip().split("\t")

        if len(data) != 2:
            continue

        this_key = data[0][:-2]
	status = data[0][-1]
        length = int(data[1])

        if old_key and old_key != this_key:
            if answer_count == 0:
                average = "NULL"
            else:
                average = float(answer_length) / answer_count
            print "Node: ", old_key,", question length: ", question_length, ", average answer length: ", average
            old_key = this_key
            question_length = "NULL"
            answer_length = 0
            answer_count = 0
        if status == 'a':
            question_length = length
        if status == 'b':
	    answer_length += length
            answer_count += 1 
	old_key = this_key

    if answer_count == 0:
        average = "NULL"
    else:
        average = float(answer_length) / answer_count
    print "Node: ", old_key,", question length: ", question_length, ", average answer length: ", average

def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
