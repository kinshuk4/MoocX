# Please fill out this stencil and submit using the provided submission script.

from GF2 import one
from itertools import combinations
from plotting import plot


## Problem 1
p1_u = [ 0, 4]
p1_v = [-1, 3]
p1_v_plus_u = [p1_u[index] + p1_v[index] for index in range(len(p1_u))]
p1_v_minus_u = [p1_v[index] - p1_u[index] for index in range(len(p1_u))]
p1_three_v_minus_two_u = [3 * p1_v[index] - 2 * p1_u[index] for index in range(len(p1_u))]



## Problem 2
p2_u = [-1,  1, 1]
p2_v = [ 2, -1, 5]
p2_v_plus_u = [p2_v[index] + p2_u[index] for index in range(len(p2_u))]
p2_v_minus_u = [p2_v[index] - p2_u[index] for index in range(len(p2_u))]
p2_two_v_minus_u = [2 * p2_v[index] - p2_u[index] for index in range(len(p2_u))]
p2_v_plus_two_u = [p2_v[index] + 2 * p2_u[index] for index in range(len(p2_u))]



## Problem 3
# Write your answer using GF2's one instead of the number 1
p3_v = [0, one, one]
p3_u = [one, one, one]

p3_vector_sum_1 = [p3_u[index] + p3_v[index] for index in range(len(p3_u))]
p3_vector_sum_2 = [p3_u[index] + p3_u[index] + p3_v[index] for index in range(len(p3_u))]


## Problem 4
# Please express your solution as a set of the letters corresponding to the solutions.
# For example, {'a','b','c'} is the subset consisting of:
#   a (1100000), b (0110000), and c (0011000).
# Leave an empty set if it cannot be expressed in terms of the other vectors.
vectors = {
    'a': [one, one, 0, 0, 0, 0, 0],
    'b': [0, one, one, 0, 0, 0, 0],
    'c': [0, 0, one, one, 0, 0, 0],
    'd': [0, 0, 0, one, one, 0, 0],
    'e': [0, 0, 0, 0, one, one, 0],
    'f': [0, 0, 0, 0, 0, one, one]
}

u1 = [0, 0, one, 0, 0, one, 0]

# u_0010010 = { 'd', 'e', 'c' }
u_0010010 = set([comb for weight in range(1,7) for comb in combinations(vectors, weight) if [sum(g) for g in zip(*[vectors[_] for _ in comb])] == u1][0])

u2 = [0, one, 0, 0, 0, one, 0]

#u_0100010 = { 'd', 'e', 'b', 'c'}
u_0100010 = set([comb for weight in range(1,7) for comb in combinations(vectors, weight) if [sum(g) for g in zip(*[vectors[_] for _ in comb])] == u2][0])



## Problem 5
# Use the same format as the previous problem
vectors = {
    'a': [one, one, one, 0, 0, 0, 0],
    'b': [0, one, one, one, 0, 0, 0],
    'c': [0, 0, one, one, one, 0, 0],
    'd': [0, 0, 0, one, one, one, 0],
    'e': [0, 0, 0, 0, one, one, one],
    'f': [0, 0, 0, 0, 0, one, one]
}

u1_p5 = [0, 0, one, 0, 0, one, 0]
v_0010010 = set([comb for weight in range(1,7) for comb in combinations(vectors, weight) if [sum(g) for g in zip(*[vectors[_] for _ in comb])] == u1_p5][0])


u2_p5 = [0, one, 0, 0, 0, one, 0]
v_0100010 = set([comb for weight in range(1,7) for comb in combinations(vectors, weight) if [sum(g) for g in zip(*[vectors[_] for _ in comb])] == u2_p5])


## Problem 6

def vector_dot_product(*vectors): return sum([ u * v for u, v in zip(*vectors)])

uv_a = vector_dot_product([1, 0], [5, 4321])
uv_b = vector_dot_product([0, 1], [12345, 6])
uv_c = vector_dot_product([-1, 3], [5,7])
uv_d = -1



## Problem 7
# use 'one' instead of '1'
x_gf2 = [one, 0, 0, 0]



## Problem 8
v1 = [2, 3, -4, 1]
v2 = [1, -5, 2, 0]
v3 = [4, 1, -1, -1]


def scalar_vector_mult(alpha, v):
    """Generate points from a vector has origin [0, 0]"""
    return [alpha*x for x in v]

def scalar_vector_ext(alpha, v, a, b):
    """Generate points from a function y=ax+b"""
    return [alpha * v[0],
            alpha * v[0] * a + b]

def draw_plot():
    v = [-1.5, 2]
    v_points = [scalar_vector_mult(i/10, v) for i in range(11)]

    u = [3, 0]
    u_points = [scalar_vector_mult(i/10, u) for i in range(11)]

    c_points = [scalar_vector_ext(i/10, u, -4/9, 4/3) for i in range(-11, 11)]

    plot(u_points + v_points + c_points)
