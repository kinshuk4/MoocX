#load data
seaData<- read.csv('./GoogleDrive/Study/Coursera/4.Data Science at Scale/datasci_course_materials-master/assignment5/seaflow_21min.csv')
str(seaData)
summary(seaData)


#Randomly split trainign/testing 50%/50%
dt = sort(sample(nrow(seaData), nrow(seaData)*.5))
training<-seaData[dt,]
testing<-seaData[-dt,]
summary(train)

#try ggplot for histogram
library(ggplot2)
ggplot(seaData, aes(x = chl_small)) +
  geom_histogram()

#plot
ggplot(subset(seaData, pop %in% c("crypto", "nano","pico","synecho","ultra")),
       aes(x=pe,
           y=chl_small,
           color=pop))+
  geom_point()

#Training decision tree:
library(rpart)
fol <- formula(pop ~ fsc_small + fsc_perp + fsc_big + pe + chl_big + chl_small)
model <- rpart(fol, method="class", data=training)
print(model)

#prediction on decision tree
pred <- predict(model, newdata=testing,type = "class")
library(caret)
confusionMatrix(pred, testing$pop)

#build and evaluate a random forest 
library(randomForest)
modelRandom <- randomForest(fol, data=training)
predRandom <- predict(modelRandom, newdata=testing,type = "class")
confusionMatrix(predRandom, testing$pop)

#estimate the variable importance 
importance(modelRandom)

#train support vector machine model and compare 
library(e1071)
modelVector <-svm(fol, data=training)
predVector <- predict(modelVector, newdata=testing,type = "class")
confusionMatrix(predVector, testing$pop)

#construct confusion matrices 
table(pred = predVector, true = testing$pop)

#plot measurement 

ggplot(seaData, aes(x = fsc_big)) +
  geom_histogram()

ggplot(subset(seaData),
       aes(x=time,
           y=chl_big))+
  geom_point()

#filtering data and imporve svm
seaData[seaData$file_id != 208,]
dt = sort(sample(nrow(seaData), nrow(seaData)*.5))
trainingRemove<-seaData[dt,]
testingRemove<-seaData[-dt,]
modelVectorImp <-svm(fol, data=trainingRemove)
predVectorImp <- predict(modelVectorImp, newdata=testingRemove,type = "class")
confusionMatrix(predVectorImp, testingRemove$pop)

