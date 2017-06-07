"""
Merge function for 2048 game.
"""

def remove_values_from_list(lst, val):
    """
    Return a copy of a list with all instances of a particular value removed.
    """
    new = lst[:]
    while val in new:
        new.remove(val)
    return new
        
def merge(line):
    """
    Function that merges a single row or column in 2048.
    """
    new = []
    # Copy the full contents of line (don't want to change it)
    temp = line[:]
    temp = remove_values_from_list(temp,0)
    while len(temp) != 0:
        temp = remove_values_from_list(temp,0)
        # Recursively check first two elements, take appropriate action
        if len(temp) == 1:
            new.append(temp[0])
            del temp[0]
        elif temp[0] == temp[0+1]:
            new.append(temp[0]*2)
            del temp[0:2]
        elif temp[0] != temp[0+1]:
            new.append(temp[0])
            del temp[0]
    # Add the zeros back to the end
    while len(new) != len(line):
        new.append(0)        
    return new