docker-compose down
CALL ./gradlew build
docker-compose build
docker-compose up
