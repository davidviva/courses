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

max = 0
text = ""
for item1 in json_decode1:
    friends1 = item1.get("user").get("followers_count")

    if friends1 > max :
    	max = friends1
    	text = item1.get("text")
print("the most influential tweet mentioned trump is: ")
print(text)

