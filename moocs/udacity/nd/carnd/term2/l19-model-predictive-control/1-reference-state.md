# Reference State

A good start to the cost function is to think of the error that you would like to minimize. For example, measuring the offset from the center of the lane, where the center of the lane can be called the reference, or desired, state.

We previously captured two errors in our state vector: **cte** and **e_ψ**

Ideally, both of these errors would be 0 - there would be no difference from the actual vehicle position and heading to the desired position and heading.

Our cost should be a function of how far these errors are from 0.

Here’s one such example of how to increment our cost at each timestep:

```c++
double cost = 0;
for (int t = 0; t < N; t++) {
    cost += pow(cte[t], 2);
    cost += pow(epsi[t], 2);
}
```

#### What problem might occur if this is the final version of our cost function?

The vehicle might stop on the way from A to B. Also, the vehicle might behave erratically.

If the vehicle comes to an idealized state in the middle of the trajectory, meaning the cost is 0, there’s no incentive to keep going. As far the model is concerned it’s an at optimum.
