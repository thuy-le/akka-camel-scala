FROM java
MAINTAINER apiumtest

EXPOSE 1339

ADD target/. /app

WORKDIR /app

USER root

RUN ls

ENTRYPOINT ["java", "-cp", "gateways-1.0-SNAPSHOT.jar", "com.apiumtech.br.gateways.Orchestrator"]
