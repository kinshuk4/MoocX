# Analytic Edge Kaggle NYT classifiction
Ryan Zhang  
Thursday, April 16, 2015  

#0 Environment  环境设定  
##0-1 Set working directory  设定工作环境  

```r
setwd("~/GitHub/edX/The Analytic Edge/Kaggle")
```

##0-2 Load Libraries  函数包  

```r
library(tm)
library(e1071)
library(neuralnet)
library(randomForest)
library(ROCR)
library(party)
library(rCUR)
library(topicmodels)
```

##0-3 Function Definition  自定义函数  
用于帮助求table后正类百分比的小函数    

```r
tCor <- function(t)round(t[,2]/rowSums(t),2)*100 
```
用于生成dummy encoding的数据框

```r
dummyEncoding <- function(df, colname){
  dDF <- as.data.frame(model.matrix(~df[,colname]))
  names(dDF) <- paste(colname,as.character(levels(df[,colname])),sep="")
  dDF}
```

#1 Data Preparing 数据准备工作
##1-1 Loading 装载

```r
NewsTrain <- read.csv("NYTimesBlogTrain.csv", stringsAsFactors = F)
NewsTest <- read.csv("NYTimesBlogTest.csv", stringsAsFactors = F)
```

##1-2 预处理
Store the number of training data points and the number of testing data points.        
记录一下训练数据和测试数据的数量。

```r
ntrain <- nrow(NewsTrain)
ntest <- nrow(NewsTest)
ntrain
```

```
## [1] 6532
```

```r
ntest
```

```
## [1] 1870
```

"Popular"" is the dependant variable, store it in a separate vector "Y", and delete the colomn from the dataframe "NewsTrain".      
要预测的因变量是“Popular”，将其存在一个单独的"Y"向量中,并从训练数据框中删除该列。

```r
Y <- as.factor(NewsTrain$Popular)
NewsTrain$Popular <- NULL
```

Combine "NewsTrain" and "NewsTest" into a single dataframe for the purpose of data preparing      
将训练数据和测试数据合并为一个单一的数据框，以便集中处理（这是否有问题？）    
**只要是非监督的变换应该都不算作弊**

```r
OriginalDF <- rbind(NewsTrain, NewsTest)
```

Filling empty entries for the first three columns with name "Other"      
将前三列里面的“”用“Other”替代

```r
for (i in 1:nrow(OriginalDF)){
  for (j in 1:3){
    if (OriginalDF[i,j] == ""){
      OriginalDF[i,j] <- "Other"}}}
```

Change the first three columns to be factors     
将前三个变量改成factor类型

```r
OriginalDF$NewsDesk <- as.factor(OriginalDF$NewsDesk)
OriginalDF$SectionName <- as.factor(OriginalDF$SectionName)
OriginalDF$SubsectionName <- as.factor(OriginalDF$SubsectionName)
```

Log Transform "WordCount"      
将WordCount做对数转换，（转换后变为正态分布）  

```r
# OriginalDF$ZWordCount <- with(OriginalDF, (WordCount - mean(WordCount))/sd(WordCount))
OriginalDF$NWordCount <- log(OriginalDF$WordCount + 1)
```

Conver the PubDate and time variable to be more R friendly and extract the hour of day, the day on month and the day of week to be seperate variables. Finally delete the PubDate column.       
将PubDate改成R的日期-时间格式，并将周几、每月几号以及每天几点这些信息单独抽取出来，删除原本的PubDate

```r
OriginalDF$PubDate <- strptime(OriginalDF$PubDate, "%Y-%m-%d %H:%M:%S")
OriginalDF$Hour <- as.factor(OriginalDF$PubDate$h)
OriginalDF$Wday <- as.factor(OriginalDF$PubDate$wday)
OriginalDF$Mday <- as.factor(OriginalDF$PubDate$mday)
# OriginalDF$isWeekend <- as.numeric(OriginalDF$Wday %in% c(0,6))
OriginalDF$PubDate <- NULL
```

Generate training and testing set    
生成训练和测试数据    

```r
train <- OriginalDF[1:ntrain, c(1:3,9:12)]
test <- OriginalDF[(ntrain+1):nrow(OriginalDF),c(1:3,9:12)]
```

##2 Exploratory Data Analysis  探索式数据分析    
First Explore the few factor variable and their relationship to the depandent variable.    
先看看前三个factor型数据与要预测的Popular之间的关系。    

