## Udacity - Artificial Intelligence for Robotics

Notes and code snippets from AI for Robotics

![A star algorithm](images/a-star-in-action.jpg)

Course lasts for 7 weeks:

- [l1-localization](l1-localization)
- [l2-kalman-filters](l2-kalman-filters)
- [l3-particle-filters](l3-particle-filters)
- [l4-search](l4-search)
- [l5-pid-control](l5-pid-control)
- [l6-slam](l6-slam)
- [l7-final-exam-n-project-runaway-robot](l7-final-exam-n-project-runaway-robot)


Code and notes based on Udacity course AI for Robotics.

Also found these summary notes:[https://storage.googleapis.com/supplemental_media/udacityu/cs373/Brief%20Course%20Summary.pdf](https://storage.googleapis.com/supplemental_media/udacityu/cs373/Brief%20Course%20Summary.pdf)

## Localization:

localization : initial belief --> sense --> move --> sense --> move

i.e. : initial belief --> ( back & forth : sense <--> move )

move loses info

sense gains info

- belief = probability
- sense = multiplication followed by normalization
- move = addition (convolution)

## Bayes Rule, for Localization:

p(A|B) = p(A) * p(B|A) / p(B)

p(Xi|M) = p(Xi) * p(M|Xi) / p(M)

- (let Xi = probability of being at a given position xi)
- (let M = probability of getting a given measurement)

p(Xi|M) = prior * measurement probability / p(M)

p(M) = normalization constant

p(M) = sum over i of ( p(Xi) * p(M|Xi) )

So: p(Xi|M) = p(Xi) * p(M|Xi) / sum over i of ( p(Xi) * p(M|Xi) )

## Theorem of Total Probabiltiy, for Motion:

p(A) = p(B) * p(A|B)

p(Xit) = sum over j of ( p(Xjt-1) * p(Xi|Xj) )

- (let t = current time stamp)
- (let t-1 = previous time stamp)

# Comparing Filters:

| Filters:                | Histogram Filters  | Kalman Filters                  | Particle Filters         |
| ----------------------- | ------------------ | ------------------------------- | ------------------------ |
| State Space             | discrete           | continuous :)                   | continuous :)            |
| Belief 'Bumps' Allowed  | multimodal :)      | unimodal                        | multimodal :)            |
| Algorithm Efficiency    | exponential (dims) | quadratic :)                    | ? (keep =< 4 dimensions) |
| Robot Position Accuracy | approx. (discrete) | approx. (linear vs. non-linear) | approximate              |

**Histogram Filter**: Measurement Updates

Approximate / proportional

P(X|Z) <- P(Z|X) P(X)

P(X|Z) <-(resampling)- importance weights * particles

**Kalman Filter**: Motion Updates

Gaussians

P(X') = sum ( P(X'|X) P(X) )

P(X') = sum of each ( sample * particle )

But the key advantage of particle filters is: easy to program. :)

**Google Car**:

Histogram Methods + Particle Methods

Robot model: 2 stationary, 2 non-stationary wheels. "Bicycle model" because half of it stationary/non wheels.

Sensor data: map (not landmarks) <- match with snapshots.

Additional sensors: GPS, inertial, etc.

## Particle Filter:

Per "guess" particle: (x,y,direction) --> particles --> approx. posterior probability representation of position

**"Survival of the fittest 'guess' particles" (with min(real-guess) or max(importance wts)** --> resample (cluster --> cluster) with higher-importance particles being **more likely** (not guaranteed) to be chosen and copied multiple times.


https://github.com/rambo/udacity_CS373

https://github.com/wasbazi/ai-robotics

https://github.com/peebz/ai-for-robotics-notes

https://github.com/ericsu921/CS373-Notes



https://github.com/hchiam/ai_for_robotics



