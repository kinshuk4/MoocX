'''
Created on Sep 9, 2013

@author: kchandra
'''


# Third Party Imports
import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd

print "Pandas Version", pd.__version__


def main():
    ''' Main Function'''

    # List of symbols, $SPX means standard and poor index, rest are company stocks
    ls_symbols = ["AAPL", "GLD", "GOOG", "$SPX", "XOM"]

    # Start and End date of the charts
    dt_start = dt.datetime(2006, 1, 1)
    dt_end = dt.datetime(2010, 12, 31)

    # We need closing prices so the timestamp should be hours=16. i.e.4 pm 
    dt_timeofday = dt.timedelta(hours=16)
    
    print ls_symbols
    print dt_start
    print dt_end
    print dt_timeofday
    
if __name__ == '__main__':
    main()