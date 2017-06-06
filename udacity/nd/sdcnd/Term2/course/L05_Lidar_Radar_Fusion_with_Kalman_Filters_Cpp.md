# L5 Lidar and Radar Fusion with Kalman Filters in C++

LIDAR and RADAR have their pros and cons, we combine both to get the best of them. Here is what we are going to learn:

1. Build. Extended Kalman Filters (Extended in a way that it will be capable of handling more complex motion and measurement models)
2. Processing flow:![img](./images/5-1.png)
   1. Lidar and Radar: First we have these 2 sensors used to estimate state and velocity of a pedestrian. This state is represented by 2D position and 2D velocity.
      ![img](./images/5-2.png)



### Lesson map

- Build an Extended Kalman Filter: Can handle more complex motion and measurement models

Processing flow:![img](./images/5-1.png)

To repeat again:

Imagine you are in a car equipped with sensors on the outside. The car sensors can detect objects moving around: for example, the sensors might detect a bicycle.

The Kalman Filter algorithm will go through the following steps:

- **first measurement** - the filter will receive initial measurements of the bicycle's position relative to the car. These measurements will come from a radar or lidar sensor.
- **initialize state and covariance matrices** - the filter will initialize the bicycle's position based on the first measurement.
- then the car will receive another sensor measurement after a time period Δt.
- **predict** - the algorithm will predict where the bicycle will be after time Δt. One basic way to predict the bicycle location after Δt is to assume the bicycle's velocity is constant; thus the bicycle will have moved velocity * Δt. In the extended Kalman filter lesson, we will assume the velocity is constant; in the unscented Kalman filter lesson, we will introduce a more complex motion model.
- **update** - the filter compares the "predicted" location with what the sensor measurement says. The predicted location and the measured location are combined to give an updated location. The Kalman filter will put more weight on either the predicted location or the measured location depending on the uncertainty of each value.
- then the car will receive another sensor measurement after a time period Δt. The algorithm then does another **predict** and **update** step.



### Estimation Problem Refresh

This is how a single sensor flow works:

![img](./images/5-3.png)

### Flow

Now we have 2 sensors measuring and update the data. This is how we handle it:

- Each sensor has its own prediction and update scheme. With multiple sensors, beliefs are updated asynchronously.

![img](./images/5-4.png)Notes:

- Laser measurement received at time k+1, radar measurement received at time k+2.
- If laser and radar measurements arrive at the same time, predict and update for one sensor first (e.g. laser) and then predict and update for the next sensor. The order doesn't matter.

#### Definition of Variables

- x is the mean state vector. For an extended Kalman filter, the mean state vector contains information about the object's position and velocity that you are tracking. It is called the "mean" state vector because position and velocity are represented by a gaussian distribution with mean x.
- P is the state covariance matrix, which contains information about the uncertainty of the object's position and velocity. You can think of it as containing standard deviations.
- k represents time steps. So xk refers to the object's position and velocity vector at time k.
- The notation k+1∣k refers to the prediction step. At time k+1, you receive a sensor measurement. Before taking into account the sensor measurement to update your belief about the object's position and velocity, you predict where you think the object will be at time k+1. You can predict the position of the object at k+1 based on its position and velocity at time k. Hence xk+1∣k means that you have predicted where the object will be at k+1 but have not yet taken the sensor measurement into account.
- xk+1 means that you have now predicted where the object will be at time k+1 and then used the sensor measurement to update the object's position and velocity.

### Kalman Filter Equations (1D case)

![img](./images/5-5.png)![img](./images/5-6.png)![img](./images/5-7.png)

### Kalman Filter Intuition

The Kalman equation contains many variables, so here is a high level overview to get some intuition about what the Kalman filter is doing.

##### Prediction

Let's say we know an object's current position and velocity , which we keep in the x variable. Now one second has passed. We can predict where the object will be one second later because we knew the object position and velocity one second ago; we'll just assume the object kept going at the same velocity.

The x′=Fx+ν equation does these prediction calculations for us.

But maybe the object didn't maintain the exact same velocity. Maybe the object changed direction, accelerated or decelerated. So when we predict the position one second later, our uncertainty increases. P′=FPFT+Q represents this increase in uncertainty.

