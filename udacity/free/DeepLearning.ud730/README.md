Originals can be found in the [Tensorflow repo](https://github.com/tensorflow/tensorflow/tree/master/tensorflow/examples/udacity).

<div align="center">
  <img src="https://www.tensorflow.org/images/tf_logo_transp.png"><br><br>
</div>
-----------------

| **`Linux CPU`** | **`Linux GPU`** | **`Mac OS CPU`** | **`Windows CPU`** | **`Android`** |
|-----------------|---------------------|------------------|-------------------|---------------|
| [![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-cpu)](https://ci.tensorflow.org/job/tensorflow-master-cpu) | [![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-linux-gpu)](https://ci.tensorflow.org/job/tensorflow-master-linux-gpu) | [![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-mac)](https://ci.tensorflow.org/job/tensorflow-master-mac) | [![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-win-cmake-py)](https://ci.tensorflow.org/job/tensorflow-master-win-cmake-py) | [![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-android)](https://ci.tensorflow.org/job/tensorflow-master-android) |

**TensorFlow** is an open source software library for numerical computation using
data flow graphs.  Nodes in the graph represent mathematical operations, while
the graph edges represent the multidimensional data arrays (tensors) that flow
between them.  This flexible architecture lets you deploy computation to one
or more CPUs or GPUs in a desktop, server, or mobile device without rewriting
code.  TensorFlow also includes TensorBoard, a data visualization toolkit.

TensorFlow was originally developed by researchers and engineers
working on the Google Brain team within Google's Machine Intelligence research
organization for the purposes of conducting machine learning and deep neural
networks research.  The system is general enough to be applicable in a wide
variety of other domains, as well.

**If you'd like to contribute to TensorFlow, be sure to review the [contribution
guidelines](CONTRIBUTING.md).**

**We use [GitHub issues](https://github.com/tensorflow/tensorflow/issues) for
tracking requests and bugs, but please see
[Community](tensorflow/g3doc/resources/index.md#community) for general questions
and discussion.**

## Installation
*See [Download and Setup](tensorflow/g3doc/get_started/os_setup.md) for instructions on how to install our release binaries or how to build from source.*

People who are a little more adventurous can also try our nightly binaries:

* Linux CPU-only: [Python 2](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=cpu-slave/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow-0.12.1-cp27-none-linux_x86_64.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=cpu-slave)) / [Python 3.4](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=cpu-slave/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow-0.12.1-cp34-cp34m-linux_x86_64.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=cpu-slave/)) / [Python 3.5](https://ci.tensorflow.org/view/Nightly/job/nightly-python35-linux-cpu/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow-0.12.1-cp35-cp35m-linux_x86_64.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-python35-linux-cpu/))
* Linux GPU: [Python 2](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-linux-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=gpu-linux/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow_gpu-0.12.1-cp27-none-linux_x86_64.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-linux-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=gpu-linux/)) / [Python 3.4](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-linux-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=gpu-linux/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow_gpu-0.12.1-cp34-cp34m-linux_x86_64.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-linux-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=gpu-linux/)) / [Python 3.5](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-linux-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3.5,label=gpu-linux/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow_gpu-0.12.1-cp35-cp35m-linux_x86_64.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-linux-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3.5,label=gpu-linux/))
* Mac CPU-only: [Python 2](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=mac-slave/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow-0.12.1-py2-none-any.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=mac-slave/)) / [Python 3](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=mac-slave/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow-0.12.1-py3-none-any.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-cpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=mac-slave/))
* Mac GPU: [Python 2](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-mac-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=gpu-mac/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow_gpu-0.12.1-py2-none-any.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-mac-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON2,label=gpu-mac/)) / [Python 3](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-mac-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=gpu-mac/lastSuccessfulBuild/artifact/pip_test/whl/tensorflow_gpu-0.12.1-py3-none-any.whl) ([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-matrix-mac-gpu/TF_BUILD_IS_OPT=OPT,TF_BUILD_IS_PIP=PIP,TF_BUILD_PYTHON_VERSION=PYTHON3,label=gpu-mac/))
* Android: [demo APK](https://ci.tensorflow.org/view/Nightly/job/nightly-android/lastSuccessfulBuild/artifact/out/tensorflow_demo.apk), [native libs](http://ci.tensorflow.org/view/Nightly/job/nightly-android/lastSuccessfulBuild/artifact/out/native/)
([build history](https://ci.tensorflow.org/view/Nightly/job/nightly-android/))

#### *Try your first TensorFlow program*
```shell
$ python
```
```python
>>> import tensorflow as tf
>>> hello = tf.constant('Hello, TensorFlow!')
>>> sess = tf.Session()
>>> sess.run(hello)
Hello, TensorFlow!
>>> a = tf.constant(10)
>>> b = tf.constant(32)
>>> sess.run(a+b)
42
>>>
```

##For more information

* [TensorFlow website](http://tensorflow.org)
* [TensorFlow whitepaper](http://download.tensorflow.org/paper/whitepaper2015.pdf)
* [TensorFlow Model Zoo](https://github.com/tensorflow/models)
* [TensorFlow MOOC on Udacity](https://www.udacity.com/course/deep-learning--ud730)

The TensorFlow community has created amazing things with TensorFlow, please see the [resources section of tensorflow.org](https://www.tensorflow.org/versions/master/resources#community) for an incomplete list.

## Chapters (to be organized)
Lesson 1
========
Machine learning to deep learning

Course outline:
1. Logistic Regression
2. Stochastic Optimization
3. Data & Parameter tuning
4. Deep Networks
5. Regularisation
6. Convolutional networks
7. Embeddings
8. Recurrent models

Library:
Tensorflow

Deep learning is only possible due to cheap GPUs and lots of data.

Supervised Classification:
Logistic Regression

Softmax converts any kind of scores to proper probablity.

One-hot encoding:
Converting classification output to binary vector, whose size is equal to size of classes to predict.

Single Layer ANN:
Input (X) ---> Linear Model (WX+b) ---> Logit (y) ---> Softmax S(y) --> Cross Entropy D(S, L) ---> 1 Hot Encoding (L)

Multinomial logistic classification.
D(S(WX+b), L)

Loss = Average Cross Entropy over all samples
Choose W & b, such that Loss is minimized.

Training, Validation, and then testing

Kaggle - Competitiong based on classification tasks

Minimum 30 examples improvement is regarded as improvement in model, otherwise it might be noise.

Validation set size > 30k examples 
changes > 0.1% in accuracy

======================================================================================================= 

Lesson 2
========

Why go for Deep Learning?
Normal model are linear.

X -> shape(1, 784)
W -> shape(784, 10)
b -> shape(1, 10)

y = X.W + b

Non-linear functions:
1. Sigmoid
f(x) = 1/(1+e(-z))
2. Rectified Linear Unit (RELU)
f(x) = max(0, x)

Neural Networks:
Input Layer - RELU Layer - Output Layer

Input (X) ---> { Linear Model (WX+b) ---> RELU Layers } * n hidden layers ---> Linear Layer (WX+b) ---> Logit (y) ---> Softmax S(y) --> Cross Entropy D(S, L) ---> 1 Hot Encoding (L)

Few Addon to improve the model:
1. L2 Regularization
2. Dropout
3. Learning rate decay
4. Momentum
5. ADAGRAD

Back-Propogation Algorithm for learning the weights and bias in the model.

Instead of using more neurons in a single hidden layer, go for more hidden layers.
Every layer will learn different features automatically.

Early termination is also used to avoid overfitting.
Also, go for regularisation to optimise your model.

Dropout technique is also used for optimise.
Remove random inputs from activation value from one layer to other, and others multiply by 2.

======================================================================================================

Lesson 3
========

Structure helps learning.
If you know the structure of the data, it will learn faster and better.

If one need Translation Invariance, start using weight sharing for multiple inputs.

Convolutional Networks (Convnets)
Neural Network where inputs share the params across the space.

Convnets Pyramid

Key Terms:
1. Patch/Kernel
2. Input Feature Map
3. Output Depth
4. Stride - Number of pixels shifting each time you move your patch.
5. Valid / Same Padding - Same padding leads to exact size of next layer

Few addon to improve the model:
1. Pooling - Reducing size of an image by taking maximum or average of neighoring pixels.
2. 1x1 Convolutionals - Using patch of 1x1 size in between normal Convnet.
3. Inception

General Mode:
Image --> Convolution --> Max Pooling --> Convolution --> Max Pooling --> Fully Connected --> Fully Connected --> Classifier

Inception involves pooling, 1x1, and simple convnets.

Convets need very large computation. Hence, GPU is better for such computations.

======================================================================================================

Lesson 4
========

Problems with text
------------------
1. Rare words are important. That means very few positive examples.
2. Semantic Ambiquity (Cats vs Kitty)

Infrequent words are important.
Related words should share same weights.

When training examples are very less, either generate more training examples, or switch to Unsupervised Learning.

Gold Star Idea:
Similiar words tends to occur in similiar context.

Learn to predict the word context.

Convert the text corpus to vectors knows as Embeddings, in which the words sharing same context will be closer together and vice versa.
1. To verify, calculate nearest neighbor distance.
2. To view, don't go for PCA, results in large data loss; go to t-sne.

Way to learn embedding:
1. Word2Vec - Using logistic regression

Embeddings is powerful algorithm.

Sampled Softmax:
During training, samples the target vector (Randomly ignoring few target), while calculating Softmax Cross Entropy.

After Embedding, one can achieve Word Analogies, for e.g
1. Semantic Analogy (Puppy -> Dog/Kitten -> Cat)
2. Syntactic Analogy (Taller -> Tall/Shorter -> Short)

RNN is used for mainly variable length input.

Recurrent Neural Network (RNN)
Neural network where input share the same params across the time.

			  Output
				|
Past --> Neural Network --> Future
				|
			  Input

Backpropogation through time:
Using SGD in RNN results in exploding and vanishing gradients problems.

Solve exploding gradients:
By gradient clipping. 

Vanishing Gradients results in forgetting distant past.
Solution:
Use LSTM (Long Short-Term Memory)
It has three gates
1. Read Memory
2. Write Memory
3. Forget Memory
Instead of using discrete flags to different gates, LSTM use continous functions, which results in differentiable.
Gate value of each gate is controlled by logistic regression on input params.

Use L2 and dropout on RNN for regularization.

RNN applications:
1. Predicting next word in the sentence.
Using Beam search to avoid overhead searching.

RNN maps variable length sequences to fixed length vectors.
It can also maps fixed length vectors to variable length sequences.
Hence, RNN can be used to map variable length sequences to variable length sequences in different domain.
Applications:
Machine Translation
Speech Recognition
Image to sequence (MSCOCO dataset)

====================================================================================================


##help from
 - https://github.com/reetawwsum/Machine-learning-MOOC-notes/blob/master/Deep%20Learning.txt