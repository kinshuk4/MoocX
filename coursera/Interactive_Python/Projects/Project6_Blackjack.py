# Mini-project #6 - Blackjack

import simplegui
import random

# load card sprite - 936x384 - source: jfitz.com
CARD_SIZE = (72, 96)
CARD_CENTER = (36, 48)
card_images = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/cards_jfitz.png")

CARD_BACK_SIZE = (72, 96)
CARD_BACK_CENTER = (36, 48)
card_back = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/card_jfitz_back.png")    

# initialize some useful global variables
in_play = False
outcome = "Hit or stand?"
score = 0

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}


# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        self.card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, self.card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_SIZE)
        
# define hand class
class Hand:
    def __init__(self):
        self.cards_in_hand = []

    def __str__(self):
        self.description = "Hand Contains"
        for card in self.cards_in_hand:
            self.description += " " + card.__str__()
        return self.description

    def add_card(self, card):
        self.cards_in_hand.append(card)

    def get_value(self):
        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        hand_value = 0
        ace_list = []
        number_of_aces = len([i for i in self.cards_in_hand if i in ace_list])
        aces_used = 0
                
        # The idea: loop through, skip aces, then do them at the end
        for card in self.cards_in_hand:
            
            if card.rank != 'A':
                hand_value += VALUES[card.rank]
            else:
                ace_list.append(card)
            
        if len(ace_list) > 0:
            hand_value += len(ace_list)
            for card in ace_list:
                if (hand_value + 10) <= 21:
                    hand_value += 10
                
        return hand_value
            
    def draw(self, canvas, start_pos):
        for indx, card in enumerate(self.cards_in_hand):
            x_coord = start_pos[0] + (indx*CARD_SIZE[0])
            card.draw(canvas, [x_coord, start_pos[1]])
 
# define deck class 
class Deck:
    def __init__(self):
        self.cards_in_deck = []
        for suit in SUITS:
            for rank in RANKS:
                self.cards_in_deck.append(Card(suit,rank))

    def shuffle(self):
        # shuffle the deck 
        random.shuffle(self.cards_in_deck)

    def deal_card(self):
        return self.cards_in_deck.pop(0)
    
    def __str__(self):
        self.description = "Deck Contains"
        for card in self.cards_in_deck:
            self.description += " " + card.__str__()
        return self.description

#define event handlers for buttons
def deal():
    global outcome, in_play, game_deck, player_hand, dealer_hand, score

    # Set the outcome
    
    # If the player hits deal in the middle of a turn, give them a loss
    if in_play == True:
        outcome = "Hand forfeited. Beginning new hand. Hit or stand?"
        score -= 1
    else:
        outcome = "Hit or stand?"
        
    # Shuffle the Deck
    game_deck = Deck()
    game_deck.shuffle()
    
    # Create player hand
    player_hand = Hand()
    player_hand.add_card(game_deck.deal_card())
    player_hand.add_card(game_deck.deal_card())
    
    # Create dealer hand
    dealer_hand = Hand()
    dealer_hand.add_card(game_deck.deal_card())
    dealer_hand.add_card(game_deck.deal_card())
    
    in_play = True

def hit():
    global player_hand, in_play, score, outcome
    if in_play == False:
        outcome = "You have busted. Click Deal to begin a new game"
    else:
        player_hand.add_card(game_deck.deal_card())
        if player_hand.get_value() > 21:
            outcome = "You have busted. Click Deal to begin a new game"
            in_play = False
            score -= 1
       
def stand():
    global player_hand, in_play, score, outcome
    
    # Dealer now plays
    # Addition to logic from description:
    #	Stop the dealer from hitting if he/she is already beating the player
    if in_play == False:
        outcome = "Click Deal to begin a new game"
    else:
        while dealer_hand.get_value() < 17:
            dealer_hand.add_card(game_deck.deal_card())
            
        # Determine winner
        if dealer_hand.get_value() > 21:
            outcome = "Dealer busts. You win! Click Deal to begin a new game."
            in_play = False
            score += 1
        else:
            if player_hand.get_value() > dealer_hand.get_value():
                outcome = "You win! Click Deal to begin a new game."
                in_play = False
                score += 1
            else:
                outcome = "Dealer wins. Click Deal to begin a new game."
                in_play = False
                score -= 1        

# draw handler    
def draw(canvas):
 
    # Draw Dealer's hand    
    dealer_start = [50, 200]
    if in_play == True:
        first_card = [50,200]
        dealer_hand.draw(canvas, first_card)
        canvas.draw_image(card_back,CARD_BACK_CENTER, CARD_BACK_SIZE, [first_card[0] + CARD_BACK_CENTER[0], first_card[1] + CARD_BACK_CENTER[1]], CARD_BACK_SIZE)
    else:
        dealer_hand.draw(canvas, [50,200])
    canvas.draw_text("Dealer's hand", (50, 315),16,"white")
    
    # Draw Player's hand
    player_start = [50, 400]
    player_hand.draw(canvas, [50,400])
    canvas.draw_text("Your hand", (50, 515),16,"white")
    
    # Draw Status
    canvas.draw_text(outcome,(50, 375),24, "White")

    # Draw Score
    scoretext = "Score: " + str(score)
    canvas.draw_text(scoretext, (25,150), 28, "White")
    
    # Draw Title
    canvas.draw_text("Blackjack", (25, 50), 38, "White")
    canvas.draw_text("Implementation by jaylamb20@gmail.com", (25, 75), 20, "White")
    
# initialization frame
frame = simplegui.create_frame("Blackjack", 600, 600)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)

# get things rolling
deal()
frame.start()
