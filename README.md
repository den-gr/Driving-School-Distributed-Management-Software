# Driving-School-Distributed-Management-Software

This is the project for the courses: Distributed Systems and Laboratory of Software Systems.
The objective of this project is to create a new software, useful for Driving Schools, and all aspects of this domain.

In particular:
- Manage Practical Driving Lessons;
- Manage Practical and Theoretical Exam's Management and all constraints and sub-arguments;
- Manage and historicize all info's about Subscribers, Instructors and Vehicles.

The project is based on creating a distributed system that implements Microservices Architecture and follows DevOps Principles, like Build Automation and Continuous Integration.

Domain Driven Design, is the methodology that has been followed to develop the project, together with Acceptance Test Driven Development to test and validate Business Requirements.

To know more about this project, refers to:
- Final Project Report: https://denguzawr22.github.io/Driving-School-Distributed-Management-Software/
- OpenApi docs - latest version: https://app.swaggerhub.com/apis/DenGuzawr22/DSDMS/latest

# How to execute and test the system

You must be aware that the project represents only Server Side of the complete application. So executing it will only check if all given tests pass correctly.

Please follow next guidelines to correctly start the project on your pc:
1. First executes Docker (Docker Desktop);
2. There must be at least Java 16 installed on the Pc;
3. Now you can proceed in executing the file named **_rerun_**
   1. **_rerun.sh_** for Unix like systems (MacOs or Linux);
   2. **_rerun.bat_** for Windows users.

## How Rerun script works

The script executes the following commands:
1. Gradle build of the entire project: includes tests of each microservice plus quality assurance controls;
2. Docker compose build: builds an image for each component of the system (one for each microservice, one for MongoDB and one for testing client);
3. Docker compose up: executes previously created images, following included dependencies (the flag serves the purpose to kill containers on exit after testing has finished);
4. Docker compose down: deallocate all containers active on the system.

```bash
./gradlew build
docker-compose build
docker-compose up --abort-on-container-exit
docker-compose down --rmi local
```

> **Attention**: during first execution all tests could not pass because of database not having enough time to finish installation. In this case, simply rerun the script.


### This project was developed by: Bacca Riccardo and Grushchak Denys.
