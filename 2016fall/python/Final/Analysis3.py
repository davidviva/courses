#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu
# measuring rating disagreement between different genders

import os
import sys
import pandas as pd
import numpy as np
import argparse
from datetime import datetime
from matplotlib import pyplot as plt

# get input
parser = argparse.ArgumentParser(description='search for diff by different size of sample')
parser.add_argument("size", help="at least number of samples")
parser.add_argument("top", help="topk movies favored by male and female")
args = parser.parse_args()

# load data
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"
data = pd.read_csv(path + '/user_ratings.csv')
path = ScriptPath + "/output"

# calculated the average ratings of each movie by gender
mean_ratings = pd.pivot_table(data, values= 'rating',index=['title'],columns='gender',aggfunc='mean')

# calculated the number of users rated of each movie by gender
#temp = pd.pivot_table(data,values = 'rating',index=['title'],columns=['gender'],aggfunc='count')
#print(temp[:10])

# calculated the number of ratings of each movie
ratings_by_title = data.groupby('title').size()

# filter movies that have too few number of ratings
active_titles = ratings_by_title.index[ratings_by_title >= int(args.size)]  
mean_ratings = mean_ratings.ix[active_titles]  
#print(mean_ratings.head())

# get top average rating movies by gender
topM = mean_ratings.sort_values(by='M', ascending=False)
topF = mean_ratings.sort_values(by='F', ascending=False)

topM.to_csv(path + '/Analysis3/male_favored.csv')
topF.to_csv(path + '/Analysis3/female_favored.csv')
print(topM[:int(args.top)])
print(topF[:int(args.top)])

#the diff
mean_ratings['diff'] = mean_ratings['M'] - mean_ratings['F']  
sorted_by_diff = mean_ratings.sort_values(by='diff') 
sorted_by_diff.to_csv(path + '/Analysis3/diff.csv')
print(sorted_by_diff[:10])

pieces = [sorted_by_diff[:10], sorted_by_diff[-10:]]
disagreements = pd.concat(pieces)['diff']
print(disagreements)
disagreements.plot(kind='barh', figsize=[8, 8])
plt.title('Male vs. Female Avg. Ratings\n(Difference > 0 = Favored by Men)')
plt.ylabel('Title')
plt.xlabel('Average Rating Difference');
plt.show()










