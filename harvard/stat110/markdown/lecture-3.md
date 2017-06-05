## Lecture 3

### [Birthday Problem](https://en.wikipedia.org/wiki/Birthday_problem)

(Exclude Feb 29, assume 365 days equally likely, assume indep. of birth)

k people , find prob. that two have same birthday 

If k > 365, prob. is 1

Let k <= 365, $P(no  match) =\frac{ 365 * 364 *... (365 - k + 1) }{365^k}$ 

P(match) ~ 50.7%, if k = 23; 97% if k = 50; 99.9999%, if k = 100

$\binom{k}{2} =  \frac{k(k-1)}{2} $  $\binom{23}{2} = 253$ 

### Properties of Probability

1. $P(A^c) = 1 - P(A)​$    
2. If $A \subseteq B$ , then $P(A) \subseteq P(B)$ 
3. $P(A\cup B) = P(A) + P(B) - P(A\cap B)$

$P(A\cup B\cup C) = P(A) + P(B) + P(C) - P(A\cap B) - P(A\cap C) - P(B\cap C) + P(A\cap B\cap C)$ 

**Proof:**

1. $1 = P(S) = P(A\cap A^c) = P(A) + P(A^c)$
2. $B = A\cup(B\cap A^c)$  $P(B) = P(A)+P(B\cap A^c)$ 
3. $P(A\cup B) = P(A\cap (B\cap A^c)) = P(A) + P(B\cap A^c)$ 

General case: 



### deMontmort's Problem(1713)

**[matching problem](http://www.math.uah.edu/stat/urn/Matching.html)** 

n cards labeled 1 to n, flipping cards over one by one, you win if the card that you name is the card that appears.

 Let $A_j$  be the event, ''jth card matches" 

$P(A_j) = 1 / n $ since all position equally likely for card labeled j

$P(A_1\cap A_2) = (n-2)! / n! = 1/n(n-1)$ 

...

$P(A_1\cap … A_k) = (n-k)! / n!$ 

$P(A_1\cup …A_n) = n*1/n - n(n-1)/2 * 1/n(n-1) + …$

$= 1 - 1/2! + 1/3! - 1/4! … (-1)^n1/n!$ $\approx 1- 1/e$ 