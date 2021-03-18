#!/bin/bash
ROOT_PATH=$(pwd)
# shellcheck disable=SC2164
pushd "$ROOT_PATH/gaea"
  sbt clean assembly && sbt coverageReport && sbt coverageAggregate
popd
pushd "$ROOT_PATH/hecate"
 mvn clean test
popd

