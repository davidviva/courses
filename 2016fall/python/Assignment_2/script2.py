#!/usr/bin/python

import json
import sys
import os
import datetime

term1 = "trump"
term2 = "hillary"

def analysis1(term1, term2):
	ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
	dt = datetime.datetime.now()
	since = dt.strftime("%Y-%m-%d")

	path1 = ScriptPath + "/" + term1 + "/" + since + "/result.json"
	result1 = []
	input_file1=open(path1, "r")
	json_decode1=json.load(input_file1)
	for item1 in json_decode1:
	    value1=item1.get("user").get("friends_count")

	    result1.append(value1)
	print("average friends count for user who tweets " + term1 + " ")
	print(sum(result1) / float(len(result1)))

	path2 = ScriptPath + "/" + term2 + "/" + since + "/result.json"
	result2 = []
	input_file2 = open(path2, "r")
	json_decode2 = json.load(input_file2)
	for item2 in json_decode2:
	    value2=item2.get("user").get("friends_count")
	    
	    result2.append(value2)
	print("average friends count for user who tweets " + term2 + " ")
	print(sum(result2) / float(len(result2)))

def analysis2(term1, term2):
	ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
	dt = datetime.datetime.now()
	since = dt.strftime("%Y-%m-%d")

	path1 = ScriptPath + "/" + term1 + "/" + since + "/result.json"
	result1 = []
	input_file1=open(path1, "r")
	json_decode1=json.load(input_file1)
	for item1 in json_decode1:
	    text1 = item1.get("text")
	    value1 = text1.split()
	    result1.append(len(value1))
	print("average tweet words count for user who tweets " + term1 + " ")
	print(sum(result1) / float(len(result1)))

	path2 = ScriptPath + "/" + term2 + "/" + since + "/result.json"
	result2 = []
	input_file2=open(path2, "r")
	json_decode2=json.load(input_file2)
	for item2 in json_decode2:
	    text2 = item2.get("text")
	    value2 = text2.split()
	    count2 = len(value2)
	    result2.append(count2)
	print("average tweet words count for user who tweets " + term2 + " ")
	print(sum(result2) / float(len(result2)))

def analysis3(term1, term2):
	ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
	dt = datetime.datetime.now()
	since = dt.strftime("%Y-%m-%d")

	path1 = ScriptPath + "/" + term1 + "/" + since + "/result.json"

	count1 = 0
	sum1 = 0;
	input_file1=open(path1, "r")
	json_decode1=json.load(input_file1)
	for item1 in json_decode1:
	    friends1 = item1.get("user").get("friends_count")
	    sum1 += friends1
	    count1 += 1
	print("average number of friends a user who tweets Trump: ")
	print(sum1 / float(count1))

	path2 = ScriptPath + "/" + term2 + "/" + since + "/result.json"
	sum2 = 0
	count2 = 0
	input_file2=open(path2, "r")
	json_decode2=json.load(input_file2)
	for item2 in json_decode2:
	    friends2 = item2.get("user").get("friends_count")
	    count2 += 1
	    sum2 += friends2
	print("average number of friends a user who tweets Hillary: ")
	print(sum2 / float(count2))


def analysis4(term1, term2):
	ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
	dt = datetime.datetime.now()
	since = dt.strftime("%Y-%m-%d")

	path1 = ScriptPath + "/" + term1 + "/" + since + "/result.json"

	count1 = 0
	sum1 = 0;
	input_file1=open(path1, "r")
	json_decode1=json.load(input_file1)

	max = 0
	text = ""
	for item1 in json_decode1:
	    friends1 = item1.get("user").get("followers_count")

	    if friends1 > max :
	    	max = friends1
	    	text = item1.get("text")
	print("the most influential tweet mentioned trump is: ")
	print(text)

def analysis5(term1, term2):
	ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
	dt = datetime.datetime.now()
	since = dt.strftime("%Y-%m-%d")

	path1 = ScriptPath + "/" + term1 + "/" + since + "/result.json"

	count1 = 0
	input_file1 = open(path1, "r")
	json_decode1=json.load(input_file1)
	for item1 in json_decode1:
	    count1 += 1
	input_file1.close()

	path2 = ScriptPath + "/" + term2 + "/" + since + "/result.json"
	count2 = 0
	input_file2=open(path2, "r")
	json_decode2=json.load(input_file2)
	for item2 in json_decode2:
	    count2 += 1
	input_file2.close()
	if count1 > count2:
		print("People mentioned " + term1 + " more today.")
	else :
		print("People mentioned " + term2 + " more today.")


term1 = input("/nPlease input the first term: ")
term2 = input("/nPlease input the second term: ")
num = input("/nPlease input the analysis num: ")
continued = "y"

while continued is 'y':
	if num is '1':
		analysis1(term1, term2)
	elif num is '2':
		analysis2(term1, term2)
	elif num is '3':
		analysis3(term1, term2)
	elif num is '4':
		analysis4(term1, term2)
	else:
		analysis5(term1, term2)

	continued = input("/ncontinue? y or n ")
	if continued is 'y':
		num = input("/nPlease input the analysis num: ")

print("finished")




