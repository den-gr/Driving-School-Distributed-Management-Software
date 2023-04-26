#!/bin/sh
./gradlew build
docker-compose build
docker-compose up
docker-compose down