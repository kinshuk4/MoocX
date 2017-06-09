Convolutional Neural Networks for Visual Recognition (CS231n)
=============================================================

Lecture 1
=========
Intro to Computer Vision, historical context

More visual data in coming years.
More data leads to labeling very difficult tasks.

Computer Vision comes from
a. Physics
b. Biology
c. Psychology
d. Computer Science
e. Engineering
f. Mathematics

Learning about history of a problem leads to understanding of domain and scope, which ultimately leads to creation of new ideas to solve the problem.

History of Vision:
a. 543 Million years B.C - Organisms develop eyes for survival.
b. 16 Century A.D - Leonardo Da Vinci documented the modern camera.
c. 1959 - Understanding how visual process happens in the brain. Vision starts with simple structure.
d. 1963 - Block world
e. 1966 - AI lab established in MIT and Stanford.
f. 1970 - David Marr discovered vision is hierarchical.
g. 1973 - Pictorial Structue (Variability in structure).
h. 1979 - Generalised Cylinder model.
i. 1997 - Segmentation in color images.
j. 2001 - Face detection
k. 2005 - Histogram of Gradients.
l. 2009 - Deformable Part Model.
m. 2012 - Convolutional Neural Network.

Scientific discovery takes luck, care, and thoughfulness.

Neurons in primary visual cortex are organised in columns, and every column sees a specific orientation.

Perceptual grouping problem is very fundamental, but very important. It's still open problem to solve by machines.

Recognition just need discrete and important features. Learning important features in an image to recognize images.
Deep learning automatically learns features.
Before deep learning, SVM or hand engineered techniques to find features are used for computer vision.

In order to compare two visuals models, benchmarking datasets were created.
Imagenet - Dataset of 22K categories and 14M images.

Course will focus on:
a. Image Classification.
b. Object detection.
c. Image captioning.

CNN is not invented overnight.
LeCun and Geoffrey Hinton invented CNN.
Deep learning is possible due to cheap GPUs and big data.

The quest for visual intelligence goes far beyond visual recognition.
Machines and programs which can write a story about a scene.

Computer vision technology can better our lives.

=======================================================================================================================

Lecture 2
=========
Image classification and the data-driven approach, k-nearest neighbor, Linear classification I

Python and numpy is used to speed up the coding part of machine learning.

Image Classification:
Core task in Computer Vision
Other tasks in Computer Vision is just an addon to Image Classification.

Input:
Images are represented as 3D array of numbers with integers between [0, 255]
For e.g 
300 * 100 * 3

Challenges:
a. Viewpoint Variation
b. Illumination
c. Deformation
d. Occlusion
e. Background clutter
f. Interclass variation

Classification using hand created techniques are completely unscalable.
Hence, we use data driven approach in visual recognition.

Data driven approach:
a. Collect a dataset of images and labels.
b. Use machine learning to train an image classifier.
c. Evaluate the classifier on a withheld set of test images.

First classifier:
Nearest Neighbor Classifier:
Remember all training images and the labels.
Predict the label of the most similar training image.
Distance metric:
a. L1 (Manhattan) distance
b. L2 (Euclidean) distance
c. k-nearest Neighbor

Hyperparameter:
a. Best distance metric
b. Value of k

Use train, validation, and test data.
Use k-fold cross validation to generalise the model.

Dataset:
Cifar10 

Library:
FLANN - Fast Library for Approximate Nearest Neighbors.

k-Nearest Neighbor on images never used.
a. Terrible performance at test time.
b. distance metric on level of whole images can be very unintuitive.

Neural Network can be used for
a. See
b. Hear
c. Language
d. control
e. Think

Predicting summary to an image:
Use CNN along with RNN.

Parametric approach:
Input -> f(x, W) -> Output

Linear Classification:
f(x, W) = Wx + b

In order to visualise the model, reshape the weight matrix and plot it.
At training time, do data transformation techniques to create more data. 
At testing time, create multiple images of testing image and then predict.

Loss function quantifying what it means to have a "good" W.
Optimization - start with random W and find W that minimizes the loss.

============================================================================================

Lecture 3
=========
Linear classification II Higher-level representations, image features Optimization, stochastic gradient descent

Loss functions:
a. Multiclass SVM loss (Hinge loss):
Given an example (xi, yi) where xi is the image and where yi is the label, and score or logit vector: s = f(xi, W)
The SVM loss is
Li = sigmoid(max(0, sj - syi + 1))
L = average of Li

There is a bug with the loss.
Weight can be very large, but loss will remain zero.

Weight Regularization:
l2 regularization
l1 regularization
Elastic net (l1 + l2)
Max norm regularization
Dropout

b. Softmax Classifier(Multinomial Logistic Regression):
scores/logit = unnormalised log probabilities of the classes.
Using Softmax function, we create probabilities.
P(Y = k|X = xi) = e^si/sigmoid(e^s)

Want to maximize the log likelihood, or (for a loss function) to minimize the negative log likelihood of the correct class.

Optimization:
Total loss = Mean loss + Regularization

Gradients:
In 1 dimension, it is called derivative.
In multiple dimensions, the gradient is the vector of (partial derivates).
In order to calculate gradients,
a. Numerical gradient
b. Analytic gradient

In practice, always use analytic gradient, but check implementation with numerical gradient. This is called gradient check.

Using loss, find the gradients. Knowing the gradient, update the weight using some learning rate.
In practice, use Mini-batch Gradient Descent.

Learning rate decay is beneficial.
Optimization techniques:
a. SGD
b. Momentum
c. NAG
d. Adagrad
e. Adadelta
f. Rmsprop
g. Adam

=================================================================================================================

Lecture 4
=========
Backpropagation, Introduction to neural networks

