# implementation of card game - Memory

import simplegui
import random

# helper function to initialize globals
def new_game():
    global deck, exposed, number_exposed, turns
    
    #Order the cards
    deck = list(range(8)*2)
    random.shuffle(deck)
    
    # Initialize list of correct guesses (seed with Falses)
    exposed = [False]*16

    # initialize number of exposed cards
    number_exposed = 0
    
    #initialzie number of turns
    turns = 0
    label.set_text("Turns = " + str(turns))
    
# define event handlers
def mouseclick(pos):
    global exposed, number_exposed, guess1, guess1_indx, guess2, guess2_indx, turns
    
    # Figure out which card was clicked
    card_indx = pos[0] // 50 
    
    # Do nothing if the clicked card is already exposed
    if exposed[card_indx] == False:
    
        # Handle list of exposed/non-exposed cards
        if number_exposed == 0:
            guess1 = deck[card_indx]
            guess1_indx = card_indx
            exposed[guess1_indx] = True
            number_exposed = 1
            
            # Increment turn count
            turns += 1
            label.set_text("Turns = " + str(turns))
            
        elif number_exposed == 1:
            guess2 = deck[card_indx]
            guess2_indx = card_indx
            exposed[guess2_indx] = True
            number_exposed = 2
        elif number_exposed == 2:
            
            # Increment turns
            turns += 1
            label.set_text("Turns = " + str(turns))
            
            ## Flip the last two cards back over if they were different
            if guess1 <> guess2:
                exposed[guess1_indx] = False
                exposed[guess2_indx] = False

            # Set new candidate
            guess1 = deck[card_indx]
            guess1_indx = card_indx
            exposed[guess1_indx] = True

            number_exposed = 1
                                  
# cards are logically 50x100 pixels in size    
def draw(canvas):
    top = 0
    bottom = 100
    line_width = 1
    iter = -1
    for card in deck:
        iter += 1
        left = 50*iter
        right = left + 50
        if exposed[iter]:
            canvas.draw_polygon([[left, top],[left, bottom], [right, bottom], [right, top]], line_width, 'white', 'Black',)
            canvas.draw_text(str(card), [(left + right-5)// 2, (top + bottom-5) // 2],28,'white')
        else:
            canvas.draw_polygon([[left, top],[left, bottom], [right, bottom], [right, top]], line_width, 'white', 'Green',)


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Reset", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
frame.start()
new_game()
