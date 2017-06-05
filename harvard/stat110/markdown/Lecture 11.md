## Lecture 11

**Sympathetic magic**

Dont' confuse r,v with its distribution

~~P(X=x) + P(Y=y)~~ 

> Word is not the thing,  the map is not the territory.

r.v -> random house  distribution -> blueprint

### Poisson Distribution - 泊松分布

$X \sim Pois(\lambda)$

#### PMF:

$P(X=k) = e^{-\lambda} \frac{\lambda^k}{k!} $   $\lambda$ is the rate parameter >0

Valid: $\sum_{k=0}^{\infty}e^{-\lambda} \frac{\lambda^k}{k!} = 1$

#### E(X)

$E(X) = \lambda e^{-\lambda}\sum_{k=1}^{\infty} \frac{\lambda^{k-1}}{(k-1)!} = \lambda $  

often used for applications where counting # of "successes" where there are a large  # trials  each with small prob of success

#### Examples:

1. \#emails in an hour
2. \#chips in choc chip cookies
3. \#earthquakes in a year in some area

#### Pois Paradigm (Pois Approximation)

Events $A_1, A_2,\dots A_n$, $P(A_j) = p_j$, $n$ large, $p_j$'s small

events independent or "weakly dependent"  

\# of  Aj's that occure is approx $Pois(\lambda)$, $\lambda = \sum p_j$

**Binomial converges to Poisson**

<u>Example.</u> 

Have n people, find approx  prob that there are 3 people with same birthday.

 $\binom{n}{3}$ triplets of people , indicator r,v for each, I~ijk~ , i<j<k

$E(triple\ matches) = \binom{n}{3}1/365^2$  

X = #triple matches  Approx $Pois(\lambda)$,  $\lambda = \binom{n}{3} 1/365^2$ 

$I_{123}, I_{124}$  are not independent 

$P(X\ge 1) = 1 - P(X=0) \approx 1 - e^{-\lambda}$ 