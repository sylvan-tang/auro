#!/usr/bin/env bash
set -x

sonar_sources=$1

sonar-scanner \
  -Dsonar.projectKey=xxxxxx \
  -Dsonar.sources=$sonar_sources \
  -Dsonar.host.url=http://localhost:10318 \
  -Dsonar.login=xxxxx
echo "sonar scanning end."
