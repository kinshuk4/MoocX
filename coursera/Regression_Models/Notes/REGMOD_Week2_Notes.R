Video 1.5a - Statistical Linear Regression Models

  A. Basic model w/ additive Gaussian errors
    || errors are iid, normal, with mean=0, variance = sigma^2
    || E[Yi] = B0 + B1x
    || normal errors implies normal Ys

  B. Likelihood
    || Maximizing the likelihood is the same as minimizing -2log likelihood
    || The least squares estimate for mu is exactly the maximum likelihood estimate (regardless of variation)
    || under Gaussian errors, ML estimates and LS estimates are identical

Video 1.5b - Interpreting Regression Coefficients

  A. Recap
    || Beta_1 = cor(Y,X)*(sd(Y)/sd(x))
    
  B. Interpretting Regression Coefficients, the intercept
    || Interpreting the constant is questionable if the Xs can never be 0 or are extremely unlikely to be 0
    || Shifting X values by some constant will change the intercept but leave the slope unchanged
    || The intercept is interpreted as the expected response at the average X value

  C. Interpretting coefficients, the slope
    || beta_1 is the expected change in response for a 1 unit change in the predictor
    || multiplication of X by a factor alpha results in dividing the coefficient by a alpha
    || beta_1 is in "units of Y per units of X"

  D. Using Regression Coefficients for Predictions
    || Just plug in the value of X to get y_hat
    
Video 1.5c - Statistical Regression Models Examples

    A. Example
    
        library(UsingR); data(diamond)
        
    || use abline() to add regression line to a plot
    
        fit <- lm(price ~ carat, data=diamond)
        coef(fit)

    || try centering the data to give the intercept meaning

        fit2 <- lm(price ~ I(carat-mean(carat)), data=diamond)
        coef(fit2)


    B. Changing Scale
        || maybe change the units to 1/10th of a carat?
        || using I() is just a transformation of the varable

    C. Predicting the price of a diamond

            newx <- c(0.16,0.27,0.34)
            coef(fit)[1]+coef(fit)[2]*newx

            predict(fit,newdata=data.frame(carat=newx)) #a more general "forecast" function in R

Video 1.6a - Residuals and residual variation

    A. Residuals
        || resid is the difference between the predicted and actual
        || e = Y_i - Y_i_hat
        || Negative residual = over-prediction ; Positive residual = under-prediction

   
Video 1.6b - Properties of Residuals

    A. Properties of the resids
        || if an intercept is included, their sum is 0
        || The expected value of e is 0
        || Resids are useful for investigating poor model fit
        || Residuals can be thought of as the outcome (Y) with the linear association of the predictor (X) removed
        || COnsider residual variation (variation after removing predictor) from systematic variation (variation explained by the regression model)
        
    B. Some Code

            data(diamond)
            y <- diamond$price; x <- diamond$carat; n <- length(y)
            fit <- lm(y ~ x)
            e <- resid(fit) #grab the residuals
            yhat <- predict(fit) #grab the predicted values
            max(abs(e-(y-yhat)))

    C. Residuals versus X
        || Can be useful to plot the residuals against the independent variable
        || any evidence of a pattern is evidence of systematic omission
        || simulate some data and show a resid plot

            x <- runif(100,-3,3); y <- x + sin(x) + rnorm(100, sd=.2)
            plot(x,y); abline(lm(y~x))
    
            plot(x, resid(lm(y~x)))
            abline(h=0)

    D. Heteroskedasticity
        || non-constant variance
        || standard deviation relies on some "factor of proportionality"

            x <- runif(100,0,6); y <- x + rnorm(100, mnea=0, sd=.001*x);
            plot(x,y); abline(lm(y~x))

Video 1.6c - Residual Variation

    A. Estimation Residual Variation
        || ML estimate of sigma^2 is biased, i.e. E[sigma_hat^2] <> sigma^2 for maximum likelihood

    B. Diamond Example
        y <- diamond$price; x <- diamond$carat; n <- length(y)
        fit <- lm(y~x)
        summary(fit)$sigma #see the residual variation

    C. Summarizing Variation
        || total variation (TSS) = Residual variation + model variation
        || R^2 = percent of total variation described by model = model variation / total variation

    D. Relationship between R^2 and r (the correlation)
        || For a univariate model, R^2 is literally r^2 (the simple linear correlation squared)
        
    E. Some facts about R^2
        || % of variation explained by the regression model
        || R^2 is the sample correlation squared
        || R^2 can be a misleading summary of model fit
        || adding random shit automatically increases R^2
        || Do example(anscombe) to see the their data

