## Download The Data
url <- "https://d396qusza40orc.cloudfront.net/exdata%2Fdata%2FNEI_data.zip"
file <- ".\\EDA_projectdata.zip"
if(file.exists(file)==FALSE) {
    download.file(url, destfile = file)
    unzip(file)
}

## Read in the Data
filename1 <- ".\\summarySCC_PM25.rds"
data2 <- readRDS(file=filename1)
filename2 <- ".\\Source_Classification_Code.rds"
t_lookup <- readRDS(file=filename2)

## subset down to just Baltimore and Los Angeles
data2 <- data2[(data2$fips=="24510" | data2$fips=="06037"),]

## Pull in EI.Sector, SCC.Level.Two (for some interesting plots)
data3 <- merge(data2, t_lookup, by="SCC"); rm(data2); rm(t_lookup);

## subset out just the motor vehicle data

## loop through EI.Sector, get motor vehicle data
indx1 <- grep("vehicle", data3$EI.Sector, ignore.case=TRUE)

## subset what we want
autodata <- data3[indx1,]

## aggregate the data by year, level three (for some good plots)
tots <- aggregate(Emissions ~ year * fips, data=autodata, sum)

## Change FIPS to more informative values
indx <- grep("06037", tots$fips)
    tots[indx,]$fips <- "Los Angeles County"

indx <- grep("24510", tots$fips)
    tots[indx,]$fips <- "Baltimore City"

## convert to year and Level Three to factors
tots$year <- as.factor(tots$year)
#tots$type <- as.factor(tots$SCC.Level.Three)
tots$fips <- as.factor(tots$fips)

## Convert Emissions to thousand-tons (for easier-to-read y axis)
tots$Emissions <- tots$Emissions/1000

## Open png device (720 x 720 px)
png(file="plot6.png",width = 720, height = 480, units = "px") 

## stacked bar plot (so we can see level and composition changes over time)
g1 <- qplot(year, Emissions, data=tots, fill=fips, geom="bar",
            stat="identity",
            main = expression("Plot 6a. PM"[2.5]*" Emissions, Motor Vehicles"),
            ylab = expression("Aggregate PM"[2.5]*" Emissions (thousand tons)")) + 
            theme(plot.title = element_text(hjust=0.2)) + theme(legend.position = "bottom")

g2 <- qplot(year, Emissions, data=tots, fill=fips, geom="bar",
           stat="identity", position = "fill",
           main = expression("Plot 6b. Shares of PM"[2.5]*", Motor Vehicles"),
           ylab = expression("Share of Combined PM"[2.5]*" Emissions")) + 
            theme(plot.title = element_text(hjust=-0.3)) + theme(legend.position = "bottom")
            
## put them side-by-side
library(gridExtra)
grid.arrange(g1,g2,ncol=2)

## Close the device
dev.off()

# [1] http://zevross.com/blog/2014/08/04/beautiful-plotting-in-r-a-ggplot2-cheatsheet-3/
# [2] http://stackoverflow.com/questions/4811316/quick-help-creating-a-stacked-bar-chart-ggplot2
# [3] http://stackoverflow.com/questions/1249548/side-by-side-plots-with-ggplot2-in-r