---
section: 4
subsection: 5
type: lecture
title: Probability Mass Functions
---

# Lecture 5: Probability Mass Functions

$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\cpr}[2]{\mathbf{P}\!\left(#1\,\middle|\,#2\right)}$
$\newcommand{\setst}[2]{\left\{#1\,\middle|\,#2\right\}}$
$\newcommand{\ex}[1]{\mathbf{E}\left[#1\right]}$
$\newcommand{\pmf}[2]{p_{#1}\left(#2\right)}$


## Overview

* Random variable
* Discrete Random variables
* Probability mass function
* Random variable examples
	* Bernoulli
	* Uniform
	* Binomial
	* Geometric
* Expectation (mean)
	* Expected value rule
	* Linearity


What is a random variable?  
A numerical quantity that takes random values.

A random variable is a function on a sample space that assigns to each element a real number.

> Notation: Big letter (e.g. $X$) for the random variable, little letter (eg $x$) for the numerical value.

What is the difference between a discrete and continuous random variable?  
The types of the inputs to the function.

Several random variables can be determined for a probabilistic experiment.

A function of random variables is itself a random variable.

Random variables have numerical values determined by the outcome of an experiment, as in, there is the random variable and the numerical value and the numerical value is the output given some input.

## Probability Mass Function

PMF is a:
* probability law
* probability distribution of a discrete r.v.

Keywords:
* Event
* Probability

> Notation: $\pmf{X}{x} = \pr{X = x} = \pr{\setst{w\in \Omega}{X(w) = x}}$

This is the probability mass function

Properties:
* Nonnegative: $\pmf{X}{x} \ge 0$
* Normalization: $\sum_x \pmf{X}{x} = 1$

So the probability mass function assigns probabilities to specific outputs of random variables based on the likelihood given the number of events that could have caused it (for the d.r.v. case)

What does it mean to find a PMF $\pmf{X}{x}$?  
It means to calculate the probability for each output.

> Pay attention to what terms of an expression actually are, e.g. random variable, constant

What are the attributes of a PMF that help characterize it?
* Parameters - variables that fully determine the behavior of a PMF
* Motivating experiment - what simple situation can a PMF help to model?
* Sample space
* Meaning of the random variable - "number of heads", "number of tosses before a head", similar to experiment above.
* What kinds of situations a PMF can be used to model - generalization of the motivating experiment
* The form of the function itself - mathematically
* The plot

To **describe** a random variable is to touch on the points above, and be able to derive what you don't know from the form of the experiment.

What are useful things to know about PMFs?
* Expected value
* Variance
* specific useful situations

## Bernoulli and indicator random variables

Describe a **Bernoulli random variable**.  
A Bernoulli random variable:
* Parameter: $p$, probability of success (1)
* Can take values 0 or 1
* Is given by the expression:
\[
X = \left\{\begin{array}{l}
0, &\text{w.p. $1 - p$}\\
1, &\text{w.p. $p$}
\end{array}\right.
\]
* Models success/failure or heads/tails experiments.

What is an indicator?  
A bernoulli random variable that is 1 if a specific event occurs.

What are indicators useful for?  
They allow translating manipulating events into manipulations of random variables. (Example??)

## Uniform random variables

Describe **uniform random variables**.
* Parameters: $a, b$ with $a \le b$. These can be the events themselves (for some experiment with numerical output) or they can simply be the min and max elements of some indexing set on the set of events.
* Models: Complete ignorance. Everything has the same probability.
* Motivating experiment: Pick one of $a, a + 1, \ldots, b$ at random, all equally likely.
* Sample space: $\{a, a+1, \ldots, b\}$, $b - a + 1$ possible values.
* Random variable: $X: X(\omega) = \omega$

> When the outcome of an experiment is a number, it can be easier to think of the events themselves as the random variables instead of having a dummy random variable in-between.

![plot of uniform random pmf](unit4lec5-probability-mass-functions\8faf6b847b3b6f4ca0a9e2d71a1bb086.png)

When $a = b$, it is a constant/deterministic. It takes a single value with probability 1.

![](unit4lec5-probability-mass-functions\7e18762a5b324f7ccf803f305eeec148.png)


## Binomial random variable

TODO: Relate this back to counting

What are the parameters of a binomial random variable?  
$n, p\in [0, 1]$

Experiment: $n$ independent tosses of a coin with $\pr{\text{heads}} = p$

Sample space: Set of sequences of H and T, of length $n$.

Random variable $X$: number of heads observed

Model of: number of successes in a given number of independent trials

The formula is something we've seen before:

\[
\pmf{X}{k} = \binom{n}{k}p^k(1-p)^{n-k}, \text{ for } k = 0, 1, \ldots, n
\]

The number of ways $k$ items could be chosen from $n$ items multiplied by the probability of that actually happening.

Binomial PMF examples with fair and unfair coins:

![](unit4lec5-probability-mass-functions\9d2618565021c7b4a27f49ac941c7531.png)

Any success/failure scenario can be modeled using binomial random variable, if even a subset is required.

## Geometric random variables

Parameters: $p$ where $0 < p \le 1$.

Experiment: Infinitely many independent tosses of a coin $\pr{\text{Heads}} = p$

> Independence of infinitely many events is just the statement that any finite subset is independent.

Sample space: Set of infinite sequences of H and T.

Random Variable $X$: Number of tosses until the first heads

Model of: waiting times, number of trials until a success

"Trial" can be very loosely interpreted.

PMF: $\pmf{X}{k} = (1 - p)^{k - 1}p$ for $k = 1, 2, 3, \ldots$

![](unit4lec5-probability-mass-functions\c02922720510028e591c83ac456fdb0d.png)

What is a tricky aspect of this PMF?
Not well defined for $\pr{\text{no heads}}$.

But this can be handled by bounding the probability by $\pr{TTT\cdots}$ and then take $k$ to be arbitrarily large.

## Expectation

\[
\ex{X} = \sum_x x \pmf{X}{x}
\]

Interpretations:
* the average in a large number of independent repetitions of the experiment
* the weighted average of the possible outcomes
* the "center of mass" of the graph

If we have an infinite sum then it needs to be well-defined. For this course we assume $\sum_x x \pmf{X}{x} < \infty$, that the sum is absolutely convergent and so is well-defined.

Given $X$ is a bernoulli random variable, what is its expected value?  
$\ex{X} = p$

Given $X$ is an indicator of event $I_A$, $X = I_A$  
$\ex{I_A}= \pr{A}$

Expectation of a uniform random variable:
\[
\begin{align}
\ex{X} &= 0\cdot\frac{1}{n + 1} + 1\cdot\frac{1}{n + 1}+\cdots+n\frac{1}{n + 1}\\
&= \frac{1}{n + 1}(0+1+\cdots +n)\\
&= \frac{1}{n + 1} \cdot\frac{n(n+1)}{2}\\
&= \frac{n}{2}
\end{align}
\]

which is exactly the center of the PMF. This illustrates that the expected value can be considered the center of mass of the PMF, the place where you would be able to balance the graph.

Another interpretation of expectations:
Kind of like a population average.

## Properties of expectation

* If a random variable is non-negative (i.e. for all possible experiment outcomes) then its expected value is non-negative.
* If $a \le X \le b$, then $a \le \ex{X} \le b$, by the definition of the expected value.
* If $c$ is a constant, $\ex{c} = c$. A constant here is like a r.v. that takes one possible value with probability 1.

> A constant can be thought of as a r.v. of a very degenerate type.

## The expected value rule

For calculating expected values of the form $\ex{g(X)}$.

![](unit4lec5-probability-mass-functions\456ca9fd91718d84a3d8ae1ba143af2a.png)

You can  think of it as averaging over $x$ or averaging over $y$.

## Linearity of expectations

![](unit4lec5-probability-mass-functions\1f8e362345286d28bb6089526e29c994.png)

This is the case where $\ex{g(x)} = g(\ex{x})$

# Followup questions

1. Name four types of discrete random variables and describe each.
2. What is expectation?
3. What are the properties of expectation?
