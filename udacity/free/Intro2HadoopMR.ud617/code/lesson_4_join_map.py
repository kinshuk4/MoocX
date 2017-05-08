#!/usr/bin/python

import sys
import csv

def mapper():
    reader = csv.reader(sys.stdin, delimiter='\t')
    writer = csv.writer(sys.stdout, delimiter='\t', quotechar='"', quoting=csv.QUOTE_ALL)

    for line in reader:

        if len(line) == 5:
            user_pointer, reputation, gold, silver, bronze = line
            print "{0}\tA\t{1}\t{2}\t{3}\t{4}"format(user_pointer, reputation, gold, silver, bronze)
        if len(line) == 19:
            id, title, tagnames, author_id, body, node_type, parent_id, abs_parent_id, added_at, score, state_string, last_edited_id, last_activity_by_id, last_activity_at, active_revision_id, extra, extra_ref_id, extra_count, marked = line
            print "{0}\tB\t{1}\t{2}\t{3}\t{4}\t{5}\t{6}\t{7}".format(id, title, tagnames, author_id, node_type, parent_id, abs_parent_id, added_at)

        