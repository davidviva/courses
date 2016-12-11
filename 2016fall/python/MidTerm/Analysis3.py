#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import csv
import os
import sys
import json

ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

gold_count = 0
silver_count = 0
bronze_count = 0
count = 0

input_file = open(path + "/users.json", "r+")
json_decode = json.load(input_file)
for item in json_decode:

	if item.get("badge_counts").get("bronze") != 0:
		bronze_count += 1
	if item.get("badge_counts").get("silver") != 0:
		silver_count += 1
	if item.get("badge_counts").get("gold") != 0:
		gold_count += 1
	if item.get("badge_counts").get("silver") + item.get("badge_counts").get("bronze") + item.get("badge_counts").get("gold") != 0:
		count += 1

input_file.close()

csvfile = open(path + "/analysis3.csv", 'w')
writer = csv.writer(csvfile)
title = ["gold users", "silver users", "bronze users", "users have badges"]
writer.writerow(title)
result = [gold_count, silver_count, bronze_count, count]
writer.writerow(result)

print("number of users have badges: " + str(gold_count))
print("number of users have gold badge: " + str(gold_count))
print("number of users have silver badge: " + str(silver_count))
print("number of users have bronze badge: " + str(bronze_count))




