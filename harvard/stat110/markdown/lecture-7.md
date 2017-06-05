## Lecture 7

> Conditioning : the soul of statistics
>
> Random variables and their distribution 

### Gambler's Ruin

Two gamblers, A and B, sequence of rounds bet \$1

$p=P(A\ wins\ a\ certain\ round), q = 1-p$, the game goes until one bankrupt

what's the probability that A wins entire gmae(so B is "ruined")?

**Assume:** A start with $\$i$, B starts with $\$(N-i)$ 



​	**Random walk:** 

​	A particle in the i position, move right 1 step or left 1 step 

​	$p$ = probability of going right.  Absorbing states: reach 0 or N



A either wins the 1st round or lose it 

**Strategy:** condition on first step



Let $P_i = (A\ wins\ game | A\ start\ at\ \$i)$

$P_i = pp_{i+1} + qp_{i-1}, 1 \le i \le N-1$

(difference equation)

$P_0 = 0, P_N = 1$  (boundary)

**Solve difference equation**

Guess $P_i = x^i$

$x^i = px^{i+1} + qx^{i-1} $

$px^2 - x + q = 0$

$x = \{1, q/p\}$

$p_i = A1^i + B(q/p)^i$

$p_0 = 0, B = -A, P_n = 1\Rightarrow 1 = A(1-q/p)^n $  

$P_i = \frac{1-(q/p)^i}{1-(q/p)^N}, if\ p \ne q$  

$P_i = i/N, if\ p=q$  

### Random Variable

It's a function from sample space S to R

think of a as numerical "summary" of an aspect of the experiment.

### Bernoulli

X is said to have Bern Distribution, if X has only 2 possible values , 0 and 1.

$P(X=1) = p, P(X=0) = 1 - p$.  

X = 1 is an event S:X(S) = 1

### Binomial (n,p)

The distribution of #success X in n indep Bern(p) trials is called Bin(n, p) 

its distribution is given by 

$P(X=k) = \binom{n}{k}p^k (1-p)^{n-k} $ 

$X \sim Bin(n,p)$, $Y \sim Bin(m, p)$ independent

Then $X + Y \sim Bin(n + m, p)$ 

 Proof:  consider n trials then m more trials