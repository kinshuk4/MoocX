$\newcommand{\cnd}[2]{\left.#1\,\middle|\,#2\right.}$
$\newcommand{\pr}[1]{\mathbf{P}\!\left(#1\right)}$
$\newcommand{\cpr}[2]{\pr{ \cnd{#1}{#2} } }$
$\newcommand{\setst}[2]{\left\{#1\,\middle|\,#2\right\}}$
$\newcommand{\ex}[1]{\mathbf{E}\left[#1\right]}$
$\newcommand{\cex}[2]{ \ex{ \cnd{#1}{#2} } }$
$\newcommand{\var}[1]{\text{var}\left(#1\right)}$
$\newcommand{\cvar}[2]{ \var{\cnd{#1}{#2}} }$
$\newcommand{\d}{ \text{d} }$
$\newcommand{\iint}[2]{ \! #1 \,\d #2 }$
$\newcommand{\pmf}[2]{ p_{ #1 }\left( #2 \right) }$
$\newcommand{\cpmf}[3]{ \pmf{ \cnd{#1}{#2} }{#3} }$
$\newcommand{\pdf}[2]{ f_{ #1 }\left( #2 \right)}$
$\newcommand{\cpdf}[3]{ \pdf{ \cnd{ #1 }{ #2 } }{ #3 } }$
$\newcommand{\cdf}[2]{ F_{ #1 }\left( #2 \right)}$
$\newcommand{\if}{\text{if }}$
$\newcommand{\exp}{\text{exp}}$
$\newcommand{\norm}{\mathcal{N}}$
$\DeclareMathOperator{\exp}{exp}$
$\DeclareMathOperator{\cov}{cov}$
$\newcommand{\ninfty}{{-\infty}}$
$\newcommand{\abs}[1]{ \left|#1\right| }$

# Solved Problems

## S1

Visual example 0-5:00

## S2

Relate $X$ and $Y$ together
![](unit6-solved-problems\2614ba15e014e2d654bebfb258b367dc.png)

Find the
![](unit6-solved-problems\86b562e514c39468666c888562f5a366.png)

Finally, think about the range:
![](unit6-solved-problems\48424932bcd653cf29498ac66eed958f.png)

Where was my reasoning incorrect?

Given the facts of the
theirs
![](unit6-solved-problems\4048b41f00281a84241f46bd0e7d240c.png)

mine
![](unit6-solved-problems\c41caadebfd892e897df7f148ea788da.png)
Problems:
1. Using $\sqrt{x}$ instead of $x^2$. In order for $\sqrt{x}$ to have the same distribution as $x$, I need to modify the equation so that it resolves to $x$, not insert $\sqrt{x}$ into the equation. Doing the latter does the opposite of what I want.
2. When laying out the integral representing $F_Y(y)$, why can't you just take what is inside? Is the trick where taking the derivative of the integrand

always think about the ranges

## S3
Ambulance travel time. An ambulance travels back and forth, at a constant speed v, along a road of length ℓ. We may model the location of the ambulance at any moment in time to be uniformly distributed over the interval (0,ℓ). Also at any moment in time, an accident (not involving the ambulance itself) occurs at a point uniformly distributed on the road; that is, the accident's distance from one of the fixed ends of the road is also uniformly distributed over the interval (0,ℓ). Assume the location of the accident and the location of the ambulance are independent.

Supposing the ambulance is capable of immediate U-turns, compute the CDF and PDF of the ambulance's travel time T to the location of the accident.

![](unit6-solved-problems\a19d3be0788d062600a52b3b901b3f53.png)

![](unit6-solved-problems\6be83f1a7ec0790e2588845281fb74fb.png)

## S4


![](unit6-solved-problems\ea96e08709d98502a536a3a34b39cf26.png)

we could do the same integration for the other side but it's easier to notice that it is symmetric

![](unit6-solved-problems\cbbdd3eabe5b8f9b13a65f22c27bb39c.png)
![](unit6-solved-problems\6e374ea99bee8bf674d12a532995e579.png)
![](unit6-solved-problems\01db7d3d6159440be948947b0e13c2f4.png)

## S5
