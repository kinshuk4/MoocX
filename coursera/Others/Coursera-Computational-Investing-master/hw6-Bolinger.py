import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da
import datetime as dt
import matplotlib.pyplot as plt
import pandas
from pylab import *

#
# Prepare to read the data
#
symbols = ["AAPL","GOOG","IBM","MSFT"]
startday = dt.datetime(2010,1,1)
endday = dt.datetime(2010,12,31)
timeofday=dt.timedelta(hours=16)
timestamps = du.getNYSEdays(startday,endday,timeofday)

dataobj = da.DataAccess('Yahoo')
voldata = dataobj.get_data(timestamps, symbols, "volume")
adjcloses = dataobj.get_data(timestamps, symbols, "close")
actualclose = dataobj.get_data(timestamps, symbols, "actual_close")

#adjcloses = adjcloses.fillna()
adjcloses = adjcloses.fillna(method='backfill')

rolling_mean = pandas.rolling_mean(adjcloses,20,min_periods=20)
rolling_std = pandas.rolling_std(adjcloses,20,min_periods=20)
bollinger_val = pandas.DataFrame(index=timestamps,columns=symbols)

for i in range(len(rolling_std[symbols[0]])):
    if rolling_std[symbols[0]][i] > 0:
        for sym in symbols:
            bollinger_val[sym][i] = (adjcloses[sym][i] - rolling_mean[sym][i]) / rolling_std[sym][i]

for stamp in timestamps:
    if stamp.month == 5 and stamp.day == 12:
        print bollinger_val.ix[stamp]

'''
# Plot the prices
plt.clf()

symtoplot = 'AAPL'
plot(adjcloses.index,adjcloses[symtoplot].values,label=symtoplot)
plot(adjcloses.index,means[symtoplot].values)
plt.legend([symtoplot,'Moving Avg.'])
plt.ylabel('Adjusted Close')

savefig("movingavg-ex.png", format='png')
'''
