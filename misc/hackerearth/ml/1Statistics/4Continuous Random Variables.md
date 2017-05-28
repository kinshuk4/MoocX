# Continuous Random Variables

- TUTORIAL
- ​

In the last tutorial we have looked into discrete random variables. In this one let us look at random variables that can handle problems dealing with continuous output.

**Continuous Random Variables**
**Def:**
A continuous random variable is as function that maps the sample space of a random experiment to an interval in the real value space. A random variable is called continuous if there is an underlying function f(x)f(x) such thatP(p≤X≤q)=∫qpf(x)dxP(p≤X≤q)=∫pqf(x)dxf(x)f(x) is a non-negative function called the probability density function (pdf). Pdf takes a value 0 for the values out of range(X)range(X). From the rules of probability,P(−∞<X<∞)=∫∞−∞f(x)dx=1P(−∞<X<∞)=∫−∞∞f(x)dx=1

**Note:** Please note that probability mass function is different form probability density function. f(x)f(x) does not give any value of probability directly hence the rules of probability do not apply for it.

Probability of the continuous random variable taking a range of values is given by the area under the curve of f(x)f(x) for that range.

**Example 1:** f(x)=x2f(x)=x2 for continuous random variable X. What is the probability that X takes a value in [0.5,1][0.5,1].
\textbf{\textit{Solution:}}

Integration of f(x)f(x) gives x3/3x3/3 and for the given interval the probability value is 0.292

**Cumulative distribution function**
Cdf remains the same as in the case discrete random variables. Cdf gives the probability value of the random variable taking a value less that given value.
**Def:** Cumulative distribution function (cdf), denoted by Cdf(X≤c)=∫c−∞f(x)dxCdf(X≤c)=∫−∞cf(x)dx
**Example 2:**
X is a random variable with range [2, 4] and pdf f(x)=x/6f(x)=x/6. What is the value of Cdf(X≤2.5)Cdf(X≤2.5).

**Solution:**
Cdf(X≤2.5)=∫2.52=0.1875Cdf(X≤2.5)=∫22.5=0.1875.
Complete CDF is as follows:

| aa       | Cdf(a)Cdf(a) |
| -------- | ------------ |
| <2<2     | 00           |
| ≤2.5≤2.5 | 0.18750.1875 |
| ≤3≤3     | 0.41670.4167 |
| ≤3.5≤3.5 | 0.68750.6875 |
| ≤4≤4     | 11           |

**Note:**
Unlike f(x), cdf(x) is indeed probability count and hence follows the constraint 0≤cdf(c)≤10≤cdf(c)≤1. As probability is non-negative value, cdf(x) is always non-decreasing function.
Important property is cdf′(x)=f(x)cdf′(x)=f(x)
The value of cdf(x) goes to 0 as x→−∞x→−∞ and to 1 as x→∞x→∞.
Generally a continuous random variable is denoted using its cdf function. For ex: X is an random variable with a distribution of cdf(x).

**Some specific distributions**

**Uniform distribution**

Again starting with the simplest of all distributions, X = Uniform(N) is used to model the scenarios where all the outcomes are equally possible. But the difference now is that the outcome is an interval of real values rather than discrete ones. For example, Uniform([c,d]) is when all the values of x(c≤x≤d)x(c≤x≤d) are equally probable with a pdf of 1d−c1d−c.

**Exponential distribution**

Exponential distribution is defined using a parameter λλ and has a pdf f(x)=λe−λx,x≥0f(x)=λe−λx,x≥0.
The important point about exponential distribution is that it is used to model waiting time for an event to occur. Popular example for this is the waiting time for nuclear decay of radioactive isotope is distributed exponentially and λλ is known as the half life of isotope.
Another important aspect of this distribution is its lack of memory. When waiting time of an activity is modeled using exponential distributions, the probability of it happening in next N minutes remains same irrespective of the time passed.

**Proof:**
According to our above claims, we have to prove that P(X>n+w|X>w)=P(X>n)P(X>n+w|X>w)=P(X>n).P(X>n+w|X>w)=P(X>n+w)P(X>w)P(X>n+w|X>w)=P(X>n+w)P(X>w)P(X>n+w)P(X>w)=e−λ(n+w)e−λw=e−λnP(X>n+w)P(X>w)=e−λ(n+w)e−λw=e−λn

**Normal Distribution**

Normal or **Gaussian** distribution is one of the most used and important continuous distribution. It is denoted using N(μ,σ2)N(μ,σ2) where μμ is the mean and σ2σ2 is the variance of the given distribution. **Standard normal distribution**, denoted by Z is normal distribution with mean=0mean=0 and variance=1variance=1.

Normal distribution is used to model stats of large data sets, error measurements in data collected, etc. The interesting point about standard normal distribution is that it is symmetric about the y-axis and follows a **bell curve**.

| Uni(c,d)Uni(c,d) | Exp(λ),x≥0Exp(λ),x≥0 | N(μ,σ2),x∈I!RN(μ,σ2),x∈I!R        |
| ---------------- | -------------------- | --------------------------------- |
| 1d−c1d−c         | λe−λxλe−λx           | 1√2σ2πe−(x−μ)22σ212σ2πe−(x−μ)22σ2 |
| x−cd−cx−cd−c     | 1−e−λx1−e−λx         | 12[1+erf(x−μσ√2)]12[1+erf(x−μσ2)] |

**Expected value**

**Recap:**
Expected value for a random variable gives the average or mean value calculated over all the possible outcomes of the variable. It is used to measure the centrality of the random variable.

**Def:**
If X is a continuous random variable that has pdf as f(x)f(x) then the expected value in interval [c,d] within its range is,E(X)=∫dcx∗f(x)dxE(X)=∫cdx∗f(x)dxExpected value is often denoted by μμ. f(x)dxf(x)dx denotes the probability value with which X can take the infinitesimal range of dxdx.

The following properties of expected value still hold (similar to discrete random variables):

E(X+Y)=E(X)+E(Y)E(X+Y)=E(X)+E(Y)
E(cX+d)=c∗E(X)+dE(cX+d)=c∗E(X)+d

Another additional property is, when Y=h(X)Y=h(X) where XX is a random variable with f(x)f(x) as pdf, E(Y)=E(h(x))=∫∞−∞h(x)f(x)dxE(Y)=E(h(x))=∫−∞∞h(x)f(x)dx

**Variance and Standard deviation**
Var(X)=E((X−μ)2)Var(X)=E((X−μ)2)σ=√Var(X)σ=Var(X)**Recap:**

Where σσ is the called the standard deviation. Looking at Var(X) in detail, it is evident that the distance of each value from the mean is squared and it's mean is calculated. This leads to calculation of average distance of the probability mass from the mean value. Square of the distance value is taken to handle the sign of the distances calculated as we only need the magnitude.

Following properties hold for variance of a continuous random variable too,

Var(aX+b)=a2Var(X)Var(aX+b)=a2Var(X)
Var(X)=E(X2)−(E(X))2Var(X)=E(X2)−(E(X))2
Var(X+Y)=Var(X)+Var(Y)Var(X+Y)=Var(X)+Var(Y) iff XX and YY are independent.

**Quantiles**
One additional measure used for continuous random variable in comparision with discrete one is quantiles.

**Def:**
Value of xx for which cdf(x)=pcdf(x)=p is called the pthpth quantile of XX.
So, median value for the random variable X is the 0.5th0.5th quantile.



https://www.hackerearth.com/practice/machine-learning/prerequisites-of-machine-learning/continuous-random-variables/tutorial/