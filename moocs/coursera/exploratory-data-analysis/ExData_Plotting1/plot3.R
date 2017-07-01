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
    data3 <- read.table(filename, colClasses=c("character", "character", rep("numeric", 7)), header=TRUE, sep=";", na.strings = "?")
    data3$Date <- as.Date(data3$Date, "%d/%m/%Y")
    
    ## Subset out the data we need (Feb. 1 and 2)
    indx <- c(grep("^2007-02-01",data3$Date),grep("^2007-02-02",data3$Date))  #grep index, list of rows with those dates
    data3 <- data3[indx,]
    
    ## Format dates as dates (using colClasses wouldn't allow you to specify format)
    data3$Date <- as.Date(data3$Date, format="%m/%d/%Y")
    data3$Time <- strptime(paste(data3$Date, data3$Time, sep=" "), format="%Y-%m-%d %H:%M:%S")
    
    ## Add a Factor Variable for Weekday
    data3$weekday <- wday(data3$Time, label=TRUE, abbr = TRUE)
    
    ## Open png device (480 x 480 px)
    png(file="plot3.png", width = 480, height = 480, units = "px") 
    
    ## Write the graphs to the device
    plot(data3$Time, data3$Sub_metering_1, type="l", col="black", lty=1, xlab="", ylab="Energy sub metering")
    lines(data3$Time, data3$Sub_metering_2, type="l", col="red", lty=1)
    lines(data3$Time, data3$Sub_metering_3, type="l", col="blue", lty=1)
    
        ## Add a legend
        legend(x="topright", legend=c("Sub_metering_1", "Sub_metering_2", "Sub_metering_3"), 
                col = c("black","red","blue"), lty=c(1,1,1))
    
    ## Close the device
    dev.off()
    
    ## References
    # [1] http://www.statmethods.net/graphs/line.html
    # [2] http://rfunction.com/archives/812
    