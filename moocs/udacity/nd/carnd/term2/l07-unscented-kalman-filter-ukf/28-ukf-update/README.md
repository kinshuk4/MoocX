Congrats, you have made it almost through the UKF. This is the last step you need to learn, and then you will be ready to implement your own UKF and fuse data from different sensors. This is a very powerful and useful ability. So let's see how the final step works and how you can update your state and your covariance matrix with the measurement. What we have is this, the predicted state mean and covariance and the predicated measurement mean and covariance. But there's one more thing that we need, and this is the actual measurement zk+1 that we receive for the time step k+1. This is actually the first time we really need to know the measurement values. What we needed right from the beginning was the time of the measurement, so we could predict to the correct time. And later, the sensor type so we could use the correct measurement model in the measurement prediction step. But this is the first time where we look into the measurement values. The update calculation closes the processing chain and produces the updated state mean and the updated state covariance matrix. These equations are exactly the same as for the standard Kalman filter. The only difference here is how you calculate the Kalman gain K. Here you need the cross-correlation matrix between the predicted sigma points in the state space and the predicted sigma points in the measurement space. And that's it. With this, you're done processing the measurement at time step k+1, and you're ready to handle the next measurement at time step k+2. 

![7-31](../../images/7-31.png)

![7-32](../../images/7-32.png)

![7-33](../../images/7-33.png)

![7-34](../../images/7-34.png)

The update part of the update step is the last part of the UKF process that updates our state and your covariance matrix with the measurement

At this point in the process we have the predicted state mean and covariance and the predicted measurement mean and covariance. The last thing we need is the actual measurement: **z|(k+1)** that we receive for the time step **k+1**. This is the first time we need to know the measurement values. What we needed right from the beginning was the time of the measurement, so we could predict the correct time. Later, we needed the sensor type so we could use the correct measurement model in the measurement prediction step.

The update calculation closes the processing chain and produces the updated state means and covariance matrix. These equations are exactly the same as for the standard Kalman filter. The only difference here is how you calculate the Kalman gain **k**. Here you need the cross-correlation matrix between the predicted sigma points in the state space and the predicted sigma points in the measurement space.

That is it. Now we are done processing the measurement at time step **k+1** and we are ready to handle the next measurement at time step **k+2**.

