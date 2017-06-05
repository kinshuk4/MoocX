---
section: 10
subsection: 25
type: lecture
title: Steady state behavior of Markov chains
---

# Steady state behavior of Markov chains

$\newcommand{\cnd}[2]{\left.#1\,\middle|\,#2\right.}$
$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\cpr}[2]{\pr{ \cnd{#1}{#2} } }$

## Overview

We will examine the questions:
* does this markov chain converge?
* is the state to which is converges dependent on initial state?

as suspected, we will see these relate to the presence of aperiodicity and a single recurrence class.

Review of markov chains then examine steady-state behavior including
* recurrent, transient states, recurrence classes
* periodic states
* convergence theorem
* balance equations

Then we'll look at birth-death processes

## warmup

finding the probability of a specific path through the state transition is just a matter of multipleing the individual state transition probabilities.

Finding multiple paths in a brute force way involves coming up with all the paths of a certain length that start and end at the given states.

![](unit10lec25-steady-state-behavior-of-markov-chains\0ce069bb30d049d7154ace3548046695.png)

## Recurrent and transient states

> review previous lecture

it's pretty clear that the class a chain starts in implies something about where it may end up

## Periodicity

The states in a recurrent class are periodic if they can be grouped into $d > 1$ groups so that all transitions from one group lead to the next group.

![state transition diagram with periodic states in two groups](unit10lec25-steady-state-behavior-of-markov-chains\0eff2ce6cff773a34ad9160610afaaeb.png)

![state transition diagram with periodic states in three groups](unit10lec25-steady-state-behavior-of-markov-chains\1ec6d511f507ed6bf42dcbbf53b71600.png)

![example of identifying the groups in a periodic state transition diagram](unit10lec25-steady-state-behavior-of-markov-chains\cfef284474dfe015d8fd4eb72ed91f95.png)

as soon as you have a self transition, the markov chain is not periodic since no matter what that state is colored it would be possible to have two consecutive timesteps with that group.

## Steady-state convergence

The central question we want to answer is, does
\[
r_{ij}(n) = \cpr{X_n = j}{X_0 = 1}
\]
converge to some specific $\pi_j$?

We have seen two situations that prevent this from happening:

1. periodicity implies it will not converge
2. multiple recurrence classes means if it converges it will be dependent on choice of $i$

![example of state transition diagram with 2 recurrence classes](unit10lec25-steady-state-behavior-of-markov-chains\9e75e51dcff6953d8abefe4259866258.png)

![example of 1 periodic recurrence class](unit10lec25-steady-state-behavior-of-markov-chains\e65dd65738fb6819c498259de4e5ba23.png)

![example of 1 aperiodic recurrence class](unit10lec25-steady-state-behavior-of-markov-chains\48fea129f567436a28df101553e00ce5.png)

These are necessary but we have a theorem that these are actually sufficient conditions for saying $r_{ij} \to \pi_j$ if
1. recurrent states are all in a single class
2. the single recurrent class is not periodic

<!--Not proven here but the general idea is that if you have a single recurrent class then an initial starting condition will either be in the class or not. If it is in the class then it will stay in the class, if it is not in the class then it is in a transient state and at some point it will take the path that does not lead back to this state or set of states. This applies for all transient states. So then it is in the recurrent state.-->

Given two copies these copies will eventually meet in a given state at a given time with probability 1.

Assuming the theorem holds, start from the $r_{ij}$ and take the limit of both sides as $n \to \infty$:

\[
\begin{align}
r_{ij}(n) &= \sum_{k=1}^m r_{ik}(n-1) p_{kj}\\
\pi_j &= \sum_k \pi_k p_{kj}
\end{align}
\]

And so we end up with $m$ equations and $m$ unknowns. But the system of equations is singular and our solutions may not be unique, (e.g. $\pi_j = 0$ for all $j$ is a solution but obviously not what we want). The solution is to look at the $\pi_j$ as probabilities and  impose the natural constraint that $\sum_j \pi_j = 1$, which is essentially the statement that the probability that the chain will end up in some state we are considering is equal to 1 (which makes sense because where else would it end up?)

To solve exactly with matrices:

re-interpret eqns as $\sum_k \pi_k p_{kj} - \pi_j$, so get the augmented matrix

\[
\begin{bmatrix}
p_{11} - 1 & p_{21} & \cdots & p_{m1} & 0\\
p_{21} & p_{22} - 1 & \cdots\\
\vdots & \vdots & \ddots\\
p_{m1}\\
1 & 1 & \cdots & 1 & 1
\end{bmatrix}
\]

## Example

![example of solving for pi_j for small 2-node markov chain](unit10lec25-steady-state-behavior-of-markov-chains\9101e4dd3fb8dea5a0add65964cef88d.png)

todo: ex 4, wait for install to finish

## Frequency interpretations

Balance equations
\[
\pi_j = \sum_k \pi_k p_{kj}
\]

visit frequency

particle analogy- observers looking at the state of a particle.

We can think of the $\pi_j$ variables directly as being frequency of being in state $j$.

![](unit10lec25-steady-state-behavior-of-markov-chains\dc7de4299cd1b29dcbc8e950039a3368.png)

given $y_j(n)$ is the number of times $j$ was observed to be the state up to time $n$.

But where does the sum come from? Let's look at the frequency of transitions from a specific states to a given state, e.g. $1\to j: \pi_1 p_{1j}$

![](unit10lec25-steady-state-behavior-of-markov-chains\81d0be0a26c7b749e8bd1f8f171744e6.png)

where $z_{1j}(n)$ is the number of times the particle went from state 1 to state $j$.

Using this, we can consider how to calculate the total frequency of all transitions into $j$, it is just given by $\sum_k \pi_k p_{kj}$.

Notice that the particle will be in state $j$ if and only if it was just transitioned to state $j$, so from that perspective we can argue that the $\pi_j$ and sum above are equal.

This is just another intuition for the balance equations, and it also gives us a shortcut into analyzing markov chains.

# Birth-death processes

![illustration of a full birth-death process state transition diagram](unit10lec25-steady-state-behavior-of-markov-chains\9cd1d710a94b2e6b9c0c259d0e8276d8.png)

A single link in the chain (at position $i$)

![](unit10lec25-steady-state-behavior-of-markov-chains\62dc55f742560eb95e2e43d63581ab72.png)

These chains can be used to model a number of situations, like the checkout counter example we did initially.

One key difference from that example is that the state probabilities here can be dependent on the specific state. This can help model even more situations like:
* disease spread
* population growth

1. cut the chain in half
2. zoom in on two specific states
3. note that the number of times that you can transition from one side to another must be similar, if you go up $n$ times, you must have gone down $n-1$, $n$, or $n+1$ times (depending on which side you started and whether the side you started on is the side you are coming back to).

![](unit10lec25-steady-state-behavior-of-markov-chains\e1cfb718037e58d27ec2b73669bd443c.png)

because of this we can make the statement

\[
\pi_i p_i = \pi_{i+1} q_{i+1}
\]

then we see

\[
\pi_{i+1} = \pi_i \frac{p_i}{q_{i+1}}, \text{ for $i = 0, 1, \ldots$}
\]

This gives us a way to solve for $\pi_{i+1}$ given $\pi_i$. So we could recursively solve for all if given $\pi_0$, but how do we get $\pi_0$?

Recall our normalizing condition, $\sum_j \pi_j = 1$, then notice that we can rewrite the sum using only $\pi_0$:

\[
\begin{align}
\sum_j \pi_j &= 1\\
\pi_0 + \pi_0\frac{p_0}{q_1} + \pi_0\frac{p_0 p_1}{q_1 q_2} + \cdots &= 1\\
\pi_0 \left[\frac{p_0}{q_1} + \frac{p_0 p_1}{q_1 q_2} + \cdots \right] &= 1
\end{align}
\]

So we solve this for $\pi_0$ then use it to solve for the rest.

## Birth-death processes 2

We now examine the special case where $p_i = p$ and $q_i = q$.

![](unit10lec25-steady-state-behavior-of-markov-chains\3731dbcaa83fd6a4d01cd97bfb49a398.png)

![](unit10lec25-steady-state-behavior-of-markov-chains\276717ae3f555c99e3c321c3647f0e02.png)

Symmetric random walk.

now assume $p < q$ and $m \approx \infty$

![](unit10lec25-steady-state-behavior-of-markov-chains\7564fffccbdffd091a1591b35278c950.png)

shifted geometric series to start at 0

first and simplest model encountered when studying queueing theory.

$\ex{X_n} = \frac{\rho}{1-\rho}$

as long as $\rho < 1$ then you will have a finite number of customers, but as soon as $\rho$ begins to approach 1 the number of customers in the system at any time will be very large. load factor 0.99.
