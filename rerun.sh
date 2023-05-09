#!/bin/sh
./gradlew build || exit 1
docker-compose build || exit 2
docker-compose up || exit 3
docker-compose down || exit 4