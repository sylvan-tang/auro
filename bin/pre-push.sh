#!/bin/bash
set -e

ROOT_PATH=$(pwd)

if [ "$( docker container inspect -f '{{.State.Running}}' 'local-mysql' )" == "false" ]; then
  docker-compose -f docker/docker-compose.yml up -d
fi
if [ "$( docker container inspect -f '{{.State.Running}}' 'local-redis' )" == "false" ]; then
  docker-compose -f docker/docker-compose.yml up -d
fi
# shellcheck disable=SC2164
pushd "$ROOT_PATH/gaea"
  sbt clean assembly && sbt coverageReport && sbt coverageAggregate
popd
pushd "$ROOT_PATH/hecate"
 mvn clean test
popd

#echo "start sonar scanning..."
#which sonar-scanner
#if [[ $? != 0 ]] ; then
#  brew install sonar-scanner
#fi
#
#if [ "$( docker container inspect -f '{{.State.Running}}' 'sonarqube' )" == "false" ]; then
#  sh $ROOT_PATH/bin/start-sonarqube.sh
#fi
#
#sonar-scanner \
#  -Dsonar.projectKey=auro \
#  -Dsonar.sources=$ROOT_PATH/gaea,$ROOT_PATH/proserpine,$ROOT_PATH/hecate/hecate-common/target/classes,$ROOT_PATH/hecate/hecate-interview/target/classes,$ROOT_PATH/hecate/hecate-tool/target/classes \
#  -Dsonar.host.url=http://localhost:10318 \
#  -Dsonar.login=3018137d5196a5c31c6bce933bc9323a9f3c025c
#
#echo "sonar scanning end."
