#!/bin/sh

# change JBOSS_HOME for your setup
JBOSS_HOME=/home/opt/eap64x

export CLASSPATH=target/ejb.jar:$JBOSS_HOME/bin/client/jboss-client.jar

#JAVA_OPT="$JAVA_OPT -Djavax.net.debug=ssl,handshake"

java $JAVA_OPT sample.ClientScopedContext