In computational graph, perform forward pass for inference, and backward pass for learning.
Using calculus, to find the gradients during backward pass. This process is called backpropagation.
Apply chain rule to compute the gradient of the loss function with respect to the weights and input.

Understanding how backprop works, helps in debugging.

Add gate: gradient distributer
Max gate: gradient router

Gradient add at branches.

Neural Network:
2-layer Neural Network:
f = W2 max(0, Wix)

Activations functions:
a. Sigmoid
b. tanh
c. ReLu
d. Leaky ReLu
e. Maxout
f. ELU

Number of layers in a neural network is counted by the layers having weights.

More the neurons in hidden layer, more the capacity of neural network.
But, use regularization.

===================================================================================================

Lecture 5
=========
Training Neural Networks Part 1, Activation functions, weight initialization, gradient flow, batch normalization, babysitting the learning process, hyperparameter optimization

Convnet doesn't need lot of data, because you can easily find pretrained Convnet from model zoo, and use it for your small dataset.

Mini-batch SGD:
a. Sample a batch of data.
b. Forward prop it through the graph, get loss.
c. Backprop to calculate the gradients.
d. Update the parameters using the gradient.

History of Neural Network:
Perceptron - 1957
MLP - 1960
Backpropagation - 1986
Deep Learning using RBM - 2006
Speech Recognition using deep learning - 2010
Deep convolutional neural network - 2012

Activation functions:
a. Sigmoid
Squashes number of range [0, 1]
3 problems:
i. Vanishing gradient problem
ii. Sigmoid output are not zero-centered.
iii. exp() is bit expensive.

b. tanh
Squashes number of range [-1, 1]
zero centered.
Still having vanishing gradient problem.
tanh is better than sigmoid.

c. ReLu
Converge much faster than sigmoid/tanh.
Very computationally efficient.
Not zero centered.
ReLu neurons can easily die, resulting in large part of the network inactive.
In order to overcome this, initialise biases with slightly positive number (0.01)

d. Leaky ReLu
f(x) = max(0.01x, x)
Will not "die".

e. Parametric Rectifier (PReLu)
f(x) = max(alphax, x)

f. Exponential Linear Units (ELU)
f(x) = if x > 0 then x
	if x <= 0 then alpha(exp(x)-1)

Finding the ideal activation function is active area of research.

Maxout Neuron:
All together different neural architecture.
max(w1Tx + b, w2Tx + b)
Doubles the number of parameters.

Use ReLu. Be careful with learning rates.
Try out Leaky ReLu/Maxout/ELU
Try out tanh, but don't expect much.
Don't use sigmoid.

Data Preprocessing:
Zero centered
Normalisation
Decorrelation (PCA)
Whitened data.

In images:
- Subtract the mean image
- Subtract per-channel mean

Weight Initialization:
Small random number (Gaussian with zero mean and 1e-2 SD)
But, not good for large network. In large network, all activations become zero.

Do "Xavier initialisation"
W = np.random.randn(fan_in, fan_out) / np.sqrt(fan_in)
It works with tanh, but not with ReLu.
With ReLu
Use np.sqrt(fan_in/2)

Batch Normalisation:
a. Compute the empirical mean and variance independently for each dimension.
b. Normalise
Usually inserted after Fully Connected/(or Convolutional) layers, and before non-linearity.
Advantages:
i. Improves gradient flow through the network.
ii. Allow higher learning rates.
iii. Reduces the strong dependence on initalization.
iv. Act as a form of regularization; reduces the need of dropout.

Babysitting the learning process:
Step 1: Preprocess the data
Step 2: Choose the architecture
Double check that the loss is reasonable.
Make sure that you can overfit very small portion of the training data.
Find the learning rate.

Hyperparameter Optimization:
Cross validation strategy with grid search or random search.
Grid search is not good. Random covers more area.

Plot loss function.
Plot accuracies.
Track the ratio of weight updates/weight magnitudes.

==========================================================================================================

Lecture 6
=========
Training Neural Networks Part 2: parameter updates, ensembles, dropout, Convolutional Neural Networks: intro

Parameter updates:
a. SGD
SGD is very slow.

b. Momentum:
Using current gradient to update the velocity, which is combination of previous velocity along with a constant and current gradient and learning rate.

c. Nesterov Accelerated Momentum:
"lookahead" gradient step
1. First make a big jump in the direction of the previous accumulated gradient.
2. Then measure the gradient where you end up and make a correction.

No bad local minima in case of deep neural network.

d. AdaGrad:
Per parameter adaptive learning rate method.
Added element-wise scaling of the gradient based on the historical sum of squares in each dimension.
It uses cache variables, which stores previous gradient, and during updation, divide the gradient by sqrt of cache variable.

e. RMSProp:
Same like AdaGrad, but contain decay rate in order to decrease the cache variable.

f. Adam:
It's like RMSProp with momentum.

Use learning rate decay over time.

Second order optimization methods:
a. Taylor expansion

b. Newton parameter - No learning rate and fast convergence.
But, this is very expensive.

c. BFGS & L-BFGS:
L-BFGS usually works very well in full batch, deterministic mode.
Doesn't work well with mini-batch.

No need for learning rate decay with Adam or Adagrad.

Evaluation:
Model Ensembles.
1. Train multiple independent models.
2. At test time average their results.

Enjoy 2 percent extra performance.
Getting small boost from averaging multiple model checkpoints of a single model.

Regularization (dropout):
Randomly set some neurons to zero in forward pass.
Forces the network to have a redundant representation.
Dropout is training a large ensemble of models.

At test time, no dropout. But, double the activations to compensate.
Or at training time, just half the activations, which is known as inverted dropout.

Gradient Checking

