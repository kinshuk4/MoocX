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

### Hypergeometric

**Story:** Have b black, w white marbles. Pick simple random sample of size n. 

Find distribution of (# white marbles in sample) = X

$$P(X=k) =\frac{\binom{w}{k} \binom{b}{n-k}}{\binom{w+b}{n}}, 0\le k\le w, 0\le n-k \le b$$ 

**Hypergeometric** sampling without replace 

$$\frac{1}{\binom{w+b}{n}} \sum_{k=0}^w \binom{w}{k} \binom{b}{n-k} = 1$$  



CDF $P(X\le x)$ 