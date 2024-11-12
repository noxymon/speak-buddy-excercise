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

### Audio Conversion
The application uses the JAVE library for audio conversion tasks. JAVE is a Java library that provides an easy-to-use API for encoding and decoding audio and video files. It supports a wide range of audio and video formats, making it suitable for various conversion tasks. JAVE is chosen for its simplicity and robustness, allowing developers to convert audio files with ease. The library is included as a dependency in the pom.xml file, making it easy to integrate with the application.

### Mapstruct
The application uses MapStruct for mapping between entities and DTOs. MapStruct is a code generation library that simplifies the process of mapping between different Java objects. It generates mapping code at compile time, reducing the need for manual mapping code and improving performance. MapStruct is chosen for its ease of use and flexibility, allowing developers to define mapping rules using annotations. The library is included as a dependency in the pom.xml file, making it easy to integrate with the application.

## Unit Testing Approach
The application uses JUnit 5 and Mockito for unit testing. JUnit 5 is a popular testing framework for Java applications, providing annotations and APIs for writing and executing tests. Mockito is a mocking framework that allows developers to create mock objects for testing. By using JUnit 5 and Mockito, developers can write comprehensive unit tests to ensure the correctness and reliability of the application. The tests cover various scenarios, including positive and negative cases, edge cases, and exception handling. The tests are organized into different test classes based on the functionality being tested, making it easy to maintain and run the tests.

It is important to note that not every file in the project needs to be unit tested. Instead, the focus should be on testing the main domain logic. This approach ensures that the core functionalities of the application are thoroughly tested, while avoiding unnecessary tests for boilerplate or configuration code.
