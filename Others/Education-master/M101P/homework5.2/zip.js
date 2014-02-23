use test

db.zips.aggregate([
{
    $match:
    {
        state : { $in : ['NY','CA'] }
    }
},
{
    $group:
    {
        _id: { state : "$state", city : "$city" },         
        sum : { $sum : '$pop'}
    }
},
{
    $match:
    {
        sum : { $gt : 25000 }
    }
},
{
    $group :
    {
        _id : null,
        avg : { $avg : '$sum' }
    }
}
])