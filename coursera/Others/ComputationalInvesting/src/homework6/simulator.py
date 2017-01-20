'''
Created on 11 Mar 2013

@author: thomas
'''
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da

import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

def simulate(startdate, enddate, symbols, allocation, type):
  timeofday = dt.timedelta(hours=16)
  timestamps = du.getNYSEdays(startdate, enddate, timeofday)
  c_dataobj = da.DataAccess('Yahoo')
  ls_keys = [type]
  ldf_data = c_dataobj.get_data(timestamps, symbols, ls_keys)
  d_data = dict(zip(ls_keys, ldf_data))
  #print d_data.values
  na_price = d_data[type].values
  print 'price', na_price[0], na_price[-1]
  #print 'na', na_price
  #print 'na_price', na_price
  normalized_price = na_price / na_price[0, :]
  #print 'norm', normalized_price
  c = normalized_price * allocation
  #print 'n*a', allocation, c
  invest = c.sum(axis=1)
  #print 'c', c
  #print 'invesi', invest


  daily_returns = tsu.daily(invest) 
  mean_daily_return = np.mean(daily_returns) 
  stdev_daily_return = np.std(daily_returns)
  sharpe_ratio = tsu.get_sharpe_ratio(daily_returns)
  return stdev_daily_return, mean_daily_return, sharpe_ratio, invest[-1]
 
  
if __name__ == '__main__':
  np.set_printoptions(precision=2)
  symbols = ["AAPL", "GLD", "GOOG", "$SPX", "XOM"]
  symbols = ["AAPL", "GOOG", "XOM", "GLD"]
  #symbols = ["AAPL", "GLD"]
  startdate = dt.datetime(2011, 1, 1)
  enddate = dt.datetime(2011, 12, 31)
  allocation = [0.4, 0.0, 0.2, 0.4]
  #allocation = [0.6, 0.4]
  
  result = simulate(startdate, enddate, symbols, allocation)
  print 'Sharpe ratio: %.11f' % (result[2])
  print 'Volatility %.13f' % (result[0])
  print 'Average Daily Return %.15f' % (result[1])
  print 'Cumulatative return %.11f' % (result[-1])
  