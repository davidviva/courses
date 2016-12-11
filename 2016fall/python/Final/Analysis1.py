#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu
# statistics of user infomation

import pylab
import os
import sys
import pandas as pd
import numpy as np
from matplotlib import pyplot as plt
import seaborn as sns
import argparse

# get input 
parser = argparse.ArgumentParser(description='see average ratings in different dimensions')
parser.add_argument("prop", help="which one want to see")
args = parser.parse_args()


# load data
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

user_ratings = pd.read_csv(path + '/user_ratings.csv')
occupation = pd.read_csv(path + '/occupation.csv')
age = pd.read_csv(path + '/age.csv')

data = pd.merge(age, pd.merge(user_ratings, occupation))
#print(userInfo.head())
path = ScriptPath + "/output"
unique_genre = set()
for genre in user_ratings.genres.values:
    unique_genre.update(genre.split('|'))

def age():
	pieces = []
	for genre in unique_genre:
		temp = pd.pivot_table(data, values= 'rating',index = genre,columns='age_group',aggfunc='mean')[1:]
		pieces.append(pd.DataFrame(temp.values, index = [genre], columns = temp.columns))

	df_age = pd.concat(pieces)
	df_age.to_csv(path + '/Analysis1/genre_age.csv')
	df_age = df_age.dropna(how = 'any')
	#print(df_age)
	plt.figure(figsize=(8,8))
	sns.set(font_scale=0.8)
	sns.heatmap(df_age)
	plt.title("Average ratings of each genre by each age group")
	plt.ylabel('Genres')
	plt.xlabel('Age group');
	plt.show()

def gender():
	pieces = []
	for genre in unique_genre:
		temp = pd.pivot_table(data, values= 'rating',index = genre,columns='gender',aggfunc='mean')[1:]
		pieces.append(pd.DataFrame(temp.values, index = [genre], columns = temp.columns))

	df_gender = pd.concat(pieces)
	df_gender.to_csv(path + '/Analysis1/genre_gender.csv')
	df_gender = df_gender.dropna(how = 'any')
	#print(df_gender)
	plt.figure(figsize=(14,8))
	list = df_gender.index
	plt.xticks(range(len(list)), list, rotation=30)
	#ax.set_xticklabels(list, rotation=30)
	plt.title('Average ratings of each genre by each gender')
	plt.plot(df_gender.M.values, 'gx--', label='male')
	plt.plot(df_gender.F.values, 'b-', label='female')
	plt.legend(loc='best') 
	plt.title("Average ratings of each genre by each age group")
	plt.ylabel('Rating')
	plt.xlabel('Genres');
	plt.show()

def occupation():
	pieces = []
	for genre in unique_genre:
		temp = pd.pivot_table(data, values= 'rating',index = genre,columns='job_description',aggfunc='mean')[1:]
		pieces.append(pd.DataFrame(temp.values, index = [genre], columns = temp.columns))

	df_occu = pd.concat(pieces)
	df_occu.to_csv(path + '/Analysis1/genre_occupation.csv')
	df_occu = df_occu.dropna(how = 'any')
	#print(df_occu)
	plt.figure(figsize=(8,8))
	sns.set(font_scale=0.8)
	plt.xticks(rotation=30)
	plt.yticks(rotation=30)
	sns.heatmap(df_occu)
	plt.title("Average ratings of each genre by each occupation")
	plt.ylabel('Genres')
	plt.xlabel('Occupation');
	plt.show()


if args.prop == 'all': 
	age()
	gender()
	occupation()
elif args.prop == 'age':
	age()
elif args.prop == 'gender':
	gender()
else:
	occupation()




