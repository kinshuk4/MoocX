Lesson 6
========

### Welcome

Notes:

------------------------------------------------------------------------

### Scatterplot Review

    #install.packages('ggplot2')
    library(ggplot2)
    data("diamonds")
    ggplot(data = diamonds, aes(x = carat, y = price)) + 
      geom_point(fill = I('#F79420'), color = I('black'), shape = 21)+
      scale_x_continuous(limits = c(0,quantile(diamonds$carat, 0.99))) + 
      scale_y_continuous(limits = c(0, quantile(diamonds$price,0.99)))

    ## Warning: Removed 926 rows containing missing values (geom_point).

![](lesson6_student_files/figure-markdown_strict/Scatterplot%20Review-1.png)

------------------------------------------------------------------------

### Price and Carat Relationship

Response: Higher weight, higher price nonlinear relationship

------------------------------------------------------------------------

### Frances Gerety

Notes:

#### A diamonds is

------------------------------------------------------------------------

### The Rise of Diamonds

Notes:

------------------------------------------------------------------------

### ggpairs Function

Notes:

    # install these if necessary
    #install.packages('GGally')
    #install.packages('scales')
    #install.packages('memisc')
    #install.packages('lattice')
    #install.packages('MASS')
    #install.packages('car')
    #install.packages('reshape')
    #install.packages('plyr')

    # load the ggplot graphics package and the others
    library(ggplot2)
    library(GGally)
    library(scales)
    library(memisc)

    ## Loading required package: lattice

    ## Loading required package: MASS

    ## 
    ## Attaching package: 'memisc'

    ## The following object is masked from 'package:scales':
    ## 
    ##     percent

    ## The following objects are masked from 'package:stats':
    ## 
    ##     contr.sum, contr.treatment, contrasts

    ## The following object is masked from 'package:base':
    ## 
    ##     as.array

    library(dplyr)

    ## 
    ## Attaching package: 'dplyr'

    ## The following objects are masked from 'package:memisc':
    ## 
    ##     collect, query, recode, rename

    ## The following object is masked from 'package:MASS':
    ## 
    ##     select

    ## The following object is masked from 'package:GGally':
    ## 
    ##     nasa

    ## The following objects are masked from 'package:stats':
    ## 
    ##     filter, lag

    ## The following objects are masked from 'package:base':
    ## 
    ##     intersect, setdiff, setequal, union

    library(reshape2)

    # sample 10,000 diamonds from the data set
    set.seed(20022012)
    diamond_samp <- diamonds[sample(1:length(diamonds$price), 10000), ]
    ggpairs(diamond_samp, 
      lower = list(continuous = wrap("points", shape = I('.'))), 
      upper = list(combo = wrap("box", outlier.shape = I('.'))))

    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.

    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.
    ## `stat_bin()` using `bins = 30`. Pick better value with `binwidth`.

![](lesson6_student_files/figure-markdown_strict/ggpairs%20Function-1.png)

What are some things you notice in the ggpairs output? Response:

------------------------------------------------------------------------

### The Demand of Diamonds

Notes:

    library(gridExtra)

    ## 
    ## Attaching package: 'gridExtra'

    ## The following object is masked from 'package:dplyr':
    ## 
    ##     combine

    plot1 <- qplot(x = price, data = diamonds, binwidth = 100, fill =I('#099DD9')) + 
      ggtitle('Price')

    plot2 <- qplot(x= price, data = diamonds, binwidth = 0.01, fill = I('#F79420')) +
      ggtitle('Price (log10)')+ scale_x_log10()

    grid.arrange(plot1, plot2, ncol = 2)

![](lesson6_student_files/figure-markdown_strict/The%20Demand%20of%20Diamonds-1.png)

------------------------------------------------------------------------

### Connecting Demand and Price Distributions

Notes:

------------------------------------------------------------------------

### Scatterplot Transformation

    qplot(carat, price, data = diamonds)+
      scale_y_continuous(trans = log10_trans()) + 
      ggtitle('Price (log10) by Carat')

![](lesson6_student_files/figure-markdown_strict/Scatterplot%20Transformation-1.png)

### Create a new function to transform the carat variable

    cuberoot_trans = function() trans_new('cuberoot', transform = function(x) x^(1/3),
                                          inverse = function(x) x^3)

