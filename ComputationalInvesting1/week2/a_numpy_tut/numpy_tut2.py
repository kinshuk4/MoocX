'''
Created on Sep 9, 2013

@author: kinshuk
'''

import numpy as np
from scipy.signal.waveforms import square

## When you use reshape(...) the total number of things in the array must stay
## the same. So reshaping an array with 2 rows and 3 columns into one with 
## 3 rows and 2 columns is fine, but 3x3 or 1x5 won't work
#print rangeArray.reshape( (3,3) ) #ERROR
squareArray = np.arange(1,10).reshape( (3,3) ) #this is fine, 9 elements
print squareArray


print "Accessing array elements"
print "element at 0,2-"+ str(squareArray[0,2])
print "element in row 0 and from col 1 to 2-"+ str(squareArray[0,1:2]) 

print squareArray[0:2,0:2] #[[1 2]  # the top left corner of squareArray
                           # [4 5]]
                           
print squareArray[:] #prints full array
print squareArray[:,1-3]#prints all the rows with column

## These ranges work just like slices and python lists. n:m:t specifies a range
## that starts at n, and stops before m, in steps of size t. If any of these 
## are left off, they're assumed to be the start, the end+1, and 1 respectively
print squareArray[:,0:3:2] #[[1 3]   #skip the middle column
                           # [4 6]
                           # [7 9]]

## Also like python lists, you can assign values to specific positions, or
## ranges of values to slices
squareArray[0,:] = np.array(range(1,4)) #set the first row to 1,2,3
squareArray[1,1] = 0                    # set the middle spot to zero
squareArray[2,:] = 1                    # set the last row to ones
print squareArray                       # [[1 2 3]
                                        #  [4 0 6]
                                        #  [1 1 1]]

## Something new to numpy arrays is indexing using an array of indices:
fibIndices = np.array( [1, 1, 2, 3] )
randomRow = np.random.random( (10,1) ) # an array of 10 random numbers
print randomRow
print randomRow[fibIndices] # the first, first, second and third element of
                             # randomRow 

## You can also use an array of true/false values to index:
boolIndices = np.array( [[ True, False,  True],
                          [False,  True, False],
                          [ True, False,  True]] )
print squareArray[boolIndices] # a 1D array with the selected values
                                # [1 3 0 1 1]

## It gets a little more complicated with 2D (and higher) arrays.  You need
## two index arrays for a 2D array:
rows = np.array( [[0,0],[2,2]] ) #get the corners of our square array
cols = np.array( [[0,2],[0,2]] )
print squareArray[rows,cols]     #[[1 3]
                                 # [1 1]]
boolRows = np.array( [False, True, False] ) # just the middle row
boolCols = np.array( [True, False, True] )  # Not the middle column
print squareArray[boolRows,boolCols]        # [4 6]

print "Operations on arrays"
## One useful trick is to create a boolean matrix based on some test and use
## that as an index in order to get the elements of a matrix that pass the
## test:
sqAverage = np.average(squareArray) # average(...) returns the average of all
                                    # the elements in the given array
betterThanAverage = squareArray > sqAverage
print betterThanAverage             #[[False False  True]
                                    # [ True False  True]
                                    # [False False False]]
print squareArray[betterThanAverage] #[3 4 6]