'''
Created on 28 Mar 2013

@author: thomas
'''
import QSTK.qstkutil.tsutil as tsu
import numpy as np
import pandas as pd
import datetime as dt
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.DataAccess as da

def run(cash, orders, values, key):
  #print 'Thomas'
  data = pd.read_csv(orders, parse_dates=True, names=['year','month','day','symbol','transaction','nr_shares','empty'], header=0)
  data = data.sort(['year', 'month', 'day']);
  old = data
  #print 'Gyldig', data[(data.day == 10) & (data.month == 6) ]
  #print 'Ugyldug', data[(data.day == 31) & (data.month == 6) ]
  
  array = np.array(data)
  #print array
  symbols = np.unique(array[:,3])
  symbols = np.array(symbols, dtype='S').tolist()
  dt_start = dt.datetime(array[0,0], array[0,1], array[0,2])
  dt_end = dt.datetime(array[-1,0], array[-1,1], array[-1,2], 16)
  print dt_start, dt_end
  'Get stock prices'
  timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))
  #print 'Ti', timestamps[-1] 
  dataobj = da.DataAccess('Yahoo')
  data = dataobj.get_data(timestamps, symbols, key)
  price = data.values
  p = pd.DataFrame(price, timestamps)
  
  #print 'price', price
  
  dict = {}
  for i in range(0, len(array)):
    orderRow = array[i]
    date = dt.datetime(orderRow[0], orderRow[1], orderRow[2], 16)
    datacolumn = symbols.index(orderRow[3])
    cell = p.ix[date][datacolumn]    
    #cell = dict[date][datacolumn]
    quantity = orderRow[5]

    if (orderRow[4] == 'Buy'):
      cash = cash - cell * quantity
      #stocks[i][datacolumn] = stockcell + quantity 
    elif (orderRow[4] == 'Sell'):
      cash = cash + cell * quantity
      #stocks[i][datacolumn] = stockcell - quantity
    dict[date] = cash
   # print 'cash', orderRow[4], cash, cell, quantity
  #print dict

  cash_array = np.zeros(len(timestamps))
  for i in range(0, len(timestamps)):
    k = timestamps[i]
    #print k, k in dict
    if (k in dict):
      cash_array[i] = dict[k]
    else:
      cash_array[i] = cash_array[i-1]
    #print cash_array[i]
  #print cash_array

  
  stocks = np.zeros((len(timestamps), len(symbols)))
  for i in range(0, len(timestamps)):
    ts = timestamps[i]
    for j in range(0, len(symbols)):
      o = old[(old.day == ts.day) & (old.month == ts.month) & (old.year == ts.year) & (old.symbol == symbols[j])]
      if i > 0:
        stocks[i][j] = stocks[i-1][j]
      val = stocks[i][j]
      if len(o) > 0:       
        gf = o.values.tolist()
        for k in range(0, len(gf)):         
          row = gf[k]
          if row[4] == 'Buy':
            val = val + row[5]
          else:
            val = val - row[5]
      stocks[i][j]  = val
  #print 'stocks', stocks

  value = np.zeros((len(timestamps), 3))
  for i in range(0,len(timestamps)):
    rowprice = 0
    for j in range(0, len(symbols)):
      rowprice = rowprice + stocks[i][j] * price[i][j]
    value[i][0] = rowprice
    value[i][1] = cash_array[i]
    value[i][2] = rowprice + cash_array[i]
  
  #print 'value', value[:,2]  
  
  #print 'stocks', stocks[5:], len(stocks)
  #print 'Data', data[:5]
  na_price = value[:,0]
  #print 'na', len(na_price), na_price[0]
  #print 'type', type(na_price)
  normalized_price = price / price[0, :]
  #print 'normal', normalized_price[5:], len(normalized_price)
  #invest = normalized_price.sum()
  #stocks = [1.0, 1.0, 1.0, 0.0]
  #print 'stocks', stocks[:5]
  c = normalized_price * stocks
  #print 'n*a', allocation, c
  #print "c", c[:10]
  #print 'normal', normalized_price[:20]
  #invest = c.sum(axis=1)
  invest = c.sum(axis=1)
  
  print 'c', c#[-5:]
  print 'invest', invest[:20]

  #daily_returns = tsu.returnize0(invest)
  daily_returns = tsu.daily(invest) 
  mean_daily_return = np.mean(daily_returns) 
  stdev_daily_return = np.std(daily_returns)
  sharpe_ratio = tsu.get_sharpe_ratio(daily_returns)
  
  print 'returns', mean_daily_return, stdev_daily_return, sharpe_ratio, invest[-1]
  times = np.zeros((len(timestamps),4))
  for i in range(0, len(timestamps)):
    times[i][0] = timestamps[i].year
    times[i][1] = timestamps[i].month
    times[i][2] = timestamps[i].day
    times[i][3] = value[:,2][i]

  np.savetxt(values, times, fmt='%d', delimiter=';')

  #mean = pd.DataFrame(value[:,2]).resample('M', how=['mean', 'median', 'std'])
  #print mean
  
if __name__ == '__main__':
  run(1000000, 'orders_q1.csv', 'values_q.csv', 'close')
  #run(1000000, 'orders2_q.csv', 'values2_q.csv', 'close')
  #run(1000000, 'spx.csv', 'valuesspx2.csv', 'close')