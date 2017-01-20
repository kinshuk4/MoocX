'''
Created on 12 Apr 2013

@author: thomas
'''
import matplotlib.pyplot as plt
import pandas as pd
import datetime as dt
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.DataAccess as da
from math import sqrt

if __name__ == '__main__':
  
  x = range(100)
  y = [sqrt(i) for i in x]
  y2 = [sqrt(3 * sqrt(i)) for i in x]
  p = pd.DataFrame(y, y2)
  ##p.plot()
  
  plt.plot(x,y,color='k',lw=2)
  plt.plot(x,y2)
  plt.fill_between(x,y,y2,color='0.8')
  plt.legend(('Model length', 'Data length', 'Total message length'),
           'upper center', shadow=True)
  plt.show()