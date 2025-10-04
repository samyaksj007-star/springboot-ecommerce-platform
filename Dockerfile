# Base image: OpenJDK 17
FROM openjdk:17-jdk-slim

# Working directory inside container
WORKDIR /app

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Copy jar file into container
COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]