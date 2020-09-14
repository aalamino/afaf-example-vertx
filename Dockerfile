# Cero stage: Package microservice
FROM maven:3.6.0-jdk-11 AS maven

# copy the pom and src code to the container
COPY ./ ./
 
# package our application code
RUN mvn clean package
 
# the second stage of our build will use open jdk 8 on alpine 3.9
FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-alpine-slim
 
# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /afaf-example-vertx/target/afaf-example-vertx-0.0.1-SNAPSHOT.jar /afaf-example-vertx.jar
 
# set the startup command to execute the jar
CMD ["java", "-jar", "/demo.jar"]