```r
tNewsDesk <- table(OriginalDF$NewsDesk[1:ntrain], Y)
t(tNewsDesk)
```

```
##    
## Y   Business Culture Foreign Magazine Metro National OpEd Other Science
##   0     1301     626     372       31   181        4  113  1710      73
##   1      247      50       3        0    17        0  408   136     121
##    
## Y   Sports Styles Travel TStyle
##   0      2    196    115    715
##   1      0    101      1      9
```

```r
tCor(tNewsDesk)
```

```
## Business  Culture  Foreign Magazine    Metro National     OpEd    Other 
##       16        7        1        0        9        0       78        7 
##  Science   Sports   Styles   Travel   TStyle 
##       62        0       34        1        1
```

```r
plot(tCor(tNewsDesk))
```

![](stratch2_files/figure-html/unnamed-chunk-14-1.png) 

```r
tSectionName <- table(OriginalDF$SectionName[1:ntrain], Y)
t(tSectionName)
```

```
##    
## Y   Arts Business Day Crosswords/Games Health Magazine Multimedia
##   0  625          999               20     74       31        139
##   1   50           93              103    120        0          2
##    
## Y   N.Y. / Region Open Opinion Other Sports Style Technology Travel U.S.
##   0           181    4     182  2171      1     2        280    116  405
##   1            17    0     425   129      0     0         50      1  100
##    
## Y   World
##   0   209
##   1     3
```

```r
tCor(tSectionName)
```

```
##             Arts     Business Day Crosswords/Games           Health 
##                7                9               84               62 
##         Magazine       Multimedia    N.Y. / Region             Open 
##                0                1                9                0 
##          Opinion            Other           Sports            Style 
##               70                6                0                0 
##       Technology           Travel             U.S.            World 
##               15                1               20                1
```

```r
plot(tCor(tSectionName))
```

![](stratch2_files/figure-html/unnamed-chunk-14-2.png) 

```r
tSubsectionName <- table(OriginalDF$SubsectionName[1:ntrain], Y)
t(tSubsectionName)
```

```
##    
## Y   Asia Pacific Dealbook Education Fashion & Style Other Politics
##   0          200      864       325               2  3846        2
##   1            3       88         0               0   980        0
##    
## Y   Room For Debate Small Business The Public Editor
##   0              61            135                 4
##   1               1              5                16
```

```r
tCor(tSubsectionName)
```

```
##      Asia Pacific          Dealbook         Education   Fashion & Style 
##                 1                 9                 0                 0 
##             Other          Politics   Room For Debate    Small Business 
##                20                 0                 2                 4 
## The Public Editor 
##                80
```

```r
plot(tCor(tSubsectionName))
```

![](stratch2_files/figure-html/unnamed-chunk-14-3.png) 
    
SectionName, SubsectionName,NewsDesk应该都是有预测能力的变量，应该保留   

Looking at the text contents    
看看文本信息   
It seems that the "Snippet" is almost redudent with "Abstract", in since 98% cases they are the same. And "Abstract" contains a little bit more infomation than "Snippet"      
Snippet应该和Abstract的重合内容非常多，前者貌T似都属于后者，因而估计只用后者就好了。    

```r
sum(OriginalDF$Snippet == OriginalDF$Abstract)/nrow(OriginalDF)
```

```
## [1] 0.9846465
```

```r
which(OriginalDF$Snippet != OriginalDF$Abstract)[1]
```

```
## [1] 22
```

```r
OriginalDF[22,5]
```

```
## [1] "In an open letter, Su Yutong, a Chinese journalist who was fired from a German public broadcaster last month after a debate over the Tiananmen Square massacre, called on the broadcasters director general to speak out for press freedom while in..."
```

```r
OriginalDF[22,6]
```

```
## [1] "In an open letter, Su Yutong, a Chinese journalist who was fired from a German public broadcaster last month after a debate over the Tiananmen Square massacre, called on the broadcasters director general to speak out for press freedom while in China."
```

Looking at WordCount    
看看字数    
The distribution of WordCount seems to be a longtail / power-law distribution.    
字数的分布似乎是幂律分布的    

```r
summary(OriginalDF$WordCount)
```

```
##    Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
##     0.0   188.0   377.0   528.8   735.0 10910.0
```

```r
hist(OriginalDF$WordCount, breaks = 70)
```

![](stratch2_files/figure-html/unnamed-chunk-16-1.png) 

