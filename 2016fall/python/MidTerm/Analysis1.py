#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import csv
import json
import os
import sys
import requests

def query(path):
	my_dictionary = {}

	f = open(path + "/users.json", 'w+')
	t_count = 500
	f.write("[")

	input_file = open(path + "/questions.json", "r")

	json_decode=json.load(input_file)
	for item in json_decode:
		title = item.get("title")
		userId = item.get("owner").get("user_id")

		user = requests.get("https://api.stackexchange.com/2.2/users/" + str(userId) + "?site=stackoverflow&key=tpHQOQQZiglYz26b5zi5Gw((")
		# print user
		for u in user.json()["items"]:

			badgeCount = u.get("badge_counts").get("bronze") + u.get("badge_counts").get("silver")*2 + u.get("badge_counts").get("gold")*3

		# print badgeCount
		my_dictionary[title] = badgeCount

		# print unique users
		#if userId not in list:
		#	list.append(userId)
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
	f.close()
	input_file.close()
	
	sortList = sorted(my_dictionary, key=my_dictionary.get, reverse=True)
	writeFile(sortList, my_dictionary, path)

def writeFile(list, dic, path): 
	csvfile = open(path + "/analysis1.csv", 'w')
	writer = csv.writer(csvfile)
	title = ["title", "weightage"]
	writer.writerow(title)
	for item in list:
		data = [item, dic[item]]
		writer.writerow(data)
	csvfile.close()

ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"
query(path)
print("Completed! Please go to check analysis1.csv")



