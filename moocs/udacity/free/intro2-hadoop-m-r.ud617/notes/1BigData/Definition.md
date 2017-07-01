Traditional SQL systems struggle for BIG DATA like "all orders across 100 stores nationwide", this is where hadoop comes into play! 

#### What is BIGDATA? 

A reasonable definition is "data that doesn’t fit on a single computer" 

#### Challenges
- Data is created Fast 
- Data is coming from various sources 

#### 3V's
Volume, Variety and Velocity

##### Volume
- SIZE of data you're dealing with 
- Price of data has dropped significantly recent years 
- 1980 - $100,00/GB 
- 2013 - $0.10/GB 
- But reliable storage such as SAN-storage are networks are expensive(traditional) 
- High cost of this puts a cap on the amount of data that companies can actually store. 
- So they usually do like this, 
- A grocery stores only SALES and throws LOGS. 
- Turns out that LOGS are incredibly useful 
- We need a cheaper way of storing data and  
- A way to read and process it efficiently 
- Storing TB of data across SAN is not that hard 
- But streaming the data from SAN across the network to a say some central processor, can take a long time and processing can be extremely slow 
- Hadoop stores data in a distributed way across multiple machines 

##### Variety
- Data often comes from lot of different sources and formats 
- The problem with using SQL DBs is that data need to fit into predefined tables. 
- A lot of data these days are unstructured or semi-structured data. 
- Example, you store customer calls as, 
    - TEXT or 
    - MP3 (so that future you can interpret the tone of the customer) 
- In Hadoop, 
      - Store in raw format 
      - Manipulate/Reformat it later  

##### Velocity
- SPEED at which data is generated 
- And the SPEED in which it is to be made available for processing 
- We need to be able to store and accept data, even if it is coming at the rate of 1TB/Day 
- In a ecommerce website, (Amazon) 
      - If you are using old ipad, suggest new one 
      - Next visit, recommend products based on last visit 
      - If u r looking at a product for 5 mins, send email notifs for sale 

#### Creator Interview
https://www.udacity.com/course/viewer#!/c-ud617/l-306818608/m-312934728 

**Doug Cutting**, Creator of Hadoop 

Here are the papers Google published about their distributed file system (GFS)[http://static.googleusercontent.com/media/research.google.com/en/us/archive/gfs-sosp2003.pdf] and their processing framework, (MapReduce)[http://static.googleusercontent.com/media/research.google.com/en/us/archive/mapreduce-osdi04.pdf]. 


>"Its grown into a general purpose platform for data-processing
- That scales much better
- And much more flexible than anything else out there"

#### Core Hadoop
The core Hadoop project consists of, 
 a way to store data, known as the Hadoop distributed file system, or HDFS.  
And a way to process data with MapReduce.  

**Key concept**
we split the data up and store it across the collection of machines known as a cluster.  
Then when we want to process the data, we process it where it's actually stored. 

 
Rather than retrieving the data from a central server, the data's already on the cluster, so we can process it in place. 

 
You can add more machines to the cluster as the amount of data you're storing grows. 

 
The machines in your cluster don't need to be anything particularly high end. Although most clusters are built using rack-mounted servers, they are typically mid-range servers, rather than top of the range equipment. 

 

 Cloudera provides free download of (Chapter 2 of Tom White’s essential text, Hadoop: The Definitive Guide)[http://go.cloudera.com/udacity-lesson-1].

#### Challenges

