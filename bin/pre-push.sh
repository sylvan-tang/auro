#!/bin/bash
set -e

ROOT_PATH=$(pwd)

docker-compose -f docker/docker-compose.yml up -d

# shellcheck disable=SC2164
pushd "$ROOT_PATH/soteria"
  sbt clean assembly && sbt coverageReport && sbt coverageAggregate
popd
pushd "$ROOT_PATH/juno"
  mvn clean test
popd
pushd "$ROOT_PATH/rhea"
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
for path in $ROOT_PATH/juno/*/target/classes; do
  java_projects+=",$path"
done

sonar-scanner \
 -Dsonar.projectKey=auro \
 -Dsonar.sources=$ROOT_PATH/soteria,$ROOT_PATH/phoebe,$ROOT_PATH/rhea/src,$java_projects \
 -Dsonar.host.url=http://localhost:10318 \
 -Dsonar.login=sqp_7a07ebc04c11c68c188bd519eab4a6745749dd8f

echo "sonar scanning end."
