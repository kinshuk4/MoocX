## Neural Networks for Machine Learning (University of Toronto)

Lecture 1
=========

Why do we need machine learning?
--------------------------------
Why machine learning?
Hard to write programs, such as 3D image recognization, credit card frauds.
Not fully understood, how our brain does such work.

How machine learning works?
Instead of writing a program for a specific task, we collect a lot of examples with set of inputs and correct output.
A machine learning algorithm takes these examples and produces a program that does the job.

Examples where such programs provides a significant role:
Recognizing patterns - Object recognization, image recognization, and voice recognization.
Recognizing anamolies - Credit card frauds.
Predication - Stock rates, Which movie person like?

MNIST database is used as toy example to learn handwritten digits.

Hard to develop a template for digits in handwritten database.

Used to develop image recognization on web.
Android use speech recognization neural net for OK Google.
Siri is also based on such programming.

What are neural network?
------------------------
Reasons to study neural computation:
Learn how the brain work.
Learn how brain does excellent parallel computation.
Solve practical problems by using learning algorithms from the brain.

How brain works?
Brain consists of lot of neurons.
Each neuron receives signals, and transmit signals.
Each neuron gets activated, after a certain strength of signal.
Neurons learns by adapting this activation by adjusting the weight of a signal.

Some simple models of neurons
-----------------------------
Models of neurons:
y  -> output
b  -> bias
xi -> inputs
wi -> weights

Linear Neuron:
y = b + sum(xi * wi) [Weighted sum of inputs]

Binary threashold neuron:
Compute a weighted input
If it exceed a certain threashold, then it send fixed size spike of activity. 
z = b + sum(xi * wi)
y = 1 if z > 0
	0 otherwise

Rectified Linear Neuron:
z = b + sum(xi * wi)
y = z if z > 0
	0 otherwise

Sigmoid Neuron:
z = b + sum(xi * wi)
y = 1 / (1 + e^(-z))

Nice derivates; makes learning easy.

Stochastic Binary Neuron:
z = b + sum(xi * wi)
P(s = 1) = 1 / (1 + e^(-z)) [Probability]

A simple examples of learning
-----------------------------
Write a program to determine handwritten digits.

Three types of learning
-----------------------
Supervised learning:
Learn to predict the output when given an input vector.

Regression: Output is a real number. 1/2 (y - t)^2 is used.
Classification: Class label.

Reinforcement learning:
Learn to select an action to maximize payoff.

Unsupervised learning:
Discover a good internal representation of the input.

==========================================================================================================

Lecture 2
=========

An overview of the main types of neural networks architecture
-------------------------------------------------------------
Feed-forward Neural Network:

Input Layer -> Hidden Layer -> Output Layer
(No cycles)

Most common in practical application
If there are more than one hidden layer, it is known as Deep Neural Net.
The activities of the neurons in each layer are a non-linear function of the activities in the layer below.

Recurrent Network:

These have directed cycles in their connection graph.
Complicated dynamics; difficult to train.
Biologically realistic.

Used in predicting the next character in the sentence.
Google used it to predict the next word in the search query.

Symmetrically Network:
Same as Recurrent, but connection between units are symmetrical (have same weights in both directions)

The first generation of neural networks
---------------------------------------
Standard paradigm for statistical pattern recognization:
1. Convert the raw input vector into a vector of feature activations.
2. Learn how to weight each of the feature activations to get a single scalar quantity.
3. If the quantity is above some threshold, decide that the input vector is a positive example of target class.

Perceptron:
Decision Unit-> Binary threshold neuron
z = b + sum(xi * wi)
y = 1 if z >= 0
	0 otherwise

Bias can be learned similiar as weights.

Learning rule of Perceptron:
if output is correct
	leave its weights alone.
else
	if output unit incorrectly outputs a 0
		add input vector to the weight vector
	if output unit incorrectly outputs a 1
		subtract input vector from the weight vector

	OR weights += error * x (where error = expected - output)

Geometrical view of Perceptrons
-------------------------------
Weight Space:
This space has one dimension per weight.
A point in the space represents the particular setting of the weights.
Each training case can be represented as the hyperplane through the origin.
The weights must lie on one side of this hyper-plane to get the answer correct.

Why the learning works?
-----------------------
Proof of learning algorithm via squared distance measure

What Perceptron can't do
------------------------
XOR gate can't be made using Perceptrons.

Conclusion:
Networks without hidden units are very limited in the input-output mappings they can learn to model.
Learning the weights going into hidden units is equivalent to learning features.

=====================================================================================

Lecture 3
=========

Learning the weights of a linear neuron
---------------------------------------
The perceptron convergence procedure works by ensuring that every time the weights change, they got closer to to every "generously feasible" set of weights.
This is bad because the average of two good solutions may be a bad solution.

MLNN works by getting actual output values get closer to target values.

Linear Neuron:
y = b + sum(xi, wi)

The error is the squared difference between the desired output and the actual output.

Why not solve the problem using equations? One per training case.
1. We want a method that real neurons can use.
2. We want a method that can be generalised to multi-layer, non-linear neural network.

Delta Rule of learning:
delta w = (learning rate) * input * (t-y)
e.g prices += learning_rate * x * error

Error surface of linear neuron
------------------------------

For linear neuron with a squared error, it is a quadratic bowl.
Vertical cross-sections are parabolas.
Horizontal cross-sections are ellipses.

For multi-layer, non-linear neuron nets the error surface is much more complex.

Online vs batch-learning:
Online - Update the weights after every training case. It makes zig-zag around the direction of steepest decent.
Batch - Update the weights after some set of training case. It does steepest decent on the error surface.

Why learning can be slow:
If the error ellipse is very elongated, i.e training case are parallel to each other, the direction of steepest decent is almost perpendicular to the direction towards the minimum.

Learning the weights of logistic output neuron
----------------------------------------------

Logistic Neuron:
z = b + sum(xi, wi) where z = logit
y = 1 / (1 + e^-z) where y = output

Delta rule of learning
wi += xi * error * y * (1-y)

The Backpropagation Algorithm
-----------------------------
We don't know what the hidden units ought to do, but we can compute how fast the error changes as we change a hidden activity.

It is the algorithm for taking one training case, and computing efficiently for every weigths in the network; how error will change, on that particular training case, as you change the weights.

How to use the derivates computed by the backpropagation algorithm
------------------------------------------------------------------
To get a fully specified learning procedure, we still need to make a lot of other decisions about how to use these error derivatives:

How often to update the weight?
How do we use the error derivates on individual cases to discover a good set of weights?

Optimization issues!
How often to update?
Online version - after each training case
or
batch - after a full sweep through the training data
or 
Mini batch - after a small sample of training data

