# Winbits Proxy (Akka-Camel-Scala)

An Akka-Camel based project written in Scala

## Installation

- Be sure that winbits-backend-mobile is up and running at port 1338

### With SBT

- Install SBT (http://www.scala-sbt.org/release/tutorial/Setup.html)

- Run test: 
```sh
$ cd /project/folder
$ sbt test
```

- Run
```sh
$ sbt 'project gateways' 'run'
```

### With Maven

- Install Maven

- Install and run the project
```sh
$ make install
$ make run
```

### Dockerize

- Install Maven 

```sh
$ make dockerize
```

- The project will be running at port 1339

## Project structure

### project: domains

Contains the business logic of the application.

### project: services

Application services as AKKA actors.

### project: gateways

Entry point of the application. In this sample is a HTTP server.