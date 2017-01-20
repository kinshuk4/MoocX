'''
Created on 12 Apr 2013

@author: thomas
'''
import unittest
import bollinger2 as bo
import datetime as dt

class Test(unittest.TestCase):


  def setUp(self):
    self.start = dt.datetime(2010,1,1)
    self.end = dt.datetime(2010,6,16) 
    self.key = 'close'
    self.loopBack = 20

  def tearDown(self):
    pass
  
#  def testGOOG(self):
#    bo.run(self.start, self.end, self.loopBack, ['GOOG'], self.key, 1.0)

 # def testVZ(self):
 #   bo.run(self.start, self.end, self.loopBack, ['VZ'], self.key, 2.0)

  def testAAPL(self):
    bo.run(self.start, self.end, self.loopBack, ['MSFT'], self.key, 1.0)
    
  #def testAAPL(self):
   # bo.run(self.start, self.end, self.loopBack, ['AAPL', 'GOOG', 'IBM', 'MSFT'], self.key)

if __name__ == "__main__":
  #import sys;sys.argv = ['', 'Test.testName']
  unittest.main()