## Motion Models and Kalman Filters

In the extended kalman filter lesson, we used a *constant velocity* model (CV). A constant velocity model is one of the most basic motion models used with object tracking.

But there are many other models including:

- constant turn rate and velocity magnitude model (CTRV)
- constant turn rate and acceleration (CTRA)
- constant steering angle and velocity (CSAV)
- constant curvature and acceleration (CCA)

Each model makes different assumptions about an object's motion. In this lesson, you will work with the CTRV model.

Keep in mind that you can use any of these motion models with either the extended Kalman filter or the unscented Kalman filter, but we wanted to expose you to more than one motion model.

## CTRV model (Constant Turn Rate and Velocity Magnitude Model)  

### CV Model Limitation

In the last lesson (Extended Kalman Filter) we learn a process model with constant velocity.  However, with the assumption that the velocity is constant you're simplifying  the way vehicles actually moves because most roads has turns.  

But this is the problem because a process model with assumption of  constant velocity and direction will predict turning vehicles incorrectly.  

When vehicles are turning, they move slower. So EKF's predicted position would be outside of the circular path of the car.

![img](../images/7-3.png)