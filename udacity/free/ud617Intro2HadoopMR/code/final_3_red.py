#!/usr/bin/python
import sys

def get_max_index(list_a, value):
    if value < min(list_a):
        return 0
    for i in range(0, len(list_a) - 1):
        if list_a[i] <= value < list_a[i + 1]:
            return i + 1
    return len(list_a)


def reducer():

    top_10_words = []
    top_10_count = []

    temp_count = 0

    old_key = None
    
    for line in sys.stdin:

        this_key = line
        if line == '':
            continue
	

        if old_key and old_key != this_key:
            if len(top_10_count) == 0:
                top_10_count.append(temp_count)
                top_10_words.append(old_key)
            else:
                temp_max = max(top_10_count)
                temp_min = min(top_10_count)
                if temp_count >= temp_min or len(top_10_count) <= 10:
                    index = get_max_index(top_10_count, temp_count)
                    top_10_count.insert(index, temp_count)
                    top_10_words.insert(index, old_key)
                    if len(top_10_count) > 10:
                        del top_10_count[0]
                        del top_10_words[0]
            old_key = this_key
            temp_count = 0
        temp_count += 1
        old_key = this_key

    temp_max = max(top_10_count)
    temp_min = min(top_10_count)
    if temp_count >= temp_min or len(top_10_count) <= 10:
        index = get_max_index(top_10_count, temp_count)
        top_10_count.insert(index, temp_count)
        top_10_words.insert(index, old_key)
        if len(top_10_count) > 10:
            del top_10_count[0]
            del top_10_words[0]
    for i in range(0, 10):
        print '{0}\t{1}'.format(top_10_words[i].strip(), top_10_count[i])
           
def main():
	import StringIO
        sys.stdin = sys.__stdin__
	reducer()
	

main()
