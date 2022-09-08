#!/bin/bash
set -e

git config user.name sylvan
git config user.email sylvan2future@gmail.com
git config --global status.submoduleSummary true

PROJECT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )"

for file_name in pre-commit post-commit pre-push; do
  if [[ -f "$PROJECT_PATH/.git/hooks/$file_name" ]]; then
    mv $PROJECT_PATH/.git/hooks/$file_name $PROJECT_PATH/.git/hooks/$file_name.bak
  fi
  ln -sf $PROJECT_PATH/bin/$file_name $PROJECT_PATH/.git/hooks/$file_name
done
