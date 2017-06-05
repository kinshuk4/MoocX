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