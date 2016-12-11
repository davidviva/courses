#!/usr/bin/python3

import argparse
import oauth2 as oauth
from twitter import Twitter, OAuth, TwitterHTTPError, TwitterStream
import json
import sys
import os
import time
import datetime
 
def parse_args():
	description = "Search twitter api."
	parser = argparse.ArgumentParser(description = description)

	parser.add_argument("term", help = "target term")
#	parser.add_argument("since", help = "start date for tweet")
#	parser.add_argument("until", help = "end date for tweet")

	args = parser.parse_args() 
	return args

def checkFolder(path):
	if not os.path.exists(path):
		os.makedirs(path)
	else:
		print("Already exists the folder")

def search(path): 
	consumer_key = "XmqX6OMesKuafQn1Kr02MAAzQ"
	consumer_secret = "ucKTLitRLFm5bXF8Sq2equZGNDGycaVGbFTADhBlirU2JBC8iH"
	access_token = "790677486921220096-7LhHIIYyz1wegGas1y0sOGKU8fVwBtB"
	access_token_secret	= "oVN6tszqwJblfWqk6Z2cK6y65qv3xz2MTLD4PHnoiGc52"

	oauth = OAuth(access_token, access_token_secret, consumer_key, consumer_secret)
	twitter = Twitter(auth = oauth)
	twitter_stream = TwitterStream(auth = oauth)
	
	dt = datetime.datetime.now()
	since = dt.strftime("%Y-%m-%d")
	until = (dt + datetime.timedelta(days = 1)).strftime("%Y-%m-%d")

	count = 100
	iterator = twitter.search.tweets(q = args.term, count = 100, since = since, until = until)

	newpath = path + "/" + since
	checkFolder(newpath)
	f = open(newpath + "/result.json", 'w+')
	f.write("[")
	for tweet in iterator["statuses"]:
	# for tweet in iterator:
	    count -= 1
	    if count < 0:
	    	break

	    if count == 0:
	    	f.write(json.dumps(tweet))
	    	continue

	    f.write(json.dumps(tweet))
	    f.write(",") 
	    f.write("\n")
	    
	f.write("]")

#input("Please input the search term: ")
args = parse_args()
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/" + args.term
checkFolder(path)
search(path)



