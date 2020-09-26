# Cero stage: Package microservice
FROM maven:3.6.0-jdk-11 AS maven
WORKDIR /app
COPY . .
 
# package our application code
RUN mvn clean package
 
# the second stage of our build will use open jdk 8 on alpine 3.9
FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-alpine-slim
 
COPY --from=maven /app/target/*-with-dependencies.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar /app/app.jar"]