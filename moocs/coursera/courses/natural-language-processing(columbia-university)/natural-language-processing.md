Natural Language Processing (NLP)
=================================

https://coursera.org/course/nlangp

Week 1
======

Introduction
============
Computer using natural language as input/output
Natural Language Understanding (NLU)
Natural Language Generation (NLG)

Application:
a. Machine Translation

b. Information Extraction
Goal: Map a document collection to structured database
Motivation:
i. Complex searches
ii. Statistical queries

c. Text Summarization

d. Dialogue Systems (Bots)

e. Basic NLP: Tagging problem
Goal: Strings to Tagged Sequences
i. Part of Speech tagging
ii. Name Entity Recognition

f. Basic NLP: Parsing
Goal: String to Parse tree

Why is NLP Hard?
Ambiquity problem.
i. At text level
ii. At acoustic level
iii. At syntactic level
iv. At semantic level (more than one meaning of a word)
v. At discourse level (Multi-clause level)

Course Coverage:
a. NLP sub-problems
b. Machine learning techniques
c. Applications

The Language Modeling Problem
=============================
Problem Statement:
We have some (finite) vocabulary
say V = {the, a, man..}

We have an (infinite) set of strings V'
the STOP
a STOP
STOP
the fan STOP

We have a training sample of example sentences in English
and we need to "learn" a probability distribution p, i.e p is a function that satisfies
Sigma p(x) = 1, p(x) >= 0 for all x belongs to V'

A good probability distribution model will be assigning good sentences with high probability.

Motivation:
Speech Recognition
Machine Translation

A Naive method:
We have N training sentences.
For any sentence x1, x2, ... xn, c(x1, x2, ... xn) is the number of times the sentence is seen in our training data.
A naive estimate:
p(x1, x2, ... xn) = c(x1, x2, ... xn)/N

Problem: Unable to generalise to new sentences.

Markov Processes:
Consider a sequence of random variables X1, X2, ... Xn
Each random variable can take any value in a finite set V
Our goal: model
P(X1 = x1, X2 = x2, ... Xn = xn)

First order Markov Processes:
P(X1 = x1, X2 = x2, ... Xn = xn) = P(X1 = x1) Product of i = 2 to N [P(Xi = xi|X1 = x1 ... Xi-1 = xi-1)]
Same as 
P(A, B, C) = P(A) * P(B|A) * P(C|A, B)

The first-order Markov assumption:
P(Xi = xi|X1 = x1 ... Xi-1) = P(Xi = xi|Xi-1 = xi-1)

Second-order Markov assumption:
P(X1 = x1, X2 = x2 ... Xn = xn) = P(X1 = x1) * P(X2 = x2|X1 = x1) * Product of i = 3 to n [P(Xi = xi|Xi-2 = xi-2, Xi-1 = xi-1)]

Modeling Variable Length Sequences:
Xn = STOP where STOP is a special symbol

Trigram Language Models:
A trigram language model consists of
a. A finite set V of vocabulary
b. A parameter q(w|u, v) for each trigram u, v, w such that w belongs to V U {STOP}

For any sentence x1 ... xn where xi belongs to V for i = 1 ... (n-1), and xn = STOP,
the probability of the sentence under the language model is
P(x1 ... xn) = Product of i = 1 to n [q(xi|xi-2, xi-1)]

For example:
Sentence - the dog barks STOP
p(the dog barks STOP) = q(the|*, *) x q(dog|*, the) x q(barks|the, dog) x q(STOP|dog, barks)

The Trigram Estimation Problem:
How to find P(wi|wi-2, wi-1)

Maximum Likelihood Estimate:
q(wi|wi-2, wi-1) = count(wi-2, wi-1, wi)/count(wi-2, wi-1)

q(laughs|the, dog) = Count(the, dog, laughs)/Count(the, dog)

Problem: Sparse data problem, count will turn out to be 0 in many cases.

Evaluating a Language Model: Perplexity:
We have some test data, m sentences
s1, s2 ... sm

We could look at the probability under our model Pie[p(si)], or more conveniently, the log probability (converting multiplication to addition)
log(Pie[p(si)]) = Sigma(log(p(si)))

l = Sigma(log(p(si)))/M where M is the total number of words in the test data.

Perplexity: 2^-l
Lower the perplexity, better the language model.

A trigram model: Perplexity = 74
A bigram model: Perplexity = 137
A unigram model: Perplexity = 955

Parameter Estimation in Language Models
=======================================
Problem in trigram problem:
Sparse Data Problem

Maximum likelihood estimate doesn't work good in Sparse Data Problem.

The Bias-Variance Trade off:

Trigram maximum likelihood estimate:
q(wi|wi-2, wi-1) = Count(wi-2, wi-1, wi)/Count(wi-2, wi-1) [Trigram Count/Bigram Count]

Bigram maximum likelihood estimate:
q(wi|wi-1) = Count(wi-1, wi)/Count(wi)

Unigram maximum likelihood estimate:
q(wi) = Count(wi)/Count() total number of words

Linear Interpolation:
q(wi|wi-2, wi-1) = lambda1 * q(wi|wi-2, wi-1)
				+  lambda2 * q(wi|wi-1)
				+  lambda3 * q(wi)

where lambda1 + lambda2 + lambda3 = 1

How to estimate the lambda values?
Hold out part of training set as "validation" data.
Define c'(w1, w2, w3) to be the number of times the trigram (w1, w2, w3) is seen in validation set.

