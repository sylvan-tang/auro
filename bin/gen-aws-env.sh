#!/bin/bash
set -ex
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

echo "#!/usr/bin/env bash" >${ROOT_PATH}/local-conf/aws-env.sh

echo "export AWS_ACCESS_KEY_ID=$1" >>${ROOT_PATH}/local-conf/aws-env.sh
echo "export AWS_SECRET_ACCESS_KEY=$2" >>${ROOT_PATH}/local-conf/aws-env.sh
chmod +x ${ROOT_PATH}/local-conf/aws-env.sh
