# Global Kinematic Model

Once again, the model we have developed:

##### x_t+1 = x_t + v_t * cos(ψ_t) * dt

##### y_t+1 = y_t + v_t * sin(ψ_t) * dt

##### ψ_t+1 = cy_t + v_t/L_f * delta_t * dt

##### v_t+1 = v_t + a_t * dt

We’ve added a variable to our state called **L_f** which measures the distance between the front of the vehicle and its center of gravity. The larger the vehicle , the slower the turn rate.

```c++
// In this quiz you'll implement the global kinematic model.
#include <math.h>
#include <iostream>
#include "Dense"

//
// Helper functions
//
double pi() { return M_PI; }
double deg2rad(double x) { return x * pi() / 180; }
double rad2deg(double x) { return x * 180 / pi(); }

const double Lf = 2;

// TODO: Implement the global kinematic model.
// Return the next state.
//
// NOTE: state is [x, y, psi, v]
// NOTE: actuators is [delta, a]
Eigen::VectorXd globalKinematic(Eigen::VectorXd state,
                                Eigen::VectorXd actuators, double dt) {
  Eigen::VectorXd next_state(state.size());

  //TODO complete the next_state calculation ...

  return next_state;
}

int main() {
  // [x, y, psi, v]
  Eigen::VectorXd state(4);
  // [delta, v]
  Eigen::VectorXd actuators(2);

  state << 0, 0, deg2rad(45), 1;
  actuators << deg2rad(5), 1;

  Eigen::VectorXd next_state = globalKinematic(state, actuators, 0.3);

  std::cout << next_state << std::endl;
}
```

**x, y, ψ, v** is the state of the vehicle, **L_f** is a physical characteristic of the vehicle, and **delta** & **a** are the actuators, or control inputs to our system. Now that we have this, we can use the model to write a simulation where we can develop a controller to follow a trajectory.
