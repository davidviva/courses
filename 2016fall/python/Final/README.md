# PythonFinal

## Dataset:  
  1.MovieLens:  
  (http://files.grouplens.org/datasets/movielens/ml-latest-small-README.html)
  &emsp;users.csv: userId, gender, age, occupation, zipcode  
  &emsp;movies.csv: movieId, title, genres  
  &emsp;ratings.csv: userId, movieId, rating, timestamp  
  &emsp;tags.csv: userId, movieId, tag, timestamp  
  &emsp;links.csv: movieId, imdbId, tmdbId  
  &emsp;genome-tags.csv: tagId, tag  
  &emsp;age.csv: age, age_group  
  &emsp;occupation.csv: occupation, description  
  
### CollectData and pre-processing:    
  1.CollecteData: download .csv files directly  
  2.pre-processing:  
  &emsp;(1) load all the files    
  &emsp;(2) extract publican year from movie titles and add a new column to store it  
  &emsp;(3) convert the rating timestamp into date and extract the year to sotre  
  &emsp;(4) separate the genres of movies  
  &emsp;(5) join the tables(movies.csv, users.csv, ratings.csv) to get a comprehensive table  
  &emsp;(6) drop NAN rows and duplicate rows   
  &emsp;(7) separate the data by genre and create new files for each genre, store them in '/data/genres' folder    
  3.folder structure:  
  &emsp;(1) root folder: '/Final': dashboard.py, CollectData.py, Analysis scripts (Analysis1-6)    
  &emsp;(2) raw data: '/Final/data':   
  &emsp;(3) result data: '/Final/output': the screenshot of dashboard, and folders for each analysis  
  
### DashBoard:  
  use TKinter and tkMessageBox  
    ![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/dashboard.png)  
    
### Analysis1:  
  1.problem: There are 3 dimensions(age, gender, occupation) of users that may influence the rating of different genres.So we calculated the average rating of each movie genre. And show the result in plot chart and heapmap to see the different tastes. 
  2.args input:  
      &emsp;(1) string 'age' or 'gender' or 'occupation    
  3.process:  
      &emsp;step1: use argparse to get the input(age, gender, occupation, or all)  
      &emsp;step2: load data: load the comparehensive table, load occupation detail table, and merge
               get the unique_genres set  
      &emsp;step3: calculate average ratings of each genre in each dimension  
      &emsp;step4: generate heatmap and plot chart to present the results  
  4.result:   
      &emsp;(1.1) the average ratings for each genre from every age group  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis1/genre_age_fig.png)       
      &emsp;(1.2) the average ratings for each genre from every age group 
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis1/screenshot of csv files/genre_age.png)  
      &emsp;(2.1) the average ratings for each genre from every gender group    
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis1/genre_gender_fig.png)    
      &emsp;&emsp;(2.2) the average ratings for each genre from every gender group    
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis1/screenshot of csv files/genre_gender.png)  
      &emsp;(3.1) the average ratings for each genre from every occupation group   
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis1/genre_occupation_fig.png)  
      &emsp;(3.2) the average ratings for each genre from every occupation group 
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis1/screenshot of csv files/genre_occupation.png)
        
### Analysis2:  
  1.problem: This part we did some analysis on the sample users to see the properties of the sample. We classifie users by age, gender, and occupation to see what's the components of our investigated targets and acknowledge what kind of people watch and rate movie more.
  2.args input:   
      &emsp;(1) string 'age' or 'gender' or 'occupation, or 'all'  
  3.process:  
      &emsp;step1: use argparse to get the input(age, gender, or occupation)   
      &emsp;step2: load data: load the users related tables: users, age, occupation, and merge
               them together to get full user informaiton  
      &emsp;step3: calculate the popularity in each age group, gender group, and occupation catagories   
      &emsp;step4: generate the pie chart to present the ratio each group  
  4.result:  
      &emsp;(1.1) the ratio of number users in each age group (pie chart)  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis2/figure_age.png)       
      &emsp;(1.2) the number of users in each age group  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis2/screenshot of csv files/age_count.png)  
      &emsp;(2.1) the ratio of number of users in each gender group (pie chart)  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis2/figure_gender.png)       
      &emsp;(2.2) the number of users in each age group  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis2/screenshot of csv files/gender_count.png)  
      &emsp;(3.1) the ratio of number of users in each occupation group (pie chart)  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis2/figure_occupation.png)       
      &emsp;(3.2) the number of users in each age group  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis2/screenshot of csv files/occupation_count.png)  
      
