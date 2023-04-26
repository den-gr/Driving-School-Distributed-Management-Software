FROM gradle:8.1.1-jdk17
WORKDIR /app
COPY ./build/output/SystemTester-0.0.1.jar .

CMD ["java", "-Ddocker=true", "-jar" , "SystemTester-0.0.1.jar"]