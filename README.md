# Winbits Proxy (Akka-Camel-Scala)

An Akka-Camel based project written in Scala

## Installation

- Be sure that the winbits-backend-mobile project is running at port 1338

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

- Go to: http://localhost:1339/ping

## Project structure

### project: domains

Contains the business logic of the application.

### project: services

Application services as AKKA actors.

### project: gateways

Entry point of the application. In this sample is a HTTP server.