Video 1.7a - Inference in Regression

    A. Recall our model and fitted values
        || this lecture assumes that youve seen confidence intervals and hypothesis tests before
        || beta_0 = Y_hat - beta_1_hat*X_bar
        || beta_1_hat = cor(Y,X)*(sd(y)/sd(x))

Video 1.7b - Tests for Regression Coefficients

    A. Review
        || statistics like estimator-estimate/s.e have the following properties
            \\ Is normally distirbuted and has a finite sample students T distribution (under normality assumptions)
            \\ can use this to test hypotheses
            \\ Can be used to create confidence intervals of theta_hat +- Quantile*sd(theta)
        || In the case of regression with iid sampling assumption and normal errors, our inferences will follow very similarly to what we saw in inference class
        || if you assume the X values are iid and normal, thats all you need to do confidence intervals

    B. standard errors (conditioned on X)
        || var(beta_1_hat) = var(Y)/var(X)
        || under iid Gaussian errors, beta_hate follows a t distribution with n-2 degrees of freedom
        || distribution approaches normality as sample size increases
        || use the pt() function to get p-values

    C. Getting a confidence interval
        
            sumCoef <- summary(fit)$coefficients
            sumCoef[1,1] + c(-1,1)*qt(.975, df=fit$df)*sumCOef[1,2]) #make your confidence interval


Video 1.7c - Prediction Intervals

    A. Prediction of Outcomes
        || You need a standard error to create a prediction interval
        || There is a slight different between the line and the prediction interval

    B. Plotting the Prediction Intervals
        || The further you are from the average of the Xs, the more error youre likely to experience
        || The standard error of the line converges to 0, standard error of estimating Y does not
        || There exists a subtle difference between error in predicting "the point on the line" and "the value of y"
        || Prediction interval incorporates uncertainty around the line

    C. Prediction of outcomes
        || Your prediction is best near the middle of the data
        || S.E. punishes you when you try to make predictions on values further from the observed history
        || the key point --> difference between predicting the variable, predicting the line

    D. Discussion
        || both intervals have varying widths
        || We are quite confident in the regression line, so the interval is very narrow
        || The prediction interval must incorporate the variability in the data around the line
        || take a look at the predict() function

    E. A final point: THe slope standard error
        || You want variability in your Xs because it decreases variability in your slope
        || More variability = better predictions
        || You want small resid variation, high X variation

Video 2.1a - Multivariate Regression

    A. Why do we care about this?
        || You need to generalize to incorporate more regressors
        || Holding things constant builds a more believable narrative
        
    B. The Linear Model
        || The general linear model extends simple linear reggressions by adding terms (GLM)
        || An intercept is included, for sure.
        || You have to have linear beta estimates, but you can do anything to the Xs

    C. How to get Estimates
        || The real way to do this requires linear algebra
        
Video 2.1c/d - More Multivariate Least Squares

    A. Summing up fitting with two regressors
        || Beta one is like removing X2 from everything
        || The estimates are partial derivatives
        || Simple linear regression is actually a two-variable regression

    B. The General Case
        || The LS estimates have those equations from slide 6
        || You need to satisfy that root equation for every regressor (p equations and p unknowns)
        || You need to know linear algebra to get a good, fast solution
        || the LS estimate for the coefficient of a multivariate regression model is exactly regression;
        || through the origin with the linear relationships with the other regressors removed from both;
        || the regressor and outcome by taking residuals
        || you've removed other variables from' the regressor and the response to isolate impact

    C. Special Case
        || if there is no relationship, things happen?
        || sum (resids * X) = 0

    D. Summing Up
        || Weve reduced p LS equations and p unknowns to p-1 eqs and unknowns
        || You can eventually get to 1 equation you want
        || if we want an adjusted relationship between y and x, keep taking residuals over confounders and do regression through origin
        || its like findings resids from lm(y~x1) 

    E. Interpretation of the coefficient
        || "the expected chagne in the response per unit change holding all other regresors fixed"
    
    F. Params
        || variance = (1/(n-p))*squared resids
        || coefs have standards errors following T distribution with n-p degrees of freedom
        || very very similar to simple linear regression
        
    G. Linear Models (Summary)
        || Linear models are the single most important applied statistical and machine learning technique
        || some things you can do
            \\Decompose a signal into its harmonics
            \\Flexibly fit complicated functions
            \\Fit factor variables as predictors
            \\uncover complex multivariate relationships with the response
            \\build accurate prediction models
        || linear models rebranded as "linear systems"
        || decomposing things into a set of linear relationships is very useful







