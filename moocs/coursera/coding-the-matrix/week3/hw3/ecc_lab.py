# coding: utf-8
from GF2 import one
from bitutil import *
from mat import *
from matutil import *
from vec import Vec

# # Task 1 part 1
""" Create an instance of Mat representing the generator matrix G. You can use
the procedure listlist2mat in the matutil module (be sure to import first).
Since we are working over GF (2), you should use the value one from the
GF2 module to represent 1"""
G = listlist2mat([[one, 0, one, one], [one, one, 0, one], [0, 0, 0, one], [one, one, one, 0], [0, 0, one, 0], [0, one, 0, 0], [one, 0, 0, 0] ])

# # Task 1 part 2
# Please write your answer as a list. Use one from GF2 and 0 as the elements.
encoding_1001 = [0, 0, one, one, 0, 0, one]


# # Task 2
# Express your answer as an instance of the Mat class.
R = listlist2mat([[one, one, 0, one, 0, 0, 0], [one, one, 0, one, 0, one, one], [one, 0, one, one, one, one, 0], [one, one, one, one, 0, 0, one]])

# # Task 3
# Create an instance of Mat representing the check matrix H.
H = listlist2mat([[0, 0, 0, one, one, one, one], [0, one, one, 0, 0, one, one], [one, 0, one, 0, one, 0, one]])

# # Task 4 part 1
def find_error(e):
    """
    Input: an error syndrome as an instance of Vec
    Output: the corresponding error vector e
    Examples:
        >>> find_error(Vec({0,1,2}, {0:one}))
        Vec({0, 1, 2, 3, 4, 5, 6},{3: one})
        >>> find_error(Vec({0,1,2}, {2:one}))
        Vec({0, 1, 2, 3, 4, 5, 6},{0: one})
        >>> find_error(Vec({0,1,2}, {1:one, 2:one}))
        Vec({0, 1, 2, 3, 4, 5, 6},{2: one})    
    """
    num = int()
    if e[0]:
        num += 4
    if e[1]:
        num += 2
    if e[2]:
        num += 1
    return Vec({0, 1, 2, 3, 4, 5, 6}, {num - 1: one}) if num else Vec({0, 1, 2, 3, 4, 5, 6}, {})

# # Task 4 part 2
# Use the Vec class for your answers.
non_codeword = Vec({0, 1, 2, 3, 4, 5, 6}, {0: one, 1:0, 2:one, 3:one, 4:0, 5:one, 6:one})
error_vector = Vec({0, 1, 2, 3, 4, 5, 6}, {0: 0, 1:0, 2:0, 3:0, 4:0, 5:0, 6:one})
code_word = Vec({0, 1, 2, 3, 4, 5, 6}, {0: one, 1:0, 2:one, 3:one, 4:0, 5:one, 6:0})
original = matrix_vector_mul(R, code_word)  # R * code_word

# # Task 5
def find_error_matrix(S):
    """
    Input: a matrix S whose columns are error syndromes
    Output: a matrix whose cth column is the error corresponding to the cth column of S.
    Example:
        >>> S = listlist2mat([[0,one,one,one],[0,one,0,0],[0,0,0,one]])
        >>> find_error_matrix(S)
        Mat(({0, 1, 2, 3, 4, 5, 6}, {0, 1, 2, 3}), {(1, 2): 0, (3, 2): one, (0, 0): 0, (4, 3): one, (3, 0): 0, (6, 0): 0, (2, 1): 0, (6, 2): 0, (2, 3): 0, (5, 1): one, (4, 2): 0, (1, 0): 0, (0, 3): 0, (4, 0): 0, (0, 1): 0, (3, 3): 0, (4, 1): 0, (6, 1): 0, (3, 1): 0, (1, 1): 0, (6, 3): 0, (2, 0): 0, (5, 0): 0, (2, 2): 0, (1, 3): 0, (5, 3): 0, (5, 2): 0, (0, 2): 0})
    """
    return coldict2mat({i: find_error(mat2coldict(S)[i]) for i in S.D[1]})

# # Task 6
s = "I'm trying to free your mind, Neo. But I can only show you the door. You�re the one that has to walk through it."
P = bits2mat(str2bits(s))

# # Task 7
C = matrix_matrix_mul(G, P)
bits_before = 928
bits_after = 1624

# # Ungraded Task
CTILDE = None

# # Task 8
def correct(A):
    """
    Input: a matrix A each column of which differs from a codeword in at most one bit
    Output: a matrix whose columns are the corresponding valid codewords.
    Example:
        >>> A = Mat(({0,1,2,3,4,5,6}, {1,2,3}), {(0,3):one, (2, 1): one, (5, 2):one, (5,3):one, (0,2): one})
        >>> correct(A)
        Mat(({0, 1, 2, 3, 4, 5, 6}, {1, 2, 3}), {(0, 1): 0, (1, 2): 0, (3, 2): 0, (1, 3): 0, (3, 3): 0, (5, 2): one, (6, 1): 0, (3, 1): 0, (2, 1): 0, (0, 2): one, (6, 3): one, (4, 2): 0, (6, 2): one, (2, 3): 0, (4, 3): 0, (2, 2): 0, (5, 1): 0, (0, 3): one, (4, 1): 0, (1, 1): 0, (5, 3): one})
    """
    return A + find_error_matrix(matrix_matrix_mul(H, A))

