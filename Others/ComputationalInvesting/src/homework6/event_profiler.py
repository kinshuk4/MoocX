'''
Created on 22 Mar 2013

@author: thomas
'''

'''
(c) 2011, 2012 Georgia Tech Research Corporation
This source code is released under the New BSD license.  Please see
http://wiki.quantsoftware.org/index.php?title=QSTK_License
for license details.

Created on January, 23, 2013

@author: Sourabh Bajaj
@contact: sourabhbajaj@gatech.edu
@summary: Event Profiler Tutorial
'''

import csv
import pandas as pd
import numpy as np
import math
import copy
import QSTK.qstkutil.qsdateutil as du
import datetime as dt
import QSTK.qstkutil.DataAccess as da
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkstudy.EventProfiler as ep

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


def find_events(ls_symbols, d_data, key, limit, days, output, times):
    ''' Finding the event dataframe '''
    df_close = d_data[key]
    ts_market = df_close['SPY']

    print "Finding Events"

    # Creating an empty dataframe
    df_events = copy.deepcopy(df_close)
    df_events = df_events * np.NAN

    print "DF_EVENTS", type(df_events)
    p = df_close
    loopback = 20
    mean = pd.rolling_mean(p, loopback)
    std = pd.rolling_std(p, loopback)
    
    upper = mean +  times * std
    low = mean - times * std
  
    bollinger = (df_close - mean) / std
    
    # Time stamps for the event range
    ldt_timestamps = df_close.index
    resultFile = open(output,'wb')
    wr = csv.writer(resultFile, dialect='excel')
    for s_sym in ls_symbols:
        for i in range(1, len(ldt_timestamps)):
            # Calculating the returns for this timestamp
            f_symprice_today = bollinger[s_sym].ix[ldt_timestamps[i]]
            f_symprice_yest = bollinger[s_sym].ix[ldt_timestamps[i - 1]]
            f_marketprice_today = bollinger['SPY'].ix[ldt_timestamps[i]] #ts_market.ix[ldt_timestamps[i]]
            f_marketprice_yest = bollinger['SPY'].ix[ldt_timestamps[i-1]]#ts_market.ix[ldt_timestamps[i - 1]]
            f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
            f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1

            if f_symprice_today < limit and f_symprice_yest >= limit and f_marketprice_today >= 1.2:
              df_events[s_sym].ix[ldt_timestamps[i]] = 1
              
              date = ldt_timestamps[i]
              row = [date.year, date.month, date.day, s_sym,'Buy',100,'']
              wr.writerow(row)

              sellDate = du.getNYSEoffset(date, 5)
              if (sellDate > ldt_timestamps[-1]):
                sellDate = ldt_timestamps[-1]
              row = [sellDate.year, sellDate.month, sellDate.day, s_sym,'Sell',100,'']
              wr.writerow(row)

            '''f_symprice_today = df_close[s_sym].ix[ldt_timestamps[i]]
            f_symprice_yest = df_close[s_sym].ix[ldt_timestamps[i - 1]]
            f_marketprice_today = ts_market.ix[ldt_timestamps[i]]
            f_marketprice_yest = ts_market.ix[ldt_timestamps[i - 1]]
            f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
            f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1

            if f_symprice_today < limit and f_symprice_yest >= limit:
              df_events[s_sym].ix[ldt_timestamps[i]] = 1
              
              date = ldt_timestamps[i]
              row = [date.year, date.month, date.day, s_sym,'Buy',100,'']
              wr.writerow(row)

              sellDate = du.getNYSEoffset(date, 5)
              if (sellDate > ldt_timestamps[-1]):
                sellDate = ldt_timestamps[-1]
              row = [sellDate.year, sellDate.month, sellDate.day, s_sym,'Sell',100,'']
              wr.writerow(row)
               '''        
            
            # Event is found if the symbol is down more then 3% while the
            # market is up more then 2%
            #if f_symreturn_today <= -0.03 and f_marketreturn_today >= 0.02:
             #   df_events[s_sym].ix[ldt_timestamps[i]] = 1

    return df_events
  
def study(dt_start, dt_end, list, key, filename, limit, days, output, times):
    ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))
    dataobj = da.DataAccess('Yahoo')
    ls_symbols = dataobj.get_symbols_from_list(list)
    ls_symbols.append('SPY')
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    
    ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
    d_data = dict(zip(ls_keys, ldf_data))

    for s_key in ls_keys:
      d_data[s_key] = d_data[s_key].fillna(method = 'ffill')
      d_data[s_key] = d_data[s_key].fillna(method = 'bfill')
      d_data[s_key] = d_data[s_key].fillna(1.0)

    df_events = find_events(ls_symbols, d_data, key, limit, days, output, times)
    print "Creating Study"
    ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
                s_filename=filename, b_market_neutral=True, b_errorbars=True,
                s_market_sym='SPY')    
    i_no_events = int(np.nansum(df_events.values))
    #print 'Test %f' % (i_no_events)
    return i_no_events

if __name__ == '__main__':
    dt_start = dt.datetime(2008, 1, 1)
    dt_end = dt.datetime(2009, 12, 31)
    ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))

    dataobj = da.DataAccess('Yahoo')
    ls_symbols = dataobj.get_symbols_from_list('sp5002012')
    ls_symbols.append('SPY')
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    
    ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
    d_data = dict(zip(ls_keys, ldf_data))

    for s_key in ls_keys:
      d_data[s_key] = d_data[s_key].fillna(method = 'ffill')
      d_data[s_key] = d_data[s_key].fillna(method = 'bfill')
      d_data[s_key] = d_data[s_key].fillna(1.0)

    df_events = find_events(ls_symbols, d_data)
    print "Creating Study"
    ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
                s_filename='MyEventStudy.pdf', b_market_neutral=True, b_errorbars=True,
                s_market_sym='SPY')