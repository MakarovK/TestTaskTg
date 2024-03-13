FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
