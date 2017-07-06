STATINF_Project
===============

A repo to store my final project for the Statistical Inference course in the Johns Hopkins University Data Science specialization on Coursera.


**The Assignment**

*Part 1: Exponential Distribution*

The exponential distribution can be simulated in R with rexp(n, lambda) where lambda is the rate parameter. The mean of exponential distribution is 1/lambda and the standard deviation is also also 1/lambda. Set lambda = 0.2 for all of the simulations. In this simulation, you will investigate the distribution of averages of 40 exponential(0.2)s. Note that you will need to do a thousand or so simulated averages of 40 exponentials.

Illustrate via simulation and associated explanatory text the properties of the distribution of the mean of 40 exponential(0.2)s.  You should
1. Show where the distribution is centered at and compare it to the theoretical center of the distribution.
2. Show how variable it is and compare it to the theoretical variance of the distribution.
3. Show that the distribution is approximately normal.
4. Evaluate the coverage of the confidence interval for 1/lambda.

*Part 2: ToothGrowth*

Now in the second portion of the class, we're going to analyze the ToothGrowth data in the R datasets package. 
Load the ToothGrowth data and perform some basic exploratory data analyses 
Provide a basic summary of the data.
Use confidence intervals and hypothesis tests to compare tooth growth by supp and dose. (Use the techniques from class even if there's other approaches worth considering)
State your conclusions and the assumptions needed for your conclusions. 

**Files in this Repo**

I wrote the supporting code behind the pdf version of the report using the latest distribution of [RStudio](http://www.rstudio.com/). The files STATINF_Project.rmd and STATINF_Project_ToothGrowth.rmd in this repo contain the underlying code. All statistical analysis and plotting is accomplished via R, and report formatting uses a combination of LaTeX, HTML, and R markdown. The pdf was compiled using **knitr** in RStudio.

The files STATINF_Project.pdf and STATINF_Project_ToothGrowth.rmd contain the more presentable pdf output of the .rmd version. This pdf was submitted as the final deliverable for the class.

