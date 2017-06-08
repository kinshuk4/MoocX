
<center> <h2>Examining National Storm Data - Economic and Health Consequences of Weather Events</h2> </center>
<center> By [James Lamb](http://www.linkedin.com/in/jameslamb1/)</center>
<center>-----------------------------------------------------------------------------------------------------------------------------------------------------</center>

###I. Synopsis

This is a brief (in content) but verbose (in code) project prepared in partial fulfillment of the requirements for the [Reproducible Research](https://class.coursera.org/repdata-010) course offered by Johns Hopkins University via Coursera, as part of the JHU Data Science Specialization. 

In this report, I sought to determine the health and economic impacts of storms using the US NOAA National Weather Service dataset on storms. I began by downloading the raw csv file and examining the contents. After that, I put a substantial amount of effort into cleaning the data and reducing the many entries for storm event type to the 48 unique values listed in the documentation. After that, I did some very basic aggregation and preliminary analysis. This crude analysis, which suffered from many weaknesses, suggested that tornadoes have caused the most economic and health damage, cumulatively, over the last 60 years or so. Hurricanes and tsunamis, though far less frequent, tended to be the most deadly and have the largest economic impacts on average.

The analysis suffered from many limitations, and its finding are weak at best. Please read the concluding remarks, and contact me if you have additional ideas for improvement.

###II. Data Processing

This analysis relies on storm data collected by the U.S. national Oceanic and Atmospheric Administration (NOAA) and distributed by [the instructor](https://twitter.com/rdpeng), and can be downloaded [here](https://d396qusza40orc.cloudfront.net/repdata%2Fdata%2FStormData.csv.bz2). There is also some supporting documentation available [here](https://d396qusza40orc.cloudfront.net/repdata%2Fpeer2_doc%2Fpd01016005curr.pdf) and [here](https://d396qusza40orc.cloudfront.net/repdata%2Fpeer2_doc%2FNCDC%20Storm%20Events-FAQ%20Page.pdf). 

The dataset includes detailed information, from 1950 through 2011, on storm characteristics like location, timing, fatalities/injuries caused, and property damages. 

There are a total of 359,191 observations in the dataset. All missing data were coded as "NA" in the original dataset.


```r
## Define a function to download a bzip2-compressed csv from the internet, read it in to R                       
read.bzip2csv <- function(url, file, header = TRUE, sep = ",", stringsAsFactors = FALSE){
    
    ## check to see if the data have already been downloaded
    if(file.exists(file)==FALSE) {
    
        ## download the data
        download.file(url, destfile = file) #note that we use one arg, "file", for     destfile and later for the read in to R   
        }
    
    ## read the data into R
    data1 <<- read.csv(file = file, header = header, sep = sep, stringsAsFactors = stringsAsFactors, strip.white=TRUE)
   
    ## print output message to the console
    out_string <- "Data downloaded succesfully, read into R, and stored in object data1."
    out_string
}

## Read in the data for this project
read.bzip2csv(url= "http://d396qusza40orc.cloudfront.net/repdata%2Fdata%2FStormData.csv.bz2", file = ".\\SStormdata.csv.bz2", sep = ",", header = TRUE, stringsAsFactors = FALSE)
```

```
## [1] "Data downloaded succesfully, read into R, and stored in object data1."
```

Next, I took some steps to clean the data. To begin, I looked at the unique values of ```EVTYPE``` to see how things could be condensed/combined.


```r
## Check unique values of EVTYPE
freqtable <- sort(table(as.factor(data1$EVTYPE))) 
#freqtable

## Calculate number of uniques
unique_evtype1 <- length(unique(data1$EVTYPE))
```

This initial analysis revealed ``985`` unique values for EVTYPE, an order of magnitude larger than the 48 types listed on pgs. 3-4 of the documentation. To address this issue, I first took the obvious steps: removing leading/ending spaces, reducing double spaces to single spaces, and changing the values of ```EVYTPE``` to upper-case.


```r
## copy event type, work with copy (so we don't overwrite the original data)
data1$EVTYPE2 <- data1$EVTYPE

## trim spaces, change multiple consecutive spaces to single space, change to upper-case
cleanup <- function (x) {
    x <- toupper(x) ## change to uppercase
    gsub("^\\s+|\\s+$","", x) ## replace leading/lagging spaces
    gsub("  "," ", x) ##replace double spaces with single spaces
    }
data1$EVTYPE2 <- cleanup(x=data1$EVTYPE2)

## remove all numbers, run cleanup again
data1$EVTYPE2 <- gsub("[0-9]","",data1$EVTYPE2)
data1$EVTYPE2 <- cleanup(x=data1$EVTYPE2)

## A function to remove special characters
library(stringr) 
rm_specials <- function(x, chars=c("-", "\\(", "\\)", "\\.")) {
    
    if (class(chars)!="character") stop('Error: char expected a character string. For example, try something like chars=c("a","b","c")')
    
    ## coerce chars to a list                                    
    chars <<- str_split(chars, " ", n= Inf) 
    
    ## remove hatever characters you fed to the function
    for (i in 1:length(chars)){
        x <- gsub(chars[[i]],"",x)
    }
    
    ## clean it up afterward
    x <- cleanup(x)
    
    return(x)
}

## Remove all special characters
data1$EVTYPE2 <- rm_specials(x=data1$EVTYPE2, chars=c("\\-", "\\(", "\\)", "\\."))

## Another condensing function. This one changes all strings containing a word to that word
rationalize <- function(x, strings) {
    
    if (class(strings)!="character") stop('Error: condense_it expected strings to be a character string. For example, try something like chars=c("a","b","c")')
    
    ## remove hatever characters you fed to the function
    for (i in 1:length(strings)){
        
        indx <- grep(strings[i], x)  ## get an index of locations containing this string. To avoid over-writing, only use starting value of strings
        
        x[indx] <- gsub("\\^", "",strings[i])
    }
    
    ## clean it up afterward
    x <- cleanup(x)
    return(x)
}

## Further rationalize the EVTYPE entries
data1$EVTYPE2 <- rationalize(x=data1$EVTYPE2, strings=c("ASTRONOMICAL LOW TIDE", "AVALANCHE", "BLIZZARD", "COASTAL FLOOD", "DEBRIS FLOW", "DENSE FOG", "DENSE SMOKE", "DROUGHT", "DUST STORM", "DUST DEVIL", "EXCESSIVE HEAT", "EXTREME COLD/WIND CHILL", "FLASH FLOOD", "^FLOOD", "FREEZING FOG", "FROST/FREEZE", "FUNNEL CLOUD", "HEAVY RAIN", "HEAVY SNOW", "HIGH SURF", "^HIGH WIND", "HURRICANE/TYPHOON", "ICE STORM", "LAKESHORE FLOOD", "LAKE EFFECT SNOW", "LIGHTNING", "MARINE HAIL", "MARINE HIGH WIND", "MARINE STRONG WIND", "MARINE THUNDERSTORM WIND", "RIP CURRENT", "SEICHE", "SLEET", "STORM TIDE", "^STRONG WIND", "^THUNDERSTORM WIND", "TORNADO", "TROPICAL DEPRESSION", "TROPICAL STORM", "TSUNAMI", "VOLCANIC ASH", "WATERSPOUT", "WILDFIRE", "WINTER STORM", "WINTER WEATHER", "SUMMARY"))
unique_evtype5 <- length(unique(data1$EVTYPE2))

## Write a function to allow search-and-replace type rationalization (for special cases)
rationalize2 <- function(x,lookup, replacement) {
    
    ## take the [i] value from lookup, replace the whole string with [i] from replacement
    
    ## swap replacement[i] for lookup[i]
    for (i in 1:length(lookup)){
        
        indx <- grep(lookup[i], x)  ## get an index of locations containing this string
        #x[indx] <- gsub(lookup[i],gsub("\\$", "",gsub("\\^", "",replacement[i])),x)
        x[indx] <- replacement[i]
    }
    
    ## clean it up afterward
    x <- cleanup(x)
    return(x)
}

## Address Some Special Cases
data1$EVTYPE2 <- rationalize2(x=data1$EVTYPE2, lookup = c("HURRICANE", "TYPHOON", "VOLCANIC", "THUNDERSTORM WINS", "MICROBURST", "GUSTNADO", "DOWNBURST", "^THUNDERSTORMWINDS", "^TSTMW", "^THUNDERSTORMS WIND", "^TSTM WIND", "WILD/FOREST FIRE", "^THUNDERTORM WINDS", "URBAN AND SMALL", "URBAN SMALL","URBAN/SM", "UNSEASONABLY HOT", "UNSEASONABLY WARM", "^TSTM WND", "^THUNDERSTORM WINDS", "TORRENTIAL RAIN", "^THUNERSTORM WINDS", "LIGHTING", "LIGNTING", "DUST DEVEL", "DUSTSTORM", "DAM BREAK", "DAM FAILURE", "FLASH FLOODING", "FROST", "WATERSPROUT", "WATER SPOUT", "WAYTERSPOUT", "BLOWOUT", "APACHE COUNTY", "^THUNDERSTORMW", "^THUNDERSTROM W", "ICE FOG", "MICOBURST", "RECORD HIGH", "GUSTY", "FLASH FLOOODING", "SEVERE THUNDERSTORM", "L STREAM", "LAKEEFFECT SNOW", "BLOWING SNOW ", "BLOWING DUST", "BRUSH FIRE", "WILD FIRES", "URBAN FLOOD", "STREET FLOODING", "THUNDERSTORM W INDS", "^THUNDERSTORM WIND", "^THUNDESTORM WINDS", "^ TSTM WIND$", "MIRCOBURST", "MARINE TSTM WIND", "LIGNTNING", "^TSTM", "MONTH", "THUNDERTSORM", "^THUNDEERSTORM WINDS", "^THUDERSTORM WIND", "SEVERE COLD", "BEACH FLOOD", "COASTAL/TIDAL FLOOD", "COASTALFLOOD", "EXTREME WIND CHILL", "EXTREME WINDCHILL", "TIDAL FLOOD", "VOG", "HEAVY SHOWER", "EXCESSIVE RAIN", "EXCESSIVE SNOW", "AVALANCE", "^DRY", "CSTL FLOOD", "GRADIENT WIND", "HYP", "^MUD", "RECORD SNOW", "NORTHERN LIGHT", "^FUNNEL", "DROWNING", "BLACK ICE", "FIRST SNOW", "GRASS FIRE", "PATTERN", "NO SEVERE WEATHER", "NONE", "SAHARAN WALL CLOUD", "SEVERE TURBULENCE", "WAKE LOW WIND", "^WIND$"), replacement = c("HURRICANE/TYPHOON", "HURRICANE/TYPHOON", "VOLCANIC ASH", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "WILDFIRE", "THUNDERSTORM WIND", "FLASH FLOOD", "FLASH FLOOD", "FLASH FLOOD", "HEAT", "HEAT", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "HEAVY RAIN", "THUNDERSTORM WIND", "LIGHTNING", "LIGHTNING", "DUST DEVIL", "DUST STORM", "FLASH FLOOD", "FLASH FLOOD", "FLASH FLOOD", "FROST/FREEZE", "WATERSPOUT", "WATERSPOUT", "WATERSPOUT", "OTHER", "OTHER", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "FREEZING FOG", "THUNDERSTORM WIND", "EXCESSIVE HEAT", "STRONG WIND", "FLASH FLOOD", "THUNDERSTORM WIND", "FLASH FLOOD", "LAKE EFFECT SNOW", "WINTER STORM", "DUST STORM", "WILDFIRE", "WILDFIRE", "FLASH FLOOD", "FLASH FLOOD", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "LIGHTNING", "THUNDERSTORM WIND", "SUMMARY", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "THUNDERSTORM WIND", "EXTREME COLD/WIND CHILL", "COASTAL FLOOD", "COASTAL FLOOD", "COASTAL FLOOD", "EXTREME COLD/WIND CHILL", "EXTREME COLD/WIND CHILL", "COASTAL FLOOD", "OTHER", "HEAVY RAIN", "HEAVY RAIN", "HEAVY SNOW", "AVALANCHE", "DROUGHT", "COASTAL FLOOD", "OTHER", "WINTER WEATHER", "DEBRIS FLOW", "HEAVY SNOW", "OTHER", "FUNNEL CLOUD", "OTHER", "OTHER", "WINTER WEATHER", "WILDFIRE", "OTHER", "OTHER", "OTHER", "DUST STORM", "OTHER", "OTHER", "OTHER"))
```

At this point, it became clear that the time required to complete this task was prohibitively long. If this were truly a report being prepared for FEMA or some other organization, I would have more than a week to do it. Given the very basic scope of this project, I decided to make a few more value substitutions (where the mapping was obvious), and to cut my losses and turn the rest of the categories into "OTHER". The frequency table below gives some initial indication of the impact of this shortcut. I understand that there are better ways to accomplish this data cleaning stage, but I honestly cannot think of any that would work in the time given for this project. If you have suggestions, I encourage you to contact me on [LinkedIN](http://www.linkedin.com/in/jameslamb1/) and share your thoughts.


```r
## One final set of special-case mappings
data1$EVTYPE2 <- rationalize2(x=data1$EVTYPE2, lookup = c("LANDSLIDE", "^FOG$", "FREEZING RAIN", "STORM SURGE", "RIVER FLOOD", "^SMALL HAIL", "^THUNDERSTORM$", "EXTREME HEAT", "^FREEZE$", "RECORD RAIN", "^THUNDERESTORM WINDS", "TORNDAO", "SAHARAN DUST", "RIVER AND STREAM FLOOD", "RECORD WARM", "RECORD HEAT", "RAPIDLY RISING WATER",  "HVY RAIN", "^EXTREME COLD$", "RECORD COLD", "HEAVY SURF", "^THUNDERSTORM^", "HEAVY LAKE SNOW", "UNSEASONABLY COLD", "ICE JAM FLOOD", "MAJOR FLOOD", "STREET FLOOD", "UNUSUALLY COLD", "HIGH TIDE", "THUNDERSTORM DAMAGE", "COLD WIND CHILL TEMPERATURES", "LOCAL FLOOD", "SNOW HIGH WIND WIND CHILL", "MINOR FLOOD", "BITTER WIND CHILL", "HEAVY WET SNOW", "FOREST FIRE", "DEEP HAIL", "SNOW/HIGH WINDS", "ROCK SLIDE", "THUNDERSTORM HAIL", "HIGHWAY FLOODING", "COASTALSTORM", "BITTER WIND CHILL", "HAZARDOUS SURF", "^SNOW", "^TUNDERSTORM WIND", "\\?" ), replacement = c("DEBRIS FLOW", "DENSE FOG", "WINTER WEATHER", "STORM SURGE/TIDE", "FLOOD", "HAIL", "THUNDERSTORM WIND", "EXCESSIVE HEAT", "FROST/FREEZE", "HEAVY RAIN", "THUNDERSTORM WIND", "TORNADO", "DUST STORM", "FLOOD", "EXCESSIVE HEAT", "EXCESSIVE HEAT", "FLASH FLOOD", "HEAVY RAIN", "EXTREME COLD/WIND CHILL", "COLD/WIND CHILL", "HIGH SURF", "THUNDERSTORM WIND", "LAKE EFFECT SNOW", "COLD/WIND CHILL", "FLOOD", "FLOOD", "FLASH FLOOD", "COLD/WIND CHILL", "HIGH SURF", "THUNDERSTORM WIND", "COLD/WIND CHILL", "FLOOD", "WINTER STORM", "FLOOD", "EXTREME COLD/WIND CHILL", "HEAVY SNOW", "WILD FIRE", "HAIL", "WINTER STORM", "DEBRIS FLOW", "HAIL", "FLOOD", "TSUNAMI", "EXTREME COLD/WIND CHILL", "HIGH SURF", "WINTER WEATHER", "THUNDERSTORM WIND", "OTHER"))

## Change everything else to "OTHER"
checklist <- unique(data1$EVTYPE2)
main_types <- c("ASTRONOMICAL LOW TIDE", "AVALANCHE", "BLIZZARD", "COASTAL FLOOD", "COLD/WIND CHILL", "DEBRIS FLOW", "DENSE FOG", "DENSE SMOKE", "DROUGHT", "DUST DEVIL", "DUST STORM", "EXCESSIVE HEAT", "EXTREME COLD/WIND CHILL", "FLASH FLOOD", "FLOOD", "FROST/FREEZE", "FUNNEL CLOUD", "FREEZING FOG", "HAIL", "HEAT", "HEAVY RAIN", "HEAVY SNOW", "HIGH SURF", "HIGH WIND", "HURRICANE/TYPHOON", "ICE STORM", "LAKE EFFECT SNOW", "LAKESHORE FLOOD", "LIGHTNING", "LIGHTNING", "MARINE HAIL", "MARINE HIGH WIND", "MARINE STRONG WIND", "MARINE THUNDERSTORM WIND", "RIP CURRENT", "SEICHE", "SLEET", "STORM SURGE/TIDE", "STRONG WIND", "THUNDERSTORM WIND", "TORNADO", "TROPICAL DEPRESSION", "TROPICAL STORM", "TSUNAMI", "VOLCANIC ASH", "WATERSPOUT", "WILDFIRE", "WINTER STORM", "WINTER WEATHER", "SUMMARY")

for (i in 1:length(unique(data1$EVTYPE2))) {
    if ((checklist[i] %in% main_types)==FALSE){
        temp <- paste("^", checklist[i], "$", sep="")
        indx <- grep(temp, data1$EVTYPE2) ## make sure we're not over-writing aything we don't want to
        data1$EVTYPE2[indx] <- "OTHER"
    }
}
unique_evtype6 <- length(unique(data1$EVTYPE2))

##Print a frequency table of the unique types
freqtable <- sort(table(as.factor(data1$EVTYPE2)))
freqtable
```

```
## 
##              DENSE SMOKE                   SEICHE                  TSUNAMI 
##                       10                       21                       21 
##          LAKESHORE FLOOD             VOLCANIC ASH             FREEZING FOG 
##                       23                       29                       48 
##       MARINE STRONG WIND      TROPICAL DEPRESSION                    SLEET 
##                       48                       60                      120 
##         MARINE HIGH WIND                  SUMMARY               DUST DEVIL 
##                      135                      136                      151 
##    ASTRONOMICAL LOW TIDE        HURRICANE/TYPHOON                AVALANCHE 
##                      174                      299                      388 
##         STORM SURGE/TIDE               DUST STORM              MARINE HAIL 
##                      409                      438                      442 
##              DEBRIS FLOW          COLD/WIND CHILL         LAKE EFFECT SNOW 
##                      646                      649                      684 
##           TROPICAL STORM              RIP CURRENT            COASTAL FLOOD 
##                      697                      777                      884 
##                     HEAT                HIGH SURF             FROST/FREEZE 
##                      921                     1162                     1489 
##                DENSE FOG  EXTREME COLD/WIND CHILL           EXCESSIVE HEAT 
##                     1834                     1895                     1955 
##                    OTHER                ICE STORM                  DROUGHT 
##                     1960                     2027                     2538 
##                 BLIZZARD               WATERSPOUT              STRONG WIND 
##                     2744                     3847                     3876 
##                 WILDFIRE MARINE THUNDERSTORM WIND             FUNNEL CLOUD 
##                     4236                     5812                     6982 
##           WINTER WEATHER             WINTER STORM               HEAVY RAIN 
##                     9208                    11445                    11842 
##                LIGHTNING               HEAVY SNOW                HIGH WIND 
##                    15777                    15837                    21785 
##                    FLOOD              FLASH FLOOD                  TORNADO 
##                    25691                    59522                    60701 
##                     HAIL        THUNDERSTORM WIND 
##                   288717                   331205
```

After taking all these steps, I was left with ``50` unique values in the "EVTYPE", the 48 event types listed in the documentation plus two classes I added, "OTHER" and "SUMMARY". "OTHER" holds all observations which I was not able to classify with any confidence and "SUMMARY" contains observations labeled as monthly or quarterly summaries of damage. 

I made the assumption that the "SUMMARY" entries contained summary statistics for given regions and time periods, and that including these observations would double-count some storms. As a result, the final datacleaning step involved removing all of the summary observations. In this step, I also changed EVTYPE2 to a factor, for use in graphing in summarizing.


```r
## Remove "SUMMARY" observations from the dataset
finaldata <- data1[(data1$EVTYPE2 == "SUMMARY")==FALSE,]

## Change "EVTYPE2" to a factor variable for graphing
finaldata$EVTYPE2 <- as.factor(finaldata$EVTYPE2)
```

###III. Results

With the data cleaned and ready for analysis, I performed two very quick analyses. Please see concluding remarks for other types of analysis I would have conducted, given more time to complete the assignment and some incentive to be thorough/complete.

####A. WhiCH Types of Events are Most Harmful with Respect to Population Health?

To assess the health effects of events, I examined only fatalities and injuries, referred to as ```FATALITIES``` and ```INJURIES```. I did not consider direct vs. indirect causal links, I did not examine the time-series characteristics of these relationships, and I did not investigate other factors which might amplify the health effects of storms (such as quality of medical care or quality of emergency-response organizations).


```r
## Organize the data by total fatalities
fataldata1 <- aggregate(FATALITIES ~ EVTYPE2, data=finaldata, na.rm=TRUE, sum)
    fataldata1 <- fataldata1[order(-fataldata1$FATALITIES),] ## sort by most fatalities
    names(fataldata1) <- c("EVTYPE", "TOTAL.FATALITIES")
    head(fataldata1)  ## print first five rows
```

```
##               EVTYPE TOTAL.FATALITIES
## 41           TORNADO             5661
## 12    EXCESSIVE HEAT             2018
## 14       FLASH FLOOD             1065
## 20              HEAT              977
## 29         LIGHTNING              817
## 40 THUNDERSTORM WIND              723
```

```r
fataldata2 <- aggregate(FATALITIES ~ EVTYPE2, data=finaldata, na.rm=TRUE, mean)
     fataldata2 <- fataldata2[order(-fataldata2$FATALITIES),] ## sort by highest average fatalities
    names(fataldata2) <- c("EVTYPE", "MEAN.FATALITIES")
    head(fataldata2)  ## print first five rows
```

```
##               EVTYPE MEAN.FATALITIES
## 44           TSUNAMI       1.6190476
## 20              HEAT       1.0608035
## 12    EXCESSIVE HEAT       1.0322251
## 35       RIP CURRENT       0.7425997
## 2          AVALANCHE       0.5798969
## 25 HURRICANE/TYPHOON       0.4515050
```

The data suggest that tornadoes have caused the most cumulative deaths over the data period (1950-2011), while tsunamies, when they appear, tend to cause the most fatalities per event on average. As you can see, though, over 1000 deaths were tied to events categorized as "OTHER", meaning that beyond the top 1 or 2 spots, the data quality issues in this dataset raise serious credibility questions. It also might be the case that certain types of storms, like tornadoes, were more likely to be reported in earlier periods of the data.

It should also be noted that this analysis does not consider population density, emergency response quality, or other factors which might impact the health impact potential of a particular storm. Keeping all these data weaknesses in mind, I next turned my attention to another aspect of health: storm-related injuries.


```r
## Organize the data by total fatalities
injurydata1 <- aggregate(INJURIES ~ EVTYPE2, data=finaldata, na.rm=TRUE, sum)
    injurydata1 <- injurydata1[order(-injurydata1$INJURIES),] ## sort by most cumulative injuries
    names(injurydata1) <- c("EVTYPE", "TOTAL.INJURIES")
    head(injurydata1, n=5)  ## print first five rows
```

```
##               EVTYPE TOTAL.INJURIES
## 41           TORNADO          91407
## 40 THUNDERSTORM WIND           9545
## 15             FLOOD           6794
## 12    EXCESSIVE HEAT           6730
## 29         LIGHTNING           5232
```

```r
injurydata2 <- aggregate(INJURIES ~ EVTYPE2, data=finaldata, na.rm=TRUE, mean)
    injurydata2 <- injurydata2[order(-injurydata2$INJURIES),] ## sort by highest average injuries per event
    names(injurydata2) <- c("EVTYPE", "MEAN.INJURIES")
    head(injurydata2, n=5)  ## print first five rows
```

```
##               EVTYPE MEAN.INJURIES
## 44           TSUNAMI      6.142857
## 25 HURRICANE/TYPHOON      4.458194
## 12    EXCESSIVE HEAT      3.442455
## 20              HEAT      2.298588
## 41           TORNADO      1.505857
```

The picture for injuries is very similar to the one for fatalities. Tornadoes contributed, by far, the most cumulative injuries over the data period, with nearly 91,500 injuries caused. Note again, however, that over 10,000 injuries were classified as "OTHER", suggesting that much work needs to be done on the data cleaning step before we can make credible claims about the relative impacts of different types of storm events on health. 

The average figures, which show the average number of injuries per occurrence of a given event type, suggest that Tsunamis and Hurricanes tend to be the most harmful, on average. Excessive heat and above-average heat are the next worst offenders. The same caveats about data quality apply. The average figures might be better understood graphically, as shown below.


```r
## Barplot of average injuries
rownames(injurydata2) <- injurydata2$EVTYPE

## Subset just what we want to plot
injurydata2graph <- head(injurydata2, n = 10)
barplot(height=injurydata2graph$MEAN.INJURIES, main = "Fig. 1 Average Injuries Caused By Different Weather Event Types", horiz=TRUE, legend = rownames(injurydata2graph), beside=TRUE, col = c("red", "blue", "brown", "darkgrey", "azure", "cyan", "aquamarine", "coral3", "green", "darkorange"))
```

![](PA2_template_files/figure-html/sec2plot-1.png) 

This preliminary analysis suggests that tornadoes tend to cause the most population health damage, though the analysis suffers from many of the weaknesses described in this section, previous sections, and the concluding remarks at the end of the paper.

####B. Which Types of Events Have the Greatest Economic Consequences?

After investigating human population consequences, I turned my attention to the economic consequences of storms. I consider a limited definition of "Total Damage", given as the sum of crop damange and property damange.


```r
## Calculate total damage
finaldata$TOTDMG <- (finaldata$CROPDMG + finaldata$PROPDMG)

## Organize the data by total fatalities
dmgdata1 <- aggregate(TOTDMG ~ EVTYPE2, data=finaldata, na.rm=TRUE, sum)
    dmgdata1 <- dmgdata1[order(-dmgdata1$TOTDMG),] ## sort by most cumulative damage
    names(dmgdata1) <- c("EVTYPE", "TOTAL DAMAGE")
    head(dmgdata1)  ## print first five rows
```

```
##               EVTYPE TOTAL DAMAGE
## 41           TORNADO    3315779.3
## 40 THUNDERSTORM WIND    2878917.0
## 14       FLASH FLOOD    1709947.1
## 19              HAIL    1270146.7
## 15             FLOOD    1099316.6
## 29         LIGHTNING     607267.9
```

```r
dmgdata2 <- aggregate(TOTDMG ~ EVTYPE2, data=finaldata, na.rm=TRUE, mean)
    dmgdata2 <- dmgdata2[order(-dmgdata2$TOTDMG),] ## sort by highest average damage
    names(dmgdata2) <- c("EVTYPE", "MEAN TOTAL DAMAGE")
    head(dmgdata2)  ## print first five rows
```

```
##               EVTYPE MEAN TOTAL DAMAGE
## 25 HURRICANE/TYPHOON         123.15866
## 43    TROPICAL STORM          80.91506
## 38  STORM SURGE/TIDE          66.07711
## 41           TORNADO          54.62479
## 36            SEICHE          46.66667
## 44           TSUNAMI          44.06190
```

Using this very crude measure of economic impact, it appears that tornadoes had the most cumulative impact over the sample, at over $3.3 million dollars. There is clearly a sever scaling problem hear (I'm guessing it has something to do with the way that damage estimates are entered), but I did not have time to address it. Note also that the "OTHER" category came in second place, suggesting once again that data quality issues plague this analysis.

Hurricanes, tropical storms, and storm surges were the most damaging on average. This suggests that, at least by this very crude measure of economic impact, these storms are more damaging when they happen, but their exclusion from the top of the cumulative damage rankings might suggest that they are much more infrequent.


###IV. Concluding Remarks

I apologize to you, my graders/ readers, for the sloppiness of this report. The amount of busy-work and unrealistic expectations of the project layout (as I understand it) severely hurt this report.  Like many of you taking this course, I am a working professional and my time is limited. I devoted 25 hours to this project, an enormous amount of time. If I were actually preparing a report for a government agency (like FEMA), I would surely have much more time to work through this dataset and would produce a far better report. With more time, I would have: 

- Performed more thorough investigation of poorly-coded events and more careful re-coding
- Investigated the time-series characteristics of storm damage
- Familiarized myself with all the variabels in the dataset, possibly leading to regression or factor analysis
- Investigated data quality over time and chosen a temporal subset to analyze (the description noted that early data are less reliable)
- Converted dollar-denominated figures to real dollars for more credible comparison of economic impacts
- Expanded the definition of "economic impact" to account for the price of insurance, home prices, and other economic variables 
- Created better, non-overlapping event type categories to unearth actionable insights

I invite you to share your comments/criticisms in the peer grading form or by contacting me via LinkedIN, and I look forward to your suggestions. But know, as you grade me, that this is far from my best work, and its quality is a direct result of the time constraints in my professional life.

###REFERENCES

In this type of project, novice users like me inevitably end up scouring Inside-R, Stack, CRAN, r-bloggers, and elsewhere in search of helpful hints and solutions to error messages. The links below are presented to give credit where credit is due. I hope that you find them as useful as I did.

[1] https://stat.ethz.ch/R-manual/R-devel/library/base/html/chartr.html

[2] http://www.statmethods.net/stats/frequencies.html

[3] http://stackoverflow.com/questions/19890633/r-produces-unsupported-url-scheme-error-when-getting-data-from-https-sites

[4] http://stackoverflow.com/questions/17185550/removing-certain-pattern-from-a-string

[4] https://stat.ethz.ch/R-manual/R-devel/library/base/html/regex.html

[6] http://www.inside-r.org/packages/cran/stringr/docs/str_split

[7] https://stat.ethz.ch/pipermail/r-help/2002-September/025359.html

