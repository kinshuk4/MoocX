# ----------
# User Instructions:
# 
# Implement the function optimum_policy2D() below.
#
# You are given a car in a grid with initial state
# init = [x-position, y-position, orientation]
# where x/y-position is its position in a given
# grid and orientation is 0-3 corresponding to 'up',
# 'left', 'down' or 'right'.
#
# Your task is to compute and return the car's optimal
# path to the position specified in `goal'; where
# the costs for each motion are as defined in `cost'.

# EXAMPLE INPUT:

# grid format:
#     0 = navigable space
#     1 = occupied space 
grid = [[1, 1, 1, 0, 0, 0],
        [1, 1, 1, 0, 1, 0],
        [0, 0, 0, 0, 0, 0],
        [1, 1, 1, 0, 1, 1],
        [1, 1, 1, 0, 1, 1]]
goal = [2, 0] # final position
init = [4, 3, 0] # first 2 elements are coordinates, third is direction
cost = [2, 1, 20] # the cost field has 3 values: right turn, no turn, left turn

# EXAMPLE OUTPUT:
# calling optimum_policy2D() should return the array
# 
# [[' ', ' ', ' ', 'R', '#', 'R'],
#  [' ', ' ', ' ', '#', ' ', '#'],
#  ['*', '#', '#', '#', '#', 'R'],
#  [' ', ' ', ' ', '#', ' ', ' '],
#  [' ', ' ', ' ', '#', ' ', ' ']]
#
# ----------


# there are four motion directions: up/left/down/right
# increasing the index in this array corresponds to
# a left turn. Decreasing is is a right turn.

forward = [[-1,  0], # go up
           [ 0, -1], # go left
           [ 1,  0], # go down
           [ 0,  1]] # do right
forward_name = ['up', 'left', 'down', 'right']

# the cost field has 3 values: right turn, no turn, left turn
action = [-1, 0, 1]
action_name = ['R', '#', 'L']


# ----------------------------------------
# modify code below
# ----------------------------------------


class position:
    def __init__(self, x, y, heading):
        self.x = x
        self.y = y
        self.heading = heading

    def clone(self):
        return position(self.x, self.y, self.heading)

    def __repr__(self):
        return "[%d,%d,%d]" % (self.x, self.y, self.heading)

    def move(self, action_idx):
        """Calculate new position when given an index from the action array"""
        if (isinstance(action_idx, str)):
            # If we got a string the map it via the action_name -list
            action_idx = action_name.index(action_idx)
        action_val = action[action_idx]
        # Get the correct dimension vector based on current heading and action modifier
        move_val = forward[(self.heading + action_val) % len(forward)]
        # Calculate new x and y
        self.x += move_val[0]
        self.y += move_val[1]
        # update the heading
        self.heading = (self.heading + action_val) % len(forward)

def print_2d_array(a):
    for i in range(len(a)):
        print a[i]

def print_3d_array(a):
    for i in range(len(a)):
        print "Dimension %d:" % i
        print_2d_array(a[i])
    
# This is just to test my position class
def test_drive(actions):
    drive_grid = [[' ' for row in range(len(grid[0]))] for col in range(len(grid))]
    p = position(init[0], init[1], init[2])
    for a in actions:
        action_idx = action_name.index(a)
        drive_grid[p.x][p.y] = a
        p.move(action_idx)
    drive_grid[p.x][p.y] = '*'
    
    print_2d_array(drive_grid)

#test_drive(['#','L', 'R', '#', 'L', 'L', '#'])

def optimum_policy2D():
    # NOTE: This implementation uses "naive" search (not A*) to solve the problem, but since in this case we did not actually need the more complex 3D policy map to solve the problem why bother (also: generating the A* heuristic and plugging it in here is not exactly rocket surgery)

    closed = [[[0 for row in range(len(grid[0]))] for col in range(len(grid))] for heading in range(len(forward))]
    expanded = [[[-1 for row in range(len(grid[0]))] for col in range(len(grid))] for heading in range(len(forward))]
    
    #print_3d_array(closed)


    expand_counter = 0
    p = position(init[0], init[1], init[2])
    g = 0 # initial cost
    # Close the starting position
    closed[p.heading][p.x][p.y] = 1
    # The list contains cost, position object and a list of actions taken so far
    expand_list = [[g, p, []]]

    while (True):
        if len(expand_list) == 0:
            print 'fail'
            return 'fail'
        
        # Get next cheapest path to investigate
        expand_list.sort()
        next = expand_list.pop(0) # we can pop the zeroeth element, no need to reverse
#        expand_list.sort(reverse=True)
#        next = expand_list.pop() # we can pop the zeroeth element, no need to reverse
#        expand_list.sort()
#        expand_list.reverse()
#        next = expand_list.pop() # we can pop the zeroeth element, no need to reverse
        g = next[0]
        p = next[1]
        actions_taken = next[2]
        expanded[p.heading][p.x][p.y] = expand_counter
        expand_counter += 1

        if (    p.x == goal[0]
            and p.y == goal[1]):
            #print "solved"
            break
        
        for a in action_name:
            aidx = action_name.index(a)
            p2 = p.clone()
            p2.move(aidx)

            if (   p2.x < 0
                or p2.x >= len(grid) # note the len is one too large that's why >=
                or p2.y < 0
                or p2.y >= len(grid[0])): # note the len is one too large that's why >=
                # outside of grid
                #print "(%d,%d) is outside the grid" % (p2.x, p2.y)
                continue
            
            if closed[p2.heading][p2.x][p2.y]:
                # Already searched (in this dimension...)
                #print "(%d,%d,%d) is already searched" % (p2.x, p2.y, p2.heading)
                continue
            
            if grid[p2.x][p2.y]:
                # Occupied
                #print "(%d,%d) is not navigable" % (p2.x, p2.y)
                continue

            g2 = g + cost[aidx]
            actions_taken2 = actions_taken[:] # Clone the list by slicing
            actions_taken2.append(a)
            expand_list.append([g2, p2, actions_taken2])
            closed[p.heading][p.x][p.y] = 1 # Mark this one as closed from further search

#        print "Expanded"
#        print_3d_array(expanded)
#        print "Closed"
#        print_3d_array(closed)
#        print "To be expanded: %s" % repr(expand_list)

    # If we get this far we're good
    path = [[' ' for row in range(len(grid[0]))] for col in range(len(grid))] # init empty path
    path_p = position(init[0],init[1],init[2])
    for a in actions_taken:
        path[path_p.x][path_p.y] = a
        path_p.move(a)
    path[path_p.x][path_p.y] = '*'


    print_2d_array(path)

    return path # Make sure your function returns the expected grid.

optimum_policy2D()


