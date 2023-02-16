#!/bin/bash
set -e
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && cd .. && pwd)"

$ROOT_PATH/bin/generate-ci-workflows.sh
$ROOT_PATH/bin/print-diff-target.sh
for make_file in ls $ROOT_PATH/*/Makefile; do
  module_path=$(dirname $make_file)
  module_name=$(basename $module_path)
  echo $module_name >$module_path/name.txt
done
$ROOT_PATH/bin/gen-mysql-dockerfile.sh
make base-image image PUSH_IMAGE=N
if [[ -f "$ROOT_PATH/mysql-diff.txt" ]]; then
  make -C $ROOT_PATH/golang gen-dao-docker
fi
make clean build format PUSH_IMAGE=N

if [[ -f "$ROOT_PATH/go-mod-diff.txt" ]]; then
  make go-work-sync PUSH_IMAGE=N
fi

# format json files
for filepath in $(find $ROOT_PATH -name '*.json' -type f | grep -v __pypackages__ | grep -v kubeflow-manifests); do
  jq . --indent 1 $filepath >$filepath.tmp
  mv $filepath.tmp $filepath
done
if [[ -f "$ROOT_PATH/python-diff.txt" ]]; then
  make scm-version PUSH_IMAGE=N
fi
$ROOT_PATH/bin/print-diff-target.sh --branch=master

which shfmt
if [[ $? != 0 ]]; then
  while true; do
    echo "install shfmt..."
    go install mvdan.cc/sh/v3/cmd/shfmt@latest
    if [[ $? = 0 ]]; then
      break
    fi
  done
fi
find . -name '*.sh' | grep -v ".history" | xargs -I {} shfmt -l -w -ci --indent=2 {}

git add .
