#!/bin/bash
set -e

ROOT_PATH=$(pwd)
pushd "$ROOT_PATH/soteria"
  sbt "scalafix RemoveUnused"
popd
pushd "$ROOT_PATH/juno"
  mvn com.coveo:fmt-maven-plugin:format
popd
pushd "$ROOT_PATH/rhea"
  cargo fmt
popd
git add .
