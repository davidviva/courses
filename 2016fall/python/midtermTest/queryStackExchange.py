#!/usr/bin/python

import json
import os
import sys
import argparse
from stackauth import StackAuth
from stackexchange import Site, StackOverflow
from stackpy import API, Site
from stackapi import StackAPI
from datetime import datetime

parser = argparse.ArgumentParser(description='Search stack exchange api.')
parser.add_argument("directory")
args = parser.parse_args()

def createFolder():
    
	# if not os.path.exists("/Users/KurtWang/Desktop/study"):
	# 	os.makedirs("/Users/KurtWang/Desktop/study")
	# else:
	# 	print ("/Users/KurtWang/Desktop/study exists already")

	if not os.path.exists(args.directory):
		os.makedirs(args.directory)
	else:
		print (args.directory + " exists already")


def query():
	API.key = "MsNDlFD9*pndob5maVkoJQ(("
	API.client_id = "8199"
	API.client_secret = "MsNDlFD9*pndob5maVkoJQ(("
	if not os.path.exists(args.directory + "/stackApi"):
		os.makedirs(args.directory + "/stackApi")
	else:
		print (args.directory + "/stackApi" + " exists already")

	site = StackAPI('stackoverflow')
	# site = Site(StackOverflow)
	questions = site.fetch('questions', tagged='python; pandas')

	f = open(args.directory + "/stackApi" + "/stackOutput.json", 'w+')
	t_count = 600
	f.write("[")
	for t in questions['items']:
	    t_count -= 1

	    if t_count < 0:
	    	break

	    if t_count == 0:
	    	f.write(json.dumps(t))
	    	continue

	    f.write(json.dumps(t))
	    f.write(",") 
	    f.write("\n")
	    
	f.write("]")

createFolder()
query()












