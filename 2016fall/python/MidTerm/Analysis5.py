#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import json
import os
import sys
import requests
import csv

def query(path):

	input_file = open(path + "/questions.json", "r")
	csvfile = open(path + "/analysis5.csv", 'w')
	writer = csv.writer(csvfile)
	title = ["User_Id", "Score"]
	writer.writerow(title)

	# user_dic : {userId, accumulated_score}
	score_dic = {}

	json_decode=json.load(input_file)
	for item in json_decode:
		userId = item.get("owner").get("user_id")
		score = item.get("score")

		# Construct the score dictionary
		if userId in score_dic.keys():
			score_dic[userId] = score_dic[userId] + score
		else :
			score_dic[userId] = score

	for user in sorted(score_dic, key=score_dic.get, reverse=True):
		data = [user, score_dic[user]]
		writer.writerow(data)

	print("Users with their scores are stored in analysis5.csv!")

	highest = 1
	for user in sorted(score_dic, key=score_dic.get, reverse=True):
		if highest == 1:
			print("userId: " + str(user))
			print("highest score: " + str(score_dic[user]))
			break;

	input_file.close()
	csvfile.close()


ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"
query(path)



