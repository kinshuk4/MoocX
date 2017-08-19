# Additional Cost Considerations

The cost function is not limited to the state. We can also include the control input. The reason we would do this is to allow us to penalize the magnitude of the input as well as the change rate. If we want to change lanes, for example, We would have a large cross track error that would want to penalize turning the wheel really sharply. The would yield a smoother lane change. We cold add the control input magnitude like this:

```
cost += pow(delta[t], 2)
```

We still need to capture the change rate of the control input to add some temporal smoothness. This additional term in the cost function captures the difference between the next control input state and the current one so we have further control over the inputs. 

```
for (t=0; t < N-1; t++)
{
	cost += pow(delta[t+1] - delta[t], 2);
}
```
