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
    
def get_state(location):
    """
    Takes the data from the "location" field and tries to find
    what U.S. state it came from.
    Narrow scope...just work on tweets with entry in the form of
    <city>, <2-letter state code>
    """
    states = {
        'AK': 'Alaska',
        'AL': 'Alabama',
        'AR': 'Arkansas',
        'AS': 'American Samoa',
        'AZ': 'Arizona',
        'CA': 'California',
        'CO': 'Colorado',
        'CT': 'Connecticut',
        'DC': 'District of Columbia',
        'DE': 'Delaware',
        'FL': 'Florida',
        'GA': 'Georgia',
        'GU': 'Guam',
        'HI': 'Hawaii',
        'IA': 'Iowa',
        'ID': 'Idaho',
        'IL': 'Illinois',
        'IN': 'Indiana',
        'KS': 'Kansas',
        'KY': 'Kentucky',
        'LA': 'Louisiana',
        'MA': 'Massachusetts',
        'MD': 'Maryland',
        'ME': 'Maine',
        'MI': 'Michigan',
        'MN': 'Minnesota',
        'MO': 'Missouri',
        'MP': 'Northern Mariana Islands',
        'MS': 'Mississippi',
        'MT': 'Montana',
        'NA': 'National',
        'NC': 'North Carolina',
        'ND': 'North Dakota',
        'NE': 'Nebraska',
        'NH': 'New Hampshire',
        'NJ': 'New Jersey',
        'NM': 'New Mexico',
        'NV': 'Nevada',
        'NY': 'New York',
        'OH': 'Ohio',
        'OK': 'Oklahoma',
        'OR': 'Oregon',
        'PA': 'Pennsylvania',
        'PR': 'Puerto Rico',
        'RI': 'Rhode Island',
        'SC': 'South Carolina',
        'SD': 'South Dakota',
        'TN': 'Tennessee',
        'TX': 'Texas',
        'UT': 'Utah',
        'VA': 'Virginia',
        'VI': 'Virgin Islands',
        'VT': 'Vermont',
        'WA': 'Washington',
        'WI': 'Wisconsin',
        'WV': 'West Virginia',
        'WY': 'Wyoming'
        }
    if "," in location:
        state_code = location.split(", ")[1]
        if state_code.upper() in states.keys():
            return state_code.upper()
        else:
            return False
    else:
        return False
       
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
        state_scores = {}
        state_counts = {}
        for line in tweets:
            try:
                # load tweet
                mydict = json.loads(line)
                
                #try to find state location (based on user location)
                loc = mydict[u'user'][u'location'].encode('utf-8')
                state = get_state(loc)
                if state != False:
                    #score tweet
                    tweet = mydict[u'text'].encode('utf-8')
                    sentiment = score_tweet(tweet.split(), score_dict)
                    
                    #update state score in dictionary
                    if state.upper() in state_scores.keys() and sentiment != 0:
                        state_scores[state.upper()] += sentiment
                        state_counts[state.upper()] += 1
                    else:
                        state_scores[state.upper()] = sentiment
                        state_counts[state.upper()] = 1

            except:
                continue #handle errors from misformatted JSON
    scores_and_counts = {}
    for state in state_scores.keys():
        scores_and_counts[state] = (state_scores[state],state_counts[state])
    return scores_and_counts
    
        
            
def main():
    """
    Given a sentiment score file and a file with Twitter JSON
    data, loops over the dictionary of state scores and prints out the name
    of the state with the highest  average sentiment.
    """
    sent_file = sys.argv[1]
    tweet_file = sys.argv[2]
    prescored_words = make_score_dictionary(sent_file)
    state_scores = score_file(tweet_file, prescored_words)
    best_score = None
    best_state = "None Found"
    for state in state_scores.keys():
        score = state_scores[state][0]
        count = state_scores[state][1]
        avg_sent = score/float(count)
        if avg_sent > best_score:
            best_state = state
            best_score = avg_sent
    sys.stdout.write(best_state)

# Pipeline:
#   1. Score all the tweets...create a dictionary of ALL WORDS in the process
#   2. Loop over the words, make their score the net of scores of all tweets they appear in
#        
if __name__ == '__main__':
    main()
    

