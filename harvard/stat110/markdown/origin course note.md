# Probobility 110

### Abbreviations  缩写

PDF - Probability Density Function 概率密度函数 $f(x)$

CDF -  Cummulative Distribution Function 分布函数 $F(X)$

PMF - Probabilty Mass Function 分布律 $P(X=k)$

R.V -  Random Variable 随机变量$X$  

Moment Generating Function**(MGF) 



## Lecture 1

Statistic is the logic of uncertainty

A **sample space** is the set of all possible outcomes of an experiment

An **event** is a subset of sample space

conterintuitive

Naive definetion of probability:

 P(A) = #favorable outcome / #possible outcomes

Example: file coins

Assumes all outcome equally likely, finite sample space

### Counting

Multip Rule:

if we have experiment with n~1~ possible outcomes, and  

### Sampling table

choose k objects out of n

|               | order matter     | order doesn't      |
| ------------- | ---------------- | ------------------ |
| replace       | n^k^             | $\binom{n+k-1}{k}$ |
| don't replace | n(n-1)...(n-k+1) | $\binom{n}{k}$     |

## Lecture 2

1. Don't lose common sense 
2. Do check answers, especially by doing simple and extreme cases
3. Label people , objects etc. If have n people, then label them 1,2…n

Example: 10 people, split into them of 6, team of 4 => $\binom{10}{6}$  2 teams of 5 => $\binom{10}{5}$ /2

Problem: pick k times from set of n objects, where order doesn't matter, with replacement.

Extreme cases: k = 0; k = 1; n = 2

Equiv : how many ways are there to put k indistinguishable particles into  n distinguishable boxes?



### Story proof- proof by interpretation

Ex1 $\binom{n}{k}$ =  $\binom{n}{n-k}$ 

Ex2  n$\binom{n-1}{k-1}$ = k$\binom{n}{k}$  pick k people out of n, with one desigenate as president.

