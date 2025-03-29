FROM openjdk:17-jdk-slim
COPY target/book-service-1.0-SNAPSHOT.jar /app/book-service.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/book-service.jar"]