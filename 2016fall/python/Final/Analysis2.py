#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu
# Basic user information

import pylab
import os
import sys
import pandas as pd
import argparse
import numpy as np
from matplotlib import pyplot as plt
import seaborn as sns

# get input 
parser = argparse.ArgumentParser(description='search for diff by different size of sample')
parser.add_argument("prop", help="which one want to see")
args = parser.parse_args()

# Get the path
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"

def loadData(path):
	users = pd.read_csv(path + '/users.csv')
	occupation = pd.read_csv(path + '/occupation.csv')
	age = pd.read_csv(path + '/age.csv')
	data = pd.merge(age, pd.merge(users, occupation))
	return data

# create pie chart by property
def ageChart(age):
	explode = (0.1, 0, 0, 0, 0, 0, 0)
	#colors = ['blue', 'green']
	labels = age.index
	number = len(labels)
	plt.pie(age.values, explode=explode, labels = labels,
	        autopct='%1.1f%%', shadow=True, startangle=140)
	plt.axis('equal')
	plt.title('Age group')
	plt.show()

def genderChart(gender):
	explode = (0.1, 0)
	labels = gender.index
	number = len(labels)
	plt.pie(gender.values, explode=explode, labels = labels,
	        autopct='%1.1f%%', shadow=True, startangle=140)
	plt.axis('equal')
	plt.title('Popularity Percentage of Gender')
	plt.show()

def occupationChart(occupation):
	labels = occupation.index
	number = len(labels)
	plt.pie(occupation.values, autopct='%1.1f%%', shadow=True, startangle=140)
	plt.legend(labels, loc="best")
	plt.axis('equal')
	plt.title('Popularity Percentage of Occupation')
	plt.show()

def createFig(age, gender, occupation):
	#fig = plt.figure(figsize=(8,8))
	#ax1 = fig.add_subplot(2, 1, 1)
	#ax2 = fig.add_subplot(2, 1, 2)
	ageChart(age)
	genderChart(gender)
	occupationChart(occupation)
	#plt.show()


data = loadData(path)
path = ScriptPath +'/output'
age = data.groupby('age_group').size()
age.to_csv(path + '/Analysis2/age_count.csv')

gender = data.groupby('gender').size()
gender.to_csv(path + '/Analysis2/gender_count.csv')

occupation = data.groupby('job_description').size()
occupation.to_csv(path + '/Analysis2/occupation_count.csv')

if args.prop == 'all': 
	createFig(age, gender, occupation)
elif args.prop == 'age':
	ageChart(age)
elif args.prop == 'gender':
	genderChart(gender)
else:
	occupationChart(occupation)

