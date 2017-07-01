In this document, we review information from the Machine Learning 1 Supervised Learning course regarding instance based learning, with a focus on the k nearest neighbors algorithm. A brief extension beyond what was discussed in the course is also provided regarding locally weighted regression (LWR).

Most of the algorithms that we encounter in this course can be categorized as e ager learners . Decision trees, regression, neural networks, SVMs, Bayes nets: all of these can be described as eager learners. In these models, we fit a function that best fits our training data; when we have new inputs, the input’s features are fed into the function, which produces an output. Once a function has been computed, the data could be lost to no detriment of the model’s performance (until we obtain more data and want to recompute our function). For eager learners, we take the time to learn from the data first and sacrifice local sensitivity to obtain quick, globalscale estimates on new data points. Here, we look at an example of a lazy learner in the k nearest neighbors algorithm. In contrast to eager learners, lazy learners do not compute a function to fit the training data before new data is received. Instead, new instances are compared to the training data itself to make a classification or regression judgment. Essentially, the data itself is the function to which new instances are fit. While the memory requirements are much larger than eager learners (storing all training data versus just a function) and judgments take longer to compute than eager learners, lazy learners have advantages in localscale estimation and easy integration of additional training data.

## Quiz: Wont you compute my neighbours
C: Okay Michael, I have two quizzes for you. Okay? M: Yeah, yeah.
C: Here's the first quiz, and here's the way it's set up.
I want you to fill in the empty boxes of this table. Okay?
M: Ooh.
C: Got it.
M: There's a lot of empty boxes.
C: There's a lot of empty boxes.
M: Okay, but Okay, let me make sure I understand what's going on here. So we're looking at
three different algorithms that are learning algorithms.
C: Yep.
M: There's one One neural net
C: No
M: Okay,
one nearest
neighbor.
C: Mm-hm
M: K
nearest
neighbor
and linear
regression.
C: Yep
Copyright © 2014 Udacity, Inc. All Rights Reserved.
M: And for
each one
you want to
know running time and space.
C: Mm-hm.
M: And this is on n points I assume, yeah, n sort, what does it mean for data points to be
sorted?
C: So let's assume we're living in a world where all of our data points are you know in r one.
Okay.
M: Oh okay that well that. That could be sorted.
C: That could be. Yeah that could be sorted. And that you know where going to be out putting
some real numbers as well. So it points on a. On a number of lines. So to make things simple
for you. I'm going to say that the points that you are given are already sorted.
M: Oh alright. It is just a scalar. So then a query point is going to come in. And then its going
to be some value. And were going to have to find the nearest neighbor or do the regression or
whatever.
C: Right.
M: Alright now that's for running time. Now for space, the space of what?
C: How much space you are going to have to do in order to accomplish your task. How much
space you going to have to use in order to accomplish your task?
M: So this is kind of like the space that's representing the class enviro. Or the regression. After
training.
C: Yes. So actually that question about after training is important. You'll notice I've divided each
of these algorithms into two phases. There's the learning phase. How much time it takes to
learn. How much space you need to learn. Then there's the query phase. When I give you some
new value and you have to output and answer. What's the running time for that and what are
the space requirements for that? Okay? You got that?
M: Yeah
C: I want that for each one. Of these three algorithms.
M: Except for one nearest neighbor which the, it appears as though you filled in for me to get
me started.
C: Right so just to get you started and make it easier for you know to know what I'm talking
about. I'm talking about big O times here. Right. I'm not going to make you write out big O. Big
O is implicit. So if we look at one nearest neighbor, and we ask well what's the running time of
learning? Well, it's constant. Right? Because there's no learning.
M: I see. You just take that sorted set of data points and you just pass it along through the query
here.
C: Right. Now, you could say that" Well, I'm going to take the data points or I'm going to copy
them into this database," and so it's linear. But let's assume they already come in a database, or
some data structure that you can use, okay?
M: Gotcha.
C: Okay, so now that actually brings us to a nice little question about how much space,
learning takes here. And, well because you have to store those points, and keep them around.
The space requirements are big O of N.
Copyright © 2014 Udacity, Inc. All Rights Reserved.
M: Yeah, that makes sense.
C: Okay, good. So given that as an example. Do you think your one example in your database.
Mm, do you think you can use that to build up labels for all the rest of the phases of the different
algorithms?
M: Yeah, I think so.
C: Okay, cool. Go for it.

## Solution: Wont you compute my neighbours


## Quiz: Domain K NNowledge
C: Okay Michael, so here's our second quiz is a row. In the last quiz, we talked about running time and space time, but now we're going to talk about how the k-nn algorithm, actually works. And in particular how different choices between distance metrics. Values of k, and how you're going to put them together, can give you different answers, okay? So, what I have over here on the left is training data. This is a regression problem and you're training data is made up of xy pairs. X is two dimensional. Okay? So this is a function from R squared to some value in R1. 

The first dimension represents something and the second dimension represents something. Then there's some particular output over here. And what I want you to do is given a query point 4, 2 produce what the proper y or output ought to be, given all of this training did.

so I want you to do it in four different cases, I want you to do it in the case where, your distance matrix is euclidean in R2.

## Solution: Domain K NNowledge
Let ED be euclidean distance and MD as manhattan distance. 



## K NN Bias

## Curse of Dimensionality
As the number of features or equivalently dimensions grows that is as we add more and more features we go x of 1, x of two then we add x of three, add more and more of these features. 

    As those features grows or as the number of dimensions grow ,the amount of data ,that we need to generalize accurately also grows exponentially.

Now this is a problem of course because Exponentially means, bad in computer science land because when things are exponential they're effectively untenable. You just can't win.

Exponentially means bad. It means that we need more, and more data as we add features and dimensions. Now as a machine learning person this is a real problem because what you want to do, or like what your instinct tells you to do is, we've got this problem, we've got a bunch of data, we're not sure what's important. So why don't we just keep adding more and more and more features. You know, we've got all these sensors and we'll just add this little bit and this little bit, and we'll keep track of GPS location and we'll see the time of the day and we'll just keep adding stuff and then we'll figure out which ones are important. But the curse of dimensionality says that every time you add another one of these features. You add another dimension, to your input space, you're going to need exponentially more data, as you add those features, in order to be able to generalize accurately. This is a very serious problem, and it sort of captures, a little bit of what the difficulties are in k and n. If you have a di, if you have distance function or a similarity function, that assumes that everything is Relevant, or equally relevant, or important, and some of them aren't. You're going to have to see a lot of data before you can figure that out, sort of before it washes itself away.