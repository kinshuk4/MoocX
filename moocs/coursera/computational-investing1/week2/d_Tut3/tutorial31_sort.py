'''
(c) 2011, 2012 Georgia Tech Research Corporation
This source code is released under the New BSD license.  Please see
http://wiki.quantsoftware.org/index.php?title=QSTK_License
for license details.

Created on January, 24, 2013

@author: Sourabh Bajaj
@contact: sourabhbajaj@gatech.edu
@summary: Example tutorial code.
'''

# QSTK Imports
import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da

# Third Party Imports
import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


def main():
    ''' Main Function'''
    # Reading the portfolio
    na_portfolio = np.loadtxt('tutorial3portfolio.csv', dtype='S5,f4',
                        delimiter=',', comments="#", skiprows=1)
    print na_portfolio

    # Sorting the portfolio by symbol name
    na_portfolio = sorted(na_portfolio, key=lambda x: x[0])
    print na_portfolio


if __name__ == '__main__':
    main()
