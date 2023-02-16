#!/bin/bash

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

git remote add upstream git@github.com:sylvan-tang/auro.git

git fetch upstream:
git rebase FETCH_HEAD

for file_name in go.work.sum all-images.txt git-diff.txt images-diff.txt base-images-diff.txt go-mod-diff.txt mysql-diff.txt mysql/create_db.sql dockers/mysql.dockerfile; do
  if [[ -f "$PROJECT_PATH/$file_name" ]]; then
    rm "$PROJECT_PATH/$file_name"
  fi
done

for file_name in scm_version.toml go.sum; do
  find $PROJECT_PATH -name $file_name -type f -print -delete
done

for file_name in .github/workflow-templates/*; do
  workflow_name=$(basename $file_name)
  find $PROJECT_PATH/.github/workflows/ -name $workflow_name -type f -print -delete
done
