
# Week 5 - Twitter
# VIDEO 5
## Read in the data


    import os
    os.chdir('C:\\Users\\iris\\Downloads')
    import pandas as pd
    tweets = pd.read_csv("tweets.csv")
    tweets.head(3)




<div style="max-height:1000px;max-width:1500px;overflow:auto;">
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Tweet</th>
      <th>Avg</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td> I have to say, Apple has by far the best custo...</td>
      <td> 2.0</td>
    </tr>
    <tr>
      <th>1</th>
      <td> iOS 7 is so fricking smooth &amp; beautiful!! #Tha...</td>
      <td> 2.0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>                                     LOVE U @APPLE</td>
      <td> 1.8</td>
    </tr>
  </tbody>
</table>
</div>



## Create dependent variable


    tweets['Negative'] = tweets['Avg'] <= -1

## Create corpus
Using `pandas.Series` as data structure for corpus


    corpus = tweets['Tweet'].copy()
    print corpus[0]

    I have to say, Apple has by far the best customer care service I have ever received! @Apple @AppStore
    

## Convert to lower-case


    corpus = corpus.str.lower()
    print corpus[0]

    i have to say, apple has by far the best customer care service i have ever received! @apple @appstore
    

## Remove punctuation


    corpus = corpus.str.replace('[^\w\s]', ' ')
    print corpus[0]

    i have to say  apple has by far the best customer care service i have ever received   apple  appstore
    

## Remove stopwords and apple


    from nltk.corpus import stopwords
    removeWords = stopwords.words('english') + [u'apple']
    print removeWords[:10]

    [u'i', u'me', u'my', u'myself', u'we', u'our', u'ours', u'ourselves', u'you', u'your']
    


    for i in corpus.index:
        text = corpus[i]
        content = text.split()
        content = [c for c in content if c not in removeWords]
        corpus[i] = ''.join(str(c)+" " for c in content)
    print corpus[0]

    say far best customer care service ever received appstore 
    

## Stem document


    from stemming.porter2 import stem
    for i in corpus.index:
        text = corpus[i]
        content = text.split()
        content = [stem(c) for c in content]
        corpus[i] = ''.join(str(c)+" " for c in content)
    print corpus[0]

    say far best custom care servic ever receiv appstor 
    

# Video 6
## Create matrix


    from sklearn.feature_extraction.text import CountVectorizer
    count_vect = CountVectorizer()
    frequencies = count_vect.fit_transform(corpus)
    frequencies




    <1181x3441 sparse matrix of type '<type 'numpy.int64'>'
    	with 10208 stored elements in Compressed Sparse Row format>



## Look at Matrix


    print frequencies[0:5,340:345].todense()

    [[0 0 1 0 0]
     [0 0 0 0 0]
     [0 0 0 0 0]
     [0 0 0 0 0]
     [0 0 0 0 0]]
    

## Check for sparsity


    tf = frequencies.sum(axis = 0)
    print sum(tf >= 20)

    63
    


    terms = CountVectorizer.get_feature_names(count_vect)
    print [terms[i] for i,f in enumerate(tf.tolist()[0]) if f >= 20]

    [u'5c', u'5s', u'android', u'anyon', u'app', u'back', u'batteri', u'better', u'bit', u'buy', u'com', u'come', u'de', u'fingerprint', u'freak', u'get', u'go', u'googl', u'http', u'io', u'ios7', u'ipad', u'iphon', u'iphone5', u'iphone5c', u'ipod', u'ipodplayerpromo', u'itun', u'like', u'lol', u'look', u'love', u'ly', u'make', u'market', u'microsoft', u'need', u'new', u'one', u'ow', u'phone', u'pic', u'pleas', u'promo', u're', u'realli', u'releas', u'rt', u'samsung', u'say', u'store', u'thank', u'think', u'time', u'twitter', u'updat', u'us', u'use', u'via', u'want', u'well', u'work', u'would']
    

## Remove sparse terms


    count_vect = CountVectorizer(min_df = 0.005)
    sparse = count_vect.fit_transform(corpus)
    sparse




    <1181x342 sparse matrix of type '<type 'numpy.int64'>'
    	with 5620 stored elements in Compressed Sparse Row format>



## Convert to a data frame


    terms = CountVectorizer.get_feature_names(count_vect)
    tweetsSparse = pd.DataFrame(sparse.todense(), columns = terms)

## Add dependent variable


    tweetsSparse['Negative'] = tweets['Negative']

## Split the data


    from sklearn.cross_validation import train_test_split
    train0, test0 = train_test_split(tweetsSparse.loc[tweetsSparse['Negative']==0,:], train_size=0.7, random_state=88)
    train1, test1 = train_test_split(tweetsSparse.loc[tweetsSparse['Negative']==1,:], train_size=0.7, random_state=88)
    trainSparse = pd.DataFrame(np.vstack((train0,train1)), columns=tweetsSparse.columns).convert_objects(convert_numeric=True)
    testSparse = pd.DataFrame(np.vstack((test0,test1)), columns=tweetsSparse.columns).convert_objects(convert_numeric=True)

# Video 7
## Build a CART model


    from sklearn import tree
    tweetCART = tree.DecisionTreeClassifier(max_depth = 8)
    tweetCART.fit(trainSparse[terms],trainSparse.Negative)




    DecisionTreeClassifier(compute_importances=None, criterion='gini',
                max_depth=8, max_features=None, max_leaf_nodes=None,
                min_density=None, min_samples_leaf=1, min_samples_split=2,
                random_state=None, splitter='best')




    from sklearn.externals.six import StringIO  
    import pydot 
    dot_data = StringIO() 
    tree.export_graphviz(tweetCART, out_file=dot_data) 
    graph = pydot.graph_from_dot_data(dot_data.getvalue()) 
    graph.write_png("tweetCART.png") 




    True




    from IPython.display import Image
    i = Image(filename='tweetCART.png')
    i




![png](Twitter_files/Twitter_33_0.png)



## Evaluate the performance of the model


    predictCART = tweetCART.predict(testSparse[terms])
    from sklearn.metrics import confusion_matrix
    print confusion_matrix(testSparse['Negative'], predictCART)

    [[293   7]
     [ 34  21]]
    


    (21+292)/(21+292+8+34.0)




    0.8816901408450705



## Baseline accuracy


    print testSparse.groupby('Negative').size()

    Negative
    False       300
    True         55
    dtype: int64
    


    300/(300+55.)




    0.8450704225352113



## Random forest model


    from sklearn.ensemble import RandomForestClassifier
    tweetRF = RandomForestClassifier(random_state = 123)
    tweetRF.fit(trainSparse[terms],trainSparse.Negative)




    RandomForestClassifier(bootstrap=True, compute_importances=None,
                criterion='gini', max_depth=None, max_features='auto',
                max_leaf_nodes=None, min_density=None, min_samples_leaf=1,
                min_samples_split=2, n_estimators=10, n_jobs=1,
                oob_score=False, random_state=123, verbose=0)



## Make predictions:


    predictRF = tweetRF.predict(testSparse[terms])
    print confusion_matrix(testSparse['Negative'], predictRF)

    [[277  23]
     [ 28  27]]
    

## Accuracy:


    (277+27)/(277+27+23+28.)




    0.856338028169014


