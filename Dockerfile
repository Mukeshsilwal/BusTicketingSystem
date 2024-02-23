# Build Stage
FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Production Stage
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
#ENV PORT 8080
ENTRYPOINT ["sh", "-c", "until curl -s http://mysql:3306; do sleep 1; done; java -jar app.jar"]
