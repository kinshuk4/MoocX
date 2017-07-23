## UKF Basics Unscented Transformation

The end center transformation solves this problem using sigma points. 

What are these sigma points, and how do they help us? It can be difficult to transform the whole state distribution through a nonlinear function. But it is very easy to transform individual points of the state space through the non-linear function, and this is what sigma points are for. 

The sigma points are chosen around the mean state and in a certain relation to the standard deviation signal of every state dimension. This is why these points are called sigma points. 

![img](../images/7-14.png)

This serve as representatives of the whole distribution. Once you have chosen the sigma points, you just insert every single sigma point into the non linear function f. So they will come out somewhere here in the predictive state space. 

All you have to do now is calculate the mean and the covariance of your group of sigma points. This will not provide the same mean and covariance as the real predicted distribution. But in many cases, it gives a useful approximation. By the way, you can apply the same technique in the linear case. 

In this case, you will even find the exact solution of the sigma points. So if the process more or less linear, the sigma point approach provides exactly the same solution as the standard common feature. But you would not use sigma points here because they are more expensive in terms of calculation time. So let's wrap this up. 

This is what you really have because of your non linear process model. This is the best possible mean and covariance you could use if you want to keep pretending you have a normal distribution. And this is what the sigma points give you. Now you know the general idea of an unscented transformation. Time to get started with the unscented comment filter. 

### Processing chain of UKF

Lets start with state vector x and covariance matrix p. We will then go through prediction and measurement step, until we reach the updated state and covariance matrix. We will start with prediction.

To sum up:

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

