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
#  print 'returns', sharpe_ratio, total_return, stdev_daily_return, mean_daily_return
  
  startdate = dt.datetime(int(data[0][0]), int(data[0][1]), int(data[0][2]), 16)
  enddate = dt.datetime(int(data[-1][0]), int(data[-1][1]), int(data[-1][2]), 16)
  allocation = [1.0]
#  print 'SPX', startdate, enddate, symbol, allocation
  result = sm.simulate(startdate, enddate, [symbol], allocation, 'close')
#  print 'result', result[2], result[3], result[0], result[1]
  f = data[-1]
  print 'The final value of the portfolio using the sample file is -- %d-%d-%d -> %.2f ' % (f[0], f[1], f[2], f[3]) #2009,12,28,54824.0
  print ''
  print 'Details of the Performance of the portfolio'
  print ''
  print 'Data Range : %d-%d-%d to %d-%d-%d' % (data[0][0], data[0][1], data[0][2], data[-1][0], data[-1][1], data[-1][2])
  print ''
  print 'Sharpe Ratio of Fund : %.12f' % (sharpe_ratio)# 0.527865227084
  print 'Sharpe Ratio of $SPX : %.12f'% (result[2])#-0.184202673931
  print ''
  print 'Total Return of Fund %.12f:' % (total_return)#  1.09648
  print 'Total Return of $SPX %.12f:' % (result[3])# 0.779305674563'
  print ''
  print 'Standard Deviation of Fund : %.12f' % stdev_daily_return# 0.0060854156452
  print 'Standard Deviation of $SPX : %.12f' % result[0]#0.022004631521'
  print ''
  print 'Average Daily Return of Fund : %.12f' % mean_daily_return# 0.000202354576186
  print 'Average Daily Return of $SPX : %.12f' % result[1]#-0.000255334653467'
    
if __name__ == '__main__':
  run('myoutput.csv', '$SPX')
  #run('values2.csv', '$SPX')
  #run('values.csv', '$SPX')
      
    #run('valuesspx2.csv', '$SPX')