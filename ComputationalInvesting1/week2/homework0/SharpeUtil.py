'''
Created on Mar 16, 2013

@author: kchandra
Gets the sharpe ratio
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

import math;


def GetAllAllocations():
    possibleAllocation=[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1]
    allAllocations=Get4Sum(possibleAllocation,1.0)
    return allAllocations
            
def Get4Sum(arr, target):
    sortedArr = sorted(arr)
    allAllocations=[[]]
    n = len(sortedArr)
    for i in range(0,n):
       for j in range(0,n):
        for k in range(0,n):
            begin=0
            end = n
            val = target - sortedArr[i]-sortedArr[j]-sortedArr[k]
            
            while begin<end:
                mid=(begin+end)/2
                if arr[mid] == val:
                    currentAll = [sortedArr[i],sortedArr[j],sortedArr[k],sortedArr[mid]]
                    
                    allAllocations.append(currentAll)
                    break;
                elif arr[mid]<val:
                    begin=mid+1
                else:
                    end=mid-1
    return allAllocations        
                
            

        
         
                
  
def GetDailyReturn(na_total_return):
    na_total_return_copy = na_total_return.copy()
    na_total_return_copy[0] = 0
    #today/yesterday -1 or (today-yesterday)/yesterday
    for i  in range(1,len(na_total_return)):
        na_total_return_copy[i] = (na_total_return[i]-na_total_return[i-1])/na_total_return[i-1]
        
    #print na_total_return_copy
    return na_total_return_copy;

                                  
def GetDailySharpeRatioForAllocation(ls_symbols, dt_start,dt_end,Allocation):
    #print ls_symbols;
        # Creating an object of the dataaccess class with Yahoo as the source.
    c_dataobj = da.DataAccess('Yahoo')

    
    # We need closing prices so the timestamp should be hours=16.
    dt_timeofday = dt.timedelta(hours=16)

    # Get a list of trading days between the start and the end.
    ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt_timeofday)


    # Keys to be read from the data, it is good to read everything in one go.
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']

    # Reading the data, now d_data is a dictionary with the keys above.
    # Timestamps and symbols are the ones that were specified before.
    ldf_data = c_dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
    d_data = dict(zip(ls_keys, ldf_data))
    
    # Getting the numpy ndarray of close prices.
    na_price = d_data['close'].values
    #print na_price
  
    
    na_normalized_price = na_price / na_price[0, :]
#     print "na_normalized_price"
#     print na_normalized_price
    
    sharpe_constant=math.sqrt(252)
    
    na_normalized_price_per_allocation =na_normalized_price*Allocation
#     print "na_normalized_price_per_allocation"
#     print na_normalized_price_per_allocation
    
    na_total_return =  na_normalized_price_per_allocation.sum(axis=1)
#     print "na_total_return"
#     print na_total_return
    
    na_daily_return = GetDailyReturn(na_total_return)
    mean = np.mean(na_daily_return)  #na_daily_return.mean();
    std_dev= np.std(na_daily_return) #na_daily_return.std_dev();
    sharpe_ratio = sharpe_constant*(mean/std_dev)
#     print sharpe_ratio,Allocation
    #print len(na_total_return);
    return sharpe_ratio


def main():

    list = GetAllAllocations()
#     print list
    # List of symbols
    #ls_symbols = ["AAPL", "GOOG", "MSFT", "IBM"]
    ls_symbols = ['C', 'GS', 'IBM', 'HNZ'] 
    #ls_symbols = ['BRCM', 'ADBE', 'AMD', 'ADI']
    # Start and End date of the charts
#     dt_start = dt.datetime(2010, 1, 1)
#     dt_end = dt.datetime(2010, 12, 31)
#     
#     Allocation1=[0.1, 0.1, 0.1, 0.7]
# 
#    
#     
#     #print min,max
#     
#     GetSharpeRatioForAllocation(ls_symbols,dt_start,dt_end,Allocation1)

    
if __name__ == '__main__':
    main()



