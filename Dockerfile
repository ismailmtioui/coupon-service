# Use a lightweight base image with JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
# Make sure to replace 'coupon-service.jar' with your actual JAR file name
COPY target/coupon-service-0.0.1-SNAPSHOT.jar coupon-service.jar

# Expose the application port
EXPOSE 8090

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "coupon-service.jar"]
