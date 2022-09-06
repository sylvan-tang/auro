#!/bin/bash
set -e

git config user.name sylvan
git config user.email sylvan2future@gmail.com

PROJECT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )"

if [[ -f "$PROJECT_PATH/.git/hooks/pre-commit" ]]; then
  mv $PROJECT_PATH/.git/hooks/pre-commit $PROJECT_PATH/.git/hooks/pre-commit.bak
fi
ln -sf $PROJECT_PATH/bin/pre-commit.sh $PROJECT_PATH/.git/hooks/pre-commit

if [[ -f "$PROJECT_PATH/.git/hooks/pre-push" ]]; then
  mv $PROJECT_PATH/.git/hooks/pre-push $PROJECT_PATH/.git/hooks/pre-push.bak
fi
ln -sf $PROJECT_PATH/bin/pre-push.sh $PROJECT_PATH/.git/hooks/pre-push
