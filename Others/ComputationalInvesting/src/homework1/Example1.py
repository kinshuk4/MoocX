'''
Created on 13 Mar 2013

@author: thomas
'''
import datetime as dt
import Simulator as s

if __name__ == '__main__':
  symbols = ["AAPL", "GOOG", "XOM", "GLD"]
  startdate = dt.datetime(2011, 1, 1)
  enddate = dt.datetime(2011, 12, 31)
  allocation = [0.4, 0.0, 0.2, 0.4]
  result = s.simulate(startdate, enddate, symbols, allocation)
  print 'Sharpe ratio: %.11f' % (result[2])
  print 'Volatility %.13f' % (result[0])
  print 'Average Daily Return %.15f' % (result[1])
  print 'Cumulatative return %.11f' % (result[-1])