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