#!/bin/bash

# Basic variables
mysqlpass="########"
bucket="s3://#####-prod-data-####/######-#"
host="####-########-rds-platform-1.######.us-east-1.rds.amazonaws.com"

# Timestamp (sortable AND readable)
stamp=`date +"%s - %A %d %B %Y @ %H%M"`

#Make sure we can connect to the database
echo "Running a check on your DB creds"
mysql -u backup -p$mysqlpass -h $host -e "SHOW DATABASES;" &>/dev/null 
rc=$?; if [[ $rc != 0 ]]; then echo "not working; check your creds" && exit $rc; fi

# List all the databases
databases=`mysql -u backup -p$mysqlpass -h $host -e "SHOW DATABASES;" | tr -d "| " | grep -v "\(Database\|information_schema\|performance_schema\|mysql\|test\)"`

# Feedback
echo "Dumping to $bucket/$stamp/"

# Loop the databases
for db in $databases; do

  # Define our filenames
  filename="$stamp - $db.sql.gz"
  tmpfile="/tmp/$filename"
  object="$bucket/$stamp/$filename"

  # Feedback
  echo "$db"

  # Dump and zip
  echo "  creating $tmpfile"
  mysqldump -u backup -p$mysqlpass -h $host --force --opt --databases "$db" | gzip -1 > "$tmpfile"
  rc=$?; if [[ $rc == 0 ]]; then echo "$db dump and zip successfull"; else echo "Something went wrong."; exit $rc; fi 

  # Upload
  echo "  uploading..."
  aws s3 --profile production cp "$tmpfile" "$object"
  rc=$?; if [[ $rc == 0 ]]; then echo "$db successfully uploaded to S3."; else echo "Upload failed"; exit $rc; fi

  # Delete
  rm -f "$tmpfile"

done;

#will change email after testing
echo "$filename uploaded to $object" |mail -s "DB Backup to S3 successfull" #####@######.com

#credit - 
#https://gist.github.com/hobakill/af680fc4948d5aaea56c

