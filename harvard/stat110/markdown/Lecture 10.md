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

### Geom

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