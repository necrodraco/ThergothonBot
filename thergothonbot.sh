#!/bin/sh

##directory where jar file is located    
dir=target
##jar file name
jar_name=thergothonbot
##Application to run
app=de.etcg.thergothonbot.App

## Permform some validation on input arguments, one example below
if [ ! -d "${dir}" ] || [ $# -gt 0 ]
then
    mvn package
    mvn dependency:copy-dependencies -DoutputDirectory=${dir}/libs
fi

mkdir -p JSON
java -cp ${dir}/libs/*:${dir}/${jar_name}-1.0-SNAPSHOT.jar ${app}