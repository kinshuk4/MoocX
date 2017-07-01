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
    if (outcome == "heart attack") {
        index <- order(myData[, 11], myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        i <- 1
        for (thisFactor in myFactor){
            if (rank == "best") {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[1]
            } else if (rank == "worst"){
                thisrank = length(which(splitHosp[[thisFactor]][, 11] != "Not Available"))
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[thisrank]]
            } else if (rank > length(which(splitHosp[[thisFactor]][, 11] != "Not Available"))) {
                hospital <- "NA"
            } else {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[rank]]
            }
            finalHosp[i] <- hospital
            finalState[i] <- thisFactor
            i <- i + 1
        }
    } else if (outcome == "heart failure") {
        index <- order(as.numeric(myData[, 17]), myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        i <- 1
        for (thisFactor in myFactor){
            if (rank == "best") {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[1]
            } else if (rank == "worst"){
                thisrank = length(which(splitHosp[[thisFactor]][, 17] != "Not Available"))
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[thisrank]]
            } else if (rank > length(which(splitHosp[[thisFactor]][, 17] != "Not Available"))) {
                hospital <- "NA"
            } else {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[rank]]
            }
            finalHosp[i] <- hospital
            finalState[i] <- thisFactor
            i <- i + 1
        }
    } else if (outcome == "pneumonia") {
        index <- order(as.numeric(myData[, 23]), myData[, 2])
        sortHosp <- myData[index, ]
        splitHosp <- split(sortHosp, sortHosp$State)
        i <- 1
        for (thisFactor in myFactor){
            if (rank == "best") {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[1]
            } else if (rank == "worst"){
                thisrank = length(which(splitHosp[[thisFactor]][, 23] != "Not Available"))
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[thisrank]]
            } else if (rank > length(which(splitHosp[[thisFactor]][, 23] != "Not Available"))) {
                hospital <- "NA"
            } else {
                hospital <- splitHosp[[thisFactor]]$Hospital.Name[[rank]]
                if (thisFactor == "HI") {print(hospital)}
            }
            finalHosp[i] <- hospital
            finalState[i] <- thisFactor
            i <- i + 1
        }
    } else {
        stop("invalid outcome")
    }
    hospital <- cbind(finalHosp, finalState)
    colnames(hospital) <- c("hospital", "state")
    row.names(hospital) <- as.character(myFactor)
    row.names(hospital)
    hospital <- as.data.frame(hospital, row.names = as.character(myFactor))
    hospital
}