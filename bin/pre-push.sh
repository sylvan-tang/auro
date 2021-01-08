#!/bin/bash
ROOT_PATH=$(pwd)
# shellcheck disable=SC2164
cd "$ROOT_PATH/gaea" && sbt clean assembly && sbt coverageReport && sbt coverageAggregate || cd "$ROOT_PATH"
cd "$ROOT_PATH/hecate" && mvn clean test || cd "$ROOT_PATH"
