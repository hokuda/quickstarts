#!/bin/bash

$JBOSS_HOME/bin/jboss-cli.sh -c << EOS
/subsystem=undertow/configuration=filter/custom-filter=restrict-handler:add(class-name=com.redhat.example.RestrictHandler, module=com.redhat.example)
/subsystem=undertow/server=default-server/host=default-host/filter-ref=restrict-handler:add()
EOS
