from qstkutil import DataAccess as da
import numpy as np
import copy
import qstkutil.qsdateutil as du
import datetime as dt
import qstkstudy.EventProfiler as ep

################ GLOBAL ######################

dataobj = da.DataAccess('Yahoo')

startday = dt.datetime(2008,1,1)
endday = dt.datetime(2009,12,31)

timeofday=dt.timedelta(hours=16)
timestamps = du.getNYSEdays(startday,endday,timeofday)

actual_close_field = "actual_close"

################ METHODS ######################

def getData():

    symbols = dataobj.get_symbols_from_list("sp5002012")
    symbols.append('SPY')
    return symbols


def findEvents(symbols, closeData):

    eventmatrix = copy.deepcopy(closeData)

    for symbol in symbols:
        for time in timestamps:
            eventmatrix[symbol][time]=np.NAN

    for symbol in symbols:
        for i in range(1,len(closeData[symbol])):
            if closeData[symbol][timestamps[i-1]] >= 10:
                if closeData[symbol][timestamps[i]] < 10:
                    eventmatrix[symbol][i] = 1.0

    return eventmatrix


def loadData(symbols):

    close = dataobj.get_data(timestamps, symbols, actual_close_field)
    return close

def main():

    symbolsData = getData()
    closeData = loadData(symbolsData)
    eventsData = findEvents(symbolsData, closeData)
    eventProfilerData = ep.EventProfiler(eventsData,startday,endday,lookback_days=20,lookforward_days=20,verbose=True)
    eventProfilerData.study(filename="Events.pdf",plotErrorBars=True,plotMarketNeutral=True,plotEvents=False,marketSymbol='SPY')

if __name__ == '__main__':
    main()
