What we will do in the remaining part of
this lesson is go through the processing

chain of the unscented
Kalman filter step by step.

We will start with an example state
vector x and covariance matrix p and

go all the way through prediction and

measurement step until we reach
the updated state and covariance matrix.

For every step, you will implement
a C++ function right away.

So you will be well prepared for
the project in the end.

As always, we will of course
start with the prediction step.

As you've seen before, we can split the
unscented prediction into three parts.

First, we need to know a good
way to choose sigma points.

Second, we need to know how
to predict the sigma points.

Well, that's easy, just insert
them into the process function.

And third, you need to know how to
calculate the prediction, mean, and

covariance from
the predicted sigma points.

Let's start with the first step.

How do we choose the sigma points?
