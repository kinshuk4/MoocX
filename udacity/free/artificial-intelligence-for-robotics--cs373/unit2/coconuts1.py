# 5 guys, one monkey, pile of coconuts
# one by one a guy takes 1/5 of the (remaining) pile, but since the pile
# is not divisible by 5 gives one coconut to monkey.
# After this the remaining pile is divided to five parts (giving one coconut to monkey)
# monkey has now 6 coconuts, how many there were originally ?


pile = 22 # not real estimate on value just to help me model this
coconuts = [0.0] * 6 # 0 is monkey, other keys are the guys

def take_coconuts(guy):
    global pile
    global coconuts
    if ((float(pile)/5) == (int(pile)/5)):
        print "   Pile of %d is divisible by 5, we did something wrong" % pile
        return False
    coconuts[0] = coconuts[0]+1
    pile = pile-1
    if ((float(pile)/5) != (int(pile)/5)):
        print "   Pile of %d is NOT divisible by 5, we did something wrong" % pile
        return False
    fifth = float(pile)/5
    coconuts[guy] = coconuts[guy]+fifth
    pile = pile - fifth
    if (pile < 5):
        print "   Ran out of coconuts!"
        return False
    return True

def test_pile():
    global pile
    global coconuts
    for i in range(1,6):
        if not take_coconuts(i):
            return False
        print "   After guy %d pile is %d coconuts" % (i, pile)
    
    print "   before final division pile is %d coconuts and each has %s" % (pile, repr(coconuts))
    # Final division
    if ((float(pile)/5) == (int(pile)/5)):
        print "   Pile of %d is divisible by 5, we did something wrong" % pile
        return False
    coconuts[0] = coconuts[0]+1
    pile = pile-1
    if ((float(pile)/5) != (int(pile)/5)):
        print "   Pile of %d is NOT divisible by 5, we did something wrong" % pile
        return False
    for i in range(1,6):
        coconuts[i] = coconuts[i]+pile/5
    
    print "   after final division each has %s" % repr(coconuts)
    return True

for pile_size in range(10000,1000000):
    pile = pile_size
    print "Brute forcing pile size %d" % pile_size
    coconuts = [0] * 6
    if test_pile():
        print "Solution is %d!" % pile_size
        break
        
    
