# L5 Lidar and Radar Fusion with Kalman Filters in C++

- Combining data from multiple sensors (as opposed to combining sequential data from one sensor) using Kalman Filters
- Estimate heading, location and speed of pedestrian in front of our car.

![img](./images/5-2.png)

### Lesson map

- Build an Extended Kalman Filter: Can handle more complex motion and measurement models

Processing flow:![img](./images/5-1.png)

1. Lidar and Radar: used to measure state and velocity of a pedestrian
2. Estimation triggered:
   - Predict: Predict pedestrian state (taking into account lapse time)
   - Apply filter depending on typo of measurement received:
     - If measurement provided by laser, apply Kalman Filter.
     - If measurement provided by Radar (non-linear (polar) measurement), may apply Extended Kalman Filter.

![img](./images/5-3.png)

### Flow

- Each sensor has its own prediction and update scheme. With multiple sensors, beliefs are updated asynchronously.

![img](./images/5-4.png)Notes:

- Laser measurement received at time k+1, radar measurement received at time k+2.
- If laser and radar measurements arrive at the same time, predict and update for one sensor first (e.g. laser) and then predict and update for the next sensor. The order doesn't matter.

### Kalman Filter Equations (1D case)

![img](./images/5-5.png)![img](./images/5-6.png)![img](./images/5-7.png)Notes:

- In the linear model, (1) velocity is constant and (2) our sensor only senses the pedestrian position.

// Write a function 'filter()' that implements a multi- // dimensional Kalman Filter for the example given //============================================================================ #include