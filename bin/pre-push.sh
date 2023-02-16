#!/bin/bash
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && cd .. && pwd)"

make start-mysql-local

sonar_sources=""

# shellcheck disable=SC2164
pushd "$ROOT_PATH/soteria"
git diff master --name-only | grep .scala
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
git diff master --name-only | grep .java
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
git diff master --name-only | grep .rs
if [[ $? == 0 ]]; then
  cargo build
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$ROOT_PATH/rust/src"
  else
    sonar_sources+=",$ROOT_PATH/rust/src"
  fi
fi
popd

pushd "$ROOT_PATH/python"
git diff master --name-only | grep .py
if [[ $? == 0 ]]; then
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$ROOT_PATH/python"
  else
    sonar_sources+=",$ROOT_PATH/python"
  fi
fi
popd

pushd "$ROOT_PATH/golang"
git diff master --name-only | grep .go
if [[ $? == 0 ]]; then
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$ROOT_PATH/golang"
  else
    sonar_sources+=",$ROOT_PATH/golang"
  fi
fi
popd

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

echo "sonar scanning start..."
sonar-scanner \
  -Dsonar.projectKey=auro \
  -Dsonar.sources=$sonar_sources \
  -Dsonar.host.url=http://localhost:10318 \
  -Dsonar.login=sqp_da7281e7364bb0fa4f24f0171af7cf2d46bd9290

echo "sonar scanning end."
