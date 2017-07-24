The goal of this coding quiz is to predict the mean and the covariance of a radar measurement. We expect a radar measurement, so I set the measurement dimension to three here. This is an example how I set the weights. To be able to build the measurement covariance matrix r, you need the values for the radar measurement uncertainty. And of course, you need the predicted sigma points. This is the matrix, where you can store your measurement sigma points. And these are the two output objects, the predicted measurement mean, and the predicted covariance matrix, s. Good luck with the quiz. 

Steps:

- Transform sigma points into measurement space
- Calculate mean predicted measurement
- Calculate measurement covariance matrix S

### Helpful Equations

![7-30](../../images/7-30.png)