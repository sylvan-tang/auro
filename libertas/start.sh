#!/bin/bash

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo $PROJECT_PATH


docker build --tag libertas:latest -f Dockerfile .

docker service create --name libertas-frontend --publish 80:80 libertas:latest
