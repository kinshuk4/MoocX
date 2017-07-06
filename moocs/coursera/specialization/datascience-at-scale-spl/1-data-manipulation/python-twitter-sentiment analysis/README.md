#Twitter Sentiment Analysis

Assignment Page [Link](https://www.coursera.org/learn/data-manipulation/programming/AxbQn/twitter-sentiment-analysis)

##Libraries Needed:
- json
- [oauth2](https://pypi.python.org/pypi/oauth2/)

##File Descriptions:

###AFINN.txt
Collection of commonly used English words and their sentiment scores,

###twitterstream.py
Used to get a livestream of tweets using the API keys and Tokens.

###tweet_sentiment.py
Finding the sentiment score of each tweet using the AFINN-111.txt.

###term_sentiment.py
Finding the sentiment scores of the words that are NOT in the AFINN.txt file using the collective sentiment of the tweet.

###frequency.py
Finding the term-frequency of all words in a file of tweet objects.

###happystate.py
Finding the happiest state in US by averaging sentiments of all the live tweets from all states in the US.

###toptenhashtags.py
Finding the most used hashtags from a file of tweet objects.

###output.txt and output2.txt
These file are used for testing with the above scripts.  
These files contain a list of [tweet objects](https://dev.twitter.com/overview/api/tweets).

