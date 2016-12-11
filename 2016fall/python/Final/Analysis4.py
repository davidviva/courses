#!/usr/bin/python
# coding: utf-8
#%matplotlib inline

# Author: Yan Wu
# the change of taste of genre  

import os
import sys
import pandas as pd
import numpy as np
import argparse
from datetime import datetime
import matplotlib.pyplot as plt

def generateChart():
	plt.figure(figsize=(14,8))
	list = mean_ratings.index
	plt.xticks(range(len(list)), list, rotation=30)
	plt.title('Rating changes during years for ' + args.genre)
	#for col in mean_ratings.columns:
	#	plt.plot(mean_ratings[col].values, label=col)
	plt.plot(mean_ratings[args.age].values, label=args.age)
	plt.legend(loc='best') 
	plt.title("Average ratings of each year by each the given age group")
	plt.ylabel('Rating')
	plt.xlabel('Rating Year');
	plt.show()

# get input 
parser = argparse.ArgumentParser(description='see the changes of taste in the age group')
parser.add_argument("genre", help="which one want to see")
parser.add_argument("age", help="which one want to see")
args = parser.parse_args()

# load data
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

age = pd.read_csv(path + '/age.csv')
ratings = pd.read_csv(path + '/genres/' + args.genre + '.csv')
data = pd.merge(age, ratings)

path = ScriptPath + "/output"

mean_ratings = pd.pivot_table(data, values= 'rating',index=['date'],columns='age_group',aggfunc='mean')
mean_ratings.to_csv(path + '/Analysis4/'+args.genre+'.csv')
mean_ratings = mean_ratings.dropna(how = 'all')
generateChart()



