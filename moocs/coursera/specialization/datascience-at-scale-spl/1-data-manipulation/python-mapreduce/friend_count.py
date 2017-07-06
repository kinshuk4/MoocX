# -*- coding: utf-8 -*-
import MapReduce
import json 
import sys



def mapper(record):
    key = record[0]
    value = record[1]

    mr.emit_intermediate(key,value)

def reducer(key, list_of_values):
	mr.emit((key,len(list_of_values)))

#def main():
mr = MapReduce.MapReduce()
#inputdata = open(sys.argv[1])
inputdata = open(sys.argv[1])
mr.execute(inputdata, mapper, reducer)
