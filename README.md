# winbits-akka-camel-proxy
Enterprise Integration for Winbits

## Installation

### With SBT

- Run test:
```sh
$ sbt test
```

- Launch
```sh
$ sbt run
```

- Build && Push Docker Image
```sh
$ sbt docker
$ make docker-push
```

### With Maven

- Run test
```sh
$ mvn test
```

- Launch
```sh
$ make run
```

- Build && Push Docker Image
```sh
$ make docker-build
$ make docker-push
```

### Launch from docker images
```sh
$ docker-compose up
```

- The project should be running at port 5000 by default

## Project structure

### project: domains

Contains the business logic of the application.

### project: services

Application services as AKKA actors.

### project: gateways

Entry point of the application. In this sample is a HTTP server.
