---
section: 5
subsection: 8
type: lecture
title: Probability Density Functions
---

# Lecture 8: Probability Density Functions

$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\cpr}[2]{\mathbf{P}\!\left(#1\,\middle|\,#2\right)}$
$\newcommand{\setst}[2]{\left\{#1\,\middle|\,#2\right\}}$
$\newcommand{\cex}[2]{\mathbf{E}\left[#1\,\middle|\,#2\right]}$
$\newcommand{\ex}[1]{\mathbf{E}\left[#1\right]}$
$\newcommand{\var}[1]{\text{var}\left(#1\right)}$
$\newcommand{\d}{\text{d}}$
$\newcommand{\iint}[2]{\!#1\,\d#2}$

## Unit Overview

Expectation, conditioning, independence in the context of continuous random variables. Continuous random variables use calculus, integrals to operate.

Probability density functions - an object that describes a continuous random variables
Expectation and variance
Cumulative distribution function - unifies discrete and continuous random variables.

Uniform, exponential, normal

A probability density function gives the area under a probability curve.

> Notation: $f_X(x)$ is a PDF.

PDFs give probability of intervals.

Properties of PDFs.
* $\pr{a \le X \le b} = \int_a^b \iint{f_X(x)}{x}$
* nonnegativity
* integral from $-\infty$ to $\infty$ is 1.

When is a random variable continuous?  
When it can be described by a PDF.

What happens to the sample space when dealing with PMFs and PDFs?  
![sample space, where has it gone?](unit5lec8-probability-density-functions\26c4a55b2f616484fcbb68a515f147b6.png)

The PMF and PDFs give the probability of a given random variable taking on a certain value. This can be thought of as some function mapping the sample space of actual events to the values assigned probabilities by the PMF/PDF.

What is the probability of a continuous random variable taking on a single value? Why?  
0, Argue from the integral or by looking at a small section of the function.

Closed and open intervals do not matter because the endpoints are just single points and therefore have probability 0.

## Uniform random variable

Similar to discrete uniform random variable, probability is the same across all possibilities in an interval.

What is the height of a continuous uniform PDF defined between $a$ and $b$?  
$\frac{1}{b-a}$

Because the product of the height and the width of the rectangle needs to be 1.

What is the generalization of the continuous uniform PMF?  
The piecewise constant PDF. It is uniform between specifically defined values.
![piecewise uniform PDF](unit5lec8-probability-density-functions\4b5754ec3336f8f524bea01231518a1d.png)

Why are they called probability density functions?  
They measure density of probability over the random variable.

## Expectation and mean of a continuous random variable
\[
\ex{X} = \int_{-\infty}^\infty \iint{x f_X(x)}{x}
\]

Just a weighted sum, same as the discrete case.

In many cases the continuous case looks like the discrete case but with integrals in place of sums.

In order to make this well-defined mathematically, we assume that
\[
\int_{-\infty}^\infty \iint{|x| f_X(x)}{x} < \infty
\]

Intuitive interpretations:
* average output of a large number of independent repetitions
* center of mass

## Properties of expectations

* If $X \ge 0$ then $\ex{X} \ge 0$
* If $a \le X \le b$, then $a \le \ex{X} \le b$

And the expected value rule for PDFs:
\[
\ex{g(X)} = \int_{-\infty}^\infty \iint{ g(x) f_X(x) }{x}
\]

linearity still holds for continuous expectations

Variance is found from the definition, $\var{X} = \ex{(X-\mu)^2}$,
And standard deviation is just $\sigma_X = \sqrt{\var{X}}$. The other properties for variance hold also,
* $\var{aX + b} = a^2\var{X}$
* $\var{X} = \ex{X^2} - \left(\ex{X}\right)^2$

$f$ itself is just a function, and can have a value for single values (of course it can, because the integral has to be evaluated), it is the probability that uses the PDF that gives 0 probability to single values.

## Mean and variance of the uniform

We can focus our attention only on the interval in which the uniform is defined because the value of the other values will be 0 and won't contribute anything to the integral.

What is the variance of a continuous uniform variable?
\[
\frac{(b-a)^2}{12}
\]
$\sigma = \frac{b-1}{\sqrt{12}}$

## Exponential random variables

What are the parameters of the exponential random variable?  
$\lambda > 0$

\[
f_X(x) =
\begin{cases}
\lambda e^{-\lambda x}, & x \ge 0\\
0,                      & x < 0
\end{cases}
\]

![](unit5lec8-probability-density-functions\0cece205679387cf48b3317d7ccc6593.png)

Similar to the geometric PDF
![](unit5lec8-probability-density-functions\3637be0bbd2b1ed7e16fb4a85277c211.png)

Let $a > 0$, and recall
\[
\int \iint{e^{ax}}{x} = \frac{1}{a}e^{ax}
\]

then
\[\begin{align}
\pr{X\ge a} &= \int_a^\infty\iint{\lambda e^{-\lambda x}}{x}\\
&= \left.\lambda\left(-\frac{1}{\lambda}\right)e^{-\lambda x}\right|^\infty_a\\
&= -e^{-\lambda \cdot \infty} + e^{-\lambda a}\\
&= e^{-\lambda a}
\end{align}\]

The probability of the tail of the function goes down with the exponential of the $a$.

Expected value of the e.r.v. (use integration by parts):
\[
\ex{X} = \int_o^\infty\iint{x\lambda e^{-\lambda x}}{x} = \frac{1}{\lambda}
\]

Expected value squared (for variance), also using integration by parts:
\[
\ex{X^2} = \int_0^\infty\iint{x^2\lambda e^{-\lambda x}}{x} = \frac{2}{\lambda^2}
\]

Variance:
\[
\var{X} = \ex{X^2} - (\ex{X})^2 = \frac{1}{\lambda^2}
\]

for small $\lambda$ the PDF has a higher variance.

Models the time we have to wait until something happens.

Contrast with geometric that models the trials we wait until seeing a success.

In the real world we can also consider examples like:
* The time until a customer arrives
* the time until a light bulb burns output
* the time until a machine breaks down
* the time until you receive an email
* the time until a meteorite falls on your house.

## Cumulative distribution functions

Motivation: There are several similarities between discrete and continuous random variables (linearity of expectations), it would be nice to have a way to talk about both of them at the same time.

Cumulative Distribution Function (CDF) is a function $F_X(x) = \pr{X \le x}$. Notationally, always uppercase $F$ and subscript with variable we're talking about.

### For continuous random variables

\[
F_X(x) = \pr{X \le x} = \int_{-\infty}^x\iint{f_X(t)}{t}
\]

CDF for a continuous uniform distribution:
![](unit5lec8-probability-density-functions\b9cdd8c81f117a04922e65c5f047fcf0.png)

Treat the CDF in cases based on what the PDF is doing for a value of $x$.

In general, the CDF contains all available probabilistic information about a random variable, it's just a different way of describing the distribution.

It's possible to represent intervals in terms of subtractions of the CDF evaluated at multiple points, so you can use it to find the probability of arbitrary intervals.

\[
\frac{\d F_X}{\d x}(x) = f_X(x)
\]
by differentiating both sides of the above, where it is differentiable. Also notice the slope of the line in the example above is exactly equal to the

### For discrete random variables

\[
F_X(x) = \pr{X \le x} = \sum_{k \le x} p_X(k)
\]

example
![](unit5lec8-probability-density-functions\67c03ebf30507ed0b850c31ac2042386.png)

## General CDF properties

* Non decreasing, if $y \ge x$ then $F_X(x) \le F_X(y)$
* $F_X(x)$ tends to 1 as $x \to \infty$
* $F_X(x)$ tends to 0 as $x \to -\infty$

Find an easier way to do problems, use linearity and other tricks, don't just hop onto the integral.

## Normal random variables

Normal or Gaussian random variables.

One of the most important distributions

Important for central limit theorem

Has convenient analytical properties, model of noise consisting of many small independent sources of noise (usually what happens)

A standard normal random variable has the form:

\[
N(0, 1) := f_X(x) = \frac{1}{\sqrt{2\pi}} e^{-x^2/2}
\]

The constant term comes from the fact that the integral over all $x$s of the function needs to be 1 and $\int_{-\infty}^\infty \iint{e^{-x^2/2}}{x} = \sqrt{2\pi}$.

![](unit5lec8-probability-density-functions\b195def7d19d5952455948c79de223b6.png)

Because it is symmetric around 0, the mean/expected value is $\ex{X} = 0$. Integrating by parts gives use $\var{X} = 1$, which explains the notation.

Generalizing:
\[
N(\mu, \sigma^2) := \frac{1}{\sigma\sqrt{2\pi}}e^{-(x-\mu)^2/2\sigma^2}
\]

![](unit5lec8-probability-density-functions\3356c1573bb36c94dc727f3e2d9187df.png)

What is an important property of normal random variables?  
They behave well analytically

Let $Y = aX + b$ and $X \sim N(\mu, \sigma^2)$. Then $\ex{Y} = a\mu + b$ and $\var{Y} = a^2 \sigma^2$.

Another fact (proved later) is that a linear function of a normal distribution is itself normally distributed: $Y \sim N(a\mu + b, a^2\sigma^2)$

What happens when $a = 0$?  
$Y = b$ is a degenerate discrete random variable, a constant. We can think of it like a special case of the normal with $N(b, 0)$, so we can say in every case that a linear function of a normal random variable is normal.

When is $X \sim N(\mu, \sigma^2)$ a continuous random variable?  
When $\sigma \neq 0$, because otherwise it is a single point with nonzero probability.

Continuous random variables have to have zero probability at specific points.
continuous random variables have to be described by a PDF.

## Calculation of normal probabilities

There's no closed form of the PDF or CDF for the normal distribution, but we have tables that can be used.

![normal table of values](unit5lec8-probability-density-functions\ea87ae04a5847cb77c55499f828eae2d.png)

The CDF of the normal is so popular it gets its own symbol, $\Phi(y)$.

Let $X$ have mean $\mu$ and variance $\sigma^2 > 0$. What do we do if we have a normally distributed r.v. but it has a different mean and std dev?  
renormalize with $Y = \frac{X - \mu}{\sigma}$.

$Y$ measures the number of std deviations away from the mean. and also $\ex{Y} = 0$ and $\var{Y} = \frac{1}{\sigma^2}\var{X} = 1$.

If $X$ is normal then $Y \sim N(0, 1)$, and we can rewrite $X=\mu + \sigma Y$. This shows that it's possible to just use the standard normal and then change as needed for specific instances.

![problem example](unit5lec8-probability-density-functions\c1db9b6aa35030bf3ea592c5938434f4.png)

Some techniques for using the normal table effectively:
* use symmetry about the mean, so being larger that some value less than the mean is the same as being smaller than some value larger than the mean.
* Use the symmetry for tails, so being smaller than something smaller than the mean is the same as being 1-larger than something larger than the mean.
