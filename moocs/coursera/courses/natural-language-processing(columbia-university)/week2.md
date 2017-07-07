# Generative Models for Supervised Learning

The **Tagging Problem** is the problem of tagging (for example) words in a sentence with the appropriate parts of speech (noun, verb etc).

In a **Supervised Learning** problem we have training examples `x_i`, `y_i` for `i=1..m`. Each `x_i` is an input, `y_i` is a label. The goal is to learn a function `f` that maps inputs to labels.

eg, with the tagging problem,

	x_1 = the dog laughs  y_1 = DT NN VB //DT = Determiner, NN = Noun, VB = Verb
	x_2 = the cat barks   y_2 = DT NN VB
	etc...

### Conditional (or Discriminative) Models

* Learn a distribution `p(y|x)` from training examples.
* For any test input `x`, define `f(x) = argmax_y p(y|x)`. ie `f(x)` is the most likely `y` according to the learned distribution.

### Generative Models

Learn distribution `p(x, y)`. ie the *joint distribution* of `x` and `y`; the probability of `x` and `y`. 

Often `p(x, y) = p(y)p(x|y)`. This means that given `p(x, y)` we can easily calculate the conditional distribution using Bayes Rule: 

	p(y|x) = ( p(y)p(x|y) ) / p(x)
	       = p(x, y) / p(x)

Output from the model is: 

	f(x) = argmax_y p(y|x)

	     //By substitution using Bayes Theorem (as above)
	     = argmax_y ( p(y)p(x|y) ) / p(x)

	     //Because p(x) does not vary with y
	     = argmax_y p(y)p(x|y)

The last term is useful as `p(x)` can often be hard to calculate.

# Hidden Markov Models

Input is sentence `x = x_1, x_2, ..., x_n`, eg `the dog`.

Label is sequence `y = y_1, y_2, ..., y_n`, eg `DT NN`.

We will define (in a moment) HMM `p(x_1, ..., x_n, y_1, ..., y_n)` for any sentence and tag sequence of same length. Using this, the most likely tag sequence for a given input `x` is `argmax[y_1...y_n] p(x_1, ..., x_n, y_1, ..., y_n)` - ie. the `y` that maximises the HMM output. Note that brute force search over this space is unlikely to be possible - will see a way round this for HMMs later.

### Trigram Markov Model

For sentence `x_1...x_n` where every `x_i` is in `V` (the vocabulary), and tag sequence `y_1...y_n+1` where `y_i` is in `S` (the set of all tags) and `y_n+1` is `STOP`, the joint probability of the sentence and the tag sequence is:

	p(x_1, ..., x_n, y_1, ..., y_n) = q(y_1 | y_-1, y_0) *    // Trigram (transition) parameters eg q(DT | NN, VT)
	                                  ... *                   // 
	                                  q(y_n+1 | y_n-1, y_n) * // 
	                                  e(x_1 | y_1) *          // Emission parameters. eg e(the | DT)
	                                  ... *                   //
	                                  e(x_n | y_n)            //

#### Exercise

Say we have tag set `S = {D, N}`, vocabulary `V = {the, dog}` and a hidden Markov Model with transition parameters:

	q(D | *, *) = 1
	q(N | *, D) = 1
	q(STOP | D, N) = 1
	q(s | u, v) = 0 for all other q params

and emission parameters:

	e(the | D) = 0.9
	e(dog | D) = 0.1
	e(dog | N) = 1

How many pairs of sequences `x_1, ..., x_n, y_1, ..., y_n+1` satisfy `p(x_1, ..., x_n, y_1, ..., y_n+1) > 0`?

	p(the, D)         = q(D | *, *) * q(STOP | *, D) * e(the | D) 
	                  = 1 * 0 * 0.9
	                  = 0 // INVALID. Same for all other 1-word sentences as STOP term will be 0
	
	p(the, the, D, N) = q(D | *, *) * q(N | *, D) * q(STOP | D, N) * e(the, D) * e(the, N)
	                  = 1 * 1 * 1 * 0.9 * 0
	                  = 0 // INVALID. Same for all other sentences where the is tagged as N
	
	p(the, dog, D, N) = q(D | *, *) * q(N | *, D) * q(STOP | D, N) * e(the, D) * e(dog, N)
	                  = 1 * 1 * 1 * 0.9 * 1
	                  = 0.9 
	
	p(dog, dog, D, N) = q(D | *, *) * q(N | *, D) * q(STOP | D, N) * e(dog, D) * e(dog, N)
	                  = 1 * 1 * 1 * 0.1 * 1
	                  = 0.1 

Notice how result is zero whenever either an illegal transition or an invalid word/tag pair features.

### Parameter Estimation

`q` can be estimated using using maximum likelihood with linear interpolation (as seen in week 1), for example:

	q(Vt | DT, JJ) = lambda_1 * ( count(Dt, JJ, VT) / count(Dt, JJ) ) +   // Trigram ML estimate
	                 lambda_2 * ( count(JJ, Vt) / count(JJ) ) +           // Bigram ML estimate
	                 lambda_3 * ( count(Vt) / count() )                   // Unigram ML estimate

Where `lambda_1 + lambda_2 + lambda_3 = 1` and `lambda_i > 0` for all `i`.

`e` can be estimated very simply, again using maximum likelihood:

	e(base | Vt)   = count(Vt, base) / count(Vt)

#### Exercise

Consider the following corpus of tagged sentences:

	the dog barks -> D N V STOP
	the cat sings -> D N V STOP

