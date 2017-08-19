# Model Predictive Control

Model predictive control reframes the task of following a trajectory as an optimization problem. The solution to the optimization problem is the optimal trajectory. Model predictive control involves simulating different actuator inputs, predicting the resulting trajectory, and selecting the trajectory with a minimum cost.

For example: Imagine that we know our current state and the reference trajectory we want to follow, we optimize our actuator inputs at each step in time in order to minimize the cost of our predicted trajectory. Once we found the lowest cost trajectory we implement the very first set of actuation commands. Then we throw away the rest of the trajectory we calculated. Instead of using the old trajectory we predicted, we take our new state and use that to calculate a optimal trajectory. In that sense we are constantly calculating inputs over future horizon. That is why this approach is sometimes called receding horizon control.

Why don't we just carry out the entire trajectory we calculated during the first pass. The reason is that our model is only approximate. Despite our best efforts, it won't match the real world exactly. Once we perform our actuation commands, our trajectory might not be exactly the same as the trajectory we predicted. So it is crucial that we constantly reevaluated to find the optimal actuations.
