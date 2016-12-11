#!/usr/bin/python
# coding: utf-8

# Author: Yan Wu

import pylab
import os
import sys
import pandas as pd
import numpy as np
from datetime import datetime

# Get the directory of current script
ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]
path = ScriptPath + "/data"
print(path)

# load data
movies = pd.read_csv(path + '/movies.csv')
users = pd.read_csv(path + '/users.csv')
links = pd.read_csv(path + '/links.csv')
ratings = pd.read_csv(path + '/ratings.csv')
occupation = pd.read_csv(path + '/occupation.csv')
age = pd.read_csv(path + '/age.csv')

# extract year
movie_links = pd.merge(movies, links)
#print(movie_links[movie_links.ix[:, 'genres'].str.contains("Children")].head())
#movie_links['year'] = movie_links.title.apply(lambda x: datetime.strptime(x.split(' ')[-1][1:-1],'%Y').date() if x.split(' ')[-1][1:-1].isdigit() else np.NAN)
movie_links['year'] = movie_links.title.apply(lambda x: x[-5:-1] if x[-5:-1].isdigit() else np.NAN)
movie_links = movie_links.dropna()
movie_links.title = movie_links.title.apply(lambda x: x[: x.find('(')])
print(movie_links.head())

# convert timestamp to date
ratings['date'] = ratings.timestamp.apply(lambda x: datetime.fromtimestamp(int(x)).strftime('%Y'))

# separated the genre
unique_genre = set()
for genre in movie_links.genres.values:
    unique_genre.update(genre.split('|'))
#print(len(unique_genre))
df = pd.merge(pd.merge(movie_links, ratings), users)
for genre in unique_genre:
    movie_links[genre] = 0
for genre in unique_genre:
    movie_links.ix[movie_links.ix[:, 'genres'].str.contains(genre), genre] = 1

#users = pd.merge(users, age)

movie_ratings = pd.merge(movie_links, ratings)
user_ratings = pd.merge(movie_ratings, users)
user_ratings = user_ratings.drop_duplicates() 
user_ratings = user_ratings.dropna(how='any')
print(user_ratings.head())

user_ratings.to_csv(path + '/user_ratings.csv')


# separate movies by genres
base_string = path + '/genres/'
df['cmd'] = base_string
for genre in unique_genre:
	df[df['genres'].str.contains(genre)].to_csv(base_string + genre + '.csv')




