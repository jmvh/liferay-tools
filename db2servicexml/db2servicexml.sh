#!/bin/bash

VERSION=0.1.0-SNAPSHOT
JAR_FILE=db2servicexml-$VERSION.jar

echo java -jar target/$JAR_FILE $@ >&2
java -jar target/$JAR_FILE $@
