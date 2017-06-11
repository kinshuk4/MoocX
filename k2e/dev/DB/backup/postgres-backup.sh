## Postgres Sql

# Backup
pg_dump -h <orig db> -U <user> -Fc  > data.backup

psql in to db...
$ drop schema public cascade;
$ \connect postgres
$ drop database <dbname>;

# Restore
pg_restore --verbose --exit-on-error -Fc  -n public -d <Database> -U <user> -h <rds instance> <db file>

# Credit - https://gist.github.com/daspecster/9ed8f235186c731bcc2f
