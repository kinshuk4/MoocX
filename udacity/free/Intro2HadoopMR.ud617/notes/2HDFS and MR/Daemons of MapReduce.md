    When you run a MapReduce job, you submit the job to what's called the Job Tracker.  

    That splits the work into mappers and reducers. Those mappers and reducers will run on the other cluster nodes.  Running the actual map and reduce tasks is handled by a daemon called the TaskTracker.  

    The TaskTracker software will run on each of these nodes. Note that since the TaskTracker runs on the same machine as the data nodes, the Hadoop framework will be able to have the map tasks work directly on the pieces of data that are stored on that machine. This will save a lot of network traffic.  

    As we saw, each Mapper processes a portion of the input data. That's known as the input split. And by default, Hadoop use an HDFS block as the input split for each Mapper. It will try to make sure that a Mapper works on data on the same machine.  

    In some cases if a hdfs block needs processing and all the 3 data nodes that holds it are busy. The the TaskTracker will stream data over the network to a free machine. This happens rarely! 

    So the Mappers will read their input data. They'll produce intermediate data, which the Hadoop framework will pass to the reducers, remember that's the shuffle and sort. Then the reducers process that data and write their final output back to HDFS. 

 

 

 

 

 

 

See more in the free (Chapter 6 of Tom White’s essential text, Hadoop: The Definitive Guide)[http://go.cloudera.com/udacity-lesson-2]