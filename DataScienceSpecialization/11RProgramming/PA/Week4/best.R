## CAPTAIN SLOG
## vim: set expandtab tabstop=4 shiftwidth=4 autoindent smartindent:
## File         : best.R
## System       : Assignment 3 (Autograded)
## Date         : 12/08/2014
## Author       : Mark Addinall
## Synopsis     : This file is part of the course work
##                assignments for the Johns Hopkins
##                series of Data Science units.
##                This unit is R Programming
##

## -------------------------------
best <- function(state, outcome) {
## Read outcome data
## Check that state and outcome are valid
## Return hospital name in that state with lowest 30-day death
## rate


outcomes <- c("heart attack", "heart failure", "pneumonia")

if (! outcome %in% outcomes) {
    stop("invalid outcome")
}

outcome_data <- read.csv('data/outcome-of-care-measures.csv', stringsAsFactors = FALSE)

states <- unique(outcome_data[, "State"])

if (! state %in% states) {
    stop("invalid state")
}

## I am just getting rid of a big ugly column name here.
## way too much typing and prone to error
if (outcome == "heart attack") {
    deaths <- "Hospital.30.Day.Death..Mortality..Rates.from.Heart.Attack" 
} else if (outcome == "heart_failure") {
    deaths <- "Hospital.30.Day.Death..Mortality..Rates.from.Heart.Failure"
} else {
    deaths <-"Hospital.30.Day.Death..Mortality..Rates.from.Pneumonia"}

## sort on two column to resolve ties in scores for first place
sortnames       <- c(deaths, "Hospital.Name")

## trim the dataset down from 46 column to 3 that we need
outcome_data    <- outcome_data[, c("Hospital.Name", "State", deaths)]

## and dump all but the state we are interested in
outcome_data    <- subset(outcome_data, outcome_data[, 2] == state)

## we know, we WANT to force the NA converstions
suppressWarnings(outcome_data[,3] <- as.numeric(outcome_data[, 3]))

## dump the NAs
## this came about because the people that put the database
## together mixed numbers with "Too tichy" so R turns the
## whole database into characters.  We need to turn some
## of them back into numbers for the sort.
outcome_data    <- na.omit(outcome_data)

## sort by rank then name
outcome_data <- outcome_data[do.call("order", outcome_data[sortnames]),]

## da da!  The WINNER!!!
print(outcome_data[1, 1])
}
