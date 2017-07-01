
# Enron Submission Free-Response Questions

```
The whole project is based on Udacity DAND Nanodegree Project 5.

If someone wants to run the whole code again:
  - 1.Please download the whole package from https://github.com/udacity/ud120-projects.git
  - 2.Then put the whole folder into ```ud120-projects```.
  - 3.Run it!
```

> Note: this project use scikit-learn version 0.18.1

## 1
*Summarize for us the goal of this project and how machine learning is useful in trying to accomplish it. As part of your answer, give some background on the dataset and how it can be used to answer the project question. Were there any outliers in the data when you got it, and how did you handle those?  [relevant rubric items: “data exploration”, “outlier investigation”]*

In 2000, Enron was one of the largest companies in the United States. By 2002, it had collapsed into bankruptcy due to widespread corporate fraud. In the resulting Federal investigation, a significant amount of typically confidential information entered into the public record, including tens of thousands of emails and detailed financial data for top executives.

The **goal** of this project is building an algorithm to identify Enron Employees who may have committed fraud based on the public Enron financial and email dataset.

#### Data exploration
> Note: All the codes for data exploration is in "data_exploration.py" in detail.

##### Summary
 - The total number of people is 146
 - The total number of features is 21
 - The number of POI is 18
 - The number of Non-POI is 128
>  A POI(person of interest) is anyone who has been indicted, settled without admitting the guilt and testified in exchange for immunity.
 - There are features with many missing values, for example, loan_advances(142 missing), director_fees(129 missing). So in the choice of final features, I will exclude these two features.

##### Outliers
There are three main outliers,"TOTAL", "LOCKHART EUGENE E" and "THE TRAVEL AGENCY IN THE PARK". The last one is not a name obviously.
So, I remove the outliers in the poi_id.py, using below code:

```
data_dict.pop("TOTAL", 0)
data_dict.pop("THE TRAVEL AGENCY IN THE PARK", 0)
data_dict.pop("LOCKHART EUGENE E", 0)
```

## 2
*What features did you end up using in your POI identifier, and what selection process did you use to pick them? Did you have to do any scaling? Why or why not? As part of the assignment, you should attempt to engineer your own feature that does not come ready-made in the dataset -- explain what feature you tried to make, and the rationale behind it. (You do not necessarily have to use it in the final analysis, only engineer and test it.) In your feature selection step, if you used an algorithm like a decision tree, please also give the feature importances of the features that you use, and if you used an automated feature selection function like SelectKBest, please report the feature scores and reasons for your choice of parameter values.  [relevant rubric items: “create new features”, “properly scale features”, “intelligently select feature”]*

#### Create new features
>  Note: The codes for create new features is in "add_features.py" in detail.

I create two new features in the dataset. As an important communication tool, emails reflect the connection between two people. There is a hypothesis that a POI sent emails and receive emails to/from other POI more frequently than to Non-POI. So, the percentage of these emails in total email is a meaningful feature.
```
fraction_from_poi = from_poi_to_this_person/to_messages
fraction_to_poi  =  from_this_person_to_poi /from_messages

```
I add these two features in the dataset and test in the final algorithm. The SelectKBest choose the new six features including fraction_to_poi for my Gaussian Naive Bayes algorithm. The precision and recall score also increase. The number of true positives increases as well.
```
### without new features
Accuracy: 0.85464    Precision: 0.48876    Recall: 0.38050    F1: 0.42789    F2: 0.39814
Total predictions: 14000    True positives:  761    False positives:  796    False negatives: 1239    True negatives: 11204

### add new features
Accuracy: 0.86050    Precision: 0.51572    Recall: 0.38550    F1: 0.44120    F2: 0.40600
Total predictions: 14000    True positives:  771    False positives:  724    False negatives: 1229    True negatives: 11276
```

#### Select features
I used the automated feature selection function -- SelectKBest.
>  Note: The process of selecting K value and best features is in "best_features_algorithm.py" in detail.

The best k=5, and features I end up using is:
```
['salary', 'bonus', 'deferred_income', 'total_stock_value', 'exercised_stock_options']

```
Obviously, these are the features with the highest score in the selectKBest, and the final score is:
```
Accuracy: 0.85464    Precision: 0.48876    Recall: 0.38050    F1: 0.42789    F2: 0.39814
Total predictions: 14000    True positives:  761    False positives:  796    False negatives: 1239    True negatives: 11204

```
If I use less or more features, the score will be lower.


#### Scale features
I have used Naive Bayes as my final algorithm. Algorithms like decision tree and linear regression don't require feature scaling, whereas Support Vector Machines (SVM) and k-means clustering does. Because these two algorithms calculate the distance between points. If one of the features has a large range, the distance will be influenced by this particular feature.

## 3
*What algorithm did you end up using? What other one(s) did you try? How did model performance differ between algorithms?  [relevant rubric item: “pick an algorithm”]*

I have used Gaussian Naive Bayes as my final algorithm. I also tried Decision Tree and Random Forest algorithm.

```
Gaussian Naive Bayes:
Accuracy: 0.85464    Precision: 0.48876    Recall: 0.38050    F1: 0.42789    F2: 0.39814

Decision Tree:
Accuracy: 0.91627    Precision: 0.57328    Recall: 0.30900    F1: 0.40156    F2: 0.34038

Random Forest Algorithm:
Accuracy: 0.84692    Precision: 0.50539    Recall: 0.23450    F1: 0.32036    F2: 0.26266
```

