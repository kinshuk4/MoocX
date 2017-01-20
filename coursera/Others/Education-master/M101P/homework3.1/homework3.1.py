import pymongo
import sys

connection = pymongo.Connection("mongodb://localhost", safe=True)

db = connection.school
students = db.students

try:
    cursor = db.students.find()
    count = cursor.count()

except:
    print "Error", sys.exc_info()[0]

counter = 0
for doc in cursor:
    id = doc['_id']
    scores = doc['scores']
    hw1 = scores[2]['score']
    hw2 = scores[3]['score']
    if hw1 > hw2:
        students.update( { '_id' : id }, { '$pull' : { 'scores' : { 'score' : hw2 } } } )
    else:
        students.update( { '_id' : id }, { '$pull' : { 'scores' : { 'score' : hw1 } } } )
