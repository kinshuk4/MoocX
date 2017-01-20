#!/usr/bin/python


import sys

def reducer():
    for line in sys.stdin:
        line_list = line.split("\t")
        if line_list[1] == "A":
            user_pointer, reputation, gold, silver, bronze = line_list
        if line_list[1] == "B":
            uid, b, title, tagnames, author_id, node_type, parent_id, abs_parent_id, added_at = line_list
            print "{0}\t{1}\t{2}\t{3}\t{4}\t{5}\t{6}\t{7}\t{8}\t{9}\t{10}\t{11}\t{12}".format(uid, title, tagnames, author_id, nodeYpde, parent_id, abs_parent_id, added_at, score, reputation, gold, silver, bronze)
            
        