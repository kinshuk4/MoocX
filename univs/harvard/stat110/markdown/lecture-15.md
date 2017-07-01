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