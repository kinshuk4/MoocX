
# The Analytic Edge Lecture code in Python
## With pandas, numpy
# Week 2 VIDEO 4
## Read in data


    import os
    import pandas as pd
    
    os.chdir('C:\\Users\\iris\\Downloads\\analyric edge\\week2')
    wine = pd.read_csv("wine.csv")
    print wine.dtypes

    Year             int64
    Price          float64
    WinterRain       int64
    AGST           float64
    HarvestRain      int64
    Age              int64
    FrancePop      float64
    dtype: object
    


    print wine.describe()

                  Year      Price  WinterRain       AGST  HarvestRain        Age  \
    count    25.000000  25.000000   25.000000  25.000000    25.000000  25.000000   
    mean   1965.800000   7.067224  605.280000  16.509336   148.560000  17.200000   
    std       7.691987   0.650341  132.277965   0.675397    74.419464   7.691987   
    min    1952.000000   6.204900  376.000000  14.983300    38.000000   5.000000   
    25%    1960.000000   6.518800  536.000000  16.200000    89.000000  11.000000   
    50%    1966.000000   7.121100  600.000000  16.533300   130.000000  17.000000   
    75%    1972.000000   7.495000  697.000000  17.066700   187.000000  23.000000   
    max    1978.000000   8.493700  830.000000  17.650000   292.000000  31.000000   
    
              FrancePop  
    count     25.000000  
    mean   49694.436760  
    std     3665.270243  
    min    43183.569000  
    25%    46583.995000  
    50%    50254.966000  
    75%    52894.183000  
    max    54602.193000  
    

## Linear Regression (one variable)
I will try to use scikit-learn to do modeling in Python
Notice the scikit learn expect X to be a 2-D matrix rather than an 1-D array
when fitting the model
So simply using `clf.fit(wine['AGST'], wine['Price'])` won't work


    from sklearn import linear_model
    model1 = linear_model.LinearRegression()
    model1.fit(np.array(wine['AGST'])[:,np.newaxis], wine['Price'])




    LinearRegression(copy_X=True, fit_intercept=True, normalize=False)



Or equvalently


    model1.fit(np.matrix([wine['AGST'].as_matrix()]).transpose(), wine['Price'])




    LinearRegression(copy_X=True, fit_intercept=True, normalize=False)



There is no simple way to output a detailed summary of the model in scikit-learn
like `summary(model)` in R
You can get the intercept and coefficients of the fitted model like this:


    print model1.coef_ ,  model1.intercept_

    [ 0.63509431] -3.41776131349
    

## Sum of Squared Errors


    # make prediction on the training data
    model1Pred = model1.predict(np.array(wine['AGST'])[:,np.newaxis]) 
    # calculating and printing out SSE
    print sum(( model1Pred - wine['Price'])**2)

    5.73487514702
    

If you really wanted detailed statistical summary of the model, take a look at
`statsmodels` rather than `scikit-learn`

## Linear Regression (two variables)


    model2 = linear_model.LinearRegression()
    model2.fit(wine[['AGST','HarvestRain']], wine['Price'])




    LinearRegression(copy_X=True, fit_intercept=True, normalize=False)




    print model2.coef_ ,  model2.intercept_

    [ 0.60261691 -0.00457006] -2.20265360095
    


    print sum(( model2.predict(wine[['AGST','HarvestRain']]) - wine['Price'])**2)

    2.97037334017
    

## Linear Regression (all variables)
Store the predictors in a list


    predictors3 = ['AGST', 'HarvestRain', 'WinterRain', 'Age', 'FrancePop']
    model3 = linear_model.LinearRegression()
    model3.fit(wine[predictors3], wine['Price'])




    LinearRegression(copy_X=True, fit_intercept=True, normalize=False)




    print model3.coef_ ,  model3.intercept_

    [  6.01223884e-01  -3.95812450e-03   1.04250681e-03   5.84748489e-04
      -4.95273038e-05] -0.450398864395
    

I guess I should produce a nicer printing...


    ts = zip(predictors3, model3.coef_)
    ts.append(('Intercept', model3.intercept_))
    for t in ts:
        print "%11s" % t[0], t[1]

           AGST 0.601223883846
    HarvestRain -0.00395812450088
     WinterRain 0.00104250681324
            Age 0.000584748489476
      FrancePop -4.9527303815e-05
      Intercept -0.450398864395
    


    print sum(( model3.predict(wine[predictors3]) - wine['Price'])**2)

    1.73211271534
    

# VIDEO 5
## Remove FrancePop


    predictors4 = ['AGST', 'HarvestRain', 'WinterRain', 'Age']
    model4 = linear_model.LinearRegression()
    model4.fit(wine[predictors4], wine['Price'])




    LinearRegression(copy_X=True, fit_intercept=True, normalize=False)




    ts = zip(predictors4, model4.coef_)
    ts.append(('Intercept', model4.intercept_))
    for t in ts:
        print "%11s" % t[0], t[1]

           AGST 0.607209347684
    HarvestRain -0.00397153413288
     WinterRain 0.00107550528687
            Age 0.0239308322336
      Intercept -3.42998018693
    

# VIDEO 6
## Correlations
Pearson correlation coefficient between two variables


    import numpy as np
    print np.corrcoef(wine['WinterRain'], wine['Price'])[0,1]

    0.136650547388
    


    print np.corrcoef(wine['Age'], wine['FrancePop'])[0,1]

    -0.994485097111
    

Correlation Matrix


    print wine.corr()

                     Year     Price  WinterRain      AGST  HarvestRain       Age  \
    Year         1.000000 -0.447768    0.016970 -0.246916     0.028009 -1.000000   
    Price       -0.447768  1.000000    0.136651  0.659563    -0.563322  0.447768   
    WinterRain   0.016970  0.136651    1.000000 -0.321091    -0.275441 -0.016970   
    AGST        -0.246916  0.659563   -0.321091  1.000000    -0.064496  0.246916   
    HarvestRain  0.028009 -0.563322   -0.275441 -0.064496     1.000000 -0.028009   
    Age         -1.000000  0.447768   -0.016970  0.246916    -0.028009  1.000000   
    FrancePop    0.994485 -0.466862   -0.001622 -0.259162     0.041264 -0.994485   
    
                 FrancePop  
    Year          0.994485  
    Price        -0.466862  
    WinterRain   -0.001622  
    AGST         -0.259162  
    HarvestRain   0.041264  
    Age          -0.994485  
    FrancePop     1.000000  
    

# VIDEO 7
## Read in test set


    wineTest = pd.read_csv("wine_test.csv")
    wineTest.dtypes




    Year             int64
    Price          float64
    WinterRain       int64
    AGST           float64
    HarvestRain      int64
    Age              int64
    FrancePop      float64
    dtype: object



## Make test set predictions


    predictTest = model4.predict(wineTest[predictors4])
    print predictTest

    [ 6.76892463  6.6849104 ]
    

## Compute R-squared


    SSE = sum((predictTest - wineTest['Price'])**2)
    SST = sum((wineTest['Price'] - mean(wineTest['Price']))**2)
    print 1 - SSE/SST

    0.334390470275
    


    print model4.score(wineTest[predictors4], wineTest['Price'])

    0.334390470275
    
