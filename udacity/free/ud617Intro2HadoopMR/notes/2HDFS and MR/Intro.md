### Concept
- Files are stored in something called the Hadoop Distributed File System.  
- As a developer, this looks very much like a regular file system.  

 

### Behind the Scenes 

 
- Imagine we're going to store a file called mydata.txt, in HDFS. 
- This file is 150 megabytes. When a file is loaded into HDFS, it's split into chunks which we call blocks.  
- Each block is pretty big. The default is 64 megabytes. 
-  Each block is given a unique name, which is blk, an underscore, and a large number. (say,  blk_22)  
- We'll call ours block one, two, and three. And in our case the first block is 64 megabytes. The second block is 64 megabytes. The third block is the remaining 22 megabytes 
- As the file is uploaded to HDFS, each block will get stored on one node in the cluster. There's a Damon, or piece of software, running on each of the machines in the cluster, called the DataNode.  
- Now clearly we need to know which blocks make up the original file. And that's handled by a separate machine, running the Damon called the NameNode.  
- The information stored on the NameNode is known as the Metadata.  
- That's fine as far as it goes, but there are some problems with this.  

 

### Problems 
- network failure between the nodes,  
- or a disk failure on a DataNode.  
- or a disk failure on the NameNode