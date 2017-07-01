# ----------
# User Instructions:
# 
# Create a function optimum_policy() that returns
# a grid which shows the optimum policy for robot
# motion. This means there should be an optimum
# direction associated with each navigable cell.
# 
# un-navigable cells must contain an empty string
# WITH a space, as shown in the previous video.
# Don't forget to mark the goal with a *

# ----------

grid = [[0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 0, 0, 0, 1, 0]]

# another test grid
grid = [[0, 0, 1, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 1, 0],
        [0, 0, 1, 0, 1, 0],
        [0, 0, 1, 0, 1, 0]]


# yet another test grid
grid = [[0, 0, 1, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [0, 0, 0, 0, 1, 0],
        [0, 0, 1, 1, 1, 0],
        [0, 0, 0, 0, 1, 0]]

grid = [[0, 0, 1, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [0, 0, 0, 0, 1, 0],
        [1, 1, 1, 1, 1, 0],
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
# modify code below
# ----------------------------------------

def optimum_policy():
    values = [[99 for row in range(len(grid[0]))] for col in range(len(grid))]
    policy = [[' ' for row in range(len(grid[0]))] for col in range(len(grid))]
    closed = [[0 for row in range(len(grid[0]))] for col in range(len(grid))]

    x = goal[0]
    y = goal[1]
    closed[x][y] = 1
    policy[x][y] = '*'
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
            policy[x2][y2] = delta_name[(i+2)%len(delta_name)] # The complement of the operation taken to reach the cell
            open.append([f2,x2,y2])

    
    

#    for i in range(len(values)):
#        print values[i]

    for i in range(len(policy)):
        print policy[i]

    return policy # Make sure your function returns the expected grid.

optimum_policy()
