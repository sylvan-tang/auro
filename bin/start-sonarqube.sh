#!/usr/bin/env bash
set -x

SONARQUBE_WEB_PORT=10318

docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p $SONARQUBE_WEB_PORT:9000 sonarqube:latest
if [[ $? != 0 ]] ; then
  docker start sonarqube
fi
