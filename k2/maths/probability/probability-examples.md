## Probability Examples

This repository is a collection of notes and examples on probability. Feel free to send feedback, more examples, or clarifications.

### Probability Distilled

*Probability is the very guide of life.* Cicero

*The probability of an event is the ratio of the number of cases favorable to it, to the number of all cases possible, when [the cases are] equally possible. ... Probability is thus simply a fraction whose numerator is the number of favorable cases and whose denominator is the number of all the cases possible.* Laplace

*Information is the ability to make predictions with a likelihood better than chance.* Chris Adami

### Conditional Probability

Question: Mr. Smith has two children. At least one of them is a boy. What is the probability that both children are boys?

[Go To Answer](#answer-one)

Question: Mr. Jones has two children. The older child is a boy. What is the probability that both children are boys?

[Go To Answer](#answer-two)

Question: Mrs. Smith has two children. At least one of them is a boy born on Tuesday. What is the probability that both children are boys?

[Go To Answer](#answer-three)

Question: There are three drawers. One drawer has two gold coins, one drawer has two silver coins, and one drawer has one gold and one silver coin. You randomly pick a drawer and randomly select a coin from within that drawer. The coin you select is a silver coin. What is the probability that the other coin is also a silver coin?

[Go To Answer](#answer-twenty-one-)

Question: This is a classic, called the Monty Hall problem. Suppose you're on a game show, and you're given the choice of three doors: Behind one door is a car; behind the others, goats. You pick a door, say door 1, and the host, who knows what's behind all three doors, opens another door, say door 3, which has a goat (the fact that the host always picks a door with a goat behind it is key to this question). He then says to you, "Do you want to pick door 2?" Is it to your advantage to switch your choice?

[Go To Answer](#answer-four)

Question: Sleeping Beauty volunteers to undergo the following experiment. On Sunday she will be put to sleep. Once or twice, during the experiment, she will be awakened, interviewed, and put back to sleep with an amnesia-inducing drug that makes her forget that awakening. A fair coin will be tossed to determine which experimental procedure to undertake: if the coin comes up heads, Sleeping Beauty will be awakened and interviewed on Monday only. If the coin comes up tails, she will be awakened and interviewed on Monday and Tuesday. In either case, she will be awakened on Wednesday without interview and the experiment ends. Any time Sleeping Beauty is awakened and interviewed, she is asked, "What's the chance that the coin toss was heads?" What should she say?

[Go To Answer](#answer-five)

Question: A test of a disease has a 95% specificity rate (5% of the time the test is incorrect, or false positive). The disease strikes 1/1000 of the population. People are tested at random, regardless of whether they are suspected of having the disease. A patient’s test is positive. What is the probability that the patient actually has the disease, given a positive test result?

[Go To Answer](#answer-six)

Question: A test of a disease has a 95% sensitivity rate (5% of the time the test is incorrect, or false negative). The disease strikes 1/1000 of the population. People are tested at random, regardless of whether they are suspected of having the disease. A patient’s test is negative. What is the probability that the patient actually has the disease, given a negative test result?

[Go To Answer](#answer-seven)

### Sampling

Question: You live in a town with two hospitals -- one large, the other small. On a given day, 60 percent of those born in one of the two hospitals are boys. Which hospital is it likely to be?

[Go To Answer](#answer-eight)

Question: During World War II, the statistician Abraham Wald tried to determine where to add extra armor to airplanes. Available data consisted of the patterns of bullet holes on planes that had survived battle. What was his strategy for where to add extra armor?

[Go To Answer](#answer-nine)

### Binomial Combinations

Question: In a five-card poker hand in a standard 52-card deck, what’s the probability of getting dealt four aces?

[Go To Answer](#answer-ten)

Question: What’s the probability of winning “Lotto 6/49”? To win, you must pick exactly six lucky numbers out of 49 randomly drawn numbers.

[Go To Answer](#answer-eleven)

Question: A machine has three independent engines and needs at least two to function properly. The probability that each engine works properly is 99%. What is the probability that at least two engines work?

[Go To Answer](#answer-twelve)

### Coincidences

Question: 100 people are in the same room. How many people does it take for the probability that two or more people share the same birthday to exceed 50%?

[Go To Answer](#answer-thirteen)

Question: What’s the probability that a monkey at a typewriter accidentally writes Hamlet? Assume 35 symbols, including letters, spaces, and punctuation marks.  Assume the total number of symbols in Hamlet to be 250,000.  

[Go To Answer](#answer-fourteen)

### Monte Carlo Induction

Question: There’s a picture on the wall of a circle drawn inside of a square (such that the circle touches the square at four points). How would you calculate the number Pi by randomly throwing darts at the drawing? Assume all darts hit the drawing somewhere with uniform probability.

[Go To Answer](#answer-fifteen)

### Power Laws

In a Gaussian bell curve, probabilities drop at an exponentially accelerating rate as you move away from the mean. In a “power law” distribution, probabilities drop at a scalar rate.

To visualize this, call the number of occurrences higher than a specific level an “exceedance” (e.g., the exceedance of one million is the number of people exceeding net worth of one million). The main property of “power laws” is scalability, such that the ratio of two exceedances is the ratio of of the exceedances raised to the negative power of the power exponent.

For example, say you think 100 books will sell more than 250,000 copies this year in the U.S. and you think the power exponent of U.S. book sales is 1.5. Then you can estimate that

```
100 * (500000/250000)^-1.5 = 35
```
books will sell more than 500,000 copies and

```
100 *(1000000/250000)^-1.5 = 12
```
books will sell more than 1,000,000 copies.

The power exponent itself is guessed a priori or inferred from past data, which makes it difficult to know the true parameter, if it actually exists. You can only estimate the exponent from past data or rely on some model that provides a crude approximation. Even with historical data, the exponent is unknowable since history never precisely repeats itself.

The exponent also varies along the distribution depending on the cut points you choose. A single precise exponent does not exist. Despite this limitation, it's useful to know that books sales, wealth distribution, word distribution, venture capital returns, stock markets, and many other phenomena are scalable, not Gaussian.

### Random Selection

Question: A drawer contains red and black socks. If you draw two socks at random, the probability that both socks are red is 1/2. What's the smallest number of socks that the drawer can contain?

[Go To Answer](#answer-sixteen)

Question: A drawer contains red and black socks. If you draw two socks at random, the probability that both socks are red is 1/2. What's the smallest number of socks that the drawer can contain if the number of black socks is even?

[Go To Answer](#answer-seventeen)

### Other

Question: Susan's father offers her a prize if she wins at least two tennis sets in a row in a three-set series to be played with her father and the club champion alternately: father-champion-father or champion-father-champion. The club champion is a better player than her father. Which series should Susan choose to maximize her chances of winning at least two games in the series?

[Go To Answer](#answer-eighteen)

Question: A three-person jury has two members, each of whom independently has probability p of making the correct decision, and a third juror who flips a coin. Majority rules for the three-person jury. A one-person jury has probability p of making the correct decision. Which jury has the better probability of making the correct decision?

[Go To Answer](#answer-nineteen)

Question: On average, how many times must you throw a die until you get a 6?

[Go To Answer](#answer-twenty)

### Answers

<a name="answer-one"></a>
**Answer: 1/3**

We know that there are either two boys or one boy.

Let "B" denote boy, "G" denote girl, and order denote birth order (for example, "BG" means the oldest child is a boy).

The probability that both children are boys, given that at least one child is a boy is:

```
[BB] / [BB, BG, GB] = 1/3
```
Another way to see this is by running a simple simulation of 1,000,000 randomly generated families with two children, at least one of whom is a boy.

``` python
from __future__ import division
import random

def random_child():
	return random.choice(["boy", "girl"])

both_boys = 0
either_boy = 0

random.seed(0)
for _ in range(1000000):
	younger = random_child()
	older = random_child()
	if older == "boy" and younger == "boy":
		both_boys += 1
	if older == "boy" or younger == "boy":
		either_boy += 1

print "both_boys count", both_boys
print "either_boy count", either_boy
print "P(both boys given at least one boy)", both_boys / either_boy
```

<a name="answer-two"></a>
**Answer: 1/2**

We know that the older child is a boy. The younger child could be either a boy or a girl.

Let "B" denote boy, "G" denote girl, and order denote birth order (for example, "BG" means the oldest child is a boy).

The probability that both children are boys, given that the older child is a boy is:

```
[BB] / [BB, BG] = 1/2
```
Another way to see this is by running a simple simulation of 1,000,000 randomly generated families with two children, the older of whom is a boy.

``` python
from __future__ import division
import random

def random_child():
	return random.choice(["boy", "girl"])

both_boys = 0
older_boy = 0

random.seed(0)
for _ in range(1000000):
	younger = random_child()
	older = random_child()
	if older == "boy":
		older_boy += 1
	if older == "boy" and younger == "boy":
		both_boys += 1

print "both_boys count", both_boys
print "older_boy count", older_boy
print "P(both boys given older boy)", both_boys / older_boy
```

<a name="answer-three"></a>
**Answer: 13/27**

We only know that one of the children is a boy born on a Tuesday. The other child could be a boy or a girl born on any day of the week, and could be older or younger than the boy born on a Tuesday.

Let "B" denote boy, "G" denote girl, number denote day of the week (1 for Sunday, 2 for Monday, etc.), and order denote birth order (for example, "B3B1" means an older boy born on a Tuesday and younger boy born on a Sunday, while "G1B3" means an older girl born on a Sunday and younger boy born on a Tuesday).

The probability that both children are boys, given that one of the children is a boy born on a Tuesday is:

```
[B3B1, B3B2, B3B3, B3B4, B3B5, B3B6, B3B7, B1B3, B2B3, B4B3, B5B3, B6B3, B7B3] /
[B3B1, B3B2, B3B3, B3B4, B3B5, B3B6, B3B7, B1B3, B2B3, B4B3, B5B3, B6B3, B7B3, B3G1, B3G2, B3G3, B3G4, B3G5, B3G6, B3G7, G1B3, G2B3, G3B3, G4B3, G5B3, G6B3, G7B3]
= 13/27
```

<a name="answer-four"></a>
**Answer: Yes, you should switch doors**

Think of the event space for this problem. Let "C" denote car, and "G" denote goat, and order denote the door (for example, [C, G, G] means a car behind door 1, a goat behind door 2, and a goat behind door 3). The event space is as follows:

```
[C, G, G], [G, C, G], [G, G, C]
```

The host always selects a goat, and never a car. Consequently, switching doors is always advantageous. To see why, take an example where you select door 1. Remember, the host always selects a goat from one of the two doors you don't choose.  

```
[C, G, G] → if you switch from door 1, you get a goat
[G, C, G] → if you switch from door 1, you get a car
[G, G, C] → if you switch from door 1, you get a car
```

Run the same analysis, but this time you select door 2.

```
[C, G, G] → if you switch from door 2, you get a car
[G, C, G] → if you switch from door 2, you get a goat
[G, G, C] → if you switch from door 2, you get a car
```

Run the same analysis, but this time you select door 3.

```
[C, G, G] → if you switch from door 3, you get a car
[G, C, G] → if you switch from door 3, you get a car
[G, G, C] → if you switch from door 3, you get a goat
```

Switching doors gives you 2/3 chance of winning the car.

<a name="answer-five"></a>
**Answer: 1/3**

Answer: Sleeping Beauty can’t remember any previous interview and has no idea whether the current interview is on Monday or on Tuesday. An interview with an equally likely coin toss is an equally likely event. Therefore, the event space of all equally likely interview possibilities is:

```
Heads tossed with 50% probability → An interview happens on Monday
Tails tossed with 50% probability → An interview happens on Monday
Tails tossed with 50% probability → An interview happens on Tuesday
```

Remember, Sleeping Beauty has amnesia about any previous interview and has no context of the interview taking place now -- is it Monday or Tuesday, she has no clue. She only knows that a fair coin was tossed and the rule for experimental procedure to undertake based on the outcome of the coin toss.

The probability that the coin landed heads, given the set of possible interviews, each of which is equally likely in the event space is:

```
[Monday interview with heads toss] /
[Monday interview with heads toss, Monday interview with tails toss, Tuesday interview with tails toss]
= 1/3
```

<a name="answer-six"></a>
**Answer: 1.9%**

Just because the person tests positive does not mean she actually has the disease.  Take a population 100,000 people. What’s the frequency of people who have the disease AND test positive (called true positives) compared to the frequency of people who do not have the disease AND test positive (called false positives)?

True positive means a person has the disease AND tests positive. In this example, 95 people are true positives in a population of 100,000.

```
100 people do have the disease * 0.95 true positive rate
= 95 people
```

False positive means a person does not have the disease AND tests positive. In this example, 4,995 people are false positives in a population of 100,000.

```
99900 people do not have the disease * 0.05 false positive rate
= 4995 people
```

The probability that the patient actually has the disease given a positive test is:

```
frequency of true positives /
(frequency of true positives + frequency of false positives)
= 95 / (95 + 4995) = 1.9%
```

<a name="answer-seven"></a>
**Answer: 0.005%**

Just because the person tests negative doesn’t mean she's really disease-free. Take a population 100,000 people. What’s the frequency of people who do not have the disease AND test negative (true negatives) compared to the frequency of people who do have the disease AND test negative (false negatives)?

True negative means a person does not have the disease AND tests negative. In this example, 94,905 people are true negatives in a population of 100,000.

```
99900 people do not have the disease * 0.95 true negative rate
 = 94905 people
```

False negative means a person does have the disease AND tests negative. In this example, 5 people are false negatives in a population of 100,000.

```
100 people do have the disease * 0.05 false negative rate
= 5 people
```

The probability that the patient has the disease given a negative test is:

```
frequency of false negatives /
(frequency of false negatives + frequency of true negatives)
= 5 / (5 + 94905) = 0.005%
```

<a name="answer-eight"></a>
**Answer: The smaller hospital**

The larger hospital has a larger sample which is more stable and should fluctuate less from the long-term average than smaller samples (here, the long-term average is 50% for each of the two sexes).

The smaller the sample size, the more the sample distribution will deviate from the population distribution. For symmetric distributions like gender, knowledge of the population increases non-linearly by the square root of n with an n-fold increase in sample size.

For example, if you draw from an urn of 50% red and 50% black balls, your confidence level about the relative proportion of red and black balls after 50 drawings versus 25 drawings is not twice as high, but rather the

```
sqrt(50/25) = sqrt(2)
```
times as high (i.e. , ~ 1.4 times more confident).

<a name="answer-nine"></a>
**Answer: Add armor to the parts of the plane that were NOT hit.**

Why? The patterns where bullet holes appeared are for planes that survived. What about the planes that were shot down? Assume that all planes had been hit more or less uniformly. The planes hit in marked areas were able to return. This means that planes that didn’t return were most likely hit somewhere else -- in unmarked places. The unmarked areas on the surviving planes were the areas that needed more armor.

<a name="answer-ten"></a>
**Answer: 1 in 54,145**

First, we need to work out the number of five-card poker hands in a standard 52-card deck. The total number of five-card hands if the order of the cards matters (i.e., permutations) is:

```
52 * 51 * 50 * 49 * 48
```

Since the order of the cards in each five-card hand doesn’t matter, we divide by the possible ways that a given poker hand can be dealt, which is:

```
5 * 4 * 3 * 2 *1
```

Dividing these numbers yields the number of five-card hands in the deck (this ratio is called the combinatorial coefficient):

```
(52 * 51 * 50 * 49 * 48) / (5 * 4 * 3 * 2 * 1) = 2598960
```

Within the set of 2,598,960 possible poker hands, there are 48 ways to have four aces plus a fifth card. Therefore, the probability of getting a four-ace poker hand is:

```
48 / 2598960, or 1 in 54145
```

<a name="answer-eleven"></a>
**Answer: 1 in 13,983,816**

The total number of ways to choose a set of 6 numbers out of 49 numbers if the order of the cards matters (i.e., permutations) is:

```
49 * 48 * 47 * 46 * 45 * 44
```

For lotto games, the order of the six numbers selected doesn’t matter. We divide by the possible ways that a given six-number set can occur, which is:

```
6 * 5 * 4 * 3 * 2 * 1
```

Dividing these numbers yields the number of six-number sets (this ratio is called the combinatorial coefficient):

```
(49 * 48 * 47 * 46 * 45 * 44) / (6 * 5 * 4 * 3 * 2 * 1) = 13983816
```

The probability of selecting the winning 6 number set is:

```
1 / 13983816
```

<a name="answer-twelve"></a>
**Answer: 99.97%**

Let’s look at the event space. The machine keeps working if all of the engines work or if two out of three of the engines work.

All three engines work in only one way:

```
[Work, Work, Work]
```

The probability that all three engines work is:

```
0.99 * 0.99 * 0.99 = 97.03%
```

Two out of three engines work in three different scenarios:

```
[Fail, Work, Work]
[Work, Fail, Work]
[Work, Work, Fail]
```

The probability of two engines working is:

```
0.01 * 0.99 * 0.99 = 0.98%
```

There are three ways that two out of three engines work, so the aggregate probability of two engines working is:

```
0.98% * 3 = 2.94%
```

The total probability that at least two engines work is simply the sum of the probabilities above, which is 99.97%.

<a name="answer-thirteen"></a>
**Answer: 23 people**

A good way to figure out the answer is to work out the probability that nobody shares the same birthday each time you add another person to the room.

```
P (at least two people share the same birthday) =
1 -  P (nobody shares the same birthday)
```

For example, when there are two people, the second person can have 364 different birthdays than that of the first person. The probability that two people have different birthdays is:

```
365/365 * 364/365 = 99.73%.
```

And the probability that these two people share the same birthday is:

```
1 - 99.73% = 0.27%
```

When there are three people, the third person can have 363 different birthdays than those of the first two people. The probability that all three people have different birthdays is:

```
365/365 * 364/365 * 363/365 = 99.18%
```

And the probability that at least two people in the group of three share the same birthday is:

```
1 - 99.18% = 0.82%.
```

Extending this process, the likelihood that two or more people share the same birthday increases. For 23 people, the probability that each person has a different birthday is:

```
(365 * 364 * 363 * ... * 343) / (365^23) = 49.27%
```

Therefore, the probability that at least two people in the group of 23 share the same birthday is:

```
1 - 49.27% = 50.73%
```

<a name="answer-fourteen"></a>
**Answer: Effectively Zero (Obviously)**

The probability is obviously infinitesimal, effectively zero for all practical purposes. Mathematically, the probability is

```
1/35^n
```
where n is the number of symbols in Hamlet, say 250,000.

```
1/35^250000
```
is not technically zero, but practically is.

<a name="answer-fifteen"></a>
**Answer: Count the ratio of holes inside the circle to total darts thrown inside the square**

The ratio of holes inside the circle to total darts inside the perimeter of the square (including darts landing inside the circle) approaches Pi with infinite precision as more and more darts are thrown.

The area of the circle is
```
pi  * r^2
```
The area of the square is
```
4 * r^2
```
The ratio of the circle area to square area, call it p is
```
p = (pi * r^2) / (4 * r^2) = pi / 4
```
Therefore,
```
pi = 4p
```
and to simplify
```
pi = p
```
since dividing 4p by 4 to segment the picture into four identical quadrants yields the same ratio (i.e., the ratio of circle:square points within each of the four smaller squares is identical to the ratio of circle:square points in the whole square.

<a name="answer-sixteen"></a>
**Answer: 4 socks**

The smallest number of socks is 4, specifically 3 red socks and 1 black sock. We can express the probability as follows (note that the first sock is not replaced in the drawer once it's selected):

```
p(both selected socks are red) = p(first sock is red) * p(second sock is red) = (initial red socks / initial total socks) * ((initial red socks - 1) / (initial total socks - 1)) = 1/2
```

In the case of 4 socks (3 red and 1 black), the probability of selecting two red socks is exactly 1/2:

```
p(both selected socks are red) = p(first sock is red) * p(second sock is red) = 3/4 * 2/3 = 6/12 = 1/2
```

<a name="answer-seventeen"></a>
**Answer: 21 socks**

If the number of black socks is even, the smallest number of total socks is 21, specifically 15 red socks and 6 black socks. We can express the probability as follows (note that the first sock is not replaced in the drawer once it's selected):

```
p(both selected socks are red) = p(first sock is red) * p(second sock is red) = (initial red socks / initial total socks) * ((initial red socks - 1) / (initial total socks - 1)) = 1/2
```

In the case of 21 socks (15 red and 6 black), the probability of selecting two red socks is exactly 1/2:

```
p(both selected socks are red) = p(first sock is red) * p(second sock is red) = 15/21 * 14/20 = 210/420 = 1/2
```

<a name="answer-eighteen"></a>
**Answer: champion-father-champion**

Susan can win at least two games in three ways:

```
[win, win, lose]
[lose, win, win]
[win, win, win]
```

Each possibility of winning at least two games requires Susan to win the middle game. Since her chances of beating her father are higher than beating the champion, it's advantageous for her to play her father in the second game. This comparative advantage in the middle game outweighs playing the first and last games against the champion.

We can see this with an example. Say she's evenly matched with her father (50% of the time Susan wins against her father) and well outmatched by the champion (10% of the time Susan wins against the champion).

It follows that the probability of Susan winning at least two out of three games for father-champion-father sets is 7.5%:

```
father-champion-father

[win, win, lose] -> 0.5 * 0.1 * (1 - 0.5) = 0.025
[lose, win, win] -> (1 - 0.5) * 0.1 * 0.5 = 0.025
[win, win, win] -> 0.5 * 0.1 * 0.5 = 0.025
```

On the other hand, the probability of Susan winning at least two out of three games for champion-father-champion sets is 9.5%, a better chance for Susan:

```
champion-father-champion

[win, win, lose] -> 0.1 * 0.5 * (1 - 0.1) = 0.045
[lose, win, win] -> (1 - 0.1) * 0.5 * 0.1 = 0.045
[win, win, win] -> 0.1 * 0.5 * 0.1 = 0.005
```

<a name="answer-nineteen"></a>
**Answer: Same probability p for both juries**

Both juries have the same probability p of making the correct decision.

The three-person jury can make the correct decision in four different ways. At least two of the three jurors must be correct (here heads denotes a correct decision):

```
[juror one correct, juror two correct, juror three flips heads] -> p * p * 0.5 = 0.5p^2
[juror one correct, juror two correct, juror three flips tails] -> p * p * 0.5 = 0.5p^2
[juror one correct, juror two incorrect, juror three flips heads] -> p * (1 - p) * 0.5 = 0.5p - 0.5p^2
[juror one incorrect, juror two correct, juror three flips heads] -> (1 - p) * p * 0.5 = 0.5p - 0.5p^2
```

Adding the four values above results in probability p, the same probability p as the single juror.

```
0.5p^2 + 0.5p^2 + 0.5p - 0.5p^2 + 0.5p - 0.5p^2 = p
```

Therefore, there's no difference between the three-person jury and the one-person jury, each jury has probability p of making the correct decision.

<a name="answer-twenty"></a>
**Answer: six**

With a six-sided die, it takes on average six rolls to roll a 6.

To verify this, let p be the probability of rolling a 6 and (q = 1 - p) be the probability if not rolling a 6. For each successive roll, the probability of rolling a 6 is:

```
trial 1: p
trial 2: q * p
trial 3: q^2 * p
etc.
```
The sum of these probabilities is 1.

We'll call the average number of trials m (the expected number of trials). m is defined as:

```
m = p + 2pq + 3pq^2 + 4pq^3 + ....
```

Solving for m shows that.

```
m(1 - q) = mp = 1
```

And therefore.

```
m = 1/p
```

In this example, the probability p of rolling a six is 1/6 and the average number of rolls m is:

```
m = 1/p = 1/(1/6) = 6
```

<a name="answer-twenty-one-"></a>
**Answer: 2/3**

We know that a silver coin was selected. This can occur in only three ways, and each of these ways is equally likely to occur with probability of 1/3.

```
drawer with two silver coins and first silver coin selected -> other coin is silver -> 1/3
drawer with two silver coins and second silver coin selected -> other coin is silver -> 1/3
drawer with one silver coin and silver coin selected -> other coin is gold -> 1/3
```

Since each of the possibilities above is equally likely, the probability that the other coin is silver is 2/3:

```
(1/3 + 1/3) / 1 = 2/3
```



https://github.com/benbusse/probability-examples