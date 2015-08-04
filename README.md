# winbits-akka-camel-proxy
Enterprise Integration for Winbits

## Installation

### With Docker

```sh
$ docker run -d --name node-server -p 172.17.42.1:1338:1338 apiumtest/winbits-backend-mobile
$ docker run -d --name akka-server -p 1339:1339 --link node-server:node-server apiumtest/winbits-proxy-mobile
```

### With SBT

- Install SBT (http://www.scala-sbt.org/release/tutorial/Setup.html)

- Run test: 
```sh
$ cd /project/folder
$ sbt test
```

- Run
```sh
$ sbt run
```

### With Maven

- Install Maven

- Install and run the project
```sh
$ make install
$ make run
```

- Open
http://localhost:1339

## Project structure

### project: domains

Contains the business logic of the application.

### project: services

Application services as AKKA actors.

### project: gateways

Entry point of the application. In this sample is a HTTP server.
