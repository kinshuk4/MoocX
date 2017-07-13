# Dynamic programming

Dynamic programming is similar to the 'divide-and-conquer' paradigm. It calls itself and goes to a smaller problem, which makes it a recursive structure.

Dynamic programming is often used for *optimzation problems*, which is to find the optimal (minimum or maximum value). It's a huge step up from brute force.

## Memoization

Memoization is storing the result of each sub-problem in an array or hash table. This makes subsequent attempts at solving the problem easier as it saves computation at the completed sub-problems at later stages.

## Fibonacci sequence

The fibonacci sequence is a classic example in employing recursion to solve a problem. For the function `F(n) = F(n-1) + F(n-2)`, the resulting recursion tree for `n = 4`

- the amount of recursion needed makes it very slow!
- top-down memoization dynamic programming helps, but memoization makes it easier because you're just calling the function at a previous state.
- bottom-up presents a solution whereby there a as few wasted cycles.

**TODO**: publish code and benchmark for above three cases, fact-check and elaborate more on above sections. write entry on data-structures

## Example Problems

There's the classic [0/1 Knapsack Problem](https://en.wikipedia.org/wiki/Knapsack_problem#Definition). A simple DP problem appeared in NOI 2008, called [Gecko](https://github.com/vjc-informatics/resources/tree/master/problem_sets/gecko).

More notes:

- [dp1.pdf](dp/dp1.pdf)
- [dp2.pdf](dp/dp2.pdf)
- [dp3.pdf](dp/dp3.pdf)

https://github.com/vjc-informatics/resources

https://github.com/nikhilchandak/Competitive-Programming-Notes