Convolutional Neural Networks:
History:
a. 1959 - Understanding how visual process happens in the brain. Vision starts with simple structure.
Visual cortex has hierarchical organisation.
b. 1980 - Neurocognitron (With unsupervised learning techniques)
c. 1998 - Gradient-based learning applied to document recognition.
d. 2012 - ImageNet Classification with Deep Convolutional Neural Network.

Applications:
a. Classification
b. Retrieval
c. Detection
d. Segmentation
e. Self Driving cars
f. Automatic tagging
g. Video classification
h. Estimate posses.
i. Play computer games
j. Read chinese characters.
k. Recognize street signs.
l. Speech processing
m. Image captioning.

===============================================================================================================

Lecture 7
=========
Convolutional Neural Networks: architectures, convolution / pooling layers, Case study of ImageNet challenge winning ConvNets

Convolutional layer:
Input layer:
Height * width * depth (color channels)

Filter/Kernel:
Small patches which convolve(slide) over convolutional layer to create an activation map.
Number of filters(depth) is choosen power of 2.
The spatial extent is choosen from 3, 5, or 1.

Stride:
Moving the filter across the convolutional layer with the stride.

Padding:
Appending convolutional layer with extra pixels in order to get activation maps same size as that of convolutional layers.

Architecture example: Using filter (5 * 5 * 3 * 6) (5 * 5 * 6 * 10)
32 * 32 * 3 -----> 28 * 28 * 6 -----> 24 * 24 * 10

Visualise the filters to interpret how the model is working.

Using padding, data loss is minimum.

Using Torch in order to use Convolutional Neural Network.

Pooling layers:
Makes the representations smaller and more manageable.
Max Pooling.
Average Pooling.

Fully connected layer:

Project:
ConvNetJS on Cifar10

LeNet (1998):

AlexNet (2012):
Error on top 5 choices 15.4%

[227 x 227 x 3] input
[55 x 55 x 96] Conv1: 96 11 x 11 filters at stride 4, pad 0
[27 x 27 x 96] Max Pool 1: 3 x 3 filters at stride 2
[27 x 27 x 96] Norm 1: Normalisation layer
[27 x 27 x 256] Conv2: 256 5 x 5 filters at stride 1, pad 2
[13 x 13 x 256] Max Pool 2: 3 x 3 filters at stride 2
[13 x 13 x 256] Norm 2: Normalisation layer
[13 x 13 x 384] Conv3: 384 3 x 3 filters at stride 1, pad 1
[13 x 13 x 384] Conv4: 384 3 x 3 filters at stride 1, pad 1
[13 x 13 x 256] Conv5: 256 3 x 3 filters at stride 1, pad 1
[6 x 6 x 256] Max Pool 3: 3 x 3 filters at stride 2
[4096] FC6: 4096 neurons
[4096] FC7: 4096 neurons
[1000] FC8: 1000 neurons (class scores)

Details:
Heavy data augmentation
droput 0.5
batch size 128
SGD momentum 0.9
Learning rate 1e-2, reduced by 10 when validation accuracy plateaus
l2 weight decay 5e-4
7 CNN ensemble

We don't use normalisation anymore.

ZFNet (2013):
Error on top 5 choices: 14.8%
Same architecture as that of AlexNet:
Conv1: change from (11 x 11 stride 4) to (7 x 7 stride 2)
Conv3, 4, 5: instead of 384, 384, 256 filters use 512, 1024, 512

VGGNet (2014):
Error on top 5 choices: 7.3%
Only 3 x 3 Conv stride 1, pad 1
and 2 x 2 Max Pool stride 2
16 layer model

Fully connected layers are very expensive.
Hence, we can also choose averaging pooling at last convolutional layer.

GoogLeNet (2014):
Error on top 5 choices: 6.67%
Using Inception module.

ResNet (2015):
Error on top 5 choices: 3.57%

152 layers.
2 - 3 weeks of training.
Have lot of skip layers.

Plain net:
H(x) = relu(xW) + b

Residual Net:
H(x) = F(x) + x where F(x) = relu(xW) + b

Details:
Batch normalisation after every Conv layer
Xavier/2 initialisation
SGD + momentum (0.9)
Learning rate 0.1, divided by 10 when validation error plateaus.
Mini batch size 256
Weight decay of 1e-5
No dropout used.

============================================================================================================

Lecture 8
=========
ConvNets for spatial localization, Object detection

Computer Vision tasks
a. Classification
b. Classification + localization (Fixed number of objects)
c. Object Detection (Variable number of objects)
d. Instance Segmentation

Classification:
Input: Image
Output: Class label
Evaluation metric: Accuracy

Localization:
Input: Image
Output: Box in the image (x, y, w, h)
Evaluation metric: Intersection over Union

Dataset:
ImageNet

Idea #1
Localization as Regression
Input: Image
Output: Box coordinates (4 numbers)

Classification & Localization:
Step 1: Train a classification model.
Step 2: Attach new fully-connected "regression head" to the network.

At test time, get scores from classification head as well as regression head.
Classification head: C numbers (one per class)
Regression head: Class agnostic: 4 numbers (one box), class specific: C x 4 numbers (one box per class).

Where to attach the regression head?
a. After conv. layers
b. After last FC layer.

Localizing multiple objects:
Want to localize exactly K objects in each image.
Use K x 4 numbers (one box per object).

Application:
Human Pose Estimation: Represent a person by K joints.
Regress (x, y) for each joint from last fully-connected layer of AlexNet.

Idea #2:
Sliding window
Instead of feeding the full image as an input, take a window of the image as an input and do the classification as well as localization, and slide the window. Merge the classification and regression score of different windows to get the final output.
But, this is very expensive.

Efficient Sliding window: Overfeat
Converting fully-connected layers into convolutions.