```r
hist(OriginalDF$NWordCount)
```

![](stratch2_files/figure-html/unnamed-chunk-16-2.png) 

Looking at publication day/weekday/hour related to Popular   
看看小时、周几、每月几号，这些有没有用   

```r
tHour <- table(OriginalDF$Hour[1:ntrain] , Y)
t(tCor(tHour))
```

```
##       0  1  2 3 4 5  6 7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
## [1,] 18 11 12 5 1 4 14 6 12 16 18 18 18 13 17 21 16 13 18 27 28 31 60 31
```

```r
plot(tCor(tHour))
```

![](stratch2_files/figure-html/unnamed-chunk-17-1.png) 

```r
tWday <- table(OriginalDF$Wday[1:ntrain], Y)
t(tCor(tWday))
```

```
##       0  1  2  3  4  5  6
## [1,] 32 17 15 16 16 14 27
```

```r
plot(tCor(tWday))
```

![](stratch2_files/figure-html/unnamed-chunk-17-2.png) 

```r
tMday <- table(OriginalDF$Mday[1:ntrain], Y)
t(tCor(tMday))
```

```
##       1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
## [1,] 18 20 17 13 20 15 18 17 18 16 17 21 20 13 20 16 15 17 16 18 15 16 12
##      24 25 26 27 28 29 30 31
## [1,] 14 19 18 18 20 15 18 13
```

```r
plot(tCor(tMday))
```

![](stratch2_files/figure-html/unnamed-chunk-17-3.png) 

```r
#tWeekend <- table(OriginalDF$isWeekend[1:ntrain], Y)
#tCor(tWeekend)
#plot(tCor(tWeekend))
```
每月几号看上去没啥用   

#3 Model fitting  模型拟合  
##3-1 randomForest on Non-Text Features 用不含文本提取的特征做随机森林
randomForest model    
随机森林模型    


```r
set.seed(1126)
rfModel <- randomForest(x = train,
                        y = Y,
                        ntree = 1000,
                        mtry = 2,
                        nodesize = 4,
                        importance = F,
                        proximity = F)
```

Evaluate on Training set 
根据测试集评价模型

```r
rfPred <- predict(rfModel, train, type = "prob")
table(rfPred[,2] > 0.5,Y)
```

```
##        Y
##            0    1
##   FALSE 5351  104
##   TRUE    88  989
```

```r
prediction <- ROCR::prediction(rfPred[,2], Y)
perf <- performance(prediction, "tpr", "fpr")
plot(perf, colorize = T, lwd = 2)
```

![](stratch2_files/figure-html/unnamed-chunk-19-1.png) 

```r
auc <- performance(prediction, "auc")
auc@y.values
```

```
## [[1]]
## [1] 0.9965738
```

Make prediction with randomForest model
用随机森林模型做预测

```r
tpred <- predict(rfModel, test, type = "prob")
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = tpred[,1])
write.csv(MySubmission, "rfRegularFeatures2.csv", row.names = F)
```

#4 Try feature engineering with text content 尝试通过文本数据做特征工程     
##4-1 TFIDF   术语频次·逆文档频次  
Extract all headline and abstract to form a corpus    
抽取题名和摘要文本构建一个语料库    

```r
text <- vector()
for (i in 1:nrow(OriginalDF)) {
  text <- rbind(text, paste(OriginalDF$Headline[i], " ", OriginalDF$Abstract[i]))
}

Corpus <- Corpus(VectorSource(text))
```

Standard Corpus processing     
标准化的语料库处理     

```r
Corpus <- tm_map(Corpus, tolower)     
Corpus <- tm_map(Corpus, PlainTextDocument)    
Corpus <- tm_map(Corpus, removePunctuation)    
Corpus <- tm_map(Corpus, removeWords, stopwords("english"))     
Corpus <- tm_map(Corpus, stemDocument)
```

人为地移除一些术语

```r
Corpus <- tm_map(Corpus, removeWords, c("new","time","york","today","day","said","say","report","week","will","year","articl","can","daili","news"))
```

Document ~ TF-IDF matrix    
构建文档~TFIDF矩阵     

```r
dtm <- DocumentTermMatrix(Corpus, control = list(weighting = weightTfIdf))   
tfdtm <- DocumentTermMatrix(Corpus)   
```

看看那些词比较频繁

