'''
Created on Mar 16, 2013

@author: kchandra
Assignment1,Question 1
The Sharpe ratio of the optimized portfolio using the parameter below lies in the range : 
Equities = ['C', 'GS', 'IBM', 'HNZ'] 
Start Date = 1st January, 2010 
End Date = 31st December, 2010 
'''
#Qstk imports
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da

# Third Party Imports
import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np;
from SharpeUtil import *

import math;


    
def GetSharpeMinMax(ls_symbols,dt_start,dt_end):
    min = 1
    max=-1
    neededAlloc =[]
    allAllocations =   GetAllAllocations() 
    print allAllocations   
    for i in range(1,len(allAllocations)):
        alloc = allAllocations[i]
        #print alloc
        sr = GetDailySharpeRatioForAllocation(ls_symbols,dt_start,dt_end,alloc)
        if(sr>max):
                max=sr
                neededAlloc = alloc
        print min,max  
     
    return min,max,neededAlloc


def main():

    # List of symbols
    #ls_symbols = ["AAPL", "GOOG", "MSFT", "IBM"]
    ls_symbols =  ['BRCM', 'TXN', 'AMD', 'ADI']  
    #ls_symbols = ['BRCM', 'ADBE', 'AMD', 'ADI']
    # Start and End date of the charts
    dt_start = dt.datetime(2011, 1, 1)
    dt_end = dt.datetime(2011, 12, 31)
    min,max,alloc = GetSharpeMinMax(ls_symbols,dt_start,dt_end)
    print min,max,alloc

   
    
    #print min,max
    
    #GetSharpeRatioForAllocation(ls_symbols,dt_start,dt_end,Allocation1)

    
if __name__ == '__main__':
    main()