Object Detection:
Since, detection has variable sized outputs, hence can't use regression method.
Using classification for detection using sliding window technique.
Problem is need to test many positions and scales.

History:
2005: Histogram of Oriented Gradients for Object Detection.
2010: Deformable Parts Model.

Region Proposals:
Find "blobby" image regions that are likely to contain images.
"Class-agnostic" object detector.
Using Selective search: Bottom-up segmentation, merging regions at multiple scales.
Other options too. Like Edgeboxes.

Object Detection via R-CNN (Region CNN)
a. Find region of interest.
b. Warped image regions.
c. Using CNN with classification head and regression head over image regions.

Object Detection datasets:
a. Pascal Voc
b. ImageNet
c. MS-COCO

Object Detection Evaluation:
Mean average precision (mAP).

R-CNN problems:
a. Slow at test time. Lot of region proposals.
b. SVM and regressors are post hoc. CNN features are not updated in response to SVM and regressors.
c. Complex multistage training pipeline.

Fast R-CNN:
Instead of finding region of interest initially, instead forward whole image through ConvNet and find region of interest from proposal method via "ROI Pooling".
Test time speed doesn't include region proposals.

Faster R-CNN:
Insert a Region Proposal Network (RPN) after the last convolutional layer.
RPN trained to produce region proposals directly; no need for external region proposals.
After RPN, use ROI Pooling and an upstream classifier and bbox regressor just like Fast R-CNN.

Region Proposal Network (RPN) are actually a CNN for finding the object and the location of the object.
Use N anchor boxes at each location.

In the end, object detection using Faster R-CNN, uses 4 losses. 2 losses for region proposals. 1 loss is for classifying whether it is region of interest and other one is regression loss for dimension of the region. Other 2 losses is for detecting the actually task, i.e classifying the class and the dimension of object.

R-CNN: 50 seconds test time, 1x speedup, 66 mAP
Fast R-CNN: 2 seconds test time, 25x speedup, 66.9 mAP
Faster R-CNN: 0.2 seconds test time, 250x speedup, 66.9 mAP

YOLO: You only look once
Detection as Regression
a. Divide image into S x S grid.
b. Within each grid cell predict:
	B Boxes: 4 coordinates + confidence
	Class scores: C numbers
c. Direct prediction using a CNN.

===============================================================================================================

Lecture 9
=========
Understanding and visualizing Convolutional Neural Networks, Backprop into image: Visualizations, deep dream, artistic style transfer,Adversarial fooling examples

Understanding Convnets:
a. Visualize patches that maximally activate neurons.
b. Visualize the filters/kernels (raw weights). Only interpretable on the first layer.
c. Visualize the representation (fc7 layer) using t-SNE.
d. Occlusion experiments. (as a function of the positions of the squares of zeros in the original image).
e. Visualizing Activations.

Deconv approach:
a. Feed image to net.
b. Pick a layer, set the gradient there to be all zero except for one 1 for some neuron of interest.
c. Backprop to image. 
This will not give good representations. Instead we use "Guided backpropagation".

Guided Backpropagation:
Backward pass for a ReLU is changed.
Blocking all the gradients with negative sign. Only keeping positive gradients. This give better representations.

Optimization approach:
a. Feed in zero
b. Set the gradient of the scores vector to be [0, 0, ... 1, ... 0], then backprop to image.
c. do a small "image update".
d. forward the image to the network.
e. go back to 2.

Visualize the data gradient:
Squeeze the 3 channel gradients of the image and visualize.
Use gradcut for segmentation.

Instead of using l2 regularization, one can use blurring the image in order to get better visualization at any layer.

Reconstructing the image from Convnet:
One can try to reconstruct the image from last fc layer or intermediate layers.

DeepDream (Google):
DeepDream modifies the image in a way that "boosts" all activations, at any layer. This creates a feedback loop.

NeuralStyle:
Merging style image to content image in order to get a neural style image, which is a mixture of both.
a. Extract contents length (CovNet activations of all layers for the given content image).
b. Extract style targets (Gram matrices of Convnet activations of all layers for the given style image).
c. Optimize over image to have:
i. The content of the content image.
ii. The style of the style image.

Fooling the Convnet:
Change the images in order to get a higher score for particular class, results in growing pattern in images corresponds to that particular class.
Explaining and harnessing the Adversarial examples in order to fool the convnet, so that it classify differently.

Backpropping to the image is powerful. It can be used for:
a. Understanding (Visualizing)
b. Segmenting objects.
c. Inverting codes
d. Fun (NeuralStyle/DeepDream)
e. Confusion and Chaos (Adversarial examples)

========================================================================================================================

Lecture 10
==========
Recurrent Neural Networks (RNN), Long Short Term Memory (LSTM), RNN language models, Image captioning

Recurrent Network offers a lot of flexibility:
Vanilla Neural Network offers fixed sized input vectors and output fixed sized output.

RNN offers variable size input and variable sized output.
a. One to many
For e.g Image Captioning
Input: Image
Output: Sequence of words.

b. many to one
For e.g Sentiment Classification
Input: Sequence of words.
Output: Sentiment

c. Many to many
For e.g Machine translation
Input: Sequence of words.
Output: Sequence of words.

d. Many to many
For e.g Video classification on frame level

We can also use RNN on fixed sized input sequentially.
RNN for Image generation.

Recurrent Neural Network:
We can process a sequence of vectors x by applying a recurrence formula at each time step:
ht = fw(ht-1, xt)
where
ht = new state
fw = some function with parameters W
ht-1 = old state
xt = input vector at some time step

Vanilla RNN:
ht = fw(ht-1, xt)
ht = tanh(Whh * ht-1, Wxh * xt)
yt = Why * ht

Application:
Character-level language model

