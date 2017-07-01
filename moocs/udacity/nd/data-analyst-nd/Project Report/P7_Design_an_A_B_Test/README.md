# P7: Design an A/B Test

## Experiment Design
### Metric Choice
>*List which metrics you will use as invariant metrics and evaluation metrics here.*

```
Invariant metrics: Number of cookies, Number of clicks
Evaluation metrics: Gross conversion, Net conversion
```

>*For each metric, explain both why you did or did not use it as an invariant metric and why you did or did not use it as an evaluation metric. Also, state what results you will look for in your evaluation metrics in order to launch the experiment.*

**Invariant metric:**
- **Number of cookies** - Number of unique users to visit the course overview page. This metric, cookies, is distributed in control and experiment groups evenly. Besides, before the visitors can see the website change, the cookies have generated, so it's a good invariant metric.
- &&Number of clicks** - Number of unique cookies to click the start free trial button. It's similar as number of cookies. Before the visitors click the button, they cannot see the experiment and the course overview pages are the same for two groups.

**Evaluation metric:**
- **Gross conversion** - Number of users who enrolled in the free trial/Number of users who clicked the Start Free Trial button. It is a good evaluation metrics. Users were asked how much time they had available to devote to the course and decide enroll or not. While, for the control group, nothing changed and users were not ask to consider the time. Because part of the experiment hypothesis is was that this might set clearer expectations for students upfront will reducing the number of frustrated students who left the free trial because they didn't have enough time. We assume that the gross conversion in the control group is higher than experiment group, because users in experiment group may consider their time schedule seriously and some of them may give up enrollment. So, it is a metric can be checked whether the experiment make sense.
- **Net conversion** - Number of user-ids remained enrolled for 14 days trial and at least make their first payment/Number of users clicked the Start Free Trial button. In my opinion, this metric is based on gross conversion and more close to our final results. In other words, it is a more precise metric to check our hypothesis. If the net conversion in experiment group is not too much lower than control group or even higher than control group, it means this experiment will not reduce the number of students to continue past the free trial and eventually complete the course. Udacity could improve the overall student experience and improve coaches' capacity to support students who are likely to complete the course.

**Others**
- **Number of User-ID** : Not an ideal metric for invariant metric. The user-ID is recorded after some users see the experiment. Thus the number of users enroll in the free trial depends on the experiment and cannot distributed evenly between two group. And it is usable as evaluation metric because it would record the the number of students to continue past the free trial, and track the  the first part of the hypothesis. It is not the best metric as it is not normalized. Net conversion is definitively better. Thus, I decide not to use it.
- **Click-through-probability** : It's not a good evaluation metric because this metric is not relevant with the aim of experiments. And it all happens before the experiments happen. It seems a good invariant metric but we have two more clear and direct metrics, cookies and clicks. So, there is no need to use third metric.
- **Retention** - In my opinion, there are two reasons why retention should not be seen as a evaluation metric. First, retention's unit of analysis (user-ids) differs from the unit of diversion (cookies), it's also going to have a pretty high variance compared to the other metrics. Second, a user-id could generate multiple cookies. So, it is possible that cookies from same user-id in different groups and mess the experiment.(Obviously, it will change in experiment group, so not good as an invariant metric). Actually, retention could be a evaluation metric, it just takes too long to compute(if we use retention, we need a large number of Pageviews) and gross and net conversion are specific and sufficient to inform about our hypothesis, therefore retention is not needed.

**Ideal results**
- **Gross conversion:** We want to see the Gross Conversion is a significantly decrease.
- **Net conversion:** We want to see the Net conversion does not decrease.

### Measuring Standard Deviation
List the standard deviation of each of your evaluation metrics.

```
Gross conversion: 0.0202
Net conversion: 0.0156
```

>*For each of your evaluation metrics, indicate whether you think the analytic estimate would be comparable to the the empirical variability, or whether you expect them to be different (in which case it might be worth doing an empirical estimate if there is time). Briefly give your reasoning in each case.*

Both Gross Conversion and Net Conversion using number of cookies, which means they are also unit of diversion. Thus, the unit of diversion is equal to unit of analysis, which indicate the analytical estimate would be comparable to the emperical variability.

### Sizing
#### Number of Samples vs. Power
>*Indicate whether you will use the Bonferroni correction during your analysis phase, and give the number of Pageviews you will need to power you experiment appropriately.*

The Bonferroni correction is not used in the analysis. Because the metrics in this test have high correlation and the Bonferroni correction may be too conservative and influence the results.

```
Number of Pageviews:685325
```

#### Duration vs. Exposure
>*Indicate what fraction of traffic you would divert to this experiment and, given this, how many days you would need to run the experiment.*

```
Number of Pageviews: 685325
Fraction of traffic: 1
Length of days: 18
```

> *Give your reasoning for the fraction you chose to divert. How risky do you think this experiment would be for Udacity?*

