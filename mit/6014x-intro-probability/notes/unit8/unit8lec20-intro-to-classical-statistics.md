---
section: 8
subsection: 20
type: lecture
title: An Introduction to Classical Statistics
---

# An Introduction to Classical Statistics

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
$\newcommand{\map}{\text{MAP}}$
$\newcommand{\lms}{\text{LMS}}$
$\newcommand{\unfrm}[2]{ \mathcal{U}\left( #1, #2 \right) }$
$\newcommand{\toip}{\stackrel{\text{i.p.}}{\to}}$

## Overview

"Classical" contrasts the Bayesian framework in that $\th$ is a non-random constant that is unknown (as opposed to as a r.v.).

Example of estimating $\th = \ex{X}$ using the sample mean.

Confidence intervals (CIs)
* using the CLS
* when the variance is unknown

Other uses of sample means
* to estimate $\rho$

Maximum Likelihood estimation - very general and applicable universally.


## Conceptual framework of classical statistics

Inference of unknown r.v.s up to this point has assumed
* Unknown $\Th$ with prior $p_\th$
* Unknown $X$ with given conditional distribution $p_\cnd{X}{\Th}$

Classical stats treats $\th$ as a constant and seeks to generate estimates for it. This can be a nice way to think about problems where
* $\th$ is a constant, like some universal constant
* you don't want to bias your estimate with a prior on $\Th$

Imagine $\th$ as some constant that impacts the value of
![](unit8lec20-intro-to-classical-statistics\d8dffce4c7564c267a2d8b93449f7288.png)

![](unit8lec20-intro-to-classical-statistics\54ab1f534bb3bb19be42f6d8635dae1d.png)

the estimator is a fn that takes data and generates an estimate.

you can think of this as having multiple potential models depending on how the $p_X$ is parameterized and by using the data some of those models can be ruled out.

![](unit8lec20-intro-to-classical-statistics\dd4bb493eb34e875da889f76321abec5.png)

Only estimation will be covered in this lecture.

There's more than one way to come up with a good estimator, no method beats all others. Multiple approaches.

### Review

What are the three types of problems in Classical statistics?  
[answer](/ "Hypothesis testing, Composite hypotheses, and estimation.")

Contrast $\Th$ and $\th$ in bayesian and classical statistics.  
[answer](/ "big Theta in the bayesian framework is a r.v., but little theta is a constant in classical statistics")

What kind of situations is classical statistics useful for?  
[answer](/ "Estimating a physical constant, doing estimation without a potentially biased prior")

What is the estimator $\Thh$ in classical statistics?  
[answer](/ "A r.v. that takes data and generates an estimate for the constant theta")

What is the mathematical way to imagine classical statistics?  
[answer](/ "Multiple potential distributions parameterized by the constant theta and you choose the one with the best fit to the data")


## The sample mean and some terminology

First we set up our context.

![](unit8lec20-intro-to-classical-statistics\7c6ca2843b12fff9bc14cde1034826ab.png)

Then let

![](unit8lec20-intro-to-classical-statistics\9c1bf610f55d53ccbaa019928fbfb3d6.png)

$\Thh_n$ is a r.v. because it depends on the r.v.s $X_i$. We call $\Thh$ an estimator (specifically for the mean value $\th$).

To evaluate this estimator we need to know what makes an estimator "good". The important part of our good properties is that the associated properties hold for all $\th$.

**unbiased**: $\ex{\Thh_n}=\th$ for all $\th$. To understand the importance of this imagine a general estimator and what its expected value is

![general estimator depends on theta](unit8lec20-intro-to-classical-statistics\d36ec43a18ec046eeca75787697ba59f.png)

It could depend on $\th$ in a number of ways, so as to under or over-shoot the mark. unbiased means it does not.

**consistency**: by the WLLN, $\Thh_n \toip \th$ for all $\th$. So as more data comes in the estimator improves.

![mean squared error](unit8lec20-intro-to-classical-statistics\cdde6db1863a8415a5526fd08d95037e.png)

In this case the MSE isn't a fn of $\th$ but that isn't true in general.

These are not sure properties, you can have bad estimators that satisfy each of them. Degenerate unbiased estimator $\Thh_n = X_1$.

Review: biased estimator, consistent estimator

## MSE of an estimator

bias - term that expresses whether estimator is systematically above or below the value trying to estimate

![](unit8lec20-intro-to-classical-statistics\958d80efcf1f670d051635cdb8bf5444.png)

look at two example estimators

![](unit8lec20-intro-to-classical-statistics\16863b5bfdcfbbcf398b836f6f1a5a80.png)

Without knowing more about $\th$ it's hard to say which estimator is better. the 0 estimator is better at values close to 0, but the $M_n$ estimator is consistent so with high enough $n$ it will converge to $\th$.

> classical statistics can't represent this, but bayesian statistics can using the prior

$\sqrt{\var{\Thh}}$ is called the **standard error**, and it represents the standard deviation of the estimates that may be received on repeated application of the estimator to different sets of data. Calculating std error is one part of designing/implementing an estimator.

Review: bias, standard error


## Confidence intervals

Problem: The value of $\Thh$ isn't enough, more information is needed. Standard error is okay but confidence intervals are better.

> What are they and why are they better?

![](unit8lec20-intro-to-classical-statistics\87eb2c9b51664a14595cc4fc82089a24.png)

A **confidence interval** (CI) is a statement with two parameters:
* An interval, $[\Thh^-, \Thh^+]$, in which each endpoint is a fn of the data
* $\alpha$ - this is a value s.t. $\pr{\Thh^- \le \th \le \Thh^+} \ge 1 - \alpha$ for all $\th$

Values for $\alpha$ are often 0.05, 0.025, or 0.01.

The CI is not a statement about the likelihood that $\th$ is between some specific values, but the likelihood that the construction of the specific interval for some set of data captured $\th$.

![](unit8lec20-intro-to-classical-statistics\c32ae1bb4f6fb7461cd23296287ac6ff.png)

which doesn't make sense, but on the bounds falling around $\th$

![](unit8lec20-intro-to-classical-statistics\96261b379b9d6c7c64465379237cf51f.png)

Review: what is a confidence interval, what does the confidence interval describe, what is alpha in the CI

## Confidence intervals for an unknown mean

![finding confidence interval](unit8lec20-intro-to-classical-statistics\eef8b324d0c5ac6abb55520d7aad440a.png)

One caveat of this technique is the requirement to know $\sigma$ for the parameter being estimated.
