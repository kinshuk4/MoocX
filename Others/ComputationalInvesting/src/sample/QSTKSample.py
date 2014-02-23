'''
Created on 28 Feb 2013

@author: thomas
'''
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da

import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd

def main():
  print 'Hello World'
  ls_symbols = ["AAPL", "GLD", "GOOG", "$SPX", "XOM"]
  dt_start = dt.datetime(2007, 1, 1)
  dt_end = dt.datetime(2010, 12, 31)
  dt_timeofday = dt.timedelta(hours=16)
  ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt_timeofday)
  c_dataobj = da.DataAccess('Yahoo')
  ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
  ldf_data = c_dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
  d_data = dict(zip(ls_keys, ldf_data))
  
  na_price = d_data['close'].values
  '''plot(na_price, 'Adjusted close', ldt_timestamps, ls_symbols)'''
  na_normalized_price = na_price / na_price[0, :]
  '''plot(na_normalized_price, 'Normalized close', ldt_timestamps, ls_symbols)'''
  
  na_rets = na_normalized_price.copy()
  tsu.returnize0(na_rets)
  print 'len %d' % (len(na_rets))
  
  t = na_rets.T
  print t[4][-50:]
  symbols = ["SPX", "XOM"]
  '''plot(na_rets[:,[-2,-1]][:50], 'Daily returns', ldt_timestamps[:50], ls_symbols[-2:])'''
  
  plt.scatter(na_rets[:,3],na_rets[:,4],c='blue')
  plt.show()
  plt.scatter(na_rets[:,3],na_rets[:,1],c='red')
  plt.show()
    
def plot(price, ylabel, timestamps, symbols):  
  plt.clf()
  plt.plot(timestamps, price)
  plt.legend(symbols)
  plt.ylabel(ylabel)
  plt.xlabel('Date')
  plt.show()
  plt.savefig('%s.pdf' % (ylabel), format='pdf')
  

if __name__ == '__main__':
  main()