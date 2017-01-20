
- To solve problems of diskfailure, Hadoop replicates each block three times, as it's stored in HDFS.   
- Hadoop just picks three nodes at random and puts one copy of the block on each of the three.  
- Well actually, it's not totally random, but that's close enough for us right now. 

 

### If a node fails 
-  So now if a single node fails, it's not a problem because we have two other copies of the block on other nodes. 
- And the NameNode is smart enough to see that these blocks are now under-replicated and it will arrange to have those block re-replicated on the cluster.  

 

### Another Problem 
-  But there's another obvious single point of failure here. What happens if the NameNode has a hardware problem? 


### NameNode Standby 
- to avoid the problem, people would configure the Name Node to store meta data, not only on it's own hard drive but also somewhere on a network file system.  
- NFS is a method of mounting a remote disk. That way, even if the Name Node bursts into flames, there would be a copy of the metadata elsewhere on the network.  
- These days, there's an even better alternative. The Name Node is not a single point of failure in most production clusters, because they've configured two Name Nodes.  
- The active Name Node works as before, but the standby can be configured to take over if the active one fails.   