import pymongo

mongostr = "mongodb://localhost:27017"
db_name = "final"

def main():

    connection = pymongo.Connection(mongostr, safe=True)
    db = connection[db_name]

    cursor_albums = db.albums.find()
    cursor_images = db.images.find()

    albums = []
    for object in cursor_albums:
        for o in object['images']:
            albums.append(o)

    images = []
    for object in cursor_images:
        images.append(object['_id'])

    orphans = list(set(images) - set(albums))
    sumTotal = sum(images) - sum(orphans)

if __name__ == "__main__":
    main()