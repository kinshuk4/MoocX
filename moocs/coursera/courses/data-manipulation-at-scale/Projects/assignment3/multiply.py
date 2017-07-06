import MapReduce
import sys

"""
Sparse matrix multiply
with Simple Python MapReduce Framework.
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
    # key: Row num for A, col num for B
    # value: (other_dimensions, value)
    
    #we want to match the rows of A with the cols of B
    mat_name = record[0]
    if mat_name.lower() == "a":
        key = (record[1])
        val = (mat_name,record[2],record[3]) #keep other dimension, value
    elif mat_name.lower() == "b":
        key = record([2])
        val = (mat_name,record[1],record[3]) #keep other dimension, value
    
    mr.emit_intermediate(key, val)

def reducer(key, list_of_values):
    """
    Reducer should keep only unique pairs.
    """
    # value: row of the matrix as tuple of form (i,j,value)
    lst_copy = list(list_of_values)
    out_val = 0 #initialize the output val
    for cell in list_of_values:
      if cell[0].lower() == "a":
        inner_dim = cell[1] #column of A, row of B
        A_val = cell[2]
        
        #find the matching value
        B_val = 0 #initialize
        for cell2 in lst_copy:
            if cell2[0].lower() == "b" and cell2[1] == inner_dim:
                B_val = cell2[2]
                
        out_val += (A_val*B_val)
                
      
      break #break after the first one (no duplicates)
      
# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
