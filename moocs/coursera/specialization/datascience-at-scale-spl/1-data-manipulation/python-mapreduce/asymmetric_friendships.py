# -*- coding: utf-8 -*-
import MapReduce
import json 
import sys



def mapper(record):
    key = record[0]
    value = record[1]
    #we need to consider (a,b) and (b,a) as a same key
    mr.emit_intermediate(hash(key)+hash(value),record)


def reducer(key, list_of_values):
	if len(list_of_values)==1:
		#if the list contains only one pair, i.e the same pair as the key (see mapper),
		#it means if our key is (a,b) only (a,b) exists in the data and not (b,a) 
		#if (b,a) existd, then our key calculation would have yield the same result for (a,b) and (b,a) and so
		#the list would have more than 1 entry if there was another combination.
		print key
		mr.emit((list_of_values[0][0],list_of_values[0][1]))
		mr.emit((list_of_values[0][1],list_of_values[0][0]))

#def main():
mr = MapReduce.MapReduce()
#inputdata = open(sys.argv[1])
inputdata = open(sys.argv[1])
mr.execute(inputdata, mapper, reducer)
