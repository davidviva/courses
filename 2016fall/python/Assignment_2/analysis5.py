#!/usr/bin/python

import json
import sys
import os
import datetime

ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
dt = datetime.datetime.now()
since = dt.strftime("%Y-%m-%d")

term1 = "trump"
term2 = "hillary"

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
	print("People mentioned trump more today.")
else :
	print("People mentioned hillary more today.")