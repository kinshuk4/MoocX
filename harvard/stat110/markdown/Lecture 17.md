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