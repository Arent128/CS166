#! /bin/bash
echo "creating db named ..."$USER"_DB"
createdb -h localhost -p $PGPORT $USER"_DB"
##/usr/lib/postgresql/bin/11/pg_ctl status
