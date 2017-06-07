    ## Download The Data
    url <- "https://d396qusza40orc.cloudfront.net/exdata%2Fdata%2Fhousehold_power_consumption.zip"
    file <- ".\\EDA_projectdata.zip"
    if(file.exists(file)==FALSE) {
        download.file(url, destfile = file)
        unzip(file)
    }

    ## Package Loading
    library(R.utils)
    library(lubridate)

    ## Read in the Data
    filename <- "household_power_consumption.txt"
    data2 <- read.table(filename, colClasses=c("character", "character", rep("numeric", 7)), header=TRUE, sep=";", na.strings = "?")
    data2$Date <- as.Date(data2$Date, "%d/%m/%Y")

    ## Subset out the data we need (Feb. 1 and 2)
    indx <- c(grep("^2007-02-01",data2$Date),grep("^2007-02-02",data2$Date))  #grep index, list of rows with those dates
    data2 <- data2[indx,]

    ## Format dates as dates (using colClasses wouldn't allow you to specify format)
    data2$Date <- as.Date(data2$Date, format="%m/%d/%Y")
    data2$Time <- strptime(paste(data2$Date, data2$Time, sep=" "), format="%Y-%m-%d %H:%M:%S")
    
    ## Add a Factor Variable for Weekday
    data2$weekday <- wday(data2$Time, label=TRUE, abbr = TRUE)
    
    ## Open png device (480 x 480 px)
    png(file="plot2.png",width = 480, height = 480, units = "px") 
    
    ## Write the line graph to the device
    plot(data2$Time, data2$Global_active_power, type="l", ylab = "Global Active Power (kilowatts)", xlab="")
    
    ## Close the device
    dev.off()