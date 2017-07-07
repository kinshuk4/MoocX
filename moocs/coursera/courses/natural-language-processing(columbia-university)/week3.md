# Probabilistic Context-Free Grammars

A probabilistic context-free grammar is simply a context-free grammar with a probability assigned to each subtree. eg

	S  => NP VP          1.0
	VP => Vi             0.4
	VP => Vt NP          0.4
	VP => VP PP          0.2
	NP => DT NN          0.3
	NP => NP PP          0.7
	etc

Notice how the probabilites for each *non-terminal* element (eg `VP`) sum to 1. 

To calculate the probability of a given tree we simply multiply the probabilities of each branch.

For a sentence `s` there may be multiple possible parse trees that can be constructed. The set of all possible trees is `T(s)`. Using PCFGs we can assign a probability `p(t)` to each of these parse trees. The most likely tree for `s` is therefore `arg max[t in T(s)] p(t)`.

### Deriving a PCFG from a Treebank

A treebank is a set of example trees (annotated by hand). The underlying grammar can be taken to be the set of all rules in the corpus. The probabilities are calculated using *maximum likelihood*:

	q_ML(a -> b) = count(a -> b) / count(a)

eg

	q_ML(VP -> Vt NP) = count(VP -> Vt NP) / count(VP)

