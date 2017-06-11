## AWS S3 VS Glacier
I am trying to compare S3 vs Glacier. Here are the findings:

| Parameter  | S3  | Glacier  |
|---|---|---|
| File Unit  |  Objects (The abstraction away from the lower layers of storage, and the separation of data from its metadata come with a number of benefits. For one, Amazon can provide a highly durable storage service for the fraction of the cost of block storage. You also only pay for the amount of storage you actually use. Therefore you don’t need to second-guess and pre-allocate disk space.)|  Archives |
| Life Cycle  |  Lifecycle rules within S3 allow you to manage the life cycle of the objects stored on S3. After a set period of time you can either have your objects automatically delete or archived off to Amazon Glacier.Details: [Lifecycle](http://cloudacademy.com/blog/wp-content/uploads/2015/02/AWSS3LifeCycle.jpg) | The cost per Terrabyte of storage and month is again only a fraction of the cost of S3. Amazon Glacier is pretty much designed as a write once and retrieve never (or rather rarely) service. This is reflected in the pricing, where extensive restores come at a additional cost and the restore of objects require lead times of up to 5 hours.  |
|  Metadata Size | 8kb per object  |  32kb per archive. This is important to keep in mind for your backup strategy, particularly if you are storing a large number of small files. If those files are unlikely to require restoring in the short term it may be more cost effective to combine them into an archive and store them directly within Amazon Glazier. |   
| Tools | Cloudberry, Cloudspaces | glacier-cmd, |
| Pricing | Pricing - [here](http://aws.amazon.com/s3/pricing/), Specifics of the API functions available are [here](http://aws.amazon.com/s3/pricing/).| Check glacier pricing [here](https://aws.amazon.com/glacier/pricing/)| 
|Example Pricing | Given below | Given Below|
| Retrieval Speed | Faster | Slow |


### S3 Pricing Example
For S3, you are mostly charged for upload bandwidth (bytes sent TO S3), download bandwidth (bytes received FROM S3), and storage (bytes IN S3). You are also charged for the number and type of API calls.

So, if you upload your 10GB of data to S3 in 10,000 1MB files, store it for a month, and then download each of the files once, you'll be charged:

    $0.00 for upload bandwidth (this is free)
    $0.10 for the 10,000 PUT requests to upload the files
    $0.95 for storing the 10GB for a month
    $1.08 for 10GB download bandwidth (the first is free, then $0.12/GB)
    $0.01 for the 10,000 GET requests to download the files

That's $2.14. If you uploaded and downloaded once each, but kept the data for a year, only the storage cost would go up to 12 * $0.95, or $11.40. If your files averaged only 100KB, so you had 100,000 of them, you'd pay 10 times as much for the PUT and GET requests, or $1.10 instead of $0.11.

You can only upload and download a single file per operation. If you combined your files into one using Zip, you'd only save by using fewer operations, which, as you can see, are pretty cheap to start with.

There is one quirk here, though. I'm pretty sure you are charged for all bandwidth usage when uploading and downloading, including request headers, not just the bodies containing your data. So if your files were really tiny the request headers might become significant, perhaps as much as the files themselves. In that case your bandwidth costs would double.

### Glacier Pricing Example
The Smart Data Retrieval with [FastGlacier webpage](http://fastglacier.com/amazon-glacier-smart-retrieval.aspx) shows an estimated cost of restoring 43.9GB of data from Glacier to be [$89.74](https://amazon-glacier.s3.amazonaws.com/smart-restore/data-retrieval-confirmation.png). 


## Conclusion
Glacier pricing is more complicated, and I've never used it myself. Basically, it reduces storage cost by almost ten-fold, leaving the other costs the same, and adding costs to archive and restore per object. Those costs seem to be significant if you have a lot of small objects, need to get a lot of your files at a time, or get files frequently. Glacier seems to be best when you have a lot of data (terabytes or more, not just gigabytes), but few operations. Given that you only have 10GB of data, S3 is so inexpensive it doesn't seem worth it to consider Glacier. Also, if you need data backup, use S3 and if you use to archive that backup, use Glacier.

References :
http://cloudacademy.com/blog/amazon-s3-vs-amazon-glacier-a-simple-backup-strategy-in-the-cloud/

http://stackoverflow.com/questions/14652276/backup-amazon-s3-or-glacier-lots-of-little-files

https://www.reddit.com/r/sysadmin/comments/2682mk/s3_glacier_restore_pricing_i_feel_like_ive_been/

https://gist.github.com/kinshuk4/f2e9edd5bfc9b2f056d83bfa9d76b2d9/edit
