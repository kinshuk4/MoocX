##2. Finding the Best Hospital in a State

#Instructions: Write a function called "best" that takes two arguments: the 2-character abbreviated name of a state and an outcome name.
#The function reads the out-come-of-care-measures.csv file and returns a character vector
#with the name of the hospital that has the best (i.e. lowest) 30-day mortality
#for the specified outcome in that state.

best <- function(state, outcome) {
    
    #read outcome data
    outcome_data <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
    
    #reset arguments to uppercase
    state <- toupper(state)
    outcome <- toupper(outcome)
    
    #Re-set column names to uppercase 
    colnames(outcome_data) <- toupper(colnames(outcome_data))
    
    #Set column number for different outcomes
    colnums <- data.frame(c("HEART ATTACK", "HEART FAILURE", "PNEUMONIA"), c(11,17,23))
    colnames(colnums) <- c("OUTCOME", "OUTCOME_COL")
    colnum <- colnums[colnums$OUTCOME==outcome,2]
    
    #Check that State & Outcome are valid
    if(nrow(outcome_data[outcome_data$STATE==state,])==0) stop('invalid state')
    if(length(colnum)==0) stop('invalid outcome')
    
    #Coerce outcome data to numeric
    outcome_data[,11] <- as.numeric(outcome_data[,11])
    outcome_data[,17] <- as.numeric(outcome_data[,17])
    outcome_data[,23] <- as.numeric(outcome_data[,23])
    
    #subset the data based on arguments provided
    working_data <- outcome_data[outcome_data$STATE==state,]
    
    #re-order the data, w/ minimum in the first row
    wd_ordered <- na.omit(working_data[order(working_data[,colnum]),])
    
    #so far so good. Up to here, working_data holds only texas data
    
    #Return the name of the hospital corresponding with the 30-day death rate, print to console
    Best_hospital <- wd_ordered[1,2]
    Best_hospital
    
}

