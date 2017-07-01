# Write a program to update your mean and variance
# when given the mean and variance of your belief
# and the mean and variance of your measurement.
# This program will update the parameters of your
# belief function.

def update(mean1, var1, mean2, var2):
    new_var = 1.0/((1.0/var1)+(1.0/var2))
    new_mean = ((var2*mean1)+(var1*mean2)) / (var2 + var1)
    return [new_mean, new_var]


def move(mu,sigma2, u, r2):
    new_mean = mu + u
    new_var = sigma2 + r2
    return [new_mean, new_var]

print update(10.,4.,10., 4.)
