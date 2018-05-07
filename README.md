# Back End Developer

## Challenge Overview
The aim of this exercise is to simulate real working conditions to provide context for a
code/design review session. The follow up review session will focus on your reasons for
database/API design and pseudo-code/code implementation. As such it isn’t necessary to
build a complete implementation, however having some runnable code is recommended
(preferably, but not required, in Java or Node.js).

## Instructions
The exercise is to develop a web API which allows the allocation of staff to shifts in a small
takeaway store. Attached is sample data giving the shifts that need to be filled over the
weekend and the staff available (along with some attributes).

There are several activities you could consider:

● Develop some questions (for the takeaway store owner) that support further
requirements that you might need in order to more fully specify such an application.

● Create a minimal database schema (or equivalently, ORM model definitions) for the
application. You could also demonstrate the effectiveness of your schema by writing
a routine to read the sample data from the database.

● Design a web API which could be used for communication between the web app's
server and client.

● Describe and/or implement an algorithm which generates an allocation of staff to
shifts.

## Considerations
Please provide relevant source code of your implementation and any documentation and
assumptions that you feel are appropriate.

● The intent of this challenge is to provide us an opportunity to judge your problem
solving, design and development skills.

● It is important to provide a solution that highlights your skills in these areas.

● It is also important that your solution highlights your knowledge of and approach to
agile software development and the key software engineering practices and
supporting processes that are important in an agile environment.

● It is not so important to provide a fully working application.

## Questions for the owner

● How would you like to balance the workload between your staff? And is that depending on the role?

● Is there a maximum of hours an employee can work a day/week?

● Is there a minimum of hours an employee can work a day/week?

● In case of a full shift, would you prefer to cover it with just one employee on one long shift or split the shift and have two employees covering it?

## Limitations

● As the days are defined per name, won't be able to plan more than one week ahead

## Generate the allocation of employees to shifts

Go to http://localhost:8080/v1/shift/allocate

## How it works

### SpringBoot

[Spring Boot](https://projects.spring.io/spring-boot/) is an Opinionated Java Framework for developing production-ready
Spring applications. Spring Boot favours convention over configuration and is designed to get you up and running as
quickly as possible.

### Gradle

[Gradle](https://gradle.org/) is the build tool for this project, and requires Java JDK to be installed. It is not
necessary to install gradle.

#### Building the application

This project makes use of [The Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html). The
following command will run a full build of the project.

    gradlew build

#### Running the application

This project makes use of the
[Gradle Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-gradle-plugin.html)
plugin. To start the application locally just run the following command

    gradlew bootRun

## H2 Console

The H2 DB console can be accessed in the browser. Navigate to [/v1/h2](http://localhost:8080/v1/h2) to access the console,
and ensure the `JDBC URL` matches the one defined in the `application.yml` file: jdbc:h2:file:~/simple-shift-db

**Note 1**: removing the `database_init` file, which will be placed in your home by default, will reset the database.
**Note 2**: if the database is not initialised properly, run the SQL file from this web console.

## Flyway

[Flyway](https://flywaydb.org/) is a database migration tool and will initialze the H2 database with sample data. The
scripts can be found in `src/main/resources/db/migration`
