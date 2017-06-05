---
section: 6
subsection: 11
type: lecture
title: Derived Distributions
---

# Lecture 11: Derived Distributions

$\newcommand{\cnd}[2]{\left.#1\,\middle|\,#2\right.}$
$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\cpr}[2]{\pr{ \cnd{#1}{#2} } }$
$\newcommand{\setst}[2]{\left\{#1\,\middle|\,#2\right\}}$
$\newcommand{\ex}[1]{\mathbf{E}\left[#1\right]}$
$\newcommand{\cex}[2]{ \ex{ \cnd{#1}{#2} } }$
$\newcommand{\var}[1]{\text{var}\left(#1\right)}$
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
$\newcommand{\abs}[1]{ \left|#1\right| }$



## Overview

* Given the distribution of $X$, find the distribution of $Y = g(X)$
  * discrete
  * continuous
  * general, using CDFs
  * linear case, $Y = aX + b$
  * general formula when $g$ is monotonic
* Given the joint distribution of $X$ and $Y$, find the distribution of $Z = g(X, Y)$.

Why is this skill important?  
We often build up models from simpler distributions.

Why should you not always jump to determining some distribution $g(X)$?  
It can be easier to use the expected value rule.


## Discrete case

Looking at a simple example $Y = g(X)$:

![](unit6lec11-derived-ditributions\b5333b5b3cc92903955995e1085f070b.png)

So when $X$ takes on the value $x$, $Y$ takes the value $g(x)$.
To find out the probability of each $y$, we want to sum the probabilities of each $x$ that leads to it.

\[
\begin{align}
\pmf{Y}{y} &= \pr{g(X) = y}\\
&= \sum_{x:g(x) = y} \pmf{X}{x}
\end{align}
\]

A linear function has, at most, a coefficient and some scalar. So, given $Y = aX + b$,
\[
\pmf{Y}{y} = \pmf{X}{\frac{y-b}{a}}
\]

To show how this is derived, take $Y = 2X + 3$ and notice
\[
\begin{align}
\pmf{Y}{y} &= \pr{Y = y}\\
&= \pr{2x + 3 = y}\\
&= \pr{X = \frac{y-3}{2}}\\
&= \pmf{X}{\frac{y - 3}{2}}
\end{align}
\]


## Linear function of a continuous random variable

What is the main difference between **linear functions** of discrete and continuous r.v.s?  
Continuous r.v.s have to worry about total area, which changes over intervals when they are stretched. So a scaling factor is needed.

![examples of scaling a continuous distribution](unit6lec11-derived-ditributions\53d0a29231b121991e20572979d17f23.png)

Even though the actual value of the PDF changes, the relative values of the corresponding parts of the distribution do not change.

What is the form of the linear function equality for continuous r.v.s?  
\[
\pdf{Y}{y} = \frac{1}{\abs{a}} \pdf{X}{\frac{y - b}{a}}
\]

What is the approach to deriving the linear function equality?  
Get the CDF on $Y$ in terms of the CDF of $X$ and differentiate both sides.


## A linear function of a normal r.v.

Just use the formula from the previous section and collect like terms:

![normal linear equation](unit6lec11-derived-ditributions\f1f0459cf51d48bfd21a9659d4e8be0f.png)

Then the result is
\[
\pdf{Y}{y} = \frac{1}{ \abs{a} }\pdf{X}{\frac{y-b}{a}}
\]

and we have the proof of the previous statement, if $X \sim \norm(\mu, \sigma^2)$, then $aX + b \sim \norm(a\mu + b, a^2\sigma^2)$.


## PDF of a general function

What are the 2 steps for finding the PDF of a general function?  
1. Find the CDF of $Y$ in terms of $X$, $\cdf{Y}{y} = \pr{Y \le y} = \pr{g(x) \le y}$
2. Differentiate to obtain the PDF. $\pdf{Y}{y} = \frac{\d F_Y}{\d y}(y)$

Examples:
Let $X$ be uniform on $[0, 2]$ and $Y = X^3$, then $\pdf{Y}{y} = \frac{1}{6}\cdot\frac{1}{y^{\frac{2}{3}}}$. Notice that $Y$ is not uniform. The function has spread the probability of $X$ across different parts of $Y$.

If you look at a graph of $\pdf{Y}{y}$:

![PDF of Y](unit6lec11-derived-ditributions\116c1b8ea45356314f61f5cdcfddb4a2.png)

then it's easy to see that little $y$s are more likely than big $y$s. $X$ may have been uniform, but as $x$ increases in value consecutive equally likely small intervals of $X$ map to larger areas of $Y$ and so the probability of values in these areas are lower.

![PDF of a general function example 2](unit6lec11-derived-ditributions\fa59434a551c6b21e5cde1f11a436564.png)


## The monotonic case

Given $Y = g(X)$, if $g(X)$ is monotonic and everywhere differentiable, then it is injective (one-to-one) and there exists an inverse $h(Y)$.

![](unit6lec11-derived-ditributions\cda7ae412f2bdc0c95db6c2b027147ee.png)

![](unit6lec11-derived-ditributions\74daf83375f40fe39373d457fd56fda3.png)

Recognize that because the blue decreasing case has negative slope then it will be negative and negated by the negative, but this is just the absolute value.

So for $Y = g(X)$ monotonic, the formula for the PDF of $Y$ is:
\[
\pdf{Y}{y} = \pdf{X}{h(y)}\abs{\frac{\d h}{\d y}(y)}
\]

For monotonic functions, using the formula breaks down the 2 step process given before into essentially: find the inverse function and differentiate.


## Intuition for the monotonic case

![](unit6lec11-derived-ditributions\bc8a44ef0437e2a06b580edd5472ba7e.png)


## Nonmonotonic example

We start with the example $Y = X^2$ over the whole domain. This is nonmonotonic, so we can't use the trick from before.

Discrete-valued warm-up

![](unit6lec11-derived-ditributions\596e8c76f0e5b41378d1a66a088d374e.png)

Graph of $x$ and $y$

![](unit6lec11-derived-ditributions\5e4eef611ae80c588fd04debc2a941de.png)

Solving for continuous case from first principles:

![](unit6lec11-derived-ditributions\b519a2cc21a18e2dc2c87af99576d657.png)


## A function of multiple r.v.s

![](unit6lec11-derived-ditributions\1b8bfae0011ca63c1ebe5bf67e69acab.png)

Essentially use arguments from geometry and go through the same steps.

Interesting problem on the last one:

![](unit6lec11-derived-ditributions\e08796ee8e4679535b9e438812c7a800.png)

It's cute that the area of the unit circle is $\pi$.
