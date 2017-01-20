
- I had a large file. Processing that serially from the top to the bottom could take a long time.  
- Instead, MapReduce is designed to process data in parallel, so your file is broken into chunks, and then processed in parallel. 

### real world scenario 
- Let's imagine we run a retailer with thousands of stores around the world. 
-  And we have a big book of sales for the year 2012.  
- We've been asked to calculate things like the total sales per store for the year.  
- Let's say the lines of the ledger look like this. The date of the purchase, the location of the store, what they bought, and the amount. 
    - 2012-03-14 MIAMI Clothes 150 
    - 2012-04-08 NYC Snacks 96 
    - 2012-03-14 LA Car 2000 
    - 2012-04-08 MIAMI Bike 470 
- One way we could do this is just start at the beginning of the ledger and for every entry we see, write down the store location and the amount. Then keep going. For the third entry, we see that we've already written down Miami. So we'll update the amount to 150, and we would continue. 
    
    Hash Table 
- Historically, we'd probably use an associative array or a hash table to solve this problem in a traditional computing environment.  
- The location would be the key and the sales for that store, the value. 
-  Then we'd process the input file one line at a time, adding each store as a key.  
     
- problems  

 
- Run out of memory due to large hash table 
- Take long time 

### Distributed Work 
-  Rather than one person reading the entire ledger, say we had more people to help, like the staff of your accounting department.  
- We'll split them into two groups, called the mappers and the reducers.  
- Then we'll take the ledger, break it into chunks, give each chunk to one of the mappers.  
- Let's see what a mapper would do. They will take the first record in their ledger, and on an index card write the name of the store, and the amount of a sale. Then they'll take the next record, and repeat. As they're writing the index cards they'll pile them up so that cards for the same store go in the same pile. By the end, each mapper will have a pile of card per store.  
- Once the mappers have finished, the reducers can collect their sets of cards. We'll tell each reducer which store they're responsible for. Such as this one for New York City, and this one for Miami and Los Angeles. The reducers go to the mappers and retrieve the pile of cards for their stores. It's fast for them to do because the mappers have already separated the cards into a pile per store. Once the reducers have retrieved all their data, they collect all the small piles and create a larger pile. Then they start going through the piles one at a time. All they have to do at this point is add up all the amounts from all the cards in the pile. That gives them a total sales for that store. They can then write that amount on their final sheet. To keep things organized each reducer goes through his or her set of piles in alphabetical order. In other words, Los Angeles before Miami. 

### Summary of MapReduce 
- Mappers are just little programs that each deal with a relatively small amount of data, and work in parallel.  
- We call that output the Intermediate Records.  
- Hadoop deals with all data in the form of key and value. 
- In our example, the key was the store name and the value was the sale total for each particular piece of input.  
- Once the Mappers have finished, a phase of Map Reduce called the Shuffle and Sort takes place. 
- The Shuffle is the movement of the intermediate records from the Mappers to the Reducers.  
- The Sort is the fact that the Reducers will organize these sets of records, in our case these piles of index cards, into sorted order. 
- Finally, each Reducer works on one set of records at a time, or one pile of cards. It gets the key and then a list of all the values. In our case, a store like New York City. And in all the sales for that store, it processes those values in some way. In our case, we were adding up the sales. Then it writes out the final results. 