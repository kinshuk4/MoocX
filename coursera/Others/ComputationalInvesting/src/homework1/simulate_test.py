'''
Created on 22 Mar 2013

@author: thomas
'''
import unittest
import datetime as dt
import Simulator as s

class Test(unittest.TestCase):


  def setUp(self):
    pass
  
  def tearDown(self):
    pass

  def testExample2(self):
    symbols = ['AXP', 'HPQ', 'IBM', 'HNZ']
    startdate = dt.datetime(2010, 1, 1)
    enddate = dt.datetime(2010, 12, 31)
    allocation = [0.0, 0.0, 0.0, 1.0]
    result = s.simulate(startdate, enddate, symbols, allocation)
    self.printer(result)
    self.assertAlmostEqual(1.29889334008, result[2], 8)
    self.assertAlmostEqual(0.00924299255937, result[0], 8)
    self.assertAlmostEqual(0.000756285585593, result[1], 8)
    self.assertAlmostEqual(1.1960583568, result[-1], 8)
    
  def testExample1(self):
    symbols = ["AAPL", "GOOG", "XOM", "GLD"]
    startdate = dt.datetime(2011, 1, 1)
    enddate = dt.datetime(2011, 12, 31)
    allocation = [0.4, 0.0, 0.2, 0.4]
    result = s.simulate(startdate, enddate, symbols, allocation)
    self.printer(result)
    self.assertAlmostEqual(1.02828403099, result[2], 8)
    self.assertAlmostEqual(0.0101467067654, result[0], 8)
    self.assertAlmostEqual(0.000657261102001, result[1], 8)
    self.assertAlmostEqual(1.16487261965, result[-1], 8)

  def testExampleSPX2(self):
    symbols = ["AAPL", "GOOG", "XOM","$SPX"]
    startdate = dt.datetime(2011, 1, 14)
    enddate = dt.datetime(2011, 12, 14)
    allocation = [0.0, 0.0, 0.0, 1.0]
    result = s.simulate(startdate, enddate, symbols, allocation)
    self.printer(result)
    self.assertAlmostEqual(1.02828403099, result[2], 8)
    self.assertAlmostEqual(0.0101467067654, result[0], 8)
    self.assertAlmostEqual(0.000657261102001, result[1], 8)
    self.assertAlmostEqual(1.16487261965, result[-1], 8)


  def printer(self, result):
    print 'Printing result:'
    print 'Sharpe ratio: %.11f' % (result[2])
    print 'Volatility %.13f' % (result[0])
    print 'Average Daily Return %.15f' % (result[1])
    print 'Cumulatative return %.11f' % (result[-1])
    print ''
    
if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()