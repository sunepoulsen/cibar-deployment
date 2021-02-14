#!/usr/bin/env sh

### Check if a directory does not exist ###
docker-compose -f ../docker/prod/devops/docker-compose.yml up -d
docker-compose -f ../docker/prod/devops/docker-compose.yml ps

docker volume inspect devops_nexus-data
docker volume inspect devops_jenkins-data
