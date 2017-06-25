# Write a program to update your mean and variance
# when given the mean and variance of your belief
# and the mean and variance of your measurement.
# This program will update the parameters of your
# belief function.

''' Given
def update(mean1, var1, mean2, var2):
    new_mean = 
    new_var =
    return [new_mean, new_var]

print update(10.,8.,13., 2.)
'''

def update(mean1, var1, mean2, var2):
    new_mean = (1 / (1 / )
    new_var = 1 / ((1 / var1) * (1 / var2))
    return [new_mean, new_var]

print update(10.,8.,13., 2.)