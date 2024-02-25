FROM maven:3.8-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /target/spring-boot-docker.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]