# Unscented Kalman Filters

- Use sigma points to approximate Prob dist to deal with non-linear process and measurement functions
  - Often better approximation than EKF
  - Don't need to calculate Jacobian

### Lesson outline:

- More sophisticated process (model) that can estimate the turn rate of a vehicle
- Go through how the Unscented Kalman Filter deals with this non-linear process model
- Implement the UKF in C++.

## CTRV model (Constant Turn Rate and Velocity Magnitude Model)

- Relaxes assumption of vehicle's constant velocity
  - When vehicles are turning, they move slower. So EKF's predicted position would be outside of the circular path of the car.

![img](../images/7-3.png)

- Describe velocity using Speed (v) and yaw (psi) angle
  - Vehicle moving in a straight line has a yaw rate (psi dot) of zero.
- Prediction: find f, where ![$x_(k+1) = f(x_k, v_k)$](https://render.githubusercontent.com/render/math?math=x_%28k%2B1%29%20%3D%20f%28x_k%2C%20v_k%29&mode=inline).
- Approach: Describe the change rates of the state x.
- Assumes constant turn rate (yaw dot) and constant velocity.

![img](../images/7-6.png)![img](../images/7-7.png)

### Process noise

![img](../images/7-8.png)

Uncertainty nu_k Two noise processes:

- v: Longitudinal acceleration noise, changes at each timestep k
- yaw

How does the noise vector influence the process model?

Sign of yaw acceleration noise:![img](../images/7-9.png)![img](../images/7-10.png)![img](../images/7-11.png)![img](../images/7-12.png)![img](../images/7-13.png)![img](../images/7-14.png)

## Unscented Kalman Filter

- Predict and update steps as before

### Unscented transformation

- Recap: issue with nonlinear process or measurement models is that predicted state distribution is usually not normal.
  - UKF goes on with normal distribution with same mean and variance as predicted distribution (an approximation
- Solve using sigma points (representatives of the whole distribution)
  - Transform individual points of the state space thorugh the non-linear function.
  - Calculate mean and variance of the sigma points.

Steps:

- Choose sigma points
- Predict sigma points (insert into process function)
- Calculate mean and variance

![img](../images/7-15.png)

- Posterior state x_k|k, posterior covariance matrix Pk|k.
- Consider two dimensions of the state first (px, py).

Sigma points

- Mean of state
- Two points per state dimension.

![img](../images/7-16.png)

- Lambda (choose): distance of state dim sigma points from the mean state
- Square root of matrix Pk|k: directions of the sigma points relative to the mean.
- xk|k: first sigma point (mean)
- first vector points

![img](../images/7-17.png)![img](../images/7-18.png)![img](../images/7-19.png)

#### Generate Sigma Points