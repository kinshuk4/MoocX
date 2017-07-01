def copy_array(p):
    q = []
    for row in range(len(p)):
        subq = []
        for col in range(len(p[0])):
            subq.append(float(p[row][col]))
        q.append(subq)
    return q
    

# Helper to pivot an array (as in switch columns to rows and vice versa)
def pivot_array(p):
    p_rotated = []
    for col in range(len(p[0])):
        subp = []
        for row in range(len(p)):
            subp.append(float(p[row][col]))
        p_rotated.append(subp)
    return p_rotated
#print "pre privot"
#show(p)
#print "after pivot"
#show(pivot_array(p))

# Helper to calculate the sum of 2d array
def array_sum_2d(p):
    tsum = 0 
    for row in range(len(p)):
        tsum = tsum + sum(p[row])
    return tsum
#print array_sum_2d(p)

# Helper to normalize the array
def normalize(p):
    s = array_sum_2d(p)
    # TODO: switch to map ?
    q = copy_array(p)
    for row in range(len(q)):
        q[row][:] = [x / s for x in q[row]]
    return q
#show(normalize(p))

# Helper to do the multiplications on array
def array_mul_2d(p, mul_by):
    # TODO: switch to map ?
    q = copy_array(p)
    for row in range(len(p)):
        q[row][:] = [x * mul_by for x in p[row]]
    return q

# helper to add two arrays to each other
def array_add_2d(p1, p2):
    q = []
    # TODO: switch to map ?
    for row in range(len(p1)):
        subq = []
        for col in range(len(p1[row])):
            subq.append(p1[row][col] + p2[row][col])
        q.append(subq)
    return q;
#show(array_add_2d(p,p))



def show(p):
    for i in range(len(p)):
        print p[i]

# initial P from q5
P = [[0.14285714285714285, 0.14285714285714285],
     [0.5714285714285714, 0.14285714285714285]]

## I don't understand why this is not the correct answer, the grader claims the lower part is fine so WTF ?
#P2 = [[0.14285714285714285*0.5714285714285714, 0.14285714285714285*0.14285714285714285],
#      [0.0, 0.0]]

# Ach so, t's addition and not multiplication in this step, oops
P3 = [[0.14285714285714285+0.5714285714285714, 0.14285714285714285+0.14285714285714285],
      [0.0, 0.0]]



#print "P2"
#show(P2)
#print "normalize(P2)"
#show(normalize(P2))

print "P3"
show(P3)
print "normalize(P3)"
show(normalize(P3))
