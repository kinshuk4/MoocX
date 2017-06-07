import MapReduce
import sys

"""
Join implementation in Simple Python MapReduce Framework.
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    """
    Input is a tuple representing a database record.
    First element is table identifier, second
    element is the primary key.
    
    Map should produce a set of tuples where the
    thing we join on is the key and all other
    values are a tuple of strings.
    """
    # key: order_id
    # value: full record
    #table_ident = record[0].encode('utf-8')
    order_id = record[1].encode('utf-8')
    mr.emit_intermediate(order_id, record)

def reducer(key, list_of_values):
    """
    Reducer should take in a bunch of tuples with
    order_ids as keys and combine them so that
    each output tuple is a combination of attributes
    from both tables.
    """
    # key: order_id
    # value: joined records
    #joined_attr = list()
    for lst in list_of_values:
      if lst[0] == u'order' or lst[0] == "order":
        order_record = list(lst) #should only be one
        break
        
    for lst in list_of_values:
      if lst[0] == "line_item":
        joined_record = order_record + lst
        mr.emit(joined_record)
      
# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
