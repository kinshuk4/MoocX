## Introduction to Unscented Kalman Filter or UKF   

The UKF is an alternative technique to deal norming your process models, or nonlinear measurement models.  But instead of linearizing a nonlinear function, the UKF uses so-called  sigma points to approximate the probability distribution.  

This has two advantages.  

- In many cases the sigma points approximate the nonlinear transition  better than a linearization does.  
- Also it is not necessary to calculate Jacobian matrix.  

### Lesson outline:

- More sophisticated process (model) that can estimate the turn rate of a vehicle
- Go through how the Unscented Kalman Filter deals with this non-linear process model
- Implement the UKF in C++.