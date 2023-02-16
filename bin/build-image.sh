#!/bin/bash
set -ex
PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

if [[ "$#" -lt 2 ]]; then
  echo "Run with: $0 {docker-file-name} {image_name} [--push_switch|-PUSH]=Y/N [--build-arg {arg}]"
  exit 1
fi

push_switch=N

docker_file_path=$1
shift
image_name=$1
shift

build_command="DOCKER_BUILDKIT=1 docker build --file $docker_file_path"

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --push_switch=* | -PUSH=*)
      push_switch=$(cut -d"=" -f 2 <<<"$1")
      ;;
    --build_arg)
      build_command="${build_command} $1"
      shift
      build_command="${build_command} $1"
      shift
      ;;
    --help)
      echo "Run with: $0 {docker_file_path} {image_name} [--push_switch|-PUSH]=Y/N [--build-arg {arg}]"
      exit 1
      ;;
  esac
  shift
done

while IFS="," read -r registry owner tag user secret_path; do
  build_command="${build_command} --tag ${registry}/${owner}/${image_name}:${tag}"
done < <(tail -n +2 $PROJECT_PATH/bin/image-config.csv)
build_command="${build_command} ."
echo "$build_command:"
eval $build_command

while IFS="," read -r registry owner tag user secret_path; do
  if [ "${push_switch}" = "Y" ]; then
    cat ${secret_path} | docker login --username ${user} --password-stdin ${registry}
    docker push ${registry}/${owner}/${image_name}:${tag}
  else
    echo skip push ${registry}/${owner}/${image_name}:${tag}
  fi
done < <(tail -n +2 $PROJECT_PATH/bin/image-config.csv)