```r
sort(colSums(as.matrix(dtm)),decreasing = T)[1:20]
```

```
##      word   fashion     obama   compani     photo    presid      clip 
## 200.21781 170.63243 116.40559 114.01688 113.04361 110.10585 108.97694 
##       one      make    appear      past     first     senat      take 
## 108.54374 108.21030 107.16552 106.80609 103.00810 102.90691 100.34892 
##      show      bank     state      busi       get   collect 
##  99.68441  97.11773  94.65478  94.07204  94.05159  93.36455
```


Get the terms    
获取术语列表     

```r
terms <- dtm$dimnames$Terms    
terms[5101:5110]
```

```
##  [1] "environmentalist" "environmentmind"  "envoy"           
##  [4] "enzym"            "eon"              "eotvo"           
##  [7] "epa"              "ephemer"          "ephemera"        
## [10] "ephron"
```

Get the matrix for training and testing set     
分别获得训练和测试数据的Document~TF-IDF矩阵     

```r
dtmTrain <- dtm[1:ntrain,]
dtmTest <- dtm[(1+ntrain):dtm$nrow,]
```

Get frequent terms matrix for testing set
获得测试集的频繁术语

```r
sparseTest <- removeSparseTerms(dtmTest, 0.97)
wordsTest <- as.data.frame(as.matrix(sparseTest))
termsTest <- names(wordsTest)
```

Filter the dtm based on frequent terms in testing set    
根据测试集的频繁术语，对原本的矩阵进行筛选     

```r
cols <- vector()
for (i in 1:length(termsTest)){
  cols = c(cols, which((terms == termsTest[i]) == T))}
dtmFiltered <- dtm[,cols]
```

Text Feature    
文本特征      

```r
termFeatures <- as.data.frame(as.matrix(dtmFiltered))
row.names(termFeatures) <- c(1:nrow(OriginalDF))
```

Append text features to the dataframe    

```r
TextADDDF <- as.data.frame(termFeatures)
```


```r
tatrain <- cbind(train, TextADDDF[1:ntrain,])
tatest <- cbind(test, TextADDDF[(ntrain+1):nrow(TextADDDF),])
```

##4-2 randomForest model with text features added  加了文本特征的随机森林模型  


```r
set.seed(1126)
tarfModel <- randomForest(x = tatrain,
                        y = Y,
                        ntree = 1000,
                        nodesize = 4,
                        importance = T,
                        proximity = F)
```

Look at the importance of features via training randomForest   
看看文本特征的重要性    

```r
t(sort(tarfModel$importance[,4],decreasing = T))
```

```
##      SectionName NewsDesk NWordCount     Hour     Mday     Wday
## [1,]    396.2285 298.8661   208.1118 178.1749 169.6555 56.53267
##      SubsectionName      one     make     may     like american      get
## [1,]        47.6461 14.59691 13.03241 12.9443 10.17726 8.795724 8.640261
##       compani     work    share     show   presid     take    state
## [1,] 8.549174 8.523599 7.247796 7.050388 7.020364 6.931702 6.839181
##          plan     two    offer     look     bank    first     busi
## [1,] 6.688198 6.37197 6.305932 6.111764 5.627447 5.112249 4.579958
##         obama     back  million     deal     unit   intern  billion
## [1,] 4.387131 4.371869 4.368047 4.065353 4.063943 3.858151 3.309587
##        editor     hous  holiday    2014  christma
## [1,] 2.303917 2.097926 1.681631 1.28059 0.2013599
```
  
Make prediction on the training set
用加了文本特征的随机森林模型对训练数据进行预测

```r
tarfPred <- predict(tarfModel, tatrain, type = "prob")
table(tarfPred[,2] > 0.5,Y)
```

```
##        Y
##            0    1
##   FALSE 5385   83
##   TRUE    54 1010
```

```r
prediction <- ROCR::prediction(tarfPred[,2], Y)
perf <- performance(prediction, "tpr", "fpr")
plot(perf, colorize = T, lwd = 2)
```

![](stratch2_files/figure-html/unnamed-chunk-35-1.png) 

```r
auc <- performance(prediction, "auc")
auc@y.values
```

```
## [[1]]
## [1] 0.9975532
```

Make prediction with randomForest model
加了文本特征的随机森林模型做预测

```r
tatpred <- predict(tarfModel, tatest, type = "prob")
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = tatpred[,1])
write.csv(MySubmission, "rfText2.csv", row.names = F)
```

