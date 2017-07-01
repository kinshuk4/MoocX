# Deep Learning week 1 notes

### Books to read

- [Deep Learning](http://www.deeplearningbook.org/)
- [Neural Networks and Deep Learning](http://neuralnetworksanddeeplearning.com/)
- [Grokking Deep Learning](https://www.manning.com/books/grokking-deep-learning)

### Python lib tutorials

- [Pandas](http://pandas.pydata.org/pandas-docs/stable/10min.html#min)
- [SciKit-learn](http://scikit-learn.org/stable/tutorial/basic/tutorial.html)
- [Pyplot](http://matplotlib.org/users/pyplot_tutorial.html)

## Linear regression

**Linear regression** is a method of finding best fitting values *m* and *b* for a line (*y = mx + b*), which passes through the points of a given array with a minimal mean error. Linear regression uses **gradient descent** to closesly approach the local minimum, by computing a partial derivative for each *m* and *b* along the way. As the line approaches the minimal error, derivatives decrease as well as iteration step:

```
new_b = b_current - (learningRate * b_gradient)
new_m = m_current - (learningRate * m_gradient)
```

The learning rate and number of iterations is chosen manualy. Wrong learning rate, too big or too small, decreases the speed of reaching the vertex. If the learning rate is too small, low derivative will slow the descent down drastically, so the values may not reach the lower point at all. On the other hand, if the learning rate is too big, iterations will reach some level in the lower part quickly, by jumping from slope to slope in zig-zags, but they might not precisely reach the bottom of the curve in any reasonable time/number of iterations.

[Code](https://github.com/llSourcell/linear_regression_live) from Siraj [live video](https://www.youtube.com/watch?v=uwwWVAgJBcM).

## Neural networks

**Neural network** - a network of neurons, which convert input data to output data. A **neuron** or **perceptrons** resemble a body, dendrites (inputs) and an axon (output). As signals enter the neuron through inputs, they are multiplied by **weights** - measure of how much each signal means to the output signal. Then, weighted signals are summed. The resulting signal is feeded through the **activation function**, usually a **sigmoid**, in order to normalize the output.

```
def sigmoid(x):
    return 1 / (1 + np.exp(-x))

output = sigmoid(dot(weights, inputs) + bias)
```

### Learning

Learning of the neural network is done by adjusting the weights by gradient descent of the error - difference between the target output and the neural network output.

```
def sigmoid_derivative(x):
    return sigmoid(x) * (1 - sigmoid(x))

error = target - output
delta_weights = learnrate * error * sigmoid_derivative(dot(weights, inputs)) * input
weights =+ delta_weights
```

Starting weights are chosen randomly, due to this, weight can get into a local minima. One of the method to avoid this - [momentum](http://sebastianruder.com/optimizing-gradient-descent/index.html#momentum).

### Backpropagation

As the neural network will grow to have 1 or more **hidden layers**, learning will become impossible with only gradient descent. To calculate the error we need to know the desired output on the neuron, and we don't have that in hidden layers. Here comes **backpropagation** - extension of gradient descent method, that bases on chain rule, where we estimate the error of the hidden layer basing on the weights, that connect them to the output.

```
delta_error_hidden = dot(weights_hidden_output, delta_error_output) * sigmoid_derivative(dot(inputs, weights_input_hidden))
```

This rule is more general, thus, neural networks with any number of hidden layers can be easily trained:

```
delta_error_hidden1 = dot(weights_hidden1_hidden2, delta_error_hidden2) * sigmoid_derivative(hidden1_input)
```

It is important to save each step of forward run, to use intermediate values (e.g. `hidden1_input`) in the backpropagation procedure.

### TensorFlow

Ran operations in tf.Session. Created a constant tensor with tf.constant(). Used tf.placeholder() and feed_dict to get input. Applied the tf.add(), tf.subtract(), tf.multiply(), and tf.divide() functions using numeric data. Learned about casting between types with tf.cast()

### Convolutional networks

**Convolutional networks** or CovNets - neural networks that share their parameters across space.

- [http://ufldl.stanford.edu/tutorial/supervised/ConvolutionalNeuralNetwork/](http://ufldl.stanford.edu/tutorial/supervised/ConvolutionalNeuralNetwork/)
- [https://adeshpande3.github.io/adeshpande3.github.io/A-Beginner's-Guide-To-Understanding-Convolutional-Neural-Networks/](https://adeshpande3.github.io/adeshpande3.github.io/A-Beginner's-Guide-To-Understanding-Convolutional-Neural-Networks/)
- [http://cs231n.github.io/convolutional-networks/](http://cs231n.github.io/convolutional-networks/)
- [http://deeplearning.net/tutorial/lenet.html](http://deeplearning.net/tutorial/lenet.html)
- [https://ujjwalkarn.me/2016/08/11/intuitive-explanation-convnets/](https://ujjwalkarn.me/2016/08/11/intuitive-explanation-convnets/)
- [http://neuralnetworksanddeeplearning.com/chap6.html](http://neuralnetworksanddeeplearning.com/chap6.html)
- [http://xrds.acm.org/blog/2016/06/convolutional-neural-networks-cnns-illustrated-explanation/](http://xrds.acm.org/blog/2016/06/convolutional-neural-networks-cnns-illustrated-explanation/)
- [http://andrew.gibiansky.com/blog/machine-learning/convolutional-neural-networks/](http://andrew.gibiansky.com/blog/machine-learning/convolutional-neural-networks/)
- [https://medium.com/@ageitgey/machine-learning-is-fun-part-3-deep-learning-and-convolutional-neural-networks-f40359318721#.l6i57z8f2](https://medium.com/@ageitgey/machine-learning-is-fun-part-3-deep-learning-and-convolutional-neural-networks-f40359318721#.l6i57z8f2)

## Supporting materials

### Gradient

- [Khan academy's Gradient and directional derivatives course](https://www.khanacademy.org/math/multivariable-calculus/multivariable-derivatives/gradient-and-directional-derivatives/v/gradient)
- [Khan academy's Chain rule course](https://www.khanacademy.org/math/multivariable-calculus/multivariable-derivatives/gradient-and-directional-derivatives/v/gradient)
- [An overview of gradient descent optimization algorithms](http://sebastianruder.com/optimizing-gradient-descent/index.html#momentum)

### Matrices

- [Khan academy's Matrices course](https://www.khanacademy.org/math/precalculus/precalc-matrices)
- [Khan academy's Vectors course](https://www.khanacademy.org/math/linear-algebra/vectors-and-spaces/vectors/v/vector-introduction-linear-algebra)

### Backpropagation

- [Andrej Karpathy's Yes you should understand backprop](https://medium.com/@karpathy/yes-you-should-understand-backprop-e2f06eab496b#.hzcjgsrsr)
- [Andrej Karpathy's Backpropagation](https://www.youtube.com/watch?v=59Hbtz7XgjM)

### History of AI

- [AI, Deep Learning, and Machine Learning: A Primer](https://vimeo.com/170189199)

### RNN and LSTM Networks

- [Understanding LSTM Networks](http://colah.github.io/posts/2015-08-Understanding-LSTMs/)
- [CS231n Lecture 10 - Recurrent Neural Networks, Image Captioning, LSTM](https://www.youtube.com/watch?v=iX5V1WpxxkY)
- [Deep Learning Book](http://www.deeplearningbook.org/contents/rnn.html)

### Math Cheatsheets

- [Linear Algebra cheatsheet](http://www.souravsengupta.com/cds2016/lectures/Savov_Notes.pdf)
- [Calculus cheatsheet](http://tutorial.math.lamar.edu/pdf/Calculus_Cheat_Sheet_All.pdf)
- [Statistics cheatsheet](http://web.mit.edu/~csvoss/Public/usabo/stats_handout.pdf)

### Convolutional networks

- [Andrej Karpathy's Convolutional Neural Networks](http://cs231n.github.io/)

### Sequence to Sequence RNNs

- [DEEP LEARNING FOR CHATBOTS, PART 1 – INTRODUCTION](http://www.wildml.com/2016/04/deep-learning-for-chatbots-part-1-introduction/)
- [Sequence to Sequence Deep Learning (Quoc Le, Google)](https://www.youtube.com/watch?v=G5RY_SUJih4)

### Reinforcement Learning

- [Simple Reinforcement Learning with Tensorflow Part 0: Q-Learning with Tables and Neural Networks](https://medium.com/emergent-future/simple-reinforcement-learning-with-tensorflow-part-0-q-learning-with-tables-and-neural-networks-d195264329d0)
- [Human-level control through deep reinforcement learning](http://www.davidqiu.com:8888/research/nature14236.pdf)
- [An implementation of Deep Q-Network in TensorFlow](https://github.com/devsisters/DQN-tensorflow)
- [Cart-Pole Balancing with Q-Learning](https://medium.com/@tuzzer/cart-pole-balancing-with-q-learning-b54c6068d947)



https://github.com/ivanturianytsia/Deep-Learning-Foundations/blob/master/Notes.ipynb