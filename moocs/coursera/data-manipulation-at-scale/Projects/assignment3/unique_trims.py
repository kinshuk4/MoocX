import MapReduce
import sys

"""
Create a clean set of nucleotide sequences
with Simple Python MapReduce Framework.

Output: Unique nucleotide strings with the
last ten characters removed and no duplicates.
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    """
    Input is list [seq_id,nucleotide].
    Key should be the trimmed list,
    value should be the sequence.
    """
    # key: Trimmed nucleotide sequence
    # value: (seq_id,nucleotide)
    key = record[1][:(len(record[1])-10)]
    seq_id = record[0] #for informational purposes only
    mr.emit_intermediate(key, (seq_id,key))

def reducer(key, list_of_values):
    """
    Reducer should keep only unique pairs.
    """
    # key: person
    # value: 1 friend
    for seq in list_of_values:
      mr.emit(seq[1])
      break #break after the first one (no duplicates)
      
# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
