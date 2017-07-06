import MapReduce
import sys

"""
Inverted Index in Simple Python MapReduce Framework.
Functions to create an index of words and texts
they appear in.
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    """
    Mapper should produce a set of tuples,
    each of which has the word as a key
    and text name as a value
    """
    # key: document identifier
    # value: document contents
    docid = record[0]
    value = record[1]
    words = value.split()
    for w in words:
      mr.emit_intermediate(w, docid)

def reducer(key, list_of_values):
    """
    Reducer should take in a bunch of tuples with
    words as keys and combine them so that each output
    tuple has a word as a key and a list of texts
    as value.
    """
    # key: word
    # value: docid containing word
    docs = set()
    for v in list_of_values:
      docs.add(v)
    docs_lst = list(docs)
    mr.emit((key, docs_lst))

# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
