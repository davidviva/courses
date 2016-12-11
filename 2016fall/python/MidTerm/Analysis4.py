#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import json
import os
import sys
import requests
import csv

# Get the directory of the collected data
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

# key: tag, value: [number of questions, number of questions were answered]
tag_dict = {}

csvfile = open(path + "/analysis4.2.csv", 'w')
writer = csv.writer(csvfile)
title = ["Question_Id", "Question_title", "tags", "Number of Answers"]
writer.writerow(title)

input_file = open(path + "/questions.json", "r")
json_decode=json.load(input_file)
for item in json_decode:
	ques_id = item.get("question_id")
	# Get the list of tags of each questiones
	tags = item.get("tags")
	# Find if the questino is answered
	is_answered = item.get("is_answered")
	# Get the number of answers given for each question
	answer_count = item.get("answer_count")

	# write the questionTags.csv
	data = [ques_id, item.get("title"), tags, answer_count]
	writer.writerow(data)

	answered = 0
	if is_answered:
		answered = 1

	for tag in tags:
		if tag_dict.get(tag) is None:
			tag_dict[tag] = [1, answered]
		else:
			tag_dict[tag][0] += 1
			tag_dict[tag][1] += answered

input_file.close()
csvfile.close()

csvfile2 = open(path + "/analysis4.csv", 'w')
writer2 = csv.writer(csvfile2)
title2 = ["Tags", "Number of questions", "Number of Answered"]
writer2.writerow(title2)

for key in tag_dict.keys():
	print("tag: " + key + "  ")
	print("  # questions: " + str(tag_dict[key][0]))
	print("  # answered: " + str(tag_dict[key][1]))
	print("/n")
	data2 = [key, tag_dict[key][0], tag_dict[key][1]]
	writer2.writerow(data2)

csvfile2.close()






