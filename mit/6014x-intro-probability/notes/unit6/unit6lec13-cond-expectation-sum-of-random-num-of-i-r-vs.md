---
section: 6
subsection: 13
type: lecture
title: Conditional expectation and variance revisited; Sum of a random number if independent r.v.s
---

# Lecture 13: Conditional expectation and variance revisited; Sum of a random number if independent r.v.s

$\newcommand{\cnd}[2]{\left.#1\,\middle|\,#2\right.}$
$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\cpr}[2]{\pr{ \cnd{#1}{#2} } }$
$\newcommand{\setst}[2]{\left\{#1\,\middle|\,#2\right\}}$
$\newcommand{\ex}[1]{\mathbf{E}\left[#1\right]}$
$\newcommand{\cex}[2]{ \ex{ \cnd{#1}{#2} } }$
$\newcommand{\var}[1]{\text{var}\left(#1\right)}$
$\newcommand{\cvar}[2]{ \var{\cnd{#1}{#2}} }$
$\newcommand{\d}{ \text{d} }$
$\newcommand{\iint}[2]{ \! #1 \,\d #2 }$
$\newcommand{\pmf}[2]{ p_{ #1 }\left( #2 \right) }$
$\newcommand{\cpmf}[3]{ \pmf{ \cnd{#1}{#2} }{#3} }$
$\newcommand{\pdf}[2]{ f_{ #1 }\left( #2 \right)}$
$\newcommand{\cpdf}[3]{ \pdf{ \cnd{ #1 }{ #2 } }{ #3 } }$
$\newcommand{\cdf}[2]{ F_{ #1 }\left( #2 \right)}$
$\newcommand{\if}{\text{if }}$
$\newcommand{\exp}{\text{exp}}$
$\newcommand{\norm}{\mathcal{N}}$
$\DeclareMathOperator{\exp}{exp}$
$\DeclareMathOperator{\cov}{cov}$
$\newcommand{\ninfty}{{-\infty}}$
$\newcommand{\abs}[1]{ \left|#1\right| }$


## Overview

* A more abstract version of the conditional expectation: $\cex{X}{Y}$
  * Viewing it as a random variable
  * **the law of iterated expectations**
* A more abstract version of the conditional variance
  * view it as a random variable
  * **the law of total variance**
* sum of a random number of independent r.v.s
  * mean
  * variance


## Conditional expectation of a random variable

Let $h$ be a function, say $h(x) = x^2$ for all $x$.

Given a random variable $X$, what is $h(X)$?  
$h(X)$ is the random variable that takes the value $x^2$ if $X$ happens to take the value $x$.

A random variable is the same thing. It takes a value and is that value in a numerical sense but also has the properties of a random variable, ie the values that it takes on are distributed according to a PDF/PMF.

function of a random variable is itself a random variable that takes the value of the random variable

What are the properties of a random variable?  
r.v.s have distributions, mean, variance, etc.

> Constants in functions can be replaced by variables parts and themselves be functions.

Recall the conditional expectation (for discrete; in cts case it is just an integral):
\[
\cex{X}{Y = y} = \sum_x x \cpmf{X}{Y}{ \cnd{x}{y} }
\]

Parameterize $Y$ and let the result be $g(Y)$. What is this?  
![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\f89d934d5c2436729276b84d4221a57e.png)

This is the abstract conditional expectation of $X$ given $Y$.

What it is vs what it's numerical value is

It is a fn of $Y$, and a random variable.

what it means to be a random variable - has a distribution (PMF or PDF), mean, variance, works as a random variable, etc.


## The law of iterated expectations

aka the [Law of total expectation](https://en.wikipedia.org/wiki/Law_of_total_expectation).


$\triangleq$ - equal to by definition

Because the function $g(y)$ exists we can define the function $g(Y)$ which is the random variable.

$\cex{X}{Y} \triangleq g(Y)$

\[
\begin{align}
\ex{\cex{X}{Y}} &= \ex{g(Y)}\\
&= \sum_y g(y) \pmf{Y}{y} &&\text{by expected value rule}\\
&= \sum_y \cex{X}{Y = y} \pmf{Y}{y} &&\text{by total expectation thm}\\
&= \ex{X}
\end{align}
\]

therefore $\ex{\cex{X}{Y}} = \ex{X}$.

It is the same in continuous cases just using integral/PDF where necessary.

The law of iterated expectations is just an abstracted version of the total expectation theorem. This is powerful as it turns out and avoids having to deal with discrete or cts r.v.s individually.


## Stick-breaking revisited

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\166c671da54f742a7dc0c77bb9222626.png)

In the first case $\cex{X}{Y = y} = \frac{y}{2}$ this is an equality between numbers whereas $\cex{X}{Y} = \frac{Y}{2}$ is an equality between random variables.


## Forecast revisions

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\111d091d103d08a110f12417c0caabf2.png)


## The conditional variance as a random variable

Looking first at a case where $Y = y$:

\[
\var{X} = \ex{(X - \ex{X})^2}\\
\cvar{X}{Y = y} = \cex{(X - \cex{X}{Y = y})^2}{Y = y}
\]

In the latter case everything is the same the conditional universe just goes down into the inner expectation.

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\e596ab8ed3da6346b68cc864bd3414ad.png)

expected value being a random variable means it has its own variance.

> You can have unspecified functions on the conditional universe in a sub-expression because they are resolved later by specification in the outer expression, but you can't have an un-conditioned internal expression.


## Derivation of the law of total variance

Not insightful but there are interesting manipulations.

Want to show $\var{X} = \ex{\cvar{X}{Y}} + \var{\cex{X}{Y}}$
\[
\begin{align}
\cvar{X}{Y = y} &= \cex{X^2}{Y = y} - (\cex{X}{Y = y})^2 && \text{for all $y$}\\
\cvar{X}{Y} &= \cex{X^2}{Y} - (\cex{X}{Y})^2\\
\ex{\cvar{X}{Y}} &= \ex{\cex{X^2}{Y}} - \ex{(\cex{X}{Y})^2}\\
&= \ex{X^2} - \ex{(\cex{X}{Y})^2}\\
\var{\cex{X}{Y}} &= \ex{(\cex{X}{Y})^2} - (\ex{\cex{X}{Y}})^2\\
&= \ex{(\cex{X}{Y})^2} - (\ex{X})^2\\
\ex{\cvar{X}{Y}} + \var{\cex{X}{Y}} &= \ex{X^2} - (\ex{X})^2
\end{align}
\]

as required.


## A simple example

Drill for using law of iterated expectations to find some $X$.

Given an $X$ described by this graph:

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\fd5007462643e0a820a19ed14966c561.png)

we want to divide and conquer. Let $Y = 1$ be the case where $X$ falls in the left square and $Y = 2$ be the case where it falls in the right rectangle, then

\[
\cex{X}{Y} = \begin{cases}
\cex{X}{Y = 1} = \frac{1}{2} & \text{w.p. $\frac{1}{2}$}\\
\cex{X}{Y = 2} = 2 & \text{w.p. $\frac{1}{2}$}
\end{cases}
\]

using the relative area of each to determine the probability and our knowledge about uniform distributions to identify the expected value. Since the whole conditional expectation has been identified we can calculate things about it, like

\[
\ex{\cex{X}{Y}} = \frac{1}{2}\cdot\frac{1}{2} + \frac{1}{2}\cdot 2 = \frac{5}{4}
\]

and by the law of iterated expectations this is exactly $\ex{X}$.

Now for the variance,

\[
\cvar{X}{Y} = \begin{cases}
\cvar{X}{Y = 1} = \frac{1}{12} & \text{w.p. $\frac{1}{2}$}\\
\cvar{X}{Y = 2} = \frac{2^2}{12} = \frac{4}{12} & \text{w.p. $\frac{1}{2}$}\\
\end{cases}
\]

so

\[
\ex{\cvar{X}{Y}} = \frac{1}{2} \cdot \frac{1}{12} + \frac{1}{2}\cdot\frac{4}{12} = \frac{5}{24}
\]

finally,
\[
\var{\cex{X}{Y}} = \frac{1}{2}\left(\frac{1}{2} - \frac{5}{4}\right)^2 + \frac{1}{2}\left(2 - \frac{5}{4}\right)^2 = \frac{9}{16}
\]

which can be plugged into the law of total variance to get $\frac{37}{48}$.


## Section means and variances

Intuition-building. Showing how we may go through a calculation

Information and data given

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\7a4d6efd0a2354ae1389775f46c583af.png)

If we just do an average over the whole class we get
![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\a984fc3003207555314076311e03b252.png)

Now dividing and conquering
![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\6fb0eafd62db7ac2df584e2690bbefd7.png)

It is essentially a calculation of the average across the entire class.

now for total variance
![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\32154c83b34bbec6e0c595b53eb5e8f0.png)

The explanation at the bottom has it. The variance in the class is broken into two sources, within the sections themselves and between the two sections.


## Mean of the (sum of (a random number of r.v.s))

> When faced with a complicated problem, try to condition on some information that will make the problem easier.

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\2b6212964903f565c536ac38ce01c96c.png)

which makes intuitive sense, but it is always good to have a formal derivation.

Note that we can turn the equalities between numbers into an equality between r.v.s because the equations are true for any choice of value from the space of the r.v.s involved.

Assuming the $X_i$ are identically distributed makes things much simpler.


## Variance of the (sum of (a random number of r.v.s))

![](unit6lec13-cond-expectation-sum-of-random-num-of-i-r-vs\ca45bef07209c2a24077aeae1c3c52a5.png)

Demonstrates the power of the law of total variance because this would be hard to come up with intuitively.