What is the value of the parameter `e(cat | N)` for an HMM estimated on this corpus?

	e(cat | N) = count(N, cat) / count(N)
	           = 1 / 2
	           = 0.5

If we use linear interpolation with `lambda_1 = lambda_2 = lambda_3 = 1/3`, what is the value of the parameter `q(STOP | N, V)`?

	q(STOP | N, V) = 1/3 * ( count(N, V, STOP) / count(N, V) ) +
	                 1/3 * ( count(V, STOP) / count(V) ) +
	                 1/3 * ( count(STOP) / count() )
	               = 1/3 * ( 2 / 2 ) + 1/3 * ( 2 / 2 ) + 1/3 * (2 / 8)
	               = 0.75

### Dealing with Low Frequency Words

Estimating `e` is complicated by the fact that `e(y | x) = 0` for all `y` if `x` is not seen in the training data. In other words, we are unable to tag words that we have not seen in training (which can happen frequently in practice).

A common method used to solve this is:

1. Split vocabulary into 2 sets: 
	* Frequent words - that appear eg 5+ times
	* Low frequency words - all other words

2. Map low frequency words into a small, finite set of tokens, eg `twoDigitNum`, `allCaps`, `capPeriod` etc. Then run the model on the mapped/tokenized dataset.

# The Viterbi Algorithm for HMMs

*Recap:* To find the predicted tag sequence for a given input, we need to solve:

	arg max[y_1..y_n+1] p( x_1...x_n, y_1...y_n+1 )

where `arg max` is taken over sequences of `y_i` in `S` (for `i=1..n` and `y_n+1` is `STOP`).

Brute force is not an option as the number of possible combinations is exponential with the size of the input (specifically `S^n`).

The **Viterbi Algorithm** provides an efficient implementation for solving this.

We define function `r` as "the first `k` terms of `p`",  ie simply:

	r(x_1, ..., x_k, y_1, ..., y_k) = q(y_1 | y_-1, y_0) *
	                                  ... *
	                                  q(y_k | y_k-2, y_k-1) *
	                                  e(x_1 | y_1) *
	                                  ... *
	                                  e(x_k | y_k)

Also `S_k` is the set of all possible tags at position `k`, so `S_-1 = {*}`, `S_0 = {*}`, `S_k = S` for 'k=1..n'.

Then define a *dynamic programming table* `pi(k, u, v)` that is the maximum probability of a sequence ending in tags `u`, `v` at position `k` (ie the max value of `r` over all sequences of `y`).

#### Exercise

We are given an HMM with transition parameters:

	q(D | *, *)    = 1
	q(N | *, D)    = 1
	q(V | D, N)    = 1
	q(STOP | N, V) = 1

and emission parameters:

	e(the | D)     = 0.8
	e(dog | D)     = 0.2
	e(dog | N)     = 0.8
	e(the | N)     = 0.2
	e(barks | V)   = 1

Say we have the sentence `the dog barks`, what is the value of `pi(3, N, V)`?

	Only possible sequence of tags is D N V STOP, giving the/D dog/N barks/V.

	pi(3, N, V) = q(D | *, *) * q(N | *, D) * q(V | D, N) * e(the | D) * e(dog | N) * e(barks | V)
	            = 1 * 1 * 1 * 0.8 * 0.8 * 1
	            = 0.64

### A Recursive Definition

Obviously the above definition of `pi` requires calculating all possibilities so is no improvement over the brute force case. However, it is possible to define `pi` recursively:

##### Base Case

	pi(0,*,*) = 1

##### Recursive Case

	pi(k,u,v) = max[w in S_k-2] ( pi(k-1,w,u) * q(v|w,u) * e(x_k|v) )

#### Example

Given input sentence `the man saw the dog with the telescope` and set of tags `S = {D, N, V, P}`,

	pi(7,P,D) = max[w in {D, N, V, P}] ( pi(6,w,P) * q(D|w,P) * e(the|D) )

The insight here is that the "highest probability path" for a given sequence ending in `P D` at position 7 **has** to include the highest probability path up to position 5. 

#### Exercise

Say we are given a tag set `S = {D, N, V, P}` and an HMM with parameters

	q(D|N,P) = 0.4
	q(D|w,P) = 0 for w != N
	e(the|D) = 0.6

We are also given the sentence `Ella walks to the red house`.

Say the dynamic programming table for this sentence has the following entries:

	pi(3,D,P) = 0.1
	pi(3,N,P) = 0.2
	pi(3,V,P) = 0.01
	pi(3,P,P) = 0.6

What is the value of pi(4,P,D)?

	pi(4,P,D) = max[w in {D, N, V, P}] ( pi(3,w,P) * q(D|w,P) * e(the|D) )
	          = max(
	                pi(3,D,P) * q(D|D,P) * e(the|D),
	                pi(3,N,P) * q(D|N,P) * e(the|D),
	                pi(3,V,P) * q(D|V,P) * e(the|D),
	                pi(3,P,P) * q(D|P,P) * e(the|D)
	            )
	          = max(
	                0.1  * 0   * 0.6,
	                0.2  * 0.4 * 0.6,
	                0.01 * 0   * 0.6,
	                0.6  * 0   * 0.6
	            )
	          = max( 0, 0.048, 0, 0)
	          = 0.048

# Pros and Cons of HMMs

* Very simple to train (just needs counts from training corpus)
* Perform relatively well (90%+ performance on named entity recognition)
* Main difficulty is modelling `e(word|tag` - requires human input (mapping low frequency words to symbols). This can get out of hand when words are more complex (as we will see later in course).