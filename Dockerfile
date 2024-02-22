# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY target/*.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the environment variable for the port
ENV PORT 8080

# Command to run the application
# CMD ["java", "-jar", "app.jar"]
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
