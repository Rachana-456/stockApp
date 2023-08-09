FROM openjdk:11
FROM mongo
COPY target/*.jar module1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","module1.jar"]