Process noise refers to the uncertainty in the prediction step. We assume the object travels at a constant velocity, but in reality, the object might accelerate or decelerate. The notation ν∼N(0,Q) defines the process noise as a gaussian distribution with mean zero and covariance Q.

##### Update

Now we get some sensor information that tells where the object is relative to the car. First we compare where we think we are with what the sensor data tells us y=z−Hx′.

The K matrix, often called the Kalman filter gain, combines the uncertainty of where we think we are P′ with the uncertainty of our sensor measurement R. If our sensor measurements are very uncertain (R is high relative to P'), then the Kalman filter will give more weight to where we think we are: x′. If where we think we are is uncertain (P' is high relative to R), the Kalman filter will put more weight on the sensor measurement: z.

Measurement noise refers to uncertainty in sensor measurements. The notation ω∼N(0,R) defines the measurement noise as a gaussian distribution with mean zero and covariance R. Measurement noise comes from uncertainty in sensor measurements.

### A Note About the State Transition Function: Bu

If you go back to the video, you'll notice that the state transition function was first given as x′=Fx+Bu+ν.

But then Bu was crossed out leaving x′=Fx+ν.

B is a matrix called the control input matrix and u is the control vector.

As an example, let's say we were tracking a car and we knew for certain how much the car's motor was going to accelerate or decelerate over time; in other words, we had an equation to model the exact amount of acceleration at any given moment. Bu would represent the updated position of the car due to the internal force of the motor. We would use ν to represent any random noise that we could not precisely predict like if the car slipped on the road or a strong wind moved the car.

For the Kalman filter lessons, we will assume that there is no way to measure or know the exact acceleration of a tracked object. For example, if we were in an autonomous vehicle tracking a bicycle, pedestrian or another car, we would not be able to model the internal forces of the other object; hence, we do not know for certain what the other object's acceleration is. Instead, we will set Bu=0 and represent acceleration as a random noise with mean ν.

Notes:

- In the linear model, (1) velocity is constant and (2) our sensor only senses the pedestrian position.

### Kalman Filter Equations in C++

Now, let's do a quick refresher of the Kalman Filter for a simple 1D motion case. Let's say that your goal is to track a pedestrian with state x that is described by a position and velocity.

x=(pv)

##### Prediction Step

When designing the Kalman filter, we have to define the two linear functions: the state transition function and the measurement function. The state transition function is

x′=F∗x+noise,

where,

F=(10Δt1)

and x′ is where we predict the object to be after time Δt.

F is a matrix that, when multiplied with x, predicts where the object will be after time Δt.

By using the linear motion model with a constant velocity, the new location, p′ is calculated as

p′=p+v∗Δt,

where p is the old location and v, the velocity, will be the same as the new velocity (v′=v) because the velocity is constant.

We can express this in a matrix form as follows:

(p′v′)=(10Δt1)(pv)

Remember we are representing the object location and velocity as gaussian distributions with mean x. When working with the equation x′=Fx+noise*, we are calculating the mean value of the state vector. The noise is also represented by a gaussian distribution but with mean zero; hence, noise = 0 is saying that the mean noise is zero. The update equation then becomes x′=F*x

But the noise does have uncertainty. The uncertainty shows up in the Q matrix as acceleration noise.

##### Update Step

For the update step, we use the measurement function to map the state vector into the measurement space of the sensor. To give a concrete example, lidar only measures an object's position. But the extended Kalman filter models an object's position and velocity. So multiplying by the measurement function H matrix will drop the velocity information from the state vector x. Then the lidar measurement position and our belief about the object's position can be compared.

z=H∗x+w

where w represents sensor measurement noise.

So for lidar, the measurement function looks like this:

z=p′.

It also can be represented in a matrix form:

z=(10)(p′v′).

As we already know, the general algorithm is composed of a prediction step where I predict the new state and covariance, P.

And we also have a measurement update (or also called many times a correction step) where we use the latest measurements to update our estimate and our uncertainty.