### Analysis3:  
  1.problem: This part we want to see some differences between male and female. So I calculates the average rating of each movie and sort it separately by men and women, then compare the ratings of men and women, find the most k disagreement movies  
  2.args input:  
      &emsp;(1) the filter threshold, we will filter movies that don't have enough rating numbers;  
      &emsp;(2) number k, we will show top k movies that female like while male don't, and top k movies that male like while female don't  
  3.process:  
      &emsp;step1: use argparse to get the input(the threshold of fillter data, and the topk number)  
      &emsp;step2: load data: load the user_ratings table  
      &emsp;step3: use pivot table to calculate average rating of each gender and group by movie title   
      &emsp;step4: statistic the number of ratings of each movie, filter records that below the threshold   
      &emsp;step5: sort the ratings by gender and store   
      &emsp;step6: add a new column to store the rating differences between male and female(use 'male rating - female rating')   
      &emsp;step7: pick the topk * 2 (favored by male and female) to generate a barh chart  
  4.result  
      &emsp;(1.1) top 2 * k movies that get the most differenct average ratings from male and female  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis3/figure_3.png)         
      &emsp;(1.2) the average ratings of each movie by different genders and sorted by female ratings  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis3/screenshot of csv files/female_favored.png)  
      &emsp;(1.3) the average ratings of each movie by different genders and sorted by male ratings   
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis3/screenshot of csv files/male_favored.png)  
      &emsp;(1.4) the average ratings of each movie by different genders and the difference of ratings between genders, sorted the data by the difference (use 'male rating - female rating')   
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis3/screenshot of csv files/diff.png)  
      
### Analysis4:  
  1.problem: Because people's taste(rating) of a given movie genre may change as time, people may want to know what is the trend of people in the same group. We calculated each age group's average ratings for the give movie genre of every year, and present it in a plot chart to show the trends.  
  2.args input:  
      &emsp;(1) age group  
      &emsp;(2) genre  
  3.process: 
      &emsp;step1: use argparse to get the input(age_group, genre)  
      &emsp;step2: load data: load the genre_rating file according to the given genre , merge age_group with the rating data
      &emsp;step3: calculate the average ratings year by year for the given genre movies and age_group   
      &emsp;step4: generate the plot chart to present the taste change trends by years
     
  4.result:  
      &emsp;(1.1) the average rating change of different years for the given genre and age group (plot chart)  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis4/Action35-44.png)       
      &emsp;(1.2) average ratings for the given genre by different age groups of every year  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis4/screenshot of csv files/Action_trends.png)  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis4/screenshot of csv files/Children_trends.png)  
  
### Analysis5:  
  1.Problem: get the topk recommendations based on the given user properties.  
  2.process:  
      &emsp;step1: use argparse to get the input(age, gender, or occupation)  
      &emsp;step2: load data: load the users related tables: users, age, occupation, and merge
               them together to get full user informaiton  
      &emsp;step3: calculate the popularity in each age group, gender group, and occupation catagories   
      &emsp;step4: generate the pie chart to present the ratio each group  
  3.result:  
      &emsp;(1.1) the top k recommendations based on the user properties (gender = male, age = 25, occupation = artist, top k = 20)
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis5/topK recommedation.png)       
      &emsp;(1.2) all the recommendation candidate movies sorted by ratings  
      &emsp;![](https://github.com/davidviva/DataAnalysisPython/raw/master/Final/output/Analysis5/screenshot of csv files/topK recommendation.png)  
      
### Analysis6:  
  1.aim: recommend registered users movies based on pearson algorithm  
  2.process:  
      &emsp;step1: use pearson algorithm to find similar users  
      &emsp;step2: train the engine first, and use the test data to test
  3.result

    
