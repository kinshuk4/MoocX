#!/usr/bin/python

import sys
import csv
import re

def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t', lineterminator='\n', quoting=csv.QUOTE_NONE)
    delimiters = " ", ".", ",", "?", ":", ";", '\"', "(", ")", "<", ">", "[", "]", "#", "$", "#", "%", "=", "-", "/", "!", "\'"
    regexPattern = '|'.join(map(re.escape, delimiters))
    for line in reader:
        if len(line) != 19:
            continue
        body = line[4].lower()
        node = line[0]
        body_proc = re.split(regexPattern, body)
        for word in body_proc:
            if word and not word.isspace():
                print '{0}\t{1}'.format(word, node)
            
       
def main():
    import StringIO
    sys.stdin = sys.__stdin__
    mapper()
    

main()
