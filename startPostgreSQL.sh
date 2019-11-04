#!/bin/bash
folder=/tmp/$USER
export PGDATA=$folder/myDB/data
export PGSOCKETS=$folder/myDB/sockets

echo $folder
#clear folder
rm -rf $folder

#initialize folders
mkdir $folder
mkdir $folder/myDB
mkdir $folder/myDB/data
mkdir $folder/myDB/sockets
sleep 1

#Initialize DB
/usr/lib/postgresql/10/bin/initdb
sleep 1

#start folder
export PGPORT=8192

/usr/lib/postgresql/10/bin/pg_ctl -o "-c unix_socket_directories=$PGSOCKETS -p $PGPORT" start
#/usr/lib/postgresql/10/bin/pg_ctl -D $PGDATA -l $folder/logfile start
