# Trigram Models

TODO

# Evaluating Language Models: Perplexity

**Perplexity** is a measure of the effectiveness of a language model on a test corpus.

Test corpus is composed of sentences `s_1...s_m`. We can use our model to calculate the probability of seeing this corpus: `p(s_1) * ... * p(s_m)`.

Note that:

	log2( p(s_1) * ... * p(s_m) ) == log2( p(s_1) ) + ... + log2( p(s_m) )

If we divide the sum above by the number of test sentences `M` we get a 'normalized' value `l` - a measure of the 'probability-per-word' for the test data. Then:

	perplexity = 2^(-l)

where

	l = ( log2( p(s_1) ) + ... + log2( p(s_m) ) ) / M

So the lower the perplexity, the better the model. 

For the simplest possible model (one that assigns uniform probability to all possible trigrams) the perplexity works out to be `N` (size of vocabulary + 1).

#### Exercise

Given trigram language model:

	q(the | *, *)         = 1
	q(dog | *, the)       = 0.5
	q(cat | *, the)       = 0.5
	q(walks | the, cat)   = 1
	q(STOP | cat, walks)  = 1
	q(runs | the, dog)    = 1
	q(STOP | dog, runs)   = 1

and test corpus `the dog runs STOP, the cat walks STOP, the dog runs STOP`, what is the perplexity of the language model?

(Note that the number of words in this test corpus `M` is 12.)

	the dog runs STOP     = 1 * 0.5 * 1 * 1   = 0.5
	the cat walks STOP    = 1 * 0.5 * 1 * 1   = 0.5
	the dog runs STOP     = 1 * 0.5 * 1 * 1   = 0.5

	l = log2(0.5) + log2(0.5) + log2(0.5)) / 12 = -0.25

	perplexity = 2^-( -0.25 ) = 1.189

### Some Context

From Goodman ("A bit of progress in language modeling"), vocabulary size was 50000. Trigram model resulted in perplexity of 74, ie vastly better than base case of 50000. Bigram model resulted in perplexity of 137, unigram 955.

Shannon found that a human modeller performed better than any trigrams, 4-grams etc.

# Linear Interpolation

### Recap on Sparse Data Problems

For trigram models we need to estimate the probability of seeing a word given its 2 predecessors. A natural way to do this is the intuative *maximum likelihood estimate*:

	q_ML( w_i | w_i-2, w_i-1 )  =   count( w_i-2, w_i-1, w_i ) / count( w_i-2, w_i-1 )

For example:

	q_ML( laughs | the, dog )   =   count( the, dog, laughs ) / count( the, dog )

However with vocabulary size `N` this results in `N^3` parameters in the model. For a vocabulary of size 20000 that is 8*10^12 parameters - far too many.

This problem manifests itself with zeros for the numerator (resulting in zero probability estimates) or the denominator (resulting in undefined estimates). 

### Other estimators

We can similarly calculate bigram and unigram estimators:

##### Bigram

	q_ML( w_i | w_i-1 )         =   count( w_i-1, w_i ) / count( w_i-1 )

##### Unigram

	q_ML( w_i )                 =   count( w_i ) / count()

Where `count()` is the number of words in the corpus.

### Bias-Variance Tradeoff

The trigram estimator has low bias (as it contains a good amount of context) so will result in a good probability estimate. However it requires a very large amount of data in order to converge reliably.

Conversely, the unigram estimator has high bias (it will result in a crude estimate only) but low variance (it will converge relatively quickly).

### Combining the Estimates

One way of improving our estimate would be to combine the trigram, bigram and unigram maximum-lihelihook estimates, using some weighting scheme:

	q( w_i | w_i-2, w_i-1 )      =   lambda_1 * q_ML( w_i | w_i-2, w_i-1 ) + 
	                                 lambda_2 * q_ML( w_i | w_i-1 ) + 
	                                 lambda_3 * q_ML( w_i ) + 

where `lambda_1 + lambda_2 + lambda_3 = 1` and `lambda_i > 0` for all `i`.

For example, using `lambda_1 = lambda_2 = lambda_3 = 1/3`:

	q( laughs | the, dog )       =   1/3 * q_ML( laughs | the, dog ) + 
	                                 1/3 * q_ML( laughs | dog) +
	                                 1/3 * q_ML( laughs )

#### Exercise

Given the corpus:

	the green book STOP
	my blue book STOP
	his green house STOP
	book STOP

