# template for Mini-Project 3 - "Stopwatch: The Game"
# Author: jaylamb20@gmail.com

# PLEASE NOTE: I moved the "full second scoring" functionality to keydown handlers
# I understand that the syllabus says to use only 1 score and tie it to the stop button,
# but I feel like this way is more fun and still satisfies the spirit of the assignment.
# Please grade accordingly.

# import modules
import simplegui

# define global variables
running_time = 0
interval = 100
score1 = 0
try1 = 0
score2 = 0
try2 = 0

# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    minutes = str(int(t)/600)
    remaining = str(t % 600)
    if int(remaining) < 10:
        seconds = "00"
        tenths = remaining
    elif int(remaining) < 100:
        seconds = "0" + remaining[0]
        tenths = remaining[1]
    else:
        seconds = remaining[:2]
        tenths = remaining[2]
    return minutes + ":" + seconds + "." + tenths
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start_handler():
    timer.start()
    
# I only have this stop the timer
def stop_handler():
    timer.stop()
    
# Reset the timer and all global variables (including the scores)
def reset_handler():
    timer.stop()
    global running_time, score1, try1, score2, try2
    running_time = 0
    score1 = 0
    try1 = 0
    score2 = 0
    try2 = 0

# define event handler for timer with 0.1 sec interval
def tick():
    global running_time
    running_time += 1
    print running_time

# define draw handler
def drawing(canvas):
    canvas.draw_text(format(running_time), (149,199),50, "#0097D1")
    p1score = str(score1) + "/" + str(try1)
    canvas.draw_text(p1score, (50,50), 34, "White")
    p2score = str(score2) + "/" + str(try2)
    canvas.draw_text(p2score, (300,50), 34, "White")
    
# define key1 and key2 handlers for players
# (this is way more fun than just 1 player)
# I tried to mirror prof. Warren's example
def key_handler(key):
    if key == simplegui.KEY_MAP['a']:
        global score1, try1
        try1 += 1
        if running_time > 10 and (running_time % 10) == 0 and timer.is_running():
            score1 += 3
        else:
            score1 -= 1
    if key == simplegui.KEY_MAP['l']:
        global score2, try2
        try2 += 1
        if running_time > 10 and (running_time % 10) == 0 and timer.is_running():
            score2 += 1
        else:
            score2 -= 1
        
# create frame, timer, buttons, 
frame = simplegui.create_frame("Stopwatch: The Game", 400,400)
timer = simplegui.create_timer(interval, tick)
start_button = frame.add_button("Start", start_handler,100)
stop_button = frame.add_button("Stop", stop_handler,100)
reset_button = frame.add_button("Reset", reset_handler, 100)

# register event handlers
frame.set_draw_handler(drawing)
frame.set_keydown_handler(key_handler)

# start frame
frame.start()
