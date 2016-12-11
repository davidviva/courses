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
