# Markov Localisation

**Lesson outline:**

- Marvok localisation: Bayes Localisation Filter
- Implement 1D Realisation of the Markov Localisation Filter in C++
- Motion and Observation Model

### Localisation Posterior: Introduction

![img](../images/11.1.png)

- z: vector of observations from time 1 to t
- m: assume map is fixed, so no time indices
- Objective: Seek transformation between local coordinates of car and global coordinates of map
  - Formulate belief bel(x_t)
- SLAM problem if you wish to solve for map (not today)

### Explaining input for 1D

![img](../images/11.2a.png)![img](../images/11.2b.png)![img](../images/11.2c.png)

- m: Work with Landmark-based maps (positions of lampposts, trees marked).
  - Generally more sparse than grid-based maps.
  - m = [1,5,10]
- z: car measures nearest k seen static objects in the driving direction
  - Observation list z_t = [...] length k
- u: control vector includes direct move between consecutive time steps
  - u = [[u_t,...,u_1], each u_t is the distance travelled by the car between time t and time t-1.
- bel(x_t) = [p(x) car is at x_t=0, p(x) car is at x_t=1,...]

Limitations:

- How much data: 432 GB of data for 10Hz LIDAR across 6 hours, 5 pieces of 4byte data per observation

*Structure of input data (coding quiz)*

### Problems with estimating our belief directly

![img](../images/11.3.png)

- Requirements are the features we would like our filter to have.
- Handles same amount of data per update (irrespective of amount of time the vehicle's been driving)
- Idea: Update belief based only on bel(x_t-1) and new observation.
- Markov assumption: concerns the relationship between uncertain quantities.

Define localisation posterior:

- Split observations into current and previous observations.

![img](../images/11.4a.png)![img](../images/11.4b.png)

Suppose we have no information about where the car was in the previous state x_{t-1}.

- use Law of Total Probability

![img](../images/11.5.png)

- Predict x_t from x_t-1. Use controls to estimate x_t-1 as well as x_t.

![img](../images/11.6.png)

### Markov Assumptions

#### First Order Markov Assumption

![img](../images/11.7.png)

- x_2 is the best predictor for x_3. States further back have no extra info that can predict x_3 better than that which x_2 has.
  - i.e. x_3 is independent of x_0, x_1.
- Therefore can rewrite posterior.
- Important: x_0 must be initialised correctly.

#### 2nd Markov Assumption

- u_t tells us nothing about x_t-1 because u_t is in the future.

Using the Markov Assumption to simplify our expression

![img](../images/11.8a.png)![img](../images/11.8b.png)

- Note on steps: Split control into u_t and previous controls
- After applying the Markov Assumption, the term p(x*t-1, ...m) describes the belief at x*{t-1}. This means we achieved a recursive structure!
- Can replace integral by sum over x_is because we're in the discrete case.

## Implementation Details for Motion Model

![img](../images/11.9a.png)![img](../images/11.9b.png)

- Transition model controlled only by x_t-1 and u_t.

![img](../images/11.10.png)![img](../images/11.11.png)

### Code

In [ ]:

```

```

## Observation Model

![img](../images/11.12.png)

- Simplify using the Markov Assumptions

![img](../images/11.13a.png)![img](../images/11.13b.png)

- Assume noise behaviour is independent (and all observations are independent)
- Pseudo range: represent ranges assuming car can stand precisely at position x_t (on a point).

### Bayes Localisation

![img](../images/11.16.png)

- Motion model includes transition model, belief at x_t-1.
- Motion model also called prediction step for belief x_t.

Observation step: new probabilities.

### Code

![img](../images/11.17.png)![img](../images/11.18.png)![img](../images/11.19.png)