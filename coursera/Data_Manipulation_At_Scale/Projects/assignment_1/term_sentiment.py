import sys
import json
    
def make_score_dictionary(sent_file):
    """
    Reads in a file with words and sentiment scores,
    returns a dictionary mirroring the file contents.
    """
    word_scores = {} # initialize an empty dictionary
    with open(sent_file) as sentiment_file:
        for line in sentiment_file:
          term, score  = line.split("\t")  # The file is tab-delimited. "\t" means "tab character"
          word_scores[term] = int(score)  # Convert the score to an integer.
    return word_scores
    
def clean_word(word):
    #coerce to lower
    temp = word.lower()
    # remove special characters
    exclude = set([",","!","?",":",".", ";"])
    temp = "".join(ch for ch in temp if not (ch in exclude))
    return temp
 
def score_tweet(tweet, score_dict):
    """
    Takes text of a tweet and scores it using the sentiment dictionary
    provided.
    """
    tweet_score = 0
    for word in tweet:
        word_cln = clean_word(word)
        if word_cln in score_dict.keys():
            tweet_score += score_dict[word_cln]
    return tweet_score

def score_file(json_file, score_dict):
    """
    Takes a JSON file of Twitter streaming output and a dictionary of
    pre-scored words. Assigns a sentiment
    score to each tweet. Returns a dictionary of tweets and scores
    """
    tweet_scores = {} #initialize empty dictionary
    with open(json_file) as twitter_file:
        #first_line = twitter_file.readlines()
        tweets = twitter_file.readlines() #ignore the first line
        for line in tweets:
            try:
                mydict = json.loads(line)
                tweet = mydict[u'text'].encode('utf-8')
                sentiment = score_tweet(tweet.split(), score_dict)
                # just add non-zero tweets (most interesting)
                if sentiment != 0:
                    tweet_scores[tweet] = sentiment #assumes unique tweets, not great
            except:
                continue #handle errors from misformatted JSON
    return tweet_scores
        
def score_new_words(tweets, score_dict):
    """
    Given an dictionary of tweets and their sentiment scores,
    builds a new dictionary of all words not in the sentiment-scoring
    file. Assigns sentiment scores to these new words by taking net sentiment
    across all tweets containing the words. Returns a dictionary of
    words not pre-scored (i.e. in the read-in sentiment file) and their
    sentiment scores.
    """
    word_scores = {}
    for tweet in tweets.keys():
        sentiment = tweets[tweet]
        for word in tweet.split():
            word_cln = clean_word(word)
            if not (word_cln in score_dict.keys()):
                if word_cln in word_scores.keys():
                    word_scores[word_cln] += sentiment
                else:
                    word_scores[word_cln] = sentiment             
    return word_scores
            
def main():
    """
    Given a sentiment score file and a file with Twitter JSON
    data, loops through tweets and prints their scores to stdout.
    """
    sent_file = sys.argv[1]
    tweet_file = sys.argv[2]
    prescored_words = make_score_dictionary(sent_file)
    scored_tweets = score_file(tweet_file, prescored_words)
    scored_new_words = score_new_words(scored_tweets, prescored_words)
    for word in scored_new_words.keys():
        sys.stdout.write(word + " " + str(scored_new_words[word])+"\n")

# Pipeline:
#   1. Score all the tweets...create a dictionary of ALL WORDS in the process
#   2. Loop over the words, make their score the net of scores of all tweets they appear in
#        
if __name__ == '__main__':
    main()
    

