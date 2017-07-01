import sys
import pickle
sys.path.append("../tools/")

from feature_format import featureFormat, targetFeatureSplit
from tester import dump_classifier_and_data
from sklearn.model_selection import StratifiedShuffleSplit
from sklearn import preprocessing
from sklearn.feature_selection import SelectKBest
from sklearn.naive_bayes import GaussianNB
from sklearn import tree
from sklearn.pipeline import Pipeline
from sklearn.model_selection import GridSearchCV

features_list = ['poi','salary', 'bonus', 'deferred_income', 'total_stock_value', 'exercised_stock_options']

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

#min_max_scaler = preprocessing.MinMaxScaler()
#features = min_max_scaler.fit_transform(features)

# split the train data
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

clf = GaussianNB()
clf.fit(features_train, labels_train)
dump_classifier_and_data(clf, my_dataset, features_list)

#from tester import dump_classifier_and_data
#from tester import test_classifier

#test_classifier(clf, my_dataset, my_features_list)
