#! /bin/bash
/usr/lib/postgresql/10/bin/pg_ctl -o "-c unix_socket_directories=$PGSOCKETS -p $PGPORT" stop 
#$PGDATA -l $folder/logfile stop
