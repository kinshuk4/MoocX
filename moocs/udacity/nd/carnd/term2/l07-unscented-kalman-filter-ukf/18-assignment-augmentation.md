Okay, let's put
the augmentation into C++ code.

Now the dimension of
the augmented state is 7.

Other things you will need
are the variance of the longitudinal

acceleration and the yaw acceleration.

Together, they build the process noise
covariance matrix which we call Q.

The rest is almost the same
as in the last person.

But this time, you have to build
the augmented state mean x_aug, and

the augmented covariance matrix P_aug.

Again, have a look at the cheat sheet,
it will also help you here.

When you build the augmented mean state,

consider that the mean value of
the acceleration noises are both zero.

And this is where
the augmented sigma points go.

Pay attention to
the dimensions of this matrix.

Good luck with the quiz.
