## Lecture 20

**Example**

 Find $E|Z_1 - Z_2|$, with $Z_1, Z_2$ i.i.d $N(0,1)$

**Therom**

$X\sim N(\mu_1, \sigma_1^2), Y\sim N(\mu, \sigma_2^2)$ indep

Then $X+Y \sim N(\mu_1+\mu_2, \sigma_1^2 + \sigma_2^2)$ 

**Proof** 

Use MGF, MGF of $X+Y$ is 

 $$e^{\mu_1t +\frac{1}{2} \sigma_1^2 t^2}  e^{\mu_2t +\frac{1}{2} \sigma_2^2 t^2}$$ 

Note $Z_1, Z_2 \sim N(0, 2)$

$E|Z_1-Z_2| = E|\sqrt{2} Z|$  $Z\sim N(0, 1)$

$= \sqrt{2}E|Z| = \sqrt{2/\pi}$ 

#### Multinomial多项分布

generalization of binomial

Defn/story of $Mult(n,\vec{p})$ , 

$\vec{p} = (p_1,…p_k)$ probability vector$ p_j \ge 0, \sum pj = 1$

$\vec{X}\sim Mult(n, p), X = (X_1, … X_k)$ 

**story:** have n objects independent putting into k categories

$P_j = P(category\  j)$ $X_j$ = #objects in category j

Joint PMF $P(X_1 = n_1, ..X_k = n_k) = \frac{n!}{n_1!n_2!…n_k!} P_1^{n_1} P_2^{n_2}...P_k^{n_k} $ 

if$n_1 +..+ n_k  = 1$; 0 otherwise



$\vec{X}\sim Mult(n,p)$  Find marginal dist of  $X_j$ Then $X_j \sim Bin(n, p_j) $ 

(each of objects either in this category j or it isn't)

$E(X_j) = np_j, Var(X_j)= np_j(1-p_j)$

##### Lumping Property

Merge category together

$\vec{X} = (X_1, … X_{10}) \sim Mult(n, (p_1,…p_{10}))$ 

**Story:** ten political parties, take n people , ask people which party they in 

$\vec{Y} = (X_1, X_2, X_3 + ..+ X_{10})$  Then $Y \sim Mult(n, (p_1, p_2,p_3+..+p_{10}))$

(wouldn't work if one can be in more than one category)

$\vec{X}\sim Mult(n, p)$, Then give $X_1 = n_1$ ,  PMF 

$(X_2,…X_k) \sim Mult_{k-1}(n-n_1, (p'_2,…p'_k))$ 

(we know how many people in the first catgory , don't know rest)

with $p'_2$ = P(being in category 2| not in category 1) 

= $\frac{p_2}{1-p_1}$  

$$p'_j = \frac{p_j}{p_2+…p_k}$$  

#### Cauchy Interview Problem

The Cauchy is dist. of $T = X/Y$ with $X, Y$ i.i.d $N(0,1)$

Find PDF of T

(doesn't have a mean and variance)

**average of million cauchy is still cauchy**

$P(\frac{X}{Y} \le t) = P(\frac{X}{|Y|} \le t)$  symmetry of $N(0,1)$

$= P(X\le t|Y|) = \frac{1}{\sqrt{2\pi}} \int_{\infty}^{\infty} e^{y^2 /2} \int_{-\infty}^{t|y|} \frac{1}{\sqrt{2\pi}} e^{x^2 /2}  dxdy $ 

$= \frac{1}{\sqrt{2\pi}} \int_{-\infty}^{\infty} e^{y^2 /2} \Phi(t|y|)dy$ 

$ = {\sqrt{2/\pi}} \int_{0}^{\infty} e^{y^2 /2} \Phi(ty)dy $ 

PDF: $F'(t) = 1/\pi(1+t^2)$ 

$P(X\le t|Y|) = \int P(X\le t|Y|| Y=y)\varphi(y)dy$

$= \int \Phi(t|y|)\varphi(y)dy$ 