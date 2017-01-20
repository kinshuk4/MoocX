'''
Created on 28 Mar 2013

@author: thomas
'''
import unittest
import marketsim as ms

class Test(unittest.TestCase):


  def test(self):
    orders = None
    values = None  
    ms.run(1000000, orders, values)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()