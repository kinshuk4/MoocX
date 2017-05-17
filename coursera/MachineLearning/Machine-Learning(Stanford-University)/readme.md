Learning Algorithms:
1. Naive Bayes (Good at text classification)
2. Support Vector Machines
3. Decision Tree
4. K-nearest Neighbors
5. AdaBoost
6. Random Forest
7. Linear Regression
8. K-Means
9. Principal Component Analysis

Mini Projects
1. Email author classification
2. Enron email

Gold Star Ideas:
1. Outliers


===================================================================================================================

Lesson 0
========
Welcome

Learn machine learning to get job at Silicon Valley.
Google's page rank algorithm is based on machine learning. Also, self driving cars.
It's all about big data.
It's always about curosity.

Lesson 1
========
Naive Bayes

Supervised Learning
-------------------
Typical example - Self driving cars

Features are the most important part of machine learning.

After getting proper features, feature visualisation should be done to get insight of the features.

One way of doing that is scatter plots.

Classification machine learning model defines the decision boundary.
Data -> Decision Surface

Application 1:
-----------
Scenario - Learn a self driving car to drive slow or fast based on road condition.

Learning Algorithm:
Naive Bayes

Library:
Scikit-learn

Performance Analysis:
Accuracy

Always train and test on two different data.
Save 10 percent of the data for testing set.

Bayes is good at text learning.

Advantages:
1. Easy to learn
2. Can learn on big feature space.
3. Simple to run; efficient.

Think about learning algorithms as a tool to suit your problem; not like a black box.

Mini Project:
Identify the author of a new email.

==========================================================================================================

Lesson 2
========
SVM

Support Vector Machines separates the data optimally using decision boundary.
It maximised the distance to the nearest points on both classes. It is also called maximum margin. It maximise the robustness.

Outliers - Data points corresponding to wrong end of decision boundary. SVM handles outliers perfectly without giving robustness.

It is really good at non-linear data.

Kernel Trick:
Kernels transforms the feature space automatically to get a linear decision boundary from the non-linear datasets.

Params are very important to fit the dataset.

Overfitting - Very complicated model. Don't overfit on your dataset, otherwise it won't generalise to new data.

Advantages:
1. Effective in high dimension.

Disadvantages:
1. It doesn't do good at large dataset.
2. It doesn't do good with lot of noise.

=========================================================================================================

Lesson 3
========
Decision Trees

Decision trees algorithms converts the dataset with features into decision making trees with branches and decision boundary based on answer to the node of a particular branch.

Params of Decision Trees:
min_samples_split results in overfitting

This is how decision tree decides where and how to split the data.
Entropy: measure of impurity in the bunch of samples. 
Information gain: entropy(parent) - [Weighted average] entropy(chidren)

Decision Tree: maximise information gain.

High Bias - Totally underfitted model
High Variance - Totally overfitted model

Advantages:
1. Graphically interpret the data very well.
2. Easy to use.

Disadvantages:
1. Overfitting often.

=============================================================================================

Lesson 4
========
Choose your own Algorithm

1. K-nearest Neighbors
2. AdaBoost
3. Random Forest

Ada Boost and Random Forest are the examples of 'Ensemble Methods'.

===============================================================================================

Lesson 5
========
Datasets and Questions

Dataset:
Enron email

Plotting learning curve to check if more data is good for your model.

Types of data:
1. Numerical
2. Categorical
3. Time series
4. text
5. Other

Always think about features which should be important for your model.

=================================================================================================

Lesson 6
========
Regressions

Output is continous real values.

Learning Algorithm:
Linear Regression

It's all about minimizing the sum of square error measure.
Two ways of doing this:
1. Ordinary Least Squares
2. Gradient Descent

Performance of regression model:
1. r square (It is independent of the number of training points)

Again, more features means more better performance.

Sometimes, fitting two model inside a single dataset, generally over the range of dataset, is better option.

================================================================================================

Lesson 7
========
Outliers

Outliers results in poor generalisation of model; unable to predict for the new data.

