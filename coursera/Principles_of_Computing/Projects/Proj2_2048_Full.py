"""
Clone of 2048 game.
"""

import poc_2048_gui
import random

# Directions, DO NOT MODIFY
UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

# Offsets for computing tile indices in each direction.
# DO NOT MODIFY this dictionary.
OFFSETS = {UP: (1, 0),
           DOWN: (-1, 0),
           LEFT: (0, 1),
           RIGHT: (0, -1)}

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
    Helper function that merges a single row or column in 2048
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

class TwentyFortyEight:
    """
    Class to run the game logic.
    """

    def __init__(self, grid_height, grid_width):
        self.__grid_height__ = grid_height
        self.__grid_width__ = grid_width
        self.__initial_cells__ = {
            UP: [(x, y) for x in [0] for y in range(grid_width)],
            DOWN: [(x, y) for x in [grid_height - 1] for y in range(grid_width)],
            LEFT: [(x, y) for x in range(grid_height) for y in [0]],
            RIGHT: [(x, y) for x in range(grid_height) for y in [grid_width-1]]
            }
        self.reset()
        
    def reset(self):
        """
        Reset the game so the grid is empty except for two
        initial tiles.
        """
        # Initial grid full of 0s of the appropriate dimensions
        self.__grid__ = [[0 for dummy_col in range(self.__grid_width__)] for dummy_row in range(self.__grid_height__)]
        self.new_tile()
        self.new_tile()

    def __str__(self):
        """
        Return a string representation of the grid for debugging.
        """
        return "\n".join(str(row) for row in self.__grid__)

    def get_grid_height(self):
        """
        Get the height of the board.
        """
        return self.__grid_height__

    def get_grid_width(self):
        """
        Get the width of the board.
        """
        return self.__grid_width__

    def move(self, direction):
        """
        Move all tiles in the given direction and add
        a new tile if any tiles moved.
        """
        offset = OFFSETS[direction]
        any_moved = False
        for cell in self.__initial_cells__[direction]:
            # iteration once to get values
            iteration = 0
            temp = []
            temp_row = cell[0]
            temp_col = cell[1]
            while (temp_row in range(self.__grid_height__)) and (temp_col in range(self.__grid_width__)):
                temp_row = cell[0]+(iteration*offset[0])
                temp_col = cell[1]+(iteration*offset[1])
                if not ((temp_row in range(self.__grid_height__)) and (temp_col in range(self.__grid_width__))):
                    break
                else:
                    temp.append(self.__grid__[temp_row][temp_col])
                    iteration += 1
                
            # run merge
            updated_vec = merge(temp)
            print "Temp", updated_vec
            
            #fill those values back into the grid
            iteration = 0
            temp_row = cell[0]
            temp_col = cell[1]
            while (temp_row in range(self.__grid_height__)) and (temp_col in range(self.__grid_width__)):
                temp_row = cell[0]+(iteration*offset[0])
                temp_col = cell[1]+(iteration*offset[1])
                if not ((temp_row in range(self.__grid_height__)) and (temp_col in range(self.__grid_width__))):
                    break
                else:
                    if self.__grid__[temp_row][temp_col] != updated_vec[iteration]:
                        any_moved = True
                    self.__grid__[temp_row][temp_col] = updated_vec[iteration]
                    iteration += 1
            
        #Add a new tile if anything changes
        if any_moved == True:
            self.new_tile()
        any_moved = False

    def new_tile(self):
        """
        Create a new tile in a randomly selected empty
        square.  The tile should be 2 90% of the time and
        4 10% of the time.
        """
        # random val
        tile_val = 2 + 2*((random.randrange(1,11)/10.0)>0.9)
        # pick a non-zero grid cell
        cells_checked = 0
        while cells_checked < (self.__grid_height__*self.__grid_width__):
            rand_row = random.randrange(0, self.__grid_height__)
            rand_col = random.randrange(0, self.__grid_width__)
            if self.__grid__[rand_row][rand_col] == 0:
                self.__grid__[rand_row][rand_col] = tile_val
                break
            cells_checked += 1

    def set_tile(self, row, col, value):
        """
        Set the tile at position row, col to have the given value.
        """
        self.__grid__[row][col] = value

    def get_tile(self, row, col):
        """
        Return the value of the tile at position row, col.
        """
        return self.__grid__[row][col]

poc_2048_gui.run_gui(TwentyFortyEight(4, 4))
