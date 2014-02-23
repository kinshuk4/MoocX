'''
Created on 13 Mar 2013

@author: thomas
'''
import datetime as dt
import Simulator as s
import itertools
import numpy as np
def matrix2():
  allocs = np.zeros((10000,4))
  n = 0
  for i in range(0,11):
    for j in range(0,11):
      for k in range(0,11):
        for l in range(0,11):
          row = [i/10.0,j/10.0,k/10.0,l/10.0]
          if sum(row)==1.0:
            allocs[n,:]=row
            n = n+1
  allocs = allocs[0:n,:]
  print allocs
  print len(allocs)
  return allocs

if __name__ == '__main__':
  symbols = ['AXP', 'HPQ', 'IBM', 'HNZ']
  synbols = ['AAPL', 'GOOG', 'IBM', 'MSTF']
  startdate = dt.datetime(2011, 1, 1)
  enddate = dt.datetime(2011, 12, 31)
  allocation = [0.2, 0.2, 0.2, 0.4]
  minAllocatioon = allocation
  #result = s.simulate(startdate, enddate, symbols, allocation)
  maxResult = s.simulate(startdate, enddate, symbols, allocation)
  minResult = maxResult  
  matrix = np.array(map(list, filter(lambda x: sum(x) == 1, itertools.product(np.linspace(0,1,11), repeat=4))))
  count = len(matrix)
  #matrix = np.array([[0.2, 0.2, 0.2, 0.4], [0.5, 0.0, 0.5, 0.0], [0.1, 0.1, 0.8, 0.0], [0.2, 0.0, 0.8, 0.0]])
  #matrix = matrix2()
  print '%d = %d' % (count, len(matrix))
  
  for i in range(0, len(matrix)):
    local = s.simulate(startdate, enddate, symbols, matrix[i])
    print '%s = %f ' % (matrix[i], local[2])
    if (maxResult[2] < local[2]):
      maxResult = local
      allocation = matrix[i]
    if (minResult[2] > local[2]):
      minResult = local
      minAllocatioon = matrix[i]
  print "The Winner is. %s (Min: %s = %.11f) (%d)" % (allocation, minAllocatioon, minResult[2], len(matrix))
  print 'Sharpe ratio: %.11f' % (maxResult[2])
  print 'Volatility %.13f' % (maxResult[0])
  print 'Average Daily Return %.15f' % (maxResult[1])
  print 'Cumulatative return %.11f' % (maxResult[-1])