# Kalman Filters

Sensors:

- Spinning Laser range finder: takes distance scans 10x / second, about 1M data points (each time). -> spot cars so you don't run into them.
- Camera on top.
- Stereo camera system
- Antennas for GPS at rear to estimate where car is in the world.

### Tracking using Kalman Filters

- Similar to Monte Carlo localisation, except it's
  - continuous (as opposed to divided into discrete grids)
  - uni-model (as opposed to multi-modal)
- Kalman filter estimates future locations based on previous locational datapoints (even if they're noisy).

### Gaussian

- 1-D Gaussian N(mu, sigma^2) -> only need to estimate two parameters.
  - mu is the mean
  - sigma^2 is the variance: measure of uncertainty
- Facts:
  - Continuous distribution, vs Monte Carlo localisation where distribution estimated by a histogram.
  - Area under the Gaussian sums to 1.
  - Exponential of a quadratic function

We prefer low-variance Gaussians for locating cars.

```
# Program the Gaussian
from math import *

def f(mu, sigma2, x):
    return 1/sqrt(2.*pi*sigma2) * exp(-.5 * (x-mu)**2 / sigma2)

print (f(10., 4., 8.))

```

```
0.12098536225957168

```

### Kalman Filter

The Kalman Filter represents our distributions by guassians and iterates on two main cycles. Iterates two things, as with localisation:

1. Measurement updates
   - [requires product](https://classroom.udacity.com/courses/cs373/lessons/48739381/concepts/487235990923#) - By updating belief by a multiplicative factor (multiplying the Gaussians)
   - Uses [Bayes Rule](https://classroom.udacity.com/courses/cs373/lessons/48739381/concepts/487221690923#)
2. Prediction (Motion updates in localisation)
   - By performing a convolution (addition)
   - Uses [total Probability](https://classroom.udacity.com/courses/cs373/lessons/48739381/concepts/486736290923#) to keep track of where all of our probability 'goes' when we move

#### 1. Measurement cycle

- Mean:
  - The lower the variance of our new measurement, the more weight we give it (pull our prior mean towards the measurement mean).
- Variance:
  - More measurements -> greater certainty (lower variance). New Gaussian has lower variance than either the prior or the measurement Gaussian.
  - Unaffected by means

In [4]:

```
# Write a program to update your mean and variance
# when given the mean and variance of your belief
# and the mean and variance of your measurement.
# This program will update the parameters of your
# belief function.

def update(mean1, var1, mean2, var2):
    new_mean = (mean1 * var2 + mean2 * var1)/(var1 + var2)
    new_var = 1/ (1/var1 + 1/var2)
    return [new_mean, new_var]

print(update(10.,8.,13., 2.))

```

```
[12.4, 1.6]

```

#### 2. Motion Update (Prediction)

- Suppose you move to the right by a certain distance. Your movement has some uncertainty, so variance increases.

In [7]:

```
# Write a program that will iteratively update and
# predict based on the location measurements 
# and inferred motions shown below. 

def update(mean1, var1, mean2, var2):
    new_mean = float(var2 * mean1 + var1 * mean2) / (var1 + var2)
    new_var = 1./(1./var1 + 1./var2)
    return [new_mean, new_var]

def predict(mean1, var1, mean2, var2):
    new_mean = mean1 + mean2
    new_var = var1 + var2
    return [new_mean, new_var]

measurements = [5., 6., 7., 9., 10.]
motion = [1., 1., 2., 1., 1.]
measurement_sig = 4.
motion_sig = 2.
mu = 0.
sig = 10000.

#Please print out ONLY the final values of the mean
#and the variance in a list [mu, sig]. 

# Insert code here
for i in range(len(measurements)):
    mu, sig = update(mu, sig, measurements[i], measurement_sig)
    print('Update: ', [mu, sig])
    mu, sig = predict(mu, sig, motion[i], motion_sig)
    print('Predict: ', [mu, sig])
print([mu, sig])

```

```
Update:  [4.998000799680128, 3.9984006397441023]
Predict:  [5.998000799680128, 5.998400639744102]
Update:  [5.999200191953932, 2.399744061425258]
Predict:  [6.999200191953932, 4.399744061425258]
Update:  [6.999619127420922, 2.0951800575117594]
Predict:  [8.999619127420921, 4.09518005751176]
Update:  [8.999811802788143, 2.0235152416216957]
Predict:  [9.999811802788143, 4.023515241621696]
Update:  [9.999906177177365, 2.0058615808441944]
Predict:  [10.999906177177365, 4.005861580844194]
[10.999906177177365, 4.005861580844194]

```

It is unexpected that the code is so simple for a Kalman filter in 1D.

### Multi-dimensional Kalman Filter

- Implicitly figures out velocity from seeing multiple positions, and from that makes predictions about future location.

#### Multivariate Gaussians

E.g. contour lines of a 2D Gaussian: Tilted Gaussian (not parallel or perpendicular to x or y axes): x and y correlated.

Build 2-dimensional estimate: one for location, one for velocity.

If we project the new 2D Gaussian into the space of velocity or x, we can't predict the velocity or the location. But this Gaussian expresses that velocity is correlated to location.

- img

Now we fold in the second observation (green)

and we can have our new predicted Gaussians (purple / blue Gaussians on the red Gaussian)

- img Subsequent observables give us information about the hidden variables, so we can estimate hidden variables.

#### Designing Kalman Filters

- State transition function
- Measurement function

(img)

Kalman Filtetr equations (don't need to know for this course)

(img)

K: Kalman gain Final lines in red: update

In [ ]:

```
# Multidimensional Kalman Filter

```

#### Looking ahead

Particle Filters

- Easy to implement
- Powerful