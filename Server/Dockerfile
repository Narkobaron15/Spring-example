# Use an official Java runtime as a parent image
FROM openjdk:20-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/spu123.jar /app/spu123.jar

# Expose Spring boot port
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "spu123.jar"]
