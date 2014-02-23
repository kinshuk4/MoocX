use enron

db.messages.aggregate([
{
    $match : { 'headers.From' : "andrew.fastow@enron.com" }
},
{
    $unwind : '$headers.To'
},
{
    $match : { 'headers.To' : "jeff.skilling@enron.com" }
},
{
    $group :
    {
        _id : null,
        count : { $sum: 1 }
    }
}
])