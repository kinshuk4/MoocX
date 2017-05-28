# Bayes’ rules, Conditional probability, Chain rule

- TUTORIAL
- ​

In the previous tutorial you got introduced to basic probability and the rules dealing with it. Now we are equipped with the ability to calculate probability of events when they are not dependent on any other events around them. But this definitely creates a practical limitation as many events are contingent on each other in reality. This tutorial dealing with conditional probability and bayes' theorem will answer these limitations.

**Conditional Probability**
Conditional probability as the name suggests, comes into play when the probability of occurrence of a particular event changes when one or more conditions are satisfied (these conditions again are events). Speaking in technical terms, if X and Y are two events then the conditional probability of X w.r.t Y is denoted by P(X|Y)P(X|Y). So when we talk in terms of conditional probability, just for an example, we make a statement like "The probability of event X given that Y has already occurred".

**What if X and Y are independent events?**
From the definition of independent events, the occurrence of event X is not dependent on event Y. Therefore P(X|Y)=P(X)P(X|Y)=P(X).

**What if X and Y are mutually exclusive?**
As X and Y are disjoint events, the probability that X will occurs when Y has already occurred is 0. Therefore, P(X|Y)=0P(X|Y)=0

**Product rule**
Product rule states that,

P(X∩Y)=P(X|Y)∗P(Y)P(X∩Y)=P(X|Y)∗P(Y)

So the joint probability that both X and Y will occur is equal to the product of two terms:

Probability that event Y occurs.
Probability that X occurs given that Y has already occurred.

From the product rule, the following can be inferred,

X⊆YX⊆Y implies P(X|Y)=P(X)/P(Y)P(X|Y)=P(X)/P(Y)
Y⊆XY⊆X implies P(X|Y)=1P(X|Y)=1

**Note:** The distributive, associative and De Morgans laws are valid for probability calculation too. The following can be inferred using these laws, 
P(X∪Y|Z)=P(X|Z)+P(Y|Z)−P(X∩Y|Z)P(X∪Y|Z)=P(X|Z)+P(Y|Z)−P(X∩Y|Z)
P(Xc|Z)=1−P(X|Z)P(Xc|Z)=1−P(X|Z)

**Chain rule**
Generalizing the product rule leads to the **chain rule**. Let E1,E2,....EnE1,E2,....En be n events. The joint probability of all the n events is given by,

P(⋂i=1,..,nEi)=P(En|⋂i=1,..,n−1Ei)∗P(⋂i=1,..,n−1Ei)P(⋂i=1,..,nEi)=P(En|⋂i=1,..,n−1Ei)∗P(⋂i=1,..,n−1Ei)

The chain rule can be used iteratively to calculate the joint probability of any no.of events.

**Bayes' theorem**
From the product rule, P(X∩Y)=P(X|Y)P(Y)P(X∩Y)=P(X|Y)P(Y) and P(Y∩X)=P(Y|X)P(X)P(Y∩X)=P(Y|X)P(X). As P(X∩Y)P(X∩Y) and P(Y∩X)P(Y∩X) are both same,P(Y|X)=P(X|Y)∗P(Y)P(X)P(Y|X)=P(X|Y)∗P(Y)P(X)

where, P(X)=P(X∩Y)+P(X∩Yc)P(X)=P(X∩Y)+P(X∩Yc) from sum rule.

https://www.hackerearth.com/practice/machine-learning/prerequisites-of-machine-learning/bayes-rules-conditional-probability-chain-rule/tutorial/