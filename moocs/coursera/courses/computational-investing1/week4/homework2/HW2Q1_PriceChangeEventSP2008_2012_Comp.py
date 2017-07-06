'''
(c) 2011, 2012 Georgia Tech Research Corporation
This source code is released under the New BSD license.  Please see
http://wiki.quantsoftware.org/index.php?title=QSTK_License
for license details.

Created on January, 23, 2013

@author: Kinshuk Chandra
@summary: HW2Q1
The event is defined as when the actual close of the stock price drops below $5.00, more specifically, when:

price[t-1] >= 5.0 and price[t] < 5.0 

an event has occurred on date t.
* Test this event using the Event Profiler over the period from 1st Jan, 2008 to 31st Dec 2009.
* Do two runs - Once with SP5002008 and other with SP5002012.
Which of the two runs gives better results ?
--Run with S&P 500 in 2012. (S&P5002012)
--Run with S&P 500 in 2008. (S&P5002008)

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


def find_events(ls_symbols, d_data):
    ''' Finding the event dataframe '''
    df_close = d_data['actual_close']
    ts_market = df_close['SPY']

    print "Finding Events"

    # Creating an empty dataframe i.e. df=datafreame
    df_events = copy.deepcopy(df_close)
    df_events = df_events * np.NAN

    # Time stamps for the event range
    ldt_timestamps = df_close.index

    for s_sym in ls_symbols:
        for i in range(1, len(ldt_timestamps)):
            # Calculating the returns for this timestamp
            f_symprice_today = df_close[s_sym].ix[ldt_timestamps[i]]
            f_symprice_yest = df_close[s_sym].ix[ldt_timestamps[i - 1]]
            f_marketprice_today = ts_market.ix[ldt_timestamps[i]]
            f_marketprice_yest = ts_market.ix[ldt_timestamps[i - 1]]
            f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
            f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1

            # Event is found if the symbol is down more then 3% while the
            # market is up more then 2%
            if f_symprice_today <5 and f_symprice_yest >= 5:
                df_events[s_sym].ix[ldt_timestamps[i]] = 1

    return df_events

def spy_events(dt_start,dt_end,ldt_timestamps,spy_year, filename):
    dataobj = da.DataAccess('Yahoo')
    ls_symbols = dataobj.get_symbols_from_list(spy_year)
    
    ls_symbols.append('SPY')

    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
    d_data = dict(zip(ls_keys, ldf_data))

    df_events = find_events(ls_symbols, d_data)
    print "Creating Study"
    ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
                s_filename=filename, b_market_neutral=True, b_errorbars=True,
                s_market_sym='SPY')    
    



if __name__ == '__main__':
    dt_start = dt.datetime(2008, 1, 1)
    dt_end = dt.datetime(2009, 12, 31)
    ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))

    spy_events(dt_start,dt_end,ldt_timestamps,'sp5002012','MyEventStudy_2012_Comp.pdf')
#     dataobj = da.DataAccess('Yahoo')
#     ls_symbols_2008 = dataobj.get_symbols_from_list('sp5002008')
#     ls_symbols_2012 = dataobj.get_symbols_from_list('sp5002012')
#     
#     ls_symbols_2008.append('SPY')
#     ls_symbols_2012.append('SPY')
# 
#     ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
#     
#     
#     ldf_data_2008 = dataobj.get_data(ldt_timestamps, ls_symbols_2008, ls_keys)
#     ldf_data_2012 = dataobj.get_data(ldt_timestamps, ls_symbols_2012, ls_keys)
#      
#     d_data_2008 = dict(zip(ls_keys, ldf_data_2008))
#     d_data_2012 = dict(zip(ls_keys, ldf_data_2012))
#      
#     df_events_2008 = find_events(ldf_data_2008, d_data_2008)
#     df_events_2012 = find_events(ldf_data_2012, d_data_2012)
#     
#     print "Creating Study"
#     ep.eventprofiler(df_events_2008, d_data_2008, i_lookback=20, i_lookforward=20,
#                 s_filename='MyEventStudy_2008_Comp.pdf', b_market_neutral=True, b_errorbars=True,
#                 s_market_sym='SPY')
#     ep.eventprofiler(df_events_2012, d_data_2012, i_lookback=20, i_lookforward=20,
#                 s_filename='MyEventStudy_2012_Comp.pdf', b_market_neutral=True, b_errorbars=True,
#                 s_market_sym='SPY')