How much to update?
Adapt the learning rate.
Adapt the learning rate of each connection.

Overfitting:
Sample error.
Regularities in training data.

Ways to reduce overfitting:
Weight decay
Weight sharing
Early stoppping
Model averaging
Dropout
Generative pre-training

================================================================================================================

Lecture 4
==========

Learn to predict the next word
------------------------------
Learning relationships in family tree.
Neural Network automatically learns various structure of family tree in different parts of the hidden layers.

A brief diversion with cognitive science
----------------------------------------
Cognitive Science (Not for engineer)
A neural net can use vectors of semantic features to implement a relational graph.

Another diversion: The softmax output function
----------------------------------------------
Softmax Output Function:
Converting the outputs of a neural network to probability distribution.

Cross Entropy:
Best cost function for Softmax.

Neuro-probabilistic language models
-----------------------------------
Trigram method:
Take a huge amount of text and count the frequencies of all triplets of words.
Use these frequencies to make bets on the relative probabilites of words given previous two words.
Back of to two words predictions, if probability is zero in trigram method.

Learning to predict the next word in the sentence.
It's all about probablility of what the next word can be.
The word with larger probability wins.

Ways to deal with large number of possible outputs in neuro-probabilistic language models
-----------------------------------------------------------------------------------------
How to reduce the size of output layer?
1. Giving the next word as the input to the model, and calculating the logits.
2. Arrange all words in a binary tree with words as the leaves.
3. Word2Vec

t-sne is good way for visualisation.

==============================================================================================================

Lecture 5
=========

Why object recognization is difficult?
--------------------------------------
Segmentation: A lot of objects.
Lighting: 
Deformation: 
Viewpoint:

No dimension hoping in input.

Ways to achieve viewpoint invariance
------------------------------------
Invariant feature approach:
Extract a large, redundant set of features that are invariant under transformations.

Judicious Normalisation:
Put a box around the object in the image.

Convolutional Neural Network for hand-written digit recognition
---------------------------------------------------------------
Replicated feature approach:
Along with pooling to achieve translational invariance.

For e.g
Le Net 5

How to add prior knowledge to the network?
1. Connectivity.
2. Weight Constraints.
3. Neuron activation functions.
4. Create more training data.

Convolutional Neural Network for Object Recognition
---------------------------------------------------
Same net can be use with minor changes to object recognition.
Architecture:
1. 7 hidden layer (not counting max pool)
2. Early layers are convolutional.
3. The last two layers are globally connected
4. RELU layers as activation functions
5. Competitive normalisation to help with variations in intensity.
6. Transformations - by training on random 224 * 224 patches from 256 * 256 images to get more data.
7. Left/Right reflections.
8. At test time, combine the opinion from ten different patches.
9. Dropout - stops hidden units from relying two much on other hidden units.

GPUs are very good for matrix-matrix multiplications.
Train the network in week.

=====================================================================================

Lecture 6
=========

Overview of mini-batch gradient descent
---------------------------------------

Mini batch is better than online version.
Update the learning rate.

A bag of tricks for mini-batch gradient descent
-----------------------------------------------
Tricks to improve gradient descent:

Initialize with random weights for hidden units.
Shifting the inputs.
Scaling the input.
Decorrelate the inputs.

Don't turn down the learning rate too soon.

Four ways to speed up mini batch:
1. Momentum
2. Separate learning rates for each parameter.
3. rmsprop - Divide the learning rate for a weight by a running average of the magnitude of recent gradients.

The Momentum Method
-------------------
Momentum method with correction afterwards.
Instead of using gradients for updation in weights, use velocity, which is combination of previous velocity and current gradients.
Use low value of momentum initially, and gradually increase the momentum.

New technique for momentum method:
1. First make a big jump in the direction of the previous accumulated gradient.
2. Then measure the gradient where you end up and make a correction.

A separate, adaptive learning rate for each connection
------------------------------------------------------
Adaptive learning for each connection by using a variable to hold individual connection learning rate.
Tricks to work better:
1. Limit the gains to lie in some reasonable range.
2. Use full batch, or big mini-batches.
3. Use adaptive learning rate with momentum.

rmsprop: Divide the gradient by a running average of its recent magnitude
-------------------------------------------------------------------------
rprop: This combines the idea of only using the sign of the gradient with the idea of adapting the step size separately for each weight.

rprop does not work with mini-batches.

rmsprop: rprop with mini-batches.

Always think of extending your current idea to be generic.

Summary of learning methods for neural networks:
For small datasets or bigger datasets without much redundancy, use full-batch method.
- Conjugate gradient
- Adaptive learning, rprop

For big, redundant datasets, use mini-batch
- Try gradient dataset with momentum.
- Try rmsprop

Why there is no simple recipe?
1. Neural network differ a lot.
2. Task differ a lot.

==============================================================================================

Lecture 7
=========

Modeling sequences: A brief overview
------------------------------------
When applying machine learning to sequences, we often want to turn an input sequence into an output sequence that lives in a different domain.
or turn a sequence of sound pressures into sequence of word identities.

Sometimes, target is to predict the next term in input sequence.

Predicting the next sequence blurs the distinction between supervised and unsupervised learning.

Various models for modeling sequences:
1. Autoregressive models
Kind of Linear model

2. Feed forward neural network
Both 1 and 2 are memoryless models

Better than memoryless, have a model with a hidden state to store information.

Two types of hidden state model:
1. Linear Dynamical Systems
Hidden units are gaussian in nature.
Driving Input ---> Hidden ---> Output
					 |
Driving Input ---> Hidden ---> Output

2. Hidden Markov Models
Have a discrete one-of-N hidden state.

Very expensive model, since hidden state can't store large data.

Recurrent Neural Networks:
Very powerful

Linear Dynamical and Hidden Markov models are stochastic models
Recurrent neural networks are deterministic.

Behavious of RNN:
1. They can oscillate. Good for motor control?
2. They can settle to point attractors. Good for retrieving memories?
3. They can behave chaotically. Bad for information processing?

But, RNN is very expensive.

Training RNNs with backpropagation
----------------------------------
A recurrent network is just a layered net that keeps reusing the same weights.

Backpropagation through time algorithm:
We can think of the recurrent net as a layered, feed forward net with shared weights and then train the feed-forward net with weights constraints.
We can also think of this training algorithm in the time domain:
a. The forward pass builds up a stack of the activities of all units at each time step.
b. The backward pass peels activities off the stack to compute the error derivates at each time step.
c. After the backward pass, we add together the derivates at all the different times for each weight.

Along with learning the weights of the model, we can adapt the initial states.

