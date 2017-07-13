my = 10
sigma2 = 4
x = 8

def f_of_x(x, sigma2, my):
    import math
    normalizer = 1.0/math.sqrt(2*math.pi*sigma2)
    tmp1 = pow(x-my,2)/sigma2
    return  normalizer * math.exp(-0.5*tmp1)

print f_of_x(x, sigma2, my)