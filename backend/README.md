# rikishi backend

## Requirements

* Java 17 compiler and runtime, e.g. [OpenJDK 17](https://openjdk.org/projects/jdk/17/)

## Structure

The convention which is used in the project is _Separation by layers_
(e.g. service layer, repository layer, controller layer etc.)

For example:

```
com
|-- example
|    `-- sampleapplication
|        |-- controller
|            |-- HelloWorldController.java
|            |-- LoginController.java
|        |-- model
|            |-- User.java
|            |-- Group.java
|        |-- service
|            |-- UserService.java
|        |-- repository
|            |-- UserRepository.java
```

Some rules:

- Data classes related to business logic should be normally placed in the `model` package
- Database-related data classes should be placed in the `model/entity` package
- REST-related data classes should be placed in the `model/rest` package

> [!NOTE]  
> These rules are not strict and can be violated in certain cases.

## Install and run

```bash
./gradlew bootRun
```

## Development

## Test

```bash
./gradlew test
```

### Lint

```bash
./gradlew spotlessApply
```
