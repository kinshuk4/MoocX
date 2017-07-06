    ## Download The Data
    url <- "https://d396qusza40orc.cloudfront.net/exdata%2Fdata%2Fhousehold_power_consumption.zip"
    file <- ".\\EDA_projectdata.zip"
    if(file.exists(file)==FALSE) {
        download.file(url, destfile = file)
        unzip(file)
    }
    
    ## Package Loading
    library(R.utils)
    
    ## Read in the Data
    filename <- "household_power_consumption.txt"
    data4 <- read.table(filename, colClasses=c("character", "character", rep("numeric", 7)), 
                        header=TRUE, sep=";", na.strings = "?")
    data4$Date <- as.Date(data4$Date, "%d/%m/%Y")
    
    ## Subset out the data we need (Feb. 1 and 2)
    indx <- c(grep("^2007-02-01",data4$Date),grep("^2007-02-02",data4$Date))  #grep index, list of rows with those dates
    data4 <- data4[indx,]
    
    ## Format dates as dates (using colClasses wouldn't allow you to specify format)
    data4$Date <- as.Date(data4$Date, format="%m/%d/%Y")
    data4$Time <- strptime(paste(data4$Date, data4$Time, sep=" "), format="%Y-%m-%d %H:%M:%S")
    
    ## Add a Factor Variable for Weekday
    data4$weekday <- wday(data4$Time, label=TRUE, abbr = TRUE)
    
    ## Open png device (480 x 480 px)
    png(file="plot4.png", width = 480, height = 480, units = "px") 
    
    ## Set the graphical parameters to get a 2x2 panel
    par(mfrow=c(2,2))
    
    ## First Plot (top left)
    plot(data4$Time, data4$Global_active_power, type="l", ylab = "Global Active Power", xlab="")
    
    ## Second Plot (top right)
    plot(data4$Time, data4$Voltage, type="l", ylab = "Voltage", xlab="datetime")
    
    ## Third Plot (bottom left)
    plot(data4$Time, data4$Sub_metering_1, type="l", col="black", lty=1, xlab="", ylab="Energy sub metering")
        lines(data4$Time, data4$Sub_metering_2, type="l", col="red", lty=1)
        lines(data4$Time, data4$Sub_metering_3, type="l", col="blue", lty=1)
        legend(x="topright", legend=c("Sub_metering_1", "Sub_metering_2", "Sub_metering_3"), 
           col = c("black","red","blue"), lty=c(1,1,1), bty="n")
    
    ## Fourth Plot (bottom right)
    plot(data4$Time, data4$Global_reactive_power, type="l", ylab="Global_reactive_power", xlab="datetime")
    
    ## Close the device
    dev.off()
    
    ## References
    # [1] http://www.statmethods.net/advgraphs/layout.html