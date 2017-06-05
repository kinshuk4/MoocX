## Lecture 5

**Thinking** conditionally is a condition for thinking

**How to solve a problem?**

1. Try simple and extreme cases

2. Break up problem into simple pieces 

   $P(B) = P(B|A_1)P(A_1) + P(B|A_2)P(A_2) +…P(B|A_n)P(A_n)$

   law of total probability 

   ​

<u>Example 1</u> 

Suppose we have 2 random cards from standard deck

Find $P(both\  aces|have\ ace)$, $P(both\ aces|have\ ace\ of\ spade)$

$$P(both\ aces|have\ ace) = P(both\ aces,\cancel{have\ ace}) / P(have\ ace) =  \frac{\binom{4}{2}/\binom{52}{2}}{1-\binom{48}{2}/\binom{52}{2}} = 1/33$$

$P(both\ aces|have\ ace\ of\ spade) = 3/51 = 1/17 $ 

<u>Example 2</u> 

Patient get tested for disease afflicts 1% of population, tests positve (has disease)

Suppose the test is advertised as "95% accurate" , suppose this means 

$D$: has disease, $T$: test positive

Trade-off: It's rare that the test is wrong, it's also rare the disease is rare

$P(T|D) = 0.95 = P(T^c |D^c)$

$$P(D|T) = \frac{P(T|D)P(D)}{P(T)} = \frac{P(T|D)P(D)}{P(T|D)P(D) + P(T|D^c)P(D^c}$$  



### Biohazards

1. confusing $P(A|B)$, $P(B|A)$  ([procecutor's fallacy](https://en.wikipedia.org/wiki/Prosecutor%27s_fallacy)) 

<u>Ex</u> [Sally Clark](https://en.wikipedia.org/wiki/Sally_Clark) case, SIDS

want P(innocence |evidence) 

1. confusing $P(A) - prior先验$ with $P(A|B) - posterior 后验$

P(A|A) = 1

1. confusing independent with conditional independent 



**Definition**:

 	Events $A,B$ are conditionally independent given $C$ if $P(A\cap B|C) = P(A|C)P(B|C)$

**Q:**Does conditional indep given C imply indep. ? No

Ex. Chess opponent of unknown strength may be that game outcomes are conditionally independent given strength 

**Q:**Does independent imply conditional independent given C? No

Ex. A: Fire alarm goes off, cause by : F:fire; C:popcorn. suppose F, C independent But P(F|A, C^c^) = 1 not conditionally indep given A



### Recommendations 推荐书籍

_Statistical science in the courtroom_ 

_Statistics for lawyers_

