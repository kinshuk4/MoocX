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