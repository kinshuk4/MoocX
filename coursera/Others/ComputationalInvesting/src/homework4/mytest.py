'''
Created on 3 Apr 2013

@author: thomas
'''
import unittest
import datetime as dt
import QSTK.qstkutil.qsdateutil as du
import numpy as np
import pandas as pd
import csv

class Test(unittest.TestCase):


  def setUp(self):
    pass


  def tearDown(self):
    pass

  def pandaText(self):
    pass

  def testName(self):
    date = dt.datetime(2010,10,17,16)
    d = du.getNYSEoffset(date, 5)
    self.assertEqual(date, d)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
  unittest.main()