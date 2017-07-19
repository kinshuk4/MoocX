## CTRV Process Noise Vector

What we have introduced so far was the deterministic part of the process model.  But of course, we also need to consider the stochastic part of the process model  and that has something to do with the process noise ν<sub>k</sub>.  

Note the notations:

- ν<sub>k</sub> - Process noise
- ν<sub>a,k</sub> - Longitudinal acceleratation noise
- ν<sub>ψ_dot_dot,k</sub> - Yaw acceleratation noise

We will describe the uncertainty of the process model with a two-dimensional  noise vector ν<sub>k</sub>, consisting of two independent scalar noise processes.  

![](../images/7-8-1.png)

The first noise process is the longitudinal acceleration noise ν<sub>a,k</sub>.  ν<sub>a,k</sub> influences the longitudinal speed of the vehicle and  it randomly changes its value at every time step k.  The longitudinal acceleration is a normally distributed white  noise with zero mean and the variance sigma a squared.  

![](../images/7-8-2.png)

The other noise process is the yaw acceleration ν<sub>ψ_dot_dot,k</sub>.  It is also a normal distributed white noise with zero mean and  it has the variance sigma yaw dot dot squared.  

![](../images/7-8-3.png)



![img](../images/7-8.png)

And what I want to discuss with you next is how the noise vector ν<sub>k</sub> influences our process model. 

