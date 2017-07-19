## CTRV Process Noise Vector

What we have introduced so far was the deterministic part of the process model.  But of course, we also need to consider the stochastic part of the process model  and that has something to do with the process noise ν<sub>k</sub>.  

We will describe the uncertainty of the process model with a two-dimensional  noise vector ν k, consisting of two independent scalar noise processes.  The first noise process is the longitudinal acceleration noise ν<sub>a</sub>.  



Note the notations:

- ν<sub>k</sub> - Process noise
- ν<sub>a</sub> - Longitudinal acceleratation noise

It influences the longitudinal speed of the vehicle and  it randomly changes its value at every time step k.  The longitudinal acceleration is a normally distributed white  noise with zero mean and the variance sigma a squared.  The other noise process is the yaw acceleration ν site dot dot.  It is also a normal distributed white noise with zero mean and  it has the variance sigma yaw dot dot squared.  And what I want to discuss with you next is how the noise vector νk influences our process model. 