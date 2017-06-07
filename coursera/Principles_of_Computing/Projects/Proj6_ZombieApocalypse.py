"""
Implementation of Zombie Apocalypse mini-project
Name: James Lamb
Date: Oct 18, 2015
"""

import random
import poc_grid
import poc_queue
import poc_zombie_gui

# global constants
EMPTY = 0 
FULL = 1
FOUR_WAY = 0
EIGHT_WAY = 1
OBSTACLE = 5
HUMAN = 6
ZOMBIE = 7


class Apocalypse(poc_grid.Grid):
    """
    Class for simulating zombie pursuit of human on grid with
    obstacles
    """

    def __init__(self, grid_height, grid_width, obstacle_list = None, 
                 zombie_list = None, human_list = None):
        """
        Create a simulation of given size with given obstacles,
        humans, and zombies
        """
        poc_grid.Grid.__init__(self, grid_height, grid_width)
        if obstacle_list != None:
            for cell in obstacle_list:
                self.set_full(cell[0], cell[1])
        if zombie_list != None:
            self._zombie_list = list(zombie_list)
        else:
            self._zombie_list = []
        if human_list != None:
            self._human_list = list(human_list)  
        else:
            self._human_list = []
        self._grid_height = grid_height
        self._grid_width = grid_width
        self._obstacle_list = obstacle_list
        
    def __str__(self):
        """
        Return multi-line string represenation for grid
        """
        ans = "Grid\n"
        for row in range(self._grid_height):
            ans += str(self._cells[row])
            ans += "\n"
        ans += "Humans: "
        ans += str(self._human_list)
        ans += "\n"
        ans += "Zombies: "
        ans += str(self._zombie_list)        
        return ans
        
    def clear(self):
        """
        Set cells in obstacle grid to be empty
        Reset zombie and human lists to be empty
        """
        poc_grid.Grid.clear(self)
        self._zombie_list = []
        self._human_list = []
        
    def add_zombie(self, row, col):
        """
        Add zombie to the zombie list
        """
        self._zombie_list.append((row,col))
                
    def num_zombies(self):
        """
        Return number of zombies
        """
        return len(self._zombie_list)       
          
    def zombies(self):
        """
        Generator that yields the zombies in the order they were
        added.
        """
        dummy_n = 0
        while dummy_n < self.num_zombies():
            zombie = self._zombie_list[dummy_n]
            yield zombie
            dummy_n += 1

    def add_human(self, row, col):
        """
        Add human to the human list
        """
        self._human_list.append((row,col))
        
    def num_humans(self):
        """
        Return number of humans
        """
        return len(self._human_list)       
    
    def humans(self):
        """
        Generator that yields the humans in the order they were added.
        """
        # replace with an actual generator
        dummy_n = 0
        while dummy_n < self.num_humans():
            yield self._human_list[dummy_n]
            dummy_n += 1
        
    def compute_distance_field(self, entity_type):
        """
        Function computes and returns a 2D distance field
        Distance at member of entity_list is zero
        Shortest paths avoid obstacles and use four-way distances
        """ 
        
        # Create a new grid of the same size as the original grid
        visited = poc_grid.Grid(self._grid_height, self._grid_width)
        # Create a 2D list distance_field of the grid size and initialize each of its entries
        # ...to be the product of height times width
        distance_field = [[(self._grid_width*self._grid_height) for dummy_col in range(self.get_grid_width())]
                          for dummy_row in range(self.get_grid_height())]
        # Create a queue boundary that is a copy of either zombie or human list.
        # For cells in the queue, initialize visited to be FULL and distance_field...
        # ...to be zero. we recommend that you use our Queue class.
        boundary = poc_queue.Queue()
        if entity_type == HUMAN:
            for entity in self.humans():
                boundary.enqueue(entity)
                # Mark the fields with these entities as visited
                visited.set_full(entity[0],entity[1])
               
                # set distance for these fields to 0
                distance_field[entity[0]][entity[1]] = 0
                
        elif entity_type == ZOMBIE:
            for entity in self.zombies():
                boundary.enqueue(entity)
                # Mark the fields with these entities as visited
                visited.set_full(entity[0],entity[1])
                # set distance for these fields to 0
                distance_field[entity[0]][entity[1]] = 0
                
        # Be sure to set the cells with obstacles to be fulls as well
        for entity in self._obstacle_list:
            visited.set_full(entity[0],entity[1])
                
        # Finally, implement a modified version of the BFS search described above
        # ... For each neighbor_cell in the inner loop, check whether
        # ... the cell has not been visited and is passable.
        # If so, update the visited grid and the boundary queue as specified
        # In this case, also update the neighbor's distance to be distance to
        # ...current_cell plus one
        while len(boundary) != 0:
            current_cell = boundary.dequeue()
            c_row = current_cell[0]
            c_col = current_cell[1]
            
            neighbors = visited.four_neighbors(c_row,c_col)
            
            for neighbor in neighbors:
                # Be sure not to include edge cells
                n_row = neighbor[0]
                n_col = neighbor[1]
                if n_row >= 0 and n_row < (self.get_grid_height()) and n_col >=0 and n_col < (self.get_grid_width()):
                    # "EMPTY" captures visited cells and impassable cells
                    if visited.is_empty(neighbor[0],neighbor[1]):
                        # Mark this cell as visited
                        visited.set_full(neighbor[0],neighbor[1])
                        # Put that neighbor in the queue to be checked
                        boundary.enqueue(neighbor)
                        # Update the neighbor's distance to be distance to current cell + 1
                        distance_field[neighbor[0]][neighbor[1]] = distance_field[c_row][c_col] + 1        
        return distance_field
                    
    def move_humans(self, zombie_distance_field):
        """
        Function that moves humans away from zombies, diagonal moves
        are allowed
        """
        # Get neighbors to move to
        print "Moving humans..."
        dummy_indx = 0
        for human in self.humans():
            # Initialize best position to staying put
            best_location = human
            highest_distance = zombie_distance_field[human[0]][human[1]]
            neighbors = self.eight_neighbors(human[0],human[1])
            for neighbor in neighbors:
                row = neighbor[0]
                col = neighbor[1]
                if row >= 0 and row < (self.get_grid_height()) and col >=0 and col < (self.get_grid_width()):
                    distance = zombie_distance_field[neighbor[0]][neighbor[1]]
                    if distance > highest_distance and self.is_empty(neighbor[0],neighbor[1]):
                        highest_distance = distance
                        best_location = neighbor
            # Move this human to the best location                                                 
            self._human_list[dummy_indx] = best_location
            dummy_indx += 1
    
    def move_zombies(self, human_distance_field):
        """
        Function that moves zombies towards humans, no diagonal moves
        are allowed
        """
        dummy_indx = 0
        for zombie in self.zombies():
            # Initialize best position to staying put
            best_location = zombie
            lowest_distance = human_distance_field[zombie[0]][zombie[1]]
            neighbors = self.four_neighbors(zombie[0],zombie[1])
            for neighbor in neighbors:
                row = neighbor[0]
                col = neighbor[1]
                if row >= 0 and row < (self.get_grid_height()) and col >=0 and col < (self.get_grid_width()):
                    distance = human_distance_field[row][col]
                    if distance < lowest_distance and self.is_empty(neighbor[0],neighbor[1]):
                        lowest_distance = distance
                        best_location = neighbor
            # Move this zombie to the best location                                                 
            self._zombie_list[dummy_indx] = best_location
            dummy_indx += 1

# Start up gui for simulation - You will need to write some code above
# before this will work without errors

#poc_zombie_gui.run_gui(Apocalypse(30, 40))
#obj = Apocalypse(20, 30, [(4, 15), (5, 15), (6, 15), (7, 15), (8, 15), (9, 15), (10, 15), (11, 15), (12, 15), (13, 15), (14, 15), (15, 15), (15, 14), (15, 13), (15, 12), (15, 11), (15, 10)], [], [(18, 14), (18, 20), (14, 24), (7, 24), (2, 22)])
#obj.compute_distance_field(HUMAN)
#Apocalypse(3, 3, [], [(1, 1)], [(1, 1)]) 
#dist = [[2, 1, 2], [1, 0, 1], [2, 1, 2]]
#obj.move_zombies(dist) 
#then obj.zombies()