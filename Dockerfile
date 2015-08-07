FROM apiumtest/maven
MAINTAINER apiumtest

EXPOSE 1339

ADD . /app

WORKDIR /app

USER root

RUN mvn clean

RUN mvn install

ENTRYPOINT ["java", "-cp", "target/gateways-1.0-SNAPSHOT.jar", "com.apiumtech.br.gateways.Orchestrator"]
