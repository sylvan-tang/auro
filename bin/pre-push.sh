#!/bin/bash
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && cd .. && pwd)"

make local-start-mysql

sonar_sources=""

# shellcheck disable=SC2164
pushd "$ROOT_PATH/soteria"
git diff origin/master --name-only | grep .scala
if [[ $? == 0 ]]; then
  sbt clean assembly && sbt coverageReport && sbt coverageAggregate
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$ROOT_PATH/soteria"
  else
    sonar_sources+=",$ROOT_PATH/soteria"
  fi
fi
popd
pushd "$ROOT_PATH/java"
git diff origin/master --name-only | grep .java
if [[ $? == 0 ]]; then
  mvn clean test
  for path in $ROOT_PATH/java/*/target/classes; do
    if [[ -z "$sonar_sources" ]]; then
      sonar_sources+="$path"
    else
      sonar_sources+=",$path"
    fi
  done
fi
popd
pushd "$ROOT_PATH/rust"
git diff origin/master --name-only | grep .rs
if [[ $? == 0 ]]; then
  cargo build
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$ROOT_PATH/rust/src"
  else
    sonar_sources+=",$ROOT_PATH/rust/src"
  fi
fi
popd

git diff origin/master --name-only | grep .py
if [[ $? == 0 ]]; then
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$ROOT_PATH/python"
  else
    sonar_sources+=",$ROOT_PATH/python"
  fi
fi

if [[ -z "$sonar_sources" ]]; then
  exit 0
fi

echo "start sonar scanning..."
which sonar-scanner
if [[ $? != 0 ]]; then
  brew install sonar-scanner
fi

if [[ "$(docker ps | grep sonarqube)" == "" ]]; then
  sh $ROOT_PATH/bin/start-sonarqube.sh
fi

set -ex
sonar-scanner \
  -Dsonar.projectKey=auro \
  -Dsonar.sources=$sonar_sources \
  -Dsonar.host.url=http://localhost:10318 \
  -Dsonar.login=sqp_7a07ebc04c11c68c188bd519eab4a6745749dd8f

echo "sonar scanning end."