What causes?
1. Sensor malfunction
2. Data entry errors
3. Freak Events

Ignore 1 and 2, but pay attention to 3rd cause for outlier. Such as fraud detection.

Outlier detection:
1. Train
2. Remove 10 percent of dataset
3. Train again
4. Repeat 2 & 3

Gold star idea:
1. Train
2. Remove points with largest residual error (10 percent)
3. Re-train

===============================================================================================

Lesson 8
========
Clustering

Unsupervised Learning:
K-Means

Limits:
It's like hill climbing algorithm, highly prone to go to local minima.

===============================================================================================

Lesson 9
========
Feature Scaling

More the size of feature, more the weight it got in the model. So, always scale it before using it in the model.

Scale the feature using MinMaxScaler()
x = (x` - xmin)/(xmax - xmin)

But, if there are outliers in the dataset, they gonna screw up the feature scaling.

Those learning algorithms which calculates the distance to the data point in the n dimension space are most affected by the feature scaling.

=============================================================================================

Lesson 10
=========
Text Learning

Bag of Words

But, some words contain more information than others. So, eliminate the low-information words.
Always remove the stop words.
Stemming is good thing to do.
Use tfidf

==============================================================================================

Lesson 11
=========
Feature Selection

Select best features
Add new features

How to add new features:
1. Use my human intuition
2. Code up the new feature
3. Visualise
4. Repeat

Why might you want to ignore a feature?
1. It's noisy
2. It causes overfitting
3. It is highly correlated with the feature that's already exist.
4. Additional feature slows down training/testing process

Features != Information

Features attempt to access information.
Use sklearn.feature_selection.SelectPercentile to feature selection.

Bias-Variance tradeoff:
----------------------
High Bias(Underfitting): pays little attention to data; model oversimplified
Symptoms:
High error on training set.
Reason:
1. Using very few features
2. Using basic model

High Variance(Overfitting): pays too much attention to data; model over complex.
Symptoms:
High error on test set than on training set.
Reason:
1. Using very large features
2. Using complex model

In both the cases, it fails to generalise.

Use regularisation to penalizing the feature
Lasso Regression

====================================================================================

Lesson 12
=========
Principal Component Analysis

Use PCA for dimension reduction of features.

Measurable vs. Latent Features:
Measurable features are those which are easily measured, but latent features are the ones we really care about; hard to calculate.

Feature selection is also good tool to reduce the size of features.

PCA looks like regression but it isn't.

PCA works well with elliptical shape of features.

Key concept:
Maximum the variance of PCA model, minimum the data loss.

Instead of performing PCA over all the features at once, one can do it in multiple steps, in order to cluster similiar feature data into single compressed feature.
But, if the feature size is very large, for e.g in classification of images, then PCA is good to determine the compressed feature itself.

PCA automatically understands the latent features from measurable features.

When to use PCA?
1. Latent features driving the patterns in data.
2. Dimensionality reduction.
	2.1 Visualise high dimensional data
	2.2 Reduce noise
	2.3 Make other algorithms work better with fewer inputs.

PCA for facial recognition.
1. PCA reduces the input size.
2. It automatically detects the underlying patterns of human faces like two eyes, nose etc.

Selecting number of Principal Components:
train on different number of PCs, and see how accuracy responds - cut off when it becomes apparent that adding more PCs doesn't buy you more discrimination.

=================================================================================================================

Lesson 13
=========
Validation

Use training on one dataset and testing on another to give brilliant model for production.

Use KFold, or train_test_split for cross validation of your model.

=================================================================================================================

Lesson 14
=========
Evaluation Metrics

Accuracy - It doesn't do good in case of skew case, and when we care about false positive 
Confusion Matrix - Recall and precision is calculated via confusion matrix.

================================================================================================================

Lesson 15
=========
Trying it all together

How machine learning works?
1. Dataset/Question
2. Features
3. Algorithm
4. Evaluation

================================================================================================================


Help from
 - https://github.com/reetawwsum/Machine-learning-MOOC-notes/blob/master/Intro%20to%20Machine%20Learning.txt