Choose lambda1, lambda2, lambda3 to maximize
L(lambda1, lambda2, lambda3) = sigma(c'(w1, w2, w3) log q(w3|w1, w2))
such that lambda1 + lambda2 + lambda3 = 1 and lambdai >= 0

Allowing the lambda's to vary:
Take a function Pie that partitions histories
e.g Pie(wi-2, wi-1) = 1 if Count(wi-1, wi-2) = 0
					  2 if 1 <= Count(wi-1, wi-2) <= 2
					  3 if 3 <= Count(wi-1, wi-2) <= 5
					  4 otherwise

q(wi|wi-2, wi-1) = lambda1^Pie(wi-2, wi-1) * q(wi|wi-2, wi-1)
				+  lambda2^Pie(wi-2, wi-1) * q(wi|wi-1)
				+  lambda3^Pie(wi-2, wi-1) * q(wi)

Discounting methods:
Count*(x) = Count(x) - 0.5

Using this new Count*(x) in trigram/bigram model leads to residual missing probability mass.
Distributing this residual missing probability mass amoung the examples having 0 count in training dataset.

Katz Back-Off Models (Bigram):
For a bigram model, define two sets
A(wi-1) = {w: Count(wi-1, w) > 0}
B(wi-1) = {w: Count(wi-1, w) = 0}

A Bigram model:
q(wi, wi-1) = Count*(wi-1, wi)/Count(wi-1) if wi belongs to A(wi-1)
			  alpha(wi-1) * q(wi)/q(w) if wi belongs to B(wi-1)

where alpha(wi-1) = 1 - sigma(Count*(wi-1, w)/Count(wi-1))

Same goes for Katz Back-Off Models (Trigram)

Three steps in deriving the language model probabilites:
1. Expand p(w1, w2, ... wn) using Chain rule.
2. Make Markov Independence Assumptions
   p(wi|w1, w2, ... wi-1) = p(wi|wi-2, wi-1)
3. Smooth the estimates using low order counts.

Other method used to improve language models:
a. "Topic" or "long-range" features.
b. Syntactic models.

=====================================================================================================================

Week 2
======

Tagging Problems, and Hidden Markov Models
==========================================

Part of Speech Tagging:
Input: A sentence, sequence of words
Output: Tagged sequence, where each word is tagged with part of speech.

Named Entity Recognition:
Input: A sentence, sequence of words
Output: Tagged sequence, where entities are tagged, such as name, place, and company.

Training dataset:
Wallstreet journal

Two types of Constraints:
a. Local
b. Contextual

Supervised Learning Problem:
Input: We have training examples x(i)
Output: y(i) is a label

Task is to learn a function f mapping input x to labels f(x)

Conditional Models:
a. Learn a distribution p(y|x) from training examples.
b. For any test input x, define f(x) = arg max p(y|x)

Generative Models:
a. Learn a distribution p(x, y) from training examples.
b. Often we have p(x, y) = p(y) * p(x|y)

Output from the model:
f(x) = arg max p(y|x)
	 = arg max p(y)p(x|y)/p(x)
	 = arg max p(y)p(x|y)

Hidden Markov Models:
We have an input sequence x = x1, x2, ... xn
We have a tag sequence y = y1, y2, ... yn
We'll use an HMM to define
p(x1, x2, ... xn, y1, y2, ... yn)

Then the most likely tag sequence for x is
arg max p(x1, x2, ... xn, y1, y2, ... yn)

Trigram Hidden Markov Models:
For any sentence x1, x2, ... xn where xi belongs to V
Any tag sequence y, y2, ... yn+1 where yi belongs to S and yn+1 = STOP
The joint probability of the sentence and tag sequence is
p(x1, x2, ... xn, y1, y2, ... yn+1) = Pie q(yi|yi-2, yi-1) * Pie e(xi|yi)

Parameters of the model are
a. q(s|u, v) - Trigram
b. e(x|s) - Emission

Parameter Estimation in HMM:
Linear Interpolation.

Dealing with Low-Frequency Words:
Step1: Split the vocabulary into two sets
Frequent words = words occuring >= 5 times in training
Low frequency words = all other words
Step2: Map low frequency words into a small, finite set, depending on prefixes, suffixes etc.

The Viterbi Algorithm:
Problem: for an input x1, x2, ... xn, find
arg max p(x1, x2, ... xn, y1, y2, ... yn+1) where p(x1, ... xn, y1, ... yn+1) = Pie q(yi|yi-2, yi-1) * Pie e(xi|yi)

Define n to be the length of the sequence.
Define Sk for k = -1 ... n to be possible tags at position k:
S-1 = S0 = {*}
Sk = S for k belongs to 1 to n

Define r(y-1, y0, y1 ... yk) = Pie(q(yi|yi-2, yi-1)) * Pie(e(xi|yi))

Define a dynmaic programming table
Table(k, u, v) = maximum probability of a tag sequence ending in tags u, v at position k
that is
Table(k, u, v) = max(r(y-1, y0, y1 ... yk))

A Recursive definition:
Base case: Pie(0, *, *) = 1
Recursive:
For any k belongs to {1 ... n}, for any u belongs to Sk-1 and v belongs to Sk:
Table(k, u, v) = max(Table(k-1, w, u) * q(v|w, u) * e(xk|v)) where w belongs to Sk-2

Bottom-up algorithm:
For k = 1 ... n
do For u belongs to Sk-1, v belongs to Sk
	do Table(k, u, v) = max(Table(k-1, w, u) * q(v|w, u) * e(xk|v)) where w belongs to Sk-2
Return max(Table(n, u, v) * q(STOP|u, v))

In order to generate the tag label,
Storing arg max in backpointers.
bp(k, u, v) = arg max(Table(k-1, w, u) * q(v|w, u) * e(xk|v))

Set (yn-1, yn) = arg max(Table(n, u, v) * q(STOP|u, v))

Top-down algorithm:
For k = (n-2) to 1
do yk = bp(k+2, yk+1, yk+2)
return the tag sequence y1 ... yn

Running time O(n|S|^3)

===========================================================================================================

Week 3
======

Parsing, and Context-Free Grammars
==================================

Parsing:
Input: A sentence
Output: A parse tree

Supervised learning

Dataset:
Penn WSJ Treebank - 50k with associated trees

Information conveyed by Parse Trees:
a. Level 1 - Part of speech for each word
b. Level 2 - Phrases
c. Useful Relationships

Application:
Machine Translation

Since every language has different orientation of parse tree, machine translation can be done easily rotating the parse tree.

Context-Free Grammars:
A context free grammar G = (N, Sigma, R, S) where:
a. N is a set of non-terminal symbols.
b. Sigma is a set of terminal symbols.
c. R is a set of rules of the form X -> Y1, Y2 ... Yn for n >= 0 where X belongs to N and Yi belongs to (N U Sigma)
d. S belongs to N is a distinguished start symbol.

For example:
A context free Grammar for English G = (N, Sigma, R, S) where
N = {S, NP, VP, PP, DT, Vi, Vt, NN, IN}
Sigma = {sleeps, saw, man, woman, telescope, the, with, in}
R = S -> NP VP
	VP -> Vi
	VP -> Vt NP
	...
	Vi -> sleeps
	Vt -> saw
	NN -> man
	...
S = S

Left-Most Derivations:
A left-most derivation is a sequence of strings s1 ... sn, where
a. s1 = S, the start symbol
b. sn belongs to Sigma*, i.e sn is made up of terminal symbols only
c. Each si for i = 2 ... n is derived from si-1 by picking the left-most non-terminal X in si-1 and replacing it by some Beta,
where X -> Beta is a rule in R.

For example
Derivation  	Rules used
S  				S -> NP VP
NP VP  			NP -> DT N
DT N VP  		DT -> the
the N VP		N -> Dog
the Dog VP 		VP -> VB
the Dog VB 		VB -> laughs
the Dog laughs

Properties of CFGs:
a. A CFG defines a set of possible derivations.
b. A string s belongs to Sigma* is in the language defined by the CFG if there is at least one derivation that yields s.
c. Each string in the language generated by the CFG may have more than one derivation ("ambiquity").

The problem with Parsing: Ambiquity

A simple grammar for English:
A fragment of a Noun Phrase Grammar:
N' => NN
N' => NN N'
N' => JJ N'
N' => N' N'
NP => DT N'

NN => box
NN => car
NN => mechanic
NN => pigeon
DT => the
DT => a

JJ => fast
JJ => metal
JJ => idealistic
JJ => clay

A sample Noun Phrase
NP
DT N'
the NN
the cat

Prepositional, and Prepositional Phrases:
IN = Prepositional

PP => IN NP

IN => in
IN => under
IN => of

Verbs, Verb Phrases, and Sentences:
Vi = Intransitive verb
Vt = Transitive verb
Vd = Ditransitive verb

VP => Vi
VP => Vt NP
VP => Vd NP NP

S => NP VP

Complementizers, and SBARs:
COMP => that
SBAR => COMP S

In short, Parse tree is formed by using various rules in CFG.
Lot of overlapping and recursive rules in CFG results in ambiquity.

Probabilistic Context-Free Grammar (PCFG)
=========================================

A CFG with probabilites on each rules in CFG.
All rules with same left hand side(non-terminal) sum to 1.

Probability of a tree t with rules
alpha1 => beta1, alpha2 => beta2 ... alpha n => beta n
is p(t) = Pie q(alpha i => beta i) where Pie is product of i = 1 to n

We have a sentence s, set of derivations(Parse Trees) for that sentence is T(s).
Then a PCFG assigns a probability p(t) to each member of T(s), i.e we have a ranking in order of probability.
Thus, helps in solving ambiquity problem.

Then, the most likely parse tree for a sentence s is
arg max p(t) where t belongs to T(s)

Learn a PCFG from data:
Penn WSJ Treebanks = 50,000 sentences with associated trees.

Deriving a PCFG from a Treebank:
Given a set of example trees(a treebank), the underlying CFG can be simply be all rules seen in the corpus.
Maximum Likelihood Estimate:
q(alpha => beta) = count(alpha => beta)/count(alpha) where count are taken from a training set of example trees.

The CKY Parsing Algorithm:
Parsing with a PCFG.
Given a PCFG and a sentence s, define T(s) to be set of trees with s as the yield.
Given a PCFG and a sentence s, find
arg max p(t) where t belongs to T(s)

Chomsky Normal Form:
A Context Free Grammar G = (N, Sigma, R, S) in Chomsky Normal form is as follows:
a. N is a set of non-terminal symbols.
b. Sigma is a set of terminal symbols.
c. R is a set of rules which takes one of the two forms:
X => Y1Y2 for X belongs to N, and Y1, Y2 belongs to N
X => Y for X belongs to N, and Y belongs to Sigma
d. S belongs to N is a distinguished start symbol.

Convert PCFG to Chomsky Normal PCFG.

A Dynamic Programming Algorithm:
Given a Chomsky Normal PCFG and a sentence s, find
max p(t) where t belongs to T(s)

Notation:
n = number of words in the sentence
wi = ith word in the sentence
N = the set of non-terminals in the grammar
S = the start symbol in the grammar

Define a dynamic programming table
table[i, j, X] = maximum probability of a constituent with non-terminal X spanning words i ... j inclusive.

Our goal is to calculate max p(t) = table[1, n, S]

Base case:
for all i = 1 ... n, for X belongs to N
	do table[i, i, X] = q(X => wi) where q(X => wi) = 0 if it is not in the grammar.

Recursive definition:
for all i = 1 ... n-1, j = (i+1) ... n, X belongs to N
	do table(i, j, X) = max(q(X => YZ) * table(i, s, Y) * table(s+1, j, Z)) where X => Y Z belongs to R, s belongs to {i ... (j-1)}

The full dynamic programming algorithm:
Input: a sentence s = x1 ... xn, a PCFG G = (N, Sigma, S, R, q)

Initialisation:
For all i belongs to 1 ... n, for all X belongs to N,
	do table(i, i, X) = q(X => xi) if X => xi belongs to R
						0 otherwise

Bottom-up algorithm:
For l = 1 ... (n-1)
	do for i = 1 ... (n - l)
		do set j = i + l
		for all X belongs to N, calculate
			do table(i, j, X) = max (q(X => Y Z) * table(i, s, Y) * table(s+1, j, Z))
			and bp(i, j, X) = arg max  (q(X => Y Z) * table(i, s, Y) * table(s+1, j, Z))

Running time O(n^3|N|^3)

To build a PCFG-parsed parser:
a. Learn a PCFG from a treebank.
b. Given a test data sentence, use the CKY algorithm to compute the highest probability tree for the sentence under the PCFG.

==============================================================================================================================

Week 4
======

Weaknesses of PCFG
==================

Lack of sensitivity to lexical information.
Lack of sensitivity to structural frequencies.

First treebank in early 1990s
PCFG => 72% accuracy.
Modern Parser => 92% accuracy.

PCFG consider independence assumption of words.

Problems in PCFG:
a. PP attachment
b. Close attachments

Lexicalized PCFGs
=================

Lexicalization of a treebank:
Add annotations specifying the "head" of each rule.
For e.g
VP => Vt(Head) NP

Recover heads using various rules.
Such as if the rule contains NN, NNS, or NNP:
Choose the rightmost NN, NNS, or NNP.
etc.

Adding Headwords to Trees:
Propogate the headword upwards using "head" of each rule.

Lexicalized PCFG:
Lexicalized CFG in Chomsky Normal Form G = (N, Sigma, R, S) where
N is a set of non-terminal symbols.
Sigma is a set of terminal symbols.
R is a set of rules which takes one of three forms:
	a. X(h) => Y1(h) Y2(w) for X belongs to N, and Y1, Y2 belongs to N, and h, w belongs to Sigma
	b. X(h) => Y1(w) Y2(h) for X belongs to N, and Y1, Y2 belongs to N, and h, w belongs to Sigma
	c. X(h) => h for X belongs to N, and h belongs to Sigma
S belongs to N is a distinguished start symbol.

Parameters in a Lexicalized PCFG:
An example parameter in a PCFG
q(S => NP VP)
An example parameter in a Lexicalized PCFG:
q(s(saw) => NP(man) VP(saw))

Parsing with Lexicalized PCFG:
Naively, parsing an n word sentence using the dynamic programming algorithm will take O(n^3 |Sigma|^3 |N|^3).
But, Sigma can be very large.
At most O(n^2 |N|^3) rules can be applicable to a given sentence w1, ... wn of length n.
This is because any rules which contain a lexical item that is not one of w1, ... wn can be safely discarded.
We can parse in O(n^5 |N|^3) time.

Parameter estimation in Lexicalized PCFG:
An example parameter in a Lexicalized PCFG
q(S(saw) => NP(man) VP(saw))

First step: decompose this parameter into the product of two parameters
q(S(saw) => NP(man) VP(saw)) = q(S => NP VP|S, saw) * q(man|S => NP VP, saw)

Second step: use smoothed estimation (Linear Interpolation) for the two parameter estimates
q(S => NP VP|S, saw) = lambda1 * q(S => NP VP|S, saw) + lambda2 * q(S => NP VP|S)
where
q(S => NP VP|S, saw) = Count(S(saw) => NP VP)/Count(S(saw))
q(S => NP VP|S) = Count(S => NP VP)/Count(S)

q(man|S => NP VP, saw) = lambda3 * q(man|S => NP VP, saw) + lambda4 * q(man|S => NP VP) + lambda5 * q(man|NP)

Accuracy of Lexicalized PCFG:
Reprensenting Trees as Constituents. It will be in form of Label Start-Point End-Point
For e.g NP 1 2
NP 4 5

Using Precision and Recall for accuracy:
Calculating Constituents for Gold standard(Tree hand annotated) and Parser Output.
G = number of constituents in gold standard
P = number of parse in output
C = number correct

Recall = C/G * 100 %
Precision = C/P * 100 %

Results:
Training data: 40k sentences from the Penn Wall Street Journal treebank.
Testing data: Around 2400 sentences from the Penn Wall Street Journal treebank.

PCFG: 70.6% Recall, 74.8% Precision
Magerman: 84% Recall, 84.3% Precision
Lexicalised PCFG: 88.1% Recall, 88.3% Precision

More recent works: More than 90%

Other type of Evaluation:
Representing Trees as dependencies. It will be in form of H W Rule
For e.g saw3 man2 S => NP VP
man2 the1 NP => DT NN
where number after the word symbolize the position of the word in the sentence.

All parses for a sentence with n words have n dependencies.
Report a single figure, dependency accuracy.
And also, can calculate precision/recall on particular dependency types.
Such as subject/verb dependecies (s-v).

Recall = # of s-v correct/# of s-v in gold standard
Precision = # of s-v correct/# of s-v in parser's output

Strengths and weaknesses of Modern Parser:
Subject-verb pairs: over 95% recall and precision
PP attachments: 82% recall and precision
Coordination attachments: 61% recall and precision

Problems due to lack of knowledge about real world.

Key Weakness of PCFG:
lack of sensitivity to lexical information.
Hence, Lexicalized PCFG was an addon to normal PCFG.

================================================================================================================

Week 5
======

Introduction to Machine Translation (MT)
========================================

Challenges in MT:
a. Lexical Ambiquity
For e.g
book the flight => reservar
read the book => libro

b. Differing Word Orders
English word order: subject - verb - object
Japanese word order: subject - object - verb

c. Syntactic structure is not preserved across different languages

d. Syntactic ambiquity causes problems.

e. Pronoun resolution.

Classical Machine Translation:
Rule based methods.

Direct Machine Translation:
Translation is word by word.
Very little analysis of the source text.
Relies on a large bilingual dictionary. For each word in the source language, the dictionary specifies a set of rules for translating the word.
After the words are translated, simple reordering rules are applied.

Problems with Direct Machine Translation:
Lack of any analysis of the source language causes several problems.
a. Difficult or impossible to capture long-range reorderings.
b. Words are translated without disambiguation of their syntactic role.

Transfer based approaches:
Three phases in translation
a. Analysis - Analyse the source language sentence, for e.g build a parse a tree.
b. Transfer - Convert the source language parse tree to a target language parse tree.
c. Generation - Convert the target language parse tree to an output sentence.

Disadvantages:
Needed O(n^2) models.

Interlingua-Based Translation:
Two phases in translation:
a. Analysis - Analyze the source language sentence into a (language-independent) representation of its meaning.
b. Generation - Convert the meaning representation into an output sentence.

Advantages:
Needed only n analysis components and n generation components.

Disadvantages:
How to represent different concepts in an interlingua?
Different languages break down concepts in quite different ways.
An interlingua might end up simple being an intersection of these different ways of breaking down concepts.

A brief introduction to statistical MT:
Parallel corpora are available in several language pairs.
Using parallel corpora as a training set of translation examples.
Converting the problem to supervised learning problem.
Dataset: IBM work on French-English translation, using the Canadian Hansards.

The Noisy Channel Model:
Goal: translation system from French to English
Have a model p(e|f) which estimates conditional probability of any English sentence e given the french sentence f.
Use training corpus to set the parameters.
A noisy channel model has two components:
p(e) the language model
p(f|e) the translation model

Giving:
p(e|f) = p(e, f)/p(f) = p(e) * p(f|e)/Sigma p(e) * p(f|e)
and
arg max p(e|f) = arg max p(e) * p(f|e)

The language model p(e) could be a trigram model, estimated from any data.
The translation model p(f|e) is trained from a parallel corpus of French/English pairs.

The IBM Translations Models
===========================

IBM Model 1: Alignments:
How do we model p(f|e)

English sentence e has l words e1 ... el
French sentence f has m words f1 ... fm

An alignments a identifies which English word each French word originated from.
Formally, an alignment a is {a1 ... am}
There are (l+1)^m possible alignments.

We'll define models for p(a|e, m) and p(f|a, e, m)
giving p(f, a|e, m) = p(a|e, m) p(f|a, e, m)

Also,
p(f|e, m) = Sigma p(a|e, m) p(f|a, e, m) for a belongs to A
where A is the set of all possible alignments.

We also calculate
p(a|f, e, m) = p(f, a|e, m)/Sigma p(f, a|e, m) for all alignment a

For a given f, e pair, we can also compute the most likely alignment
a* = arg max p(a|f, e, m)

The original IBM models are rarely used for translation, but they are used for recovering alignments.

In IBM model 1 all alignments a are equally likely:
p(a|e, m) = 1/(l+1)^m

p(f|a, e, m) = Pie t(fj|eaj) for j = 1 to m

IBM Model 1: The Generative Process
To generate a French string f from an English string e:
Step 1: Pick an alignment a with probability 1/(l+1)^m
Step 2: Pick the French words with probability p(f|a, e, m) = Pie t(fj|eaj)

The final result:
p(f, a|e, m) = p(a|e, m) p(f|a, e, m)

t(f|e) is trained using parallel corpus.

IBM Model 2:
Only difference: alignment or distortion parameters

q(i|j, l, m) = Probability that jth French word is connected to ith English word, given sentence lengths of e and f are l and m respectively.

Define:
p(a|e, m) = Pie q(aj|j, l, m) for j = 1 to m where a = {a1 ... am}

Gives:
p(f, a|e, m) = Pie q(aj|j, l, m) t(fj|eaj) for i = 1 to m

IBM Model 2: The Generative Process
To generate a French string f from an English string e:
Step 1: Pick an alignment a with probability Pie q(aj|j, l, m)
Step 2: Pick the French words with probability p(f|a, e, m) = Pie t(fj|eaj)

The final result:
p(f, a|e, m) = p(a|e, m) p(f|a, e, m)

Recovering Alignments:
If we have parameters q and t, we can easily recover the most likely alignment for any sentence pair
Given a sentence pair e1, e2, ... el, f1, f2 ... fm define
	aj = arg max q(a|j, l, m) t(fj|ea) for j = 1 to m

The EM Algorithm for IBM Model 2:
Input: (e(k), f(k)) for k = 1 to n, where e(k) is English sentence and f(k) is French sentence
Output: parameters t(f|e) and q(i|j, l, m)

A key challenge: We do not have alignments on our training examples.

Parameter Estimation if the Alignments are observed:
Training data: (e(k), f(k), a(k)) where e(k) is English sentence, f(k) is French sentence, and a(k) is the alignment
Maximum-likelihood parameter estimates in this case are trivial:
t(f|e) = Count(e, f)/Count(e)
q(j|i, l, m) = Count(j|i, l, m)/Count(i, l, m)


Pseudo code:
Input: A training corpus (f(k), e(k), a(k)) for k = 1 to n where f(k) = f1 ... fm, e(k) = e1 ... el, a(k) = a1 ... am

Algorithm:
Set all counts c(...) = 0
For k = 1 to n
	do for i = 1 to mk, for j = 0 to lk:
		do c(ej, fi) = c(ej, fi) + delta(k, i, j)
		c(ej) = c(ej) + delta(k, i, j)
		c(j|i, l, m) = c(j|i, l, m) + delta(k, i, j)
		c(i, l, m) = c(i, l, m) + delta(k, i, j)
	
	where delta(k, i, j) = 1 if ai = j, 0 otherwise

Output:
t(f|e) = c(e, f)/c(e)
q(j|i, l, m) = c(j|i, l, m)/c(i, l, m)

Paramter Estimation with the EM Algorithm:
Training examples: (e(k), f(k)) for k = 1 to n
The algorithm is related to algorithm when alignments are observed, but two key differences.
1. The algorithm is iterative. We start with some initial choice for the q and t parameters. At each iteration we compute some "counts" based on the data together with our current parameter estimates. We then re-estimate our parameters with these counts, and iterate.
2. delta(k, i, j) = q(j|i, lk, mk) t(fi|ej) / Sigma q(j|i, lk, mk) t(fi|ej)

Iterate 10 to 20 times.

delta(k, i, j) = P(ai = j|e(k), f(k), t, q)

Justification of the Algorithm:
The log likelihood function:
L(t, q) = Sigma log p(f(k), e(k)) = Sigma log Sigma p(f(k), a|e(k))

The maximum likelihood estimates are
arg max L(t, q)

The EM algorithm will converge to a local maximum of the log-likelihood function.

=========================================================================================================================

Week 6
======

Phrase-based Translation Models
===============================

2nd generation Statistical Machine Translation system.

First stage in training a phrase-based model is extraction of a phrase-based (PB) lexicon.
A PB lexicon pairs strings in one language with strings in other language.
For e.g nach Kanada - in Canada

Finding PB lexicon using parallel corpus.
Using alignments from the IBM Model 2.

Representation as Alignment Matrix:
In IBM Model 2, each foreign word is aligned to exactly one English word.

Problems in IBM Model 2:
a. Alignments are often noisy.
b. Many to one alignments.

Finding Alignment Matrices:
Step 1: train IBM model 2 for p(f|e), and come up with most likely alignment for each (e, f) pair.
Step 2: train IBM model 2 for p(e|f), and come up with most likely alignment for each (e, f) pair.

We now have two alignments:
take intersection of the two alignments as a starting point, and using various heuristics to grow Alignments.

Extracting Phrase pairs from the Alignment Matrix:
A phrase-pair consists of a sequence of English words, e, paired with a sequence of foreign words, f.
A phrase-pair (e, f) is consistent if
a. there is at least one word in e aligned to a word f.
b. there is no words in f aligned to words outside e
c. there is no words in e aligned to words outside f

Extract all consistent phrase pairs from the training examples.

Probabilies for Phrase Pairs:
For any phrase pair (f, e) extracted from the training data, we can calculate
t(f|e) = Count(f, e)/Count(e)

Phrase based models: A sketch
Score = Language Model + Phrase model + Distortion model
Language Model: log q(Today|*, *)
Phrase Model: log t(Heute|Today)
Distortion Model: n(Eta) * 0

Decoding of Phrase-Based Translation Models
===========================================

A phrase-based model consists of:
a. A phrase-based lexicon, consisting of entries (f, e) such as
(wir mussen, we must)
Each lexical entry has a score g(f, e), such as
g(wir mussen, we must) = log(Count(wir mussen, we must) / Count(we must))

b. A trigram language model with parameters q(w|u, v), such as
q(also|we, must)

c. A "distortion parameter" n (typically negative).

For a particular input (source-language) sentence x1 ... xn, a phrase is a tuple (s, t, e), where e = xs ... xt
For e.g (1, 2, we must)
P is the set of all phrases for sentence.
For any phrase p, s(p), t(p), and e(p) are its three components.
g(p) is the score of the phrase.

A derivation y is a finite sequence of phrases, p1 ... pL, where each pj for j belongs to {1 ... L} is a member of P.
For any derivation y we use e(y) to refer to the underlying translation defined by y.
y = (1, 3, we must also), (7, 7, take), (4, 5, this criticism), (6, 6, seriously)
and e(y) = we must also take this criticism seriously

Valid Derivations:
For an input sentence x = x1 ... xn, we use y(x) to refer to the set of valid derivations for x.
y(x) is the set of all finite length sequences of phrases p1 p2 ... pL such that:
a. Each pk for k belongs to {1 ... L} is a member of the set of phrases P for x1 ... xn.
b. Each word in x is translated exactly once.
c. For all k belongs to {1 ... (L-1)}, |t(pk) + 1 - s(pk+1)| <= d where d >= 0 is a parameter of the model. In addition, we must have |1 - s(p1)| <= d

Scoring Derivations:
The optimal translation under the model for a source-language sentence x will be
arg max f(y)

In phrase-based systems, the score for any derivation y is calculated as follows:
f(y) = h(e(y)) + Sigma g(pk) + Sigma eta * |t(pk) + 1 - s(pk+1)|
where h(e(y)) is trigram language model
g(pk) is phrase-based score for pk
and eta is distortion penalty.

Finding arg max f(y) is NP-hard problem. Using Beam Search to solve this problem.

Decoding Algorithm:

A state is a tuple (e1, e2, b, r, alpha)
where e1, e2 are English words, b is a bit-string of length n, r is an integer specifying the end-point of the last phrase in the state, and the alpha is the score for the state.

The initial state is
q0 = (*, *, 0^n, 0, 0)

States, and the Search Space
(*, *, 0000000, 0, 0) -> (we, must, 1100000, 2, -1.5) Using (1, 2, we must)
and so on

Translation problem is converted to the problem of searching the state graph to the final state having highest score.

Transitions:
We have ph(q) for any state q, which returns set of phrases that are allowed to follow state q = (e1, e2, b, r, alpha)
For a phrase p to be a member of ph(q), it must satisfy
a. p must not overlap with the bit string b, we need bi = 0 for i belongs to s(p) ... t(p)
b. The distortion limit must not be violated. |r + 1 - s(p)| <= d

In addition, we define next(p, q) to be the state formed by combining state q with state p.

The next function:
Formally, if q = (e1, e2, b, r, alpha) and p = (s, t, epsilon1 ... epsilon_m), then
next(q, p) is the state q' = (e1', e2', b', r', alpha') defines as follows:
a. First, for convenience, define e-1 = e1, e0 = e2
b. Define e1' = epsilon_m-1, e2' = epsilon_m
c. Define bi' = 1 for i belongs to {s ... t}. Define bi' = bi for i doesn't belongs to {s ... t}
d. Define r' = t
e. Define alpha' = alpha + g(p) + Sigma log q(epsilon_i|epsilon_i-2, epsilon_i-1) + eta * |r + 1 - s|

