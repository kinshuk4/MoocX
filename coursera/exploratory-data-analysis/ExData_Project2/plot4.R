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

## Pull in EI.Sector, SCC.Level.Two (for some interesting plots)
data3 <- merge(data2, t_lookup, by="SCC"); rm(data2); rm(t_lookup);

## subset out just the coal

    ## loop through EI.Sector, get coal
    indx1 <- grep("coal", data3$EI.Sector, ignore.case=TRUE)

    ## subset what we want
    coaldata <- data3[indx1,]

    ## loop through EI.Sector, get coal
    indx1 <- grep("combustion", data3$SCC.Level.Three, ignore.case=TRUE)
    
    ## subset what we want
    coaldatadata <- data3[indx1,]

## aggregate the data by year, level three (for some good plots)
tots <- aggregate(Emissions ~ year * SCC.Level.Three, data=coaldata, sum)

# ## Shorten/rationalize factor names (for cleaner graph)
  tots$SCC.Level.Three <- as.character(tots$SCC.Level.Three)
  indx <- grep("bituminous", tots$SCC.Level.Three, ignore.case=TRUE)
      tots[indx,]$SCC.Level.Three <- "Bituminous"
      rm(indx)

 indx <- grep("commercial", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Commercial/Inst."

## convert to year and Level Three to factors
tots$year <- as.factor(tots$year)
tots$type <- as.factor(tots$SCC.Level.Three)

## Convert Emissions to thousand-tons (for easier-to-read y axis)
tots$Emissions <- tots$Emissions/1000

## Open png device (480 x 480 px)
png(file="plot4.png",width = 480, height = 480, units = "px") 

## stacked bar plot (so we can see level and composition changes over time)
g <- qplot(year, Emissions, data=tots, fill=SCC.Level.Three, geom="bar", 
           stat="identity",
           main = expression("Plot 4. Aggregate PM"[2.5]*" From U.S. Coal Sources"),
           ylab = expression("Aggregate PM"[2.5]*" Emissions (thousand tons)"),
           xlab = "Observation Year")
g

## Close the device
dev.off()
