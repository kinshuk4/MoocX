## Mini-Proejct 1: Rock-paper-scissors-lizard-Spock
## Author: jaylamb20@gmail.com


# The key idea of this program is to equate the strings
# "rock", "paper", "scissors", "lizard", "Spock" to numbers
# as follows:
#
# 0 - rock
# 1 - Spock
# 2 - paper
# 3 - lizard
# 4 - scissors

## Import Modules
import random

## Store the names and numbers in dictionaries (seemed faster than else-ifs)
names = {'rock':0, 'Spock':1, 'paper':2, 'lizard':3, 'scissors':4}
numbers = {0:'rock', 1:'Spock', 2:'paper', 3:'lizard', 4:'scissors'}

def name_to_number(name):
    # Look up the number associated with a name
    return names[name]

def number_to_name(number):
    # Lookup up the name associated with a number
    return numbers[number]
    

def rpsls(player_choice): 
    
    # print a blank line to separate consecutive games
    print ""
    # print out the message for the player's choice
    print 'Player chooses ' + player_choice
    
    # convert the player's choice to player_number using the function name_to_number()
    player_number = name_to_number(player_choice)
    
    # compute random guess for comp_number using random.randrange()
    comp_number = random.randrange(0,5)
    
    # convert comp_number to comp_choice using the function number_to_name()
    comp_choice = number_to_name(comp_number)
    
    # print out the message for computer's choice
    print 'Computer chooses ' + comp_choice

    # compute difference of comp_number and player_number modulo five
    distance = (comp_number - player_number) % 5

    # use if/elif/else to determine winner, print winner message
    if comp_number == player_number:
        print "Player and computer tie!"
    elif distance <= 2:
        print "Computer wins!"
    else:
        print "Player wins!"
    
   
## Testing
rpsls("rock")
rpsls("Spock")
rpsls("paper")
rpsls("lizard")
rpsls("scissors")


