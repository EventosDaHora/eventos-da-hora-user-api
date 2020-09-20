#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/eventos-da-hora-user-api-*.jar /usr/local/lib/eventos-da-hora-user-api.jar
EXPOSE 8181
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/usr/local/lib/eventos-da-hora-user-api.jar"]


## Start with a base image containing Java runtime
#FROM openjdk:8-jdk-alpine
#
## Add a volume pointing to /tmp
#VOLUME /tmp
#
## Make port 8080 available to the world outside this container
#EXPOSE 8181
#
## The application's jar file
#ARG JAR_FILE=target/eventos-da-hora-user-api-*.jar
#
## Add the application's jar to the container
#ADD ${JAR_FILE} /opt/eventos-da-hora-user-api.jar
#
## Run the jar file
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/opt/eventos-da-hora-user-api.jar"]



