#!/usr/bin/env bash
rm -rf sootOutput
class=$(basename ${1})
dir=$(dirname ${1})
java -jar soot-trunk.jar -f jimple -soot-classpath "rt.jar:${dir}" ${class%.*}
