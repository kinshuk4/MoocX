# -*- coding: utf-8 -*-
import sys
import json
import string
from collections import defaultdict

def construct_dict(sentiment_score_file):
    senti_file = open(sentiment_score_file)
    scores = {} # initialize an empty dictionary
    for line in senti_file:
      term, score  = line.split("\t")  # The file is tab-delimited. "\t" means "tab character"
      scores[term] = int(score)  # Convert the score to an integer.	
    return scores

def norm_word(word):
    exclude = set(string.punctuation)
    word = ''.join(ch for ch in word.lower() if ch not in exclude)
    return word

def get_senti(senti_dict,line):
	senti_score = 0
	for word in line.split(' '):
		if word in senti_dict:
			senti_score += senti_dict[word]
	return senti_score

def geo_info(tweet):
	try:

		if tweet['place']['country_code'] == 'US':
			state = tweet['place']['full_name'][-2:]
			#print state
			return True,state
		else:
			return False,''
	except:
		pass
	return False,''


def main():
	senti_dict = construct_dict(sys.argv[1])
	tweet_file = open(sys.argv[2])
	
	state_happy_index = defaultdict()
	total_tweet_count = 0

	for line in tweet_file:
		d = json.loads(line.encode('utf8'))
		try:
			if d['lang'] == 'en': #accept only english tweets
				if 'text' in d.keys(): #if there is a text field
					norm_tweet = norm_word(d['text'].encode('utf8'))
					is_US,state = geo_info(d)
					if is_US: #only if tweet is from the US
						total_tweet_count += 1
						senti_score = get_senti(senti_dict,norm_tweet)
						if state in state_happy_index:
							state_happy_index[state] += senti_score
						else:
							state_happy_index[state] = senti_score
		except:
			pass

	happiest_state = 'XX'
	happy_score = -1
	saddest_state = 'YY'
	sad_score = 99999

	for state,score in state_happy_index.items():
		if score > happy_score:
			happy_score = score
			happiest_state = state
		if score < saddest_state:
			saddest_state = state
			sad_score = score
	print happiest_state

	#print happiest_state,happy_score/float(total_tweet_count)
	#print saddest_state,sad_score/total_tweet_count




if __name__ == '__main__':
    main()



