---
section: 7
subsection: 17
type: lecture
title: Linear least mean squares (LLMS) estimation
---

# Lecture 17: Linear least mean squares (LLMS) estimation

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

The conditional expectations $\cex{\Th}{X}$ hard to compute due to:
* complicated non-linear structure
* incomplete information/unknown distributions
* hard to calculate event with known distributions

For this reason we focus on linear estimators $\Thh = aX + b$ and look at minimizing their MSE. A solution to this is given in terms of the means, variances, covariances of the random variables.

* Mathematical properties of LLMS (variance, expected value, multidimensional case, )
* Example
* How the estimator can be used


## LLMS formulation

Recall that a function is linear if it has the form $aX + b$ with $a, b$ constants.

We know that $\Thh_\lms$ is the best that we can do, but for the reasons given before we want to find something better. Looking at prior Example

![](unit7lec17-linear-lms-estimation\787e0efe54755ddeef5a8a2b95ca9b5a.png)

our solution to LMS is in blue, but it is nonlinear and kind of complicated (piecewise), so it's hard to work with. We want a straight line! To do that we restrict our focus to linear estimators
![](unit7lec17-linear-lms-estimation\7eed3a9ddeb79cb6f8e91041e8d71e32.png)

and we keep the typical MSE criterion

![](unit7lec17-linear-lms-estimation\f9ac9e4e4f1dec190e93f4a41f475771.png)

then our goal is really:

![](unit7lec17-linear-lms-estimation\fef166bddc9de61c64c1378e55535479.png)

One note, LLMS is easy when the soln is already linear, and specifically

![](unit7lec17-linear-lms-estimation\0d766be2fd1797d1e97a0a77f0794734.png)


## LLMS solution

Suppose $\Th, X$ are dependent and $a, b$ are constants.

2 stages:
1. Assume $a$ has been found - then we want to choose $b$ to minimize the LLMS estimate. so $b$ is a constant to minimize the squared difference and ends up being $b = \ex{\Th} - a\ex{X}$
2. Now that we have $b$ we can find $a$ by first substituting in

![](unit7lec17-linear-lms-estimation\f427508bd08aaeafe03b0c1e442e0ada.png)

then we optimize by finding $\frac{\d}{\d a} = 0$

![](unit7lec17-linear-lms-estimation\0a182471fbe49eff8fd2b819faca53d4.png)

and we find

\[
\Thh_L = \ex{\Th} + \frac{\cov(\Th, X)}{\var{X}}\left(X - \ex{X}\right)
\]

now recall corr. coeff and notice

![](unit7lec17-linear-lms-estimation\1fb48ab0dc845a065222feacc42ebb64.png)

which shows the $\Thh_L$ can also be written

![](unit7lec17-linear-lms-estimation\c85acbdcf831cd382fdb2b3504956a40.png)


## Remarks and the error variance

![](unit7lec17-linear-lms-estimation\fa0477cfb7146a15bdca8e90770cd2c9.png)

* Looking at the pieces we need, it's only means, variances, and covariances.

Next consider parts of the estimator. The estimator starts with a baseline and then we have what can be considered an error term whose size impacts the change to the estimate and where the size is impacted by

![](unit7lec17-linear-lms-estimation\5394f378fd9722b8aa28ac9e6152f008.png)

Derivation of

![](unit7lec17-linear-lms-estimation\4a24ac740db86f3f1a68e3038bfef6b3.png)

![](unit7lec17-linear-lms-estimation\2b86a07ecb3949b01fc01a9c6f028bbb.png)

assuming $\ex{\Th} = \ex{X} = 0$ (but it holds for non-zero means)

This shows us that the estimator error is just the variance of $\Th$ when the observations and the variable are uncorrelated, and in the extreme case where $|\rho| = 1$, the error is 0 (because they are linearly related.)

The correlation coefficient plays a crucial role in LLS estimation, it determines the form and how much uncertainty can be reduced.


## LLMS Example

![](unit7lec17-linear-lms-estimation\0542c4ff5d9c5fe432d7945bb8f72316.png)

Using our previous Example

![](unit7lec17-linear-lms-estimation\460ded0cd69fd3d1a4c8f27fb033e49c.png)

