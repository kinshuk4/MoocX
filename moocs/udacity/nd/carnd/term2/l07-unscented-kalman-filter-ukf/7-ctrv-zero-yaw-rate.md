## Yaw rate of 0

Now we have the solution of the integral.  It is a function that brings the state from time step k,  to time step k + 1, awesome.  There's only one problem with our solution and  that is the division by 0 when the yaw rate is in.  And this is an important case because it means  the weakness driving on a straight road, which happens quite often.  

There are two ways to derive the solution for this special case. 

- You can put the yaw rate to 0 right from the beginning and  solve the integral again, for this special case.  
- But remember, when the yaw rate is 0,  it means the vehicle drives in a straight line.  In this case,  you can also directly see the solution by looking at the triangle again. 

Change in the x-position over time when ψ<sub>k</sub>_dot=0 : v<sub>k</sub>cos(ψ<sub>k</sub>)Δt

Change in the y-position over time when ψ<sub>k</sub>_dot=0 : v<sub>k</sub>sin(ψ<sub>k</sub>)Δt