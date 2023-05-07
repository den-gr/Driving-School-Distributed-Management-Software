FROM gradle:8.1.1-jdk17
WORKDIR /app
COPY ./build/output/DrivingService-0.0.1.jar .

ARG MONGO_URI
ENV MONGO_URI="-Dmongo_uri=${MONGO_URI}"

ARG DRIVING_PORT
ENV DRIVING_PORT="-Ddriving_port=${DRIVING_PORT}"

CMD java $MONGO_URI $DRIVING_PORT -jar DrivingService-0.0.1.jar