## Lecture 2

1. Don't lose common sense 
2. Do check answers, especially by doing simple and extreme cases
3. Label people , objects etc. If have n people, then label them 1,2…n

Example: 10 people, split into them of 6, team of 4 => $\binom{10}{6}$  2 teams of 5 => $\binom{10}{5}$ /2

Problem: pick k times from set of n objects, where order doesn't matter, with replacement.

Extreme cases: k = 0; k = 1; n = 2

Equiv : how many ways are there to put k indistinguishable particles into  n distinguishable boxes?



### Story proof- proof by interpretation

Ex1 $\binom{n}{k}$ =  $\binom{n}{n-k}$ 

Ex2  n$\binom{n-1}{k-1}$ = k$\binom{n}{k}$  pick k people out of n, with one desigenate as president.

Ex3 $ \binom{m+n}{k} = \sum_{j=0}^k \binom{m}{j} \binom{n}{k-j} $ ([vandermonde](https://en.wikipedia.org/wiki/Vandermonde_matrix)) (范德蒙)

### Axioms of Probability

**Non-naive definition**

Probability sample consists of S and P, where S is sample space, and P , a function which takes an event $A\subseteq S$ as input, returns $P(A) \in [0,1]$ as output. 

such that 

1. $P(\phi) = 0, P(S) = 1$ 
2. $P(U_{n=1}^{\infty}A_n) =  \sum_{n=1}^{\infty} P(A_n) $ if $A1,A2..An$ are disjoint (not overlap)