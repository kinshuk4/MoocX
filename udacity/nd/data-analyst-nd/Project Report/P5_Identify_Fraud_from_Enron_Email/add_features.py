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
from tester import dump_classifier_and_data
from tester import test_classifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.pipeline import Pipeline

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

#add new feature
for person in data_dict:
    #print (data_dict[person]['to_messages'],data_dict[person]['from_poi_to_this_person'] )
    if data_dict[person]['to_messages'] != 'NaN' and data_dict[person]['from_poi_to_this_person'] !=  'NaN':
        data_dict[person]['fraction_from_poi'] =  float(data_dict[person]['from_poi_to_this_person']) / data_dict[person]['to_messages']
    else:
        data_dict[person]['fraction_from_poi'] = 'NaN'

for person in data_dict:
    if data_dict[person]['from_messages'] != 'NaN' and data_dict[person]['from_this_person_to_poi'] !=  'NaN':
        data_dict[person]['fraction_to_poi'] =  float(data_dict[person]['from_this_person_to_poi']) / data_dict[person]['from_messages']
    else:
        data_dict[person]['fraction_to_poi'] = 'NaN'

#update features_list
features_list.append('fraction_from_poi')
features_list.append('fraction_to_poi')

my_dataset = data_dict
data = featureFormat(my_dataset, features_list, sort_keys = True)
labels, features = targetFeatureSplit(data)




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

#test the effect of that feature on the final algorithm performance
find_best(GaussianNB(),nb)
#find_best(tree.DecisionTreeClassifier(),dt)
#find_best(RandomForestClassifier(),rdf)
