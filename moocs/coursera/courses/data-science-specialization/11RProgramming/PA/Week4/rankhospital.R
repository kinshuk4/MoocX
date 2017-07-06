## CAPTAIN SLOG
## vim: set expandtab tabstop=4 shiftwidth=4 autoindent smartindent:
## File         : rankhospital.R
## System       : Assignment 3 (Autograded)
## Date         : 12/08/2014
## Author       : Mark Addinall
## Synopsis     : This file is part of the course work
##                assignments for the Johns Hopkins
##                series of Data Science units.
##                This unit is R Programming
##
## The function reads the outcome-of-care-measures.csv ﬁle 
##and returns a character vector with the name of the hospital 
##that has the ranking speciﬁed by the num argument. 
##
## For example, the call rankhospital("MD", "heart failure", 5)
## would return a character vector containing the name of the 
## hospital with the 5th lowest 30-day death rate for heart failure. 
## The num argument can take values “best”, “worst”, or an integer 
## indicating the ranking (smaller numbers are better). 
##
## If the number given by num is larger than the number of 
## hospitals in that state, then the function should return NA. 
## Hospitals that do not have data on a particular outcome should
## be excluded from the set of hospitals when deciding the rankings.
##
## Handling ties. It may occur that multiple hospitals have the 
## same 30-day mortality rate for a given cause of death. 
## In those cases ties should be broken by using the hospital name. 
## For example, in Texas (“TX”), the hospitals with lowest 30-day 
## mortality rate for heart failure are shown here.
##
## > head(texas)
## Hospital.Name Rate Rank
## 3935 FORT DUNCAN MEDICAL CENTER 8.1 1
## 4085 TOMBALL REGIONAL MEDICAL CENTER 8.5 2
## 4103 CYPRESS FAIRBANKS MEDICAL CENTER 8.7 3
## 3954 DETAR HOSPITAL NAVARRO 8.7 4
## 4010 METHODIST HOSPITAL,THE 8.8 5
## 3962 MISSION REGIONAL MEDICAL CENTER 8.8 6
##


## -----------------------------------------------------
rankhospital <- function(state, outcome, num = "best") {

## Read outcome data
## Check that state and outcome are valid
## Return hospital name in that state with the given rank
## 30-day death rate

if (num == "best") {            ## mixing argument types, tsk!
    num <- "1"                  ## who writes these specs?
} else if (num == "worst") {    ## at the end of the program
        num <- "-1"             ## if we find that the rank
}                               ## requested is LESS than 0,
                                ## then we want the LAST record
                                ## in the set to be returned

suppressWarnings(num <- as.numeric(num))

if (is.na(num)) {               ## this shouldn't happen,
    return(num)                 ## BUT, with a very loosely
}                               ## type language, num COULD be "BUM"


outcomes <- c("heart attack", "heart failure", "pneumonia")

if (! outcome %in% outcomes) {
    stop("invalid outcome")
}

outcome_data <- read.csv('data/outcome-of-care-measures.csv', 
                                stringsAsFactors = FALSE)

states <- unique(outcome_data[, "State"])

if (! state %in% states) {
    stop("invalid state")
}

## I am just getting rid of a big ugly column name here.
## way too much typing and prone to error

if (outcome == "heart attack") {
    deaths <- "Hospital.30.Day.Death..Mortality..Rates.from.Heart.Attack" 
} else if (outcome == "heart failure") {
    deaths <- "Hospital.30.Day.Death..Mortality..Rates.from.Heart.Failure"
} else {
    deaths <-"Hospital.30.Day.Death..Mortality..Rates.from.Pneumonia"}

## sort on two column to resolve ties in scores for first place

sortnames       <- c(deaths, "Hospital.Name")

## trim the dataset down from 46 column to 4 that we need

outcome_data    <- outcome_data[, c("Provider.Number", "Hospital.Name", "State", deaths)]

## and dump all but the state we are interested in

outcome_data    <- subset(outcome_data, outcome_data[, 3] == state)

number_hospitals <- nrow(outcome_data)

## go awy if a silly ROOLY big number record
## is requested

if (number_hospitals < num) {
    return(NA)
}


## we know, we WANT to force the NA converstions

suppressWarnings(outcome_data[,4] <- as.numeric(outcome_data[, 4]))

## dump the NAs
## this came about because the people that put the database
## together mixed numbers with "Too tichy" so R turns the
## whole database into characters.  We need to turn some
## of them back into numbers for the sort.

outcome_data    <- na.omit(outcome_data)

## sort by rank then name

outcome_data <- outcome_data[do.call("order", outcome_data[sortnames]),]

if (num == -1) {                ## worst rank
    num <- nrow(outcome_data)
}

print(outcome_data[num, 2])

}
