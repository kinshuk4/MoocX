# -----------
# User Instructions:
# 
# Modify the function search() so that it returns
# a table of values called expand. This table
# will keep track of which step each node was
# expanded.
#
# For grading purposes, please leave the return
# statement at the bottom.
# ----------


grid = [[0, 0, 1, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 1, 0],
        [0, 0, 1, 0, 1, 0],
        [0, 0, 1, 0, 1, 0]]

init = [0, 0]
goal = [len(grid)-1, len(grid[0])-1]

delta = [[-1, 0 ], # go up
         [ 0, -1], # go left
         [ 1, 0 ], # go down
         [ 0, 1 ]] # go right

delta_name = ['^', '<', 'v', '>']

cost = 1


# ----------------------------------------
# modify code below
# ----------------------------------------

def search():
    closed = [[0 for row in range(len(grid[0]))] for col in range(len(grid))]
    expand = [] # The method above seems to make funny references
    for i in range(len(grid)):
        expand.append([-1] * len(grid[0]))

    closed[init[0]][init[1]] = 1

    x = init[0]
    y = init[1]
    g = 0

    expand_counter = 0

    open = [[g, x, y]]

    found = False  # flag that is set when search is complete
    resign = False # flag set if we can't find expand

    while not found and not resign:
        if len(open) == 0:
            resign = True
        else:
            open.sort()
            next = open.pop(0) # we can pop the zeroeth element, no need to reverse
#            open.reverse()
#            next = open.pop()
            x = next[1]
            y = next[2]
            g = next[0]

            expand[x][y] = expand_counter + 0
            expand_counter += 1
            
            if x == goal[0] and y == goal[1]:
                found = True
            else:
                for i in range(len(delta)):
                    x2 = x + delta[i][0]
                    y2 = y + delta[i][1]
                    if x2 >= 0 and x2 < len(grid) and y2 >=0 and y2 < len(grid[0]):
                        if closed[x2][y2] == 0 and grid[x2][y2] == 0:
                            g2 = g + cost
                            # This actually probably unneccessary due to marking closed immediately instead of after checking
#                            if open.count([g2, x2, y2]):
#                                continue # skip if already in the table
                            open.append([g2, x2, y2])
                            closed[x2][y2] = 1
                            #print "setting expand[%d][%d]=%d " % (x2, y2, expand_counter)
    for i in range(len(expand)):
        print expand[i]
    return expand #Leave this line for grading purposes!


search()
