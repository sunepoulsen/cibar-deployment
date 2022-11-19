# Ciber Deployment

This repository contains Docker Compose files to run all the DevOps containers in a single environment with Docker Compose. Each container will mount 
its production data to the local filesystem to keep the production data safe if the application containers are deleted.

## Applications

The current applications are deployed:

- Nexus: Maven repository for dependencies.

## Ports

The applications are using these ports

| Application Name         | Protocol | Environment | Internal Port  | External Port  |
|--------------------------|----------|-------------|----------------|----------------|
| Nexus                    | HTTP     | Prod        | 8081           | 21081          |
| Docker shapshot registry | HTTP     | Prod        | 9101           | 21901          |
| Docker shapshot registry | HTTPS    | Prod        | 9102           | -              |
| Docker releases registry | HTTP     | Prod        | 9103           | 21903          |
| Docker releases registry | HTTPS    | Prod        | 9104           | -              |
| Docker cibar registry    | HTTP     | Prod        | 9201           | 21911          |
| Docker cibar registry    | HTTPS    | Prod        | 9202           | 21912          |
