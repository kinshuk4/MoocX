//
// Created by Matthew Zimmer on 4/1/17.
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

void UKF::PredictMeanAndCovariance(VectorXd* x_out, MatrixXd* P_out) {

  //set state dimension
  int n_x = 5;

  //set augmented dimension
  int n_aug = 7;

  //define spreading parameter
  double lambda = 3 - n_aug;

  //create example matrix with predicted sigma points
  MatrixXd Xsig_pred = MatrixXd(n_x, 2 * n_aug + 1);
  Xsig_pred <<
            5.9374,  6.0640,   5.925,  5.9436,  5.9266,  5.9374,  5.9389,  5.9374,  5.8106,  5.9457,  5.9310,  5.9465,  5.9374,  5.9359,  5.93744,
          1.48,  1.4436,   1.660,  1.4934,  1.5036,    1.48,  1.4868,    1.48,  1.5271,  1.3104,  1.4787,  1.4674,    1.48,  1.4851,    1.486,
          2.204,  2.2841,  2.2455,  2.2958,   2.204,   2.204,  2.2395,   2.204,  2.1256,  2.1642,  2.1139,   2.204,   2.204,  2.1702,   2.2049,
          0.5367, 0.47338, 0.67809, 0.55455, 0.64364, 0.54337,  0.5367, 0.53851, 0.60017, 0.39546, 0.51900, 0.42991, 0.530188,  0.5367, 0.535048,
          0.352, 0.29997, 0.46212, 0.37633,  0.4841, 0.41872,   0.352, 0.38744, 0.40562, 0.24347, 0.32926,  0.2214, 0.28687,   0.352, 0.318159;

  //create vector for weights
  VectorXd weights = VectorXd(2*n_aug+1);

  //create vector for predicted state
  VectorXd x = VectorXd(n_x);

  //create covariance matrix for prediction
  MatrixXd P = MatrixXd(n_x, n_x);


/*******************************************************************************
 * Student part begin
 ******************************************************************************/

  //set weights
  double w_i = 1/(2*(lambda+n_aug));

  size_t N = 2*n_aug+1;
  for(int i = 0; i < N; i++) {
    if(i==0) {
      weights(i) = lambda/(lambda+n_aug);
    } else {
      weights(i) = w_i;
    }
  }

  double w_sum;
  std::cout << "weights sum = " << weights.sum() << std::endl;


  //predict state mean
  for(int i = 0; i < N; i++) {
    x = x + weights(i)*Xsig_pred.col(i);
  }

  //predict state covariance matrix
  VectorXd x_diff;
  size_t N_P = n_x;
  for(int i = 0; i < N; i++) {
    x_diff = Xsig_pred.col(i)-x;
    //angle normalization
    /**
     * When calculating the predicted state covariance matrix,
     * in the equation we always need the difference between
     * the mean predicted state and a sigma points. The problem
     * here is that the state contains an angle. As you have
     * learned before, subtracting angles is a problem for Kalman
     * filters, because the result might be 2π plus a small angle,
     * instead of just a small angle. That’s why I normalize
     * the angle here.
     *
     *
     *
     * LIFE LESSON:
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     *          Make sure you always normalize when you calculate the difference between angles
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    while (x_diff(3)> M_PI) x_diff(3)-=2.*M_PI;
    while (x_diff(3)<-M_PI) x_diff(3)+=2.*M_PI;

    P = P + weights(i) * x_diff * x_diff.transpose();
  }


/*******************************************************************************
 * Student part end
 ******************************************************************************/

  //print result
  std::cout << "Predicted state" << std::endl;
  std::cout << x << std::endl;
  std::cout << "Predicted covariance matrix" << std::endl;
  std::cout << P << std::endl;

  //write result
  *x_out = x;
  *P_out = P;
}
#pragma clang diagnostic pop