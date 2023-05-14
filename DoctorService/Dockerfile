FROM gradle:8.1.1-jdk17
WORKDIR /app

COPY ./build/output/DoctorService-SNAPSHOT.jar .

ARG MONGO_URI
ENV MONGO_URI="-Dmongo_uri=${MONGO_URI}"

ARG DOCTOR_PORT
ENV DOCTOR_PORT="-Ddoctor_port=${DOCTOR_PORT}"


CMD java $MONGO_URI $DOCTOR_PORT -jar ExamService-SNAPSHOT.jar