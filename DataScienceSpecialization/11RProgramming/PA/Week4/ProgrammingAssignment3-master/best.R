best <- function(state, outcome) {
    options(warn=-1)
    ## Read outcome data
    myData <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
    
    ## Check that state and outcome are valid
    if (!(state %in% myData[, 7])) { stop("invalid state")}
    
    ## Return hospital name in that state with lowest 30-day deat rate
    if (outcome == "heart attack") {
        index <- order(myData[, 11], myData[, 2])
    } else if (outcome == "heart failure") {
        index <- order(myData[, 17], myData[, 2])
    } else if (outcome == "pneumonia") {
        index <- order(as.numeric(myData[, 23]), myData[, 2])
    } else {
        stop("invalid outcome")
    }
    sortHosp <- myData[index, ]
    splitHosp <- split(sortHosp, sortHosp$State)
    bestHosp <- splitHosp[[state]]$Hospital.Name[1]
    bestHosp
}