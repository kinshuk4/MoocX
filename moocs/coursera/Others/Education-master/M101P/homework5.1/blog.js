use blog

db.posts.aggregate(
 { $project :
  { 
   _id : 0,
   comments : 1
  }
 },
 { $unwind :'$comments' },
 { $group : 
  {
   "_id" : "$comments.author",
   "count" : { $sum : 1 }
  }
 },
 { $sort : { "count" : - 1 } }
);
