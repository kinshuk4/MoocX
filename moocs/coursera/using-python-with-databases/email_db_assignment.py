
# Bring in the SQLlite module
import sqlite3

# create the db, connect with it via the cursor
conn = sqlite3.connect('emaildb.sqlite')
cur = conn.cursor()

# Pass in some SQL code
cur.execute('DROP TABLE IF EXISTS Counts')
cur.execute('CREATE TABLE Counts (org TEXT, count INTEGER)')

# Parse the mbox file
fname = 'mbox.txt'

# Open up the file
fhand = open(fname)

#parse the lines of the file
for line in fhand:
    # we only care about lines starting with "from
    if not line.startswith('From: ') : continue
    
    #split the line into words
    pieces = line.split()
    
    #second word is the email address
    email = pieces[1]
    org = email.split('@')[1]
    
    #Pull in the current count of records in the db with this email address
    cur.execute('SELECT count FROM Counts WHERE org = ?', (org,))
    
    try:
        # If you got any rows back, grab the next one
        count = cur.fetchone()[0]
        
        # Update the count for this email (add 1) in the db
        cur.execute('UPDATE Counts SET count = count+1 WHERE org = ?', (org,))
    except:
        # Add a row for this email address to the db, set its count to 1
        cur.execute('INSERT INTO Counts (org, count) VALUES (?,1)', (org, ))

# Write these changes (we've made in memory) to disk
conn.commit()

cur.close()  
# Set up the command we'll execute. This pulls the emails and counts, ordering in descending order by count
# and giving us the top 10
#'sqlstr = 'SELECT email, count FROM Counts ORDER BY count DESC LIMIT 10' 

