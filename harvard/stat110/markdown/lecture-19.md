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

 More Details: [Chicken and Egg (Probability) Problem