The Equality function:
The function eq(q, q') returns true or false
Assuming q = (e1, e2, b, r, alpha), and q' = (e1', e2', b', r', alpha'),
eq(q, q') is true if and only if e1 = e1', e2 = e2', b = b', r = r'

The Decoding Algorithm:
Inputs: sentence x1 ... xn. Phrase-based model (L, h, d, eta)
The phrase-based model defines the functions ph(q) and next(q, p)
Initialisation: set Q0 = {q0}, Qi = Null for i = 1 to n
For i = 0 to n-1
do for each state q belongs to beam(Qi), for each phrase p belongs to ph(q)
	1. q' = next(q, p)
	2. Add(Qi, q', q, p) where i = len(q')
Return highest scoring state in Qn. Backpointers can be used to find the underlying sequence of phrases(and the translation)

Definition of Add(Q, q', q, p)
if there is some q'' belongs to Q such that eq(q'', q') = true
	if alpha(q') > alpha(q'')
		Q = {q'} U Q \ {q''}
		set bp(q') = (q, p)
	else
		return
else
	Q = Q U {q'}
	set bp(q') = (q, p)

Definition of beam(Q)
Define alpha* = max alpha(q)
i.e alpha* is the highest score for any state in Q.

Define beta >= 0 to be beam width parameter,
then, beam(Q) = {q belongs to Q: alpha(q) >= alpha* - beta}

==================================================================================================================================

Week 7
======

Log-Linear Models
=================

Application of Log-Linear Models:

1. The Language Modeling Problem
   wi is the ith word in a document
   Estimate a distribution p(wi|w1, w2, ... wi-1) given previous "history" w1 ... wi-1

For e.g
The meaning of life is wi
Here wi is filled using distribution from language model.

a. Trigram Estimate
Cons:
Makes use of only bigram, trigram, unigram estimates
Many other "features" of w1 ... wi-1 may be useful.

2. Part-of-Speech Tagging
   Input: Sequence of Words
   Output: Sequence of tags from Part-of-Speech

The task:
p(ti|t1 ... ti-1, w1 ... wn)
Again, many other "features" may be useful.

The General Problem:
We have some input domain X
Have a finite label set Y
Aim is to provide a conditional probability p(y|x) for any x, y where x belongs to X, and y belongs to Y

Language Modeling Problem
p(wi|w1 ... wi-1)

Feature Vector Representations:
A feature is a function fk(x, y) belongs to R
(Often binary features or indicator functions)
fk(x, y) belongs to {0, 1}

Say we have m features fk for k = 1 to m
For e.g
Trigram feature
f(x, y) = {1 if y = model, wi-2 = any, wi-1 = statistical
		   0 otherwise}

In practice, we would probably introduce one trigram feature for every trigram seen in the training data
fN(u, v, w)(x, y) = {1 if y = w, wi-2 = u, wi-1 = v
					 0 otherwise}

and N(u, v, w) is a function that maps each (u, v, w) trigram to a different integer.

The POS-tagging example:
Each x is a "history" of the form <t1, ... ti-1, w1, ... wn, i>
Each y is a POS tag, such as NN, NNS, Vt, Vi, ...
We have m features fk(x, y) for k = 1 to m

Word/tag features - wi is base and y = Vt
Spelling features - wi ends in ing and y = VBG
Contextual features - if <ti-2, ti-1, y> = <DT, JJ, Vt>

The final result:
We can come up with practically any features regarding history/tag pairs.
For a given history x belongs to X, each label in Y is mapped to a different feature vector.
f(x, y) = 1000010001...
Sparse feature values

Parameters Vectors:
Given features fk(x, y) for k = 1 to m
also define a parameter vector v belongs to R^m
Each (x, y) pair is then mapped to a "score"
v.f(x, y) = Sigma vk fk(x, y)

Log-linear model (Using Softmax function):
p(y|x; v) = e^(v.f(x, y))/Sigma e^(v.f(x, y'))

Why the name log-linear model:
log p(y|x; v) = v.f(x, y) - log Sigma e^(v.f(x, y'))

Parameter estimation in log-linear models:

Maximum-Likelihood Estimation
Given training sample:
(xi, yi) for i = 1 to n, each (xi, yi) belongs to X, Y
v = arg max L(v)
where
L(v) = Sigma log(p(yi|xi; v)) = Sigma v.f(xi, yi) - Sigma log Sigma e^(v.f(xi, y'))

L(v) is a concave function; hence having same global and local optima. Any hill climbing function will work.

Gradient descent method to find the arg max L(v)
Initialisation: v = 0
Iterate until convergence:
Calculate delta = dL(v)/dv
Calculate Beta* = arg max L(v + Beta delta)
Set v <- v + beta delta

Conjugate Gradient Methods:
LBFGS

Smoothing-Regularization in log-linear models:
Due to same positive examples in training data, vk tends to go to infinity, which doesn't helps in making the model generalised.

Regularization:
a. Modified Loss function (-lambda/2 Sigma vk^2)
b. Run conjugate gradient method as before.
c. Adds a penalty for large weights.

Using log-linear model for trigram model.

====================================================================================================================

Week 8
======

Log-linear Models for Tagging (MEMMs) - Maximum-Entropy Markov Models
=====================================================================

Log-linear models are also known as Maximum-entropy models.

Part of Speech tagging:
Input: Sequence of words
Output: Sequence of part of speech tags

Name-Entity Recognition:
Input: Sequence of words
Output: Sequence of entity tags

Log-linear models for Tagging:
We have an input sentence w[1:n] = w1 ... wn
We have a tag sequence t[1:n] = t1 ... tn
We'll use an log linear model to define
p(t1 ... tn|w1 ... wn)
Then,
the most likely tag sequence for w[1:n] is
t*[1:n] = arg max p(t[1:n]|w[1:n])

How to model p(t[1:n]|w[1:n])?
A Trigram Log-linear models:
p(t[1:n]|w[1:n]) = Pie p(tj|w1 ... wn, t1 ... tj-1) // Chain rule
				 = Pie p(tj|w1 ... wn, tj-2, tj-1)  // Independence assumptions

Hence,
p(tj|w1 ... wn, t1 ... tj-1) = p(tj|w1 ... wn, tj-2, tj-1)

Representations:
A history is a 4-tuple <t-2, t-1, w[1:n], i>
i is the index of the word to be tagged
X is the set of all possible histories.

Feature Vector Representations:
We have some input domain X, and a finite label set Y.
Aim is to provide a conditional probability p(y|x) for any x belongs to X, and y belongs to Y

A feature is a function f: X * y -> R
Often binary features {0, 1}

Say we have m features fk for k = 1 to m
A feature vector f(x, y) belongs to R^m

For e.g
f1(h, t) = {1 if current word wi is base and t = Vt
			0 otherwise}

Word/tag features
Spelling features
Contextual features

We also have a parameter vector v belongs to R^m

p(y|x; v) = e^(v.f(x, y))/Sigma e^(v.f(x, y'))

To train a log-linear model, we need a training set (xi, yi) for i = 1 to n. Then search for
v* = arg max (Sigma log p(yi|xi; v)) - lambda/2 Sigma vk^2
Training set is all history/tag pairs seen in the training set.

The Viterbi Algorithm:
Problem: for an input w1 ... wn, find
arg max p(t1 ... tn|w1 ... wn)

We assume that p takes the form
p(t1 ... tn|w1 ... wn) = Pie q(ti|ti-2, ti-1, w[1:n], i)

Define n to be length of the sentence
Define
r(t1 ... tk) = Pie q(ti|ti-2, ti-1, w[1:n], i)

Define a dynamic programming table
table(k, u, v) = maximum probability of a tag sequence ending in tags u, v at position k
that is,
table(k, u, v) = max r(t1 ... tk-2, u, v)

A recursive definition:
Base case:
table(0, *, *) = 1

Recursive definition:
For any k belongs to {1 ... n} for any u belongs to Sk-1, and v belongs to Sk
table(k, u, v) = max (table(k-1, t, u) * q(v|t, u, w[1:n], k))
where Sk is the set of possible tags at position k

Input: a sentence w1 ... wn, log-linear model that provides q(v|t, u, w[1:n], i) for any tag-trigram t, u, v for any i belongs to {1 ... n}

Initialisation:
Set table(0, *, *) = 1

Algorithm:
for k = 1 to n
do for u belongs to Sk-1, v belongs to Sk
	do table(k, u, v) = max (table(k-1, t, u) * q(v|t, u, w[1:n], k))
	   bp(k, u, v) = arg max (table(k-1, t, u) * q(v|t, u, w[1:n], k)))

Set (tn-1, tn) = arg max(u, v) table(n, u, v)
for k = (n - 2) to n
do tk = bp(k+2, tk+1, tk+2)

return the tag sequence t1 to tn

Application: FAQ Segmentation

Line Features to use in Log-linear model

MEMM has precision of 0.867 and recall of 0.681

Log-linear Models for History-based Parsing
===========================================

How do we define p(T|S) if T is a parse tree (or another structure)
S = w[1:n]

A general approach:
Step1: represent a tree as a sequence of decisions d1 ... dm
T = <d1, d2 ... dm>

Step2: the probability of a tree is
p(T|S) = Pie p(di|d1 ... di-1, S)

Step3: Use a log-linear model to estimate
p(di|d1 ... di-1, S)

Step4: Search arg max p(T|S)

Represent a tree as a sequence of decisions:
Three layers of Structure
1. Part-of-Speech tags
2. Chunks
3. Remaining structure

Layer 1:
Part-of-Speech tags

First n decisions are tagging decisions.

Layer 2:
Chunks

Chunks are defined as any phrase where all children are part-of-speech tags.

Next n decisions are chunk tagging decisions.

Layer 3:
Remaining structure

Alternate between two classes of actions:
a. Join(X) or Start(X), where X is a label
b. Check = Yes or Check = No

Check = Yes takes previous Join or Start action, and converts it into a completed constituent.

The Final sequence of decisions:
<d1 ... dm> = < Level1 Level2 Level3 >

For e.g < DT NN Vt ...
		Start(NP) Join(NP) Other ...
		Start(S) Check = No Start(Vp) ... >

Step3: Use a log-linear model to estimate
p(di|d1 ... di-1, S)

p(di|d1 ... di-1, S) = e^(f(<d1 ... di-1, S>, di) * v) / Sigma e^(f(<d1 ... di-1, S>, d) * v)

<d1 ... di-1, S> is the history
di is the outcome
f maps a history/outcome pair to a feature vector
v is the parameter vector

f is defined differently depending on whether next decision is:
a. A tagging decision
b. A chunking decision
c. A start/join decision after chunking
d. A check = no / check = yes decision

Step4: The Search Problem - Beam search

=============================================================================================================

Week 9
======

Unsupervised Learning Brown Clustering
======================================

The Brown Clustering Algorithm:
Input: a (large) corpus of words
Output1: a partition of words into word clusters
Output2: (generalisation of 1): a heirarchical word clustering

The intuition:
Similar words appear in similar contexts.
More precisely: similar words have similar distributions of words to their immediate left and right.

The Formulation:
V is the set of all words seen in the corpus w1 ... wt
Say C: V -> {1 ... k} is a partition of the vocabulary into k classes.

The model:
p(w1 ... wt) = Pie p(wi|wi-1) = Pie e(wi|C(wi)) * q(C(wi)|C(wi-1))

Better way
log p(w1 ... wt) = Sigma log (p(wi|wi-1) = Pie e(wi|C(wi)) * q(C(wi)|C(wi-1)))

A Brown clustering model consists of:
a. A vocabulary V
b. A function C: V -> {1 ... k}
c. A parameter e(v|c) for every v belongs to V, c belongs to {1 ... k}
d. A parameter q(c'|c) for every c', c belongs to {1 ... k}

Measuring the quality of C:
Quality(C) = Sigma log e(wi|C(wi)) q(C(wi)|C(wi-1))
		   = Sigma Sigma p(c, c') log p(c, c')/p(c)p(c') + G

Here,
p(c, c') = n(c, c')/Sigma n(c, c'), p(c) = n(c)/Sigma n(c)

where n(c) is the number of times class c occurs in the corpus
n(c, c') is the number of times c' is seen following c, under the function C

A First algorithm:
We start with |V| clusters; each word gets its own cluster
Our aim is to find k final clusters

We run |V| - k merge steps:
do At each merge step we pick two clusters ci and cj, and merge them into a single cluster
We greedily pick merges such that Quality(C) is maximized at each step

Running time: 
Naive: O(|V|^5). 
Improved algorithm gives O(|V|^3)

A second algorithm:
Parameter of the approach is m (for e.g m = 1000)
Take the top m most frequent words, put each into its own cluster, c1 ... cm
for i = (m+1) to |V|
do Create a new cluster, cm+1, for the ith most frequent word. We now have m + 1 clusters
   Choose two clusters from c1 ... cm+1 to be merged; pick the merge that gives a maximum value for Quality(C)

Carry out (m-1) final merges, to create a full heirarchy.

Running time:
O(|V|m^2 + n) where n is corpus length

Application:

a. Name tagging with Word Clusters and Discriminative Training
Using heirarchical bit strings found by the unsupervised word clustering.

Using Active learning along with word clustering to increase the accuracy

Active learning:
Choose to label the most "Useful" example at each stage

Best model with name tagging:
Discriminative + Clusters + Active

Global Linear Models (GLMs)
===========================

Supervised learning in Natural Language:
General task: induce a function F from members of a set X to members of a set Y. For e.g

Problem 	x belongs X y belongs Y
Parsing 	sentence 	parse tree
Translation French      English
Tagging 	sentence 	sequence of tags

The Models so far:
Most of the models we've seen so far are history-based models:
a. We break structures down into a derivation, or sequence of derivation.
b. Each decision has an associated conditional probability.
c. Probability of structure is a product of decision probabilities.
d. Parameter values are estimated using variants of maximum-likelihood estimation.
e. Function F: X -> Y is defined as
F(x) = arg max p(x, y; Theta) or F(x) = arg max p(y|x; Theta)

For e.g
PCFG
Log-linear taggers

A new set of techniques: Global Linear Models
We'll move away from history-based models, i.e No idea of a "derivation" or attaching probability to "decisions"
Instead we'll have feature vectors over entire structures "Global features"

Motivation:
Freedom in defining features

A need for flexible features:
Parallelism in coordination
Semantic features

Three components of Global Linear Models:
1. f is a function that maps a structure (x, y) to a feature vector f(x, y) belongs to R^d
2. GEN is a function that maps an input x to a set of candidates GEN(x)
3. v is a parameter vector (also a member of R^d)

Training data is used to set the value of v.

Components:
1. f
   f maps a candidate to a feature vector belongs to R^d
   f defines the representation of a candidate

Features:
A "feature" is a function on a structure. For e.g
h(x, y) = Number of times a -> b c is seen in (x, y)

Feature vectors:
A set of functions h1 ... hd define a feature vector
f(x) = <h1(x), h2(x) ... hd(x)>

2. GEN
   GEN enumerates a set of candidates for a sentence. For e.g
   Parsing: GEN(x) is the set of parses for x under a grammar
   Translation: GEN(x) is the set of all possible English translations for the French sentence x.

3. v
   v is a parameter vector belongs to R^d
   f and v together maps a candidate to a real-valued score.

Putting it all together:
X is set of sentences, Y is set of possible outputs.
Need to learn a function F: X -> Y
GEN, f, v define
F(x) = arg max f(x, y) . v for all y belongs to GEN(x)

Choose the highest scoring candidate as the most plausible structure.

End to end model:
Sentence -> GEN -> Different parse trees -> Each parse tree is converted to feature vectors using f -> Each feature vector is multiplied by v to get the maximum scoring feature vector.

Hence, v plays the crucial role in selecting the final parse trees.

Application:
a. Reranking Approaches to Parsing:

Use a baseline parser (Lexicalised PCFG) to produce top N parses for each sentence in training and test data.
GEN(x) is the top N parses for x under the baseline parser

Supervision:
for each xi take yi to be the parse that is "closest" to the treebank parse in GEN(x)

The Representation f:
Each component of f could be essentially any feature over parse trees. For e.g
f1(x, y) = log probability of (x, y) under the baseline model
f2(x, y) = Parallelism feature

A variant of Perceptron Algorithm:

Inputs: Training set (xi, yi) for i = 1 to n, T is number of epochs

Initialisation: v = 0

Define: F(x) = arg max f(x, y) . v for y belongs to GEN(x)

Algorithm:
	for t = 1 to T, i = 1 to n
	do zi = F(xi)
	if (zi != yi)
	then v = v + f(xi, yi) - f(xi, zi)

Output: Parameters v

Reranked models scored 89.5 % as compared to 88.2 % from Lexicalised PCFG
With better features, better baseline model, these models can achieve 91 % accuracy.

================================================================================================================

Week 10
=======

GLMs for Tagging
================

The Perceptron Algorithm for Tagging:
Tagging Problem:
Input: Sequence of words
Output: Sequence of tags

Models:
a. HMM
b. Log-linear models
c. GLMs

Input x are sentences w[1:n] = {w1 ... wn}
Define T to be set of all possible tags
GEN(w[1:n]) = T^n i.e all tag sequences of length n

Local Feature-Vector Representations:
Take a history/tag pair (h, t)
gs(h, t) for s = 1 to d are local features representing tagging decision t in context h
Word/tag features
Contextual features

Define global features through global features:
f(w[1:n], t[1:n]) = Sigma g(hi, ti)
where ti is the ith tag, and hi is the ith history.

Typically, local features are indicator features.
and global features are then counts.

Putting it all together:
GEN(w[1:n]) is the set of all tagged sequences of length n
GEN, f, v define
F(w[1:n]) = arg max v . f(w[1:n], t[1:n])
		  = arg max v . Sigma g(hi, ti)
		  = arg max Sigma v . g(hi, ti)

Dynamic Programming can be used to find the arg max.

Training a Tagger using the Perceptron Algorithm:

Inputs: Training set (wi, ti) for i = 1 to n

Initialisation: v = 0

Algorithm:
	for t = 1 to T, i = 1 to n
	do z[1:n] = arg max v . f(wi, ui) // z[1:n] can be calculated with dynamic programming.
	if (zi != ti)
	then v = v + f(wi, ti) - f(wi, zi)

Output: Parameter vector v

Performance: 2.89% error as compared to 3.28% error in log-linear model.

GLMs for Dependency Parsing
===========================

Unlabeled Dependency Parses:
root John saw a movie

root is a special root symbol
Each dependency is a pair (h, m) where h is the index of a head word, m is the index of the modifier word.
Dependencies in above example are (0, 2) (2, 1) (2, 4) and (4, 3)

Dependency Parsing Problem:
Input: Sequence of words
Output: Dependency pairs

Conditions on Dependency Structures:
a. The dependency arcs form a directed tree, with the root symbol at the root of the tree.
b. There are no "crossing dependencies".

Non-projective structures are models which allow "crossing dependencies".

Supervised learning problem:
Extract dependency structures from treebanks.

Treebank -> Dependency banks using Lexicalisation

Efficiency of Dependency Parsing:
Unlabeled dependency parsing is O(n^3) using dynamic programming

GLM for Dependency Parsing:
x is a sentence
GEN(x) is set of all dependency structures for x
f(x, y) is a feature vector for a sentence x paired with a dependency parse y.

Local feature vectors:
f(x, y) = Sigma g(x, h, m) where g(x, h, m) maps a sentence x and a dependency (h, m) to a local feature vector.

g1(x, h, m) = 1 if xh = saw, and xm = movie.
g2(x, h, m) = 1 if POS(h) = VBD, POS(m) = NN
Unigram features
Bigram features
Contextual features
In-between features

To run the perceptron algorithm, we must be able to calculate
F(x) = arg max w . f(x, y)

Using local features
F(x) = arg max Sigma w . g(x, h, m) // Using dynamic programming

Performance: 91.5 % as compared to 91.4 % of Lexicalised PCFG, but with lower running time of O(n^3)

Extensions:
2nd order dependency parsing
Non-projective dependency structures

Other options for parameter estimation:
Conditional random fields ("giant" log-linear models, gradient ascent for parameter estimation)
Large-margin methods (Related to SVM)

=======================================================================================================================

#### help from
- https://github.com/reetawwsum/Machine-learning-MOOC-notes/blob/master/Natural%20Language%20Processing.txt