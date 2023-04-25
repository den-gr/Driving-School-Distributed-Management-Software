#!/bin/sh
docker-compose down
./gradlew build
docker-compose build
docker-compose up