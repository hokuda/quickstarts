#!/bin/sh


echo "######################## HIT RETURN for KEY PASS #########################"

#keytool -genkeypair -alias jboss -keyalg RSA -sigalg SHA256withRSA -keysize 2048 -keystore server.jks -storetype JKS -validity 3650 -keypass password -storepass password
keytool -genkeypair -alias jboss -keyalg RSA -sigalg SHA256withRSA -keysize 2048 -keystore server.jks -storetype JKS -validity 3650 -storepass password -dname "cn=localhost"

keytool -exportcert -rfc -alias jboss -keystore server.jks -storepass password > server.pem

keytool -import -v -trustcacerts -alias selfca -file server.pem -keystore trust.jks -storepass password


keytool -genkeypair -alias jboss -keyalg RSA -sigalg SHA1withRSA -keysize 2048 -keystore server128.jks -storetype JKS -validity 3650 -storepass password -dname "cn=localhost"

keytool -exportcert -rfc -alias jboss -keystore server128.jks -storepass password > server128.pem

keytool -import -v -trustcacerts -alias selfca -file server128.pem -keystore trust128.jks -storepass password

#rm *.jks