##4-3 Why not a neural net?  试试神经网络      
Neural net based on numerical features.    
基于数值特征的神经网络（没有GPU 炒鸡慢）       

```r
# nntrain <- tatrain[,c(4,8:40)]
# nntest <- tatest[,c(4,8:40)]
# nntrain$Popular <- as.numeric(as.character(Y))
# nameofvars <- names(nntrain)
# nnformula <- as.formula(paste("Popular ~", 
#                         paste(nameofvars[!nameofvars %in% "Popular"], collapse = " + ")))
# ptm <- proc.time()
# nn <- neuralnet(formula = nnformula,
#                 data = nntrain, 
#                 hidden = 2,
#                 threshold = 0.02,
#                 stepmax = 1e8,
#                 err.fct="ce",
#                 linear.output=FALSE)
# plot(nn)
# pnn <- compute(nn,nntrain[,1:34])
# summary(pnn$net.result)
# nnpredict <- as.vector(pnn$net.result)
# prediction <- ROCR::prediction(nnpredict, Y)
# perf <- performance(prediction, "tpr", "fpr")
# plot(perf, colorize = T, lwd = 2)
# auc <- performance(prediction, "auc")
# auc@y.values
# proc.time() - ptm
```

##4-4 Support Vector Machine 支持向量机
先做下Dummy Encoding

```r
taDF <- rbind(tatrain, tatest)
names(taDF)
```

```
##  [1] "NewsDesk"       "SectionName"    "SubsectionName" "NWordCount"    
##  [5] "Hour"           "Wday"           "Mday"           "2014"          
##  [9] "american"       "back"           "bank"           "billion"       
## [13] "busi"           "christma"       "compani"        "deal"          
## [17] "editor"         "first"          "get"            "holiday"       
## [21] "hous"           "intern"         "like"           "look"          
## [25] "make"           "may"            "million"        "obama"         
## [29] "offer"          "one"            "plan"           "presid"        
## [33] "share"          "show"           "state"          "take"          
## [37] "two"            "unit"           "work"
```

```r
tadDF <- taDF[,c(4,8:39)]
toEnco <- c("NewsDesk","SectionName","SubsectionName","Hour","Wday","Mday")
for (colname in toEnco){
  tadDF <- cbind(tadDF, dummyEncoding(taDF, colname))
}
names(tadDF) <- make.names(tadDF)
tadtrain <- tadDF[1:ntrain,]
tadtest <- tadDF[((1+ntrain):nrow(tadDF)),]
```

SVM Model Fitting 支持向量机

```r
svmModel <- svm(x = tadtrain, 
                y = Y,
                cost = 100, gamma = 1, probability = T)
svmpred <- predict(svmModel, tadtrain, decision.values = T, probability = T)
svmpredp <- attr(svmpred, "probabilities")[,1]
prediction <- ROCR::prediction(svmpredp, Y)
perf <- performance(prediction, "tpr", "fpr")
plot(perf, colorize = T, lwd = 2)
```

![](stratch2_files/figure-html/unnamed-chunk-39-1.png) 

```r
auc <- performance(prediction, "auc")
auc@y.values
```

```
## [[1]]
## [1] 0.9999995
```

```r
svmtpred <- predict(svmModel, tadtest, probability = T)
svmtpredp <- attr(svmtpred, "probabilities")[,1]
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = svmtpredp)
write.csv(MySubmission, "svmText2.csv", row.names = F)
```

##4-5 Simple Ensemble 简单的组合模型

```r
enpred <- 0.2*svmtpredp+0.8*tatpred[,2]
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = enpred)
write.csv(MySubmission, "svm_rf_ensemble2.csv", row.names = F)
```


#5 Clustering on TFIDF matrix 做个聚类看看

```r
cTest <- removeSparseTerms(dtmTest, 0.98)
cWords <- as.data.frame(as.matrix(cTest))
cTerms <- names(cWords)

cols <- vector()
for (i in 1:length(cTerms)){
  cols = c(cols, which((terms == cTerms[i]) == T))
}
cdtm <- dtm[,cols]
cMatrix <- as.matrix(cdtm)
cDF <- as.data.frame(cMatrix)

kmclusters <- kmeans(cDF, 9, iter.max = 5000)

tactrain <- cbind(tatrain, kmclusters$cluster[1:ntrain])
names(tactrain) <- c(names(tactrain)[1:39],"cluster")
tactest <- cbind(tatest, kmclusters$cluster[(1+ntrain):nrow(OriginalDF)])
names(tactest) <- c(names(tactest)[1:39],"cluster")
```

