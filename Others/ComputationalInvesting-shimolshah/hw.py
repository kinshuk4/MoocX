# QSTK Imports
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da

# Third Party Imports
import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

print "Hello"

def main1():
     startdate = dt.datetime(2011, 1, 1)
     enddate = dt.datetime(2011,12,31)
     vol, daily_ret, sharpe, cum_ret = simulate (startdate,enddate, ['AAPL','GLD','GOOG','XOM'], [0.4,0.4,0.0,0.2], True)
     
def main():
    print "Test"
#    startdate = dt.datetime(2011, 1, 1)
#    enddate = dt.datetime(2011,12,31)
#    vol, daily_ret, sharpe, cum_ret = simulate (startdate,enddate, ['AAPL','GLD','GOOG','XOM'], [0.4,0.4,0.0,0.2])
    #startdate = dt.datetime(2011, 1, 1)
    #enddate = dt.datetime(2011,12,31)
    #startdate = dt.datetime(2010,1,1)
    #enddate = dt.datetime(2010, 12,31)
    startdate = dt.datetime(2011,1,1)
    enddate = dt.datetime(2011, 12,31)
    # symbols = ['AXP','HPQ', 'IBM', 'HNZ']
    # symbols = ['BRCM','TXN','IBM','HNZ']
    symbols = ['AAPL','GOOG','IBM','MSFT']
    # driver(startdate, enddate, ['AXP','HPQ','IBM','HNZ'])
    # distribute (['AXP','HPQ','IBM','HNZ'], 1)
    allocation_list = distribute (symbols, 1.0, "  ")
    print allocation_list

    max_sharpe = 0.0
    max_allocation = []


    dt_timeofday = dt.timedelta(hours=16)
    ldt_timestamps = du.getNYSEdays(startdate, enddate, dt_timeofday)
    c_dataobj = da.DataAccess('Yahoo', cachestalltime=0)
    # Keys to be read from the data, it is good to read everything in one go.
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    ldf_data = c_dataobj.get_data(ldt_timestamps, symbols, ls_keys)
    d_data = dict(zip(ls_keys, ldf_data))

    for allocation in allocation_list:
        print allocation

        vol, daily_ret, sharpe, cum_ret = simulate (startdate,enddate, symbols, allocation, False, d_data)
        if sharpe >= max_sharpe:
            max_sharpe = sharpe
            max_allocation = allocation
            
    print "Best allocation: ", max_allocation, " with Maximum sharpe: ", max_sharpe



def distribute (symbols, allocation, space):
    symbols = list(symbols)
#    print space, len(symbols)
#    print space, allocation
    return_list = []
    symbols.pop(0)
    for i in range (0, (int)(allocation*10)+1):
        if len(symbols) >=1:
            sub_allocation_array = distribute(symbols, allocation - i/10.0, space + "  ")
            for sub_allocation in sub_allocation_array:
#                print space, sub_allocation
                temp = [round(i/10.0,1)] + sub_allocation
                return_list += [temp]
        else:
            return_list.append([round(allocation,1)])
            break
    
 #   print space, return_list
    return return_list

def driver(startdate, enddate, symbols):
    allocation = []
    for i in range(0,11):
        if i == 10:
            allocation = [1.0, 0.0, 0.0, 0.0]
            print allocation
            break;
        for j in range(0,11):
            if i+j == 10:
                allocation = [i/10.0, j/10.0, 0.0, 0.0]
                print allocation
                break;
            for k in range(0,11):
                if i+j+k == 10:
                    allocation = [i/10.0, j/10.0, k/10.0, 0.0]
                    print allocation
                    break
                for l in range(0,11):
                    if i+j+k+l ==10:
                        allocation = [i/10.0, j/10.0, k/10.0, l/10.0]
                        print allocation;
                        break;
                    else:
                        continue;
    
    #    vol, daily_ret, sharpe, cum_ret = simulate (startdate,enddate, symbols, [0.0,0.0,0.0,1.0])
    
    #print vol
    #print daily_ret
    #print sharpe
    #print cum_ret
    

def simulate(startdate, enddate, symbols, allocs, chart, d_data):
    dt_timeofday = dt.timedelta(hours=16)
    ldt_timestamps = du.getNYSEdays(startdate, enddate, dt_timeofday)

    c_dataobj = da.DataAccess('Yahoo', cachestalltime=0)

    # Keys to be read from the data, it is good to read everything in one go.
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']

#    ldf_data = c_dataobj.get_data(ldt_timestamps, symbols, ls_keys)
#    d_data = dict(zip(ls_keys, ldf_data))
    
    ldf_data_chart = {}
    if chart:
        ldf_data_chart = c_dataobj.get_data(ldt_timestamps, ["AAPL" , "INTC"], ["close"])
        d_data_chart = dict(zip(["close"], ldf_data_chart))
        #for s_key in ['close']:
        #    d_data_chart[s_key] = d_data_chart[s_key].fillna(method='ffill')
        #    d_data_chart[s_key] = d_data_chart[s_key].fillna(method='bfill')
        #    d_data_chart[s_key] = d_data_chart[s_key].fillna(1.0)
        na_price = d_data_chart['close'].values
        print na_price
        print [1,2]

    #print ldt_timestamps

    stock = {}
    absolute_value = {}
    adjusted_return = {}

    for idx, symbol in enumerate(symbols):
        stock[symbol]= allocs[idx] * 100 / d_data['close'][symbol][ldt_timestamps[0]]

    previous_total = 0
    chart_data = ldf_data_chart
    for timestamp in d_data['close'][symbols[0]].keys():
        total = 0

        for idx, symbol in enumerate(symbols):
            total = total + d_data['close'][symbol][timestamp] * stock[symbol]

        if previous_total == 0 :
            return_percentage = 0
        else:
            return_percentage = total/previous_total - 1
        previous_total = total
        absolute_value[timestamp] = total 
        adjusted_return[timestamp] = return_percentage

#    print "Average return: ", np.average(adjusted_return.values())
#    print "Standard deviation: ", np.std(adjusted_return.values())

#    print "Sharpe ratio: ", np.sqrt(250)* np.average(adjusted_return.values()) / np.std(adjusted_return.values())
#    print "Total return: ", (absolute_value[sorted(absolute_value.keys())[len(absolute_value.keys())-1]]/absolute_value[sorted(absolute_value.keys())[0]] - 1)
    
    #print adjusted_return

    if chart:
        # Plotting the prices with x-axis=timestamps
        plt.clf()
        plt.plot(ldt_timestamps, na_price)
        plt.legend(["MyFund", "$SPY"])
        plt.ylabel('Adjusted Close')
        plt.xlabel('Date')
        plt.savefig('hw1chart.pdf', format='pdf')
            

    return np.std(adjusted_return.values()), np.average(adjusted_return.values()), np.sqrt(250)* np.average(adjusted_return.values()) / np.std(adjusted_return.values()), (absolute_value[sorted(absolute_value.keys())[len(absolute_value.keys())-1]]/absolute_value[sorted(absolute_value.keys())[0]] - 1)
    
if __name__ == '__main__':
    main()
