"""
Planner for Yahtzee
Simplifications:  only allow discard and roll, only score against upper level
"""

# Used to increase the timeout, if necessary
import codeskulptor
codeskulptor.set_timeout(20)

def gen_all_sequences(outcomes, length):
    """
    Iterative function that enumerates the set of all sequences of
    outcomes of given length.
    """
    
    answer_set = set([()])
    for dummy_idx in range(length):
        temp_set = set()
        for partial_sequence in answer_set:
            for item in outcomes:
                new_sequence = list(partial_sequence)
                new_sequence.append(item)
                temp_set.add(tuple(new_sequence))
        answer_set = temp_set
    return answer_set


def score(hand):
    """
    Compute the maximal score for a Yahtzee hand according to the
    upper section of the Yahtzee score card.

    hand: full yahtzee hand

    Returns an integer score 
    """
    scr = 0
    for dummy_num in range(1,max(hand)+1):
        if dummy_num in hand:
            tmp = 0
            for dummy_i in range(len(hand)):
                if hand[dummy_i] == dummy_num:
                    tmp += dummy_num
            if tmp > scr:
                scr = tmp
    return scr


def expected_value(held_dice, num_die_sides, num_free_dice):
    """
    Compute the expected value based on held_dice given that there
    are num_free_dice to be rolled, each with num_die_sides.

    held_dice: dice that you will hold
    num_die_sides: number of sides on each die
    num_free_dice: number of dice to be rolled

    Returns a floating point expected value
    """
    possible_other = gen_all_sequences(range(1,num_die_sides+1), num_free_dice)
    total_seqs = len(possible_other)
    exp_val = 0
    for seq in possible_other:
        tmp_held = list(held_dice)
        tmp_hand = tmp_held + list(seq)
        tmp_hand.sort()
        tmp_hand = tuple(tmp_hand)
        hand_score = score(tmp_hand)
        exp_val += hand_score*(1.0/total_seqs)
    return exp_val


def gen_all_holds(hand):
    """
    Generate all possible choices of dice from hand to hold.

    hand: full yahtzee hand

    Returns a set of tuples, where each tuple is dice to hold
    """
    answer_set = set([()])
    
    #for dummy_idx in range(len(hand)):
    temp_set = set([()])
    for val in list(hand):
        for seq in answer_set:
            new_sequence = list(seq)
            new_sequence.append(val)
            new_sequence.sort()
            temp_set.add(tuple(new_sequence))
        answer_set = set(temp_set)
    return answer_set

def strategy(hand, num_die_sides):
    """
    Compute the hold that maximizes the expected value when the
    discarded dice are rolled.

    hand: full yahtzee hand
    num_die_sides: number of sides on each die

    Returns a tuple where the first element is the expected score and
    the second element is a tuple of the dice to hold
    """
    possible_holds = gen_all_holds(hand)
    best_score = 0
    for hold in possible_holds:
        exp_val = expected_value(hold, num_die_sides, len(hand)-len(hold))
        if exp_val > best_score:
            best_hold = tuple(hold)
            best_score = float(exp_val)
    return (best_score, best_hold)

def run_example():
    """
    Compute the dice to hold and expected score for an example hand
    """
    num_die_sides = 6
    hand = (1, 1, 1, 5, 6)
    hand_score, hold = strategy(hand, num_die_sides)
    print "Best strategy for hand", hand, "is to hold", hold, "with expected score", hand_score

#run_example()
                                     