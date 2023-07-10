# Stage 1: Build the application with Maven
FROM maven:3.8.3-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final Docker image with the built application
FROM openjdk:8-jre
WORKDIR /app
COPY --from=build /app/target/gdd-0.0.1-SNAPSHOT.jar ./gdd-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "gdd-0.0.1-SNAPSHOT.jar"]