'''
Created on 12 Apr 2013

@author: thomas
'''
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import datetime as dt
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.DataAccess as da

def run(startDate, endDate, loopback, symbols, key, times):
  print "It works"
  timestamps = du.getNYSEdays(startDate, endDate, dt.timedelta(hours=16))
  dataobj = da.DataAccess('Yahoo')
    
  data = dataobj.get_data(timestamps, symbols, key)
  price = data.values
  p = pd.DataFrame(price, timestamps)
  
  mean = pd.rolling_mean(p, loopback)
  std = pd.rolling_std(p, loopback)
  #print 'mean', mean.values, std.values

  upper = mean +  times * std
  low = mean - times * std
  
  prices = (price - mean) / std

  print 'Prices',  prices.ix[dt.datetime(2010,6,14,16)]
  
  x = data.index
  fig, ax = plt.subplots(1)
  ax.plot(timestamps, price)  #ax = plt.subplot(t)
  ax.plot(timestamps, mean)
  #ax.plot(timestamps, upper)
  #ax.plot(timestamps, low)
  
  #upper = upper.fillna(value=0)
  #upper = upper.fillna(method='backfill')
  ax.fill_between(timestamps, upper[0], low[0], facecolor='b', alpha=0.1)
  ax.legend(('Price', 'Mean', 'Upper', "Lower"), 'upper center', shadow=True)
  ax.set_ylabel('Actual close')
    
  fig, ax2 = plt.subplots(1)
  ax2.plot(timestamps, prices, label='bollinger')
  ax2.fill_between(timestamps, 1, -1, facecolor='b', alpha=0.1)
  #ax2.legend(('bollinger'), 'upper center', shadow=True)
  ax2.set_ylabel('Bollinger')
  
  #plt.fill_between(x, price, 0, where=None, interpolate=True)
  #pd.plot()
  
  #plt.fill_between(x, price, 0, where=None, interpolate=True)
  #plt.plot(x, price, 'r')
  #plt.plot(x, mean, 'g')
  #plt.plot(x, upper, 'b')
  #plt.plot(x, low, 'c')

  #plt.legend(('Price', 'Mean', 'Upper', "Lower"), 'upper center', shadow=True)
  plt.show()
  
  print prices.values[-5:]
  
if __name__ == '__main__':
  start = dt.datetime(2010, 1, 1)
  end = dt.datetime(2010, 9, 30)
  run(start, end, 20, ['VZ'], 'actual_close', 2.0)