#### Use the cuberoot\_trans function

    ggplot(aes(carat, price), data = diamonds) + 
      geom_point() + 
      scale_x_continuous(trans = cuberoot_trans(), limits = c(0.2, 3),
                         breaks = c(0.2, 0.5, 1, 2, 3)) + 
      scale_y_continuous(trans = log10_trans(), limits = c(350, 15000),
                         breaks = c(350, 1000, 5000, 10000, 15000)) +
      ggtitle('Price (log10) by Cube-Root of Carat')

    ## Warning: Removed 1683 rows containing missing values (geom_point).

![](lesson6_student_files/figure-markdown_strict/Use%20cuberoot_trans-1.png)

------------------------------------------------------------------------

### Overplotting Revisited

    head(sort(table(diamonds$carat), decreasing = T))

    ## 
    ##  0.3 0.31 1.01  0.7 0.32    1 
    ## 2604 2249 2242 1981 1840 1558

    head(sort(table(diamonds$price), decreasing = T))

    ## 
    ## 605 802 625 828 776 698 
    ## 132 127 126 125 124 121

    ggplot(aes(carat, price), data = diamonds) + 
      geom_point(alpha = 0.5, position = 'jitter', size = 0.75) + 
      scale_x_continuous(trans = cuberoot_trans(), limits = c(0.2, 3),
                         breaks = c(0.2, 0.5, 1, 2, 3)) + 
      scale_y_continuous(trans = log10_trans(), limits = c(350, 15000),
                         breaks = c(350, 1000, 5000, 10000, 15000)) +
      ggtitle('Price (log10) by Cube-Root of Carat')

    ## Warning: Removed 1691 rows containing missing values (geom_point).

![](lesson6_student_files/figure-markdown_strict/Overplotting%20Revisited-1.png)

------------------------------------------------------------------------

### Other Qualitative Factors

Notes:

------------------------------------------------------------------------

### Price vs. Carat and Clarity

Alter the code below.

    # install and load the RColorBrewer package
    #install.packages('RColorBrewer')
    library(RColorBrewer)

    ggplot(aes(x = carat, y = price, ,color = clarity), data = diamonds) + 
      geom_point(alpha = 0.5, size = 1, position = 'jitter') +
      scale_color_brewer(type = 'div',
        guide = guide_legend(title = 'Clarity', reverse = T,
        override.aes = list(alpha = 1, size = 2))) +  
      scale_x_continuous(trans = cuberoot_trans(), limits = c(0.2, 3),
        breaks = c(0.2, 0.5, 1, 2, 3)) + 
      scale_y_continuous(trans = log10_trans(), limits = c(350, 15000),
        breaks = c(350, 1000, 5000, 10000, 15000)) +
      ggtitle('Price (log10) by Cube-Root of Carat and Clarity')

    ## Warning: Removed 1693 rows containing missing values (geom_point).

![](lesson6_student_files/figure-markdown_strict/Price%20vs.%20Carat%20and%20Clarity-1.png)

------------------------------------------------------------------------

### Clarity and Price

Response:

------------------------------------------------------------------------

### Price vs. Carat and Cut

Alter the code below.

    ggplot(aes(x = carat, y = price, color = cut), data = diamonds) + 
      geom_point(alpha = 0.5, size = 1, position = 'jitter') +
      scale_color_brewer(type = 'div',
                         guide = guide_legend(title = 'Cut', reverse = T,
                                              override.aes = list(alpha = 1, size = 2))) +  
      scale_x_continuous(trans = cuberoot_trans(), limits = c(0.2, 3),
                         breaks = c(0.2, 0.5, 1, 2, 3)) + 
      scale_y_continuous(trans = log10_trans(), limits = c(350, 15000),
                         breaks = c(350, 1000, 5000, 10000, 15000)) +
      ggtitle('Price (log10) by Cube-Root of Carat and Cut')

    ## Warning: Removed 1696 rows containing missing values (geom_point).

![](lesson6_student_files/figure-markdown_strict/Price%20vs.%20Carat%20and%20Cut-1.png)

------------------------------------------------------------------------

### Cut and Price

Response:

------------------------------------------------------------------------

### Price vs. Carat and Color

