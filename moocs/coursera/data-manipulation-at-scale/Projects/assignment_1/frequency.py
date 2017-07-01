import sys
import json

def clean_word(word):
    #coerce to lower
    temp = word.lower()
    # remove special characters
    exclude = set([",","!","?",":",".", ";"])
    temp = "".join(ch for ch in temp if not (ch in exclude))
    return temp

def term_freq_hist(tweet_file):
    """
    Given a file of the JSON output of streaming
    data from the Twitter API, returns a dictionary
    containing every term and its relative frequency,
    calculated as (# of occurrences)/(# of total terms in file).
    """
    with open(tweet_file) as tweet_file:
        #Read in all the tweets
        tweets = tweet_file.readlines()
        # Loop over tweets, creating dictionary of word freqs
        freq_hist = {}
        total_terms = 0
        for line in tweets:
            try:
                temp = json.loads(line)
                tweet = temp[u'text'].encode('utf-8')
                for word in tweet.split():
                    total_terms += 1
                    word_cln = clean_word(word)
                    if word_cln in freq_hist.keys():
                        freq_hist[word_cln] += 1
                    else:
                        freq_hist[word_cln] = 1
            except:
                continue #handle errors from misformatted JSON
                
        # Loop through this new dictionary,
        # convert freqs to relative freqs
        for word in freq_hist.keys():
            freq_hist[word] = freq_hist[word]/float(total_terms)
            
    return freq_hist   

def main():
    freq_hist = term_freq_hist(sys.argv[1])
    for term in freq_hist.keys():
        sys.stdout.write(term + " " + str(freq_hist[term]) + "\n")
        
#call the function (gulp)
if __name__ == '__main__':
    main()
            
    