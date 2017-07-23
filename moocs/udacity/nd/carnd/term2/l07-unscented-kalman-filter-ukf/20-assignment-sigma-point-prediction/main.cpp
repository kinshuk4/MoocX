//
// Created by Matthew Zimmer on 3/30/17.
//

#include <iostream>
#include "Eigen/Dense"
#include <vector>
#include "ukf.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "IncompatibleTypes"
using namespace std;
using Eigen::MatrixXd;
using Eigen::VectorXd;
using std::vector;

int main() {

  //Create a UKF instance
  UKF ukf;

/*******************************************************************************
* Programming assignment calls
*******************************************************************************/

  MatrixXd Xsig_pred = MatrixXd(15, 5);
  ukf.SigmaPointPrediction(&Xsig_pred);

  return 0;
}

#pragma clang diagnostic pop