![](unit7lec17-linear-lms-estimation\938890a60fcc653691db6a2b7e950131.png)

we just come up with the terms needed

![](unit7lec17-linear-lms-estimation\64d246bd7ceb11c5f3825b5cfb6fe188.png)

![](unit7lec17-linear-lms-estimation\84961fcaa3faa2976e5210d82b4bf63a.png)

and get $\Thh_L = 7 + \frac{9}{10}(x-7)$

essentially:
* get the means
* get the variances
* get the covariance
* solve


LMS is guaranteed to take values only in the same interval as $\Th$, but LLMS has no such guarantees.


## Coin bias example

Just an exercise on an example we've done before

![](unit7lec17-linear-lms-estimation\f36ec585655eaba9ac8a63154a2a82d5.png)

From previous calculations we know that the LMS estimator is linear, so we already have the LLMS estimator

![](unit7lec17-linear-lms-estimation\aa5ed31cb77ca4926be43d45cc9adfa7.png)

but for practice we can derive from the formula

![](unit7lec17-linear-lms-estimation\22ae49e76350926d7805e440f4ca584f.png)

First, calculate all the needed quantities

![](unit7lec17-linear-lms-estimation\8b7b2923c959bf8fd8aa29a4e0437946.png)

![](unit7lec17-linear-lms-estimation\5b605c1ae38e8d7f4e8de635b72a95ae.png)


## LLMS with multiple observations

Unknown $\Th$ with observations $X = (X_1, \ldots, X_n)$

We restrict to estimators of the form $\Thh = a_1 X_1 + \cdots + a_n X_n + b$

The goal is to find the best choices of $a_1, \ldots, a_n, b$ that minimize

![](unit7lec17-linear-lms-estimation\362db73310685d05f00f1e5f04b48e8b.png)

First observe that if $\cex{\Th}{X}$ is linear in $X$ then LMS estimator is the same as LLMS.

Next, consider the form of the expected value above, it has several terms, some of them quadratic and others single values

\[
a_1^2\ex{X_1^2} + 2a_1a_2\ex{X_1X_2} + \cdots + a_1\ex{X_1\Th} + \cdots
\]

If we take the derivative of this then we get a linear function and when we set it to 0 we just get a system of linear equations. So we solve for some linear system in $b$ and then the individual $a_i$.

Notice that the expectations are just parts of the things that we already know to look for, means, variances, covariances.

This makes the things practical because we don't need to model the whole distribution of the random variables involved.

And if there are multiple random variables $\Th_j$ we just apply the method to each of them separately.


## Example with multiple observations

![](unit7lec17-linear-lms-estimation\ea2c7d64c9fd4d80749cbc24b10efb6c.png)

We want to take a shortcut to avoid computing

![](unit7lec17-linear-lms-estimation\edc46e7cc0989e6263ff52fe8fb80c8a.png)

then since the LMS estimator is linear thaat is also the form of our LLMS estimator.

Now suppose we have general distributions, but with the same means, variances, etc. Then the covariances are also the same since they are just related to the means and because the variables are uncorrelated.

Then the optimal solution is the same since the LLMS only cares about the means, variances, and covariances. All we needed to do was make enough assumptions that these attributes of the distributions were fixed and didn't vary between the different cases.


## Choices in representing the observations

Discuss an interesting point about linear estimators.

What happens when your observations are on a different scale? $X$ vs $X^3$.

harder to calculate higher power variances/covariances

We can choose between classes of estimators here depending on what we know about the underlying $\Th$ we're trying to figure out.

![](unit7lec17-linear-lms-estimation\453fe1c63ee58577e48b2a00affdc826.png)

We can also consider estimators of the type $\Thh = a_1 X + a_2 X^2 + a_3 X^3 + b$. in this case each of the $X^i$ terms can be thought of as a different observation. Because it is more general that the above items we can get more accuracy, but the trade-off is increased difficulty in getting the optimal coefficients.

We can also mix and match

https://en.wikipedia.org/wiki/Linear_regression

![](unit7lec17-linear-lms-estimation\8b64f8f13de6f4a1e10ee128ab96ba46.png)

Different choices give us different performance.
