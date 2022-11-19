from __future__ import division
import time
import random
import copy

'''numList=[]
for i in range(100):
    numList.append(random.randint(0,10000))
#print numList'''
numList=[4408, 8887, 6207, 1828, 2876, 1869, 2596, 8976, 7089, 1112, 3610, 8540, 125, 2023, 6388, 1868, 8822, 4051, 3609, 8705, 821, 8404, 8004, 2002, 4490, 4237, 6948, 5779, 5511, 3873, 5073, 6508, 1944, 5312, 1667, 5324, 446, 9585, 6865, 6078, 2770, 9145, 9683, 6289, 7850, 1622, 1424, 4482, 6655, 5958, 9356, 8026, 4007, 8976, 9692, 7122, 1045, 6155, 5847, 4133, 5716, 1483, 8782, 6482, 7718, 2631, 3799, 8654, 662, 2346, 8986, 8612, 1514, 3307, 4526, 6261, 4554, 1831, 1409, 7353, 4254, 9144, 49, 2418, 138, 8784, 6144, 4516, 7185, 4749, 7792, 7168, 2545, 345, 167, 7724, 2461, 6346, 4834, 9534]
def bubble_sort(numList,flag=True):
    for i in range(len(numList)-1):
        for j in range(len(numList)-1-i):
            if flag:
                if numList[j]>numList[j+1]:
                    numList[j],numList[j+1]=numList[j+1],numList[j]
            else:
                if numList[j]<numList[j+1]:
                    numList[j],numList[j+1]=numList[j+1],numList[j]
    return numList

def cocktail_sort(numList,flag=True):
    B=0
    E=len(numList)-1
    r=True
    while not B==E:
        if r:
            for i in range(B,E):
                if flag:
                    if numList[i]>numList[i+1]:
                        numList[i],numList[i+1]=numList[i+1],numList[i]
                else:
                    if numList[i]<numList[i+1]:
                        numList[i],numList[i+1]=numList[i+1],numList[i]
            E=E-1
            r=False
        else:
            for i in range(len(numList)-E,len(numList)-B):
                if flag:
                    if numList[-i-1]>numList[-i]:
                        numList[-i-1],numList[-i]=numList[-i],numList[-i-1]
                else:
                    if numList[-i-1]<numList[-i]:
                        numList[-i-1],numList[-i]=numList[-i],numList[-i-1]
            B=B+1
            r=True
    return numList

def insertion_sort(numList,flag=True):
    sortedNumList=[numList[0]]
    index=0
    for i in range(1,len(numList)):
        index=0
        for j in range(len(sortedNumList)):
            #index=j+1
            if flag:
                if numList[i]<sortedNumList[j]:
                    index=j
                    break
                else:
                    index=j+1
            else:
                if numList[i]>sortedNumList[j]:
                    index=j
                    break
                else:
                    index=j+1
        sortedNumList.insert(index,numList[i])
    return sortedNumList

def bucket_sort(numList,flag=True):
    minNum=numList[0]
    maxNum=numList[0]
    for i in range(1,len(numList)):
        if numList[i]<minNum:
            minNum=numList[i]
            continue
        if numList[i]>maxNum:
            maxNum=numList[i]
    n=len(numList)
    if n==2:
        #print numList,[minNum,maxNum]
        return [minNum,maxNum]
    #maxNum=maxNum+1
    numDic=[]
    N=(maxNum-minNum)/(n-1)
    for i in range(n):
        numDic.append([(minNum+i*N,minNum+(i+1)*N),[]])
    for i in range(n):
        newN=int((numList[i]-minNum)/N)
        numDic[newN][1].append(numList[i])
    sortedNumList=[]
    #kList=sorted(numDic.keys())
    '''if not flag:
        kList.reverse()
    
    for k in kList:
        #print k
        if len(numDic[k])<=1:
            sortedNumList.extend(numDic[k])
        else:
            sortedNumList.extend(bucket_sort(numDic[k],flag))'''
    if flag:
        for i in numDic:
            if len(i[1])<=1:
                sortedNumList.extend(i[1])
            else:
                sortedNumList.extend(bucket_sort(i[1],flag))
    else:
        for i in range(1,len(numDic)+1):
            if len(numDic[-i][1])<=1:
                sortedNumList.extend(numDic[-i][1])
            else:
                sortedNumList.extend(bucket_sort(numDic[-i][1],flag))
    '''if len(sortedNumList)<len(numList):
        print numList,sortedNumList,numDic'''
    return sortedNumList
    #print minNum,maxNum

