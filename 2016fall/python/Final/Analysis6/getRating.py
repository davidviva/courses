
from math import sqrt
from loadMovieLens import loadMovieLensTrain
from loadMovieLens import loadMovieLensTest
    
# Use pearson algorithm to calculate the user similarity
def sim_pearson(prefer, person1, person2):
    sim = {}
    # find the items that both person rated
    for item in prefer[person1]:
        if item in prefer[person2]:
            sim[item] = 1           #add to sim dictionary
    n = len(sim)
    if len(sim)==0:
        return -1

    # sum the preferences
    sum1 = sum([prefer[person1][item] for item in sim])  
    sum2 = sum([prefer[person2][item] for item in sim])  

    sum1Sq = sum( [pow(prefer[person1][item] ,2) for item in sim] )
    sum2Sq = sum( [pow(prefer[person2][item] ,2) for item in sim] )

    # ∑XiYi
    sumMulti = sum([prefer[person1][item]*prefer[person2][item] for item in sim])

    num1 = sumMulti - (sum1*sum2/n)
    num2 = sqrt( (sum1Sq-pow(sum1,2)/n)*(sum2Sq-pow(sum2,2)/n))  
    if num2==0:                                                ### 如果分母为0，本处将返回0.
        return 0  

    result = num1/num2
    return result


# Get the k similar users that have similar ratings of item (default k = 20)
def topKMatches(prefer, person, itemId, k=20, sim = sim_pearson):
    userSet = []
    scores = []
    users = []
    # find all users in the prefer set who rated the given item, save into userSet 
    for user in prefer:
        if itemId in prefer[user]:
            userSet.append(user)
    # calculate the similarity
    scores = [(sim(prefer, person, other),other) for other in userSet if other!=person]

    # sort by similarity scores
    scores.sort()
    scores.reverse()

    if len(scores)<=k:   
        for item in scores:
            users.append(item[1])  #extract userId
        return users
    else:                
        kscore = scores[0:k]
        for item in kscore:
            users.append(item[1]) 
        return users              


# calculate the average scores of picked users
def getAverage(prefer, userId):
    count = 0
    sum = 0
    for item in prefer[userId]:
        sum = sum + prefer[userId][item]
        count = count+1
    return sum/count


# predict the given user's rating of an item
def getRating(prefer1, userId, itemId, knumber=20,similarity=sim_pearson):
    sim = 0.0
    averageOther =0.0
    jiaquanAverage = 0.0
    simSums = 0.0
    # get the set of users who have scores
    users = topKMatches(prefer1, userId, itemId, k=knumber, sim = sim_pearson)

    averageOfUser = getAverage(prefer1, userId)     

    for other in users:
        sim = similarity(prefer1, userId, other)   
        averageOther = getAverage(prefer1, other) 
        simSums += abs(sim) 
        jiaquanAverage +=  (prefer1[other][itemId]-averageOther)*sim  

    if simSums==0:
        return averageOfUser
    else:
        return (averageOfUser + jiaquanAverage/simSums)  


# fileResult is the result
def getAllUserRating(fileTrain='u1.base', fileTest='u1.test', fileResult='result.txt', similarity=sim_pearson):
    prefer1 = loadMovieLensTrain(fileTrain)        
    prefer2 = loadMovieLensTest(fileTest)        
    inAllnum = 0

    file = open(fileResult, 'a')
    file.write("%s\n"%("------------------------------------------------------"))
    
    for userid in prefer2:        
        for item in prefer2[userid]:   
            rating = getRating(prefer1, userid, item, 20)   # predict the user's rating based on the train data
            file.write('%s\t%s\t%s\n'%(userid, item, rating))
            inAllnum = inAllnum +1
    file.close()
    print("-------------Completed!!-----------",inAllnum)


if __name__ == "__main__":
    print("\n--------------processing... -----------\n")
    getAllUserRating('u1.base', 'u1.test', 'result.txt')