Providing the input to recurrent network:
1. Specify the initial states of all the units as input.
2. Specify the initial states of subset of the units.
3. Specify the states of the same subset of the units at every time step.
This the most natural way to model sequential data.

Specify the target to recurrent network:
1. Specify desired final activities of all the units.
2. Specify desired final activities of all units for last few steps.
3. Specify the desired activity of a subset of the units.

A toy example of training a RNN
-------------------------------
Example: Adding up binary number

Problems with feed forward neural network:
1. We must decide in advance the maximum number of digits in each number.
2. The processing applied to the beginning of a long number does not generalise to the end of the long number because it uses different weights.

A recurrent neural net for binary addition:
1. Two input units and one output unit.
2. It is given two input digits at each time step.
3. The desired output at each time step is the output for the column that was provided as input two time steps ago.
	a. It takes one time step to update the hidden units based on the two input digits.
	b. It takes another time step for the hidden units to cause the output.
4. Three hidden units, fully interconnected in both directions.

Why it is difficult to train an RNN
-----------------------------------
In forward pass, we use squashing functions (like logistic) to prevent the activity vectors from exploding.
The backward pass, is completely linear. If you double the error derivates at the final layer, all the error derivates will double.

What happens to the magnitude of the gradients as we backpropogate through many layers?
1. If the weights are small, the gradient shrink exponentially and vice versa.
2. In an RNN trained on long sequences (100 time steps) the gradients can explode or vanish.
So, RNN have difficulties dealing with long-range dependencies.

Four effective ways to learn an RNN:
1. Long Short term memory
2. Hessian Free Optimization
3. Echo State Networks
4. Good initialisation with momentum.

Long term Short term memory (LSTM)
----------------------------------
Memory cell using logistic and linear units with multiplicative interaction.
1. Information gets into the cell whenever its "write" gate is on.
2. The information stays in the cell so long as its "keep" gate is on.
3. Information can be read from the cell by turning on its "read" gate.

Backpropogation with memory cell:
It works perfectly with binary gates to read or write the data in cell.

Task:
Reading cursive handwriting
1. The input is a sequence of (x, y, p) coordinates of the tip of the pen, where p indicates whether the pen is up or down.
2. The output is sequence of characters.

=======================================================================================================================

Lecture 8
=========

A brief overview of "Hessian-Free" optimization
-----------------------------------------------
The maximum error reduction depends on the ratio of the gradient to the curvature.
So, if the gradients are small, find models with very small curvature in error vs weights graph.

Newton's method:
The basic problem with steepest descent on a quadratic error surface is that the gradient is not the direction we want to go in.
a. If the error surface has circular cross-sections, the gradients is fine.
b. So lets apply a linear transformations that turn ellipses into circles.

Linear transformation is done by inversing the curvature matrix, which is expensive.
Since, curvature matrix has too many terms to be of use in big matrix.

How to avoid inverting a huge matrix?
a. Use only diagonal terms. But, this leads to loss of data.
b. Take an approximated matrix of the curvature matrix, using Hessian-Free or LBFGS.

The reason steepest descent goes wrong is that the gradient for one weight gets messed up by the simultaneous changes to all the other weights.

In HF method, we make an approximation to the curvature matrix and then, assuming that approximation is correct, we minimize the error using an efficient technique called conjugate gradient.

Conjugate gradient is guaranteed to find the minimum of an N-dimensional quadratic surface in N steps.
After many less than N steps it is typically got the error very close to the minimum value.

Conjugate gradient can be applied to batch based non-linear models as well.

Modeling character strings with multiplicative connections
----------------------------------------------------------
It's easy to handle character prediction rather than word prediction.
Preprocessing text to get words is a big hassle.

RNN model to predict next character.

1500 Hidden units -------------------> 1500 Hidden units
 (time t-1)									(time t)       -------------> Softmax (Prediction distribution of next word)

 						Character Input----->
 							1 to 86

Advantages:
a. Lot easier to predict 86 characters than 100,000 words.

Instead of using above mentioned model, we use tree based model.
The next hidden representation needs to depend on the conjunction of the current character and the current hidden representations.

Instead of using the inputs to the recurrent net to provide additive extra input to the hidden units, we could use the current input character to choose the whole hidden-to-hidden weight matrix.

Using factors to implement multiplicative interactions.

Learn to predict the next character using HF
--------------------------------------------
Using HF optimizer, it took month on a GPU board to get a really good model.

How to generate character strings from the model:
a. Start the model with default hidden state.
b. Give it a "burn in" sequence of characters and let it update its hidden state after each character.
c. Then look at the probability distribution it predicts the next character.
d. Pick a character randomly from the distribution and tell the net that this was the character that actually occured.
e. Continue to let it pick characters.
f. Look at the character strings it produces to see what it "knows".

Asking computers to complete the missing sentences to check what the computer knows.

It knows a lot about syntax but its very hard to pin down exactly what form this knowledge has.

RNN requires much less training data to reach the same level of performance as other models.
RNNs improve faster than other methods as the dataset get bigger.

Echo State networks (ESN)
-------------------------
Key idea:
Not training hidden-hidden layer, just training hidden-output layer.
Make early layers random and fixed.
The learning is then very simple (assuming linear output units).

Setting the random connnections in an Echo State networks:
Set the hidden->hidden weights so that the length of the activity vector stays above the same after each iteration.
This allows the input to echo around the network for the long time.
Use sparse connectivity. (Set most of the weights to zero)
Learning is very fast.

A simple example of an echo state network:
Input
A real-valued time-varying value that specifies the frequency of a sine wave.

Output:
A sine wave with the currently specified frequency.

Learning method:
Fit a linear model that takes the states of the hidden units as input and produces a single scalar unit.

The demonstrate that its very important to initialize weights sensibly.

They can do impressive modeling of one-dimensional time-series.

=================================================================================================================

Lecture 9
=========

Overview of ways to improve generalization
------------------------------------------
Overfitting:
The training data contains information about the regularities in the mapping from input to output. But it also contains sampling error.
The model fits both kinds of regularity. If the model is very flexible it can model the sampling error really well.

Preventing overfitting:
a. Get more data
b. Use a model that has right capacity
c. Average many different models.
d. Use a single neural network architecture, but average the predictions made by many different weight vectors.

Limit the capacity of a neural net:
a. Architecture: Limit the number of hidden layers and the number of units per layer.
b. Early stopping: Start with small weights and stop the learning before it overfits.
c. Weight decay: Penalize large weights using penalties or constraints on their squared values (l2) or absolute values (l1).
d. Noise: Add noise to the weights or the activities.

Typically, we use combination of above methods.

How to choose meta parameters that control capacity:
a. The wrong method is to try lot of alternatives and see what gives best performance on the test set.

