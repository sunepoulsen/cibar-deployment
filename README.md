# Ciber Deployment

This repository contains Docker Compose files to run all the DevOps containers in a single environment with Docker Compose. Each container will mount 
its production data to the local filesystem to keep the production data safe if the application containers are deleted.

## Applications

The current applications are deployed:

- Nexus: Maven repository for dependencies.

## Ports

The applications are using these ports

| Application Name         | Environment | Port  |
|--------------------------|-------------|-------|
| Jenkins                  | Prod        | 21080 |
| Nexus                    | Prod        | 21081 |
| Docker shapshot registry | Prod        | 21901 |
| Docker releases registry | Prod        | 21902 |
| Docker cibar registry    | Prod        | 21903 |
