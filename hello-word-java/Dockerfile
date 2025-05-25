# Dockerfile - 1 - Creating Docker Image
# FROM openjdk:18.0-slim
# COPY target/*.jar app.jar
# EXPOSE 5000
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# Dockerfile - 2 - Build Jar File -Multi Stage
#FROM maven:3.8.6-openjdk-18-slim AS build
#WORKDIR /home/app
#COPY . /home/app
#RUN mvn -f /home/app/pom.xml clean package

#FROM openjdk:18.0-slim
#EXPOSE 5000
#COPY --from=build /home/app/target/*.jar app.jar
#ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]

# Dockerfile - 3 - Improve Layer Caching
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/docker/hello_word_java/HelloWordJavaApplication.java /home/app/src/main/java/com/docker/hello_word_java/HelloWordJavaApplication.java

RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18.0-slim
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
