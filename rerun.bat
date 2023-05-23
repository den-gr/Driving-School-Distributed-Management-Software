CALL ./gradlew build || exit /b 1
docker-compose build || exit /b 2
docker-compose up --abort-on-container-exit || exit /b 3
docker-compose down --rmi local || exit /b 4