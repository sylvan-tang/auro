#!/usr/bin/env bash
set -e

PROJECT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && cd .. && pwd )"

# stop all container
#docker container stop $(docker container ls -aq)
# stop all containers defined in docker/docker-compose.yml
docker-compose -f docker/docker-compose.yml down

# docker system prune -a # delete everything for all stopped containers

# start all containers defined in docker/docker-compose.yml
docker-compose -f docker/docker-compose.yml up -d

pushd $PROJECT_PATH/juno

read -p "确认清空数据库[y/N]? " -n 1 -r
if [[ $REPLY =~ ^[Yy]$ ]]; then
  while read -r url user password location skipFlag; do
    if [[ "$skipFlag" == "true" ]]; then
      continue
    fi
    mvn -pl juno-persistence -Dflyway.skip=false -Dflyway.url="$url" -Dflyway.user=$user -Dflyway.password=$password flyway:clean
  done <"$PROJECT_PATH/juno/bin/db.conf"
fi

while read -r url user password location skipFlag; do
  if [[ "$skipFlag" == "true" ]]; then
    continue
  fi
  mvn -pl juno-persistence -Dflyway.skip=false -Dflyway.url="$url" -Dflyway.user=$user -Dflyway.password=$password flyway:migrate
done <"$PROJECT_PATH/juno/bin/db.conf"
mvn -pl juno-persistence -Djooq.codegen.skip=false jooq-codegen:generate
popd
