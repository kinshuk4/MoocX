'''
Created on 29 Mar 2013

@author: thomas
'''
import pandas as pd
import numpy as np
import QSTK.qstkutil.tsutil as tsu
import simulator as sm
import datetime as dt

def run(values, symbol):
  #data = pd.read_csv(values, header=False)
  data = np.loadtxt(values, delimiter=';', skiprows=0)
  values = data[:,3]
  #values = data.values[:,3]
  #invest = values.sum(axis=1)
  normalized_price = values / values[0]
  daily_returns = tsu.daily(normalized_price) 
  mean_daily_return = np.mean(daily_returns) 
  stdev_daily_return = np.std(daily_returns)
  sharpe_ratio = tsu.get_sharpe_ratio(daily_returns)
  total_return = values[-1] / values[0]
  print 'returns', sharpe_ratio, total_return, stdev_daily_return, mean_daily_return
  
  startdate = dt.datetime(int(data[0][0]), int(data[0][1]), int(data[0][2]), 16)
  enddate = dt.datetime(int(data[-1][0]), int(data[-1][1]), int(data[-1][2]), 16)
  allocation = [1.0]
  print 'SPX', startdate, enddate, symbol, allocation
  result = sm.simulate(startdate, enddate, [symbol], allocation, 'close')
  print 'result', result[2], result[3], result[0], result[1]
    
if __name__ == '__main__':
  #run('arto.csv', '$SPX')
  run('values2.csv', '$SPX')
  #run('values.csv', '$SPX')
      
    #run('valuesspx2.csv', '$SPX')