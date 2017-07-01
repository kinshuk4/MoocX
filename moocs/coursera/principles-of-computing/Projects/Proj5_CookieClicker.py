"""
Cookie Clicker Simulator
"""

import simpleplot
import math

# Used to increase the timeout, if necessary
import codeskulptor
codeskulptor.set_timeout(20)

import poc_clicker_provided as provided

# Constants
SIM_TIME = 10000000000.0

class ClickerState:
    """
    Simple class to keep track of the game state.
    """
    
    def __init__(self):
        self.__total_cookies__ = 0.0
        self.__current_cookies__ = 0.0
        self.__current_time__ = 0.0
        self.__current_cps__ = 1.0
        self.__history__ = [(0.0, None, 0.0, 0.0)]
        
    def __str__(self):
        """
        Return human readable state
        """
        out_string = ("Total Cookies: " + str(self.__total_cookies__) + "\n" +
                      "History: " + str(len(self.__history__)) + str(self.__history__)
                      )
                      
        return out_string
        
    def get_cookies(self):
        """
        Return current number of cookies 
        (not total number of cookies)
        
        Should return a float
        """
        return float(self.__current_cookies__)
    
    def get_cps(self):
        """
        Get current CPS

        Should return a float
        """
        return float(self.__current_cps__)
    
    def get_time(self):
        """
        Get current time

        Should return a float
        """
        return float(self.__current_time__)
    
    def get_history(self):
        """
        Return history list

        History list should be a list of tuples of the form:
        (time, item, cost of item, total cookies)

        For example: [(0.0, None, 0.0, 0.0)]

        Should return a copy of any internal data structures,
        so that they will not be modified outside of the class.
        """
        temp = self.__history__[:]
        return temp

    def time_until(self, cookies):
        """
        Return time until you have the given number of cookies
        (could be 0.0 if you already have enough cookies)

        Should return a float with no fractional part
        """
        if type(cookies) == float or type(cookies) == int:
            if self.__current_cookies__ >= cookies:
                return 0.0
            else:   
                return float(int(math.ceil((cookies - self.__current_cookies__)/self.__current_cps__)))
    
    def wait(self, time):
        """
        Wait for given amount of time and update state

        Should do nothing if time <= 0.0
        """
        if time > 0.0:
            self.__current_time__ += float(time)
            self.__current_cookies__ += float((time * self.__current_cps__))
            self.__total_cookies__ += float((time * self.__current_cps__))
    
    def buy_item(self, item_name, cost, additional_cps):
        """
        Buy an item and update state

        Should do nothing if you cannot afford the item
        """
        if self.__current_cookies__ >= cost:
            self.__current_cookies__ -= cost
            self.__current_cps__ += additional_cps
            self.__history__.append((self.__current_time__,item_name,cost,self.__total_cookies__))
     
def simulate_clicker(build_info, duration, strategy):
    """
    Function to run a Cookie Clicker game for the given
    duration with the given strategy.  Returns a ClickerState
    object corresponding to the final state of the game.
    """
    #clone the build_info object
    this_build = build_info.clone()
    game = ClickerState()
    
    while game.get_time() <= duration:
        #get a recommendation from the strategy:
        next_item = strategy(game.get_cookies(), game.get_cps(), game.get_history(), 
                 math.ceil(int(duration-game.get_time())), this_build)
        if next_item == None:
            break
        else:
            item_cost = this_build.get_cost(next_item)
            add_cps = this_build.get_cps(next_item)
            if (game.get_time() + game.time_until(item_cost)) <= duration:
                game.wait(game.time_until(item_cost))
                game.buy_item(next_item, item_cost, add_cps)
                this_build.update_item(next_item)
            else:
                break
                
    #If time remains, buy cookies until the end
    if game.get_time() < duration:
        game.wait((duration - game.get_time()))
                
    return game

def strategy_cursor_broken(cookies, cps, history, time_left, build_info):
    """
    Always pick Cursor!

    Note that this simplistic (and broken) strategy does not properly
    check whether it can actually buy a Cursor in the time left.  Your
    simulate_clicker function must be able to deal with such broken
    strategies.  Further, your strategy functions must correctly check
    if you can buy the item in the time left and return None if you
    can't.
    """
    return "Cursor"

def strategy_none(cookies, cps, history, time_left, build_info):
    """
    Always return None

    This is a pointless strategy that will never buy anything, but
    that you can use to help debug your simulate_clicker function.
    """
    return None

def strategy_cheap(cookies, cps, history, time_left, build_info):
    """
    Always buy the cheapest item you can afford in the time left.
    """
    # determine cheapest affordable
    lowest_cost = float('Inf')
    found_one = False
    for possibility in build_info.build_items():
        cost = build_info.get_cost(possibility)
        if cost <= lowest_cost and cost <= float(cookies + cps*time_left):
            best_item = possibility
            lowest_cost = cost
            found_one = True
            
    #Return the best candidate
    if found_one:
        return best_item
    else:
        return None
    
