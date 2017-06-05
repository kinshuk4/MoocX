---
section: 7
subsection: 16
type: lecture
title: Least mean squares (LMS) estimation
---

# Lecture 16: Least mean squares (LMS) estimation

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

## Overview

What are the two methods we already have for estimating an unknown parameters?  
MAP, conditional expectation (LMS)

Impose **performance criterion** and then find an estimate which is optimal with respect to that criterion.

The performance criterion to be introduced is the mean squared error and the optimal estimator ends up being the conditional expectation.

![](unit7lec16-lms-estimation\6019ddc4dc0bd9f5d482730ced5f0456.png)

* Mathematical properties
* Example

## LMS estimation without any observations

Given some $\Th \sim \unfrm{4}{10}$ but no observations, what do we do when we want a point estimate? We can use MAP or our conditional expectation, but the first 1 doesn't give a unique answer (because uniform). How do we determine which is the best?

That's why we introduce a **performance criterion** a checker for estimators and seek to minimize it.

Going the calculus route, we take our MSE performance criterion and minimize by finding the root of the derivative.

![](unit7lec16-lms-estimation\3d0295d5a98bf94f0c1c1c3ccd9499d1.png)

but that's really calculusy.

Taking a more probabilistic approach:

![](unit7lec16-lms-estimation\f2115862ec793048960fb765f5a6b502.png)

we can't do anything about the variance of the unknown so we minimize the $\thh$ and we do that by setting it equal to $\ex{\Th}$.

![](unit7lec16-lms-estimation\70a4be38772462b37ed7ac7ea6de216d.png)

Conclusions:
* The best possible mean squared error we can hope to get with some estimator is $\var(\Th)$.

## LMS estimation; single unknown and observation

The difference here is that there is an observation, we just show that the value that minimizes the MSE is still the LMS estimator over all other estimators

![](unit7lec16-lms-estimation\1a18fce9d55dad7b05fd9a623b62d37d.png)

![](unit7lec16-lms-estimation\5aadb0f908be402a09ffcf59d16aeda5.png)

![](unit7lec16-lms-estimation\64adbb6d9f7ff51fa003a90ecf556335.png)

![](unit7lec16-lms-estimation\176a046167e0d708aa7989afa5494a81.png)

Note: iterated expectations only apply to fns of random variables, and say that conditional expectation is the same as unconditional expectation.

## LMS performance evaluation

LMS estimatre and estimator are important and it is nice that it is simple.

It is important enough to look at its performance. We saw before the performance was equal to the variance, and the performance conditioned on some data would just be

![](unit7lec16-lms-estimation\9bd5e4759a09fa4812bbbecbca888d0a.png)

This is the quantity you would report to your boss if he asked how well the estimator could estimate. What if you hadn't done any measurements? Then use the expected value of the MSE, which is just the average of all variances that are possible given values for $X$

![](unit7lec16-lms-estimation\a697d17c0aa2ed5590368c10c900b6eb.png)

Remember hypothesis testing? LMS isn't relevant to that, only estimation. In hypothesis testing we care about the probability of being wrong, not how far we were away from the right answer.

Same as MAP if the posterior is "unimodal" and symmetric around the mean. [Unimodal](https://en.wikipedia.org/wiki/Unimodality)

## Example

We start with two distributions:

![](unit7lec16-lms-estimation\0cc27ed181d436e1849c4ad9397a0969.png)

![](unit7lec16-lms-estimation\10111defafbdecabef7f9c2ebf8f81d4.png)

We can think of this as if $X = \Th + U$, where $U \sim \unfrm{-1}{1}

![](unit7lec16-lms-estimation\5afc0d82f88245593b3d13df2ec832ba.png)

The midpoint of the vertical intersections is used as the expected value in the conditional universe because the cross-section is uniform.

This example gives us a look into a few things:
* graphing intuition of bayes rule, reversing the conditioning without going through the rote application of the formula
* Example of solving for the LMS estimator (expected value)

## LMS performance evaluation

performance evaluation question

We want to evaluate the performance of the estimator that we created graphically.

The performance is just that of the LMS, and we know

![](unit7lec16-lms-estimation\ae7845e467a387aa70921a96838291cb.png)

So there are three parts to this exercise. Using the following facts:

$\var{\unfrm{a}{b}} = \frac{(b - a)^2}{12}$

![](unit7lec16-lms-estimation\188794da637c1d6f46e7f34bee88a656.png)

in the blue section the variance is just that of the uniform and since the conditional uniform is of length 2 the variance there is $\frac{1}{3}$.

In the tails the length of the uniform intervals increases linearly with $x$ so the variance increases quadratically

![](unit7lec16-lms-estimation\ee29692a8b6b2bbad06ef8e9177a996d.png)

Now what about the overall expectation? This is going to have the form

![](unit7lec16-lms-estimation\ea008b614043d16fc4edd86c9a276f8d.png)

We aren't given $X$ but we can get it from the joint PDF by taking the marginal then plugging it into the eq above.

Notice that we can kind of estimate the expected value because it is going to be an average of the variance that we have graphed and for that we can see it will be somewhere in $[0, \frac{1}{3}]$ and closer to $\frac{1}{3}$ than 0.

## The multidimensional case

Generalize to multiple parameters and multiple variables.
Nothing in our arguments requires that $\Th$ or $X$ are single dimensioned

![](unit7lec16-lms-estimation\46a264e5987974122fea61f57a57072f.png)

![](unit7lec16-lms-estimation\16ae32e8cea1c143d40ab1b6adece373.png)

![](unit7lec16-lms-estimation\6b74a326127b276a0ed3461deb8b4344.png)


What are the challenges of LMS estimation?  
1. The model $\cpdf{X}{\Th}{\cnd{x}{\th}}$ (required for bayes rule) may not be available
2. It can be hard to compute/implement/analyze

    The denominator is an integral ![](unit7lec16-lms-estimation\7ff675012969537226ae84926e79441f.png)

but even given that, we would still have to do the expected value of $\Th_j$ which is a multi-dimensional integral over all the possible thetas

![](unit7lec16-lms-estimation\291d724de7753b4bb40b7c4d2868b437.png)


## Properties of the LMS estimation error

Define estimator

![](unit7lec16-lms-estimation\e1ba4fb6d9ab3c985c6fce0daa503332.png)

define estimator error

![](unit7lec16-lms-estimation\f3a7603da97adc3674782f6ead7222ed.png)

show error is 0

![](unit7lec16-lms-estimation\851f0de1367bddd03246574df7150315.png)

by law of iterated expectations

derive conditional error is 0

![](unit7lec16-lms-estimation\910e732225e1211d26317a37173930cc.png)

because $\Thh$ conditioned on $X = x$ is just $\thh$, a constant.

derive covariance is 0

![](unit7lec16-lms-estimation\abe4f7c798d7cc685f15244b9b282c99.png)

from our previous result and the law of iterated expectations.

Now consider

![](unit7lec16-lms-estimation\55a2fd72f1cf4b7a712f4e04a18b954d.png)

then because they have 0 covariance we have

![](unit7lec16-lms-estimation\25209c25423fd72221e156e95466ac8b.png)

this can also be derived from the law of total variances (in different notation).

This is foundational for the general theory of least-squares estimation. Just an interesting aside.
