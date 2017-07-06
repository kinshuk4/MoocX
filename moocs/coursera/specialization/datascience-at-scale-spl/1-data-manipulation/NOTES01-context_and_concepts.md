#1. Examples of Data Science
## 2012 election
- state polls have predictive power
- getting the answer may be the easy part but there is difficulty in sharing and presenting results
- simple methods are usually best when analyzed with enough good data
   - this trumps more sophisticated methods
- Obama campaign's data driven ground game
  - use SQL database for analytics
    - Vertica - use for fast "Speed of thought" queries
  - Hadoop used as well for aggregated generations

## Hurricane Sandy
- twitter data used to follow power outages
- weather data pulled from the web used to show barometric pressure over time
   - illustrates the "Ad hoc" application of data science
   - wind speeds graphed as well

## Text data analysis
- Google has already broken all 20th century books in to a table of n-grams
- mood scores produced by wordnet
- researchers have combine these resources to produce mood analysis in literature
- analysis of a z-sore of (Joy - Sadness) showed a dip towards sadness after WWII
- emotion - random words showed a decrease in emotion over time while showing an increase of fear words
- Sourced from resources such as linguistics, anthropology, history are becoming hard sciences because of the ability of Data Science
   - all Science is becoming Data Science
- for large data sets (i.e. wiki leeks) users will want analysis to show trends

## determining importance
- page rank
   - method of rating importance by showing how many links there are to site
- analytics on graph of citation network
- show the joining of biology and neurology to form neuroscience
   - Bibliometrics

## other examples
- induce a graph on the ingredients of recipes
   - build and analyze this graph to find clusters
- last.FM set used to identify genres over time
   - repurposed data originally used for search
- Google trends finding flu outbreaks by looking at search words
   - far over estimated 2013 flu
   - the media attention on the flu skewed the results
   - biased data
- Microsoft research - side effects of drugs
   - results gained by monitoring web search traffic
   - correlated search of drugs and a unknown side effect
- 6 Italian seismologist convicted of manslaughter for failing to predict earthquake
  - when you make a prediction you are putting weight behind it

# Summary
- graph analytics
- databases
- visualization
- large datasets
- ad hock interactive analysis
- repurposing data

#2. Working Definitions of Data Science
##  What is Data Science?
  - Hal Varin, Google's Chief Economist
    - "The ability to take data-to be able to understand it to process it, to extract value from it, to visualize it, to communicate it- that's going to be a hugely important skill"
  - Mike Driscoll, CEO of metamarkets
    - "Data Science is the civil engineering of data. Its acolytes process a practical knowledge of tools & materials, coupled with a theoretical understanding of what's possible."
- Drew Conways - perhaps a mix of 3 skills
  - hacking skills
  - Math and Stats
  - Substantive Expertise - diving in to the data themselves as apposed to building tools for others
- What do data scientists do?
  - "find nuggets of truth and explain them to business leaders" - Richard Snee, EMC
  - "tend to hard scientists...come from a discipline in which survival depends on getting the most from the data" - DJ Patil, linkedin
- Mike Driscoll - 3 sexy skills
  - Stats
  - Data Mugging - parsing, scraping, formatting
  - Visualization
- Jeffery Stanton, Syracuse
  - "DS refers...collection, preparation, analysis, visualization, management and preservation of large collections of information"
- Hillary Mason, bit.ly
  - "DS is someone who can obtain, scrub, explore, model and interpret data, blending hacking, statistics and ML. DSs not only adept at working with data, but appreciate data itself as a first-class product"
  - being able to organize data for the use of others
  - data wrangling, data jujitsu
- 3 types of tasks
  - prepare to run model
    - gathering, cleaning, munging
  - running model
  - communication of the results
- DS is really about Data Products
  - Data-driven apps
    - spellchecker, translator
  - interactive visualizations
    - Google flu, Global Burden of Disease
      - not only a paper but an online product usable by others
  - Online Database
    - Enterprise Data Warehouse, Sloan Digital Sky Survey
