# SpeakBuddy Sound Receiver API

## Overview

SpeakBuddy Sound Receiver API is a Spring Boot application designed to handle audio processing tasks. It includes functionalities for retrieving and converting audio files, as well as managing user and phrase mappings.

## Features

- Retrieve audio files based on user and phrase IDs.
- Convert audio files to different formats using the JAVE library.
- Manage mappings between users and phrases.

## Technologies Used

- Java
- Spring Boot
- Maven
- JUnit 5
- Mockito
- JAVE (Java Audio/Video Encoder)

## Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/noxymon-mekari/speakbuddy-sound-receiver-api.git
cd speakbuddy-sound-receiver-api
```

## Stack
### Database
he application uses the H2 database for development and testing purposes. H2 is an in-memory database that is lightweight and fast, making it ideal for development environments. Using H2 simplifies the portability of the application, as it does not require any external database setup. This allows developers to quickly set up and run the application without additional configuration.  For production environments, the application can be easily enhanced to use other proper RDBMS (Relational Database Management Systems) such as MySQL, PostgreSQL, or Oracle. This can be achieved by updating the database configuration in the `application.properties` file and including the necessary dependencies in the pom.xml file.  

### Server Servlet
The application is built using Spring Boot, which includes an embedded Tomcat server. This allows the application to run as a standalone Java application without the need for an external servlet container.  

### Background Job
The application uses JobRunr for background job processing. JobRunr is chosen for its simplicity and powerful features. It allows developers to define background jobs using plain Java code, without the need for complex configurations. JobRunr also supports distributed job processing, which means jobs can be executed across multiple instances of the application, providing scalability and fault tolerance. Additionally, audio converting processes can take a significant amount of time, and using JobRunr allows these tasks to be handled asynchronously, preventing the API from being blocked while waiting for the conversion to complete.

Currently, for simplicity, the application utilizes an in-memory storage provider for JobRunr. This is suitable for development and testing environments. However, for production environments, the storage provider can be enhanced to use a more robust and persistent storage solution, such as a relational database or a distributed storage system. This can be achieved by updating the storageProvider bean configuration in the JobrunrConfig class.