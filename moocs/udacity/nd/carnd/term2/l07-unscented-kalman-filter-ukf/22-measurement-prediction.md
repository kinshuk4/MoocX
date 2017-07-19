You have successfully predicted
the state mean and covariance matrix.
Terrific, this means we can go
into the update step now and
predict the measurement.
This is where we are now.
We have successfully predicted
the state from times step k
to times k step plus one.
Now we have to transform the predicted
state into the measurement space.
The function that defines this
transformation is the measurement model.
Of course, now we have to consider what
kind of sensor produced the current
measurement and
use the corresponding measurement model.
The problem we have here is very similar
to the problem we had
in the prediction step.
We need to transform a distribution
through a non linear function so
we can apply exactly the same on
center transformation approach
as we did before during
the state prediction.
However, we can take two shortcuts
here and make it a little easier.
Let me show you how.
The first thing we did in the prediction
step was generating [INAUDIBLE] points.
We could do the same here again
using the predicted mean and
covariance matrix.
However, a popular shortcut is
to just reuse the signal points we
already have from the prediction step.
So we can skip generating
sigma points this time.
In this very case,
we can also skip the augmentation step.
Why is that?
We needed the augmentation
because the process noise
had a non linear effect on the state.
Let's assume we're talking about
a greater measurement in this example.
Here the measurement model was
a non linear fraction, but
the measurement noise had
a purely additive effect.
In this case we don't need
to perform an augmentation,
there is an easier way to
consider the measurement noise.
I will show you later how this works.
So the only thing that's left for
us to do is actually transforming
the individual sigma points we already
have into the measurement space and
using them to calculate the mean and
the covariance matrix S of
the predicted measurement.
Again, we want to store the transformed
measurement signal points
as columns in a matrix.
I call this matrix colorgraphic Z.
Remember the measurement
space of the rater.
This was the radial distance rho,
the angle phi, and
the radial velocity rho dot, so
it is a three dimensional space.
This means the matrix with the
measurement sigma points has three rows
and 15 columns in our case.
Regarding for
the measurement noise omega,
just put in 0 here, since we will
account for the measurement noise later.
Now that we have the sigma points and
the measurement space, We want to
calculate the resulting mean and
covariance of the predicted measurement.
This is also very similar as before and
given by these equations.
The only thing that comes
in addition here is that we
add the measurement covariance noise.
This is how we account for
the measurement noise.
Remember, we can do this instead
of the augmentation here,
because the measurement noise
in our case does not have
a non-linear effect on the measurement
but it's purely additive.
And that's how you predict
the measurement mean and covariance.
It's exactly the same
problem set as before, but
by using the two sharp cards I just
introduced, it is a very easy step.
After this step you have made it almost
through the UKF processing chain.
