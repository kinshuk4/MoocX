---
section: 1
subsection: 1
type: lecture
title: Probability Models and Axioms
---

# Probability Models and Axioms
<!-- not deleted on drive -->
$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$

Terms:  
1. Probabilistic Model
2. Sample space

What are the two steps of constructing a probabilistic model?
1. Construct a sample space
2. Specify the likelihood of outcomes

What are the main properties of a sample space?
1. Mutually exclusive - no two outcomes can occur at the same time
2. Collectively exhaustive - every outcome is contained in the sample space.
3. At the right granularity - individual samples are at the level of detail that captures the information relevant to you

How is the sample space denoted?  
\(\Omega\)

For what kinds of models is a tree useful?  
Discrete sequential models.

What is an event?  
A subset of the sample space to which probability is assigned

Probabilities are in the range $[0, 1]$.

What are the probability axioms?
1. Nonnegativity: Given $A \subset \Omega, \pr{A} \ge 0$
2. Normalization: $\pr{\Omega} = 1$
3. Finite additivity: If $A \cap B = \varnothing$, then $\pr{A\cup B} = \pr{A} + \pr{B}$

What are some consequences of the probability axioms?
1. $\pr{A} \le 1$
2. Generalized finite additivity for disjoint events.
3. $\pr{A} + \pr{A^c} = 1$
4. If $A \subset B$, then $\pr{A} \le \pr{B}$
5. $\pr{A \cup B} = \pr{A} + \pr{B} - \pr{A \cap B}$
6. Union bound: $\pr{A \cup B} \le \pr{A} + \pr{B}$

What is the discrete uniform law?  
Given $\Omega$ of $n$ equally likely elements, and $A \subset \Omega, |A| = k$, then $\pr{A} = k \cdot \frac{1}{n}$

What are the steps of a probability calculation?
1. Specify the sample space
2. Specify a probability law (somehow)
3. Identify an event of interest
4. Calculate...

Strengthening of finite additivity axiom:
Countable additivity axiom:
If $A_1, A_2, A_3, \ldots$ is an infinite **sequence** of disjoint events, then $\pr{A_1 \cup A_2 \cup A_3 \cup \cdots} = \pr{A_1} + \pr{A_2} + \pr{A_3} + \cdots$

![](unit1lec1-probability-models\1d81db0ae5a72c5fdc2e7e07ec8a156e.png)

Geometric sum
infinite sum

Countable and uncountable.

Probabilities can be considered frequencies.  
Probabilities can describe beliefs, or betting preferences.

What is the role of probability theory?  
A framework for analyzing phenomena with uncertain outcomes
* rules for consistent reasoning
* used for predictions and decisions.

Statistics complements probability.

![relationship between probability theory and statistics](unit1lec1-probability-models\3a398f9f2f46087527ee73fc99827ee3.png)