- Distinguishing DS from...
  - Business Intelligence
    - Data warehouse, dashboards & reports
    - upfront effort to design and build
    - full stack, not ad hoc
    - not expected to consume own data products and make decisions themselves
  - Statistics
    - Statisticians is comfortable from the understanding the the dataset will fit in main memory
    - we are switching to a data rich regime
  - DBAs focus on relational model
    - DSs use visual data, graphs ect.
  - Visualization experts, less concerned with massive scale
  - ML
    - DSs spend more time on wrangling and less on choosing the right model
- "I worry that the DS role is like the mythical "webmaster" of the 90s: master of all trades."
  - Arron Kimbal, CTO Wibidata
- What DS tells me:
  - If your a DBA, you need to deal with unstructured data
  - if you are a statistician, you need to learn to deal with data that does not fit in memory
  - if...Soft Eng, you need to learn statistical modeling and how to communicate results
  - if...Business Analyst, you need to learn about algorithms and tradeoffs at scale
    - costs change dramatically based on your decisions
    - as we trust algorithms more and more we must understand them ourselves
- Cources offered
  - Breadth
    - **tools           ->        Abstractions**
    - Hadoop           ->        MapReduse
    - Postgre          ->        Relational Algebra
    - glm() in R       ->        Logistic Regression
    - Tableau          ->        InfoVis
  - Depth
    - **Structures       ->          Statistics**
    - Management       ->          Analysis
    - RelationalAlg    ->          Linear Algebra
    - Standards        ->          ad hoc files
  - Scale
    - **desktop          ->          cloud**
    - main memory      ->          distributed
    - R                ->          Hadoop
    - local files      ->          S3, Azure Storage
  - Target
    - **hackers              ->          analysts**
    - assume py, java, R   ->          assume little or no programming

#3 Charactesign this Course
## Tools vs. Abstractions
- What goes around comes around
  - pre 04 - RDBMS
  - 04 - Dean et al. MapReduce
  - 08 - Hadoop,
  - 08 - Pig & Dryad (competitors, Relational Alg. in Hadoop)
  - 09 - Hive, SQL on Hadoop
  - 09 - Hbase, indexing in Hadoop
  - 10 - Dietrich et al. Schemas and indexing in Hadoop
  - 12 - Transaction in HBase (VoltDB, NewSQL systems)
- Some permanent contributions:
  - Fault tolerance
  - Schema-on-read
    - not limiting the applications to a pre-defined schema
  - User-defined functions that don't suck
    - putting full Java code at the database level
- focusing too much on tools will show a snapshot in time but you may miss the larger abstractions that will be more important over time

## Desktop Scale vs. Cloud Scale
- We might not really know the abstractions of data Science
  - Data Jujitsu, Data Wrangling, Data Munging
    - We have no idea what we are takling about
  - Candidates
    1. Matrices and lin Alg.?
    2. relations and relational algebra?
    3. objects and methods?
    4. files and scripts?
    5. data frames and functions?
  - 1 and 2 are what we should be the most focused on
    - focused on the types of tasks that we do with data
- Desktop scale vs. Cloud scale
  - can't just focused on functions for desktop processing
  - you must be aware of what other systems can do and write applications in terms of other systems that can scale out
  - GREP does not scale
- large scale data is not just bigger it is different

## Hackers vs. Analysts
- "US faces shortage of 140k to 190k people "with deep analytical skills" as well as 1.5M managers and analysts who know-how to use the analysis of big data to make effective decisions."
    - Mckinsey Global Institute
- Line between Hackers and Analysts is more blurry now
- many queries written by non-programmers

## Structs vs Stats
- "80% of analytics is sums and averages"
    - Aaron Kimball, wibidata
- types of tasks
  - preparing to run model
    - 80% of the work
  - Choosing/Running model
  - Interpreting the results
    - the other 80% of the work
