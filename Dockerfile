# Build Stage
FROM maven:3.6.3 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Production Stage
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENV PORT 8080
RUN apt-get update && apt-get install -y curl
CMD ["sh", "-c", "until curl -s mysql:3306; do sleep 1; done; java -jar app.jar"]
