#!/bin/sh
# Indicate the path of the java compiler to use
#export JAVA_HOME=/usr/csshare/pkgs/jdk1.7.0_17
export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Export classpath with the postgressql driver
export CLASSPATH=$CLASSPATH:$PWD/pg73jdbc3.jar

# compile the java program
javac EmbeddedSQL.java

#run the java program
#Use your database name, port number and login
java EmbeddedSQL $USER"_DB" $PGPORT $USER

