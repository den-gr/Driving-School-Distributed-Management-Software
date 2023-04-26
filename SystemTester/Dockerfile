FROM gradle:8.1.1-jdk17
WORKDIR /app
COPY ./build/output/SystemTester-0.0.1.jar .

ARG DOSSIER_HOST=localhost
ENV STR="-Ddossier_host="
ENV HOST="${STR}${DOSSIER_HOST}"

CMD java $HOST -jar SystemTester-0.0.1.jar