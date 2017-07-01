#!/usr/bin/python

import sys
import csv


def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t')
    writer = csv.writer(sys.stdout, delimiter='\t', quotechar='"', quoting=csv.QUOTE_ALL)
    top_10 = []
    for line in reader:
        body = line[4]
        if len(top_10) == 0:
            top_10.append(line)
            top_10.insert(0, "")
            continue
        if len(top_10) > 10 or top_10[0] == "":
            top_10.pop(0)
        top_10.insert(0, line)
        top_10.sort(key = lambda x: len(x[4]))
    top_10 = top_10[(len(top_10) - 10):(len(top_10))] 
    for i in range(len(top_10)):
        temp_line = top_10[i]
        st = temp_line[4]
        print "\"\"\t\"\"\t\"\"\t\"\"\t\""+st+"\"\t\"\""
            
       
test_text = """\"\"\t\"\"\t\"\"\t\"\"\t\"333\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"88888888\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"1\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"11111\n111111\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"1000000000\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"22\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"4444\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"666666\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"55555\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"999999999\"\t\"\"
\"\"\t\"\"\t\"\"\t\"\"\t\"7777777\"\t\"\"
"""

def main():
    import StringIO
    sys.stdin = StringIO.StringIO(test_text)
    mapper()
    sys.stdin = sys.__stdin__

main()
