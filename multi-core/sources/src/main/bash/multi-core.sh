#!/bin/bash

##
## Starts the applicattion.
##

CMD="java -jar"
BASE_DIR=`dirname $0`
JAR="${BASE_DIR}/../../../target/multi-core-1.0-SNAPSHOT.jar"

${CMD} ${JAR} $@
