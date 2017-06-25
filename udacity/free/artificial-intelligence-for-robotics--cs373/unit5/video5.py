# -----------
# User Instructions
#
# Define a function smooth that takes a path as its input
# (with optional parameters for weight_data, weight_smooth)
# and returns a smooth path.
#
# Smoothing should be implemented by iteratively updating
# each entry in newpath until some desired level of accuracy
# is reached. The update should be done according to the
# gradient descent equations given in the previous video:
#
# If your function isn't submitting it is possible that the
# runtime is too long. Try sacrificing accuracy for speed.
# -----------


from math import *

# Don't modify path inside your function.
path = [[0, 0],
        [0, 1],
        [0, 2],
        [1, 2],
        [2, 2],
        [3, 2],
        [4, 2],
        [4, 3],
        [4, 4]]

# ------------------------------------------------
# smooth coordinates
#

def smooth(path, weight_data = 0.5, weight_smooth = 0.1, tolerance = 0.000001):

    # Make a deep copy of path into newpath
    newpath = [[0 for row in range(len(path[0]))] for col in range(len(path))]
    for i in range(len(path)):
        for j in range(len(path[0])):
            newpath[i][j] = path[i][j]


    #### ENTER CODE BELOW THIS LINE ###
    alpha = weight_data
    beta = weight_smooth
    # Seems these tolerances are way too high...
    #tolerance = 0.001 * (len(path)-2)
    tmppath = [[0 for row in range(len(path[0]))] for col in range(len(path))]
    last_total_change = 0.0
    while True:
        total_change = 0.0
        for i in range(len(path)):
            # Skip first and last
            if (   i == 0
                or i == len(path)-1):
                continue
            
            for j in range(len(path[i])): # Update each term (we don't have the matrix class now...)
                # "official example (that in actually a bit wrong, see http://www.udacity-forums.com/cs373/questions/23361/unit5-5-gradient-descent-simultaneous-update)
#                tmptmp = newpath[i][j]
#                newpath[i][j] += weight_data * (path[i][j] - newpath[i][j])
#                newpath[i][j] += weight_smooth * (newpath[i-1][j] + newpath[i+1][j] - (2.0 * newpath[i][j]))
#                total_change += abs(tmptmp -  newpath[i][j])
                
                
                # Version doing it properly (from same URL)
                alpha_value = alpha * (path[i][j] - newpath[i][j])
                beta_value = beta * (newpath[i+1][j] + newpath[i-1][j] - 2 * newpath[i][j])
                tmppath[i][j] += alpha_value + beta_value
                
                #print "alpha_value=%f beta_value=%f" % (alpha_value, beta_value)

                total_change += abs(alpha_value+beta_value) # This produces a value approaching zero
            
        newpath = tmppath

        print "total_change %f (tolerance %f)" % (total_change, tolerance)
        if (   total_change < tolerance # Good enough
            or total_change == last_total_change): # Stops changing
            break
        
        last_total_change = total_change

    return newpath # Leave this line for the grader!

# feel free to leave this and the following lines if you want to print.
newpath = smooth(path)

# thank you - EnTerr - for posting this on our discussion forum
for i in range(len(path)):
    print '['+ ', '.join('%.3f'%x for x in path[i]) +'] -> ['+ ', '.join('%.3f'%x for x in newpath[i]) +']'





