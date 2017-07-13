from video22 import matrix

num_landmarks = 2

m1 = matrix()
m1.zero(6,6)
row = 0
for i in range(m1.dimx**2):
    column = i % m1.dimx
    m1.value[row][column] = i+1
    if column == m1.dimx-1:
        row += 1

print "m1 initialized"
m1.show()

new_dimx = m1.dimx+2
first_lm_idx = m1.dimx-(num_landmarks*2)
keep_indices = range(0, first_lm_idx)
keep_indices += range(first_lm_idx+2,new_dimx)

print "keep_indices=%s" % repr(keep_indices)

m2 = m1.expand(new_dimx, new_dimx, keep_indices, keep_indices)


print "m1 expanded (m2)"
m2.show()


m3 = m1.take([0,1],range(m1.dimx-(num_landmarks*2), m1.dimx))
print "m1 slice A (m3)"
m3.show()

m4 = m1.take([0,1],[0,1])
print "m1 slice B (m4)"
m4.show()

m5 = m1.take(range(2,m1.dimx))
print "m1 slice O1 (m5)"
m5.show()

x1 = matrix()
x1.zero(6,1)
for i in range(x1.dimx):
    x1.value[i][0] = i+1

print "x1 initialized"
x1.show()

x2 = x1.take([0,1],[0])
print "x1 slice C (x2)"
x2.show()

x3 = x1.take(range(x1.dimx-(num_landmarks*2),x1.dimx),[0])
print "x1 slice X1 (x3)"
x3.show()


