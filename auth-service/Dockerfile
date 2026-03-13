# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY auth-service/pom.xml auth-service/pom.xml
COPY task-service/pom.xml task-service/pom.xml
RUN mvn -q -pl auth-service -am dependency:go-offline
COPY auth-service/src auth-service/src
RUN mvn -q -pl auth-service -am clean package spring-boot:repackage -DskipTests && cp /app/auth-service/target/auth-service-1.0.0.jar /app/auth-service.jar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/auth-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
