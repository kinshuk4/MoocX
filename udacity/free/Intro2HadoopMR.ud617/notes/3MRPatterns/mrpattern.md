## MapReduce Design Patterns

1. Filtering patterns
	a. Sampling
	b. Top - N
2. Summarization patterns
	a. Counting
	b. Min/Max
	c. Statistics
	d. Index
3. Structural patterns
	a. Combining data sets

Filtering Pattern:
Filter the data to find the subset of data.
Types:
a. Simple filter
b. Bloom filter
c. Sampling
d. Random Sampling
e. Top 10

Top 10 MapReduce:
a. Each mapper generate top N list
b. Reducer find global top N

Summarization patterns:
a. Inverted index
b. Numerical summarization

Combiners:
In order to avoid all the mappers' keys values output to transfer to reducer
	node, combiners reduce the local data in each data node, also known as pre-
	reducer phase.

Structural pattern:
RDBMS -> Hadoop
Take advantage of hierarchial data

In order to use this pattern:
a. Data sources linked by foreign keys
b. Data must be structured and row-based.
