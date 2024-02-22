# Build Stage
FROM maven:3.6.3-openjdk-11 as build
WORKDIR /app
COPY pom.xml .
RUN mvn clean install -DskipTests
COPY src src
RUN mvn package -DskipTests

# Production Stage
FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENV PORT 8080
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
