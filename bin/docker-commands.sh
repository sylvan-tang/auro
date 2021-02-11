#!/usr/bin/env bash
set -x -e
# stop all container
#docker container stop $(docker container ls -aq)
# stop all containers defined in docker/docker-compose.yml
docker-compose -f docker/docker-compose.yml down

# docker system prune -a # delete everything for all stopped containers

# start all containers defined in docker/docker-compose.yml
docker-compose -f docker/docker-compose.yml up -d
