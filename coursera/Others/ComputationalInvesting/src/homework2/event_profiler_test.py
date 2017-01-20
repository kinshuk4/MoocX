'''
Created on 22 Mar 2013

@author: thomas
'''
import unittest
import event_profiler as ep
import datetime as dt

class Test(unittest.TestCase):


  def setUp(self):
    self.dt_start = dt.datetime(2008, 1, 1)
    self.dt_end = dt.datetime(2009, 12, 31)


  def tearDown(self):
    pass


  def test2012_5(self):
    count = ep.study(self.dt_start, self.dt_end, 'sp5002012', 'actual_close', 'EventStudy2012_5', 5.0)
    self.assertEqual(176.0, count, 'msg')

  def test2008_5(self):
    count = ep.study(self.dt_start, self.dt_end, 'sp5002008', 'actual_close', 'EventStudy2008_5', 5.0)
    self.assertEqual(326.0, count, 'msg')

  def test2008_7(self):
    count = ep.study(self.dt_start, self.dt_end, 'sp5002008', 'actual_close', 'EventStudy2008_7', 7.0)
    self.assertEqual(468.0, count, 'msg')

  def test2012_8(self):
    count = ep.study(self.dt_start, self.dt_end, 'sp5002012', 'actual_close', 'EventStudy2012_8', 8.0)
    self.assertEqual(375.0, count, 'msg')


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.test2012']
    unittest.main()