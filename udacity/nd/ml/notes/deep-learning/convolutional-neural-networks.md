# Convolutional Neural Networks

## Structure helps learning

If our input data presents some structure, we can leverage it to design our neural network. In this way, we have to do less training and the network will generally perform better. Take the example below. If our model has to output only the letter that is presented in some image, it doesn't need to learn about the colors of the image. So it will learn about the letters faster if we use grey scale pictures to train it, instead of colored ones.

![Structure helps learning](images/cnns/structure-helps-learning.png)

## Statistical invariance

Another example of structured data that helps to train models faster is images containing some feature. For example, an image with a kitten on the left corner, is the same as an image with a kitten on the center, or on the right corner, for the purpose of identifying kittens. So if we can leverage that to train models faster. This phenomena is called `translation invariance`

![Translation invariance](images/cnns/translation-invariance.png)

The same idea can be applied to text. For example, a noun like `kitten` will most likely have the same meaning, disregarding where it occurs on the text.

![Translation invariance with texts](images/cnns/translation-invariance-2.png)

We can use this `statistical invariance` (i.e. features that don't change on average across time or space) to our benefits while designing neural networks by using a technique called `weight sharing`. Weight sharing is when we share the weights of inputs that represent the same information, therefore training them at the same time.

Image
 - Different positions
 - Same objects

Text
 - Kitten in a long text
 - You can use weight sharing and train them jointly for those inputs


![Statistical invariance](images/cnns/statistical-invariance.png)

## ConvNets

Convolutional Neural Networks (or ConvNets for short) are neural networks that share their weight parameters across space.

![ConvNets weight sharing](images/cnns/convnets-weight-sharing.png)

To understand CNNs, we have to understand what is a convolution.

A convolution is when we have some input, for example an image: width x height x depth (RGB), and take patches of it, with lesser dimension, and apply some function to these patches resulting in an output with lesser width and height, but higher depth than the input.   

![Convolutions 1](images/cnns/convolutions-1.png)

On the image example, the output of the convolution of a 256x256 image with 3 channels RGB, could be a 128x128x16 3D Matrix, like shown below.

![Convolutions 2](images/cnns/convolutions-2.png)

![Convolutions 3](images/cnns/convolutions-3.png)

While a regular neural network has various layers of matrix multiplications, a convolutional neural network consists of a pyramid of convolutions and on the top sits a classifier.

![Convolutional pyramid](images/cnns/convolutional-pyramid.png)

![ConvNet Overview](images/cnns/convnet-overview.png)

## Convolutional Lingo (OR Terms)

There are various terms related to ConvNets.

- *Patch* or *kernel* is the subset of pixels of an image that is taken as input by each node of a CNN.
- *Feature Map* is each layer of the depth dimension of the input. For example, an RGB image contains 3 feature maps.

![cnn-patch-feature-map](images/cnns/cnn-patch-feature-map.png)

- ***Stride*** is the distance between each patch.

![cnn-stride](images/cnns/cnn-stride.png)

- *Padding* is the strategy applied to fill the convolution output (or not). This can be `same padding` or `valid padding`.
    - `Same padding` consists of filling the output with zeros so that the width and height match those of the input.
    - `Valid padding` doesn't fill the output with zeros, leaving it with a different size than the input.

**Strides, depth and padding**
Imagine you have 28x28 image.
 - You run a 3x3 convolution on it.
 - Input depth: 3
 - Output depth: 8
![cnn-patch-feature-map](images/cnns/strides_depth_padding.png)
 - For stride: 1 and padding: same (1)
    - You would have the exact same dimensions.
    - You would be taking a F x F x D_input dot-product to come up with a number.
 - For stride: 1 and padding: valid (0)
    - You would have one less row and column
 - For stride: 2 and padding: valid (0)
    - You would have half the output.

**Calculating Output Size**
O = (W - K - 2P)/S + 1 
 - O is the output height/length
 - W is the input height/length
 - K is the filter size (kernel size)
 - P is the padding
 - S is the stride

**Padding Size**
 - In general it's common to see same (zero) padding, stride 1 and filters of size FxF.
 - Zero-padding = (F - 1)/2
 - If you do not pad (same padding), you would decrease the width and height of your layers gradually. This might not be something you want.

**Depth**
 - Number of filters = depth.
 - We try to keep this in powers of two.
    - 32, 64, 128, 512 etc.
    - This is for computational reasons.
   
**Number of Parameters**
Number of parameters in layer = (F x F x D_input + 1) x D_filter
 - Where F is the filter size
 - D_input is the depth of the input layer
 - 1 is the bias
 - D_filter is the depth of the filter
 - Parameters per filter: (F x F x D_input + 1)

**Convolution Networks**
Finally the convnet:
![cnn-patch-feature-map](images/cnns/conv-net-fully-connected-classifier.png)

Chain rule with sharing
![cnn-patch-feature-map](images/cnns/chain-rule-with-sharing.png)

## Pooling

Pooling is a technique to reduce the size of the image in order to make the convnet more efficient. It consists of taking the max of a subset of pixels. For example, given an output of the convolution layer, we take 2x2 pixels and select the maximum value. In this example, after the pooling layer, the output will be reduced to width/2 * height * 2.

__Striding__
 - We shift the filter by a few pixel each time.
 - This is very aggressive method that removes a lot of information.
__Pooling__
 - We can take a smaller stride.
 - Take all the convolutions in the neighbors.
 - Combine them somehow, and this is called pooling.
    - We will be preserving the depth.
    - But we will be reducing the width and height

![Pooling](images/cnns/pooling.png)

__Max Pooling__
The greatest advantages of applying max pooling instead of increasing the stride to reduce the output is the accuracy. Increasing stride results in information loss, while max pooling takes into account all the pixels in the neighborhood to select the maximum value.

The disadvantage of max pooling is that the model requires more computing to train and predict and also increases the number of hyperparameters to tune.

![Max Pooling](images/cnns/max-pooling.png)

Some famous CNN Architecture consist of 2 convolution layers interleaved with 2 pooling layers that feeds 2 dense layers and then output the classification. These architectures were used to create two ConvNets that won world challenges in the past, called `LENET-5` and `ALEXNET`.

![Famous Architectures](images/cnns/famous-cnn-architectures.png)

__Average Pooling__
Besides Max Pooling, average pooling is also another useful pooling technique, which provides a blurred version of the image as an output.

![Average pooling](images/cnns/average-pooling.png)

## 1x1 Convolutions

At first, it might sound strange to make 1x1 convolutions. The whole idea behind CNNs is to take advantage of context and proximity of the pixels information to classify the data.

The idea of using 1x1 convolutions is to create deep layers of convolutions, by applying a 1x1 convolution after a regular convolution. This is equivalent to a matrix multiply operation over the result of the first convolution and it introduces more parameters to be tuned by the network, deepening the network.

Also, this is a pre-requisite for an advanced architecture of CNNs that is being used more frequently nowadays. This architecture is called Inception Module.

![1x1 Convolutions](images/cnns/1-1-convolutions.png)

## Inception Module

The Inception module architecture is a way to add more parameters to a ConvNet without compromising the cost of training, comparing to adding another convolutional layer. It consists of stacking pooling and convolutional layers with different sizes and then concatenating all of their outputs. The advantage of this module is that we can choose the parameters in a way that we keep the number of parameters very small, while performing better than with a single convolutional layer.

This is like an ensemble of methods.

![Inception Module](images/cnns/inception-modules.png)

**Evaluation of results**
 - We can use accuracy to evaluate the predicted values and our labels.
 - But a better method would be to use the top-1 and top-5 errors.

**Progress in Convs (in order of lower top-1 and top-5 errors)**
 - LeNet-5
 - AlexNet
 - ZFNet
 - VGGNet
 - GoogLeNet
 - ResNet
    - As of September 2016, this is the latest state-of-the-art implementation for convs.

**Further Readings**
 - [Convolution Arithmetic for Deep Learning](http://arxiv.org/pdf/1603.07285v1.pdf)
 - [A Beginner’s Guide To Understanding Convolutional Neural Networks Part 1](http://www.kdnuggets.com/2016/09/beginners-guide-understanding-convolutional-neural-networks-part-1.html)
 - [A Beginner’s Guide To Understanding Convolutional Neural Networks Part 2](http://www.kdnuggets.com/2016/09/beginners-guide-understanding-convolutional-neural-networks-part-2.html)
 - [CS231n Winter 2016 Lecture 7 Convolutional Neural Networks Video](https://www.youtube.com/watch?v=AQirPKrAyDg)
 - [CS231n Winter 2016 Lecture 7 Convolutional Neural Networks Lecture Notes](http://cs231n.stanford.edu/slides/winter1516_lecture7.pdf)



