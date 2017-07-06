#10: Reasoning about Scale
## What Does Scalable Mean?
- Operationally:
  - in the past: "works even if data dosen't fit in main memory"
    - "out of core processiong" - uses disk not just main memory
  - now: "can make use of 1000s of cheep computers"
    - the datatranfer from disk is too show, this allows for the use of main memory of many machines
    - Scale out
- Algorithmically:
  - in the past: if you have N data times, you must do no more that N<sup>m</sup> opertations - polynomal time algorithims
  - Now: if you have N data items, you must do no more that N<sup>m</sup>/K operations for some large k (k is the number of machines)
    - Polynomial-time algorithms must be paralleized
  - Soon: If you have N data items, you should do no more that N * log(N) operations
    - As the data size goes up, you may only get one pass at the data
    - the data is streaming -- you better make the pass count
    - Ex: large Synoptic Survay Telescope (30TB/night)

## A Skethch of Algorithmic Complexity
- Ex. Find matching DNA sequences
  - Given sequence
  - find all matching GATTACGATATTA
  - linear search perofmed in O(N)
  - if we can sort we can use binary serch O(log(N))
      -O(N * log(N)) including the sort
- Databases are good a "Needle in Haystak" problems
  - Extractign small resutls from big datasets
  - Transparently provide "old style" scalability
  - query will always finish regardless of dataset size
  - Indexes are easily build and automatically used when appropriate
    - Sorts recores according to the given column
  - a lot of algroythmic work is allready done for you in a SQL statment
- New task: Read Trimming
  - Given a set of DNA sequence
  - trim the final n bps of each sequence
  -  generate a new dataset
  - index is not going to help, we need to touch every record
  - each taks is independant, we can break in to peices and process in parallel
  - O(N/k)
- Schematic of a parallel "read Trimming" task
  - distribute data across computers
  - apply function to the data
  - reslts in many sub-results
- The pattern:
  - function that **maps** data to the computation required
- What if we want to compute the word frequency acroos all documets
  - the results need to be shared be we want this done in parallel
  - given our many sub-results
    - we need a function that will send each required result to just one computer
    - has function to find couter id from the value of the data (i.e. the word to count)
  - These fucnction **reduce** the results
- The programmers task in then simlified to just creating the Map and Reduce functions

## 11 MapReduce Abstraction
## MapReduce Data Model
- Google paper 2004
- free implimentation: Hadoop
- Map-reduce = high-level programming model and implementatin for large-scale paralle data procession
  - implemented on GPUs, grpups of mobile phones, cores
- Data Model
  - A file = a bag of (key, value) pairs
  - A map-reduce program:
    - input: a bag of (inputkey, value) pairs
    - Output: a bag of (outputkey, value) pairs
   - Assumption that a value is small (fits on one machine)

## Map reduce funtions
- Step 1: the MAP Phase
- User provides the MAP-function
  - inpurt: (input key, value)
  - ouptupt bag of: (intermediate key, value)
  - system applies the map function in paralle to all (input key, value) pairs in the input file
- Step 2 the Reduce phase
- User provdes the REDUCE funtion
  - input (intermediate key, bag of values)
  - Output: bag of values
  - the system will group all pairs with the same intermediate key and pass the bag of vlaues the the REDUCE function
- Example:

```
map(String input_key, String input_value):
  //input_key: document name
  //input_value: documet contents
  for each word w in input_value:
    EmitIntermediate(w,1);

reduce(String output_key, Iterator intermediate_values):
  // output_key: word
  // output_values:???
  int results = 0;
  for each v in intermediate_values:
    results +=v;
  EmitFinal(Intermediate_key, result)
```

- Map: for each word in input_Value, emit that word and the number 1
- Shuffel: group all intermediate keys (words), with a series of values (1's)
- Reduce: initilize counter to 0, and count all 1's for each word

### improving the algroithgm
- instead of issuing a key-value pare for each word, count all instes of the word in each document and issure the total to the reuce function
- the bottleneck often is the amout of data going accross the network

## MR example: word length
- how many words are
    - big
    - medium
    - small
    - tiny
- HDFS automatilcy splits large documents in to "Chunks"
- Map for chunk 1
    - (big, 17)
    - (medium, 77)
    - (small, 93)
    - (tiny, 6)
- shuffel orginized all catagoreies
- reduce adds all sub-totals

### Build an Inverted Index
- Input
  - tweet1, ("I love pancakes for breakfast)
  - tweet2, ("I dislike pancakes")
  - tweet3, ("What should I eat for breakfast?")
  - tweet4, ("I love to eat")
- Output
  - "pancakes", (tweet1, tweet3)
  - "breakfast", (tweet1, tweet3)
  - "eat", (tweet3, tweet4)
  - "love", (tweet1, tweet4)

- Map produces tupes of (word, docid)
    - (pancake, tweet1)
    - multiptle copies of the same word can be discareded
- Reduce just takes the word and its iterator
    - (word, [tweet1,..., tweetN])

