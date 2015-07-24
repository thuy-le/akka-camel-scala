# Winbits Proxy (Akka-Camel-Scala)

An Akka-Camel based project written in Scala

## Installation

- Installl SBT (http://www.scala-sbt.org/release/tutorial/Setup.html)
- Run test: 
```sh
$ cd /project/folder
$ sbt test
```

- Run
```sh
$ sbt 'project gateways' 'run'
```

## Project structure

### project: domains

Contains the business logic of the application.

### project: services

Application services as AKKA actors.

### project: gateways

Entry point of the application. In this sample is a HTTP server.