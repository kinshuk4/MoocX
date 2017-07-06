if(!require('plyr')){ install.packages('plyr')}
require('plyr', quietly = TRUE)
pkgs <- c('rCharts', 'DT', 'xtable', 'dplyr')
#'@ options(RCHART_TEMPLATE = 'Rickshaw.html', RCHART_LIB = 'morris')

sht <- c('Key Stats', 'Income Statement', 'Balance Sheet', 'Cash Flow')
NKE <- llply(sht, function(i) {
    read_excel(paste0('4 Financial Evaluation and Strategy - Corporate Finance/data/NIKE Inc NYSE NKE Financials.xls'), sheet = i)})
VFC <- llply(sht, function(i) {
    read_excel(paste0('4 Financial Evaluation and Strategy - Corporate Finance/data/V F Corporation NYSE VFC Financials.xls'), sheet = i)})
names(NKE) <- names(VFC) <- sht
rm(sht)

## Define server logic required to draw a histogram
shinyServer(function(input, output) {
  ## Define a reactive expression for the document term matrix
  terms <- reactive({
    ## Change when the "update" button is pressed...
    input$update
    ## ...but not for anything else
    isolate({
      withProgress({
        setProgress(message = "Processing corpus...")
        getTermMatrix(input$selection)
      })
    })
  })
  
  ## Make the wordcloud drawing predictable during a session
  wordcloud_rep <- repeatable(wordcloud)
  
  output$txte <- renderText({
    paste0("<a href='", terms()$lnk, "'>", input$selection, "</a>")
  })
  
  output$article <- renderText({
    terms()$txt
  })
  
  output$wordplot <- renderPlot({
    mat <- terms()$mat

  })
  
  output$table <- renderDataTable({
    dat <- terms()$dat
    })
  
  ## make the rickshaw rChart
  ## http://rcharts.io/gallery/
  ## http://timelyportfolio.github.io/rCharts_rickshaw_gettingstarted/
  output$histplot <- renderChart2({
    #'@ chart <- Rickshaw$new()
    dat <- terms()$dat
    dat <- filter(dat[seq(ifelse(input$max>nrow(dat),nrow(dat),input$max)),], 
                  Docs>=ifelse(input$freq>max(dat$Docs),max(dat$Docs),input$freq))
    ## http://stackoverflow.com/questions/26789478/rcharts-and-shiny-plot-does-not-show-up
    nPlot(Docs ~ Term, data = dat, type = 'multiBarChart')
    #'@ chart$nPlot(Docs ~ Term, data = dat, type = 'multiBarChart')
    #'@ chart$set(width = 600, height = '100%', slider = TRUE)
    #'@ return(chart)
  })
  
})

