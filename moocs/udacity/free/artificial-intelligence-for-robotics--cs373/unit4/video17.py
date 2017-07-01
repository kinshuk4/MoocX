# ----------
# User Instructions:
# 
# Create a function compute_value() which returns
# a grid of values. Value is defined as the minimum
# number of moves required to get from a cell to the
# goal. 
#
# If it is impossible to reach the goal from a cell
# you should assign that cell a value of 99.

# ----------

grid = [[0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 0, 0, 0, 1, 0]]

init = [0, 0]
goal = [len(grid)-1, len(grid[0])-1]

delta = [[-1, 0 ], # go up
         [ 0, -1], # go left
         [ 1, 0 ], # go down
         [ 0, 1 ]] # go right

delta_name = ['^', '<', 'v', '>']

cost_step = 1 # the cost associated with moving from a cell to an adjacent one.

# ----------------------------------------
# insert code below
# ----------------------------------------

def compute_value():
    values = [[99 for row in range(len(grid[0]))] for col in range(len(grid))]
    closed = [[0 for row in range(len(grid[0]))] for col in range(len(grid))]
    x = goal[0]
    y = goal[1]
    closed[x][y] = 1
    f = 0
    open = [[f, x, y]]
    while (True):
        if len(open) == 0:
            break
        
        #open.sort() # not needed for this algo, we evaluate every cell
        next = open.pop(0) # we can pop the zeroeth element, no need to reverse
        x = next[1]
        y = next[2]
        f = next[0]
        closed[x][y] = 1
        values[x][y] = f

        for i in range(len(delta)):
            f2 = f + cost_step
            x2 = x + delta[i][0]
            y2 = y + delta[i][1]
            if (   x2 < 0 # Skip values outside of the grid
                or y2 < 0
                or x2 > len(grid)-1
                or y2 > len(grid[0])-1):
                #print "(%d,%d) is outside the grid" % (x2, y2)
                continue
            if (closed[x2][y2] == 1): #Already checked
                #print "(%d,%d) is already checked" % (x2, y2)
                continue
            if (grid[x2][y2] == 1): #Occupied space, do not expand
                #print "(%d,%d) is occupied" % (x2, y2)
                continue
            open.append([f2,x2,y2])

    
    

    for i in range(len(values)):
        print values[i]
    return values #make sure your function returns a grid of values as demonstrated in the previous video.


compute_value()