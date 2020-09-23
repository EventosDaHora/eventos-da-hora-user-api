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
EXPOSE 8001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/usr/local/lib/eventos-da-hora-user-api.jar"]



