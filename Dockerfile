FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/softwarev-0.0.1-SNAPSHOT.jar softwarev-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "softwarev-0.0.1-SNAPSHOT.jar"]
