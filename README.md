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