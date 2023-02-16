#!/bin/bash
set -ex
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"
PYPKG_PATH=$1
sed -Ei \
  's/(from|import) thirdparty/\1 ...thirdparty/g' \
  ${PYPKG_PATH}/apis/*/*/*.py*
sed -Ei \
  's/(from|import) (.+\.v1)/\1 ...\2/g' \
  ${PYPKG_PATH}/apis/*/v1/*.py*