Cross-validation:
Divide the total dataset into three subset:
a. Training data
b. Validation data
c. Test data

Using N subset as N-1 training data and 1 validation data. N-fold Cross Validation.

Early Stopping:
Start with smaller weights and let them grow until the performance on the validation set starts geting worse.

When the weights are very small, every hidden unit is in its linear range. That means logistic hidden units behaves like a linear unit.

Limiting the size of the weights
--------------------------------
Network with small weights is better than network with large weights.

The standard l2 weight penalty (weight decay) involves adding an extra term to cost function that penalizes the squared weights.

It prevents the network from using weights that it doesn't need.
If the network has two very similar inputs it prefer to put half the weight on each rather than all the weight on one.

l1 weight penalty uses absolute values of the weights. This makes many weights exactly equal to zero.

Instead of using weights penalties, we can also use weights constraints.
Put a constraint on the maximum squared length of the incoming weight vector of each unit.

Advantages:
a. Its easier to set a sensible value.
b. They prevent hidden units getting struck at zero.
c. They prevent weight exploding.

Using noise as a regularizer
----------------------------
Adding Gaussian noise to the inputs is exactly equal to l2 regularization on the weights.
In more complex nets, adding Gaussian noise to the weights of a multilayer non-linear neural net is not exactly to using a l2 weight penalty.
It works better in RNN.

Using noise in the activities as a regularizer.
Making the units binary and stochastic on the forward pass, but on the backward pass, doing it properly.

Introduction to the Bayesian Approach
-------------------------------------
Instead of considering only one set of hyper parameters, consider all the settings of hyper parameters as probability distribution.
The Bayesian framework assumes that we always have a prior distribution for everything.

Using a distribution instead of parameters values.

The Bayesian interpretation of weight decay
-------------------------------------------
Finding a weight vector that minimizes the squared residuals is equivalent to finding a weight vector that maximizes the log probability density of the correct answer.

We assume the answer is generated by adding Gaussian noise to the output of the neural network.

Minimizing squared error is the same as maximizing log prob under a Gaussian.

The constant in l2 regularization is actually a ratio between variance of data and the weights.

MacKay's quick and dirty method of fixing weight costs
------------------------------------------------------
We can interpret weight penalties as the ratio of two variances.
The best value of output noise is found by simply using the variance of the residual errors.

We don't need a validation set.

a. Start with guesses for both the noise variance and the weight prior variance.
b. Do some learning using the ratio of the variances as the weight penalty coefficient.
c. Reset the noise variance to be the variance of the residual errors.
d. Do step b & c again.

=====================================================================================================================

Lecture 10
==========

Why it helps to combine models
------------------------------
Combining network: The bias-variance trade-off
The bias term is big if the model has too little capacity to fit the data.
The variance term is big if the model has so much capacity that it is good at fitting the sampling error in each particular training set.
By averaging away the variance we can use individual models with high capacity.

The expected square error by picking a model at random is greater than the squared error by averaging the model by the variance of the outputs of the model.

Overview of ways to make predictors differ:
a. Rely on the learning algorithm getting struck in different local optima.
b. Use lot of different kinds of models, including ones that not a neural network.
c. For neural network models, make them different by using:
	i. Different number of hidden layers.
	ii. Different number of units per layer.
	iii. Different type of units.
	iv. Different types or strengths of weight penalty.
	v. Different learning algorithms.

Making models differ by changing their training data:
a. Bagging: Train different models on different subsets of data
	i. Random forests
Using neural nets with bagging is very expensive.

b. Boosting: Train a sequence of low capacity models. Weigth the training cases differently for each model in the sequence.

Mixtures of Experts
-------------------
We can look at the input data for a particular case to help us decide which model to rely on.
This may allows particular models to specialize in a subset of the training cases.
They do not learn on cases for which they are not picked. So they can ignore stuff they are not good at modeling.

They key idea is to make each expert focus on predicting the right answer for the cases where it is already doing better than the other experts.

A spectrum of models:
a. Very local models
	i. Nearest neighbors

b. Fully global models:
	i. Polynomial

c. Multiple local models
Instead of using a single global model or lots of very local models, use several models of intermediate complexity.

Partitioning based on input alone versing partitioning based on input-output relationship:
We need to cluster the training cases into subsets, one for each local model.

An error function that encourages cooperation:
We compare the average of all the predictors with the target and train to reduce the discrepancy.

An error function that encourages specialization:
We compare each predictor separately with the target.
We also use a "manager" to determine the probability of picking each expert.

The idea of full Bayesian learning
----------------------------------
Instead of trying to find the best single settings of the parameters compute the full posterior distribution over all possible parameter settings.
To make predictions, let each different settings of the parameters make its own prediction and then combine all these predictions by weighting each of them by the posterior probability of that setting of the parameter.

It is very computationally intensive.

This approach allows to use complicated models even when we don't have much data.

There is no reason why the amount of data should influence our prior beliefs about the complexity of the model.

Approximating full Bayesian learning in a neural net:
If the neural net only has a few parameters we could put a grid over the parameter space and evaluate p(w|d) at each grid-point.

Making full Bayesian learning practical
---------------------------------------
It might be good enough to just sample weight vectors according to their posterior probabilities.

In standard backpropagation, we keep moving the weights in the direction that decreases the cost.
Using sampling weight vectors, we add some Gaussian noise to the weight vector after each update.

If we use just the right amount of noise, and if we let the weight vector wander around for long enough before we take a sample, we will get an unbiased sample from the true posterior over weight vectors.
This is called 'Markov Chain Monte Carlo' method.

Full Bayesian learning with mini-batches:
If we compute the gradient of the cost function on random mini-batch we will get an unbiased estimate with sampling noise.

Dropout: an efficient way to combine neural nets
------------------------------------------------
Two ways to average models:
a. Mixture: We can combine models by averaging their output probabilities.
b. Product: We can combine models by taking the geometric means of their output probabilities.

Randomly omit each hidden units with probability 0.5
Dropout as a form of model averaging.
It's better than l2 and l1 regularizer.

During testing time, it's better to use all of the hidden units, but to halve their outgoing weights.

Use dropout of 0.5 in every layer in hidden layer.

In input layer, use dropout with higher probability of keeping an input unit.

If deep neural net is significantly overfitting, dropout will usually reduce the number of errors by a lot.

===================================================================================================================

Lecture 11
==========

Hopfield Nets
-------------
It is composed of binary threshold units with recurrent connections between them.
Each binary "configuration" of the whole network has an energy.

The global energy is the sum of many contributions. Each contribution depends on one connection weight and the binary states of two neurons.

