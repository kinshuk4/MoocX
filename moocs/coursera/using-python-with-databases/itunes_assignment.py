# PYTHDB Assignment 2
# Motivation: Given XML representation of iTunes, parse it and store in a SQLite DB

# Bring in the SQLlite module
import sqlite3
import xml.etree.ElementTree as ET

# create the db, connect with it via the cursor
conn = sqlite3.connect('itunes_assignment.sqlite')
cur = conn.cursor()

# Create the tables if they don't already exist
cur.executescript('''
CREATE TABLE IF NOT EXISTS Artist (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS Genre (
    id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    name    TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS Album (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    artist_id INTEGER,
    title TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS Track (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    title TEXT UNIQUE,
    album_id INTEGER,
    genre_id INTEGER,
    len INTEGER, rating INTEGER, count INTEGER
);
''')

# Open a connection with the library XML file
fname = 'Library.xml'
fhand = open(fname)

# A small helper function for checking the XML data

# <key>Track ID</key><integer>369</integer>
# <key>Name</key><string>Another One Bites The Dust</string>
# <key>Artist</key><string>Queen</string>
def lookup(d, key):
    found = False
    for child in d:
        if found : return child.text
        if child.tag == 'key' and child.text == key :
            found = True
    return None

# Use ET to parse the XML into a big Python dictionary
stuff = ET.parse(fname)

# Grab all the 3-deep dictionaries from the XML
all = stuff.findall('dict/dict/dict')
print 'Dict count:', len(all)

for entry in all:
    #If there is not "Track ID" in this dictionary, move on
    if ( lookup(entry, 'Track ID') is None ) : continue

    #grab some information from the records
    name = lookup(entry, 'Name')
    artist = lookup(entry, 'Artist')
    album = lookup(entry, 'Album')
    count = lookup(entry, 'Play Count')
    rating = lookup(entry, 'Rating')
    length = lookup(entry, 'Total Time')
    genre = lookup(entry, 'Genre')

    # we only want "pretty" records
    if name is None or artist is None or album is None or genre is None : 
        continue

    #print out the record if it's a good one
    print name, artist, album, genre

    cur.execute('''INSERT OR IGNORE INTO Artist (name) 
        VALUES ( ? )''', ( artist, ) )
    cur.execute('SELECT id FROM Artist WHERE name = ? ', (artist, ))
    artist_id = cur.fetchone()[0]

    cur.execute('''INSERT OR IGNORE INTO Album (title, artist_id) 
        VALUES ( ?, ? )''', ( album, artist_id ) )
    cur.execute('SELECT id FROM Album WHERE title = ? ', (album, ))
    album_id = cur.fetchone()[0]
    
    #add the genre to the genre table
    cur.execute('''INSERT OR IGNORE INTO Genre (name) 
        VALUES ( ? )''', ( genre,) )
    
    # Get the genre_id back out
    cur.execute('SELECT id FROM Genre WHERE name = ? ', (genre, ))
    genre_id = cur.fetchone()[0]

    # Update the tracks table
    cur.execute('''INSERT OR REPLACE INTO Track
        (title, album_id, genre_id, len, rating, count) 
        VALUES ( ?, ?, ?, ?, ?, ? )''', 
        ( name, album_id, genre_id, length, rating, count ) )

    # Commit the changes
    conn.commit()