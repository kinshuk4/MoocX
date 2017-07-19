Now, let's look at the influence of
the noise vector on the position.

For this, I will zoom in to our object.

This is where our object
is at time step k, and

this is where our object is at time step
k+1, without any acceleration noise.

Clearly the yaw acceleration
noise will change the radius of

the circle we are driving.

So the yaw acceleration does have
some influence onto the position.

If the yaw acceleration is positive,
we might end up here.

And if the yaw acceleration is negative,
we might end up here.

However, we assume the effect
of the yaw acceleration on

the position is relatively small
in comparison to other factors.

And we will neglect here.

Now let's look at the effect of
the longitudinal acceleration noise

on the position.

The exact calculation is
pretty straightforward.

You have to go back to the differential
equation where we started.

But this time instead of a constant
velocity, you would need to

assume a constant acceleration and
then solve the integral for that case.

This is absolutely doable, and
you are welcome to give it a try.

But it takes some time.

I want to speed things up a little bit,
so

we can go into the unscented
Kalman filter sooner.

So I suggest using an approximation
here, which is really easy, but

it's still pretty close
to the exact solution.

What we will use as an approximation
is the acceleration offset of a car

driving exactly straight, like here.

This approximation will be okay as
long as the yaw rate is not too high.

Can you tell me in the next quiz,
what the acceleration offset in Px and

Py would be if the car
drove perfectly straight?
