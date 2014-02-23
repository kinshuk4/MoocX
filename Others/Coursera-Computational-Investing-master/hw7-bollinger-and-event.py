# Homework 7

import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da
import datetime as dt
import matplotlib.pyplot as plt
import pandas
import csv
import QSTK.qstkstudy.EventProfiler as ep
from pylab import *




# claim paramters and variables
startday = dt.datetime(2008,1,1)
endday = dt.datetime(2009,12,31)
timeofday=dt.timedelta(hours=16)
timestamps = du.getNYSEdays(startday,endday,timeofday)
dataobj = da.DataAccess('Yahoo')
symbols = dataobj.get_symbols_from_list('sp5002012')
symbols.append('SPY')
market_sym = ['SPY']

# create the 'SPY' bollinger value dataframe
adjcloses = dataobj.get_data(timestamps, market_sym, "close")
adjcloses = adjcloses.fillna(method='backfill')

market_rolling_mean = pandas.rolling_mean(adjcloses,20,min_periods=20)
market_rolling_std = pandas.rolling_std(adjcloses,20,min_periods=20)
market_bollinger_val = pandas.DataFrame(index=timestamps,columns=market_sym)
for i in range(len(market_rolling_std['SPY'])):
    if market_rolling_std['SPY'][i] > 0:
            market_bollinger_val['SPY'][i] = (adjcloses['SPY'][i] - market_rolling_mean['SPY'][i]) / market_rolling_std['SPY'][i]

print "market bollinger data collected... done"
# create event matrix
keys = ['close']
ldf_data = dataobj.get_data(timestamps, symbols, keys)
d_data = dict(zip(keys, ldf_data))
for s_key in keys:
        d_data[s_key] = d_data[s_key].fillna(method='ffill')
        d_data[s_key] = d_data[s_key].fillna(method='bfill')
        d_data[s_key] = d_data[s_key].fillna(1.0)

df_close = d_data['close']
# Creating an empty dataframe
df_events = df_close
#df_events = pandas.DataFrame(index=timestamps,columns=symbols)
df_events = df_events * np.NAN
print "create event matrix... done"

# Part 1
# implements the Bollinger bands as an indicator using 20 day look back
def find_bollinger_event():
    return

# Part 2
# create an event study with the signal below
# Bollinger value for the equity today <= -2.0
# Bollinger value for the equity yesterday >= 2.0
# Bollinger value for the SPY today >= 1.0
writer = csv.writer(file('hw7orders3.csv','wb'))
count = 0
for syms in symbols:
    sym = list()
    sym.append(syms)
    sym_adjcloses = dataobj.get_data(timestamps, sym, "close")
    sym_adjcloses = sym_adjcloses.fillna(method='backfill')
    sym_rolling_mean = pandas.rolling_mean(sym_adjcloses,20,min_periods=20)
    sym_rolling_std = pandas.rolling_std(sym_adjcloses,20,min_periods=20)
    sym_bollinger_val = pandas.DataFrame(index=timestamps,columns=sym)
    for i in range(len(sym_rolling_std[sym])):
        if sym_rolling_std[sym[0]][i] > 0:
            sym_bollinger_val[sym[0]][i] = (sym_adjcloses[sym[0]][i] - sym_rolling_mean[sym[0]][i]) / sym_rolling_std[sym[0]][i]
    for i in range(len(market_bollinger_val['SPY'])):
        if market_bollinger_val['SPY'][i] >= 1.1:
            if sym_bollinger_val[sym[0]][i] <= -2.0 and sym_bollinger_val[sym[0]][i-1] >= -2.0:
                df_events[sym[0]].ix[timestamps[i]] = 1
                count = count + 1
                print count,sym[0],timestamps[i], market_bollinger_val['SPY'][i],sym_bollinger_val[sym[0]][i],sym_bollinger_val[sym[0]][i-1]
                t = timestamps[i]
                line1 = [t.year,t.month,t.day,sym[0],'BUY',100]
                writer.writerow(line1)
                if i+5 >= len(timestamps):
                    line2 = [timestamps[len(timestamps)-1].year,
                         timestamps[len(timestamps)-1].month,
                         timestamps[len(timestamps)-1].day,
                         sym[0],
                         'SELL',
                         '100']
                else:
                    line2 = [timestamps[i+5].year,
                         timestamps[i+5].month,
                         timestamps[i+5].day,
                         sym[0],
                         'SELL',
                         '100']
                writer.writerow(line2)
    
print "Creating Study"
ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
                s_filename='hw7EventStudy1point3.pdf', b_market_neutral=True, b_errorbars=True,
                s_market_sym='SPY')    

    
            
    
# Part 3
# Revise your event analyzer to output a series of trades in events
# Date, AAPL, BUY, 100
# Date+ 5 days, AAPL, SELL, 100

# Part 4
# feed the output into market simulator

# Part 5
# report the performance of that strategy
