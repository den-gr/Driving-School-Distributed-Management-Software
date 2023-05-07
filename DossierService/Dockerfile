FROM gradle:8.1.1-jdk17
WORKDIR /app
COPY ./build/output/DossierService-0.0.1.jar .

ARG MONGO_URI
ENV MONGO_URI="-Dmongo_uri=${MONGO_URI}"

ARG DOSSIER_PORT
ENV DOSSIER_PORT="-Ddossier_port=${DOSSIER_PORT}"

CMD java $MONGO_URI $DOSSIER_PORT -jar DossierService-0.0.1.jar

