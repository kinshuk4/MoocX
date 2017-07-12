
# Unit 3, Modeling the Expert
## Video 4
## Read in dataset


    import os
    os.chdir('C:\Users\Violetta_Chen\Downloads')
    import pandas as pd
    quality = pd.read_csv('quality.csv')

## Look at structure


    def dfStr(df):
        print "The dataframe contains {0} rows and {1} columns".format(df.shape[0], df.shape[1])
        print "The data types of columns are: \n"
        print df.dtypes
    
    dfStr(quality)

    The dataframe contains 131 rows and 14 columns
    The data types of columns are: 
    
    MemberID                  int64
    InpatientDays             int64
    ERVisits                  int64
    OfficeVisits              int64
    Narcotics                 int64
    DaysSinceLastERVisit    float64
    Pain                      int64
    TotalVisits               int64
    ProviderCount             int64
    MedicalClaims             int64
    ClaimLines                int64
    StartedOnCombination       bool
    AcuteDrugGapSmall         int64
    PoorCare                  int64
    dtype: object
    

## Table outcome


    quality.groupby('PoorCare').size()




    PoorCare
    0           98
    1           33
    dtype: int64



## Baseline accuracy


    98.0/131




    0.7480916030534351



## Create training and testing sets
I will be using `train_test_split` from scikit-learn to split the dataset into
training and testing sets
The data types returned by `train_test_split` in default is `object`, thus you
need to convert them back when creating dataframes using `pandas`
Also simply using `train_test_split` on the entire dataframe will not enforce
the ration of 1 and 0 in the dependent variable in the splitted sets. So it
should be done separately.


    from sklearn.cross_validation import train_test_split
    train0, test0 = train_test_split(quality.loc[quality['PoorCare']==0,:], train_size=0.75, random_state=88)
    train1, test1 = train_test_split(quality.loc[quality['PoorCare']==1,:], train_size=0.75, random_state=88)
    qualityTrain = pd.DataFrame(np.vstack((train0,train1)), columns=quality.columns).convert_objects(convert_numeric=True)
    qualityTest = pd.DataFrame(np.vstack((test0,test1)), columns=quality.columns).convert_objects(convert_numeric=True)

## Logistic Regression Model Using `statsmodels`
Need to create a dummy variable for intercept


    qualityTrain['Intercept'] = 1
    import statsmodels.api as sm
    QualityLog = sm.Logit(qualityTrain['PoorCare'], qualityTrain[['OfficeVisits','Narcotics','Intercept']]).fit()
    print QualityLog.summary()

    Optimization terminated successfully.
             Current function value: 0.389286
             Iterations 7
                               Logit Regression Results                           
    ==============================================================================
    Dep. Variable:               PoorCare   No. Observations:                   97
    Model:                          Logit   Df Residuals:                       94
    Method:                           MLE   Df Model:                            2
    Date:                Wed, 18 Mar 2015   Pseudo R-squ.:                  0.3042
    Time:                        09:55:21   Log-Likelihood:                -37.761
    converged:                       True   LL-Null:                       -54.270
                                            LLR p-value:                 6.762e-08
    ================================================================================
                       coef    std err          z      P>|z|      [95.0% Conf. Int.]
    --------------------------------------------------------------------------------
    OfficeVisits     0.0715      0.031      2.341      0.019         0.012     0.131
    Narcotics        0.1774      0.064      2.764      0.006         0.052     0.303
    Intercept       -2.8730      0.574     -5.003      0.000        -3.998    -1.748
    ================================================================================
    