Alter the code below.

    ggplot(aes(x = carat, y = price, color = color), data = diamonds) + 
      geom_point(alpha = 0.5, size = 1, position = 'jitter') +
      scale_color_brewer(type = 'div',
                         guide = guide_legend(title = 'Color',
                                              override.aes = list(alpha = 1, size = 2))) +  
      scale_x_continuous(trans = cuberoot_trans(), limits = c(0.2, 3),
                         breaks = c(0.2, 0.5, 1, 2, 3)) + 
      scale_y_continuous(trans = log10_trans(), limits = c(350, 15000),
                         breaks = c(350, 1000, 5000, 10000, 15000)) +
      ggtitle('Price (log10) by Cube-Root of Carat and Color')

    ## Warning: Removed 1688 rows containing missing values (geom_point).

![](lesson6_student_files/figure-markdown_strict/Price%20vs.%20Carat%20and%20Color-1.png)

    #Did you remove reverse = T on the guide_legend? It's a small detail that reminds us that D is the best color and J is worse for rating diamond colors.

------------------------------------------------------------------------

### Color and Price

Response:

------------------------------------------------------------------------

### Linear Models in R

Notes:

Response:

------------------------------------------------------------------------

### Building the Linear Model

Notes:

    m1 <- lm(I(log(price)) ~ I(carat^(1/3)), data = diamonds)
    m2 <- update(m1, ~ . + carat)
    m3 <- update(m2, ~ . + cut)
    m4 <- update(m3, ~ . + color)
    m5 <- update(m4, ~ . + clarity)
    mtable(m1, m2, m3, m4, m5)

    ## 
    ## Calls:
    ## m1: lm(formula = I(log(price)) ~ I(carat^(1/3)), data = diamonds)
    ## m2: lm(formula = I(log(price)) ~ I(carat^(1/3)) + carat, data = diamonds)
    ## m3: lm(formula = I(log(price)) ~ I(carat^(1/3)) + carat + cut, data = diamonds)
    ## m4: lm(formula = I(log(price)) ~ I(carat^(1/3)) + carat + cut + color, 
    ##     data = diamonds)
    ## m5: lm(formula = I(log(price)) ~ I(carat^(1/3)) + carat + cut + color + 
    ##     clarity, data = diamonds)
    ## 
    ## =========================================================================
    ##                      m1         m2         m3         m4         m5      
    ## -------------------------------------------------------------------------
    ##   (Intercept)      2.821***   1.039***   0.874***   0.932***   0.415***  
    ##                   (0.006)    (0.019)    (0.019)    (0.017)    (0.010)    
    ##   I(carat^(1/3))   5.558***   8.568***   8.703***   8.438***   9.144***  
    ##                   (0.007)    (0.032)    (0.031)    (0.028)    (0.016)    
    ##   carat                      -1.137***  -1.163***  -0.992***  -1.093***  
    ##                              (0.012)    (0.011)    (0.010)    (0.006)    
    ##   cut: .L                                0.224***   0.224***   0.120***  
    ##                                         (0.004)    (0.004)    (0.002)    
    ##   cut: .Q                               -0.062***  -0.062***  -0.031***  
    ##                                         (0.004)    (0.003)    (0.002)    
    ##   cut: .C                                0.051***   0.052***   0.014***  
    ##                                         (0.003)    (0.003)    (0.002)    
    ##   cut: ^4                                0.018***   0.018***  -0.002     
    ##                                         (0.003)    (0.002)    (0.001)    
    ##   color: .L                                        -0.373***  -0.441***  
    ##                                                    (0.003)    (0.002)    
    ##   color: .Q                                        -0.129***  -0.093***  
    ##                                                    (0.003)    (0.002)    
    ##   color: .C                                         0.001     -0.013***  
    ##                                                    (0.003)    (0.002)    
    ##   color: ^4                                         0.029***   0.012***  
    ##                                                    (0.003)    (0.002)    
    ##   color: ^5                                        -0.016***  -0.003*    
    ##                                                    (0.003)    (0.001)    
    ##   color: ^6                                        -0.023***   0.001     
    ##                                                    (0.002)    (0.001)    
    ##   clarity: .L                                                  0.907***  
    ##                                                               (0.003)    
    ##   clarity: .Q                                                 -0.240***  
    ##                                                               (0.003)    
    ##   clarity: .C                                                  0.131***  
    ##                                                               (0.003)    
    ##   clarity: ^4                                                 -0.063***  
    ##                                                               (0.002)    
    ##   clarity: ^5                                                  0.026***  
    ##                                                               (0.002)    
    ##   clarity: ^6                                                 -0.002     
    ##                                                               (0.002)    
    ##   clarity: ^7                                                  0.032***  
    ##                                                               (0.001)    
    ## -------------------------------------------------------------------------
    ##   R-squared            0.9        0.9        0.9        1.0        1.0   
    ##   adj. R-squared       0.9        0.9        0.9        1.0        1.0   
    ##   sigma                0.3        0.3        0.3        0.2        0.1   
    ##   F               652012.1   387489.4   138654.5    87959.5   173791.1   
    ##   p                    0.0        0.0        0.0        0.0        0.0   
    ##   Log-likelihood   -7962.5    -3631.3    -1837.4     4235.2    34091.3   
    ##   Deviance          4242.8     3613.4     3380.8     2699.2      892.2   
    ##   AIC              15931.0     7270.6     3690.8    -8442.5   -68140.5   
    ##   BIC              15957.7     7306.2     3762.0    -8317.9   -67953.7   
    ##   N                53940      53940      53940      53940      53940     
    ## =========================================================================

