import pandas as pd
import numpy as np
import math
import copy
import QSTK.qstkutil.qsdateutil as du
import datetime as dt
import QSTK.qstkutil.DataAccess as da
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkstudy.EventProfiler as ep
import csv
import sys,getopt
import bisect


filename = ""
benchmark = ""
rawdata = []

def test_msg(args):
    global filename
    global benchmark

    print "Hello"
    filename = args[0]
    benchmark = args[1]
    print filename
    print benchmark

def read_csv():
    reader = csv.reader(open(filename, 'rU'), delimiter=',')
    global rawdata
    for row in reader:
        rawdata.append(row) 
    #print rawdata    

def process_data():
    previous_total = 0
    total = 0
    adjusted_return = {}
    for data in rawdata:
        date = dt.datetime(int(data[0]),int(data[1]),int(data[2]))
        date_int = int((data[0] + data[1] + data[2]))
        total = int(data[3])
        if(previous_total != 0):
            return_percentage = float(total)/previous_total - 1
        else:
            return_percentage = 0
        previous_total = total

        #absolute_value[date] = total 
        adjusted_return[date_int] = return_percentage

    print "Sharpe ratio: ", np.sqrt(252)* np.average(adjusted_return.values()) \
        / np.std(adjusted_return.values())
    print "Total return: ", float(rawdata[-1][3])/int(rawdata[0][3])
    print "Standard deviation: ", np.std(adjusted_return.values())
    print "Average daily return: ", np.average(adjusted_return.values())
    
if __name__ == '__main__':
    test_msg(sys.argv[1:])
    read_csv()
    process_data()
