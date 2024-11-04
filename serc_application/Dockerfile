FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.8.5-openjdk-17
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar SercApplication.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","demo.jar"]
