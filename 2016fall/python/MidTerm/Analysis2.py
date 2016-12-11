#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import json
import os
import sys
import requests
import csv

# check if the given path is valid, or create a new folder
def checkFolder(path):
	if not os.path.exists(path):
		os.makedirs(path)
	else:
		print("Already exists the folder")

def query(path):

	input_file = open(path + "/users.json", "r")
	# tag_dic : {tag, {userId, reputation}}
	tag_dic = {}
	# user_dic : {userId, userInfo[userName, link, reputation]}
	user_dic = {}

	json_decode=json.load(input_file)
	for item in json_decode:
		userId = item.get("user_id")
		userName = item.get("display_name")
		link = item.get("link")
		reputation = item.get("reputation")

		# Construct the user dictionary
		userInfo = [userName, link, reputation]
		user_dic[userId] = userInfo

		requestUrl = "https://api.stackexchange.com/2.2/users/" + str(userId) + "/tags?order=desc&sort=popular&site=stackoverflow&key=tpHQOQQZiglYz26b5zi5Gw(("

		# Construct tag dictionary
		tags = requests.get(requestUrl)
		for u in tags.json()["items"]:
			tag = u.get("name")
			if tag in tag_dic.keys():
				tag_dic[tag][userId] = reputation
			else:
				tag_dic[tag] = {}
				tag_dic[tag][userId] = reputation


    
	# Write the result into the .csv file
	for tag in tag_dic.keys():

		csvfile = open(path + "/analysis2/" + tag + ".csv", 'w')
		writer = csv.writer(csvfile)
		title = ["tag", "userId", "userName", "Link", "Reputation"]
		writer.writerow(title)
		for user in sorted(tag_dic[tag], key=tag_dic[tag].get, reverse=True):
			userInfo = user_dic[user]
			data = [tag, user, userInfo[0], userInfo[1], userInfo[2]]
			writer.writerow(data)
		csvfile.close()

	input_file.close()

ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"
checkFolder(path + "/analysis2")
query(path)



