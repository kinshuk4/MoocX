use test

db.zips.aggregate([
{
    $project:
    {
        first_char: { $substr : ["$city",0,1]
        },
        pop : 1
    }
}, 
{  
    $match:
    {
        first_char: { $regex : '/^\d/' }
    }
}
])