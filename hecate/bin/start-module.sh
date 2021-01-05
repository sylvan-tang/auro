#!/usr/bin/env bash
set -e -x

module=""
profile=""

function echo_help() {
  echo "Run with example: sh bin/start-module.sh -Pdev --module=hecate-tool"
}

while [[ "$#" -gt 0 ]]; do
    case $1 in
    -P*)
        profile=$(cut -d"P" -f 2 <<<"$1")
        ;;
    --module=*)
        module=$(cut -d"=" -f 2 <<<"$1")
        ;;
    --help)
        echo_help
        exit 1
        ;;
    esac
    shift
done

if [[ -z $module ]]; then
  echo_help
  exit 1
fi
if [[ -z $profile ]]; then
  echo_help
  exit 1
fi
mvn spring-boot:run -am -DskipTests -Dspring.profiles.active=${profile} -pl "${module}"
