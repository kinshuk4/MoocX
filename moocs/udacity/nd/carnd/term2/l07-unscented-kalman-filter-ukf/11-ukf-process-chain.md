What you have derived for
the CTR remodel, is a process model that

considers the possibility to drive
on a straight line, or doing a turn.

That's really great because it will
help us to achieve good results

when tracking a bike
in the project later.

Now let's go to the next part of this
lesson, and see how the UKF works.

The way the incentive common filter
processes measurements over time is

exactly the same as the extended
common filter does.

We have a prediction step, which is
independent of the measurement model.

In this step, you will use
the CTR model we just arrived.

Then we have an update step where we
use the radar measurement model, or

the laser measurement model,

depending on the type of
measurement we just received.

This means you'll be able to apply
the same top level processing chain for

the incentive common filter as you
use for the extended common filter.

The difference between the incentive and
the extended common filters is how

the incentive common filter deals
with the non linear process models or

measurement models.

For that, the incentive common filter
uses an approach called incentive

transformation.

Let me show what this is.
