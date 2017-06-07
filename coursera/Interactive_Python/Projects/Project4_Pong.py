# Implementation of classic arcade game Pong
# Author: jay_lamb20@gmail.com

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600
HEIGHT = 400       
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2
LEFT = False
RIGHT = True
VEL_INCREASE = 0.1

# initialize ball_pos and ball_vel for new bal in middle of table
# if direction is RIGHT, the ball's velocity is upper right, else upper left
def spawn_ball(direction):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos = [WIDTH/2, HEIGHT/2]
    if direction == "RIGHT":
        ball_vel = [random.randrange(2,4),-random.randrange(1,3)]
    elif direction == "LEFT":
        ball_vel = [-random.randrange(2,4),-random.randrange(1,3)]

# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel # these are numbers
    global score1, score2  # these are ints
    spawn_ball("RIGHT")
    paddle1_pos = [HEIGHT/2 - HALF_PAD_HEIGHT, HEIGHT/2 + HALF_PAD_HEIGHT]
    paddle2_pos = [HEIGHT/2 - HALF_PAD_HEIGHT, HEIGHT/2 + HALF_PAD_HEIGHT]
    paddle1_vel = 0
    paddle2_vel = 0
    score1 = 0
    score2 = 0

def draw(canvas):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel
    ball_pos[0] += ball_vel[0]
    ball_pos[1] += ball_vel[1]
 
        
    # draw mid line and gutters
    canvas.draw_line([WIDTH / 2, 0],[WIDTH / 2, HEIGHT], 1, "White")
    canvas.draw_line([PAD_WIDTH, 0],[PAD_WIDTH, HEIGHT], 1, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH, 0],[WIDTH - PAD_WIDTH, HEIGHT], 1, "White")
        
    # update ball
            
    # draw ball
    canvas.draw_circle((ball_pos[0],ball_pos[1]), BALL_RADIUS, 2, 'Blue', 'White')
    
    # make the ball bounce off the top and bottom walls
    if ball_pos[1] < BALL_RADIUS:
        ball_vel[1] = -ball_vel[1]
    elif ball_pos[1] > (HEIGHT - BALL_RADIUS - 1):
        ball_vel [1] = -ball_vel[1]
        
    # Test if the ball touches the gutters or paddles
    if ball_pos[0] <= PAD_WIDTH + BALL_RADIUS:
        
        # striking the left gutter (send it back!)
        if (ball_pos[1] + BALL_RADIUS) >= paddle1_pos[0] and (ball_pos[1]-BALL_RADIUS) <= paddle1_pos[1]:
            ball_vel[0] = -(ball_vel[0]*(1+VEL_INCREASE))
        else:  
            score2 += 1
            spawn_ball("RIGHT")
            
    elif ball_pos[0] > (WIDTH-BALL_RADIUS-PAD_WIDTH-1):
        
        # striking the right gutter
        if (ball_pos[1] + BALL_RADIUS) >= paddle2_pos[0] and (ball_pos[1]-BALL_RADIUS) <= paddle2_pos[1]:
            ball_vel[0] = -(ball_vel[0]*(1+VEL_INCREASE))
        else:  
            score1 += 1
            spawn_ball("LEFT")
        
    # update paddle's vertical position, keep paddle on the screen
    if (paddle1_pos[1] + paddle1_vel) <= HEIGHT and (paddle1_pos[0] + paddle1_vel) >= 0: 
        paddle1_pos[0] += paddle1_vel
        paddle1_pos[1] += paddle1_vel
        
    if (paddle2_pos[1] + paddle2_vel) <= HEIGHT and (paddle2_pos[0] + paddle2_vel) >= 0: 
        paddle2_pos[0] += paddle2_vel
        paddle2_pos[1] += paddle2_vel 
    
    # Draw paddles
    canvas.draw_line((0,paddle1_pos[0]),(0, paddle1_pos[1]),PAD_WIDTH,"White")
    canvas.draw_line((WIDTH-PAD_WIDTH+3,paddle2_pos[0]),(WIDTH-PAD_WIDTH+3, paddle2_pos[1]),PAD_WIDTH,"White")
    
    # draw scores
    canvas.draw_text(str(int(score1)),(WIDTH/4.0, 50),40, "Orange")
    canvas.draw_text(str(int(score2)),(3.0*WIDTH/4.0, 50), 40, "Orange")
    
def keydown(key):
    global paddle1_vel, paddle2_vel
    if key == simplegui.KEY_MAP['w']:
        paddle1_vel = -2
    elif key == simplegui.KEY_MAP['s']:
        paddle1_vel = 2
    if key == simplegui.KEY_MAP['up']:
        paddle2_vel = -2
    elif key == simplegui.KEY_MAP['down']: 
        paddle2_vel = 2
   
def keyup(key):
    global paddle1_vel, paddle2_vel
    if key == simplegui.KEY_MAP['w'] or key == simplegui.KEY_MAP['s']:
        paddle1_vel = 0
    elif key == simplegui.KEY_MAP['up'] or key == simplegui.KEY_MAP['down']:
        paddle2_vel = 0

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)

# Add a Restart button
restart_button = frame.add_button("Restart", new_game)

# Set event handlers
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)



# start frame
new_game()
frame.start()
