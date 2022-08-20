FROM openjdk:11-jre-slim

ADD target/component-processing-service-0.0.1-SNAPSHOT.jar ComponentProcessingService-app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/ComponentProcessingService-app.jar"]