def strategy_expensive(cookies, cps, history, time_left, build_info):
    """
    Always buy the most expensive item you can afford in the time left.
    """
    highest_cost = float('-Inf')
    found_one = False
    for possibility in build_info.build_items():
        cost = build_info.get_cost(possibility)
        if cost >= highest_cost and cost <= float(cookies + cps*time_left):
            best_item = possibility
            highest_cost = cost
            found_one = True
            
    #Return the best candidate
    if found_one:
        return best_item
    else:
        return None
    
def strategy_best_cps_return(cookies, cps, history, time_left, build_info):
    """
    Picks next move based on CPS boost per cookie spent
    """
    #	1. Choose item with the highest one-step-ahead CPS boost
    best_cps_return = float('-Inf')
    found_one = False
    for possibility in build_info.build_items():
        
        #item characteristics
        item_cost = build_info.get_cost(possibility)
        item_cps = build_info.get_cps(possibility)
        cps_per_cost = float(item_cps)/float(item_cost)
            
        if item_cost <= float(cookies + cps*time_left) and cps_per_cost > best_cps_return:
            best_item = possibility
            best_cps_return = cps_per_cost
            found_one = True
            
    #Return the best candidate
    if found_one:
        return best_item
    else:
        return None
    
def strategy_reasonable_return(cookies, cps, history, time_left, build_info):
    """
    Chooses the item with the best CPS/cost ratio, that can be bought in a 
    "reasonable" amount of time.
    """
    best_cps_return = float('-Inf')
    found_one = False
    wait_thresh = 0.7 * time_left
    for possibility in build_info.build_items():
        
        #item characteristics
        item_cost = build_info.get_cost(possibility)
        item_cps = build_info.get_cps(possibility)
        cps_per_cost = float(item_cps)/float(item_cost)
        
        if float(item_cost) <= cookies:
            time_to_buy = 0
        else:
            time_to_buy = math.ceil(int((item_cost-cookies)/float(cps)))
            
        if (item_cost <= float(cookies + cps*time_left) and cps_per_cost > best_cps_return and
            time_to_buy <= wait_thresh):
            best_item = possibility
            best_cps_return = cps_per_cost
            found_one = True
            
    #Return the best candidate
    if found_one:
        return best_item
    else:
        return None    
           
def strategy_best(cookies, cps, history, time_left, build_info):
    """
    This strategy is an ensemble of other strategies:
    Starts with a few periods of buying based on return, excluding extreme values.
    Buys strictly cheap stuff for the next few turns (fueled by the big purchases from before.)
    Goes back to playing reasonable return for the rest of the game.
    If the "reasonable return" strategy returns None, this plays cheap (end-of-game strategy).
    
    Parameters tuned by inspection (no fancy grid search).
    I found that cutoff1 of 12 and cutoff2 of 147 work for SIM_TIME of 10e10,
    and coded them as references to SIM_TIME...but that of course does not mean that
    this will play an optimal (or even good) game for other SIM_TIMEs.
    """
    cutoff1 = int(math.ceil(0.0000000012*SIM_TIME))
    cutoff2 = int(math.ceil(0.0000000147*SIM_TIME))
    next_item = None
    if len(history) <= cutoff1:
        next_item = strategy_reasonable_return(cookies,cps,history,time_left,build_info)
    elif next_item == None and len(history) > cutoff1 and len(history) <= cutoff2:
        next_item = strategy_cheap(cookies,cps,history,time_left,build_info)
    elif next_item == None:
        next_item = strategy_reasonable_return(cookies,cps,history,time_left,build_info)
    
    if next_item == None:
        next_item = strategy_cheap(cookies,cps,history,time_left,build_info)

        
    return next_item

def run_strategy(strategy_name, time, strategy):
    """
    Run a simulation for the given time with one strategy.
    """
    state = simulate_clicker(provided.BuildInfo(), time, strategy)
    print strategy_name, ":", state

    # Plot total cookies over time

    # Uncomment out the lines below to see a plot of total cookies vs. time
    # Be sure to allow popups, if you do want to see it

    #history = state.get_history()
    #history = [(item[0], item[3]) for item in history]
    #simpleplot.plot_lines(strategy_name, 1000, 400, 'Time', 'Total Cookies', [history], True)

def run():
    """
    Run the simulator.
    """    
    
    # Add calls to run_strategy to run additional strategies
    run_strategy("Cheap", SIM_TIME, strategy_cheap)
    run_strategy("Expensive", SIM_TIME, strategy_expensive)
    run_strategy("Best", SIM_TIME, strategy_best)
    
    # run_strategy("Best", SIM_TIME, strategy_best)
    
#run()
    

