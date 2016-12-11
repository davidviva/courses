#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import os
import sys
import pandas as pd
import numpy as np
from datetime import datetime
from matplotlib import pyplot as plt
import argparse

# get input 
parser = argparse.ArgumentParser(description='very simple recommendation')
parser.add_argument("gender", help="gender")
parser.add_argument("age", help="age")
parser.add_argument("occup", help="occup")
parser.add_argument("topK", help="how manywant to see")
args = parser.parse_args()

# load data
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

user_ratings = pd.read_csv(path + '/user_ratings.csv')
occupation = pd.read_csv(path + '/occupation.csv')
age = pd.read_csv(path + '/age.csv')

df = pd.merge(age, pd.merge(user_ratings, occupation))
#print(userInfo.head())
path = ScriptPath + "/output"

def  calculate(data):
	age = data[data['age'] == int(args.age)]
	gender = age[data['gender'] == args.gender]
	occupation = gender[data['job_description'] == args.occup]

	ratings_by_title = df.groupby('title').size()
	active_titles = ratings_by_title.index[ratings_by_title >= 50]  

	mean_ratings = pd.pivot_table(occupation, values= 'rating',index=['title'],columns='gender',aggfunc='mean')
	mean_ratings = mean_ratings.ix[active_titles]  
	mean_ratings = mean_ratings.sort_values(by = args.gender, ascending = False)
	mean_ratings.to_csv(path + '/Analysis5/recommendation.csv') 
	
	return  mean_ratings

result = calculate(df)[:int(args.topK)]

plt.figure(figsize=(8,9))
result.plot(kind='bar', facecolor = 'lightskyblue',edgecolor = 'w')
plt.xticks(rotation=90) 
plt.xlabel('Titles')
plt.ylabel('mean rating')
plt.title('Top recommendations')
plt.show()  

#print(result)