Input: Sequence of characters.
Output: Predicting the next character.

Convert the characters to indices, and feed and predict the indices.

Hyperparameters:
Hidden size: Size of neurons in hidden layer
sequence length: Length of text to backprop.
learning rate

Randomly initialization of
Wxh
Whh
Why

Assign zeros for biases
bh
by

Use batch of sequence length in order to train your model.
Using Adagrad update.

Feeding the character as one-hot encoding.
Input: Batch of sequence
Output: Batch of sequence with one offset. (Predicting the next character)
Forward pass with batch.
Find the loss after full batch, backprop till the first character in the batch, find the gradients, and update the weights.

Basic idea:
During training, we use softmax classifier in order to backprop the loss of prediction of the next character to the hidden layers.

Application:
a. Train on Shakespeare vocab, and RNN will generate new shakespeare corpus.
b. Train on algebraic geometry, and RNN generate mathematics. Proofs and lemma.
c. Train on linux source code, and RNN generate C functions.

Gold Star idea:
Even RNN is trained on batch of sequence, it is able to generalise way over the batch length.
Every neuron in hidden layer works differently, and activate for certain pattern of sequence.
Visualizing hidden cells in an RNN.

Image Captioning:
Input: Image
Output: Description of image

Architecture:
Image ---> CNN ---> RNN ---> Sequence of words.

Inference:
Image ---> CNN ---> Instead of softmax layer at the top, feed the fc7 layer (top layer) to the RNN
h = tanh(Wxh * x + Whh * h + Wih * v)
where Wih * v is coming from CNN, v is fc7 layer.

Initial input to the RNN is 300 dimensions vector of words(Special initial vector) from our vocab.
Sample the output (word) from RNN and feed it again to RNN.
Until RNN sample special <END> token.

Number of dimenion in output layer in RNN is equal to dimension of words in vocab. 

Training data will consist of image as input and sequence of words as output.
This sequence of words will became our vocabulary with an <end> token.

Output of CNN only goes to first timestamp of RNN, and RNN will remember the image, as well as predict the next word for vocabulary.
Better backprop from the output of the RNN to the input of the CNN.

Dataset:
MS-COCO

Fancier architectures:
RNN attends spatially to different parts of images while generating each word of the sentence.
a. Input image
b. Convolutional feature extraction.
c. RNN with attention over the image.
d. Word by word generation.

Deep RNN:
Stack multiple RNN and RNN will get input from below RNN or direct input and previous RNN state.

Long Short Term Memory (LSTM):
It is used instead of tanh in order to calculate activation of RNN using input and previous RNN state.

RNN usually have single hidden vector at every single timestamp.
LSTM model has two vectors at every single timestamp, i.e hidden vector and cell state vector.

(i f o g) = (sigm sigm sigm tanh) W * (ht-1, ht)
ct = f gate ct-1 + i gate g (Cell update)
ht = o gate tanh(ct) (Hidden update)

In RNN, we have vanishing gradient problem, but in LSTM, we don't have.
Vanishing gradient problem arrises due to multiplication of same Whh vector with gradient over and over again. It can also lead to exploding gradient problem.

In order to control exploding gradient problem, use gradient clipping.
Use LSTM to control vanishing gradient problem.
Forget gate in LSTM doesn't help in overcoming vanishing gradient problem. Hence, initialize the LSTM forget gate with high bias, and the model will learn with time how to use the forget gate effectively.

GRU is better than LSTM.

====================================================================================================================

Lecture 11
==========
Training ConvNets in practice, Data augmentation, transfer learning, Distributed training, CPU/GPU bottlenecks, Efficient convolutions

Data Augmentation:
Transform the input image before feeding it to CNN.
a. Horizontal flips
b. Random crops/scales
	i. Training: sample random crops/scales
	ii. Testing: average a fixed set of crops.
c. Color jitter: 
	i. Simple: Change the contrast.
	ii. Complex: a. Apply PCA to all [R, G, B] pixels in training set.
				 b. Sample a "color offset" along principal components directions.
				 c. Add offset to all pixels of a training image.
d. Get creative.
Random mix combinations of:
	i. translation
	ii. rotation.
	iii. stretching
	iv. shearing.
	v. lens distortions.

A general theme:
a. Training: Add random noise.
b. Testing: Marginalise over the noise.
Using Dropout or DropConnect.

Especially useful for small dataset.

Transfer learning:
a. Train or download on imagenet.
b. Small dataset: feature extractor, freeze convolutional layers and just train the head of the model.
c. Medium dataset: finetuning, train more the layers in the model.

During finetuning, we have three kinds of layers:
a. Frozen layers
b. New layers
c. Intermediate layers, initialised from pre-training, but still optimizing.

Early layers captures more generic data, and final layers captures more specific.

Transfer learning with CNN is pervasive. It's very normal.

Have some dataset of interest but it has < -1M images?
a. Find a very large dataset that has similar data, train a big ConvNet there.
b. Transfer learn to your dataset.

Caffe ConvNet library has a "Model Zoo" of pretrained models.

All about Convolutions:
a. How to stack them.
The power of small filters:
i. Three 3 x 3 conv gives similar representational power as a single 7 x 7 convolutional.
Stack of 3 x 3 conv have fewer parameters, and non-linearity.

Using 1 x 1 "bottleneck":
i. "bottleneck" 1 x 1 conv to reduce dimension.
ii. 3 x 3 conv at reduced dimension.
iii. Restore dimension with another 1 x 1 conv.

Bottleneck stacks are again have fewer parameters, and non-linearity.

Using 1 x 3 and 3 x 1 conv in order to reduce the features, which is better than one 3 x 3 conv.
Using 1 x 1, 1 x 3, 3 x 1, 3 x 3 to reduce the parameter further.
This is used by Google.

