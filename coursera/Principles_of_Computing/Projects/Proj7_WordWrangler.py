"""
Project: Word Wrangler game
Course: Principles of Computing 2 (Rice University)
Author: James Lamb (GUI provided by professors)
Date: October 23, 2015
"""

import urllib2
import codeskulptor
import poc_wrangler_provided as provided

WORDFILE = "assets_scrabble_words3.txt"

# Functions to manipulate ordered word lists

def remove_duplicates(list1):
    """
    Eliminate duplicates in a sorted list.

    Returns a new sorted list with the same elements in list1, but
    with no duplicates.

    This function can be iterative.
    """
    tmp_lst = []
    for item in list1:
        if not item in tmp_lst:
            tmp_lst.append(item)
    return tmp_lst

def intersect(list1, list2):
    """
    Compute the intersection of two sorted lists.

    Returns a new sorted list containing only elements that are in
    both list1 and list2.

    This function can be iterative.
    """
    
    # "Parallel Walk" recommended by Andrew Raibeck on the class forums
    lst = []
    indx1 = 0
    indx2 = 0
    while indx1 != len(list1) and indx2 != len(list2):
        if list1[indx1] == list2[indx2]:
            lst.append(list1[indx1])
            indx1 += 1
            indx2 += 1
        else:
            if list1[indx1] < list2[indx2]:
                indx1 += 1
            else:
                indx2 += 1
        
    return lst

# Functions to perform merge sort

def merge(list1, list2):
    """
    Merge two sorted lists.

    Returns a new sorted list containing all of the elements that
    are in either list1 and list2.

    This function can be iterative.
    """   
    tmp1 = list(list1)
    tmp2 = list(list2)
    m_lst = []
    while len(tmp1) != 0 and len(tmp2) !=0:
        if tmp1[0] < tmp2[0]:
            m_lst.append(tmp1.pop(0))
        else:
            m_lst.append(tmp2.pop(0))
    m_lst = m_lst + tmp1 + tmp2
    return m_lst
                
def merge_sort(list1):
    """
    Sort the elements of list1.

    Return a new sorted list with the same elements as list1.

    This function should be recursive.
    """
    
    # Split the list
    tmp1 = list1[:(len(list1)/2)]
    tmp2 = list1[(len(list1)/2):]
    
    # Sort these sublists
    if len(tmp1) > 1:
        tmp1 = merge_sort(tmp1)
    if len(tmp2) > 1:
        tmp2 = merge_sort(tmp2)

    return merge(tmp1, tmp2)

# Function to generate all strings for the word wrangler game

def gen_all_strings(word):
    """
    Generate all strings that can be composed from the letters in word
    in any order.

    Returns a list of all strings that can be formed from the letters
    in word.

    This function should be recursive.
    """
    # Helper reference (http://stackoverflow.com/questions/21660346/python-recursion-and-list)
    
    if len(word) == 0: # base case: list is empty
        lst = ['']
    #elif len(word) == 1: #base case: one word
    #    lst = [word]
    else:
        lst = []
        
        # Split into first character and rest
        first = word[0]
        rest = word[1:]
        
        # gen all strings from rest
        rest_strings = gen_all_strings(rest)
        
        # add first everywhere you can
        if len(rest) > 0:
            for strng in rest_strings:
                #lst.append(strng)
                for dummy_i in range(len(strng)+1):
                    if dummy_i == 0:
                        nxt = list(first) + list(strng)
                    else:
                        nxt_l = list(strng)
                        nxt = nxt_l[:(dummy_i)] + list(first) + nxt_l[(dummy_i):]
                    
                    nxt_str = "".join(nxt)
                    lst.append(nxt_str)
        else:
            lst.append(first)
        lst = lst + rest_strings

    return lst

# Function to load words from a file

def load_words(filename):
    """
    Load word list from the file named filename.

    Returns a list of strings.
    """
    myfile = urllib2.urlopen(codeskulptor.file2url(filename)).read()
    mywords = myfile.split("\n")
    return mywords

def run():
    """
    Run game.
    """
    words = load_words(WORDFILE)
    wrangler = provided.WordWrangler(words, remove_duplicates, 
                                     intersect, merge_sort, 
                                     gen_all_strings)
    provided.run_game(wrangler)

# Uncomment when you are ready to try the game
run()

    
    
