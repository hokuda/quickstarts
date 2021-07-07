#!/bin/sh -v

export CLASSPATH=.
export JAVA_OPTS="-Djavax.net.ssl.keyStoreType=pkcs12 -Djavax.net.ssl.keyStore=/home/hokuda/openssl/test3-rootca/new.p12 -Djavax.net.ssl.keyStorePassword=password"
export JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.trustStore=./newtrust.jks -Djavax.net.ssl.trustStorePassword=password"
#java -cp . -Dhttps.protocols=TLSv1 -Djavax.net.debug=all SimpleHttpsClient localhost 8443
#command="$JAVA_HOME/bin/java -cp . -Djavax.net.debug=ssl,handshake $JAVA_OPTS SimpleHttpsClient localhost 8443"
#command="$JAVA_HOME/bin/java -cp . -Djavax.net.debug=ssl,handshake,record,plaintext $JAVA_OPTS SimpleHttpsClient localhost 8443"
command="$JAVA_HOME/bin/java -cp . -Djavax.net.debug=ssl,handshake,record,plaintext $JAVA_OPTS SimpleHttpsClient https://localhost:8443/"

echo "$command"

$command
