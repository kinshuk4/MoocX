# The Relationship Between Pollution And Traffic Index Data Visualization

The final visualiztaion is [here](http://bl.ocks.org/clarkyu2016/raw/66dcdc69e44236127c7b5be26aecd7c9/).

## Summary
Nowadays, pollution and traffic jam are two major problems. I think these two things have some correlation. For example, if the commute time is longer, maybe more CO2 emissions will caused. So I collect the pollution index data and traffic index data from different countries and to show their relationship.

This is my **final summary** of the graph :
> There's a positive correlation between pollution index and traffic index. Generally, higher traffic will give rise to higher pollution. And the situation is much serious in Asia.

## Design
Initially, because of two variables, I choose the scatter chart as my chart type. And I applied different color data
by continents. The legends was on the left. Readers can click the labels to choose only single continent data.

**After receiving first feedback from Conan, I applied the following modifications:**
1. Use built-in legend from dimple.js.
2. Use the interactive legend to replace the initial legend.
3. add some labels to guide the reader that legend is interactive.
4. add two bar chart to show the reader Top10 countries with highest index.
5. add the description of two variables.

**After receiving two feedbacks from georgeliu1998, I applied the following modifications:**
1. adjust the opacity to avoid overlap.
2. changed the color coding, use the similar color as Olympic rings to represent different continents.
3. add the circle size.
4. delete the grid lines for the main scatter chart.

**After receiving third feedbacks from danielhavir, I applied the following modifications:**
1. add animation to two bar charts in the side bar.
2. fix the x and y axis.
3. adjust the position of legend.
4. explain two indices in more detail.

**After receiving forth feedbacks from code reviewer, I applied the following modifications:**
1. fix the x and y axis and reverse it
2. add source in the foot.
3. add explanatory description in the page, highlight the findings.
4. explain two indices in more detail.
5. add source in the foot.
6. explain two indices in more detail.

>The final html file is ```index.html```.

## Feedback
#### 1. From Conan (My friend who is studying CS):
I understand the story you want tell. But I am confused about two "index". What's the traffic index and pollution index meaning?
Besides, The chart looks a little ugly, especially the buttion in the side, and I don't know it can click.

#### 2. From georgeliu1998 (Udacity forum mentor):

**1st feedback:**
It clearly shows that there's a positive correlation between pollution level and commute time.
Also the ability to show certain continents using legend is great. It would be even better if
you can increase the size of the circles so that it's easier for the reader to explore the information. You may need to adjust the transparency when the circles overlap. Also, I am not very good at colors, so a different color scheme to differentiate continents may be helpful.
You can even consider using different shapes for the continents.

**2nd feedback:**
It'll be even better to remove the grid lines in the chart as they don't seem to add much value.

#### 3. From danielhavir (Udacity classmate)
I feel the legend is a little close to the scatter chart, may it will look better to put forward left. The circle at the top-right corner is too
high for me, maybe you could modify the ticks for y axis. To be honest, I can’t really think of anything to make it even better. Maybe explain the indices a little bit more, because it’s a bit unclear how to interpret them. As for the visualization itself, I like it just the way it is.

#### 4. From code reviewers
What I want from you in particular: 1)Explore the chart by yourself and find an interesting outcome.2)highlight this outcome and specify on the project page.3)change x and y axis so pollution will depend on traffic




## Resources
#### 1. Dataset
The original data is from [NUMBEO](https://www.numbeo.com).

1. [Traffic Index](https://www.numbeo.com/traffic/rankings_by_country.jsp)
2. [Pollution Index](https://www.numbeo.com/pollution/rankings_by_country.jsp)

#### 2. Visualisation
In this visualisation, [one advanced example](http://dimplejs.org/advanced_examples_viewer.html?id=advanced_interactive_legends) from the dimples.js are referred and also got many inspiration
from the [Full API Documentation](https://github.com/PMSI-AlignAlytics/dimple/wiki) of dimple.js
