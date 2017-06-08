import sys
import json
    
def make_score_dictionary(sent_file):
    """
    Reads in a file with words and sentiment scores,
    creates a dictionary in the global environment mirroring the file
    contents.
    """
    global scores
    scores = {} # initialize an empty dictionary
    with open(sent_file) as sentiment_file:
        for line in sentiment_file:
          term, score  = line.split("\t")  # The file is tab-delimited. "\t" means "tab character"
          scores[term] = int(score)  # Convert the score to an integer.
 
def score_tweet(tweet):
    """
    Takes text of a tweet and scores it using the sentiment dictionary.
    """
    tweet_score = 0
    for word in tweet:
        if word in scores.keys():
            tweet_score += scores[word]
    return tweet_score

def score_file(json_file):
    """
    Takes a JSON file of Twitter streaming output and assigns a sentiment
    score to each tweet.
    """
    with open(json_file) as twitter_file:
        #first_line = twitter_file.readlines()
        tweets = twitter_file.readlines() #ignore the first line
        for line in tweets:
            try:
                mydict = json.loads(line)
                tweet = mydict[u'text']
                sentiment = score_tweet(tweet.split())
                sys.stdout.write(str(sentiment) + "\n") #print sentiment
            except:
                continue #handle errors from misformatted JSON
        
def main():
    """
    Given a sentiment score file and a file with Twitter JSON
    data, loops through tweets and prints their scores to stdout.
    """
    sent_file = sys.argv[1]
    tweet_file = sys.argv[2]
    make_score_dictionary(sent_file)
    score_file(tweet_file)

#call the function (gulp)
if __name__ == '__main__':
    main()
               

    
#tweets2dict('three_minutes_tweets.json')

## References
# https://docs.python.org/2/library/sys.html
