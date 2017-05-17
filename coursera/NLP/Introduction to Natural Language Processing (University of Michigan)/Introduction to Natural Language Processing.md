Introduction to Natural Language Processing
===========================================

Week 1
======
Introduction 1/2

NLP is the study of the computational treatment of natural (human) language.

Computers to understand (and generate) human language.

Modern application:
Search engines
Question Answering (IBM's Watson)
Natural language assistants (Siri)
Translation Systems (Google Translate)
News digest (Yahoo)

NLP draws on research in
a. Linguistics
b. Theoretical Computer Science
c. Mathematics
d. Statistics
e. AI
f. Psychology
g. Databases, etc.

Goals of this class:
a. Understand that language processing is hard.
b. Understand the key problems in NLP.
c. Learn about the methods used to address these problems.
d. Understand the limitations of these methods.

Language and Communication:
a. Speaker
- Intention (goal, shared knowledge, and beliefs)
- Generation (tactical)
- Synthesis (text or speech)

b. Listener
- Perception
- Interpretation (syntactic, semantic, pragmatic)
- Incorporation (internalization, understanding)

c. Both
- Context (grounding)

Basic NLP Pipeline:
(U)ndestanding & (G)eneration
Language ---- (U) ---> Computer --- (G) ----> Language

Genres of Text:
Blogs, emails, press releases, chat, debates, etc
Scientific research papers, fiction books, Poetry

Ambiguous nature of sentences results in NLP hard problems.
a. Lexical Ambiguity
b. Structural Ambiguity
c. Scope Ambiguity

Structure of the course:
a. Four major parts.
	i. Linguistic, mathematical, and computational background.
	ii. Computational models of morphology, syntax, semantics, discourse, pragmatics.
	iii. Core NLP technology: parsing, POS tagging, text generation, etc.
	iv. Applications: text classification, machine translation, information extraction, etc

b. Three major goals
	i. Learn the basic principles and theoretical issues underlying NLP.
	ii. Learn techniques and tools used to develop practical, robust systems.
	iii. Gain insight into some open research problems in NLP.

Why NLP is hard?
Metaphorical sentences are hard for NLP
For e.g Time flies like an arrow.

Common sense is hard for NLP machines
For e.g Each American has a mother. (Multiple mothers)
Each American has a president. (Single president)

Syntax error
* Little a has Mary lamb.

Semantic error
? Colorless green ideas sleep furiously.

Ambigous words.

Others ambiguity:
Morphological ambiguity (Structure of a sentence)
Phonetic ambiguity
Part of Speech
Syntactic
PP attachment
Sense
Modality
Subjectivity
CC attachment
Negation
Referential
Reflexive
Ellipsis and parallelism
Metonympy (the substitution of the name of an attribute or adjunct for that of the thing meant)

Other Sources of Difficulties:
Non-standard, slang, and novel words
Complex sentences
Humor and sarcasm
Absense of world knowledge
Semantics and pragmatics

Synonyms and Paraphrases

Most of the ambiquity are fairly common sense, which computers doesn't posses.

Linguistic knowledge:
a. Constituents
For e.g Children eat pizza or They eat pizza.

b. Collocations
For e.g red wine, but not brown wine

How to get this knowledge in the system:
- Manual rules
- Automatic acquire from large text collections (corpora)

Knowledge about language:
a. Phonetics and phonology: the study of sound
b. Morphology: the study of word components
c. Syntax: the study of sentence and phrase structure
d. Lexical semantics: the study of meanings of words
e. Compositional semantics: how to combine words
f. Pragmatics: how to accomplish goals
g. Discourse conventions: how to deal with units larger than utterances

How to store language knowledge:
Finite-state automata
Grammars
Complexity
Dynamic Programming

Mathematics and Statistics:
Probability
Statistical Models
Hypothesis testing
Linear algebra
Optimization
Numerical methods

Mathematical and Computational tools:
Language models
Estimation methods
Context-free Grammar (CFG)
Hidden Markov Models (HMM)
Conditional Random Fields (CRF)
Generative/discriminative models
Maximum entropy models

Statistical Techniques:
Vector space representation for WSD
Noisy channel models for MT
Graph-based Random walk methods for sentiment analysis

Aritficial Intelligence:
Logic
Agents
Planning
Constraint satisfaction
Machine learning

Linguistics:
IPA Chart for language pronouncation
Languages are related, all derived from Proto-Indo-European Langauge (PIE)

Language changes over time.

Diversity of languages:
Absence of Articles
Cases
Sound systems
Social status

NACLO Problems

Language Universals:
Unconditional and conditional types of universals; common in all languages.

==================================================================================

Week 2
======
Introduction 2/2

Part of Speech:
Noun, pronoun, articles, adjectives, adverb, etc.

Computers see text that they don't really understand.
They have to use some prior knowledge.
They reason probabilistically.
They use context.
They can be wrong.

Morphology and the Lexicon:

In linguistics, morphology is the study of words, how they are formed, and their relationship to other words in the same language. It analyzes the structure of words and parts of words, such as stems, root words, prefixes, and suffixes.

Mental Lexicon consits of meaning of word, its pronounciation, part of speech.
Without these, morphology of words are very important.
Intuition and productivity also plays a vital role.

Morphological Analysis:
sleeps = sleep + V + 3P + SG
done = do + V + PP

Semantics:
Lexical and compositional semantics
Lexical is meaning of individual words, and compositional is understanding the meaning of sentence based on meaning of components.

Pragmatics:
The study of how knowledge about the world and language conventions interact with literal meaning.

Text Similarity:
Similarity dataset consits of two words and a similarity score given by human judgements.
Word2vec model is used for text similarity.

Many types of similarity:
Morphological
Spelling
Synonymy
Homophony
Semantic
Sentence (Paraphrases)
Document
Cross-lingual

Morphological similarity: Stemming:
Word with the same root.

To stem a word is to reduce it to a base form, called the stem, after removing various suffixes and endings and, sometimes, performing some additional transformations.

Porter's stemming method is a rule based algorithm.
The method is not always accurate.
The measure of a word is an indication of the number of syllables in it.
Porter's algorithm use measure of a word to convert it into stem using various steps.

Spelling Similarity: Edit Distance:
It can be achieved using Edit operations.

Levenshtein Method:
Based on Dynamic Programming
Insertion, deletion, and substitutions have cost 1.

Recursive Relation:
s1(i) - ith char in string s1
s2(j) - jth char in string s2
D(i, j) - edit distance between a prefix of s1 of length i and prefix of s2 of length j
t(i, j) - cost of aligning the ith character in string s1 with jth character in string s2

Recursive dependencies:
D(i, 0) = i
D(0, j) = j
D(i, j) = min (D(i-1, j) + 1, D(i, j-1) + 1, D(i-1, j-1) + t(i, j))

t(i, j) = 0 if s1(i) = s2(j)
t(i, j) = 1 otherwise

Other costs:
Damerau modification

Other costs includes keyboard typo and OCR mistakes.

NACLO:
Competition in Linguistics

Preprocessing:
Removing non-text
Dealing with text encoding
Sentence segmentation
Normalisation
Stemming
Morphological analysis
Capitalization
Name-entity extraction

Types or Tokens
Tokenization

Word segmenation in many language are very difficult.

Sentence boundary Recognition:
Decision trees
Features

=====================================================================================================

Week 3
======
NLP Tasks and Text Similarity

Semantic Similarity: Synonyms and Paraphrases
Synonyms: Different words having similar meaning.

Polysemy: is the property of words to have multiple senses. They can be used as multiple POS.

Synsets: Group together all synonyms of the same word.

WordNet dataset:
Database of words and semantic relations between them.
Tree structure of different words.

Other dataset:
EuroWordNet
Open Thesaurus
Freebase
DBPedia
BabelNet

MeSH - Medical Subject Headings

Thesaurus-based Word Similarity Methods:
Path Similarity
Version 1:
Sim(v, w) = - pathlength(v, w)

Version 2:
Sim(v, w) = - log pathlength(v, w)

Problems:
There may be no tree for the specific domain or language.
A specific word may not be in any tree.
Is-A edges are not all equally apart in similarity space.

Version 3:
Sim(v, w) = - log P(LCS(v, w))
where LCS = lowest common subsumer

Least Common Subsumer of two concepts A and B is "the most specific concept which is an ancestor of both A and B", where the concept tree is defined by the is-a relation.

Version 4: (Lin Similarity)
IC(c) = - log P(c) # IC - Information Content
Sim(v, w) = 2 * log P(LCS(v, w)) / (log P(v) + log P(w))

Implemented in Python: nltk
Usage: dog.lin_similarity(cat, brown_ic)

The Vector Space model:
Used in information retrieval to determine which document is more similar to a given query q.
Documents and queries are representated in the same space.
Often, the angle between two vectors is used as a proxy for the similarity of the underlying documents.

Cosine Similarity:
Normalised dot products of two vectors.
Sim(d, q) = Sigma (di.qi) / (sqrt(Sigma (di)^2)) (sqrt(Sigma (qi)^2)) 

or Jaccard coefficient
Sim(d, q) = |d intersection q| / |d union q|

Distributional Similarity:
Two words that appear in similar contexts are likely to be semantically related.
However, the context can be any of the following:
a. The word before the target word.
b. The word after the target word.
c. Any word within n words of the target words.
d. Any word within a specific syntactic relationship with target word.
e. Any word within the same sentence.
f. Any word within the same document.

Dimensionality Reduction:

Problems with the simple vector approaches to similarity:
a. Polysemy (sim < cos)
b. Synonymy (sim > cos)
c. Relatedness
d. Sparse matrix

Eigen vectors for matrix reduction in case of square matrices.

Singular Value Decomposition (SVD) in other cases.

Latent Semantic Indexing (LSI):
Dimensionality reduction = identification of hidden (latent concepts)
Query matching in latent space.

NLP Tasks:
Part of Speech taggging
Parsing
Dependency Parsing
Information Extraction
Semantic analysis (First order logic, Inference)
Reading comprehension
Text Understanding
Word Sense disambiguation
Named Entity Recognition
Semantic Role Labeling
Coreference Resolution
Question Answering
Sentiment Analysis
Machine Translation
Text Summarization
Text to Speech
Entailment and Paraphrasing
Dialogue System, and etc.

Garden path sentences:
For e.g
Don't bother coming
Don't bother coming early.

=========================================================================================

Week 4
======
Syntax and Parsing Part 1

Syntax:
Grammatical rules apply to categories and groups of words, not individual words.

Constituents:
Continous, non-crossing words.

Constituents test:
a. "coordination" test
b. "pronoun" test
c. "question by repetition" test
d. "topicalization" test
e. "question" test, and etc

How to generate sentences:
One way: tree structure using grammar (CFG) rules

Adjective ordering:
det < number < size < color < purpose < noun

Recursion:
Allows to generate long sentences.

Parsing:
Associating tree structures to a sentence, given a grammar (often CFG)

Application of Parsing:
a. Grammar checking
b. Question answering
c. Machine translation
d. Information extraction
e. Speech generation, and etc

Context-free Grammar:
4-tuple (N, Sigma, R, S), where
N = non-terminal symbols.
Sigma = terminal symbols.
R = set of rules alpha -> beta
S = start symbol from N

Penn Treebank dataset

Leftmost derivation.

Classic Parsing Methods:
Parsing As Search
There are two types of constraints on the parses:
a. From the input sentence
b. From the grammar

Two general approaches to parsing:
a. Top-down
b. Bottom-up

Bottom-up:
explores options that won't lead to a full parse.

Top-up:
explores options that don't match the full sentence.

Dynamic Programming:
caches of intermediate results

Cocke-Kasami-Younger (CKY) Parser
based on dynamic programming, and Chomsky Normal Grammar

Shift-reduce parsing:
A bottom-up parser: tries to match the RHS of a production until it can bind an S
Shift operation: Each word in the input sentence is pushed onto a stack.
Reduce operation: If the top n words on the top of the stack match the RHS of a production, then they are popped and replaced by the LHS of the production
Stopping operation: The process stops when the input sentence has been processed and S has been popped from the stack.

Dynamic Programming:
Motivation:
a. A lot of work is repeated.
b. Caching intermediate results improves the complexity.

DP:
Building a parse for a substring [i, j] based on all parses [i, k] and [k, j] that are included in it.

Complexity:
O(n^3)

Issues with CKY:
Weak equivalence only
a. Same language, different structure
b. If the grammar had to be converted to CNF, then the final parse tree doesn't match the original grammar.
c. However, it can be converted back using a specific procedure.

Syntactic ambiquity:
a. CKY has no way to perform syntactic disambiguation.

Earley Parser:
No need to convert the grammar to CNF
Left to right

Complexity:
Faster than O(n^3)

Looks for both full and partial consituents.
For e.g S -> Aux. NP VP

When reading word k, it has already identified all hypothesis that are consistent with words 1 to k-1

It uses a dynamic programming table, just like CKY

Problems in dynamic programming parser:
a. Agreement
Many combinations of rules are needed to express agreement.
b. Non-independence
Use lexicalised grammar

Syntax helps understand the meaning of a sentence.

The Penn Treebank:
Used for supervised learning methods.
Size: 40k training sentences
2400 test sentences

Penn Treebank tagset

Classification task:
a. Document Retrival
b. Part of Speech tagging
c. Parsing

Data Split:
Training
Validation
Test

Evaluation methods:
a. Accuracy
b. Precision and Recall
c. F1: harmonic mean of precision and recall

================================================================================================

Week 5
======
Syntax and Parsing Part 2

Parsing noun sequences:
Parsing programming languages are unambigous.

Parsing human languages is ambigous:
a. Coordination scope: Small boys and girls are playing.
b. Prepositional phrase attachment: I saw the man with the telescope.
c. Gaps: Mary likes Physics but hates Chemistry.
d. Particles vs. Prepositions: She ran up a large bill.
e. Gerund vs Adjectives: Playing cards can be expensive.

Noun-noun compounds:
Fish tank = tank that holds fish
Fish net = net used to catch fish
Fish soup = soup made with fish

Head of the compound:
Second word usually. 
For e.g College junior - a kind of junior

Prepositional Phrase Attachment 1/3:
a. High (verbal): join board as director
b. Low (nominal): is chairman of Elsevier

Binary classification problem:
Input: a prepositional phrase and the surrounding context
Output: a binary label: 0 (high) or 1(low)
In practice: the context consists only of four words: the preposition, the verb before the preposition, the noun before the preposition, and the noun after the preposition

Dataset: RRR94 (Part of Penn treebank)

Supervised learning: Evaluation
a. Manually label a set of instances.
b. Split the labeled data into training and testing sets.
c. Use the training data to find patterns.
d. Apply these patterns on the testing dataset.
e. For evaluation: use accuracy

Prepositional Phrase Attachment 2/3:
Find the lower bound and upper bound on accuracy.
Lower bound: Classifying everything with one class(Popular class in the test dataset).
Upper bound: Human performance

Using Linguistic knowledge to create more features.
Using various decision rules to create a classifier.

Prepositional Phrase Attachment 3/3:
Using maximum likelihood estimate for classifying prepositional phrase attachment.

Statistical Parsing:
Need for Probabilistic Parsing:
Need for ranking the parse trees

Probabilistic Context-Free Grammars (PCFG)

The probability of a parse tree t given n productions used to build it
p(t) = Pie P(alpha_i -> beta_i)

The most likely parse is
arg max p(t)

Probabilistic Parsing Methods:
a. Probabilistic Earley algorithm
b. Probabilistic Cocke-Kasami-Younger (CKY) algorithm

Probabilistic Grammars:
Learned from training corpus

Maximum Likelihood Estimates:
P(alpha -> beta) = Count(alpha -> beta) / Count(alpha)

Lexicalised Parsing:
Limitation of PCFG:
a. The probabilities don't depend on the specific words.
b. It is not possible to disambiguate sentences based on semantic information.

Using head of a phrase as an additional source of information.

Collin's Parser for Lexicalised Parsing

Issues with Lexicalised Parsing:
a. Sparseness of training data
b. Combinatorial explosion

Discriminative Reranking:
A parser may return many parses of sentence, with small differences in probabilities.
Other considerations may need to be taken into account:
a. Parse tree depth
b. left attachment or right attachment
c. discourse structure

Dependency Parsing:

Dependency structure:
For e.g blue house
blue: modifier, dependent, child, subordinate
house: head, governor, parent, regent

Sentence Parsing (Starts with S)
Dependency Parsing (Starts with verb)

Techniques:
a. Dynamic Programming O(n^5)
CKY Parser

b. Constraint based methods O(n^3)
NP Complete problem

c. Deterministic Parsing O(n^2)
Graph-based methods
Maximum Spanning trees

English dependency trees are mostly projective (can be drawn without crossing dependencies).
Other languages are not.

Dependency parsing is equivalent to search for maximum spanning tree in a directed graph.

e. Malt Parser O(n)
Very similar to shift-reduce parsing

Application:
a. Information Extraction
b. Dependency Kernels

Alternative Parsing Formalism:

Mildly Context-Sensitive Grammars:
a. Tree Substitution Grammar (TSG)
Terminals generate entire tree fragments.
TSG and CFG are formally equivalent.

b. Tree Adjoining Grammar (TAG)
Like TSG, but allow adjunction
It can generate languages like a^n b^n c^n
For e.g
Mary gave a book and a magazine to Chen and Mike, respectively.
TAG is formally more powerful than CFG
TAG is less powerful than CSG

c. Combinatory Categorial Grammar (CCG)
Complex types
For e.g X\Y and X/Y
These takes an argument of type Y and return an object of type X.
X\Y - means that Y should appear on the left.
X/Y - means that Y should appear on the right.

For e.g
I sleep

CCG can generate the language a^n b^n c^n d^n

Semantic Parsing:
Associate a semantic expression with each node.

For e.g
Javier eats pizza.

Parse tree
N: Javier
V: lambda, x, y, eat(x, y)
N: pizza
VP: lambda, x, y, eat(x, pizza)
S: eat(Javier, pizza)

Application:
Question Answering
Bots

==================================================================================================

Week 6
======
Language Modeling

Probabilities:
Probabilistic reasoning is very important in NLP.
Applications:
Speech recognition
Machine Translation

Probabilities make it possible to combine evidence from multiple sources in a systematic way.

Termilogies:
a. Probability theory
b. Experiment
c. Possible outcomes
d. Same spaces
e. Events
f. Probability distribution

Meaning of probabilites:
a. Frequentist
I threw the coin 10 times and it turned up heads 5 times.
b. Subjective
I am willing to bet 50 cents on heads.

Conditional Probability:
Prior and posterior probability
P(A|B) = P(A intersection B) / P(B)

The chain rule:
P(w1, w2, ... wn) = P(w1) P(w2|w1) P(w3|w2, w1) ... P(wn|w1 ... wn-1)
or Pie P(wi|w1 ... wi-1)

Used in Markov models.

Independence:
P(A intersection B) = P(A) P(B)
Unless P(B) is 0,
P(A) = P(A|B)

Adding and Removing constraints (Backoff) while calculating conditional probabilities with multiple joint probabilities.
Adding: More accurate, but need lot of data.

Random Variables:
The numbers are generated by a stochastic process with a certain probability distribution.

Probability mass function:
Probability that the random variable has different numeric values.
P(x) = P(X = x)

Bayes Theorem:
P(A, B) = P(B|A) P(A)
P(A, B) = P(A|B) P(B)

P(B|A) = P(A|B) P(B) / P(A)

Language Modeling:

Probabilistic Language Models:
Assign a probability to a sentence
P(S) = P(w1, w2, ..., wn)

Application:
Predicting the next word in the sequence
P(wn|w1 ... wn-1)

Speech recognition
Text generation
Spelling correction
Machine translation
OCR
Summarization
Document classification

P(w1, w2, ..., wn) = P(w1) P(w2|w1) P(w3|w1, w2) ... P(wn|w1, ... wn-1)

N-gram model:
Markov assumption - Only look at limited history
Unigram
Bigram
Trigram

Brown corpus

Maximum Likelihood Estimates:
Use training data
Count how many times a given context appears in it.

These estimates may not be good for corpora from other genres.

Special symbols to start the sentence and end.

N-grams and Regular Languages:
N-grams are just one way to represent weighted regular languages.

Generative Model

Use logarithms (base 10) to avoid underflow of small probabilities.
Use sum instead of product.

Smoothing:
Too many parameters to estimate even a unigram model.
MLE assigns values of 0 to unseen data.

Main idea:
Reassigning some probability mass to unseen data.
Distributing some of the probability mass to allow for novel events.

Add-one (Laplace) smoothing:
Bigrams: P(wi|wi-1) = ((C(wi-1, wi) + 1) / C(wi-1) + V)

Good turing:
Revised counts C* = (c+1) Nc+1/Nc

Kneser-ney
Class-based n-grams

Backoff:
Going back to the lower-order n-gram model if the higher-order model is sparse.

Interpolation:
P'(wi|wi-2, wi-1) = lambda1 * P(wi|wi-2, wi-1) + lambda2 * P(wi|wi-1) + lambda3 * P(wi)

Evaluation of LM:
a. Extrinsic
Use in an application
b. Intrinsic
Cheaper

Correlate the two for validation purposes.

Intrinsic:
a. Perplexity
Average branching factor in predicting the next word.
Lower is better

b. Shannon Game
Predicting the next word

Perplexity across distribution is very different.
Cross entropy = log (perplexity)

c. Word Error rate
Number of insertions, deletions, and substitutions.
Same as Levenshtein Edit Distance

Issues:
Out of vocabulary words: Clustering helps

Long Distance Dependencies: n gram model doesn't do much good.
Missing syntactic information
Missing semantic information

Other models in LM:
Syntactic models
Caching models

Word Sense Disambiguation:
Polysemy: Words have multiple senses.

Task:
given a word
and its context
Output: meaning of a word from multiple meanings.

Application:
Machine Translation
Accent restoration
Text to speech generation

Models:
a. Dictionary Method:
Match sentences to dictionary definitions.
Overlap method

b. Decision Lists:
Two sense per word
Ordered rules: collocation -> sense
For e.g
fish within window -> bass1
striped bass -> bass1
guitar within window -> bass2

Classification features:
i. Collocations
ii. Position
iii. Part of Speech
iv. Nearby words
v. Syntactic information

c. K-nearest neighbor

d. Bootstrapping
Start with two senses and seeds for each sense
For e.g plant1: leaf, plant2: factory
Use these seeds to label the data using a supervised classifier (decision list)
Add some of the newly labeled examples to the training data.

Two principles:
One sense per collocation
One sense per discourse

Training data for WSD:
Senseval/Semcor
Pseudo words
Multilingual corpora

Evaluation:
Precision and Recall

================================================================================================

Week 7
======
Part of Speech Tagging and Information Extraction

Noisy Channel Model:
P(X, Y) = P(X) P(Y|X)

It behaves like an encoder and decoder
e -> e => f -> f => e -> e'

e' = arg max P(e|f) = arg max P(e) P(f|e)
where P(f|e) is translation model
P(e) is language model

Uses of the Noisy channel model:
Handwriting recognition
Text generation
Text summarization
Machine translation
Spelling correction

Part of Speech tagging:
Input: Sequence of words
Output: Sequence of POS tags

POS tags:
a. Open class:
nouns, non-modal verbs, adjective, adverbs

b. Closed class:
Prepositions, modal verbs, conjunctions, particles

Dataset
Penn treebank

Three main techniques:
a. Rule based
b. Machine learning (Conditional random fields, maximum entropy, Markov models)
c. Transformation based

Source of information for tagging:
a. Lexical information
b. Spelling
c. Capitalization
d. Neighboring words

Evaluation:
Baseline:
tag each word with its most likely tag
tag each OOV word as a noun
around 90% accuracy

Upper bound
98% accuracy

Rule based POS tagging:
Use dictionary or finite-state transducers to find all possible parts of speech.
Use disambiguation rules

Hidden Markov Models:

Markov Models:
Sequences of random variables that aren't independent.
For e.g Weather forecast

Properties:
a. Limited horizon
P(Xt+1 = sk|X1, ... xt) = P(Xt+1 = sk|xt)

b. Time invariant

Visible MM:
P(X1 ... Xt) = P(X1) P(X2|X1) P(X3|X1, X2) ... P(Xt|X1 ... Xt-1)

Hidden MM:
Definition
Q: sequence of states
O: sequence of observations
qo, qf: special (start, final) states
A: state transition probabilites
B: symbol emission probabilities
Pie: Initial state probabilities
Mue: (A, B, Pie) = Complete probabilistic model

Can be used to model state sequences and observation sequences.
For e.g
P(s, w) = Pie P(si|si-1) P(wi|si)

The state encode the most recent history
The transitions encode likely sequences of states.
Use MLE to estimate the transition probabilities.

Emissions:
Estimating the estimation probabilities.
Standard smoothing.
Heuristics.

Inference:
Find the most likely sequence of tags, given the sequence of words.
t* = arg max P(t|w)
Given the model Mue, it is possible to compute P(t|w) for all values of w.
In order to avoid too many combinations.
a. Beam search
b. Viterbi algorithm

Viterbi algorithm:
Dynamic Programming
Memoization
Backpointers

HMM learning:
a. Supervised
- Training sequences are labeled.
Estimate the static probabilities using MLE
Estimate the observations probabilities using MLE
Use smoothing

b. Unsupervised
- Training sequences are unlabeled.
- Known number of states
Use EM (Expectation Maximization) methods
Forward-backward (Baum-Welch) algorithm

Algorithm:
Randomly set the parameters of the HMM.
Until the parameters converge repeat:
a. E step: determine the probability of the various state sequences for generating the observations
b. M step: reestimate the parameters based on the probabilities.

c. Semi-supervised
- Some training sequences are labeled.

Statistical POS tagging:
T = arg max P(T|W)
By Bayes' theorm:
P(T|W) = P(T) P(W|T)/P(W)

P(W) can be ignored.
P(T) is prior, and P(W|T) is likelihood.

P(W|T) = Pie P(wi|ti)
P(T) = Pie P(ti|ti-1)

Hence,
T = arg max Pie P(wi|ti) P(ti|ti-1)

Evaluating Taggers:
Dataset:
Training set
Validation set
Test set

Transformation based learning:
Keeps on transforming the tag list based on rules.
It consists of change list and conditions.

New domains:
Lower performance

Distributional clustering:
Combine statistics about semantically related words.

Information Extraction:
Input: Unstructured or semi-structured data. For e.g news stories, papers, resumes
Output: Entities, build knowledge base

Extract:
Named Entities
Time
Events

It is considered as sequence labeling problem.

Classification methods:
Use the categories of the previous tokens as features in classifying the next one.
Direction matters.

Named Entity Recognition:
a. Segmentation
b. Classification

Relation Extraction:
Links between entities.

View IE as a sequence labeling problem
- Use HMM

Use pattern
- Use regex

Use features
- Capitalisation, digits

Evaluating template-based IE:
For each test document:
Number of correct template extractions.
Number of slot/value pairs extracted.
Number of extracted slot/value pairs that are correct.

Relation extraction:
Person-person
Person-organisation
Organisation-organisation
Organisation-location

Core NLP task
Used for building knowledge bases, question answering.

Models:
a. Using patterns
b. Supervised learning
c. Semi-supervised learning

Extracting Is-A relations.
Using patterns

Supervised learning:
Look for sentences that have two entities that we know are part of the target relation.
Look at the other words in the sentence, especially the ones between the two entites.
Use a classifier to determine whether the relation exists.

Semi-supervised learning:
Start with some seed (words)
Look for other sentences with the same words.
Look for expressions that appear nearby.
Look for other sentences with the same expressions.

Evaluation:
Precision
Recall
F1 measure

=====================================================================================================

Week 8
======
Question Answering

Siri
Ask Jeeves (ask.com)
WolframAlpha
IBM Watson

People ask question online

Dataset:
The Excite Corpus
AOL corpus

Question type Taxonomy:
a. Yes/No vs wh.(who, what)
b. Factual vs. Procedural
c. Single vs. multiple answers
d. Objective vs. Subjective
e. Context specific vs. generic
f. Known answer in the collection, y/n

State of the Art:
Question types: Mostly factual, short-answer questions
System architecture:
IR component
Statistical approaches using lot of data.
Relatively little knowledge

Older Systems:
a. BASEBALL
b. ELIZA
c. SHRDLU
d. Lunar
e. Murax
f. START
g. Deep Red
h. Jupiter

Evaluation of QA, System Architecture:

The TREC QA evaluation
a. MRR:
Mean Reciprocal Rank = 1/N (Sigma 1/rank)

b. TRR:
Total Reciprocal Rank

c. Confidence-weighted score

Other types of questions:
a. Definitional
b. List
c. Crosslingual
d. Series

Architecture:
Many questions can be answered by traditional search engines.

System components:
a. Source identification
b. Query modulation
c. Document retrieval
d. Sentence ranking
e. Answer extraction
f. Answer ranking

Question type classification
QA-token like place, country, person etc.

UIUC papers on QA

QA System Architecture:

Techniques:
a. Classification task
b. Regular expressions

Query Formulation:
Determine which words to include in the IR query.

Passage Retrieval:
Features
- Proper nouns that match the query
- Near each other
- Entities that match the expected answer type

Answer Retrieval:
Use NER (Name Entity Recognition) to identify the matching phrases.
Features
- Distance to query words
- Answer type
- Wordnet similarity
- Redundancy

Types:
Traditional corpus based systems
Open web based systems

AnSel:
Feature selection:
a. Avgdst
b. Notinq
c. Frequency
d. Sscore
e. Number
f. Rspanno
g. Count
h. Type

IONAUT:
Passage Retrieval
Entity Recognition
Entity Recognition

Mulder:
First large-scale Web QA system
Components:
- Maximum entropy parser
- PC-Kimmo for unknown words
- Link Parser
- Google
Tokenization
Query transformations

NSIR:
Probabilistic phrase ranking
Search engines

AskMSR:
Query rewriting
Snippet retrieval
N-gram ranking

Echihabi and Marcu:
Based on noisy channel model

LCC:
Uses logic form transformations
Uses axioms for inference

QASM:
Noisy channel model
Convert NL question to query
Channel operators

Ravinchandran and Hovy:
Automatically learn surface patterns
Starts with a seed
Query web
Find patterns that contain both the question and the answer terms

IBM Watson:
Uses 'DeepQA'
16TB of RAM
10 racks of IBM Power 750 servers running Linux
2880 processor cores
Operating at 80 teraflops.
Knowledge sources:
200 million pages of structured and unstructured content (4TB of disk storage)
Including wikipedia, Wordnet
Betting strategy
Question types: 2500 of them

QA challenges:
Word sense disambiguation
Co-reference resolution
Semantic role labeling
Temporal Questions

==================================================================================================

Week 9
======
Text Summarization

Input: Paragraph
Output: Summary of the paragraph

Types:
News summarization
Book summarization
Movie summarization
Search engine snippets

Genres:
Headlines
Outlines
Minutes
Biographies
Abridgments
Sound bites
Movie summaries

Other way to categorize summaries types:
a. Input
b. Output
c. Purpose
d. Form
e. Dimensions
f. Context

Stages:
Content identification
Conceptual organization
Realization

Extractive Summarization:
Selecting units of the original text
Baseline: Extract the first few sentences.

Models:
a. Baxendale
Positional method
- Pick the first and last sentences of the paragraph.
- Naive but decent approach.

b. Luhn
- Stemming
- Stop word are removed
- Frequency of content terms
- Sentence-level significance factor

c. Edmundson
- Position and frequency
- Cue words (bonus and stigma words)
- Document structure
- Linear combination of the features

d. Frump
- Knowledge based
- Slot-filling based on UPI news stories
- Based on 50 sketchy scripts
- Difficult to port to other domains.

e. Paice
- Survey up to 1990
- Problems: Lack of balance, lack of cohesion

f. Brandow et al.
- "Lead" achieves acceptability of 90% vs. 74.4% for "intelligent" summaries.
- sentence based features

g. Kupiec et al.
- First trainable method
- Naive Bayes classifier
- Features: Sentence length, Presence of uppercase words, sentence position in paragraph.
Performance:
84% precision

h. Summons
- First work on multi-document summarization
- Approach: 
Knowledge based
information extraction (MUC templates)
Text generation

i. Mitra/Allen/Salton
- Semantic hyperlinks
Paths linking highly connected paragraphs are more likely to contain information central to the topic of the article.

j. Mani/Bloedorn
Graph-based method for identifying similarities and differences between documents.
Single event or sequence of events
Content entties and relations

k. Barzilay & M. Elhadad
Lexical chains
Wordnet based
Types of relations: extra-strong, strong, medium strong.

l. Marcu
Focuses on text coherence
Based on Rhetorical Structure theory

m. Noisy channel model
Gisting (OCELOT)
g* = arg max p(g|d) = arg max p(g) . p(d|g)

n. Carbonell and Goldstein
Maximal Marginal relevance
Greedy selection method
Query based summaries

o. Mead
Salience-based extractive summarization
Vector space model
Features: position, length, centroid

p. Conroy and O'Leary
Using HMM
Takes into account the local dependencies between sentences
Features: Position, number of terms, similarity to document terms

q. Osborne
Don't assume feature independence
Use maxent (log-linear) models
Better than naive bayes

r. Lexrank
Lexical Centrality
- Represent text as a graph
- Graph clustering

s. Gong and Liu
Using Latent Semantic Analysis
TFIDF weights in the matrix

Summarization Evaluation:
Criteria:
a. Length
b. Fidelity
c. Salience
d. Grammatrical
e. Non-redundancy
f. Structure & coherence

Ideal Evaluation:
Information content
C = Compression ratio = |S|/|D|
R = Retention ratio = i(S)/i(D)

Types of evaluation methods:
a. Extrinsic techniques (task-based)
Make same decision using the summary as well as full text
b. Intrinsic techniques
Comparing summaries against gold standard.

Metrics:
a. Precision and Recall, F1 Measure

b. Rouge: R stands for Recall
Rouge-n: measure of n-gram overlap between a summary and a set of reference summaries.
Rouge-L: uses longest common subsequence instead of n-gram overlap.

c. Relative Utility
Comparing multiple gold standard summaries.

Subsumption and Equivalence:
Subsumption: If the information content of sentence a (I(a)) is contained within sentence b, then a becomes informationally redundant.
I(a) subset I(b)

Equivalence: If I(a) subset I(b) and I(b) subset I(a)

Pyramid:
Based on Semantic Content units.
Used for multi-document summarization.
Different surface realizations with equivalent meanings.

Available Corpora:
DUC and TAC corpora
SummBank corpus
SUMMAC corpus
NY Times corpus

Sentence Simplification:
Removing some parts of sentences:
- Quotes
- Appositions
- Adjective and Adverbs

Application:
Subtitling
Headline generation
Mobile devices

Knight and Marcu:
Use structured (syntactic) information
Two approaches:
Noisy channel model
Decision based

=====================================================================================================================

Week 10
=======
Collocations and Information Retrieval

Collocation:
Phrases occur together.
For e.g dead end, strong tea

Properties:
- Common use
- No general syntactic or semantic rules
- Important for non-native speakers

Properties:
a. Substitutions are usually not allowed.
b. Language and dialect-specific
c. Common in technical language
d. Recurrent in context

Uses:
Diambiguation
Translation
Generation

Types:
a. Grammatical
b. Semantic
c. Flexible

Collocations breaks down into Base-Collocator pairs.

Extracting collocations:

a. Mutual information:
I(x;y) = log P(x, y) / P(x) P(y)

Larger means stronger

b. Yule's coefficient
A: frequency of pairs involving both W and X
B: frequency of pairs involving W only
C: frequency of pairs involving X only
D: frequency of pairs involving neither

Y = AD-BC/AD+BC, -1 <= Y <= 1

Flexible and Rigid collocations

Translating Collocations:
Don't translate word by word.

Information Retrieval:
It is about building search engines

Examples:
a. Conventional (library catalog)
b. Text-based (Google)
c. Image-based
d. Question answering system
e. Clustering systems

Challenges:
a. Dynamically generated content
b. New pages get added all the time

Characteristics of user queries:
a. Sessions
b. Very short queries
c. Large number of typos
d. Small number of popular queries
e. No use of advanced query operators

Information Retrieval:
Baseline process
Given a collection of documents
And a user's query
Find the most relevant documents

Key terms:
a. Query
b. Document
c. Collection
d. Index
e. Term

Search Engine Architecture:
Decide what to index
Collect it
Index it (efficiently)
Keep the index up to date
Provide user friendly query facilities

Document Representations:
Term-document matrix
Document-document matrix

Store document words in an Inverted Index
For e.g
Word: D1, D2
Word: D3, D4

Document and query are considered as vectors

The matching process:

a. Distance measures
Euclidean
Manhattan
Word overlap
Jaccard coefficient

b. Similarity measures
Cosine measure

Positional Indexing:
Keep track of all words and their positions in the documents

Document Ranking:
Compute the similarity between the query and each of the documents
Use cosine similarity
Use TF*IDF weighting
Return the top k matches to the user

Inverse document frequency (IDF):
IDF = log (N/dk) + 1
where
N: number of documents
dk: number of documents containing term k
fk: absolute frequency of term k in document i
wik: weight of term k in document i

Evaluation of IR:
Size of index
Speed of indexing
Speed of retrieval
Accuracy
Timeliness
Ease of use
Expressiveness of search language

Use precision and recall over accuracy.

The Sign test for comparing two search engines.
Other test:
Student t-test
Wilcoxon Matched-Pairs Signed-Ranks test

Text Classification:
Assigning documents to predefined categories.
For e.g topics, languages, users

Types:
Hierarchical vs. flat
Overlapping or non-overlapping

Techniques:
Generative (k-nn, Naive Bayes): Model joint prob p(x, y) and use Bayesian prediction to compute p(y|x)
Discriminative (SVM, Regression): Model p(y|x) directly.

Each document is represented as vector in an n-dimensional space.
Similar documents appear nearby in the vector space.

Use MLE for training in Naive Bayes.

Feature Selection: The Kie^2 test
Compute the Kie^2 for all words and pick the top k among them.

Dataset:
20newsgroups
Reuters-21578
WebKB
RCV1

Other techniques:
Decision Trees
Linear Regression
Perceptron Algorithm
K-nn

Text Clustering:
Unknown number of categories
Documents in the same cluster are relevant to the same query.

Techniques:
k-means

Evaluation:
Purity
RAND index

Heirarchical clustering
- Single linkage
- Complete linkage
- Average linkage

Clustering using dendrograms

Information Retrieval Toolkit:
Smart
MG
Lemur
Terrier
Clairlib
Lucene/SOLR

====================================================================================================================

Week 11
=======
Sentiment Analysis and Semantics

Sentiment Analysis:

Research questions:
- Subjectivity analysis
- Polarity analysis (Positive/negative, number of stars)
- Viewpoint analysis

Sentiment target:
- entity
- aspect

Level of granularity:
- Document
- Sentence
- Attribute

Opinion words:
- Base
- Comparative

Problems:
Subtlety
Concession
Manipulation
Sarcasm and irony

SA as a classification problem:
Set of features
- words
- punctuation
- phrases
- syntax

Techniques:
MaxEnt
SVM
Naive Bayes

Sentiment Lexicons:
SentiWordNet
General Inquirer
LIWC
MPQA subjectivity lexicon

Dictionary-based Methods:
Start from known seeds. For e.g happy, angry
Expand using WordNet. Synonyms, hypernyms
Random-walk based method

Automatic Extraction of Sentiment words:
Semi-supervised methods (Clustering techniques)

PMI (Turney) Pointwise mutual information
Check how often a give unlabeled word appears with a known positive word.
PMI = log hits(word1 NEAR word2) / hits(word1) hits(word2)

Semantics:
Representing meaning
Capturing the meaning of linguistic utterances using formal notation.
Linguistic meaning vs. Pragmatic meaning

For e.g
I bought a book.
x, y: Buying(x) ^ Buyer(speaker, x) ^ BoughtItem(y, x) ^ Book(y)
Buying(Buyer = speaker, BoughtItem = book)

Entailment:
One fact follows from another.
"All cats have whiskers" and "Martin is a cat" entail the statement "Martin has whiskers".

Presupposition:
"The Queen of Utopia is dead" presupposes that Utopia has a queen.

Representing and Understanding Meaning:

Understanding meaning:
If an agent hears a sentence and can act accordingly, the agent is said to understand it.
Understanding may involve inference and pragmatics.
So, understanding may involve a procedure.

Properties:
Verifiability
Unambiguousness
Canonical form
Expressiveness
Inference

Representing meaning:
Use logic representations. For e.g First order logic
Use theorm proving one statement entails other.

Propositional Logic:
The simplest type of logic
Deals with two sentences and negation, conjunction, disjunction, implication, and biconditional between them.

It can be expressed in Backus Naur Form.

Semantics of Propositional logic

First order logic:

Pros of Propositional logic
- Compositional
- Declartive

Cons:
- Limited expressive power
- Represents facts

FOL:
Used to represent
- Objects
- Relations
- Functions

Formula -> AtomicFormula | Formula Connective Formula | Quantifier Variable Formula | Not Formula
AtomicFormula -> Predicate(Term)
Term -> Function(Term) | Constant | Variable
Connective -> AND | OR
Quantifier
Constant
Variable
Predicate
Function

Lambda Expressions:
lambda x: x+1

Knowledge Representation:

Ontologies
Categories and Objects
Events
Time
Beliefs

Semantics of FOL:
FOL sentences can be assigned a value of true or false.
For e.g ISA(Milo, Cat) = true

Martin went from the kitchen to the yard.
ISA(e, Going) ^ Goer(e, Martin) ^ Origin(e, kitchen) ^ Target(e, yard)

Fluents:
A predicate that is true at a given time: T(f, t)

Use Modal logic: Possiblity, Tempora logic, Belief logic to represent time and belief

Inference:

Modus Ponens:
alpha
apha => beta
------------
beta

For e.g
Cat(Martin)
Cat(x) => EatsFish(x)
---------------------
EatsFish(Martin)

Forward Chaining: as individual facts are added to the database, all derived inferences are generated.
Backward Chaining: starts from queries.

Use FOL for inference.

Universal Instantiation:
Every instantiation of a universally quantified sentence is entailed by it.

Existential Instantiation:

Unification:
If a substitution theta is available, unification is possible.

Semantic Parsing:
Converting natural language to a logical form.
For e.g executable code for a specific application.

Stages:
Input
Syntactic Analysis
Semantic Analysis

Compositional Semantics:
Add Semantic attachments to CFG rules.
Compositional semantics
- Parse the sentence syntactically
- Associate some semantics to each word
- Combine the semantics of words and non-terminals recursively.
- Until the root of the sentence.

For e.g
Input: Javier likes pizza
Output: like(Javier, pizza)

Parsing:
- Associate a semantic expression with each node.
Javier - N: Javier
likes - V: lamda x, y likes(x, y)
pizza - N: pizza

VP -> V N
VP: lambda x, pizza likes(x, pizza)

S -> N VP
S: likes(Javier, pizza)

Using CCG (Combinatorial Categorial Grammar) for sematic parsing:
CCG representations for semantics
For e.g
ADJ: lambda x tall(x)

Other models:
GeoQuery
Zettlemoyer and Collins

===============================================================================================================

Week 12
=======
Discourse, Machine Translation, and Generation

Discourse Analysis:
Issues
a. Anaphora: Referring expression and antecedents.
b. Coreference resolution

Coreference resolution:
Agreement constraints
- gender, number, animacy
Syntactic constraints
- parallelism
Sentence ordering
- recency

Properties for antecedent: Salience weights

Recency handling: weights are cut in half after each sentence is processed.

Resolution of Anaphora Procedure using Salience weights.

Coherence:
Coherent sentences are those which talk about same topic.

Nucleus and Satellite:
For e.g
The carpenter was tired. He had been working all day.
1st sentence: Nucleus
2nd sentence: Satellite

Coherence relations:
a. Result
b. Explanation
c. Parallel
d. Elloboration
e. Other relations

Discourse Parsing:
Four RST relations: contrast, cause-explanation, evidence, condition, and elaboration + non-relation
Up to 4M automatically labeled examples per relation
Naive Bayes
Word co-occurence features

Centering:
Goal: understand the local coherence of discourse
Inference load associated with badly chosen referring expressions.
Too much focus shift makes the text hard to understand.

Cross-document structure (CST):

Argumentative Zoning:
Aim
Textual
Own
Background
Contrast
Basis
Other

Local Entity Coherence:

Dialogue Systems:
Turn taking
Default turn-taking rule
Barge-in

Conversational Implicature:
Implicature: Meaningful inferences that the listener can make

Grice's Maxims:
Maxim of quantity
Maxim of quality
Maxim of relevance
Maxim of manner

Speech Acts:
Assertives
Directives
Commissives
Expressives
Declarations

Dialogue system architecture:
Understanding
Dialogue manager
Task manager
Generation

Prosody:
Properties
- Rhythm
- Intonation
- Stress

Used to express emotions, emphasis, etc.

Machine Translation:

Parallel corpora:
The Rosetta Stone
The Hansards Corpus
The Bible

Language Differences:
Word Order
Prepositions
Inflection
Lexical distinctions
Multiple translation
Vocabulary

Basic Techniques:
Direct approach - 50's, 60's
Transfer method
Interlingua

Noisy channel methods:
Parametric probabilistic models of language and translation

e' = arg max P(f|e) P(e)

IBM translation models
Alignments

Advanced Methods:
Tree-to-tree
Phrase-based
Syntax-based
Clause restructuring
Synchronous Grammars

Evaluation:
Human judgements
Edit costs
BLEU

Decoding:
Find a translation that maximizes P(F|E) P(E)
NP-complete for IBM model 1
Use phrase translation table
Use A*
Use beam search

Tools:
Language Modeling toolkits: SRILM, CMULM
Translation systems: Giza++, Moses
Decoders: Pharaoh

Text Generation:
Process of deliberately constructing a natural language text in order to meet specified communicative goals.

NLG:
Mapping meaning to text
Stages:
Content selection
Lexical choice
Sentence structure
Discourse structure

Examples NLG system:
FOG
Plandoc

NLG is about choices:
Content
Coherence
Style
Media
Syntax
Aggregation
Referring expressions
Lexical choice

=====================================================================================================================
#### help from
 - https://github.com/reetawwsum/Machine-learning-MOOC-notes/blob/master/Introduction%20to%20Natural%20Language%20Processing.txt
