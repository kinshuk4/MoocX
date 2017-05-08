#!/usr/bin/python

import sys
import pickle
sys.path.append("../tools/")

import numpy as np
from feature_format import featureFormat, targetFeatureSplit
from tester import dump_classifier_and_data
from tester import test_classifier
from sklearn.preprocessing import MinMaxScaler
from sklearn.naive_bayes import GaussianNB
from sklearn.pipeline import Pipeline
import pprint as pp

from sklearn.model_selection import GridSearchCV
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import f_classif
from sklearn import tree
from sklearn.model_selection import StratifiedShuffleSplit
from sklearn.metrics import accuracy_score
from tester import dump_classifier_and_data
from tester import test_classifier
from sklearn.ensemble import RandomForestClassifier

### features_list is a list of strings, each of which is a feature name.
### The first feature must be "poi".
features_list = ['poi','salary', 'deferral_payments', 'total_payments', 'loan_advances', 'bonus',
  'restricted_stock_deferred', 'deferred_income', 'total_stock_value',
  'expenses', 'exercised_stock_options', 'other', 'long_term_incentive',
  'restricted_stock', 'director_fees','to_messages', 'from_poi_to_this_person',
  'from_messages', 'from_this_person_to_poi', 'shared_receipt_with_poi']

### Load the dictionary containing the dataset
with open("final_project_dataset.pkl", "r") as data_file:
    data_dict = pickle.load(data_file)

#move outliers
data_dict.pop("TOTAL", 0)
data_dict.pop("THE TRAVEL AGENCY IN THE PARK", 0)
data_dict.pop("LOCKHART EUGENE E", 0)

my_dataset = data_dict
my_features_list = features_list
data = featureFormat(my_dataset, features_list, sort_keys = True)
labels, features = targetFeatureSplit(data)

#print (np.asarray(features)).shape
#SelectKBest(f_classif, k=5).fit(features, labels)
#features_new = SelectKBest(f_classif, k=5).fit_transform(features, labels)
#selection = SelectKBest(k=6)
#selection.fit(features,labels)
#print selection.scores_

from sklearn.pipeline import Pipeline
#score = []
#for i in range(0,len(features_list)-1):
    #score.append((features_list[1:][i],selection.scores_[i]))
#sorted_by_second = sorted(score, key=lambda tup: tup[1],reverse=True)
#print sorted_by_second

#features_selected = [features_list[i+1] for i in selection.get_support(indices=True)]
#print features_selected

# there are 19 initial features(exclude poi).
nb = {
        'select__k' : range(1,19),
}

dt = {
    'select__k' : range(1,19),
    'clf__min_samples_split' : [2, 3, 4, 5],
    'clf__criterion' : ['gini', 'entropy'],
    'clf__max_depth': [None, 1, 2, 5, 10],
    'clf__min_samples_leaf':  [1, 2, 3, 4, 5, 6, 7, 8]
}

rdf ={
    'select__k' : range(1,19),
    'clf__n_estimators': [10,50,100,150],
}

def find_best(classifier, parameters):

    estimators = [('select',SelectKBest()), ('clf',classifier)]
    pipe = Pipeline(estimators)

    #pp.pprint(sorted(pipe.get_params().keys()))
    sss = StratifiedShuffleSplit(n_splits=3, test_size=0.9, random_state=42)
    sss.get_n_splits(features,labels)

    result = GridSearchCV(pipe, parameters, cv = sss)
    result.fit(features, labels)
    clf = result.best_estimator_

    print result.best_params_

    my_features_list = [features_list[i+1] for i in clf.named_steps['select'].get_support(indices=True)]
    my_features_list.insert(0, "poi")

    print my_features_list
    dump_classifier_and_data(clf, my_dataset, my_features_list)
    test_classifier(clf, my_dataset, my_features_list)

find_best(GaussianNB(),nb)
find_best(RandomForestClassifier(),rdf)
#find_best(tree.DecisionTreeClassifier(),dt)
#Becareful, if you decision tree, it will take a long time to run the result.
