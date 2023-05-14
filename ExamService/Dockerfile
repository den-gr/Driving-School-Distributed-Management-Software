FROM gradle:8.1.1-jdk17
WORKDIR /app

COPY ./build/output/ExamService-SNAPSHOT.jar .

ARG MONGO_URI
ENV MONGO_URI="-Dmongo_uri=${MONGO_URI}"

ARG EXAM_PORT
ENV DRIVING_PORT="-Dexam_port=${EXAM_PORT}"


CMD java $MONGO_URI $EXAM_PORT -jar ExamService-SNAPSHOT.jar