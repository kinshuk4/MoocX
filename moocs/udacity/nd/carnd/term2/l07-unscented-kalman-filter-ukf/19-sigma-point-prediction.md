## Sigma Point Prediction

Now that we have generated sigma points, we can begin the second step of the prediction step: predicting sigma points

Using the matrix of augmented sigma points we simply insert every sigma point into the process model. We store every predicted sigma point as a column of a matrix. We call this matrix calligraphic **X_(k+1|k)**.

The input to the process model is a seven dimensional augmented vector, which makes sense. This makes sense since there are five state dimensions and two noise dimensions. The output is a five dimensional vector because this is what the process model returns, that means the matrix with the predicted sigma points has five rows and fifteen columns.

![7-22](../images/7-22.png)

You have now successfully mastered the generation of Sigma Points, and even implemented an example in C++. Really great job, and you know what? This was the most difficult and complicated part, all the remaining steps will be much simpler. Let's move on the prediction of Sigma Points. What we have now is this matrix of augmented Sigma Points. For the prediction step, we simply insert every Sigma Point into the process model. Again, we store every predicted Sigma Point as a column of a matrix, we call this matrix calligraphic Xk+1|k. 

![7-23](../images/7-23.png)

When you will bring this to C++ code, the main thing you have to do is implement this process model. One thing you should also consider, the input to the process model is a seven dimensional Augmented vector, which makes sense. These are five state dimensions and two noise dimensions. The output is a five dimensional vector, because this is what the process model returns, that means the matrix with the predicted Sigma Points has five rows and 15 columns. All right, that's everything you need to know about the prediction of Sigma Points. Let's see if you can implement this in C++. 