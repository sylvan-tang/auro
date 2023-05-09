#!/bin/bash
set -ex
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

sonar_sources=""

# shellcheck disable=SC2164
for path in $(cat $ROOT_PATH/sonar.txt); do
  if [[ -z "$sonar_sources" ]]; then
    sonar_sources+="$path"
  else
    sonar_sources+=",$path"
  fi
done
echo $sonar_sources

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

set -ex
echo "sonar scanning start..."
sonar-scanner \
  -Dsonar.projectKey=auro \
  -Dsonar.sources=$sonar_sources \
  -Dsonar.host.url=http://localhost:10318 \
  -Dsonar.login=sqp_032fab3858ada38fbd441fd9849e74486e7877d5

echo "sonar scanning end."
