"""
Monte Carlo Tic-Tac-Toe Player
"""

import codeskulptor
import random
import poc_ttt_gui
import poc_ttt_provided as provided

# Constants for Monte Carlo simulator
# You may change the values of these constants as desired, but
#  do not change their names.
NTRIALS = 300      # Number of trials to run
SCORE_CURRENT = 1.0 # Score for squares played by the current player
SCORE_OTHER = 1.0   # Score for squares played by the other player
EMPTY = provided.EMPTY
DRAW = provided.DRAW
PLAYERX = provided.PLAYERX
PLAYERO = provided.PLAYERO

# Set timeout (while loops are a dangerous beast)
codeskulptor.set_timeout(20)
    
# Add your functions here
def mc_trial(board,player):
    """
    Plays a game on the provided board, starting with the given player and making random moves.
    """
    while board.check_win() == None:
        temp_row = random.randrange(board.get_dim())
        temp_col = random.randrange(board.get_dim())
        if board.square(temp_row,temp_col) == EMPTY:
            board.move(temp_row,temp_col,player)
            player = provided.switch_player(player)
    
def mc_update_scores(scores,board,player):
    """
    Score a completed game of Tic-Tac-Toe.
    """
    if not (board.check_win() == DRAW or board.check_win() == None):
        other = provided.switch_player(player)
        for coord in [(x,y) for x in range(board.get_dim()) for y in range(board.get_dim())]:
            if board.check_win() == player:
                if board.square(coord[0],coord[1]) == player:
                    scores[coord[0]][coord[1]] += SCORE_CURRENT
                elif board.square(coord[0],coord[1]) == other:
                    scores[coord[0]][coord[1]] -= SCORE_OTHER
            elif board.check_win() == other:
                if board.square(coord[0],coord[1]) == other:
                    scores[coord[0]][coord[1]] += SCORE_OTHER
                elif board.square(coord[0],coord[1]) == player:
                    scores[coord[0]][coord[1]] -= SCORE_CURRENT

def get_best_move(board,scores):
    """
    Traverse the scoreboard and find the best square (of those
    empty in the current game board).
    """
    best_score = None
    for square in board.get_empty_squares():
        scr = scores[square[0]][square[1]]
        if scr > best_score:
            best_square = (square[0], square[1])
            best_score = scr
    return best_square
    
def mc_move(board,player,trials):
    """
    Run a Monte Carlo simulation to choose the best next move
    if there is no one-step-ahead win.
    """
    # check if there are any one-step-ahead wins
    one_step_winner = False
    one_step_board = board.clone()
    for coord in [(x,y) for x in range(board.get_dim()) for y in range(board.get_dim())]:
        one_step_board = board.clone()
        one_step_board.move(coord[0], coord[1], player)
        if one_step_board.check_win() == player:
            best_move = (coord[0], coord[1])
            one_step_winner = True
            
    # If no low-hanging fruit, run the Monte Carlo        
    if one_step_winner:
        return best_move
    else:
        # initialize a scoreboard
        #tmp = [[0.0]*(board.get_dim())]*(board.get_dim())
        scoreboard = []
        for dummy_j in range(board.get_dim()):
            inner = []
            for dummy_i in range(board.get_dim()):
                inner.append(0)
            scoreboard.append(inner)
        # run some trials
        for dummy_i in range(trials):
            temp_board = board.clone()
            mc_trial(temp_board,player)
            mc_update_scores(scoreboard,temp_board,player)
        print scoreboard
        #Take the best move, based on the trials
        best_move = get_best_move(board,scoreboard)
        if best_move == False:
            print "You need to run more trials!"
            # pick a random square
            first_sq = board.get_empty_squares()
            first_sq = first_sq[0]
            best_move = (first_sq[0], first_sq[1])
        else:
            best_move = best_move
        return best_move

# Test game with the console or the GUI.  Uncomment whichever 
# you prefer.  Both should be commented out when you submit 
# for testing to save time.
#myboard = provided.TTTBoard(3, False, [[provided.PLAYERX, provided.EMPTY, provided.EMPTY], [provided.PLAYERO, provided.PLAYERO, provided.EMPTY], [provided.EMPTY, provided.PLAYERX, provided.EMPTY]])
#print str(myboard)
#print mc_move(myboard, provided.PLAYERX, NTRIALS)
provided.play_game(mc_move, NTRIALS, False)        
#poc_ttt_gui.run_gui(3, provided.PLAYERX, mc_move, NTRIALS, False)
