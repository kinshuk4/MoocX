# Dealing With Stopping

If the goal is to move the vehicle from A to B then coming to a halt in the middle of the reference trajectory is a problem. A simple solution is a set of reference velocity: 

```
const += pow(v[t] - referenceVelocity, 2)
```

The cost function will penalize the vehicle for not maintaining that reference velocity. Another option is to measure the Euclidean distance between the current position of the vehicle and the destination, then add that distance to the cost.
