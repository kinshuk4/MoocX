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

def find_events(ls_symbols, d_data):
    ''' Finding the event dataframe '''
    df_close = d_data['close']
    ts_market = df_close['SPY']

    print "Finding Events"

    # Creating an empty dataframe
    df_events = copy.deepcopy(df_close)
    df_events = df_events * np.NAN

    # Time stamps for the event range
    ldt_timestamps = df_close.index

    spy_bollinger = (df_close['SPY'] - pd.rolling_mean(df_close['SPY'],20)) / pd.rolling_std(df_close['SPY'],20)
    for s_sym in ls_symbols:
        #if( s_sym == 'GOOG' ):
        #    print pd.rolling_mean(df_close[s_sym], 20)
        #if( s_sym == 'SPY' ):
        #    spy_bollinger = (df_close[s_sym] - pd.rolling_mean(df_close[s_sym],20)) / pd.rolling_std(df_close[s_sym],20)
        #else:
        #    equity_bollinger = (df_close[s_sym] - pd.rolling_mean(df_close[s_sym],20)) / pd.rolling_std(df_close[s_sym],20)
        equity_bollinger = (df_close[s_sym] - pd.rolling_mean(df_close[s_sym],20)) / pd.rolling_std(df_close[s_sym],20)    
        for i in range(1, len(ldt_timestamps)):
            # Calculating the returns for this timestamp
            # f_symprice_today = df_close[s_sym].ix[ldt_timestamps[i]]
            # f_symprice_yest = df_close[s_sym].ix[ldt_timestamps[i - 1]]
            # f_marketprice_today = ts_market.ix[ldt_timestamps[i]]
            # f_marketprice_yest = ts_market.ix[ldt_timestamps[i - 1]]
            # f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
            # f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1

            # Event is found if the symbol is down more then 3% while the
            # market is up more then 2%
            
            #f_equity_today = (d_data['close'] - pd.rolling_mean(d_data['close'], int(sys.argv[4]))) / pd.rolling_std(d_data['close'], int(sys.argv[4]))
            equity_bollinger_today = equity_bollinger.loc[ldt_timestamps[i]]
            equity_bollinger_yest = equity_bollinger.loc[ldt_timestamps[i-1]]
            spy_bollinger_today = spy_bollinger.loc[ldt_timestamps[i]]
            
            if equity_bollinger_today < -2.0 and equity_bollinger_yest >= -2.0 and spy_bollinger_today >= 1.3:
                df_events[s_sym].ix[ldt_timestamps[i]] = 1

    return df_events

def load(dataobj, ls_symbols, ldt_timestamps):
    #print ls_symbols
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)

    d_data = dict(zip(ls_keys, ldf_data))

    for s_key in ls_keys:
        d_data[s_key] = d_data[s_key].fillna(method='ffill')
        d_data[s_key] = d_data[s_key].fillna(method='bfill')
        d_data[s_key] = d_data[s_key].fillna(1.0)
    return d_data
    
if __name__ == '__main__':

    dt_start = dt.datetime(2008, 1, 1)
    dt_end = dt.datetime(2009, 12, 31)
    ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))

    dataobj = da.DataAccess('Yahoo')

    ls_symbols = dataobj.get_symbols_from_list('sp5002012') + ['SPY']

    d_data = load(dataobj, ls_symbols, ldt_timestamps) 

    df_events = find_events(ls_symbols, d_data)
    print "Creating Study"
    ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
                    s_filename='MyEventStudy.pdf', b_market_neutral=True, b_errorbars=True,
                    s_market_sym='SPY')

