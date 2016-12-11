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
result1 = []
input_file1=open(path1, "r")
json_decode1=json.load(input_file1)
for item1 in json_decode1:
    value1=item1.get("user").get("friends_count")

    result1.append(value1)
print("average friends count for user who tweets Trump: ")
print(sum(result1) / float(len(result1)))

path2 = ScriptPath + "/" + term2 + "/" + since + "/result.json"
result2 = []
input_file2 = open(path2, "r")
json_decode2 = json.load(input_file2)
for item2 in json_decode2:
    value2=item2.get("user").get("friends_count")
    
    result2.append(value2)
print("average friends count for user who tweets Hillary: ")
print(sum(result2) / float(len(result2)))
