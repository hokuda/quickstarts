#!/bin/bash

$JBOSS_HOME/bin/jboss-cli.sh << EOS
module add --name=com.redhat.example --slot=main --resources=./target/undertow-httphandler-1.0-SNAPSHOT.jar --dependencies=io.undertow.core,javaee.api,javax.api
EOS