def counting_sort(numList,flag=True):
    minNum=numList[0]
    maxNum=numList[0]
    for i in range(1,len(numList)):
        if numList[i]<minNum:
            minNum=numList[i]
            continue
        if numList[i]>maxNum:
            maxNum=numList[i]
    numDic=[]
    for i in range(minNum,maxNum+1):
        numDic.append([i,0])
    for i in numList:
        numDic[i-minNum][1]+=1
    sortedNumList=[]
    if flag:
        for i in numDic:
            sortedNumList.extend([i[0]]*i[1])
    else:
        for i in range(1,len(numDic)+1):
            sortedNumList.extend([numDic[-i][0]]*numDic[-i][1])
    return sortedNumList

def merge_sort(numList,flag=True):
    sortedNumList=[]
    for i in numList:
        sortedNumList.append([i])
    while len(sortedNumList)>1:
        newSList=[]
        for i in range(len(sortedNumList))[::2]:
            if i<len(sortedNumList)-1:
                s=[]
                index1=0
                index2=0
                while index1<len(sortedNumList[i]) and index2<len(sortedNumList[i+1]):
                    if flag:
                        if sortedNumList[i][index1]<sortedNumList[i+1][index2]:
                            s.append(sortedNumList[i][index1])
                            index1+=1
                        else:
                            s.append(sortedNumList[i+1][index2])
                            index2+=1
                    else:
                        if sortedNumList[i][index1]>sortedNumList[i+1][index2]:
                            s.append(sortedNumList[i][index1])
                            index1+=1
                        else:
                            s.append(sortedNumList[i+1][index2])
                            index2+=1
                if index1<len(sortedNumList[i]):
                    s.extend(sortedNumList[i][index1:])
                if index2<len(sortedNumList[i+1]):
                    s.extend(sortedNumList[i+1][index2:])
                newSList.append(s)
            else:
                newSList.append(sortedNumList[i])
        sortedNumList=newSList
    return sortedNumList[0]

def binary_tree_sort(numList,flag=True):
    tree=[]    
    for i in numList:
        t=tree
        f=True
        while f:
            #print t
            if t:
                if flag:
                    if t[0]>i:
                        t=t[1]
                    else:
                        t=t[2]
                else:
                    if t[0]<i:
                        t=t[1]
                    else:
                        t=t[2]
            else:
                t.append(i)
                t.append([])
                t.append([])
                f=False
    sortedNumList=[tree[1],tree[0],tree[2]]
    f=True
    index=0
    while index<len(sortedNumList):
        if type(sortedNumList[index])==list:
            if sortedNumList[index]:
                sortedNumList=sortedNumList[:index]+[sortedNumList[index][1],sortedNumList[index][0],sortedNumList[index][2]]+sortedNumList[index+1:]
                #index+=1
            else:
                sortedNumList=sortedNumList[:index]+sortedNumList[index+1:]
        else:
            index+=1
    return sortedNumList

def radix_sort(numList,flag=True):
    sortedNumList=copy.copy(numList)
    exp=0
    numDic=[]
    for i in range(10):
        numDic.append([])
    while not len(numDic[0])==len(numList):
        newNDic=[]
        for i in range(10):
            newNDic.append([])
        for i in sortedNumList:
            n=i%(10**(exp+1))
            m=int(n/(10**exp))           
            newNDic[m].append(i)
        sortedNumList=[]
        for i in newNDic:
            sortedNumList.extend(i)
        numDic=newNDic
        exp+=1
    if flag:
        return sortedNumList
    else:
        return sortedNumList[::-1]
        
if __name__=='__main__':
    time1=time.time()
    print(bubble_sort(copy.copy(numList),flag=True))
    print(time.time()-time1)
    time1=time.time()
    print(cocktail_sort(copy.copy(numList),flag=True))
    print(time.time()-time1)
    time1=time.time()
    print(insertion_sort(copy.copy(numList),flag=False))
    print(time.time()-time1)
    time1=time.time()
    print(bucket_sort(copy.copy(numList),flag=True))
    print(time.time()-time1)
    time1=time.time()
    print(counting_sort(copy.copy(numList),flag=True))
    print(time.time()-time1)
    time1=time.time()
    print(merge_sort(copy.copy(numList),flag=False))
    print(time.time()-time1)
    time1=time.time()
    print(binary_tree_sort(copy.copy(numList),flag=True))
    print(time.time()-time1)
    time1=time.time()
    print(radix_sort(copy.copy(numList),flag=False))
    print(time.time()-time1)
                
