rankhospital <- function(state, outcome, rank = "best") {
    options(warn=-1)
    ## Read outcome data
    myData <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
    
    ## Check that state is valid
    if (!(state %in% myData[, 7])) { stop("invalid state")}
    
    ## Return hospital name in that state with given rank
    ## 30-day death rate
    if (outcome == "heart attack") {
        index <- order(myData[, 11], myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        if (rank == "best") {
            rank = 1
        } else if (rank == "worst"){
            rank = length(which(splitHosp[[state]][, 11] != "Not Available"))
        }
        if (rank > length(which(splitHosp[[state]][, 11] != "Not Available"))) {
            hospital <- "NA"
        } else{
            hospital <- splitHosp[[state]]$Hospital.Name[[rank]]
        }
    } else if (outcome == "heart failure") {
        index <- order(myData[, 17], myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        if (rank == "best") {
            rank = 1
        } else if (rank == "worst"){
            rank = length(which(splitHosp[[state]][, 17] != "Not Available"))
        }
        if (rank > length(which(splitHosp[[state]][, 17] != "Not Available"))) {
            hospital <- "NA"
        } else{
            hospital <- splitHosp[[state]]$Hospital.Name[[rank]]
        }
    } else if (outcome == "pneumonia") {
        index <- order(as.numeric(myData[, 23]), myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        if (rank == "best") {
            rank = 1
        } else if (rank == "worst"){
            rank = length(which(splitHosp[[state]][, 23] != "Not Available"))
        }
        if (rank > length(which(splitHosp[[state]][, 23] != "Not Available"))) {
            hospital <- "NA"
        } else{
            hospital <- splitHosp[[state]]$Hospital.Name[[rank]]
        }
    } else {
        stop("invalid outcome")
    }
    hospital
    
}