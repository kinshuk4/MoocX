import MapReduce
import sys

"""
Count friends in a social graph
with Simple Python MapReduce Framework.
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    """
    Input is a [PersonA, PersonB] list.
    Mapper should produce a tuple with
    PersonA as the key and the value 1.
    """
    # key: Person
    # value: 1 friend
    key = record[0].encode('utf-8')
    mr.emit_intermediate(key, 1)

def reducer(key, list_of_values):
    """
    Reducer should take tuples of people
    and count their friends
    """
    # key: person
    # value: 1 friend
    friend_count = 0
    for val in list_of_values:
      friend_count += val
    
    mr.emit((key, friend_count))
      
# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