##5-1 Another randomForest with cluster labels added 加上聚类标签后再来一个随机森林 


```r
set.seed(1126)
tacrfModel <- randomForest(x = tactrain,
                        y = Y,
                        ntree = 1000,
                        mtry = 6,
                        nodesize = 4,
                        importance = T,
                        proximity = F)
t(sort(tacrfModel$importance[,4],decreasing = T))
```

```
##      SectionName NewsDesk NWordCount     Hour     Mday     Wday
## [1,]    380.2659 311.3443   207.4139 173.9483 171.1596 56.52249
##      SubsectionName cluster      one      may     make     like     get
## [1,]       47.46518 17.0429 14.34507 13.03573 11.19859 10.24335 8.76608
##          work american  compani   presid   share    state     take
## [1,] 8.432137  8.39404 7.599656 6.960871 6.85159 6.769085 6.717223
##          plan    offer      two     look     show     bank    first
## [1,] 6.649779 6.595219 6.498815 6.297407 5.797245 5.055276 4.839271
##          back    obama  million     unit   intern     busi     deal
## [1,] 4.349682 4.216221 4.088953 3.992783 3.974734 3.867143 3.831603
##       billion   editor     hous  holiday     2014  christma
## [1,] 2.901811 2.297754 1.888237 1.631008 1.378666 0.2095161
```

```r
tacrfPred <- predict(tacrfModel, tactrain, type = "prob")
table(tacrfPred[,2] > 0.5,Y)
```

```
##        Y
##            0    1
##   FALSE 5380   83
##   TRUE    59 1010
```

```r
prediction <- ROCR::prediction(tacrfPred[,2], Y)
perf <- performance(prediction, "tpr", "fpr")
plot(perf, colorize = T, lwd = 2)
```

![](stratch2_files/figure-html/unnamed-chunk-42-1.png) 

```r
auc <- performance(prediction, "auc")
auc@y.values
```

```
## [[1]]
## [1] 0.9976108
```

```r
tacrfPred <- predict(tacrfModel, newdata = tactest, type = "prob")
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = tacrfPred[,2])
write.csv(MySubmission, "rfTextCluster2.csv", row.names = F)
```

#6 Matrix Factorization 矩阵分解  
##6-1 SVD 奇异值分解

```r
s <- svd(cMatrix)
Sig <- diag(s$d)
plot(s$d)
```

![](stratch2_files/figure-html/unnamed-chunk-43-1.png) 

```r
totaleng <- sum(Sig^2)
engsum <- 0
for (i in 1:nrow(Sig)){
  engsum <- engsum + Sig[i,i]^2
  if (engsum/totaleng > 0.8){
    print(i)
    break}}
```

```
## [1] 51
```
需要51个术语才能保留原TF-IDF矩阵80%的能量   

##6-2 CUR Matrix Decomposition CUR矩阵分解

```r
res <- CUR(cMatrix, c = 9, r = 85, k = 51)
```

将投影加到训练、测试数据

```r
ncolC <- ncol(getC(res))
Ak <- getC(res) %*% getU(res)[,1:ncolC]
AkDF <- as.data.frame(Ak)
tacftrain <- cbind(tactrain, AkDF[1:ntrain,])
tacftest <- cbind(tactest, AkDF[((1+ntrain):nrow(AkDF)),])
```

##6-3 randomForest with pc added 加上主要成分投影后的随机森林


```r
set.seed(1126)
tacfrfModel <- randomForest(x = tacftrain,
                        y = Y,
                        ntree = 1000,
                        mtry = 8,
                        nodesize = 4,
                        importance = T,
                        proximity = F)
t(tacfrfModel$importance[,4])
```

