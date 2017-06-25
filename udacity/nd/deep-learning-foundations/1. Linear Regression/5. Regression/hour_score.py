from numpy import *
import matplotlib.pyplot as plt 

#Residual Sum of Square
def compute_error(b, m, points):
    totalError = 0

    for i in range(0, len(points)):
        x = points[i,0]
        y = points[i,1]
        totalError += (y - (m*x + b)) ** 2

    #average
    return totalError / float(len(points))

def step_gradient(b, m, points, learning_rate):
    b_gradient = 0
    m_gradient = 0

    n = float(len(points))
    for i in range(0, len(points)):
        x = points[i, 0]
        y = points[i, 1]

        #gradient descent: the key!
        b_gradient += -(2/n) * (y - ((m * x) + b))
        m_gradient += -(2/n) * x * (y - ((m * x) + b))

    new_b = b - (learning_rate * b_gradient)
    new_m = m - (learning_rate * m_gradient)

    return [new_b, new_m]

def gradient_descent_runner(points, b, m, learning_rate, num_iterations):
    for i in range(num_iterations):
        #update b and m 
        b, m = step_gradient(b, m, array(points), learning_rate)

    return [b, m]

def run():
    #generate data from txt file
    points = genfromtxt('hour_score.csv', delimiter=',')

    #hyperparameters
    learning_rate = 0.0001
    num_iterations = 1000

    #y = mx + b
    initial_b = 0
    initial_m = 0

    #train model
    print 'starting gradient descent at b = {0}, m = {1}, error = {2}'.format(initial_b, initial_m, compute_error(initial_b, initial_m, points))
    [b, m] = gradient_descent_runner(points, initial_b, initial_m, learning_rate, num_iterations)
    print 'After {0} iterations b = {1}, m = {2}, error = {3}'.format(num_iterations, b, m, compute_error(b, m, points))

    #visualise result
    plt.scatter(points[:,0], points[:,1])
    plt.plot(points[:,0], points[:,0]*m+b)
    plt.show()

if __name__ == '__main__':
    run()