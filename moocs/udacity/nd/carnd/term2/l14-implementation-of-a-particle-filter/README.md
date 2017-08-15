# Implementation of a Particle Filter

### Pseudocode

![img](../images/14.1.png)

- 1: Initialisation
- 3: Prediction step (add control input)
- 4-5: Update step
- 7-10: Resampling step
- 11: Bayes Filter Posterior

Flowchart:![img](../images/14.2.png)

### Initialisation

- Number of sampling points
  - Want enough to cover the possible positions.
  - Too many particles slow down your filter (need to localise in real time).
- Ways of sampling
  - Uniformly (not practical if state space is too large, e.g. entire surface of the earth).
  - Sample around some initial estimate, e.g. GPS.
    - Gaussian