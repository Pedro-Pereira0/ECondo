#!/bin/sh

#Package as a JAR file
./mvnw clean package -DskipTests

#Copy it to the docker folder
cp target/auth-0.0.1-SNAPSHOT.jar src/main/docker