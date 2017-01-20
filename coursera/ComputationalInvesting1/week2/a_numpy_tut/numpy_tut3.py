'''
Created on Sep 9, 2013

@author: kchandra
'''
import numpy as np

squareArray = np.arange(1,10).reshape( (3,3) ) #this is fine, 9 elements

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



## Indexing like this can also be used to assign values to elements of the
## array. This is particularly useful if you want to filter an array, say by 
## making sure that all of its values are above/below a certain threshold:
sqStdDev = np.std(squareArray) # std(...) returns the standard deviation of
                               # all the elements in the given array
clampedSqArray = np.array(squareArray.copy(), dtype=float) 
                                    # make a copy of squareArray that will
                                    # be "clamped". It will only contain
                                    # values within one standard deviation
                                    # of the mean. Values that are too low
                                    # or to high will be set to the min
                                    # and max respectively. We set 
                                    # dtype=float because sqAverage
                                    # and sqStdDev are floating point
                                    # numbers, and we don't want to 
                                    # truncate them down to integers.
clampedSqArray[ (squareArray-sqAverage) > sqStdDev ] = sqAverage+sqStdDev
clampedSqArray[ (squareArray-sqAverage) < -sqStdDev ] = sqAverage-sqStdDev
print clampedSqArray # [[ 1.          2.          3.        ]
                     #  [ 3.90272394  0.31949828  3.90272394]
                     #  [ 1.          1.          1.        ]]


## Multiplying and dividing arrays by numbers does what you'd expect. It
## multiples/divides element-wise
print squareArray * 2 # [[ 2  4  6]
                      #  [ 8  0 12]
                      #  [ 2  2  2]]

## Addition works similarly:
print squareArray + np.ones( (3,3) ) #[[2 3 4]
                                     # [5 1 7]
                                     # [2 2 2]]

## Multiplying two arrays together (of the same size) is also element wise
print squareArray * np.arange(1,10).reshape( (3,3) ) #[[ 1  4  9]
                                                     # [16  0 36]
                                                     # [ 7  8  9]]

## Unless you use the dot(...) function, which does matrix multiplication
## from linear algebra:
matA = np.array( [[1,2],[3,4]] )
matB = np.array( [[5,6],[7,8]] )
print np.dot(matA,matB) #[[19 22]
                        # [43 50]]

## And thats it! There's a lot more to the numpy library, and there are a few
## things I skipped over here, such as what happens when array dimensions
## don't line up when you're indexing or multiplying them together, so if 
## you're interested, I strongly suggest you head over to the scipy wiki's
## numpy tutorial for a more in depth look at using numpy arrays:
##
##            http://www.scipy.org/Tentative_NumPy_Tutorial