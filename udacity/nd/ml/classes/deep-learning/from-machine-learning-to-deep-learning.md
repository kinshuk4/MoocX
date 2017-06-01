# From Machine Learning to Deep Learning

## Deep Learning: Introduction

**Pedestrian Detection Example**

- We can use a binary classifier of pedestrian and no pedestrian.
- Then we slide a "window" across all possible locations in the image to detect if there is a pedestrian.

**Web Search Ranking Example**

- Take pair of query and webpage.
- We then classify as relevant or not relevant.

### Training your Logistic Classifier

A Logistic Classifier is a function that outputs scores for a given input. It is trained by adapting the weights and the bias.

![Logistic Classifier](images/from-ml-to-dl/logistic-classifier.png)

- Wx + b = Y
- We take inputs as a vector x, and multiply it by the weights matrix W, the we add the bias vector b to produce y, output which is a matrix/vector of scores.

When we train a classifier, we want to output probabilities, instead of scores. This probabilities will be checked against a threshold so that we have a binary classification.

![Probabilities Classifier](images/from-ml-to-dl/probabilities-classifier.png)

To transform scores into probabilities we can use a softmax function as described below.

**Softmax function**

- We can turn scores (logits) into probabilities using a softmax function.

  - The probabilities will all sum to 1.
  - Probability will be low if it's a high score.
  - Probability will be high if it's a low score.

- ​

![Softmax Function](images/from-ml-to-dl/softmax.png)

```python
"""Softmax Function"""
# Your softmax(x) function should 
# return a NumPy array of the same shape as x.

scores = [3.0, 1.0, 0.2]

import numpy as np

def softmax(x):
    """Compute softmax values for each sets of scores in x."""
    # TODO: Compute and return softmax(x)
    # We sum across the row.
    soft_max = np.exp(x) / np.sum(np.exp(x), axis=0)
    return soft_max


# Probabilities should sum to 1.
print(softmax(scores))

# Plot softmax curves
import matplotlib.pyplot as plt
x = np.arange(-2.0, 6.0, 0.1)
scores = np.vstack([x, np.ones_like(x), 0.2 * np.ones_like(x)])

plt.plot(x, softmax(scores).T, linewidth=2)
plt.show()
```

Output:

```
[ 0.8360188   0.11314284  0.05083836]
```

![softmax_output](./images/from-ml-to-dl/softmax_output.png)

```
# Multiply scores by 10.
scores = np.array([3.0, 1.0, 0.2])
print(softmax(scores * 10))

```

```
[  9.99999998e-01   2.06115362e-09   6.91440009e-13]

```

Probabilities get close to either 1.0 or 0.0.

In [5]:

```
# Divide scores by 10.
scores = np.array([3.0, 1.0, 0.2])
print(softmax(scores / 10))

[ 0.38842275  0.31801365  0.2935636 ]

```

- Probabilities get cose to the uniform distribution because since all the scores decrease in magnitude, the resulting softmax probabilities will be closer to each other.
- We want to be confident over time from this to the above with more data.

![lr](./images/from-ml-to-dl/lr.png)



## Cross Entropy

**One-Hot Encoding**

- We want the output to be 1 or 0. This is basically one-hot encoding.

![One Hot Encoding Problem](images/from-ml-to-dl/cross-entropy-intro.png)

![Cross Entropy Definition](images/from-ml-to-dl/cross-entropy-definition.png)

![Multinomial Logistic Classification Recap](images/from-ml-to-dl/multinomial-logistic-classification-recap.png)

![Multinomial  Logistic Classification](images/from-ml-to-dl/multinomial-logistic-classification.png)

## Minimizing Cross Entropy

We want to find the `W` and `b` that provide a high distance to the incorrect class while having a low distance to the correct class.

![Intro](images/from-ml-to-dl/minimizing-cross-entropy-intro.png)

For this, we define something called `loss function`:

![Loss Function](images/from-ml-to-dl/loss-function-cross-entropy.png)

This loss function is the average cross-entropy of all our data, so it is a big expensive operation. To understand how to minimize this function, suppose we could represent it in terms of 2 weights (W and b):

![Loss Function Representation](images/from-ml-to-dl/loss-function-representation.png)

**Training loss**

- How do we find weights w and bias b to have low distance for correct class and high distance for incorrect class.
- We can do this using a training loss function.
  - We want to minimize the following loss function.
    ![loss_func](./images/from-ml-to-dl/loss_func.svg)
- Minimize loss graph based on two weights by taking small steps called gradient descent.We take the derivative of the loss with respect to our parameters

Now we turned our machine learning problem into a numerical optimization problem. We have to find the 2 weights that provide the minimal loss function. And we can do this using gradient descent.

![Gradient descent solution](images/from-ml-to-dl/gradient-descent-solver.png)

## Normalized Inputs And Initial Weights

When handling big data, we have to be aware of rounding problems, while handling very big and small numbers simultaneously. As a good practice, we want our variables to have zero mean and equal variance, whenever possible.

