We start at the beginning of the UKF
processing chain, which means

we are on the prediction step, and
we want to generate sigma points.

At the beginning of the prediction step,
we have the posterior state xkk and

the posterior covariance matrix
Pkk from the last iteration.

They represent the distribution
of our current state.

And for this distribution we now
want to generate sigma points.

The number of sigma points
depends on the state dimension.

Remember this is the state
vector of the CTRV model so

the dimension of our state is nx = 5.

We will choose 2nx + 1 sigma points.

The first point is
the mean of the state.

And then we have another two
points per state dimension,

which will be spread in
different directions.

In our case,
this would be 11 sigma points.

To make things easier right now, we
will consider only two dimensions of our

state vector, the position Px,
and the position Py.

This makes it easier to
visualize what's happening.

Now that our state dimension is two, we
are only looking for five sigma points.

Together with you, I want to
calculate these five sigma points and

store them in this matrix,
which I write as calligraphic X.

Every column of this matrix
represents one sigma point.

Let's bring this example to life and

put some realistic values into the mean
state and the covariance matrix.

Just as you will have it later
in the real application.

Now if you Google for
Unscented Kalman filter and look for

a rule how to generate sigma points,
this is what you will find.

This looks awful.

Maybe this is why people don't use
the Unscented Kalman filter more often.

The good news is, this rule is
actually pretty simple to apply.

The first thing you need to
know is what this lambda is,

this is a design parameter.

You can choose where in
relation to the arrow

ellipses you want to
put your sigma points.

I will show you, later,
how this effect works.

Some people report good
results with lambda = 3- nx.

Another thing you might be wondering
is what is the square root of a matrix?

More specifically, we are looking for

the matrix A which fulfills
A transposed times A = P.

There are several algorithms available
that will calculate such a matrix.

Later, I will show you
how to do this in C++.

But here, I'll give you the solution for
the square root matrix so

we can continue.

And this is actually all we need
to calculate the sigma points.

So how do we read this rule?

The first column of the matrix tells
us what the first sigma point is.

And this is always easy,

because the first sigma point is
simply the mean state estimate.

This term contains two
more sigma points.

In the square root matrix,
we have two vectors.

The first vector points
into this direction, and

the other vector points
into this direction.

And we multiply these vectors
by this printing factor.

Now, you can see what the design
parameter lambda does.

If lambda is larger, the sigma points
move further away from the mean state.

If lambda is smaller, the sigma
points move closer to the mean state.

This all happens in relation
to this arrow ellipse.

In our case, the resulting
sigma points will end up here.

In our example, the overall spreading
factor works out to square root of 3.

So we multiply this vector and
this vector by

the square root of 3, and
add it to the mean state.

Finally, here we use
the same vectors again, but

we apply them in the opposite direction.

This means that these sigma points
will show up here, and here.

In the next quiz I would like you to
calculate the two remaining sigma points

from our example.
