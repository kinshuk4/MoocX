# Log Linear Models 

(Also called Maximum Entropy Models.)

A couple of motivating examples:

* **Language Modelling Problem**: We may want to extend the trigram language model with more features, eg:
	
		q(w_i | w_1..w_i-1)  = lambda_1 * q_ML(w_i | w_i-2 w_i-1) * 
		                       lambda_2 * q_ML(w_i | w_i-1 ) *
		                       lambda_3 * q_Ml(w_i) *
		                       //Extra terms below
		                       lambda_4 * q_ML(w_i | w_i-2) *
		                       lambda_5 * q_ML(w_i | author) *
		                       lambda_6 * q_ML(w_i | w_i not in w_1..w_i-1) *
		                       //etc
  
* **Part-of-Speech tagging**: We may implement (as an alternative to an HMM) a part-of-speeach tagging algorithm by modelling the distribution `p(t_i | t_1..t_i-1, w_1..w_n)`. As above, we may want to add many more features to this model.

Attempting to handle either of these by simply extending the existing formulae quickly becomes unweildy and is not practical.

### The General Problem

We have:

* some (potentially infinite) **input domain** `X` (eg. set of all possible document histories `w_1..w_i-1`)
* a  *finite* **label set** `Y` (eg. `w_i`)

The aim is to provide a **conditional probability** `p(y | x)` for any `x`, `y` where `x in X`, `y in Y`.

### Feature Vector Representations

A **feature** is a function `f_k(x, y)` in `R`. In practice these are often **binary features** `f_k(x, y)` in `{0, 1}`.

Say we have `m` features `f_k` for `k=1..m`, a **feature vector** is `f(x, y)` in `R^m` for any `x`, `y`.

For example:

	//unigram feature
	f_1(x, y) = 1 if y = "model"                                        
	            0 otherwise

	//bigram feature
	f_2(x, y) = 1 if y = "model" and w_i-1 = "statistical"              
	          = 0 otherwise

	//trigram feature
	f_3(x, y) = 1 if y = "model", w_i-2 = "any", w_i-1 = "statistical"  
	          = 0 otherwise

	//skip-bigram feature
	f_4(x, y) = 1 if y = "model", w_i-1 = "any"                         
	          = 0 otherwise

	f_5(x, y) = 1 if y = "model", y not in w_1..w_i-1
	          = 0 otherwise

	//etc...

#### The Final Result

For a given history `x` in `X`, each output in `y` is mapped to a different feature vector:

	f(("word1", "word2", "word3"), "word1") = 1000001010001001
	f(("word1", "word2", "word3"), "word2") = 0010100100100110
	f(("word1", "word2", "word3"), "word3") = 0000010010001001

Note that these feature vectors are often *sparse*.

### Parameter Vectors

Given features `f_k(x, y)` for `k=1..m` also define a **parameter vector** `v` in `REAL_NUMBERS^m`. Each `(x, y)` pair is them mapped to a score:

	v f(x, y) = v_1 * f_1(x, y) + ... + v_n * f_n(x, y)

Each possible `y` gets a different score:

	v . f(x, model) = 5.6
	v . f(x, is) = 1.5
	v . f(x, the) = -3.2
	v . f(x, models) = 4.5
	//etc

We can convert these scores to probabilities via the following:

	p(y | x, v) =            e ^ (v f(x, y)) 
	                _______________________________________
	                 sum[over y' in Y]( e ^ (v f(x, y')) )

(So the more negative the score `vf(x, y)`, the closer the probability to `0`, and the more positive, the closer to `1`.)

#### Exercise

Consider the label set `Y = {cat, dog, hat, cot}` with features:
	
	f_1(x, y) = 1 if x=the and y ends with at
	          = 0 otherwise
	f_2(x, y) = 1 if x=the and y starts with c
	          = 0 otherwise
	f_3(x, y) = 1 if x=the and y has second letter o
	          = 0 otherwise

Say we are given weight vector `v = (3 1 1)`, what is the value of `p(hat | the, v)`?

	v f(cat | the)  = (3 1 1) * (1 1 0) = 4
	v f(dog | the)  = (3 1 1) * (0 0 1) = 1
	v f(hat | the)  = (3 1 1) * (1 0 0) = 3
	v f(cot | the)  = (3 1 1) * (0 1 1) = 2

	p(hat | the, v) = e^3 / (e^4 + e^1 + e^3 + e^2)
	                = 0.237

