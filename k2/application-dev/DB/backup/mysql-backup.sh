#!/bin/bash
### MySQL Server Login Info ###
MUSER="root"
MPASS="root_password"
MHOST="localhost"
MYSQL="$(which mysql)"
MYSQLDUMP="$(which mysqldump)"
BAK="/path/to/export"

# USE THIS CODE TO BACKUP ONLY ONE SPECIFIC DB
DB="my_database"
FILE=$BAK/$db_back.sql
$MYSQLDUMP -u $MUSER -h $MHOST -p$MPASS $DB > $FILE

# USE BELOW CODE TO BACKUP ALL DBS And GZIP them
# GZIP="$(which gzip)"

### FTP SERVER Login info ###
# FTPU="FTP-SERVER-USER-NAME"
# FTPP="FTP-SERVER-PASSWORD"
# FTPS="FTP-SERVER-IP-ADDRESS"
# NOW=$(date +"%d-%m-%Y") 

### See comments below ###
### [ ! -d $BAK ] && mkdir -p $BAK || /bin/rm -f $BAK/* ###
# [ ! -d "$BAK" ] && mkdir -p "$BAK" 
# DBS="$($MYSQL -u $MUSER -h $MHOST -p$MPASS -Bse 'show databases')"
# for db in $DBS
# do
#  FILE=$BAK/$db.$NOW-$(date +"%T").gz
#  $MYSQLDUMP -u $MUSER -h $MHOST -p$MPASS $db | $GZIP -9 > $FILE
# done
#  
# lftp -u $FTPU,$FTPP -e "mkdir /mysql/$NOW;cd /mysql/$NOW; mput /backup/mysql/*; quit" $FTPS


- https://gist.github.com/daspecster/9ed8f235186c731bcc2f
