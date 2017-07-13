# ----------
# User Instructions:
# 
# Define a function, search() that takes no input
# and returns a list
# in the form of [optimal path length, x, y]. For
# the grid shown below, your function should output
# [11, 4, 5].
#
# If there is no valid path from the start point
# to the goal, your function should return the string
# 'fail'
# ----------

# Grid format:
#   0 = Navigable space
#   1 = Occupied space

grid = [[0, 0, 1, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [0, 0, 0, 0, 1, 0],
        [0, 0, 1, 1, 1, 0],
        [0, 0, 0, 0, 1, 0]]

init = [0, 0]
goal = [len(grid)-1, len(grid[0])-1] # Make sure that the goal definition stays in the function.

delta = [[-1, 0 ], # go up
        [ 0, -1], # go left
        [ 1, 0 ], # go down
        [ 0, 1 ]] # go right

delta_name = ['^', '<', 'v', '>']

cost = 1

def search():
    # ----------------------------------------
    # insert code here and make sure it returns the appropriate result
    # ----------------------------------------

    # Initialize our checked matrix (note this as the grid is in y,x format)
    # checked = [[0] * len(grid[0])] * len(grid) # arghs, this will create references...
    checked = []
    for i in range(len(grid)):
        checked.append([0] * len(grid[0]))
    to_check = {} # Keyed by the cost
    
    def show_array(a):
        for i in range(len(a)):
            print a[i]
    
    def clean_tocheck():
        for cost in to_check.keys():
            if len(to_check[cost]) == 0:
                del(to_check[cost])
    
    def show_tocheck():
        clean_tocheck()
        print "To check"
        keys = to_check.keys()
        keys.sort()
        for cost in keys:
            print "%d: %s" % (cost, to_check[cost])

    def get_next_check():
        clean_tocheck()
        keys = to_check.keys()
        keys.sort()
        for cost in keys:
            return to_check[cost].pop(0)
    
    def check_and_expand(x,y, current_cost):
        #print "Checking %d,%d (goal: %d,%d)" % (x,y,goal[0], goal[1])
        if (    x == goal[0]
            and y == goal[1]):
                return [current_cost,x,y]
                #return [current_cost,y,x]
        checked[x][y] = 1

        #print "Checked cells"
        #show_array(checked)
        
        new_cost = current_cost + cost
        for i in range(len(delta)):
            new_x = x + delta[i][0]
            new_y = y + delta[i][1]
            if (   new_x < 0 # Skip values outside of the grid
                or new_y < 0
                or new_x > len(grid)-1
                or new_y > len(grid[0])-1):
                #print "(%d,%d) is outside the grid" % (new_x, new_y)
                continue
            if (grid[new_x][new_y] == 1): #Occupied space, we cannot expand here
                #print "(%d,%d) is occupied" % (new_x, new_y)
                checked[new_x][new_y] = 1 # Mark it as checked while at it
                continue
            if (checked[new_x][new_y]): # Already checked, do not expand here
                #print "(%d,%d) is already checked" % (new_x, new_y)
                continue
            if not to_check.has_key(new_cost):
                to_check[new_cost] = []
            to_check[new_cost].append([new_x,new_y,new_cost])
        #show_tocheck()
        return False


    #print "Grid"
    #show_array(grid)

    initial_ret = check_and_expand(init[0], init[0], 0)
    if (initial_ret): # unlikely but possible case, we're already where we want to go
        return initial_ret
    while (True):
        clean_tocheck()
        if (len(to_check.keys()) == 0):
            print 'fail'
            return 'fail' # Nothing more to expand and we did not reach goal, this is a failure
        next_check = get_next_check()
        res = check_and_expand(next_check[0], next_check[1], next_check[2])
        if (res): # Solution found
            print res
            return res


search()        
        
        
    