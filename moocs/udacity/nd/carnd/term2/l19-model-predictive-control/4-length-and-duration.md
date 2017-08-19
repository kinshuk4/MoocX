# Length & Duration

The prediction horizon is the duration over which future predictions are made. We’ll refer to this as **T**.

**T** is the product of two other variables, **N** and **dt**

**N** is the number of timesteps in the horizon. **dt** is how much time elapses between actuations. For example, if **N** were 20 and **dt** were 0.5, then **T** would be 10 seconds.

**N**, **dt**, and **T** are hyperparameters you will need to tune for each model predictive controller you build. However, there are some general guidelines. **T** should be as large as possible, while **dt** should be as small as possible.

These guidelines create tradeoffs

### Horizon

In the case of driving a car, **T** should be a few seconds, at most. Beyond that horizon, the environment will change enough that it won't make sense to predict any further into the future.

### Number of Timesteps

The goal of Model Predictive Control is to optimize the control inputs: **delta**, **a**. An optimizer will tune these inputs until a low cost vector of control inputs is found. The length of this vector is determined by **N**:

```
[delta_1, a_1, delta_2, a_2, ..., delta_n-1, a_n-1]
```

Thus **N** determines the number of variables optimized by the MPC. This is also a major driver of computational cost.

### Timestep Duration

MPC attempts to approximate a continues reference trajectory by means of discrete paths between actuations. Larger values of **dt** result in less frequent actuations, which makes it harder to accurately approximate a continuous reference trajectory. This is sometimes called "discretization error".

A good approach to setting **N**, **dt**, and **T** is to first determine a reasonable range for **T** and then tune **dt** and **N** appropriately, keeping the effect of each in mind.

![alt tag](imgs/lengthAndDuration.png)

The blue line is the reference trajectory and the red line the trajectory computed by Model Predictive Control. In this example the horizon has 7 steps, **N**, and the space in between white pebbles signifies the time elapsed, **dt**.
