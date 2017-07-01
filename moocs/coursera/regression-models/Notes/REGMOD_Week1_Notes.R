Regression Models Week 1

Video 101 - Introduction to Regression

    A. History of Regression
        || developed by Sir Francis Galton
        || Galton designed the parent-child data
    B. Purpose of the class
        || Find a simple model describing relationships between variables

Video 101b - Basic Least Squares

    A. Galtons Data
        || invented concepts of regression and correlation
        || his data are in the package "UsingR"
        || Overplotting is an issue from discretization
    B. Some Code
        
            library(UsingR); data(galton)
            #one row, two columns
            par(mfrow=c(1,2))
            hist(galton$child,col="blue", breaks=100)
            hist(galton$parent, col="blue", breaks=100)

    C. Finding the middle of datasets via least squares
        || center of mass = "the point that minimizes the squared deviations from the mean"
        || this is the heart of least squares

Video 101c - Least Squares (cont.)

    A. An Experiment using R studios manipulate function
        
            library(manipulate)
            myHist <- function(mu){
                hist(galton$child,col="blue",breaks=100)
                lines(c(mu,mu),c(0,150),col="red",lwd=5)
                mse <- mean((galton$child-mu)^2)
                text(63,150,paste("mu= ",mu))
                text(63,140,paste("MSE= ",round(mse,2)))
            }

Video 101d - Regression Through the Origin

    A. Comparing childrens' heights and parents' heights
        
            plot(galton$parent,galton$child,pch=19,col="blue")

    B. Regression through the origin

        || you get pretty goofy results if you run the line through the origin
        || Check out the code in the lecture notes/markdown
    
            manipulate(myHist(mu),mu=slider(62,74,step=0.5))
    
    C. The Solution

            lm(I(child-mean(child)) ~ I(parent-mean(parent)) - 1, data=galton)

        || using the -1 tells R not to fit an intercept

Video 102a - Basic Notation and Background

    A. Some basic definitions
        || we_ll cover some basic definitions and notation used throughout the class
    B. Some notation
        || subscripts for observations
        || capital letters for conceptual values (e.g. probability)
        || lowercase letters for realized values
    C. The empirical mean
        || The sample mean
        || If we subtract the mean from the data, the resulting data have mean 0
        || This is called centering the random variables
        || The mean is a measure of central tendancy of the data
        || The mean is the LS solution for minimum squared deviations

Video 102b - Normalization and Correlation

    A. The empirical standard deviation and variance
        || the empirical sd is defined as the square roots of the sample variance divided by n-1
        || The data defined by Xi/s would have empirical standard deviation 1. "Scaling" the data
        || using n-1 produces an unbiased estimate
    B. Normalization
        || Z-scores, basically
        || centering and scaling the data (converting to Z scores) is called "normalizing"
        || subtract the mean and divide by standard deviations
    C. The empirical covariance
        || cov(x,y) = sum of (Xi-Xbar)(Yi=Ybar) / n-1
        || cor(x,y) = cov(x,y)/Sx*Sy
        || cor(x,y)=0 implies not linear relationship

Video 103a - Linear Least Squares Estimation of Regression Lines

    A. General LS for linear equations
    B. Fitting the best line
        || Yi = B0 + B1Xi

Video 103b - Linear Least Squares Special Cases

    A. Mean only Regression
        || the constant equals sample mean dep if no slope
    B. Regression through the origin

Video 103c - Linear Least Squares Solved

    A. Recapping what we know
        || The constant is the sample mean dep. if no slope
    B. What happens with constant and non-zero slope?
        || if you normalized the data, the slope of the line is just cor(y,x)
        || slope with and without centering is identical
    C. Revising Galtons data
        
            y <- galton$child
            x <- galton$parent
            beta1 <- cor(y,x) * sd(y)/sd(x)
            beta0 <- mean(y)-beta1*mean(x)
            
    D. Final
        || regression through the origin yields an equivalent slope if you center the data first

            yc <- y-mean(y)
            xc <- x-mean(x)
            beta1 <- sum(xc*yc)/sum(xc^2)
            
Video 104a - Regression to the Mean

    A. Historical Side note, Regression to Mediocrity
        || the random part is what drives regression to the mean
        || the intrinsic part (drivers) changes the mean
        
Video 104b - Regression to the Mean Example

    A. Some stuff
        || slope of regression line if we normalize is just cor(Y,X)
        || normal slope --> cor(X,Y)*sd(depvar)/sd(indvar)
        || a bunch of R code for plotting in the slides
    B. Normalizing the data and setting plotting parameters
        
            xlab = "Father's height, normalized"
            ylab = "Son's height, normalized"
            xlim, ylim
            bg = "lightblue", col = "black"', cex=1.1, pch=21
            frame=FALSE'
    C. Plot the data, code
        || use abline() to add a line to a plot
        || all regression lines go through the sample means
    D. Discussion
        || If you had to predict a son_s norm height, it would be cor(Y,X)*Xi
        || regression to mean effect --> multiplication by this correlation shrinks toward 0
        || if the correlation is 1, there is no regression to the mean
        || regression to the mean ahs been thought about and generalized recently
        || nature of error distribution, nonlinearity, changes this a bit
        