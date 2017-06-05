---
section: 7
subsection: 15
type: lecture
title: Linear models with normal noise
---

# Lecture 15: Linear models with normal noise

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

## Overview

We will look at a special case of continuous parameter, continuous observation problem where the parameters are independent normal r.v.s.

\[
X_i = \sum_{j=1}^m a_{ij}\Th_j + W_i
\]

with $W_i, \Th_j$ independent normal random variables.

* $\Th_j$ represents the variables to uncover and $W_i$ is just noise
* Very common and convenient model
* $X_i$ are normal because they are the sum of normal r.v.s
* LMS and MAP are the same (peak of normal is at mean) and given by linear functions
* Nice analytic properties
* Long example of estimating trajectory given noisy sensor readings

## Recognizing normal PDFs

Recall Normal r.v.s have the form

![](unit7lec15-linear-models-w-normal-noise\15fbcf9f0cad93331d71b554346c87da.png)

going from specific fns to parameters of normal

easy case:

![](unit7lec15-linear-models-w-normal-noise\49032291edb1fd9f337b877541306d28.png)

pdf has to die out, so exponent must be negative (assume so from now on)

derivation of parameters of PDF

![](unit7lec15-linear-models-w-normal-noise\50189ee9345f739d36b6294cb00da314.png)

this shows us that a distribution of this form is normal.

for the mean we know that's where the distance is maximal, or the part $\alpha x^2 + \beta x + \gamma$ is minimal, which is where its derivative is 0, which is exactly where it is indicated in the box.

## Normal unknown and additive noise

Simple first, complex later.

Additive normal noise. Let $X = \Th + W$ with $\Th, W \sim N(0, 1)$ and independent.

Since they are both cts, we use the appropriate bayes rule

\[
\cpdf{\Th}{X}{\cnd{\th}{x}} = \frac{ \color{blue}{\pdf{\Th}{\th}} \color{red}{\cpdf{X}{\Th}{\cnd{x}{\th}}} }{\color{green}{\pdf{X}{x}}}\\
\pdf{X}{x} = \int \iint{\pdf{\Th}{\th} \cpdf{X}{\Th}{\cnd{x}{\th}} }{\th}
\]

We start by finding the parts of our eqn. Given $\th$,

\[
\color{red}{\cpdf{X}{\Th}{\cnd{x}{\th}}}: X = \th + W
\]

which (since $W$ is standard normal) implies that $X \sim N(\th, 1)$. $\Th$ is standard normal, so we know its pdf.

Using these,

\[
\begin{align}
\cpdf{\Th}{X}{\cnd{\th}{x}} &= \color{green}{\frac{1}{\pdf{X}{x}}} \color{blue}{c_1 e^{-\frac{1}{2}\th^2}} \color{red}{c_2 e^{-\frac{1}{2}(x - \th)^2}}\\
&= c(x)e^{-g(\th)}
\end{align}
\]

where $g(\th)$ is just some quadratic fn of $\th$. Fixing $x$, we can see that this is also normal by previous work.

As discussed previously, a normal r.v. gives us $\thh_\map = \thh_\lms = \cex{\Th}{X = x}$. To find it's value we want to see where $\cpdf{\Th}{X}{\cnd{\th}{x}}$ is maximal, which means we want to minimize $g(\th)$. $g(\th)$ is quadratic and has a positive leading term, so we can just find the $\th$ for which $g^\prime(\th) = 0$. Combining terms gives us

\[
\begin{align}
\frac{\d}{\d\th}\left[g(x)\right] &= \frac{\d}{\d\th}\left[\frac{1}{2}\th^2 + \frac{1}{2}(x - \th)^2\right]\\
&= \th + (\th - x)
\end{align}
\]

Setting that equal to 0 gives us $\th = \frac{x}{2}$. For the estimator we get $\Thh_\map = \cex{\Th}{X} = \frac{X}{2}$.

How special is this example?  
It's not. It also works for general means and variances.
working it out on your own helps determine that the posterior is normal, so the LMS and MAP coincide and the estimators are linear, of the form $\Thh = aX + b$.

Recall:
$N(a, b) + N(c, d) = N(a + c, b + d)$

$Y = aX+b \implies Y \sim N(a\mu + b, a^2\sigma^2)$

then we can accept that $X$ is normal, but what is its estimator?

it will be where the sum of the exponents on $\pdf{\Th}{\th}$ and $\cpdf{X}{\Th}{\cnd{X}{\th}}$ are equal to 0. Given $X = a\Th + bW$ and conditioned on $\Th = \th$, we have $X \sim N(b\mu_W + a\th, b^2\sigma_W^2)$. Let $\Th \sim N(\mu_\Th, \sigma_\Th^2)$ then we have

\[
-(\th - \mu_\Th)^2\cdot\frac{1}{2\sigma_\Th^2} -(x - (b\mu_W + a\th))^2\cdot\frac{1}{2b^2\sigma_W^2}
\]

The negated squared terms are always negative, so...

todo: finish derivation for problem

\[
\frac{1}{b^2\sigma_W^2 + a^2\sigma_\Th^2}\cdot\left(
  \mu_\Th\sigma_W^2 + ax\sigma_\Th^2 - b\mu_W\sigma_\Th^2\right)
\]

## The case of multiple observations

Measure the same variable multiple times with some different noise on each. Now we have **multiple** observations!

\[
\begin{array}{c}
X_1 &= \Th + W_1\\
\vdots&\\
X_n &= \Th + W_n
\end{array}
\]