Decision needs to be sequential:
a. Simultaneous decision can make the energy go up.
b. Simultaneous parallel updating can results in oscillations.

Memories could be energy minima of neural net.
The binary threshold decision rule can then be used to "clean up" incomplete or corrupted memories.

An item can be accessed by just knowing part of its content.
It is robust against hardware damage.

Storing memories in Hopfield net:
If we use activities of 1 and -1, we can store a binary state vector by incrementing the weight between any two units by product of their activities.
It is not error-driven.

Dealing with spurious minima in Hopfield Net
--------------------------------------------
The storage capacity of a Hopfield net:
The capacity of a totally connected net with N units is only about 0.15N memories.
A N bits per memory this is only 0.15N^2
This does not make efficient use of the bits required to store the weights.
After storing M memories, each connection weight has an integer value in range [-M, M]
So the number of bits required to store the weights and biases is N^2 log(2M+1)

Spurious minima limit capacity:
Each time we memorize a configuration, we hope to create a new energy minimum.
Two nearby minima merge to create a minimum at a intermediate location.
This limits the capacity of Hopfield net.

Avoiding spurious minima by unlearning:
Let the net settle from a random initial state, and then do unlearning.
This will get rid of deep, spurious minima and increase memory capacity.
This is what dreams are for.
Hopfield networks also provide a model for understanding human memory.

Increasing the capacity of a Hopfield net:
Instead of trying to store vectors in one shot, cycle through the training set many times.
Use the perceptron convergence procedure to train each unit to have the correct state given the states of all the other units in that vector.

Hopfield Nets with hidden units
-------------------------------
Instead of using the net to store memories, use it to construct interpretations of sensory input.
The input is represented by the visible units.
The interpretation is represented by the states of the hidden units.
The badness of the interpretation is represented by the energy.

Two difficult computational issues:
a. Search - How do we avoid getting trapped in poor local minima of the enery function?
b. Learning - How do we learn the weights on the connections to the hidden units and between the hidden units?

Using stochastic units to improve units
---------------------------------------
Noisy networks find better energy minima:
A Hopfield net always make decisions that reduce the energy.
This makes it impossible to escape the local minima.
We can use random noise to escape from poor minima.
Start with a lot of noise so its easy to cross energy barriers.
Slowly reduce the noise so that the system ends up in a deep minimum. This is called 'Simulated Annealing'.

Stochastic binary units:
Replace the binary threshold units by binary stochastic units that make biased random decisions.
The "temperature" controls the amount of noise.

Simulated Annealing led to Boltzmann machines.

Thermal equilibrium at a temperature 1:
Reaching thermal euilibrium does not mean that the system has settled down into the lowest energy configuration.
The thing that settles down is the probability distribution over configurations.

How a Boltzmann Machine models data
-----------------------------------
Stochastic Hopfield Nets with hidden units, also knows as Boltzmann Machines.
Given a training set of binary vectors, fit a model that will assign a probability to every possible binary vector.
This is useful for deciding if other binary vectors come from the same distribution.
It can be used for monitoring complex systems to detect unusual behaviour.

How a causal model generates data:
a. First pick the hidden states from thier prior distribution.
b. Then pick the visible states from their conditional distribution given the hidden states.

How a Boltzmann Machine generates data:
Everything is defined in terms of the energies of joint configurations of the visible and hidden units.

Getting a sample from the model:
If there are more than a few hidden units, we cannot compute the normalizing term because it has exponentially many terms.
So we use Markov Chain Monte Carlo to get samples from the model starting from the random global configuration.
Keep picking units at random and allowing them to stochastically update their states based on their energy gaps.
Run the Markov chain until it reaches stationary distribution.

====================================================================================================================

Lecture 12
==========

The Boltzmann Machine learning algorithm
----------------------------------------
The goal of learning:
We want to maximize the product of the probabilities that Boltzmann machine assigns to the binary vectors in the training set.

Learning is very difficult:
Learning algorithm must know all the weights.
But, a very surprising fact is everything one weight needs to know about the other weights and the data is contained in the difference of two correlations.
It includes both the learning and unlearning in a same learning algorithm. Positive and negative phase.

Why the derivate is so simple:
The probability of a global configuration at thermal equilibrium is an exponential function of the energy. Hence, settling to equilibrium makes the log probability a linear function of the energy.
The energy is a linear function of the weights and states.
Hence, the process of settling to thermal equilibrim propogates information about the weights.
We don't need backprop.

An inefficient way to collect the statistics required for learning:
Positive phase:
a. Clamp the data vector on the visible units and set the hidden units to random binary states.
b. Update the hidden units one unit at a time until the network reaches thermal equilibrium at the temperature 1.
c. Sample <sisj> for every connected pair of units.
d. Repeat for all data vectors in training set and average.

Negative phase:
a. Set all the units to random binary states.
Rest is same as that of positive phase.

More efficient way to get the statistics
----------------------------------------
If we start from a random state, it may take a long time to reach thermal equilibrium. Also, its very hard to tell when we get there.
Start from whatever state you ended up in last time you saw that data vector. This stored state is called a "particle".

Positive phase:
a. Keep a set of "data-specific particles", one per training case. Each particle has a current value that is a configuration of the hidden units.
b. Sequentially update all the hidden units a few times in each particle with the relevant data vector clamped.
c. For each connected pair of units, average SiSj over all data-specific particles.

Negative phase:
a. Keep a set of "fantasy particles". Each particle has a value that is global configuration.
b. Rest is same but, using "fantasy particles".

It works brilliantly with full batch.

It doesn't apply to mini-batch, because by the time we get back to the same data vector again, the weights will have been updated many times.

In order to overcome this, we make strong assumption about how we understand the world:
When a data vector is clamped, we will assume that the set of good explanations is uni-modal.
We restrict ourselves to learning models in which one sensory input vector does not have multiple very different explanations.

The simple mean field approximation:
a. If we want to get the statistics right, we need to update the units stochastically and sequentially.
prob(si) = f(bi + sigma(sj * Wij))
b. But, if we are in hurry we can use probabilities instead of binary state, and update the units in parallel.
prob(Pt+1) = f(bi + sigma(Pt * Wij))
c. To avoid biphasic oscillations we can use damped mean field.

An efficient mini-batch learning procedure for Boltzmann machines:
Positive phase:
Initialize all the hidden probabilities at 0.5
a. Clamp a data vector on the visible units.
b. Update all the hidden units in parallel until convergence using mean field updates.
c. After the net has converged, record PiPj for every connected pair units and average this over all data in the mini-batch.

Negative phase:
Keep a set of "fantasy particles". Each particle has a value that is a global configuration.
a. Sequentially update all the units in each fantasy particle a few times.
b. For each connected pair of units, average SiSj over all the fantasy particles.

