# State

If the vehicle is located at a position which we track with **x** and **y**. A car will also have an orientation which we will denote **cy** or **ψ**. This is a good model for a stationary vehicle, but what if it is in motion? In this case it will also have a velocity which we'll call **v**. Now we have the vehicle's state, the next task is to model how that state evolves over time.

#### State = [x, y, ψ, v]

i.e.

- X, Y coordinates
- Orientation
- Velocity