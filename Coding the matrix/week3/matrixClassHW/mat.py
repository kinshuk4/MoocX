from vec import Vec, keys

def getitem(M, k):
    "Returns the value of entry k in M. The value of k should be a pair."
    assert k[0] in M.D[0] and k[1] in M.D[1]
    return M.f.get(k,0)

def setitem(M, k, val):
    "Sets the element of M with label k to be val. The value of k should be a pair"
    assert k[0] in M.D[0] and k[1] in M.D[1]
    M.f[k] = val

def add(A, B):
    "Returns the sum of A and B"
    assert A.D == B.D
    ret = Mat(A.D,{})
    for k in keys(A,B):
        ret[k] = A[k]+B[k]
    return ret

def scalar_mul(M, alpha):
    "Returns the product of scalar alpha with M"
    ret = Mat(M.D,{})
    for k in M.f.keys():
        ret[k]=alpha*M[k]
    return ret

def equal(A, B):
    "Returns true iff A is equal to B"
    assert A.D == B.D
    for k in keys(A,B):
        if A[k] != B[k]:
            return False
    return True

def transpose(M):
    "Returns the transpose of M"
    return Mat((M.D[1],M.D[0]), {(j,i):v for ((i,j),v) in M.f.items() })

def vector_matrix_mul(v, M):
    "Returns the product of vector v and matrix M"
    assert M.D[0] == v.D
    # return Vec(M.D[1], { c:sum((v[r]*M[r,c] for r in v.D)) for c in M.D[1] })
    ret = Vec(M.D[1],{})
    for (i,j) in M.f.keys():
        ret[j] = ret[j] + v[i]*M[i,j]
    return ret

def matrix_vector_mul(M, v):
    "Returns the product of matrix M and vector v"
    assert M.D[1] == v.D
    # return Vec(M.D[0], { r:sum((v[c]*M[r,c] for c in v.D)) for r in M.D[0] })
    ret = Vec(M.D[0],{})
    for (i,j) in M.f.keys():
        ret[i] = ret[i] + M[i,j]*v[j]
    return ret

# could probably use some more thorough tests!
def matrix_matrix_mul(A, B):
    "Returns the product of A and B"
    assert A.D[1] == B.D[0]
    # rows = matutil.mat2rowdict(A)
    # cols = matutil.mat2coldict(B)
    # return Mat((A.D[0],B.D[1]),
    # { (r,c):rows[r]*cols[c] for r in rows.keys() for c in cols.keys() })

    # Think of matrices as IxJ and JxK
    I = A.D[0]
    J = A.D[1]
    K = B.D[1]
    ret = Mat((I,K),{})
    # Do fewest lookups/multiplies
    if len(A.f) * len(B.D[1]) <= len(B.f) * len(A.D[0]):
        for (i,j) in A.f.keys():
            for k in K:
                if (j,k) in B.f:
                    ret[i,k] = ret[i,k] + A[i,j]*B[j,k]
    else:
        for (j,k) in B.f.keys():
            for i in I:
                if (i,j) in A.f:
                    ret[i,k] = ret[i,k] + A[i,j]*B[j,k]
    return ret

    

################################################################################

class Mat:
    def __init__(self, labels, function):
        self.D = labels
        self.f = function

    __getitem__ = getitem
    __setitem__ = setitem
    transpose = transpose

    def __neg__(self):
        return (-1)*self

    def __mul__(self,other):
        if Mat == type(other):
            return matrix_matrix_mul(self,other)
        elif Vec == type(other):
            return matrix_vector_mul(self,other)
        else:
            return scalar_mul(self,other)
            #this will only be used if other is scalar (or not-supported). mat and vec both have __mul__ implemented

    def __rmul__(self, other):
        if Vec == type(other):
            return vector_matrix_mul(other, self)
        else: # Assume scalar
            return scalar_mul(self, other)

    __add__ = add

    def __sub__(self,a,b):
        return a+(-b)

    __eq__ = equal

    def copy(self):
        return Mat(self.D, self.f.copy())

    def __str__(self,M, rows=None, cols=None):
        "string representation for print()"
        if rows == None:
            try:
                rows = sorted(M.D[0])
            except TypeError:
                rows = sorted(M.D[0], key=hash)
        if cols == None:
            try:
                cols = sorted(M.D[1])
            except TypeError:
                cols = sorted(M.D[1], key=hash)
        separator = ' | '
        numdec = 3
        pre = 1+max([len(str(r)) for r in rows])
        colw = {col:(1+max([len(str(col))] + [len('{0:.{1}G}'.format(M[row,col],numdec)) if isinstance(M[row,col], int) or isinstance(M[row,col], float) else len(str(M[row,col])) for row in rows])) for col in cols}
        s1 = ' '*(1+ pre + len(separator))
        s2 = ''.join(['{0:>{1}}'.format(c,colw[c]) for c in cols])
        s3 = ' '*(pre+len(separator)) + '-'*(sum(list(colw.values())) + 1)
        s4 = ''.join(['{0:>{1}} {2}'.format(r, pre,separator)+''.join(['{0:>{1}.{2}G}'.format(M[r,c],colw[c],numdec) if isinstance(M[r,c], int) or isinstance(M[r,c], float) else '{0:>{1}}'.format(M[r,c], colw[c]) for c in cols])+'\n' for r in rows])
        return '\n' + s1 + s2 + '\n' + s3 + '\n' + s4

    def pp(self, rows, cols):
        print(self.__str__(rows, cols))

    def __repr__(self):
        "evaluatable representation"
        return "Mat(" + str(self.D) +", " + str(self.f) + ")"

import matutil