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

$P(X\ge s+t|X\ge s) = P(X\ge s+t, X\ge s) / P(X\ge s) =  P(X\ge s+t) / P(X\ge s)  = e^{-\lambda t} = P(X\ge t)$ 

$X\sim Expo(\lambda)$

$E(X|X>a) = a + E(X-a|X>a) = a + q/\lambda$  by memoryless