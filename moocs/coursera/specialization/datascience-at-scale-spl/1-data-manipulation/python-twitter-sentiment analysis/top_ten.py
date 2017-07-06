# -*- coding: utf-8 -*-
import sys
import json
import string
from collections import defaultdict
import operator


def norm_word(word):
    exclude = set(string.punctuation)
    word = ''.join(ch for ch in word.lower() if ch not in exclude)
    return word

def upd_dict(h_tags,h_dict):

	for hashtag in h_tags:
		if hashtag in h_dict:
			h_dict[hashtag] += 1
		else:
			h_dict[hashtag] = 1

def main():
	tweet_file = open(sys.argv[1])
	h_dict = defaultdict()
	for line in tweet_file:
		d = json.loads(line.encode('utf8'))
		hashtags = d['entities']['hashtags']
		h_tags = []
		for tags in hashtags:
			h_tags.append(norm_word(tags['text'].encode('utf8')))

		upd_dict(h_tags,h_dict)
	#print h_dict
	h_dict = sorted(h_dict.items(), key=operator.itemgetter(1),reverse=True)
	#print h_dict
	for i in range(10):
		print h_dict[i][0],h_dict[i][1]

if __name__ == '__main__':
    main()