Notice how adding cut to our model does not help explain much of the
variance in the price of diamonds. This fits with out exploration
earlier.

------------------------------------------------------------------------

### Model Problems

Video Notes:

Research: (Take some time to come up with 2-4 problems for the model)
(You should 10-20 min on this)

1.  consider the inflation

Response:

------------------------------------------------------------------------

### A Bigger, Better Data Set

Notes:

    #install.packages('bitops')
    #install.packages('RCurl')
    library('bitops')
    library('RCurl')

    #diamondsurl = getBinaryURL("https://raw.github.com/solomonm/diamonds-data/master/BigDiamonds.Rda")

    #load(rawConnection(diamondsurl))
    load("BigDiamonds.rda")

The code used to obtain the data is available here:
<https://github.com/solomonm/diamonds-data>

Building a Model Using the Big Diamonds Data Set
------------------------------------------------

Notes:

    diamondsbig$logprice = log(diamondsbig$price)
    m1 <- lm(I(logprice) ~ I(carat^(1/3)), data = diamondsbig[diamondsbig$price <10000 & diamondsbig$cert == "GIA",])
    m2 <- update(m1, ~ . + carat)
    m3 <- update(m2, ~ . + cut)
    m4 <- update(m3, ~ . + color)
    m5 <- update(m4, ~ . + clarity)
    mtable(m1, m2, m3, m4, m5)

    ## 
    ## Calls:
    ## m1: lm(formula = I(logprice) ~ I(carat^(1/3)), data = diamondsbig[diamondsbig$price < 
    ##     10000 & diamondsbig$cert == "GIA", ])
    ## m2: lm(formula = I(logprice) ~ I(carat^(1/3)) + carat, data = diamondsbig[diamondsbig$price < 
    ##     10000 & diamondsbig$cert == "GIA", ])
    ## m3: lm(formula = I(logprice) ~ I(carat^(1/3)) + carat + cut, data = diamondsbig[diamondsbig$price < 
    ##     10000 & diamondsbig$cert == "GIA", ])
    ## m4: lm(formula = I(logprice) ~ I(carat^(1/3)) + carat + cut + color, 
    ##     data = diamondsbig[diamondsbig$price < 10000 & diamondsbig$cert == 
    ##         "GIA", ])
    ## m5: lm(formula = I(logprice) ~ I(carat^(1/3)) + carat + cut + color + 
    ##     clarity, data = diamondsbig[diamondsbig$price < 10000 & diamondsbig$cert == 
    ##     "GIA", ])
    ## 
    ## =========================================================================
    ##                      m1         m2         m3         m4         m5      
    ## -------------------------------------------------------------------------
    ##   (Intercept)      2.671***   1.333***   0.949***   0.529***  -0.464***  
    ##                   (0.003)    (0.012)    (0.012)    (0.010)    (0.009)    
    ##   I(carat^(1/3))   5.839***   8.243***   8.633***   8.110***   8.320***  
    ##                   (0.004)    (0.022)    (0.021)    (0.017)    (0.012)    
    ##   carat                      -1.061***  -1.223***  -0.782***  -0.763***  
    ##                              (0.009)    (0.009)    (0.007)    (0.005)    
    ##   cut: V.Good                            0.120***   0.090***   0.071***  
    ##                                         (0.002)    (0.001)    (0.001)    
    ##   cut: Ideal                             0.211***   0.181***   0.131***  
    ##                                         (0.002)    (0.001)    (0.001)    
    ##   color: K/L                                        0.123***   0.117***  
    ##                                                    (0.004)    (0.003)    
    ##   color: J/L                                        0.312***   0.318***  
    ##                                                    (0.003)    (0.002)    
    ##   color: I/L                                        0.451***   0.469***  
    ##                                                    (0.003)    (0.002)    
    ##   color: H/L                                        0.569***   0.602***  
    ##                                                    (0.003)    (0.002)    
    ##   color: G/L                                        0.633***   0.665***  
    ##                                                    (0.003)    (0.002)    
    ##   color: F/L                                        0.687***   0.723***  
    ##                                                    (0.003)    (0.002)    
    ##   color: E/L                                        0.729***   0.756***  
    ##                                                    (0.003)    (0.002)    
    ##   color: D/L                                        0.812***   0.827***  
    ##                                                    (0.003)    (0.002)    
    ##   clarity: I1                                                  0.301***  
    ##                                                               (0.006)    
    ##   clarity: SI2                                                 0.607***  
    ##                                                               (0.006)    
    ##   clarity: SI1                                                 0.727***  
    ##                                                               (0.006)    
    ##   clarity: VS2                                                 0.836***  
    ##                                                               (0.006)    
    ##   clarity: VS1                                                 0.891***  
    ##                                                               (0.006)    
    ##   clarity: VVS2                                                0.935***  
    ##                                                               (0.006)    
    ##   clarity: VVS1                                                0.995***  
    ##                                                               (0.006)    
    ##   clarity: IF                                                  1.052***  
    ##                                                               (0.006)    
    ## -------------------------------------------------------------------------
    ##   R-squared             0.9        0.9       0.9        0.9         1.0  
    ##   adj. R-squared        0.9        0.9       0.9        0.9         1.0  
    ##   sigma                 0.3        0.3       0.3        0.2         0.2  
    ##   F               2700903.7  1406538.3  754405.4   423311.5    521161.4  
    ##   p                     0.0        0.0       0.0        0.0         0.0  
    ##   Log-likelihood   -60137.8   -53996.3  -43339.8    37830.4    154124.3  
    ##   Deviance          28298.7    27291.5   25628.3    15874.9      7992.7  
    ##   AIC              120281.6   108000.5   86691.6   -75632.8   -308204.5  
    ##   BIC              120313.8   108043.5   86756.0   -75482.6   -307968.4  
    ##   N                338946     338946    338946     338946      338946    
    ## =========================================================================

