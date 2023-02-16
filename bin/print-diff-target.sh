#!/usr/bin/env bash
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"
branch=master
diff_command="git status"
while [[ "$#" -gt 0 ]]; do
  case $1 in
    --branch=* | -B=*)
      branch=$(cut -d"=" -f 2 <<<"$1")
      diff_command="git diff $branch --name-only"
      ;;
    --help)
      echo "Run with: $0 [--branch|-B]=master"
      exit 1
      ;;
  esac
  shift
done

echo $diff_command

if [ -f "$ROOT_PATH/git-diff.txt" ]; then
  rm "$ROOT_PATH/git-diff.txt"
fi
for model in $(ls */Makefile); do
  dir_path=$(dirname $model)
  diff_check=$(eval $diff_command | grep -v "scm_version" | grep -v "Makefile" | grep -v "config.ini" | grep -v "config.toml" | grep $dir_path)
  if [[ -z "$diff_check" && "$dir_path" != "make-template" ]]; then
    continue
  fi
  echo $dir_path
  echo $dir_path >>"$ROOT_PATH/git-diff.txt"
done

for item in toolkits/cmd/gen-proto:protobuf toolkits/cmd/gen-grpc:golang; do
  key=$(echo $item | cut -d ':' -f 1)
  value=$(echo $item | cut -d ':' -f 2)
  echo "key  : ${key}"
  echo "value: ${value}"
  diff_check=$(eval $diff_command | grep ${key})
  if [[ ! -z "$diff_check" ]]; then
    echo "${value}" >>"$ROOT_PATH/git-diff.txt"
  fi
done

sort "$ROOT_PATH/git-diff.txt" | uniq >"$ROOT_PATH/git-diff.txt.tmp"
mv "$ROOT_PATH/git-diff.txt.tmp" "$ROOT_PATH/git-diff.txt"

if [ -f "$ROOT_PATH/base-images-diff.txt" ]; then
  rm "$ROOT_PATH/base-images-diff.txt"
fi
if [ -f "$ROOT_PATH/images-diff.txt" ]; then
  rm "$ROOT_PATH/images-diff.txt"
fi

echo "make-template-env" >>"$ROOT_PATH/base-images-diff.txt"
echo "make-template-env" >>"$ROOT_PATH/images-diff.txt"

for dockerfile_name in $(ls dockers/*.dockerfile); do
  file_name=$(basename $dockerfile_name .dockerfile)
  diff_check=$(eval "$diff_command" | grep -v "scm_version" | grep -v "Makefile" | grep -v "config.ini" | grep -v "config.toml" | grep $file_name)
  if [[ -z "$diff_check" ]]; then
    continue
  fi
  if [[ $file_name == *-env ]]; then
    echo $(basename $file_name -env) >>"$ROOT_PATH/images-diff.txt"
  else
    echo $file_name >>"$ROOT_PATH/base-images-diff.txt"
  fi
done

if [ -f "$ROOT_PATH/all-images.txt" ]; then
  rm "$ROOT_PATH/all-images.txt"
fi

for dockerfile_name in $(ls dockers/*.dockerfile); do
  file_name=$(basename $dockerfile_name .dockerfile)
  if [[ $file_name != *-env ]]; then
    echo $file_name >>"$ROOT_PATH/all-images.txt"
  fi
done

for dockerfile_name in $(ls dockers/*.dockerfile); do
  file_name=$(basename $dockerfile_name .dockerfile)
  if [[ $file_name == *-env ]]; then
    echo $file_name >>"$ROOT_PATH/all-images.txt"
  fi
done

for item in mysql-diff:mysql python-diff:.py go-mod-diff:go.mod; do
  key=$(echo $item | cut -d ':' -f 1)
  value=$(echo $item | cut -d ':' -f 2)
  echo "key  : ${key}"
  echo "value: ${value}"
  file_name=$ROOT_PATH/$key.txt
  if [[ -f "$file_name" ]]; then
    rm "$file_name"
  fi
  diff_check=$(eval $diff_command | grep -v $key.txt | grep ${value})
  if [[ ! -z "$diff_check" ]]; then
    touch "$file_name"
  fi
done
