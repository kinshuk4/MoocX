import numpy as np
from math import sin, cos
from plot import plot_trajectory

class UserCode:
    def __init__(self):
        self.position = np.array([[0], [0]])

    def measurement_callback(self, t, dt, navdata):
        '''
        :param t: time since simulation start
        :param dt: time since last call to measurement_callback
        :param navdata: measurements of the quadrotor
        '''

        # compute transformation matrix
        psi = navdata.rotZ
        R = np.array([[cos(psi), -sin(psi)], [sin(psi), cos(psi)]])

        vx = navdata.vx
        vy = navdata.vy
        ds_local = np.array([[vx * dt],[vy * dt]])

        ds_global = np.dot(R, ds_local)

        self.position = self.position + ds_global

        # TODO: update self.position by integrating measurements contained in navdata
        plot_trajectory("odometry", self.position)
