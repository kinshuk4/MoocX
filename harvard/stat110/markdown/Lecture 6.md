## Lecture 6

### Monty Hall

> Suppose you're on a game show, and you're given the choice of three doors: Behind one door is a car; behind the others, goats. You pick a door, say No. 1, and the host, who knows what's behind the doors, opens another door, say No. 3, which has a goat. He then says to you, "Do you want to pick door No. 2?" Is it to your advantage to switch your choice?
>
> From Wikipedia [Monty Hall problem](https://en.wikipedia.org/wiki/Monty_Hall_problem)

1 door has car, 2 doors have goats, Monty knows which door has car.

Monty always open a goat door. If he has a choice , he picks with equal probability.  Should you switch?

Note: if Monty opens door 2, we know door 2 has a goat, and Monty opened door 2

**LOTP: Law of Total Probability**

wish we knew where the car is

$S$ : succeed (assuming switch)

$D_j$ = Door j has car (j = 1, 2, 3)

You choose door 1 and the host opens door 3

$P(S) = P(S|D_1)\frac{1}{3}+ P(S|D_2) \frac{1}{3} + P(S|D_3) \frac{1}{3}$

$= 0 + 1 * \frac{1}{3}+ 1* \frac{1}{3} =\frac{2}{3}$

By symmetry P(S|Monty opens 2) = 2/3

### Simpson's Paradox

| Dr.Hibbert | heart | bandaid |
| ---------- | ----- | ------- |
| success    | 70    | 10      |
| failure    | 20    | 0       |

| Dr.Nick | heart | bandaid |
| ------- | ----- | ------- |
| success | 2     | 81      |
| failure | 8     | 9       |



$A$: successful surgery

$B$: treated by Nick

$C$: heart surgery

$P(A|B) = 81\% > P(A|B^c) = 80\%$ 

$P(A|B, C)  < P(A|B^c,C)$

$P(A|B, C^c) < P(A|B^c, C^c)$

C is a confounder