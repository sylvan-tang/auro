#!/bin/bash
ROOT_PATH=$(pwd)
pushd "$ROOT_PATH/gaea"
  sbt "scalafix RemoveUnused"
popd
pushd "$ROOT_PATH/hecate"
  mvn com.coveo:fmt-maven-plugin:format
popd
git add .


which sonar-scanner
if [[ $? != 0 ]] ; then
  brew install sonar-scanner
fi

echo $ROOT_PATH
sonar-scanner \
  -Dsonar.projectKey=auro \
  -Dsonar.sources=$ROOT_PATH/gaea,$ROOT_PATH/proserpine,$ROOT_PATH/hecate/hecate-common/target/classes,$ROOT_PATH/hecate/hecate-interview/target/classes,$ROOT_PATH/hecate/hecate-tool/target/classes \
  -Dsonar.host.url=http://localhost:10318 \
  -Dsonar.login=3018137d5196a5c31c6bce933bc9323a9f3c025c


echo $ROOT_PATH