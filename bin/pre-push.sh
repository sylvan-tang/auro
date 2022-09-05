#!/bin/bash
set -e

ROOT_PATH=$(pwd)

docker-compose -f docker/docker-compose.yml up -d

# shellcheck disable=SC2164
pushd "$ROOT_PATH/gaea"
  sbt clean assembly && sbt coverageReport && sbt coverageAggregate
popd
pushd "$ROOT_PATH/hecate"
 mvn clean test
popd
pushd "$ROOT_PATH/rhadamanthys"
 cargo build
popd

echo "start sonar scanning..."
which sonar-scanner
if [[ $? != 0 ]] ; then
 brew install sonar-scanner
fi

if [[ "$( docker ps | grep sonarqube )" == "" ]]; then
 sh $ROOT_PATH/bin/start-sonarqube.sh
fi

java_projects=""
for path in $ROOT_PATH/hecate/*/src; do
  java_projects+=",$path"
done

sonar-scanner \
 -Dsonar.projectKey=auro \
 -Dsonar.sources=$ROOT_PATH/gaea,$ROOT_PATH/proserpine,$ROOT_PATH/rhadamanthys/src,$java_projects \
 -Dsonar.host.url=http://localhost:10318 \
 -Dsonar.login=sqp_7a07ebc04c11c68c188bd519eab4a6745749dd8f

echo "sonar scanning end."
