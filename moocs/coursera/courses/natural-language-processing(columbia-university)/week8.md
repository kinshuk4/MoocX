# Log Linear Models for Tagging

We can also use log-linear models to solve the tagging problem. 

If we have input sentence `w_[1:n] = w_1, w_2 ... w_n` and tag sequence `y_[1..n] = y_1, y_2 ... y_n` we can use a log-linear model to define

	p(t_1, t_2 ... t_n | w_1, w_2 ... w_n)

for any sentence and tag sequence of the same length.

Note the contrast with the HMM which instead defines `p(t_1, t_2 ... t_n, w_1, w_2 ... w_n)`.

Then the most likely tag sequence for `w_[1:n]` is

	t*_[1:n] = arg max[t_[1:n]] p(t_[1:n] | w_[1:n])

How to model `p(t_[1:n] | w_[1:n])`?

	p(t_[1:n] | w_[1:n]) = p(t_1 | w_1, ..., w_n) *                   //By chain rule
	                       p(t_2 | w_1, ..., w_n, t_1) * .
	                       .. * 
	                       p(t_n | w_1, ..., w_n, t_1, ..., t_n-1)

	                     = p(t_1 | w_1, ..., w_n, t_-1, t_0) *        //By trigram independence assumption
	                       p(t_2 | w_1, ..., w_n, t_0, t_1) * .
	                       .. * 
	                       p(t_n | w_1, ..., w_n, t_n-2 t_n-1)

### Representation: Histories

A *history* is a 4-tuple `(t_-2, t_-1, w[1:n], i)` where :

* `t_-2`, `t_-1` are the 2 previous tags
* `w_[1:n]` are the `n` words in the input sentence
* `i` is the index of the word being tagged

`X` is the the set of all possible histories.

`Y` is the set of all possible tags `Y = {NN, NNS, Vt, ...}`. We also have `m` features `f_k: X * Y -> R` for `k=1..m`.

Example features:

	f_1(h, t) = 1 if w_i=base and t=Vt            //effectively e(base|Vt) in HMM
	            0 otherwise
	f_2(h, t) = 1 if w_i ends in ing and t=VBG    //impossible for HMM to capture
	            0 otherwise

(Ratnaparki, 96) defines the following set of features:

* word/tag features for all word/tag pairs, eg `if w_i=base and t=Vt`
* spelling features for all prefixes/suffixes of length <= 4, eg `if w_i ends in ing and t=VBG`
* contextual features, eg trigram/bigram/unigram, `if w_i-1=the and t=Vt`

This set of features is much more powerful than an HMM and is very close to still being the state of the art.

### The Viterbi Algorithm

The **Viterbi Algorithm** is constructed and justified in a similar way to with HMMs. Define:

	r(t_1,...,t_k) = mult[i=1..k] q(t_i | t_i-2, t_i-1, w_[1:n], i)

and dynamic programming table `pi(k, u, v)` as maximum probability of a tag sequence ending in tags `u`, `v` at position `k`.

#### Base Case

	pi(0,*,*) = 1 //Identical to HMM algo

#### Recursive Case

For any `k` in `{1..n}`, for any `u` in `S_k-1` and `v` in `S_k`:

	pi(k,u,v) = max[t in S_k-2] pi(k-1,t,u) *  q(v | t,u,w_[1:n],k)	

`q(v | t,u,w_[1:n],k)` is a log linear model (in place of `q(v | t,u) * e(w_k | v)` in HMM).


# Log Linear Models for History-based Parsing

If `S=w_[1:n]`, we've now seen how to define `p(T | S)` when `t_[1:n]` is a tag sequence (`T=t_[1:n]`). But what if `T` is a parse tree or some other structure?

1. Represent a tree as a sequence of **decisions** `d_1...d_m`, `T=(d_1...d_m)`. `m` is **not** necesarily the length of the sentence.
2. The probability of a tree is
		
		p(T | S) = mult[i=1..m] p(d_i | d_1...d_i-1, S)  // Note no Markov assumption (d_i depends on ALL previous decisions)

3. Use a log-linear model to estimate `p(d_i | d_1...d_n, S)`
4. Search?

### Ratnaparkhi's Parser: Three Layers of Structure

#### 1. Part-of-Speech tags

First `n` decisions are tagging decisions, eg for `S=(the lawyer questioned the witness about the revolver)`, `(d_1...d_n) = (DT, NN, Vt, DT, NN, IN, DT, NN)`.

#### 2. Chunks

Chunks are defined as any phrase where all children are part-of-speech tags. eg

	   _NP_         Vt        _NP_        IN      _NP_
	  |    |        |        |    |       |      |    |
	 DT    NN   questioned   DT   NN    about   DT    NN
	  |    |                 |    |              |    |
	 the lawyer             the witness         the revolver

In this case the the three `NP`s are chunks. In the decision structure, the next `n` decisions are chunk tagging decisions:

	Start(NP)   Join(NP)   Other     Start(NP)  Join(NP)   Other   Start(NP)  Join(NP)
	    |          |         |          |          |         |        |          |
	    DT         NN        Vt         DT         NN        IN       DT         NN
	    |          |         |          |          |         |        |          |
	   the       lawyer  questioned    the       witness    about    the      revolver

So `(d_1..d)2n) = (DT, NN, Vt, DT, NN, IN, DT, NN, Start(NP), Join(NP), Other, Start(NP), Join(NP), Other, Start(NP), Join(NP))`.

#### 3. Remaining Structure

Alternate between 2 classes of action:

* `Start(X)` or `Join(X)`, where `X` is a label (`NP`, `S`, `VP` etc), where
	- `Start(X)` starts a new constituent with label `X` 
	- `Join(X)` continues a constituent with label `X`
	Both always act on the left-most consituent with no label.
* `Check=YES` or `Check=NO`, where
	- `Check=YES` indicates that previous `Start` or `Join` action is a completed constituent
	- `Check=NO` does nothing

So

	(d_1...d_m) = (
	               //First n Level1 decisions
	               DT, NN, Vt, DT, NN, IN, DT, NN,
	               //Second n Level2 decisions
	               Start(NP), Join(NP), Other, Start(NP), Join(NP), 
	               Other, Start(NP), Join(NP),
	               //Level3 decisions
	               Start(S), Check=NO, Start(VP), Check=NO, Join(VP), 
	               Check=NO, Start(PP), Check=NO, Join(PP), Check=YES, 
	               Join(VP), Check=YES, Join(S), Check=YES
	              )

### Applying a Log-Linear Model

Use a log-linear model to estimate `p(d_i | d_1...d_i-1, S)` where

	p(d_i | d_1...d_i-1, S) =       e^( f( (d_1...d_i-1, S), d_i ) * v )
	                          _________________________________________________
	                          sum[d in A] e^( f( (d_1...d_i-1, S), d ) * v )

where

* `(d_1..d_i-1, S)` is the history
* `d_i` is the outcome
* `f` maps a history/outcome pair to a feature vector
* `v` is a parameter vector
* `A` is a set of possible actions

#### How do we define `f`?

Ratnaparkhi's method defines `f` differently depending on whether the next decision is:

* a tagging decision
* a chunk-tagging decision
* a start/join decision after chunking
* a check=yes/no decision

Features can be defined in the usual way.

### Seach

In POS tagging we could use the Viterbi Algorithm as each tag depended only only the previous 2 tags. However this (Markov) property does not hold here; each decision `d_i` could depend on arbitrary decisions in the past. Therefore there is no opportunity to use dynamic programming.

Instead Ratnaparkhi uses a **beam seach** method.