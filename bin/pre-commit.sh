#!/bin/bash
ROOT_PATH=$(pwd)
# shellcheck disable=SC2164
# shellcheck disable=SC2164
cd "$ROOT_PATH/jasper"; mvn com.coveo:fmt-maven-plugin:format; cd "$ROOT_PATH"
git add .
