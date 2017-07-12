
# VIDEO 2
## Read in data


    import pandas as pd
    import os
    os.chdir('C:\\Users\\iris\\Downloads\\analyric edge\\week2')
    baseball = pd.read_csv("baseball.csv")
    print baseball.dtypes
    print baseball.shape

    Team             object
    League           object
    Year              int64
    RS                int64
    RA                int64
    W                 int64
    OBP             float64
    SLG             float64
    BA              float64
    Playoffs          int64
    RankSeason      float64
    RankPlayoffs    float64
    G                 int64
    OOBP            float64
    OSLG            float64
    dtype: object
    (1232, 15)
    

## Subset to only include moneyball years
First create a logical vector of True and Falses
Then use the logical vector to subset the dataframe


    year = baseball['Year'] < 2002
    moneyball = baseball[year]
    print moneyball.shape

    (902, 15)
    

## Compute Run Difference
To avoid error message, use `column.copy()` rather than directly using `column`


    moneyball['RD'] = moneyball['RS'].copy() - moneyball['RA'].copy()
    print moneyball.dtypes

    Team             object
    League           object
    Year              int64
    RS                int64
    RA                int64
    W                 int64
    OBP             float64
    SLG             float64
    BA              float64
    Playoffs          int64
    RankSeason      float64
    RankPlayoffs    float64
    G                 int64
    OOBP            float64
    OSLG            float64
    RD                int64
    dtype: object
    

## Scatterplot to check for linear relationship


    moneyball.plot( x = 'RD', y = 'W', kind = 'scatter')




    <matplotlib.axes._subplots.AxesSubplot at 0xa9f0240>




![png](MoneyBall_files/MoneyBall_7_1.png)


## Regression model to predict wins
I will be using statsmodels for this linear regression model


    import statsmodels.formula.api as smf
    winsReg = smf.ols('W ~ RD', data = moneyball).fit()
    print winsReg.summary()

                                OLS Regression Results                            
    ==============================================================================
    Dep. Variable:                      W   R-squared:                       0.881
    Model:                            OLS   Adj. R-squared:                  0.881
    Method:                 Least Squares   F-statistic:                     6651.
    Date:                Tue, 10 Mar 2015   Prob (F-statistic):               0.00
    Time:                        19:52:09   Log-Likelihood:                -2515.5
    No. Observations:                 902   AIC:                             5035.
    Df Residuals:                     900   BIC:                             5045.
    Df Model:                           1                                         
    Covariance Type:            nonrobust                                         
    ==============================================================================
                     coef    std err          t      P>|t|      [95.0% Conf. Int.]
    ------------------------------------------------------------------------------
    Intercept     80.8814      0.131    616.675      0.000        80.624    81.139
    RD             0.1058      0.001     81.554      0.000         0.103     0.108
    ==============================================================================
    Omnibus:                        5.788   Durbin-Watson:                   2.076
    Prob(Omnibus):                  0.055   Jarque-Bera (JB):                5.736
    Skew:                          -0.195   Prob(JB):                       0.0568
    Kurtosis:                       3.033   Cond. No.                         101.
    ==============================================================================
    
    Warnings:
    [1] Standard Errors assume that the covariance matrix of the errors is correctly specified.
    

## Regression model to predict runs scored


    RunsReg = smf.ols('RS ~ OBP + SLG + BA', data = moneyball).fit()
    print RunsReg.summary()

                                OLS Regression Results                            
    ==============================================================================
    Dep. Variable:                     RS   R-squared:                       0.930
    Model:                            OLS   Adj. R-squared:                  0.930
    Method:                 Least Squares   F-statistic:                     3989.
    Date:                Tue, 10 Mar 2015   Prob (F-statistic):               0.00
    Time:                        19:55:24   Log-Likelihood:                -4170.2
    No. Observations:                 902   AIC:                             8348.
    Df Residuals:                     898   BIC:                             8368.
    Df Model:                           3                                         
    Covariance Type:            nonrobust                                         
    ==============================================================================
                     coef    std err          t      P>|t|      [95.0% Conf. Int.]
    ------------------------------------------------------------------------------
    Intercept   -788.4570     19.697    -40.029      0.000      -827.115  -749.799
    OBP         2917.4214    110.466     26.410      0.000      2700.619  3134.224
    SLG         1637.9277     45.994     35.612      0.000      1547.659  1728.197
    BA          -368.9661    130.580     -2.826      0.005      -625.244  -112.688
    ==============================================================================
    Omnibus:                        3.441   Durbin-Watson:                   1.943
    Prob(Omnibus):                  0.179   Jarque-Bera (JB):                3.381
    Skew:                           0.150   Prob(JB):                        0.184
    Kurtosis:                       3.018   Cond. No.                         214.
    ==============================================================================
    
    Warnings:
    [1] Standard Errors assume that the covariance matrix of the errors is correctly specified.
    


    RunsReg = smf.ols('RS ~ OBP + SLG', data = moneyball).fit()
    print RunsReg.summary()

                                OLS Regression Results                            
    ==============================================================================
    Dep. Variable:                     RS   R-squared:                       0.930
    Model:                            OLS   Adj. R-squared:                  0.929
    Method:                 Least Squares   F-statistic:                     5934.
    Date:                Tue, 10 Mar 2015   Prob (F-statistic):               0.00
    Time:                        19:55:49   Log-Likelihood:                -4174.2
    No. Observations:                 902   AIC:                             8354.
    Df Residuals:                     899   BIC:                             8369.
    Df Model:                           2                                         
    Covariance Type:            nonrobust                                         
    ==============================================================================
                     coef    std err          t      P>|t|      [95.0% Conf. Int.]
    ------------------------------------------------------------------------------
    Intercept   -804.6271     18.921    -42.526      0.000      -841.761  -767.493
    OBP         2737.7680     90.685     30.190      0.000      2559.790  2915.746
    SLG         1584.9086     42.156     37.597      0.000      1502.174  1667.643
    ==============================================================================
    Omnibus:                        3.099   Durbin-Watson:                   1.933
    Prob(Omnibus):                  0.212   Jarque-Bera (JB):                3.106
    Skew:                           0.143   Prob(JB):                        0.212
    Kurtosis:                       2.972   Cond. No.                         134.
    ==============================================================================
    
    Warnings:
    [1] Standard Errors assume that the covariance matrix of the errors is correctly specified.
    


    
