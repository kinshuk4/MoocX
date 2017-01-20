'''
Created on 22 Mar 2013

@author: thomas
'''
import unittest
import event_profiler as ep
import datetime as dt
import marketsim as ms
import analyse as an

class Test(unittest.TestCase):


  def setUp(self):
    self.dt_start = dt.datetime(2008, 1, 1, 16)
    self.dt_end = dt.datetime(2009, 12, 31, 16)


  def tearDown(self):
    pass


#   def test2012(self):
#     orders = 'orders1.csv'
#     valueFileName = 'values1.csv'
#     count = ep.study(self.dt_start, self.dt_end, 'sp5002012', 'actual_close', 'EventStudy2012', 5.0, 5, orders)
#     ms.run(50000, orders, valueFileName, 'close')
#     an.run(valueFileName, '$SPX')
#     #self.assertEqual(176.0, count, 'msg')

  def testQuiz1(self):
    print ("Q2")
    orders = 'orders_quiz2.csv'
    valueFileName = 'values_quiz2.csv'
    #7
    count = ep.study(self.dt_start, self.dt_end, 'sp5002012', 'actual_close', 'EventStudy2012', 7.0, 5, orders)
    ms.run(50000, orders, valueFileName, 'close')
    an.run(valueFileName, '$SPX')
    
# def testQuiz2(self):
#   orders = 'orders_quiz2.csv'
#   valueFileName = 'values_quiz2.csv'
#   #6.0
#   count = ep.study(self.dt_start, self.dt_end, 'sp5002012', 'actual_close', 'EventStudy2012', 7.0, 5, orders)
#   ms.run(50000, orders, valueFileName, 'close')
#   an.run(valueFileName, '$SPX')

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.test2012']
    unittest.main()