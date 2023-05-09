CALL ./gradlew build || exit /b 1
docker-compose build || exit /b 2
docker-compose up || exit /b 3
docker-compose down || exit /b 4