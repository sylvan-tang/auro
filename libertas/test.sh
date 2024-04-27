#!/bin/bash

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo $PROJECT_PATH

docker run --rm \
	    --volume $PROJECT_PATH:/mnt \
	    node:slim bash -c "set -e && cd /mnt && npm test -- --watchAll=false"
