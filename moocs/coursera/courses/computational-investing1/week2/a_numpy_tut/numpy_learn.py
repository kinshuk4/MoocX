'''
Created on Sep 9, 2013

@author: kchandra
'''
import numpy as np
import scipy as sc

#getting a 1d zero array
zeroArray = np.zeros(5)
print zeroArray;

#1d array of int zeros
zeroArray = np.zeros((5,), dtype=np.int)
print zeroArray

#2X3 zero array
zeroArray = np.zeros((2,3))
print zeroArray

#array of ones
oneArray = np.ones(5)
print oneArray

oneArray2d = np.ones((2,3))
print oneArray2d
#accessing element
print oneArray2d[1,1]

#random array
#randomArr = np.random(5)
#print randomArr

#creating array by hand
my_array = [1, 'rebecca', 'allard', 15]
print my_array

my_2dArray = [[1,2,3],
              [4,5,6]]
print my_2dArray


#range array
rangeArr = np.arange(6,12)
print rangeArr


#reshaping the array
reshapedArr = rangeArr.reshape((2,3))
print reshapedArr
#range array is still the same
print rangeArr


#reshaping the reshaped
reshapedArr=reshapedArr.reshape((3,2))
print reshapedArr




