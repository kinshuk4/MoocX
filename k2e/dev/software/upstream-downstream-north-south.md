### Upstream/Downstream

**Question**

The question is whether "up" and "down" refer to 

a) the communication direction in the system architecture?

b) the direction of the event flow?

**Answer**

I strongly think that b) is the right answer. 

why? think of the event stream as as river. if you throw an apple in the river (= publish an event), it travels down the river until it is picked up (= consumed) by somebody downstream. 

Upstream components are other parts of the system that your component depends on to do its job. If the design of an upstream component changes, the ability of your component to function may be affected. If an upstream component has a bug, this bug may be manifested in your component.

Downstream components are parts of the system that your component can affect. Changes in your component can ripple to components that are downstream from your component.

Consider an application that consists of a database tier and an application tier. The database tier would be considereded to be upstream of the application tier.

### Northbound / Southbound

This refers to the architecture diagram in the presentation.

If the box is in the top of the page, that is north so, northbound. Likewise for south, west and east.

https://softwareengineering.stackexchange.com/questions/71080/what-does-downstream-upstream-design-mean

