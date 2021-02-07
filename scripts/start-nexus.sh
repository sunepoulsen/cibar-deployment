#!/usr/bin/env sh

### Check if a directory does not exist ###
if [ ! -d "~/.ciber/prod/data/nexus" ]
then
  mkdir -p ~/.ciber/prod/data/nexus
  sudo chown -R 200.200 ~/.ciber/prod/data/nexus
fi

docker-compose -f ../docker/prod/nexus/docker-compose.yml up -d
docker-compose -f ../docker/prod/nexus/docker-compose.yml ps
