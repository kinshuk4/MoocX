"""
Loyd's Fifteen puzzle - solver and visualizer
Note that solved configuration has the blank (zero) tile in upper left
Use the arrows key to swap this tile with its neighbors
"""

import poc_fifteen_gui

class Puzzle:
    """
    Class representation for the Fifteen puzzle
    """

    def __init__(self, puzzle_height, puzzle_width, initial_grid=None):
        """
        Initialize puzzle with default height and width
        Returns a Puzzle object
        """
        self._height = puzzle_height
        self._width = puzzle_width
        self._grid = [[col + puzzle_width * row
                       for col in range(self._width)]
                      for row in range(self._height)]

        if initial_grid != None:
            for row in range(puzzle_height):
                for col in range(puzzle_width):
                    self._grid[row][col] = initial_grid[row][col]

    def __str__(self):
        """
        Generate string representaion for puzzle
        Returns a string
        """
        ans = ""
        for row in range(self._height):
            ans += str(self._grid[row])
            ans += "\n"
        return ans

    #####################################
    # GUI methods

    def get_height(self):
        """
        Getter for puzzle height
        Returns an integer
        """
        return self._height

    def get_width(self):
        """
        Getter for puzzle width
        Returns an integer
        """
        return self._width

    def get_number(self, row, col):
        """
        Getter for the number at tile position pos
        Returns an integer
        """
        return self._grid[row][col]

    def set_number(self, row, col, value):
        """
        Setter for the number at tile position pos
        """
        self._grid[row][col] = value

    def clone(self):
        """
        Make a copy of the puzzle to update during solving
        Returns a Puzzle object
        """
        new_puzzle = Puzzle(self._height, self._width, self._grid)
        return new_puzzle

    ########################################################
    # Core puzzle methods

    def current_position(self, solved_row, solved_col):
        """
        Locate the current position of the tile that will be at
        position (solved_row, solved_col) when the puzzle is solved
        Returns a tuple of two integers        
        """
        solved_value = (solved_col + self._width * solved_row)

        for row in range(self._height):
            for col in range(self._width):
                if self._grid[row][col] == solved_value:
                    return (row, col)
        assert False, "Value " + str(solved_value) + " not found"

    def update_puzzle(self, move_string):
        """
        Updates the puzzle state based on the provided move string
        """
        zero_row, zero_col = self.current_position(0, 0)
        for direction in move_string:
            if direction == "l":
                assert zero_col > 0, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row][zero_col - 1]
                self._grid[zero_row][zero_col - 1] = 0
                zero_col -= 1
            elif direction == "r":
                assert zero_col < self._width - 1, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row][zero_col + 1]
                self._grid[zero_row][zero_col + 1] = 0
                zero_col += 1
            elif direction == "u":
                assert zero_row > 0, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row - 1][zero_col]
                self._grid[zero_row - 1][zero_col] = 0
                zero_row -= 1
            elif direction == "d":
                assert zero_row < self._height - 1, "move off grid: " + direction
                self._grid[zero_row][zero_col] = self._grid[zero_row + 1][zero_col]
                self._grid[zero_row + 1][zero_col] = 0
                zero_row += 1
            else:
                assert False, "invalid direction: " + direction
    
    ##################################################################
    # My helper methods
    
    def get_cells_below(self, target_row,target_col):
        """
        Returns a list of tuples containing the index of all cells
        to the right of (target_row,target_col) and all in the rows below
        it.
        """
        neighblst = []
        if target_row == (self._height-1) and target_col == (self._width-1):
            return True
        else:
            for row in range(target_row,self.get_height()):
                if row == target_row and (target_col == self.get_width()-1):
                    continue
                if row == target_row and (target_col != self.get_width()-1):
                    colrange = range(target_col+1, self._width)
                else:
                    colrange = range(0,self.get_width())
                for col in colrange:
                    neighblst.append((row,col))
            return neighblst
    
    def get_loc(self, num):
        """
        Find the current location of some integer on the board.
        """
        for row in range(self._height):
            for col in range(self._width):
                here = self.get_number(row,col)
                if here == num:
                    return (row,col)
                else:
                    continue
                    
    def move_up(self, zero_loc, dest_loc):
        """
        Move the zero tile up to a certain cell from its current
        location. Start by immediately going up, then right or left
        once on the desired row.
        """
        dest_row = dest_loc[0]
        dest_col = dest_loc[1]
        zero_row = zero_loc[0]
        zero_col = zero_loc[1]
        move_str = ""
        
        ups = zero_row - dest_row
        move_str += "".join(['u']*ups)
        if dest_col < zero_col:
            lefts = zero_col - dest_col
            move_str +=  "".join(['l']*lefts)
        elif dest_col > zero_col:
            rights = dest_col - zero_col
            move_str += "".join(['r']*rights)
        return move_str
    
    def move_down(self, zero_loc, target_loc, trgt_tile_loc):
        """
        If legal, move the zero tile (at zero loc) to move the target
        tile (at trgt_loc) to it's target location (target_loc).
        Return the move string for the zero.
        """
        # Go to the left until we're one left of the target
        next_bit = "".join(['lddru']*(target_loc[0]-trgt_tile_loc[0]))
        return next_bit
    
    ##################################################################
    # Phase one methods
                
    def lower_row_invariant(self, target_row, target_col):
        """
        Check whether the puzzle satisfies the specified invariant
        at the given position in the bottom rows of the puzzle (target_row > 1)
        Returns a boolean
        """
        if target_row > (self.get_height()-1) or target_col > (self.get_width()-1):
            return False
        elif self.get_number(target_row, target_col) != 0:
            return False
        else:
            dummy_cond = True
            if self.get_cells_below(target_row, target_col) == True:
                return True
            else:
                for cell in self.get_cells_below(target_row, target_col):
                    # Is the value in this cell the one that will be there in the solution?
                    if self.current_position(cell[0],cell[1]) != (cell[0],cell[1]):
                        dummy_cond = False
                        break
            return dummy_cond
            
    def solve_interior_tile(self, target_row, target_col):
        """
        Place correct tile at target position
        Updates puzzle and returns a move string.
        Only called when row > 1, col != 0
        """
        assert self.lower_row_invariant(target_row,target_col)
        
        target_loc = (target_row, target_col)
        
        # Create a dummy board to play with
        tmp_brd = self.clone()

        # 1. Move the 0 up to the location of the target (only ups and lefts)
        move_str = (self.move_up(target_loc,
                                 self.current_position(target_row,target_col))) 
                                                      
        trgt_num = (target_row*self.get_width()) + target_col

        # Now the 0 is in wherever the target was. Either right,left,or above
        
        # 2. Apply moves to get the target tile to the correct location
        
        tmp_brd.update_puzzle(move_str)
        next_bit = ""
       
        #what's the board looking like?
        zero_loc = tmp_brd.get_loc(0)
        trgt_tile_loc = tmp_brd.get_loc(trgt_num)
        
        # Case 1: target tile is in the correct column
        if trgt_tile_loc[1] == target_col and zero_loc[1] == target_col:
            # Just move it down (if they're in the same col, 0 must be directly above the target tile)
            next_bit += self.move_down(zero_loc, target_loc, trgt_tile_loc) + 'ld'
            
        # Case 2: target tile is to the left of the target column
        elif zero_loc[1] < trgt_tile_loc[1]:
            if trgt_tile_loc[0] != 0:
                #move the 0 to the spot above the target tile
                dest = (trgt_tile_loc[0]-1, trgt_tile_loc[1])
                next_bit += self.move_up(zero_loc, dest)
                tmp_brd.update_puzzle(next_bit)
                zero_loc = tmp_brd.get_loc(0)
                next_bit += self.move_down(zero_loc, target_loc, trgt_tile_loc) + 'ld'
            else:
                if trgt_tile_loc[1] != self.get_width()-1:
                    
                    # Move the zero to 1 above the target, in the correct col
                    next_bit += 'd'
                    rights = target_loc[1] - zero_loc[1] -1
                    next_bit += "".join(['r']*rights) + 'urdlur'
                    
                    tmp_tmp_brd = tmp_brd.clone()
                    tmp_tmp_brd.update_puzzle(next_bit)
                    zero_loc = tmp_tmp_brd.get_loc(0)
                    trgt_tile_loc = tmp_tmp_brd.get_loc(trgt_num)
                    next_bit += self.move_down(zero_loc, target_loc, trgt_tile_loc) + 'ld'
                    
                else:

                    #move the target tile 1 row down, then go put the 0 on top of it
                    next_bit += "".join(['r']*rights) + 'uldrudlur'
                    tmp_brd.update_puzzle(next_bit)
                    zero_loc = tmp_brd.get_loc(0)
                    trgt_tile_loc = tmp_brd.get_loc(trgt_num)
                    next_bit += tmp_brd.move_down(zero_loc, target_loc, trgt_tile_loc) + 'ld'
                
        # Case 3 Need to move it left
        elif zero_loc[1] > trgt_tile_loc[1]:
            if trgt_tile_loc[0] != 0:
                #move the 0 to the spot above the target tile
                dest = (trgt_tile_loc[0]-1, trgt_tile_loc[1])
                next_bit += self.move_up(zero_loc, dest)
                tmp_brd.update_puzzle(next_bit)
                zero_loc = tmp_brd.get_loc(0)
                next_bit += self.move_down(zero_loc, target_loc, trgt_tile_loc) + 'ld'
            else:
                #move the target tile 1 row down, then go put the 0 on top of it
                next_bit += 'd'
                lefts = zero_loc[1] - target_col
                next_bit += "".join(['l']*lefts) + 'u'

                tmp_brd.update_puzzle(next_bit)
                zero_loc = tmp_brd.get_loc(0)
                trgt_tile_loc = tmp_brd.get_loc(trgt_num)
                next_bit += self.move_down(zero_loc, target_loc, trgt_tile_loc) + 'ld'  
                
        move_str += next_bit
        
        #update the puzzle
        self.update_puzzle(move_str)
        
        return move_str

    def solve_col0_tile(self, target_row):
        """
        Solve tile in column zero on specified row (> 1)
        Updates puzzle and returns a move string
        """
        # make sure we get the right type of board
        assert self.lower_row_invariant(target_row,0)
        
        # make a clone
        tmp_brd = self.clone()
        
        move_str = 'ur'
        tmp_brd.update_puzzle(move_str)
      
        zero_loc = tmp_brd.get_loc(0)
        trgt_num = (target_row*tmp_brd.get_width())
        trgt_tile_loc = tmp_brd.get_loc(trgt_num)
        if trgt_tile_loc == (target_row, 0):
            
            #just need to move the zero to the end of the row
            next_bit = "".join(['r']*(self.get_width()-1-zero_loc[1]))
            move_str += next_bit

        else:
            
            #get the target tile to (i-1,1) and the 0 to (i-1,0)
            if trgt_tile_loc[0] == zero_loc[0]:
                if trgt_tile_loc[1] > zero_loc[1]:
                    next_bit = "".join(['r']*(trgt_tile_loc[1]-zero_loc[1])) + 'ulld' + "".join(['rulld']*(trgt_tile_loc[1]-target_row - 1))
                elif trgt_tile_loc[1] < zero_loc[1]:
                    # only one alignment (target tile is in (i-1,1) could cause this!)
                    next_bit = "".join(['l']*(zero_loc[1]-trgt_tile_loc[1]))
            else:
                # Get under it
                next_bit = "".join(['u']*(zero_loc[0]-trgt_tile_loc[0]-1))
                
                # repeat cyclical moves to bring it over
                if trgt_tile_loc[1] == 0:
                    #assumes target is at exactly (i-2,0)
                    next_bit += 'lurdl'
                elif trgt_tile_loc[1] == 1:
                    
                    if trgt_tile_loc[0] == zero_loc[0]-1:
                        next_bit = "uld"
                        
                    else:
                        # Move it down
                        next_bit += "".join(['ulddru']*(zero_loc[0]-trgt_tile_loc[0]-1)) + 'ld'
                else:

                    # move under it
                    next_bit += "".join(['r']*(trgt_tile_loc[1]-zero_loc[1]))
                    
                    # move it down if necessary. - should be in row (j-1),target at (j-2)
                    tmp_tmp_brd = tmp_brd.clone()
                    if (target_row - trgt_tile_loc[0]) > 2:
                        next_bit += "".join(['ulddr']*(target_row-trgt_tile_loc[0]-2))
                        tmp_tmp_brd.update_puzzle(next_bit)
                        
                    trgt_tile_loc = tmp_tmp_brd.get_loc(trgt_num)    
                    
                    next_bit += 'uldr' + "".join(['ulldr']*(trgt_tile_loc[1]-2)) + 'ulld'
                    
            # Update the board again
            move_str += next_bit
            tmp_brd.update_puzzle(next_bit)

            # Check that we are where we should be
            zero_loc = tmp_brd.get_loc(0)
            trgt_tile_loc = tmp_brd.get_loc(trgt_num)

            # Solve the 3x2 (HW 9)
            solve_3x2 = 'ruldrdlurdluurddlur'
            move_str += solve_3x2
            tmp_brd.update_puzzle(solve_3x2)

            # move the zero to the next bad cell
            next_bit = tmp_brd.__start_solve__()
            if next_bit == "":
                print "Good to go!"
            else:
                move_str += next_bit
                        
        #apply the move
        self.update_puzzle(move_str)
        
        print "end of solve:"
        print str(self)
        
        return move_str

    #############################################################
    # Phase two methods

    def row0_invariant(self, target_col):
        """
        Check whether the puzzle satisfies the row zero invariant
        at the given column (col > 1)
        Returns a boolean
        """
        # is the zero tile at (i, 1)?
        if self.get_loc(0) != (0,target_col):
            return False
        else:
            # Are all positions strictly to the right (here or above) solved?
            all_good = True
            for row in range(0,2):
                for col in range(target_col, self.get_width()):
                    if not (row == 0 and col == target_col):
                        trgt_num = row*self.get_width() + col
                        if self.get_number(row,col) != trgt_num:
                            all_good = False
            if all_good:
                #are the cells underneath this good?
                for row in range(2, self.get_height()):
                    for col in range(self.get_width()):
                        trgt_num = row*self.get_width() + col
                        if self.get_number(row,col) != trgt_num:
                            all_good = False
                    
            return all_good

    def row1_invariant(self, target_col):
        """
        Check whether the puzzle satisfies the row one invariant
        at the given column (col > 1)
        Returns a boolean
        """
        # is the zero tile at (i, 1)?
        if self.get_loc(0) != (1,target_col):
            return False
        else:
            # Are all positions strictly to the right (here or above) solved?
            all_good = True
            for row in range(0,2):
                for col in range(target_col+1, self.get_width()):
                    trgt_num = row*self.get_width() + col
                    if self.get_number(row,col) != trgt_num:
                        all_good = False
            
            if all_good:
                #are the cells underneath this good?
                for row in range(2, self.get_height()):
                    for col in range(self.get_width()):
                        trgt_num = row*self.get_width() + col
                        if self.get_number(row,col) != trgt_num:
                            all_good = False
                    
            return all_good

    def solve_row0_tile(self, target_col):
        """
        Solve the tile in row zero at the specified column
        Updates puzzle and returns a move string
        """
        # make sure we got a good board
        assert self.row0_invariant(target_col)
        
        # clone it
        tmp_brd = self.clone()
        trgt_num =  target_col
        
        # Move the zero to (1, j-1)
        move_str = 'ld'
        tmp_brd.update_puzzle(move_str)
        
        # puzzle state
        
        trgt_tile_loc = tmp_brd.get_loc(trgt_num)
        zero_loc = tmp_brd.get_loc(0)
        if trgt_tile_loc == (0, target_col):
            # we're done!
            self.update_puzzle(move_str)
            return move_str
        else:
            # move the target tile to (1, j-1) and zero to (1, j-2)
            # target directly to the left
            if trgt_tile_loc[0] == 1:

                lefts = (zero_loc[1]-trgt_tile_loc[1])
                if lefts == 1:
                    next_bit = 'l'
                else:  
                    # move over to it
                    next_bit = "".join(['l']*(lefts))
                    # use cyclical moves to replace it
                    next_bit += "".join(['urrdl']*(lefts-1))
            elif trgt_tile_loc[0] == 0:
                #move under it
                lefts = (zero_loc[1]-trgt_tile_loc[1])
                if lefts == 0:
                    next_bit = 'uld'
                else:  
                    # move over to it
                    next_bit = "".join(['l']*(lefts))
                    # use cyclical moves to replace it
                    next_bit += 'urdl' + "".join(['urrdl']*(lefts-1))
            
            # After getting stuff into position, apply the fixed solution
            next_bit += 'urdlurrdluldrruld'
            
        move_str += next_bit
        #update the puzzle
        self.update_puzzle(move_str)
        
        return move_str

    def solve_row1_tile(self, target_col):
        """
        Solve the tile in row one at the specified column
        Updates puzzle and returns a move string
        """
        # make sure we get a good board
        assert self.row1_invariant(target_col)
        
        # clone it
        tmp_brd = self.clone()
        
        # puzzle state
        trgt_num = (self.get_width()) + target_col
        trgt_tile_loc = tmp_brd.get_loc(trgt_num)
        
        # Easy: Directly above
        if trgt_tile_loc == (0, target_col):
            move_str = 'u'
        # Easy: Directly next to
        elif trgt_tile_loc == (1, target_col-1):
            move_str = 'lur'
        elif trgt_tile_loc == (0,target_col-1):
            move_str = 'lurdlur'
        # Somewhere else on the first row
        elif trgt_tile_loc[0] == 0:
            #get under it
            gap = (target_col-trgt_tile_loc[1])
            move_str = "".join(['l']*gap)
            #move it over
            move_str += "".join(['urdlurrdl']*(gap-1)) + 'ur'
        #somewhere else on row 0
        else:
            # switch places with it
            gap = (target_col-trgt_tile_loc[1])
            move_str = "".join(['l']*gap)

            # use cyclical moves to get it back
            move_str += "".join(['urrdl']*(gap-1))

            # move the 0 to the correct location
            move_str += 'ur'
        
        # call the invariant
        tmp_brd.update_puzzle(move_str)
        
        assert tmp_brd.row0_invariant(target_col), "solve_row1 produced a bad move"
        
        #apply the move
        self.update_puzzle(move_str)
        
        return move_str

    ###########################################################
    # Phase 3 methods

    def solve_2x2(self):
        """
        Solve the upper left 2x2 part of the puzzle
        Updates the puzzle and returns a move string
        """
        # Make sure we got a good board
        assert self.row1_invariant(1), "Not today, bro"
        
        # clone it
        tmp_brd = self.clone()
        
        # puzzle state (remember we are in cell (1,1)
        
        move_str = "ul"
        tmp_brd.update_puzzle(move_str)
        
        # Did we get lucky?
        if tmp_brd.__top_four_invariant__():
            return True
        else:
            # let's try 3 rdlu's (reverts to the main one after 3)
            next_bit = ""
            for dummy_i in range(1,4):
                next_bit += "rdlu"
                tmp_brd.update_puzzle('rdlu')
                if tmp_brd.__top_four_invariant__():
                    move_str += next_bit
                    break
                else:
                    continue
            if tmp_brd.__top_four_invariant__():
                next_bit = ""
                for dummy_i in range(1,4):
                    next_bit += "drlu"
                    tmp_brd.update_puzzle('drlu')
                    if tmp_brd.__top_four_invariant__():
                        move_str += next_bit
                        break
                    else:
                        continue
                        
            #update the puzzle
            self.update_puzzle(move_str)
            
            return move_str

    def __top_four_invariant__(self):
        """
        Are the top four cells solved?
        """
        dummy_a = int(self.get_number(0,0) == 0)
        dummy_b = int(self.get_number(0,1) == 1)
        dummy_c = int(self.get_number(1,0) == self.get_width())
        dummy_d = int(self.get_number(1,1) == self.get_width()+1)
        if sum([dummy_a, dummy_b, dummy_c, dummy_d])==4:
            return True
        else:
            return False
        
    def __find_bad_cell__(self):
        """
        Returns True if the puzzle is fully solved,
        otherwise False.
        """
        
        # If the puzzle is solved, return "True"
        bad_cell = True
        
        #Check everything below the top two rows, from bottom up
        for row in range(2, self.get_height())[::-1]:
            for col in range(self.get_width())[::-1]:
                trgt_num = (row*self.get_width()) + col
                if self.get_loc(trgt_num) != (row,col):
                    bad_cell = (row,col)
                    break
            if bad_cell != True:
                break
                
        #Check the top two rows (work backwards from the columns, up from rows)
        if bad_cell == True:
            for col in range(self.get_width())[::-1]:
                for row in range(2)[::-1]:
                    trgt_num = (row*self.get_width()) + col
                    if self.get_loc(trgt_num) != (row,col):
                        bad_cell = (row,col)
                        break
                if bad_cell != True:
                    break      
        return bad_cell
    
    def __start_solve__(self):
        """
        Takes a solvable but not-yet-solved puzzle,
        returns the move string to move the zero tile to 
        wherever it needs to be to begin
        solving the puzzle.
        """
        # clone board
        tmp_brd = self.clone()
        move_str = ""
        
        #Find the first incorrect cell (count up from the bottom)
        bad_cell = tmp_brd.__find_bad_cell__() 

        # Zero location
        zero_loc = tmp_brd.get_loc(0)
        # move the zero to wherever the bad cell is
        if bad_cell[0] == 1:
            # Only use downs and rights
            move_str += "".join(['d']*(1-zero_loc[0])) + "".join(['r']*(bad_cell[1]-zero_loc[1]))
        elif bad_cell[0] == 0:
            # Only use ups and rights
            move_str += "".join(['u']*(zero_loc[0])) + "".join(['r']*(bad_cell[1]-zero_loc[1]))
        else:
            if bad_cell[1] == 0:
                # Only lefts and downs
                # --- left first ---
                move_str += "".join(['l']*(zero_loc[1]-bad_cell[1]))
                # --- then down ---
                move_str += "".join(['d']*(bad_cell[0]-zero_loc[0]))
            else:
                # --- left/right first ---
                if bad_cell[1] < zero_loc[1]:
                    move_str += "".join(['l']*(zero_loc[1]-bad_cell[1]))
                elif bad_cell[1] > zero_loc[1]:
                    move_str += "".join(['r']*(bad_cell[1]-zero_loc[1]))
                # --- then down ---
                move_str += "".join(['d']*(bad_cell[0]-zero_loc[0]))

        return move_str
            
    def solve_puzzle(self):
        """
        Generate a solution string for a puzzle
        Updates the puzzle and returns a move string
        """
        # Clone the board
        tmp_brd = self.clone()
        print "BEGINNING:"
        print str(tmp_brd)
        
        # Did we get lucky?
        if tmp_brd.__find_bad_cell__() == True:
            return ""
        else:
           
            # well shit. It was worth a shot. Moving on...
            
            #String to move the zero into place
            move_str = tmp_brd.__start_solve__()
                
            # Actually move the zero into place
            tmp_brd.update_puzzle(move_str)
            print "Getting the 0 in the right place..."
            print str(tmp_brd)
            
            # Solve the bottom two rows
            zero_loc = tmp_brd.get_loc(0)
            while not zero_loc[0] in [0,1]:
                zero_loc = tmp_brd.get_loc(0)
                print "ZERO:", zero_loc
                if zero_loc[1] == 0:
                    print "blegh"
                    print str(tmp_brd)
                    assert tmp_brd.lower_row_invariant(zero_loc[0],zero_loc[1])
                    next_bit = tmp_brd.solve_col0_tile(zero_loc[0])
                    move_str += next_bit
                else:
                    print "HERE"
                    print tmp_brd.get_cells_below(zero_loc[0],zero_loc[1])
                    assert tmp_brd.lower_row_invariant(zero_loc[0],zero_loc[1])
                    next_bit = tmp_brd.solve_interior_tile(zero_loc[0],zero_loc[1])
                    
                    print "move:", next_bit
                    print "result:"
                    print str(tmp_brd)
                    
                    move_str += next_bit
                
                zero_loc = tmp_brd.get_loc(0)
                if zero_loc[0] in [0,1]:
                    break
                    
            # Solve the top two rows (except 2x2)
            zero_loc = tmp_brd.get_loc(0)
            while not (zero_loc[0] in [0,1] and zero_loc[1] in [0,1]):
                zero_loc = tmp_brd.get_loc(0)
                if tmp_brd.row0_invariant(zero_loc[1]):                    
                    next_bit = tmp_brd.solve_row0_tile(zero_loc[1])
                    move_str += next_bit
                    print "next"
                    print str(tmp_brd)
                    assert tmp_brd.row1_invariant(zero_loc[1]-1)
                else:
                    print "row1 part here"
                    next_bit = tmp_brd.solve_row1_tile(zero_loc[1])
                    move_str += next_bit

                    print "next:"
                    print str(tmp_brd)
                    assert tmp_brd.row0_invariant(zero_loc[1])
                    
                zero_loc = tmp_brd.get_loc(0)
                    
            # Thankfully, mercifully, solve the 2x2
            next_bit = tmp_brd.solve_2x2()
            
            print "next:"
            print str(tmp_brd)

            move_str += next_bit
            
        self.update_puzzle(move_str)
            
        return move_str

# Start interactive simulation
#poc_fifteen_gui.FifteenGUI(Puzzle(4,4, [[4,2,0,3],[5,1,6,7],[8,9,10,11],[12,13,14,15]]))


#poc_fifteen_gui.FifteenGUI(Puzzle(3, 3, [[2, 5, 4], [1, 3, 0], [6, 7, 8]]))
#obj = Puzzle(4, 5, [[15, 16, 0, 3, 4], [5, 6, 7, 8, 9], [10, 11, 12, 13, 14], [1, 2, 17, 18, 19]])
#obj.solve_puzzle()

#obj = Puzzle(4,5, [[11, 5, 7, 3, 4],[10, 2, 6, 8, 9],[1, 0, 12, 13, 14],[15, 16, 17, 18, 19]])
#obj.solve_interior_tile(2,1)

#obj = Puzzle(4,5, [[5, 10, 7, 3, 4], [2, 1, 6, 8, 9], [0, 11, 12, 13, 14], [15, 16, 17, 18, 19]])
#obj.solve_col0_tile(2)    


#obj = Puzzle(3, 3, [[8, 7, 6], [5, 4, 3], [2, 1, 0]])
#obj.solve_interior_tile(2, 2)





