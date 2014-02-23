# Homework 1
# Author: Shan Lu

# Usage
# vol, daily_ret, sharpe, cum_ret = simulate((2011,1,1),(2011,12,31),["AAPL","GLD","GOOG","XOM"],[0.2,0.2,0.3,0.3])
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da
import datetime as dt
import matplotlib.pyplot as plt
from pylab import *
import pandas

c_dataobj = da.DataAccess('Yahoo',cachestalltime=0)

dt_start = dt.datetime(2011,1,1) # start_date
dt_end = dt.datetime(2011,12,31) # end_date
symbols = ['BRCM', 'ADBE', 'AMD', 'ADI'] #symbols
max_sharpe = 0
max_alloc = [0,0,0,0]

dt_timeofday = dt.timedelta(hours=16)
ldt_timestamps = du.getNYSEdays(dt_start,dt_end,dt_timeofday)
ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
ldf_data = c_dataobj.get_data(ldt_timestamps, symbols, ls_keys)
d_data = dict(zip(ls_keys, ldf_data))
df_rets = d_data['close'].copy()
na_rets = df_rets.values
tsu.returnize0(na_rets)
for i in range(11):
    for j in range(11):
        for k in range(11):
            for l in range(11):
                if i*0.1+j*0.1+k*0.1+l*0.1 == 1:
                    port_alloc = [i*0.1,j*0.1,k*0.1,l*0.1]
                    na_portrets = np.sum(na_rets * port_alloc, axis=1)
                    na_port_total = np.cumprod(na_portrets + 1)
                    na_component_total = np.cumprod(na_rets + 1, axis=0)
                    cumu_ret = sum(na_component_total[len(na_component_total)-1]*port_alloc)
                    # calculate solution
                    vol = np.std(na_portrets)
                    avg_daily_return = np.mean(na_portrets)
                    sharpe = np.sqrt(250)*avg_daily_return/vol
                    if sharpe > max_sharpe:
                        max_sharpe = sharpe
                        max_alloc = port_alloc
                    
                    
#port_alloc = [0.4,0.4,0.0,0.2] #portfolio allocation

'''
dt_start = dt.datetime(2010,1,1)
dt_end = dt.datetime(2010,12,31)
symbols = ['AXP', 'HPQ', 'IBM', 'HNZ']
port_alloc = [0.0,0.0,0.0,1.0]
'''




print "Start Date: " + str(dt_start)
print "End Date: " + str(dt_end)
print "Symbols: " + str(symbols)
print "Optimal Allocations: " + str(max_alloc)
print "Sharpe Ratio: " + str(max_sharpe)
print "Volatility (stdev of daily returns): " + str(vol)
print "Average Daily Return: " + str(avg_daily_return)
print "Cumulative return: " +str(cumu_ret)

