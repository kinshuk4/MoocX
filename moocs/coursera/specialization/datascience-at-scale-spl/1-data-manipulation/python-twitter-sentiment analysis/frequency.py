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
	non_senti_words = []
	for word in line.split(' '):
		if word in senti_dict:
			senti_score += senti_dict[word]
		else:
			non_senti_words.append(word)
	return senti_score,non_senti_words



def update_dict(dct,tweet):
	for word in tweet.split():
		if word in dct:
			dct[word] += 1
		else:
			dct[word] = 1



def main():
	dct = defaultdict()
	tweet_file = open(sys.argv[1])
	for line in tweet_file:
		d = json.loads(line.encode('utf8'))
		if 'text' in d.keys():
			update_dict(dct,norm_word(d['text'].encode('utf8')))

	all_occ = sum(dct.values())
	for word in dct.keys():
		print word,dct[word]/float(all_occ)


if __name__ == '__main__':
    main()


