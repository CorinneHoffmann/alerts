# Safetynet alerts
entreprise safetynet projet alerts

An app for help urgency when occurs hurricane, or fire, or flood. 
We provide some informations about people who live in a city (phone, adress, email....) 
This app uses Java to run and stores the data in memory. Data are loaded with Json file.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.3
- Spring Boot

- Log4j 

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html


### Running App

File Json are present under the `resources` folder in the code base.

Finally, you will be ready to import the code into an IDE of your choice and run the App.java to launch the application.

### Testing

The app has unit tests and integration tests written.
To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.
- JUNIT et Jacoco

`mvn test`

### Reports

To obtains tests reports execute the below command.

'mvn site'
