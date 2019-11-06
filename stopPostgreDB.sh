#! /bin/bash
/usr/lib/postgresql/11/bin/pg_ctl -o "-c unix_socket_directories=$PGSOCKETS -p $PGPORT" stop 
#$PGDATA -l $folder/logfile stop
