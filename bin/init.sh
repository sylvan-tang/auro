#!/bin/bash
set -e

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"
# wget -O gf https://github.com/gogf/gf-cli/releases/download/v1.16.2/gf_darwin_amd64 && chmod +x gf && ./gf install && rm gf
for file_name in pre-commit pre-push; do
  if [[ -f "$PROJECT_PATH/.git/hooks/$file_name" ]]; then
    mv $PROJECT_PATH/.git/hooks/$file_name $PROJECT_PATH/.git/hooks/$file_name.bak
  fi
  ln -sf $PROJECT_PATH/bin/$file_name.sh $PROJECT_PATH/.git/hooks/$file_name
done
