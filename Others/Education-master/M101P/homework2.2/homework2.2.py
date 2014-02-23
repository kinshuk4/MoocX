import pymongo
import sys

connection = pymongo.Connection("mongodb://localhost", safe=True)

db = connection.students
grades = db.grades

try:
    cursor = db.grades.find({'type':'homework'}).sort([('student_id',pymongo.ASCENDING),('score',pymongo.DESCENDING)]).limit(1000)

except:
    print "Error", sys.exc_info()[0]

counter = 0
for doc in cursor:
    if counter%2==0:
        pass
    else:
        #print doc
        grades.remove({'_id':doc['_id']})
    counter += 1