In this experiment, If student indicated fewer than 5 hours per week, a message would appear indicating that Udacity courses usually require a greater time commitment for successful completion, and suggesting that the student might like to access the course materials for free. They can enroll any time when they think they have enough time. So it doesn't harm students. Also we don’t ask personal information in this experiment, such as Political attitudes, personal disease history, sexual preferences. Therefore, the experiment has no privacy problems. Besides, this experiment does not significantly increase burden of Udacity website, does not change website functions too much, no too much extra data generate, so the website would not be harmed by this experiment. All in all, we could use 100% traffic, 18 days is long, if we decrease fraction of traffic, we would need more times to run this experiment, so I prefer divert 100% fraction of Udacity’s traffic to this experiment.


## Experiment Analysis
### Sanity Checks
>*For each of your invariant metrics, give the 95% confidence interval for the value you expect to observe, the actual observed value, and whether the metric passes your sanity check.*

Both metrics pass the sanity checks. The final results are:
```
Number of cookies
Confidence Interval: [0.4988,0.5012]
Observed: 0.5006

Number of clicks
Confidence Interval: [0.4959,0.5041]
Observed: 0.5005
```

## Result Analysis
### Effect Size Tests
>*For each of your evaluation metrics, give a 95% confidence interval around the difference between the experiment and control groups. Indicate whether each metric is statistically and practically significant.*

```
Gross conversion
Confidence Interval: [-0.0291,-0.0120]
#statistically significant (CI doesn't contain zero)
#practically significant (CI doesn't contain dmin value)

Net conversion
Confidence Interval: [-0.0116,0.0019]
#not statistically significant (CI contains zero)
#not practically significant (CI contains dmin value)
```

### Sign Tests
>*For each of your evaluation metrics, do a sign test using the day-by-day data, and report the p-value of the sign test and whether the result is statistically significant.*

```
Gross Conversion
Success: 4
Total: 23
Probability: 0.5
Two-tailed p-value : 0.0026
#statistically significant(<0.025)

Net conversion
Success: 10
Total: 23
Probability: 0.5
Two-tailed p-value : 0.6776
#not statistically significant(>0.025)
```

## Summary
> *State whether you used the Bonferroni correction, and explain why or why not. If there are any discrepancies between the effect size hypothesis tests and the sign tests, describe the discrepancy and why you think it arose.*

The Bonferroni correction is one of several methods used to counteract the problem of multiple comparisons. Whether to use the Bonferroni correction depends on what we are testing. If we test for ```OR``` on all metrics, the the Bonferroni correction can be applied. If we test for ```AND```,  there is no need to use the Bonferroni correction.

In other words, if we want to make a decision and want fewer metrics to be significant and the more independent these metrics are, the more need to use the Bonferroni correction. Because we want to make our significance level strictly with less error rate (false positive). While, if the metrics are highly related, and we want more metrics to be significant, we don't use correction. The metrics in this experiments have higly correction, therefore, the Bonferroni correction is not applied here.

## Recommendation
> Make a recommendation and briefly describe your reasoning.

We can see the result of Gross Conversion show negative and practically significant. It means many users are discouraged by the time requirement and give up to enroll. This is a good thing for Udacity team since  the new prompt deterred students from enrolling who weren't going to pay anyway. Udacity could improve the overall student experience and improve coaches' capacity to support students who are likely to complete the course. It matched our first part of hypothesis. While, Net conversion is statistically and practically insignificant and the confidence interval includes negative numbers. Therefore, there is a risk that the experiment may lead to a decrease in business. So the recommendation is NOT launching this Free Trial Screener immediately, we need to do other test of discuss more specifically.


## Follow-Up Experiment
> Give a high-level description of the follow up experiment you would run, what your hypothesis would be, what metrics you would want to measure, what your unit of diversion would be, and your reasoning for these choices.

The follow up experiment should focus on "Retention" I propose a kind of gamification approach -- ranking list. We will show a ranking list and the star of week(or month). The purpose of the "ranking list" would be to give students knew timely on how much time they have spent on the course, and motivate them to keep. The most motivations is that the user can earn higher status(Lv.1, Lv.2, lv.3... ) when they spend more time on learning, and also increase the comparisons between classmates.(even can provide some rewards to the star of month, if Udacity has enough budget). In an experiment, The experiment group would see the display of such a ranking list while the control group not.  

**hypothesis**: The display of a ranking list would significantly reduce the number of frustrated students who cancel early in the course.

**Unit of diversion** : User-IDs, after enrollment the unit of diversion can be a user_id which is much more stable than a cookie.

**Invariant metric:** : The number of user-IDs enrolling into the free trial. Because the mini-project happens after users enroll in a lesson. User only can know the mini-projects after enrollment. So The number of Enrollment can see as a invariant and users can be divided into two groups evenly.

**Evaluation metric:** : Retention. The aim of experiment is to ensure whether the experiment can reduce the number of frustrated students who cancel early in the course, so rentention rate is related with  probability of early cancel, it will be a good metric.




> Note: the full calculation process is on [google sheet](https://docs.google.com/spreadsheets/d/11wkEn-9oq1vSUfYT0xuhGXNVylNAHmD73gkgCNa81H0/edit?usp=sharing)
