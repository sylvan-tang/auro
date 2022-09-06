#!/bin/bash
set -e

ROOT_PATH=$(pwd)
pushd "$ROOT_PATH/gaea"
  sbt "scalafix RemoveUnused"
popd
pushd "$ROOT_PATH/hecate"
  mvn com.coveo:fmt-maven-plugin:format
popd
pushd "$ROOT_PATH/rhadamanthys"
  cargo fmt
popd
git add .