b. How to compute them.
Implementing Convolutions: im2col
There are highly optimized matrix multiplications routines for just about every platform.
Turn convolutions into matrix multiplications.

Implementing Convolutions: FFT (Fast Fourier Theorm)
The convolution of f and g is equal to the elementwise product of their fourier transforms.
Using FFT, we can compute the Discrete Fourier transform of an N-dimensional vector in O(NlogN) time.
FFT convolutions get a big speedup for larger filters.

Implementing Convolutions: "Fast Algorithms"
Using Strassen's Algorithm for matrix multiplication, getting O(N^2.81)

Implementation Details:
NVIDIA is much more common for deep learning.

CPU:
Few, fast cores (1 - 16)
Good at sequential processing.

GPU:
Many, slower cores (thousands)
Originally for graphics.
Good for parallel computation.

GPU are good at matrix multiplications.
GPU are very good at convolutions. (cuDNN library)

GPU can be programmed:
a. CUDA (NVIDIA only)
Write C code directly on GPU
b. OpenCL
Similar to CUDA, but run on anything.
c. Udacity: Intro to parallel programming.

Even with GPUs, training can be slow.
VGG ~ 2 - 3 weeks training with 4 GPUs.
Resnet ~ 2 - 3 weeks with 4 GPUs.

Distribute the mini-batch on each GPU.

Multi-GPU training:
a. In lower layers, use data parallelism: each worker trains the same convnet layers on a different data batch.
b. In upper layers, use model parallelism: all workers train on same batch.

Distbelief (Google):
Model implemented on CPU.
Synchronous vs Async gradient update.
Tensorflow automatically use the current hardware optimally. No need to write abstract code for parallelism.

Bottlenecks:
a. GPU - CPU communication is a bottleneck.
b. CPU - disk bottleneck. Pre-processed images stored contiguously in files, read as raw byte stream from SSD disk.
c. GPU memory bottleneck.

Floating point precision:
a. 64 bit "double" precision as default in a lot of programming.
b. 32 bit "single" precision is typically used for CNNs for performance.
c. 16 bit "half" precision will be new standard. Supported in cuDNN. Use stochastic rounding technique.
One can even go down till 1 bit.

=======================================================================================================================

Lecture 12
==========
Overview of Caffe/Torch/Theano/TensorFlow

Caffe:
From U.S Berkeley
Written in C++
Has Python and MATLAB bindings.
Good for training or finetuning feedforward models.

Don't be afraid to read the source code.

Main classes:
a. Blob: Stores data and derivates. (Input, weights, activations, Output, Gradients)
b. Layer: Transform bottom blobs to top blobs.
c. Net: Many layers: computes gradients via forward/backward.
d. Solver: Uses gradients to update weights.

Caffe uses Protocol Buffers. "Typed json" from Google, used to serialize the data to the disk.
.proto and prototxt
Use protobuff library to read the data from .proto files as a object of a class.

Training/Finetuning:
No need to write code.
a. Convert data (run a script)
b. Define net (edit prototxt)
c. Define solver (edit prototxt)
d. Train (with pretrained weights) (run a script)

Step1: Convert data
DataLayer reading from LMDB is the easiest.
Create LMDB using convert_imageset.
Create HDF5 file yourself using h5py

Step2: Define net
Edit prototxt file.
Finetuning is done by using different names for layers, so that it don't use the previous weights and biases.

Step3: Define solver
Write a prototxt file defining a SolverParameter.

Step4: Train
Call the binary file (train)

Model zoo:
Download pretrained nets. AlexNet, VGG, GoogleNet.

Caffe has python interface. Good for:
a. Interfacing with numpy.
b. Extract features.
c. Compute gradients.
d. Define layers in Python with numpy (CPU only).

Pros:
a. Good for feedforward networks.
b. Good for finetuning existing networks.
c. Train models without writing a code.
d. Python interface is pretty useful.

Cons:
a. Need to write C++/CUDA for new GPU layers.
b. Not good for recurrent networks.
c. Cumbersome for big networks.

Torch:
From LYU + IDIAP
Written in C and Lua
Used a lot in Facebook, DeepMind.

Write in Lua:
High level scripting language, easy to interface with C.
Similar to javascript.

Tensors:
Torch tensors are just like numpy arrays.
Unlike numpy, GPU is just a datatype away.

nn package:
nn module lets you easily build and train neural nets.

cunn package:
Running on GPU is easy.
Import a few new packages.
Cast network and criterion.
Cast data and labels.

optim package:
Implements different update rules: Momentum, Adam etc.

Modules:
Torch just has Modules.
Modules are classes written in Lua, easy to read and write.

Container module:
Alow you to combine multiple modules.

nngraph module:
Use nngraph to build modules that combine their inputs in complex ways.

Pretrained models:
loadcaffe: load pretrained caffe models.

Package management:
Use luarocks to install and update lua packages.

Prons:
a. Lot of modular pieces that are easy to combine.
b. Most of the library code is in Lua, easy to read.
c. Lot of pretrained models!

Cons:
a. Lua
b. Less plug and play than Caffe
c. Not great for RNNs.

Theano:
From Yoshua Benio's group of University of Montreal.
Embracing computation graphs, symbolic computation.
High-level wrappers: Keras, Lasagne
Write your code in Python.

Problem: Shipping weights and gradients to CPU on every iteration to update.
Use shared variable to avoid overhead.
Easy to implement RNN.

Lasagne: High level Wrapper
Keras: High level Wrapper

Lasagne Model zoo.

Pros:
a. Python + numpy
b. Computational graph is nice abstraction.
c. RNNs fit nicely in computational graph.
d. High level wrappers (Lasagne, Keras)