- "...no greater barrier to effective data management will exist that the variety of incompatible data formats, non-aligned data structures, and inconsistent data semantics."
    - Doug Laney, 3-D Data Management: Controlling Data Volume, Velocity and Variety
- Problem: how much time do you spend "handling data" as apposed to "doing science"?
    - Mode answer of researchers: 90%
- Increasing amount of interest in figuring how to get data into the database
- Databases and Stat packages
  - analysts download data for use in there stat package/prog language
  - many of these just load all of the data into main memory, limiting the amount that can be processed
    - many turn to sampling to resolve this
  - if the data can be queried from a database on disk you get the scaling for free
  - many databases can take use of parallelism which the stat packages may not
- Sparse Matrix Multiply in SQL
  - representing matrices in DBs sounds odd but can be a good idea and matrix operations can be performed
  - Christian Grant, MADSkills


#4. Related Topics
## A Forth Paradigm of Science
- "eScience" = "Data Science
- Science has moved from empirical observations to theoretical models to theoretical computation
- in the last 10 years - a new field of inquiry, creating and mining huge datasets
- Empirical, Theoretical, Computational eScience
- Science is about asking questions
	- traditionally "Query the world"
	- eScience: "Download the world"
- The cost of finding, integrating and analyzing data, then communicating results, is the new bottleneck

## Data-Intensive Science Examples
- Large Synoptic Survey telescope
  - returning to the same area of the sky allowing for comparison over time
- In life sciences high throughput sequences can produce 1TB/day
- Ocenography: NSF ocean observations initiative
- The web: 20+ billion web pages
- eScience is about the analysis of data
  - automated or semi-automated extraction of knowledge from massive volumes of data
  - not just a matter of volume
- 3 V's
  - volume: rows/bytes
  - variety: columns/dimensions/sources
  - Velocity: number of rows/bytes per unit of time
- Another  V:
  - Veracity: can we trust the data
- Summary
  - Science is in the midst of a generational shift form data-poor to data-rich enterprise
  - analysis has replaced data acquisition
  - Business is beginning to look a lot like science
    - acquiring and keeping data
    - making empirical decisions

## Big Data and the 3Vs
- Volume, Velocity, Variety
- Velocity: latency of data processing relative to the growing demand for interactivity
- Variety: diversity of sources, formats, quality, structures
- Big Data
  - "Big Data is any data that tis expensive to manage and hard to extract value from"
    - Michael Franklin, Berkeley
  - temptation to throw up a large number - while this might in fact actually be manageable
- History of the term "Big Data"
  - "The keepers of big data say they do it for the consumer's benefit. But data have a way of being used for purposes other that originally intended."
    - Erik Larson, Harper's mag, 1989
    - key point: data used in ways other that originally intended
    - Larson was speaking about the monitoring of consumer data
- 3V's attributed to Doug Lanely, Gartner
  - On Volume: lower cost of e-channels let to up to 10x the quantity of data about an individual transaction
  - On Velocity: increased point of interaction speed
  - On Variety: no greater barrier to effective data management than incompatible formats, non-aligned structures and inconsistent semantics

## Big Data Sources
- "big Data...and the next wave for infraStress" - John Mashey
- IO interface is not keeping up with the need to access the data quickly
- "...the necessity of grappling with Big Data... is now a key theme in all sciences - arguably the key scientific theme of out times."
    - Francis Diebold
- Where does big data come from
  - "data exhaust" from customers - tracking every point that can be recoded
  - New and pervasive sensors
  - ability to "keep everything" due to increased storage resources
- Sensors
  - "black boxes" for cars
  - used for crashes and for insurance
  - HydroSense/ElectriSense - monitor consummation of home reassures
    - monitors use of water by recording the recognizable pressure change from the main line
    - similar process for monitoring electrical usage
    - 1 sensor can detect all the usage points
