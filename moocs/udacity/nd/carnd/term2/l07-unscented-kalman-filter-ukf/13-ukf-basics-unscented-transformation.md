The end center transformation solves
this problem using sigma points.

What are these sigma points,
and how do they help us?

It can be difficult to transform
the whole state distribution through

a nonlinear function.

But it is very easy to transform
individual points of the state space

through the non-linear function, and
this is what sigma points are for.

The sigma points are chosen around the
mean state and in a certain relation to

the standard deviation signal
of every state dimension.

This is why these points
are called sigma points.

This serve as representatives
of the whole distribution.

Once you have chosen the sigma points,

you just insert every single sigma
point into the non and your function f.

So they will come out somewhere
here in the predictive state space.

All you have to do now is
calculate the mean and

the covariance of your
group of sigma points.

This will now provide the same mean and

covariance as the real
predicted distribution.

But in many cases,
it gives a useful approximation.

By the way, you can apply the same
technique in the linear case.

In this case, you will even find
the exact solution of the sigma points.

So if the process more or
less linear, the sigma point approach

provides exactly the same solution
as the standard common feature.

But you would not use
sigma points here because

they are more expensive in
terms of calculation time.

So let's wrap this up.

This is what you really have because
of your non linear process model.

This is the best possible mean and
covariance you could use

if you want to keep pretending
you have a normal distribution.

And this is what the sigma
points give you.

Now you know the general idea
of an unscented transformation.

Time to get started with
the unscented comment filter.