Cons:
a. Raw Theano is somewhat low level.
b. Error messages can be unhelpful.
c. Large models can have long compile time.
d. Much "fatter" than Torch; more magic.

Tensorflow:
From Google
Very similar to Theano - all about computational graph
Easy visualizations (Tensorboard)
Multi-GPU and multi-node training.

Placeholders:
Input and output nodes.

Variables:
Weights and biases

Tensorboard:
Create summaries for loss and weights in order to visualize what's happening inside your models.
Name scope expand to show individual operations.

Multi-GPU support:
Data parallelism:
Async or sync

Model parallelism:
Split model across GPUs.

Distributed:
Single machine
Many machines

Very less pretrained models.

Pros:
a. Python + numpy
b. Computation graph abstraction.
c. Great for RNNs.
d. Slightly more convenient than raw Theano.
e. Tensorboard for visualization.
f. Data and model parallelism.
g. Distributed models.

Cons:
a. Slower than other frameworks right now.
b. Much "fatter" than torch.
c. Not many pretrained models.

==============================================================================================================

Lecture 13
==========
Segmentation, Soft attention models, Spatial transformer networks

Segmentation:
a. Semantic
Label every pixel.
Don't differentiate instances.

b. Instance
Detect instances given category, label pixels.
Simultaneous detection and segmentation. 

Semantic Segmentation:
a. Extract patch.
b. Run through a CNN.
c. Classify center pixel.
d. Repeat for every pixel.

But, this is very expensive.
In order to overcome this, run "fully convolutional" network to get all pixels at once.

Multi-scale:
a. Resize image to multiple scales. Also create segmentation trees.
b. Run one CNN per scale.
c. Upscale outputs and concatenate.
d. Labeling.
e. Combine everything for final outputs.

Refinement:
a. Apply CNN once to get labels.
b. Downsize the input image to match the output of the first layer.
c. Apply again to refine labels.
d. And again.

It will works like a RNN.

Upsampling:
Use only deep learning for segmentation.
Contains learnable upsampling layer at the end.
Also, contains "skip connections".

Deconvolution:
3 x 3 "deconvolution", stride 2 pad 1
Input gives weight for filter.
Same as backward pass for normal convolution.

Normal VGG ---> "Upside down" VGG

Dataset:
Pascal segmentation

Instance Segmentation:
Detech instances, give category, label pixels.
Simultaneous detection and segmentation. (SDS)

Similar to R-CNN:

Image ---> Proposal Generation ---> External Segment proposals ---> Feature extraction & Mask out background with mean image ---> Box CNN & Region CNN ---> Region Classification ---> Region refinement

To do region refinement:
Hypercolumns:

Cascades:
Similar to Faster R-CNN

Large input image --> Conv feature map ---> Region proposal network (RPN) ---> ROI warping, pooling ---> Reshape boxes to fixed size, figure/ground logistic regression ---> Mask out background & predict object class.

All three predictions in the above net can be learned via supervised learning technique. 

Attention Models:
Soft Attention for Captioning:
a. Instead of using the last layer of the image as the input for RNN, use convolutional features and feeding it directly to the hidden layer of the RNN, and finding Distribution over L location and the first word.
b. Feeding it back to the convolutional features and using that as the new input, along with first word, calculate again distribution over vocab.
c. Repeat until the model predicts the <end> token.

Soft vs Hard Attention:
Soft Attention: Summarize all location, train with backprop.
Hard Attention: Sample one location, with argmax. Can't use gradient descent. Need reinforcement learning.

Attention constrained to fixed grid.

Soft Attention for Translation:
Sequence to sequence model using RNN.

Soft Attention for everything:
Speech recognition.
Video captioning.
Image, question to answer, attention over image.

Attending to Arbitary Regions:
a. Read text, generate handwriting using an RNN.
b. Attend to arbitary regions of the output by predicting params of the mixture model.

DRAW:
a. Classify images by attending to arbitary regions of the input.
b. Generate images by attending to arbitary regions of the output.

Spatial Transformer Network:
a. Using the input image and box coordinates, finding the cropped and rescaled image.
b. Function mapping pixel coordinates (xt, yt) of output to pixel coordinates (xs, ys) of input.
c. Network attends to input by predicting theta.
d. Repeat for all pixels in output to get a sampling grid.
e. Then use bilinear interpolation to compute output.

Predicting the output of an input which is not accurate, wrapped, cluttered.

======================================================================================================================

Lecture 14
==========
ConvNets for videos, Unsupervised learning

Input: H X W X C X time frames

Processing videos:
a. Feature-based approaches to Activity Recognition
Dense trajectories and motion boundary descriptors for action recognition:
Action Recognition with improved trajectories:

a. Detect feature points.
b. Track features with optical flow.
c. Extract HOG/HOF/MBH features in the (stabilized) coordinate system of each tracklet.
All goes to svm for classification or regression learning.

Using CNN for videos:
Extend the convolutional filters in time, perform Spatio-Temporal Convolutions!
For e.g can have 11 x 11 x T filters, where T = 2..15
Having 3D convolutions.
This is known as Slow Fusion.

Early Fusion:
Merging the channels and time frames.

Late Fusion:
Two convnet looking at different time frames and merging the output together.

Single Frame:
Convnet looking at only one frame.

Sports dataset:
1 million videos
487 sports classes

Always go for single frame convnets. They are very powerful.
If local motion is very important in learning task, then go for Slow Fusion Convnets.

Spatio-Temporal Convnets:
Input video ---> Spatial stream ConvNet & Temporal stream ConvNet --> Fuse the two convnet in the end for class scores.
This is known as two stream model.

Models unable to detect optical flow, due to lack of amount of learning data.

