# -----------
# User Instructions
#
# Implement twiddle as shown in the previous two videos.
# Your accumulated error should be very small!
#
# Your twiddle function should RETURN the accumulated
# error. Try adjusting the parameters p and dp to make
# this error as small as possible.
#
# Try to get your error below 1.0e-10 with as few iterations
# as possible (too many iterations will cause a timeout).
# No cheating!
# ------------
 
from math import *
import random


# ------------------------------------------------
# 
# this is the robot class
#

class robot:

    # --------
    # init: 
    #    creates robot and initializes location/orientation to 0, 0, 0
    #

    def __init__(self, length = 20.0):
        self.x = 0.0
        self.y = 0.0
        self.orientation = 0.0
        self.length = length
        self.steering_noise = 0.0
        self.distance_noise = 0.0
        self.steering_drift = 0.0

    # --------
    # set: 
    #	sets a robot coordinate
    #

    def set(self, new_x, new_y, new_orientation):

        self.x = float(new_x)
        self.y = float(new_y)
        self.orientation = float(new_orientation) % (2.0 * pi)


    # --------
    # set_noise: 
    #	sets the noise parameters
    #

    def set_noise(self, new_s_noise, new_d_noise):
        # makes it possible to change the noise parameters
        # this is often useful in particle filters
        self.steering_noise = float(new_s_noise)
        self.distance_noise = float(new_d_noise)

    # --------
    # set_steering_drift: 
    #	sets the systematical steering drift parameter
    #

    def set_steering_drift(self, drift):
        self.steering_drift = drift
        
    # --------
    # move: 
    #    steering = front wheel steering angle, limited by max_steering_angle
    #    distance = total distance driven, most be non-negative

    def move(self, steering, distance, 
             tolerance = 0.001, max_steering_angle = pi / 4.0):

        if steering > max_steering_angle:
            steering = max_steering_angle
        if steering < -max_steering_angle:
            steering = -max_steering_angle
        if distance < 0.0:
            distance = 0.0


        # make a new copy
        res = robot()
        res.length         = self.length
        res.steering_noise = self.steering_noise
        res.distance_noise = self.distance_noise
        res.steering_drift = self.steering_drift

        # apply noise
        steering2 = random.gauss(steering, self.steering_noise)
        distance2 = random.gauss(distance, self.distance_noise)

        # apply steering drift
        steering2 += self.steering_drift

        # Execute motion
        turn = tan(steering2) * distance2 / res.length

        if abs(turn) < tolerance:

            # approximate by straight line motion

            res.x = self.x + (distance2 * cos(self.orientation))
            res.y = self.y + (distance2 * sin(self.orientation))
            res.orientation = (self.orientation + turn) % (2.0 * pi)

        else:

            # approximate bicycle model for motion

            radius = distance2 / turn
            cx = self.x - (sin(self.orientation) * radius)
            cy = self.y + (cos(self.orientation) * radius)
            res.orientation = (self.orientation + turn) % (2.0 * pi)
            res.x = cx + (sin(res.orientation) * radius)
            res.y = cy - (cos(res.orientation) * radius)

        return res




    def __repr__(self):
        return '[x=%.5f y=%.5f orient=%.5f]'  % (self.x, self.y, self.orientation)


# ------------------------------------------------------------------------
#
# run - does a single control run.


def run(params):
    myrobot = robot()
    myrobot.set(0.0, 1.0, 0.0)
    speed = 1.0 # motion distance is equal to speed (we assume time = 1)
    N = 100
    myrobot.set_steering_drift(10.0 / 180.0 * pi) # 10 degree bias, this will be added in by the move function, you do not need to add it below!
    old_CTE = myrobot.y
    int_error = 0
    error = 0
    for i in range(2*N):
        CTE = myrobot.y
        int_error += speed*cos(myrobot.orientation)*CTE
        steering = -params[0]*CTE  - params[1]*(CTE - old_CTE) - params[2]*int_error
        old_CTE = myrobot.y
        myrobot = myrobot.move(steering, speed)
        if i > N:
            error += CTE**2
    #print 'error = ', error
    return error


############## ADD CODE BELOW ####################
            
def twiddle(tol = 0.1):

    # Basic init without any sensible guesses
    n_params = 3
    params = [0.0 for col in range(n_params)] # aka [0.0,0.0,0.0]
    bumps = [1.0 for col in range(n_params)] # aka [1.0,1.0,1.0]

    # Educated guesses to reduce rounds and improve accuracy
    params = [2.0, 8.0, 0.0] # P D and I
    bumps = [0.1, 0.1, 0.2]
    n = 0
    
    best_error = run(params)
    while sum(bumps) > tol:
        n += 1
        print "Twiddle #%d, best_error=%s, sum(bumps)=%s" % (n, best_error, sum(bumps))
        for i in range(len(params)):
            params[i] += bumps[i]
            error = run(params)
            if error < best_error:
                best_error = error
                bumps[i] *= 1.1
            else:
                params[i] -= 2*bumps[i]
                error = run(params)
                if error < best_error:
                    best_error = error
                    bumps[i] *= 1.1
                else:
                    params[i] += bumps[i]
                    bumps[i] *= 0.9
        
                    
#    return best_error
    return params

params = twiddle()
final_error = run(params)
print "Final run(%s) error %s" % (repr(params), final_error)

