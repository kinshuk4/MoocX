## 4. Ranking Hospitals in All States

## Instructions: Write a function called rankall that takes two arguments: an outcome
# name (outcome) and a hospital ranking (num). The function reads the data and returns
# a two-column data frame containing the hospital in each state that has the ranking
# specified in num. The function call rankall("heart attack", "best") would return a data
# frame containing the names of the hospitals that are the best in their respective states
# for every state (some may be NA). First column is named hospital, second is named
# state. Hospitals that do not have adata on a particular outcome should be excluded from
# the set of hospitals when deciding the rankings.

# The rankall function should handle ties in the 30-day mortality rates in the same way that
# rankhospital handles ties

rankall <- function(outcome, num){
    
    #add DC to state.abbs & then alphabetize it
    states <- append(state.abb, "DC", after=length(state.abb))
    states <- sort(states)
    
    #create a dataframe to store everything in, give it names
    rank_df <- data.frame(rep(0,length(states)), rep(0,length(states)))
    colnames(rank_df) <- c("hospital", "state")
    
    #Fill in the states
    for(i in 1:length(states)) {
        rank_df[i,2] <- states[i]
    }
    
    #name the rows by state
    rownames(rank_df) <- rank_df[,2]
    
    #Fill in the hospitals
    for(i in 1:length(states)){
        rank_df[i,1] <- rankhospital(states[i],outcome,num)
    }
    
    rank_df
}