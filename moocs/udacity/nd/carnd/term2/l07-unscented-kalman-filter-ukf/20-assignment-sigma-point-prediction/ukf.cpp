//
// Created by Matthew Zimmer on 3/30/17.
//

#include <iostream>
#include "ukf.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "IncompatibleTypes"

UKF::UKF() {
  //TODO Auto-generated constructor stub
  Init();
}

UKF::~UKF() {
  //TODO Auto-generated destructor stub
}

void UKF::Init() {

}


/*******************************************************************************
* Programming assignment functions:
*******************************************************************************/

void UKF::SigmaPointPrediction(MatrixXd *Xsig_out) {

  //set state dimension
  int n_x = 5;

  //set augmented dimension
  int n_aug = 7;

  //create example sigma point matrix
  MatrixXd Xsig_aug = MatrixXd(n_aug, 2 * n_aug + 1); // 7 x 15
  Xsig_aug <<
           5.7441, 5.85768, 5.7441, 5.7441, 5.7441, 5.7441, 5.7441, 5.7441, 5.63052, 5.7441, 5.7441, 5.7441, 5.7441, 5.7441, 5.7441,
          1.38, 1.34566, 1.52806, 1.38, 1.38, 1.38, 1.38, 1.38, 1.41434, 1.23194, 1.38, 1.38, 1.38, 1.38, 1.38,
          2.2049, 2.28414, 2.24557, 2.29582, 2.2049, 2.2049, 2.2049, 2.2049, 2.12566, 2.16423, 2.11398, 2.2049, 2.2049, 2.2049, 2.2049,
          0.5015, 0.44339, 0.631886, 0.516923, 0.595227, 0.5015, 0.5015, 0.5015, 0.55961, 0.371114, 0.486077, 0.407773, 0.5015, 0.5015, 0.5015,
          0.3528, 0.299973, 0.462123, 0.376339, 0.48417, 0.418721, 0.3528, 0.3528, 0.405627, 0.243477, 0.329261, 0.22143, 0.286879, 0.3528, 0.3528,
          0, 0, 0, 0, 0, 0, 0.34641, 0, 0, 0, 0, 0, 0, -0.34641, 0,
          0, 0, 0, 0, 0, 0, 0, 0.34641, 0, 0, 0, 0, 0, 0, -0.34641;

  //create matrix with predicted sigma points as columns
  MatrixXd Xsig_pred = MatrixXd(n_x, 2 * n_aug + 1); // 5 x 15

  double delta_t = 0.1; //time diff in sec
/*******************************************************************************
 * Student part begin
 ******************************************************************************/

  //predict sigma points
  //avoid division by zero
  //write predicted sigma points into right column
  VectorXd x;

  size_t N = 2 * n_aug + 1;
  for (int i = 0; i < N; i++) {
    x = Xsig_aug.col(i);

    double p_x = x(0);
    double p_y = x(1);
    double v = x(2);
    double yaw = x(3);
    double yaw_dot = x(4);
    double nu_a = x(5);
    double nu_yawdd = x(6);

    VectorXd a = VectorXd(5);
    if (fabs(yaw_dot) > 0.001) {
      a << (v / yaw_dot) * (sin(yaw + yaw_dot * delta_t) - sin(yaw)),
              (v / yaw_dot) * (cos(yaw) - cos(yaw + yaw_dot * delta_t)),
              0,
              yaw_dot * delta_t,
              0;
    } else {
      a << v * cos(yaw) * delta_t,
              v * sin(yaw) * delta_t,
              0,
              yaw_dot * delta_t,
              0;
    }

    VectorXd b = VectorXd(5);
    b << 0.5 * (delta_t * delta_t) * cos(yaw) * nu_a,
            0.5 * (delta_t * delta_t) * sin(yaw) * nu_a,
            delta_t * nu_a,
            0.5 * (delta_t * delta_t) * nu_yawdd,
            delta_t * nu_yawdd;
    VectorXd x_seg = x.segment(0, n_x);
    Xsig_pred.col(i) = x_seg + a + b;
  }


/*******************************************************************************
 * Student part end
 ******************************************************************************/

  //print result
  std::cout << "Xsig_pred = " << std::endl << Xsig_pred << std::endl;

  //write result
  *Xsig_out = Xsig_pred;

}

#pragma clang diagnostic pop