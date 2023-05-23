#!/bin/sh
./gradlew build
docker-compose build
docker-compose up --abort-on-container-exit
docker-compose down --rmi local