Where $\Th \sim N(x_0, \sigma_0^2)$ (weirdo notation explained later) and $W_i \sim N(0, \sigma_i^2)$ with $\Th, W_1, \ldots, W_n$ independent.

Like usual we have a bayes rule:

\[
\cpdf{\Th}{X}{\cnd{\th}{x}} = \frac{ \color{blue}{\pdf{\Th}{\th}} \color{red}{\cpdf{X}{\Th}{\cnd{x}{\th}}} }{\color{green}{\pdf{X}{x}}}\\
\pdf{X}{x} = \int \iint{\pdf{\Th}{\th} \cpdf{X}{\Th}{\cnd{x}{\th}} }{\th}
\]

but the $X$ above stands for the vector of values of observations.

So let's start on calculating $\color{red}{\text{this}}$ guy by looking at a single example, $\cpdf{X_i}{\Th}{\cnd{x_i}{\th}}$. Given $\Th = \th$, this becomes $X_i = \th + W_i \sim N(\th, \sigma_i^2)$ and so we can write

\[
\cpdf{X_i}{\Th}{\cnd{x_i}{\th}} = c_i e^{-(x_o - \th)^2 / 2\sigma_i^2}
\]

What about the vector case? This is just a shorthand for the joint PDF

\[
\begin{align}
\cpdf{X}{\Th}{\cnd{x}{\th}} &= \cpdf{X_1,\ldots,X_n}{\Th}{\cnd{x_1,\ldots,x_n}{\Th}}\\
&= \prod_{i=1}^n \cpdf{X_i}{\Th}{\cnd{x_i}{\th}}
\end{align}
\]

Argue that $W_i$ are independent regardless of conditioning on $\Th$, so $X_i$ are independent and this is a known result from before. Now for the posterior...

![](unit7lec15-linear-models-w-normal-noise\9a1f08193fdb0967a5a1cd76b0edfc02.png)

cleaned up

![](unit7lec15-linear-models-w-normal-noise\cc16cc2b061c4ddb963b36951347ec8a.png)

key conclusions
* posterior is norm
* LMS and MAP estimates coincide
* These estimates are linear of the form $\thh = a_0 + a_1 x_1 + \cdots + a_n x_n$.

Interpretations:
each $x_i$ gets multiplied by a cofficient.
weighted average of $x_0$ (prior mean) and $x_i$ (observations)

The weights are determined by the variances, the most weight is given to the terms with the smallest variance.

We stay within random variables, linear functions, and the formula itself is nice and intuitive.

In a multiple-observation problem the $X_i$s are dependent because they are all affected by $\Th$, but if $\Th$ is known then they are conditionally independent.

Just remember the $x_0$ and $\sigma_0^2$ are the mean/variance of the prior on $\Th$

![](unit7lec15-linear-models-w-normal-noise\034e0259d14d74fce1486362d1539716.png)

![](unit7lec15-linear-models-w-normal-noise\55b0009fa5a7c8162deaf0398ce8ee8a.png)


## The mean squared error

conditional mean squared error is the error that is remaining after we have seen the observations.

Conditional on the observations, the value of the estimator is determined.

Determine the performance of the estimator using

![](unit7lec15-linear-models-w-normal-noise\45a1b114d165d5c8b164355e56e0779b.png)

but how do we get the variance.

Since we have

![](unit7lec15-linear-models-w-normal-noise\ea9e06b6f30677c6e49bfa5342351a79.png)

and recall that

![](unit7lec15-linear-models-w-normal-noise\15921a651f966a09b28c3c27ccda1f77.png)

then we know that the variance is fully determined by the coefficient on the $x^2$ term. We're dealing with $\th$ here so we collect all the coefficients and arrive at

![](unit7lec15-linear-models-w-normal-noise\847327628788e9cf4563357038aac5f4.png)

so the conditional variance is

![](unit7lec15-linear-models-w-normal-noise\a3e6475d48f84af4cb883b41a9a2d315.png)

mean squared error given you have seen the information.

But what about the unconditional estimator?

![](unit7lec15-linear-models-w-normal-noise\1ca9630f71acf82b25223bfa01f06ac0.png)

The espected value is a constant we know the value of, and if we pull that out of the integral we're left with an integral of a PDF over all values which is just 1 so we get the original.

Special cases:
* some of the variances of the noise terms is small: then the sum is big and the inverse of that is small. so if even 1 of the measurements has low noise then the MSE will be small
* else if all noise variances are large then the sum will be small, inverted which is big, so the MSE is large.

![](unit7lec15-linear-models-w-normal-noise\cf2a911cfe5a7592904aff238a25ca3f.png)

Another special case, when the variances for all the distributions is the same.

![](unit7lec15-linear-models-w-normal-noise\f808ac76c895b6054d906a9e46bc6df1.png)

As the number of examples that we have goes up the mean squared error goes down.

Also notice that the equality of the expected values means that no little value of little $x$ is very important, they are all treated the same.

An exxample that demonstrates this:

![](unit7lec15-linear-models-w-normal-noise\4fa53417a196fb55a407524845bc40eb.png)

![](unit7lec15-linear-models-w-normal-noise\8ead5b2ef85ff9c2fce6821d6b287ddb.png)


We can say anything we want about the performance of this estimator by giving a single number instead of having to worry about the different values for $X$.

we want to scale our $X$ so our $\Th$ has coeff of 1, not proceed with the above eq as is for weird cases like $Y = 3\Th + W$ (use $Y^\prime = \Th + (W/3)$).

## Multiple parameters, trajectory estimation
