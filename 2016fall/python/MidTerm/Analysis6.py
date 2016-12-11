#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import csv
import os
import sys
import json

ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

csvfile = open(path + "/analysis6.csv", 'w')
writer = csv.writer(csvfile)
title = ["name", "gold badges", "silver badges", "bronze badges"]
writer.writerow(title)

input_file = open(path + "/users.json", "r+")
json_decode = json.load(input_file)
for item in json_decode:

	gold = item.get("badge_counts").get("gold")
	silver = item.get("badge_counts").get("silver")
	bronze = item.get("badge_counts").get("bronze")
	list = []
	if silver != 0:
		if gold != 0:
			list.append(item.get("display_name"))
			list.append(gold)
			list.append(silver)
			list.append(bronze)
			writer.writerow(list)

input_file.close()
csvfile.close()




