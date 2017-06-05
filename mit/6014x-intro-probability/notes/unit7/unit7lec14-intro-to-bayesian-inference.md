---
section: 7
subsection: 14
type: lecture
title: Intro to Bayesian Inference
---

# Lecture 14: Intro to Bayesian Inference

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
$\newcommand{\Th}{\Theta}$
$\newcommand{\th}{\theta}$
$\newcommand{\Thh}{\widehat{\Theta}}$
$\newcommand{\thh}{\widehat{\theta}}$
$\newcommand{\unfrm}[2]{ \mathcal{U}\left( #1, #2 \right) }$


## Overview

* The big picture
  * Motivation, applications
  * problem types
    1. hypothesis testing
    2. estimation

* The general framework
  * Bayes rule to find the posterior distribution of an unknown based on some other known value.
  * different versions depending on whether the random variable is discrete or continuous
  * get a numerical estimate of the unknown random variable (place where it is largest or the mean of the posterior, MAP, LMS)
  * measure performance of the estimate (probability of error, mean squared error)
  * lots of examples to help with discipline and confidence in application.

## Overview of some application domains

This is a big picture we've seen before:

![](unit7lec14-intro-to-bayesian-inference\2d09b9c83e0143ee81752d6a07001d61.png)

Recall that inference/statistics works to generate models from **data**. Models are then analyzed using probability theory.

The prevalence of data in the modern world has changed what statisticians can do. More data and more computing power means bigger models with more parameters.

Some examples where inference is used:
* Polling
* Marketing, advertising - recommender systems like Netflix
* Finance
* Life sciences - analyzing genomic data to determine disease causes

Modeling/monitoring:
* the ocean
* global climate
* pollution

physics and astronomy

signal processing - reducing and accounting for noise in different applications

Regardless of the application, the approach is the same.

## Types of inference problems

Inference/statistics may be use for 2 purposes:
* model building
* inferring unobserved variables

in the latter case the model is given to us but is incomplete.

Example:
Some signal goes through a medium, has some noise added to it, and is received as $X$ by a sensor.

![](unit7lec14-intro-to-bayesian-inference\031282c085b97061bccf63e548493e42.png)

and we're given the relationship $X = aS + W$. The two questions reasonable to ask here are:

1. Given $S$ and $X$, what is the medium (i.e. find $a$) - this is model bulding
2. Given $a$, $X$, what was the original signal $S$? - this is variable estimation

The interpretation is different but the math is the same.

Hypothesis testing problems
unknown is categorical/discrete valued-answer problem - unknown has a few possible values and the idea is to make bad decisions with low probability.

Estimation problem:
- numerical unknown, e.g. linear regression
- want an estimate that is close to the truth

## The Bayesian inference framework

$\Th$ unknown r.v.

What is the primary difference between the bayesian framework and other frameworks?  
In the bayesian framework the unknown variable is treated as a random variable instead of a constant.

What is the "prior distribution" in the bayesian framework?  
The distribution of the unknown. $p_\Th$ or $f_\Th$, our beliefs about the unknown before obtaining data.

Then we get some observation $X$ and we generate an observation model $p_{X|\Th}$ or $f_{X|\Th}$

![](unit7lec14-intro-to-bayesian-inference\bfd30e1c8ac6b0181d0c9760832e000c.png)

Where do we get the prior distribution?  
* symmetry argument - we can assume all possibilities are equally likely
* range - known range, behavior
* past knowledge about distribution of $\Th$ (prior studies)
* arbitrary, statistician's choice.

What is the output of Bayesian inference?  
A posterior distribution of the form $\cpmf{\Theta}{X}{\cnd{\cdot}{x}}$ or $\cpdf{\Theta}{X}{\cnd{\cdot}{x}}$.

![](unit7lec14-intro-to-bayesian-inference\a64116d5469a038c61827341fcf2b204.png)

Example: electoral votes.

We could say simply some probability that a particular person will win, but a more complete result would look like

![](unit7lec14-intro-to-bayesian-inference\cdcc0ba6e23184a829e7493557928e84.png)

This is useful but there may also be some estimation and summarization done after the posterior is defined:
* point estimates
* error analysis

Asked to provide a single guess about what $\Theta$ is, how to proceed?
* maximum a posteriori probability (MAP) rule - report the largest value
\[
\begin{align}
\cpmf{\Th}{X}{ \cnd{\th^* }{x} } &= \max_\th \cpmf{\Th}{X}{\cnd{\th}{x}}\\
\cpdf{\Th}{X}{ \cnd{\th^* }{x} } &= \max_\th \cpdf{\Th}{X}{\cnd{\th}{x}}\\
\end{align}
\]

We find the $\theta$ over all $\Theta$ that maximizes the posterior distribution.

We may also want to give the mean of the posterior, which is the conditional expectation estimator. Least Mean Squares (LMS):

\[
\cex{\Th}{X = x}
\]

which gives you the smallest mean squared error

The number produced is an estimate. $\thh = g(x)$ based on the data $x$ which is processed by $g$. Given we don't know $x$, then we have what is called an **estimator**, a **random variable** on $X$, $\Thh = g(X)$.

Estimator may also be used to refer to the function $g$ doing the processing on the data.

Notice that these two things are different. The estimate is a number given certain data and the estimator is the rule used to process the data.

We know what it takes to calculate conditional distributions and we have 2 estimators, so we just need to do examples.

## Discrete Parameters, discrete observation

Situation: Incoming data ($X$) is discrete and we want to generate a model over discrete outputs. The values of $\Th$ can be considered alternative hypotheses.

Versions of Bayes rule for this case:

![](unit7lec14-intro-to-bayesian-inference\e381a1caf27af4e254b55a25854642d5.png)

 Refer back to bayes rule lectures for info on how to determine distribution, now just assume we have observed $X$ and already have the conditional PMF of $\Theta$.

 Suppose $\Theta$ is

 ![](unit7lec14-intro-to-bayesian-inference\c2a149807003109ced079ff9e55a147c.png)

We want to come up with an **estimate** using our two estimators:

1. MAP rule: $\thh = 2$
2. LMS rule = $\thh = \cex{\Th}{X = x} = 2.2$

We also want to determine our error. There are two places specifically where errors can originate:

1. Our estimate - conditional probability of error
2. Our estimator - overall probability of error

First, our estimate. Recall that $\Theta$ is a hypothesis, a r.v. that can take on values $\theta$ from some discrete set with a certain probability. When we estimate or summarize by taking the most likely outcome, there's still the chance that the outcome itself is something else. So this **conditional probability of error** is the probability of selecting a hypothesis that turns out to be wrong, given by

\[
\cpr{\thh \neq \Theta}{X = x}
\]

For the MAP rule, what is the probability that something other than our chosen $\thh$ occurs? 0.4. It's the sum of all other possibilities.

By thinking through what happens if we were to choose other values we can determine a property of the MAP rule which is that is minimizes this conditional probability of error.

Now, the estimator. overall probability of error is the probability that our estimator is different than the actual parameters, $\pr{\Thh \neq \Theta} = \sum_X \cpr{\Thh \neq \Theta}{X = x} \pmf{X}{x}$ by the total probability theorem.
Weighted probability of error. Another formulation just uses $\sum_\theta \cpr{\Thh \neq \Theta}{\Theta = \theta} \pmf{\Theta}{\theta}$. Use whichever is convenient. Why does the second formulation make sense?

Since each term of the sum is as small as possible it means the sum is as small as possible under the MAP rule. So it's optimal.

## Discrete parameter, continuous observation

Applicable version of the Bayes rule:

![](unit7lec14-intro-to-bayesian-inference\1563233f6dfaa24d30f8ab74cbbe2435.png)

Example:

We send signal $\Theta \in \{1, 2, 3\}$, letting $X = \Theta + W$ and $W \sim N(0, \sigma^2)$ indp. of $\Theta$.

We want to get the different parts required for Bayes rule, so start by noticing that given $\Theta$, $X$ is just a constant + $W$. and we have

![](unit7lec14-intro-to-bayesian-inference\a39a2b54aff1a182140680ddf2a8315b.png)

giving us

![](unit7lec14-intro-to-bayesian-inference\9c48b38d6762c17aa1f56642c6b8b4ab.png)

Estimate:
* MAP rule: $\thh = 2$

Conditional probability of error:
* $\cpr{\thh \neq \Th}{X = x}$ - which is smallest under the map rule

Overall probability of error:
\[
\begin{align}
\pr{\Thh \neq \Th} &= \int \iint{\cpr{\Thh \neq \Th}{X = x}\pdf{X}{x}}{x} && \text{or}\\
&= \sum_\th \cpr{\Thh \neq \th}{\Th = \th}\pmf{\Th}{\th}
\end{align}
\]

Since the MAP rule makes the probability of each as small as possible, we can conclude that it minimizes the conditional probability of error.

## Continuous parameter, continuous observation

Related bayes rule

![](unit7lec14-intro-to-bayesian-inference\090b1260bc4df2564e239007bc28d438.png)

Linear normal models - combine r.v.s in a linear function, and all r.v.s are known to be normal. Ex:
- we have a noisy signal $\Th$. what we receive is given by $X = \Th + W$ and we wish to recover $\Th$.
also multi-dimensional versions where $\Theta$ is a vector of multiple components and there are many measurements $X$. Detailed study later.

estimating parameter of a uniform

$X: \unfrm{0}{\Th}$

$\Th: \unfrm{0}{1}$

Given we don't know the range of $X$ but we have observations about it, we want to estimate $\Theta$.

Like before we want to come up with estimators to find some $\Thh$, and we can use MAP, LMS just the same. We also want to characterize performance of estimators. One way of doing that is with squared distance
Given an estimator based on some data

\[
\cex{\left(\Thh - \Th\right)^2}{X = x}
\]

Or we can check over all possible $x$ to get the overall performance, unconditional mean squared error $\ex{\left(\Thh - \Th\right)^2}$. Talked about later.

Since LMS is the mean, the mean squared error is just the conditional variance.

> Second moment of the exponential distribution? In qq.

## Inferring the unknown bias of a coin

Example demonstrates solving for a continuous unknown parameter and discrete observations.

Bayes rule here:

\[
\begin{align}
\cpdf{\Th}{K}{\cnd{\th}{k}} &= \frac{\pdf{\Th}{\th}\cpmf{K}{\Th}{\cnd{k}{\th}}}{\pmf{K}{k}}\\
\pmf{K}{k} &= \int \iint{ \pdf{\Th}{\th^\prime}\cpmf{K}{\Th}{\cnd{k}{\th^\prime}}}{\th^\prime}
\end{align}
\]

The point is to generate an expression that can take in data ($k$ heads in $n$ flips) and give information about the possible bias of the coin.

Given biased coin find the posterior distribution.

The standard example uses:

* coin with bias $\Th$, which has prior $\pdf{\Th}{\cdot}$
* for the below, fix $n$ and let $K$ be the number of heads.
* Assume $\pdf{\Th}{\cdot} \sim \unfrm{0}{1}$

Find parts of Bayes rule, assuming $\th \in [0, 1]$

\[
\begin{align}
\cpdf{\Th}{K}{\cnd{\th}{k}} &= \frac{\color{blue}{1\cdot\binom{n}{k}}\color{red}{\th^k(1 - \th)^{n - k}}}{\color{blue}{\pmf{K}{k}}}\\
&= \color{blue}{\frac{1}{d(n, k)}}\color{red}{\th^k(1 - \th)^{n - k}} && \text{accumulating constants}
\end{align}
\]

The last expression is the *beta distribution* with parameters $(k + 1, n - k + 1)$. Don't sweat the +1s, that's just convention.

what if we assumed the beta distribution as a prior?
![](unit7lec14-intro-to-bayesian-inference\2dd439667bfc89427bedc049bc2614bc.png)

we get the beta distribution anyway, nice property.

Why is this property nice?  
It allows us to recursively update the posterior as we get more observations.

As we get new data it impacts our conditional distribution of $\theta$ and lets us make better predictions about its value.

In the problem even though it was overwhelmingly obvious that the coin was biased towards tails our prior being biased meant that after the trials it was even.

## Inferring the unknown bias of a coin - point estimates

What are point estimates?  
Actual estimates done on the posterior.

We derived the posterior last time:

\[
\cpdf{\Th}{K}{\cnd{\th}{k}} = \frac{1}{d(n, k)}\th^k(1 - \th)^{n - k}
\]

LMS == conditional expectation of $\Theta$ given specific number of heads.

First we do the MAP estimate, finding the maximum by taking the derivative, setting it equal to 0, and finding $\theta$. Given our estimate, the estimator just uses the random variable.

![](unit7lec14-intro-to-bayesian-inference\156b86bd8a788262148e5892a414a75c.png)

Next LMS. We take the integral over all $\theta$ and use a nice equality from calculus. Remember the coefficient should be equal to that necessary to make the PDF equal 1 over the domain. But recognize that when we take the conditional expectation we're adding another $\theta$ into the mix. using the slick equality we have for both the coefficient and integral itself gives us our equality in the end.

![](unit7lec14-intro-to-bayesian-inference\1b5748b38340616194fca12600f7ed5d.png)

![](unit7lec14-intro-to-bayesian-inference\9c37658b835013f946170468c144f4cc.png)

## Summary

We are given a problem: find some variable. And we are given two distributions:
1. Some prior distribution for our unknown ($\pmf{\Th}{\cdot}$) - best guess
2. Conditional distribution for some observable r.v. ($\cpmf{X}{\Th}{\cnd{\cdot}{\cdot}}$)

Task: Find a distribution for $\Th$ that takes into account $X$ ($\cpmf{\Theta}{X}{\cnd{\cdot}{x}}$) using an appropriate version of bayes rule

summarize findings using an estimator.

We covered 2 such estimators:
1. MAP - get the best guess
2. LMS - conditional expectation of $\Theta$

Then evaluate the performance of the estimator.

![](unit7lec14-intro-to-bayesian-inference\bb254bef3dbc010bc4d5303e7011ba1d.png)

the actual calculations just sum or integrate the posterior distribution we found. unconditional cases require total probability or expectation theorem and average over all possible $X$.
