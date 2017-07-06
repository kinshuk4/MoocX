## CAPTAIN SLOG
## vim: set expandtab tabstop=4 shiftwidth=4 autoindent smartindent:
## File         : rankall.R
## System       : Assignment 3 (Autograded)
## Date         : 18/08/2014
## Author       : Mark Addinall
## Synopsis     : This file is part of the course work
##                assignments for the Johns Hopkins
##                series of Data Science units.
##                This unit is R Programming
##
##
## Write a function called rankall that takes two arguments: 
## an outcome name (outcome) and a hospital rank- ing (num). 
## The function reads the outcome-of-care-measures.csv file 
## and returns a 2-column data frame containing the hospital 
## in each state that has the ranking specified in num. 
##
## For example the function call
## rankall("heart attack", "best") 
## would return a data frame containing the names of the hospitals that
## are the best in their respective states for 30-day heart attack 
## death rates. The function should return a value
## for every state (some may be NA). 
##    
## The first column in the data 
## frame is named hospital, which contains the hospital name, 
## and the second column is named state, which contains the 
## 2-character abbreviation for the state name. Hospitals that 
## do not have data on a particular outcome should be excluded 
## from the set of hospitals when deciding the rankings.


## -----------------------------------------
rankall <- function(outcome, num = "best") {

## Read outcome data
## Check that state and outcome are valid
## For each state, find the hospital of the given rank
## Return a data frame with the hospital names and the
## (abbreviated) state name

i_num <- num

outcomes <- c("heart attack", "heart failure", "pneumonia")

if (! outcome %in% outcomes) {
    stop("invalid outcome")
}


## build an empty data frame as we don't know
## how many records will be required so pre-allocating
## space is problamatical

rank_all  <- data.frame(  hospital = character(), 
                          state = character(), 
                          stringsAsFactors = FALSE)

## start the processing

outcome_data <- read.csv('data/outcome-of-care-measures.csv', 
                                stringsAsFactors = FALSE)

states <- unique(outcome_data[, "State"])


## I am just getting rid of a big ugly column name here.
## way too much typing and prone to error

if (outcome == "heart attack") {
    deaths <- "Hospital.30.Day.Death..Mortality..Rates.from.Heart.Attack" 
} else if (outcome == "heart_failure") {
    deaths <- "Hospital.30.Day.Death..Mortality..Rates.from.Heart.Failure"
} else {
    deaths <-"Hospital.30.Day.Death..Mortality..Rates.from.Pneumonia"}

for (state_index in states) {

   rank_entry <- rankhospital(state_index, outcome, i_num)
        
   if (!is.null(rank_entry)) {
        rank_all <- rbind(rank_all, 
                              data.frame( hospital = rank_entry, 
                                          state = state_index,
                                          stringsAsFactors = FALSE))
    }
 }

rank_all <- rank_all[order(rank_all$state), ]
return(rank_all)

}


