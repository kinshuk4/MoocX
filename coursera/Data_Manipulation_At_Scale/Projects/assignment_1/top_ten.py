import sys
import json
    
def clean_word(word):
    #coerce to lower
    temp = word.lower()
    # remove special characters
    exclude = set([",","!","?",":",".", ";"])
    temp = "".join(ch for ch in temp if not (ch in exclude))
    return temp
       
def count_hashtags(json_file):
    """
    Takes a JSON file of Twitter streaming output and a dictionary of
    pre-scored words. Assigns a sentiment
    score to each tweet. Returns a dictionary of tweets and scores
    """
    hash_counts = {} #initialize empty dictionary
    with open(json_file) as twitter_file:
        tweets = twitter_file.readlines() #ignore the first line
        for line in tweets:
            try:
                # load tweet
                mydict = json.loads(line)
                
                #get hashtags
                #tweet = mydict[u'text'].encode('utf-8')
                hashtags = mydict[u'entities']['hashtags']
                for tag_data in hashtags:
                    tag_text = tag_data[u'text'].encode('utf-8')
                    word_cln = clean_word(tag_text)                    
                    if word_cln in hash_counts.keys():
                        hash_counts[word_cln] += 1
                    else:
                        hash_counts[word_cln] = 1
            except:
                continue #handle errors from misformatted JSON
    return hash_counts
               
def main():
    """
    Given a sentiment score file and a file with Twitter JSON
    data, loops over the dictionary of state scores and prints out the name
    of the state with the highest  average sentiment.
    """
    tweet_file = sys.argv[1]
    hashtag_summary = count_hashtags(tweet_file)
    #get a sorted list of key-val pairs
    # (sorted in descending order by count)
    counts = sorted(hashtag_summary.items(), key=lambda x: x[1], reverse=True)
    
    # if len(counts) < 10:
        # top = len(counts)
    # else:
        # top = 10
    top = 10    
    for dummy_i in range(top):
        hashtag = counts[dummy_i][0]
        count = counts[dummy_i][1]
        sys.stdout.write(hashtag + " " + str(count)+"\n")

# Pipeline:
#   1. Score all the tweets...create a dictionary of ALL WORDS in the process
#   2. Loop over the words, make their score the net of scores of all tweets they appear in
#        
if __name__ == '__main__':
    main()
    
# References
# http://stackoverflow.com/questions/613183/sort-a-python-dictionary-by-value
# https://wiki.python.org/moin/HowTo/Sorting#Ascending_and_Descending
