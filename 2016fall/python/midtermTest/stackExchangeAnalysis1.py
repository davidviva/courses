#!/usr/bin/python

import json
import os
import sys
import argparse
import requests
# from stackauth import StackAuth
from stackexchange import Site, StackOverflow
from stackpy import API, Site
# from stackapi import StackAPI
from datetime import datetime

parser = argparse.ArgumentParser(description='Search stack exchange api.')
parser.add_argument("directory")
args = parser.parse_args()

if not os.path.exists(args.directory + "/stackApi"):
	os.makedirs(args.directory + "/stackApi")
else:
	print (args.directory + "/stackApi" + " exists already")

my_dictionary = {}

f = open(args.directory + "/stackApi" + "/stackUserOutput.json", 'w+')
t_count = 600
f.write("[")

input_file = open(args.directory + "/stackApi/stackOutput.json", "r")
# input_file=open(args.file, "r")
json_decode=json.load(input_file)
for item in json_decode:
	title = item.get("title")
	userId = item.get("owner").get("user_id")

	# print title
	# print userId

	# site = StackAPI('stackoverflow', "j5VAW9jTzqHlVWRbeZZ2ng((")
	# site = Site(StackOverflow)
	# user = site.users('users/{ids}', id=userId)
	# user = site.users.ids(ids=[userId])
	user = requests.get("https://api.stackexchange.com/2.2/users/" + str(userId) + "?site=stackoverflow&key=j5VAW9jTzqHlVWRbeZZ2ng((")
	# print user
	for u in user.json()["items"]:

		badgeCount = u.get("badge_counts").get("bronze") + u.get("badge_counts").get("silver") + u.get("badge_counts").get("gold")

	# print badgeCount
	my_dictionary[title] = badgeCount

	for u in user.json()["items"]:
		t_count -= 1

		if t_count < 0:
		    break

		if t_count == 0:
		    f.write(json.dumps(u))
		    continue

		f.write(json.dumps(u))
		f.write(",") 
		f.write("\n")
f.write("]")

# count = 10
print("badge count by reverse order: ")
for w in sorted(my_dictionary, key=my_dictionary.get, reverse=True):
	# count -= 1

	# if count < 0:
	# 	break

	print w, my_dictionary[w]









