## Lecture 9

### CDF

$F(x) = P(X\le x)$ ,  as a function of real $x$

$P(a<X<b) = F(b) - F(a)$ 

#### Properties of CDF

1. increasing
2. right continuous 
3. $F(x) \to 0\ as\ x \to - \infty,\  F(x)\to 1\ as\ x \to \infty $

This is "only if"

### Independence of r.v.s

$X,Y$ are independent r.v.s if $P(X\le x, Y\le y) = P(X\le x)P(Y\le y)$ for all $x, y$

Discrete case : $P(X=x, Y=y) = P(X=x)P(Y=y)$ 

### Average(Means, Expected Values)

<u>Example</u>

1,2,3,4,5,6, -> 1+2+..+6 / 6 = 3.5

1,1,1,1,1,3,3,5

two ways: 

1. add, divide 


1. 5/8 * 1 +2/8 * 3 + 1/8 * 5

#### Average of a discrete r.v.s

$E(X) = \sum_{} xP(X=x)$  

summed over $x$ with $P(X=x) > 0$

##### X~ Bern(p)

$E(x) = 1P(X=1) +0P(X=0) = p$

X = 1 if A occurs , 0 otherwise (indicator r.v.s)

Then $E(x) = P(A)$  **fundamental bridge** between E and P

##### X~Bin(n,p)

$E(X) = \sum_{k=0}^n k\binom{n}{k} p^k q^{n-k}$ 

$=  \sum_{k=1}^n n \binom{n-1}{k-1} p^k q^{n-k}$

$ = np \sum_{k=0}^n  \binom{n-1}{k-1} p^{k-1} q^{n-k}$ 

$ = np \sum_{j=0}^{n-1}  \binom{n-1}{j} p^{j} q^{n-j-1}$ 

$=np$  

#### Linearity

1. $E(X+Y) = E(X) +E(Y)$  even if $X, Y$ are dependent
2. $E(cX) = cE(X)$ 

##### Redo Bin

$E(X) = np$  by linearity since $X = x_1+..+x_n$ 



<u>Ex.</u> 5 Card hand ,$X = \#aces$ let $X_j$ be indecator of jth card being as ace, $1\le j \le 5$

$E(X) =(indicator) E(X_1 + ..+X_5) =(linearity) E(X_1) +..+ E(X_5) = (symmetry) 5E(X_1)$

$= (fundamental\ bridge) 5P(1st\ card\ ace) = 5/13$ 

even though $X_j$'s are dependent

This gives expected value of any Hypergeometric

### Geometric

$Geom(p)$ : independent $Bern(p)$ trials, count # failures before 1st success.

Let $X\sim Geom(p)$  , $q = 1-p$  

#### PMF:

 $P(X=k) = q^k p$  valid since $\sum_{k=0}^{\infty} pq^k = p/1-q = 1$

  $E(x) = \sum_{k=0} kpq^k$

$=p \sum_{k=1} kq^k$

$= q/p$

#### E(X) Story Proof:

Let $c = E(X)$,

$c = 0*p + (1+c)q$

$= q+cq => c = q/p$ 