import pickle
import sys
import matplotlib.pyplot
import numpy as np
import pandas as pd

sys.path.append("../tools/")
from feature_format import featureFormat, targetFeatureSplit

with open("final_project_dataset.pkl", "r") as data_file:
    data_dict = pickle.load(data_file)

data = pd.DataFrame.from_dict(data_dict, orient = 'index')
data.head()


print "poi", len(data)
print "poi", data['poi'].value_counts()[True]
print "Non-poi", data['poi'].value_counts()[False]

print "##################################"
print "are there features with many missing values?"
def check_nan(features_list):
    check_nan = {}
    for feature in features_list:
        check_nan[feature] = 0

    for feature in features_list:
        for name in data_dict:
            if data_dict[name][feature] == "NaN":
                check_nan[feature] += 1
    return check_nan
check_nan([feature for feature in data_dict['ALLEN PHILLIP K']])


## Find two outliers for the data and print them
# The version of sklearn: version 0.18.1
### read in data dictionary, convert to numpy array
data_dict = pickle.load( open("../final_project/final_project_dataset.pkl", "r") )

#make a plot with two features
features = ["salary", "bonus"]
data = featureFormat(data_dict, features)
for point in data:
    salary = point[0]
    bonus = point[1]
    matplotlib.pyplot.scatter( salary, bonus )

matplotlib.pyplot.xlabel("salary")
matplotlib.pyplot.ylabel("bonus")
matplotlib.pyplot.show()

#find the outlier
for name in data_dict:
    if data_dict[name]["salary"] != 'NaN' and data_dict[name]["bonus"] != 'NaN':
        if data_dict[name]["salary"] >=25000000 and data_dict[name]["bonus"] >= 8000000:
            print "The outlier is: ", name
# So, Total is a outlier


### check if there is item without any data(all feature is NaN)
for name in data_dict:
    check_feature = False
    for feature in data_dict[name]:
        if feature != "poi":
            if data_dict[name][feature] != 'NaN':
                check_feature = True
    if check_feature == False:
        print "Another outlier: ",name
