#!/bin/bash
set -e
PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

from_template=$1
to_module_path=$2
module_name=$3
echo $module_name

if [ -d $to_module_path ]; then
  read -p "Module path $to_module_path is exists, do you want to delete it?: [y/N]" confirm
  if [[ $confirm == [yY] ]]; then
    rm -rf $to_module_path
  else
    exit 0
  fi
fi

cp -r $PROJECT_PATH/templates/$from_template $to_module_path

SYSTEM_NAME=$(uname)
if [[ "$SYSTEM_NAME" = "Linux" ]]; then
  SYSTEM_NAME=$(echo $(awk -F= '/^NAME/{print $2}' /etc/os-release) | tr -d '"' | awk '{print $1}')
fi
SYSTEM_NAME=$(echo "$SYSTEM_NAME" | awk '{print tolower($0)}')

if [[ "$SYSTEM_NAME" = "darwin" ]]; then
  find $to_module_path -type f -exec sed -i '' -e "s/replacement/${module_name}/g" {} +
else
  find $to_module_path -type f -exec sed -i "s/replacement/${module_name}/g" {} +
fi
