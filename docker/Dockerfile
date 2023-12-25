# Build stage
FROM maven:3.8.4-openjdk-11 AS builder

WORKDIR /app

# Copy only the POM file first to leverage Docker cache
COPY pom.xml /app/

# Copy the entire project
COPY . /app/

# Build the project
RUN mvn clean package

# Final stage
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "transaction-0.0.1-SNAPSHOT.jar"]
