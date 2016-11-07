#!/usr/bin/env bash
args=$(($#-1))
java -jar soot-trunk.jar -f jimple -soot-classpath "rt.jar:${@:1:$args}" ${!#}
