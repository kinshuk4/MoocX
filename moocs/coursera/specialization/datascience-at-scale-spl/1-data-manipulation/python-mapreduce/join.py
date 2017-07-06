# -*- coding: utf-8 -*-
import MapReduce
import json 
import sys



def mapper(record):
    key = record[1]
    value = record

    mr.emit_intermediate(key,value)

def reducer(key, list_of_values):
    for order_records in list_of_values:
        if order_records[0]=='order':
            for line_recrods in list_of_values:
                if line_recrods[0]=='line_item':
                    mr.emit(order_records+line_recrods)

#def main():
mr = MapReduce.MapReduce()
inputdata = open(sys.argv[1])
#inputdata = open("records.json")
mr.execute(inputdata, mapper, reducer)
