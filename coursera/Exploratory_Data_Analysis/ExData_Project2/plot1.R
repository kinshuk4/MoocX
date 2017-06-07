    ## Download The Data
    url <- "https://d396qusza40orc.cloudfront.net/exdata%2Fdata%2FNEI_data.zip"
    file <- ".\\EDA_projectdata.zip"
    if(file.exists(file)==FALSE) {
        download.file(url, destfile = file)
        unzip(file)
    }
    
    ## Read in the Data
    filename1 <- ".\\summarySCC_PM25.rds"
        data1 <- readRDS(file=filename1)
    filename2 <- ".\\Source_Classification_Code.rds"
        t_lookup <- readRDS(file=filename2)
    
    ## Sum total emissions by year
    tots <- aggregate(Emissions ~ year, data=data1, sum)
    tots$year <- as.factor(tots$year)
    
    ## Convert Emissions to thousand-tons (for easier-to-read y axis)
    tots$Emissions <- tots$Emissions/1000
    
    ## Open png device (480 x 480 px)
    png(file="plot1.png",width = 480, height = 480, units = "px") 
    
    ## Write the line graph to the device
    barplot(height=tots$Emissions, col = "skyblue2", names.arg=as.character(tots$year),
            main = expression("Plot 1. U.S. Aggregate PM"[2.5]*" Emissions"), 
            ylab = expression("Aggregate PM"[2.5]*" Emissions (thousand tons)"), xlab = "Observation Year")
    
    ## Close the device
    dev.off()
    
    ## References
    # [1] https://stat.ethz.ch/pipermail/r-help/2008-June/163851.html