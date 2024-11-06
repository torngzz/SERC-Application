FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /target/serc_application-.0.1-SNAPSHOT.jar serc_application.jar
EXPOSE 8082
ENTRYPOINT [ "java", "-jar", "serc_application.jar" ]