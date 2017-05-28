# Discrete Random Variables

- TUTORIAL
- ​

In the previous tutorial you got introduced to various concepts of probability. Now let us see how to build functions over the outcomes of a random experiment.

**Discrete Random Variables**

**Def:**
A discrete random variable is defined as function that maps the sample space to a set of discrete real values.X:S→RX:S→Rwhere X is the random variable, S is the sample space and RR is the set of real numbers. Just like any other function, X takes in a value and computes the result according to the rule defined for it.

Elaborating it more, if X is a random variable defined for a certain random experiment with a sample space S, then X=cX=c means the event E containing all possible outcomes ei∈Sei∈S such that X(ei)=cX(ei)=c.

**Note:**
Random variable can take values that are not in sample too. All the values that are not in the sample space are mapped to empty set.

**Example 1:**
From a set of 5 boys and 5 girls, three kids were selected for a painting competition but their genders are not known. Let X be the random variable that denotes the no.of girls selected. What is the event set "c" such that X(c) = 3.

**Solution:**
X(c) = 3 implies all the outcomes where all the 3 kids selected are girls. c = {(GGG)}.

**Probability Mass Function**

Probability mass function (pmf) is the probability defined over a given random variable.For a random variable X, p(X = c) is denoted as p(c) and the mapping of each value in sample space to their respective probabilities is known as pmf. For all the values c that are not in sample space, p(c) is pointing towards the probability of an empty set and the value is equal to 0.
**Note:**
Pmf is just another function that has probability values as results. Hence 0≤p(c)≤10≤p(c)≤1 for any value c.

**Example 2:**
In continuation to example 1, what is the value of p(3) and also write down the pmf function for random variable X.

**Solution:**
Only case in the sample space that has all three kids as girls is (GGG). The probability of that happening is 1/121/12. The total pmf is,

| aa   | p(a)p(a) |
| ---- | -------- |
| 00   | 1/121/12 |
| 11   | 5/125/12 |
| 22   | 5/125/12 |
| 33   | 1/121/12 |

p(4)=0p(4)=0

 as random variable X can never take the value 4 or for that matter any value greater than 3.

**Cumulative distribution function**

We have seen above that subjecting a random variable to equality leads to pmf. Now what if we deal with inequalities? As per the definition, X≤cX≤c leads to the event set containing all the outcomes that satisfy the equality condition from −∞−∞ to cc. When probability function is applied over such an inequality, it leads to a cumulative probability value giving the estimate of the value being less than or equal to cc.

**Def:**
Cumulative distribution function (cdf), denoted by CD(c) = ∑bp(b)∑bp(b) for all −∞≤b≤c−∞≤b≤c.

**Example:**
Continuing the example 2, what is the value of CD(2)? Also calculate the whole CDF for X.

**Solution:**
CD(2)=p(0)+p(1)+p(2)=11/12CD(2)=p(0)+p(1)+p(2)=11/12. Complete CDF is as follows:

| aa   | p(a)p(a) | CD(a)CD(a) |
| ---- | -------- | ---------- |
| 00   | 1/121/12 | 1/121/12   |
| 11   | 5/125/12 | 6/126/12   |
| 22   | 5/125/12 | 11/1211/12 |
| 33   | 1/121/12 | 11         |

**Some specific distributions**

**Uniform distribution**

Starting with the simplest of all distributions, X = Uniform(N) is used to model the scenarios where all the outcomes are equally possible. For example, the probability of getting a heads or tails are equal in case of a fair coin. So, Uniform(N) implies that there are N outcomes and each has a probability of 1/N1/N.

**Bernoulli distribution**

Bernoulli distribution is used to model the scenarios in which each trail of random experiment has exactly two possible outcomes. One possible outcomes is termed as **success** and the other as **failure**. The main parameter **p** is the probability with success might occur in any trial of the random experiment. X = Bernoulli(p) implies the following points,

Denoting the random variable in binary values, it can take 0 (failure) or 1 (success) value for each trail.
X = 1 is attained with a probability of **p** and X = 0 with a probability of **1-p**.

**Binomial distribution**

Binomial distribution is used to model **n** independent trails of Bernoulli distributions. If X follows Binomial(n,p) then, X = k implies, the event of having k successes in a series of n independent Bernoulli trials.P(X=k)=nCk(pk)((1−p)n−k)P(X=k)=nCk(pk)((1−p)n−k)The coefficient nCknCk is for counting the possible ways of picking the k success events in the total n trails and pk∗(1−p)n−kpk∗(1−p)n−k gives the combined probability of n independent trials of Bernoulli distribution.

**Geometric distribution**

Geometric distribution is also defined over Bernoulli distribution but it models the situation in which k failures occur before the first success. So, if X follows geometric(p)P(X=k)=(1−p)k∗pP(X=k)=(1−p)k∗pX = k is the event in which the first success was observed after k failures.

Arithmetic rules are applicable to random variables too. They can be added, subtracted or multiplied together. If X and Y follows geometric distribution with same probability p, then X + Y is also a geometric distribution.

**Note:** Try to write the PMF tables for each of these distributions!

**Expected value** Expected value for a random variable gives the average or mean value calculated over all the possible outcomes of the variable.
**Def:**
If X is a random variable that takes value vivi with a probability of p(vi)p(vi) and has a sample space of size n, then the expected value is,E(X)=∑nvi∗p(vi)E(X)=∑nvi∗p(vi)Expected value is often denoted by μμ.

It is clearly evident from the above equation that expected value need not be in the sample space set of the random variable rather it just gives the information about the central tendency of the random variable as a whole. Expected value follows the same when a random variable is multiplied with a scalar or added to other random variable. It also holds for shifting of variable.
E(X+Y)=E(X)+E(Y)E(X+Y)=E(X)+E(Y)
E(cX+d)=c∗E(X)+dE(cX+d)=c∗E(X)+d

**Variance and Standard deviation**

Expected value is a good measure to represent the random value in a single value. But it also leaves out some vital information that is necessary to understand it better. **Variance** is used to understand the dispersion of probability mass around the mean value of the random value.Var(X)=E((X−μ)2)Var(X)=E((X−μ)2)σ=√(Var(X))σ=(Var(X))Where σσ is the called the standard deviation. Looking at Var(X) in detail, it is evident that the distance of each value from the mean is squared and it's mean is calculated. This leads to calculation of average distance of the probability mass from the mean value. Square of the distance value is taken to handle the sign of the distances calculated as we only need the magnitude.

Following properties hold for variance of a random variable,
Var(aX+b)=a2Var(X)Var(aX+b)=a2Var(X)
Var(X)=E(X2)−(E(X))2Var(X)=E(X2)−(E(X))2
Var(X+Y)=Var(X)+Var(Y)Var(X+Y)=Var(X)+Var(Y) iff X and Y are independent.

**Note:** Try to calculate the mean and variance for all the above described distributions!

| Distribution | Mean     | Variance       |
| ------------ | -------- | -------------- |
| Uniform      | n+12n+12 | n2−112n2−112   |
| Bernoulli    | pp       | p(1−p)p(1−p)   |
| Binomial     | npnp     | np(1−p)np(1−p) |
| Geometric    | 1−pp1−pp | 1−pp21−pp2     |



https://www.hackerearth.com/practice/machine-learning/prerequisites-of-machine-learning/discrete-random-variables/tutorial/