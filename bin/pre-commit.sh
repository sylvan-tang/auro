#!/bin/bash
ROOT_PATH=$(pwd)
# shellcheck disable=SC2164
cd "$ROOT_PATH/gaea" && sbt "scalafix RemoveUnused" || cd "$ROOT_PATH"
# shellcheck disable=SC2164
cd "$ROOT_PATH/hecate" && mvn com.coveo:fmt-maven-plugin:format || cd "$ROOT_PATH"
git add .