Making the updates more parallel:
Special architecture that allows parallel updates which are much more efficient.
- No connections within a layer.
- No skip-layer connections.

This is called Deep Boltzmann Machine (DBM)
It's general Boltzmann Machine with a lot of missing connections.

To check whether the model is learned:
Remove all the inputs and generate samples from the model.

DBM can be used to learn to recognize MNIST.

The learning raises the effective mixing rate:
The learning interacts with the Markov chain that is being used to gather the "negative statistics".

How fantasy particles move between the model's modes:
If a mode has more fantasy particles than data, the energy surface is raised until the fantasy particles escape.
The energy surface is being changed to help mixing in addition to defining the model.

Restricted Boltzmann Machines
-----------------------------
We restrict the connectivity to make inference and learning easier.
- Only one layer of hidden layers.
- No connections between hidden units.

In an RBM it only takes one step to reach thermal equilibrium when the visible units are clamped.

PCD: An efficient mini-batch learning procedure for RBM:
Positive phase:
Clamp a datavector on the visible untis.
a. Compute the exact value of <vihj> for all pairs of a visible and hidden units.
b. For every connected pair of units, average <vihj> over all data in the mini-batch.

Negative phase:
Keep a set of "fantasy particles". Each particle has a value that is a global configuration.
a. Update each fantasy particle a few times using alternating parallel updates.
b. For every connected pair of units, average <vihj> over all fantasy particles.

Inefficient version of the Boltzmann machine learning algorithm for an RBM:
Start with a training vector on the visible units. Then alternate between updating the hidden units in parallel and updating all the visible units in parallel.
Measuring statistics at equillibrium (infinity)
delta(wij) = epsilon * (<vihj>0 - <vihj>infinity)

Contrastive divergence: A surprising short-cut
delta(wij) = epsilon * (<vihj>0 - <vihj>1)

When does the shortcut fail?
We need to worry about regions of the data-space that the model likes but which are very far from any data.
These low energy holes cause the normalisation term to be big and we cannot sense them if we use the shortcut.
A good compromise between speed and correctness is to start with small weights and use CD1, once the weights grow, the Markov chain mixes more slowly so we use CD3. And so on.

An example of Contrastive Divergence Learning:
In order to reconstruct only one handwritten digit, the model can work with 50 hidden units in first layer.
For 10 digit classes, first layer can work with 500 hidden layers.

RBMs for collaborative filtering
--------------------------------
Collaborative filtering: The Netflix competition

Using language model:
The data is strings of triples of the form: User, movie, and rating
But, instead of using large hidden units, the model works well with just scalar product of user features and movie features.
And it is equivalent to matrix factorization.

An RBM alternative to matrix factorization:
Suppose we treat each user as a training case.
- A user is a vector of movie ratings.
- There is one visible unit per movie and its a 5 way softmax.
- The CD learning rule for a softmax is the same as for a binary unit.
- There are ~100 hidden units.
One of the visible values in unknown.
- It need to filled by the model.

How to avoid dealing with all those missing ratings:
a. For each user, use an RBM that only has visible units for the movies the user rated.
b. So instead of one RBM for all users, we have different RBM for every user.
- All the RBMs use the same hidden units.
- The weights from each hidden unit to each movie are shared by all the users who rated the movie.

================================================================================================================================

Lecture 13
==========

The ups and downs of backpropagation
------------------------------------
A brief history of backprop:
The backpropagation algorithm for learning multiple layers of features was invented several times in 70's and 80's.
Multiple researchers worked on this independently for 20 years.
But by the late 1990's most serious researchers in machine learning had given up on it.

Why backprop failed?
The popular explanation of why backprop failed in the 90's:
a. It could not make good use of multiple hidden layers.
b. It did not work well in RNN or deep auto-encoders.
c. SVM worked well, and better than backprop.

The real reason it failed:
a. Computers were thousands of times too slow.
b. Labeled datasets were hundreds of times too small.
c. Deep networks were too small and not initialised sensibly.

A spectrum of machine learning tasks:
Typical statistics 										Artificial Intelligence
a. Low dimensional data (less than 100 dimensions)		a. High-dimensional data (more than 100 dimensions)
b. Lots of noise in the data.							b. The noise is not the main problem.
c. Not much structure in the data.						c. There is a huge amount of structure in the data, but its too complicated
The structure can be captured by a fairly				to represented by a simple model.
simple model.
d. The main problem is separating true structure		d. The main problem is figuring out a way to represent the complicated structure
from noise.												so that it can be learned.

Support Vector Machines were never a good bet for AI tasks that need good representations:
SVMs are just a clever reincarnation of Perceptrons.

Belief Nets
===========
What is wrong with backprop?
a. It requires labeled training data.
b. The learning time does not scale well.
It is very slow in networks with multiple hidden layers.
It can stuck in local optima.

Overcoming the limitations of back-propogation by using unsupervised learning:
Keep the efficiency and simplicity of using of gradient method for adjusting the weights, but use it for modeling the structure of the sensory inputs.
- Adjust the weights to maximize the probability that a generative model would have generated the sensory input.

What kind of generative model should we learn?
a. An energy-based model like a Boltzmann machine.
b. A causal model made of idealized neurons.
c. A hybrid of two.

Artificial Intelligence and Probability:
In early AI, using probability is considered to be very stupid.

The marriage of graph theory and probability theory:
In 1980, AI majorly consists of expert systems.

Belief nets:
It is a directed acyclic graph composed of stochastic variables.

Graphical Models vs Neural Networks:
a. Early graphical models used experts to define the graph structure and the conditional probabilities.
Researchers initially focused on doing correct inference, not on learning.
b. For neural nets, learning was central. Hand-wiring the knowledge was not cool.
Knowledge came from learning the training data.

Two types of generative neural network composed of stochastic binary neurons:
a. Energy-based
We connect binary stochastic neurons using symmetric connections to get a Boltzmann machine.
b. Causal
We connect binary stochastic neurons in a directed acyclic grpah to get a Sigmoid Belief net.

Learning Sigmoid Belief Nets
----------------------------
It is easy to generate an unbiased example at the leaf nodes, so we can see what kinds of data the network believes in.
It is hard to infer the posterior distribution over all possible configurations of hidden causes.
It is hard to even get a sample from the posterior.

Learning rule for sigmoid belief nets:
Learning is easy if we can get an unbiased sample from the posterior distribution over hidden states given the observed data.
For each unit, maximize the log probability that its binary state in the sample from the posterior would be generated by the sampled binary states of its parents.
delta(wji) = epsilon * sj * (si - pi)

