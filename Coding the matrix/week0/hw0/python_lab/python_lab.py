import itertools

## Task 1
minutes_in_week = 60*24*7

## Task 2
remainder_without_mod = 2304811 - (2304811 // 47) * 47

## Task 3
divisible_by_3 = ((673 + 909) % 3) == 0

## Task 4
x = -9
y = 1/2
statement_val = 2**(y-1/2) if x >= -10 else 2**(y+1/2)

## Task 5
first_five_squares = { _**2 for _ in {1,2,3,4,5} }

## Task 6
first_five_pows_two = { 2**_ for _ in {0,1,2,3,4} }

## Task 7: enter in the two new sets
X1 = { 1, 2, 3 }
Y1 = { 5, 6, 7 }

## Task 8: enter in the two new sets
X2 = { -4, -2, -1 }
Y2 = { 0, 1, 2 }

## Task 9
base = 10
digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
three_digits_set = {int(str.join('',[i for i in map(lambda x: str(x), _)]),base) for _ in itertools.product(digits, repeat=3)}

## Task 10
S = {1, 2, 3, 4}
T = {3, 4, 5, 6}
S_intersect_T = {s for s in S for t in T if s == t }

## Task 11
L_average = sum([20, 10, 15,75])/len([20, 10, 15, 75]) # average of: [20, 10, 15, 75]

## Task 12
LofL = [[.25, .75, .1], [-1, 0], [4, 4, 4, 4]]
LofL_sum = sum([sum(x) for x in LofL]) # use form: sum([sum(...) ... ])

## Task 13
cartesian_product = [[_, __] for _ in {'A','B','C'} for __ in {1,2,3}] # use form: [ ... {'A','B','C'} ... {1,2,3} ... ]

## Task 14
S = {-4, -2, 1, 2, 5, 0}
zero_sum_list = [ (_, __, ___) for _ in S for __ in S for ___ in S if (_ + __ + ___) == 0 ]

## Task 15
exclude_zero_list = [ (_, __, ___) for _ in S for __ in S for ___ in S if (_ + __ + ___) == 0 and not (_== __ == ___ == 0) ]

## Task 16
first_of_tuples_list = [ (_, __, ___) for _ in S for __ in S for ___ in S if (_ + __ + ___) == 0 and not (_== __ == ___ == 0) ][0]

## Task 17
L1 = [1,2,3,4,1] # <-- want len(L1) != len(list(set(L1)))
L2 = [2,3,1,4,0] # <-- same len(L2) == len(list(set(L2))) but L2 != list(set(L2))

## Task 18
odd_num_list_range = {_ for _ in range(1,100,2)}

## Task 19
L = ['A','B','C','D','E']
range_and_zip = list(zip(range(0,5), L))

## Task 20
list_sum_zip = [_ + __ for (_, __) in zip([10,25,40], [1,15, 20])]

## Task 21
dlist = [{'James':'Sean', 'director':'Terence'}, {'James':'Roger', 'director':'Lewis'}, {'James':'Pierce', 'director':'Roger'}]
k = 'James'
value_list = [_[k] for _ in dlist]

## Task 22
dlist = [{'Bilbo':'Ian','Frodo':'Elijah'},{'Bilbo':'Martin','Thorin':'Richard'}]
k = 'Bilbo'
value_list_modified_1 = [_[k] if k in _ else 'NOT PRESENT' for _ in dlist] # <-- Use the same expression here
k = 'Frodo'
value_list_modified_2 = [_[k] if k in _ else 'NOT PRESENT' for _ in dlist] # <-- as you do here

## Task 23
square_dict = { _:_**2 for _ in range(0,100)}

## Task 24
D = {'red','white','blue'}
identity_dict = {_:_ for _ in D}

## Task 25
base = 10
digits = set(range(10))
representation_dict = { int(str.join('', [str(__) for __ in _]), base):list(_) for _ in itertools.product(digits, repeat=3) }

## Task 26
d = {0:1000.0, 1:1200.50, 2:990}
names = ['Larry', 'Curly', 'Moe']
listdict2dict = { _[0]:_[1] for _ in zip(names, d.values()) }

## Task 27
def nextInts(L): return [ _ for _ in map(lambda x: x+1, L) ]

## Task 28
def cubes(L): return [ _ for _ in map(lambda x: x**3, L) ]

## Task 29
def dict2list(dct, keylist): return [ dct[k] for k in keylist if k in dct ]

## Task 30 
def list2dict(L, keylist): return { k:v for (k, v) in zip(keylist, L) }

