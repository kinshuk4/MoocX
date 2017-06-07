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

## subset down to just Baltimore 
data2 <- data2[data2$fips=="24510",]

## Pull in EI.Sector, SCC.Level.Two (for some interesting plots)
data3 <- merge(data2, t_lookup, by="SCC"); rm(data2); rm(t_lookup);

## subset out just the motor vehicle data

## loop through EI.Sector, get coal
indx1 <- grep("vehicle", data3$EI.Sector, ignore.case=TRUE)

## subset what we want
autodata <- data3[indx1,]

## aggregate the data by year, level three (for some good plots)
tots <- aggregate(Emissions ~ year * SCC.Level.Three, data=autodata, sum)

## Shorten/rationalize factor names (for cleaner graph)
tots$SCC.Level.Three <- as.character(tots$SCC.Level.Three)

indx <- grep("HDDV", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Hvy Duty Diesel Vehicles"
    rm(indx)

indx <- grep("LDGT", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Light Duty Gasoline Trucks"

indx <- grep("Heavy Duty Diesel Buses", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Hvy Duty Diesel Buses"

indx <- grep("LDDT", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Light Duty Diesel Vehicles"

indx <- grep("LDGV", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Light Duty Gasoline Vehicles"

indx <- grep("LDDV", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Light Duty Diesel Vehicles"

indx <- grep("HDGV", tots$SCC.Level.Three, ignore.case=TRUE)
    tots[indx,]$SCC.Level.Three <- "Hvy Duty Gasoline Vehicles"

## convert to year and Level Three to factors
tots$year <- as.factor(tots$year)
tots$type <- as.factor(tots$SCC.Level.Three)

## Convert Emissions to thousand-tons (for easier-to-read y axis)
tots$Emissions <- tots$Emissions/1000

## Open png device (480 x 480 px)
png(file="stacked_bar.png",width = 480, height = 480, units = "px") 

## stacked bar plot (so we can see level and composition changes over time)
g <- qplot(year, Emissions, data=tots, fill=SCC.Level.Three, geom="bar", 
           stat="identity",
           main = expression("Aggregate PM"[2.5]*" Emissions from Baltimore, MD Vehicles"),
           ylab = expression("Aggregate PM"[2.5]*" Emissions (thousand tons)"),
           xlab = "Observation Year") + theme(plot.title = element_text(hjust=0.2))
g

## Close the device
dev.off()
