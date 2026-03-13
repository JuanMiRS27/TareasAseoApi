# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
ARG SERVICE_MODULE
WORKDIR /app

COPY pom.xml ./
COPY auth-service/pom.xml auth-service/pom.xml
COPY task-service/pom.xml task-service/pom.xml
RUN mvn -q -pl ${SERVICE_MODULE} -am dependency:go-offline

COPY auth-service/src auth-service/src
COPY task-service/src task-service/src
RUN mvn -q -pl ${SERVICE_MODULE} -am clean package spring-boot:repackage -DskipTests \
    && cp /app/${SERVICE_MODULE}/target/${SERVICE_MODULE}-1.0.0.jar /app/app.jar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
