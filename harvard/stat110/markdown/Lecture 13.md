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