"""
Mini-max Tic-Tac-Toe Player
"""

import poc_ttt_gui
import poc_ttt_provided as provided

# Set timeout, as mini-max can take a long time
import codeskulptor
codeskulptor.set_timeout(60)

# SCORING VALUES - DO NOT MODIFY
SCORES = {provided.PLAYERX: 1,
          provided.DRAW: 0,
          provided.PLAYERO: -1}

def mm_move(board, player):
    """
    Make a move on the board.
    
    Returns a tuple with two elements.  The first element is the score
    of the given board and the second element is the desired move as a
    tuple, (row, col).
    """
    
    #NOTE:
    #	1. A board where O wins is always -1
    #	2. A board where X wins is always 1
    #	3. X is the maximizing player, O the minimizing player
    
    # End the game if it is over (base cases)
    if board.check_win() == provided.PLAYERX:
        return SCORES[provided.PLAYERX], (-1,-1)
    elif board.check_win() == provided.PLAYERO:
        return SCORES[provided.PLAYERO], (-1,-1)
    elif board.check_win() == provided.DRAW:
        return SCORES[provided.DRAW], (-1,-1)
    else:
        
        # If not, call mm_move() on all the other moves
        empties = board.get_empty_squares()
        best = float('-Inf')
        for square in empties:
            
            # clone the board
            tmp_board = board.clone()
            
            # current player moves on an empty square
            tmp_board.move(square[0], square[1],player)
            
            # simulate the other player's best move
            opponent = provided.switch_player(player)
            next_move = mm_move(tmp_board,opponent)
            
            # Get the score of this new board
            # The multiplication...
            #	for PLAYER0, boards ending at -1 will be scored 1
            scr = next_move[0]*SCORES[player]
            if scr == 1:
                best_move = next_move[0], (square[0],square[1])
                break
            elif scr > best:
                best = next_move[0]
                best_move = best, (square[0],square[1])
        return best_move
                                      
def move_wrapper(board, player, trials):
    """
    Wrapper to allow the use of the same infrastructure that was used
    for Monte Carlo Tic-Tac-Toe.
    """
    move = mm_move(board, player)
    assert move[1] != (-1, -1), "returned illegal move (-1, -1)"
    return move[1]

# Test game with the console or the GUI.
# Uncomment whichever you prefer.
# Both should be commented out when you submit for
# testing to save time.

#provided.play_game(move_wrapper, 1, False)        
#poc_ttt_gui.run_gui(3, provided.PLAYERO, move_wrapper, 1, False)
