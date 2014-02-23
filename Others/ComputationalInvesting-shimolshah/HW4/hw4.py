import hw2
import datetime as dt
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.DataAccess as da
import csv
import marketsim



if __name__ == '__main__':
    dt_start = dt.datetime(2008, 1, 1)
    dt_end = dt.datetime(2009, 12, 31)
    #dt_end = dt.datetime(2008, 6, 30)
    ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))

    dataobj = da.DataAccess('Yahoo')
    ls_symbols = dataobj.get_symbols_from_list('sp5002012')
    # ls_symbols = dataobj.get_symbols_from_list('sp5002008')
    # ls_symbols.append('SPY')
    print "test1"
    ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
    ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
    print "test2"

    d_data = dict(zip(ls_keys, ldf_data))

    print "test3"
    for s_key in ls_keys:
        d_data[s_key] = d_data[s_key].fillna(method='ffill')
        d_data[s_key] = d_data[s_key].fillna(method='bfill')
        d_data[s_key] = d_data[s_key].fillna(1.0)

    df_events = hw2.find_events(ls_symbols, d_data)
    #print df_events
    
    marketsim.read_csv([0,'orders.csv'])
    marketsim.preprocess_raw_data()
    # print uniqueDates
    #print ""
    #print uniqueSymbols
    marketsim.perform_step2()
    # print d_data['close']['AAPL']
    marketsim.generate_trade_matrix(50000)
    print "Trading Matrix"
    # print trade_matrix
    marketsim.generate_holding_matrix()
    print "Holding Matrix"
    # print holding_matrix
    marketsim.calculate_returns()
    print "Value Matrix"
    # print values_matrix
    marketsim.write_csv('values.csv')
    
    