Long-time Spatio-Temporal Convnets:
LSTM/RNN with 3D ConvNets.

Input Video ---> 3D Convnets ---> RNN
Better approach is to replace every neuron in 3D ConvNet with a RNN.
Using Simplier version of LSTM - GRU
Input Video --->  RNN Convnet. (GRU-RCN)

Unsupervised Learning:
Input: Data x
Goal: Learn some structure of the data.

For e.g clustering, dimensionality reduction, feature learning, generative models etc.

Autoencoders
Generative Adversarial networks. (Generate Samples)

Autoencoders:
Input ---> Encoder ---> Features ---> Decoder ---> Reconstructured input
Encoder and Decoder shares weights.
l2 euclidean loss.

After training, throw away decoder.
Use encoder to initialize a supervised model.
Fine-tune encoder jointly with classifier.

Greedy training in Autoencoders using RBMs was classic techniques.
This is not common anymore.
With ReLU, proper initialization, batchnorm, Adam etc easily train the network from scratch.

Generate images from an encoder.

Variational Encoder:
A Bayesian spin on an autoencoder - generate data.

Prior: Assume p(z) is a unit Gaussian.
Conditional: Assume p(x|z) is a diagonal Gaussian, predict mean and variance with neural network.

Input: Latent state.
Output: Mean and diagonal covariance of p(x|z)

By Bayes rule:
p(z|x) = (p(x|z) . p(z))/p(x)

where p(x|z) can be found by decoder network.
p(z) by Gaussian.
p(x) is intractible integral. We ignore it.

Basic structure of Variational Autoencoder:
Data(x) ---> Encoder network ---> Mean and (diagonal) covariance of q(z|x) ---> Sample from q(z|x) ---> Decoder network ---> Mean and (diagonal) covariance of p(x|z) ---> Reconstructed (xx)

Training like a normal autoencoder:
Reconstruction loss at the end. Regularization towards prior in middle.

After network is trained:
Sample from prior p(z) ---> Decoder network ---> Mean and (diagonal) covariance ---> Sample from p(x|z) ---> Generated (xx)

Trained on MNIST, and faces dataset.

Generative Adversarial Nets:
Random noise (z) ---> Generator ---> Fake image (x) ---> Discriminator ---> y (Real or fake?)
Train on real images.
Train generator and discriminator jointly. After training, easy to generate images.

Multiscale:
Generate low-res image --> upsample ---> Generate delta, add ---> upsample .... --> final image.
Discriminators work at each level.

Simplifying:
Generator is an upsampling network with fractionally-strided convolutions. Discriminator is a convolutional network.

Adversarial networks are good at Vector Math.

=======================================================================================================================

Lecture 15
==========
Invited Talk: Jeff Dean

Google Brain project started in 2011, with a focus on pushing state-of-the-art in neural networks.
Research & production.

1st generation:
DistBelief - scalable, good for production, but not very flexible for research.

2nd generation:
Tensorflow - scalable, good for production and research too.

Large datasets + Powerful models.

2012: Learning from Unlabeled images.
Autencoders on youtube videos.
Not convolutional
Train on 10 million (Youtube)
1000 machines (16,000 cores) for 1 week.
Using Async SGD for optimization.
1.15 billion parameters.

Adding Supervision.
Improved state of the art for Imagenet 20k class task.

2012: Speech - Feedforward Acoustic models.
Trained in < 5 days on cluster of 800 machines.
Launched at time of Jellybean release of Android.

Transfer learning in case of lower dataset.
Multilingual Training.

LSTMs to model time dimension.
Convolutions to make model frequency invariant.
CLDNN (Convolutional, Long Short-term memory Fully Connected Deep Neural Networks)
LSTM end to end is the future.

Vision problems in Google:
a. Image search
b. StreetView
c. Satellite Imagery
d. Translations
e. Robotics
f. Self driving cars

AlexNet was the beginning for the ANN in 2012.

The Inception Architecture for Vision.

Google Photo Search: Deep Convolutional Neural Networks.
Google Cloud Vision API.

Google Project Sunroof.

Language Understanding:
Represent embedding space.
Word2Vec - Finding nearest neighbor embeddings.
Directions are meaningful.

2015: RankBrain in Google Search Ranking
Query & doc --> Deep neural network --> Score for doc, query pair.
Third most important search ranking signal of (100s).

Smart Reply:
Integrated in Gmail - Deep RNN.

Combined Vision + Translation:

Reduce inference cost:
Quantize the weights to 8 bits. 4x memory reduction, 4x computation efficiency.

Distillation:
The ensemble implements a function from input to output. Forget the models in the ensemble and the way they are parametrized and focus on the function.
Transfer the knowledge in the function into a single smaller model.

If we have the ensemble, we can divide the averaged logits from the ensemble by a "temperature T" to get a much softer distribution. This is known as soft targets.
Train the model on hard target plus the soft targets.
Soft targets are very good regularizer.
Also train much faster.

Tensorflow:
Core in C++
Different front ends for specifying the computations.
a. Python and C++

Placement of task on hardware.

Model Parallelism:
Best way to decrease training time: decrease the step time.
Local connectivity.
On single core
Across cores
Across devices
Across machines.

Partition model across machines.

Data Parallelism:
Use multiple model replicas to process different.
Different parameter server, model replicas, and data centers.

Google use Data Parrallelism.

Sequence to Sequence model via Tensorflow.

Image Captioning.

Using LSTM to solve graph problems like Travelling Salesman problem.

Tensorflow queues.

=====================================================================================================

#### help from
- https://github.com/reetawwsum/Machine-learning-MOOC-notes/blob/master/Convolutional%20Neural%20Networks%20for%20Visual%20Recognition%20(Stanford%20CS231n).txt
- http://cs231n.github.io/