## Make predictions on training set


    predictTrain =  QualityLog.predict()
    print predictTrain

    [ 0.21409682  0.20792723  0.15513627  0.15513627  0.16952904  0.09102833
      0.11761437  0.09102833  0.12095314  0.10356925  0.0612244   0.57347677
      0.12123395  0.09711829  0.11382242  0.13328096  0.09711829  0.0612244
      0.08528419  0.14175685  0.1366838   0.09102833  0.16005598  0.13699543
      0.11013747  0.29329533  0.2938425   0.11382242  0.1964003   0.12876152
      0.29493862  0.31685708  0.07987066  0.14175685  0.07987066  0.12095314
      0.11013747  0.06997534  0.10017872  0.08528419  0.07987066  0.5850803
      0.21994553  0.20705952  0.20792723  0.36253852  0.18022127  0.11761437
      0.10680892  0.28513498  0.19102437  0.2867511   0.08528419  0.09391682
      0.1290578   0.09102833  0.16473785  0.07718914  0.43083676  0.16990082
      0.07459035  0.11382242  0.27235695  0.12095314  0.12523791  0.25237953
      0.05350486  0.07477268  0.08528419  0.07224887  0.1290578   0.05350486
      0.09711829  0.99921197  0.95532822  0.98353566  0.602321    0.266104
      0.14175685  0.99815848  0.42370491  0.11013747  0.4688101   0.99993188
      0.21994553  0.15513627  0.10332455  0.92141417  0.12523791  0.11355656
      0.09081026  0.19061698  0.95532822  0.57347677  0.96700293  0.13297648
      0.93300705]
    

## Analyze predictions
I am taking advantage of the `describe()` and `groupby()` functions of
`DataFrame` objects to do the calculations.


    print pd.DataFrame(predictTrain).describe()

                   0
    count  97.000000
    mean    0.247423
    std     0.260452
    min     0.053505
    25%     0.097118
    50%     0.133281
    75%     0.266104
    max     0.999932
    


    df = pd.DataFrame({'predictTrain':predictTrain, 'PoorCare':qualityTrain['PoorCare']})
    df.groupby('PoorCare')['predictTrain'].mean()




    PoorCare
    0           0.158482
    1           0.517951
    Name: predictTrain, dtype: float64



# Video 5
## Confusion matrix for threshold of 0.5
A little bit trickier to do the table


    df['boolean'] = df['predictTrain'] >= 0.5
    print df.groupby(['PoorCare','boolean']).size()

    PoorCare  boolean
    0         False      71
              True        2
    1         False      13
              True       11
    dtype: int64
    

## Sensitivity and specificity


    11/24.0




    0.4583333333333333




    71/73.0




    0.9726027397260274



## Confusion matrix for threshold of 0.7


    df['boolean'] = df['predictTrain'] >= 0.7
    print df.groupby(['PoorCare','boolean']).size()

    PoorCare  boolean
    0         False      73
    1         False      15
              True        9
    dtype: int64
    

## Sensitivity and specificity


    9/24.0




    0.375




    73/73.0




    1.0



## Confusion matrix for threshold of 0.2
This time using `confusion_matrix` from `scikit-learn`


    from sklearn.metrics import confusion_matrix
    df['boolean'] = df['predictTrain'] >= 0.2
    print confusion_matrix(df['PoorCare'] == 1, df['boolean'])

    [[56 17]
     [ 9 15]]
    

## Sensitivity and specificity


    15.0/24




    0.625




    56.0/73




    0.7671232876712328



# Video 6
## Performance function
Using `metrics` from `scikit-learn`


    from sklearn.metrics import roc_curve, auc
    fpr, tpr, thresholds = roc_curve(df['PoorCare'] == 1, df['predictTrain'])
    roc_auc = auc(fpr, tpr)

## Plot ROC curve
I have given up on coloring the line... and the thresholds are not as nice as
R's ROCR package.


    import matplotlib.pyplot as plt
    plt.figure()
    plt.plot(fpr, tpr, label='ROC curve (area = %0.2f)' % roc_auc)
    plt.plot([0, 1], [0, 1], 'k--')
    for i in range(len(fpr)):
        if i%5 == 0:
            plt.text(fpr[i],tpr[i],str(round(thresholds[i],2)))
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title('Receiver operating characteristic Plot')
    plt.legend(loc="lower right")
    plt.show()


![png](modelingexpert_files/modelingexpert_35_0.png)