**Numerical Stability**

- Adding very small values to a large value would introduce problems.

- We would always want the following:

  1. **Well conditioned**
     Mean Xi = 0, Variance sigma(Xi) = sigma(Xj)
     ![Zero mean](images/from-ml-to-dl/zero-mean.png)

     1. - This well-conditioned variables make it easier for optimizers to find a solution for the problem.

          ![Well conditioned variables](images/from-ml-to-dl/well-conditioned.png)

        - Optimizer don't have to do a lot of searching

          - We can do this with image.
            - Take pixel value (0 to 255)
            - Subtract 128 and divide by 128.

  2. 1. **Random weight initialization**Draw weights randomly with a Gaussian distribution and standard deviation ![$\sigma$](https://render.githubusercontent.com/render/math?math=%5Csigma&mode=inline)We can start with small ![$\sigma$](https://render.githubusercontent.com/render/math?math=%5Csigma&mode=inline) (uncertain)
        ​

  ​

On images, we can normalize the data by subtracting and dividing each channel by 128. This helps the optimizer to converge on a solution faster.

![Normalizing Images](images/from-ml-to-dl/normalizing-images.png)

**What do we have now?**

The same idea applies to the weight initialization for the logistic function. Using gaussian distributions and small standard deviations, select the weights. Small sigmas represent more uncertainty, while large sigmas, represent more bias.

![Weight initialization](images/from-ml-to-dl/weight-initialization.png)

So now we know all steps for initializing the Logistic Classifier.

![Logistic Classifier initialization](images/from-ml-to-dl/logistic-classifier-initialization.png)

And the optimization steps consist of taking the derivative of the loss function on each weight and updating the weight to its new value, hence and repeat.

![optimization steps](images/from-ml-to-dl/optimization-steps.png)

## Validation Test Set Size Continued

As a rule of the thumb, a change in the model that affects the classification of less than 30 samples can be interpreted as noise. In contrast, a change that affects 30 or more samples, can be interpreted as a gain in accuracy for the model. Because of this, a validation set should have at least 30,000 samples, so that we can measure gains of 0.1% in accuracy.

![Validation Test Set Size](images/from-ml-to-dl/validation-test-size.png)

## Stochastic Gradient Descent

Stochastic Gradient Descent is an alternative to Gradient Descent. It is a trade-off in in which we select a tiny random sample from the dataset to compute the global loss function and the derivative faster and, instead of taking tens of large steps to find the minimum optimal function, we take hunderds to thousands smaller steps.

![Gradient Descent](images/from-ml-to-dl/gd-vs-sgd-1.png)

![Stochastic Gradient Descent](images/from-ml-to-dl/gd-vs-sgd-2.png)

Usually we prefer SGD to GD, because it scales better with model and data size, and we want both on deep learning. The drawback is that SGD have some issues on real-world problems, like the definition of the random sample, and the steps take us into random directions which, sometimes, increase the overall loss.

## Momentum And Learning Rate Decay

To improve SGD results, we need to take a few steps.

First, as stated before, we need to normalize our inputs.

![Helping SGD](images/from-ml-to-dl/helping-sgd.png)

Then we can use `Momentum` and `Learning Rate Decay` to improve convergence.

### Momentum

Momentum is a moving average of the derivatives from the last steps. With momentum, we retain previous knowledge about the direction in which we have to go to get to the minimal loss function. We use momentum instead of the flat value of the derivative on a given step.

![Momentum SGD](images/from-ml-to-dl/momentum-sgd.png)

### Learning Rate Decay

Learning Rate is the fraction of the derivative we take each step. Learning Rate decay is the amount the learning rate drops each step we take. As a general rule of the thumb, lowering the learning rate over time is key to convergence of the model.

![Learning Rate Decay SGD](images/from-ml-to-dl/learning-rate-decay-sgd.png)

## Parameter Hyperspace

Learning Rate tuning can be very strange. A higher learning rate doesn't mean the model learns more or learns faster. That is not true. In general, a lower learning rate generates better models.

![Learning Rate Tuning](images/from-ml-to-dl/learning-rate-tuning.png)

SGD tuning is known as black-magic, because there are many hyperparameters to work with.

![SGD Black Magic](images/from-ml-to-dl/sgd-black-magic.png)

There is a simplification of SGD called ADAGRAD, which works out the learning rate and momentum. Usually it doesn't work so well as a handpicked tuned SGD, but it is much simpler to work with.

![ADAGRAD](images/from-ml-to-dl/adagrad.png)

Although we have seen how to train a Logistic Regression SGD model, it is still a linear model, which might not be the best solution for non-linear problems. Because of this, we need to go deeper into deep learning.

![Recap](images/from-ml-to-dl/recap-deeper.png)

[UPDATE_FROM: https://github.com/ritchieng/machine-learning-nanodegree/blob/master/deep_learning/introduction/Introduction_deep_learning.ipynb]
