

import pandas as pd
import numpy as np
import math
import copy
import QSTK.qstkutil.qsdateutil as du
import datetime as dt
import QSTK.qstkutil.DataAccess as da
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkstudy.EventProfiler as ep
import csv



if __name__ == '__main__':
    initial_cash = 100000
    current_cash = initial_cash
    o = open("hw7orders3.csv",'rU')
    o.seek(0)
    reader = csv.reader(o)
    symbols = list()
    date_boundary = [dt.date(2020,1,1),dt.date(1900,1,1)]
    # 1. Read CSV into 'trades' array, then scan it
    trades = list()
    # 0:year 1:month 2:day 3:symbol 4:trade 5:amount
    for row in reader:        
        year = int(row[0])
        month = int(row[1])
        day = int(row[2])
        symbol = row[3]
        trade = row[4]
        amount = int(row[5])
        trades.append([dt.date(year,month,day),symbol,trade,amount])
        if dt.date(year,month,day) < date_boundary[0]:
            date_boundary[0] = dt.date(year,month,day)
        if dt.date(year,month,day) > date_boundary[1]:
            date_boundary[1] = dt.date(year,month,day)
        if symbol not in symbols:
            symbols.append(symbol)
    current_own = list()
    #trades.sort()
    for i in range(len(symbols)):
        current_own.append(0)
    prev_cash = initial_cash
    prev_equity = current_own
    # 2. Read data
    dt_start = date_boundary[0]
    dt_end = date_boundary[1]
    dt_timeofday = dt.timedelta(hours=16)
    ldt_timestamps = du.getNYSEdays(dt_start,dt_end,dt_timeofday)
    
    c_dataobj = da.DataAccess('Yahoo')
    ls_keys = ['close']
    ldf_data = c_dataobj.get_data(ldt_timestamps, symbols, ls_keys)
    d_data = dict(zip(ls_keys, ldf_data))
    
    cash = list()
    own = list()
    portfolio = list()
    cash_daily = list()
    equity_daily = list()
    initial_own = list()
    for i in range(len(symbols)):
        initial_own.append(-1)
    for i in range(len(d_data['close'].index)):
        ts = d_data['close'].index[i]
        portfolio.append([ts.year,ts.month,ts.day,0])
        cash_daily.append([ts.year,ts.month,ts.day,0])
        equity_daily.append([ts.year,ts.month,ts.day,initial_own[0:len(initial_own)]])
    #initial_cash = 100000    
    portfolio[0] = [dt_start.year,dt_start.month,dt_start.day,100000]
    
    # 3. simulate the orders
    prevCount = -1
    for t in trades:
        current_date = t[0]
        current_symbol = t[1]
        current_col_idx = -1
        # get the symbol column index
        for i in range(len(symbols)):
            if symbols[i] == current_symbol:
                current_col_idx = i
                break    
        current_trade = t[2]
        current_amount = t[3]
        current_row_idx = -1
        # get the price data on that specific security on that specific day
        for i in range(len(d_data['close'].index)):
            ts = d_data['close'].index[i]
            that_date = dt.date(ts.year,ts.month,ts.day)
            if current_date == that_date:
                current_price = d_data['close'].get(current_symbol)[i]
                current_row_idx = i
                break
        # calculating the cash changes
        payment = current_price * current_amount;
        #print "date:   payment"
        #print current_date, payment
        if current_trade == 'BUY':
            current_cash = current_cash - payment
            cash.append((current_date,current_cash))            
            current_own[current_col_idx] = current_own[current_col_idx] + current_amount
            own.append((current_date,current_own[0:len(current_own)]))
            cash_daily[current_row_idx][3] = current_cash
            equity_daily[current_row_idx][3] = current_own[0:len(current_own)]
        elif current_trade == 'SELL':
            current_cash = current_cash + payment
            cash.append((current_date,current_cash))
            current_own[current_col_idx] = current_own[current_col_idx] - current_amount
            own.append((current_date,current_own[0:len(current_own)]))
            cash_daily[current_row_idx][3] = current_cash
            equity_daily[current_row_idx][3] = current_own[0:len(current_own)]
 
    # 4. fill out cash_daily data
    for i in range(len(cash_daily)):
        if i == 0:
            continue
        if cash_daily[i][3] == 0:
            cash_daily[i][3] = cash_daily[i-1][3]
    # 5. fill out equity_daily data
    for i in range(len(equity_daily)):
        if i == 0:
            continue
        if equity_daily[i][3] == initial_own:
            equity_daily[i][3] = equity_daily[i-1][3]
    
    for i in range(len(portfolio)):
        equity_value = 0
        for j in range(len(symbols)):
            equity_value = equity_value + d_data['close'].get(symbols[j])[i] * equity_daily[i][3][j]
        portfolio[i][3] = equity_value + cash_daily[i][3]
    # 6. write to CSV file  
    writer = csv.writer(file('hw7values3.csv','wb'))
    for line in portfolio:
        writer.writerow(line)
        

    
    #print "The final value of the portfolio using the sample file is " + str(cash[-1][1])
    #print "Details of the Performance of the portfolio :\n"
    #print "Data Range : " + str(date_boundary[0]) +" to "+str(date_boundary[1])
    
        
        
    