Ex3 $ \binom{m+n}{k} = \sum_{j=0}^k \binom{m}{j} \binom{n}{k-j} $ ([vandermonde](https://en.wikipedia.org/wiki/Vandermonde_matrix)) (范德蒙)

### Axioms of Probability

**Non-naive definition**

Probability sample consists of S and P, where S is sample space, and P , a function which takes an event $A\subseteq S$ as input, returns $P(A) \in [0,1]$ as output. 

such that 

1. $P(\phi) = 0, P(S) = 1$ 
2. $P(U_{n=1}^{\infty}A_n) =  \sum_{n=1}^{\infty} P(A_n) $ if $A1,A2..An$ are disjoint (not overlap)

## Lecture 3

### [Birthday Problem](https://en.wikipedia.org/wiki/Birthday_problem)

(Exclude Feb 29, assume 365 days equally likely, assume indep. of birth)

k people , find prob. that two have same birthday 

If k > 365, prob. is 1

Let k <= 365, $P(no  match) =\frac{ 365 * 364 *... (365 - k + 1) }{365^k}$ 

P(match) ~ 50.7%, if k = 23; 97% if k = 50; 99.9999%, if k = 100

$\binom{k}{2} =  \frac{k(k-1)}{2} $  $\binom{23}{2} = 253$ 

### Properties of Probability

1. $P(A^c) = 1 - P(A)$    
2. If $A \subseteq B$ , then $P(A) \subseteq P(B)$ 
3. $P(A\cup B) = P(A) + P(B) - P(A\cap B)$

$P(A\cup B\cup C) = P(A) + P(B) + P(C) - P(A\cap B) - P(A\cap C) - P(B\cap C) + P(A\cap B\cap C)$ 

**Proof:**

1. $1 = P(S) = P(A\cap A^c) = P(A) + P(A^c)$
2. $B = A\cup(B\cap A^c)$  $P(B) = P(A)+P(B\cap A^c)$ 
3. $P(A\cup B) = P(A\cap (B\cap A^c)) = P(A) + P(B\cap A^c)$ 

General case: 



### deMontmort's Problem(1713)

**[matching problem](http://www.math.uah.edu/stat/urn/Matching.html)** 

n cards labeled 1 to n, flipping cards over one by one, you win if the card that you name is the card that appears.

 Let $A_j$  be the event, ''jth card matches" 

$P(A_j) = 1 / n $ since all position equally likely for card labeled j

$P(A_1\cap A_2) = (n-2)! / n! = 1/n(n-1)$ 

...

$P(A_1\cap … A_k) = (n-k)! / n!$ 

$P(A_1\cup …A_n) = n*1/n - n(n-1)/2 * 1/n(n-1) + …$

$= 1 - 1/2! + 1/3! - 1/4! … (-1)^n1/n!$ $\approx 1- 1/e$ 

  ## Lecture 4

Definition:** Events A, B are independent if $P(A\cap B) = P(A)P(B)$ 

Note: completely different form disjointness

$A, B, C$ are independent, if $P(A, B) = P(A)P(B)$, $P(A,C) = P(A)P(C)$, $P(B,C) = P(B)P(C)$ and $P(A, B, C) = P(A)P(B)P(C)$

Similarly for events A1,…An

### Newton-Pepys Problem(1693)

> The **Newton–Pepys problem** is a [probability](https://en.wikipedia.org/wiki/Probability) problem concerning the probability of throwing sixes from a certain number of dice.
>
> In 1693 [Samuel Pepys](https://en.wikipedia.org/wiki/Samuel_Pepys) and [Isaac Newton](https://en.wikipedia.org/wiki/Isaac_Newton) corresponded over a problem posed by Pepys in relation to a [wager](https://en.wikipedia.org/wiki/Gambling) he planned to make. The problem was:
>
>
> *A. Six fair dice are tossed independently and at least one “6” appears.*
>
> *B. Twelve fair dice are tossed independently and at least two “6”s appear.*
>
> *C. Eighteen fair dice are tossed independently and at least three “6”s appear.*
>
> Pepys initially thought that outcome C had the highest probability, but Newton correctly concluded that outcome A actually has the highest probability.
>
> Quoted from Wikipedia : [Newton–Pepys problem](https://en.wikipedia.org/wiki/Newton%E2%80%93Pepys_problem)

**Answer:**

$P(A) = 1 - (5/6)^6 \approx 0.665$

$P(B) = 1 - (5/6)^{12} - 12 *(1/6)(5/6)^{11}  \approx  0.619$ 

$P(C) = 1 - \sum_{k=0}^2 \binom{18}{k} (1/6)^k (5/6)^{(18-k)}  \approx 0.597$ 

### Conditional Probability 条件概率

— How should you update probability/beliefs/uncertainty based on new evidence?

> "Conditioning is the soul of statistic" 

#### Definition:

$$P(A|B) = \frac{P(A\cap B)} {P(B)}$$, if $P(B) > 0$

#### Intuition: 

1. Pebble world , there are finite possible outcomes, each one is represented as a pebble. For example, 9 outcomes, that is 9 pebbles , total mass is 1. B: four pebbles, $P(A|B)$:get rid of pebbles in $B^c$ , renormalize to make total mass again

2. Frequentist world: repeat experiment many times

   (100101101) 001001011 11111111 

    circle repeatitions where B occurred ; among those , what fraction of time did A also occur?

### Theorem

1. $P(A\cap B) = P(B)P(A|B) = P(A)P(B|A)$
2. $P(A_1…A_n) = P(A_1)P(A_2|A_1)P(A_3|A_1,A_2)…P(A_n|A_1,A_2…A_{n-1})$
3. $$P(A|B) = \frac{P(B|A)P(A)}{P(B)}$$ 

## Lecture 5

**Thinking** conditionally is a condition for thinking

**How to solve a problem?**

1. Try simple and extreme cases

2. Break up problem into simple pieces 

   $P(B) = P(B|A_1)P(A_1) + P(B|A_2)P(A_2) +…P(B|A_n)P(A_n)$

   law of total probability 

   ​

<u>Example 1</u> 

Suppose we have 2 random cards from standard deck

Find $P(both\  aces|have\ ace)$, $P(both\ aces|have\ ace\ of\ spade)$

$$P(both\ aces|have\ ace) = P(both\ aces,\cancel{have\ ace}) / P(have\ ace) =  \frac{\binom{4}{2}/\binom{52}{2}}{1-\binom{48}{2}/\binom{52}{2}} = 1/33$$

$P(both\ aces|have\ ace\ of\ spade) = 3/51 = 1/17 $ 

<u>Example 2</u> 

Patient get tested for disease afflicts 1% of population, tests positve (has disease)

Suppose the test is advertised as "95% accurate" , suppose this means 

$D$: has disease, $T$: test positive

Trade-off: It's rare that the test is wrong, it's also rare the disease is rare

$P(T|D) = 0.95 = P(T^c |D^c)$

$$P(D|T) = \frac{P(T|D)P(D)}{P(T)} = \frac{P(T|D)P(D)}{P(T|D)P(D) + P(T|D^c)P(D^c}$$  



### Biohazards

1. confusing $P(A|B)$, $P(B|A)$  ([procecutor's fallacy](https://en.wikipedia.org/wiki/Prosecutor%27s_fallacy)) 

<u>Ex</u> [Sally Clark](https://en.wikipedia.org/wiki/Sally_Clark) case, SIDS

want P(innocence |evidence) 

2. confusing $P(A) - prior先验$ with $P(A|B) - posterior 后验$

P(A|A) = 1

3. confusing independent with conditional independent 




**Definition**:

 	Events $A,B$ are conditionally independent given $C$ if $P(A\cap B|C) = P(A|C)P(B|C)$

**Q:**Does conditional indep given C imply indep. ? No

Ex. Chess opponent of unknown strength may be that game outcomes are conditionally independent given strength 

**Q:**Does independent imply conditional independent given C? No

Ex. A: Fire alarm goes off, cause by : F:fire; C:popcorn. suppose F, C independent But P(F|A, C^c^) = 1 not conditionally indep given A



### Recommendations 推荐书籍

_Statistical science in the courtroom_ 

_Statistics for lawyers_




## Lecture 6

### Monty Hall

> Suppose you're on a game show, and you're given the choice of three doors: Behind one door is a car; behind the others, goats. You pick a door, say No. 1, and the host, who knows what's behind the doors, opens another door, say No. 3, which has a goat. He then says to you, "Do you want to pick door No. 2?" Is it to your advantage to switch your choice?
>
> From Wikipedia [Monty Hall problem](https://en.wikipedia.org/wiki/Monty_Hall_problem)

1 door has car, 2 doors have goats, Monty knows which door has car.

Monty always open a goat door. If he has a choice , he picks with equal probability.  Should you switch?

Note: if Monty opens door 2, we know door 2 has a goat, and Monty opened door 2

**LOTP: Law of Total Probability 全概率定理**

wish we knew where the car is

$S$ : succeed (assuming switch)

$D_j$ = Door j has car (j = 1, 2, 3)

You choose door 1 and the host opens door 3

$P(S) = P(S|D_1)\frac{1}{3}+ P(S|D_2) \frac{1}{3} + P(S|D_3) \frac{1}{3}$

$= 0 + 1 * \frac{1}{3}+ 1* \frac{1}{3} =\frac{2}{3}$

By symmetry P(S|Monty opens 2) = 2/3

### Simpson's Paradox

| Dr.Hibbert | heart | bandaid |
| ---------- | ----- | ------- |
| success    | 70    | 10      |
| failure    | 20    | 0       |

| Dr.Nick | heart | bandaid |
| ------- | ----- | ------- |
| success | 2     | 81      |
| failure | 8     | 9       |



$A$: successful surgery

$B$: treated by Nick

$C$: heart surgery

$P(A|B) = 81\% > P(A|B^c) = 80\%$ 

$P(A|B, C)  < P(A|B^c,C)$

$P(A|B, C^c) < P(A|B^c, C^c)$

C is a confounder

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

### Random Variable - 随机变量

It's a function from sample space S to R

think of a as numerical "summary" of an aspect of the experiment.

### Bernoulli - 伯努利

X is said to have Bern Distribution, if X has only 2 possible values , 0 and 1.

$P(X=1) = p, P(X=0) = 1 - p$.  

X = 1 is an event S:X(S) = 1

### Binomial (n,p) - 二项分布

The distribution of #success X in n indep Bern(p) trials is called Bin(n, p) 

its distribution is given by 

$P(X=k) = \binom{n}{k}p^k (1-p)^{n-k} $ 

$X \sim Bin(n,p)$, $Y \sim Bin(m, p)$ independent

Then $X + Y \sim Bin(n + m, p)$ 

 Proof:  consider n trials then m more trials

## Lecture 8

### Binomial Distribution

$ X\sim Bin(n,p)$ 

1. Story: X is #sucess in n **independent** Bern(p) trials

2. sum of indicator : $X=X_1 + X_2 +…+X_n$;  

   $X_j = 1$ if jth trial success, 0 otherwise   

   i.i.d.Bern(q)  => independent identically distributed

3. **PMF** $P(X=k) = \binom{n}{k}p^k (1-p)^{n-k}$  



#### CDF

$X\le x$ is an event

$F(x) = P(X\le x)$

then F is the CDF of X (**cummulative distribution function**) 

**PMF**(for discrete  r.v.s)  

Discrete: possible values $a_1, a_2, …,a_n$ could be listed out

$P(X=a_j)$ for all $j = p_j$

$p_j \ge 0$, $\sum p_j = 1$  



$X\sim Bin(n,p), Y\sim Bin(m,p) \Rightarrow X+Y \sim Bin(n+m, p)$

**Proof:**

1. immediate from story

2. $X = x_1 +…+ x_n, Y = y_1 +…+ y_m \Rightarrow X+Y = \sum x + \sum y$

   sum of n+m i.i.d Bern(p) => Bern

3. $P(X+Y=k) = \sum_{j=0}^k P(X+Y=k|X=j)P(X=j) $ 

   $ = \sum_{j=0}^k P(Y=k-j|X=j) \binom{n}{j}p^j q^{n-}j$   

   $= \sum_{j=0}^k \binom{m}{k-j} p^{k-j}q^{m-k-j} \binom{n}{j} p^j q^{n-j}$

   $=p^k q^{m+n-k} \sum_{j=0}^k \binom{m}{k-j} \binom{n}{j}$ 

   VanderMonde $\sum_{j=0}^k \binom{m}{k-j} \binom{n}{j} = \binom{m+n}{k}$

   convolution

<u>Ex.</u> 5 card hand find distribution of #aces  - PMF(or CDF)

let X = (#aces)  find $P(X=k)$, 

$$P(X=k) = \frac{\binom{4}{k} \binom{48}{5-k}}{\binom{52}{5}}, for\ k \in \{0,1,2,3,4\}$$

 <u>Not</u> Binomial.  Like the elk problem(homework)

### Hypergeometric - 超几何

**Story:** Have b black, w white marbles. Pick simple random sample of size n. 

Find distribution of (# white marbles in sample) = X

$$P(X=k) =\frac{\binom{w}{k} \binom{b}{n-k}}{\binom{w+b}{n}}, 0\le k\le w, 0\le n-k \le b$$ 

**Hypergeometric** sampling without replace 

$$\frac{1}{\binom{w+b}{n}} \sum_{k=0}^w \binom{w}{k} \binom{b}{n-k} = 1$$  



CDF $P(X\le x)$ 

## Lecture 9

### CDF 概率分布函数

$F(x) = P(X\le x)$ ,  as a function of real $x$

$P(a<X<b) = F(b) - F(a)$ 

#### Properties of CDF

1. increasing
2. right continuous 
3. $F(x) \to 0\ as\ x \to - \infty,\  F(x)\to 1\ as\ x \to \infty $

This is "only if"

### Independence of r.v.s

$X,Y$ are independent r.v.s if $P(X\le x, Y\le y) = P(X\le x)P(Y\le y)$ for all $x, y$

Discrete case : $P(X=x, Y=y) = P(X=x)P(Y=y)$ 

### Average(Means, Expected Values) - 均值（期望）

<u>Example</u>

1,2,3,4,5,6, -> 1+2+..+6 / 6 = 3.5

1,1,1,1,1,3,3,5

two ways: 

1. add, divide 


2. 5/8 * 1 +2/8 * 3 + 1/8 * 5

#### Average of a discrete r.v.s

$E(X) = \sum_{} xP(X=x)$  

summed over $x$ with $P(X=x) > 0$

##### X~ Bern(p)

$E(x) = 1P(X=1) +0P(X=0) = p$

X = 1 if A occurs , 0 otherwise (indicator r.v.s)

Then $E(x) = P(A)$  **fundamental bridge** between E and P

##### X~Bin(n,p)

$E(X) = \sum_{k=0}^n k\binom{n}{k} p^k q^{n-k}$ 

$=  \sum_{k=1}^n n \binom{n-1}{k-1} p^k q^{n-k}$

$ = np \sum_{k=0}^n  \binom{n-1}{k-1} p^{k-1} q^{n-k}$ 

$ = np \sum_{j=0}^{n-1}  \binom{n-1}{j} p^{j} q^{n-j-1}$ 

$=np$  

#### Linearity 线性

1. $E(X+Y) = E(X) +E(Y)$  even if $X, Y$ are dependent
2. $E(cX) = cE(X)$ 

##### Redo Bin

$E(X) = np$  by linearity since $X = x_1+..+x_n$ 



<u>Ex.</u> 5 Card hand ,$X = \#aces$ let $X_j$ be indecator of jth card being as ace, $1\le j \le 5$

$E(X) =(indicator) E(X_1 + ..+X_5) =(linearity) E(X_1) +..+ E(X_5) = (symmetry) 5E(X_1)$

$= (fundamental\ bridge) 5P(1st\ card\ ace) = 5/13$ 

even though $X_j$'s are dependent

This gives expected value of any Hypergeometric

### Geometric 几何分布

$Geom(p)$ : independent $Bern(p)$ trials, count # failures before 1st success.

Let $X\sim Geom(p)$  , $q = 1-p$  

#### PMF:

 $P(X=k) = q^k p$  valid since $\sum_{k=0}^{\infty} pq^k = p/1-q = 1$

  $E(x) = \sum_{k=0} kpq^k$

$=p \sum_{k=1} kq^k$

$= q/p$

#### E(X) Story Proof:

Let $c = E(X)$,

$c = 0*p + (1+c)q$

$= q+cq => c = q/p$ 

## Lecture 10

### Linearity

Let $T = X+Y$, show $E(T) = E(X)+E(Y)$ 

$\sum_{t} P(T=t) = \sum_{x} xP(X=x) + \sum_{y} yP(Y=y)$  

Extreme dependent $X=Y$

$E(X+Y) = E(2X) =2E(X)$ 

### Negetive Binomial

parameters $r,p$

**story:** independent $Bern(p)$ trials #failures before the $r$th success

**PMF**: $P(X=n) =   \binom{n+r-1}{r-1} p^r (1-p)^n$   

**E(X):** $E(X) = E(X_1+\dots +X_r) = E(X_1) +\dots +E(X_r) = rq/p

$X_j$ is #failures between $(j-1)$th and $j$th success, $X_j\sim Geom(p)$  

###Geom

$X\sim FS(p)$ time until 1st success , counting the success

Let $Y = X-1$, Then $Y\sim Geom(p)$ 

$E(X) = E(Y+1) = E(Y) + 1 =q/p + 1 = 1/p$ 

### Putnam

Random permutation of $1,2,..n$ , where  $n\ge  2$

Find expected # of local maxima.  Ex. **3**214**7**5**6**  

Let $I_j$  be indecator r.v of position $j$ having a local max, $1\le j \le n$

$E(I_1 + \dots +I_n) = E(I_1)+\dots +E(I_n) = \frac{n-2}{3}  + 2/2 = \frac{n+1}{3}$   

### St.Petersburg Paradox

Get $2^x$ where X is #filps of fair coin until first H, including the success

$Y = 2^x$ find $E(Y)$ 

$E(Y) = \sum_{k=1}^{\infty} 2^k \frac{1}{2^k} = \sum_{} 1 = \infty$   

bound at $$2^{40}$$  .  Then $\sum_{k=1}^{40} 2^k \frac{1}{2^k} = 40$   

$E(2^x) =\infty$ not $q= 2^{E(x)} = 4$ 

## Lecture 11

**Sympathetic magic**

Dont' confuse r,v with its distribution

~~P(X=x) + P(Y=y)~~ 

> Word is not the thing,  the map is not the territory.

r.v -> random house  distribution -> blueprint

### Poisson Distribution - 泊松分布

$X \sim Pois(\lambda)$

####PMF:
$P(X=k) = e^{-\lambda} \frac{\lambda^k}{k!} $   $\lambda$ is the rate parameter >0

Valid: $\sum_{k=0}^{\infty}e^{-\lambda} \frac{\lambda^k}{k!} = 1$
####E(X)
$E(X) = \lambda e^{-\lambda}\sum_{k=1}^{\infty} \frac{\lambda^{k-1}}{(k-1)!} = \lambda $  

often used for applications where counting # of "successes" where there are a large  # trials  each with small prob of success

####Examples:
1. \#emails in an hour
2. \#chips in choc chip cookies
3. \#earthquakes in a year in some area

####Pois Paradigm (Pois Approximation)

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

## Lecture 12

|             | Discrete离散                     | Continuous连续                             |
| ----------- | ------------------------------ | ---------------------------------------- |
| **PMF／PDF** | $P(X=x)$                       | $f_x(x) = F'_X(x)$                       |
| **CDF**     | $F_x(x) = P(X\le x)$           | $F_X(x) = P(X\le x)$                     |
| **E(X)**    | $E(x) = \sum_{} xP(X=x)$       | $E(X) = \int xf(x)dx$                    |
|             |                                |                                          |
| **LOTUS**   | $E(g(x)) = \sum_{} g(x)P(X=x)$ | $E(g(X)) = \int_{-\infty}^{\infty} g(x)f(x)dx $ |
|             |                                |                                          |


### PDF - Probability Density Function

#### Definiton:

R.v X has PDF f(x) if $P(a\leq X\leq b) = \int_{a}^{b} f(x)dx $  for all a and b

#### To be valid:

1. $f(x) \ge 0$
2. $\int_{-\infty}^{\infty} f(x)dx = 1$  



If X has PDF f, the CDF is $F(x) = P(X\leq x) = \int_{-\infty}^{x}f(t)dt $   

If X has CDF F ( and X is continous ), then $f(x) =F'(x)$ by FTC

$P(a\le X\le b) = \int_{a}^{b} f(x)dx = F(b) - F(a )$   

### Var & Std

#### Variance - 方差

$Var(X) = E(X - EX)^2$  

Another Way to Express Var:

$Var(X) = E(X^2 - 2X(EX) + (EX)) = E(X^2) - 2E(X)E(X) + (EX)^2 $

$= E(X^2) - (EX)^2$

**Notation** $EX^2 = E(X^2)$ 

#### Standard deviation - 标准差

 $SD(X) = \sqrt{Var(X)}$  

### Uniform - 均匀分布

$Unif(a,b)$

probability length

#### PDF

 f(x) = c, if a <= x <= b; 0, otherwise 

#### CDF

$1 = \int_{a}^b cdx = c = \frac{1}{b-a}$ 
$$
F(X)= \int_{-\infty}^{x} f(t)dt = 0, if x < a; 1, if x > b; x-a/x-b, if a<=x <= b;
$$
#### E(X)

$E(X) = \int_{a}^{b} \frac{x}{b-a} dx = \frac{a+b}{2} $  

$Y = X^2, E(X^2) =E(Y) = \int_{-\infty}^{\infty} x^2 f(x)dx $ 

**law of the unconscious statistician (LOTUS)**

$E(g(X)) = \int_{-\infty}^{\infty} g(x)f(x)dx $  

#### Var(U)

Let $U\sim Unif(0,1),\ E(U) = 1/2$ ,$ E(U^2) = \int_{0}^{1} u^2f(u)du = 1/3$

$Var(U) = 1/3 - 1/4 = 1/12$ 

#### Universality

> Uniform is Universal 

Let $U\sim Unif(0,1)$ F be CDF (assume F is strictly increasing and continuous)

Then Let $X = F^{-1}(U)$, Then $X \sim F$  

##### Proof:

 $P(X\leq x) = P(F^{-1} (U) \leq x) =P(U\leq F(x)) = F(x)$ 

## Lecture 13

### Universality of Unif

let  F be a cont. strictly increasing CDF

Then $X = F^{-1}(U) \sim  F$ if $U\sim unif(0, 1)$

Also: if $X\sim F$, then $F(X)\sim Unif(0,1)$

$F(x) = P(X\leq x)$ ~~F(X) = P(X \leq X) = 1~~ 

Ex. Let  $F(x) = 1 - e^{-x}, x>0$  (Expo(1)), $U\sim Unif(0,1)$ 

simulate X~F. $F^{-1}(u) = -\ln (1-u)$  => $F^{-1}(U) = -\ln (1-U) \sim F $ 

$1-U \sim Unif(0, 1)$ symmetry of Unif

$a+bU$ is Unif on some interval. Nonlinear usually -> Non Unif. 

### Independent of r.v.s 

$X_1,\dots X_n$

#### Definiton:

$X_1,\dots X_n$ independent if $P(X_1 \le x_1,\dots X_n\le x_n) = P(X_1\le x_1)\dots P(X_n\le x_n)$

for all $x_1,\dots x_n$

##### Discrete case

 joint PMF $P(X_1 = x_1,\dots X_n=x_n) = P(X_1=x_1)\dots P(X_n=x_n) $ 

#### Example

$X_1, X_2 \sim Bern(1/2)$ i.i.d, $X_3 = 1$ if $X_1 = X_2$; 0 otherwise

These are pairwise indep, not indep

### Normal Distribution 正态分布

(Central Limit Therom: sum of a lot of i.i.d r.v.s looks Normal )

$N(0, 1)$ - mean = 0, var = 1 

has PDF  

$$f(z) = ce^{\frac{z^2}{2}}$$ 

 c - normalizing const，$c = 1/\sqrt{2\pi}$ 

$Z\sim N(0,1)$ 

$EZ = 0$  by symmetry odd function

$E(Z^3) = 0$  "3rd moment"

$ Var(Z) = E(Z^2) - (EZ)^2 = E(Z^2) = 1$  LOTUS

Notation : $\Phi$ is the standard Normal CDF 

 $$\Phi(z) = \frac{1}{\sqrt{2\pi}} \int_{-\infty}^z e^{-\frac{t^2}{x}}dt$$  

$\Phi(-z) = 1- \Phi(z)$  by symmetry

## Lecture 14

$-Z\sim N(0,1)$   (symmetry) Normal

Let $X = \mu + \sigma Z$ - $\mu$ (mean, location) $\sigma >0$ (SD, scale) 

Then we say $X \sim N(\mu, \sigma^2)$ 

$E(X) = \mu, Var(X) = \sigma^2Var(Z)$ 



$Var(X+c) = Var(X)$ 

$Var(cX) = c^2 Var(X)$ 

$Var(X) \geq 0, Var(X) = 0$, if and only if $P(X=a) = 0$, for some a

$Var(X +Y) \ne Var(X) + Var(Y)$  in general , Var not linear

[equal if X,Y are independent]

$Var(X+X) = Var(2X) = 4Var(X)$ 



$Z = \frac{X-\mu}{\sigma}$  standard 

Find PDF of $X \sim N(\mu, \sigma^2)$ 

CDF: $P(X\le x) = P(\frac{X-\mu}{\sigma} \le \frac{x-\mu}{\sigma}) = \Phi(\frac{X-\mu}{\sigma})$ 

$-X = -\mu + \sigma(-Z) \sim N(-\mu, \sigma^2)$ 

Later we'll show if $X_j \sim N(\mu_j,\sigma_j^2)$ indep, 

$X_1 + X_2 \sim N(\mu_1 + \mu_2, \sigma_1^2+\sigma_2^2)$ 

$X_1 - X_2 \sim N(\mu_1 - \mu_2, \sigma_1^2+\sigma_2^2)$ 

**68-95-99.7% Rule**

### LOTUS

X: 0, 1, 2, 3...

X^2^: 0, 1, 4 ,9...

$E(X) = \sum_{x} P(X=x)$

$E(X^2) = \sum_{x} x^2 P(X=x)$ 

$X \sim Pois(\lambda)$

$E(X^2) = \sum_{k=0}^{\infty}k^2 e^{-\lambda} \frac{\lambda^k}{k!}  = \lambda^2 + \lambda$ 

$Var(X) =  \lambda^2 + \lambda - \lambda^2 = \lambda$

$\sum_{k=0}^{\infty} \frac{\lambda^k}{k!} = e^{\lambda}$ always true

$\lambda \sum_{k=1}^{\infty} \frac{k \lambda^{k-1}}{k!} =\lambda e^{\lambda}$ 

$\sum_{k=1}^{\infty} \frac{k^2 \lambda^{k-1}}{k!} =(\lambda+1) e^{\lambda}$ 



$X\sim Bin(n,p)$ Find Var(X)

$X = I_1 + ..I_n, I_j \sim Bern(p)$ 

$X^2 = I_1^2 +..+ I_n^2 + 2I_1I_2+..+ 2I_{n-1}I_n$ 

$E(X^2) = nE(I_1^2) + 2\binom{n}{2}E(I_1I_2)$  indicator of success on both trials 1,2

$= np + n(n-1)p^2 = np + n^2p^2 - np^2$

$Var(X) = np - np^2 = np(1-q) = npq, q=(1-p)$   



#### Prove LOTUS for discrete sample space

$E(g(x)) = \sum_{} g(x)P(X=x)$ 

 group $\sum_{x} g(x)P(X=x) = \sum_{s\in S} g(X(s))P(\{s\})$   ungrouped

## Lecture 15

### Coupon collector(Toy collector)

n toy types, equally likely,  find expected time until have complete set

$T = T_1 + T_2 + \dots T_n$

$T_1$ =(time until 1st new toy) = 1

$T_2$ = (Addtional time until 2nd new toy)

$T_3$ = (…..until 3rd )

$T_1$ = 1 

$T_2 - 1 \sim Geom((n-1)/n)$ 

$T_j - 1 \sim Geom(\frac{n-(j-1)}{n})$ 

$E(T) = E(T_1) + E(T_2) +… E(T_n) = 1 + n/(n-1)+ n/(n-2) + n/1 = n(1 + 1/2 +…+ 1/n) \approx n\log n$  

### Universality

$X\sim F$

$F(x_0) = 1/3$

$P(F(X) \le 1/3) = P(X \le x_0) = F(x_0) = 1/3$ 

### Logistic Distribution

$$F(x) = \frac{e^x}{1 + e^x}$$

$U\sim Unif(0, 1)$ , consider $F^{-1}(U) = \log \frac{U}{1-U}$ is logistic



Let $X, Y, Z$ ,be i.i.d positive r.v.s  Find $E(\frac{X}{X+Y+Z})$ 

$E(\frac{X}{X+Y+Z})  = E(\frac{Y}{X+Y+Z}) = E(\frac{Z}{X+Y+Z})$ by symmetry 

$E(\frac{X}{X+Y+Z})  + E(\frac{Y}{X+Y+Z}) + E(\frac{Z}{X+Y+Z}) = E(\frac{X+Y+Z}{X+Y+Z}) = 1$ by linearity

$E(\frac{X}{X+Y+Z}) = 1/3$ 

**LOTUS**

$U\sim Unif(0,1)$  $X=U^2, Y=e^x$

Find E(Y) as an integral

$E(Y) = \int_{0}^{1}e^{x}f(x)dx$  f(x) PDF of x,  need more work

$P(U^2 \le x) = P(U\le \sqrt{x}) = \sqrt{x},0 <x<1$ 

​ 

Better: $Y = e^{U^2}$  $E(Y) = \int_{0}^{1}e^{U^2}dU$  



$X\sim Bin(n,p), q =1-p$

find distribution of n-X

$P(n-X=k) = P(X=n-k) = \binom{n}{n-k}p^{n-k}q^k = \binom{n}{k}q^kp^{n-k}$ 

story: $n-X \sim Bin(n,q)$ 

by swapping ''success and failure" 



Ex #emails I get in time t is $Pois(\lambda t)$ 

Find PDF of T, time of 1st  email.

$P(T>t) = P(N_t = 0) = e^{-\lambda t}(\lambda t)^0 / 0! = e^{-\lambda t}$ 

with N_* = (#emails in [0, *])

CDF is $1 - e^{-\lambda t}, t>0$ 



Distribution is the blueprint, for creating random variable, that was our random house and then don't confuse random variable with a constant 

constant would be like a specific house

the random vairable is that the random house

$f(x) = \frac{1}{2}x^{-\frac{1}{2}}, x\in (0,1)$ 

## Lecture 16

#### Exponential Distribution

rate parameter $\lambda$

$X \sim Expo(\lambda)$ has PDF $\lambda e^{-\lambda x}, x>0$ 0 otherwise

CDF $F(x)= \int_{0}^{x}\lambda e^{-\lambda t}dt = 1 - e^{\lambda x}, x>0$ 

##### Example

Let $Y= \lambda X$ then $Y\sim Expo(1)$ 

since $P(Y\le y) = P(X\le y/\lambda)= 1- e^{-y}$ 



Let $Y\sim Expo(1)$ find $E(Y), Var(Y)$

$E(Y) = \int_{0}^{\infty} ye^{-y}dy = 1$ , $du = dy, dv = -e^{-y}$ 

$Var(Y) = E(Y^2) - (EY)^2 = 1$  LOTUS

So $ X=Y/\lambda$ has $E(X) = 1/\lambda, Var(X) = 1/\lambda^2$ 

##### Memoryless Property 

$P(X\ge s+t|X\ge s) = P(X\ge t)$ 

Here $P(X\ge s) = 1  -P(X\le s) = e^{-\lambda s}$

$P(X\ge s+t|X\ge s) = P(X\ge s+t, X\ge s) / P(X\ge s) =  P(X\ge s+t) / P(X\ge s)  = e^{-\lambda t} = P(X\ge t)​$ 

$X\sim Expo(\lambda)$

$E(X|X>a) = a + E(X-a|X>a) = a + q/\lambda$  by memoryless

## Lecture 17

$E(T|T>20) > E(T)$  

If memoryless, we would have $E(T|T>20) = 20 + E(T)$

**Therom**: If $X$ is a positive continuous r.v.  with memoryless property, then $X\sim Expo(\lambda)$  for some $\lambda$ 

**Proof** Let $F$ be the $CDF$ of $X$, $G(x) = P(X>x) = 1 - F(x)$

memoryless property is $G(s+t) = G(s)G(t)$ solve for G.

let $s=t$, $G(2t) = G(t)^2$ , $G(3t) = G(t)^3$ …$G(kt) = G(t)^k$ 

$G(t/2) = G(t)^{1/2}$ …$G(t/k) = G(t)^{1/k}$

$G(\frac{m}{n} t) = G(t)^{\frac{m}{n}}$  So $G(xt) = G(t)^x $ for all real x >0

let $t = 1$, $G(x) = G(1)^x = e^{x\ln G(1)} = e^{-\lambda x}$  $ lnG(1) = -\lambda$ 

### Moment Generating Function(MGF)

#### Definition

A r.v X has MGF $M(t) = E(e^{tx})$ 

as a function of t, if this is finite on some (-0, a), a>0

t is just a placeholder

Why moment "generating" ?

$$E(e^{tx}) = E(\sum_{n=0}^{\infty} \frac{x^n t^n}{n!}) = \sum_{n=0}^{\infty}\frac{E(x^n)t^n}{n!}$$  $E(x^n)$ - nth moment



#### Three reasons why MGF important: 

Let X have MGF M(t)

1. The nth moment $E(x^n)$ , is coef of $\frac{t^n}{n!}$  in Taylor series of M, 

   and    $M^{(n)}(0) = E(X^n)$ 

2. MGF determines the distribution.  i.e. if X,Y have same MGF , then they have same CDF

3. If X has MGF $M_x$, Y has MGF $M_y$, X independent of Y, then MGF of X+Y is $E(e^{t(X+Y)}) = E(e^{tX})  + E(e^{tY})$ 

#### Example

 $X\sim Bern(p)$, $M(t) = E(e^{tX}) = pe^t + q, q= 1-p$

$X\sim Bin(n, p) => M(t) = (pe^t + q)^n$ 

$Z\sim N(0,1)$  => $$M(t) = \frac{1}{\sqrt{2\pi}}\int_{-\infty}^{\infty}e^{tZ - \frac{Z^2}{2}}dz$$ 

$$= \frac{e^{t^2/2}}{\sqrt{2\pi}}\int_{-\infty}^{\infty}e^{-\frac{1}{2}(Z-t)^2}dz = e^{t^2/2}$$

### Laplace Rule of Succession

Given $p$, $X_1, X_2 …$ i.i.d,  $Bern(p)$

p unknown, 

Laplace used the rule of succession to calculate the probability that the sun will rise tomorrow

Bayesian : treat p as a r.v.

Let $P\sim Unif(0,1)$  (prior) Let $S_n = X_1,\dots X_n$

So$ S_n|p \sim Bin(n,p)$ , $p \sim Unif(0,1)$

Find Posterior $p|S_n$ , and $P(X_{n+1} = 1| S_n = n)$ 

$$f(p|S_n = k) = \frac{P(S_n = k|p)f(p)}{P(S_n =k)}$$  

 f(p) - prior, 1;  $P(S_n =k)$ does not depend on p

$P(S_n = k) = \int_{0}^{1}P(S_n = k/p)f(p)dp$ 

$$ \varpropto \frac{p^k(1-p)^{n-k}}{f(p|S_n = n)=(n+1)p^n}$$

$p(X_{n+1}=1|S_n = n) = \int_{0}^{1}(n+1)pp^mdp= \frac{n+1}{n+2}$ 



## Lecture 18

### MGF

#### Expo MGF

$X\sim Expo(1)$, find MGF, moment 

$M(t) = E(e^{tx}) = \int_{0}^{\infty} e^{-tx}e^{-x}dx = \int_{0}^{\infty} e^{-x(1-t)}dx = \frac{1}{1-t}, t<1$ 

$M'(0) = E(X), M''(0) = E(X^2) , M'''(0) = E(X^3)\dots$

$ |t| <1,  \frac{1}{1-t} = \sum_{n=0}^{\infty}t^n = \sum_{n=0}^{\infty} n!\frac{t^n}{n!} $    $E(X^n) = n! $

$Y\sim Expo(\lambda)$, let $X = \lambda Y \sim Expo(1)$, so $Y^n = \frac{X^n}{\lambda^n}$ 

$E(Y^n) = \frac{E(X^n)}{\lambda^n} = \frac{n!}{\lambda^n}$ 

#### Normal MGF

Let $Z\sim N(0,1)$ ,find all its moment

$E(Z^n) = 0$ for n, odd by symmetry

MGF $$M(t) =e^{t^2/2} = \sum_{n=0}^{\infty} \frac{(t^2/2)^n}{n!} =  \sum_{n=0}^{\infty} \frac{(2n)!t^{2n}}{z^n n!(2n)!}$$  

=>$$E(Z^{2n}) = \frac{(2n)!}{2^n n!}$$ 

#### Poisson MGF

$X\sim Pois(\lambda)$   $E(e^{tx}) = \sum_{k=0}^{\infty} e^{tx} e^{-\lambda} \frac{\lambda^k}{k!} = e^{-\lambda} e^{\lambda e^t} $ 

let $ Y\sim Pois(\mu)$ **indep** of X ,find distribution of $X+Y$ , 

Multiply  MGFs, $e^{\lambda(e^t-1)}e^{\mu (e^t-1)} = e^{(\lambda+\mu)(e^t-1)}$  => $X+Y \sim Pois(\lambda + \mu)$ 

sum of independent Poisson is still Poisson

 Counter-example if $X, Y$ dependent: $X =Y$  => $X+Y = 2X$ is not Poisson since:

$E(X+Y) = E(2X) = 2\lambda, Var(2X) = 4\lambda $ 

### Joint Distribution

X, Y Bernouli

Example. 2D

|      | Y=0  | Y=1  |      |
| ---- | ---- | ---- | ---- |
| X=0  | 2/6  | 1/6  | 3/6  |
| X=1  | 2/6  | 1/6  | 3/6  |
|      | 4/6  | 2/6  |      |

They are independent 



X, Y r.vs 

#### Joint CDF

$F(x,y) =P(X\le x, Y \le y)$

#### Joint PMF (discrete case)

$P(X=x, Y=y)$ 

#### Marginal CDF

$P(X\le x)$ is marginal dist. of X

#### Joint PDF (cont.)

$f(x, y)$ such that 

$P((X,Y)\in B) = \iint_{B} f(x, y)dxdy$ 

#### Independence

$X,Y$ independent if and only if $F(x,y) = F_X(x)F_Y(y)$

Equiv. 

$P(X=x, Y=y) = P(X=x)P(Y=y)$

$ f(x, y) = f_X(x)f_Y(y)$ for all $x, y >0$

#### Getting marginals

$P(X=x, Y=y) = \sum_y P(X=x, Y=y)$ discrete

$f_Y(y) = \int_{-\infty}^{\infty} f_{X,Y}(x,y)dx$

##### Example

1. Uniform on $square\{(x,y): x,y\in [0,1]\} $

   joint PDF const. on the square, 0 outside

   integral is area c = 1/area = 1

   marginal:  X,Y are independent Unif(0,1)

2. Unif in disc $x^2 = y^2 \le 1$ 

   joint PDF: $1/\pi$ inside the circle; 0 otherwise

   X,Y dependent. 

   Given X=x , $|y| \le \sqrt{1-x^2}$ 

## Lecture 19

#### Marginal distribution 边缘概率分布

Joint CDF $F(x,y) = P(X\le x, Y\le y)$ 

cont. case(joint PDF) : $f(x, y) = \frac{\partial}{\partial x \partial y}F(x, y)$ 

$P((X, Y) \in A) = \iint_{A} f(x,y)dxdy$ 

Marginal PDF of $X$: $\int f(x,y)dy$ 

Conditional PDF of $Y|X$ is 

$$f_{Y|X}(y|x) = \frac{f_{X,Y}(x,y) }{f_{X}(x)}  = \frac{f_{X|Y}(x|y)f_{Y}(y)}{f_{X}(x)} $$

$X, Y$ independent if $f_{X,Y}(x,y) = f_X(x)f_Y(y)$ for all $X,Y$



##### 2-D LOTUS

Let $(X,Y)$ have joint PDF $f(x,y)$

and let $g(x, y)$ be a real-valued fn of $x,y$

Then $Eg(X,Y) = \iint g(x,y)f(x,y)dxdy$ 



##### Theorm

If $X,Y$ are indep, then $E(XY) = E(X)E(Y)$

**Independent implies uncorrelated**

Proof (continuous case)

$E(XY) = \iint xyf_X(x)f_Y(y)dxdy =  \int yf_Y(y) \int xf_X(x)dxdy = (EX)(EY)$ 



**Example**

$X, Y$ i.i.d $Unif(0,1)$ find $E|X-Y|$

 LOTUS $\int_0^1 \int_0^1 |x-y|dxdy = \iint_{x>y}(x-y)dxdy + \iint_{x\le y} (y-x)dxdy$ 

$= 2\int_0^1 \int_y^1 (x-y)dxdy = 2\int_0^1 (x^2/2 - yx)|_y^1 dy = 1/3$ 



Let $M = max(X,Y)$

$L = min (X, Y)$  (L stand for little and less one not large one)

$|X-Y| = M-L$

$E(M-L) = 1/3$

$E(M)-E(L) = 1/3$

$E(M+L)= E(X+Y) = E(M)+E(L) = 1$

=> $E(M) = 2/3, E(L) = 1/3$

#### Chicken-egg

some hens some hatch some don't hatch, the eggs are independent

 $N \sim Pois(\lambda)$ eggs, each hatches with prob. p, indep, Let X = #hatch

so $X|N \sim Bin(N,p)$ 

 Let Y = # don't hatch, so $X + Y = N$

Find joint PMF of X,Y

$P(X=i, Y=j) = \sum P(X=i, Y=j| N=n)P(N=n) $

$= P(X=i, Y=j|N=i+j)P(N=i+j)$ 

$$ = P(X=i|N=i+j)P(N=i+j) =\frac{(i+j)!}{(i!j!)} p^i q^j \frac{e^{-\lambda} \lambda^{i+j}}{(i+j)!} $$  

$ = (e^{\lambda p} \frac{(\lambda p)^i}{i!}) (e^{\lambda q} \frac{(\lambda q)^j}{j!})$ 

=> X, Y are indep, $X\sim Pois(\lambda p),  Y\sim Pois(\lambda q)$ 

 More Details: [Chicken and Egg (Probability) Problem](https://matthewhr.wordpress.com/2012/11/23/chicken-and-egg-probability-problem/)

## Lecture 20

**Example**

 Find $E|Z_1 - Z_2|$, with $Z_1, Z_2$ i.i.d $N(0,1)$

**Therom**

$X\sim N(\mu_1, \sigma_1^2), Y\sim N(\mu, \sigma_2^2)$ indep

Then $X+Y \sim N(\mu_1+\mu_2, \sigma_1^2 + \sigma_2^2)$ 

**Proof** 

Use MGF, MGF of $X+Y$ is 

 $$e^{\mu_1t +\frac{1}{2} \sigma_1^2 t^2}  e^{\mu_2t +\frac{1}{2} \sigma_2^2 t^2}$$ 

Note $Z_1, Z_2 \sim N(0, 2)$

$E|Z_1-Z_2| = E|\sqrt{2} Z|$  $Z\sim N(0, 1)$

$= \sqrt{2}E|Z| = \sqrt{2/\pi}$ 

#### Multinomial多项分布

generalization of binomial

Defn/story of $Mult(n,\vec{p})$ , 

$\vec{p} = (p_1,…p_k)$ probability vector$ p_j \ge 0, \sum pj = 1$

$\vec{X}\sim Mult(n, p), X = (X_1, … X_k)$ 

**story:** have n objects independent putting into k categories

$P_j = P(category\  j)$ $X_j$ = #objects in category j

Joint PMF $P(X_1 = n_1, ..X_k = n_k) = \frac{n!}{n_1!n_2!…n_k!} P_1^{n_1} P_2^{n_2}...P_k^{n_k} $ 

if$n_1 +..+ n_k  = 1$; 0 otherwise



$\vec{X}\sim Mult(n,p)$  Find marginal dist of  $X_j$ Then $X_j \sim Bin(n, p_j) $ 

(each of objects either in this category j or it isn't)

$E(X_j) = np_j, Var(X_j)= np_j(1-p_j)$

##### Lumping Property

Merge category together

$\vec{X} = (X_1, … X_{10}) \sim Mult(n, (p_1,…p_{10}))$ 

**Story:** ten political parties, take n people , ask people which party they in 

$\vec{Y} = (X_1, X_2, X_3 + ..+ X_{10})$  Then $Y \sim Mult(n, (p_1, p_2,p_3+..+p_{10}))$

(wouldn't work if one can be in more than one category)

$\vec{X}\sim Mult(n, p)$, Then give $X_1 = n_1$ ,  PMF 

$(X_2,…X_k) \sim Mult_{k-1}(n-n_1, (p'_2,…p'_k))$ 

(we know how many people in the first catgory , don't know rest)

with $p'_2$ = P(being in category 2| not in category 1) 

= $\frac{p_2}{1-p_1}$  

$$p'_j = \frac{p_j}{p_2+…p_k}$$  

#### Cauchy Interview Problem

The Cauchy is dist. of $T = X/Y$ with $X, Y$ i.i.d $N(0,1)$

Find PDF of T

(doesn't have a mean and variance)

**average of million cauchy is still cauchy**

$P(\frac{X}{Y} \le t) = P(\frac{X}{|Y|} \le t)$  symmetry of $N(0,1)$

$= P(X\le t|Y|) = \frac{1}{\sqrt{2\pi}} \int_{\infty}^{\infty} e^{y^2 /2} \int_{-\infty}^{t|y|} \frac{1}{\sqrt{2\pi}} e^{x^2 /2}  dxdy $ 

$= \frac{1}{\sqrt{2\pi}} \int_{-\infty}^{\infty} e^{y^2 /2} \Phi(t|y|)dy$ 

$ = {\sqrt{2/\pi}} \int_{0}^{\infty} e^{y^2 /2} \Phi(ty)dy $ 

PDF: $F'(t) = 1/\pi(1+t^2)$ 

$P(X\le t|Y|) = \int P(X\le t|Y|| Y=y)\varphi(y)dy$

$= \int \Phi(t|y|)\varphi(y)dy$ 

## Lecture 21

#### Covariance协方差

##### Definition

 $Cov(X,Y)= E((X-EX)(Y-EY))$ 

$= E(XY) - E(X)E(Y)$ 

since $=E(XY)-E(X)E(Y) - E(X)E(Y) + E(X)E(Y)$

##### Property 

1. $Cov(X,X) = Var(X)$

2. $Cov(X,Y) = Cov(Y,X)$ 

3. $Cov(X,c) = 0$, if $c$ is const.

4. $Cov(cX,Y) = cCov(X,Y)$  

5. $Cov(X,Y+Z)=Cov(X,Y) + Cov(X,Z)$ 

   bilinearty(4 , 5)

6. $Cov(X+Y, Z+W) = Cov(X,Y) + Cov(X,W)+Cov(Y,Z)+Cov(Y,W)$

   $Cov(\sum_i^m a_iX_i, \sum_j^n b_j Y_j) = \sum_{i,j} a_i b_j Cov(X_i, Y_j)$  

7. $Var(X_1 + X_2)=Var(X_1) + Var(X_2) + 2Cov(X_1, X_2)$ 

   $Var(X_1+…+ X_n) = Var(X_1)+..+ Var(X_n) + 2\sum_{i<j} Cov(X_i,X_j)$  


##### Therom

If $X, Y$ are independent then they're uncorrelated i.e $Cov(X,Y) = 0$

Converse is false(common mistake) 

**Example**

 $Z\sim N(0.1)$ 

$X=Z, Y=Z^2, Cov(X,Y) = E(XY) - E(X)E(Y) = E(Z^3) - E(Z)E(Z^2) = 0$

but very dependent: Y is a function of X, (we know X then we know Y)

Y determines magnituide of X

#### Correlation相关系数

##### Definition

 $Corr(X,Y) = \frac{Cov(X,Y)}{SD(X)SD(Y)} = Cov(\frac{X-EX}{SD(X)}, \frac{Y-EY}{SD(Y)})$ 

##### Therom 

$-1 \le Corr \le 1$  (form of Cauchy-schwarz)

###### Proof

WLOG  assume X,Y are standardized  let $Corr(X,Y) = \rho$

$0 \le Var(X+Y) = Var(X) + Var(Y) + 2Cov(X,Y) = 2 + 2\rho$

$0 \le Var(X-Y) = Var(X) + Var(Y) - 2Cov(X,Y) = 2 - 2\rho$

= > $0 \le \rho \le 1$ 

##### Example

Cov in a Multinomial 

$(X_1, … X_k) \sim Multi(n, \vec{p})$

Find $Cov(X_i, X_j)$ for all $i,j$

If $i=j$, $Cov(X_i, X_i) = Var(X_i) = np_i(1-p_i)$

Now let $i \ne j$

find $Cov(X_1, X_2) = c$, 

$Var(X_1 + X_2) = np_1(1-p_1) + np_2(1-p_2) + 2c$

$= n(p_1+p_2)(1-(p_1+p_2))$ => $c = -np_1p_2$

General: $Cov(X_i, X_j) = -np_ip_j, for\ i\ne j$

##### Example

 $X\sim Bin(n,p)$, write as $X=X_1+…X_n$,  $X_j$ are i.i.d Bern(p)

$Var X_j = EX_j^2 - (EX_j)^2 = p - p^2 = p(1-p)=pq$

 Let $I_A$ be indicator r.v. of event A 

$I_A^2 = I_A, I_A^3 = I_A$

$I_AI_B = I_{A\cap B}$

$VarX = npq$ since $Cov(X_i,X_j) = 0$

##### Example

 $X\sim HGeom(w, b, n)$  

$X = X_1 + ..X_n$, $X_j = 1$ if $j$th ball is white; 0 otherwise

symmetry 

$Var(X) = nVar(X_1) + 2\binom{n}{2}Cov(X_1, X_2)$ 

$Cov(X_1, X_2) = E(X_1X_2) - E(X_1)E(X_2) = \frac{w}{w+b} (\frac{w-1}{w+b-1}) - (\frac{w}{w+b})^2$  



## Lecture 22

#### Variance of Hypergeom(w,b,n),

$p = w/(w+b),  w+b = N$

$Var(X) = nVar(X_1) + 2\binom{n}{2}Cov(X_1, X_2)$ 

$=\frac{N-n}{N-1} np(1-p) $

$\frac{N-n}{N-1}$  finite population correction

Extreme case n= 1, N much much larger than n

Transformations 

Therom

Let $X$ be a continous  r.v with PDG $f_{X, Y} = g(X)$

where $g$ is differentiable, **strictly increaing**. Then the PDF of $Y$ is given by

$ f_Y(y) = f_X(x)dx/dy$ where $y = g(x)$, $x =g^{-1}(y)$

and this is written in terms of y

Also $dx/dy = (dy/dx)^{-1}$  

Proof 

CDF of Y is $P(Y\le y) = P(g(X) \le y)$

$= P(X\le g^{-1}(y)) = F_X(g^{-1}(y))h = F_X(x)$

 => $f_Y(y) = f_X(x)\frac{dx}{dy}$  [chain rule]

Ex. Log normal   $Y = e^Z, Z\sim N(0,1)$

$\frac{dy}{dZ} = e^Z= y$

f_Y = 

Transfomations in multi dimensions 

$\vec{Y} = g(\vec{X})$  

$X = (X_1,\dots X_n)$ continuous

joint PDF of Y is $f_Y(y) = f_X(x) |dx/dy | $

$|d\vec{x}/d\vec{y} |$ Jacobian  abs of value of determinant

$|dy/dx|^{-1}$    

### Convolution (sums)

Let $T = X+Y$, $X,Y$ indep.

#### Discrete :

 $P(T=t) = \sum_{x} P(X=x)P(Y = t-x)$

#### Continuous

 $f_T(t) = \int f_Xf(x)f_Y(t-x)dx$ 

since $F_Y(t) = P(T\le t) = \int P(X+Y\le t|X=x)f_X(x)dx$

$=\int F_Y(t-x)f_X(x)dx$

Idea: prove existence of objects with desired properties A using probability

Show P(A) >0 for a random object . 

Suppose each object has a "score" . Show there is an object with "good" score. there is an object with score is at least E(X) (X score of random object)

Example

Suppose 100 people , 15 committees of 20 , each person is on 3 committee, show there exist 2 committee with overlap $\ge$ 3    

Idea: find average overlap of 2 random committee

$E(overlap) = 100 \frac{\binom{3}{2}}{\binom{15}{2}} = 20 / 7$

=> there exists pair of committee with overlap of $\ge 20/7$ => have overlap of $\ge 3$

## Lecture 23

**Beta Distribution**

generalization of uniform dist.

Beta(a,b) a>0 , b>0

PDF $f(x) = c x^{a-1}(1-x)^{b-1}$ , 0<x<1

+ flexible family of continuous distribution on (0,1)

Ex, a =b = 1, uniform 

a = 1/2 = b  U shape

+ often used as prior for a parameter in (0,1)
+ "conjugate prior to Binormial "
+ connections to other distribution 

**Conjugate prior for Bin**

$X|p \sim Bin(n,p)$  $p\sim Beta(a,b)$ [prior]

Find posterior dist. $p|X$  

$f(p|X=k) = P(X=k|p) f(p)/P(X=k) $

$=\binom{n}{k}p^k(1-p)^{n-k}  c p^{a-1}(1-p)^{b-1}/P(X=k)$

$\propto p^{a+k-1}(1-p)^{b+n-k-1}$ 

=> $p|X \sim Beta(a+X, b+n-X)$ 

( P(X=k) does not depend on p)

Find$ \int_0^1 x^k(1-x)^{n-k}dx$  without using calculus

using a story (Bayse' Billiards)

n+1 billiard balls, all whit, paint one pink , throw  them at (0,1) indep

or : first throw , then paint 1 pink

let X = #balls to left of the pink one 

$P(X=k) = \int_0^1 P(X=k|p)f(p)dp$  f(p) = 1

$= \int_0^1  \binom{n}{k}p^k(1-p)^{n-k}  = 1/(n+1)$ 

 

## Lecture 25

Bank-post office example

wait X time at bank to serve, $X\sim Gamma(a, \lambda)$ 

at office $Y\sim Gamma(b, \lambda)$ 

independent

Find joint distribution of $X+Y = T, \frac{X}{X+Y} = W$  

Let \lambda = 1 to simplify notation

joint PDF $f_{T,W}(t,w) = f_{X,Y}(x,y) |d(x,y)/d(t,w)|$ 

$=\frac{1}{\Gamma(a) \Gamma(b)} x^a e^{-x}y^be^{-y}/xy  |-t|$ 

$=\frac{\Gamma(a+b)}{\Gamma(a) \Gamma(b)} w^{a-1}(1-w)^{b-1} t^{a+b}e^{-t}/t\Gamma(a+b)$  

$t^{a+b}e^{-t}/t\Gamma(a+b) = Gamma(a+b, 1)$ 

x+y = t, x/(x+y) = w => x =tw, y = t(1-w)

det |(w,t)(1-w, -t| = -t

$f_W(w) = \int f_{T,W}(t,w)dt = \frac{\Gamma(a+b)}{\Gamma(a) \Gamma(b)} w^{a-1}(1-w)^{b-1}$  

=>  know normalizing const. of  Beta(a,b)

$T\sim Gamma(a+b, 1), W\sim Beta(a,b)$  are independent 

Find E(W), W\sim Beta(a,b)

1. LOTUS/defn 
2. $E(X/(X+Y)) = E(X)/E(X+Y)=a/(a+b)$  be careful  

Why is  E(X/(X+Y))E(X+Y) = E(X) in this special problem of Gamma-Beta?

X/(X+Y) is independent of X+Y => uncorrelated

**Order Statistcs**

generalization of max and min problem

Let X_1, .. X_n, be i.i.d, 

The order statistics are $X_1 \le X_2 \le ..\le X_n$, where X_1 is min, X_n = max

e.g if n is even, the median is X_{(n+1)/2}  Get other "quantiles"

Difficult since dependent , Tricky in discrete case  because of "ties". 

Let$ X_1,..X_n$ be i.i.d with PDF f, CDF F, Find the CDF and PDF of $X_{(j)}$

CDF : P(X_j \le x) = P(at least j of X_i's are \le  x)

$= \sum_{k=j}^{n} \binom{n}{k} F(x)^k (1-F(x))^{n-k}$ 

PDF $f_{X(j)}(x)dx=  n \binom{n-1}{j-1}  (f(x)dx)F(x)^{j-1}(1-F(X))^{n-j} $ 

Ex. U_1, ..U_n i.i.d. Unif(0,1)

PDF of jth order statistic

$f_{Uj}(x) = n \binom{n-1}{j-1}  x^{j-1}(1-x)^{n-j}$  

=> $Uj\sim Beta(j, n-j+1)$ 

$E|U_1 - U_2| = E(max) - E(min)$

$max \sim Beta(2,1), min \sim Beta(1,2)$  



$E(X|A)  = E(X|A)P(A) + E(X|A^c)P(A^c)$

$E(X) = \sum_x xP(X=k)$ 

## Lecture 26

Two Envolope Paradox

one envolope contains twice as much as others

Argument1 E(Y) = E(X) by symmetry  —true

Argument2 E(Y) = E(Y|Y=2X)P(Y=2X) + P(Y|Y=X/2)P(Y=x/2)

=(\ne) E(2X)1/2 + E(x/2)1/2 = 5/4 E(X) — false

= E(2X|Y=2X)1/2 + E(X/2|Y=X/2)1/2 

$E(Y|Y=2X) \ne  E(2X)$ 

Let I be indicator of Y = 2X 

Then X, I are dependent



Patterns in coin filips

Repeated fair coin flips

How many flips until HT?  find E(W_{HT}) = 4

…………………………HH?  find E(W_{HH}) = 6

symmetry E(W_TT) = E(W_HH)

E(W_HT)  = E(W_TH)

$E(W_{HT}) = E(W_1) +E(W_2) = 2 + 2 = 4$, since $W_j - 1 \sim Geom(1/2)$ 

E(W_{HH}) = E(W_{HH}|1st toss H)1/2+ E(W_{HH}|1st toss T)1/2

= (2 1/2 + (2+EW_HH)1/2)1/2 + (1 + EW_HH)1/2

=> EW_HH = 6



**peter donnelly** ted.com

Y discrete

$E(Y|X=x) = \sum_y yP(Y=y|X=x)$  

Y continuous

$E(Y|X=x) = \int_{-\infty}^{\infty} yf_{Y|X}(y|x)dy $ 

X continuous

$E(Y|X=x) = \int_{-\infty}^{\infty} y\frac{f_{Y,X}(y,x)}{f_X(x)}dx$ 

Let g(x) = E(Y|X=x)

Then define E(Y|X) = g(X)

e.g. if $g(x) = x^2$, then $g(X) = X^2$ 

so E(Y|X) is a r.v  funciton of X

Ex, X,Y i.i.d Pois(\lambda)

$E(X+Y|X) = E(X|X) + E(Y|X) = X + E(Y)   = X+ \lambda$ 

E(h(X)|X) = h(X)   (X is a function itself, )

 E(X|X+Y) , Let T = X+Y, find conditional PMF 

P(X=k|T=n) = P(T=n|X=k)P(X=k)/P(T=n)

= P(Y=n-k)P(X=k)/P(T=n)

= \binom{n}{k} 1/2^n

$X|T=n \sim Bin(n, 1/2)$ 

E(X|T=n) = n/2 => E(X|T) = T/2

E(X|X+Y) = E(Y|X+Y) by symmetry since i.i.d

E(X|X+Y) + E(Y|X+Y) = E(X+Y|X+Y) = X+Y

=> E(X|T) = T/2



Iterated E (Adam'a law)

 E(E(Y|X)) = E(Y)

## Lecture 27

Ex. let $X\sim N(0, 1)$, Y =X^2

Then $E(Y|X) = E(X^2|X) = X^2 = Y$

E(X|Y) = E(X|X^2) = 0,since if observe X^2 = a , then X=+-\sqrt{a} equally likely 

Ex. stick  length 1, break off random piece, break off aonther piece

E of second piece

$X\sim Unif(0,1)  Y|X \sim Unif(0,X)$  

E(Y|X=x) = x/2, so E(Y|X) = X/2, E(E(Y|X)) = 1/4 = E(Y)

**Useful Properties**

1. $E(h(X)Y|X) = h(X)E(Y|X)$ [taking out what's known]
2. $E(Y|X) = E(Y)$, if X, Y are independent
3. $E(E(Y|X))=E(Y)$  Iterated Expectation(Adam's law)
4. $E((Y-E(Y|X))h(X)) = 0$, i.e. Y-E(Y|X) (residual) is uncorrlated with h(X)
5. $Var(Y) = E(Var(Y|X)) +Var(E(Y|X))$   EVE's law

Proof of 4. 

$E(Yh(X)) - E(E(Y|X)h(X)) = E(Yh(X)) - E(E(h(X)Y|X))$

$= E(Yh(X)) -E(Yh(X)) = 0$

Proof of 3. [discrete case]

Let E(Y|X) = g(X)

$E(g(X))= \sum_x g(x)P(X=x) = \sum_x E(Y|X=x)P(X=x)$

$= \sum_x (\sum_y yP(Y=y|X=x) )P(X=x)$

$= \sum_y \sum_x yP(Y=y, X=x) = \sum_y y P(Y=y) =E(Y)$  



Defn $Var(Y|X) = E(Y^2|X)-(E(Y|X))^2 = E((Y-E(Y|X))^2|X )$ 

Ex. Pick random city, pick random sample of n people in that city,

X =#with disease,  Q= proportion of people in the random city with disease

Find E(X), Var(X), assuming Q \sim Beta(a,b), X|Q \sim Bin(n,Q)

E(X) = E(E(X|Q)) = E(nQ) = na/(a+b)

Var(X)= E(Var(X|Q)) + Var(E(X|Q))= E(nQ(1-Q)) -n^2Var(Q)

$E(Q(1-Q))= \frac{ab}{(a+b+1)(a+b)}$

$Var(Q) = \frac{\mu(1-\mu)}{a+b+1}, \mu = a/(a+b)$ 

## Lecture 28

Ex. Store with a random # customers , N = (# customers)  Let  X~j~ be amount jth customers spend, X~j~ has mean $\mu, var, \sigma^2$  

assume N, X_1, X_2 … are indep

Find mean, var of $X = \sum_{j=1}^{N}X_j$ 

~~E(X)=N\mu~~ 

$E(X)=\sum_{n=0}^{\infty}E(X|N=n)P(N=n)=\sum_{n=0}^{\infty} \mu nP(N=n)$

$=\mu E(N)$

Adam's law $ E(X) = E(E(X|N)) = E(\mu N)=\mu E(N)$

$Var(X) = E(Var(X|N)) + Var(E(X|N))=E(N\sigma^2)+Var(\mu N)$

$=\sigma^2E(\mu) + \mu^2Var(N) $

Stat

**Inequalities** 

1. Cauchy-Schwarz: $|E(XY)| \le \sqrt{E(X^2)E(Y^2)}$  

    [if X,Y uncorrlated E(XY)=E(X)E(Y)] 

   IF X,Y have mean 0, then $|Cov(X,Y)|=|\frac{E(XY)}{(EX^2EY^2)^{1/2}}| \le 1$ 

2. Jensen's inequality: if  g is convex , then $Eg(X) \ge g(EX) $ 

   convex : $g''(x)\ge 0$  e.g. $y = x^2$ ,$y=|x|$ 

   IF h is concave , $Eh(x) \le h(EX)$  

   $EX^2 \ge (EX)^2$ 

   let X be postive , $E(1/X) \ge 1/E(X)$  $E(lnX) \le \ln E(X)$ 

3. Markov $P(|X|\ge a) \le E|X|/a$  , for any a >0

   Note that  $aI_{|X|\ge a} \le |X|$ So $aEI_{|X|\ge a} \le E|X|$  

4. Chebyshev $P(|X-\mu|> a) \le \frac{VarX}{a^2}$ 

   $P(|X-\mu|>< SD(X) ) \le \frac{1}{c^2}, c>0$  

   for $\mu = EX , a >0 $ 

   ​

Proof 2. 

$g(x) \ge a+bx$    y=a+bx is the tangent line

$g(X)\ge a+bX$ =>$ Eg(X) \ge E(a+bX) = a+bE(X) = a+b\mu = g(\mu) = g(EX)$ 

Example 3.

Ex. 100 people , Is it possible that at least 95% are younger than avg in group? - yes

Is it at 50% are older than twice avg age?  - No

Proof 4. $P(|X-\mu|>a) = P((X-\mu)^2 > a^2) \le E(X-\mu)^2/a^2=VarX/a^2$ 

## Lecture 29

let X_1,X_2… be i.i.d mean \mu, var \sigma^2,  let $\overline{X_n} = 1/n \sum_{j=1}^{n} X_j $  (sample mean)

(strong)Law of Large Numbers: $\overline{X_n} $ -> \mu as n-> \infty with probability 1

Ex. $X_j \sim Bern(p)$ , then X_1 + ..X_n /n -> p with probability 1

(weak) LLN For c >0, $P(|\overline{X_n} - \mu| >c) -> 0$  as n-> \infty



proof weak

$P(|\overline{X_n} - \mu| >c) \le VarX_n^-/c^2 1/n^2 n \sigma^2 /c^2 = \sigma^2/nc^2 ->0$  

$X_n^- - \mu -> 0$  with probability 1, but what does the distribution of X_n^- look like ?

Central limit Therom:  $n^{1/2}(X_n^- - \mu)/\sigma$ -> N(0,1) in distribution

Equivalently: $\frac{\sum_{j=1}^n X_j -n\mu}{\sqrt{n}\sigma}$  -> N(0,1)

Proof (assume MGF M(t) of X_j exists)

can assume \mu = 0, \sigma = 1, since consider 



Binormial Approxiamated by Normal 

Let X\sim Bin(n,p) think of X = \sum X_j , X_j \sim Bern(p), i.i.d 

$$P(a\le X \le b )=  P( \frac{a - np }{\sqrt{npq}} \le \frac{X - np }{\sqrt{npq}}  \le \frac{b - np }{\sqrt{npq}}  )​$$ 

$\approx \Phi (\frac{b - np }{\sqrt{npq}}) - \Phi(\frac{a - np }{\sqrt{npq}})$ 

contrast with Pois approx

Pois n large p small , \lambda = np

Normal n large , p close to 1/2



$P(X=a) = P(a-1/2 \le a \le 1+1/2)$   a-integer

## Lecture 30

**Chi-square**

$\chi^2(n)$   (Chi-square) 

Let $V=Z_1^2 + Z_2^2 +..+Z_N^2$ i.i.d N(0,1)

then $V\sim \chi^2(n)$  

Fact $\chi^2(1)$ is $Gamma(1/2, 1/2)$  

So $\chi^2(n)$ is $Gamma(n/2, 1/2)$  

**Student - t** (Gosset, 1908)

Let $T = \frac{Z}{\sqrt{V/n}}$ , with $Z\sim N(0,1) ,V\sim \chi^2(n)$ independent

Then $T\sim t_n$  

Properties:

1. symmetric, i.e $-T \sim t_n$  

2. $n=1 \Rightarrow Cauchy$ , mean doesn't exist

3. $n\ge 2 \Rightarrow E(T) = E(Z)E( \frac{1}{\sqrt{V/n}}) = 0$  

4. Heavier-tailed than Normal

5. For n large,t_n looks very much like N(0,1)

   distribution of t_n goes to N(0,1) as $n\to \infty$  

$E(Z^2) = 1, E(Z^4) = 3, E(Z^5)=3\times 5 ..$  used MGF

Another way:

$E(Z^{2n})=E((Z^2)^n), Z^2 = \chi^2(1) \sim Gamma(1/2, 1/2)$

Let $T_n = \frac{Z}{\sqrt{V/n}}$ , with $Z\sim N(0,1) ,$ independent

$ V_n=Z_1^2 + Z_2^2 +..+Z_N^2$

Then $V_n/n \to 1$ with probability 2 by LLN since $EZ_1^2 = 1$

so $T_n \to Z$  with probability 1 So $t_n$ converges to N(0,1)

**Multivariate Normal** (MVN)

Defn Random vector $(X_1, X_2,…X_k) = \vec{X}$ is Multivariate Normal if every linear combination $t_1X_1 + t_2X_2 + ..t_kX_k$  is Normal

Ex Let Z,W be i.i.d N(0,1) Then (Z+2W, 3Z+5W) is MVN , 

since s(Z+2W) + t(3Z+5W) = (s+3t)Z+(2s+5t)W is Normal 

Non-Ex.

 Z\sim N(0,1), let S be random independent of Z, then Z, SZ are marginally N(0,1), But (Z,SZ) is not Normal. look at Z+SZ 

Let $EX_j = \mu_j$, MGF of $\vec{X}$ (MVN) is $E(e^{\vec{t}'\vec{X}})  = E(e^{t_1X_1+t_2X_2+…+t_kX_k})$

$= exp((t_1\mu_1+..+t_k\mu_k)+1/2\times Var(t_1X_1+t_2X_2+…+t_kX_k))$  

Theorm: within MVN, uncorrlated implies indep

$\vec{X} = \binom{\vec{X_1}}{\vec{X_2}}$ MVN, id every component of $\vec{X_1}$ is uncorrlated with every component of $\vec{X_2}$,then $X_1$ is independent of $X_2$ 

Ex. Let X,Y be i.i.d N(0,1) Then (X+Y, X-Y) is MVN, 

uncorr: $Cov(X+Y, X-Y) = Var(X) + Cov(X,Y)-Cov(X,Y)-Var(Y) = 0$ 

So X+Y, X-Y are independent

## Lecture 31

### Markov chains 

(Example of stochastic processes)

$X_0, X_1,X_2,…$    (think of $X_n$ as state of system at discrete time n)

#### Markov property:

$X_n$ : present, $X_{n+1}$: future

$P(X_{n+1}=j|X_n=i,X_{n-1}=i_{n-1},…,X_0=i_0)$ 

$=P(X_{n+1}=j|X_n=i) = q_{ij}$  (transition prob.)"homogeneous"

**Intuition:** past, future are conditionally indep. given present

transition matrix $Q=(q_{ij})$  

Each row sums to 1

MCMC - Markov Chain Monte Carlo

suppose at time n, X_n has distribution \vec{S} (row vector 1\times M matrix, PMF listed out )

$P(X_{n+1}=j) = \sum_i P(X_{n+1}=j|X_n = j)P(X_n=i)$

$=\sum_i S_i q_{ij}$  is jth entry of $\vec{S}Q$ (1\*M, M\*M)

So $\vec{S}Q$ is the distribution at time n+1

So $\vec{S}Q^2$  is the … at time n+2, 

So $\vec{S}Q^3$ …… at n+3 , and so on.

$P(X_{n+1}=j|X_n=i) = q_{ij}$ 

$P(X_{n+2}=j|X_n=i) = \sum_k P(X_{n+2}=j|X_{n+1}=k,\cancel{X_n = i})P(X_{n+1}=k|X_n=i)= \sum_k q_{ik} q_{kj}$  is $(i,j)$ of $Q^2$  

$P(X_{n+m}=j|X_n=i) $  is ($(i,j)$  entry of $Q^m$)  



#### Stationary Distribution

$\vec{S}$ (prob. vector 1*M) is stationary for the chain if $\vec{S}Q = \vec{S}$ 

(eigenvalue eigenvector)

##### Questions 

1. Does a stationary distribution exist?
2. Is it unique?
3. Does chain converge to $\vec{S}$?
4. How can we compute it?

## Lecture 32

 ![lecture32](lecture32.png)

Chain is <u>irreducible</u> if possible (with pos. prob.) to get from anywhere to anywhere.

A <u>state</u> is recurrent if starting there , chain has prob. 1 to returning to that store. Otherwise, <u>transient</u>.   

### Stationary Dist.

#### Definiton :

$\vec{S}$ (prob. vector 1*M) is stationary for a chain with trans. matrix Q

 if $\vec{S}Q = \vec{S}$ 

#### Theorem 

For any <u>irreducible</u> Markov chain (with finitely many states):

1. A stationary $\vec{S}$ exists.

2. It is unique.

3. $S_i = \frac{1}{r_i}$, where $r_i$ is average return time to $i$ (starting at $i$).

4. If $Q^m$ is strictly positive for some $m$ , then $P(X_n=i) \to S_i\ as\ n\to \infty$ ;

   $\vec{t}Q^n \to  \vec{S} \ as\ n\to \infty$  , (t any prob. vector)

### <u>Reversible</u> Markov chains

##### Definition:

 Markov chain with trains. matrix $Q=(q_{ij})$ is reversible if there is a prob. vector $\vec{S}$ such that $S_i q_{ij} = S_j q_{ji}$ for all states i, j

##### Theroem:

If reversible with respect to $\vec{S}$ then $\vec{S}$ is stationary 

Let $S_i q_{ij} = S_j q_{ji}$ for all $i, j$ ,show $\vec{S}Q=\vec{S}$

$\sum_iS_iq_{ij} =\sum_i S_j q_{ji} = S_j \sum_i q_{ji} = S_j$   

So $\vec{S}Q = \vec{S}$

##### Example:

Random Walk on an (Undirected) Network

 ![lecture32_2](lecture32_2.png)

Let $d_i$ be degree of i $[d_1 = 2, d_2=2, d_3 = 3, d_4 =1]$

Then $d_i q_{ij} = d_j q_{ji}$ for all $i,j$

Let $i\ne j$, $q_{ij}, q_{ji}$ are both 0 or both not 0

If $\{i, j\}$ is an edge, then $q_{ij} =  1/d_i $ 

So with $M$ nodes $1,2,…M$ , degree $d_i$, 

then $\vec{S}$ with $S_i = d_i/\sum_j d_j$ is stationary

## Lecture 33

$W_ij \le 0$, "weight", $W_{ij} =0$ if no edge $\{i,j\}$, $W_{ij}=W_{ji}$

random walk: from state i, go to j with prob. $\propto $ $W_{ij}$

If \{i,j\} is an edge, then $q_{ij} = W_{ij}/\sum_k W_{ik}$  

Note: $(\sum_k W_{ik})q_{ij} = W_{ji} = (\sum_k W_{jk}) q_{ji}$

$\Rightarrow S_i \propto \sum_k W_{ik}$     

**Conversely: any reversible chain is of this form!**
Let $W_{ij} = S_i q_{ij} = S_j q_{ji} = W_{ji}$ 
$$P(X_{n+1} = j| X_n = i) =\frac{ W_{ij}}{\sum_k W_{ik}} =  \frac{S_i q_{ij}}{ \sum_k S_i q_{jk}} = \frac{q_{ij}}{ \sum_k q_{jk}} = q_{ij}$$

### Non-reversible example: Google PageRank 

WWW four webpages

 ![lecture33](lecture33.png)

Importance of page should be based on pages linking to and their importance

$$ Q= \begin{bmatrix} 0 & 1/2 & 1/2 & 0  \\ 1/2 & 0 & 1/2 & 0  \\ 0 & 0 & 0 & 1 \\ 1/4 & 1/4 & 1/4  & 1/4  \end{bmatrix}$$

S: score 
$S_j = \sum_i S_iq_{ij}$ 

\vec{S} = \vec{S}Q
which says \vec{S} is stationary dist. of random-surfing chain
if \vec{S} is normalized

$G = \alpha Q + (1-\alpha) \frac{J}{M}$, where M=# pages, J = all ones,  $0<\alpha <1$ , $\frac{J}{M}$ teleportation, (original value $\alpha = 0.85$)
guarantees irreducibility, no zeroes in transition matrix G
use convergence to stationarity! 

Let $\vec{t}$ be initial prob. vector
$\vec{t}G$ (after one step)
$\vec{t}G = \alpha \vec{t}Q + (1-\alpha) \frac{\vec{t}J}{M}$
$\vec{t}Q$ very sparse(mostly 0's)
$\vec{t}J = (1,1\dots 1)$
$(\vec{t}G)G = \vec{t}G^2$  $\vec{t}G^3 \dots \vec{t}G^n \dots$

## Lecture 34

### 10

1. Conditioning
2. Symmetry
3. r.v.s and their distributions
4. Stories
5. Linearity
6. Indicator r.v.s
7. LOTUS
8. LLN - Law of Large Numbers 
9. CLT - Central Limit Theroem
10. Markov Chains

**1-4:** what is randomness, uncertainty?

**5-7:** computing average 

**8-10:** long run behavior

###Course
1. 111
2. 139
3. 123
4. 115
5. 171
6. 160

### Regression 

$Y = \beta_0 + \beta_1 X + \epsilon$, $E(\epsilon|X) = 0$

$Cov(Y,X) = Cov(\beta_1 X, X) + Cov(\epsilon, X)=\beta_1 Var(X)$
$\Rightarrow \beta_1 = \frac{Cov(X,Y)}{Var(X)}$

$E(\epsilon) = E(E(\epsilon|X)) = 0$
$E(E(\epsilon X|X) = 0 $

###Recommendations:
_Mostly Harmless Econometrics_

Sampling from finite pupulation
true values $y_1,\dots ,y_n$, treated as fixed
sample of size $n$,
Goal: estimate$ \sum_{j=1}^n y_j$
prob. that person $j$ is in sample $p_j>0$ (known)
Let $(X_1, Z_1),\dots, (X_n, Z_n)$ be data, with $X_j$ is the y value , $Z_j$ is the ID number
Question: How do we get unbiased estimated for the total?
Then $\sum_{j=1}^n frac{X_j}{P_{Z_j}}$ is unbiased.
$\sum_{j=1}^n frac{X_j}{P_{Z_j}} = \sum_{j=1}^N \frac{I_j y_j}{P_j}$, $I_j$ is indicator of jth perpon being included
Expected value: $\sum_{j=1}^N \cancel{\frac{P_j}{P_j}} y_j$ 
Howitz - Thompson, inverse prob. w
Is it a good estimator? 
Ex. Basu's Elephant




















