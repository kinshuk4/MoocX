##3. Ranking the Hospitals by Outcome in a State

##Instructions: Write a function called rankhospital that takes three arguments: the
# state, an outcome, and ranking of a hospital for that outcome.
# The function reads the outcome-of-care-measures.csv file and returns a character vector
# with the name of the hospital that has the ranking specified by the num argument

rankhospital <- function(state, outcome, num) {
    
    if(exists("outcome_data")=="FALSE"){
    #read outcome data
    outcome_data <<- read.csv("outcome-of-care-measures.csv", colClasses = "character")
    }
    
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
    wd_ordered <- na.omit(working_data[order(working_data[,colnum],working_data$HOSPITAL.NAME),])
    
    
    #make a new dataframe, only with the hospital name, rate for the indicator, and rank
    wd_rank <- cbind(wd_ordered[,2], wd_ordered[,colnum], rep(0, nrow(wd_ordered))) 
    wd_rank <- as.data.frame(wd_rank, stringsAsFactors=FALSE)
    colnames(wd_rank) <- c("Hospital.Name", "Rate", "Rank")
  
    for (i in 1:nrow(wd_rank)) {
        wd_rank[i,3] <- i
    }
    
    #assign num for a few conditions
    if(num == "best") {
        num <- 1}
    
    if(num =="worst") {
        num <- nrow(wd_rank)
    }
    
    #Return hospital name for given state, outcome, rank
    Hospital_ranked <- wd_rank[num,1]
    Hospital_ranked  
}