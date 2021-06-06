#!/usr/bin/env bash
set -x -e

PROJECT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && cd .. && pwd )"

# stop all container
#docker container stop $(docker container ls -aq)
# stop all containers defined in docker/docker-compose.yml
docker-compose -f docker/docker-compose.yml down

# docker system prune -a # delete everything for all stopped containers

# start all containers defined in docker/docker-compose.yml
docker-compose -f docker/docker-compose.yml up -d

pushd $PROJECT_PATH/hecate

read -p "确认清空数据库[y/N]? " -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  mvn -pl hecate-persistence -Dflyway.skip=false flyway:clean
fi
mvn -pl hecate-persistence -Dflyway.skip=false flyway:migrate
mvn -pl hecate-persistence -Djooq.codegen.skip=false jooq-codegen:generate
popd
