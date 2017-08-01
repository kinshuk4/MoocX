import quadrotor.command as cmd
from math import sqrt

def plan_mission(mission):

    # this is an example illustrating the different motion commands,
    # replace them with your own commands and activate all beacons
    commands  = [
        # suppose that the beacons are placed on a set of grid points, 
        # labelled as follows:
        # (0,0), (0,1), (0,2)
        # (1,0), (1,1), (1,2)
        # (2,0), (2,1), (2,2)
        
        # increase altitude to meet beacons
        cmd.up(1.0),
        
        # align with first grid point at grid point (2,2)
        cmd.turn_right(90.0),
        cmd.forward(2.0),
        cmd.turn_left(90.0),
        
        # move through the beacon at (2,2) to the beacon at (0,2)
        cmd.forward(5.0),
        
        # align with beacon at grid point (0,1)
        cmd.turn_left(90.0),

        # move from beacon at (0,2) through the beacon at (0,1) to the beacon at (0,0)
        cmd.forward(4.0),

        # align with beacon at grid point (1,0)
        cmd.turn_left(90.0),

        # move from beacon at (0,0) through the beacon at (1,0) to the beacon at (2,0)
        cmd.forward(4.0),

        # align with beacon at grid point (2,1)
        cmd.turn_left(90.0),

        # move beacon at grid point (2,1)
        cmd.forward(2.0),

        # align with beacon at grid point (1,1)
        cmd.turn_left(90.0),

        # move beacon at grid point (2,1)
        cmd.forward(2.0),
    ]

    mission.add_commands(commands)
