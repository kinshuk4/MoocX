pkgs <- c('shiny', 'DT', 'rCharts')

# Define UI for application that draws a histogram
ui <- shinyUI(
  fluidPage(
    # Application title
    titlePanel('Natural Language Analysis',
               #tags$li(class = 'dropdown',
               tags$a(href='http://rpubs.com/englianhu/ryoeng', target='_blank', 
                      tags$img(height = '20px', alt='Ryo Eng', #align='right', 
                               src='https://avatars0.githubusercontent.com/u/7227582?v=3&s=460')
                      #        )
               )),
    
    # Sidebar with a slider input for number of bins
    sidebarLayout(
      sidebarPanel(
        selectInput('selection', 'Choose a stock:',
                    choices = stocks),
        actionButton('update', 'Change')
      ),
      
      # Show a plot of the generated distribution
      mainPanel(
        tabsetPanel(
          tabPanel('Article', htmlOutput('txte'), verbatimTextOutput('article')),
          tabPanel('WordCloud', plotOutput('wordplot')), 
          tabPanel('Table', DT::dataTableOutput('table')), 
          tabPanel('Histogram', 
                   #div(id = 'chart', style = 'display:inline;position:absolute', 
                   showOutput('histplot', 'nvd3')#)
          ), 
          tabPanel('Reference', 
                   h4('Reference:'),
                   p('1. ', HTML("<a href='http://shiny.rstudio.com/articles/'>Articles</a>")),
                   p('2. ', HTML("<a href='http://shiny.rstudio.com/reference/shiny/latest/'>Function reference version 0.13.0</a>")),
                   p('3. ', HTML("<a href='http://shiny.rstudio.com/gallery/word-cloud.html'>Word Cloud</a>"),
                     tags$a(href='http://rpubs.com/englianhu/ryoeng', target='_blank', 
                            tags$img(height = '20px', alt='hot', #align='right', 
                                     src='http://www.clipartbest.com/cliparts/niB/z9r/niBz9roiA.jpeg'))),
                   br(),
                   h4('Here are few samples where you can write yours :'),
                   p('1. ', HTML("<a href='http://rstudio-pubs-static.s3.amazonaws.com/162182_1e9e92f369c446b19eb34993e05c6b3a.html'>Data Science Capstone Milestone Report Quiz 1</a>"),
                     tags$a(href='http://rpubs.com/englianhu/ryoeng', target='_blank', 
                            tags$img(height = '20px', alt='hot', #align='right', 
                                     src='http://www.clipartbest.com/cliparts/niB/z9r/niBz9roiA.jpeg'))),
                   p('2. ', HTML("<a href='https://rstudio-pubs-static.s3.amazonaws.com/41478_296489dad2da4720ab6abcae7750be4f.html'>Milestone Report - Exploratory analysis</a>")),
                   p('3. ', HTML("<a href='http://rpubs.com/dxrodri/SwiftKeyMilestoneReport'>Capstone Swiftykey Project - Milestone Report</a>"),
                     tags$a(href='http://rpubs.com/englianhu/ryoeng', target='_blank', 
                            tags$img(height = '20px', alt='hot', #align='right', 
                                     src='http://www.clipartbest.com/cliparts/niB/z9r/niBz9roiA.jpeg'))),
                   p('4. ', HTML("<a href='https://github.com/filipelenfers/DataScienceCapstoneProject'>Coursera Data Science Specialization Captstone Project</a>")),
                   p('5. ', HTML("<a href='https://rstudio-pubs-static.s3.amazonaws.com/95039_83c56b22662b442d9a2037ded0c3b2c6.html'>Data Science Capstone Project: Data Analysis Report</a>")),
                   p('6. ', HTML("<a href='https://rstudio-pubs-static.s3.amazonaws.com/42046_1145eaaa455840b7a2a0d8a74146dd3a.html'>Natural Language Processing - Text Mining and Text Prediction: Exploratory Analysis on Swiftkey Datasets</a>")),
                   p('7. ', HTML("<a href='https://github.com/englianhu/DSCapstone'>Coursera/Johns Hopkins Data Science Cert Capstone</a>"),
                     tags$a(href='http://rpubs.com/englianhu/ryoeng', target='_blank', 
                            tags$img(height = '20px', alt='hot', #align='right', 
                                     src='http://www.clipartbest.com/cliparts/niB/z9r/niBz9roiA.jpeg'))),
                   p('8. ', HTML("<a href='https://github.com/englianhu/datasciencecoursera-1'>datasciencecoursera-1</a>")),
                   p('9. ', HTML("<a href='https://github.com/englianhu/courses'>DataScienceSpecialization/courses</a>"))
          )
        )
      )
    )
  ))

