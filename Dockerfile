# Use OpenJDK 17 image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Build the project (Maven build)
RUN ./mvnw clean package

# Expose the port the app will run on (default 8080)
EXPOSE 8080

# Command to run the JAR file (change this to your actual JAR name)
CMD ["java", "-jar", "target/WeatherF-0.0.1-SNAPSHOT.jar"]
