
import sys
import os

# load given train dataset
# fileName : train data

ScriptPath = os.path.split( os.path.realpath(sys.argv[0]))[0]

def loadMovieLensTrain(fileName='u1.base'):
    str1 = ScriptPath + '/Analysis6/movielens/'       
    
    prefer = {}
    for line in open(str1+fileName,'r'):       # open give file
        (userid, movieid, rating,ts) = line.split('\t')     # every line has 4 columns
        prefer.setdefault(userid, {})    
        prefer[userid][movieid] = float(rating)    

    return prefer      # format : {'user1':{itemid:rating, itemid2:rating, ,,}, {,,,}}


#  load test dataset
#  fileName: test data
def loadMovieLensTest(fileName='u1.test'):
    str1 = ScriptPath + '/Analysis6/movielens/'    
    prefer = {}
    for line in open(str1+fileName,'r'):    
        (userid, movieid, rating,ts) = line.split('\t') 
        prefer.setdefault(userid, {})    
        prefer[userid][movieid] = float(rating)   
    return prefer                   


if __name__ == "__main__":
    #print ("""This part can test the above 2 functions """)
    
    trainDict = loadMovieLensTrain()
    testDict = loadMovieLensTest()

    print (len(trainDict))
    print (len(testDict))
    print ('test passed')
                        

