------------------------------------------------------------------------

Predictions
-----------

Example Diamond from BlueNile: Round 1.00 Very Good I VS1 $5,601

    #Be sure you’ve loaded the library memisc and have m5 saved as an object in your workspace.
    thisDiamond = data.frame(carat = 1.00, cut = "V.Good",
                             color = "I", clarity="VS1")

    modelEstimate = predict(m5, newdata = thisDiamond,
                            interval="prediction", level = .95)

    dat = data.frame(m4$model, m4$residuals) 

    with(dat, sd(m4.residuals)) 

    ## [1] 0.2164168

    with(subset(dat, carat > .9 & carat < 1.1), sd(m4.residuals)) 

    ## [1] 0.2178147

    dat$resid <- as.numeric(dat$m4.residuals)
    ggplot(aes(y = resid, x = round(carat, 2)), data = dat) + 
      geom_line(stat = "summary", fun.y = sd) 

    ## Warning: Removed 1 rows containing missing values (geom_path).

![](lesson6_student_files/figure-markdown_strict/unnamed-chunk-1-1.png)

Evaluate how well the model predicts the BlueNile diamond's price. Think
about the fitted point estimate as well as the 95% CI.

------------------------------------------------------------------------

Final Thoughts
--------------

Notes:

------------------------------------------------------------------------

Click **KnitHTML** to see all of your hard work and to have an html page
of this lesson, your answers, and your notes!
