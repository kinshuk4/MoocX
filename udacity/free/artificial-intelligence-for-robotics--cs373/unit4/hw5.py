# --------------
# USER INSTRUCTIONS
#
# Write a function called stochastic_value that 
# takes no input and RETURNS two grids. The
# first grid, value, should contain the computed
# value of each cell as shown in the video. The
# second grid, policy, should contain the optimum
# policy for each cell.
#
# Stay tuned for a homework help video! This should
# be available by Thursday and will be visible
# in the course content tab.
#
# Good luck! Keep learning!
#
# --------------
# GRADING NOTES
#
# We will be calling your stochastic_value function
# with several different grids and different values
# of success_prob, collision_cost, and cost_step.
# In order to be marked correct, your function must
# RETURN (it does not have to print) two grids,
# value and policy.
#
# When grading your value grid, we will compare the
# value of each cell with the true value according
# to this model. If your answer for each cell
# is sufficiently close to the correct answer
# (within 0.001), you will be marked as correct.
#
# NOTE: Please do not modify the values of grid,
# success_prob, collision_cost, or cost_step inside
# your function. Doing so could result in your
# submission being inappropriately marked as incorrect.

# -------------
# GLOBAL VARIABLES
#
# You may modify these variables for testing
# purposes, but you should only modify them here.
# Do NOT modify them inside your stochastic_value
# function.

grid = [[0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 1, 1, 0]]

## First test grid from video
#grid = [[0, 0, 0],
#        [0, 0, 0]]
#
## Second test grid from video
#grid = [[0, 1, 0],
#        [0, 0, 0]]
#
       
goal = [0, len(grid[0])-1] # Goal is in top right corner


delta = [[-1, 0 ], # go up
         [ 0, -1], # go left
         [ 1, 0 ], # go down
         [ 0, 1 ]] # go right

delta_name = ['^', '<', 'v', '>'] # Use these when creating your policy grid.

success_prob = 0.5                      
failure_prob = (1.0 - success_prob)/2.0 # Probability(stepping left) = prob(stepping right) = failure_prob
collision_cost = 100                    
cost_step = 1        
                     

############## INSERT/MODIFY YOUR CODE BELOW ##################
#
# You may modify the code below if you want, but remember that
# your function must...
#
# 1) ...be called stochastic_value().
# 2) ...NOT take any arguments.
# 3) ...return two grids: FIRST value and THEN policy.

def print_2d_array(a):
    for i in range(len(a)):
        print a[i]

def get_veer_value(x, y, a, mod, values):
    delta_veer = delta[(a+mod)%len(delta)]
    x2 = x + delta_veer[0]
    y2 = y + delta_veer[1]

    if not is_inside_grid(x2,y2):
        #print "(%d,%d) is outside the grid" % (x2, y2)
        return collision_cost
    
    if (grid[x2][y2] == 1): #Occupied space
        #print "(%d,%d) is occupied" % (x2, y2)
        return collision_cost

    return values[x2][y2]

def is_inside_grid(x2,y2):
    if (   x2 < 0 
        or y2 < 0
        or x2 > len(grid)-1
        or y2 > len(grid[0])-1):
        return False
    return True

def stochastic_value():
    value = [[1000 for row in range(len(grid[0]))] for col in range(len(grid))]
    policy = [[' ' for row in range(len(grid[0]))] for col in range(len(grid))]

    cell_changed = True
    i = 0

    while cell_changed:
        i += 1
        cell_changed = False

        for x in range(len(grid)):
            for y in range(len(grid[0])):
                #print "Iteration #%d, checking %d,%d" % (i, x, y)
                if (    goal[0] == x
                    and goal[1] == y
                    and value[x][y] > 0): 
                    #Goal, set value to zero if nonzero
                    value[x][y] = 0
                    policy[x][y] = '*'
                    cell_changed = True
                    continue
                        
                if grid[x][y] == 1: # Occupied cell, skip it
                    #print "(%d,%d) is occupied" % (x, y)
                    continue

                # Check the movements
                for a in range(len(delta)):
                    x2 = x + delta[a][0]
                    y2 = y + delta[a][1]

                    if not is_inside_grid(x2,y2):
                        #print "(%d,%d) is outside the grid" % (x2, y2)
                        continue

                    if (grid[x2][y2] == 1): #Occupied space, skip
                        #print "(%d,%d) is occupied" % (x2, y2)
                        continue

                    # Calculate stochastic cost
                    # PONDER: Skip the checks above for the succesfull case and calculate *everything* based on the collision costs ?? (second thought though that might give wrong results)
                    v2 = value[x2][y2] * success_prob # Start cost of successfull move
                    v2 += get_veer_value(x, y, a, 1, value) * failure_prob # Add the cost of veering left (weighted by probability)
                    v2 += get_veer_value(x, y, a, -1, value) * failure_prob # Same for veering right
                    v2 += cost_step # And finally the normal cost step

                    if v2 < value[x][y]: # Update cost if cheaper than previous
                        cell_changed = True
                        value[x][y] = v2
                        policy[x][y] = delta_name[a]

    
    return value, policy
