In this video I would
like to discuss with you

how to choose noise parameters and
how to evaluate this choice.

This is not only related to
the unscented Kalman filter,

but it can be applied
to any Bayesian filter.

When we talked about our process and
our measurement models,

we introduced several
sources of uncertainty.

In the process model,
we introduced the process noise nu k.

And in the measurement model,

we introduced the measurement
noise omega k.

In case of a radar measurement, this was
the noise of the distance measurement

rho, the angle measurement phi,
and the radial velocity rho dot.

We also quantified the noise with
the process noise covariance matrix and

the measurement noise covariance matrix.

So these are the variances of the noise.

And they quantify how
strong the noise is.

But we haven't discussed where
we get these values from.

Let's start with the measurement noise.

This noise describes how
precise the sensor can measure.

It means we have to look
into the sensor manual and

see how precise the sensor is.

For example, we might read the radar
can measure the distance rho

with the standard
deviation of 0.3 meters.

This means we know the value for
the variance sigma rho squared,

which is 0.09 meters squared.

We also have to assume the noise
is white and normally distributed.

Which is of course not true for
some sensors.

It is a little more difficult to
choose an appropriate process noise.

In reality,

objects in a traffic environment don't
move with wide acceleration noise.

This would mean a driver constantly
switches between gas and brake.

So we really apply a strong
approximation here.

But assuming a wide process noise.

A rule of thumb for
getting useful values is the following.

Try to estimate what
the maximum acceleration is you

expect in your environment.

Let's say we want to track
cars in an urban environment.

Then these cars usually
don't accelerate or

brake stronger than 6
meters per second squared.

Now, the rule of thumb says,

choose half of the maximum acceleration
you expect as process noise.

In this case, we would choose 9 meters
squared over seconds to the power 4 as

variance for the acceleration noise.

If this is a good value for
you really depends on your application.

Is it important for your application
to react fast on changes?

Then you choose the process
noise a little higher.

Is it important to provide
smooth estimations?

Then you choose the process
noise a little lower.

Usually, you want to know of course
if you set up the noise parameters

correctly.

For that purpose, you can run
a consistency check on the filter.

What does consistency mean?

At every time cycle, we calculate the
measurement prediction zk + 1 pipe k and

the covariance matrix
S of this prediction.

Then we receive the actual measurement
zk + 1 for that time step.

In this example, everything looks okay.

The actual measurement occurred
somewhere inside the error ellipse.

But this might look differently.

Imagine you set up a filter, and
your measurement looks like this.

Here, something is wrong.

You constantly underestimate
the uncertainty of

the predicted measurement.

This means your filter
is not consistent.

Of course,
these are all stochastic processes.

And it might just be
a natural coincidence.

But if the results
keep looking like this,

this explanation is extremely unlikely.

This result tells you that you
underestimate the uncertainty

in your system.

Your estimate is less
precise than you think.

The same can happen in
the other direction.

Look at this example.

Here you overestimate
the uncertainty of your system.

It means your estimate is actually
more precise than you think.

So this filter is also inconsistent.

A filter is consistent if it provides
a realistic estimation uncertainty.

It is very easy to check
the consistency of your filter, and

I recommend to always do that
when you design a filter.

An important consistency
check is called NIS,

it means Normalized Innovation Squared.

This is how the NIS is calculated.

Its meaning is very intuitive.

The innovation is the difference
between the predicted measurement and

the actual measurement.

And normalized means you put it into
relation to the covariance matrix S.

That's why you have the inverse
of the matrix S here.

The NIS is just a scalar number and
super easy to calculate.

But you need to know
what number to expect.

For that, you need to know something
about the statistics of this NIS.

The NIS value follows a distribution
which is called chi-squared

distribution.

If you Google chi-squared distribution,
you will find a table like this.

And this table tells you the number
you should expect for your NIS.

Let's find the right number for
our example.

DF means degrees of freedom.

That's the dimension of
our measurement space.

We have a three-dimensional
radar measurement.

So we have three degrees of freedom.

So what do all these numbers mean?

in 95% of all cases,

your NIS will be higher than 0.352.

And this column says in 5% of all cases
your NIS will be higher than 7.815.

Let's remember this number, 7.8.

What you can always do when you
design a filter is plot the 95% line.

In our case, that's 7.8.

And then for every time step k,
calculate and plot also the NIS value.

If you see something like this,
then everything looks great.

Sometimes you are over the 95% line,
but that's what we expect.

If you see something like this, it means
you underestimate the uncertainty in

your system,
like in the example we saw before.

If you see something like this,

it means you overestimate
the uncertainty in your system.

Your estimations are actually
more precise than you think.

Unfortunately, the NIS test does not
tell you where the mistake comes from,

but it gives you at least some feedback.

In this case, for example, it might be
a good idea to reduce the process noise

a little bit and try again.

In the project, at the end of this
lesson, take a look at your NIS and

use it to check your
process noise settings.
