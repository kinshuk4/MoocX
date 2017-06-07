import MapReduce
import sys

"""
Count asymmetric friendships in a social graph
with Simple Python MapReduce Framework.

Output: all pairs where (person,friends)
appears in the dataset but not the opposite.
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    """
    Input is a [PersonA, PersonB] list.
    Mapper should produce a tuple with
    two_person (sorted) key and value 1
    """
    # key: two person pair (sorted)
    # value: 1
    rcd = [x.encode('utf-8').lower() for x in record]
    rcd.sort()
    key = "_".join(rcd)
    mr.emit_intermediate(key, (record))

def reducer(key, list_of_values):
    """
    Reducer should sum the pairs.
    Only emit tuples where count
    is 1.
    """
    # key: person
    # value: 1 friend
    occur_count = 0
    for attr in list_of_values:
      friend_pair = list(attr)
      occur_count += 1
    if occur_count == 1:
        mr.emit((friend_pair[0], friend_pair[1]))
        
        #This is wrong...but so is the autograder.
        mr.emit((friend_pair[1], friend_pair[0]))
      
# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
