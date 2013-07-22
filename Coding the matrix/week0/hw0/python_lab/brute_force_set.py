#!/usr/bin/env python
"""
This script is about to find a pair-and-non-overlapping of sets A and B thus that

>>> result = { x * y for x in A for y in B if x != y }

Set result has 5 element
"""
import sys
import itertools

N = {_ for _ in range(-10,11)}

SETS = [set(_) for _ in itertools.combinations(N,3)]

checked_sets = 0

def pick_set():
    global SETS
    [calculate(X, Y) for X in SETS for Y in SETS if not X & Y]

def calculate(set1, set2):
    global checked_sets
    result = { x * y for x in set1 for y in set2 if x != y }
    if len(result) == 5:
        print(set1)
        print(set2)
        sys.exit()

    checked_sets += 1
    sys.stdout.write('Checked %d sets\r' % checked_sets)
    sys.stdout.flush()

if __name__ == "__main__":
    pick_set()
