'''
@author: Shimol Shah
'''


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
import sys
"""
Accepts a list of symbols along with start and end date
Returns the Event Matrix which is a pandas Datamatrix
Event matrix has the following structure :
    |IBM |GOOG|XOM |MSFT| GS | JP |
(d1)|nan |nan | 1  |nan |nan | 1  |
(d2)|nan | 1  |nan |nan |nan |nan |
(d3)| 1  |nan | 1  |nan | 1  |nan |
(d4)|nan |  1 |nan | 1  |nan |nan |
...................................
...................................
Also, d1 = start date
nan = no information about any event.
1 = status bit(positively confirms the event occurence)
"""


def load(ls_symbols, ldt_timestamps):
    dataobj = da.DataAccess('Yahoo')

    #print ldt_timestamps
    #print ls_symbols
    ldt_timestamps.sort()
    print "---"
    print ldt_timestamps[0]
    print ldt_timestamps[-1]
    ldt_timestamps = du.getNYSEdays(ldt_timestamps[0], ldt_timestamps[-1], dt.timedelta(hours=16))

    print "---"

    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)

    d_data = dict(zip(ls_keys, ldf_data))

    for s_key in ls_keys:
        d_data[s_key] = d_data[s_key].fillna(method='ffill')
        d_data[s_key] = d_data[s_key].fillna(method='bfill')
        d_data[s_key] = d_data[s_key].fillna(1.0)
    #print d_data['close'].loc[ldt_timestamps[0]]
    #print d_data['close'].loc[ldt_timestamps[-1]]
    #print d_data['close'].loc[dt.datetime(2010,12,22,16)]
    return d_data
    
def getDays(end_date, days_prior):
    print "end_date: ", end_date
    print "dates_prior", days_prior
    date = end_date
    dates = []
    for i in range(1, days_prior):
      date = du.getPrevNNYSEday(date,dt.timedelta(hours=-1)) + dt.timedelta(hours=-7)
      dates += [date]
    return dates
    
    
if __name__ == '__main__':
    dates = getDays(dt.datetime(int(sys.argv[1]), int(sys.argv[2]), int(sys.argv[3])),int(sys.argv[4])+10)
    #print dates
    d_data = load([str(sys.argv[5])],dates) 
    print "***"
    print (d_data['close'] - pd.rolling_mean(d_data['close'], int(sys.argv[4]))) / pd.rolling_std(d_data['close'], int(sys.argv[4]))
    
    #dt = dt.datetime(sys.argv[1], sys.argv[2], sys.argv[3])
    #print dt
