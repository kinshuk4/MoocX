rankall <- function(outcome, rank = "best") {
    options(warn=-1)
    ## Read outcome data
    myData <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
    
    myFactor <- unique(factor(myData$State))
    numRows <- length(myFactor)
    finalHosp <- matrix(ncol = 1, nrow = numRows)
    finalState <- matrix(ncol = 1, nrow = numRows)
    
    ## Return hospital name in that state with given rank
    ## 30-day death rate
    if (outcome == "pneumonia") {
        index <- order(myData[, 23], myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        i <- 1
        for (thisFactor in myFactor){
            if (rank == "best") {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[1]
            } else if (rank == "worst"){
                rank = length(which(splitHosp[[thisFactor]][, 23] != "Not Available"))
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[rank]]
            } else if (rank > length(which(splitHosp[[thisFactor]][, 23] != "Not Available"))) {
                hospital <- "NA"
            } else {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[rank]]
            }
            finalHosp[i] <- hospital
            finalState[i] <- thisFactor
            i <- i + 1
        }
    } else {
        stop("invalid outcome")
    }
    hospital <- cbind(finalHosp, finalState)
    names(hospital) <- c("hospital", "state")
    row.names(hospital) <- as.character(myFactor)
    hospital
}