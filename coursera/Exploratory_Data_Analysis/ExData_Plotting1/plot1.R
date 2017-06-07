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
    data1 <- read.table(filename, colClasses=c("character", "character", rep("numeric", 7)), header=TRUE, sep=";", na.strings = "?")
    data1$Date <- as.Date(data1$Date, "%d/%m/%Y")
    
    ## Subset out the data we need (Feb. 1 and 2)
    indx <- c(grep("^2007-02-01",data1$Date),grep("^2007-02-02",data1$Date))  #grep index, list of rows with those dates
    data1 <- data1[indx,]

    ## Open png device (480 x 480 px)
    png(file="plot1.png", width = 480, height = 480, units = "px") 
    
    ## Write the histogram to the device
    hist(data1$Global_active_power, main="Global Active Power",ylim=c(0,1200),xlab="Global Active Power (kilowatts)",col="red",breaks=12)
    
    ## Close the device
    dev.off()
