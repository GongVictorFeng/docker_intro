# Docker Introduction

## Traditional Deployment
  * Deployment process described in a document
  * Operations team follows steps to:
    * Setup Hardware
    * Setup OS (Linux, Windows, Mac,...)
    * Install Software (Java, Python, NodeJs,...)
    * Setup Application Dependencies
    * Install Application
  * Manual approach:
    * Takes a lot of time
    * High chance of making mistakes

## Deployment Process with Docker
  * Simplified Deployment Process:
    * OS does not matter
    * Programming Language does not matter
    * Hardware does not matter
  * Developer creates a Docker Image
  * Operations run the Docker Image
    * Using a very simple command
  * Takeaway: Once you have a Docker Image, irrespective of what the docker image contains, you run it the same way!
  
## How Docker Make it Easy
  * Docker image has everything needed to run the application:
    * Operating System
    * Application Runtime (JDK or Python or NodeJS)
    * Application code and dependencies
  * Docker container can be run the same way everywhere:
    * Local machine
    * Corporate data center
    * Cloud

## Why Docker is popular
  * Standardized Application Packaging
    * Same packaging for all types of applications - Java, Python or JS
  * Multi Platform Support 
    * Local Machine, Data Center, Cloud -AWS, Azure and GCP
  * Isolation
    * Containers have isolation from one another

## What's happening in the Background
 `docker container run -d -p 5000:5000 directiveName/fileName:0.0.1.RELEASE`
 * Docker image is downloaded from Docker Registry (Default: Docker Hub)
   * https://hub.docker.com/r/directiveName/fileName
   * Image is a set of bytes
   * Container: Running Image
   * directiveName/fileName: Repository Name
   * 0.0.1.RELEASE: Tag (or version)
   * -p hostPort:containerPort: Maps internal docker port (container port) to a port on the host (host port)
     * By default, Docker uses its own internal network called bridge network
     * We are mapping a host port so that users can access your application
   * -d: Detached Mode (Do not tie up the terminal)

## Understanding Docker Terminology
  * Docker Image: A package representing specific version of application (or software)
    * Contains everything the app needs
      * OS, software, code, dependencies
  * Docker Registry: A place to store docker images
  * Docker Hub: A registry to host Docker images
  * Docker Repository: Docker images for a specific app (tags are used to differentiate different images)
  * Docker Container: Runtime instance of a docker image
  * Dockerfile: File with instructions to create a Docker image

## Dockerfile - 1 
`FROM openjdk:18.0-slim`  
`COPY target/*.jar app.jar`  
`EXPOSE 5000`  
`ENTRYPOINT ["java", "-jar", "/app.jar"]`
  * Dockerfile contains instruction to create Docker images
    * From - Sets a base image
    * Copy - Copies new files or directories into image
    * Expose - Informs Docker about the port that the container listens on at runtime
    * Entrypoint - Configure a command that will be run at container launch
  * docker build -t docker-intro/hello-world:v1 .

## Dockerfile - 2 - Build Jar File - Multi Stage
`FROM maven:3.8.6-openjdk-18-slim AS build`   
`WORKDIR /home/app`  
`COPY . /home/app`  
`RUN mvn -f /home/app/pom.xml clean package`  

`FROM openjdk:18.0-slim`  
`EXPOSE 5000`  
`COPY --from=build /home/app/target/*.jar app.jar`  
`ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]`   

  * The previous step, the jar file is created by Maven install in the local machine
  * Let build the jar file as part of creation of Docker Image
  * build does not make use of anything built on the local machine

## Dockerfile - 3 - Improve Layer Caching
`FROM maven:3.8.6-openjdk-18-slim AS build`  
`WORKDIR /home/app`  

`COPY ./pom.xml /home/app/pom.xml`  
`COPY ./src/main/java/com/docker/hello_word_java/HelloWordJavaApplication.java /home/app/src/main/java/com/docker/hello_word_java/HelloWordJavaApplication.java`  

`RUN mvn -f /home/app/pom.xml clean package`  

`COPY . /home/app`  
`RUN mvn -f /home/app/pom.xml clean package`  

`FROM openjdk:18.0-slim`  
`EXPOSE 5000`  
`COPY --from=build /home/app/target/*.jar app.jar`  
`ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]`  

  * The previous step - multi-stage approach takes long time for building even with a small code change
    * because the entire application needs to be rebuilt again
  * Docker caches every layer and tries to reuse it
  * use this feature to make build more efficient
    * copy pom.xml and application file to the image first, cuz it will not be changed often
    * do clean package to get all dependencies done
    * copy everything into image
    * then do clean package again, if there is no changes in the dependency, the previous layers can be reused.

## Spring Boot Maven Plugin -Create Docker Image
  * Spring Boot Maven Plugin: Provides Spring Boot support in Apache Maven
    * Example: Create executable jar package
    * Example: Run Spring Boot application
    * Example: Create a Container Image
    * Commands:
      * mvn spring-boot:repackage (create jar or war)
      * mvn spring-boot:run (Run application)
      * mvn spring-boot:start (Non-blocking. Use it to run integration tests)
      * mvn spring-boot:stop (Stop application started with start command)
      * mvn spring-boot:build-image (Build a container image)

## Docker Microservice
    `<plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <image>
                <name>gongvictorfeng/docker-intro-${project.artifactId}:${project.version}</name>
            </image>
            <pullPolicy>IF_NOT_PRESENT</pullPolicy>
        </configuration>
    </plugin>`  

  * added the above configuration in maven plugin in pom.xml
  * run command `mvn spring-boot:build-image` to build a container image
  * see implementation: https://github.com/GongVictorFeng/docker_intro/commit/93825d981b298cf756b00087090509b35bb35032

