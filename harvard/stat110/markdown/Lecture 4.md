## Lecture 4

**Definition:** Events A, B are independent if $P(A\cap B) = P(A)P(B)$ 

Note: completely different form disjointness

$A, B, C$ are independent, if $P(A, B) = P(A)P(B)$, $P(A,C) = P(A)P(C)$, $P(B,C) = P(B)P(C)$ and $P(A, B, C) = P(A)P(B)P(C)$

Similarly for events A1,…An

### Newton-Pepys Problem(1693)

> The **Newton–Pepys problem** is a [probability](https://en.wikipedia.org/wiki/Probability) problem concerning the probability of throwing sixes from a certain number of dice.
>
> In 1693 [Samuel Pepys](https://en.wikipedia.org/wiki/Samuel_Pepys) and [Isaac Newton](https://en.wikipedia.org/wiki/Isaac_Newton) corresponded over a problem posed by Pepys in relation to a [wager](https://en.wikipedia.org/wiki/Gambling) he planned to make. The problem was:
>
>
> *A. Six fair dice are tossed independently and at least one “6” appears.*
>
> *B. Twelve fair dice are tossed independently and at least two “6”s appear.*
>
> *C. Eighteen fair dice are tossed independently and at least three “6”s appear.*
>
> Pepys initially thought that outcome C had the highest probability, but Newton correctly concluded that outcome A actually has the highest probability.
>
> Quoted from Wikipedia : [Newton–Pepys problem](https://en.wikipedia.org/wiki/Newton%E2%80%93Pepys_problem)

**Answer:**

$P(A) = 1 - (5/6)^6 \approx 0.665$

$P(B) = 1 - (5/6)^{12} - 12 *(1/6)(5/6)^{11}  \approx  0.619$ 

$P(C) = 1 - \sum_{k=0}^2 \binom{18}{k} (1/6)^k (5/6)^{(18-k)}  \approx 0.597$ 

### Conditional Probability 条件概率

— How should you update probability/beliefs/uncertainty based on new evidence?

> "Conditioning is the soul of statistic" 

#### Definition:

$$P(A|B) = \frac{P(A\cap B)} {P(B)}$$, if $P(B) > 0$

#### Intuition:

1. Pebble world , there are finite possible outcomes, each one is represented as a pebble. For example, 9 outcomes, that is 9 pebbles , total mass is 1. B: four pebbles, $P(A|B)$:get rid of pebbles in $B^c$ , renormalize to make total mass again

2. Frequentist world: repeat experiment many times

   (100101101) 001001011 11111111 

    circle repeatitions where B occurred ; among those , what fraction of time did A also occur?

### Theorem

1. $P(A\cap B) = P(B)P(A|B) = P(A)P(B|A)$
2. $P(A_1…A_n) = P(A_1)P(A_2|A_1)P(A_3|A_1,A_2)…P(A_n|A_1,A_2…A_{n-1})$
3. $$P(A|B) = \frac{P(B|A)P(A)}{P(B)}$$ 