Even if two hidden causes are independent in the prior, they can become dependent when we observe an effect that they can both influence. This is called Explaining away.

To learn W, we need to sample from the posterior distribution in the first hidden layer.
Problem 1: The posterior is not factorial because of "explaining away".
Problem 2: The posterior depends on the prior as well as likelihood.
Problem 3: We need to integrate over all possible configurations in the hidden layers to get the prior for the first hidden layer.

Some methods for learning deep belief nets:
a. Monte Carlo methods
But, it is pretty slow in large, deep belief nets.
b. Variational methods
They only get approximate samples from the posterior.

The wake-sleep algorithm
------------------------
It's hard to learn complicated models like Sigmoid Belief Nets.
The problem is that it's hard to infer the posterior distribution over hidden configurations when given a datavector.

Crazy idea:
Do the inference wrong; from other distribution.
Maybe learning will work.
This turns out to be true for SBNs.

At each hidden layer, we assume that the posterior over hidden configurations factorizes into a product of distributions for each separate hidden units.

Algorithm:
Wake phase:
Use recognition weights to perform bottom-up pass.
- Train the generative weights to reconstruct activites in each layer from the layer above.
Sleep phase:
Use generative weights to generate samples from the model.
- Train the recognition weights to reconstruct activites in each layer from the layer below.

Flaws:
a. The recognition weights are trained to invert the generative model in parts of the space where is no space.
b. The recognition weights do not follow the gradient of the log probability of the data. They only approximately follow the gradient of the variational bound of the probability.
c. The posterior over the top hidden layer is very far from independent because of explaining away effects.

Maybe it is how brain works.

Mode averaging:
The best recognition model you can get is when you assume that the posterior over hidden states factorizes.

=====================================================================================================================

Lecture 14
==========

Learning layers of features by stacking RBMs
--------------------------------------------
a. First train a layer of features that receive input directly from the pixels.
b. Then train the activations of the trained features as if they were pixels and learn features of features in second hidden layer.
c. Then do it again.
Each time we add another layer of features we improve a variational lower bound on the log probability of generating the training data.

Combining two RBMs to make a DBN:
Data ----> h1 ----> h2 <----> h3
     <----    <----
      W1		W2		W3

To generate model:
1. Get an equilibrium sample from the top-level RBM by performing alternating Gibbs sampling for a long time.
2. Perform a top-down pass to get states for all the other layers.
The lower level bottom-up connections are not part of the generative model. They are just used for inference.

Averaging factorial distributions:
If you average some factorial distributions, you do NOT get a factorial distribution.

Why does greedy learning work?
We can express RBM model as
p(v) = epsilon(p(h) * p(v|h))
If we improve p(h), we will improve p(v)

Fine-tuning with a contrastive version of sleep-wake algorithm:
After learning many layers of features, we can fine-tune the features to improve generation.
a. Do a stochastic bottom-up pass.
- Then adjust the top-bottom weights of lower layers to be good at reconstructing the feature activities in the layer below.
b. Do a few iterations of sampling in the top level RBM.
- Then adjust the weights in the top-level RBM using CD.
c. Do a stochastic top-down pass
- Then adjust the bottom-up weights to be good at reconstructing the feature activities in the layer above.

The DBN used for modeling the joint distribution of MNIST digits and the labels:
28 * 28      --------> 500 units --------> 500 units <---------> 2000
pixel image  <--------			 <--------						units
										   10 labels <---------> 

a. The first two hidden layers are learned without using labels.
b. The top layer is learned as an RBM for modeling the labels concatenated with the features in the second hidden layer.
c. The weights are then fine-tuned to be a better generative model using contrastive hidden wake-sleep.

This model will inference, as well as generate handwritten digits.

Discriminative fine-tuning for DBNs
-----------------------------------
a. First learn one layer at a time by stacking RBMs.
b. Treat this as "pre-training" that finds a good initial set of weights which can then be fine-tuned by a local search procedure.
- Contrastive wake-sleep is a way of fine-tuning the model to be better at generation.
c. Backprop can be used to fine-tune the model to be better at discrimination.

Why backprop works better with greedy pre-training:
We do not start backprop until we already have sensible feature detectors that should already be very helpful for the discrimination task.
So the initial gradients are sensible and backprop only needs to perform a local search from a sensible starting point.

Most of the information in the final weights comes from modeling the distribution of input vectors.
- The input vectors generally contains a lot more information than the labels.
- The precious information in the label is only used for the fine-tuning.

Using DBN with softmax layer at the top with backprop for classifying MNIST digits.

What happens during discriminative fine-tuning?
----------------------------------------------
Network with pretraining (via Unsupervised learning technique) always do better than network without pretraining.
Model with more layers and pretraining do better than model with more layers, but without pretraining.

Model with pretraining and model without pretraining contains no overlapping.

Why unsupervised pre-training makes sense:
Stuff -----> (High bandwidth) Image
Stuff -----> (Low bandwidth) Label

Label of the image is determined by the image as well as interpretation of image in the real world.
Hence, it makes sense to first learn to recover the stuff that caused the image by inverting the high bandwidth pathway.

Modeling real-valued data with an RBM
-------------------------------------
Model pixels as a Gaussian variables. Alternating Gibbs sampling is still easy, though learning needs to be much slower.

Gaussian-Binary RBM's:
Lots of people have failed to get these to work properly. Its extermely hard to learn tigh variances for the visible units.
We need many more hidden units than visible units.

Stepped Sigmoid units:
a. Make many copies of a stochastic binary unit.
b. All copies have the same weights and the same adaptive bias b, but they have different fixed offsets to the bias.

Contrastive divergence learning works well for the sum of stochastic logistic units with offset biases.
It also works for rectified linear units. These are much faster to compute than the sum of many logistic units with different biases.

A nice property of rectified linear units:
If a relu has a bias of zero, it exhibits scale invariance.
R(ax) = aR(x)
It is like the equivariance to translation exhibited by convolutional nets.
R(shift(x)) = shift(R(x))

RBMs are Infinite Sigmoid Belief Nets
-------------------------------------
Inference in the directed net is exactly equivalent to letting an RBM settle to equilibrium starting at the data.
Inference is equal to generation.

=====================================================================================================================

Lecture 15
==========

From Principal Components Analysis to Autoencoders
--------------------------------------------------
PCA:
This takes N-dimensional data and finds the M orthogonal directions in which the data have the most variance.
We reconstruct by using the mean value(over all the data) on N-M directions that are not represented.

Using backprop to implement PCA inefficiently:
Try to make the output be the same as the input in a network with a central bottleneck.
If the hidden and the output layers are linear, it will learn hidden units that are a linear function of the data and minimize the squared reconstruction error.
This is exactly what PCA does.

