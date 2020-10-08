#!/bin/bash
ROOT_PATH=$(pwd)
# shellcheck disable=SC2164
cd "$ROOT_PATH/gaea" && sbt clean assembly && sbt coverageReport && sbt coverageAggregate || cd "$ROOT_PATH"
git add .
git commit --amend --no-edit
