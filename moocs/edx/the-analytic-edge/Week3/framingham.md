
# Unit 3, The Framingham Heart Study
## Video 3
## Read in the dataset


    import os
    os.chdir('C:\Users\Violetta_Chen\Downloads')
    import pandas as pd
    framingham = pd.read_csv("framingham.csv")

## Look at structure


    def dfStr(df):
        print "The dataframe contains {0} rows and {1} columns".format(df.shape[0], df.shape[1])
        print "The data types of columns are: \n"
        print df.dtypes
    
    dfStr(framingham)

    The dataframe contains 4240 rows and 16 columns
    The data types of columns are: 
    
    male                 int64
    age                  int64
    education          float64
    currentSmoker        int64
    cigsPerDay         float64
    BPMeds             float64
    prevalentStroke      int64
    prevalentHyp         int64
    diabetes             int64
    totChol            float64
    sysBP              float64
    diaBP              float64
    BMI                float64
    heartRate          float64
    glucose            float64
    TenYearCHD           int64
    dtype: object
    

## Randomly split the data into training and testing sets


    from sklearn.cross_validation import train_test_split
    train0, test0 = train_test_split(framingham.loc[framingham['TenYearCHD'] == 0,:], train_size=0.65, random_state=1000)
    train1, test1 = train_test_split(framingham.loc[framingham['TenYearCHD'] == 1,:], train_size=0.65, random_state=1000)
    framinghamTrain = pd.DataFrame(np.vstack((train0,train1)), columns=framingham.columns).convert_objects(convert_numeric=True)
    framinghamTest = pd.DataFrame(np.vstack((test0,test1)), columns=framingham.columns).convert_objects(convert_numeric=True)

## Imputing the datasets
A simple strategy to impute the dataset, replace `nan`s using the mean of that
variable


    predictors = framinghamTrain.columns.tolist()
    predictors.remove('TenYearCHD')
    from sklearn.preprocessing import Imputer
    imp = Imputer(missing_values='NaN', strategy='mean', axis=0)
    imp.fit(framinghamTrain[predictors])
    X = imp.transform(framinghamTrain[predictors])
    y = framinghamTrain['TenYearCHD']

## Logistic Regression Model
I will be using `scikit-learn` this time


    from sklearn.linear_model import LogisticRegression
    framinghamLog = LogisticRegression(penalty='l1', tol=1e-6)
    framinghamLog.fit(X, y)




    LogisticRegression(C=1.0, class_weight=None, dual=False, fit_intercept=True,
              intercept_scaling=1, penalty='l1', random_state=None, tol=1e-06)



## Predictions on the test set


    imp = Imputer(missing_values='NaN', strategy='mean', axis=0)
    imp.fit(framinghamTest[predictors])
    Xtest = imp.transform(framinghamTest[predictors])
    ytest = framinghamTest['TenYearCHD']
    predictTest = framinghamLog.predict(Xtest)

## Confusion matrix with threshold of 0.5


    from sklearn.metrics import confusion_matrix
    print confusion_matrix(ytest==1, predictTest >= 0.5)

    [[1249   10]
     [ 208   18]]
    

## Accuracy


    (18+1249)/(1249+10+208+18.0)




    0.8531986531986532



## Baseline accuracy


    (10+1249)/(1249+10+208+18.0)




    0.8478114478114478



## Test set AUC


    from sklearn.metrics import roc_curve, auc
    fpr, tpr, thresholds = roc_curve(ytest==1, predictTest)
    roc_auc = auc(fpr, tpr)
    print roc_auc

    0.535851602972
    