Using backprop to generalise PCA:
With a non-linear layers before and after the code, it should be possible to efficiently represent data that lies on or near a non-linear manifold.
- The encoder (Layers from input to code) converts coordinates in the input space to coordinates on the manifold.
- The decoder (Layers from code to the output) does the inverse mapping.

We are using supervised learning algorithm to do unsupervised learning.

Deep Autoencoders
-----------------
To do non-linear dimensionality reduction.
- They provide flexible mappings both ways.
- The learning time is linear in the number of training cases.
- The final encoding model is fairly compact and fast.

Problems:
Gradients used to die eventually.

Ways to optimize:
Use unsupervised layer-by-layer pre-training.
Just initialise the weights carefully as in Echo-State nets.

Deep Autoencoders on MNIST:
784 -> 1000 -> 500 -> 250 -> 30 linear units -> 250 -> 500 -> 1000 -> 784

Using stacks of RBMs and for layers till 30 linear units, and then using transpose of weights learned in decoding layers.
Then fine tuning with backprop.

It is used to compress the data, and reconstruct it.

Deep Autoencoders for document retrieval and visualisation
----------------------------------------------------------
How to find documents that are similar to a query document using Autoencoders:
a. Convert each document into a "bag of words".
b. We reduce each query vector to a much smaller vector that still contains most of the information about the content of the document.
c. Compare the smaller vector to other smaller vectors and find similar vectors.
d. Reconstruct similar vectors to documents.

2000 word counts -> 500 neurons -> 200 neurons -> 10 vectors -> 200 neurons -> 500 neurons -> 2000 word counts.

The non-linearity used for reconstructing bags of words:
At the output of the autoencoder, we use a softmax.
The resulting probability vector gives the probability of getting a particular word if we pick a non-stop word at random from the document.

It is better than Latent Semantic Analyis, and also provide better visualisation, when plotting on 2d graph with classes as different colors.

Semantic hashing
----------------
Storing similar documents along side in memory.

Binary descriptors are beneficial for image retrival.

Finding binary codes for documents:
a. Train an auto-encoder using 30 logistic units for code layer.
b. During the fine-tuning stage, add noise to the inputs to the code units.
or use binary stochastic units in the code layer during training.

2000 word counts -> 500 neurons -> 200 neurons -> 30 binary descriptors -> 200 neurons -> 500 neurons -> 2000 word counts.

Instead of restructing the document, we can use 30 binary descriptors as a memory address.
Similar addresses will have similar documents.

Learning binary codes for image retrieval
-----------------------------------------
A two-stage method:
a. First, use semantic hashing with 28-bit binary codes to get a long "shortlist" of promising images.
b. Then use 256-bit hashing codes to do a serial search for good matches.
- This only requires a few words of storage per image and the serial search can be done using fast bit-operations.

Euclidean distance is worse than using 256-bit codes.

How to make image retrival more sensitive to objects and less sensitive to pixels:
a. First train a big net to recognize lots of different types of object in real images.
b. Then use the activity vector in the last hidden layer as the representation of the image.

Shallow auto-encoders for pre-training
--------------------------------------

RBMs as auto-encoders:
When we train an RBM with one-step contrastive divergence, it tries to make the reconstructions look like data.
- It's strongly regularised by using binary activities in the hidden layer.

Stacking auto-encoders doesn't work like stacking RBMs.

Denoising auto-encoders:
Add noise to the input vector by setting many of its components to zero.
Pre-training is very effective if we use a stack of denoising auto-encoders.

Contractive auto-encoders:
Try to make the activites of the hidden units as insensitive as possible to the inputs by penalizing the squared gradient of each hidden activity with respect to the inputs.

Conclusions about pre-training:
a. For dataset that do not have huge numbers of labeled cases, pre-training helps subsequent discriminative learning.
- Especially if there is extra data that is unlabeled.
b. For large dataset, it is not necessary.
c. But, if we make the nets much larger we will need pre-training again.

==========================================================================================================================

Lecture 16
==========

Learning a joint model of images and captions
---------------------------------------------
Modeling the joint density of images and captions:
a. Train a multilayer model of images.
b. Train a separate multilayer model of word-count vectors.
c. Then add a new top layer that is connected to the top layers of both individual models.
- Use further joint training of the whole system to allow each modality to improve the earlier layers of other modality.

Instead of using a deep belief net, use a DBM that has symmetric connections between all pairs of layers.

Combining three RBMs to make a DBM:
The top and bottom RBMs must be pre-trained with the weights in one direction twice as big as in the other direction.

Hierarchical coordinate frames
------------------------------
Why convolutional neural network are doomed?
a. Pooling loses the precise spatial relationships between higher-level parts such as noise and a mouth.
b. Convolutional nets that just use translations cannot extrapolate their understanding of geometric relationships to radically new viewpoints.

The hierarchical coordinate frame approach:
Use a group of neurons to represent the conjunction of the shape of a feature and its pose relative to the retina.
Recognize larger features by using the consistency of the poses of the parts.

A crucial property of pose vectors:
They allow spatial transformations to be modeled by linear operations.
The invariant geometric properties of a shape are in weights, not in the activites.

Visual systems impose coordinate frames in order to represent shapes.

Bayesian optimization of neural network hyperparameters
-------------------------------------------------------
Lets machine learning figure out the hyper parameters!
One of the commonest reasons for not using neural network is that it requires a lot of skill to set hyper-parameters:
a. Number of layers
b. Number of units per layer
c. Type of unit
d. Weight penalty
e. Learning rate
f. Momentum etc

Naive grid search:
Make a list of alternative values for each hyper-params and then try all the possible combinations.

Sample random combinations:
This is much better if some hyper-param have no effect.

Using machine leanring to figure out hyper parameters:
Predict regions of the hyper-parameter space that might give better results.
Build a model that predicts the results knowing previous results with different settings of the hyper-parameters.

Gaussian Process models:
These models assume that similar inputs give similar outputs.
Instead of just telling the result with new hyper-parameter, the model should also tell how uncertain it is.
They predict a Gaussian distribution of values.

Keep track of the best settings so far.
Pick a setting of the hyper-parameter such that the expected improvement in our best setting is big.

The fog of progress
-------------------
The effect of exponential progress:
Over the short term, things change slowly and its easy to predict progress.
But, in the longer run our perception of the future hits a wall.
So the long term future of machine learning and neural network is a total mystery.

================================================================================================================

### help from
 - https://github.com/reetawwsum/Machine-learning-MOOC-notes/blob/master/Neural%20Networks%20for%20Machine%20Learning%20(University%20of%20Toronto).txt
