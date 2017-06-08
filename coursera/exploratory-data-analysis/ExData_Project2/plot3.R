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
    
    ## Sum total emissions by year and type
    tots <- aggregate(Emissions ~ year * type, data=data2[data2$fips=="24510",], sum)
    tots$year <- as.factor(tots$year)
    tots$type <- as.factor(tots$type)
    
    ## Convert Emissions to thousand-tons (for easier-to-read y axis)
    tots$Emissions <- tots$Emissions/1000
    
    ## Open png device (480 x 480 px)
    png(file="facets_bar.png",width = 480, height = 480, units = "px") 
    
    ## Load ggplot2
    library(ggplot2)
    
    ## Initialize the plot
    g <- qplot(year, Emissions, data=tots, geom="bar", stat="identity", 
               facets = type ~., main = expression("Aggregate PM"[2.5]*" Emissions in Baltimore, MD"),
               ylab = expression("Aggregate PM"[2.5]*" Emissions (thousand tons)"), xlab = "Observation Year", fill=type)
    
    ## print plot
    g
    
    ## Close the device
    dev.off()
    
    ## References
    # [1] https://stat.ethz.ch/pipermail/r-help/2008-June/163851.html
    # [2] http://docs.ggplot2.org/0.9.3.1/geom_bar.html