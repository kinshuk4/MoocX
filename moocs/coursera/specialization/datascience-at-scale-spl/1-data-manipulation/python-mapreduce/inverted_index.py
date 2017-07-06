# -*- coding: utf-8 -*-
import MapReduce
import json 
import sys



def mapper(record):
    # key: document identifier
    # value: document contents
    key = record[0]
    value = record[1]
    words = value.split()
    for w in words:
      mr.emit_intermediate(w, key)

def reducer(key, list_of_values):
    lst = []
    for v in list_of_values:
        if v not in lst:
            lst.append(v)
    mr.emit((key, lst))


mr = MapReduce.MapReduce()

inputdata = open(sys.argv[1])

#inputdata = open("books.json")


mr.execute(inputdata, mapper, reducer)


