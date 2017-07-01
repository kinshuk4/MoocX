---
section: 8
subsection: 18
type: lecture
title: "Inequalities, convergence, and the Weak Law of Large Numbers"
---

# Inequalities, convergence, and the Weak Law of Large Numbers

$\newcommand{\ex}[1]{\mathbf{E}\left[#1\right]}$
$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\abs}[1]{ \left|#1\right| }$
$\newcommand{\unfrm}[2]{ \mathcal{U}\left( #1, #2 \right) }$

## Overview

Goal: Lean and understand the Weak law of large numbers (WLLN)

To support that goal, learn about the **Markov** and **Chebyshev** inequalities.

The **sample mean** is an application that can use these inequalities and we look at an example in the context of **polling**.

We also define **convergence** in probability.


## The Markov inequality

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\1b27c66c9d8a4d74c0192166954f8a81.png)

extrapolate probablility of extreme events from some bit of information about the r.v.

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\166e60b9378234a558fb7dbead0bcbfc.png)

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\f4242751f647b6e1fe8a8f083fafb3d2.png)

$\ex{Y} \le \ex{X}$ because the inequality between $X$ and $Y$ holds across their whole range.

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\1f53612f93ec15796453c780dda35658.png)

bound aabove the value for fn
tail behavior
a bound is strong/ good if it is somewhat close to the actual bound.

this one is not very strong since the value falls off exponentially but the bound falls of as a factor of $/frac{1}{a}$

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\aa15723ecdd79c9649418f6a95ccd999.png)

In this case we have to make adjustment for the r.v. not being strictly $\ge 0$

and we can also hone in on a closer bound by recognizing symmetry


## The Chebyshev inequality

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\70dc57efb4af0471b89d66d30132ce9f.png)

derivation:

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\8c7ee11e6371d0f58b6f0cb1c4f64203.png)

examples:
![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\cf78e0329983049ba308c532e272b3d4.png)

exponential r.v. example:
![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\6b75025bd37d032d669728923a823d6f.png)

why is the chebyshev inequality more informative than the markov inequality?
it uses more information about the r.v.

The Chebyshev inequality is stronger when $a$ is large, but it is possible to get a lower bound with the Markov inequality.


## Weak law of large numbers

essentially, the average of the sum of a sequence of i.i.d. r.v.s converges to the expected value of the unknown.

long experiment where we select a lot of r.v.s

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\f0d0b972d7511e40f327c0b1d77764d6.png)

we consider $M_n$ to be a random variable and ask some questions about it

what is its expected value and variance

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\d2414ebdc78453dbf36ed1e7d13ecc81.png)

which we then use with the chebyshev inequality to see that

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\7cf37d671928501230ae00ecac2b3a91.png)

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\27d3438291efbad39b42e12cd2b0d725.png)

so as the number of selected samples grows the deviation from the true mean is going to become smaller

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\afec3166b430882a0ff6af564bc8b777.png)


## Polling

Some referendum is going to take place. We assume that people will not change their mind and we seek to identify the fraction $p$ of the population that will vote "yes".

Select people uniformly and independently. Let $X_i$ be an indicator that the $i$th person said yes. Then $\ex{X_i} = p$

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\e44456f95a55c72061de6938741607ed.png)

Give me the exact answer

No can do.

Give me an answer that is guaranteed to have a small error

Can't do that either (why? because your sample might not be representative)

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\3b04c7b674acac83a7a4ba70c9adb19c.png)

What I can do is give you an answer where the probability of error is small with high probability.

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\092059d7953a314ca794acf3462c5ea6.png)

for $n = 10000$

We don't know what $p$ is but we can plot it:
![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\a4c2f121ad6a18f812cd2acd94fd22d0.png)

so the maximum this value can be is 1/2 which makes the value 1/4, 25% chance of error. If we want a smaller chance of error, then

two parameters:
* accuracy you want
* the confidence of the accuracy

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\01022af89bee2fa0b4c33fd660b04267.png)

50,000 people is a lot but it is what is required to get our confidence. There are things that can be done to reduce this requirement, like going for a slightly larger probability of error (3%). Another option is to use a more accurate bound than the Chebyshev inequality.


## Convergence in probability

For the WLLN we want to simplify and say that $M_n$ converges to $\mu$, but we need to define "convergence" here.

A sequence $Y_n$ converges in probability to a number $a$ if for any $\varepsilon > 0$
\[
\lim_{n\to\infty}\pr{\abs{Y_n - a} \ge \varepsilon} = 0
\]

comparing to ordinary convergence

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\a15c62f66feb6e1d35df7c266c5cf07d.png)

Suppose that $X_n \to a, Y_n \to b$ in probability, then

1. if $g$ is continuous, then $g(X_n) \to g(a)$
2. $X_n + Y_n \to a + b$
3. $\ex{X_n}$ need not converge to $a$


## Convergence in probability examples

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\e964f1862adfd2503d9b933fbb418cf1.png)

convergence in probability has to do with the bulk of the distribution, whereas the expectation is sensitive to high-valued tails.

Next consider $X_i \sim \unfrm{0}{1}$. Does this converge?

No, because it does not settle down after many trials.

But what about $Y_n = \min\{X_1, \ldots, X_n\}$.
![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\15a5bb846f0b71c0e194c8e3156244c8.png)

The first step is to make an educated guess about what the limit may be, the value that the sequence may converges to.

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\bfd4f63c333a3e9407381ade9b99ef4d.png)

Write the expression for the value being epsilon away from the limit, then calculate the probability, either directly or by trying to bound it somehow and show that the bound goes to 0.


## Related topics

How far can the given topics be taken?

Better bounds on tail probabilities:
* Chernoff bound
![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\e86556526da6f904b9a8cfd0fdc6a700.png)

but it requires addtl  assumptions

also using central limit theorem - $M_n \sim N(\mu, \sigma^2/n)$, as if it is normal

different types of convergence.
* convergence in probability
* convergence with probability 1 - seqce of r.v.s and another r.v.

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\32896165ad33ab7b199cc88e320621f7.png)

that a given $\omega$ is the same as the $Y$ in

strong law of large numbers

![](unit8lec18-inequalities-convergences-weak-law-of-large-numbers\7276fe0bc735e9b675ff65c5a699a66f.png)

convergence of a sequence of distributions (CDFs) to a limiting CDF. - convergence in distribution.