```
##      NewsDesk SectionName SubsectionName NWordCount     Hour    Wday
## [1,] 295.9518    399.8515       46.01973     204.18 172.3858 56.1607
##          Mday      2014 american     back     bank  billion     busi
## [1,] 176.1824 0.4671426 7.743959 3.646521 1.996056 0.915587 2.954088
##       christma compani     deal   editor    first      get  holiday
## [1,] 0.2413072 6.58052 2.629738 1.983522 4.243793 3.621945 1.487193
##          hous   intern     like     look     make      may  million
## [1,] 1.400708 3.103797 8.663697 5.532513 9.796518 11.14877 3.371132
##         obama    offer      one     plan   presid    share     show
## [1,] 3.948829 5.642457 12.59629 6.132944 6.638399 6.471147 4.765766
##         state     take      two     unit     work  cluster       V1
## [1,] 5.868264 1.819177 5.863628 3.579396 7.939424 15.30199 9.853846
##            V2       V3       V4       V5       V6       V7       V8
## [1,] 10.07501 9.009133 10.19126 10.18063 10.41876 10.48854 10.13502
##            V9
## [1,] 8.914536
```

```r
tacfrfPred <- predict(tacfrfModel, tacftrain, type = "prob")
table(tacfrfPred[,2] > 0.5,Y)
```

```
##        Y
##            0    1
##   FALSE 5403   59
##   TRUE    36 1034
```

```r
prediction <- ROCR::prediction(tacfrfPred[,2], Y)
perf <- performance(prediction, "tpr", "fpr")
plot(perf, colorize = T, lwd = 2)
```

![](stratch2_files/figure-html/unnamed-chunk-46-1.png) 

```r
auc <- performance(prediction, "auc")
auc@y.values
```

```
## [[1]]
## [1] 0.9986456
```

```r
tacfrfPred <- predict(tacfrfModel, newdata = tacftest, type = "prob")
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = tacfrfPred[,2])
write.csv(MySubmission, "rfTextClusterFactorization2.csv", row.names = F)
```

#7 LDA?

```r
lda <- LDA(tfdtm, 11)
set.seed(1126)
tacfltrain <- cbind(tacftrain,topics(lda)[1:ntrain])
tacfltest <- cbind(tacftest, topics(lda)[(1+ntrain):nrow(OriginalDF)])
names(tacfltrain) <- c(names(tacfltrain)[1:49],"LDA")
names(tacfltest) <- c(names(tacfltest)[1:49],"LDA")
tacflrfModel <- randomForest(x = tacfltrain,
                        y = Y,
                        ntree = 1000,
                        mtry = 8,
                        nodesize = 4,
                        importance = T,
                        proximity = F)
t(sort(tacflrfModel$importance[,4],decreasing = T ))
```

```
##      SectionName NewsDesk NWordCount   Mday     Hour     Wday
## [1,]     398.179 291.7998   199.2225 175.39 171.0922 54.28662
##      SubsectionName      LDA  cluster      one      may       V7       V5
## [1,]        44.3481 39.42477 14.45974 12.03431 10.86216 10.18127 10.07052
##           V8       V4       V6    make       V2       V1       V9       V3
## [1,] 9.97795 9.873379 9.851737 9.83196 9.670958 9.592133 8.952562 8.496423
##          like american     work   presid compani    share     plan
## [1,] 8.016313 7.713043 7.343749 6.118085 6.10384 6.031665 5.791983
##         state      two    offer     look     show    first    obama
## [1,] 5.509998 5.452782 5.378101 5.290253 4.829537 3.902716 3.727818
##           get     unit     back     busi  million   intern     deal
## [1,] 3.531385 3.365954 3.331423 3.183295 3.072874 2.675174 2.497947
##          bank     take   editor holiday     hous   billion      2014
## [1,] 2.027298 1.753312 1.665522 1.50865 1.351132 0.8290295 0.4329638
##       christma
## [1,] 0.1992466
```

```r
tacflrfPred <- predict(tacflrfModel, tacfltrain, type = "prob")
table(tacflrfPred[,2] > 0.5,Y)
```

```
##        Y
##            0    1
##   FALSE 5408   51
##   TRUE    31 1042
```

```r
prediction <- ROCR::prediction(tacflrfPred[,2], Y)
perf <- performance(prediction, "tpr", "fpr")
plot(perf, colorize = T, lwd = 2)
```

![](stratch2_files/figure-html/unnamed-chunk-47-1.png) 

```r
auc <- performance(prediction, "auc")
auc@y.values
```

```
## [[1]]
## [1] 0.9990811
```

```r
tacflrfPred <- predict(tacflrfModel, newdata = tacfltest, type = "prob")
MySubmission = data.frame(UniqueID = NewsTest$UniqueID, Probability1 = tacfrfPred[,2])
write.csv(MySubmission, "rfTextClusterFactorizationLDA2", row.names = F)
```

