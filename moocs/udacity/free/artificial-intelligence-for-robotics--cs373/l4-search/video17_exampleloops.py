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
# Don't forget to mark the goal with a '*'

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
# modify code below
# ----------------------------------------

def print_2d_array(a):
    for i in range(len(a)):
        print a[i]

def print_3d_array(a):
    for i in range(len(a)):
        print "Dimension %d:" % i
        print_2d_array(a[i])
    

def optimum_policy():
    value = [[99 for row in range(len(grid[0]))] for col in range(len(grid))]
    policy = False

    cell_changed = True
    i = 0

    while cell_changed:
        i += 1
        cell_changed = False

        for x in range(len(grid)):
            for y in range(len(grid[0])):
                print "Iteration #%d, checking %d,%d" % (i, x, y)
                if (    goal[0] == x
                    and goal[1] == y
                    and value[x][y] > 0): 
                    #Goal, set value to zero if nonzero
                    value[x][y] = 0
                    cell_changed = True
                    continue
                        
                if grid[x][y] == 1: # Occupied cell, skip it
                    #print "(%d,%d) is occupied" % (x, y)
                    continue

                # Check the movements
                for a in range(len(delta)):
                    x2 = x + delta[a][0]
                    y2 = y + delta[a][1]

                    if (   x2 < 0 # Skip values outside of the grid
                        or y2 < 0
                        or x2 > len(grid)-1
                        or y2 > len(grid[0])-1):
                        print "(%d,%d) is outside the grid" % (x2, y2)
                        continue

                    if (grid[x2][y2] == 1): #Occupied space, do not expand
                        print "(%d,%d) is occupied" % (x2, y2)
                        continue

                    v2 = value[x2][y2] + cost_step
                    if v2 < value[x][y]: # Update cost if cheaper than previous
                        cell_changed = True
                        value[x][y] = v2

    print_2d_array(value)

    return policy # Make sure your function returns the expected grid.

optimum_policy()
