colors = [['red', 'green', 'green', 'red' , 'red'],
          ['red', 'red', 'green', 'red', 'red'],
          ['red', 'red', 'green', 'green', 'red'],
          ['red', 'red', 'red', 'red', 'red']]

measurements = ['green', 'green', 'green' ,'green', 'green']
motions = [[0,0],[0,1],[1,0],[1,0],[0,1]]

sensor_right = 0.7
p_move = 0.8

def show(p):
    for i in range(len(p)):
        print p[i]

#DO NOT USE IMPORT
#ENTER CODE BELOW HERE
#ANY CODE ABOVE WILL CAUSE
#HOMEWORK TO BE GRADED
#INCORRECT

p = []

def sum_arr(temp_world):
    total = 0
    for i in range(len(temp_world)):
        total += sum(temp_world[i])
    return total 

def sense(world_colors, probabilities, measured_color, sensor_precision):
    pHit = sensor_precision
    pMiss = 1 - sensor_precision
    
    for i in range(len(world_colors)):
        for j in range(len(world_colors[0])):
            hit = (measured_color == world_colors[i][j])
            probabilities[i][j] *= hit * pHit + ((1-hit) * pMiss)
    
    normalization = sum_arr(probabilities)
    
    for i in range(len(world_colors)):
        for j in range(len(world_colors[0])):
            probabilities[i][j] /= normalization
    return probabilities

def move(world, movement_length, exact_coefficient):
    size_y = len(world)
    size_x = len(world[0]) 
    temp_world = [[0 for i in range(len(world[0]))] for j in range(len(world))]

    for i in range(size_y):
        for j in range(size_x):
            y = movement_length[0]
            x = movement_length[1]
            temp_world[i][j] = world[(i-y) % size_y][(j-x) % size_x] * exact_coefficient
            temp_world[i][j] += world[i][j] * (1.0 - exact_coefficient)
    return temp_world 

#Your probability array must be printed 
#with the following code.

def move_and_measure(world, movement, sensor, movement_precision, sensor_precision):
    p_init = 1.0 / float(len(world)) / float(len(world[0]))
    probabilities = [[p_init for i in range(len(world[0]))] for j in range(len(world))]
    for i in range(len(movement)):
        probabilities = move(probabilities, movement[i], movement_precision)
        probabilities = sense(world, probabilities, sensor[i], sensor_precision)
    show(probabilities)
    return probabilities

test_world = [['green', 'green', 'green'], 
              ['green', 'red'  ,  'red' ],
              ['green', 'green', 'green']]

test_move = [[0,0],[0,1]]
test_sensor = ['red', 'red']

#move_and_measure(test_world, test_move, test_sensor, 0.5, 0.80)
move_and_measure(colors, motions, measurements, p_move, sensor_right)
