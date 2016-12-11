#!/usr/bin/python

# Author: Yan Wu

import json
import os
import sys
from stackapi import StackAPI

# check if the given path is valid, or create a new folder
def checkFolder(path):
	if not os.path.exists(path):
		os.makedirs(path)
	else:
		print("Already exists the folder")

# store 500 questiones with the given tags and store them into a .json file
def query(path):
	key = "tpHQOQQZiglYz26b5zi5Gw(("

	# fetch 600 records by default
	questions = StackAPI('stackoverflow').fetch('questions', tagged='python; numpy')

	f = open(path + "/questions.json", 'w+')
	# Get 500 records from the fetch result
	t_count = 500

	# write the records into .json file
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
	f.close()

# Get the directory of current script
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

checkFolder(path)
query(path)