### 4
*What does it mean to tune the parameters of an algorithm, and what can happen if you don’t do this well?  How did you tune the parameters of your particular algorithm? (Some algorithms do not have parameters that you need to tune -- if this is the case for the one you picked, identify and briefly explain how you would have done it for the model that was not your final choice or a different model that does utilize parameter tuning, e.g. a decision tree classifier).  [relevant rubric item: “tune the algorithm”]*



Tuning for an algorithm is a important process for optimizing the performance of algorithm. The default parameters are not always suitable for different datasets. So, it is necessary to selecting the good parameter for the algorithm.

Although Gaussian Naive Bayes doesn't have parameters need to tune, I still used GridSearchCV to tune the parameters of decision tree algorithm and RandomForest algorithm.

For example, for decision tree algorithm, I set up follow parameters for GridSearchCV.

```
dt = {
    'select__k' : range(1,19),
    'clf__min_samples_split' : [2, 3, 4, 5],
    'clf__criterion' : ['gini', 'entropy'],
    'clf__max_depth': [None, 1, 2, 5, 10],
    'clf__min_samples_leaf':  [1, 2, 3, 4, 5, 6, 7, 8]
}

```
min_samples_split is the minimum number of samples required to split an internal node. min_samples_leaf is the minimum number of samples required to be at a leaf node. And the maximum depth of the tree determines when the splitting of decision tree node stops.These parameters determines how the decision tree algorithm performs.

### 5
*What is validation, and what’s a classic mistake you can make if you do it wrong? How did you validate your analysis?  [relevant rubric item: “validation strategy”]*

Validation is the strategy to evaluate the performance of the model on unseen data. A classic mistake is to evaluate the performance of an algorithm on the same dataset it was trained on. It will make the algorithm perform better than it actually does.

Generally, to split the data randomly into training and testing set, we can use cross-validation to avoid overfitting. But in our case, the class of Non-POI and POI is extremely **imbalanced**, 128 vs 18, so the randomly choose into training and testing may cause some bad results. For example, all the testing set are Non-POI and make the testing meaningless. Thus, I need to stratify the data. Data stratification is the process of dividing members of the population into homogeneous subgroups before sampling. In other words, using stratification I can really choose training and test data randomly and avoid the previous situation.The Stratified ShuffleSplit cross-validator is a good way to do stratification in scikit-learn, which is a merge of StratifiedKFold and ShuffleSplit.It could make randomly chosen training and test sets multiple times and average the results over all the tests and ensure our training and testing splits have class distribution. In the conclusion, stratification and ShuffleSplit cross-validator are well suited in this case.

In the final poi_id.py, the stratified ShuffleSplit is applied:
```
sss = StratifiedShuffleSplit(n_splits=3, test_size=0.9, random_state=42)
sss.get_n_splits(features,labels)
for train_index, test_index in sss.split(features,labels):
    features_train = []
    labels_train =[]
    features_test = []
    labels_test = []
    for ii in train_index:
        features_train.append( features[ii] )
        labels_train.append( labels[ii] )
    for jj in test_index:
        features_test.append( features[jj] )
        labels_test.append( labels[jj] )
```

### 6
Give at least 2 evaluation metrics and your average performance for each of them.  Explain an interpretation of your metrics that says something human-understandable about your algorithm’s performance. [relevant rubric item: “usage of evaluation metrics”]

When the classes are nearly balanced, accuracy is a good evaluation metric to evaluate the performance of model.
```
Accuracy = Number of labels predicted correctly / Total number of predictions
```
However, in this dataset, The the number of POI is only 18, comparing with 128 Non-POI. It seems accuracy is not an ideal metric. Precision and Recall are two metrics more suitable for the dataset.

```
    Precision = True Positive / (True Positive + False Positive)
```
Precision means that the accurate of a POI gets flagged in the test set.When **Precision** is high,  means if a POI is flagged, I am confident this person is likely to be a real POI.

```
    Recall = True Positive / (True Positive + False Negative)
```
Recall means the ability to find POI. When **Recall** is high, which means in the test set, I can find many real POIs and flag them, although some flagged person is not real POI and is flagged incorrectly.

In my final algorithm, the result is below:
```
Accuracy: 0.85464    Precision: 0.48876    Recall: 0.38050    F1: 0.42789    F2: 0.39814
Total predictions: 14000    True positives:  761    False positives:  796    False negatives: 1239    True negatives: 11204
```
This result means, in the total of 14000 predictions,
 - There are 761 predictions that flagged POIs are real POIs.
 - There are 796 predictions that flagged POIS are not real POIs.
 - There are 1239 predictions that flagged Non-POIs are real POIs.
 - There are 11204 predictions that flagged Non-POIs are really Non-POIs.
 Due to the precision and recall, socre are both higher than 0.3, the final algorithm is not too bad.

### Reference
- https://www.quora.com/Which-machine-learning-algorithms-dont-require-feature-scaling
- http://scikit-learn.org/stable/modules/generated/sklearn.cross_validation.StratifiedShuffleSplit.html
- http://scikit-learn.org/stable/
- https://discussions.udacity.com/t/i-have-doubt-on-evaluation-matrix-lesson/206564
- https://discussions.udacity.com/t/how-to-understand-word-data-ii-for-ii-in-train-indices/216406/2
