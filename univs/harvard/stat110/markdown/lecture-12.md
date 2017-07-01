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