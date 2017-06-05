---
section: 9
subsection: 21
type: lecture
title: The Bernoulli Process
---

# The Bernoulli Process

## Overview

The Bernoulli Process is the simplest nontrivial stochastic process.

* Definition
* Stochastic processes
* Basic properties (memorylessness)
* The time of the $k$th success/arrival
* Distribution of interarrival times (time between arrivals)
* Merging and splitting
* Poisson approximation (large number of time slots with a small probability of arrival) for binomial PMF

> *regime* - a system or planned way of doing things, especially one imposed from above

> *asymptotic* - relating to limiting behavior

## The Bernoulli Process

Recall bernoulli r.v.s.

![](unit9lec21-the-bernoulli-process\9c57ada947d6108a964ace0eeb155af5.png)

## Stochastic Process

A stochastic process is a sequence of r.v.s

an infinite sequence

To fully describe a stochastic process it is required to state information about the individual r.v.s

Information like the mean, variance, and PMF.

we also need to specify how these r.v.s interact with one another, what is their joint PMF. It isn't enough to describe $n$ of them, we need to be able to describe it no matter what $n$ is.

second view:
collection indexed by an index that keeps increasing, like a process that evolves over time

sample space
experiment runs in time
![](unit9lec21-the-bernoulli-process\1204fb21b91c909f2caec2fbeda7abd2.png)

$\Omega$ = set of all infinite sequences of 0s and 1s

![stochastic process calculation example](unit9lec21-the-bernoulli-process\d8b1bcb41871fae141f8d500edc2b4cf.png)

How to calculate properties of a stochastic process as it evolves over infinite times.

Bound above and push it down.

0 is the only thing smaller than an arbitrarily small number

Sometimes we'll think of it one way, and other times we'll think of it another way.

## Bernoulli process review

![](unit9lec21-the-bernoulli-process\30a351ef2b9fc7bcab48276dc242725a.png)

time until first success/arrival

![](unit9lec21-the-bernoulli-process\ce430abe1d62b523c837b0a19d0e0370.png)

geometric r.v.

## Independence, memorylessness, and the fresh-start properties

Your friend starts watching a bernoulli process until time $n$, then you come into the room. What do you see? You see a process $Y_i$ which has the properties of being independent and being bernoulli. It gets a fresh start after time $n$.

![](unit9lec21-the-bernoulli-process\ec03348d2daba1cf971cb74ae2816b76.png)

Now consider the case where your friend waits until after the first success before bringing you into the room. What do you see? The same thing. The $\{Y_i\}$ are independent of the $X_1,\ldots,X_{T_1}$
![](unit9lec21-the-bernoulli-process\39234e04d826181b1d9e5b7729f688f0.png)

![](unit9lec21-the-bernoulli-process\fe50ff6c2c9096c94cb757b3bcc5aff8.png)

The process $X_{N+1}, X_{N+2}, \ldots$ is:
* a Bernoulli process
* independent of $N, X_1, \ldots, X_N$

But the similarity in these examples is the fact that $n$ was *causally* determined. That is that it was determined by something watching for some event and telling you about it without prior knowledge.

A non-causal $N$ would be like if your friend knew that some specific sequence was going to come up and called you ahead of time to watch. When this happens what you see does not conform to the description of a bernoulli process.

e.g. $N$ is the time just before the occurrence of 1,1,1

so the conclusion is that the above holds only when $N$ is causally determined.

causally can also mean "only influenced by past events"

...skipped...

## Merging of Bernoulli Processes

As an example consider a server receiving traffic from multiple sources, $X_t \sim$ bernoulli($p$) and $Y_t \sim$ bernoulli($q$). How do we merge them?

assume they are independent.

The argument is that the merged process is a bernoulli process with a certain parameter.

![images of the two bernoulli processes and their merged version](unit9lec21-the-bernoulli-process\3b557897e79657e38314a2921ad33c5f.png)

the merged version has an arrival whenever either of the others has an arrival.

To show it is bernoulli we need to show that $Z_t$ is independent of $Z_{t+1}$. $Z_t = g(X_t, Y_t)$ and $Z_{t+1} = g(X_{t+1}, Y_{t+1})$. Since the individual ones are bernoulli the $t$ and $t+1$ timesteps are independent, and we also assumed that the individual processes were independent of each other so $Z_t$ is independent of $Z_{t+1}$. This shows pairwise independence but we need independence of the whole collection $Z_1, \ldots, Z_t$ which can be derived with a similar argument.

![chart of probabilities of arrivals in the merged process](unit9lec21-the-bernoulli-process\f232c4116034ee864d64c840aad6da11.png)

The probability of the green region is $1 - (1 - p)(1 - q)$ which expands to $p + q - pq$, so the merged process is bernoulli($p + q - pq$) across each time step. This + independence establishes that this is a bernoulli process.

Given an arrival in the merged process, what is the probability that there was an arrival in the first process?

It's the probability of $pq + p(1 - q) = p$ over the total probability of the arrival occurring which was calculated above $p + q - pq$.

Imagining merged bernoulli processes (two processes governing arrivals to a queue) doesn't mean there would be 2 arrivals in a single time step.

todo: review ex 1