In a language model based on linear interpolation with `lambda_i = 1/3` for all `i in {1, 2, 3}`, what is the value of the parameter `q_LI( book | the, green )` in the model?

	q_LI( book | the, green )    = 1/3 * q_ML( book | the, green ) + 
	                               1/3 * q_ML( book | green ) + 
	                               1/3 * q_ML( book )

	                             = 1/3 * ( count( the, green, book ) / count( the, green ) ) +
	                               1/3 * ( count( green, book ) / count( green ) ) +
	                               1/3 * ( count( book ) / count() )

	                             = 1/3 * ( 1 / 1 ) + 1/3 * ( 1 / 2 ) + 1/3 * ( 3 / 14 ) 

	                             = 0.571

### Choosing Lambdas

These are estimated from the data:

* Hold out a percentage of the training set as "vailidation" data.

* Define `c'(w_1, w_2, w_3)` as the number of times the trigram `(w_1, w_2, w_3)` is seen in the validation set.

* Choose `lambda_1`, `lambda_2`, `lambda_3` to maximise:
		
		L(lambda_1, lambda_2, lambda_3)  =  sum[over w_1,w_2,w_3]( 
		                                      c'(w_1, w_2, w_3) * log( q( w_3 | w_1, w_2 )) 
		                                    )
  (Note that q contains `lambda_1`, `lambda_2`, `lambda_3`.)

  A smaller `q` results in a more negative `log( q )`, which is then compounded by the true number of occurances `c'`. So the worse the `lambda`s are, the more negative the overall sum will be.

### Practical Note

In practice it is generally important to vary the `lambda`s based on how frequently given tri/brigrams appear in the corpus. eg For trigrams that do not appear anywhere in the corpus we would set `lambda_1 = 0` and adjust `lambda_1` and `lambda_2` upwards accordingly.

# Discounting

Maximum likelihood estimates are going to systematically over-estimate the likelihood of seeing a bigrams that appeared in the training corpus (particularly for low count items), and underestimate the likelihood of those that did not.

Therefore we might apply a discount of (say) 0.5 to the observed counts, `count*(x) = count(x) - 0.5`

	            |             |                        |              | 
	x           | count(x)    | q_ML( w_i | w_i-1)     | count*(x)    | count*(x) / count(the)
	---------------------------------------------------------------------------------------------
	the         | 48          |                        |              |
	            |             |                        |              | 
	the, dog    | 15          | 15/48                  | 14.5         | 14.5/48
	the, woman  | 11          | 11/48                  | 10.5         | 11.5/48
	...
	etc

When is comes to summing all of the likelihood estimates there will be some missing probability mass, specifically `0.5 * number_of_rows_in_table`. We can then allocate this 'left-over' probability `alpha` to other words not seen to follow `the` in the training corpus. This is known as a *Katz Back-Off Model*.

### Katz Back-Off Model (Bigrams)

For each word `w` define 2 sets: 

* `A` contains all words that follow `w` at least once in the training data
* `B` contains all words that do not follow `w` in the training data

We can then define `q_BO` (the likelihood of `w_i` following `w_i-1`) as follows:

	q_BO( w_i | w_i-1 )  =  count*( w_i-1, w_i ) / count( w_i-1 )                      if w_i is in A(w_i-1)
	                     =  alpha( w_i-1 ) * ( q_ML( w_i ) / sum[over B]( q_ML( w ) ))    if w_i is in B(w_i-1)

That is:

* for words in `A`, simply use the discounded maximum likelihood. 
* for words in `B`, use previous word's `alpha`, weighted by how common the previous word is releative to the rest of `B`.

#### Exercise

Given corpus:

	the book STOP
	his house STOP

and discount 0.5, ie `c*(v, w) = c(v, w) - 0.5`, what is the value of `q_BO( book | his )`?

	For his, A = { house }, B = { the, book, STOP, his }

	q_BO( book | his ) = alpha( his ) * q_ML( book ) / ( q_ML( the ) + q_ML( book ) + q_ML( STOP ) )

	alpha(his) = 1 - (0.5 / 1) = 0.5

	q_ML( the )  = 1/6
	q_ML( book ) = 1/6
	q_ML( STOP ) = 2/6
	q_ML( his ) = 1/6

	So, 

	q_BO( book | his ) = 0.5 * (1/6) / (1/6 + 1/6 + 2/6 + 1/6)
	                   = 0.1

### Katz Back-Off Model (Trigrams)

Thee Katz model for trigrams buils upon the bigram model: `alpha` is weighted by `q_BO` calculated at the bigram level.

	If w_i is in B(w_i-2, w_i-1)

	q_BO( w_i | w_i-2, w_i-1 )  =  (alpha( w_i-2, w_i-1 ) * q_BO( w_i | w_i-1 ) ) / sum[over B]( q_BO( w | w_i-1 ) ) 

### Choosing the discount value

This is generally a value between 0 and 1. A value of 0.5 is a reasonable guess; a better value can be found by optimizing on validation data in a similar way to the `lambda`s in linear interpolation.