### Why the name?

	p(y | x, v)     =            e ^ (v f(x, y)) 
	                    _______________________________________
	                     sum[over y' in Y]( e ^ (v f(x, y')) )

	ln p(y | x, v)  = v f(x, y) - ln sum[over y' in Y]( e ^ (v f(x, y')) )
	                      |                          |
	                  linear term            normalization term

### Parameter Estimation

We estimate the parameter vector `v` using *maximum likelihood estimation* over each training sample `(x_i, y_i)` in `X * Y`:

	v_ML = arg max[v in R] L(v)

where

	L(v) = sum[i=1..n]( ln p(y_i | x_i, v) )
	     = sum[i=1..n]( ln v f(x_i, y_i) ) - sum[i=1..n]( ln sum[y' in Y]( e^(v f(x_i, y')) ) )

It is not possible to find a closed form solution, however `L(v)` is a **concave** function, so we can optimize using gradient ascent. It can be shown that:

	d L(v)
	_____   = sum[i=1..n]( f_k(x_i, y_i) ) - sum[i=1..n]sum[y' in Y]( f_k(x_i, y') * p(y' | x_i, v) )
	d v_k                 |                                               |
	               Empirical counts                       Expected counts under current model

#### Exercise

Consider the label set `Y = {cat, dog}` and features

	f_1(x, y) = 1 if x=the and y ends with at
	            0 otherwise
	f_2(x, y) = 1 if x=the and y starts with c
	            0 otherwise
	f_3(x, y) = 1 if x=the and y has second letter o
	            0 otherwise

Say we are given training data `(x_1,y_1)=(the cat)` and `(x_2,y_2)=(the dog)`. If our current weight vector `v=(0 0 0)`, what is `dL(v)/dv_2`?

	f_2(the cat) = 1
	f_2(the dog) = 0

	p(cat | the, v) = e^0/(e^0 + e^0) = 0.5
	p(dog | the, v) = e^0/(e^0 + e^0) = 0.5

	dL(v)/dv_2 = sum[i=1..n]( f_2(x_i, y_i) ) - sum[i=1..n]sum[y' in Y]( f_2(x_i, y') * p(y' | x_i, v) )
	           = (f_2(the cat) + f_2(the dog)) - (
	               f_2(the cat) * p(cat | the,v) + f_2(the dog) * p(dog | the,v) +
	               f_2(the cat) * p(cat | the,v) + f_2(the dog) * p(dog | the,v)
	             ) 
	           = (1 + 0) - (1 * 0.5 + 0 * 0.5 + 1 * 0.5 * 0 * 0.5)
	           = 0

### Regularization

Say we have a feature:

	f_100(x, y) = 1 if current word w_i is base and y = Vt
	            = 0 otherwise

In training data, we see `base` 3 times, tagged as `Vt` every time. Maximum likelihood should therefore cause `p(Vt | x_i, v) = 1` for any input where `w_i = base`. (Note that for this to happen, v_100 -> infinity.) This seems unduly confident based on only 3 training examples. 

We therefore apply *regularizaton* to the optimization:

	L(v)       = <as before> - (lambda / 2) sum[k=1..m]( v_k^2 )
	dL(v)/dv_k = <as before> - lambda * v_k

This has the effect of penalising large values of `v_k` and in general significantly improves the generalization of the resulting model.

#### Exercise

Given the same label set, features and training data as the previous exercise, but with `v=(1 1 1)` and `L(v)` now defined to use regularisation with `lambda=1`, what is the value of `dL(v)/dv_2`?

	v f(the cat) = (1 1 1) * (1 1 0) = 2
	v f(the dog) = (1 1 1) * (0 0 1) = 1

	p(cat | the, v) = e^2/(e^2 + e^1) = 0.731
	p(dog | the, v) = e^1/(e^2 + e^1) = 0.269

	dL(v)/dv_2 = (f_2(the cat) + f_2(the dog)) - (
	               f_2(the cat) * p(cat | the,v) + f_2(the dog) * p(dog | the,v) +
	               f_2(the cat) * p(cat | the,v) + f_2(the dog) * p(dog | the,v)
	             ) - lambda * v_2
	            = (1 + 0) - (1 * 0.731 + 0 * 0.269 + 1 * 0.731 + 0 * 0.269) - 1 * 1
	            = -1.462