```c++
// Write a function 'filter()' that implements a multi- // dimensional Kalman Filter for the example given 
#include <iostream>
#include "Dense"
#include <vector>

using namespace std;
using namespace Eigen;

//Kalman Filter variables
VectorXd x;	// object state
MatrixXd P;	// object covariance matrix
VectorXd u;	// external motion
MatrixXd F; // state transition matrix
MatrixXd H;	// measurement matrix
MatrixXd R;	// measurement covariance matrix
MatrixXd I; // Identity matrix
MatrixXd Q;	// process covariance matrix

vector<VectorXd> measurements;
void filter(VectorXd &x, MatrixXd &P);


int main() {
	/**
	 * Code used as example to work with Eigen matrices
	 */
//	//you can create a  vertical vector of two elements with a command like this
//	VectorXd my_vector(2);
//	//you can use the so called comma initializer to set all the coefficients to some values
//	my_vector << 10, 20;
//
//
//	//and you can use the cout command to print out the vector
//	cout << my_vector << endl;
//
//
//	//the matrices can be created in the same way.
//	//For example, This is an initialization of a 2 by 2 matrix
//	//with the values 1, 2, 3, and 4
//	MatrixXd my_matrix(2,2);
//	my_matrix << 1, 2,
//			3, 4;
//	cout << my_matrix << endl;
//
//
//	//you can use the same comma initializer or you can set each matrix value explicitly
//	// For example that's how we can change the matrix elements in the second row
//	my_matrix(1,0) = 11;    //second row, first column
//	my_matrix(1,1) = 12;    //second row, second column
//	cout << my_matrix << endl;
//
//
//	//Also, you can compute the transpose of a matrix with the following command
//	MatrixXd my_matrix_t = my_matrix.transpose();
//	cout << my_matrix_t << endl;
//
//
//	//And here is how you can get the matrix inverse
//	MatrixXd my_matrix_i = my_matrix.inverse();
//	cout << my_matrix_i << endl;
//
//
//	//For multiplying the matrix m with the vector b you can write this in one line as let’s say matrix c equals m times v.
//	//
//	MatrixXd another_matrix;
//	another_matrix = my_matrix*my_vector;
//	cout << another_matrix << endl;


	//design the KF with 1D motion
	x = VectorXd(2);
	x << 0, 0;
	
	P = MatrixXd(2, 2);
	P << 1000, 0, 0, 1000;
	
	u = VectorXd(2);
	u << 0, 0;
	
	F = MatrixXd(2, 2);
	F << 1, 1, 0, 1;
	
	H = MatrixXd(1, 2);
	H << 1, 0;
	
	R = MatrixXd(1, 1);
	R << 1;
	
	I = MatrixXd::Identity(2, 2);
	
	Q = MatrixXd(2, 2);
	Q << 0, 0, 0, 0;
	
	//create a list of measurements
	VectorXd single_meas(1);
	single_meas << 1;
	measurements.push_back(single_meas);
	single_meas << 2;
	measurements.push_back(single_meas);
	single_meas << 3;
	measurements.push_back(single_meas);
	
	//call Kalman filter algorithm
	filter(x, P);
	
	return 0;

}


void filter(VectorXd &x, MatrixXd &P) {

	for (unsigned int n = 0; n < measurements.size(); ++n) {

		VectorXd z = measurements[n];
		//YOUR CODE HERE
		
		// KF Measurement update step
		 VectorXd y = z - H * x;
	     MatrixXd Ht = H.transpose();
         MatrixXd S = H * P * Ht * R;
         MatrixXd Si = S.inverse();
         MatrixXd K = P * Ht * Si;
		// new state
		x = x + (K * y); // new state
        P = (I - K * H) * P; // covariance
		// KF Prediction step
		x = F * x + u;
      	MatrixXd Ft = F.transpose();
      	P = F * P * Ft + Q;
      
		std::cout << "x=" << std::endl <<  x << std::endl;
		std::cout << "P=" << std::endl <<  P << std::endl;


	}
}
```



Why do we not use the process noise in the state prediction function, even though the state transition equation has one? In other words, why does the code set u << 0, 0 for the equation x = F * x + u?

Answer: The noise mean is 0.

Looking closely at the process noise, we know from the Kalman Filter algorithm that its mean is zero and its covariance matrix is usually noted by Q * N(0,Q). The first equation only predicts the mean state. As the mean value of the noise is zero, it does not directly affect the predicted state. However, we can see that the noise covariance Q is added here to the state covariance prediction so that the state uncertainty always increases through the process noise.